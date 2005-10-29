package org.opensails.shipyard;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.opensails.shipyard.model.ITreeItem;

public class TreeItemContentProvider implements ITreeContentProvider {
    public Object[] getChildren(Object parentElement) {
        return ((ITreeItem) parentElement).getChildren();
    }

    public Object getParent(Object element) {
        return null;
    }

    public boolean hasChildren(Object element) {
        return ((ITreeItem) element).hasChildren();
    }

    public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
    }

    public void dispose() {
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }
}
