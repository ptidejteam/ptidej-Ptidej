// $Id: LRU.java 0.1.1 2006/09/16 00:00:00 b00__1 Exp $
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

import java.util.prefs.Preferences;

/**
 * LRU = Last Recently Used
 * <p>
 * This class is responsible for remembering the user's options.
 * It's intended to ease the usage of the gui for the user.
 * 
 * @author Bogdan Pistol
 */
public class LRU {

    /**
     * String
     */
    public static final String IMPORT_SOURCES_FILESYSTEM_PATH = 
        "LRU_Import_Sources_Filesystem_Path"; //$NON-NLS-1$
    
    /**
     * int 1 or int 2
     */
    public static final String IMPORT_SOURCES_CHOOSER = 
        "LRU_Import_Sources_Chooser"; //$NON-NLS-1$
    
    /**
     * String
     */
    public static final String IMPORT_SOURCES_LANGUAGE = 
        "LRU_Import_Sources_Language"; //$NON-NLS-1$
    
    /**
     * Boolean
     */
    public static final String IMPORT_SOURCES_DESCEND = 
        "LRU_Import_Sources_Descend"; //$NON-NLS-1$
    
    /**
     * Boolean
     */
    public static final String IMPORT_SOURCES_CHANGED = 
        "LRU_Import_Sources_Changed"; //$NON-NLS-1$
    
    /**
     * Boolean
     */
    public static final String IMPORT_SOURCES_DIAGRAMS = 
        "LRU_Import_Sources_Diagrams"; //$NON-NLS-1$
    
    /**
     * Boolean
     */
    public static final String IMPORT_SOURCES_MINIMIZE = 
        "LRU_Import_Sources_Minimize"; //$NON-NLS-1$
    
    /**
     * Boolean
     */
    public static final String IMPORT_SOURCES_LAYOUT = 
        "LRU_Import_Sources_Layout"; //$NON-NLS-1$
    
    /**
     * int 0 or int 1 or int 2 
     */
    public static final String IMPORT_SOURCES_LEVEL = 
        "LRU_Import_Sources_Level"; //$NON-NLS-1$
    
    /**
     * String 
     */
    public static final String IMPORT_DEFAULT_PATH = 
        "LRU_Import_Default_Path"; //$NON-NLS-1$
    
    /**
     * String 
     */
    public static final String IMPORT_CHOSER_DEFAULT = 
        "LRU_Import_Choser_Default"; //$NON-NLS-1$
    
    /**
     * String 
     */
    public static final String EXPORT_DEFAULT_PATH = 
        "LRU_Export_Default_Path"; //$NON-NLS-1$
    
    /**
     * String 
     */
    public static final String EXPORT_CHOSER_DEFAULT = 
        "LRU_Export_Choser_Default"; //$NON-NLS-1$
    
    /**
     * Saves the value
     * @param key what to save
     * @param value the value to save
     */
    public static void put(String key, String value) {
        Preferences.userNodeForPackage(LRU.class).put(key, value);
    }
    
    /**
     * Getter for the value
     * @param key what to get
     * @return the value
     */
    public static String get(String key) {
        return Preferences.userNodeForPackage(LRU.class)
            .get(key, ""); //$NON-NLS-1$
    }
    
}
