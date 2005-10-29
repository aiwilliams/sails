package org.opensails.shipyard.model;

public interface ITreeItem {
    String getName();
    Object[] getChildren();
    boolean hasChildren();
    void open();
    void addChangeListener(TreeItemChangeListener listener);
    void removeChangeListener(TreeItemChangeListener listener);
    void dispose();
}
