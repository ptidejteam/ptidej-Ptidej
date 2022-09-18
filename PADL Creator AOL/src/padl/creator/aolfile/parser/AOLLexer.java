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
package padl.creator.aolfile.parser;

import padl.creator.aolfile.javacup.runtime.Symbol;
import util.multilingual.MultilingualManager;

public class AOLLexer implements padl.creator.aolfile.javacup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

	public void reportErrorMessage(
		final String aMessage,
		padl.creator.aolfile.javacup.runtime.Symbol info) {
		System.err.println(MultilingualManager.getString(
			"Err_REPORT",
			AOLLexer.class,
			new Object[] { aMessage, info, Integer.valueOf(this.yyline + 1),
					Integer.valueOf(this.yychar), this.yytext() }));
	}
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

	public AOLLexer(java.io.Reader reader) {
		this();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		this.yy_reader = new java.io.BufferedReader(reader);
	}

	public AOLLexer(java.io.InputStream instream) {
		this();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		this.yy_reader =
			new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private AOLLexer() {
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

	private final int YYINITIAL = 0;
	private final int COMMENT = 1;
	private final int yy_state_dtrans[] = { 0, 43 };
	private void yybegin(int state) {
		this.yy_lexical_state = state;
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
				this.yy_buffer = yy_double(this.yy_buffer);
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
	private void yy_move_end() {
		if (this.yy_buffer_end > this.yy_buffer_start
				&& '\n' == this.yy_buffer[this.yy_buffer_end - 1])
			this.yy_buffer_end--;
		if (this.yy_buffer_end > this.yy_buffer_start
				&& '\r' == this.yy_buffer[this.yy_buffer_end - 1])
			this.yy_buffer_end--;
	}
	private boolean yy_last_was_cr = false;
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
			else
				this.yy_last_was_cr = false;
		}
		this.yychar = this.yychar + this.yy_buffer_index - this.yy_buffer_start;
		this.yy_buffer_start = this.yy_buffer_index;
	}
	private void yy_mark_end() {
		this.yy_buffer_end = this.yy_buffer_index;
	}
	private void yy_to_mark() {
		this.yy_buffer_index = this.yy_buffer_end;
		this.yy_at_bol =
			(this.yy_buffer_end > this.yy_buffer_start)
					&& ('\r' == this.yy_buffer[this.yy_buffer_end - 1]
							|| '\n' == this.yy_buffer[this.yy_buffer_end - 1]
							|| 2028/*LS*/== this.yy_buffer[this.yy_buffer_end - 1] || 2029/*PS*/== this.yy_buffer[this.yy_buffer_end - 1]);
	}
	private java.lang.String yytext() {
		return (new java.lang.String(
			this.yy_buffer,
			this.yy_buffer_start,
			this.yy_buffer_end - this.yy_buffer_start));
	}
	private char[] yy_double(char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2 * buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private java.lang.String yy_error_string[] = { "Error: Internal error.\n",
			"Error: Unmatched input.\n" };
	private void yy_error(int code, boolean fatal) {
		java.lang.System.out.print(this.yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i = 0; i < size1; i++) {
			for (int j = 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString =
					(commaIndex == -1) ? st : st.substring(0, commaIndex);
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
	private int yy_acpt[] = { this./* 0 */YY_NO_ANCHOR,
			this./* 1 */YY_NO_ANCHOR, this./* 2 */YY_NO_ANCHOR,
			this./* 3 */YY_NO_ANCHOR, this./* 4 */YY_NO_ANCHOR,
			this./* 5 */YY_NO_ANCHOR, this./* 6 */YY_NO_ANCHOR,
			this./* 7 */YY_NO_ANCHOR, this./* 8 */YY_NO_ANCHOR,
			this./* 9 */YY_NO_ANCHOR, this./* 10 */YY_NO_ANCHOR,
			this./* 11 */YY_NO_ANCHOR, this./* 12 */YY_NO_ANCHOR,
			this./* 13 */YY_NO_ANCHOR, this./* 14 */YY_NO_ANCHOR,
			this./* 15 */YY_NO_ANCHOR, this./* 16 */YY_NO_ANCHOR,
			this./* 17 */YY_NO_ANCHOR, this./* 18 */YY_NO_ANCHOR,
			this./* 19 */YY_NO_ANCHOR, this./* 20 */YY_NO_ANCHOR,
			this./* 21 */YY_NO_ANCHOR, this./* 22 */YY_NO_ANCHOR,
			this./* 23 */YY_NO_ANCHOR, this./* 24 */YY_NO_ANCHOR,
			this./* 25 */YY_NO_ANCHOR, this./* 26 */YY_NO_ANCHOR,
			this./* 27 */YY_NO_ANCHOR, this./* 28 */YY_NO_ANCHOR,
			this./* 29 */YY_NO_ANCHOR, this./* 30 */YY_NO_ANCHOR,
			this./* 31 */YY_NO_ANCHOR, this./* 32 */YY_NO_ANCHOR,
			this./* 33 */YY_NO_ANCHOR, this./* 34 */YY_NO_ANCHOR,
			this./* 35 */YY_NO_ANCHOR, this./* 36 */YY_NO_ANCHOR,
			this./* 37 */YY_NO_ANCHOR, this./* 38 */YY_NO_ANCHOR,
			this./* 39 */YY_NO_ANCHOR, this./* 40 */YY_NO_ANCHOR,
			this./* 41 */YY_NO_ANCHOR, this./* 42 */YY_NO_ANCHOR,
			this./* 43 */YY_NO_ANCHOR, this./* 44 */YY_NO_ANCHOR,
			this./* 45 */YY_NO_ANCHOR, this./* 46 */YY_NO_ANCHOR,
			this./* 47 */YY_NOT_ACCEPT, this./* 48 */YY_NO_ANCHOR,
			this./* 49 */YY_NO_ANCHOR, this./* 50 */YY_NO_ANCHOR,
			this./* 51 */YY_NO_ANCHOR, this./* 52 */YY_NOT_ACCEPT,
			this./* 53 */YY_NO_ANCHOR, this./* 54 */YY_NO_ANCHOR,
			this./* 55 */YY_NO_ANCHOR, this./* 56 */YY_NO_ANCHOR,
			this./* 57 */YY_NO_ANCHOR, this./* 58 */YY_NO_ANCHOR,
			this./* 59 */YY_NO_ANCHOR, this./* 60 */YY_NO_ANCHOR,
			this./* 61 */YY_NO_ANCHOR, this./* 62 */YY_NO_ANCHOR,
			this./* 63 */YY_NO_ANCHOR, this./* 64 */YY_NO_ANCHOR,
			this./* 65 */YY_NO_ANCHOR, this./* 66 */YY_NO_ANCHOR,
			this./* 67 */YY_NO_ANCHOR, this./* 68 */YY_NO_ANCHOR,
			this./* 69 */YY_NO_ANCHOR, this./* 70 */YY_NO_ANCHOR,
			this./* 71 */YY_NO_ANCHOR, this./* 72 */YY_NO_ANCHOR,
			this./* 73 */YY_NO_ANCHOR, this./* 74 */YY_NO_ANCHOR,
			this./* 75 */YY_NO_ANCHOR, this./* 76 */YY_NO_ANCHOR,
			this./* 77 */YY_NO_ANCHOR, this./* 78 */YY_NO_ANCHOR,
			this./* 79 */YY_NO_ANCHOR, this./* 80 */YY_NO_ANCHOR,
			this./* 81 */YY_NO_ANCHOR, this./* 82 */YY_NO_ANCHOR,
			this./* 83 */YY_NO_ANCHOR, this./* 84 */YY_NO_ANCHOR,
			this./* 85 */YY_NO_ANCHOR, this./* 86 */YY_NO_ANCHOR,
			this./* 87 */YY_NO_ANCHOR, this./* 88 */YY_NO_ANCHOR,
			this./* 89 */YY_NO_ANCHOR, this./* 90 */YY_NO_ANCHOR,
			this./* 91 */YY_NO_ANCHOR, this./* 92 */YY_NO_ANCHOR,
			this./* 93 */YY_NO_ANCHOR, this./* 94 */YY_NO_ANCHOR,
			this./* 95 */YY_NO_ANCHOR, this./* 96 */YY_NO_ANCHOR,
			this./* 97 */YY_NO_ANCHOR, this./* 98 */YY_NO_ANCHOR,
			this./* 99 */YY_NO_ANCHOR, this./* 100 */YY_NO_ANCHOR,
			this./* 101 */YY_NO_ANCHOR, this./* 102 */YY_NO_ANCHOR,
			this./* 103 */YY_NO_ANCHOR, this./* 104 */YY_NO_ANCHOR,
			this./* 105 */YY_NO_ANCHOR, this./* 106 */YY_NO_ANCHOR,
			this./* 107 */YY_NO_ANCHOR, this./* 108 */YY_NO_ANCHOR,
			this./* 109 */YY_NO_ANCHOR, this./* 110 */YY_NO_ANCHOR,
			this./* 111 */YY_NO_ANCHOR, this./* 112 */YY_NO_ANCHOR,
			this./* 113 */YY_NO_ANCHOR, this./* 114 */YY_NO_ANCHOR,
			this./* 115 */YY_NO_ANCHOR, this./* 116 */YY_NO_ANCHOR,
			this./* 117 */YY_NO_ANCHOR, this./* 118 */YY_NO_ANCHOR,
			this./* 119 */YY_NO_ANCHOR, this./* 120 */YY_NO_ANCHOR,
			this./* 121 */YY_NO_ANCHOR, this./* 122 */YY_NO_ANCHOR,
			this./* 123 */YY_NO_ANCHOR, this./* 124 */YY_NO_ANCHOR,
			this./* 125 */YY_NO_ANCHOR, this./* 126 */YY_NO_ANCHOR,
			this./* 127 */YY_NO_ANCHOR, this./* 128 */YY_NO_ANCHOR,
			this./* 129 */YY_NO_ANCHOR, this./* 130 */YY_NO_ANCHOR,
			this./* 131 */YY_NO_ANCHOR, this./* 132 */YY_NO_ANCHOR,
			this./* 133 */YY_NO_ANCHOR, this./* 134 */YY_NO_ANCHOR,
			this./* 135 */YY_NO_ANCHOR, this./* 136 */YY_NO_ANCHOR,
			this./* 137 */YY_NO_ANCHOR, this./* 138 */YY_NO_ANCHOR,
			this./* 139 */YY_NO_ANCHOR, this./* 140 */YY_NO_ANCHOR,
			this./* 141 */YY_NO_ANCHOR, this./* 142 */YY_NO_ANCHOR,
			this./* 143 */YY_NO_ANCHOR, this./* 144 */YY_NO_ANCHOR,
			this./* 145 */YY_NO_ANCHOR, this./* 146 */YY_NO_ANCHOR,
			this./* 147 */YY_NO_ANCHOR, this./* 148 */YY_NO_ANCHOR,
			this./* 149 */YY_NO_ANCHOR, this./* 150 */YY_NO_ANCHOR,
			this./* 151 */YY_NO_ANCHOR, this./* 152 */YY_NO_ANCHOR,
			this./* 153 */YY_NO_ANCHOR, this./* 154 */YY_NO_ANCHOR,
			this./* 155 */YY_NO_ANCHOR, this./* 156 */YY_NO_ANCHOR,
			this./* 157 */YY_NO_ANCHOR, this./* 158 */YY_NO_ANCHOR,
			this./* 159 */YY_NO_ANCHOR, this./* 160 */YY_NO_ANCHOR,
			this./* 161 */YY_NO_ANCHOR, this./* 162 */YY_NO_ANCHOR,
			this./* 163 */YY_NO_ANCHOR, this./* 164 */YY_NO_ANCHOR,
			this./* 165 */YY_NO_ANCHOR, this./* 166 */YY_NO_ANCHOR,
			this./* 167 */YY_NO_ANCHOR, this./* 168 */YY_NO_ANCHOR,
			this./* 169 */YY_NO_ANCHOR, this./* 170 */YY_NO_ANCHOR,
			this./* 171 */YY_NO_ANCHOR, this./* 172 */YY_NO_ANCHOR,
			this./* 173 */YY_NO_ANCHOR, this./* 174 */YY_NO_ANCHOR,
			this./* 175 */YY_NO_ANCHOR, this./* 176 */YY_NO_ANCHOR,
			this./* 177 */YY_NO_ANCHOR, this./* 178 */YY_NO_ANCHOR,
			this./* 179 */YY_NO_ANCHOR, this./* 180 */YY_NO_ANCHOR,
			this./* 181 */YY_NO_ANCHOR, this./* 182 */YY_NO_ANCHOR,
			this./* 183 */YY_NO_ANCHOR, this./* 184 */YY_NO_ANCHOR,
			this./* 185 */YY_NO_ANCHOR, this./* 186 */YY_NO_ANCHOR,
			this./* 187 */YY_NO_ANCHOR, this./* 188 */YY_NO_ANCHOR,
			this./* 189 */YY_NO_ANCHOR, this./* 190 */YY_NO_ANCHOR,
			this./* 191 */YY_NO_ANCHOR, this./* 192 */YY_NO_ANCHOR,
			this./* 193 */YY_NO_ANCHOR, this./* 194 */YY_NO_ANCHOR,
			this./* 195 */YY_NO_ANCHOR, this./* 196 */YY_NO_ANCHOR,
			this./* 197 */YY_NO_ANCHOR, this./* 198 */YY_NO_ANCHOR,
			this./* 199 */YY_NO_ANCHOR, this./* 200 */YY_NO_ANCHOR,
			this./* 201 */YY_NO_ANCHOR, this./* 202 */YY_NO_ANCHOR,
			this./* 203 */YY_NO_ANCHOR, this./* 204 */YY_NO_ANCHOR,
			this./* 205 */YY_NO_ANCHOR, this./* 206 */YY_NO_ANCHOR,
			this./* 207 */YY_NO_ANCHOR, this./* 208 */YY_NO_ANCHOR,
			this./* 209 */YY_NO_ANCHOR, this./* 210 */YY_NO_ANCHOR,
			this./* 211 */YY_NO_ANCHOR, this./* 212 */YY_NO_ANCHOR,
			this./* 213 */YY_NO_ANCHOR, this./* 214 */YY_NO_ANCHOR,
			this./* 215 */YY_NO_ANCHOR, this./* 216 */YY_NO_ANCHOR,
			this./* 217 */YY_NO_ANCHOR, this./* 218 */YY_NO_ANCHOR,
			this./* 219 */YY_NO_ANCHOR, this./* 220 */YY_NO_ANCHOR,
			this./* 221 */YY_NO_ANCHOR, this./* 222 */YY_NO_ANCHOR,
			this./* 223 */YY_NO_ANCHOR, this./* 224 */YY_NO_ANCHOR,
			this./* 225 */YY_NO_ANCHOR, this./* 226 */YY_NO_ANCHOR,
			this./* 227 */YY_NO_ANCHOR, this./* 228 */YY_NO_ANCHOR };
	private int yy_cmap[] =
		unpackFromString(
			1,
			130,
			"53:9,54,48,53,54,55,53:18,54,50,53,52,53,50,2,53,43,44,1,50,24,50:2,51,50:1"
					+ "0,23,47,45,50,46,53,50,3,4,8,31,10,37,9,32,11,49:2,19,26,13,12,29,49,7,5,6,"
					+ "14,30,49:2,27,25,50,53,50:2,28,53,17,49,15,41,33,49,40,49,39,49:2,16,34,21,"
					+ "20,35,49:2,18,22,38,42,49:2,36,49,53,50,53,50,53,0:2")[0];

	private int yy_rmap[] =
		unpackFromString(
			1,
			229,
			"0,1:13,2,3:28,4,5,1,6,5,7,6,8,6,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,"
					+ "23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,"
					+ "48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,"
					+ "73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,"
					+ "98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,1"
					+ "17,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,"
					+ "136,137,138,139,140,141,3,142,143,144,145,146,147,148,149,150,151,152,153,1"
					+ "54,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171,172,"
					+ "3,173,174,175,176,177,178,179,180,181,182")[0];

	private int yy_nxt[][] =
		unpackFromString(
			183,
			56,
			"1,2,3,48,218,164,218,140,141,223,218:2,84,113,225,142,218:6,226,4,5,218,114"
					+ ",218:2,143,218:8,227,218:3,228,6,7,8,9,10,11,218:3,12,13,11:2,-1:59,218:20,"
					+ "-1:2,218:3,206,218:14,-1:6,218,186:2,-1:7,218:20,-1:2,218:3,186,218:14,-1:6"
					+ ",218,186:2,-1:4,1,44,49:46,45,49:2,50,46,49:2,51,-1,54,49:46,-1,49:2,-1,49:"
					+ "4,-1,47,49:46,-1,49:2,52,49:4,-1:3,218,183,218,184,218:2,185,218:13,-1:2,21"
					+ "8:3,186,218:14,-1:6,218,186:2,-1:6,49:46,-1,49:2,56,49:4,-1:3,218:7,14,218:"
					+ "12,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:5,54,49:46,-1,49:2,52,49:4,-1:3,"
					+ "218:7,15,218:12,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:5,47,49:46,-1,49:2,"
					+ "56,49:4,-1:3,218:20,-1:2,218:2,16,186,218:14,-1:6,218,186:2,-1:7,218:3,17,2"
					+ "18:16,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:2,18,218:17,-1:2,218:3,"
					+ "186,218:14,-1:6,218,186:2,-1:7,218:2,19,218:17,-1:2,218:3,186,218:14,-1:6,2"
					+ "18,186:2,-1:7,218:15,20,218:4,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218"
					+ ":19,21,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:2,22,218:17,-1:2,218:3"
					+ ",186,218:14,-1:6,218,186:2,-1:7,218:20,-1:2,218:3,186,218:2,23,218:11,-1:6,"
					+ "218,186:2,-1:7,218:5,24,218:14,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,21"
					+ "8:7,25,218:12,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:3,26,218:16,-1:"
					+ "2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:10,27,218:9,-1:2,218:3,186,218:1"
					+ "4,-1:6,218,186:2,-1:7,218:20,-1:2,218:3,186,218:4,28,218:9,-1:6,218,186:2,-"
					+ "1:7,218:20,-1:2,218:3,186,218:4,29,218:9,-1:6,218,186:2,-1:7,218:20,-1:2,21"
					+ "8:3,186,218:12,30,218,-1:6,218,186:2,-1:7,218:20,-1:2,218:3,186,218:4,31,21"
					+ "8:9,-1:6,218,186:2,-1:7,218:4,32,218:15,-1:2,218:3,186,218:14,-1:6,218,186:"
					+ "2,-1:7,218:20,-1:2,218:3,186,218:2,33,218:11,-1:6,218,186:2,-1:7,218:20,-1:"
					+ "2,218:3,186,218:2,34,218:11,-1:6,218,186:2,-1:7,218:2,35,218:17,-1:2,218:3,"
					+ "186,218:14,-1:6,218,186:2,-1:7,218:2,36,218:17,-1:2,218:3,186,218:14,-1:6,2"
					+ "18,186:2,-1:7,218:2,37,218:17,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218"
					+ ":10,38,218:9,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:20,-1:2,218:2,39"
					+ ",186,218:14,-1:6,218,186:2,-1:7,218:7,40,218:12,-1:2,218:3,186,218:14,-1:6,"
					+ "218,186:2,-1:7,218:10,41,218:9,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,21"
					+ "8:7,42,218:12,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:10,53,218:9,-1:"
					+ "2,218:3,186,190,218:13,-1:6,218,186:2,-1:7,218:20,-1:2,218,55,218,186,218:1"
					+ "4,-1:6,218,186:2,-1:7,218:10,57,218:9,-1:2,218:3,186,218:14,-1:6,218,186:2,"
					+ "-1:7,218:16,58,218:3,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:7,59,218"
					+ ":12,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:2,60,218:17,-1:2,218:3,18"
					+ "6,218:14,-1:6,218,186:2,-1:7,218:15,61,218:4,-1:2,218:3,186,218:14,-1:6,218"
					+ ",186:2,-1:7,218:15,62,218:4,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:3"
					+ ",63,218:16,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:7,64,218:12,-1:2,2"
					+ "18:3,186,218:14,-1:6,218,186:2,-1:7,218:8,65,218:11,-1:2,218:3,186,218:14,-"
					+ "1:6,218,186:2,-1:7,218:3,66,218:16,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:"
					+ "7,218:5,67,218:14,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:9,68,218:10"
					+ ",-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:19,69,-1:2,218:3,186,218:14,"
					+ "-1:6,218,186:2,-1:7,218:20,-1:2,218:3,186,218:5,70,218:8,-1:6,218,186:2,-1:"
					+ "7,218:20,-1:2,218:3,186,218:4,71,218:9,-1:6,218,186:2,-1:7,218:13,72,218:6,"
					+ "-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:7,73,218:12,-1:2,218:3,186,21"
					+ "8:14,-1:6,218,186:2,-1:7,218:7,74,218:12,-1:2,218:3,186,218:14,-1:6,218,186"
					+ ":2,-1:7,218:7,75,218:12,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:7,76,"
					+ "218:12,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:7,77,218:12,-1:2,218:3"
					+ ",186,218:14,-1:6,218,186:2,-1:7,218:10,78,218:9,-1:2,218:3,186,218:14,-1:6,"
					+ "218,186:2,-1:7,218:9,79,218:10,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,21"
					+ "8:10,80,218:9,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:20,-1:2,218:3,1"
					+ "86,81,218:13,-1:6,218,186:2,-1:7,218:9,82,218:10,-1:2,218:3,186,218:14,-1:6"
					+ ",218,186:2,-1:7,218:10,83,218:9,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,8"
					+ "5,218:19,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,86,218:10,87,218:8,-1:2,"
					+ "218:3,186,218:14,-1:6,218,186:2,-1:7,218:16,88,218:3,-1:2,218:3,186,218:14,"
					+ "-1:6,218,186:2,-1:7,89,218:19,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218"
					+ ":14,90,218:5,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:18,91,218,-1:2,2"
					+ "18:3,186,218:14,-1:6,218,186:2,-1:7,218:4,92,218:15,-1:2,218:3,186,218:14,-"
					+ "1:6,218,186:2,-1:7,218:4,93,218:15,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:"
					+ "7,218:16,94,218:3,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,95,218:19,-1:2,"
					+ "218:3,186,218:14,-1:6,218,186:2,-1:7,96,218:19,-1:2,218:3,186,218:14,-1:6,2"
					+ "18,186:2,-1:7,218:8,97,218:11,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218"
					+ ":14,98,218:5,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:14,99,218:5,-1:2"
					+ ",218:3,186,218:14,-1:6,218,186:2,-1:7,218:18,100,218,-1:2,218:3,186,218:14,"
					+ "-1:6,218,186:2,-1:7,218:20,-1:2,218:3,186,218:10,101,218:3,-1:6,218,186:2,-"
					+ "1:7,218:10,102,218:9,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:10,103,2"
					+ "18:9,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:3,104,218:16,-1:2,218:3,"
					+ "186,218:14,-1:6,218,186:2,-1:7,218:3,105,218:16,-1:2,218:3,186,218:14,-1:6,"
					+ "218,186:2,-1:7,218:2,106,218:17,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,2"
					+ "18:9,107,218:10,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:8,108,218:11,"
					+ "-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,109,218:19,-1:2,218:3,186,218:14,"
					+ "-1:6,218,186:2,-1:7,218:9,110,218:10,-1:2,218:3,186,218:14,-1:6,218,186:2,-"
					+ "1:7,218:8,111,218:11,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:9,112,21"
					+ "8:10,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:7,188,218,115,218:10,-1:"
					+ "2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:9,189,218:6,116,218:3,-1:2,218:3"
					+ ",186,218:14,-1:6,218,186:2,-1:7,218:13,117,218:3,118,218:2,-1:2,218:3,186,2"
					+ "18:14,-1:6,218,186:2,-1:7,119,218:3,165,218:6,145,218:8,-1:2,218:3,186,218:"
					+ "14,-1:6,218,186:2,-1:7,120,218:19,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7"
					+ ",218,121,218:18,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:20,-1:2,218:3"
					+ ",186,218,122,218:12,-1:6,218,186:2,-1:7,218:4,123,218:15,-1:2,218:3,186,218"
					+ ":14,-1:6,218,186:2,-1:7,218:3,124,218:16,-1:2,218:3,186,218:14,-1:6,218,186"
					+ ":2,-1:7,218:13,125,218:6,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:18,1"
					+ "26,218,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:20,-1:2,218:3,186,218:"
					+ "11,127,218:2,-1:6,218,186:2,-1:7,218:19,128,-1:2,218:3,186,218:14,-1:6,218,"
					+ "186:2,-1:7,218:8,129,218:11,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:8"
					+ ",130,218:11,-1:2,218:3,180,218:14,-1:6,218,186:2,-1:7,218:5,131,218:14,-1:2"
					+ ",218:3,186,218:14,-1:6,218,186:2,-1:7,218:11,132,218:8,-1:2,218:3,186,218:1"
					+ "4,-1:6,218,186:2,-1:7,218:2,133,218:17,-1:2,218:3,186,218:14,-1:6,218,186:2"
					+ ",-1:7,218:8,134,218:11,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:3,135,"
					+ "218:16,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:20,-1:2,218,136,218,18"
					+ "6,218:14,-1:6,218,186:2,-1:7,218:5,137,218:14,-1:2,218:3,186,218:14,-1:6,21"
					+ "8,186:2,-1:7,218:3,138,218:16,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218"
					+ ":20,-1:2,218:3,139,218:14,-1:6,218,186:2,-1:7,218:11,187,218:8,-1:2,218:3,1"
					+ "86,218:3,144,218:10,-1:6,218,186:2,-1:7,218:8,146,202,218:10,-1:2,218:3,186"
					+ ",218:14,-1:6,218,186:2,-1:7,218:3,147,218:16,-1:2,218:3,186,218:14,-1:6,218"
					+ ",186:2,-1:7,148,218:19,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:20,-1:"
					+ "2,218:3,186,218:6,149,218:7,-1:6,218,186:2,-1:7,218:20,-1:2,218:3,186,218:4"
					+ ",150,218:9,-1:6,218,186:2,-1:7,218:20,-1:2,218:3,186,218:10,151,218:3,-1:6,"
					+ "218,186:2,-1:7,218:14,152,218:5,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,1"
					+ "53,218:19,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:20,-1:2,218:3,186,2"
					+ "18:8,154,218:5,-1:6,218,186:2,-1:7,218:7,155,218:12,-1:2,218:3,186,218:14,-"
					+ "1:6,218,186:2,-1:7,218,156,218:18,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7"
					+ ",157,218:19,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:3,158,218:16,-1:2"
					+ ",218:3,186,218:14,-1:6,218,186:2,-1:7,159,218:19,-1:2,218:3,186,218:14,-1:6"
					+ ",218,186:2,-1:7,218:20,-1:2,218:3,160,218:14,-1:6,218,186:2,-1:7,218:2,161,"
					+ "218:17,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,162,218:19,-1:2,218:3,186,"
					+ "218:14,-1:6,218,186:2,-1:7,218:20,-1:2,218:2,163,186,218:14,-1:6,218,186:2,"
					+ "-1:7,218:2,166,218:17,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:3,196,2"
					+ "18:16,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:6,220,218:13,-1:2,218:3"
					+ ",186,218:14,-1:6,218,186:2,-1:7,218,197,218:18,-1:2,218:3,186,218:14,-1:6,2"
					+ "18,186:2,-1:7,218:16,167,218:3,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,21"
					+ "8:10,198,218:9,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:3,200,218:3,22"
					+ "4,218:12,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:20,-1:2,218:3,186,21"
					+ "8:2,201,218:11,-1:6,218,186:2,-1:7,218:20,-1:2,218:3,186,218:5,168,218:8,-1"
					+ ":6,218,186:2,-1:7,218:20,-1:2,218:3,186,218:6,169,218:7,-1:6,218,186:2,-1:7"
					+ ",218:15,170,218:4,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:13,171,218:"
					+ "6,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:4,203,218:15,-1:2,218:3,186"
					+ ",218:14,-1:6,218,186:2,-1:7,218:5,204,218:14,-1:2,218:3,186,218:14,-1:6,218"
					+ ",186:2,-1:7,218:3,172,218:16,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:"
					+ "7,205,218:12,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:8,222,218:11,-1:"
					+ "2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:7,173,218:12,-1:2,218:3,186,218:"
					+ "14,-1:6,218,186:2,-1:7,218:3,174,218:16,-1:2,218:3,186,218:14,-1:6,218,186:"
					+ "2,-1:7,218:8,175,218:11,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:16,17"
					+ "6,218:3,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:4,209,218:15,-1:2,218"
					+ ":3,186,218:14,-1:6,218,186:2,-1:7,218:9,210,218:10,-1:2,218:3,186,218:14,-1"
					+ ":6,218,186:2,-1:7,177,218:19,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:"
					+ "6,178,218:13,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,212,218:19,-1:2,218:"
					+ "3,186,218:14,-1:6,218,186:2,-1:7,218:4,179,218:15,-1:2,218:3,186,218:14,-1:"
					+ "6,218,186:2,-1:7,218:10,213,218:9,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7"
					+ ",218:16,214,218:3,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,215,218:19,-1:2"
					+ ",218:3,186,218:14,-1:6,218,186:2,-1:7,218:8,216,218:11,-1:2,218:3,186,218:1"
					+ "4,-1:6,218,186:2,-1:7,218:16,217,218:3,-1:2,218:3,186,218:14,-1:6,218,186:2"
					+ ",-1:7,218:20,-1:2,181,218:2,186,218:14,-1:6,218,186:2,-1:7,218:16,182,218:3"
					+ ",-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:10,199,218:9,-1:2,218:3,186,"
					+ "218:14,-1:6,218,186:2,-1:7,218:4,221,218:15,-1:2,218:3,186,218:14,-1:6,218,"
					+ "186:2,-1:7,218:7,208,218:12,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:9"
					+ ",211,218:10,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:7,219,218:12,-1:2"
					+ ",218:3,186,218:14,-1:6,218,186:2,-1:7,218:4,207,218:15,-1:2,218:3,186,218:1"
					+ "4,-1:6,218,186:2,-1:7,218:10,191,218:9,-1:2,218:3,186,218:14,-1:6,218,186:2"
					+ ",-1:7,218:20,-1:2,218:3,186,218:4,192,218:2,193,218:6,-1:6,218,186:2,-1:7,2"
					+ "18:18,194,218,-1:2,218:3,186,218:14,-1:6,218,186:2,-1:7,218:17,195,218:2,-1"
					+ ":2,218:3,186,218:14,-1:6,218,186:2,-1:4");

	public padl.creator.aolfile.javacup.runtime.Symbol next_token()
			throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = this.YY_NO_ANCHOR;
		int yy_state = this.yy_state_dtrans[this.yy_lexical_state];
		int yy_next_state = this.YY_NO_STATE;
		int yy_last_accept_state = this.YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = this.yy_acpt[yy_state];
		if (this.YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && this.yy_at_bol)
				yy_lookahead = this.YY_BOL;
			else
				yy_lookahead = yy_advance();
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
					yy_mark_end();
				}
			}
			else {
				if (this.YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = this.yy_acpt[yy_last_accept_state];
					if (0 != (this.YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
						case 0 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
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
								return new Symbol(AOLSymbols.POINTER, "POINTER");
							}
						case -4 :
							break;
						case 3 :
							{
								return new Symbol(AOLSymbols.POINTER, "POINTER");
							}
						case -5 :
							break;
						case 4 :
							{
								return new Symbol(AOLSymbols.COLON, ":");
							}
						case -6 :
							break;
						case 5 :
							{
								return new Symbol(AOLSymbols.COMMA, ",");
							}
						case -7 :
							break;
						case 6 :
							{
								return new Symbol(AOLSymbols.LPAREN, "(");
							}
						case -8 :
							break;
						case 7 :
							{
								return new Symbol(AOLSymbols.RPAREN, ")");
							}
						case -9 :
							break;
						case 8 :
							{
								return new Symbol(AOLSymbols.LBRACE, "<");
							}
						case -10 :
							break;
						case 9 :
							{
								return new Symbol(AOLSymbols.RBRACE, ">");
							}
						case -11 :
							break;
						case 10 :
							{
								return new Symbol(AOLSymbols.SEMICOLON, ";");
							}
						case -12 :
							break;
						case 11 :
							{ /* Ignore white space */
							}
						case -13 :
							break;
						case 12 :
							{
								yybegin(this.COMMENT);
							}
						case -14 :
							break;
						case 13 :
							{
								System.err.println("Illegal character: "
										+ yytext());
							}
						case -15 :
							break;
						case 14 :
							{
								return new Symbol(AOLSymbols.ONE, "ONE");
							}
						case -16 :
							break;
						case 15 :
							{
								return new Symbol(AOLSymbols.NAME, "NAME");
							}
						case -17 :
							break;
						case 16 :
							{
								return new Symbol(AOLSymbols.MANY, "MANY");
							}
						case -18 :
							break;
						case 17 :
							{
								return new Symbol(AOLSymbols.MULT, "MULT");
							}
						case -19 :
							break;
						case 18 :
							{
								return new Symbol(AOLSymbols.ROLES, "ROLES");
							}
						case -20 :
							break;
						case 19 :
							{
								return new Symbol(AOLSymbols.CLASS, "CLASS");
							}
						case -21 :
							break;
						case 20 :
							{
								return new Symbol(AOLSymbols.CLASS, "CLASS");
							}
						case -22 :
							break;
						case 21 :
							{
								return new Symbol(AOLSymbols.CONST, "const");
							}
						case -23 :
							break;
						case 22 :
							{
								return new Symbol(AOLSymbols.PARTS, "PARTS");
							}
						case -24 :
							break;
						case 23 :
							{
								return new Symbol(AOLSymbols.SHARED, "SHARED");
							}
						case -25 :
							break;
						case 24 :
							{
								return new Symbol(AOLSymbols.PUBLIC, "PUBLIC");
							}
						case -26 :
							break;
						case 25 :
							{
								return new Symbol(AOLSymbols.PRIVATE, "PRIVATE");
							}
						case -27 :
							break;
						case 26 :
							{
								return new Symbol(
									AOLSymbols.ABSTRACT,
									"ABSTRACT");
							}
						case -28 :
							break;
						case 27 :
							{
								return new Symbol(
									AOLSymbols.RELATION,
									"RELATION");
							}
						case -29 :
							break;
						case 28 :
							{
								return new Symbol(
									AOLSymbols.TEMPLATE,
									"template");
							}
						case -30 :
							break;
						case 29 :
							{
								return new Symbol(
									AOLSymbols.TYPENAME,
									"typename");
							}
						case -31 :
							break;
						case 30 :
							{
								return new Symbol(
									AOLSymbols.UNSIGNED,
									"unsigned");
							}
						case -32 :
							break;
						case 31 :
							{
								return new Symbol(
									AOLSymbols.VOLATILE,
									"volatile");
							}
						case -33 :
							break;
						case 32 :
							{
								return new Symbol(
									AOLSymbols.CONTAINER,
									"CONTAINER");
							}
						case -34 :
							break;
						case 33 :
							{
								return new Symbol(
									AOLSymbols.UNDEFINED,
									"UNDEFINED");
							}
						case -35 :
							break;
						case 34 :
							{
								return new Symbol(
									AOLSymbols.PROTECTED,
									"PROTECTED");
							}
						case -36 :
							break;
						case 35 :
							{
								return new Symbol(
									AOLSymbols.ATTRIBUTES,
									"ATTRIBUTES");
							}
						case -37 :
							break;
						case 36 :
							{
								return new Symbol(
									AOLSymbols.SUBCLASSES,
									"SUBCLASSES");
							}
						case -38 :
							break;
						case 37 :
							{
								return new Symbol(
									AOLSymbols.OPERATIONS,
									"OPERATIONS");
							}
						case -39 :
							break;
						case 38 :
							{
								return new Symbol(
									AOLSymbols.AGGREGATION,
									"AGGREGATION");
							}
						case -40 :
							break;
						case 39 :
							{
								return new Symbol(
									AOLSymbols.ONE_OR_MANY,
									"ONE_OR_MANY");
							}
						case -41 :
							break;
						case 40 :
							{
								return new Symbol(
									AOLSymbols.UNDEF_SCOPE,
									"UNDEF_SCOPE");
							}
						case -42 :
							break;
						case 41 :
							{
								return new Symbol(
									AOLSymbols.GENERALIZATION,
									"GENERALIZATION");
							}
						case -43 :
							break;
						case 42 :
							{
								return new Symbol(
									AOLSymbols.OPTIONALLY_ONE,
									"OPTIONALLY_ONE");
							}
						case -44 :
							break;
						case 43 :
							{
							}
						case -45 :
							break;
						case 44 :
							{
							}
						case -46 :
							break;
						case 45 :
							{
								yybegin(this.YYINITIAL);
							}
						case -47 :
							break;
						case 46 :
							{
							}
						case -48 :
							break;
						case 48 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -49 :
							break;
						case 49 :
							{
							}
						case -50 :
							break;
						case 50 :
							{
							}
						case -51 :
							break;
						case 51 :
							{
								yybegin(this.YYINITIAL);
							}
						case -52 :
							break;
						case 53 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -53 :
							break;
						case 54 :
							{
							}
						case -54 :
							break;
						case 55 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -55 :
							break;
						case 56 :
							{
							}
						case -56 :
							break;
						case 57 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -57 :
							break;
						case 58 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -58 :
							break;
						case 59 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -59 :
							break;
						case 60 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -60 :
							break;
						case 61 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -61 :
							break;
						case 62 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -62 :
							break;
						case 63 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -63 :
							break;
						case 64 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -64 :
							break;
						case 65 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -65 :
							break;
						case 66 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -66 :
							break;
						case 67 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -67 :
							break;
						case 68 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -68 :
							break;
						case 69 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -69 :
							break;
						case 70 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -70 :
							break;
						case 71 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -71 :
							break;
						case 72 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -72 :
							break;
						case 73 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -73 :
							break;
						case 74 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -74 :
							break;
						case 75 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -75 :
							break;
						case 76 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -76 :
							break;
						case 77 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -77 :
							break;
						case 78 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -78 :
							break;
						case 79 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -79 :
							break;
						case 80 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -80 :
							break;
						case 81 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -81 :
							break;
						case 82 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -82 :
							break;
						case 83 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -83 :
							break;
						case 84 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -84 :
							break;
						case 85 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -85 :
							break;
						case 86 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -86 :
							break;
						case 87 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -87 :
							break;
						case 88 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -88 :
							break;
						case 89 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -89 :
							break;
						case 90 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -90 :
							break;
						case 91 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -91 :
							break;
						case 92 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -92 :
							break;
						case 93 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -93 :
							break;
						case 94 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -94 :
							break;
						case 95 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -95 :
							break;
						case 96 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -96 :
							break;
						case 97 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -97 :
							break;
						case 98 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -98 :
							break;
						case 99 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -99 :
							break;
						case 100 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -100 :
							break;
						case 101 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -101 :
							break;
						case 102 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -102 :
							break;
						case 103 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -103 :
							break;
						case 104 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -104 :
							break;
						case 105 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -105 :
							break;
						case 106 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -106 :
							break;
						case 107 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -107 :
							break;
						case 108 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -108 :
							break;
						case 109 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -109 :
							break;
						case 110 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -110 :
							break;
						case 111 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -111 :
							break;
						case 112 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -112 :
							break;
						case 113 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -113 :
							break;
						case 114 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -114 :
							break;
						case 115 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -115 :
							break;
						case 116 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -116 :
							break;
						case 117 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -117 :
							break;
						case 118 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -118 :
							break;
						case 119 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -119 :
							break;
						case 120 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -120 :
							break;
						case 121 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -121 :
							break;
						case 122 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -122 :
							break;
						case 123 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -123 :
							break;
						case 124 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -124 :
							break;
						case 125 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -125 :
							break;
						case 126 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -126 :
							break;
						case 127 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -127 :
							break;
						case 128 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -128 :
							break;
						case 129 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -129 :
							break;
						case 130 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -130 :
							break;
						case 131 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -131 :
							break;
						case 132 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -132 :
							break;
						case 133 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -133 :
							break;
						case 134 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -134 :
							break;
						case 135 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -135 :
							break;
						case 136 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -136 :
							break;
						case 137 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -137 :
							break;
						case 138 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -138 :
							break;
						case 139 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -139 :
							break;
						case 140 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -140 :
							break;
						case 141 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -141 :
							break;
						case 142 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -142 :
							break;
						case 143 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -143 :
							break;
						case 144 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -144 :
							break;
						case 145 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -145 :
							break;
						case 146 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -146 :
							break;
						case 147 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -147 :
							break;
						case 148 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -148 :
							break;
						case 149 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -149 :
							break;
						case 150 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -150 :
							break;
						case 151 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -151 :
							break;
						case 152 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -152 :
							break;
						case 153 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -153 :
							break;
						case 154 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -154 :
							break;
						case 155 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -155 :
							break;
						case 156 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -156 :
							break;
						case 157 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -157 :
							break;
						case 158 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -158 :
							break;
						case 159 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -159 :
							break;
						case 160 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -160 :
							break;
						case 161 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -161 :
							break;
						case 162 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -162 :
							break;
						case 163 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -163 :
							break;
						case 164 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -164 :
							break;
						case 165 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -165 :
							break;
						case 166 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -166 :
							break;
						case 167 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -167 :
							break;
						case 168 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -168 :
							break;
						case 169 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -169 :
							break;
						case 170 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -170 :
							break;
						case 171 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -171 :
							break;
						case 172 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -172 :
							break;
						case 173 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -173 :
							break;
						case 174 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -174 :
							break;
						case 175 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -175 :
							break;
						case 176 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -176 :
							break;
						case 177 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -177 :
							break;
						case 178 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -178 :
							break;
						case 179 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -179 :
							break;
						case 180 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -180 :
							break;
						case 181 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -181 :
							break;
						case 182 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -182 :
							break;
						case 183 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -183 :
							break;
						case 184 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -184 :
							break;
						case 185 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -185 :
							break;
						case 186 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -186 :
							break;
						case 187 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -187 :
							break;
						case 188 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -188 :
							break;
						case 189 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -189 :
							break;
						case 190 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -190 :
							break;
						case 191 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -191 :
							break;
						case 192 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -192 :
							break;
						case 193 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -193 :
							break;
						case 194 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -194 :
							break;
						case 195 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -195 :
							break;
						case 196 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -196 :
							break;
						case 197 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -197 :
							break;
						case 198 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -198 :
							break;
						case 199 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -199 :
							break;
						case 200 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -200 :
							break;
						case 201 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -201 :
							break;
						case 202 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -202 :
							break;
						case 203 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -203 :
							break;
						case 204 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -204 :
							break;
						case 205 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -205 :
							break;
						case 206 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -206 :
							break;
						case 207 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -207 :
							break;
						case 208 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -208 :
							break;
						case 209 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -209 :
							break;
						case 210 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -210 :
							break;
						case 211 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -211 :
							break;
						case 212 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -212 :
							break;
						case 213 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -213 :
							break;
						case 214 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -214 :
							break;
						case 215 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -215 :
							break;
						case 216 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -216 :
							break;
						case 217 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -217 :
							break;
						case 218 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -218 :
							break;
						case 219 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -219 :
							break;
						case 220 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -220 :
							break;
						case 221 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -221 :
							break;
						case 222 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -222 :
							break;
						case 223 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -223 :
							break;
						case 224 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -224 :
							break;
						case 225 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -225 :
							break;
						case 226 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -226 :
							break;
						case 227 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -227 :
							break;
						case 228 :
							{
								return new Symbol(
									AOLSymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -228 :
							break;
						default :
							yy_error(this.YY_E_INTERNAL, false);
						case -1 :
					}
					yy_initial = true;
					yy_state = this.yy_state_dtrans[this.yy_lexical_state];
					yy_next_state = this.YY_NO_STATE;
					yy_last_accept_state = this.YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = this.yy_acpt[yy_state];
					if (this.YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
