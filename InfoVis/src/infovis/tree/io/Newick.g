header {
/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the X11 Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.tree.io;
}

{
import infovis.Tree;
import infovis.utils.IntVector;
import infovis.column.StringColumn;
import infovis.column.FloatColumn;

/**
 * Parser for the Newick Tree format.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.10 $
 */
}
class NewickParser extends Parser;
options {
	exportVocab=Newick;
	k = 2;
}

{
	Tree tree;
	IntVector stack = new IntVector();
	StringColumn nameColumn;
	FloatColumn lengthColumn;
	String name;
	int serial;
	
	int current() { return stack.top(); }
	void push() { 
		stack.push(tree.addNode(current()));
	}
	
	void pop() {
		if (nameColumn.isValueUndefined(current())) {
			addName("?"+getName()+(serial++)+"?");
		}
		stack.pop();
	}
	
	void addName(String name) {
		if (nameColumn == null)
			nameColumn = StringColumn.findColumn(tree, "name");
		nameColumn.setExtend(current(), name);
	}
	
	void addLength(String length) {
		if (lengthColumn == null)
			lengthColumn = FloatColumn.findColumn(tree, "length");
		lengthColumn.setValueOrNullAt(current(), length);
	}
	
	String getName() { return name; }
	void setName(String name) { this.name = name; }
	
	Tree getTree() { return tree; }
	void setTree(Tree t) { tree = t; }
}

tree{ String name, length; stack.push(Tree.ROOT); } :
	descendant_list
	( name=label { addName(name); } )? ( COLON length=branch_length { addLength(length); })? SEMI
	{ pop(); }
	;

descendant_list :
	LPAREN subtree ( COMMA subtree )* RPAREN
	;

subtree{ String name, length; push(); } :
	descendant_list ( name=label { addName(name); })? ( COLON length=branch_length { addLength(length); })?
		{ pop(); }
	|	name=label { addName(name); } ( COLON length=branch_length { addLength(length); } )?
		{ pop(); }
	;

label returns [ String ret = null]:
	l:LABEL	{ ret = l.getText(); }
	;
	
branch_length returns [ String ret = null]:
	n:NUMBER { ret = n.getText(); }
	;

{
/**
 * Lexer for the Newick Tree format.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.10 $
 */
}	
class NewickLexer extends Lexer;
options {
	exportVocab=Newick;
	k=4;
}

LABEL
    :   ( 'a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '.' | '0'..'9')*
    |   '"' (ESC|~'"')* '"'
    |   '\'' (ESC|~'\'')* '\''
    ;

WS	:	(' '
	|	'\t'
	|	'\n'	{newline();}
	|	'\r')
		{ _ttype = Token.SKIP; }
	;

NUMBER
	:	('+' | '-' )? 
		(	('0'..'9')+ "." ('0'..'9')* 
		|	"." ('0'..'9')+
		)
	;

protected
ESC
    :   '\\'
		(	'n'
		|	'r'
		|	't'
		|	'b'
		|	'f'
		|	'"'
		|	'\''
		|	'\\'
		)
    ;

 COLON : ":" ;
 SEMI : ";" ;
 LPAREN : "(" ;
 RPAREN : ")" ;
 COMMA : "," ;
 
 
 
