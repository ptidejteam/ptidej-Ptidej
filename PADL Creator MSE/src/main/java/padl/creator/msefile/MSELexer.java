/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package padl.creator.msefile;

import padl.creator.msefile.javacup.runtime.Symbol;
import util.multilingual.MultilingualManager;

public class MSELexer implements padl.creator.msefile.javacup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	//	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 65536;
	private final int YY_EOF = 65537;

	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;
	//	private final boolean yy_eof_done = false;

	private final int YYINITIAL = 0;

	private final int COMMENT = 1;

	private final int yy_state_dtrans[] = { 0, 19 };

	private boolean yy_last_was_cr = false;
	private final int YY_E_INTERNAL = 0;
	//	private final int YY_E_MATCH = 1;
	private final java.lang.String yy_error_string[] = {
			"Error: Internal error.\n", "Error: Unmatched input.\n" };
	private final int yy_acpt[] = { this./* 0 */YY_NO_ANCHOR,
			this./* 1 */YY_NO_ANCHOR, this./* 2 */YY_NO_ANCHOR,
			this./* 3 */YY_NO_ANCHOR, this./* 4 */YY_NO_ANCHOR,
			this./* 5 */YY_NO_ANCHOR, this./* 6 */YY_NO_ANCHOR,
			this./* 7 */YY_NO_ANCHOR, this./* 8 */YY_NO_ANCHOR,
			this./* 9 */YY_NO_ANCHOR, this./* 10 */YY_NO_ANCHOR,
			this./* 11 */YY_NOT_ACCEPT, this./* 12 */YY_NO_ANCHOR,
			this./* 13 */YY_NO_ANCHOR, this./* 14 */YY_NO_ANCHOR,
			this./* 15 */YY_NO_ANCHOR, this./* 16 */YY_NO_ANCHOR,
			this./* 17 */YY_NO_ANCHOR, this./* 18 */YY_NO_ANCHOR,
			this./* 19 */YY_NO_ANCHOR, this./* 20 */YY_NO_ANCHOR,
			this./* 21 */YY_NO_ANCHOR, this./* 22 */YY_NO_ANCHOR,
			this./* 23 */YY_NO_ANCHOR, this./* 24 */YY_NO_ANCHOR,
			this./* 25 */YY_NO_ANCHOR, this./* 26 */YY_NOT_ACCEPT,
			this./* 27 */YY_NO_ANCHOR, this./* 28 */YY_NO_ANCHOR,
			this./* 29 */YY_NO_ANCHOR, this./* 30 */YY_NO_ANCHOR,
			this./* 31 */YY_NO_ANCHOR, this./* 32 */YY_NO_ANCHOR,
			this./* 33 */YY_NOT_ACCEPT, this./* 34 */YY_NO_ANCHOR,
			this./* 35 */YY_NO_ANCHOR, this./* 36 */YY_NOT_ACCEPT,
			this./* 37 */YY_NO_ANCHOR, this./* 38 */YY_NO_ANCHOR,
			this./* 39 */YY_NO_ANCHOR, this./* 40 */YY_NO_ANCHOR,
			this./* 41 */YY_NO_ANCHOR, this./* 42 */YY_NO_ANCHOR,
			this./* 43 */YY_NO_ANCHOR, this./* 44 */YY_NO_ANCHOR,
			this./* 45 */YY_NO_ANCHOR, this./* 46 */YY_NO_ANCHOR,
			this./* 47 */YY_NO_ANCHOR, this./* 48 */YY_NO_ANCHOR,
			this./* 49 */YY_NO_ANCHOR, this./* 50 */YY_NO_ANCHOR,
			this./* 51 */YY_NO_ANCHOR, this./* 52 */YY_NO_ANCHOR,
			this./* 53 */YY_NO_ANCHOR, this./* 54 */YY_NO_ANCHOR,
			this./* 55 */YY_NO_ANCHOR, this./* 56 */YY_NO_ANCHOR,
			this./* 57 */YY_NO_ANCHOR, this./* 58 */YY_NO_ANCHOR };
	private final int yy_cmap[] =
		this
			.unpackFromString(
				1,
				65538,
				"23:9,24,19,23,24,22,23:18,24,23:2,29,23:3,20,17,18,16,28,23,28,27,21,26:10,"
						+ "1,23:6,25:26,23:6,11,25:2,3,5,6,25,14,2,25:2,12,8,25:2,7,25,4,15,9,13,10,25"
						+ ":4,23:65413,0:2")[0];
	private final int yy_rmap[] =
		this
			.unpackFromString(
				1,
				59,
				"0,1:2,2,1:4,3,1,4,3,1,5:6,6,7,1,8,9,5,1,10,8,11,8,10,12,10,7,13,14,11,15,16"
						+ ",17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36")[0];
	private final int yy_nxt[][] =
		this
			.unpackFromString(
				37,
				30,
				"1,2,3,24:3,53,48,24,49,54,24:5,4,5,6,7,8,25,7,25,7,24,23,32,23,9,-1:32,24,1"
						+ "0,24:12,-1:9,24:3,-1:3,11:19,12,11:9,-1:2,24:2,44,24:11,-1:9,24:3,-1:4,24:1"
						+ "4,-1:9,24:3,-1:2,1,27:15,20,27:2,21,27,28,29,27:6,22,-1,27:15,34,27:2,-1,27"
						+ ",-1,27:8,-1,27:15,33,27:2,-1,27,36,27:8,-1:26,23,26,-1:28,30,-1:4,27:15,-1,"
						+ "27:2,-1,27,37,27:8,-1:2,24:12,13,24,-1:9,24:3,-1:3,27:15,34,27:2,-1,27,36,2"
						+ "7:8,-1:2,24:3,14,24:10,-1:9,24:3,-1:3,27:15,33,27:2,-1,27,37,27:8,-1:2,24:4"
						+ ",15,24:9,-1:9,24:3,-1:4,24:3,16,24:10,-1:9,24:3,-1:4,24:3,17,24:10,-1:9,24:"
						+ "3,-1:4,24:3,18,24:10,-1:9,24:3,-1:4,24:7,31,24:6,-1:9,24:3,-1:4,24:11,35,24"
						+ ":2,-1:9,24:3,-1:4,24:3,38,24:10,-1:9,24:3,-1:4,24:13,39,-1:9,24:3,-1:4,24:1"
						+ "1,40,24:2,-1:9,24:3,-1:4,24:8,41,24:5,-1:9,24:3,-1:4,24:2,58,24:6,42,24:4,-"
						+ "1:9,24:3,-1:4,24:2,43,24:11,-1:9,24:3,-1:4,24:10,45,24:3,-1:9,24:3,-1:4,24:"
						+ "10,46,24:3,-1:9,24:3,-1:4,47,24:13,-1:9,24:3,-1:4,24:9,50,24:4,-1:9,24:3,-1"
						+ ":4,24:9,51,24:4,-1:9,24:3,-1:4,24:7,52,24:6,-1:9,24:3,-1:4,55,24:13,-1:9,24"
						+ ":3,-1:4,24:6,56,24:7,-1:9,24:3,-1:4,57,24:13,-1:9,24:3,-1:2");
	private MSELexer() {
		this.yy_buffer = new char[this.YY_BUFFER_SIZE];
		this.yy_buffer_read = 0;
		this.yy_buffer_index = 0;
		this.yy_buffer_start = 0;
		this.yy_buffer_end = 0;
		this.yychar = 0;
		this.yyline = 0;
		this.yy_at_bol = true;
		this.yy_lexical_state = this.YYINITIAL;
	}
	public MSELexer(final java.io.InputStream instream) {
		this();
		if (null == instream) {
			throw new Error("Error: Bad input stream initializer.");
		}
		this.yy_reader =
			new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}
	public MSELexer(final java.io.Reader reader) {
		this();
		if (null == reader) {
			throw new Error("Error: Bad input stream initializer.");
		}
		this.yy_reader = new java.io.BufferedReader(reader);
	}
	public padl.creator.msefile.javacup.runtime.Symbol next_token()
			throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = this.YY_NO_ANCHOR;
		int yy_state = this.yy_state_dtrans[this.yy_lexical_state];
		int yy_next_state = this.YY_NO_STATE;
		int yy_last_accept_state = this.YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		this.yy_mark_start();
		yy_this_accept = this.yy_acpt[yy_state];
		if (this.YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			this.yy_mark_end();
		}
		while (true) {
			if (yy_initial && this.yy_at_bol) {
				yy_lookahead = this.YY_BOL;
			}
			else {
				yy_lookahead = this.yy_advance();
			}
			yy_next_state = this.YY_F;
			yy_next_state =
				this.yy_nxt[this.yy_rmap[yy_state]][this.yy_cmap[yy_lookahead]];
			if (this.YY_EOF == yy_lookahead && true == yy_initial) {
				return null;
			}
			if (this.YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = this.yy_acpt[yy_state];
				if (this.YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					this.yy_mark_end();
				}
			}
			else {
				if (this.YY_NO_STATE == yy_last_accept_state) {
					throw new Error("Lexical Error: Unmatched Input.");
				}
				else {
					yy_anchor = this.yy_acpt[yy_last_accept_state];
					if (0 != (this.YY_END & yy_anchor)) {
						this.yy_move_end();
					}
					this.yy_to_mark();
					switch (yy_last_accept_state) {
						case 0 :
							{
								return new Symbol(
									MSESymbols.INTEGER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -2 :
							break;
						case 1 :

						case -3 :
							break;
						case 2 :
							{
								return new Symbol(MSESymbols.COLON, ":");
							}
						case -4 :
							break;
						case 3 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -5 :
							break;
						case 4 :
							{
								return new Symbol(MSESymbols.STAR, "*");
							}
						case -6 :
							break;
						case 5 :
							{
								return new Symbol(MSESymbols.LPAREN, "(");
							}
						case -7 :
							break;
						case 6 :
							{
								return new Symbol(MSESymbols.RPAREN, ")");
							}
						case -8 :
							break;
						case 7 :
							{ /* Ignore white space */
							}
						case -9 :
							break;
						case 8 :
							{
								System.err.println("Illegal character: "
										+ this.yytext());
							}
						case -10 :
							break;
						case 9 :
							{
								this.yybegin(this.COMMENT);
							}
						case -11 :
							break;
						case 10 :
							{
								return new Symbol(MSESymbols.ID, "id");
							}
						case -12 :
							break;
						case 12 :
							{
								return new Symbol(
									MSESymbols.STRING,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext().substring(
										1,
										this.yytext().length() - 2));
							}
						case -13 :
							break;
						case 13 :
							{
								return new Symbol(MSESymbols.PATH, "path");
							}
						case -14 :
							break;
						case 14 :
							{
								return new Symbol(MSESymbols.TRUE, "true");
							}
						case -15 :
							break;
						case 15 :
							{
								return new Symbol(MSESymbols.IDREF, "idref");
							}
						case -16 :
							break;
						case 16 :
							{
								return new Symbol(MSESymbols.FALSE, "false");
							}
						case -17 :
							break;
						case 17 :
							{
								return new Symbol(MSESymbols.VALUE, "value");
							}
						case -18 :
							break;
						case 18 :
							{
								return new Symbol(
									MSESymbols.PRIMITIVE,
									"primitive");
							}
						case -19 :
							break;
						case 19 :
							{
							}
						case -20 :
							break;
						case 20 :
							{
							}
						case -21 :
							break;
						case 21 :
							{
								this.yybegin(this.YYINITIAL);
							}
						case -22 :
							break;
						case 22 :
							{
							}
						case -23 :
							break;
						case 23 :
							{
								return new Symbol(
									MSESymbols.INTEGER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -24 :
							break;
						case 24 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -25 :
							break;
						case 25 :
							{
								System.err.println("Illegal character: "
										+ this.yytext());
							}
						case -26 :
							break;
						case 27 :
							{
							}
						case -27 :
							break;
						case 28 :
							{
							}
						case -28 :
							break;
						case 29 :
							{
								this.yybegin(this.YYINITIAL);
							}
						case -29 :
							break;
						case 30 :
							{
								return new Symbol(
									MSESymbols.INTEGER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -30 :
							break;
						case 31 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -31 :
							break;
						case 32 :
							{
								System.err.println("Illegal character: "
										+ this.yytext());
							}
						case -32 :
							break;
						case 34 :
							{
							}
						case -33 :
							break;
						case 35 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -34 :
							break;
						case 37 :
							{
							}
						case -35 :
							break;
						case 38 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -36 :
							break;
						case 39 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -37 :
							break;
						case 40 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -38 :
							break;
						case 41 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -39 :
							break;
						case 42 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -40 :
							break;
						case 43 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -41 :
							break;
						case 44 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -42 :
							break;
						case 45 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -43 :
							break;
						case 46 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -44 :
							break;
						case 47 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -45 :
							break;
						case 48 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -46 :
							break;
						case 49 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -47 :
							break;
						case 50 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -48 :
							break;
						case 51 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -49 :
							break;
						case 52 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -50 :
							break;
						case 53 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -51 :
							break;
						case 54 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -52 :
							break;
						case 55 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -53 :
							break;
						case 56 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -54 :
							break;
						case 57 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -55 :
							break;
						case 58 :
							{
								return new Symbol(
									MSESymbols.NAME,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -56 :
							break;
						default :
							this.yy_error(this.YY_E_INTERNAL, false);
						case -1 :
					}
					yy_initial = true;
					yy_state = this.yy_state_dtrans[this.yy_lexical_state];
					yy_next_state = this.YY_NO_STATE;
					yy_last_accept_state = this.YY_NO_STATE;
					this.yy_mark_start();
					yy_this_accept = this.yy_acpt[yy_state];
					if (this.YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						this.yy_mark_end();
					}
				}
			}
		}
	}
	public void reportErrorMessage(
		final String aMessage,
		final padl.creator.msefile.javacup.runtime.Symbol info) {
		System.err.println(MultilingualManager.getString(
			"Err_REPORT",
			MSELexer.class,
			new Object[] { aMessage, info, Integer.valueOf(this.yyline + 1),
					Integer.valueOf(this.yychar), this.yytext() }));
	}
	private int[][] unpackFromString(final int size1, final int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		final int res[][] = new int[size1][size2];
		for (int i = 0; i < size1; i++) {
			for (int j = 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString =
					commaIndex == -1 ? st : st.substring(0, commaIndex);
				st = st.substring(commaIndex + 1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j] = Integer.parseInt(workString);
					continue;
				}
				lengthString = workString.substring(colonIndex + 1);
				sequenceLength = Integer.parseInt(lengthString);
				workString = workString.substring(0, colonIndex);
				sequenceInteger = Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_advance() throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (this.yy_buffer_index < this.yy_buffer_read) {
			return this.yy_buffer[this.yy_buffer_index++];
		}

		if (0 != this.yy_buffer_start) {
			i = this.yy_buffer_start;
			j = 0;
			while (i < this.yy_buffer_read) {
				this.yy_buffer[j] = this.yy_buffer[i];
				++i;
				++j;
			}
			this.yy_buffer_end = this.yy_buffer_end - this.yy_buffer_start;
			this.yy_buffer_start = 0;
			this.yy_buffer_read = j;
			this.yy_buffer_index = j;
			next_read =
				this.yy_reader.read(
					this.yy_buffer,
					this.yy_buffer_read,
					this.yy_buffer.length - this.yy_buffer_read);
			if (-1 == next_read) {
				return this.YY_EOF;
			}
			this.yy_buffer_read = this.yy_buffer_read + next_read;
		}

		while (this.yy_buffer_index >= this.yy_buffer_read) {
			if (this.yy_buffer_index >= this.yy_buffer.length) {
				this.yy_buffer = this.yy_double(this.yy_buffer);
			}
			next_read =
				this.yy_reader.read(
					this.yy_buffer,
					this.yy_buffer_read,
					this.yy_buffer.length - this.yy_buffer_read);
			if (-1 == next_read) {
				return this.YY_EOF;
			}
			this.yy_buffer_read = this.yy_buffer_read + next_read;
		}
		return this.yy_buffer[this.yy_buffer_index++];
	}
	private char[] yy_double(final char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2 * buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private void yy_error(final int code, final boolean fatal) {
		java.lang.System.out.print(this.yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private void yy_mark_end() {
		this.yy_buffer_end = this.yy_buffer_index;
	}
	private void yy_mark_start() {
		int i;
		for (i = this.yy_buffer_start; i < this.yy_buffer_index; ++i) {
			if ('\n' == this.yy_buffer[i] && !this.yy_last_was_cr) {
				++this.yyline;
			}
			if ('\r' == this.yy_buffer[i]) {
				++this.yyline;
				this.yy_last_was_cr = true;
			}
			else {
				this.yy_last_was_cr = false;
			}
		}
		this.yychar = this.yychar + this.yy_buffer_index - this.yy_buffer_start;
		this.yy_buffer_start = this.yy_buffer_index;
	}
	private void yy_move_end() {
		if (this.yy_buffer_end > this.yy_buffer_start
				&& '\n' == this.yy_buffer[this.yy_buffer_end - 1]) {
			this.yy_buffer_end--;
		}
		if (this.yy_buffer_end > this.yy_buffer_start
				&& '\r' == this.yy_buffer[this.yy_buffer_end - 1]) {
			this.yy_buffer_end--;
		}
	}
	private void yy_to_mark() {
		this.yy_buffer_index = this.yy_buffer_end;
		this.yy_at_bol =
			this.yy_buffer_end > this.yy_buffer_start
					&& ('\r' == this.yy_buffer[this.yy_buffer_end - 1]
							|| '\n' == this.yy_buffer[this.yy_buffer_end - 1]
							|| 2028/*LS*/== this.yy_buffer[this.yy_buffer_end - 1] || 2029/*PS*/== this.yy_buffer[this.yy_buffer_end - 1]);
	}

	private void yybegin(final int state) {
		this.yy_lexical_state = state;
	}

	//	private int yylength() {
	//		return this.yy_buffer_end - this.yy_buffer_start;
	//	}

	private java.lang.String yytext() {
		return new java.lang.String(
			this.yy_buffer,
			this.yy_buffer_start,
			this.yy_buffer_end - this.yy_buffer_start);
	}
}
