package org.opensails.sails.form;

public class FormMeta {
	/**
	 * The prefix of all form meta fields.
	 */
	public static final String META_PREFIX = "form.meta.";

	public static final String ACTION_PREFIX = META_PREFIX + "action.";

	public static final String CHECKBOX_PREFIX = META_PREFIX + "cb.";

	// TODO: Move this outta here. Need object for Metas
	public static String action(String action, String... parameters) {
		StringBuilder metaName = new StringBuilder();
		metaName.append(ACTION_PREFIX);
		metaName.append(action);
		for (String param : parameters) {
			metaName.append("_");
			metaName.append(param);
		}
		return metaName.toString();
	}
}
