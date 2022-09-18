/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.tree.io;

import infovis.Tree;
import infovis.io.AbstractTableWriter;

import java.io.Writer;

/**
 * Abstract Writer for Trees.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public abstract class AbstractTreeWriter extends AbstractTableWriter {
    protected Tree tree;
	
    protected AbstractTreeWriter(Writer out, Tree tree) {
	super(out, tree);
	this.tree = tree;
    }
}
