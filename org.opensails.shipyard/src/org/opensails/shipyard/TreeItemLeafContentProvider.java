package org.opensails.shipyard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.opensails.shipyard.model.ITreeItem;

public class TreeItemLeafContentProvider implements ITreeContentProvider {
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
        return getLeaves(inputElement).toArray();
    }

    protected List getLeaves(Object element) {
        if (!hasChildren(element))
            return Arrays.asList(new Object[] {element});
        List childrensLeaves = new ArrayList();
        Object[] children = getChildren(element);
        for (int i = 0; i < children.length; i++)
            childrensLeaves.addAll(getLeaves(children[i]));
        return childrensLeaves;
    }

    public void dispose() {
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }
}
