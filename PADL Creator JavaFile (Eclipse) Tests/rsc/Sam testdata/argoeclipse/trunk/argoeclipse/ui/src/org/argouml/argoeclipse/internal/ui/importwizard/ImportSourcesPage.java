// $Id: ImportSourcesPage 191 2006/08/23 00:00:00 b00__1 Exp $
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

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import org.argouml.application.api.PluggableImportSettings;
import org.argouml.application.api.PluggableImportTypes;
import org.argouml.application.api.ProgressListener;
import org.argouml.argoeclipse.internal.core.imports.ImportSources;
import org.argouml.argoeclipse.internal.core.model.LRU;
import org.argouml.argoeclipse.internal.ui.Activator;
import org.argouml.argoeclipse.internal.ui.util.InformationDialog;
import org.argouml.argoeclipse.internal.ui.util.StatusBar;
import org.argouml.i18n.Translator;

/**
 * This class is responsible with the options available for Import.
 * It also handles the progress GUI for long imports.
 * @author Bogdan Pistol
 */
public abstract class ImportSourcesPage extends WizardPage {
    
    /**
     * The source.
     */
    private Text path;
    
    private CTabFolder tabFolder;
    
    /**
     * The accepted languages for import.
     */
    private CCombo languages;
    
    /**
     * @see {@link PluggableImportSettings#isDescendRecursivelyEnabled()}
     */
    private Button descendRecursively;
    
    /**
     * @see {@link PluggableImportSettings#isChangedEnabled()}
     */
    private Button changed;
    
    /**
     * @see {@link PluggableImportSettings#isCreateDiagramsEnabled()}
     */
    private Button createDiagrams;
    
    /**
     * @see {@link PluggableImportSettings#isMinimiseEnabled()}
     */
    private Button minimise;
    
    /**
     * @see {@link PluggableImportSettings#isAutomaticDiagramLayoutEnabled()}
     */
    private Button automaticDiagramLayout;
    
    /**
     * Import level 0
     */
    private Button classfiers;
    
    /**
     * Import level 1
     */
    private Button classfiersAndFeatureSpecifications;
    
    /**
     * Import level 2
     */
    private Button fullImport;
    
    /**
     * @see {@link PluggableImportSettings#getEncoding()}
     */
    private Text encoding;
    
    /**
     * The classpath.
     */
    private Table classpathTable;
    
    private List languageList;
    
    private static final String DYNAMIC_LRU_PREFIX = 
        "LRU_Import_Sources_Language_"; //$NON-NLS-1$
    
    private static final String DYNAMIC_LRU_OPTION = 
        "_Option_"; //$NON-NLS-1$
    
    private Map dynamicLRU = new HashMap();

    /**
     * Constructor
     */
    public ImportSourcesPage() {
        super("secondPage"); //$NON-NLS-1$
        setTitle(ImportWizardMessages.importSourcesTitle);
        setPageComplete(false);
        languageList = ImportSources.getInstance().getLanguages();
    }

    /**
     * Creates an area that displays the controls/widgets.
     * @param parent the composite parent
     * @return the created composite
     */
    protected Composite createWorkArea(Composite parent) {
        Composite workArea = new Composite(parent, SWT.NONE);
        setControl(workArea);
        
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        workArea.setLayout(layout);
        workArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        
        Composite location = new Composite(workArea, SWT.NONE);
        GridLayout locationLayout = new GridLayout();
        locationLayout.numColumns = 2;
        locationLayout.marginHeight = 0;
        locationLayout.marginWidth = 0;
        location.setLayout(locationLayout);
        location.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
                
        new Label(location, SWT.NONE).setText(ImportWizardMessages.sourcePath);
        path = new Text(location, SWT.SINGLE | SWT.BORDER);
        path.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        path.addModifyListener(new ModifyListener() {            
            public void modifyText(ModifyEvent e) {
                validate();                
            }                        
        });
        
        Composite returnedComposite = new Composite(workArea, SWT.NONE);        
        returnedComposite.setLayout(layout);
        returnedComposite.setLayoutData(
                new GridData(SWT.RIGHT, SWT.FILL, true, false));
        
        tabFolder = new CTabFolder(workArea, SWT.FLAT);
        tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        CTabItem general = new CTabItem(tabFolder, SWT.NONE);
        general.setText(
                Translator.localize("action.import-general")); //$NON-NLS-1$
        general.setControl(createGeneralTab());
        createClassPathTab();
        createLanguageSettingsTabs();
        tabFolder.setSelection(general);
        
        return returnedComposite;
    }
    
