package org.opensails.sails.processors;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.opensails.sails.controller.oem.PartialActionResult;
import org.opensails.sails.oem.GetEvent;
import org.opensails.sails.oem.SailsEventFixture;
import org.opensails.sails.template.IMixinResolver;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.viento.ExceptionHandler;
import org.opensails.viento.IBinding;

public class PartialActionResultProcessorTest extends TestCase {
	ShamMixinResolver mixinResolver;
	Set<Object> mixins = new HashSet<Object>();
	boolean renderIExpectCalled;

	public void testProcess() throws Exception {
		PartialActionResultProcessor processor = new PartialActionResultProcessor(new ShamTemplateRenderer());
		GetEvent actionGet = SailsEventFixture.actionGet("controller", "action");
		actionGet.getContainer().register(IBinding.class, new ShamTemplateBinding());
		mixinResolver = new ShamMixinResolver();
		actionGet.getContainer().register(IMixinResolver.class, mixinResolver);
		processor.process(new PartialActionResult(actionGet));
		assertTrue(renderIExpectCalled);
		assertTrue(mixins.contains(mixinResolver));
	}

	class ShamMixinResolver implements IMixinResolver {
		public Object methodMissing(String methodName, Object[] args) {
			return null;
		}
	}

	class ShamTemplateBinding implements IBinding {
		public void mixin(Class<?> target, Object behaviour) {}

		public void mixin(Object behavior) {
			mixins.add(behavior);
		}

		public void put(String key, Object object) {}

		public void setExceptionHandler(ExceptionHandler exceptionHandler) {}

		public void putAll(Map<String, Object> map) {}
	}

	class ShamTemplateRenderer implements ITemplateRenderer<ShamTemplateBinding> {
		public ShamTemplateBinding createBinding(ShamTemplateBinding parent) {
			return null;
		}

		public StringBuilder render(String templateIdentifier, ShamTemplateBinding binding) {
			throw new AssertionFailedError("This should not be called as we want to render into a specific StringBuilder");
		}

		public StringBuilder render(String templateIdentifier, ShamTemplateBinding binding, StringBuilder target) {
			renderIExpectCalled = true;
			return target;
		}

		public boolean templateExists(String templateIdentifier) {
			return false;
		}
	}
}
