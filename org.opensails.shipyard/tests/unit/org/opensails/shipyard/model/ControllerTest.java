package org.opensails.shipyard.model;

import java.util.List;

import junit.framework.TestCase;

import org.opensails.shipyard.shams.ShamFolder;
import org.opensails.shipyard.shams.ShamType;

public class ControllerTest extends TestCase {

    public void testGetName() {
        Controller controller = new Controller(new ShamType("Fred"));
        assertEquals("fred", controller.getName());
        controller.dispose();
        controller = new Controller(new ShamType("FredController"));
        assertEquals("fred", controller.getName());
        controller.setTemplateFolder(new ShamFolder("jack"));
        assertEquals("The class wins", "fred", controller.getName());
        controller.dispose();
        controller = new Controller(new ShamFolder("fred"));
        assertEquals("fred", controller.getName());
        controller.dispose();
    }
    
    public void testGetTemplates() {
        ShamFolder templateFolder = new ShamFolder("fred");
        templateFolder.setFiles(new String[] {"one.vm"});
        Controller controller = new Controller(templateFolder);
        List templates = controller.getTemplates();
        assertEquals(1, templates.size());
        assertEquals("one", ((Template)templates.get(0)).getName());
        
        templateFolder.setFiles(new String[] {"one.vm", "two.vm"});
        controller.refreshTemplates();
        templates = controller.getTemplates();
        assertEquals(2, templates.size());
        assertEquals("one", ((Template)templates.get(0)).getName());
        assertEquals("two", ((Template)templates.get(1)).getName());
    }
    
    public void testGetActions() {
        ShamType type = new ShamType("TestController");
        type.setActions(new String[] {"actionOne"});
        Controller controller = new Controller(type);
        
        List actions = controller.getActions();
        assertEquals(1, actions.size());
        assertEquals("actionOne", ((Action)actions.get(0)).getName());
        
        type.setActions(new String[] {"actionOne", "actionTwo"});
        controller.refreshActions();
        actions = controller.getActions();
        assertEquals(2, actions.size());
        assertEquals("actionOne", ((Action)actions.get(0)).getName());
        assertEquals("actionTwo", ((Action)actions.get(1)).getName());
    }
}
