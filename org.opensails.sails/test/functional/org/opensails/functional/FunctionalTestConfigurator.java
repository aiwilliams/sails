package org.opensails.functional;

import java.util.List;

import org.opensails.functional.controllers.EventTestController;
import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.configurator.ApplicationPackage;
import org.opensails.sails.configurator.IContainerConfigurator;
import org.opensails.sails.configurator.IEventConfigurator;
import org.opensails.sails.configurator.IPackageDescriptor;
import org.opensails.sails.configurator.IResourceResolverConfigurator;
import org.opensails.sails.configurator.SailsConfigurator;
import org.opensails.sails.configurator.oem.DefaultContainerConfigurator;
import org.opensails.sails.configurator.oem.DefaultEventConfigurator;
import org.opensails.sails.configurator.oem.DefaultPackageDescriptor;
import org.opensails.sails.configurator.oem.DefaultResourceResolverConfigurator;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.form.html.Text;
import org.opensails.sails.oem.FileSystemResourceResolver;
import org.opensails.sails.oem.ResourceResolver;
import org.opensails.viento.IBinding;

public class FunctionalTestConfigurator extends SailsConfigurator {
	@Override
	public IPackageDescriptor createPackageDescriptor() {
		return new DefaultPackageDescriptor(getApplicationPackage()) {
			@Override
			protected void addComponentPackages(List<ApplicationPackage> componentPackages) {
				componentPackages.add(new ApplicationPackage(getApplicationPackage(), "components"));
			}

			@Override
			protected void addControllerPackages(List<ApplicationPackage> controllerPackages) {
				controllerPackages.add(new ApplicationPackage(getApplicationPackage(), EventTestController.class));
			}
		};
	}

	@Override
	public IContainerConfigurator getContainerConfigurator() {
		return new DefaultContainerConfigurator() {
			@Override
			public void configure(IConfigurableSailsApplication application, ApplicationContainer container) {
				container.register(ShamApplicationStartable.class);
			}
		};
	}

	@Override
	public IEventConfigurator getEventConfigurator() {
		return new DefaultEventConfigurator() {
			@Override
			public void configure(ISailsEvent event, IBinding binding) {
				super.configure(event, binding);
				binding.mixin(Text.class, new TextDecorator());
			}
		};
	}

	@Override
	public IResourceResolverConfigurator getResourceResolverConfigurator() {
		return new DefaultResourceResolverConfigurator() {
			public void configure(IConfigurableSailsApplication application, ResourceResolver resolver) {
				resolver.push(new FileSystemResourceResolver("test/views"));
				resolver.push(new FileSystemResourceResolver("test"));
			};
		};
	}

	public static class TextDecorator {
		public String decorated(Text text) {
			return "<span class=\"decorated\">" + text.renderThyself();
		}
	}
}
