// $ANTLR : "C:/eclipse/workspace/infovis/src/infovis/tree/io/Newick.g" -> "NewickParser.java"$

/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the X11 Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.tree.io;

public interface NewickTokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int COLON = 4;
	int SEMI = 5;
	int LPAREN = 6;
	int COMMA = 7;
	int RPAREN = 8;
	int LABEL = 9;
	int NUMBER = 10;
	int WS = 11;
	int ESC = 12;
}
