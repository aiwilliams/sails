package org.opensails.rigging;

import junit.framework.Assert;

public class ShamComponentWithDependencies {
	public ShamComponent dependency;

	public ShamComponentWithDependencies(ShamComponent one, ShamComponent two, String dontUseThisOne) {
		Assert.fail("Shouldn't call a constructor that cannot be satisfied");
	}
	
	public ShamComponentWithDependencies(ShamComponent one, ShamComponent two) {
		this.dependency = one;
	}

	public ShamComponentWithDependencies(ShamComponent component) {
		Assert.fail("Should call the greedier constructor");
	}
}
