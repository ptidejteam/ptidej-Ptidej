// $Id: PreferencePage0.java 189 2006-08-18 22:18:45Z tfmorris $
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

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.PathEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import org.argouml.application.api.Argo;
import org.argouml.argoeclipse.internal.ui.Activator;
import org.argouml.i18n.Translator;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class PreferencePage0
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

    /**
     * Construct a preference page.
     */
    public PreferencePage0() {
        super(GRID);
        setPreferenceStore(Activator.getDefault().getPreferenceStore());
        setDescription("ArgoEclipse general preferences");
    }

    /**
     * Creates the field editors. Field editors are abstractions of
     * the common GUI blocks needed to manipulate various types
     * of preferences. Each field editor knows how to save and
     * restore itself.
     */
    public void createFieldEditors() {
        this.

        // Preferences (General) tab settings
        addField(
                new BooleanFieldEditor(
                        PreferenceConstants.P_DEFER_INIT,
                        "&Defer initialization of ArgoEclipse",
                        getFieldEditorParent()));
        addField(
                new BooleanFieldEditor(
                        Argo.KEY_SPLASH.getKey(),
                        Translator.localize("label.splash"), //$NON-NLS-1$
                        getFieldEditorParent()));

        // Preload classes is not implemented for plugin
        // Reload last project is implemented by Eclipse logic

        addField(
                new BooleanFieldEditor(
                        Argo.KEY_XMI_STRIP_DIAGRAMS.getKey(),
                        Translator.localize("label.strip-diagrams"), //$NON-NLS-1$
                        getFieldEditorParent()));
        

        addField(new FileFieldEditor(PreferenceConstants.P_PROFILE, 
                Translator.localize("label.default-profile"),  //$NON-NLS-1$
                getFieldEditorParent()));
        
        addField(new PathEditor(PreferenceConstants.P_SEARCH_PATH, 
                "&Model search list:", "Model directory", 
                getFieldEditorParent()));
    }

    /**
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
    }

}