package org.opensails.sails.util;

import java.io.IOException;
import java.io.OutputStream;

public class NullOutputStream extends OutputStream {
	@Override
	public void write(int b) throws IOException {}
}
