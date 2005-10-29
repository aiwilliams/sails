package org.opensails.shipyard.shams;

import java.io.InputStream;
import java.io.Reader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.content.IContentDescription;

public class ShamFile extends ShamResource implements IFile {

    public ShamFile(String name) {
        super(name);
    }

    public void appendContents(InputStream source, boolean force, boolean keepHistory, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void appendContents(InputStream source, int updateFlags, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void create(InputStream source, boolean force, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void create(InputStream source, int updateFlags, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void createLink(IPath localLocation, int updateFlags, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void delete(boolean force, boolean keepHistory, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public String getCharset() throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }

    public String getCharset(boolean checkImplicit) throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }

    public String getCharsetFor(Reader reader) throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }

    public IContentDescription getContentDescription() throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }

    public InputStream getContents() throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }

    public InputStream getContents(boolean force) throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }

    public int getEncoding() throws CoreException {
        // TODO Auto-generated method stub
        return 0;
    }

    public IFileState[] getHistory(IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }

    public void move(IPath destination, boolean force, boolean keepHistory, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void setCharset(String newCharset) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void setCharset(String newCharset, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void setContents(InputStream source, boolean force, boolean keepHistory, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void setContents(IFileState source, boolean force, boolean keepHistory, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void setContents(InputStream source, int updateFlags, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    public void setContents(IFileState source, int updateFlags, IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

}
