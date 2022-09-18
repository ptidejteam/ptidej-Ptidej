// $Id: ImportSourcesFirstPage 191 2006/08/23 00:00:00 b00__1 Exp $
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

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import org.argouml.argoeclipse.internal.core.model.LRU;

/**
 * Implements radio buttons so the user can choose to import from the
 * filesystem or the workspace. 
 * @author Bogdan Pistol
 */
public class ImportSourcesFirstPage extends WizardPage {
    
    /**
     * The workspace was chosen.
     */
    public static final int WORKSPACE = 1;
    
    /**
     * The filesystem was chosen.
     */
    public static final int FILESYSTEM = 2;
    
    /**
     * The default selection is the workspace.
     */
    private int selection;
    
    private Button workspaceRadio;
    
    private Button filesystemRadio;

    /**
     * Constructor
     */
    public ImportSourcesFirstPage() {
        super("newPage0"); //$NON-NLS-1$
        setTitle(ImportWizardMessages.importSourcesTitle);
        setDescription(ImportWizardMessages.firstPageDescription);
        String s = LRU.get(LRU.IMPORT_SOURCES_CHOOSER);
        if (s.equals("")) { //$NON-NLS-1$
            selection = WORKSPACE;
        } else {
            selection = Integer.parseInt(s);
        }
    }
    
    /*
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        Composite workArea = new Composite(parent, SWT.NONE);
        setControl(workArea);
        workArea.setLayout(new GridLayout());
        workspaceRadio = new Button(workArea, SWT.RADIO);
        workspaceRadio.setText(ImportWizardMessages.workspaceRadio);
        workspaceRadio.setSelection(selection == WORKSPACE);
        workspaceRadio.addSelectionListener(new SelectionAdapter() {
            
            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                selection = WORKSPACE;
            }
            
        });
        filesystemRadio = new Button(workArea, SWT.RADIO);
        filesystemRadio.setText(ImportWizardMessages.filesystemRadio);
        filesystemRadio.setSelection(selection == FILESYSTEM);
        filesystemRadio.addSelectionListener(new SelectionAdapter() {
            
            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                selection = FILESYSTEM;
            }
            
        });
    }

    /**
     * @return Returns the selection.
     */
    public int getSelection() {
        return selection;
    }
    
    /*
     * @see org.eclipse.jface.wizard.WizardPage#isCurrentPage()
     */
    public boolean isCurrentPage() {
        return super.isCurrentPage();
    }
        
}
