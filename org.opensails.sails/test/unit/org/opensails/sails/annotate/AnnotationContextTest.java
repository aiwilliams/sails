package org.opensails.sails.annotate;

import java.lang.annotation.ElementType;
import java.util.List;

import junit.framework.TestCase;

import org.opensails.sails.action.ActionFixture;
import org.opensails.sails.controller.oem.BaseController;
import org.opensails.sails.template.Layout;

public class AnnotationContextTest extends TestCase {
	public void testBehaviors() {
		AnnotationContext context = new AnnotationContext(ShamEventContext.class);
		List<BehaviorInstance> behaviors = context.behaviors(ActionFixture.defaultAdapters("one", ShamEventContext.class));
		assertEquals(2, behaviors.size());
		assertEquals(ElementType.METHOD, behaviors.get(1).getElementType());
	}

	public void testIndicators() {}

	@Layout("class")
	public class ShamEventContext extends BaseController {
		@Layout("one")
		public void one() {}
	}
}
