package org.opensails.shipyard.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.opensails.viento.Binding;
import org.opensails.viento.VientoTemplate;

public class SailsProjectTest extends TestCase {
    private IProject project;
    private SailsProject sailsProject;

    public void testCreate() throws Exception {
        sailsProject = new SailsProject("myTestProject");
        sailsProject.setRootPackage("a.b");
        sailsProject.setApplicationName("appName");
        project = sailsProject.create();
        checkNewProject();

        sailsProject = new SailsProject("my TestProject");
        sailsProject.setRootPackage("a.b");
        sailsProject.setApplicationName("app Name");
        project = sailsProject.create();
        checkNewProject();
    }

    private void checkConfiguratorClass() {
        Binding binding = new Binding();
        binding.put("packageDeclaration", sailsProject.getRootPackage());
        binding.put("configuratorClassName", sailsProject.getConfiguratorClassName());
        VientoTemplate template = new VientoTemplate(getClass().getResourceAsStream("configuratorExpectation"));
        String path = configPackagePath() + "/" + sailsProject.getConfiguratorClassName() + ".java";
        assertEquals(template.render(binding), readContents(path));
    }

    private void checkJettyBootClass() {
        Binding binding = new Binding();
        binding.put("packageDeclaration", sailsProject.getRootPackage());
        VientoTemplate template = new VientoTemplate(getClass().getResourceAsStream("jettyBootExpectation"));
        String path = configPackagePath() + "/" + SailsProject.JETTY_BOOT_JAVA;
        assertEquals(template.render(binding), readContents(path));
    }

    private void checkNatures() throws CoreException {
        assertTrue(project.hasNature(SailsNature.ID));
    }

    private void checkNewProject() throws CoreException {
        checkNatures();
        checkStructure();
        checkConfiguratorClass();
        checkJettyBootClass();
        checkWebXml();
    }

    private void checkStructure() {
        assertTrue(project.getFile(SailsProject.WEB_XML).exists());
        assertTrue(project.getFolder(SailsProject.IMAGES).exists());
        assertTrue(project.getFolder(SailsProject.SCRIPTS).exists());
        assertTrue(project.getFolder(SailsProject.STYLES).exists());
        assertTrue(project.getFile(SailsProject.STYLES + "/" + sailsProject.getApplicationStyle()).exists());

        assertTrue(project.getFile(configPackagePath() + "/" + sailsProject.getConfiguratorClassName() + ".java").exists());
        assertTrue(project.getFile(configPackagePath() + "/JettyBoot.java").exists());
        assertTrue(project.getFolder(SailsProject.SRC_CONTROLLERS).exists());
        assertTrue(project.getFolder(SailsProject.SRC_MODELS).exists());
        assertTrue(project.getFolder(SailsProject.SRC_TESTS_UNIT).exists());
        assertTrue(project.getFolder(SailsProject.SRC_TESTS_CONTROLLER).exists());

        assertTrue(project.getFolder(SailsProject.ECLIPSE_CLASSES).exists());
        assertTrue(project.getFolder(SailsProject.ECLIPSE_LAUNCH).exists());
    }

    private void checkWebXml() {
        Binding binding = new Binding();
        binding.put("projectName", sailsProject.getName());
        binding.put("canonicalConfiguratorClassName", sailsProject.getRootPackage() + "." + sailsProject.getConfiguratorClassName());
        VientoTemplate template = new VientoTemplate(getClass().getResourceAsStream("webXmlExpectation"));
        assertEquals(template.render(binding), readContents(SailsProject.WEB_XML));
    }

    private String configPackagePath() {
        return SailsProject.SRC_CONFIG + "/" + sailsProject.getRootPackage().replace('.', '/');
    }

    private String readContents(String path) {
        IFile file = project.getFile(new Path(path));
        InputStream input = null;
        try {
            input = file.getContents();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead = -1;
            while ((bytesRead = input.read(buffer)) != -1)
                out.write(buffer, 0, bytesRead);
            input.close();
            return out.toString().trim();
        } catch (Exception e) {
            throw new RuntimeException("Couldn't read file");
        } finally {
            if (input != null) try {
                input.close();
            } catch (IOException e) {
                throw new RuntimeException("Could close the stream", e);
            }
        }
    }
}
