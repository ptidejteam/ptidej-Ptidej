// $Id: NewFileWizard.java 2006/06/28 00:00:00 b00__1 $
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

package org.argouml.argoeclipse.internal.ui.newwizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.part.FileEditorInput;

import org.argouml.argoeclipse.internal.core.model.ActionsListener;
import org.argouml.argoeclipse.internal.core.model.ArgoProject;
import org.argouml.argoeclipse.internal.core.model.Register;
import org.argouml.argoeclipse.internal.ui.editor.DiagramEditor;
import org.argouml.argoeclipse.internal.ui.model.Actions;
import org.argouml.argoeclipse.internal.ui.model.InitUI;
import org.argouml.argoeclipse.internal.ui.util.HybridAction;

/**
 * This class deals with the new wizard of argouml.
 * @author Bogdan Pistol
 */
public class NewFileWizard extends Wizard implements INewWizard {
    
    private static final String[] EXTENSIONS = 
        new String[] {".zargo", //$NON-NLS-1$
                      ".uml", //$NON-NLS-1$
                      ".zip"}; //$NON-NLS-1$
    
    /**
     * A standard new file wizard for resources.
     */
    private WizardNewFileCreationPage page;
    
    /**
     * This contains the selected projects in the eclipse explorer when
     * the new wizard is opened. 
     */
    private IStructuredSelection selection;

    /**
     * The workbench window.
     */
    private IWorkbenchWindow window;

    /*
     * @see org.eclipse.jface.wizard.IWizard#addPages()
     */
    public void addPages() {
        super.addPages();
        setWindowTitle(NewWizardMessages.wizardTitle);
        page = new WizardNewFileCreationPage(
                "newPage", selection); //$NON-NLS-1$
        page.setTitle(NewWizardMessages.wizardTitle);
        page.setDescription(NewWizardMessages.wizardDescription);        
        addPage(page);
    }
        
    /*
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    public boolean performFinish() {
        String filename = page.getFileName();
        boolean ok = false;
        for (int i = 0; i < EXTENSIONS.length; i++) {
            if (filename.toLowerCase().endsWith(EXTENSIONS[i])) {
                ok = true;
                break;
            }
        }
        if (!ok) {
            page.setFileName(filename + EXTENSIONS[0]);
            if (page.getErrorMessage() != null) {
                return false;
            }
        }
        
        // the new wizard can be used anywhere, so we must be sure it's init
        InitUI.initialize();
        
        if (Register.getInstance().isRegistered(Register.EDITOR)
                && ((Boolean) ActionsListener.getInstance().getEvent(
                        ActionsListener.SAVE)).booleanValue()
                && !MessageDialog.openConfirm(null,
                        NewWizardMessages.confirmOverwiteTitle,
                        NewWizardMessages.confirmOverwiteDescription)) {
            return false;
        }
        ArgoProject.closeProject();
        
        IFile file = page.createNewFile();
        ((HybridAction) Actions.getInstance().getActionSave())
                .getSwingAction().setEnabled(false);
        Actions.getInstance().getActionNew().run();
        
        FileEditorInput input = new FileEditorInput(file);
        ArgoProject.saveProject(input.getPath().toOSString());
        try {
            file.refreshLocal(IResource.DEPTH_ZERO, null);
        } catch (CoreException e) {
            e.printStackTrace();
        }

        IWorkbenchPage activePage = window.getActivePage();
        
        if (Register.getInstance().isRegistered(Register.EDITOR)) {
            DiagramEditor ed = (DiagramEditor) Register.getInstance()
                .getRegistered(Register.EDITOR);
            ed.setPartName(input.getName());
            ed.setInput(input);
        } else {
            try {
                activePage.openEditor(input,
                        "org.argouml.argoeclipse.internal.ui" //$NON-NLS-1$
                        + ".editor.DiagramEditor"); //$NON-NLS-1$
            } catch (PartInitException e) {
                e.printStackTrace();
            }
        }
        
        return true;
    }

    /*
     * @see org.eclipse.ui.IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
     */
    public void init(IWorkbench workbench, IStructuredSelection sel) {
        window = workbench.getActiveWorkbenchWindow(); 
        selection = sel;
    }

    /**
     * Disposer.
     */
    public void dispose() {
        window = null;
    }       

}
