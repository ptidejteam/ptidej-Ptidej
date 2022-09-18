header {
/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the X11 Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.graph.io;
}

{
import infovis.Graph;
import infovis.Table;
import infovis.Column;
import infovis.io.AbstractReader;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.ArrayList;
import infovis.utils.IntVector;
import infovis.column.StringColumn;
import infovis.utils.RowIterator;

/**
 * Parser for the DOT Graph format.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.11 $
 */
}	
class DOTParser extends Parser;
options {
	exportVocab=DOT;
	k = 2;
}

{
	Graph graph;
	HashMap nodeMap = new HashMap();
	StringColumn id;
	boolean updating;
	Rectangle2D.Float bbox;

	public Graph getGraph() {
		return graph;
	}
	
	public void setGraph(Graph graph) {
		this.graph = graph;
		id= StringColumn.findColumn(graph.getVertexTable(), "id");
		if (! id.isEmpty()) {
			for (RowIterator iter = id.iterator(); iter.hasNext(); ) {
				int vertex = iter.nextRow();
				nodeMap.put(id.get(vertex), new Integer(vertex));
			}
		}
	}
	
	public boolean isUpdating() {
		return updating;
	}
	
	public void setUpdating(boolean set) {
		updating = set;
	}
	
	public Rectangle2D.Float getBbox() {
		return bbox;
	}
	
	public int findVertex(String name) {
		Integer i = (Integer)nodeMap.get(name);
		if (i == null) {
			int v = graph.addVertex();
			i = new Integer(v);
			nodeMap.put(name, i);
			id.setExtend(v, name);
		}
		return i.intValue();
	}
	
	public int findEdge(int in, int out) {
		for (RowIterator iter = graph.edgeIterator(in); iter.hasNext(); ) {
			int edge = iter.nextRow();
			
			if (graph.getOutVertex(edge) == out)
				return edge;
		}
		return graph.addEdge(in, out);
	}
	
	void append(IntVector into, IntVector from) {
		if (from == null)
			return;
		for (int i = 0; i < from.size(); i++) {
			into.add(from.get(i));
		}
	}
	
	IntVector nodeList(int node) {
		IntVector ret = new IntVector();
		ret.add(node);
		return ret;
	}
	
	protected Column createColumn(String name, String field) {
		return AbstractReader.createColumn(AbstractReader.guessFieldType(field), name);
	}

	Rectangle2D.Float parseBbox(String value) {
		int index = value.indexOf(',');
		int xmin = Integer.parseInt(value.substring(0, index));
		int prev;
		prev = index+1;
		index = value.indexOf(',', prev);
		int ymin = Integer.parseInt(value.substring(prev, index));
		prev = index+1;
		index = value.indexOf(',', prev);
		int xmax = Integer.parseInt(value.substring(prev, index));
		prev = index+1;
		int ymax = Integer.parseInt(value.substring(prev));
		
		return new Rectangle2D.Float(xmin, ymin, xmax - xmin, ymax-ymin);
	}
	
	void addGraphAttributes(ArrayList attrs) {
		for (int j = 0; j < attrs.size(); j += 2) {
			String name = (String)attrs.get(j);
			String metaName = "dot_"+name;
			String value = (String)attrs.get(j+1);
			graph.getEdgeTable().getMetadata().put(metaName, value);
			if (name.equals("bb")) {
				bbox = parseBbox(value);
			}
		}
	}
	
	void addEdgeAttributes(IntVector el, ArrayList attrs) {
		for (int i = 0; i < el.size(); i++) {
			int e = el.get(i);
			for (int j = 0; j < attrs.size(); j += 2) {
				String name = (String)attrs.get(j);
				String colName = "#dot_"+name;
				String value = (String)attrs.get(j+1);
				Column c = graph.getEdgeTable().getColumn(colName);
				if (c == null) {
					c = createColumn(colName, value);
					graph.getEdgeTable().addColumn(c);
				}
				c.setValueOrNullAt(e, value);
			}
		}
	}
	
	void addNodeAttributes(IntVector nl, ArrayList attrs) {
		for (int i = 0; i < nl.size(); i++) {
			int v = nl.get(i);
			for (int j = 0; j < attrs.size(); j += 2) {
				String name = (String)attrs.get(j);
				String colName = "#dot_"+name;
				String value = (String)attrs.get(j+1);
				Column c = graph.getVertexTable().getColumn(colName);
				if (c == null) {
					c = createColumn(colName, value);
					graph.getVertexTable().addColumn(c);
				}
				c.setValueOrNullAt(v, value);
			}
		}
	}
	
	String getText(Token t) {
		String s = t.getText();
		int index = s.indexOf('\\');
		
		if (index == -1) {
			return s;
		}
		StringBuffer buffer = new StringBuffer();
		int prev = 0;
		while (index != -1) {
			buffer.append(s.substring(prev, index));
			prev = index+2;
			if ((index+1) == s.length()) {
				return buffer.toString();
			}
		    char c = s.charAt(index+1);
            if (c == '\r') {
                if (prev < s.length() && s.charAt(prev) == '\n')
                    prev++;
            }
            else if (c != 'N' && c != '\n') { // ignore \N for now
            	switch(c) {
            		case 't': c = '\t'; break;
            	}
				buffer.append(c);
			}
			index = s.indexOf('\\', prev);
		}
		buffer.append(s.substring(prev));
		return buffer.toString();
	}
}

