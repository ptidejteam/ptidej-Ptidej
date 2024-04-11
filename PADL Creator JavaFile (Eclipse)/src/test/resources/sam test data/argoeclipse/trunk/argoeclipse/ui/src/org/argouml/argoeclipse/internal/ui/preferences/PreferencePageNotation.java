// $Id: PreferencePage.java 43 2006-06-29 06:32:14Z tfmorris $
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
import org.eclipse.jface.preference.PathEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import org.argouml.argoeclipse.internal.ui.Activator;
import org.argouml.i18n.Translator;
import org.argouml.notation.Notation;

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

public class PreferencePageNotation
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

    /**
     * Construct a preference page.
     */
    public PreferencePageNotation() {
        super(GRID);
        setPreferenceStore(Activator.getDefault().getPreferenceStore());
        setDescription("ArgoEclipse notation preferences");
    }

    /**
     * Creates the field editors. Field editors are abstractions of
     * the common GUI blocks needed to manipulate various types
     * of preferences. Each field editor knows how to save and
     * restore itself.
     */
    public void createFieldEditors() {
       
        // Notations tab settings
        addField(new RadioGroupFieldEditor(
                PreferenceConstants.P_NOTATION_LANGUAGE,
                Translator.localize("label.notation-language"),
                1,
                new String[][] {
                    {"&UML", "uml"}, 
                    {"&Java", "java"}
                }, getFieldEditorParent()));

        addField(
                new BooleanFieldEditor(
                        Notation.KEY_USE_GUILLEMOTS.getKey(),
                        Translator.localize("label.use-guillemots"), //$NON-NLS-1$
                        getFieldEditorParent()));
        addField(
                new BooleanFieldEditor(
                        Notation.KEY_SHOW_VISIBILITY.getKey(),
                        Translator.localize("label.show-visibility"), //$NON-NLS-1$
                        getFieldEditorParent()));
        addField(
                new BooleanFieldEditor(
                        Notation.KEY_SHOW_MULTIPLICITY.getKey(),
                        Translator.localize("label.show-multiplicity"), //$NON-NLS-1$
                        getFieldEditorParent()));
        addField(
                new BooleanFieldEditor(
                        Notation.KEY_SHOW_INITIAL_VALUE.getKey(),
                        Translator.localize("label.show-initialvalue"), //$NON-NLS-1$
                        getFieldEditorParent()));
        addField(
                new BooleanFieldEditor(
                        Notation.KEY_SHOW_PROPERTIES.getKey(),
                        Translator.localize("label.show-properties"), //$NON-NLS-1$
                        getFieldEditorParent()));
        addField(
                new BooleanFieldEditor(
                        Notation.KEY_SHOW_TYPES.getKey(),
                        Translator.localize("label.show-types"), //$NON-NLS-1$
                        getFieldEditorParent()));
        addField(
                new BooleanFieldEditor(
                        Notation.KEY_SHOW_STEREOTYPES.getKey(),
                        Translator.localize("label.show-stereotypes"), //$NON-NLS-1$
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