package org.opensails.shipyard.model;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.JavaRuntime;
import org.opensails.sails.Sails;
import org.opensails.shipyard.SailsClasspathContainer;
import org.opensails.shipyard.ShipyardPlugin;
import org.opensails.shipyard.StringHelper;
import org.opensails.viento.Binding;
import org.opensails.viento.VientoTemplate;

public class SailsProject {
    public static final String IMAGES = Sails.DEFAULT_CONTEXT_ROOT_DIRECTORY + "/images";
    public static final String JETTY_BOOT_JAVA = "JettyBoot.java";
    public static final String SCRIPTS = Sails.DEFAULT_CONTEXT_ROOT_DIRECTORY + "/scripts";
    public static final String SRC = "src";
    public static final String SRC_CONFIG = SRC + "/config";
    public static final String SRC_CONTROLLERS = SRC + "/controllers";
    public static final String SRC_MODELS = SRC + "/models";
    public static final String SRC_TESTS = SRC + "/tests";
    public static final String SRC_TESTS_CONTROLLER = SRC_TESTS + "/controller";
    public static final String SRC_TESTS_UNIT = SRC_TESTS + "/unit";
    public static final String STYLES = Sails.DEFAULT_CONTEXT_ROOT_DIRECTORY + "/styles";
    public static final String TOOLS_ECLIPSE = "tools/eclipse";
    public static final String ECLIPSE_CLASSES = TOOLS_ECLIPSE + "/classes";
    public static final String ECLIPSE_LAUNCH = TOOLS_ECLIPSE + "/launch";
    public static final String WEB_INF = Sails.DEFAULT_CONTEXT_ROOT_DIRECTORY + "/WEB-INF";
    public static final String WEB_XML = WEB_INF + "/web.xml";

    protected String applicationName;
    protected String name;
    protected IProject project;
    protected String rootPackage;

    public SailsProject(String name) {
        this.name = name;
    }

    public IProject create() {
        project = ShipyardPlugin.getWorkspace().getRoot().getProject(name);
        if (project.exists()) return project;

        try {
            createProject();
            setupProjectDescription();
            createWarTree(project, applicationName);
            createToolsTree(project);

            IJavaProject javaProject = JavaCore.create(project);
            javaProject.setOption(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_5);
            List<IClasspathEntry> classPathEntrys = new ArrayList<IClasspathEntry>();
            classPathEntrys.add(JavaCore.newContainerEntry(new Path(JavaRuntime.JRE_CONTAINER)));
            classPathEntrys.add(JavaCore.newContainerEntry(new Path(SailsClasspathContainer.ID)));

            IFolder configFolder = createFolder(project, SRC_CONFIG);
            classPathEntrys.add(createSourceEntry(configFolder));

            IFolder controllersFolder = createFolder(project, SRC_CONTROLLERS);
            classPathEntrys.add(createSourceEntry(controllersFolder));

            IFolder modelsFolder = createFolder(project, SRC_MODELS);
            classPathEntrys.add(createSourceEntry(modelsFolder));

            IFolder testsUnitFolder = createFolder(project, SRC_TESTS_UNIT);
            classPathEntrys.add(createSourceEntry(testsUnitFolder));

            IFolder testsControllerFolder = createFolder(project, SRC_TESTS_CONTROLLER);
            classPathEntrys.add(createSourceEntry(testsControllerFolder));

            javaProject.setRawClasspath((IClasspathEntry[]) classPathEntrys.toArray(new IClasspathEntry[classPathEntrys.size()]), null);

            createFileContents(javaProject, configFolder, applicationName, rootPackage);

            javaProject.save(null, true);
        } catch (CoreException e) {
            throw new RuntimeException("Failure creating Sails project", e);
        }
        return project;
    }

    public String getApplicationName() {
        return applicationName;
    }

    /**
     * @return the name of the style sheet for this project
     */
    public String getApplicationStyle() {
        return StringHelper.notCapitalized(getName().replaceAll("\\s", "")) + ".css";
    }

