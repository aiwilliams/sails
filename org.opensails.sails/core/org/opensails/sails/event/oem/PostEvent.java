package org.opensails.sails.event.oem;

import java.util.regex.*;

import javax.servlet.http.*;

import org.apache.commons.lang.*;
import org.opensails.sails.*;
import org.opensails.sails.action.*;
import org.opensails.sails.action.oem.*;

import static org.opensails.sails.form.FormMeta.*;

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
		String[] actionMetas = getFieldNamesPrefixed(ACTION_PREFIX);
		if (actionMetas.length < 1) return null;
		if (actionMetas.length == 2) return XY_COORDINATE_REPLACE.matcher(actionMetas[0]).replaceFirst(StringUtils.EMPTY);
		for (int i = 0; i < actionMetas.length; i++) {
			if (isImageSubmit(actionMetas[i])) continue;
			return actionMetas[i];
		}
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
