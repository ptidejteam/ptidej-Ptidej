// $Id: ExternalResourcePage.java 96 2006/07/27 00:00:00 b00__1 $
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

package org.argouml.argoeclipse.internal.ui.wizard;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.ResourceListSelectionDialog;

import org.argouml.argoeclipse.internal.core.model.LRU;

/**
 * This page deals with the resource import/export from
 * eclipse projects and the filesystem.
 * @author Bogdan Pistol
 */
public class ExternalResourcePage extends WizardPage {  
    
    /**
     * This style type is used for exporting a resource into a file.
     */
    public static final int EXPORT_FILE = 0;
    
    /**
     * This style type is used for exporting resources into a folder.
     */
    public static final int EXPORT_TO_FOLDER = 1;
    
    /**
     * This style type is used for importing a resource from a file.
     */
    public static final int IMPORT_FILE = 2;
    
    /**
     * This style type is used for importing a resource from a file or a folder.
     */
    public static final int IMPORT_FILE_OR_FOLDER = 3;
    
    /**
     * The textfield that contains the path in the filesystem.
     */
    private Text filesystemPath;  
    
    /**
     * The textfield that contains the path in the workspace.
     */
    private Text workspacePath;
    
    /**
     * The filename from the workspace.
     */
    private Text workspaceFile;
    
    /**
     * The extensions that are available.
     */
    private String [] extensions;
    
    /**
     * The title of the page.
     */
    private String title;
    
    /**
     * The contents of the additional options related with the workspace.
     */
    private Composite extraContents;
    
    /**
     * The parent of all the extra contents.
     */
    private Composite extraContentsParent;
    
    /**
     * Height of the extra contents. 
     */
    private int extraContentsHeight = -1;
    
    /**
     * The filesystem browser button.
     */
    private Button workspaceContainerBrowser;
    
    /**
     * The style of the page.
     */
    private int style; 
    
    /**
     * Stores the IFile for refresh purposes. 
     */
    private IFile ifile;
    
    /**
     * Stores the IContainer for refresh purposes.
     */
    private IContainer icontainer;
    
    private String filesystemDefaultPath;
    
    private static final int CHOSER_DEFAULT_WORKSPACE = 1;
    
    private static final int CHOSER_DEFAULT_FILESYSTEM = 2;
    
    /**
     * This will be the default selected option to export into the workspace
     * or the filesystem.
     */
    private int defaultChooser;
    
    /**
     * Constructor
     * 
     * @param pageTitle
     *            the name of this page
     * @param pageDescription
     *            the description of the page
     * @param acceptedExtensions
     *            the extensions that the save dialog will handle
     * @param type
     *            the style of the page, valid values are
     *            ExternalResource.EXPORT_FILE, ExternalResource.IMPORT_FILE,
     *            ExternalResource.EXPORT_TO_FOLDER
     */
    public ExternalResourcePage(String pageTitle, String pageDescription,
            String[] acceptedExtensions, int type) {
        super("newPage"); //$NON-NLS-1$
        String path = null;
        String choser = null;
        if (type == IMPORT_FILE || type == IMPORT_FILE_OR_FOLDER) {
            path = LRU.get(LRU.IMPORT_DEFAULT_PATH);
            choser = LRU.get(LRU.IMPORT_CHOSER_DEFAULT);
        } else {
            path = LRU.get(LRU.EXPORT_DEFAULT_PATH);
            choser = LRU.get(LRU.EXPORT_CHOSER_DEFAULT);
        }
        if (path == null || path.equals("")) { //$NON-NLS-1$
            path = "/"; //$NON-NLS-1$
        }
        if (choser == null || choser.equals("")) { //$NON-NLS-1$
            defaultChooser = CHOSER_DEFAULT_WORKSPACE;
        } else {
            defaultChooser = Integer.parseInt(choser);
        }
        filesystemDefaultPath = path;
         
        style = type;
        extensions = acceptedExtensions;
        title = pageTitle;
        setTitle(title);
        setDescription(pageDescription);
        setPageComplete(false);                
    }
    
