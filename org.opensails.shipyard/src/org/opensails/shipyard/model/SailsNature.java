package org.opensails.shipyard.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.opensails.sails.controller.IController;
import org.opensails.shipyard.ShipyardPlugin;
import org.opensails.shipyard.StringHelper;
import org.osgi.service.prefs.BackingStoreException;

public class SailsNature extends TreeItem implements IProjectNature {
    protected static final String APPLICATION_TYPE_NAME_KEY = "org.opensails.shipyard.sailsnature.applicationTypeName";
    protected static final String APPLICATION_DIRECTORY_KEY = "org.opensails.shipyard.sailsnature.applicationDirectory";
    public static final String ID = "org.opensails.shipyard.sailsnature";

    protected IProject project;
    protected IType type;
    protected Map controllers;

    protected void addControllers(ICompilationUnit cu, Map controllers) throws JavaModelException {
        IType[] types = cu.getTypes();
        for (int i = 0; i < types.length; i++)
            addControllers(types[i], controllers);
    }

    protected void addControllers(IType type, Map controllers) {
        try {
            if (Flags.isAbstract(type.getFlags()))
                return;
            ITypeHierarchy hierarchy = type.newSupertypeHierarchy(null);
            if (hierarchy.contains(getControllerType())) {
                Controller controller = getController(type);
                controllers.put(controller.getName(), controller);
            }
        } catch (JavaModelException e) {
            throw new RuntimeException(e);
        }
    }

    protected Controller getController(IType type) {
        Controller controller = new Controller(type);
        controller.setTemplateFolder(getViewFolder().getFolder(controller.getName()));
        controller.addChangeListener(this);
        return controller;
    }

    public void configure() throws CoreException {
    }

    public void deconfigure() throws CoreException {
    }

    protected void changed() {
        if (refreshControllers())
            super.changed();
    }

    public Controller[] getControllers() {
        if (controllers == null)
            refreshControllers();
        return (Controller[]) controllers.values().toArray(new Controller[controllers.size()]);
    }

    /**
     * @return true if the list changed.
     */
    protected boolean refreshControllers() {
        Set oldKeys = getKeys();
        Map controllers = findControllers();
        boolean keysChanged = !controllers.keySet().containsAll(oldKeys) || controllers.keySet().size() != oldKeys.size();
        if (keysChanged) {
            disposeControllers();
            this.controllers = controllers;
        } else
            disposeChildren(controllers.values());
        return keysChanged;
    }

    protected Map findControllers() {
        Map controllers = new HashMap();
        addJavaControllers(controllers);
        addTemplateControllers(controllers);
        return controllers;
    }

    protected Set getKeys() {
        if (controllers == null)
            return Collections.EMPTY_SET;
        return controllers.keySet();
    }

    protected void disposeControllers() {
        if (controllers == null)
            return;
        disposeChildren(controllers.values());
    }

    public void dispose() {
        disposeControllers();
        super.dispose();
    }

    protected void addTemplateControllers(Map controllers) {
        try {
            IResource[] folders = getViewFolder().members();
            for (int i = 0; i < folders.length; i++) {
                String name = folders[i].getName();
                if (name.equals("CVS") || !(folders[i] instanceof IFolder))
                    continue;
                Controller controller = (Controller) controllers.get(name);
                if (controller == null) {
                    Controller newController = new Controller((IFolder) folders[i]);
                    newController.addChangeListener(this);
                    controllers.put(name, newController);
                }
            }
        } catch (CoreException e) {
            throw new RuntimeException(e);
        }
    }

    public IFolder getViewFolder() {
        return getProject().getFolder(getEclipsePreferences().get(APPLICATION_DIRECTORY_KEY, "app") + "/WEB-INF/view");
    }

    protected void addJavaControllers(Map controllers) {
        try {
            ICompilationUnit[] compilationUnits = getControllersPackage().getCompilationUnits();
            for (int i = 0; i < compilationUnits.length; i++)
                addControllers(compilationUnits[i], controllers);
        } catch (JavaModelException e) {
            throw new RuntimeException(e);
        }
    }

