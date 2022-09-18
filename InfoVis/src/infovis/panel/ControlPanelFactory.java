/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.panel;

import infovis.Visualization;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class ControlPanelFactory {
    static ControlPanelFactory sharedInstance;
    
    Map creators = new HashMap();

    /**
     * Constructor for ControlPanelFactory.
     */
    public ControlPanelFactory() {
        createDefaults();
    }
    
    protected void createDefaults() {
        put(Visualization.class,
            new Creator() {
            public ControlPanel create(Visualization visualization, Class c) {
                if (c == Visualization.class) {
                    return new ControlPanel(visualization);
                }
                return null;
            }
        });
    }

    public static ControlPanelFactory sharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new ControlPanelFactory();
        }
        return sharedInstance;
    }
    
    public static void setSharedInstance(ControlPanelFactory shared) {
        sharedInstance = shared;
    }
    
    /**
     * Creates a Control Panel from a Visualization.
     *
     * @param c The Visualization.
     *
     * @return A Control Panel.
     */
    public ControlPanel createControlPanel(Visualization visualization) {
        ControlPanel ret = null;
        Class visClass = Visualization.class;
        for (Class c = visualization.getClass(); visClass.isAssignableFrom(c); c = c.getSuperclass()) {
            Creator creator = get(c);
            if (creator != null)
                ret = creator.create(visualization, c);
            if (ret != null)
                break;
        }
        return ret;
    }

    /**
     * Adds a default creator for a specific kind of visualization.
     *
     * @param c The creator
     */
    public void put(Class c, Creator creator) {
        assert(Visualization.class.isAssignableFrom(c));
        
        this.creators.put(c, creator);
    }
    
    public void put(Class visClass, Class cpClass) {
        put(visClass, new DefaultCreator(cpClass));
    }
    
    public void setDefault(Class visClass, Class cpClass) {
        if (get(visClass) == null)
            put(visClass, cpClass);
    }
    
    public Creator get(Class c) {
        return (Creator)this.creators.get(c);
    }

    public interface Creator {
        ControlPanel create(Visualization visualization, Class c);
    }
    
    public static class DefaultCreator implements Creator {
        Class controlPanelClass;
        
        public DefaultCreator(Class c) {
            assert(ControlPanel.class.isAssignableFrom(c));
            this.controlPanelClass = c;
        }
        
        public ControlPanel create(Visualization visualization, Class c) {
            Class[] parameterTypes = { c };
            Constructor cons;
            try {
                cons = this.controlPanelClass.getConstructor(parameterTypes);
            }
            catch (NoSuchMethodException e) {
                return null;
            }
            if (cons != null) {
                Object[] args = { visualization };
                try {
                    return (ControlPanel)cons.newInstance(args);
                }
                catch (InstantiationException e) {
                }
                catch (IllegalAccessException e) {
                }
                catch (InvocationTargetException e) {
                }
            }
            return null;
        }
        
    }
}
