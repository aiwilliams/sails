package org.opensails.sails.adapter;

import org.opensails.sails.form.FileUpload;
import org.opensails.sails.form.FormFields;

/**
 * Declares what the values for/from the web should be. This allows for the
 * IAdapter to declare how the model state is represented in an html ui.
 * 
 * @param <T> the value type, like String.class, String[].class,
 *        FileUpload.class
 */
public abstract class FieldType<T> {
	public static final FieldType<FileUpload> FILE_UPLOAD = new FieldType<FileUpload>() {
		@Override
		public FileUpload getValue(FormFields formFields, String name) {
			return formFields.file(name);
		}
	};
	public static final FieldType<String> STRING = new FieldType<String>() {
		@Override
		public String getValue(FormFields formFields, String name) {
			if (formFields.isFile(name)) return formFields.file(name).stringContent();
			else return formFields.value(name);
		}
	};
	public static final FieldType<String[]> STRING_ARRAY = new FieldType<String[]>() {
		@Override
		public String[] getValue(FormFields formFields, String name) {
			return formFields.values(name);
		}
	};

	public abstract T getValue(FormFields formFields, String name);
}
