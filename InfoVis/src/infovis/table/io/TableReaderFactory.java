/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.table.io;

import infovis.Table;
import infovis.io.AbstractReader;
import infovis.io.AbstractReaderFactory;
import infovis.tree.DefaultTree;
import infovis.tree.io.DirectoryTreeReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Factory of table readers.
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class TableReaderFactory extends AbstractReaderFactory {
    static TableReaderFactory sharedInstance = new TableReaderFactory();

    protected void addDefaultCreators() {
	add(new AbstractCreator(".csv") {
		public AbstractReader doCreate(
					       String name,
					       Table table,
					       boolean decompress)
		    throws IOException, FileNotFoundException {
		    return new CSVTableReader(open(name, decompress), name, table);
		}
	    });
	add(new AbstractCreator(".tqd") {
		public AbstractReader doCreate(
					       String name,
					       Table table,
					       boolean decompress)
		    throws IOException, FileNotFoundException {
		    return new TQDTableReader(open(name, decompress), table);
		}
	    });
	add(new Creator() {
		public AbstractReader create(
					     String name,
					     Table table) {
		    File f = new File(name);
		    if (! f.isDirectory()) 
			return null;
		    return new DirectoryTreeReader(name, new DefaultTree(table));
		}
	    });
    }
	
    public static TableReaderFactory sharedInstance() {
	return sharedInstance;
    }
	
    public static AbstractReader createReader(String name, Table table) {
	return sharedInstance().create(name, table);
    }
    
    public static boolean readTable(String name, Table table) {
        return sharedInstance().tryRead(name, table);
    }
}
