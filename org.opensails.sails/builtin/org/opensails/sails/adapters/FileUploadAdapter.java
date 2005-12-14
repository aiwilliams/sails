package org.opensails.sails.adapters;

import org.opensails.sails.adapter.AbstractAdapter;
import org.opensails.sails.adapter.AdaptationException;
import org.opensails.sails.adapter.FieldType;
import org.opensails.sails.form.FileUpload;

public class FileUploadAdapter extends AbstractAdapter<FileUpload, FileUpload> {
	public FileUpload forModel(Class<? extends FileUpload> modelType, FileUpload fromWeb) throws AdaptationException {
		return fromWeb;
	}

	public FileUpload forWeb(Class<? extends FileUpload> modelType, FileUpload fromModel) throws AdaptationException {
		return fromModel;
	}
	
	@Override
	public FieldType getFieldType() {
		return FieldType.FILE_UPLOAD;
	}
}
