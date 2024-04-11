// $Id: DiagramEditor.java 2006/06/22 00:00:00 b00__1 $
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

package org.argouml.argoeclipse.internal.ui.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.progress.WorkbenchJob;

import org.argouml.argoeclipse.internal.core.model.ActionsListener;
import org.argouml.argoeclipse.internal.core.model.ArgoProject;
import org.argouml.argoeclipse.internal.core.model.Register;
import org.argouml.argoeclipse.internal.ui.actionSet.PulldownMenu;
import org.argouml.argoeclipse.internal.ui.model.InitUI;
import org.argouml.argoeclipse.internal.ui.model.Panels;
import org.argouml.argoeclipse.internal.ui.util.SwingWrapper;

/**
 * Deals with the diagram editor.
 * 
 * @author Bogdan Pistol
 */
public class DiagramEditor extends EditorPart {

    /**
     * If it's dirty or not.
     */
    private boolean dirtyState;
    
    private PropertyChangeListener saveListener;
    
    /**
     * Constructor
     */
    public DiagramEditor() {
        // if we don't init here, it's possible that createPartControl will not
        // be the first method call, and we'll perform operations uninitialized
        InitUI.initialize();
    }

    /*
     * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
     */
    public void doSave(IProgressMonitor monitor) {
        if (!Register.getInstance().isRegistered(Register.EDITOR, this)) {
            return;
        }
        if (ArgoProject.isSaveAsRequired()) {
            doSaveAs();
        } else {
            ArgoProject.saveProject();
        }
    }

    /*
     * @see org.eclipse.ui.part.EditorPart#doSaveAs()
     */
    public void doSaveAs() {
        if (!Register.getInstance().isRegistered(Register.EDITOR, this)) {
            return;
        }
        
        Shell shell = getSite().getShell();
        IEditorInput input = null;
        if (ArgoProject.getProjectPath() != null) {
            input = getEditorInput();
        }
        
        SaveAsDialog dialog = new SaveAsDialog(shell);
        if (input != null) {
            IFile original = (input instanceof IFileEditorInput) 
                ? ((IFileEditorInput) input).getFile() : null;
            if (original != null) {
                dialog.setOriginalFile(original);
            }
        }
        
        dialog.create();
        if (dialog.open() == Window.CANCEL) {
            return;
        }
        
        IPath filePath = dialog.getResult();
        if (filePath == null) {
            return;
        }
        String ext = filePath.getFileExtension();
        
        boolean addExt = false;
        if (ext == null) {
            addExt = true;
        }
        if (!ext.toLowerCase().equals("zargo") //$NON-NLS-1$
                && !ext.toLowerCase().equals("uml") //$NON-NLS-1$
                && !ext.toLowerCase().equals("zip")) { //$NON-NLS-1$
            addExt = true;
        }
        if (addExt) {
            filePath = filePath.removeFileExtension();
            filePath = filePath.addFileExtension("zargo"); //$NON-NLS-1$
        }
        
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IFile file = workspace.getRoot().getFile(filePath);
        try {
            file.create(null, IResource.NONE, null);            
        } catch (CoreException e) {
            MessageDialog.openError(shell, EditorMessages.warningTitle,
                    EditorMessages.warningMsg);
            return;
        }
        
        ArgoProject.saveProject(file.getLocation().toOSString());
        
        try {
            file.refreshLocal(IResource.DEPTH_ZERO, null);
        } catch (CoreException e) {
            e.printStackTrace();
        }
        
        setPartName(file.getName());            
        setInput(new FileEditorInput(file));
    }

    /*
     * @see org.eclipse.ui.part.EditorPart#init(org.eclipse.ui.IEditorSite,
     *      org.eclipse.ui.IEditorInput)
     */
    public void init(IEditorSite site, IEditorInput input)
        throws PartInitException {
        setSite(site);
        setInput(input);
        if (Register.getInstance().isRegistered(Register.EDITOR)) {
            return;
        }
        
        if (input instanceof IPathEditorInput) {
            IPathEditorInput in = (IPathEditorInput) input;
            ArgoProject.loadProject(in.getPath().toOSString());
            setPartName(input.getName());
        }
        Register.getInstance().register(Register.EDITOR, this);
        WorkbenchJob job = new WorkbenchJob("actionSet") { //$NON-NLS-1$
            public IStatus runInUIThread(IProgressMonitor monitor) {
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                .showActionSet("org.argouml.argoeclipse.ui.actionSet"); //$NON-NLS-1$
                Display.getDefault().asyncExec(new Runnable() {
                    
                    public void run() {
                        PulldownMenu.initMenus();
                    }
                    
                });                
                return Status.OK_STATUS;
            }
        };
        job.setSystem(true);
        job.schedule();
    }
    
    /*
     * @see org.eclipse.ui.part.EditorPart#setInput(org.eclipse.ui.IEditorInput)
     */
    public void setInput(IEditorInput input) {
        super.setInput(input);
    }

    /*
     * @see org.eclipse.ui.part.EditorPart#isDirty()
     */
    public boolean isDirty() {
        return Register.getInstance().isRegistered(Register.EDITOR, this)
            ? dirtyState : false;
    }

    /*
     * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
     */
    public boolean isSaveAsAllowed() {
        return Register.getInstance().isRegistered(Register.EDITOR, this)
            ? true : false;
    }

    /**
     * This creates a AWT(Advanced Widget Toolkit) frame inside a SWT (Standard
     * Widget Toolkit) and this allows us to use AWT/Swing inside Eclipse.
     * 
     * @param parent the root of the AWT Frame
     * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    public void createPartControl(Composite parent) {
        if (!Register.getInstance().isRegistered(Register.EDITOR, this)) {
            new Label(parent, SWT.NONE).setText(
                    EditorMessages.warningNotRegistered);
        } else {
            JPanel panel = Panels.getEditorPanel();
            if (panel != null) {
                SwingWrapper.wrap(panel, parent);
                saveListener = getSaveListener();
                ActionsListener.getInstance().addListener(
                        saveListener, ActionsListener.SAVE);
            } else {
                new Label(parent, SWT.NONE).setText(
                        EditorMessages.warningNullPanel);
            }
        }
    }

    /**
     * Catch focus changes.
     * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
     */
    public void setFocus() {
        // TODO: do something with the focus
    }
    
    /*
     * @see org.eclipse.ui.part.EditorPart#setPartName(java.lang.String)
     */
    public void setPartName(String partName) {
        super.setPartName(partName);
    }

    /**
     * Frees the resources. This happens when we close the editor.
     */
    public void dispose() {
        super.dispose();
        if (!Register.getInstance().isRegistered(Register.EDITOR, this)) {
            return;
        }
        ActionsListener.getInstance().removeListener(
                saveListener, ActionsListener.SAVE);
        Register.getInstance().unregister(this);
        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                .hideActionSet("org.argouml.argoeclipse.ui.actionSet"); //$NON-NLS-1$
        ArgoProject.closeProject();
    }
    
    private PropertyChangeListener getSaveListener() {
        return new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                dirtyState = ((Boolean) evt.getNewValue()).booleanValue();
                
                Display.getDefault().syncExec(new Runnable() {
                    public void run() {
                        firePropertyChange(IEditorPart.PROP_DIRTY);
                    }
                });
                
                if (!dirtyState 
                        && getEditorInput() instanceof FileEditorInput) {
                    try {
                        ((FileEditorInput) getEditorInput()).getFile()
                            .refreshLocal(IResource.DEPTH_ZERO, null);
                    } catch (CoreException e) {
                        e.printStackTrace();
                    }
                } 
            }
            
        };
    }
    
}

