// $Id: ArgoProject.java 0.1.1 2006/09/16 00:00:00 b00__1 Exp $
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

package org.argouml.argoeclipse.internal.core.model;

import java.io.File;
import java.net.URI;

import org.argouml.cognitive.Designer;
import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.model.Model;
import org.argouml.persistence.PersistenceManager;
import org.argouml.ui.ProjectBrowser;

/**
 * This class is responsible with the common actions involving an argo project.
 * @author Bogdan Pistol
 */
public class ArgoProject {
    
    /**
     * Determines is we need to use Save As.
     * @return true for Save As or false
     */
    public static boolean isSaveAsRequired() {
        return getProjectPath() == null;
    }
    
    /**
     * Returns the path of the project
     * @return the path
     */
    public static String getProjectPath() {
        URI uri = ProjectManager.getManager().getCurrentProject().getURI();
        return uri == null ? null : uri.toString();
    }
    
    /**
     * Saves the argo project
     */
    public static void saveProject() {
        ProjectBrowser.getInstance().trySave(true, new File(
                ProjectManager.getManager().getCurrentProject().getURI()),
                null);
    }
    
    /**
     * Saves the argo project silently.
     * @param path the path of the argo project
     */
    public static void saveProject(String path) {
        ProjectBrowser.getInstance().trySave(true, new File(path), null);
    }
    
    /**
     * Loads an argo project.
     * 
     * @param path
     *            the location of the file
     */
    public static void loadProject(String path) {
        Init.initialize(null);
        Model.getPump().startPumpingEvents();
        ProjectBrowser.getInstance().loadProject(new File(path), true, null);
        ProjectManager.getManager().getCurrentProject().setURI(
                new File(path).toURI());
    }
    
    /**
     * Closes the current argo project.
     */
    public static void closeProject() {        
        Model.getPump().flushModelEvents();
        Model.getPump().stopPumpingEvents();
        Model.getPump().flushModelEvents();
        Designer.disableCritiquing();
        Designer.clearCritiquing();
        ProjectBrowser.getInstance().clearDialogs();
        Project project = ProjectManager.getManager().getCurrentProject();
        // Some classes need to have a project always.
        // TODO: We should change those classes.
        ProjectManager.getManager().setCurrentProject(new Project());
        ProjectManager.getManager().removeProject(project);
    }
    
    /**
     * Exports as XMI.
     * @param file where to save
     */
    public static void exportXMI(File file) {
        String name = file.getName();
        ProjectBrowser.getInstance().trySave(true, file, null);
    }

}
