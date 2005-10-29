package org.opensails.sails.form.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * A basic implementation of a
 * {@link org.javaonsails.sails.form.SelectModel SelectModel} where each item in
 * the provided List has it's toString result rendered as the value and label of
 * the HTML OPTION.
 * 
 * Override the appropriate methods to render in a specific way.
 */
public class ListSelectModel implements SelectModel {
	protected List<Object> list;

	public ListSelectModel(List<Object> list) {
		if (list == null) list = new ArrayList<Object>(0);
		this.list = new ArrayList<Object>(list);
	}

	public ListSelectModel(Object... options) {
		this(Arrays.asList(options));
	}

	public boolean contains(Object object) {
		return allOptions().contains(object);
	}

	public String getLabel(int index) {
		if (indexInRange(index)) return getLabel(getOption(index));
		throw new IndexOutOfBoundsException(String.valueOf(index) + " is out of range: 0 to " + getOptionCount());
	}

	public String getLabel(Object object) {
		return object == null ? NULL_OPTION_LABEL : object.toString();
	}

	public Object getOption(int index) {
		return allOptions().get(index);
	}

	public int getOptionCount() {
		return allOptions().size();
	}

	public String getValue(int index) {
		if (indexInRange(index)) return getValue(getOption(index));
		throw new IndexOutOfBoundsException(index + " is out of range: 0 to " + getOptionCount());
	}

	public String getValue(Object object) {
		return object == null ? NULL_OPTION_VALUE : object.toString();
	}

	public Object translateValue(String value) {
		for (Iterator iter = allOptions().iterator(); iter.hasNext();) {
			Object option = (Object) iter.next();
			if (getValue(option).equals(value)) return option;
		}
		throw new IllegalArgumentException("The option " + value + " is not legal");
	}

	protected List allOptions() {
		return list;
	}

	protected boolean indexInRange(int index) {
		return index >= 0 && index < getOptionCount();
	}

	protected int indexOf(Object object) {
		return allOptions().indexOf(object);
	}
}