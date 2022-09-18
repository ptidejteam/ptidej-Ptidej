/* This source file is part of ogre4j
 *     (The JNI bindings for OGRE)
 * For the latest info, see http://www.ogre4j.org/
 * 
 * Copyright (c) 2005 netAllied GmbH, Tettnang
 * Also see acknowledgements in Readme.html
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA, or go to
 * http://www.gnu.org/copyleft/lesser.txt.
 * 
 * 
 * Library.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.1 $
 * $Date: 2005/07/29 09:58:55 $
 * $Author: yavin02 $
 */
package org.ogre4j;

public class Library {

/**
 * Returns the platform name.
 *
 * @return the platform name of the currently running SWT
 */
static String getPlatform () {
    String [] names = new String [] {"motif", "gtk", "win32", "photon", "carbon"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
    for (int i = 0; i < names.length; i++) {
        try {
            Class.forName("org.eclipse.swt.internal."+names[i]+".OS"); //$NON-NLS-1$ //$NON-NLS-2$
            return names[i];
        } catch (ClassNotFoundException e) {
        }
    }
    return "unknown"; //$NON-NLS-1$
}

/**
 * Loads the shared library that matches the version of the
 * Java code which is currently running.  SWT shared libraries
 * follow an encoding scheme where the major, minor and revision
 * numbers are embedded in the library name and this along with
 * <code>name</code> is used to load the library.  If this fails,
 * <code>name</code> is used in another attempt to load the library,
 * this time ignoring the SWT version encoding scheme.
 *
 * @param name the name of the library to load
 */
public static void loadLibrary (String name) {
    /*
     * Include platform name to support different windowing systems
     * on same operating system.
     */
    //String platform = getPlatform ();
    
    try
    {
        String newName = name; // + "-" + platform; //$NON-NLS-1$ //$NON-NLS-2$
        System.loadLibrary (newName);
        return;
    } catch (UnsatisfiedLinkError e1) {     
        throw e1;
    }
}

}
