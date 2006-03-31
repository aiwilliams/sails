package org.opensails.sails.model.oem;

import junit.framework.TestCase;

import org.opensails.sails.tester.util.CollectionAssert;

public class DotPropertyPathTest extends TestCase {
    protected DotPropertyPath path;
    protected DotPropertyPath shortPath;

    protected void setUp() {
        path = new DotPropertyPath("level1.level2.level3.level4");
        shortPath = new DotPropertyPath("level1");
    }

    public void testAllNodes() {
        CollectionAssert.containsOnly(new String[] { "level1", "level2", "level3", "level4" }, path.getAllNodes());
        CollectionAssert.containsOnly(new String[] { "level1" }, shortPath.getAllNodes());
    }

    public void testConstructor() throws Exception {
        try {
            new DotPropertyPath("");
            fail("empty is useless");
        } catch (IllegalArgumentException expected) {}
        try {
            new DotPropertyPath("   ");
            fail("blank is useless");
        } catch (IllegalArgumentException expected) {}
        try {
            new DotPropertyPath(null);
            fail("null is useless");
        } catch (NullPointerException e) {}
        
        CollectionAssert.containsOnly(new String[] {"a", "b"}, new DotPropertyPath("  a.b ").getAllNodes());
    }

    public void testGetNodeCount() {
        assertEquals(4, path.getNodeCount());
        assertEquals(1, shortPath.getNodeCount());
    }

    public void testGetProperty() {
        assertEquals("level4", path.getPropertyName());
        assertEquals("level1", shortPath.getPropertyName());
    }

    public void testGetProperty_AtIndex() {
        assertEquals("level1.level2.level3.level4", path.getProperty(0));
        assertEquals("level2.level3.level4", path.getProperty(1));
        assertEquals("level3.level4", path.getProperty(2));
        assertEquals("level4", path.getProperty(3));
        assertEquals("Should get an empty string when indexing beyond length of DotPropertyPath.", "", path.getProperty(4));

        try {
            path.getProperty(-1);
            fail("Should have thrown a RuntimeException when index is negative.");
        } catch (RuntimeException e) {}

        assertEquals("level1", shortPath.getProperty(0));
        assertEquals("Should get an empty string when indexing beyond length of short DotPropertyPath.", "", shortPath.getProperty(1));
    }

    public void testGetTargetIdentifier() {
        assertEquals("level1", path.getModelName());
        assertEquals("level1", shortPath.getModelName());
    }
}
