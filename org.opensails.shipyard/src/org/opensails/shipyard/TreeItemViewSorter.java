package org.opensails.shipyard;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.opensails.shipyard.model.ITreeItem;

public class TreeItemViewSorter extends ViewerSorter {

    public int compare(Viewer viewer, Object e1, Object e2) {
        return ((ITreeItem) e1).getName().compareTo(((ITreeItem) e2).getName());
    }
}
