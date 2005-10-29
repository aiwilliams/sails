package org.opensails.shipyard.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;

public class Controller extends TreeItem {

    protected static final String CONTROLLER_SUFFIX = "Controller";
    protected IType type;
    protected String name;
    protected IFolder templateFolder;
    protected IFile indexTemplate;
    protected List templates;
    protected List actions;

    public Controller(IType type) {
        setType(type);
    }

    public Controller(IFolder folder) {
        setTemplateFolder(folder);
    }

    protected void setType(IType type) {
        watch(type.getResource());
        this.type = type;
        String typeName = type.getElementName();
        if (typeName.endsWith(CONTROLLER_SUFFIX))
            typeName = typeName.substring(0, typeName.length() - CONTROLLER_SUFFIX.length());
        name = Character.toLowerCase(typeName.charAt(0)) + typeName.substring(1);
    }

    public String getName() {
        return name;
    }

    public IType getType() {
        return type;
    }

    public IFile getIndexTemplate() {
        return indexTemplate;
    }

    public IFolder getTemplateFolder() {
        return templateFolder;
    }

    public Object[] getChildren() {
        List children = new ArrayList();
        children.addAll(getTemplates());
        children.addAll(getActions());
        return children.toArray();
    }

    public boolean hasChildren() {
        return true;
    }

    public List getTemplates() {
        if (templates == null)
            refreshTemplates();
        return templates;
    }

    public void dispose() {
        disposeChildren(templates);
        disposeChildren(actions);
        super.dispose();
    }

    protected void changed() {
        refreshTemplates();
        refreshActions();
        super.changed();
    }

    protected void refreshTemplates() {
        disposeChildren(templates);
        templates = Collections.EMPTY_LIST;
        if (!templateFolder.exists())
            return;
        try {
            IResource[] files = templateFolder.members();
            templates = new ArrayList(files.length);
            for (int i = 0; i < files.length; i++) {
                Template template = new Template((IFile) files[i]);
                template.addChangeListener(this);
                templates.add(template);
            }
        } catch (CoreException e) {
            throw new RuntimeException(e);
        }
    }

    public List getActions() {
        if (actions == null)
            refreshActions();
        return actions;
    }
    
    public Action getAction(String name) {
        for (Iterator iter = getActions().iterator(); iter.hasNext();) {
            Action action = (Action) iter.next();
            if (action.getName().equals(name))
                return action;
        }
        return null;
    }
    
    public Template getTemplate(String name) {
        for (Iterator iter = getTemplates().iterator(); iter.hasNext();) {
            Template template = (Template) iter.next();
            if (template.getName().equals(name))
                return template;
        }
        return null;
    }

    protected void refreshActions() {
        disposeChildren(actions);
        actions = new ArrayList();
        try {
            IMethod[] methods = getType().getMethods();
            for (int i = 0; i < methods.length; i++) {
                IMethod method = methods[i];
                if (isAction(method)) {
                    Action action = new Action(method);
                    action.addChangeListener(this);
                    actions.add(action);
                }
            }
        } catch (JavaModelException e) {
            throw new RuntimeException(e);
        }
    }

    protected boolean isAction(IMethod method) throws JavaModelException {
        return Flags.isPublic(method.getFlags()) && !method.isConstructor();
    }

    public void setTemplateFolder(IFolder folder) {
        watchChildren(folder);
        this.templateFolder = folder;
        if (name == null)
            name = folder.getName();
        IFile index = folder.getFile("index.vm");
        if (index.exists())
            indexTemplate = index;
    }

    public void open() {
        try {
            if (getType() != null)
                JavaUI.openInEditor(getType());
            // else if (getIndexTemplate() != null)
            // EditorUtility.openInEditor(getIndexTemplate());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
