package org.opensails.shipyard;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.opensails.shipyard.model.ITreeItem;

public class TreeItemLabelProvider extends LabelProvider {
    public String getText(Object element) {
        return ((ITreeItem) element).getName();
    }

    public Image getImage(Object element) {
        return ShipyardPlugin.getDefault().getImageRegistry().get(element.getClass().getName());
    }
}
