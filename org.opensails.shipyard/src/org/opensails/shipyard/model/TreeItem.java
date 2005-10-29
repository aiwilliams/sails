package org.opensails.shipyard.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.opensails.shipyard.ShipyardPlugin;

public abstract class TreeItem implements ITreeItem, TreeItemChangeListener {

    protected List listeners = new ArrayList();
    protected List resourceListeners = new ArrayList();

    public void addChangeListener(TreeItemChangeListener listener) {
        listeners.add(listener);
    }

    public void removeChangeListener(TreeItemChangeListener listener) {
        listeners.remove(listener);
    }

    protected void fireChangeEvent(ITreeItem item) {
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            TreeItemChangeListener listener = (TreeItemChangeListener) iter.next();
            listener.changed(item);
        }
    }

    protected void disposeChildren(Collection children) {
        if (children == null) return;
        for (Iterator iter = children.iterator(); iter.hasNext();) {
            ITreeItem item = (ITreeItem) iter.next();
            item.dispose();
        }
    }

    public void dispose() {
        listeners.clear();
        disposeWorkspaceListeners();
    }

    protected void disposeWorkspaceListeners() {
        for (Iterator iter = resourceListeners.iterator(); iter.hasNext();) {
            IResourceChangeListener listener = (IResourceChangeListener) iter.next();
            ShipyardPlugin.getWorkspace().removeResourceChangeListener(listener);
        }
    }

    public void changed(ITreeItem item) {
        fireChangeEvent(item);
    }

    protected void changed() {
        fireChangeEvent(this);
    }

    protected void watch(final IResource resource) {
        IResourceChangeListener listener = new IResourceChangeListener() {
            public void resourceChanged(IResourceChangeEvent event) {
                try {
                    event.getDelta().accept(new IResourceDeltaVisitor() {
                        public boolean visit(IResourceDelta delta) throws CoreException {
                            if (delta.getAffectedChildren().length == 0 && delta.getResource().getFullPath().equals(resource.getFullPath())) changed();
                            return true;
                        }
                    });
                } catch (CoreException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        resourceListeners.add(listener);
        resource.getWorkspace().addResourceChangeListener(listener, IResourceChangeEvent.POST_BUILD | IResourceChangeEvent.POST_CHANGE);
    }

    protected void watchChildren(final IResource resource) {
        watchChildren(resource, IResourceChangeEvent.POST_CHANGE);
    }

    protected void watchChildren(final IResource resource, int eventMask) {
        IResourceChangeListener listener = new IResourceChangeListener() {
            public void resourceChanged(IResourceChangeEvent event) {
                try {
                    event.getDelta().accept(new IResourceDeltaVisitor() {
                        public boolean visit(IResourceDelta delta) throws CoreException {
                            if (delta.getAffectedChildren().length == 0 && delta.getResource().getParent().getFullPath().equals(resource.getFullPath())) changed();
                            return true;
                        }
                    });
                } catch (CoreException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        resourceListeners.add(listener);
        resource.getWorkspace().addResourceChangeListener(listener, eventMask);
    }

    protected void watchProjects() {
        IResourceChangeListener listener = new IResourceChangeListener() {
            public void resourceChanged(IResourceChangeEvent event) {
                try {
                    event.getDelta().accept(new IResourceDeltaVisitor() {
                        public boolean visit(IResourceDelta delta) throws CoreException {
                            if (delta.getAffectedChildren().length == 0 && delta.getResource() instanceof IProject) changed();
                            return true;
                        }
                    });
                } catch (CoreException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        resourceListeners.add(listener);
        ShipyardPlugin.getWorkspace().addResourceChangeListener(listener, IResourceChangeEvent.POST_CHANGE);
    }
}
