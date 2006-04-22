package org.opensails.mixin.tester;

import java.util.HashMap;
import java.util.Map;

import org.opensails.rigging.InstantiationListener;
import org.opensails.sails.configurator.SailsConfigurator;
import org.opensails.sails.tester.browser.Browser;
import org.opensails.sails.tester.browser.SailsTestApplication;
import org.opensails.viento.IBinding;

public class MixinTester {
	protected Browser browser;
	protected InstantiationListener<IBinding> exposer;
	protected Map<String, Object> exposures;

	public MixinTester(Class<? extends SailsConfigurator> configuratorClass) {
		this.exposures = new HashMap<String, Object>();
		this.exposer = new Exposer();

		SailsTestApplication sailsTestApplication = new SailsTestApplication(configuratorClass);
		browser = sailsTestApplication.openBrowser();
	}

	public void assertEquals(String expected, String vientoTemplate) {
		browser.getContainer().registerInstantiationListener(IBinding.class, exposer);
		browser.getTemplated(vientoTemplate).assertEquals(expected);
	}

	public void expose(String key, Object object) {
		exposures.put(key, object);
	}

	class Exposer implements InstantiationListener<IBinding> {
		public void instantiated(IBinding newInstance) {
			newInstance.putAll(exposures);
		}
	}
}
