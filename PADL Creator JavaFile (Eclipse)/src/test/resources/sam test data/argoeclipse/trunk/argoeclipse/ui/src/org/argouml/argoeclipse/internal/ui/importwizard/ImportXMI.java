// $Id: ImportXMI.java 2006/08/16 00:00:00 b00__1 $
// Copyright (c) 2006 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
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

import java.beans.PropertyChangeEvent;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import org.argouml.argoeclipse.internal.core.model.ActionsListener;
import org.argouml.argoeclipse.internal.core.model.ArgoProject;
import org.argouml.argoeclipse.internal.core.model.Register;
import org.argouml.argoeclipse.internal.ui.editor.DiagramEditor;
import org.argouml.argoeclipse.internal.ui.wizard.ExternalResourcePage;

/**
 * Implementation of Import XMI Wizard.
 * @author Bogdan Pistol
 */
public class ImportXMI extends Wizard implements IImportWizard {

    /**
     * The wizard page.
     */
    private ExternalResourcePage page;
    
    private static final String EXTENSION = "xmi"; //$NON-NLS-1$
    
    /*
     * @see org.eclipse.jface.wizard.Wizard#addPage(org.eclipse.jface.wizard.IWizardPage)
     */
    public void addPages() {
        if (!Register.getInstance().isRegistered(Register.EDITOR)) {
            MessageDialog.openError(getShell(),
                    ImportWizardMessages.editorWarningTitle,
                    ImportWizardMessages.editorWarningMsg);
            return;            
        }
        page = new ExternalResourcePage(ImportWizardMessages.importXMITitle,
                ImportWizardMessages.importXMIDescription,
                new String[] {EXTENSION}, ExternalResourcePage.IMPORT_FILE);
        addPage(page);
    }
    
    /*
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    public boolean performFinish() {
        String result = page.getResourcePath();
        if (result == null) {
            return false;
        }
        if (((Boolean)ActionsListener.getInstance().getEvent(
                ActionsListener.SAVE)).booleanValue()
                && !MessageDialog.openConfirm(getShell(),
                        ImportWizardMessages.confirmOverwiteTitle,
                        ImportWizardMessages.confirmOverwiteDescription)) {
            return false;
        }
        
        ArgoProject.closeProject();
        EditorPart ed = (EditorPart) Register.getInstance().getRegistered(
                Register.EDITOR);
        try {
            ed.init(ed.getEditorSite(),
                    new IEditorInput() {

                        public boolean exists() {
                            return false;
                        }

                        public ImageDescriptor getImageDescriptor() {
                            return null;
                        }

                        public String getName() {
                            return null;
                        }

                        public IPersistableElement getPersistable() {
                            return null;
                        }

                        public String getToolTipText() {
                            return null;
                        }

                        public Object getAdapter(Class adapter) {
                            return null;
                        }

                    });
        } catch (PartInitException e) {
        }
        ArgoProject.loadProject(result);
        ((DiagramEditor) ed).setPartName(new Path(result).lastSegment());
        ActionsListener.getInstance().setEvent(ActionsListener.SAVE,
                Boolean.valueOf(true));
        return true;
    }
    
    /*
     * @see org.eclipse.ui.IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {                
        setWindowTitle(ImportWizardMessages.genericImport);
    }

}
