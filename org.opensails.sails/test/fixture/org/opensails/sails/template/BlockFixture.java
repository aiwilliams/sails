package org.opensails.sails.template;

import org.opensails.viento.Block;

public class BlockFixture {
	public static Block create(final String blockReturns) {
		return new Block(null, null) {
			@Override
			public String evaluate() {
				return blockReturns;
			}
		};
	}
}
