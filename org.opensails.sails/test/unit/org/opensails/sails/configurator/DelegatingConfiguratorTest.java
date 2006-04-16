package org.opensails.sails.configurator;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.apache.commons.configuration.CompositeConfiguration;
import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.ISailsApplicationConfigurator;
import org.opensails.sails.action.oem.ActionResultProcessorResolver;
import org.opensails.sails.adapter.oem.AdapterResolver;
import org.opensails.sails.component.oem.ComponentResolver;
import org.opensails.sails.controller.oem.ControllerResolver;
import org.opensails.sails.oem.Dispatcher;
import org.opensails.sails.oem.EventProcessorResolver;
import org.opensails.sails.oem.ResourceResolver;
import org.opensails.sails.url.UrlResolver;
import org.opensails.sails.util.ClassHelper;

public class DelegatingConfiguratorTest extends TestCase {
	public void testAllMethodsOverridden() throws Exception {
		assertAllMethodsOverridden(DelegatingConfigurator.class);
	}

	public void testDelegates() throws Exception {
		assertAllMethodsOverridden(CapturingDelegate.class);

		Method configureApplication = SailsConfigurator.class.getDeclaredMethod("configure", new Class[] { IConfigurableSailsApplication.class });
		Method installContainer = SailsConfigurator.class.getDeclaredMethod("installContainer", new Class[] {});
		DelegatingConfigurator delegatingConfigurator = new DelegatingConfigurator(CapturingDelegate.class);
		CapturingDelegate capturingDelegate = (CapturingDelegate) delegatingConfigurator.delegate;
		for (Method method : SailsConfigurator.class.getDeclaredMethods()) {
			// skip - they call super
			if (configureApplication.equals(method) || installContainer.equals(method)) continue;
			if (Modifier.isPublic(method.getModifiers()) || Modifier.isProtected(method.getModifiers())) {
				method.invoke(delegatingConfigurator, new Object[method.getParameterTypes().length]);
				capturingDelegate.assertInvoked(method);
			}
		}
	}

	void assertAllMethodsOverridden(Class<? extends SailsConfigurator> delegatingClass) throws NoSuchMethodException {
		for (Method method : SailsConfigurator.class.getDeclaredMethods()) {
			if (Modifier.isPublic(method.getModifiers()) || Modifier.isProtected(method.getModifiers())) {
				assertNotNull(delegatingClass.getDeclaredMethod(method.getName(), (Class[]) method.getParameterTypes()));
			}
		}
	}

	public static class CapturingDelegate extends SailsConfigurator {
		private Method methodInvoked;

		public void assertInvoked(Method method) {
			assertEquals(method, getMethodInvoked());
			methodInvoked = null;
		}

		@Override
		public void configure(IConfigurableSailsApplication application) {
			setMethodInvoked(findMethod(IConfigurableSailsApplication.class));
		}

		@Override
		public IPackageDescriptor createPackageDescriptor() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		public IConfigurationConfigurator getConfigurationConfigurator() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		public IContainerConfigurator getContainerConfigurator() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		public IEventConfigurator getEventConfigurator() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		public IFormProcessingConfigurator getFormProcessingConfigurator() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		public IObjectPersisterConfigurator getPersisterConfigurator() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		public IResourceResolverConfigurator getResourceResolverConfigurator() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected void configureName() {
			setMethodInvoked(findMethod());
		}

		@Override
		protected ApplicationContainer createApplicationContainer() {
			setMethodInvoked(findMethod());
			return null;
		}

		protected Method findMethod() {
			try {
				throw new RuntimeException();
			} catch (Exception e) {
				StackTraceElement[] stackTrace = e.getStackTrace();
				String methodName = stackTrace[1].getMethodName();
				try {
					return ClassHelper.declaredMethodsNamed(SailsConfigurator.class, methodName)[0];
				} catch (Exception e1) {
					throw new AssertionFailedError(String.format("There is no method %s with 0 parameters", methodName));
				}
			}
		}

		protected Method findMethod(Class... parameterTypes) {
			try {
				throw new RuntimeException();
			} catch (Exception e) {
				StackTraceElement[] stackTrace = e.getStackTrace();
				String methodName = stackTrace[1].getMethodName();
				try {
					return SailsConfigurator.class.getDeclaredMethod(methodName, parameterTypes);
				} catch (Exception e1) {
					throw new AssertionFailedError(String.format("There is no method %s with parameters of type %s", methodName, parameterTypes));
				}
			}
		}

		@Override
		protected ApplicationPackage getApplicationPackage() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected Package getApplicationRootPackage() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected String getBuiltinAdaptersPackage() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected String getBuiltinComponentPackage() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected String getBuiltinControllerPackage() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected String getBuitinActionResultProcessorPackage() {
			setMethodInvoked(findMethod());
			return null;
		}

		protected Method getMethodInvoked() {
			return methodInvoked;
		}

		@Override
		protected ActionResultProcessorResolver installActionResultProcessorResolver() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected AdapterResolver installAdapterResolver() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected ComponentResolver installComponentResolver() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected CompositeConfiguration installConfiguration() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected ApplicationContainer installContainer() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected ControllerResolver installControllerResolver() {
			methodInvoked = findMethod();
			return null;
		}

		@Override
		protected Dispatcher installDispatcher() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected void installEventConfigurator(IFormProcessingConfigurator formProcessingConfigurator) {
			setMethodInvoked(findMethod(IFormProcessingConfigurator.class));
		}

		@Override
		protected EventProcessorResolver installEventProcessorResolver() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected IFormProcessingConfigurator installFormProcessingConfigurator() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected IPackageDescriptor installPackageDescriptor() {
			methodInvoked = findMethod();
			return null;
		}

		@Override
		protected ResourceResolver installResourceResolver() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected UrlResolver installUrlResolverResolver() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected void provideApplicationScopedContainerAccess(ApplicationContainer applicationContainer) {
			setMethodInvoked(findMethod(new Class[] { ApplicationContainer.class }));
		}

		@Override
		protected void setApplication(IConfigurableSailsApplication application) {
			setMethodInvoked(findMethod(IConfigurableSailsApplication.class));
		}

		@Override
		protected void setConfigurator(ISailsApplicationConfigurator configurator) {
			setMethodInvoked(findMethod(ISailsApplicationConfigurator.class));
		}

		@Override
		protected void setContainer(ApplicationContainer installContainer) {
			setMethodInvoked(findMethod(new Class[] { ApplicationContainer.class }));
		}

		protected void setMethodInvoked(Method methodInvoked) {
			if (this.methodInvoked == null) this.methodInvoked = methodInvoked;
		}

		@Override
		protected void setPackageDescriptor(IPackageDescriptor installPackageDescriptor) {
			setMethodInvoked(findMethod(new Class[] { IPackageDescriptor.class }));
		}
	}
}
