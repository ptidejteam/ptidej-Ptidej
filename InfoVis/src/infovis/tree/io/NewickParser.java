// $ANTLR : "C:/eclipse/workspace/infovis/src/infovis/tree/io/Newick.g" -> "NewickParser.java"$

/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the X11 Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.tree.io;

import infovis.Tree;
import infovis.column.FloatColumn;
import infovis.column.StringColumn;
import infovis.utils.IntVector;
import antlr.NoViableAltException;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.collections.impl.BitSet;

/**
 * Parser for the Newick Tree format.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */

public class NewickParser extends antlr.LLkParser
       implements NewickTokenTypes
 {

	Tree tree;
	IntVector stack = new IntVector();
	StringColumn nameColumn;
	FloatColumn lengthColumn;
	String name;
	int serial;
	
	int current() { return this.stack.top(); }
	void push() { 
		this.stack.push(this.tree.addNode(current()));
	}
	
	void pop() {
		if (this.nameColumn.isValueUndefined(current())) {
			addName("?"+getName()+(this.serial++)+"?");
		}
		this.stack.pop();
	}
	
	void addName(String name) {
		if (this.nameColumn == null)
			this.nameColumn = StringColumn.findColumn(this.tree, "name");
		this.nameColumn.setExtend(current(), name);
	}
	
	void addLength(String length) {
		if (this.lengthColumn == null)
			this.lengthColumn = FloatColumn.findColumn(this.tree, "length");
		this.lengthColumn.setValueOrNullAt(current(), length);
	}
	
	String getName() { return this.name; }
	void setName(String name) { this.name = name; }
	
	Tree getTree() { return this.tree; }
	void setTree(Tree t) { this.tree = t; }

protected NewickParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  this.tokenNames = _tokenNames;
}

public NewickParser(TokenBuffer tokenBuf) {
  this(tokenBuf,2);
}

protected NewickParser(TokenStream lexer, int k) {
  super(lexer,k);
  this.tokenNames = _tokenNames;
}

public NewickParser(TokenStream lexer) {
  this(lexer,2);
}

public NewickParser(ParserSharedInputState state) {
  super(state,2);
  this.tokenNames = _tokenNames;
}

	public final void tree() throws RecognitionException, TokenStreamException {
		
		String name, length; this.stack.push(Tree.ROOT);
		
		try {      // for error handling
			descendant_list();
			{
			switch ( LA(1)) {
			case LABEL:
			{
				name=label();
				addName(name);
				break;
			}
			case COLON:
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case COLON:
			{
				match(COLON);
				length=branch_length();
				addLength(length);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(SEMI);
			pop();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_0);
		}
	}
	
	public final void descendant_list() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LPAREN);
			subtree();
			{
			_loop6:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					subtree();
				}
				else {
					break _loop6;
				}
				
			} while (true);
			}
			match(RPAREN);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_1);
		}
	}
	
	public final String  label() throws RecognitionException, TokenStreamException {
		 String ret = null;
		
		Token  l = null;
		
		try {      // for error handling
			l = LT(1);
			match(LABEL);
			ret = l.getText();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_2);
		}
		return ret;
	}
	
	public final String  branch_length() throws RecognitionException, TokenStreamException {
		 String ret = null;
		
		Token  n = null;
		
		try {      // for error handling
			n = LT(1);
			match(NUMBER);
			ret = n.getText();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_3);
		}
		return ret;
	}
	
	public final void subtree() throws RecognitionException, TokenStreamException {
		
		String name, length; push();
		
		try {      // for error handling
			switch ( LA(1)) {
			case LPAREN:
			{
				descendant_list();
				{
				switch ( LA(1)) {
				case LABEL:
				{
					name=label();
					addName(name);
					break;
				}
				case COLON:
				case COMMA:
				case RPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				{
				switch ( LA(1)) {
				case COLON:
				{
					match(COLON);
					length=branch_length();
					addLength(length);
					break;
				}
				case COMMA:
				case RPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				pop();
				break;
			}
			case LABEL:
			{
				name=label();
				addName(name);
				{
				switch ( LA(1)) {
				case COLON:
				{
					match(COLON);
					length=branch_length();
					addLength(length);
					break;
				}
				case COMMA:
				case RPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				pop();
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
			consumeUntil(_tokenSet_4);
		}
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"COLON",
		"SEMI",
		"LPAREN",
		"COMMA",
		"RPAREN",
		"LABEL",
		"NUMBER",
		"WS",
		"ESC"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 944L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 432L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 416L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 384L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	
	}
