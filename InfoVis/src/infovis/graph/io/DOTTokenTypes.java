// $ANTLR : "C:/eclipse/workspace/infovis/src/infovis/graph/io/DOT.g" -> "DOTLexer.java"$

/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the X11 Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.graph.io;

public interface DOTTokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int LITERAL_strict = 4;
	int ATOM = 5;
	int LCUR = 6;
	int RCUR = 7;
	int LITERAL_graph = 8;
	int LITERAL_digraph = 9;
	int LITERAL_node = 10;
	int LITERAL_edge = 11;
	int SEMI = 12;
	int D_EDGE_OP = 13;
	int ND_EDGE_OP = 14;
	int COMMA = 15;
	int LBR = 16;
	int RBR = 17;
	int EQUAL = 18;
	int LITERAL_subgraph = 19;
	int WS = 20;
	int ESC = 21;
	int COLON = 22;
}
