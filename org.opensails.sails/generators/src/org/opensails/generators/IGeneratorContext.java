package org.opensails.generators;

import org.opensails.ezfile.EzFile;
import org.opensails.viento.Binding;

public interface IGeneratorContext {
	EzFile template(String path);

	String render(String path, Binding binding);
}
