package org.opensails.shipyard.shams;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.jobs.ISchedulingRule;

public class ShamResource implements IResource {

    protected String name;

    public ShamResource(String name) {
        this.name = name;
    }
    
    public ShamResource() {
    }
    
    public void accept(IResourceProxyVisitor visitor, int memberFlags) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void accept(IResourceVisitor visitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void accept(IResourceVisitor visitor, int depth, boolean includePhantoms) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void accept(IResourceVisitor visitor, int depth, int memberFlags) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void clearHistory(IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void copy(IPath destination, boolean force, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void copy(IPath destination, int updateFlags, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void copy(IProjectDescription description, boolean force, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void copy(IProjectDescription description, int updateFlags, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public IMarker createMarker(String type) throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }

    public void delete(boolean force, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void delete(int updateFlags, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void deleteMarkers(String type, boolean includeSubtypes, int depth) throws CoreException {
        // TODO Auto-generated method stub

    }

    public boolean exists() {
        return true;
    }

    public IMarker findMarker(long id) throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }

    public IMarker[] findMarkers(String type, boolean includeSubtypes, int depth) throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }

    public String getFileExtension() {
        // TODO Auto-generated method stub
        return null;
    }

    public IPath getFullPath() {
        return new Path("/" + name);
    }

    public long getLocalTimeStamp() {
        // TODO Auto-generated method stub
        return 0;
    }

    public IPath getLocation() {
        // TODO Auto-generated method stub
        return null;
    }

    public IMarker getMarker(long id) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getModificationStamp() {
        // TODO Auto-generated method stub
        return 0;
    }

    public String getName() {
        return name;
    }

    public IContainer getParent() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getPersistentProperty(QualifiedName key) throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }

    public IProject getProject() {
        // TODO Auto-generated method stub
        return null;
    }

    public IPath getProjectRelativePath() {
        // TODO Auto-generated method stub
        return null;
    }

    public IPath getRawLocation() {
        // TODO Auto-generated method stub
        return null;
    }

    public ResourceAttributes getResourceAttributes() {
        // TODO Auto-generated method stub
        return null;
    }

    public Object getSessionProperty(QualifiedName key) throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }

    public int getType() {
        // TODO Auto-generated method stub
        return 0;
    }

    public IWorkspace getWorkspace() {
        return ShamWorkspace.getDefault();
    }

    public boolean isAccessible() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isDerived() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isLocal(int depth) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isLinked() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isPhantom() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isReadOnly() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isSynchronized(int depth) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isTeamPrivateMember() {
        // TODO Auto-generated method stub
        return false;
    }

    public void move(IPath destination, boolean force, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void move(IPath destination, int updateFlags, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void move(IProjectDescription description, boolean force, boolean keepHistory, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void move(IProjectDescription description, int updateFlags, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void refreshLocal(int depth, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void revertModificationStamp(long value) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void setDerived(boolean isDerived) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void setLocal(boolean flag, int depth, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public long setLocalTimeStamp(long value) throws CoreException {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setPersistentProperty(QualifiedName key, String value) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void setReadOnly(boolean readOnly) {
        // TODO Auto-generated method stub

    }

    public void setResourceAttributes(ResourceAttributes attributes) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void setSessionProperty(QualifiedName key, Object value) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void setTeamPrivateMember(boolean isTeamPrivate) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void touch(IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public Object getAdapter(Class adapter) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean contains(ISchedulingRule rule) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isConflicting(ISchedulingRule rule) {
        // TODO Auto-generated method stub
        return false;
    }

}
