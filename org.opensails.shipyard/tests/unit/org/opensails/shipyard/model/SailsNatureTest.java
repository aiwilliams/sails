package org.opensails.shipyard.model;

import java.io.ByteArrayInputStream;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.opensails.shipyard.ShipyardPlugin;

public class SailsNatureTest extends TestCase {

    protected IProject project;
    protected SailsNature sailsNature;
    protected IPackageFragment controllersPackage;
    protected Controller controller;

    protected void setUp() throws Exception {
        SailsProject sailsProject = new SailsProject("TestProject");
        sailsProject.setRootPackage("test.project");
        sailsProject.setApplicationName("TestProject");
        project = sailsProject.create();
        
        sailsNature = ShipyardPlugin.getDefault().getSailsModel().getSailsNature(project);
        controller = sailsNature.createController("TestController", true, true);
        controller.getTemplateFolder().getFile("index.vto").create(new ByteArrayInputStream("".getBytes()), true, null);
        
        controllersPackage = sailsNature.getControllersPackage();
        controllersPackage.createCompilationUnit("NotAController.java", "package test.project.controllers;\r\n\r\npublic class NotAController {\r\n}", true, null);
        controllersPackage.createCompilationUnit("AbstractController.java", "package test.project.controllers;\r\n\r\nimport org.opensails.sails.oem.BaseController;\r\n\r\npublic abstract class AbstractController extends BaseController {\r\n}", true, null);
    }
    
    protected void tearDown() throws Exception {
        // TODO: Make setUp lazy so we don't have to start over every time  
        project.delete(true, true, null);
    }
    
    public void testGetControllers() {
        Controller[] actual = sailsNature.getControllers();
        assertEquals(1, actual.length);
        assertEquals("test", actual[0].getName());
        assertEquals(controller.getType().getFullyQualifiedName(), actual[0].getType().getFullyQualifiedName());
		List templates = actual[0].getTemplates();
		assertEquals(1, templates.size());
		assertEquals("index", ((Template) templates.get(0)).getName());
    }
}
