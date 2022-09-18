// $Id: Register.java 0.1.1 2006/09/15 00:00:00 b00__1 Exp $
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Only one editor or view can be opened at a time, so this class will
 * handle the registered objects (the objects that first asked for an editor
 * or view), and report if someone is already registered for an editor/view.
 * 
 * @author Bogdan Pistol
 */
public class Register {
    
    /**
     * The diagram editor.
     */
    public static final Integer EDITOR = Integer.valueOf(1);
    
    /**
     * The ToDo view.
     */
    public static final Integer TODO = Integer.valueOf(2);
    
    /**
     * The project browser view.
     */
    public static final Integer EXPLORER = Integer.valueOf(3);
    
    /**
     * The details view.
     */
    public static final Integer DETAILS = Integer.valueOf(4);
    
    private Map register = new HashMap();
    
    private static Register instance;
    
    private Register() {
    }
    
    /**
     * Factory method.
     * @return the instance
     */
    public static Register getInstance() {
        if (instance == null) {
            instance = new Register();
        }
        return instance;
    }
    
    /**
     * Determines if the candidate is registered for the specified pane.
     * @param pane the pane for wich the candidate is tested
     * @param candidate the object to test
     * @return true if it's registered or false
     */
    public boolean isRegistered(Integer pane, Object candidate) {
        if (pane != null && candidate != null) {
            Object o = register.get(pane);
            return o != null && o.equals(candidate);            
        }
        return false;
    }
    
    /**
     * Determines if some object is registered for the specified pane.
     * @param pane the pane for wich the candidate is tested
     * @return true if it's registered or false
     */
    public boolean isRegistered(Integer pane) {
        if (pane == null) {
            return false;
        }
        return register.containsKey(pane);
    }
    
    /**
     * Tries to register a candidate object with the pane.
     * @param pane the desired pane
     * @param candidate the object to register
     */
    public void register(Integer pane, Object candidate) {
        if (pane != EDITOR && pane != TODO && pane != DETAILS
                && pane != EXPLORER) {
            return; 
        }
        if (candidate == null) {
            return;
        }
        if (isRegistered(pane)) {
            return;
        }
        register.put(pane, candidate);
    }
    
    /**
     * Unregisters an object that was registered.
     * @param candidate the object to unregister
     */
    public void unregister(Object candidate) {
        if (candidate == null) {
            return;
        }
        if (!register.containsValue(candidate)) {
            return;
        }
        Set set = register.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue().equals(candidate)) {
                it.remove();
                break;
            }
        }
    }
    
    /**
     * Returns the current registered object.
     * @param pane the type of editor/view registered
     * @return the registered object or null
     */
    public Object getRegistered(Integer pane) {
        return register.get(pane);
    }

}
