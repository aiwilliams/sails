package org.opensails.sails.mixins;

import junit.framework.TestCase;

import org.opensails.sails.oem.GetEvent;
import org.opensails.sails.oem.SailsEventFixture;
import org.opensails.sails.url.UrlType;

public class ScriptMixinTest extends TestCase {
	public void testScript() throws Exception {
		GetEvent event = SailsEventFixture.actionGet();
		ScriptMixin mixin = new ScriptMixin(event);
		String scriptHtml = mixin.invoke(new Object[] { "myscript" }).toString();
		assertEquals("<script type=\"text/javascript\" src=\"" + event.resolve(UrlType.SCRIPT, "myscript") + "\"></script>", scriptHtml);
	}

	public void testBuiltin() throws Exception {
		GetEvent event = SailsEventFixture.actionGet();
		ScriptMixin mixin = new ScriptMixin(event);
		String scriptHtml = mixin.invoke(new Object[] { null }).builtin("myscript").toString();
		assertEquals("<script type=\"text/javascript\" src=\"" + event.resolve(UrlType.SCRIPT_BUILTIN, "myscript") + "\"></script>", scriptHtml);
	}
}
