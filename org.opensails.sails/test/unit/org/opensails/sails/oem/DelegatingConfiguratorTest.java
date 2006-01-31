package org.opensails.sails.oem;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.apache.commons.configuration.CompositeConfiguration;
import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.action.oem.ActionResultProcessorResolver;
import org.opensails.sails.adapter.oem.AdapterResolver;
import org.opensails.sails.component.oem.ComponentResolver;
import org.opensails.sails.controller.oem.ControllerResolver;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.template.MixinResolver;
import org.opensails.sails.url.UrlResolver;
import org.opensails.sails.util.ClassHelper;
import org.opensails.viento.IBinding;

public class DelegatingConfiguratorTest extends TestCase {
	public void testAllMethodsOverridden() throws Exception {
		assertAllMethodsOverridden(DelegatingConfigurator.class);
	}

	public void testDelegates() throws Exception {
		assertAllMethodsOverridden(CapturingDelegate.class);

		Method configureApplication = BaseConfigurator.class.getDeclaredMethod("configure", new Class[] { IConfigurableSailsApplication.class });
		DelegatingConfigurator delegatingConfigurator = new DelegatingConfigurator(CapturingDelegate.class);
		CapturingDelegate capturingDelegate = (CapturingDelegate) delegatingConfigurator.delegate;
		for (Method method : BaseConfigurator.class.getDeclaredMethods()) {
			// skip - it calls super
			if (configureApplication.equals(method)) continue;
			if (Modifier.isPublic(method.getModifiers()) || Modifier.isProtected(method.getModifiers())) {
				method.invoke(delegatingConfigurator, new Object[method.getParameterTypes().length]);
				capturingDelegate.assertInvoked(method);
			}
		}
	}

	void assertAllMethodsOverridden(Class<? extends BaseConfigurator> delegatingClass) throws NoSuchMethodException {
		for (Method method : BaseConfigurator.class.getDeclaredMethods()) {
			if (Modifier.isPublic(method.getModifiers()) || Modifier.isProtected(method.getModifiers())) {
				assertNotNull(delegatingClass.getDeclaredMethod(method.getName(), (Class[]) method.getParameterTypes()));
			}
		}
	}

	public static class CapturingDelegate extends BaseConfigurator {
		private Method methodInvoked;

		public void assertInvoked(Method method) {
			assertEquals(method, getMethodInvoked());
			methodInvoked = null;
		}

		@Override
		public void configure(ActionResultProcessorResolver resultProcessorResolver) {
			setMethodInvoked(findMethod(new Class[] { ActionResultProcessorResolver.class }));
		}

		@Override
		public void configure(AdapterResolver adapterResolver) {
			setMethodInvoked(findMethod(new Class[] { AdapterResolver.class }));
		}

		@Override
		public void configure(ComponentResolver componentResolver) {
			setMethodInvoked(findMethod(new Class[] { ComponentResolver.class }));
		}

		@Override
		public void configure(ControllerResolver controllerResolver) {
			setMethodInvoked(findMethod(new Class[] { ControllerResolver.class }));
		}

		@Override
		public void configure(IConfigurableSailsApplication application) {
			setMethodInvoked(findMethod(new Class[] { IConfigurableSailsApplication.class }));
		}

		@Override
		public void configure(ISailsEvent event, IBinding binding) {
			setMethodInvoked(findMethod(new Class[] { ISailsEvent.class, IBinding.class }));
		}

		@Override
		public void configure(ISailsEvent event, MixinResolver resolver) {
			setMethodInvoked(findMethod(new Class[] { ISailsEvent.class, MixinResolver.class }));
		}

		@Override
		public void configure(ISailsEvent event, RequestContainer eventContainer) {
			setMethodInvoked(findMethod(new Class[] { ISailsEvent.class, RequestContainer.class }));
		}

		@Override
		public void configure(ResourceResolver resourceResolver) {
			setMethodInvoked(findMethod(new Class[] { ResourceResolver.class }));
		}

