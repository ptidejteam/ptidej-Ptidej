/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.tree.io;

import infovis.Tree;
import infovis.io.AbstractTableReader;

import java.io.BufferedReader;

/**
 * Abstract Reader for Trees.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public abstract class AbstractTreeReader extends AbstractTableReader {
    protected Tree tree;
	
    protected AbstractTreeReader(BufferedReader in, String name, Tree tree) {
	super(in, name, tree);
	this.tree = tree;
    }
	
	

}
