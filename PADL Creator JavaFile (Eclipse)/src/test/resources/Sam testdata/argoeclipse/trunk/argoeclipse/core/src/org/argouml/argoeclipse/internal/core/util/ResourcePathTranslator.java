// $Id: ResourcePathTranslator.java 216 2006/08/27 00:00:00 b00__1 Exp $
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

package org.argouml.argoeclipse.internal.core.util;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * This class is responsible for converting Eclipse resources into java.io.File
 * and find out if a java.io.File corresponds to a resource from the
 * Eclipse workspace.
 *
 * @author Bogdan Pistol
 */
public class ResourcePathTranslator {
    
    /**
     * Verifies if the workspace resource with this path exists in the 
     * filesystem and returns it's filesystem path.
     * 
     * @param workspacePath the path of the resource in the Eclipse workspace
     * @return if it exists returns the path in the filesystem otherwise returns
     *         null
     */
    public static String getFilesystemPath(String workspacePath) {
        String result = null;

        IPath path = new Path(workspacePath);
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IFile file = null;

        file = root.getFile(path);       

        if (file != null && file.exists()) {
            result = file.getLocation().toOSString();
        } else {
            IProject project = root.getProject(path.segment(0));

            if (project.exists()) {
                path = path.removeFirstSegments(1);
                if (path.segmentCount() == 0) {
                    result = project.getLocation().toOSString();
                } else {
                    IFolder folder = project.getFolder(path);
                    if (folder.exists()) {
                        result = folder.getLocation().toOSString();
                    }
                }
            }
        }

        return result;
    }
    
    /**
     * Verifies if the filesystem resource exists in the workspace and returns
     * it's path in the workspace.
     * 
     * @param filesystemPath the path in the filesystem
     * @return if it exists returns the path in the workspace otherwise returns
     *         null
     */
    public static String getWorkspacePath(String filesystemPath) {
        String result = null;

        IPath path = new Path(filesystemPath);
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IFile file = null;

        try {
            file = root.getFileForLocation(path);
        } catch (RuntimeException e) {
        }

        if (file != null && file.exists()) {
            result = file.getFullPath().toOSString();
        } else {
            IContainer[] container = root.findContainersForLocation(path);
            if (container != null && container.length == 1) {
                result = container[0].getFullPath().toOSString();
            }
        }

        return result;
    }

}
