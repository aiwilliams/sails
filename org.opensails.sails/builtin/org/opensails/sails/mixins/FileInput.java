package org.opensails.sails.mixins;

import org.opensails.sails.form.html.InputElement;

public class FileInput extends InputElement<FileInput> {
	public FileInput(String name) {
		super("file", name);
	}
}
