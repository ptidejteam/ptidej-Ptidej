/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.visualization;

import infovis.Graph;
import infovis.Table;
import infovis.Tree;
import infovis.Visualization;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Class VisualizationFactory
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class VisualizationFactory {
    static VisualizationFactory sharedInstance;
    
    Map creators = new HashMap();
    
    public VisualizationFactory(){
        createDefaults();
    }
    
    protected void createDefaults() {
        // avoid loading all the visualization class at startup
        setDefault("Scatter Plot", Table.class,
            "infovis.table.visualization.ScatterPlotVisualization");
        setDefault("Time Series", Table.class,
            "infovis.table.visualization.TimeSeriesVisualization");
        setDefault("Icicle Tree", Tree.class,
            "infovis.tree.visualization.IcicleTreeVisualization");
        setDefault("Tree Node-Link Diagram", Tree.class,
            "infovis.tree.visualization.NodeLinkTreeVisualization");
        setDefault("Treemap", Tree.class,
            "infovis.tree.visualization.TreemapVisualization");
        setDefault("Matrix Graph", Graph.class,
            "infovis.graph.visualization.MatrixVisualization");
        setDefault("Graph Node-Link Diagram", Graph.class,
            "infovis.graph.visualization.NodeLinkGraphVisualization");
    }
    
    public static VisualizationFactory sharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new VisualizationFactory();
        }
        return sharedInstance;
    }
    
    public static void setSharedInstance(VisualizationFactory shared) {
        sharedInstance = shared;
    }
    
    public void add(Creator creator) {
        this.creators.put(creator.getName(), creator);
    }
    
    public void setDefault(String name, Class tableClass, String visualizationClass) {
        add(new DefaultCreator(name, tableClass, visualizationClass));
    }
    
    public Creator[] getCompatibleCreators(Table table) {
        ArrayList list = new ArrayList();
        for (Iterator iter = iterator(table); iter.hasNext(); ) {
            Creator c = (Creator)iter.next();
            list.add(c);
        }
        Creator[] creators = new Creator[list.size()];
        list.toArray(creators);
        return creators;
    }
    
    /**
     * Returns an iterator over the visualization creators 
     * compatible with the specified table.
     * 
     * @param table the table
     * @return an iterator over the visualization creators
     * compatible with the specified table.
     */    
    public Iterator iterator(Table table) {
        return new VisualizationIterator(table); 
    }
    
    class VisualizationIterator implements Iterator {
        Iterator entriesIterator;
        Table table;
        Creator current;
        
        public VisualizationIterator(Table table) {
            this.table = table;
            this.entriesIterator = VisualizationFactory.this.creators.entrySet().iterator();
            skipIncompatible();
        }
        
        void skipIncompatible() {
            while (this.entriesIterator.hasNext()) {
                Map.Entry entry = (Map.Entry)this.entriesIterator.next();
                this.current = (Creator)entry.getValue();
                if (this.current.isCompatible(this.table))
                    return;                 
            }
            this.current = null;
        }
        
        public boolean hasNext() {
            return this.current != null;
        }
        
        public Object next() {
            Creator ret = this.current;
            this.current = null;
            skipIncompatible();
            return ret;
        }
        public void remove() {
        }

    }

    public interface Creator {
         Visualization create(Table table);
         String getName();
         boolean isCompatible(Table table);
    }
    
    public static class DefaultCreator implements Creator {
        String name;
        Class tableClass;
        String visualizationClass;
        
        public DefaultCreator(String name, Class tableClass, String visualizationClass) {
            this.name = name;
            this.tableClass = tableClass;
            this.visualizationClass = visualizationClass; 
        }
        
        public String getName() {
            return this.name;
        }
        
        public boolean isCompatible(Table table) {
            return this.tableClass.isAssignableFrom(table.getClass());
        }
        
        public Visualization create(Table table) {
            Class c;
            try {
                c = Class.forName(this.visualizationClass);
            }
            catch(ClassNotFoundException e) {
                return null;
            }
            Class[] parameterTypes = { this.tableClass };
            
            Constructor cons;
            try {
                cons = c.getConstructor(parameterTypes);
            }
            catch (NoSuchMethodException e) {
                return null;
            }
            if (cons != null) {
                Object[] args = { table };
                try {
                    return (Visualization)cons.newInstance(args);
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
