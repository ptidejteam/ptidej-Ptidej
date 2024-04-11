// $Id: ImportSourcesWizard.java 2006/08/17 00:00:00 b00__1 $
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

import java.io.File;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import org.argouml.argoeclipse.internal.core.model.LRU;
import org.argouml.argoeclipse.internal.core.model.Register;
import org.argouml.argoeclipse.internal.core.util.ResourcePathTranslator;

/**
 * Responsible for the Import Sources Wizard.
 * @author Bogdan Pistol
 */
public class ImportSourcesWizard extends Wizard implements IImportWizard {
    
    private ImportSourcesFirstPage firstPage;
    
    private ImportSourcesPageWorkspace workspacePage;
    
    private ImportSourcesPageFilesystem filesystemPage;
    
    
    
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
        firstPage = new ImportSourcesFirstPage();
        addPage(firstPage);
        workspacePage = new ImportSourcesPageWorkspace();
        addPage(workspacePage);
        filesystemPage = new ImportSourcesPageFilesystem();
        addPage(filesystemPage);
    }    

    /*
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     *      org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        setWindowTitle(ImportWizardMessages.genericImport);
    }

    /*
     * @see org.eclipse.jface.wizard.Wizard#getNextPage(org.eclipse.jface.wizard.IWizardPage)
     */
    public IWizardPage getNextPage(IWizardPage page) {
        if (page.equals(workspacePage) || page.equals(filesystemPage)) {
            return null;
        }
        if (firstPage.getSelection() 
                == ImportSourcesFirstPage.WORKSPACE) {
            return workspacePage;
        } else {
            return filesystemPage;
        }        
    }

    /*
     * @see org.eclipse.jface.wizard.Wizard#getPreviousPage(org.eclipse.jface.wizard.IWizardPage)
     */
    public IWizardPage getPreviousPage(IWizardPage page) {
        return page.equals(firstPage) ? null : firstPage;
    }

    /*
     * @see org.eclipse.jface.wizard.Wizard#canFinish()
     */
    public boolean canFinish() {
        if (firstPage.isCurrentPage()) {
            return false;
        }
        if (workspacePage.isCurrentPage()) {
            return workspacePage.isPageComplete();
        }
        return filesystemPage.isPageComplete();
    }
    
    /*
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    public boolean performFinish() {
        String source = null;
        ImportSourcesPage page;
        if (filesystemPage.isCurrentPage()) {
            File file = new File(filesystemPage.getPath());            
            if (file.isAbsolute() && file.exists()) {
                source = file.getPath();
            }
            page = filesystemPage;
        } else {
            source = ResourcePathTranslator.getFilesystemPath(
                    workspacePage.getPath());
            page = workspacePage;
        }
        
        if (source == null) {
            page.setErrorMessage(ImportWizardMessages.pathError);
            return false;
        }
        if (filesystemPage.isCurrentPage()) {
            LRU.put(LRU.IMPORT_SOURCES_FILESYSTEM_PATH, source);
        }
        LRU.put(LRU.IMPORT_SOURCES_CHOOSER, Integer.toString(firstPage
                .getSelection()));
        
        return page.performFinish(source);        
                
    }
    
}
