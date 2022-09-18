// $Id: ImportSources 216 2006/08/25 00:00:00 b00__1 Exp $
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

package org.argouml.argoeclipse.internal.core.imports;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.tigris.gef.base.Globals;

import org.argouml.application.api.Argo;
import org.argouml.application.api.PluggableImportEx;
import org.argouml.application.api.PluggableImportSettings;
import org.argouml.application.api.Configuration;
import org.argouml.application.api.PluggableImport;
import org.argouml.application.api.ProgressListener;
import org.argouml.cognitive.Designer;
import org.argouml.i18n.Translator;
import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.model.Model;
import org.argouml.ui.explorer.ExplorerEventAdaptor;
import org.argouml.uml.diagram.static_structure.ClassDiagramGraphModel;
import org.argouml.uml.diagram.static_structure.layout.ClassdiagramLayouter;
import org.argouml.uml.diagram.ui.UMLDiagram;
import org.argouml.uml.reveng.DiagramInterface;
import org.argouml.uml.reveng.ImportClassLoader;

/**
 * This class is responsible with the import sources functionality.
 *
 * @author Bogdan Pistol (rewrote it for ArgoEclipse)
 * @author Andreas Rueckert (original author)
 * 
 */
public class ImportSources {
    
    /**
     * The % maximum progress required to preparing for import. 
     */
    private static final int MAX_PROGRESS_PREPARE = 1;
    
    /**
     * The % maximum progress required to import.
     */
    private static final int MAX_PROGRESS_IMPORT = 99;
    
    /**
     * The instance of this class.
     */
    private static ImportSources instance;
    
    /**
     * A list with PluggableImport modules.
     */
    private List modules;
    
    /**
     * The PluggableImport selected by the user.
     */
    private PluggableImportEx module;
    
    /**
     * An interface with the diagram for common import operations.
     */
    private DiagramInterface diagramInterface;
    
    /**
     * The classpaths selected by the user.
     */
    private URL[] classPaths;
    
    /**
     * The settings for this import.
     */
    private PluggableImportSettings settings;
    
    /**
     * This is the general import level chose by the user.
     * <p>
     * There is another import level in CommonImportSettings, that is the
     * current import for use and can be different from this one, because if 
     * the user chose a level higher than 0 we must use 0 first and then a
     * higher level.
     */
    private int importLevel;
    
    private ImportSources() {        
        modules = Argo.getPlugins(PluggableImport.class);
    }
    
    /**
     * This a singleton, but the instance is not kept forever.
     * After the import finishes the instance will be assigned to null.
     * So we have a new instance everytime we do a new import, but in the
     * course of the import the instance is singleton.
     * @return the current instance or creates a new one if this is a new
     * import
     */
    public static ImportSources getInstance() {
        if (instance == null) {
            instance = new ImportSources();
        }
        return instance;
    }
    
    /**
     * Returns the possible languages in which the user can import the sources. 
     * @return a list of Strings with the names of the languages available
     */
    public List getLanguages() {
        List languages = new ArrayList();
        ListIterator iterator = modules.listIterator();
        while (iterator.hasNext()) {
            languages.add(((PluggableImport) iterator.next()).getModuleName());
        }
        return languages;
    }
    
    /**
     * Returns the speciffic settings for a language. The settings are in a raw
     * format implementing nested interfaces from {@link PluggableImportTypes}.
     * The caller will test the items from the list to see which interface they
     * implement.
     * 
     * @see PluggableImportTypes
     * @see PluggableImportTypes.Label
     * @see PluggableImportTypes.UniqueSelection
     * @param lang
     *            the 0-based index (from
     *            {@link ImportSources#getLanguages()}) of the language
     *            for wich the settings are returned
     * @return a list of objects implementing nested interfaces from
     *         {@link PluggableImportTypes}
     */
    public List getSpecificLanguageSettings(int lang) {
        if (lang < 0 || lang >= modules.size()) {
            return null;
        }
        return ((PluggableImportEx) modules.get(lang)).getSpecificImportSettings();
    }
    
    /**
     * The default encoding. This should be asked by the GUI for
     * initialization.
     * @return the encoding stored in Argo.KEY_INPUT_SOURCE_ENCODING key or if
     * this is null the default system encoding
     */
    public String getEncoding() {
        String enc = Configuration.getString(Argo.KEY_INPUT_SOURCE_ENCODING); 
        if (enc == null || enc.trim().equals("")) { //$NON-NLS-1$
            enc = System.getProperty("file.encoding"); //$NON-NLS-1$
        }
        
        return enc;
    }

    /**
     * Gets the import classpaths. This should be asked by the GUI for
     * initialization.
     * @return a list with Strings representing the classpaths
     */
    public List getImportClasspath() {
        List list = new ArrayList();
        URL[] urls = ImportClassLoader.getURLs(Configuration.getString(
                Argo.KEY_USER_IMPORT_CLASSPATH, "")); //$NON-NLS-1$
        for (int i = 0; i < urls.length; i++) {
            list.add(urls[i].getFile());
        }
        return list;
    }
    
