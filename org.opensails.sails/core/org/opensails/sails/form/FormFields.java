package org.opensails.sails.form;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.SetUtils;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.opensails.sails.SailsException;

/**
 * Encapsulates the values of successful controls from a form submission. See
 * http://www.w3.org/TR/html4/interact/forms.html#submit-format to gain further
 * insight into the inconsistencies of the HTML specification.
 * <p>
 * Why? Provides:
 * <ul>
 * <li>a way for objects that are created within a dependancy injection
 * container to declare their need for the form fields of the current event</li>
 * <li>ability to remove fields - the HttpServletRequest won't allow that</li>
 * <li>a unified interface to reading values from different enctype (like
 * multipart forms)</li>
 * </ul>
 * 
 * @see org.opensails.sails.form.HtmlForm
 */
public class FormFields {
	/**
	 * The value to use when a field has a null or empty String.
	 * <p>
	 * If you desire #value() to return something other than null, like an empty
	 * String, for non-extant or blank values, set this to your liking.
	 */
	public static String NULL_OR_BLANK_STRING_VALUE = null;

	/**
	 * The value to use when a field has a null or empty String[].
	 * <p>
	 * If you desire #values() to return something other than an empty String[],
	 * like null, for non-extant or zero-length values, set this to your liking.
	 */
	public static String[] NULL_OR_EMPTY_STRING_ARRAY_VALUE = new String[0];

	protected Map<String, Object> backingMap;
	protected boolean multipartContent;

	@SuppressWarnings("unchecked")
	public FormFields(HttpServletRequest request) {
		multipartContent = isMultipartRequest(request);
		if (multipartContent) initializeFromMultipart(request);
		else backingMap = new HashMap<String, Object>(request.getParameterMap());
	}

	/**
	 * For subclassing
	 */
	protected FormFields() {
		this(new HashMap<String, Object>());
	}

	private FormFields(Map<String, Object> backingMap) {
		this.backingMap = backingMap;
	}

	public boolean contains(String key) {
		return backingMap.containsKey(key);
	}

	public FileUpload file(String name) {
		if (!multipartContent) throw new SailsException("Form was not multipart. Set enctype=\"multipart/form-data\" on your form if you want to upload files.");
		return (FileUpload) backingMap.get(name);
	}

	/**
	 * @return all current field names
	 * @see #getNames()
	 * @see #getNamesPrefixed(String)
	 */
	public Set<String> getAllNames() {
		return backingMap.keySet();
	}

	/**
	 * @return field names that are FormMetas, minus the prefix itself
	 */
	public Set<String> getMetaNames() {
		return getNamesPrefixed(FormMeta.META_PREFIX);
	}

	/**
	 * @return field names that are not FormMetas
	 * @see #getMetaNames()
	 * @see #getNamesPrefixed(String)
	 */
	public Set<String> getNames() {
		return backingMap.keySet();
	}

	/**
	 * @param prefix
	 * @return all field names with prefix, minus the prefix itself, includes
	 *         FormMetas
	 */
	@SuppressWarnings("unchecked")
	public Set<String> getNamesPrefixed(String prefix) {
		if (isEmpty()) return SetUtils.EMPTY_SET;
		Set<String> fieldNames = new HashSet<String>(5);
		for (String key : getAllNames())
			if (key.startsWith(prefix)) fieldNames.add(key.substring(prefix.length()));
		return fieldNames;
	}

	public boolean isEmpty() {
		return backingMap.isEmpty();
	}

	public boolean isFile(String name) {
		Object object = backingMap.get(name);
		return object != null && object instanceof FileUpload;
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

	public void setValue(String name, Object value) {
		if (value == null) return;
		if (value.getClass() != String.class) throw new IllegalArgumentException("Parameters can only be Strings");
		backingMap.put(name, new String[] { (String) value });
	}

	public void setValues(String name, String... values) {
		backingMap.put(name, values);
	}

	/**
	 * @return a copy of the fields in a Map
	 */
	public Map<String, Object> toMap() {
		return new HashMap<String, Object>(backingMap);
	}

	@Override
	public String toString() {
		if (isEmpty()) return "Empty form";
		StringBuilder string = new StringBuilder();
		for (String name : getNames()) {
			string.append(name);
			string.append(" :: ");
			string.append(value(name));
			string.append("\n");
		}
		return string.toString();
	}

	/**
	 * Coerces the value for fieldName into a single String. If the value is
	 * actually a String[], the String at index 0 is returned. If it is a
	 * FileUpload, the file name is returned.
	 * 
	 * @param fieldName
	 * @return a String for fieldName
	 */
	public String value(String fieldName) {
		Object value = backingMap.get(fieldName);
		if (value == null) return null;
		if (value instanceof FileUpload) value = ((FileUpload) value).filename();
		else value = stringValue(value);
		return (String) value;
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
		if (value == null) return NULL_OR_EMPTY_STRING_ARRAY_VALUE;

		String[] values = null;
		if (value.getClass().isArray()) {
			values = (String[]) value;
			if (values.length == 0) return NULL_OR_EMPTY_STRING_ARRAY_VALUE;
		} else values = new String[] { value(fieldName) };
		return values;
	}

	protected void addFieldValue(String fieldName, String string) {
		String[] existing = (String[]) backingMap.get(fieldName);
		if (existing == null) existing = new String[] { string };
		else {
			String[] expanded = new String[existing.length + 1];
			System.arraycopy(existing, 0, expanded, 0, existing.length);
			expanded[existing.length] = string;
			existing = expanded;
		}
		backingMap.put(fieldName, existing);
	}

	@SuppressWarnings("unchecked")
	protected void initializeFromMultipart(HttpServletRequest request) {
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

	protected boolean isMultipartRequest(HttpServletRequest request) {
		return DiskFileUpload.isMultipartContent(request);
	}

	/**
	 * Converts non-null value to a String. Does not handle FileUpload.
	 * 
	 * @param value
	 * @return String for value
	 */
	protected String stringValue(Object value) {
		if (value.getClass().isArray()) {
			String[] values = (String[]) value;
			if (values.length == 0) return NULL_OR_BLANK_STRING_VALUE;
			if (values.length >= 1) value = values[0];
		}
		if (StringUtils.isEmpty((String) value)) return NULL_OR_BLANK_STRING_VALUE;
		return (String) value;
	}
}
