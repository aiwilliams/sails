package org.opensails.sails.controller.oem;

import junit.framework.TestCase;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ApplicationScope;
import org.opensails.sails.controller.IAction;
import org.opensails.sails.controller.IActionResult;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.oem.ExceptionEvent;
import org.opensails.sails.oem.GetEvent;
import org.opensails.sails.oem.SailsEventFixture;

public class ControllerTest extends TestCase {
	ShamController controllerImpl;
	Class<?> requestedControllerType;

	/**
	 * A controller will always return an Action, even when it doesn't exist on
	 * the controller implementation class. This is a Good Thing that allows for
	 * us to act like everything is perfect until there is nothing to send to
	 * the client. Then, it isn't that there was no action: it is that there is
	 * nothing more we can do. Saavy?
	 */
	public void testGetAction() throws Exception {
		Controller controller = ControllerFixture.defaultAdapters(ShamController.class);
		IAction action = controller.getAction("voidActionNoParams");
		assertNotNull(action);
		assertSame(action, controller.getAction("voidActionNoParams"));

		action = controller.getAction("nonExistantActionsToo");
		assertNotNull(action);
		assertSame(action, controller.getAction("nonExistantActionsToo"));
	}

	public void testProcess() throws Exception {
		controllerImpl = new ShamController();
		Controller controller = ControllerFixture.defaultAdapters(ShamController.class);
		GetEvent event = SailsEventFixture.actionGet(ShamController.class, "resultAction");
		ScopedContainer container = new ScopedContainer(ApplicationScope.REQUEST) {
			@Override
			@SuppressWarnings("unchecked")
			public <T> T instance(Class<T> key, Class defaultImplementation) {
				if (ShamController.class.isAssignableFrom(key)) {
					requestedControllerType = key;
					return (T) controllerImpl;
				}
				return super.instance(key);
			}
		};
		event.setContainer(container);
		IActionResult result = controller.process(event);
		assertEquals(ShamController.class, requestedControllerType);
		assertEquals("resultAction()", controllerImpl.actionInvoked);
		assertSame(controllerImpl.resultReturned, result);
		assertSame(event.getContainer(), controllerImpl.getContainer());
		assertSame(controllerImpl, event.getContainer().instance(IControllerImpl.class));
	}

	public void testProcess_ExceptionEvent() throws Exception {
		controllerImpl = new ShamController();
		Controller controller = ControllerFixture.defaultAdapters(ShamController.class);
		ExceptionEvent event = SailsEventFixture.actionException(ShamController.class, "resultAction");
		ScopedContainer container = new ScopedContainer(ApplicationScope.REQUEST) {
			@Override
			@SuppressWarnings("unchecked")
			public <T> T instance(Class<T> key, Class defaultImplementation) {
				if (ShamController.class.isAssignableFrom(key)) {
					requestedControllerType = key;
					return (T) controllerImpl;
				}
				return super.instance(key, defaultImplementation);
			}
		};
		event.setContainer(container);
		controller.process(event);
		assertEquals("exception(" + ExceptionEvent.class + ")", controllerImpl.actionInvoked);
		assertSame(event.getContainer(), controllerImpl.getContainer());
		assertSame(controllerImpl, event.getContainer().instance(IControllerImpl.class));
	}

	public void testProcess_LayoutAnnotations() throws Exception {
		controllerImpl = new ShamControllerLayouts();
		Controller controller = ControllerFixture.defaultAdapters(ShamControllerLayouts.class);
		GetEvent event = SailsEventFixture.actionGet(ShamControllerLayouts.class, "classLayout");
		ScopedContainer container = new ScopedContainer(ApplicationScope.REQUEST) {
			@Override
			@SuppressWarnings("unchecked")
			public <T> T instance(Class<T> key, Class defaultImplementation) {
				if (ShamControllerLayouts.class.isAssignableFrom(key)) {
					requestedControllerType = key;
					return (T) controllerImpl;
				}
				return super.instance(key, defaultImplementation);
			}
		};
		event.setContainer(container);
		TemplateActionResult result = (TemplateActionResult) controller.process(event);
		assertEquals("classLayout()", controllerImpl.actionInvoked);
		assertEquals("classLayout", result.getLayout());
	}

	public void testProcess_NoControllerImpl() throws Exception {
		Controller controller = ControllerFixture.defaultAdapters();
		GetEvent event = SailsEventFixture.actionGet();
		controller.process(event);
		assertFalse(event.getContainer().contains(IControllerImpl.class));
	}
}
