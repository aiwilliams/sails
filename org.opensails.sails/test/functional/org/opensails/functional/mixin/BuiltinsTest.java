package org.opensails.functional.mixin;

import junit.framework.TestCase;

import org.opensails.functional.SailsFunctionalTester;
import org.opensails.sails.tester.Page;
import org.opensails.sails.tester.TestGetEvent;

public class BuiltinsTest extends TestCase {
    /*
     * Will fail until toString is replaced with renderThyself. BuiltinScript,
     * and other things that can be required, must be wrapped in something
     * special that will output nothing in the location they are required. That
     * is why this test is failing. It is outputing itself and the RequireOutput
     * is outputing it - I think.
     */
    public void testRequire() throws Exception {
        SailsFunctionalTester t = new SailsFunctionalTester();
        TestGetEvent event = t.createVirtualEvent("require/test", "$require.script('applicationScript')$require.script.builtin('prototype')$require.script.builtin('prototype.js')");
        Page page = t.doGet(event);
        page.scripts().assertContains("prototype", 0);
        page.scripts().assertContains("prototype.js", 1);
    }
}
