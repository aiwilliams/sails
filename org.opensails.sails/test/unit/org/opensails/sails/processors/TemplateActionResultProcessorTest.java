package org.opensails.sails.processors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.controller.oem.Controller;
import org.opensails.sails.controller.oem.ShamController;
import org.opensails.sails.controller.oem.TemplateActionResult;
import org.opensails.sails.helper.IHelperResolver;
import org.opensails.sails.oem.AdapterResolver;
import org.opensails.sails.oem.GetEvent;
import org.opensails.sails.oem.SailsEventFixture;
import org.opensails.sails.template.IExceptionHandler;
import org.opensails.sails.template.ITemplateBinding;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.sails.util.CollectionAssert;

/*
 * TODO: Refactor these tests. Oh my.
 */
public class TemplateActionResultProcessorTest extends TestCase {
	ShamHelperResolver helperResolver;
	Set<Object> mixins = new HashSet<Object>();

	public void testProcess() throws Exception {
		ShamTemplateRenderer shamTemplateRenderer = new ShamTemplateRenderer();
		TemplateActionResultProcessor processor = new TemplateActionResultProcessor(shamTemplateRenderer);
		GetEvent actionGet = SailsEventFixture.actionGet(ShamController.class, "action");
		ShamController controllerImpl = new ShamController();
		actionGet.getContainer().register(IControllerImpl.class, controllerImpl);
		actionGet.getContainer().register(ITemplateBinding.class, new ShamTemplateBinding());
		helperResolver = new ShamHelperResolver();
		actionGet.getContainer().register(IHelperResolver.class, helperResolver);
		processor.process(new TemplateActionResult(actionGet));
		assertTrue(shamTemplateRenderer.renderIExpectCalled);
		assertTrue(mixins.contains(controllerImpl));
		assertTrue(mixins.contains(helperResolver));
	}

	public void testProcess_Layout() throws Exception {
		ShamTemplateRenderer shamTemplateRenderer = new ShamTemplateRenderer();
		TemplateActionResultProcessor processor = new TemplateActionResultProcessor(shamTemplateRenderer);
		GetEvent actionGet = SailsEventFixture.actionGet(ShamLayoutController.class, "action");
		Controller controller = new Controller(ShamLayoutController.class, new AdapterResolver());
		ShamLayoutController controllerImpl = new ShamLayoutController();
		controllerImpl.set(actionGet, controller);
		actionGet.getContainer().register(IControllerImpl.class, controllerImpl);
		actionGet.getContainer().register(ITemplateBinding.class, new ShamTemplateBinding());
		helperResolver = new ShamHelperResolver();
		actionGet.getContainer().register(IHelperResolver.class, helperResolver);
		TemplateActionResult templateActionResult = new TemplateActionResult(actionGet);
		templateActionResult.layout("layout");
		processor.process(templateActionResult);
		CollectionAssert.containsOnlyOrdered(new String[] { "shamLayout/action", "layout" }, shamTemplateRenderer.templatesRendered);
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
		List<ITemplateBinding> bindingsUsed = new ArrayList<ITemplateBinding>();
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
	}
}
