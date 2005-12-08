package org.opensails.sails.form;

import java.util.*;

import org.apache.commons.lang.*;
import org.opensails.sails.model.IPropertyAccessor.*;

/**
 * Why? Provides a way for objects that are created within a dependancy
 * injection container to declare their need for the form fields of the current
 * post event. And, since we have it, also provides ability to remove fields -
 * the HttpServletRequest won't allow that.
 * 
 * @see org.opensails.sails.form.HtmlForm
 */
public class FormFields {
	public static FormFields quick(Object... objects) {
		if (objects.length % 2 != 0) throw new IllegalArgumentException("Must provide key value pairs. You have given an odd number of arguments.");
		Map<String, String[]> map = new HashMap<String, String[]>();
		for (int i = 0; i < objects.length; i += 2) {
			String key = (String) objects[i];
			Object value = objects[i + 1];
			if (value instanceof String[]) map.put(key, (String[]) value);
			else map.put(key, new String[] { (String) value });
		}
		return new FormFields(map);
	}

	protected Map<String, String[]> backingMap;

	public FormFields() {
		this(new HashMap<String, String[]>());
	}

	public FormFields(Map<String, String[]> backingMap) {
		this.backingMap = backingMap;
	}

	public boolean contains(String key) {
		return backingMap.containsKey(key);
	}

	public String[] fieldNames() {
		return (String[]) backingMap.keySet().toArray(new String[backingMap.keySet().size()]);
	}

	public Set fieldNamesSet() {
		return backingMap.keySet();
	}

	/**
	 * Provides for the removal of value for fieldName.
	 * 
	 * @param fieldName
	 * @return the value, null if not set
	 */
	public Object remove(String fieldName) {
		return backingMap.remove(fieldName);
	}

	public void setValue(String name, String value) {
		backingMap.put(name, new String[] { value });
	}

	public void setValues(String name, String[] values) {
		backingMap.put(name, values);
	}

	/**
	 * @return a copy of the fields in a Map
	 */
	public Map<String, Object> toMap() {
		return new HashMap<String, Object>(backingMap);
	}

	/**
	 * Coerces the value for fieldName into a single String. If the value is
	 * actually a String[], the String at index 0 is returned.
	 * 
	 * @param fieldName
	 * @return a String for fieldName
	 */
	public String value(String fieldName) {
		Object value = backingMap.get(fieldName);
		if (value.getClass().isArray()) {
			String[] values = (String[]) value;
			if (values.length == 0) return nullValue((String) null);
			if (values.length >= 1) value = values[0];
		}
		if (StringUtils.isEmpty((String) value)) return nullValue((String) value);
		return (String) value;
	}

	public Object valueAs(String fieldName, FieldType fieldType) {
		switch (fieldType) {
		case STRING:
			return value(fieldName);
		default:
			return values(fieldName);
		}
	}

	/**
	 * Coerces the value for fieldName into a String[]. If the value is actually
	 * a String, the String is placed in a String[] with length of 1.
	 * 
	 * @param fieldName
	 * @return a String[] for fieldName
	 */
	public String[] values(String fieldName) {
		String[] values = null;
		Object value = backingMap.get(fieldName);
		if (value.getClass().isArray()) {
			values = (String[]) value;
			if (values.length == 0) return nullValue((String[]) null);
		} else values = new String[] { (String) value };
		return values;
	}

	/**
	 * If you desire to return something other than null, like an empty String,
	 * for non-extant or blank values, override this.
	 * 
	 * @param nullOrBlankString
	 * @return the value to use when the field has a null or empty String
	 */
	protected String nullValue(String nullOrBlankString) {
		return null;
	}

	/**
	 * If you desire to return something other than null, like an empty
	 * String[], for non-extant or zero-length values, override this.
	 * 
	 * @param nullOrZeroLengthStringArray
	 * @return the value to use when the field has a null or empty String
	 */
	protected String[] nullValue(String[] nullOrZeroLengthStringArray) {
		return null;
	}
}
