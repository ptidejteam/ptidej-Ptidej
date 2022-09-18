/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.tree.io;

import infovis.Table;
import infovis.Tree;
import infovis.io.AbstractReader;
import infovis.io.AbstractReaderFactory;
import infovis.tree.DefaultTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Factory of tree readers.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class TreeReaderFactory extends AbstractReaderFactory {
    static TreeReaderFactory sharedInstance = new TreeReaderFactory();

    /**
     * Returns a tree from a table.
     *
     * @param table the table.
     *
     * @return a tree from a table.
     */
    public static Tree getTree(Table table) {
        if (table instanceof Tree) {
            return (Tree)table;
        }
        return new DefaultTree(table.getTable());
    }

    protected void addDefaultCreators() {
        add(new AbstractCreator(".xml") {
                public AbstractReader doCreate(String name, Table table,
                                               boolean decompress)
                                        throws IOException, 
                                               FileNotFoundException {
                    return new XMLTreeReader(open(name, decompress), name,
                                             getTree(table));
                }
            });
        add(new AbstractCreator(".tm3") {
                public AbstractReader doCreate(String name, Table table,
                                               boolean decompress)
                                        throws IOException, 
                                               FileNotFoundException {
                    return new TM3TreeReader(open(name, decompress), name,
                                             getTree(table));
                }
            });
        add(new AbstractCreator(".nh") {
                public AbstractReader doCreate(String name, Table table,
                                               boolean decompress)
                                        throws IOException, 
                                               FileNotFoundException {
                    return new NewickTreeReader(open(name, decompress), name,
                                                getTree(table));
                }
            });
        add(new Creator() {
                public AbstractReader create(String name, Table table) {
                    File f = new File(name);
                    if (!f.isDirectory())
                        return null;
                    return new DirectoryTreeReader(name, getTree(table));
                }
            });
    }

    /**
     * Returns the shared instance of this <code>TreeReaderFactory</code>
     *
     * @return the shared instance of this <code>TreeReaderFactory</code>
     */
    public static TreeReaderFactory sharedInstance() {
        return sharedInstance;
    }

    /**
     * Creates a tree reader from a specified resource name and a tree
     *
     * @param name the resource name
     * @param tree the tree
     *
     * @return a tree reader or <code>null</code>.
     */
    public static AbstractReader createReader(String name, Tree tree) {
        return sharedInstance().create(name, tree);
    }
    
    public static boolean readTree(String name, Tree tree) {
        return sharedInstance().tryRead(name, tree);
    }
}
