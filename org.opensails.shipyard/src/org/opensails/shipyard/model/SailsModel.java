package org.opensails.shipyard.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.opensails.shipyard.ShipyardPlugin;

public class SailsModel extends TreeItem {
    protected List sailsProjects;

    public SailsModel() {
        watchProjects();
    }

    public void dispose() {
        disposeChildren(sailsProjects);
        super.dispose();
    }

    public SailsNature[] getAllSailsProjects() {
        if (sailsProjects == null) refreshSailsProjects();
        return (SailsNature[]) sailsProjects.toArray(new SailsNature[sailsProjects.size()]);
    }

    public Object[] getChildren() {
        return getAllSailsProjects();
    }

    public String getName() {
        return "Sails Model";
    }

    public SailsNature getSailsNature(IProject project) {
        try {
            return (SailsNature) project.getNature(SailsNature.ID);
        } catch (CoreException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasChildren() {
        return true;
    }

    public void open() {}

    protected void changed() {
        refreshSailsProjects();
        super.changed();
    }

    protected void createControllersSource(IJavaProject javaProject, IFolder srcFolder, String srcSubDirectory, String applicationName, String rootPackageName)
            throws CoreException, JavaModelException {
        IFolder sourceFolder = srcFolder.getFolder(srcSubDirectory);
        sourceFolder.create(true, true, null);

        IClasspathEntry classpathEntry = JavaCore.newSourceEntry(sourceFolder.getFullPath());
        javaProject.setRawClasspath(new IClasspathEntry[] { classpathEntry }, null);
    }

    protected void refreshSailsProjects() {
        disposeChildren(sailsProjects);
        sailsProjects = new ArrayList();
        try {
            IProject[] allProjects = ShipyardPlugin.getWorkspace().getRoot().getProjects();
            for (int i = 0; i < allProjects.length; i++) {
                if (allProjects[i].isOpen() && allProjects[i].hasNature(SailsNature.ID)) {
                    SailsNature sailsProject = getSailsNature(allProjects[i]);
                    sailsProject.addChangeListener(this);
                    sailsProjects.add(sailsProject);
                }
            }
        } catch (CoreException e) {
            throw new RuntimeException(e);
        }
    }
}
