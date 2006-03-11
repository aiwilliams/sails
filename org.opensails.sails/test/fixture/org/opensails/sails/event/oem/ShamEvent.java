package org.opensails.sails.event.oem;

import javax.servlet.http.HttpSession;

import org.opensails.sails.oem.SailsApplicationFixture;
import org.opensails.sails.tester.servletapi.ShamHttpServletRequest;
import org.opensails.sails.tester.servletapi.ShamHttpServletResponse;

public class ShamEvent extends AbstractEvent {
	public boolean beginDispatchCalled;
	public boolean endDispatchCalled;
	public boolean sessionCreatedCalled;

	public ShamEvent() {
		this(new ShamHttpServletRequest(), new ShamHttpServletResponse());
	}

	public ShamEvent(ShamHttpServletRequest request, ShamHttpServletResponse response) {
		super(request, response);
		this.application = SailsApplicationFixture.basic();
		initialize(application.getContainer());
	}

	@Override
	public void beginDispatch() {
		beginDispatchCalled = true;
	}

	@Override
	public void endDispatch() {
		endDispatchCalled = true;
	}

	@Override
	public void sessionCreated(HttpSession session) {
		sessionCreatedCalled = true;
	}
}
