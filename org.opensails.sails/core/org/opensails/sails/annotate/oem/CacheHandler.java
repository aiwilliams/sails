package org.opensails.sails.annotate.oem;

import java.io.ByteArrayOutputStream;

import org.opensails.sails.action.oem.ActionInvocation;
import org.opensails.sails.action.oem.StringActionResult;
import org.opensails.sails.annotate.BehaviorInstance;
import org.opensails.sails.event.EventListenerAdapter;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.oem.FragmentCache;
import org.opensails.sails.oem.HostFragmentKey;
import org.opensails.sails.template.CacheType;
import org.opensails.sails.template.Cached;

public class CacheHandler extends BehaviorHandlerAdapter<Cached> {
	protected FragmentCache fragmentCache;
	protected CacheType cacheType;
	protected ISailsEvent event;
	protected ByteArrayOutputStream outputRecorder;

	public CacheHandler(FragmentCache fragmentCache) {
		this.fragmentCache = fragmentCache;
	}

	public boolean add(BehaviorInstance<Cached> instance) {
		cacheType = instance.getAnnotation().value();
		return STOP_ADDING_BEHAVIORS;
	}

	@Override
	public boolean beforeAction(ActionInvocation invocation) {
		this.event = invocation.event;

		switch (cacheType) {
		case ACTION:
			String cachedPageContent = fragmentCache.read(new HostFragmentKey(event));
			if (cachedPageContent == null) {
				listenToEndOfEvent(invocation.event);
				return allowActionAndRecordOutput();
			} else return preventActionAndRespondFromCache(invocation, cachedPageContent);
		case PAGE:
			// nothing to do in this case - wait for end of execution to write
			// to cache
			return ALLOW_ACTION_EXECUTION;
		}
		return PREVENT_ACTION_EXECUTION;
	}

	protected boolean allowActionAndRecordOutput() {
		outputRecorder = new ByteArrayOutputStream();
		event.recordOutput(outputRecorder);
		return ALLOW_ACTION_EXECUTION;
	}

	protected boolean preventActionAndRespondFromCache(ActionInvocation invocation, String cachedPageContent) {
		invocation.setResult(new StringActionResult(event, cachedPageContent));
		return PREVENT_ACTION_EXECUTION;
	}

	protected void listenToEndOfEvent(ISailsEvent event) {
		event.getContainer().register(new EventListener());
	}

	protected class EventListener extends EventListenerAdapter {
		@Override
		public void endDispatch(ISailsEvent event) {
			switch (cacheType) {
			case ACTION:
				fragmentCache.write(new HostFragmentKey(event), outputRecorder.toString());
			case PAGE:
			}
		}
	}
}