    /**
     * The name of the {@link org.opensails.sails.ISailsApplicationConfigurator}
     * implementation for this project. This does not inlcude the package name.
     * 
     * @return class name, not canonical
     */
    public String getConfiguratorClassName() {
        return StringHelper.capitalized(applicationName.replaceAll("\\s", "").trim()) + "Configurator";
    }

    public String getName() {
        return name;
    }

    public IProject getProject() {
        return project;
    }

    public String getRootPackage() {
        return rootPackage;
    }

    public void setApplicationName(String name) {
        this.applicationName = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRootPackage(String packageRoot) {
        this.rootPackage = packageRoot;
    }

    protected void createFileContents(IJavaProject javaProject, IFolder configSourceFolder, String applicationName, String rootPackageName) throws CoreException,
            JavaModelException {
        IPackageFragmentRoot configPackageRoot = javaProject.getPackageFragmentRoot(configSourceFolder);
        IPackageFragment configRootPackage = configPackageRoot.createPackageFragment(rootPackageName, true, null);

        Binding binding = new Binding();
        binding.put("projectName", javaProject.getProject().getName());
        binding.put("rootPackage", rootPackageName);
        binding.put("configuratorClassName", getConfiguratorClassName());
        binding.put("portNumber", new Integer(1111));
        binding.put("contextRoot", "./war");

        VientoTemplate configuratorTemplate = new VientoTemplate(getClass().getResourceAsStream("configurator.vto"));
        configRootPackage.createCompilationUnit(getConfiguratorClassName() + ".java", configuratorTemplate.render(binding), true, null);

        VientoTemplate jettyBootTemplate = new VientoTemplate(getClass().getResourceAsStream("jettyBoot.vto"));
        configRootPackage.createCompilationUnit(JETTY_BOOT_JAVA, jettyBootTemplate.render(binding), true, null);

        VientoTemplate webXmlTemplate = new VientoTemplate(getClass().getResourceAsStream("web.xml.vto"));
        IFile file = javaProject.getProject().getFile(new Path(WEB_XML));
        file.setContents(new ByteArrayInputStream(webXmlTemplate.render(binding).getBytes()), true, false, null);

        VientoTemplate launchConfig = new VientoTemplate(getClass().getResourceAsStream("eclipse.launch.vto"));
        javaProject.getProject().getFile(new Path(ECLIPSE_LAUNCH + "/" + applicationName + ".launch")).create(new ByteArrayInputStream(launchConfig.render(binding).getBytes()), true, null);
    }

    protected IFolder createFolder(IProject project, String path) {
        IFolder lastFolder = null;
        StringTokenizer eachFolder = new StringTokenizer(path, "/");
        while (eachFolder.hasMoreTokens()) {
            if (lastFolder == null) lastFolder = project.getFolder(eachFolder.nextToken());
            else lastFolder = lastFolder.getFolder(eachFolder.nextToken());
            try {
                if (!lastFolder.exists()) lastFolder.create(true, true, null);
            } catch (CoreException e) {
                throw new RuntimeException("Failed to create folder with path " + path, e);
            }
        }
        return lastFolder;
    }

    protected IClasspathEntry createSourceEntry(IFolder folder) {
        return JavaCore.newSourceEntry(folder.getFullPath());
    }

    protected void createToolsTree(IProject project) {
        createFolder(project, ECLIPSE_CLASSES);
        createFolder(project, ECLIPSE_LAUNCH);
    }

    protected void createWarTree(IProject project, String applicationName) throws CoreException {
        IFolder webInfFolder = createFolder(project, WEB_INF);
        webInfFolder.getFile("web.xml").create(new ByteArrayInputStream(new byte[0]), true, null);

        createFolder(project, IMAGES);
        createFolder(project, SCRIPTS);
        IFolder styleFolder = createFolder(project, STYLES);
        styleFolder.getFile(getApplicationStyle()).create(new ByteArrayInputStream(new byte[0]), true, null);
    }

    private void createProject() throws CoreException {
        project.create(null);
        project.open(null);
    }

    private void setupProjectDescription() throws CoreException {
        IProjectDescription description = project.getDescription();
        description.setNatureIds(new String[] { JavaCore.NATURE_ID, SailsNature.ID });
        project.setDescription(description, null);
    }
}
