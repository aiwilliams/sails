package org.opensails.generators;

public interface IGeneratorArguments {

	IGeneratorArguments NULL = new IGeneratorArguments() {
		public String named(String string) {
			return null;
		}
	};

	String named(String string);

}
