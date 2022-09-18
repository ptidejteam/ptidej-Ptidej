// $ANTLR : "C:/eclipse/workspace/infovis/src/infovis/graph/io/DOT.g" -> "DOTLexer.java"$

/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the X11 Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.graph.io;

import java.io.InputStream;
import java.io.Reader;
import java.util.Hashtable;

import antlr.ANTLRHashString;
import antlr.ByteBuffer;
import antlr.CharBuffer;
import antlr.CharStreamException;
import antlr.CharStreamIOException;
import antlr.InputBuffer;
import antlr.LexerSharedInputState;
import antlr.NoViableAltForCharException;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.TokenStreamRecognitionException;
import antlr.collections.impl.BitSet;

/**
 * Lexer for the DOT Graph format.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class DOTLexer extends antlr.CharScanner implements DOTTokenTypes, TokenStream
 {
public DOTLexer(InputStream in) {
	this(new ByteBuffer(in));
}
public DOTLexer(Reader in) {
	this(new CharBuffer(in));
}
public DOTLexer(InputBuffer ib) {
	this(new LexerSharedInputState(ib));
}
public DOTLexer(LexerSharedInputState state) {
	super(state);
	this.caseSensitiveLiterals = true;
	setCaseSensitive(true);
	this.literals = new Hashtable();
	this.literals.put(new ANTLRHashString("edge", this), Integer.valueOf(11));
	this.literals.put(new ANTLRHashString("digraph", this), Integer.valueOf(9));
	this.literals.put(new ANTLRHashString("node", this), Integer.valueOf(10));
	this.literals.put(new ANTLRHashString("graph", this), Integer.valueOf(8));
	this.literals.put(new ANTLRHashString("strict", this), Integer.valueOf(4));
	this.literals.put(new ANTLRHashString("subgraph", this), Integer.valueOf(19));
}

public Token nextToken() throws TokenStreamException {
	tryAgain:
	for (;;) {
		int _ttype = Token.INVALID_TYPE;
		resetText();
		try {   // for char stream error handling
			try {   // for lexical error handling
				switch ( LA(1)) {
				case '"':  case '\'':  case '0':  case '1':
				case '2':  case '3':  case '4':  case '5':
				case '6':  case '7':  case '8':  case '9':
				case 'A':  case 'B':  case 'C':  case 'D':
				case 'E':  case 'F':  case 'G':  case 'H':
				case 'I':  case 'J':  case 'K':  case 'L':
				case 'M':  case 'N':  case 'O':  case 'P':
				case 'Q':  case 'R':  case 'S':  case 'T':
				case 'U':  case 'V':  case 'W':  case 'X':
				case 'Y':  case 'Z':  case '_':  case 'a':
				case 'b':  case 'c':  case 'd':  case 'e':
				case 'f':  case 'g':  case 'h':  case 'i':
				case 'j':  case 'k':  case 'l':  case 'm':
				case 'n':  case 'o':  case 'p':  case 'q':
				case 'r':  case 's':  case 't':  case 'u':
				case 'v':  case 'w':  case 'x':  case 'y':
				case 'z':
				{
					mATOM(true);
					break;
				}
				case '\t':  case '\n':  case '\r':  case ' ':
				{
					mWS(true);
					break;
				}
				case ';':
				{
					mSEMI(true);
					break;
				}
				case ',':
				{
					mCOMMA(true);
					break;
				}
				case '{':
				{
					mLCUR(true);
					break;
				}
				case '}':
				{
					mRCUR(true);
					break;
				}
				case '[':
				{
					mLBR(true);
					break;
				}
				case ']':
				{
					mRBR(true);
					break;
				}
				case '=':
				{
					mEQUAL(true);
					break;
				}
				case ':':
				{
					mCOLON(true);
					break;
				}
				default:
					if ((LA(1)=='-') && (LA(2)=='>')) {
						mD_EDGE_OP(true);
					}
					else if ((LA(1)=='-') && (LA(2)=='-')) {
						mND_EDGE_OP(true);
					}
				else {
					if (LA(1)==EOF_CHAR) {uponEOF(); this._returnToken = makeToken(Token.EOF_TYPE);}
				else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				}
				if ( this._returnToken==null ) continue tryAgain; // found SKIP token
				_ttype = this._returnToken.getType();
				this._returnToken.setType(_ttype);
				return this._returnToken;
			}
			catch (RecognitionException e) {
				throw new TokenStreamRecognitionException(e);
			}
		}
		catch (CharStreamException cse) {
			if ( cse instanceof CharStreamIOException ) {
				throw new TokenStreamIOException(((CharStreamIOException)cse).io);
			}
			else {
				throw new TokenStreamException(cse.getMessage());
			}
		}
	}
}

	public final void mATOM(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=this.text.length();
		_ttype = ATOM;
		int _saveIndex;
		
		switch ( LA(1)) {
		case '0':  case '1':  case '2':  case '3':
		case '4':  case '5':  case '6':  case '7':
		case '8':  case '9':  case 'A':  case 'B':
		case 'C':  case 'D':  case 'E':  case 'F':
		case 'G':  case 'H':  case 'I':  case 'J':
		case 'K':  case 'L':  case 'M':  case 'N':
		case 'O':  case 'P':  case 'Q':  case 'R':
		case 'S':  case 'T':  case 'U':  case 'V':
		case 'W':  case 'X':  case 'Y':  case 'Z':
		case '_':  case 'a':  case 'b':  case 'c':
		case 'd':  case 'e':  case 'f':  case 'g':
		case 'h':  case 'i':  case 'j':  case 'k':
		case 'l':  case 'm':  case 'n':  case 'o':
		case 'p':  case 'q':  case 'r':  case 's':
		case 't':  case 'u':  case 'v':  case 'w':
		case 'x':  case 'y':  case 'z':
		{
			{
			int _cnt650=0;
			_loop650:
			do {
				switch ( LA(1)) {
				case 'a':  case 'b':  case 'c':  case 'd':
				case 'e':  case 'f':  case 'g':  case 'h':
				case 'i':  case 'j':  case 'k':  case 'l':
				case 'm':  case 'n':  case 'o':  case 'p':
				case 'q':  case 'r':  case 's':  case 't':
				case 'u':  case 'v':  case 'w':  case 'x':
				case 'y':  case 'z':
				{
					matchRange('a','z');
					break;
				}
				case 'A':  case 'B':  case 'C':  case 'D':
				case 'E':  case 'F':  case 'G':  case 'H':
				case 'I':  case 'J':  case 'K':  case 'L':
				case 'M':  case 'N':  case 'O':  case 'P':
				case 'Q':  case 'R':  case 'S':  case 'T':
				case 'U':  case 'V':  case 'W':  case 'X':
				case 'Y':  case 'Z':
				{
					matchRange('A','Z');
					break;
				}
				case '_':
				{
					match('_');
					break;
				}
				case '0':  case '1':  case '2':  case '3':
				case '4':  case '5':  case '6':  case '7':
				case '8':  case '9':
				{
					matchRange('0','9');
					break;
				}
				default:
				{
					if ( _cnt650>=1 ) { break _loop650; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				}
				_cnt650++;
			} while (true);
			}
			break;
		}
		case '"':
		{
			_saveIndex=this.text.length();
			match('"');
			this.text.setLength(_saveIndex);
			{
			_loop652:
			do {
				if ((LA(1)=='\\')) {
					mESC(false);
				}
				else if ((_tokenSet_0.member(LA(1)))) {
					matchNot('"');
				}
				else {
					break _loop652;
				}
				
			} while (true);
			}
			_saveIndex=this.text.length();
			match('"');
			this.text.setLength(_saveIndex);
			break;
		}
		case '\'':
		{
			_saveIndex=this.text.length();
			match('\'');
			this.text.setLength(_saveIndex);
			{
			_loop654:
			do {
				if ((LA(1)=='\\')) {
					mESC(false);
				}
				else if ((_tokenSet_1.member(LA(1)))) {
					matchNot('\'');
				}
				else {
					break _loop654;
				}
				
			} while (true);
			}
			_saveIndex=this.text.length();
			match('\'');
			this.text.setLength(_saveIndex);
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		_ttype = testLiteralsTable(_ttype);
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(this.text.getBuffer(), _begin, this.text.length()-_begin));
		}
		this._returnToken = _token;
	}
	
	protected final void mESC(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=this.text.length();
		_ttype = ESC;
		match('\\');
		{
		switch ( LA(1)) {
		case 'n':
		{
			match('n');
			break;
		}
		case 'N':
		{
			match('N');
			break;
		}
		case 'r':
		{
			match('r');
			break;
		}
		case 't':
		{
			match('t');
			break;
		}
		case 'b':
		{
			match('b');
			break;
		}
		case 'f':
		{
			match('f');
			break;
		}
		case '"':
		{
			match('"');
			break;
		}
		case '\n':
		{
			match('\n');
			break;
		}
		case '\r':
		{
			match('\r');
			break;
		}
		case '\'':
		{
			match('\'');
			break;
		}
		case '\\':
		{
			match('\\');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(this.text.getBuffer(), _begin, this.text.length()-_begin));
		}
		this._returnToken = _token;
	}
	
	public final void mWS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=this.text.length();
		_ttype = WS;
		{
		switch ( LA(1)) {
		case ' ':
		{
			match(' ');
			break;
		}
		case '\t':
		{
			match('\t');
			break;
		}
		case '\n':
		{
			match('\n');
			newline();
			break;
		}
		case '\r':
		{
			match('\r');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		_ttype = Token.SKIP;
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(this.text.getBuffer(), _begin, this.text.length()-_begin));
		}
		this._returnToken = _token;
	}
	
	public final void mD_EDGE_OP(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=this.text.length();
		_ttype = D_EDGE_OP;
		match("->");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(this.text.getBuffer(), _begin, this.text.length()-_begin));
		}
		this._returnToken = _token;
	}
	
	public final void mND_EDGE_OP(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=this.text.length();
		_ttype = ND_EDGE_OP;
		match("--");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(this.text.getBuffer(), _begin, this.text.length()-_begin));
		}
		this._returnToken = _token;
	}
	
	public final void mSEMI(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=this.text.length();
		_ttype = SEMI;
		match(';');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(this.text.getBuffer(), _begin, this.text.length()-_begin));
		}
		this._returnToken = _token;
	}
	
	public final void mCOMMA(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=this.text.length();
		_ttype = COMMA;
		match(',');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(this.text.getBuffer(), _begin, this.text.length()-_begin));
		}
		this._returnToken = _token;
	}
	
	public final void mLCUR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=this.text.length();
		_ttype = LCUR;
		match('{');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(this.text.getBuffer(), _begin, this.text.length()-_begin));
		}
		this._returnToken = _token;
	}
	
	public final void mRCUR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=this.text.length();
		_ttype = RCUR;
		match('}');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(this.text.getBuffer(), _begin, this.text.length()-_begin));
		}
		this._returnToken = _token;
	}
	
	public final void mLBR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=this.text.length();
		_ttype = LBR;
		match('[');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(this.text.getBuffer(), _begin, this.text.length()-_begin));
		}
		this._returnToken = _token;
	}
	
	public final void mRBR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=this.text.length();
		_ttype = RBR;
		match(']');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(this.text.getBuffer(), _begin, this.text.length()-_begin));
		}
		this._returnToken = _token;
	}
	
	public final void mEQUAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=this.text.length();
		_ttype = EQUAL;
		match('=');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(this.text.getBuffer(), _begin, this.text.length()-_begin));
		}
		this._returnToken = _token;
	}
	
	public final void mCOLON(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=this.text.length();
		_ttype = COLON;
		match(':');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(this.text.getBuffer(), _begin, this.text.length()-_begin));
		}
		this._returnToken = _token;
	}
	
	
	private static final long[] mk_tokenSet_0() {
		long[] data = new long[8];
		data[0]=-17179869192L;
		data[1]=-268435457L;
		for (int i = 2; i<=3; i++) { data[i]=-1L; }
		for (int i = 4; i<=7; i++) { data[i]=0L; }
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = new long[8];
		data[0]=-549755813896L;
		data[1]=-268435457L;
		for (int i = 2; i<=3; i++) { data[i]=-1L; }
		for (int i = 4; i<=7; i++) { data[i]=0L; }
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	
	}
