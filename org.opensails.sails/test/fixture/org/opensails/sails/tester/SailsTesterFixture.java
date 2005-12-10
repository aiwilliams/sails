package org.opensails.sails.tester;

import org.opensails.sails.oem.BaseConfigurator;

public class SailsTesterFixture extends SailsTesterTest {
	public static SailsTester create() {
		return new SailsTester(BaseConfigurator.class);
	}

}
