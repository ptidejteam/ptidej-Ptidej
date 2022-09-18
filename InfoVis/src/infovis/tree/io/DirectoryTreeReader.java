/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.tree.io;

import infovis.Tree;
import infovis.column.LongColumn;
import infovis.column.StringColumn;
import infovis.metadata.AggregationConstants;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Reader of Directory.
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class DirectoryTreeReader extends AbstractTreeReader {
    private String directory;
    private LongColumn lengthColumn;
    private StringColumn nameColumn;
    private LongColumn dateColumn;
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LENGTH = "length";
    public static final String COLUMN_DATE = "date";
	
    public DirectoryTreeReader(String directory, Tree tree) {
	super(null, "dir", tree);
	this.directory = directory;
	this.nameColumn = StringColumn.findColumn(tree, COLUMN_NAME);
	this.lengthColumn = LongColumn.findColumn(tree, COLUMN_LENGTH);
	this.dateColumn = LongColumn.findColumn(tree, COLUMN_DATE);
	this.lengthColumn.getMetadata().put(
            AggregationConstants.AGGREGATION_TYPE,
            AggregationConstants.AGGREGATION_TYPE_ATLEAF);
    }
	
    protected void disableNotify() {
	this.nameColumn.disableNotify();
	this.dateColumn.disableNotify();
	this.lengthColumn.disableNotify();
    }
	
    protected void enableNotify() {
	this.nameColumn.enableNotify();
	this.dateColumn.enableNotify();
	this.lengthColumn.enableNotify();
    }
	
    boolean readDirectory(File file, int parent) {
	int node = this.tree.addNode(parent);
		
	this.nameColumn.setExtend(node, file.getName());
	this.dateColumn.setExtend(node, file.lastModified());
		
	if (file.isDirectory()) {
	    //lengthColumn.setExtend(node, 0);
	    String[] list = file.list();
	    for (int i = 0; i < list.length; i++) {
		if (! readDirectory(new File(file, list[i]), node))
		    return false;
	    }
	    if (list.length == 0)
		this.lengthColumn.setExtend(node, 0);
	}
	else {
	    this.lengthColumn.setExtend(node, file.length());
	}
			
	return true;
    }
    /**
     * @see infovis.io.AbstractTableReader#load()
     */
    public boolean load() {
        boolean ret = false;
	disableNotify();
	try {
	    ret = readDirectory(new File(this.directory), Tree.ROOT);
	}
	finally {
	    enableNotify();
	}
        this.dateColumn.setFormat(new SimpleDateFormat("dd MMM yyyy HH:mm:ss"));
        return ret;
    }


}
