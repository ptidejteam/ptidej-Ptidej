import infovis.Tree;
import infovis.tree.DefaultTree;
import junit.framework.TestCase;

/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class TreeTest extends TestCase {

	/**
	 * Constructor for TreeTest.
	 */
	public TreeTest(String name) {
		super(name);
	}
	
	public void testTree() {
		Tree tree = new DefaultTree();
		
		int node = tree.addNode(Tree.ROOT);
		assertEquals(1, node);
	}

}
