package org.opensails.shipyard.views;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;
import org.opensails.shipyard.ShipyardPlugin;
import org.opensails.shipyard.TreeItemContentProvider;
import org.opensails.shipyard.TreeItemLabelProvider;
import org.opensails.shipyard.TreeItemViewSorter;
import org.opensails.shipyard.model.ITreeItem;
import org.opensails.shipyard.model.TreeItemChangeListener;

public class ControllersView extends ViewPart {
    protected TreeViewer treeViewer;
    protected TreeItemChangeListener modelListener;
    
    public ControllersView() {
    }
    
    public void dispose() {
        ShipyardPlugin.getDefault().getSailsModel().removeChangeListener(modelListener);
        super.dispose();
    }

    public void createPartControl(Composite parent) {
        parent.setLayout(new GridLayout());


        treeViewer = new TreeViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        treeViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
        treeViewer.setContentProvider(new TreeItemContentProvider());
        treeViewer.setLabelProvider(new TreeItemLabelProvider());
        treeViewer.setInput(ShipyardPlugin.getDefault().getSailsModel());
        treeViewer.addOpenListener(new IOpenListener() {
            public void open(OpenEvent event) {
                ((ITreeItem) ((StructuredSelection)event.getSelection()).getFirstElement()).open();
            }
        });
        treeViewer.setSorter(new TreeItemViewSorter());
        addContextMenu();
        
        modelListener = new TreeItemChangeListener() {
            public void changed(final ITreeItem item) {
                getSite().getShell().getDisplay().syncExec(new Runnable() {
                    public void run() {
                        treeViewer.refresh(item);
                    }
                });
            }
        };
        ShipyardPlugin.getDefault().getSailsModel().addChangeListener(modelListener);
        
        Button refresh = new Button(parent, SWT.PUSH);
        refresh.setText("Refresh");
        refresh.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                treeViewer.refresh();
            }
        });
    }

    protected void addContextMenu() {
        MenuManager menuManager= new MenuManager("#PopupMenu");
        menuManager.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
        Menu contextMenu= menuManager.createContextMenu(treeViewer.getControl());
        treeViewer.getControl().setMenu(contextMenu);
        getSite().registerContextMenu(menuManager, treeViewer);
    }

    public void setFocus() {
    }
}
