package org.opensails.sails.processors;

import java.util.HashSet;
import java.util.Set;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.opensails.sails.controller.IController;
import org.opensails.sails.controller.oem.ShamController;
import org.opensails.sails.controller.oem.TemplateActionResult;
import org.opensails.sails.helper.IHelperResolver;
import org.opensails.sails.oem.GetEvent;
import org.opensails.sails.oem.SailsEventFixture;
import org.opensails.sails.template.IExceptionHandler;
import org.opensails.sails.template.ITemplateBinding;
import org.opensails.sails.template.ITemplateRenderer;

public class TemplateActionResultProcessorTest extends TestCase {
	ShamHelperResolver helperResolver;
	Set<Object> mixins = new HashSet<Object>();
	boolean renderIExpectCalled;

	public void testProcess() throws Exception {
		TemplateActionResultProcessor processor = new TemplateActionResultProcessor(new ShamTemplateRenderer());
		GetEvent actionGet = SailsEventFixture.actionGet(ShamController.class, "action");
		ShamController controllerImpl = new ShamController();
		actionGet.getContainer().register(IController.class, controllerImpl);
		actionGet.getContainer().register(ITemplateBinding.class, new ShamTemplateBinding());
		helperResolver = new ShamHelperResolver();
		actionGet.getContainer().register(IHelperResolver.class, helperResolver);
		processor.process(new TemplateActionResult(actionGet));
		assertTrue(renderIExpectCalled);
		assertTrue(mixins.contains(controllerImpl));
		assertTrue(mixins.contains(helperResolver));
	}

	class ShamHelperResolver implements IHelperResolver {
		public Object methodMissing(String methodName, Object[] args) {
			return null;
		}
	}

	class ShamTemplateBinding implements ITemplateBinding {
		public void mixin(Class<?> target, Object helper) {}

		public void mixin(Object helper) {
			mixins.add(helper);
		}

		public void put(String key, Object object) {}

		public void setExceptionHandler(IExceptionHandler exceptionHandler) {}
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
