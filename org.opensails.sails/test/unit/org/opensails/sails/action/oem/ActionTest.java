package org.opensails.sails.action.oem;

import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.apache.commons.lang.ArrayUtils;
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
		Action action = new Action("voidActionMultiple", ShamController.class, null);
		CollectionAssert.containsOnlyOrdered(new Method[] { actionMethodTwoParams, actionMethodOneParam, actionMethodNoParams }, action.actionMethods);
	}

	/**
	 * It is important to maintain knowledge about the number of arguments for
	 * each method that matches our action name. This allows the action to
	 * execute the method that most closely resembles our URL and it's quantity
	 * of parameters.
	 */
	public void testConstructor_SingleMethod() throws Exception {
		Action voidActionNoParams = new Action("voidActionNoParams", ShamController.class, null);
		Method voidActionNoParamsMethod = ShamController.class.getMethod("voidActionNoParams", ArrayUtils.EMPTY_CLASS_ARRAY);
		CollectionAssert.containsOnly(voidActionNoParamsMethod, voidActionNoParams.actionMethods);
	}

	public void testMethodHavingArgCount() throws Exception {
		Action action = new Action("voidActionMultiple", ShamController.class, null);
		assertEquals(actionMethodTwoParams, action.methodHavingArgCount(2));
		assertEquals(actionMethodTwoParams, action.methodHavingArgCount(5));
		assertEquals(actionMethodOneParam, action.methodHavingArgCount(1));
		assertEquals(actionMethodNoParams, action.methodHavingArgCount(0));

		action = new Action("voidActionParams", ShamController.class, null);
		assertNull(action.methodHavingArgCount(0));
	}
}
