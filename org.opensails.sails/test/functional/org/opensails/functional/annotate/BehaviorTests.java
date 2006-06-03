package org.opensails.functional.annotate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.opensails.functional.SailsFunctionalTester;
import org.opensails.sails.action.oem.ActionInvocation;
import org.opensails.sails.annotate.Behavior;
import org.opensails.sails.annotate.BehaviorInstance;
import org.opensails.sails.annotate.IBehaviorHandler;
import org.opensails.sails.controller.oem.BaseController;

public class BehaviorTests extends TestCase {
	protected static Map<String, Object> STATES = new HashMap<String, Object>();

	protected static void incrementCount(String stateName) {
		Integer value = (Integer) STATES.get(stateName);
		if (value == null) value = 0;
		STATES.put(stateName, value.intValue() + 1);
	}

	public void testAfterAction() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester();
		t.getApplication().registerController(MyController.class);
		t.get("my", "myAction");

		Map<String, Object> expectations = new HashMap<String, Object>();
		expectations.put("myActionExecuted", 1);
		expectations.put("beforeActionExecuted", 1);
		expectations.put("afterActionExecuted", 1);

		assertEquals(expectations, STATES);
	}

	@Override
	protected void tearDown() throws Exception {
		STATES.clear();
	}

	@Behavior(MyHandler.class)
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface MyBehavior {}

	@MyBehavior
	public static class MyController extends BaseController {
		public void myAction() {
			incrementCount("myActionExecuted");
		}
	}

	public static class MyHandler implements IBehaviorHandler<MyBehavior> {
		public boolean add(BehaviorInstance<MyBehavior> instance) {
			return CONTINUE_ADDING_BEHAVIORS;
		}

		public void afterAction(ActionInvocation invocation) {
			incrementCount("afterActionExecuted");
		}

		public boolean beforeAction(ActionInvocation invocation) {
			incrementCount("beforeActionExecuted");
			return ALLOW_ACTION_EXECUTION;
		}
	}
}
