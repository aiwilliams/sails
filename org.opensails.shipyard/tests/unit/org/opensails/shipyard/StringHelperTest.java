package org.opensails.shipyard;

import junit.framework.TestCase;


public class StringHelperTest extends TestCase {

    public void testSansSuffix() {
        assertEquals("sans", StringHelper.sansSuffix("sansSuffix", "Suffix"));
        assertEquals("notApplicable", StringHelper.sansSuffix("notApplicable", "Suffix"));
    }
    
    public void testCapitalized() {
        assertEquals("Capitalized", StringHelper.capitalized("capitalized"));
        assertEquals("Capitalized", StringHelper.capitalized("Capitalized"));
    }

    public void testNotCapitalized() {
        assertEquals("notCapitalized", StringHelper.notCapitalized("NotCapitalized"));
        assertEquals("notCapitalized", StringHelper.notCapitalized("notCapitalized"));
    }
}
