package org.opensails.shipyard.shams;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IOpenable;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeParameter;
import org.eclipse.jdt.core.JavaModelException;

public class ShamMethod implements IMethod {
    
    protected String name;

    public ShamMethod(String name) {
        this.name = name;
    }

    public String getElementName() {
        return name;
    }

    public String[] getExceptionTypes() throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public String[] getTypeParameterSignatures() throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public ITypeParameter[] getTypeParameters() throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public int getNumberOfParameters() {
        // TODO Auto-generated method stub
        return 0;
    }

    public String getKey() {
        // TODO Auto-generated method stub
        return null;
    }

    public String[] getParameterNames() throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public String[] getParameterTypes() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getReturnType() throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public String getSignature() throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public ITypeParameter getTypeParameter(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isConstructor() throws JavaModelException {
        return false;
    }

    public boolean isMainMethod() throws JavaModelException {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isResolved() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isSimilar(IMethod method) {
        // TODO Auto-generated method stub
        return false;
    }

    public IClassFile getClassFile() {
        // TODO Auto-generated method stub
        return null;
    }

    public ICompilationUnit getCompilationUnit() {
        // TODO Auto-generated method stub
        return null;
    }

    public IType getDeclaringType() {
        // TODO Auto-generated method stub
        return null;
    }

    public int getFlags() throws JavaModelException {
        return Flags.AccPublic;
    }

    public ISourceRange getNameRange() throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public IType getType(String name, int occurrenceCount) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isBinary() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean exists() {
        // TODO Auto-generated method stub
        return false;
    }

    public IJavaElement getAncestor(int ancestorType) {
        // TODO Auto-generated method stub
        return null;
    }

    public IResource getCorrespondingResource() throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public int getElementType() {
        // TODO Auto-generated method stub
        return 0;
    }

    public String getHandleIdentifier() {
        // TODO Auto-generated method stub
        return null;
    }

    public IJavaModel getJavaModel() {
        // TODO Auto-generated method stub
        return null;
    }

    public IJavaProject getJavaProject() {
        // TODO Auto-generated method stub
        return null;
    }

    public IOpenable getOpenable() {
        // TODO Auto-generated method stub
        return null;
    }

    public IJavaElement getParent() {
        // TODO Auto-generated method stub
        return null;
    }

    public IPath getPath() {
        // TODO Auto-generated method stub
        return null;
    }

    public IJavaElement getPrimaryElement() {
        // TODO Auto-generated method stub
        return null;
    }

    public IResource getResource() {
        // TODO Auto-generated method stub
        return null;
    }

    public ISchedulingRule getSchedulingRule() {
        // TODO Auto-generated method stub
        return null;
    }

    public IResource getUnderlyingResource() throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isReadOnly() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isStructureKnown() throws JavaModelException {
        // TODO Auto-generated method stub
        return false;
    }

    public Object getAdapter(Class adapter) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getSource() throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public ISourceRange getSourceRange() throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public void copy(IJavaElement container, IJavaElement sibling, String rename, boolean replace, IProgressMonitor monitor)
            throws JavaModelException {
        // TODO Auto-generated method stub

    }

    public void delete(boolean force, IProgressMonitor monitor) throws JavaModelException {
        // TODO Auto-generated method stub

    }

    public void move(IJavaElement container, IJavaElement sibling, String rename, boolean replace, IProgressMonitor monitor)
            throws JavaModelException {
        // TODO Auto-generated method stub

    }

    public void rename(String name, boolean replace, IProgressMonitor monitor) throws JavaModelException {
        // TODO Auto-generated method stub

    }

    public IJavaElement[] getChildren() throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean hasChildren() throws JavaModelException {
        // TODO Auto-generated method stub
        return false;
    }

}
