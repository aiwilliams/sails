package org.opensails.sails.form.html;

/**
 * The 'model' of an HTML SELECT form field. It provides all of the options as
 * well as methods that know how to adapt the options for rendering the HTML and
 * that can turn the HTML OPTION value attributes back into Objects.
 */
public interface SelectModel {
    /**
     * When the 'null' option is selected, this label is displayed to the user.
     */
    String NULL_OPTION_LABEL = "";

    /**
     * When the 'null' option is selected, this value is found in the post for
     * the property of this SelectModel.
     */
    String NULL_OPTION_VALUE = "_null_option_value_";

    /**
     * @param object
     * @return true if object is one of the options
     */
	public abstract boolean contains(Object object);

    /**
     * @return the label of the option at index
     */
    public abstract String getLabel(int index);

    /**
     * @return the label of the Object option
     */
    public abstract String getLabel(Object option);

    /**
     * @return the Object of the option at index
     */
    public abstract Object getOption(int index);

    /**
     * @return the number of options for this select
     */
    public abstract int getOptionCount();

    /**
     * @return the value of the option at index
     */
    public abstract String getValue(int index);

    /**
     * @return the value of the Object option
     */
    public abstract String getValue(Object object);
}