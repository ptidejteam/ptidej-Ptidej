// $Id: ExportGraphics.java 111 2006/08/09 00:00:00 b00__1 $
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

package org.argouml.argoeclipse.internal.ui.exportwizard;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import org.argouml.argoeclipse.internal.core.model.Register;
import org.argouml.argoeclipse.internal.ui.model.Actions;
import org.argouml.argoeclipse.internal.ui.util.HybridAction;
import org.argouml.argoeclipse.internal.ui.wizard.ExternalResourcePage;
import org.argouml.uml.ui.ActionSaveGraphics;

/**
 * Deals with the export Graphics wizard.
 * @author Bogdan Pistol 
 */
public class ExportGraphics extends Wizard implements IExportWizard {
    
    static final String[] FILE_EXTENSIONS = new String[] {"png",  //$NON-NLS-1$
                                                          "gif",  //$NON-NLS-1$
                                                          "svg",  //$NON-NLS-1$
                                                          "ps",  //$NON-NLS-1$
                                                          "eps"  //$NON-NLS-1$
    };

    /**
     * The wizard page.
     */
    private ExternalResourcePage page;
     
    /*
     * @see org.eclipse.jface.wizard.Wizard#addPage(org.eclipse.jface.wizard.IWizardPage)
     */
    public void addPages() {
        if (!verifyProjectOpen()) {
            return;
        }
        page = new ExternalResourcePage(
                ExportWizardMessages.exportGraphicsTitle,
                ExportWizardMessages.exportGraphicsDescription, 
                FILE_EXTENSIONS,
                ExternalResourcePage.EXPORT_FILE);
        addPage(page);
    }

    /**
     * Verifies if the diagram editor is opened.
     * @return true for opened and false otherwise
     */
    protected boolean verifyProjectOpen() {
        if (!Register.getInstance().isRegistered(Register.EDITOR)) {
            MessageDialog.openError(getShell(),
                    ExportWizardMessages.diagramWarningTitle,
                    ExportWizardMessages.diagramWarningDescription);
            return false;
        } else {
            return true;
        }
    }

    /*
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    public boolean performFinish() {       
        String result = page.getResourcePath();
        if (result == null) {
            return false;
        }
        HybridAction action =
                (HybridAction) Actions.getInstance().getActionExportGraphics();
        ((ActionSaveGraphics) action.getSwingAction()).doCommand(result);
        page.refreshResource();
        return true;
    }

    /*
     * @see org.eclipse.ui.IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        setWindowTitle(ExportWizardMessages.exportWizardTitle);        
    }

}
