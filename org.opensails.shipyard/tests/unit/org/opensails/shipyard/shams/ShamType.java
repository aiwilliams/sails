package org.opensails.shipyard.shams;

import java.io.InputStream;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jdt.core.CompletionRequestor;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.ICompletionRequestor;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IInitializer;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IOpenable;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.ITypeParameter;
import org.eclipse.jdt.core.IWorkingCopy;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.WorkingCopyOwner;

public class ShamType implements IType {

    protected String name;
    protected String[] actions;

    public ShamType(String name) {
        this.name = name;
    }

    public void codeComplete(char[] snippet, int insertion, int position, char[][] localVariableTypeNames, char[][] localVariableNames,
            int[] localVariableModifiers, boolean isStatic, ICompletionRequestor requestor) throws JavaModelException {
        // TODO Auto-generated method stub

    }

    public void codeComplete(char[] snippet, int insertion, int position, char[][] localVariableTypeNames, char[][] localVariableNames,
            int[] localVariableModifiers, boolean isStatic, ICompletionRequestor requestor, WorkingCopyOwner owner) throws JavaModelException {
        // TODO Auto-generated method stub

    }

    public void codeComplete(char[] snippet, int insertion, int position, char[][] localVariableTypeNames, char[][] localVariableNames,
            int[] localVariableModifiers, boolean isStatic, CompletionRequestor requestor) throws JavaModelException {
        // TODO Auto-generated method stub

    }

    public void codeComplete(char[] snippet, int insertion, int position, char[][] localVariableTypeNames, char[][] localVariableNames,
            int[] localVariableModifiers, boolean isStatic, CompletionRequestor requestor, WorkingCopyOwner owner) throws JavaModelException {
        // TODO Auto-generated method stub

    }

    public IField createField(String contents, IJavaElement sibling, boolean force, IProgressMonitor monitor) throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public IInitializer createInitializer(String contents, IJavaElement sibling, IProgressMonitor monitor) throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public IMethod createMethod(String contents, IJavaElement sibling, boolean force, IProgressMonitor monitor) throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public IType createType(String contents, IJavaElement sibling, boolean force, IProgressMonitor monitor) throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public IMethod[] findMethods(IMethod method) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getElementName() {
        return name;
    }

    public IField getField(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    public IField[] getFields() throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public String getFullyQualifiedName() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getFullyQualifiedName(char enclosingTypeSeparator) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getFullyQualifiedParameterizedName() throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public IInitializer getInitializer(int occurrenceCount) {
        // TODO Auto-generated method stub
        return null;
    }

    public IInitializer[] getInitializers() throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public String getKey() {
        // TODO Auto-generated method stub
        return null;
    }

    public IMethod getMethod(String name, String[] parameterTypeSignatures) {
        // TODO Auto-generated method stub
        return null;
    }

    public IMethod[] getMethods() throws JavaModelException {
        IMethod[] methods = new IMethod[actions.length];
        for (int i = 0; i < actions.length; i++) {
            methods[i] = new ShamMethod(actions[i]);
        }
        return methods;
    }

    public IPackageFragment getPackageFragment() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getSuperclassName() throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public String getSuperclassTypeSignature() throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public String[] getSuperInterfaceTypeSignatures() throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public String[] getSuperInterfaceNames() throws JavaModelException {
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

    public IType getType(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    public ITypeParameter getTypeParameter(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getTypeQualifiedName() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getTypeQualifiedName(char enclosingTypeSeparator) {
        // TODO Auto-generated method stub
        return null;
    }

    public IType[] getTypes() throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isAnonymous() throws JavaModelException {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isClass() throws JavaModelException {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isEnum() throws JavaModelException {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isInterface() throws JavaModelException {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isAnnotation() throws JavaModelException {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isLocal() throws JavaModelException {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isMember() throws JavaModelException {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isResolved() {
        // TODO Auto-generated method stub
        return false;
    }

    public ITypeHierarchy loadTypeHierachy(InputStream input, IProgressMonitor monitor) throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public ITypeHierarchy newSupertypeHierarchy(IProgressMonitor monitor) throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public ITypeHierarchy newSupertypeHierarchy(ICompilationUnit[] workingCopies, IProgressMonitor monitor) throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public ITypeHierarchy newSupertypeHierarchy(IWorkingCopy[] workingCopies, IProgressMonitor monitor) throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public ITypeHierarchy newSupertypeHierarchy(WorkingCopyOwner owner, IProgressMonitor monitor) throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public ITypeHierarchy newTypeHierarchy(IJavaProject project, IProgressMonitor monitor) throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public ITypeHierarchy newTypeHierarchy(IJavaProject project, WorkingCopyOwner owner, IProgressMonitor monitor) throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public ITypeHierarchy newTypeHierarchy(IProgressMonitor monitor) throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public ITypeHierarchy newTypeHierarchy(ICompilationUnit[] workingCopies, IProgressMonitor monitor) throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public ITypeHierarchy newTypeHierarchy(IWorkingCopy[] workingCopies, IProgressMonitor monitor) throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public ITypeHierarchy newTypeHierarchy(WorkingCopyOwner owner, IProgressMonitor monitor) throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public String[][] resolveType(String typeName) throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
    }

    public String[][] resolveType(String typeName, WorkingCopyOwner owner) throws JavaModelException {
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return 0;
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
        return new ShamFile(name + ".java");
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

    public void setActions(String[] actions) {
        this.actions = actions;
    }

}
