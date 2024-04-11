// $Id: ConfigurationEclipse.java 189 2006-08-18 22:18:45Z tfmorris $
// Copyright (c) 1996-2006 The Regents of the University of California. All
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

import java.io.File;
import java.net.URL;

import org.eclipse.jface.preference.IPreferenceStore;

import org.argouml.application.configuration.ConfigurationHandler;
import org.argouml.argoeclipse.internal.ui.Activator;

/**
 * An ArgoUML configuration handler which uses the Eclipse preference store.
 * This acts as an adapter for the interface that the main ArgoUML code
 * expects.
 * 
 * @author Tom Morris
 */
class ConfigurationEclipse extends ConfigurationHandler {

    /**
     * Our preference store.
     */
    private IPreferenceStore store;
    
    /**
     * The path to the file that we loaded preferences from.
     */
    private String preferenceLoadPath = getDefaultPath();
    
    /**
     * Anonymous constructor.
     */
    ConfigurationEclipse() {
	super(true); // config is changeable
    }

    /**
     * Returns the default configuration path.  Always returns the 
     * empty string for this implementation.
     *
     * @return the empty string.
     */
    public String getDefaultPath() {
        return ""; //$NON-NLS-1$
    }


    /**
     * Load the configuration from a specified location.
     *
     * @param file  the path to load the configuration from.
     *
     * @return true if the load was successful, false if not.
     */
    public boolean loadFile(File file) {
        // TODO: Do we want to allow anything other than the default? - tfm
        if (!(getDefaultPath().equals(file.getPath()))) {
//            store = new PreferenceStore(file.getPath());
//            preferenceLoadPath = file.getPath();
            return false;
        } else {
            store = Activator.getDefault().getPreferenceStore();
            preferenceLoadPath = file.getPath();
            return true;
        }
    }

    /**
     * Save the configuration to a specified location.
     *
     * @param file  the path to save the configuration at.
     *
     * @return true if the save was successful, false if not.
     */
    public boolean saveFile(File file) {
        // We only allow saving to the place we loaded from
        if (!preferenceLoadPath.equals(file.getPath())) {
            return false;
        }
        Activator.getDefault().savePluginPreferences();
        return true;
    }

    /**
     * Load the configuration from a URL.  
     * Throws an UnsupportedOperationException.
     *
     * @param url  the path to load the configuration from.
     *
     * @return true if the load was successful, false if not.
     */
    public boolean loadURL(URL url) {
        throw new UnsupportedOperationException();
    }

    /**
     * Save the configuration to a specified location.
     * Throws an UnsupportedOperationException.
     * 
     * @param url  the path to save the configuration at.
     *
     * @return true if the save was successful, false if not.
     */
    public boolean saveURL(URL url) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the string value of a configuration property.
     *
     * @param key the key to return the value of.
     * @param defaultValue the value to return if the key was not found.
     *
     * @return the string value of the key if found, otherwise null;
     */
    public String getValue(String key, String defaultValue) {
        // The semantics are slightly different between the ArgoUML
        // and Eclipse models, but this is pretty close.
        store.setDefault(key, defaultValue);
        return store.getString(key);
    }

    /**
     * Sets the string value of a configuration property.
     *
     * @param key the key to set.
     * @param value the value to set the key to.
     */
    public void setValue(String key, String value) {
        store.setValue(key, value);
    }

    /**
     * Remove a property.
     *
     * @param key The property to remove.
     */
    public void remove(String key) {
        // TODO: Should we throw an unsupported exception here?
        setValue(key, null);
    }

}

