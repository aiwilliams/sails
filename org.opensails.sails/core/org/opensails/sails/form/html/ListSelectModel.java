package org.opensails.sails.form.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * A basic implementation of a
 * {@link org.javaonsails.sails.form.SelectModel SelectModel} where each item in
 * the provided List has it's toString result rendered as the value and label of
 * the HTML OPTION.
 * 
 * Override the appropriate methods to render in a specific way.
 */
public class ListSelectModel<T> implements SelectModel<T> {
	protected List<T> list;
	protected T selected;

	/**
	 * Convenience ctor for Collections.
	 * <p>
	 * A ListSelectModel expects things to be in order. Your Collection may not
	 * be. Expect things to be in random order.
	 * 
	 * @param options
	 */
	public ListSelectModel(Collection<T> options) {
		this(new ArrayList<T>(options));
	}

	/**
	 * @param options
	 */
	public ListSelectModel(List<T> options) {
		if (options == null) options = new ArrayList<T>(0);
		this.list = new ArrayList<T>(options);
	}

	public ListSelectModel(T... options) {
		this(Arrays.asList(options));
	}

	public boolean contains(T object) {
		return allOptions().contains(object);
	}

	public String getLabel(int index) {
		if (indexInRange(index)) return getLabel(getOption(index));
		throw new IndexOutOfBoundsException(String.valueOf(index) + " is out of range: 0 to " + getOptionCount());
	}

	public String getLabel(T object) {
		return object == null ? NULL_OPTION_LABEL : object.toString();
	}

	public T getOption(int index) {
		return allOptions().get(index);
	}

	public int getOptionCount() {
		return allOptions().size();
	}

	public T getSelected() {
		return selected;
	}

	public String getValue(int index) {
		if (indexInRange(index)) return getValue(getOption(index));
		throw new IndexOutOfBoundsException(index + " is out of range: 0 to " + getOptionCount());
	}

	public String getValue(T object) {
		return object == null ? NULL_OPTION_VALUE : object.toString();
	}

	public boolean hasSelected() {
		return selected != null;
	}

	public SelectModel select(T option) {
		selected = option;
		return this;
	}

	public T translateValue(String value) {
		for (T option : allOptions())
			if (getValue(option).equals(value)) return option;
		throw new IllegalArgumentException("The option " + value + " is not legal");
	}

	protected List<T> allOptions() {
		return list;
	}

	protected boolean indexInRange(int index) {
		return index >= 0 && index < getOptionCount();
	}

	protected int indexOf(T object) {
		return allOptions().indexOf(object);
	}
}