package org.opensails.sails.configurator;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.tester.browser.SailsTestApplication;

public class SailsConfiguratorTests extends TestCase {
	static List<String> configureCalled = new ArrayList<String>();

	public void testIObjectPersister() throws Exception {
		SailsTestApplication application = new SailsTestApplication(CustomConfigurator.class);
		assertTrue(configureCalled.contains("IObjectPersisterConfigurator#configure(IConfigurableSailsApplication, ApplicationContainer)"));
		assertFalse(configureCalled.contains("IObjectPersisterConfigurator#configure(IConfigurableSailsApplication, RequestContainer)"));

		configureCalled.clear();
		application.openBrowser().get();
		assertFalse(configureCalled.contains("IObjectPersisterConfigurator#configure(IConfigurableSailsApplication, ApplicationContainer)"));
		assertTrue(configureCalled.contains("IObjectPersisterConfigurator#configure(IConfigurableSailsApplication, RequestContainer)"));
	}

	@Override
	protected void setUp() throws Exception {
		configureCalled.clear();
	}

	public static class CustomConfigurator extends SailsConfigurator {
		@Override
		public IObjectPersisterConfigurator getPersisterConfigurator() {
			return new IObjectPersisterConfigurator() {
				public void configure(IConfigurableSailsApplication application, ApplicationContainer container) {
					configureCalled.add("IObjectPersisterConfigurator#configure(IConfigurableSailsApplication, ApplicationContainer)");
				}

				public void configure(ISailsEvent event, RequestContainer container) {
					configureCalled.add("IObjectPersisterConfigurator#configure(IConfigurableSailsApplication, RequestContainer)");
				}
			};
		}
	}
}
