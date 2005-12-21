package org.opensails.sails.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.opensails.sails.SailsException;
import org.opensails.sails.adapter.FieldType;

/**
 * Why? Provides:
 * <ul>
 * <li>a way for objects that are created within a dependancy injection
 * container to declare their need for the form fields of the current post event</li>
 * <li>ability to remove fields - the HttpServletRequest won't allow that</li>
 * <li>a unified interface to reading values from different enctype (like
 * multipart forms)</li>
 * </ul>
 * 
 * @see org.opensails.sails.form.HtmlForm
 */
public class FormFields {
	public static FormFields quick(Object... objects) {
		if (objects.length % 2 != 0) throw new IllegalArgumentException("Must provide key value pairs. You have given an odd number of arguments.");
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < objects.length; i += 2) {
			String key = (String) objects[i];
			Object value = objects[i + 1];
			if (value instanceof String[]) map.put(key, (String[]) value);
			else map.put(key, new String[] { (String) value });
		}
		return new FormFields(map);
	}

	protected Map<String, Object> backingMap;

	public FormFields() {
		this(new HashMap<String, Object>());
	}

	@SuppressWarnings("unchecked")
	public FormFields(HttpServletRequest request) {
		if (!DiskFileUpload.isMultipartContent(request)) {
			backingMap = new HashMap<String, Object>(request.getParameterMap());
			return;
		}

		backingMap = new HashMap<String, Object>();
		DiskFileUpload upload = new DiskFileUpload();
		try {
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				String fieldName = item.getFieldName();
				if (item.isFormField()) addFieldValue(fieldName, item.getString());
				else backingMap.put(fieldName, new FileUpload(item));
			}
		} catch (FileUploadException e) {
			throw new SailsException(e);
		}
	}

	private FormFields(Map<String, Object> backingMap) {
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

	public FileUpload file(String name) {
		return (FileUpload) backingMap.get(name);
	}

	public boolean isEmpty() {
		return backingMap.isEmpty();
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
		if (value == null) return null;
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
		case STRING_ARRAY:
			return values(fieldName);
		case FILE_UPLOAD:
			return file(fieldName);
		default:
			throw new SailsException(String.format("Could not provide the value of %s as %s", fieldName, fieldType));
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
		Object value = backingMap.get(fieldName);
		if (value == null) return null;

		String[] values = null;
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

	private void addFieldValue(String fieldName, String string) {
		String[] existing = (String[]) backingMap.get(fieldName);
		if (existing == null) existing = new String[] { string };
		else {
			String[] expanded = new String[existing.length + 1];
			System.arraycopy(existing, 0, expanded, 0, existing.length);
			existing = expanded;
		}
		backingMap.put(fieldName, existing);
	}
}
