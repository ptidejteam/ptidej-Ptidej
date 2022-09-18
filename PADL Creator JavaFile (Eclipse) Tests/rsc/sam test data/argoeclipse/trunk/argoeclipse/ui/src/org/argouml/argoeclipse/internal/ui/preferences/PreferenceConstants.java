// $Id: PreferenceConstants.java 189 2006-08-18 22:18:45Z tfmorris $
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

package org.argouml.argoeclipse.internal.ui.preferences;

import org.argouml.application.api.Configuration;
import org.argouml.application.api.ConfigurationKey;

/**
 * Constant definitions for plug-in preferences
 */

public class PreferenceConstants {

    /**
     * File containing the profile to use.
     */
    public static final String P_PROFILE = "profilePreference"; //$NON-NLS-1$

    /**
     * Preference setting which controls whether initialization is deferred.
     */
    public static final String P_DEFER_INIT = 
        "deferInitializationPreference"; //$NON-NLS-1$

    /**
     * Preference setting for fill color.
     */
    public static final String P_COLOR_FILL = 
        "fillColorPreference"; //$NON-NLS-1$

    /**
     * Preference setting for font.
     */
    public static final String P_FONT = "fontPreference"; //$NON-NLS-1$
    
    /**
     * Preference setting containing search path for external references.
     */
    public static final String P_SEARCH_PATH = 
        "searchPathPreference"; //$NON-NLS-1$
    
    /**
     * Default notation language for a new project.
     */
    public static final String P_NOTATION_LANGUAGE = 
        "notationLanguage"; //$NON-NLS-1$
    
    /**
     * Key for default startup directory.
     */
    public static final ConfigurationKey KEY_STARTUP_DIR =
        Configuration.makeKey(
                "default",  //$NON-NLS-1$
                "user",  //$NON-NLS-1$
                "dir");   //$NON-NLS-1$

    /**
     * Key to show splash screen.
     */
    public static final ConfigurationKey KEY_SPLASH =
        Configuration.makeKey("init", "splash");  //$NON-NLS-1$//$NON-NLS-2$

    /**
     * Key to preload classes.
     */
    public static final ConfigurationKey KEY_PRELOAD =
        Configuration.makeKey("init", "preload");  //$NON-NLS-1$//$NON-NLS-2$

    /**
     * Key to report usage statistics.
     */
    public static final ConfigurationKey KEY_EDEM =
        Configuration.makeKey("init", "edem");  //$NON-NLS-1$//$NON-NLS-2$

    /**
     * Key for last saved project URL.
     */
    public static final ConfigurationKey KEY_MOST_RECENT_PROJECT_FILE =
        Configuration.makeKey(
                "project",  //$NON-NLS-1$
                "mostrecent",  //$NON-NLS-1$
                "file"); //$NON-NLS-1$

    /**
     * Key to reload last saved project on startup.
     */
    public static final ConfigurationKey KEY_RELOAD_RECENT_PROJECT =
        Configuration.makeKey(
                "init",  //$NON-NLS-1$
                "project",  //$NON-NLS-1$
                "loadmostrecent"); //$NON-NLS-1$

    /**
     * Key for number of last recently used file entries in menu list.
     */
    public static final ConfigurationKey KEY_NUMBER_LAST_RECENT_USED =
        Configuration.makeKey("project",  //$NON-NLS-1$
                "mostrecent", "maxNumber");  //$NON-NLS-1$//$NON-NLS-2$

    /**
     * Key for theme.
     */
    public static final ConfigurationKey KEY_SCREEN_THEME =
        Configuration.makeKey("screen", "theme");  //$NON-NLS-1$//$NON-NLS-2$

    /**
     * Key for look and feel class name.
     */
    public static final ConfigurationKey KEY_LOOK_AND_FEEL_CLASS =
        Configuration.makeKey("screen",  //$NON-NLS-1$
                "lookAndFeelClass"); //$NON-NLS-1$

    /**
     * Key for theme class name.
     */
    public static final ConfigurationKey KEY_THEME_CLASS =
        Configuration.makeKey("screen",  //$NON-NLS-1$
                "themeClass");  //$NON-NLS-1$

    /**
     * Key to enable smooth edges of diagram text and lines (anti-aliasing).
     */
    public static final ConfigurationKey KEY_SMOOTH_EDGES =
        Configuration.makeKey("screen",  //$NON-NLS-1$
                "diagram-antialiasing"); //$NON-NLS-1$


    /**
     * Key for user java reverse engineering classpath.
     */
    public static final ConfigurationKey KEY_USER_IMPORT_CLASSPATH =
        Configuration.makeKey("import",  //$NON-NLS-1$
                "clazzpath");  //$NON-NLS-1$

    public static final ConfigurationKey KEY_INPUT_SOURCE_ENCODING =
        Configuration.makeKey("import",  //$NON-NLS-1$
                "file", "encoding");  //$NON-NLS-1$//$NON-NLS-2$
    
    public static final ConfigurationKey KEY_XMI_STRIP_DIAGRAMS =
        Configuration.makeKey("import",  //$NON-NLS-1$
                "xmi", "stripDiagrams");  //$NON-NLS-1$//$NON-NLS-2$

    public static final ConfigurationKey KEY_DEFAULT_MODEL =
        Configuration.makeKey("defaultModel"); //$NON-NLS-1$

    public static final ConfigurationKey KEY_USER_EXPLORER_PERSPECTIVES =
        Configuration.makeKey("explorer",  //$NON-NLS-1$
                "perspectives"); //$NON-NLS-1$

    public static final ConfigurationKey KEY_LOCALE =
        Configuration.makeKey("locale"); //$NON-NLS-1$

    public static final String CONSOLE_LOG = 
        "argo.console.log"; //$NON-NLS-1$
    public static final String ARGO_CONSOLE_SUPPRESS = 
        "argo.console.suppress"; //$NON-NLS-1$
    public static final String ARGO_CONSOLE_PREFIX = 
        "argo.console.prefix"; //$NON-NLS-1$


}
