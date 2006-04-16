/*
 * Created on Mar 2, 2005
 * 
 * (c) 2005 Adam Williams
 */
package org.opensails.sails.util;

import junit.framework.TestCase;

import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.controller.oem.ShamController;
import org.opensails.spyglass.SpyGlass;
import org.opensails.spyglass.resolvers.PackageClassResolver;

public class ComponentPackageTest extends TestCase {
    protected PackageClassResolver<IControllerImpl> resolver = new PackageClassResolver<IControllerImpl>(SpyGlass.getPackageName(ShamController.class), "Controller");

    public void testResolveComponentImplementation_DiscoverInPackage() throws Exception {
        Class<? extends IControllerImpl> type = resolver.resolve("shamController");
        assertEquals(ShamController.class, type);
    }

    public void testResolveControllerImplementation_DiscoverInPackage_AppendingControllerToName() {
        Class<? extends IControllerImpl> type = resolver.resolve("sham");
        assertEquals("Controller suffix should be optional", ShamController.class, type);
    }
}