    /*
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        
        initializeDialogUnits(parent);
        
        Composite workArea = new Composite(parent, SWT.NONE);
        setControl(workArea);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        workArea.setLayout(layout);
        workArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        
        Composite workspaceArea = new Composite(workArea, SWT.NONE);
        GridLayout workspaceLayout = new GridLayout();
        workspaceLayout.marginHeight = 0;
        workspaceLayout.marginWidth = 0;
        workspaceLayout.numColumns = (style == IMPORT_FILE)
                || (style == IMPORT_FILE_OR_FOLDER) ? 2 : 3;
        workspaceArea.setLayout(workspaceLayout);
        workspaceArea.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true,
                false));                
        
        new Label(workspaceArea, SWT.NONE).setText(style == IMPORT_FILE
                || style == IMPORT_FILE_OR_FOLDER
                ? WizardMessages.workspaceLabel
                        : WizardMessages.containerLabel);
        
        workspacePath = new Text(workspaceArea, SWT.SINGLE | SWT.BORDER);
        workspacePath.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true,
                false));
        workspacePath.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                validate();                
            }                        
        });
        
        if (style == IMPORT_FILE || style == IMPORT_FILE_OR_FOLDER) {
            new Label(workspaceArea, SWT.NONE);
        }
        workspaceContainerBrowser = new Button(workspaceArea, SWT.PUSH);
        workspaceContainerBrowser.setText(WizardMessages.browseButton);
        if (style == IMPORT_FILE || style == IMPORT_FILE_OR_FOLDER) {
            workspaceContainerBrowser.setLayoutData(new GridData(
                    SWT.RIGHT, SWT.NONE, false, false));
        }
        workspaceContainerBrowser.addSelectionListener(new SelectionAdapter() {
            
            public void widgetSelected(SelectionEvent e) {
                if (style == IMPORT_FILE || style == IMPORT_FILE_OR_FOLDER) {
                    handleBrowseContainerAndFile();
                } else {
                    handleBrowseContainer();
                }
            }
            
        });                 
        
        if (style != EXPORT_TO_FOLDER && style != IMPORT_FILE
                && style != IMPORT_FILE_OR_FOLDER) {
            new Label(workspaceArea, SWT.NONE).setText(
                    WizardMessages.filenameLabel);
            workspaceFile = new Text(workspaceArea, SWT.SINGLE | SWT.BORDER);
            workspaceFile.setLayoutData(new GridData(SWT.FILL, SWT.NONE,
                    true, false));
            workspaceFile.addModifyListener(new ModifyListener() {
                public void modifyText(ModifyEvent e) {
                    validate();
                }
            });
        }
        
        Button checkbox = new Button(workArea, SWT.CHECK);
        checkbox.setText(WizardMessages.useFilesystemLabel);
        checkbox.setToolTipText(WizardMessages.useFilesystemDescription);
        checkbox.addSelectionListener(new SelectionAdapter() {
            
            public void widgetSelected(SelectionEvent e) {
                handleCheckbox();
            }
            
        });
        
        extraContentsParent = new Composite(workArea, SWT.NONE);        
        extraContentsParent.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true,
                false));                                
        extraContentsParent.setLayout(layout);                        
        
        workArea.pack();
        setControl(workArea);
        if (defaultChooser == CHOSER_DEFAULT_FILESYSTEM) {
            checkbox.setSelection(true);
            handleCheckbox();
        }
    }
    
    private void handleCheckbox() {
        Shell shell = getShell();
        Point shellSize = shell.getSize();
        Composite composite = (Composite) getControl();

        if (extraContents != null) {
            extraContents.dispose();
            extraContents = null;
            composite.layout();
            shell.setSize(shellSize.x, shellSize.y - extraContentsHeight); 
            
            workspacePath.setEnabled(true);
            workspaceContainerBrowser.setEnabled(true);
            if (workspaceFile != null) {
                workspaceFile.setEnabled(true);
            }
        } else {
            extraContents = createExtraContents(extraContentsParent);
            
            if (extraContentsHeight == -1) {
                Point groupSize = extraContents.computeSize(
                        SWT.DEFAULT, SWT.DEFAULT, true);
                extraContentsHeight = groupSize.y;
            }
            shell.setSize(shellSize.x, shellSize.y + extraContentsHeight);
            composite.layout(); 
            
            workspacePath.setEnabled(false);
            workspaceContainerBrowser.setEnabled(false);
            if (workspaceFile != null) {
                workspaceFile.setEnabled(false);
            }
        }
        validate();
    } 
    
    /**
     * Creates the contents of the extra page.
     * @param parent the composite parent
     * @return the newly created composite
     */
    private Composite createExtraContents(Composite parent) {
        Composite contents = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        layout.numColumns = 2;
        contents.setLayout(layout);
        contents.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
        
        new Label(contents, SWT.NONE).setText(WizardMessages.filesystemLabel);
        
        filesystemPath = new Text(contents, SWT.SINGLE | SWT.BORDER);
        filesystemPath.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true,
                false));
        filesystemPath.addModifyListener(new ModifyListener() {            
            public void modifyText(ModifyEvent e) {
                validate();
            }                        
        });
        
        new Label(contents, SWT.NONE);  // to skip to the next row
        Button filesystemBrowser = new Button(contents, SWT.PUSH);
        filesystemBrowser.setText(WizardMessages.browseButton);
        filesystemBrowser.setLayoutData(new GridData(SWT.RIGHT, SWT.NONE, false,
                false));
        filesystemBrowser.addSelectionListener(new SelectionAdapter() {
            
            public void widgetSelected(SelectionEvent e) {                
                handleFilesystemBrowser();
            }
            
        });        

        return contents;
    }
    
    private void handleBrowseContainerAndFile() {
        ResourceListSelectionDialog dialog = new ResourceListSelectionDialog(
                getShell(), ResourcesPlugin.getWorkspace().getRoot(),
                style == IMPORT_FILE ? IResource.FILE : IResource.FILE
                        | IResource.FOLDER | IResource.PROJECT);        
        if (dialog.open() == Window.OK) {
            Object[] result = dialog.getResult();
            if (result.length == 1) {
                workspacePath.setText(addExtension(((IResource) result[0])
                        .getFullPath().toOSString()));
                validate();
            }
        }
    }

    /**
     * Opens the filesystem selection dialog.
     */
    private void handleFilesystemBrowser() {
        Dialog dialog = null;
        if (style == IMPORT_FILE) {
            dialog = new FileDialog(getShell(), SWT.OPEN);            
        } else if (style == EXPORT_FILE) {
            dialog = new FileDialog(getShell(), SWT.SAVE);
        } else if (style == EXPORT_TO_FOLDER) {
            dialog = new DirectoryDialog(getShell());
        }
        
        dialog.setText(title);
        
        String result = null;
        
        if (style != EXPORT_TO_FOLDER) {
            String[] ext = new String[extensions.length + 1];
            String[] extName = new String[extensions.length + 1];
            
            for (int i = 0; i < ext.length - 1; i++) {
                ext[i] = "*." + extensions[i]; //$NON-NLS-1$
            }
            ext[ext.length - 1] = "*"; //$NON-NLS-1$
            ((FileDialog) dialog).setFilterExtensions(ext);
            
            for (int i = 0; i < extName.length - 1; i++) {
                extName[i] = extensions[i].toUpperCase() + " " //$NON-NLS-1$
                    + WizardMessages.filesSuffix;
            }
            extName[extName.length - 1] = WizardMessages.allFiles;
            ((FileDialog) dialog).setFilterNames(extName);
            ((FileDialog) dialog).setFilterPath(filesystemDefaultPath);
            result = ((FileDialog) dialog).open();
        } else {
            ((DirectoryDialog) dialog)
                    .setMessage(WizardMessages.filesystemFolderDialogTitle);
            ((DirectoryDialog) dialog).setFilterPath(filesystemDefaultPath
                    .substring(0, filesystemDefaultPath.lastIndexOf(
                            "/"))); //$NON-NLS-1$
            result = ((DirectoryDialog) dialog).open();
        }                
        
        if (result == null) {
            return;            
        }

        filesystemPath.setText(addExtension(result)); 
        validate();
    }
    
    /**
     * If it's necessary the default extension will be added.
     * @param name the string that represent the filename
     * @return the string with the possible extension added
     */
    private String addExtension(String name) {
        if (style == EXPORT_TO_FOLDER) {
            return name;
        }
        String result = name;
        if (extensions.length >= 1) {
            boolean addDefaultExtension = true;
            for (int i = 0; i < extensions.length; i++) {
                if (name.toLowerCase().endsWith(
                        "." + extensions[i])) { //$NON-NLS-1$
                    addDefaultExtension = false;
                }
            }
            if (addDefaultExtension) {
                result += "." + extensions[0]; //$NON-NLS-1$
            }
        }
        
        return result;
    }
        
    private void handleBrowseContainer() { 
        ContainerSelectionDialog dialog = new ContainerSelectionDialog(
                getShell(), null, false,
                WizardMessages.containerSelectionTitle);
        dialog.showClosedProjects(false);        
        dialog.open();
        Object [] result = dialog.getResult();
        if (result != null && result.length == 1 && result[0] instanceof Path) {
            Path path = (Path) result[0];
            workspacePath.setText(path.toOSString());
        }     
        validate();
    }  
    
    /**
     * Ensures that everything is OK.     
     */
    private void validate() {
        if (getErrorMessage() != null) {
            setErrorMessage(null);        
        }
        boolean valid = true;
        if (extraContents != null) {
            if (filesystemPath.getText().equals("")) { //$NON-NLS-1$
                valid = false;
            }
        } else {
            if (workspacePath.getText().equals("")) { //$NON-NLS-1$
                valid = false;
            }
            if ((style != EXPORT_TO_FOLDER && style != IMPORT_FILE)
                    && workspaceFile.getText().equals("")) { //$NON-NLS-1$
                valid = false;
            }
        }
        setPageComplete(valid);
    }        
    
    private boolean confirmOverwrite() {
        return MessageDialog.openConfirm(getShell(),
                WizardMessages.overwriteDialogTitle,
                WizardMessages.overwriteDialogDescription);
    }        
    
    /**
     * Creates the resource and returns it's path.
     * <p>
     * If the style is EXPORT_TO_FOLDER then it only returns the path.
     * <p>
     * If you want to modify this resource
     * you must call refreshResource() after you modified it.
     * This way the resource will be synchronized. 
     * 
     * @return the resource path or null if something goes wrong
     */
    public String getResourcePath() {
        String result = null;

        if (extraContents == null) {
            IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

            if (style == EXPORT_TO_FOLDER) {
                IPath path = new Path(workspacePath.getText());
                IProject project = root.getProject(path.segment(0));
                
                if (!project.exists()) {
                    setErrorMessage(WizardMessages.pathError);
                    return null;
                }
                
                path = path.removeFirstSegments(1);                
                if (path.segmentCount() == 0) {
                    icontainer = project;
                } else {
                    IFolder folder = project.getFolder(path);
                    if (!folder.exists()) {
                        setErrorMessage(WizardMessages.pathError);
                        return null;
                    }
                    icontainer = folder;
                }
                
                result = icontainer.getLocation().toOSString();
            } else {
                Text text = style != IMPORT_FILE ? workspaceFile
                        : workspacePath;
                text.setText(addExtension(text.getText()));
                IPath path = new Path(
                        (style != IMPORT_FILE ? workspacePath.getText()
                                + Path.SEPARATOR : "") //$NON-NLS-1$
                                + text.getText());
                
                try {
                    ifile = root.getFile(path);
                } catch (RuntimeException e1) {
                    setErrorMessage(WizardMessages.pathError);
                    return null;
                }
                if (ifile.exists()) {
                    if (style != IMPORT_FILE && !confirmOverwrite()) {
                        return null;
                    }
                } else {
                    if (style == IMPORT_FILE) {
                        setErrorMessage(WizardMessages.pathError);
                        return null;
                    } else {
                        try {
                            ifile.create(null, IResource.NONE, null);
                        } catch (CoreException e) {
                            setErrorMessage(WizardMessages.pathError);
                            return null;
                        }
                    }
                }
                result = ifile.getLocation().toOSString();
            }
            if (style == IMPORT_FILE || style == IMPORT_FILE_OR_FOLDER) {
                LRU.put(LRU.IMPORT_CHOSER_DEFAULT, Integer
                        .toString(CHOSER_DEFAULT_WORKSPACE));
            } else {
                LRU.put(LRU.EXPORT_CHOSER_DEFAULT, Integer
                        .toString(CHOSER_DEFAULT_WORKSPACE));
            }
        } else {
            filesystemPath.setText(addExtension(filesystemPath.getText()));
            File file = new File(filesystemPath.getText());

            if (!file.isAbsolute()) {
                setErrorMessage(WizardMessages.pathError);
                return null;
            }

            if (style == EXPORT_TO_FOLDER) {
                if (!file.exists()) {
                    setErrorMessage(WizardMessages.pathError);
                    return null;
                }
            } else {
                if (file.exists()) {
                    if (style != IMPORT_FILE && !confirmOverwrite()) {
                        return null;
                    }
                } else {
                    if (style == IMPORT_FILE) {
                        setErrorMessage(WizardMessages.pathError);
                        return null;
                    } else {
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            setErrorMessage(WizardMessages.pathError);
                            return null;
                        }
                    }
                }
            }
            if (style == IMPORT_FILE || style == IMPORT_FILE_OR_FOLDER) {
                LRU.put(LRU.IMPORT_DEFAULT_PATH, file.getPath());
                LRU.put(LRU.IMPORT_CHOSER_DEFAULT, Integer
                        .toString(CHOSER_DEFAULT_FILESYSTEM));
            } else {
                LRU.put(LRU.EXPORT_DEFAULT_PATH, file.getPath());
                LRU.put(LRU.EXPORT_CHOSER_DEFAULT, Integer
                        .toString(CHOSER_DEFAULT_FILESYSTEM));
            }

            result = file.getPath();
        }

        return result;
    }
    
    /**
     * Refreshes the resource.
     */
    public void refreshResource() {
        if (ifile != null) {
            try {
                ifile.refreshLocal(IResource.DEPTH_ZERO, null);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        if (icontainer != null) {
            try {
                icontainer.refreshLocal(IResource.DEPTH_INFINITE, null);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
    }
    
}