    /**
     * Stores the options chose by the user, and gets ready for import.
     * 
     * @param lang
     *            the 0-based index (from
     *            {@link ImportSources#getLanguages()}) of the language
     * @param commonSettings
     *            the import settings chose by the user
     * @param classPath
     *            the list of Strings that represents the classpaths
     */
    public void readyForImport(int lang, PluggableImportSettings commonSettings,
            List classPath) {
        module = (PluggableImportEx) modules.get(lang);
        settings = commonSettings;
        importLevel = settings.getCurrentImportLevel();
        settings.setCurrentImportLevel(0);      // to start from level 0
        Configuration.setString(
                Argo.KEY_INPUT_SOURCE_ENCODING, settings.getSourceEncoding());
        classPaths = new URL[classPath.size()];
        for (int i = 0; i < classPath.size(); i++) {
            try {
                classPaths[i] = new File((String) classPath.get(i)).toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        try {
            ImportClassLoader.getInstance(classPaths);
            ImportClassLoader.getInstance().saveUserPath();                
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Builds a list with files to be parsed.
     * @return the list with files
     */
    private List prepareFiles() {
        List filesToParse = getFilesForImport();

        if (settings.isChangedEnabled()) {
            // filter out all unchanged files
            Object model =
                ProjectManager.getManager().getCurrentProject().getModel();
            for (int i = filesToParse.size() - 1; i >= 0; i--) {
                File f = (File) filesToParse.get(i);
                String fn = f.getAbsolutePath();
                String lm = String.valueOf(f.lastModified());
                if (lm.equals(
                        Model.getFacade().getTaggedValueValue(model, fn))) {
                    filesToParse.remove(i);
                }
            }
        }

        if (importLevel != 0) { // 2 passes needed
            filesToParse.addAll(filesToParse);
        }
        
        diagramInterface = getCurrentDiagram();
        
        return filesToParse;
    }
    
    /**
     * @return the current diagram
     */
    private DiagramInterface getCurrentDiagram() {
        DiagramInterface result = null;
        if (Globals.curEditor().getGraphModel()
                instanceof ClassDiagramGraphModel) {

            result =  new DiagramInterface(Globals.curEditor());

        }
        return result;
    }
    
    /**
     * Gets all the files from the tree source.
     * @return a list with files
     */
    private List getFilesForImport() {
        List result = new ArrayList();
        List toDoDirectories = new ArrayList();
        List doneDirectories = new ArrayList();
        File file = new File(settings.getSourcePath());
        
        toDoDirectories.add(file);
        
        while (toDoDirectories.size() > 0) {
            File curDir = (File) toDoDirectories.get(0);
            toDoDirectories.remove(0);
            doneDirectories.add(curDir);

            if (!curDir.isDirectory()) {
                // For some reason, this eledged directory is a single file
                // This could be that there is some confusion or just
                // the normal, that a single file was selected and is
                // supposed to be imported.
                if (module.isParseable(curDir)) {
                    result.add(curDir);
                }
                continue;
            }

            // Get the contents of the directory
            String[] files = curDir.list();

            for (int i = 0; i < files.length; i++) {
                File curFile = new File(curDir, files[i]);

                // The following test can cause trouble with
                // links, because links are accepted as
                // directories, even if they link files. Links
                // could also result in infinite loops. For this
                // reason we don't do this traversing recursively.
                if (curFile.isDirectory()) {
                    // If this file is a directory
                    if (settings.isDescendRecursivelyEnabled()) {
                        if (doneDirectories.indexOf(curFile) >= 0
                                || toDoDirectories.indexOf(curFile) >= 0) {
                            ; // This one is already seen or to be seen.
                        } else {
                            toDoDirectories.add(curFile);
                        }
                    }
                } else {
                    if (module.isParseable(curFile)) {
                        result.add(curFile);
                    }
                }
            }
        }
        
        return result;
    }    

    /**
     * Start the import action.
     * <p>
     * This method is called when all the import settings are ready.
     * <p>
     * This method looks at the {@link ProgressListener#isCanceled()}
     * periodicaly to determine if the action should cancel.
     * @param monitor this monitors the progress of the import
     */
    public void startImporting(ProgressListener monitor) {
        monitor.setMaximumProgress(MAX_PROGRESS_PREPARE + MAX_PROGRESS_IMPORT);
        int progress = 0;
        monitor.updateSubTask(ImportsMessages.preImport);
        List files = prepareFiles();
        progress += MAX_PROGRESS_PREPARE;
        monitor.updateProgress(progress);
        if (files.size() == 0) {
            monitor.notifyNullAction();
            return;
        }
        Model.getPump().stopPumpingEvents();
        boolean criticThreadWasOn = Designer.theDesigner().getAutoCritique();
        if (criticThreadWasOn) {
            Designer.theDesigner().setAutoCritique(false);
        }
        try {
            doImport(files, monitor, progress);
        } finally {
            if (criticThreadWasOn) {
                Designer.theDesigner().setAutoCritique(true);
            }
            ExplorerEventAdaptor.getInstance().structureChanged();
            Model.getPump().startPumpingEvents();
        }
        instance = null;        // to start a new fresh import next time
    }
    
    /**
     * Do the import.
     * @param filesLeft the files to parse
     * @param monitor the progress meter
     * @param progress the actual progress until now
     */
    private void doImport(List filesLeft, final ProgressListener monitor,
            int progress) {
        int countFiles = filesLeft.size();
        int countFilesThisPass = countFiles;
        List nextPassFiles = new ArrayList();
        final StringBuffer problems = new StringBuffer();
        
        while (filesLeft.size() > 0) {
            if (importLevel > 0) {
                if (filesLeft.size() <= countFiles / 2) {
                    settings.setCurrentImportLevel(importLevel);
                }
            }
            Object curFile = filesLeft.get(0);
            filesLeft.remove(0);

            try {
                int tot = countFiles;
                if (diagramInterface != null) {
                    tot += diagramInterface.getModifiedDiagrams().size() / 10;
                }
                int act = countFiles - filesLeft.size() - nextPassFiles.size();
                if (importLevel > 0 && act >= tot / 2) {
                    monitor.updateMainTask(ImportsMessages.secondPhase);
                } else {
                    monitor.updateMainTask(ImportsMessages.firstPhase);
                }
                monitor.updateSubTask(ImportsMessages.parsingAction
                        + curFile.toString());
                parseFile(
                        ProjectManager.getManager().getCurrentProject(),
                        curFile);
                progress = MAX_PROGRESS_PREPARE + MAX_PROGRESS_IMPORT * act
                        / tot;
                monitor.updateProgress(progress);
            } catch (Exception e) {
                nextPassFiles.add(curFile);
                StringBuffer sb = new StringBuffer(80);
                // RuntimeExceptions should be reported here!
                sb.append(e instanceof RuntimeException 
                        ? ImportsMessages.programBug
                        : ImportsMessages.programException);
                sb.append(curFile.toString());
                sb.append(ImportsMessages.warningConclusion + "\n"); //$NON-NLS-1$
                StringWriter sw = new StringWriter();
                PrintWriter pw = new java.io.PrintWriter(sw);
                e.printStackTrace(pw);
                sb.append(sw.getBuffer());
                problems.append(sb);
            }

            if (monitor.isCanceled()) {         // if the user hit cancel
                return;
            }
            
            if (filesLeft.size() == 0 && nextPassFiles.size() > 0
                    && nextPassFiles.size() < countFilesThisPass) {
                filesLeft = nextPassFiles;
                nextPassFiles = new ArrayList();
                countFilesThisPass = filesLeft.size();
            }
        }
        
        if (settings.isAutomaticDiagramLayoutEnabled()) {
            monitor.updateMainTask(ImportsMessages.postImport);
            layoutDiagrams(monitor, progress, countFiles);
        }
        monitor.updateMainTask(ImportsMessages.importFinished);
        monitor.updateSubTask(""); //$NON-NLS-1$
        monitor.updateProgress(MAX_PROGRESS_PREPARE
                + MAX_PROGRESS_IMPORT);
        if (problems != null && problems.length() > 0) {
                    monitor.notifyMessage(
                            Translator.localize(
                                 "dialog.title.import-problems"), //$NON-NLS-1$
                            Translator.localize(
                                 "label.import-problems"),        //$NON-NLS-1$
                            problems.toString());
                }
        }
    
    /**
     * Layouts the diagrams.
     * 
     * @param monitor
     *            the progress meter
     * @param progress
     *            the actual progress until now
     * @param countFiles
     *            the total initial files to parse
     */
    private void layoutDiagrams(ProgressListener monitor, int progress,
            int countFiles) {
        if (diagramInterface == null) {
            return;
        }
        monitor.updateSubTask(ImportsMessages.layoutingAction);
        int tot = countFiles + diagramInterface.getModifiedDiagrams().size()
                / 10;
        for (int i = 0; 
                i < diagramInterface.getModifiedDiagrams().size(); i++) {
            UMLDiagram diagram = 
                (UMLDiagram) diagramInterface.getModifiedDiagrams().
                    elementAt(i);
            ClassdiagramLayouter layouter = module.getLayout(diagram);
            layouter.layout();
            int act = countFiles + (i + 1) / 10;
            progress = MAX_PROGRESS_PREPARE + MAX_PROGRESS_IMPORT * act / tot;
            monitor.updateProgress(progress);
        }
    }
    
    /**
     * Parse 1 Java file.
     * @param project the project
     * @param f The file to parse.
     * @exception Exception ??? TODO: Couldn't we throw a narrower one?
     */
    private void parseFile(Project project, Object f) throws Exception {
        module.parseFile(project, f, diagramInterface, settings);
        // set the lastModified value
        String fn = ((File) f).getAbsolutePath();
        String lm = String.valueOf(((File) f).lastModified());
        if (lm != null) {
            Model.getCoreHelper().setTaggedValue(project.getModel(), fn, lm);
        }
    }
    
}