    private void createClassPathTab() {
        CTabItem tab = new CTabItem(tabFolder, SWT.NONE);
        tab.setText(ImportWizardMessages.classpath);
        Composite composite = new Composite(tabFolder, SWT.NONE);        
        composite.setLayout(new GridLayout());
        tab.setControl(composite);
        classpathTable = new Table(composite, SWT.V_SCROLL | SWT.H_SCROLL);
        classpathTable.setLayoutData(
                new GridData(SWT.FILL, SWT.FILL, true, true));
        classpathTable.setLinesVisible(true);
        fillClassPathList(classpathTable);
        Composite buttons = new Composite(composite, SWT.NONE);
        buttons.setLayoutData(new GridData(SWT.RIGHT, SWT.NONE, false, false));
        createClassPathAddRemoveButtons(buttons);
    }
    
    /**
     * Fills the list with classpaths.
     * @param classpaths the list of paths
     */
    protected abstract void fillClassPathList(Table classpaths);

    /**
     * This method initialize the Remove button from the classpath list.
     * @param button the button in cause
     */
    protected void configureClassPathRemoveButton(final Button button) {
        button.setText(ImportWizardMessages.removeClasspath);
        if (classpathTable.getSelectionIndex() == -1) {
            button.setEnabled(false);
        }
        classpathTable.addSelectionListener(new SelectionAdapter() {
           
            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);                
                button.setEnabled(classpathTable.getSelectionIndex() != -1);
            }
            
        });
        button.addSelectionListener(new SelectionAdapter() {
            
            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                if (classpathTable.getSelectionIndex() == -1) {
                    return;
                }
                int i = classpathTable.getSelectionIndex();
                classpathTable.remove(i);
                if (classpathTable.getItemCount() == 0) {
                    button.setEnabled(false);
                } else {
                    classpathTable.select(i > 1 ? i - 1 : 0);
                }                
            }
            
        });
    }
    
    /**
     * Creates the buttons that handles add/remove items from the classpathTable
     * list.
     * @param parent the shell parent
     */
    protected abstract void createClassPathAddRemoveButtons(Composite parent);
    
    /**
     * Adds the path to the list of paths.
     * @param classpath the value to add
     */
    protected void addClassPath(String classpath) {
        for (int i = 0; i < classpathTable.getItemCount(); i++) {
            if (classpathTable.getItem(i).getText().equals(classpath)) {
                MessageDialog.openInformation(getShell(),
                        ImportWizardMessages.addClasspathWarningTitle,
                        ImportWizardMessages.addClasspathWarningDescription);
                return;
            }
        }
        new TableItem(classpathTable, SWT.NONE).setText(classpath);
    }
    
    private void createLanguageSettingsTabs() {
        for (int i = 0; i < languages.getItemCount(); i++) {
            CTabItem tab = new CTabItem(tabFolder, SWT.NONE);
            tab.setText(languages.getItem(i));
            Composite composite = new Composite(tabFolder, SWT.NONE);
            composite.setLayout(new GridLayout());
            composite.setLayoutData(
                    new GridData(SWT.FILL, SWT.FILL, true, true));
            tab.setControl(composite);
            List settings = 
                ImportSources.getInstance().getSpecificLanguageSettings(i);
            for (int j = 0; j < settings.size(); j++) {
                Object o = settings.get(j);
                if (o instanceof PluggableImportTypes.Label) {
                    new CLabel(composite, SWT.NONE).setText(
                            ((PluggableImportTypes.Label) o).getLabel());
                } else if (o instanceof PluggableImportTypes.UniqueSelection) {
                    createRadioGroup(composite, i, j,
                            (PluggableImportTypes.UniqueSelection) o);
                }
            }
        }
    }
    
    private void createRadioGroup(Composite parent, final int lang,
            final int typeNo,
            final PluggableImportTypes.UniqueSelection options) {
        Composite group = new Composite(parent, SWT.NONE);
        group.setLayout(new GridLayout());
        group.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
        List list = options.getOptions();
        String s = LRU.get(DYNAMIC_LRU_PREFIX + (String) languageList.get(lang)
                + DYNAMIC_LRU_OPTION + Integer.toString(typeNo));
        int defaultOption = options.getDefaultSelection();
        if (!s.equals("")) { //$NON-NLS-1$
            defaultOption = Integer.parseInt(s);
        }
        for (int i = 0; i < list.size(); i++) {
            Button radio = new Button(group, SWT.RADIO);
            radio.setText((String) list.get(i));
            if (defaultOption == i) {
                radio.setSelection(true);
            }
            final int j = i;
            radio.addSelectionListener(new SelectionAdapter() {
                
                public void widgetSelected(SelectionEvent e) {
                    super.widgetSelected(e);                    
                    options.setSelection(j);
                    dynamicLRU.put(DYNAMIC_LRU_PREFIX
                            + (String) languageList.get(lang)
                            + DYNAMIC_LRU_OPTION + Integer.toString(typeNo),
                            Integer.toString(j));
                }
                
            });
        }
    }
    
    private Composite createGeneralTab() {
        Composite tab = new Composite(tabFolder, SWT.NONE);
        tab.setLayout(new GridLayout());
        tab.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        new CLabel(tab, SWT.NONE).setText(Translator.localize(
                "action.import-select-lang")); //$NON-NLS-1$
        languages = new CCombo(tab, SWT.DROP_DOWN | SWT.READ_ONLY);
        languages.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        ListIterator lang = languageList.listIterator();
        while (lang.hasNext()) {
            languages.add((String) lang.next());            
        }
        String s = LRU.get(LRU.IMPORT_SOURCES_LANGUAGE);
        if (languageList.contains(s)) {
            languages.select(languageList.indexOf(s));
        } else {
            languages.select(0);
        }
        descendRecursively = new Button(tab, SWT.CHECK);
        descendRecursively.setText(Translator.localize(
                "action.import-option-descend-dir-recur")); //$NON-NLS-1$
        s = LRU.get(LRU.IMPORT_SOURCES_DESCEND);
        descendRecursively.setSelection(s.equals("")  //$NON-NLS-1$
                ? true : Boolean.valueOf(s).booleanValue());
        changed = new Button(tab, SWT.CHECK);
        changed.setText(Translator.localize(
                "action.import-option-changed_new")); //$NON-NLS-1$
        s = LRU.get(LRU.IMPORT_SOURCES_CHANGED);
        changed.setSelection(s.equals("")  //$NON-NLS-1$
                ? true : Boolean.valueOf(s).booleanValue());
        createDiagrams = new Button(tab, SWT.CHECK);
        createDiagrams.setText(Translator.localize(
                "action.import-option-create-diagram")); //$NON-NLS-1$
        s = LRU.get(LRU.IMPORT_SOURCES_DIAGRAMS);
        createDiagrams.setSelection(s.equals("")  //$NON-NLS-1$
                ? true : Boolean.valueOf(s).booleanValue());
        createDiagrams.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                minimise.setEnabled(createDiagrams.getSelection());
                automaticDiagramLayout.setEnabled(
                        createDiagrams.getSelection());
                if (!createDiagrams.getSelection()) {
                    minimise.setSelection(false);
                    automaticDiagramLayout.setSelection(false);
                }
            }
            
        });
        minimise = new Button(tab, SWT.CHECK);
        minimise.setText(Translator.localize(
                "action.import-option-min-class-icon")); //$NON-NLS-1$
        s = LRU.get(LRU.IMPORT_SOURCES_MINIMIZE);
        if (!createDiagrams.getSelection()) {
            minimise.setEnabled(false);
        } else {
            minimise.setSelection(s.equals("")  //$NON-NLS-1$
                    ? true : Boolean.valueOf(s).booleanValue());
        }
        automaticDiagramLayout = new Button(tab, SWT.CHECK);
        automaticDiagramLayout.setText(Translator.localize(
                "action" //$NON-NLS-1$
                + ".import-option-perform-auto-diagram-layout")); //$NON-NLS-1$
        s = LRU.get(LRU.IMPORT_SOURCES_LAYOUT);
        if (!createDiagrams.getSelection()) {
            automaticDiagramLayout.setEnabled(false);
        } else {
            automaticDiagramLayout.setSelection(s.equals("")  //$NON-NLS-1$
                    ? true : Boolean.valueOf(s).booleanValue());
        }
        new CLabel(tab, SWT.NONE).setText(Translator.localize(
                "action.import-level-of-import-detail")); //$NON-NLS-1$
        classfiers = new Button(tab, SWT.RADIO);
        classfiers.setText(Translator.localize(
                "action.import-option-classfiers")); //$NON-NLS-1$
        int level = 2;
        s = LRU.get(LRU.IMPORT_SOURCES_LEVEL);
        if (!s.equals("")) { //$NON-NLS-1$
            level = Integer.parseInt(s);
        }
        if (level == 0) {
            classfiers.setSelection(true);
        }
        classfiersAndFeatureSpecifications = new Button(tab, SWT.RADIO);
        classfiersAndFeatureSpecifications.setText(Translator.localize(
                "action.import-option-classifiers-plus-specs")); //$NON-NLS-1$
        if (level == 1) {
            classfiersAndFeatureSpecifications.setSelection(true);
        }
        fullImport = new Button(tab, SWT.RADIO);
        fullImport.setText(Translator.localize(
                "action.import-option-full-import")); //$NON-NLS-1$
        if (level == 2) {
            fullImport.setSelection(true);            
        }
        new CLabel(tab, SWT.NONE).setText(Translator.localize(
                "action.import-file-encoding")); //$NON-NLS-1$
        encoding = new Text(tab, SWT.SINGLE | SWT.BORDER);
        encoding.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        encoding.setText(ImportSources.getInstance().getEncoding());
        
        return tab;
    }

    /**
     * @param text The path to set.
     */
    protected void setPath(String text) {
        path.setText(text);
    }

    /**
     * @return Returns the path.
     */
    protected String getPath() {
        return path.getText();
    }

    /*
     * @see org.eclipse.jface.wizard.WizardPage#isCurrentPage()
     */
    public boolean isCurrentPage() {
        return super.isCurrentPage();
    }
    
    /**
     * Ensures that everything is OK.
     */
    protected void validate() {
        if (getErrorMessage() != null) {            
            setErrorMessage(null);        
        }
        setPageComplete(!path.getText().equals("") //$NON-NLS-1$
                && languages.getSelectionIndex() != -1);
    }
    
    /**
     * Performs the import.
     * @param source the actual source path, this can be diferent from what the
     * user filled in the TextBox because if we are in a workspace this source
     * is the filesystem path of the workspace resource
     * @return the success or failure
     */
    public boolean performFinish(final String source) {
        if (languages.getItemCount() == 0) {
            setErrorMessage(ImportWizardMessages.noLanguageError);
            return false;
        }
        final int importType;
        if (classfiers.getSelection()) {
            importType = 0;
        } else if (classfiersAndFeatureSpecifications.getSelection()) {
            importType = 1;
        } else {
            importType = 2;
        }
        final String enc = encoding.getText();
        final boolean isLayoutChecked = automaticDiagramLayout.getSelection();
        final boolean isChangedChecked = changed.getSelection();
        final boolean isDiagramsChecked = createDiagrams.getSelection();
        final boolean isDescendChecked = descendRecursively.getSelection();
        final boolean isMinimiseChecked = this.minimise.getSelection();
        
        Iterator it = dynamicLRU.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            LRU.put((String) entry.getKey(), (String) entry.getValue());
        }
        LRU.put(LRU.IMPORT_SOURCES_LANGUAGE, (String) languageList
                .get(languages.getSelectionIndex()));
        LRU.put(LRU.IMPORT_SOURCES_DESCEND, Boolean.toString(isDescendChecked));
        LRU.put(LRU.IMPORT_SOURCES_CHANGED, Boolean.toString(isChangedChecked));
        LRU.put(LRU.IMPORT_SOURCES_DIAGRAMS,
                Boolean.toString(isDiagramsChecked));
        LRU.put(LRU.IMPORT_SOURCES_MINIMIZE,
                Boolean.toString(isMinimiseChecked));
        LRU.put(LRU.IMPORT_SOURCES_LAYOUT, Boolean.toString(isLayoutChecked));
        LRU.put(LRU.IMPORT_SOURCES_LEVEL, Integer.toString(importType));
        
        ImportSources.getInstance().readyForImport(
                languages.getSelectionIndex(),
                new PluggableImportSettings() {
                    private int importLevel = importType;
                    
                    public int getCurrentImportLevel() {
                        return importLevel;
                    }
                                        
                    public void setCurrentImportLevel(int level) {
                        importLevel = level;
                    }
                    
                    public String getSourceEncoding() {
                        return enc;
                    }
                    
                    public String getSourcePath() {
                        return source;
                    }

                    public boolean isAutomaticDiagramLayoutEnabled() {
                        return isLayoutChecked;
                    }

                    public boolean isChangedEnabled() {
                        return isChangedChecked;
                    }

                    public boolean isCreateDiagramsEnabled() {
                        return isDiagramsChecked;
                    }

                    public boolean isDescendRecursivelyEnabled() {
                        return isDescendChecked;
                    }

                    public boolean isMinimiseEnabled() {
                        return isMinimiseChecked;
                    }
                }, getClassPath(classpathTable));
        
        Display.getDefault().asyncExec(new Runnable() {

            public void run() {
                try {
                    Activator.getDefault().getWorkbench().getProgressService()
                        .run(true, true, getRunnableWithProgress());
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        return true;
    }    

    private IRunnableWithProgress getRunnableWithProgress() {
        return new IRunnableWithProgress() {
            
            private int currentProgress;
            
            public void run(final IProgressMonitor monitor)
                throws InvocationTargetException, InterruptedException {
                
                ImportSources.getInstance().startImporting(
                        new ProgressListener() {
                            
                            public void notifyMessage(final String title,
                                    final String introduction,
                                    final String problem) {
                                
                                Display.getDefault().asyncExec(new Runnable() {
                                    public void run() {
                                        Shell shell = Activator.getDefault()
                                            .getWorkbench()
                                                .getActiveWorkbenchWindow()
                                                    .getShell();
                                        new InformationDialog(shell, title,
                                                introduction, problem).open();
                                    }
                                });
                            }

                            public void setMaximumProgress(int max) {
                                monitor.beginTask("", max); //$NON-NLS-1$
                            }

                            public void updateProgress(int progress) {
                                monitor.worked(progress - currentProgress);
                                currentProgress = progress;
                            }

                            public void updateSubTask(String action) {
                                monitor.subTask(action);
                            }

                            public boolean isCanceled() {
                                return monitor.isCanceled();
                            }

                            public void updateMainTask(String task) {
                                monitor.setTaskName(task);
                            }

                            public void notifyNullAction() {
                                StatusBar.show(
                                        ImportWizardMessages.noProperInput);
                            }

                        });
                monitor.done();
            }
            
        };
    }

    /**
     * Returns the classpath. It's abstract because if we work in the
     * workspace then we should translate the classpath in java.io.File.
     * (because ArgoUML works with java.io.File)
     * @param classpaths the table from wich the values should be extracted
     * @return the list of classpaths
     */
    protected abstract List getClassPath(Table classpaths);
    
}
