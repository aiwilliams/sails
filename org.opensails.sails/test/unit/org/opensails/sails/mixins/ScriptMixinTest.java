package org.opensails.sails.mixins;

import junit.framework.TestCase;

import org.opensails.sails.event.oem.GetEvent;
import org.opensails.sails.event.oem.SailsEventFixture;
import org.opensails.sails.url.UrlType;
import org.opensails.viento.tester.VientoTester;

public class ScriptMixinTest extends TestCase {
	public void testScript() throws Exception {
		GetEvent event = SailsEventFixture.actionGet();
		VientoTester tester = new SailsBuiltinVientoTester(event);
		tester.verifyRender("$script(myscript)", "<script type=\"text/javascript\" src=\"" + event.resolve(UrlType.SCRIPT, "myscript") + "\"></script>");
	}

	public void testScript_WithBlock() throws Exception {
		GetEvent event = SailsEventFixture.actionGet();
		VientoTester tester = new SailsBuiltinVientoTester(event);
		tester.verifyRender("$script [[ some script ]]", "<script type=\"text/javascript\">\n some script \n</script>");
	}
	
	public void testBuiltin() throws Exception {
		GetEvent event = SailsEventFixture.actionGet();
		VientoTester tester = new SailsBuiltinVientoTester(event);
		tester.verifyRender("$script.builtin(myscript)", "<script type=\"text/javascript\" src=\"" + event.resolve(UrlType.SCRIPT_BUILTIN, "myscript") + "\"></script>");
	}
}
