package org.opensails.shipyard.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;
import org.opensails.shipyard.ShipyardPlugin;

public class Action extends TreeItem {
    protected IMethod method;
    protected String name;
    
    public Action(IMethod method) {
        this.method = method;
        this.name = method.getElementName();
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
            IEditorPart part = IDE.openEditor(page, (IFile) method.getResource());
            ((ITextEditor)part).selectAndReveal(method.getSourceRange().getOffset(), method.getSourceRange().getLength());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public IMethod getMethod() {
        return method;
    }
}
