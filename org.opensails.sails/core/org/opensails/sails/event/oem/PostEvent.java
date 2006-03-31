package org.opensails.sails.event.oem;

import static org.opensails.sails.form.FormMeta.ACTION_PREFIX;

import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.opensails.sails.ISailsApplication;
import org.opensails.sails.action.IActionResult;
import org.opensails.sails.action.oem.ActionParameterList;

public class PostEvent extends AbstractEvent {
	protected static final Pattern XY_COORDINATE_PATTERN = Pattern.compile(".*?\\.[x|y]$");
	protected static final Pattern XY_COORDINATE_REPLACE = Pattern.compile("\\.[x|y]$");

	public PostEvent(HttpServletRequest req, HttpServletResponse resp) {
		super(req, resp);
	}

	public PostEvent(ISailsApplication application, HttpServletRequest req, HttpServletResponse resp) {
		super(application, application.getContainer(), req, resp);
	}

	@Override
	public String getActionName() {
		String actionMetaValue = findActionMeta();
		if (actionMetaValue == null) return super.getActionName();
		if (actionMetaValue.indexOf('_') > 0) return actionMetaValue.substring(0, actionMetaValue.indexOf('_'));
		else return actionMetaValue;
	}

	@Override
	public IActionResult visit(IActionEventProcessor eventProcessor) {
		return eventProcessor.process(this);
	}

	@Override
	protected ActionParameterList createParameters(String[] eventParameters) {
		String[] submitActionParameters = findSubmitActionParameters();
		if (submitActionParameters == null) return super.createParameters(eventParameters);
		return super.createParameters(submitActionParameters);
	}

	protected String findActionMeta() {
		Set<String> actionMetas = getFormFields().getNamesPrefixed(ACTION_PREFIX);
		if (actionMetas.size() < 1) return null;
		if (actionMetas.size() == 2) return XY_COORDINATE_REPLACE.matcher(actionMetas.iterator().next()).replaceFirst(StringUtils.EMPTY);
		for (String meta : actionMetas)
			if (!isImageSubmit(meta)) return meta;
		return null;
	}

	protected String[] findSubmitActionParameters() {
		String actionMetaValue = findActionMeta();
		if (actionMetaValue == null) return null;

		String[] parameters = ArrayUtils.EMPTY_STRING_ARRAY;
		int actionParameterStart = actionMetaValue.indexOf('_');
		if (actionParameterStart > 0) parameters = actionMetaValue.substring(actionParameterStart + 1).split("_");
		return parameters;
	}

	protected boolean isImageSubmit(String actionMeta) {
		return XY_COORDINATE_PATTERN.matcher(actionMeta).matches();
	}
}
