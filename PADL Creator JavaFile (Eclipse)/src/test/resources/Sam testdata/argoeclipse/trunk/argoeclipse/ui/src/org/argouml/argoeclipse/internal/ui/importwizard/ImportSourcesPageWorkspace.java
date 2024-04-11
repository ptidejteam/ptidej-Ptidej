// $Id: ImportSourcesPageWorkspace 191 2006/08/23 00:00:00 b00__1 Exp $
// Copyright (c) 2006 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies. This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason. IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.argoeclipse.internal.ui.importwizard;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.dialogs.ResourceListSelectionDialog;

import org.argouml.argoeclipse.internal.core.imports.ImportSources;
import org.argouml.argoeclipse.internal.core.util.ResourcePathTranslator;

/**
 * This class is responsible with the workspace behavior of the ImportWizard.
 * @author Bogdan Pistol
 */
public class ImportSourcesPageWorkspace extends ImportSourcesPage {    

    /**
     * Constructor
     */
    public ImportSourcesPageWorkspace() {
        setDescription(ImportWizardMessages.importWorkspaceDescription);        
    }
    
    /*
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        Composite workArea = createWorkArea(parent);
        Button browser = new Button(workArea, SWT.PUSH);
        browser.setText(ImportWizardMessages.browseButton);
        browser.setLayoutData(new GridData(SWT.RIGHT, SWT.NONE, false, false));
        browser.addSelectionListener(new SelectionAdapter() {
            
            public void widgetSelected(SelectionEvent e) {                
                handleBrowser();
                validate();
            }
            
        });
    }
    
    private void handleBrowser() {
        ResourceListSelectionDialog dialog = new ResourceListSelectionDialog(
                getShell(), ResourcesPlugin.getWorkspace().getRoot(),
                IResource.FILE | IResource.FOLDER | IResource.PROJECT);        
        if (dialog.open() == Window.OK) {
            Object[] result = dialog.getResult();
            if (result.length == 1) {
                setPath(((IResource) result[0]).getFullPath().toOSString());
            }
        }
    }

    /*
     * @see org.argouml.argoeclipse.internal.ui.util.importwizard.ImportSourcesPage#createClassPathAddRemoveButtons(org.eclipse.swt.widgets.Composite)
     */
    protected void createClassPathAddRemoveButtons(Composite parent) {
        GridLayout buttonsLayout = new GridLayout();
        buttonsLayout.numColumns = 2;
        buttonsLayout.makeColumnsEqualWidth = true;
        parent.setLayout(buttonsLayout);
        Button addPath = new Button(parent, SWT.PUSH);
        addPath.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
        addPath.setText(ImportWizardMessages.addPathButton);
        addPath.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                handleAddPath();
            }
            
        });
        Button remove = new Button(parent, SWT.PUSH);
        remove.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
        configureClassPathRemoveButton(remove);        
    }   
    
    private void handleAddPath() {
        ResourceListSelectionDialog dialog = new ResourceListSelectionDialog(
                getShell(), ResourcesPlugin.getWorkspace().getRoot(),
                IResource.FILE | IResource.FOLDER | IResource.PROJECT);
        if (dialog.open() == Window.OK) {
            Object[] result = dialog.getResult();
            if (result.length == 1) {
                addClassPath(((IResource) result[0]).getFullPath()
                        .toOSString());
            }
        }
    }
    
    /*
     * @see org.argouml.argoeclipse.internal.ui.util.importwizard.ImportSourcesPage#fillClassPathList(org.eclipse.swt.widgets.Table)
     */
    protected void fillClassPathList(Table classpathTable) {
        ListIterator it = 
            ImportSources.getInstance().getImportClasspath().listIterator();
        while (it.hasNext()) {
            String path = (String) it.next();
            String workspacePath = 
                ResourcePathTranslator.getWorkspacePath(path);
            if (workspacePath == null) {
                // TODO: Do we just want to skip classpath items which don't
                // map into the workspace? - tfm
                new TableItem(classpathTable, SWT.NONE).setText(path);
            } else {
                new TableItem(classpathTable, SWT.NONE).setText(workspacePath);
            }
        }
    }

    /*
     * Converts workspace paths into filesystem paths.
     * 
     * @see org.argouml.argoeclipse.internal.ui.util.importwizard.ImportSourcesPage#getClassPath(org.eclipse.swt.widgets.Table)
     */
    protected List getClassPath(Table classpathTable) {
        List list = new ArrayList();        
        for (int i = 0; i < classpathTable.getItemCount(); i++) {
            list.add(ResourcePathTranslator.getFilesystemPath(
                    classpathTable.getItem(i).getText()));
        }
        return list;
    }
    
}
