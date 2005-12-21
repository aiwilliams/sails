package org.opensails.sails.action.oem;

import java.lang.reflect.Method;
import java.util.Arrays;

import junit.framework.TestCase;

import org.apache.commons.lang.ArrayUtils;
import org.opensails.sails.action.ActionFixture;
import org.opensails.sails.action.IAction;
import org.opensails.sails.controller.oem.ShamController;
import org.opensails.sails.tester.util.CollectionAssert;

public class ActionTest extends TestCase {
	Method actionMethodNoParams;
	Method actionMethodOneParam;
	Method actionMethodTwoParams;
	Method voidActionParams;

	public ActionTest() throws Exception, Exception {
		voidActionParams = ShamController.class.getMethod("voidActionParams", new Class[] { String.class });
		actionMethodNoParams = ShamController.class.getMethod("voidActionMultiple", ArrayUtils.EMPTY_CLASS_ARRAY);
		actionMethodOneParam = ShamController.class.getMethod("voidActionMultiple", new Class[] { int.class });
		actionMethodTwoParams = ShamController.class.getMethod("voidActionMultiple", new Class[] { String.class, Object.class });
	}

	public void testConstructor_MultipleMethods() throws Exception {
		Action action = ActionFixture.defaultAdapters("voidActionMultiple", ShamController.class);
		CollectionAssert.containsOnlyOrdered(new Method[] { actionMethodTwoParams, actionMethodOneParam, actionMethodNoParams }, action.actionMethods);
	}

	/**
	 * It is important to maintain knowledge about the number of arguments for
	 * each method that matches our action name. This allows the action to
	 * execute the method that most closely resembles our URL and it's quantity
	 * of parameters.
	 */
	public void testConstructor_SingleMethod() throws Exception {
		Action voidActionNoParams = ActionFixture.defaultAdapters("voidActionNoParams", ShamController.class);
		Method voidActionNoParamsMethod = ShamController.class.getMethod("voidActionNoParams", ArrayUtils.EMPTY_CLASS_ARRAY);
		CollectionAssert.containsOnly(voidActionNoParamsMethod, voidActionNoParams.actionMethods);
	}

	public void testExecute_AdaptersUsed() {
		Action action = ActionFixture.defaultAdapters("voidActionMultiple", ShamController.class);
		ShamController controller = new ShamController();
		ActionFixture.execute(action, controller, new String[] { "1" });
		assertEquals(1, controller.intArgumentValue);
	}

	/**
	 * If the url has zero parameters, and there is not a zero argument method,
	 * don't call any code
	 */
	public void testExecute_MostNumberOfArguments() throws Exception {
		Action action = ActionFixture.defaultAdapters("voidActionParams", ShamController.class);
		ShamController controller = new ShamController();
		ActionFixture.execute(action, controller);
		assertNull(controller.actionInvoked);
	}

	public void testGetParameterTypes() throws Exception {
		IAction action = ActionFixture.defaultAdapters("voidActionMultiple", ShamController.class);
		assertTrue(Arrays.equals(actionMethodOneParam.getParameterTypes(), action.getParameterTypes(1)));
		assertEquals(0, action.getParameterTypes(0).length);
	}

	public void testMethodHavingArgCount() throws Exception {
		Action action = ActionFixture.defaultAdapters("voidActionMultiple", ShamController.class);
		assertEquals(actionMethodTwoParams, action.methodHavingArgCount(2));
		assertEquals(actionMethodTwoParams, action.methodHavingArgCount(5));
		assertEquals(actionMethodOneParam, action.methodHavingArgCount(1));
		assertEquals(actionMethodNoParams, action.methodHavingArgCount(0));

		action = ActionFixture.defaultAdapters("voidActionParams", ShamController.class);
		assertNull(action.methodHavingArgCount(0));
	}
}