    public IPackageFragment getControllersPackage() {
        IPackageFragment rootPackage = getType().getPackageFragment();
        IPackageFragmentRoot src = (IPackageFragmentRoot) rootPackage.getParent();
        return src.getPackageFragment(rootPackage.getElementName() + ".controllers");
    }

    /**
     * @return the org.javaonsails.sails.Controller type that is on this
     *         project's classpath.
     */
    public IType getControllerType() {
        return getType(IController.class.getName());
    }

    protected IType getType(String fullyQualifiedName) {
        try {
            return getJavaProject().findType(fullyQualifiedName);
        } catch (JavaModelException e) {
            throw new RuntimeException(e);
        }
    }

    protected IEclipsePreferences getEclipsePreferences() {
        IScopeContext context = new ProjectScope(getProject());
        return context.getNode(ShipyardPlugin.PLUGIN_ID);
    }

    protected IJavaProject getJavaProject() {
        return JavaCore.create(project);
    }

    public IProject getProject() {
        return project;
    }

    protected String getProperty(String key) {
        String value = getEclipsePreferences().get(key, null);
        if (value == null)
            throw new RuntimeException("Settings corrupt for Sails Project " + project.getName());
        return value;
    }

    public IType getType() {
        if (type == null) {
            String typeName = getProperty(APPLICATION_TYPE_NAME_KEY);
            try {
                type = getJavaProject().findType(typeName);
            } catch (JavaModelException e) {
                throw new RuntimeException(e);
            }
        }
        return type;
    }

    public void setApplicationTypeName(String fullyQualifiedName) {
        getEclipsePreferences().put(APPLICATION_TYPE_NAME_KEY, fullyQualifiedName);
        try {
            getEclipsePreferences().flush();
        } catch (BackingStoreException e) {
            throw new RuntimeException(e);
        }
        refreshWorkspaceListeners();
    }

    protected void refreshWorkspaceListeners() {
        disposeWorkspaceListeners();
        createWorkspaceListeners();
    }

    public void setProject(IProject project) {
        this.project = project;
        createWorkspaceListeners();
    }

    protected void createWorkspaceListeners() {
        try {
            watchChildren(getControllersPackage().getResource(), IResourceChangeEvent.POST_BUILD);
        } catch (Exception e) {
            // Something didn't exist. Carry on.
        }
        watchChildren(getViewFolder());
    }

    public String getName() {
        return StringHelper.sansSuffix(getType().getElementName(), "Configurator");
    }

    public Object[] getChildren() {
        return getControllers();
    }

    public boolean hasChildren() {
        return true;
    }

    public void open() {
        try {
            JavaUI.openInEditor(getType());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Controller createController(String controllerName, boolean shouldCreateClass, boolean shouldCreateViewFolder) {
        // TODO: Check that the controller doesn't already exist.
        if (shouldCreateClass) {
            try {
                ICompilationUnit unit = getControllersPackage().createCompilationUnit(StringHelper.capitalized(controllerName) + ".java",
                        getControllerBody(controllerName), false, null);
                unit.save(null, false);
            } catch (JavaModelException e) {
                throw new RuntimeException("This probably means that there was already a controller with that name.", e);
            }
        }
        if (shouldCreateViewFolder) {
            try {
                getViewFolder().getFolder(StringHelper.notCapitalized(controllerName)).create(false, true, null);
            } catch (CoreException e) {
                throw new RuntimeException(e);
            }
        }
        changed();
        return getController(controllerName);
    }

    protected String getControllerBody(String name) {
        return "package " + (getControllersPackage().getElementName()) + ";\r\n\r\nimport "
                + (getControllerType().getFullyQualifiedName()) + ";\r\n\r\npublic class " + (StringHelper.capitalized(name))
                + " implements IController {\r\n}";
    }

    public Controller getController(String controllerName) {
        if (controllers == null)
            refreshControllers();
        return (Controller) controllers.get(StringHelper.notCapitalized(controllerName));
    }
}
