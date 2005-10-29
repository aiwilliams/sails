package org.opensails.shipyard.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.ide.IDE;
import org.opensails.shipyard.ShipyardPlugin;

public class Template extends TreeItem {
    
    protected IFile file;
    protected String name;

    public Template(IFile file) {
        this.file = file;
        name = file.getName().substring(0, file.getName().length() - 3);
    }

    public String getName() {
        return name;
    }

    public Object[] getChildren() {
        return null;
    }

    public boolean hasChildren() {
        return false;
    }

    public void open() {
        try {
            IWorkbenchPage page = ShipyardPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
            IDE.openEditor(page, file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
