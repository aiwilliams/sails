package org.opensails.sails.controller.oem;

import junit.framework.TestCase;

import org.opensails.sails.action.IAction;
import org.opensails.sails.action.IActionResult;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.event.oem.ExceptionEvent;
import org.opensails.sails.event.oem.GetEvent;
import org.opensails.sails.event.oem.SailsEventFixture;

public class ControllerTest extends TestCase {
	ShamController controllerImpl;

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
		Controller controller = ControllerFixture.defaultAdapters(controllerImpl = new ShamController());
		GetEvent event = SailsEventFixture.actionGet(ShamController.class, "resultAction");
		IActionResult result = controller.process(event);
		assertEquals("resultAction()", controllerImpl.actionInvoked);
		assertSame(controllerImpl.resultReturned, result);
		assertSame(event.getContainer(), controllerImpl.getContainer());
	}

	public void testProcess_ExceptionEvent() throws Exception {
		Controller controller = ControllerFixture.defaultAdapters(controllerImpl = new ShamController());
		ExceptionEvent event = SailsEventFixture.actionException(ShamController.class, "resultAction");
		controller.process(event);
		assertEquals("exception(" + ExceptionEvent.class + ")", controllerImpl.actionInvoked);
		assertSame(event.getContainer(), controllerImpl.getContainer());
	}

	public void testProcess_NoControllerImpl() throws Exception {
		Controller controller = ControllerFixture.create();
		GetEvent event = SailsEventFixture.actionGet();
		controller.process(event);
		assertFalse(event.getContainer().contains(IControllerImpl.class));
	}

	IActionResult process(Controller controller, String actionName) {
		return controller.process(SailsEventFixture.actionGet(controller.getImplementation(), actionName));
	}
}
