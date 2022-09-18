// $Id: ImportSourcesPageFilesystem 191 2006/08/23 00:00:00 b00__1 Exp $
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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import org.argouml.argoeclipse.internal.core.imports.ImportSources;
import org.argouml.argoeclipse.internal.core.model.LRU;

/**
 * This class is responsible with the filesystem behavior of the ImportWizard.
 * @author Bogdan Pistol
 */
public class ImportSourcesPageFilesystem extends ImportSourcesPage {
    
    /**
     * Constructor
     */
    public ImportSourcesPageFilesystem() {
        setDescription(ImportWizardMessages.importFilesystemDescription);
    }
    
    /*
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        Composite workArea = createWorkArea(parent);
        
        Composite buttons = new Composite(workArea, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        buttons.setLayout(layout);
        buttons.setLayoutData(new GridData(SWT.RIGHT, SWT.NONE, false, false));
        
        Button browseFiles = new Button(buttons, SWT.PUSH);
        browseFiles.setText(ImportWizardMessages.browseFiles);        
        browseFiles.setLayoutData(
                new GridData(SWT.FILL, SWT.NONE, true, false));
        browseFiles.addSelectionListener(new SelectionAdapter() {
            
            public void widgetSelected(SelectionEvent e) {
                handleBrowseFiles();
                validate();
            }
            
        });
        Button browseFolders = new Button(buttons, SWT.PUSH);
        browseFolders.setText(ImportWizardMessages.browseFolders);        
        browseFolders.setLayoutData(
                new GridData(SWT.FILL, SWT.NONE, true, false));
        browseFolders.addSelectionListener(new SelectionAdapter() {
            
            public void widgetSelected(SelectionEvent e) {                
                handleBrowseFolders();
                validate();
            }
            
        });
    }
    
    private void handleBrowseFiles() {
        FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
        dialog.setText(ImportWizardMessages.browseFilesTitle);
        String s = LRU.get(LRU.IMPORT_SOURCES_FILESYSTEM_PATH);
        dialog.setFilterPath(s.equals("") ? "/" : s); //$NON-NLS-1$//$NON-NLS-2$
        String result = dialog.open();
        if (result == null) {
            return;
        }
        setPath(result);
    }
    
    private void handleBrowseFolders() {
        DirectoryDialog dialog = new DirectoryDialog(getShell());
        dialog.setText(ImportWizardMessages.browseFoldersTitle);
        String s = LRU.get(LRU.IMPORT_SOURCES_FILESYSTEM_PATH);
        dialog.setFilterPath(s.equals("") ? "/"  //$NON-NLS-1$//$NON-NLS-2$
                : s.substring(0, s.lastIndexOf("/"))); //$NON-NLS-1$
        String result = dialog.open();
        if (result == null) {
            return;
        }
        setPath(result);
    }

    /*
     * @see org.argouml.argoeclipse.internal.ui.util.importwizard.ImportSourcesPage#createClassPathAddRemoveButtons()
     */
    protected void createClassPathAddRemoveButtons(Composite parent) {
        GridLayout buttonsLayout = new GridLayout();
        buttonsLayout.numColumns = 2;
        buttonsLayout.makeColumnsEqualWidth = true;
        parent.setLayout(buttonsLayout);
        Button addFile = new Button(parent, SWT.PUSH);
        addFile.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
        addFile.setText(ImportWizardMessages.addFilePath);
        addFile.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                handleAddFilePath();
            }
            
        });
        Button remove = new Button(parent, SWT.PUSH);
        remove.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
        configureClassPathRemoveButton(remove);
        Button addFolder = new Button(parent, SWT.PUSH);
        addFolder.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
        addFolder.setText(ImportWizardMessages.addFolderPath);
        addFolder.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                handleAddFolderPath();
            }
            
        });
    }
    
    private void handleAddFilePath() {
        FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
        dialog.setText(ImportWizardMessages.setClasspath);
        String s = LRU.get(LRU.IMPORT_SOURCES_FILESYSTEM_PATH);
        dialog.setFilterPath(s.equals("") ? "/" : s); //$NON-NLS-1$//$NON-NLS-2$
        String result = dialog.open();
        if (result == null) {
            return;
        }
        addClassPath(result);
    }
    
    private void handleAddFolderPath() {
        DirectoryDialog dialog = new DirectoryDialog(getShell());
        dialog.setText(ImportWizardMessages.setClasspath);
        String s = LRU.get(LRU.IMPORT_SOURCES_FILESYSTEM_PATH);
        dialog.setFilterPath(s.equals("") ? "/" //$NON-NLS-1$ //$NON-NLS-2$
                : s.substring(0, s.lastIndexOf("/"))); //$NON-NLS-1$
        String result = dialog.open();
        if (result == null) {
            return;
        }
        addClassPath(result);
    }

    /*
     * @see org.argouml.argoeclipse.internal.ui.util.importwizard.ImportSourcesPage#fillClassPathList(org.eclipse.swt.widgets.Table)
     */
    protected void fillClassPathList(Table classpathTable) {
        ListIterator it = ImportSources.getInstance().getImportClasspath()
            .listIterator();
        while (it.hasNext()) {
            new TableItem(classpathTable, SWT.NONE).setText((String) it.next());
        }
    }

    /*
     * @see org.argouml.argoeclipse.internal.ui.util.importwizard.ImportSourcesPage#getClassPath(org.eclipse.swt.widgets.Table)
     */
    protected List getClassPath(Table classpathTable) {
        List list = new ArrayList();        
        for (int i = 0; i < classpathTable.getItemCount(); i++) {
            list.add(classpathTable.getItem(i).getText());
        }
        return list;
    }
    
}
