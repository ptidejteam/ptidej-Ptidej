// $ANTLR : "C:/eclipse/workspace/infovis/src/infovis/graph/io/DOT.g" -> "DOTParser.java"$

/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the X11 Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.graph.io;

import infovis.Column;
import infovis.Graph;
import infovis.column.StringColumn;
import infovis.io.AbstractReader;
import infovis.utils.IntVector;
import infovis.utils.RowIterator;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

import antlr.NoViableAltException;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.collections.impl.BitSet;

/**
 * Parser for the DOT Graph format.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */

public class DOTParser extends antlr.LLkParser
       implements DOTTokenTypes
 {

	Graph graph;
	HashMap nodeMap = new HashMap();
	StringColumn id;
	boolean updating;
	Rectangle2D.Float bbox;

	public Graph getGraph() {
		return this.graph;
	}
	
	public void setGraph(Graph graph) {
		this.graph = graph;
		this.id= StringColumn.findColumn(graph.getVertexTable(), "id");
		if (! this.id.isEmpty()) {
			for (RowIterator iter = this.id.iterator(); iter.hasNext(); ) {
				int vertex = iter.nextRow();
				this.nodeMap.put(this.id.get(vertex), Integer.valueOf(vertex));
			}
		}
	}
	
	public boolean isUpdating() {
		return this.updating;
	}
	
	public void setUpdating(boolean set) {
		this.updating = set;
	}
	
	public Rectangle2D.Float getBbox() {
		return this.bbox;
	}
	
	public int findVertex(String name) {
		Integer i = (Integer)this.nodeMap.get(name);
		if (i == null) {
			int v = this.graph.addVertex();
			i = Integer.valueOf(v);
			this.nodeMap.put(name, i);
			this.id.setExtend(v, name);
		}
		return i.intValue();
	}
	
	public int findEdge(int in, int out) {
		for (RowIterator iter = this.graph.edgeIterator(in); iter.hasNext(); ) {
			int edge = iter.nextRow();
			
			if (this.graph.getOutVertex(edge) == out)
				return edge;
		}
		return this.graph.addEdge(in, out);
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
			this.graph.getEdgeTable().getMetadata().put(metaName, value);
			if (name.equals("bb")) {
				this.bbox = parseBbox(value);
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
				Column c = this.graph.getEdgeTable().getColumn(colName);
				if (c == null) {
					c = createColumn(colName, value);
					this.graph.getEdgeTable().addColumn(c);
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
				Column c = this.graph.getVertexTable().getColumn(colName);
				if (c == null) {
					c = createColumn(colName, value);
					this.graph.getVertexTable().addColumn(c);
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

protected DOTParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  this.tokenNames = _tokenNames;
}

public DOTParser(TokenBuffer tokenBuf) {
  this(tokenBuf,2);
}

protected DOTParser(TokenStream lexer, int k) {
  super(lexer,k);
  this.tokenNames = _tokenNames;
}

public DOTParser(TokenStream lexer) {
  this(lexer,2);
}

public DOTParser(ParserSharedInputState state) {
  super(state,2);
  this.tokenNames = _tokenNames;
}

	public final void graph() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			hdr();
			body();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_0);
		}
	}
	
	public final void hdr() throws RecognitionException, TokenStreamException {
		
		Token  a = null;
		
		try {      // for error handling
			{
			{
			switch ( LA(1)) {
			case LITERAL_strict:
			{
				match(LITERAL_strict);
				break;
			}
			case LITERAL_graph:
			case LITERAL_digraph:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			graph_type();
			}
			{
			a = LT(1);
			match(ATOM);
			this.graph.setName(getText(a));
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_1);
		}
	}
	
	public final IntVector  body() throws RecognitionException, TokenStreamException {
		IntVector ret=null;
		
		
		try {      // for error handling
			match(LCUR);
			ret=opt_stmt_list();
			match(RCUR);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_2);
		}
		return ret;
	}
	
	public final void graph_type() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_graph:
			{
				match(LITERAL_graph);
				this.graph.setDirected(false);
				break;
			}
			case LITERAL_digraph:
			{
				match(LITERAL_digraph);
				this.graph.setDirected(true);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_3);
		}
	}
	
	public final IntVector  opt_stmt_list() throws RecognitionException, TokenStreamException {
		IntVector ret=new IntVector();
		
		IntVector iv;
		
		try {      // for error handling
			{
			_loop622:
			do {
				if ((_tokenSet_4.member(LA(1)))) {
					iv=stmt();
					append(ret, iv);
				}
				else {
					break _loop622;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_5);
		}
		return ret;
	}
	
	public final IntVector  stmt() throws RecognitionException, TokenStreamException {
		IntVector ret=null;
		
		ret = null; ArrayList la;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_graph:
			{
				match(LITERAL_graph);
				la=attr_list();
				addGraphAttributes(la);
				break;
			}
			case LITERAL_node:
			{
				match(LITERAL_node);
				la=attr_list();
				break;
			}
			case LITERAL_edge:
			{
				match(LITERAL_edge);
				la=attr_list();
				break;
			}
			case SEMI:
			{
				match(SEMI);
				break;
			}
			default:
				if ((LA(1)==ATOM) && (LA(2)==EQUAL)) {
					graph_attr_defs();
				}
				else if ((LA(1)==ATOM||LA(1)==LCUR||LA(1)==LITERAL_subgraph) && (_tokenSet_6.member(LA(2)))) {
					ret=compound();
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_7);
		}
		return ret;
	}
	
	public final ArrayList  attr_list() throws RecognitionException, TokenStreamException {
		 ArrayList ret=null;
		
		ret = new ArrayList();
		
		try {      // for error handling
			{
			_loop637:
			do {
				if ((LA(1)==LBR)) {
					match(LBR);
					attr_defs(ret);
					match(RBR);
				}
				else {
					break _loop637;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_7);
		}
		return ret;
	}
	
	public final void graph_attr_defs() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			attr_list_simple();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_7);
		}
	}
	
	public final IntVector  compound() throws RecognitionException, TokenStreamException {
		IntVector ret=null;
		
		int n; IntVector nl, el; ArrayList attrs; ret=new IntVector();
		
		try {      // for error handling
			if ((LA(1)==ATOM) && (_tokenSet_8.member(LA(2)))) {
				n=node_id();
				attrs=attr_list();
				
						ret.add(n);
						addNodeAttributes(ret,attrs);
					
			}
			else if ((LA(1)==ATOM) && (LA(2)==D_EDGE_OP||LA(2)==ND_EDGE_OP)) {
				n=node_id();
				el=edge_cont(nodeList(n));
				attrs=attr_list();
				
						addEdgeAttributes(el, attrs);
						ret.add(n);
						for (int i = 0; i < el.size(); i++) {
							ret.add(this.graph.getOutVertex(el.get(i)));
						}
					
			}
			else if ((LA(1)==LCUR||LA(1)==LITERAL_subgraph)) {
				nl=subgraph();
				el=edge_cont(nl);
				attrs=attr_list();
				addEdgeAttributes(el, attrs);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_7);
		}
		return ret;
	}
	
	public final int  node_id() throws RecognitionException, TokenStreamException {
		int n = Graph.NIL;
		
		
		try {      // for error handling
			n=node();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_6);
		}
		return n;
	}
	
	public final IntVector  edge_cont(
		IntVector vertices
	) throws RecognitionException, TokenStreamException {
		IntVector ret=null;
		
		int n; IntVector nl, el; ret = new IntVector();
		
		try {      // for error handling
			edge_op();
			{
			switch ( LA(1)) {
			case ATOM:
			{
				n=node_id();
				nl=nodeList(n);
				break;
			}
			case LCUR:
			case LITERAL_subgraph:
			{
				nl=subgraph();
				append(ret, nl);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			
					for (int i = 0; i < vertices.size(); i++) {
						for (int j = 0; j < nl.size(); j++) {
							int e = findEdge(vertices.get(i), nl.get(j));
							ret.add(e);
						}
					}
				
			{
			switch ( LA(1)) {
			case D_EDGE_OP:
			case ND_EDGE_OP:
			{
				el=edge_cont(nl);
				append(ret, el);
				break;
			}
			case ATOM:
			case LCUR:
			case RCUR:
			case LITERAL_graph:
			case LITERAL_node:
			case LITERAL_edge:
			case SEMI:
			case LBR:
			case LITERAL_subgraph:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_8);
		}
		return ret;
	}
	
	public final IntVector  subgraph() throws RecognitionException, TokenStreamException {
		IntVector ret=null;
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_subgraph:
			{
				match(LITERAL_subgraph);
				{
				switch ( LA(1)) {
				case ATOM:
				{
					match(ATOM);
					break;
				}
				case LCUR:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				break;
			}
			case LCUR:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			ret=body();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_2);
		}
		return ret;
	}
	
	public final void edge_op() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case D_EDGE_OP:
			{
				match(D_EDGE_OP);
				break;
			}
			case ND_EDGE_OP:
			{
				match(ND_EDGE_OP);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_9);
		}
	}
	
	public final int  node() throws RecognitionException, TokenStreamException {
		int node=Graph.NIL;
		
		Token  n = null;
		
		try {      // for error handling
			n = LT(1);
			match(ATOM);
			node = findVertex(getText(n));
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_10);
		}
		return node;
	}
	
	public final IntVector  simple() throws RecognitionException, TokenStreamException {
		IntVector ret = null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case ATOM:
			{
				ret=node_list();
				break;
			}
			case LCUR:
			case LITERAL_subgraph:
			{
				ret=subgraph();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_0);
		}
		return ret;
	}
	
	public final IntVector  node_list() throws RecognitionException, TokenStreamException {
		IntVector ret=null;
		
		int n; ret = new IntVector();
		
		try {      // for error handling
			n=node();
			ret.add(n);
			{
			_loop633:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					n=node();
					ret.add(n);
				}
				else {
					break _loop633;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_0);
		}
		return ret;
	}
	
	public final void attr_defs(
		ArrayList arr
	) throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			attr_item(arr);
			{
			_loop641:
			do {
				if ((LA(1)==ATOM||LA(1)==COMMA)) {
					{
					switch ( LA(1)) {
					case COMMA:
					{
						match(COMMA);
						break;
					}
					case ATOM:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					attr_item(arr);
				}
				else {
					break _loop641;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_11);
		}
	}
	
	public final void attr_item(
		ArrayList arr
	) throws RecognitionException, TokenStreamException {
		
		Token  a = null;
		Token  v = null;
		
		try {      // for error handling
			a = LT(1);
			match(ATOM);
			match(EQUAL);
			v = LT(1);
			match(ATOM);
			
				arr.add(getText(a));
				arr.add(getText(v));
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_12);
		}
	}
	
	public final void attr_list_simple() throws RecognitionException, TokenStreamException {
		
		try {      // for error handling
			LT(1);
			match(ATOM);
			match(EQUAL);
			LT(1);
			match(ATOM);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_7);
		}
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"\"strict\"",
		"an identifier",
		"LCUR",
		"RCUR",
		"\"graph\"",
		"\"digraph\"",
		"\"node\"",
		"\"edge\"",
		"SEMI",
		"D_EDGE_OP",
		"ND_EDGE_OP",
		"COMMA",
		"LBR",
		"RBR",
		"EQUAL",
		"\"subgraph\"",
		"WS",
		"ESC",
		"COLON"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 64L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 622050L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 32L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 531808L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { 128L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = { 622048L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = { 531936L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = { 597472L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	private static final long[] mk_tokenSet_9() {
		long[] data = { 524384L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
	private static final long[] mk_tokenSet_10() {
		long[] data = { 654818L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	private static final long[] mk_tokenSet_11() {
		long[] data = { 131072L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
	private static final long[] mk_tokenSet_12() {
		long[] data = { 163872L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
	
	}
