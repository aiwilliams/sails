package org.opensails.sails.processors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.opensails.sails.action.oem.TemplateActionResult;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.controller.oem.ShamController;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.event.oem.GetEvent;
import org.opensails.sails.event.oem.SailsEventFixture;
import org.opensails.sails.template.IMixinResolver;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.sails.tester.util.CollectionAssert;
import org.opensails.sails.url.IUrl;
import org.opensails.viento.ExceptionHandler;
import org.opensails.viento.IBinding;

/*
 * TODO: Refactor these tests. Oh my.
 */
public class TemplateActionResultProcessorTest extends TestCase {
	ShamMixinResolver mixinResolver;
	Set<Object> mixins = new HashSet<Object>();

	public void testProcess() throws Exception {
		ShamTemplateRenderer shamTemplateRenderer = new ShamTemplateRenderer();
		TemplateActionResultProcessor processor = new TemplateActionResultProcessor(shamTemplateRenderer);
		GetEvent actionGet = SailsEventFixture.actionGet(ShamController.class, "action");
		ShamController controllerImpl = new ShamController();
		controllerImpl.setEventContext(actionGet, null);
		actionGet.getContainer().register(IEventProcessingContext.class, controllerImpl);
		actionGet.getContainer().register(IControllerImpl.class, controllerImpl);
		actionGet.getContainer().register(IBinding.class, new ShamTemplateBinding());
		mixinResolver = new ShamMixinResolver();
		actionGet.getContainer().register(IMixinResolver.class, mixinResolver);
		TemplateActionResult actionResult = new TemplateActionResult(actionGet);
		actionResult.setLayout("layout");
		processor.process(actionResult);
		assertTrue(shamTemplateRenderer.renderIExpectCalled);
		CollectionAssert.containsOnlyOrdered(new String[] { "sham/action", "sham/layout" }, shamTemplateRenderer.templatesRendered);
		assertTrue(mixins.contains(controllerImpl));
		assertTrue(mixins.contains(mixinResolver));
	}

	class ShamMixinResolver implements IMixinResolver {
		public Object methodMissing(String methodName, Object[] args) {
			return null;
		}
	}

	class ShamTemplateBinding implements IBinding {
		public void mixin(Class<?> target, Object behaviour) {}

		public void mixin(Object behaviour) {
			mixins.add(behaviour);
		}

		public void put(String key, Object object) {}

		public void setExceptionHandler(ExceptionHandler exceptionHandler) {}

		public void putAll(Map<String, Object> map) {}
	}

	class ShamTemplateRenderer implements ITemplateRenderer<ShamTemplateBinding> {
		List<IBinding> bindingsUsed = new ArrayList<IBinding>();
		boolean renderIExpectCalled;
		List<String> templatesRendered = new ArrayList<String>();

		public ShamTemplateBinding createBinding(ShamTemplateBinding parent) {
			return null;
		}

		public StringBuilder render(String templateIdentifier, ShamTemplateBinding binding) {
			throw new AssertionFailedError("This should not be called as we want to render into a specific StringBuilder");
		}

		public StringBuilder render(String templateIdentifier, ShamTemplateBinding binding, StringBuilder target) {
			templatesRendered.add(templateIdentifier);
			bindingsUsed.add(binding);
			renderIExpectCalled = true;
			return target;
		}

		public boolean templateExists(String templateIdentifier) {
			return false;
		}

		public StringBuilder renderString(String templateContent, ShamTemplateBinding binding) {
			return null;
		}

		public StringBuilder render(IUrl templateUrl, ShamTemplateBinding binding) {
			return null;
		}
	}
}
