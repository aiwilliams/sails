package org.opensails.shipyard.shams;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

public class ShamFolder extends ShamResource implements IFolder {

    protected String[] fileNames;

    public ShamFolder(String name) {
        super(name);
    }

    public void create(boolean force, boolean local, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void create(int updateFlags, boolean local, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void createLink(IPath localLocation, int updateFlags, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void delete(boolean force, boolean keepHistory, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }
    
    public void setFiles(String[] fileNames) {
        this.fileNames = fileNames;
    }

    public IFile getFile(String name) {
        return new ShamFile(name);
    }

    public IFolder getFolder(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    public void move(IPath destination, boolean force, boolean keepHistory, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public boolean exists(IPath path) {
        // TODO Auto-generated method stub
        return false;
    }

    public IResource findMember(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    public IResource findMember(String name, boolean includePhantoms) {
        // TODO Auto-generated method stub
        return null;
    }

    public IResource findMember(IPath path) {
        // TODO Auto-generated method stub
        return null;
    }

    public IResource findMember(IPath path, boolean includePhantoms) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getDefaultCharset() throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }

    public String getDefaultCharset(boolean checkImplicit) throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }

    public IFile getFile(IPath path) {
        // TODO Auto-generated method stub
        return null;
    }

    public IFolder getFolder(IPath path) {
        // TODO Auto-generated method stub
        return null;
    }

    public IResource[] members() throws CoreException {
        IResource[] resources = new IResource[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            resources[i] = new ShamFile(fileNames[i]);
        }
        return resources;
    }

    public IResource[] members(boolean includePhantoms) throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }

    public IResource[] members(int memberFlags) throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }

    public IFile[] findDeletedMembersWithHistory(int depth, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }

    public void setDefaultCharset(String charset) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void setDefaultCharset(String charset, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }
}
