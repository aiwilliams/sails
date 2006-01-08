package org.opensails.sails.http;

public class HttpHeader {
	protected final String name;
	protected String value;

	protected HttpHeader(String name) {
		this(name, null);
	}

	protected HttpHeader(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String name() {
		return name;
	}

	@Override
	public String toString() {
		return value();
	}

	public String value() {
		return value;
	}
}
