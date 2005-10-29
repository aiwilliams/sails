package org.opensails.shipyard.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.opensails.shipyard.ShipyardPlugin;
import org.opensails.shipyard.TreeItemLabelProvider;
import org.opensails.shipyard.TreeItemLeafContentProvider;
import org.opensails.shipyard.model.Controller;
import org.opensails.shipyard.model.SailsNature;

public class OpenSailsResourceSelectionDialog implements IWorkbenchWindowActionDelegate {

    private IWorkbenchWindow window;

    public void dispose() {
    }

    public void init(IWorkbenchWindow window) {
        this.window = window;
    }

    public void run(IAction action) {
        SailsResourceSelectionDialog dialog = new SailsResourceSelectionDialog(window.getShell());
        dialog.open();
    }

    public void selectionChanged(IAction action, ISelection selection) {
    }
    
    public class SailsResourceSelectionDialog extends Dialog {
        protected ListViewer listViewer;
        protected Text searchBox;
        protected List list = new ArrayList();

        public SailsResourceSelectionDialog(Shell parentShell) {
            super(parentShell);
        }
        
        protected void configureShell(Shell newShell) {
            super.configureShell(newShell);
            newShell.setText("Open Sails Resource");
        }
        
        protected Control createDialogArea(Composite parent) {
            Composite composite = (Composite) super.createDialogArea(parent);
            composite.setLayout(new GridLayout());
            
            searchBox = new Text(composite, SWT.BORDER);
            searchBox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            
            listViewer = new ListViewer(composite);
            listViewer.getList().setLayoutData(new GridData(GridData.FILL_BOTH));
            listViewer.setContentProvider(new TreeItemLeafContentProvider());
            listViewer.setLabelProvider(new TreeItemLabelProvider());
            listViewer.setInput(ShipyardPlugin.getDefault().getSailsModel());
            listViewer.refresh();
            
            return composite;
        }
    }
}
