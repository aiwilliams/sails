package org.opensails.sails.form.html;

import java.util.Arrays;
import java.util.List;

/**
 * Provides a SelectModel based on a List where there is an alternative option -
 * that is, one not in the provided List.
 * 
 * The alternative option is not a real object - how would it be converted if it
 * were? The goal here is to provide a way to dislay a select of options where
 * one value represents a flag that tells the form processor that the user
 * desires to use an alternate value for the property. It is up to the
 * application to fulfill that request.
 */
public class AlternativeSelectModel extends ListSelectModel {
    public static final String DEFAULT_ALTERNATIVE_OPTION_LABEL = "--- Other ---";
    public static final String DEFAULT_ALTERNATIVE_OPTION_VALUE = "_alternative_option_value_";

    protected String alternativeOptionLabel;
    protected String alternativeOptionValue;

    public AlternativeSelectModel(List<Object> options) {
        this(options, DEFAULT_ALTERNATIVE_OPTION_LABEL);
    }

    public AlternativeSelectModel(List<Object> options, String alternativeOptionLabel) {
        this(options, alternativeOptionLabel, DEFAULT_ALTERNATIVE_OPTION_VALUE);
    }

    public AlternativeSelectModel(List<Object> options, String alternativeOptionLabel, String alternativeOptionValue) {
        super(options);
        this.alternativeOptionLabel = alternativeOptionLabel;
        this.alternativeOptionValue = alternativeOptionValue;
    }

    public AlternativeSelectModel(Object[] options) {
        this(Arrays.asList(options));
    }

    public AlternativeSelectModel(Object[] options, String alternativeOptionLabel) {
        this(Arrays.asList(options), alternativeOptionLabel);
    }

    public AlternativeSelectModel(Object[] options, String alternativeOptionLabel, String alternativeOptionValue) {
        this(Arrays.asList(options), alternativeOptionLabel, alternativeOptionValue);
    }

    @Override
    public String getLabel(int index) {
        if (index == 0) return alternativeOptionLabel;
        return super.getLabel(index);
    }

    @Override
    public String getLabel(Object object) {
        if (alternativeOptionValue.equals(object)) return alternativeOptionLabel;
        return super.getLabel(object);
    }

    @Override
    public Object getOption(int index) {
        if (index == 0) return alternativeOptionValue;
        return super.getOption(index - 1);
    }

    @Override
    public int getOptionCount() {
        return super.getOptionCount() + 1;
    }

    @Override
    public String getValue(int index) {
        if (index == 0) return alternativeOptionValue;
        return super.getValue(index);
    }

    @Override
    public Object translateValue(String value) {
        if (alternativeOptionValue.equals(value)) return value;
        return super.translateValue(value);
    }

    @Override
    protected int indexOf(Object object) {
        return super.indexOf(object) + 1;
    }
}
