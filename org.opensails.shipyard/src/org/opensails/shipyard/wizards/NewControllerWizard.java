package org.opensails.shipyard.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.opensails.shipyard.ShipyardPlugin;
import org.opensails.shipyard.model.SailsNature;

public class NewControllerWizard extends Wizard implements INewWizard {

    protected NewControllerWizardPage page;
    protected SailsNature project;

    public NewControllerWizard() {
        super();
        setWindowTitle("New Controller");
    }
    
    public NewControllerWizard(SailsNature project) {
        this();
        this.project = project;
    }
    
    public boolean performFinish() {
        // TODO: Allow the user to select a project rather than assuming the first one.
        if (project == null)
            project = ShipyardPlugin.getDefault().getSailsModel().getAllSailsProjects()[0];
        project.createController(page.getControllerName(), page.shouldCreateClass(), page.shouldCreateViewFolder()).open();
        return true;
    }

    public void init(IWorkbench workbench, IStructuredSelection selection) {
    }
    
    public void addPages() {
        super.addPages();
        page = new NewControllerWizardPage();
        addPage(page);
    }

    protected class NewControllerWizardPage extends WizardPage {
        protected Text controllerNameText;
        protected Button both;
        protected Button classOnly;
        protected Button folderOnly;

        protected NewControllerWizardPage() {
            super("New Controller");
            setTitle("New Controller");
            setDescription("Create a new Controller.");
        }

        public void createControl(Composite parent) {
            Composite composite = new Composite(parent, SWT.NONE);
            composite.setLayoutData(new GridData(GridData.FILL_BOTH));
            composite.setLayout(new GridLayout(2, false));
            
            new Label(composite, SWT.NONE).setText("Controller Name: ");
            controllerNameText = new Text(composite, SWT.BORDER);
            controllerNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            
            Group group = new Group(composite, SWT.SHADOW_ETCHED_IN);
            group.setText("Resources to create");
            group.setLayout(new GridLayout());
            GridData gridData = new GridData();
            gridData.horizontalSpan = 2;
            group.setLayoutData(gridData);

            both = new Button(group, SWT.RADIO);
            both.setText("Both a Controller class and a template folder");
            both.setSelection(true);
            classOnly = new Button(group, SWT.RADIO);
            classOnly.setText("Only a Controller class");
            folderOnly = new Button(group, SWT.RADIO);
            folderOnly.setText("Only a template folder");
            
            setControl(composite);
        }
        
        public String getControllerName() {
            return controllerNameText.getText();
        }
        
        public boolean shouldCreateClass() {
            return both.getSelection() || classOnly.getSelection();
        }
        
        public boolean shouldCreateViewFolder() {
            return both.getSelection() || folderOnly.getSelection();
        }
    }
}
