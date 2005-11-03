package org.opensails.sails.oem;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.opensails.sails.ISailsApplication;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.controller.oem.Controller;
import org.opensails.sails.form.FormFields;
import org.opensails.sails.form.html.Submit;

public class PostEvent extends AbstractEvent {
	protected static final Pattern XY_COORDINATE_PATTERN = Pattern.compile(".*?\\.[x|y]$");
	protected static final Pattern XY_COORDINATE_REPLACE = Pattern.compile("\\.[x|y]$");

	public PostEvent(ISailsApplication application, HttpServletRequest req, HttpServletResponse resp) {
		super(application, req, resp);
	}

	@Override
	public void beginDispatch() {}

	@Override
	public void endDispatch() {}

	@Override
	public String getActionName() {
		String actionMetaValue = findActionMeta();
		if (actionMetaValue == null) return super.getActionName();
		if (actionMetaValue.indexOf('/') > 0) return actionMetaValue.substring(0, actionMetaValue.indexOf('/'));
		else return actionMetaValue;
	}

	@Override
	public String[] getActionParameters() {
		String[] submitActionParameters = findSubmitActionParameters();
		if (submitActionParameters != null) return submitActionParameters;
		return super.getActionParameters();
	}

	@Override
	public IActionResult visit(Controller controller) {
		return controller.process(this);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void containerSet() {
		super.containerSet();
		container.register(FormFields.class, new FormFields(req.getParameterMap()));
	}

	protected String findActionMeta() {
		String[] actionMetas = getFieldNamesPrefixed(Submit.ACTION_PREFIX);
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
		int actionParameterStart = actionMetaValue.indexOf('/');
		if (actionParameterStart > 0) parameters = actionMetaValue.substring(actionParameterStart + 1).split("/");
		return parameters;
	}

	protected boolean isImageSubmit(String actionMeta) {
		return XY_COORDINATE_PATTERN.matcher(actionMeta).matches();
	}
}
