package org.opensails.sails.oem;

import junit.framework.*;

import org.opensails.sails.*;
import org.opensails.sails.action.*;
import org.opensails.sails.controller.*;
import org.opensails.sails.controller.oem.*;
import org.opensails.sails.event.*;
import org.opensails.sails.event.oem.*;

public class DispatcherTest extends TestCase {
	boolean beginDispatchCalled;
	boolean configureEventAndContainerCalled;
	Controller controller;
	Dispatcher dispatcher;
	boolean endDispatchCalled;
	RuntimeException exceptionException;
	RuntimeException getException;
	RuntimeException postException;
	ISailsEvent processedEvent;
	ExceptionEvent processedExceptionEvent;
	IActionResult result, processedResult;
	IActionResultProcessor resultProcessor;
	RuntimeException unknownException;

	public void testDispatch_CallsOnConfigurator() throws Exception {
		final GetEvent dispatchedEvent = SailsEventFixture.actionGet("controller", "action");
		ISailsApplication application = SailsApplicationFixture.basic();
		application.getContainer().register(ISailsEventConfigurator.class, new ISailsEventConfigurator() {
			public void configure(ISailsEvent event, RequestContainer eventContainer) {
				configureEventAndContainerCalled = true;
				assertSame(dispatchedEvent, event);
				assertSame(event.getContainer(), eventContainer);
			}
		});
		dispatcher = createDispatcher(application);
		dispatcher.dispatch(dispatchedEvent);
		assertTrue(configureEventAndContainerCalled);
	}

	public void testDispatch_Exception() throws Exception {
		ExceptionEvent event = SailsEventFixture.actionException("controller", "action");
		dispatcher.dispatch(event);
		checkContainer(event);
		checkContainer(event.getOriginatingEvent());
		assertSame(event.getOriginatingEvent().getContainer(), event.getContainer());
		assertSame(event, processedExceptionEvent);
		assertSame(result, processedResult);
	}

	public void testDispatch_Exception_Exception() throws Exception {
		exceptionException = new RuntimeException("Failure in processing get event");
		ExceptionEvent event = SailsEventFixture.actionException("controller", "action");
		try {
			dispatcher.dispatch(event);
			fail("This one gets through, as the exception controller failed handling an exception. We'll make SailsApplication catch these - it will tell something that can handle it");
		} catch (Throwable expected) {}
		assertSame(event, processedExceptionEvent);
		assertNull(processedResult);
	}

	public void testDispatch_Get() throws Exception {
		GetEvent event = SailsEventFixture.actionGet("controller", "action");
		dispatcher.dispatch(event);
		checkContainer(event);
		assertSame(event, processedEvent);
		assertSame(result, processedResult);
	}

	public void testDispatch_Get_Exception() throws Exception {
		getException = new RuntimeException("Failure in processing get event");
		GetEvent event = SailsEventFixture.actionGet("controller", "action");
		dispatcher.dispatch(event);
		assertSame(event, processedEvent);
		assertNotSame(event, processedExceptionEvent);
		assertSame(result, processedResult);
		assertSame(event, processedExceptionEvent.getOriginatingEvent());
		assertSame(getException, processedExceptionEvent.getException());
	}

	public void testDispatch_NotifiesEvents() throws Exception {
		ShamEvent event = SailsEventFixture.sham();
		dispatcher.dispatch(event);
		assertTrue(event.beginDispatchCalled);
		assertTrue(event.endDispatchCalled);
	}

	public void testDispatch_NotifiesEvents_ExceptionEvents() throws Exception {
		ExceptionEvent event = new ExceptionEvent(SailsEventFixture.unknown(), new RuntimeException()) {
			@Override
			public void beginDispatch() {
				beginDispatchCalled = true;
			}

			@Override
			public void endDispatch() {
				endDispatchCalled = true;
			}
		};
		dispatcher.dispatch(event);
		assertTrue(beginDispatchCalled);
		assertTrue(endDispatchCalled);
	}

	public void testDispatch_Post() throws Exception {
		PostEvent event = SailsEventFixture.actionPost("controller", "action");
		dispatcher.dispatch(event);
		checkContainer(event);
		assertSame(event, processedEvent);
		assertSame(result, processedResult);
	}

	public void testDispatch_Post_Exception() throws Exception {
		postException = new RuntimeException("Failure in processing post event");
		PostEvent event = SailsEventFixture.actionPost("controller", "action");
		dispatcher.dispatch(event);
		assertSame(event, processedEvent);
		assertNotSame(event, processedExceptionEvent);
		assertSame(result, processedResult);
		assertSame(event, processedExceptionEvent.getOriginatingEvent());
		assertSame(postException, processedExceptionEvent.getException());
	}

	public void testDispatch_UnknownEventSubclass() throws Exception {
		ILifecycleEvent event = SailsEventFixture.unknown();
		dispatcher.dispatch(event);
		assertSame(event, processedEvent);
		assertSame(result, processedResult);
	}

	public void testDispatch_UnknownEventSubclass_Exception() throws Exception {
		unknownException = new RuntimeException("Failure in processing unknown event");
		ILifecycleEvent event = SailsEventFixture.unknown();
		dispatcher.dispatch(event);
		assertSame(event, processedEvent);
		assertNotSame(event, processedExceptionEvent);
		assertSame(result, processedResult);
		assertSame(event, processedExceptionEvent.getOriginatingEvent());
		assertSame(unknownException, processedExceptionEvent.getException());
	}

	protected void checkContainer(ISailsEvent event) {
		assertNotNull(event.getContainer());
		assertEquals(ApplicationScope.REQUEST, event.getContainer().getScope());

		ISailsEvent expectedInContainer = event;
		// Good for now. Perhaps someone will complain
		if (event instanceof ExceptionEvent) expectedInContainer = ((ExceptionEvent) event).getOriginatingEvent();
		assertEquals(expectedInContainer, event.getContainer().instance(ISailsEvent.class));
	}

	protected Dispatcher createDispatcher(ISailsApplication application) {
		return new Dispatcher(application, application.getContainer().instance(ISailsEventConfigurator.class), new IControllerResolver() {
			public IController resolve(String controllerIdentifier) {
				return controller;
			}

			public boolean resolvesNamespace(String namespace) {
				return "Controller".equals(namespace);
			}
		}, new IActionResultProcessorResolver() {
			public IActionResultProcessor resolve(IActionResult result) {
				return resultProcessor;
			}
		});
	}

	@Override
	protected void setUp() throws Exception {
		result = ActionResultFixture.template();
		controller = new Controller(null, null) {
			@Override
			public IActionResult process(ExceptionEvent event) {
				processedExceptionEvent = event;
				if (exceptionException != null) throw exceptionException;
				return result;
			}

			@Override
			public IActionResult process(GetEvent event) {
				processedEvent = event;
				if (getException != null) throw getException;
				return result;
			}

			@Override
			public IActionResult process(ISailsEvent event) {
				processedEvent = event;
				if (unknownException != null) throw unknownException;
				return result;
			}

			@Override
			public IActionResult process(PostEvent event) {
				processedEvent = event;
				if (postException != null) throw postException;
				return result;
			}
		};
		resultProcessor = new IActionResultProcessor() {
			public void process(IActionResult result) {
				processedResult = result;
			}
		};
		dispatcher = createDispatcher(SailsApplicationFixture.basic());
	}
}
