package org.opensails.component.tester;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.opensails.sails.IResourceResolver;
import org.opensails.sails.Sails;
import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.oem.BaseConfigurator;
import org.opensails.sails.oem.ResourceResolver;
import org.opensails.sails.template.viento.VientoTemplateRenderer;
import org.opensails.sails.tester.SailsTester;
import org.opensails.sails.tester.SailsTesterConfigurator;
import org.opensails.sails.url.IUrl;

public class SailsComponentTester extends SailsTester {
	protected Map<String, TestComponent> contentMap = new HashMap<String, TestComponent>();

	public SailsComponentTester(Class<? extends BaseConfigurator> configurator) {
		super(configurator);
	}

	public <C extends IComponentImpl> TestComponent<C> component(Class<C> componentClass) {
		String eventPath = String.format("dynamicallyGeneratedForTestingComponents/%s", Sails.componentName(componentClass));
		TestComponent<C> testComponent = new TestComponent<C>(this, createGetEvent(eventPath), componentClass);
		contentMap.put(eventPath + VientoTemplateRenderer.TEMPLATE_IDENTIFIER_EXTENSION, testComponent);
		return testComponent;
	}

	@Override
	protected SailsTesterConfigurator instrumentedConfigurator(Class<? extends BaseConfigurator> configuratorClass) {
		return new SailsTesterConfigurator(configuratorClass) {
			@Override
			public void configure(ResourceResolver resourceResolver) {
				resourceResolver.push(new IResourceResolver() {
					public boolean exists(IUrl applicationUrl) {
						return false; // not called in this situation
					}

					public boolean exists(String identifier) {
						return false; // not called in this situation
					}

					public InputStream resolve(IUrl applicationUrl) {
						return null;
					}

					public InputStream resolve(String identifier) {
						return contentMap.get(identifier).referringTemplateContent();
					}
				});
				super.configure(resourceResolver);
			}
		};
	}
}
