package org.opensails.functional.template;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.opensails.functional.SailsFunctionalTester;
import org.opensails.functional.controllers.TemplateTestController;
import org.opensails.sails.action.BeforeFilter;
import org.opensails.sails.action.IAction;
import org.opensails.sails.action.IActionFilter;
import org.opensails.sails.controller.oem.BaseController;
import org.opensails.sails.oem.FragmentCache;
import org.opensails.sails.oem.FragmentKey;
import org.opensails.sails.template.CacheType;
import org.opensails.sails.template.Cached;
import org.opensails.sails.tester.Page;
import org.opensails.sails.tester.util.CollectionAssert;

public class PageTests extends TestCase {
	public void testCache_Action() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester(TemplateTestController.class);
		FragmentCache fragmentCache = t.instance(FragmentCache.class);

		final Set<String> invokedActions = new HashSet<String>();

		BaseController actionCacheController = new BaseController() {
			@SuppressWarnings("unused")
			@Cached(CacheType.ACTION)
			@BeforeFilter(filter = CachedActionFilter.class)
			public void actionCached() {
				invokedActions.add("actionCached");
				renderString("hello, mate");
			}
		};

		t.getApplication().registerController("cacheTest", actionCacheController);
		t.get("cacheTest", "actionCached").assertEquals("hello, mate");
		Set<FragmentKey> cacheKeys = fragmentCache.keys("cacheTest/actionCached");
		assertEquals(1, cacheKeys.size());
		CollectionAssert.containsOnly(new String[] { "beforeAction" }, CachedActionFilter.invoked);
		CollectionAssert.containsOnly(new String[] { "actionCached" }, invokedActions);

		CachedActionFilter.invoked.clear();
		invokedActions.clear();
		t.getApplication().registerController("cacheTest", actionCacheController);
		t.get("cacheTest", "actionCached").assertEquals("hello, mate");
		cacheKeys = fragmentCache.keys("cacheTest/actionCached");
		assertEquals(1, cacheKeys.size());
		FragmentKey actionFragmentKey = cacheKeys.iterator().next();
		CollectionAssert.containsOnly(new String[] { "beforeAction" }, CachedActionFilter.invoked);
		assertTrue(invokedActions.isEmpty());

		CachedActionFilter.invoked.clear();
		invokedActions.clear();
		fragmentCache.expire(actionFragmentKey);
		t.getApplication().registerController("cacheTest", actionCacheController);
		t.get("cacheTest", "actionCached").assertEquals("hello, mate");
		CollectionAssert.containsOnly(new String[] { "beforeAction" }, CachedActionFilter.invoked);
		CollectionAssert.containsOnly(new String[] { "actionCached" }, invokedActions);
	}

	public void testCache_Fragment() {
		SailsFunctionalTester t = new SailsFunctionalTester(TemplateTestController.class);

		TemplateTestController.RENDERED_IN_TEMPLATE = "Value on first call";
		t.registerTemplate("templateTest/cacheFragment", "$cache.fragment [[$renderedInTemplate]]; $cache.fragment('named') [[$renderedInTemplate]]");
		t.get("cacheFragment").assertEquals("Value on first call Value on first call");

		TemplateTestController.RENDERED_IN_TEMPLATE = "Value on second call";
		t.get("cacheFragment").assertEquals("Value on first call Value on first call");

		t.get("expireFragment");
		t.get("cacheFragment").assertEquals("Value on second call Value on first call");
	}

	public void testExposed() throws Exception {
		SailsFunctionalTester t = new SailsFunctionalTester(TemplateTestController.class);
		Page page = t.get("exposeMethod");
		page.exposed("inMethod").assertEquals("inMethodValue");
	}

	public static class CachedActionFilter implements IActionFilter<BaseController> {
		public static Set<String> invoked = new HashSet<String>();

		public void afterAction(IAction action, BaseController context) {
		// TODO: One after filters are implemented, include that here and in
		// tests referencing my invoked
		}

		public boolean beforeAction(IAction action, BaseController context) {
			invoked.add("beforeAction");
			return true;
		}
	}
}
