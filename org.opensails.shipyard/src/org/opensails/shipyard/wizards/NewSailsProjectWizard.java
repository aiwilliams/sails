package org.opensails.shipyard.wizards;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.opensails.shipyard.ShipyardPlugin;
import org.opensails.shipyard.model.SailsProject;

public class NewSailsProjectWizard extends Wizard implements INewWizard {
    protected SailsProject newProject;
    protected NewSailsProjectWizardPage newSailsProjectWizardPage;

    public NewSailsProjectWizard() {
        super();
        newProject = new SailsProject("Sailing");
        setWindowTitle("New Sails Project");
    }

    public void addPages() {
        super.addPages();
        newSailsProjectWizardPage = new NewSailsProjectWizardPage(newProject);
        addPage(newSailsProjectWizardPage);
    }

    public void init(IWorkbench workbench, IStructuredSelection selection) {}

    public boolean performFinish() {
        newSailsProjectWizardPage.updateProjectSettings();
        newProject.create();
        return true;
    }

    protected static class NewSailsProjectWizardPage extends WizardPage {
        private boolean customizedApplicationName;
        private boolean customizedPackage;
        protected Text applicationNameText;
        protected final SailsProject project;
        protected Text projectNameText;
        protected Text rootPackageText;

        protected NewSailsProjectWizardPage(SailsProject project) {
            super("New Sails Project");
            setTitle("New Sails Project");
            setDescription("Create a new Sails project.");
            this.project = project;
        }

        public void createControl(Composite parent) {
            Composite composite = new Composite(parent, SWT.NONE);
            composite.setLayoutData(new GridData(GridData.FILL_BOTH));
            composite.setLayout(new GridLayout(2, false));

            new Label(composite, SWT.NONE).setText("Project Name: ");
            projectNameText = new Text(composite, SWT.BORDER);
            projectNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            new Label(composite, SWT.NONE).setText("Root Package: ");
            rootPackageText = new Text(composite, SWT.BORDER);
            rootPackageText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            new Label(composite, SWT.NONE).setText("Application Name: ");
            applicationNameText = new Text(composite, SWT.BORDER);
            applicationNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            setControl(composite);
            addListeners();
        }

        public String getApplicationNameGuess() {
            return projectNameText.getText();
        }

        public String getRootPackageGuess() {
            return projectNameText.getText() == null ? null : projectNameText.getText().trim().toLowerCase().replaceAll("\\s", ".");
        }

        public boolean isPageComplete() {
            IStatus nameStatus = ShipyardPlugin.getWorkspace().validateName(projectNameText.getText(), IResource.PROJECT);
            if (!nameStatus.isOK()) {
                setErrorMessage(nameStatus.getMessage());
                return false;
            }
            setErrorMessage(null);
            return this.rootPackageText.getText() != null && this.applicationNameText.getText() != null;
        }

        protected void addListeners() {
            projectNameText.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    if (!customizedPackage) rootPackageText.setText(getRootPackageGuess());
                    if (!customizedApplicationName) applicationNameText.setText(getApplicationNameGuess());
                }
            });
            rootPackageText.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    customizedPackage = true;
                }
            });
            applicationNameText.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    customizedApplicationName = true;
                }
            });

            KeyAdapter buttonUpdater = new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    getContainer().updateButtons();
                }
            };
            projectNameText.addKeyListener(buttonUpdater);
            rootPackageText.addKeyListener(buttonUpdater);
            applicationNameText.addKeyListener(buttonUpdater);
        }

        protected void updateProjectSettings() {
            project.setName(projectNameText.getText());
            project.setRootPackage(rootPackageText.getText());
            project.setApplicationName(applicationNameText.getText());
        }
    }
}
