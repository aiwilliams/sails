package org.opensails.shipyard;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.internal.ui.wizards.AbstractOpenWizardAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.opensails.shipyard.model.SailsNature;
import org.opensails.shipyard.wizards.NewControllerWizard;

public class NewControllerAction extends AbstractOpenWizardAction implements IObjectActionDelegate {

    protected IWorkbenchPart targetPart;

    public NewControllerAction() {
        super();
    }

    protected Wizard createWizard() throws CoreException {
        return new NewControllerWizard();
    }

    protected SailsNature getSelectedProject() {
        // FIXME
        return (SailsNature) ((IStructuredSelection)targetPart.getSite().getSelectionProvider().getSelection()).getFirstElement();
    }

    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        this.targetPart = targetPart;
    }
}
