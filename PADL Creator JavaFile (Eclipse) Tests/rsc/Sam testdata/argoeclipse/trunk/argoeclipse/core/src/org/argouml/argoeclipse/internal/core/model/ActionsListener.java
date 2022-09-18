// $Id: ActionsListener.java 0.1.1 2006/09/16 00:00:00 b00__1 Exp $
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tigris.gef.base.Editor;
import org.tigris.gef.base.Globals;
import org.tigris.gef.graph.GraphModel;

import org.argouml.cognitive.Designer;
import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.ui.ProjectBrowser;
import org.argouml.ui.targetmanager.TargetEvent;
import org.argouml.ui.targetmanager.TargetListener;
import org.argouml.ui.targetmanager.TargetManager;
import org.argouml.uml.diagram.UMLMutableGraphSupport;
import org.argouml.uml.ui.ActionSaveProject;

/**
 * This class is responsible with supplying all the neccessary events from
 * ArgoUML to whatever listener needs it.
 * @author Bogdan Pistol
 */
public class ActionsListener {
    
    private static ActionsListener instance;
    
    /**
     * ActionsListener for Save action
     */
    public static final String SAVE = "save"; //$NON-NLS-1$
    
    /**
     * ActionsListener for changes to the argo project
     */
    public static final String PROJECT = "project"; //$NON-NLS-1$
    
    /**
     * ActionsListener for changes to the target
     */
    public static final String TARGET = "target"; //$NON-NLS-1$
    
    private Map values = new HashMap();
    
    private List listeners = new ArrayList();
    
    private List listenerTypes = new ArrayList();
    
    private ActionsListener() {
        values.put(SAVE, Boolean.valueOf(false));
        values.put(PROJECT, null);
        values.put(TARGET, null);
        
        ((ActionSaveProject) ProjectBrowser.getInstance().getSaveAction())
            .addPropertyChangeListener(getSaveListener());
        ProjectManager.getManager().addPropertyChangeListener(
                getProjectListener());
        TargetManager.getInstance().addTargetListener(
                getTargetListener());
    }
    
    private PropertyChangeListener getSaveListener() {
        return new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                notifyListeners(SAVE, evt.getNewValue());
            }
            
        };
    }
    
    private PropertyChangeListener getProjectListener() {
        return new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(
                        ProjectManager.CURRENT_PROJECT_PROPERTY_NAME)) {
                    Project p = (Project) evt.getNewValue();
                    if (p != null) {
                        Designer.setCritiquingRoot(p);
                        TargetManager.getInstance().setTarget(
                                p.getInitialTarget());
                        notifyListeners(PROJECT, evt.getNewValue());
                    }
                }
            }

        };
    }
    
    private TargetListener getTargetListener() {
        return new TargetListener() {
            
            /**
             * Enabled the remove action if an item is selected in anything
             * other then the activity or state diagrams.
             */
            private void determineRemoveEnabled() {
                Editor editor = Globals.curEditor();
                Collection figs = editor.getSelectionManager().getFigs();
                boolean removeEnabled = !figs.isEmpty();
                GraphModel gm = editor.getGraphModel();
                if (gm instanceof UMLMutableGraphSupport) {
                    removeEnabled = ((UMLMutableGraphSupport) gm)
                        .isRemoveFromDiagramAllowed(figs);
                }
                ProjectBrowser.getInstance().getRemoveFromDiagramAction()
                        .setEnabled(removeEnabled);
            }

            public void targetAdded(TargetEvent e) {
                determineRemoveEnabled();
                notifyListeners(TARGET, e.getNewTarget());
            }

            public void targetRemoved(TargetEvent e) {
                notifyListeners(TARGET, e.getNewTarget());
            }

            public void targetSet(TargetEvent e) {
                notifyListeners(TARGET, e.getNewTarget());
            }

        };
    }
    
    private void notifyListeners(String type, Object newValue) {
        for (int i = 0; i < listeners.size(); i++) {
            if (listenerTypes.get(i).equals(type)) {
                ((PropertyChangeListener) listeners.get(i))
                        .propertyChange(new PropertyChangeEvent(
                                this, type, values.get(type), newValue));
            }
        }        
        values.put(type, newValue);
    }
    
    /**
     * Factory method.
     * @return the ActionsListener instance
     */
    public static ActionsListener getInstance() {
        if (instance == null) {
            instance = new ActionsListener();
        }
        return instance;
    }
    
    /**
     * Adds a listener.
     * @param listener the listener
     * @param action the event that the listener listens
     */
    public void addListener(PropertyChangeListener listener, String action) {
        if (listener != null && action != null 
                && !(listeners.contains(listener)
                && listenerTypes.get(listeners.indexOf(listener)) == action)) {
            listeners.add(listener);
            listenerTypes.add(action);
        }
    }
   
    /**
     * Removes a listener.
     * @param listener the listener
     * @param action the event that the listener listens
     */
    public void removeListener(PropertyChangeListener listener, String action) {
        if (listener != null && action != null && listeners.contains(listener)
                && listenerTypes.get(listeners.indexOf(listener)) == action) {
            listeners.remove(listener);
            listenerTypes.remove(action);
        }
    }
    
    /**
     * Determines if the action is enabled.
     * @param action the action to query 
     * @return the last event the action received or null for errors or
     * uninitialized
     */
    public Object getEvent(String action) {
        if (action == null) {
            return null;
        }
        if (values.containsKey(action)) {
            return values.get(action);
        }
        return null;
    }
    
    /**
     * Sets an event for broadcast.
     * @param action the type of action
     * @param event the event to send
     */
    public void setEvent(String action, Object event) {
        notifyListeners(action, event);
    }

}