		@Override
		protected void configure(IConfigurableSailsApplication application, CompositeConfiguration compositeConfiguration) {
			setMethodInvoked(findMethod(new Class[] { IConfigurableSailsApplication.class, CompositeConfiguration.class }));
		}

		@Override
		protected void configure(IConfigurableSailsApplication application, ScopedContainer container) {
			setMethodInvoked(findMethod(new Class[] { IConfigurableSailsApplication.class, ScopedContainer.class }));
		}

		@Override
		protected void configureName(IConfigurableSailsApplication application, CompositeConfiguration configuration) {
			setMethodInvoked(findMethod());
		}

		protected Method findMethod() {
			try {
				throw new RuntimeException();
			} catch (Exception e) {
				StackTraceElement[] stackTrace = e.getStackTrace();
				String methodName = stackTrace[1].getMethodName();
				try {
					return ClassHelper.declaredMethodsNamed(BaseConfigurator.class, methodName)[0];
				} catch (Exception e1) {
					throw new AssertionFailedError(String.format("There is no method %s with 0 parameters", methodName));
				}
			}
		}

		protected Method findMethod(Class[] parameterTypes) {
			try {
				throw new RuntimeException();
			} catch (Exception e) {
				StackTraceElement[] stackTrace = e.getStackTrace();
				String methodName = stackTrace[1].getMethodName();
				try {
					return BaseConfigurator.class.getDeclaredMethod(methodName, parameterTypes);
				} catch (Exception e1) {
					throw new AssertionFailedError(String.format("There is no method %s with parameters of type %s", methodName, parameterTypes));
				}
			}
		}

		@Override
		protected String getApplicationRootPackage() {
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
		protected String getBuiltinMixinPackage() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected String getBuitinActionResultProcessorPackage() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected String getDefaultActionResultProcessorPackage() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected String getDefaultAdaptersPackage() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected String getDefaultComponentPackage() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected String getDefaultControllerPackage() {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected String getDefaultMixinPackage() {
			setMethodInvoked(findMethod());
			return null;
		}

		protected Method getMethodInvoked() {
			return methodInvoked;
		}

		@Override
		protected ActionResultProcessorResolver installActionResultProcessorResolver(IConfigurableSailsApplication application, ScopedContainer container) {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected AdapterResolver installAdapterResolver(IConfigurableSailsApplication application, ScopedContainer container) {
			setMethodInvoked(findMethod(new Class[] { IConfigurableSailsApplication.class, ScopedContainer.class }));
			return null;
		}

		@Override
		protected ComponentResolver installComponentResolver(IConfigurableSailsApplication application, ScopedContainer container) {
			setMethodInvoked(findMethod(new Class[] { IConfigurableSailsApplication.class, ScopedContainer.class }));
			return null;
		}

		@Override
		protected CompositeConfiguration installConfiguration(IConfigurableSailsApplication application) {
			setMethodInvoked(findMethod(new Class[] { IConfigurableSailsApplication.class }));
			return null;
		}

		@Override
		protected void installConfigurator(IConfigurableSailsApplication application) {
			setMethodInvoked(findMethod());
		}

		@Override
		protected ScopedContainer installContainer(IConfigurableSailsApplication application) {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected ControllerResolver installControllerResolver(IConfigurableSailsApplication application, ScopedContainer container) {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected Dispatcher installDispatcher(IConfigurableSailsApplication application, ScopedContainer container) {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected MixinResolver installMixinResolver(ISailsEvent event, RequestContainer eventContainer) {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected void installObjectPersister(IConfigurableSailsApplication application, ScopedContainer container) {
			setMethodInvoked(findMethod());
		}

		@Override
		protected ResourceResolver installResourceResolver(IConfigurableSailsApplication application, ScopedContainer container) {
			setMethodInvoked(findMethod());
			return null;
		}

		@Override
		protected UrlResolver installUrlResolverResolver(IConfigurableSailsApplication application, ScopedContainer container) {
			setMethodInvoked(findMethod());
			return null;
		}

		protected void setMethodInvoked(Method methodInvoked) {
			if (this.methodInvoked == null) this.methodInvoked = methodInvoked;
		}
	}
}
