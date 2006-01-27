package org.opensails.generators;

import java.util.Map;

import org.opensails.sails.util.Quick;

public class MapGeneratorArguments implements IGeneratorArguments {
	private final Map<String, String> backingMap;

	public MapGeneratorArguments(Map<String, String> input) {
		this.backingMap = input;
	}

	public String named(String value) {
		return backingMap.get(value);
	}

	public static MapGeneratorArguments create(String... namesAndValues) {
		return new MapGeneratorArguments(Quick.map(String.class, (Object[]) namesAndValues));
	}
}
