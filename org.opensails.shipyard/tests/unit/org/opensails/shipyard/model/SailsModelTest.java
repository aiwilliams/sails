package org.opensails.shipyard.model;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.opensails.shipyard.ShipyardPlugin;

public class SailsModelTest extends TestCase {

    protected IProject project;
    protected SailsNature sailsNature;
    protected IJavaProject javaProject;
	protected IProject closedProject;

    protected void setUp() throws Exception {
        project = ShipyardPlugin.getWorkspace().getRoot().getProject("org.opensails.test");
        project.create(null);
        project.open(null);

        IProjectDescription description = project.getDescription();
        description.setNatureIds(new String[] { JavaCore.NATURE_ID, SailsNature.ID });
        project.setDescription(description, null);
        
        sailsNature = ShipyardPlugin.getDefault().getSailsModel().getSailsNature(project);
        javaProject = JavaCore.create(project);
        javaProject.save(null, true);
		
		closedProject = ShipyardPlugin.getWorkspace().getRoot().getProject("org.opensails.closed");
		closedProject.create(null);
		closedProject.close(null);
    }

    protected void tearDown() throws Exception {
        // TODO: Make setUp lazy so we don't have to start over every time
        project.delete(true, true, null);
        closedProject.delete(true, true, null);
    }

    public void testGetAllSailsProjects() {
        SailsNature[] projects = ShipyardPlugin.getDefault().getSailsModel().getAllSailsProjects();
        assertEquals(1, projects.length);
        assertSame(sailsNature, projects[0]);
    }

    public void testGetSailsNature() {
        assertSame(sailsNature, ShipyardPlugin.getDefault().getSailsModel().getSailsNature(project));
    }
}