graph
	:	hdr body
	;

hdr
    :   (	(	"strict"	)? graph_type
    	)
        ( a:ATOM { graph.setName(getText(a)); } )
    ;

body returns [IntVector ret=null]
	:	LCUR ret=opt_stmt_list RCUR
	;

graph_type
	:	"graph"		{ graph.setDirected(false); }
    |   "digraph"	{ graph.setDirected(true); }
    ;

opt_stmt_list returns [IntVector ret=new IntVector()]
	{ IntVector iv; }
    :   ( iv=stmt 	{ append(ret, iv); } )*
    ;

stmt returns [IntVector ret=null]
	{ ret = null; ArrayList la; }
    :   "graph" la=attr_list { addGraphAttributes(la); }
    |	"node"	la=attr_list
    |	"edge"	la=attr_list
    |   graph_attr_defs 
	| 	ret=compound
    |	SEMI
    ;

compound returns [IntVector ret=null]
	{ int n; IntVector nl, el; ArrayList attrs; ret=new IntVector(); }
    :   n=node_id	attrs=attr_list
    	{
    		ret.add(n);
    		addNodeAttributes(ret,attrs);
    	}
    |	n=node_id	el=edge_cont[nodeList(n)] attrs=attr_list
    	{ 
    		addEdgeAttributes(el, attrs);
    		ret.add(n);
    		for (int i = 0; i < el.size(); i++) {
    			ret.add(graph.getOutVertex(el.get(i)));
    		}
    	}
    |	nl=subgraph	el=edge_cont[nl] attrs=attr_list
    	{ addEdgeAttributes(el, attrs); }
    ;

edge_cont [IntVector vertices] returns [IntVector ret=null]
	{ int n; IntVector nl, el; ret = new IntVector(); }
	:	edge_op
		(	n=node_id	{ nl=nodeList(n); }
		|	nl=subgraph	{ append(ret, nl); }
		)
	{
		for (int i = 0; i < vertices.size(); i++) {
			for (int j = 0; j < nl.size(); j++) {
				int e = findEdge(vertices.get(i), nl.get(j));
				ret.add(e);
			}
		}
	}
	( el=edge_cont[nl]	{ append(ret, el); } )?
	;

node_id returns [int n = Graph.NIL]
	:	n=node
	;

simple returns[IntVector ret = null]
    :   ret=node_list
    |   ret=subgraph
    ;

edge_op
    :   D_EDGE_OP
    |   ND_EDGE_OP
    ;

node_list returns[IntVector ret=null]
	{ int n; ret = new IntVector(); }
    :   n=node  { ret.add(n); }
        ( COMMA n=node	{ ret.add(n); } )*
    ;

node returns[int node=Graph.NIL]
    :   n:ATOM	{ node = findVertex(getText(n)); }
    ;

attr_list returns [ ArrayList ret=null]
	{ ret = new ArrayList(); }
    :   ( LBR attr_defs[ret] RBR )*
    ;

attr_defs [ArrayList arr]
    :   attr_item[arr] ( ( COMMA )? attr_item[arr] )*
    ;

attr_item [ArrayList arr]
    :   a:ATOM EQUAL v:ATOM	
    {
    	arr.add(getText(a));
    	arr.add(getText(v));
    }
    ;

graph_attr_defs
	:	attr_list_simple
	;

attr_list_simple
    :   a:ATOM EQUAL v:ATOM
    ;

subgraph returns[IntVector ret=null]
    :   ( "subgraph"( ATOM )? )?  ret=body
    ;

/**
 * Lexer for the DOT Graph format.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.11 $
 */
class DOTLexer extends Lexer;
options {
	exportVocab=DOT;
	k=4;
	charVocabulary = '\3'..'\377';
	testLiterals = false;
}

ATOM
options {
	testLiterals = true;
	paraphrase = "an identifier";
}
    :   ( 'a'..'z' | 'A'..'Z' | '_' | '0'..'9' )+
    |   '"'! (ESC|~'"')* '"'!
    |   '\''! (ESC|~'\'')* '\''!
    ;

WS	:	(' '
	|	'\t'
	|	'\n'	{newline();}
	|	'\r')
		{ _ttype = Token.SKIP; }
	;

protected
ESC
    :   '\\'
		(	'n'
		|	'N'
		|	'r'
		|	't'
		|	'b'
		|	'f'
		|	'"'
		|	'\n'
		|	'\r'
		|	'\''
		|	'\\'
		)
    ;

D_EDGE_OP
    :   "->"
    ;

ND_EDGE_OP
    :   "--"
    ;

SEMI
    :   ';'
    ;

COMMA
    :   ','
    ;

LCUR
    :   '{'
    ;

RCUR
    :   '}'
    ;

LBR
    :   '['
    ;

RBR
    :   ']'
    ;

EQUAL
    :   '='
    ;

COLON
    :   ':'
    ;
