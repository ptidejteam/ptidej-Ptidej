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
package sad.rule.creator;

import sad.rule.creator.javacup.runtime.Symbol;

class RULELexer implements sad.rule.creator.javacup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

	private int comment_count = 0;
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	private final int YYINITIAL = 0;

	private final int COMMENT = 1;

	private final int yy_state_dtrans[] = { 0, 57 };

	private final int YY_E_INTERNAL = 0;
	private final java.lang.String yy_error_string[] = {
			"Error: Internal error.\n", "Error: Unmatched input.\n" };
	private final int yy_acpt[] = { this./* 0 */YY_NO_ANCHOR,
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
			this./* 47 */YY_NO_ANCHOR, this./* 48 */YY_NO_ANCHOR,
			this./* 49 */YY_NO_ANCHOR, this./* 50 */YY_NO_ANCHOR,
			this./* 51 */YY_NO_ANCHOR, this./* 52 */YY_NO_ANCHOR,
			this./* 53 */YY_NO_ANCHOR, this./* 54 */YY_NO_ANCHOR,
			this./* 55 */YY_NO_ANCHOR, this./* 56 */YY_NO_ANCHOR,
			this./* 57 */YY_NO_ANCHOR, this./* 58 */YY_NO_ANCHOR,
			this./* 59 */YY_NOT_ACCEPT, this./* 60 */YY_NO_ANCHOR,
			this./* 61 */YY_NO_ANCHOR, this./* 62 */YY_NO_ANCHOR,
			this./* 63 */YY_NO_ANCHOR, this./* 64 */YY_NOT_ACCEPT,
			this./* 65 */YY_NO_ANCHOR, this./* 66 */YY_NO_ANCHOR,
			this./* 67 */YY_NOT_ACCEPT, this./* 68 */YY_NO_ANCHOR,
			this./* 69 */YY_NO_ANCHOR, this./* 70 */YY_NOT_ACCEPT,
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
			this./* 227 */YY_NO_ANCHOR, this./* 228 */YY_NO_ANCHOR,
			this./* 229 */YY_NO_ANCHOR, this./* 230 */YY_NO_ANCHOR,
			this./* 231 */YY_NO_ANCHOR, this./* 232 */YY_NO_ANCHOR,
			this./* 233 */YY_NO_ANCHOR, this./* 234 */YY_NO_ANCHOR,
			this./* 235 */YY_NO_ANCHOR, this./* 236 */YY_NO_ANCHOR,
			this./* 237 */YY_NO_ANCHOR, this./* 238 */YY_NO_ANCHOR,
			this./* 239 */YY_NO_ANCHOR, this./* 240 */YY_NO_ANCHOR,
			this./* 241 */YY_NO_ANCHOR, this./* 242 */YY_NO_ANCHOR,
			this./* 243 */YY_NO_ANCHOR, this./* 244 */YY_NO_ANCHOR,
			this./* 245 */YY_NO_ANCHOR, this./* 246 */YY_NO_ANCHOR,
			this./* 247 */YY_NO_ANCHOR, this./* 248 */YY_NO_ANCHOR,
			this./* 249 */YY_NO_ANCHOR, this./* 250 */YY_NO_ANCHOR,
			this./* 251 */YY_NO_ANCHOR, this./* 252 */YY_NO_ANCHOR,
			this./* 253 */YY_NO_ANCHOR, this./* 254 */YY_NO_ANCHOR,
			this./* 255 */YY_NO_ANCHOR, this./* 256 */YY_NO_ANCHOR,
			this./* 257 */YY_NO_ANCHOR, this./* 258 */YY_NO_ANCHOR,
			this./* 259 */YY_NO_ANCHOR, this./* 260 */YY_NO_ANCHOR,
			this./* 261 */YY_NO_ANCHOR, this./* 262 */YY_NO_ANCHOR,
			this./* 263 */YY_NO_ANCHOR, this./* 264 */YY_NO_ANCHOR,
			this./* 265 */YY_NO_ANCHOR, this./* 266 */YY_NO_ANCHOR,
			this./* 267 */YY_NO_ANCHOR, this./* 268 */YY_NO_ANCHOR,
			this./* 269 */YY_NO_ANCHOR, this./* 270 */YY_NO_ANCHOR,
			this./* 271 */YY_NO_ANCHOR, this./* 272 */YY_NO_ANCHOR,
			this./* 273 */YY_NO_ANCHOR, this./* 274 */YY_NO_ANCHOR,
			this./* 275 */YY_NO_ANCHOR, this./* 276 */YY_NO_ANCHOR,
			this./* 277 */YY_NO_ANCHOR, this./* 278 */YY_NO_ANCHOR,
			this./* 279 */YY_NO_ANCHOR, this./* 280 */YY_NO_ANCHOR,
			this./* 281 */YY_NO_ANCHOR, this./* 282 */YY_NO_ANCHOR,
			this./* 283 */YY_NO_ANCHOR, this./* 284 */YY_NO_ANCHOR,
			this./* 285 */YY_NO_ANCHOR, this./* 286 */YY_NO_ANCHOR,
			this./* 287 */YY_NO_ANCHOR, this./* 288 */YY_NO_ANCHOR,
			this./* 289 */YY_NO_ANCHOR, this./* 290 */YY_NO_ANCHOR,
			this./* 291 */YY_NO_ANCHOR, this./* 292 */YY_NO_ANCHOR,
			this./* 293 */YY_NO_ANCHOR, this./* 294 */YY_NO_ANCHOR,
			this./* 295 */YY_NO_ANCHOR };
	private final int yy_cmap[] =
		this
			.unpackFromString(
				1,
				130,
				"37:9,33,34,37,33:2,37:18,33,38,37:3,38:2,37,5,6,36,30,7,31,38,35,38:10,2,1,"
						+ "38:3,37:2,14,29,13,15,11,20,21,26,16,38:2,10,22,17,19,28,32,8,23,18,9,24,27"
						+ ",38,25,38:2,37,38:2,12,37,38:26,3,38,4,38,37,0:2")[0];
	private final int yy_rmap[] =
		this
			.unpackFromString(
				1,
				296,
				"0,1:8,2:2,1,2:4,3,2,4,5,6,7,2:35,8,1,9,1:2,10,11,12,13,14,15,16,17,18,19,20"
						+ ",21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45"
						+ ",46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,2,68,69,"
						+ "70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,"
						+ "95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,"
						+ "115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133"
						+ ",134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151,15"
						+ "2,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,1"
						+ "71,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,"
						+ "190,191,192,193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,208"
						+ ",209,210,211,212,213,214,215,216,2,217,218,219,220,221,222,223,224,225,226,"
						+ "227,228,229,230,231,232,233,234,235,236,237,238,239,240,241")[0];
	private final int yy_nxt[][] =
		this
			.unpackFromString(
				242,
				39,
				"1,2,3,4,5,6,7,8,62,241,114,65,270,278,281,282,115,116,68,117,283,284,285,11"
						+ "8,286,270,287,270,288,270,9,10,270,11:2,71,270,-1,270,-1:47,270:22,120:2,27"
						+ "0,-1:2,120,270,-1,270,-1:8,270:4,155,270:17,120:2,270,-1:2,120,270,-1,270,-"
						+ "1:8,270:4,159,270:17,120:2,270,-1:2,120,270,-1,270,-1:8,270:4,166,270:17,12"
						+ "0:2,270,-1:2,120,270,-1,270,-1:8,270:4,170,270:17,120:2,270,-1:2,120,270,-1"
						+ ",270,-1:8,270:3,251,270:18,120:2,270,-1:2,120,270,-1,270,1,63:33,58,59,64,6"
						+ "3:2,-1,63:33,-1,66,60,63:2,-1:8,270,119,270:20,120:2,270,-1:2,120,270,-1,27"
						+ "0,-1,63:33,-1,67,70,63:2,-1,63:33,-1,61,69,63:2,-1:8,270:22,120:2,12,-1:2,1"
						+ "20,270,-1,270,-1,63:33,-1,66,70,63:2,-1,63:33,-1,66,-1,63:2,-1:8,270:11,13,"
						+ "270:10,120:2,270,-1:2,120,270,-1,270,-1,63:33,-1,67,69,63:2,-1,63:33,-1:2,6"
						+ "9,63:2,-1:8,270:22,120:2,270,-1:2,120,14,-1,270,-1:8,270:19,15,270:2,120:2,"
						+ "270,-1:2,120,270,-1,270,-1:8,270:5,79,270:4,142,270,16,270:5,245,270:3,120:"
						+ "2,270,-1:2,120,270,-1,270,-1:8,270:13,17,270:8,120:2,270,-1:2,120,270,-1,27"
						+ "0,-1:8,270:3,18,270:18,120:2,270,-1:2,120,270,-1,270,-1:8,270:20,19,270,120"
						+ ":2,270,-1:2,120,270,-1,270,-1:8,270:3,20,270:18,120:2,270,-1:2,120,270,-1,2"
						+ "70,-1:8,270:12,21,270:9,120:2,270,-1:2,120,270,-1,270,-1:8,270:2,22,270:19,"
						+ "120:2,270,-1:2,120,270,-1,270,-1:8,270:3,23,270:18,120:2,270,-1:2,120,270,-"
						+ "1,270,-1:8,270:14,24,270:7,120:2,270,-1:2,120,270,-1,270,-1:8,270:17,25,270"
						+ ":4,120:2,270,-1:2,120,270,-1,270,-1:8,270:18,26,270:3,120:2,270,-1:2,120,27"
						+ "0,-1,270,-1:8,270:9,27,270:12,120:2,270,-1:2,120,270,-1,270,-1:8,270:5,28,2"
						+ "70:16,120:2,270,-1:2,120,270,-1,270,-1:8,29,270:21,120:2,270,-1:2,120,270,-"
						+ "1,270,-1:8,270:15,30,270:6,120:2,270,-1:2,120,270,-1,270,-1:8,270:13,31,270"
						+ ":8,120:2,270,-1:2,120,270,-1,270,-1:8,270:22,120:2,32,-1:2,120,270,-1,270,-"
						+ "1:8,270:22,120:2,33,-1:2,120,270,-1,270,-1:8,270:14,34,270:7,120:2,270,-1:2"
						+ ",120,270,-1,270,-1:8,270:5,35,270:16,120:2,270,-1:2,120,270,-1,270,-1:8,270"
						+ ":22,120:2,36,-1:2,120,270,-1,270,-1:8,270:10,37,270:11,120:2,270,-1:2,120,2"
						+ "70,-1,270,-1:8,270:10,38,270:11,120:2,270,-1:2,120,270,-1,270,-1:8,270:5,39"
						+ ",270:16,120:2,270,-1:2,120,270,-1,270,-1:8,270:19,40,270:2,120:2,270,-1:2,1"
						+ "20,270,-1,270,-1:8,270:7,41,270:14,120:2,270,-1:2,120,270,-1,270,-1:8,270:3"
						+ ",42,270:18,120:2,270,-1:2,120,270,-1,270,-1:8,270:3,43,270:18,120:2,270,-1:"
						+ "2,120,270,-1,270,-1:8,270:18,44,270:3,120:2,270,-1:2,120,270,-1,270,-1:8,27"
						+ "0:7,45,270:14,120:2,270,-1:2,120,270,-1,270,-1:8,270:3,46,270:18,120:2,270,"
						+ "-1:2,120,270,-1,270,-1:8,270:10,47,270:11,120:2,270,-1:2,120,270,-1,270,-1:"
						+ "8,270:17,48,270:4,120:2,270,-1:2,120,270,-1,270,-1:8,270:7,49,270:14,120:2,"
						+ "270,-1:2,120,270,-1,270,-1:8,270:7,50,270:14,120:2,270,-1:2,120,270,-1,270,"
						+ "-1:8,270:3,51,270:18,120:2,270,-1:2,120,270,-1,270,-1:8,270:3,52,270:18,120"
						+ ":2,270,-1:2,120,270,-1,270,-1:8,53,270:21,120:2,270,-1:2,120,270,-1,270,-1:"
						+ "8,270:14,54,270:7,120:2,270,-1:2,120,270,-1,270,-1:8,270:3,55,270:18,120:2,"
						+ "270,-1:2,120,270,-1,270,-1:8,56,270:21,120:2,270,-1:2,120,270,-1,270,-1:8,2"
						+ "70:11,72,270:10,120:2,270,-1:2,120,270,-1,270,-1:8,270:9,73,270:5,127,270:6"
						+ ",120:2,270,-1:2,120,270,-1,270,-1:8,270:3,74,270:7,128,270:10,120:2,270,-1:"
						+ "2,120,270,-1,270,-1:8,270:9,75,270:10,129,270,120:2,270,-1:2,120,270,-1,270"
						+ ",-1:8,270,76,270,243,270:6,136,270:11,120:2,270,-1:2,120,270,-1,270,-1:8,27"
						+ "0:2,77,270:19,120:2,270,-1:2,120,270,-1,270,-1:8,270:8,139,270:13,120:2,270"
						+ ",-1:2,120,270,-1,270,-1:8,270:6,244,270:15,120:2,270,-1:2,120,270,-1,270,-1"
						+ ":8,270:14,140,270:7,120:2,270,-1:2,120,270,-1,270,-1:8,270:13,271,270:8,120"
						+ ":2,270,-1:2,120,270,-1,270,-1:8,270:15,141,270:6,120:2,270,-1:2,120,270,-1,"
						+ "270,-1:8,270:12,78,270:9,120:2,270,-1:2,120,270,-1,270,-1:8,270:4,143,270:1"
						+ "7,120:2,270,-1:2,120,270,-1,270,-1:8,270:9,80,144,270:11,120:2,270,-1:2,120"
						+ ",270,-1,270,-1:8,270:10,145,270:11,120:2,270,-1:2,120,270,-1,270,-1:8,270:1"
						+ "1,81,270:10,120:2,270,-1:2,120,270,-1,270,-1:8,270:3,246,270:18,120:2,270,-"
						+ "1:2,120,270,-1,270,-1:8,270:11,248,270:10,120:2,270,-1:2,120,270,-1,270,-1:"
						+ "8,270:2,146,270:19,120:2,270,-1:2,120,270,-1,270,-1:8,270:7,250,270:2,147,2"
						+ "70:11,120:2,270,-1:2,120,270,-1,270,-1:8,270:9,82,270:12,120:2,270,-1:2,120"
						+ ",270,-1,270,-1:8,148,270:21,120:2,270,-1:2,120,270,-1,270,-1:8,270:13,83,27"
						+ "0:8,120:2,270,-1:2,120,270,-1,270,-1:8,270:21,151,120:2,270,-1:2,120,270,-1"
						+ ",270,-1:8,270:11,84,270:10,120:2,270,-1:2,120,270,-1,270,-1:8,270:20,153,27"
						+ "0,120:2,270,-1:2,120,270,-1,270,-1:8,270:11,85,270:10,120:2,270,-1:2,120,27"
						+ "0,-1,270,-1:8,270:3,86,270:18,120:2,270,-1:2,120,270,-1,270,-1:8,270:6,157,"
						+ "270:15,120:2,270,-1:2,120,270,-1,270,-1:8,270:4,158,270:17,120:2,270,-1:2,1"
						+ "20,270,-1,270,-1:8,270:8,160,270:13,120:2,270,-1:2,120,270,-1,270,-1:8,270:"
						+ "10,163,270:11,120:2,270,-1:2,120,270,-1,270,-1:8,165,270:17,252,270:3,120:2"
						+ ",270,-1:2,120,270,-1,270,-1:8,270,168,270:20,120:2,270,-1:2,120,270,-1,270,"
						+ "-1:8,270:17,169,270:4,120:2,270,-1:2,120,270,-1,270,-1:8,270:16,253,270:5,1"
						+ "20:2,270,-1:2,120,270,-1,270,-1:8,270:2,254,270:19,120:2,270,-1:2,120,270,-"
						+ "1,270,-1:8,270:15,255,270:6,120:2,270,-1:2,120,270,-1,270,-1:8,270:11,87,27"
						+ "0:10,120:2,270,-1:2,120,270,-1,270,-1:8,270:3,88,270:18,120:2,270,-1:2,120,"
						+ "270,-1,270,-1:8,270:3,89,270:18,120:2,270,-1:2,120,270,-1,270,-1:8,171,270:"
						+ "21,120:2,270,-1:2,120,270,-1,270,-1:8,270:21,172,120:2,270,-1:2,120,270,-1,"
						+ "270,-1:8,270:3,90,270:18,120:2,270,-1:2,120,270,-1,270,-1:8,270:11,173,270:"
						+ "2,174,270:7,120:2,270,-1:2,120,270,-1,270,-1:8,270:11,272,270:10,120:2,270,"
						+ "-1:2,120,270,-1,270,-1:8,270:7,292,270:14,120:2,270,-1:2,120,270,-1,270,-1:"
						+ "8,270:6,175,270:15,120:2,270,-1:2,120,270,-1,270,-1:8,270:8,176,270:13,120:"
						+ "2,270,-1:2,120,270,-1,270,-1:8,270,91,270:20,120:2,270,-1:2,120,270,-1,270,"
						+ "-1:8,270:8,92,270:13,120:2,270,-1:2,120,270,-1,270,-1:8,270:3,93,270:18,120"
						+ ":2,270,-1:2,120,270,-1,270,-1:8,270:9,178,270:12,120:2,270,-1:2,120,270,-1,"
						+ "270,-1:8,270:5,94,270:16,120:2,270,-1:2,120,270,-1,270,-1:8,270:4,179,270:1"
						+ "7,120:2,270,-1:2,120,270,-1,270,-1:8,270:5,180,270:16,120:2,270,-1:2,120,27"
						+ "0,-1,270,-1:8,270:8,95,270:13,120:2,270,-1:2,120,270,-1,270,-1:8,270:15,273"
						+ ",270:6,120:2,270,-1:2,120,270,-1,270,-1:8,181,270:21,120:2,270,-1:2,120,270"
						+ ",-1,270,-1:8,270:3,279,270:18,120:2,270,-1:2,120,270,-1,270,-1:8,270:2,260,"
						+ "270:19,120:2,270,-1:2,120,270,-1,270,-1:8,270:20,183,270,120:2,270,-1:2,120"
						+ ",270,-1,270,-1:8,270:7,184,270:14,120:2,270,-1:2,120,270,-1,270,-1:8,270:10"
						+ ",185,270:11,120:2,270,-1:2,120,270,-1,270,-1:8,270:2,186,270:15,187,270:3,1"
						+ "20:2,270,-1:2,120,270,-1,270,-1:8,270:6,189,270:15,120:2,270,-1:2,120,270,-"
						+ "1,270,-1:8,270:4,192,270:17,120:2,270,-1:2,120,270,-1,270,-1:8,270:9,194,27"
						+ "0:12,120:2,270,-1:2,120,270,-1,270,-1:8,270:2,289,270:19,120:2,270,-1:2,120"
						+ ",270,-1,270,-1:8,270:4,197,270:4,262,270:12,120:2,270,-1:2,120,270,-1,270,-"
						+ "1:8,270:8,96,270:13,120:2,270,-1:2,120,270,-1,270,-1:8,270:11,97,270:10,120"
						+ ":2,270,-1:2,120,270,-1,270,-1:8,270:8,198,270:13,120:2,270,-1:2,120,270,-1,"
						+ "270,-1:8,270:3,199,270:18,120:2,270,-1:2,120,270,-1,270,-1:8,98,270:21,120:"
						+ "2,270,-1:2,120,270,-1,270,-1:8,270:14,99,270:7,120:2,270,-1:2,120,270,-1,27"
						+ "0,-1:8,276,270:21,120:2,270,-1:2,120,270,-1,270,-1:8,270:14,280,270:7,120:2"
						+ ",270,-1:2,120,270,-1,270,-1:8,270:18,202,270:3,120:2,270,-1:2,120,270,-1,27"
						+ "0,-1:8,270:6,203,270:15,120:2,270,-1:2,120,270,-1,270,-1:8,270:14,100,270:7"
						+ ",120:2,270,-1:2,120,270,-1,270,-1:8,270:16,204,270:5,120:2,270,-1:2,120,270"
						+ ",-1,270,-1:8,270:6,205,270:2,206,270:12,120:2,270,-1:2,120,270,-1,270,-1:8,"
						+ "270:13,101,270:8,120:2,270,-1:2,120,270,-1,270,-1:8,270:4,293,270:17,120:2,"
						+ "270,-1:2,120,270,-1,270,-1:8,270:12,208,270:9,120:2,270,-1:2,120,270,-1,270"
						+ ",-1:8,270:10,209,270:11,120:2,270,-1:2,120,270,-1,270,-1:8,270:11,102,270:1"
						+ "0,120:2,270,-1:2,120,270,-1,270,-1:8,270:2,212,270:19,120:2,270,-1:2,120,27"
						+ "0,-1,270,-1:8,270:6,213,270:15,120:2,270,-1:2,120,270,-1,270,-1:8,270:5,264"
						+ ",270:16,120:2,270,-1:2,120,270,-1,270,-1:8,270:11,295,270:10,120:2,270,-1:2"
						+ ",120,270,-1,270,-1:8,270:14,103,270:7,120:2,270,-1:2,120,270,-1,270,-1:8,27"
						+ "0:8,214,270:13,120:2,270,-1:2,120,270,-1,270,-1:8,270:4,215,270:17,120:2,27"
						+ "0,-1:2,120,270,-1,270,-1:8,270:5,104,270:16,120:2,270,-1:2,120,270,-1,270,-"
						+ "1:8,270:9,105,270:12,120:2,270,-1:2,120,270,-1,270,-1:8,270:17,216,270:4,12"
						+ "0:2,270,-1:2,120,270,-1,270,-1:8,217,270:21,120:2,270,-1:2,120,270,-1,270,-"
						+ "1:8,270:3,219,270:18,120:2,270,-1:2,120,270,-1,270,-1:8,270:20,220,270,120:"
						+ "2,270,-1:2,120,270,-1,270,-1:8,270:4,221,270:17,120:2,270,-1:2,120,270,-1,2"
						+ "70,-1:8,270:8,267,270:13,120:2,270,-1:2,120,270,-1,270,-1:8,270:9,222,270:1"
						+ "2,120:2,270,-1:2,120,270,-1,270,-1:8,270:2,106,270:19,120:2,270,-1:2,120,27"
						+ "0,-1,270,-1:8,270:6,225,270:15,120:2,270,-1:2,120,270,-1,270,-1:8,270:11,22"
						+ "6,270:10,120:2,270,-1:2,120,270,-1,270,-1:8,270:10,228,270:11,120:2,270,-1:"
						+ "2,120,270,-1,270,-1:8,270:15,268,270:6,120:2,270,-1:2,120,270,-1,270,-1:8,2"
						+ "70:2,107,270:19,120:2,270,-1:2,120,270,-1,270,-1:8,229,270:21,120:2,270,-1:"
						+ "2,120,270,-1,270,-1:8,270:9,108,270:12,120:2,270,-1:2,120,270,-1,270,-1:8,2"
						+ "70:21,230,120:2,270,-1:2,120,270,-1,270,-1:8,270:3,231,270:18,120:2,270,-1:"
						+ "2,120,270,-1,270,-1:8,270:6,234,270:15,120:2,270,-1:2,120,270,-1,270,-1:8,2"
						+ "70:2,109,270:19,120:2,270,-1:2,120,270,-1,270,-1:8,235,270:21,120:2,270,-1:"
						+ "2,120,270,-1,270,-1:8,270:11,110,270:10,120:2,270,-1:2,120,270,-1,270,-1:8,"
						+ "270:6,111,270:15,120:2,270,-1:2,120,270,-1,270,-1:8,270:14,236,270:7,120:2,"
						+ "270,-1:2,120,270,-1,270,-1:8,270:12,237,270:9,120:2,270,-1:2,120,270,-1,270"
						+ ",-1:8,270:3,238,270:18,120:2,270,-1:2,120,270,-1,270,-1:8,270:6,239,270:15,"
						+ "120:2,270,-1:2,120,270,-1,270,-1:8,270:10,240,270:11,120:2,270,-1:2,120,270"
						+ ",-1,270,-1:8,270:5,112,270:16,120:2,270,-1:2,120,270,-1,270,-1:8,270:3,113,"
						+ "270:18,120:2,270,-1:2,120,270,-1,270,-1:8,270:9,121,270:12,120:2,270,-1:2,1"
						+ "20,270,-1,270,-1:8,270:8,150,270:13,120:2,270,-1:2,120,270,-1,270,-1:8,270:"
						+ "14,249,270:7,120:2,270,-1:2,120,270,-1,270,-1:8,270:15,152,270:6,120:2,270,"
						+ "-1:2,120,270,-1,270,-1:8,270:3,156,270:18,120:2,270,-1:2,120,270,-1,270,-1:"
						+ "8,270:2,161,270:19,120:2,270,-1:2,120,270,-1,270,-1:8,149,270:21,120:2,270,"
						+ "-1:2,120,270,-1,270,-1:8,270:21,162,120:2,270,-1:2,120,270,-1,270,-1:8,270:"
						+ "6,167,270:15,120:2,270,-1:2,120,270,-1,270,-1:8,270:8,164,270:13,120:2,270,"
						+ "-1:2,120,270,-1,270,-1:8,257,270:21,120:2,270,-1:2,120,270,-1,270,-1:8,270:"
						+ "11,177,270:10,120:2,270,-1:2,120,270,-1,270,-1:8,270:6,258,270:15,120:2,270"
						+ ",-1:2,120,270,-1,270,-1:8,270:8,256,270:13,120:2,270,-1:2,120,270,-1,270,-1"
						+ ":8,270:9,259,270:12,120:2,270,-1:2,120,270,-1,270,-1:8,270:5,275,270:16,120"
						+ ":2,270,-1:2,120,270,-1,270,-1:8,270:3,261,270:18,120:2,270,-1:2,120,270,-1,"
						+ "270,-1:8,270:10,188,270:11,120:2,270,-1:2,120,270,-1,270,-1:8,270:6,190,270"
						+ ":15,120:2,270,-1:2,120,270,-1,270,-1:8,270:4,196,270:17,120:2,270,-1:2,120,"
						+ "270,-1,270,-1:8,270:9,201,270:12,120:2,270,-1:2,120,270,-1,270,-1:8,270:6,2"
						+ "07,270:15,120:2,270,-1:2,120,270,-1,270,-1:8,270:4,265,270:17,120:2,270,-1:"
						+ "2,120,270,-1,270,-1:8,270:5,266,270:16,120:2,270,-1:2,120,270,-1,270,-1:8,2"
						+ "70:8,218,270:13,120:2,270,-1:2,120,270,-1,270,-1:8,270:3,223,270:18,120:2,2"
						+ "70,-1:2,120,270,-1,270,-1:8,270:6,227,270:15,120:2,270,-1:2,120,270,-1,270,"
						+ "-1:8,270:15,232,270:6,120:2,270,-1:2,120,270,-1,270,-1:8,233,270:21,120:2,2"
						+ "70,-1:2,120,270,-1,270,-1:8,154,270:21,120:2,270,-1:2,120,270,-1,270,-1:8,2"
						+ "70:9,182,270:12,120:2,270,-1:2,120,270,-1,270,-1:8,270:10,191,270:11,120:2,"
						+ "270,-1:2,120,270,-1,270,-1:8,270:6,195,270:15,120:2,270,-1:2,120,270,-1,270"
						+ ",-1:8,270:4,200,270:17,120:2,270,-1:2,120,270,-1,270,-1:8,270:6,210,270:15,"
						+ "120:2,270,-1:2,120,270,-1,270,-1:8,270:3,224,270:18,120:2,270,-1:2,120,270,"
						+ "-1,270,-1:8,270:2,122,270:8,123,270:10,120:2,270,-1:2,120,270,-1,270,-1:8,2"
						+ "70:10,193,270:11,120:2,270,-1:2,120,270,-1,270,-1:8,270:6,211,270:15,120:2,"
						+ "270,-1:2,120,270,-1,270,-1:8,270:13,124,270,125,270:6,120:2,270,-1:2,120,27"
						+ "0,-1,270,-1:8,270:8,126,270:13,120:2,270,-1:2,120,270,-1,270,-1:8,130,270:7"
						+ ",131,270:13,120:2,270,-1:2,120,270,-1,270,-1:8,270:2,132,270:19,120:2,270,-"
						+ "1:2,120,270,-1,270,-1:8,270,133,270,134,270:2,135,270:15,120:2,270,-1:2,120"
						+ ",270,-1,270,-1:8,270:3,247,270:18,120:2,270,-1:2,120,270,-1,270,-1:8,270:8,"
						+ "137,270:13,120:2,270,-1:2,120,270,-1,270,-1:8,242,138,270:20,120:2,270,-1:2"
						+ ",120,270,-1,270,-1:8,270:3,263,270:18,120:2,270,-1:2,120,270,-1,270,-1:8,27"
						+ "0:8,277,270:13,120:2,270,-1:2,120,270,-1,270,-1:8,270:6,269,270:15,120:2,27"
						+ "0,-1:2,120,270,-1,270,-1:8,270:9,274,270:12,120:2,270,-1:2,120,270,-1,270,-"
						+ "1:8,270:12,290,270:9,120:2,270,-1:2,120,270,-1,270,-1:8,270:20,291,270,120:"
						+ "2,270,-1:2,120,270,-1,270,-1:8,270:4,294,270:17,120:2,270,-1:2,120,270,-1,2"
						+ "70");
	private RULELexer() {
		this.yy_buffer = new char[this.YY_BUFFER_SIZE];
		this.yy_buffer_read = 0;
		this.yy_buffer_index = 0;
		this.yy_buffer_start = 0;
		this.yy_buffer_end = 0;
		this.yychar = 0;
		this.yy_at_bol = true;
		this.yy_lexical_state = this.YYINITIAL;
	}
	RULELexer(final java.io.InputStream instream) {
		this();
		if (null == instream) {
			throw new Error("Error: Bad input stream initializer.");
		}
		this.yy_reader =
			new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}
	RULELexer(final java.io.Reader reader) {
		this();
		if (null == reader) {
			throw new Error("Error: Bad input stream initializer.");
		}
		this.yy_reader = new java.io.BufferedReader(reader);
	}
	public sad.rule.creator.javacup.runtime.Symbol next_token()
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
									RULESymbols.IDENTIFIER,
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
								return new Symbol(RULESymbols.SEMI, ";");
							}
						case -4 :
							break;
						case 3 :
							{
								return new Symbol(RULESymbols.TWO_DOT, ":");
							}
						case -5 :
							break;
						case 4 :
							{
								return new Symbol(RULESymbols.LBRACE, "{");
							}
						case -6 :
							break;
						case 5 :
							{
								return new Symbol(RULESymbols.RBRACE, "}");
							}
						case -7 :
							break;
						case 6 :
							{
								return new Symbol(RULESymbols.LPAREN, "(");
							}
						case -8 :
							break;
						case 7 :
							{
								return new Symbol(RULESymbols.RPAREN, ")");
							}
						case -9 :
							break;
						case 8 :
							{
								return new Symbol(RULESymbols.COLON, ",");
							}
						case -10 :
							break;
						case 9 :
							{
								return new Symbol(RULESymbols.PLUS, "+");
							}
						case -11 :
							break;
						case 10 :
							{
								return new Symbol(RULESymbols.MINUS, "-");
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
								return new Symbol(RULESymbols.EQ, "EQ");
							}
						case -14 :
							break;
						case 13 :
							{
								return new Symbol(RULESymbols.TO, "TO");
							}
						case -15 :
							break;
						case 14 :
							{
								this.yybegin(this.COMMENT);
								this.comment_count = this.comment_count + 1;
							}
						case -16 :
							break;
						case 15 :
							{
								return new Symbol(RULESymbols.LOW, "LOW");
							}
						case -17 :
							break;
						case 16 :
							{
								return new Symbol(RULESymbols.INF, "INF");
							}
						case -18 :
							break;
						case 17 :
							{
								return new Symbol(RULESymbols.NEG, "NEG");
							}
						case -19 :
							break;
						case 18 :
							{
								return new Symbol(RULESymbols.ONE, "ONE");
							}
						case -20 :
							break;
						case 19 :
							{
								return new Symbol(RULESymbols.SUP, "SUP");
							}
						case -21 :
							break;
						case 20 :
							{
								return new Symbol(RULESymbols.RULE, "RULE");
							}
						case -22 :
							break;
						case 21 :
							{
								return new Symbol(RULESymbols.DIFF, "DIFF");
							}
						case -23 :
							break;
						case 22 :
							{
								return new Symbol(RULESymbols.INCL, "INCL");
							}
						case -24 :
							break;
						case 23 :
							{
								return new Symbol(RULESymbols.NONE, "NONE");
							}
						case -25 :
							break;
						case 24 :
							{
								return new Symbol(RULESymbols.FROM, "FROM");
							}
						case -26 :
							break;
						case 25 :
							{
								return new Symbol(RULESymbols.MANY, "MANY");
							}
						case -27 :
							break;
						case 26 :
							{
								return new Symbol(RULESymbols.HIGH, "HIGH");
							}
						case -28 :
							break;
						case 27 :
							{
								return new Symbol(RULESymbols.UNION, "UNION");
							}
						case -29 :
							break;
						case 28 :
							{
								return new Symbol(RULESymbols.ASSOC, "ASSOC");
							}
						case -30 :
							break;
						case 29 :
							{
								return new Symbol(RULESymbols.INTER, "INTER");
							}
						case -31 :
							break;
						case 30 :
							{
								return new Symbol(RULESymbols.COMPOS, "COMPOS");
							}
						case -32 :
							break;
						case 31 :
							{
								return new Symbol(RULESymbols.AGGREG, "AGGREG");
							}
						case -33 :
							break;
						case 32 :
							{
								return new Symbol(RULESymbols.INF_EQ, "INF_EQ");
							}
						case -34 :
							break;
						case 33 :
							{
								return new Symbol(RULESymbols.NOT_EQ, "NOT_EQ");
							}
						case -35 :
							break;
						case 34 :
							{
								return new Symbol(RULESymbols.MEDIUM, "MEDIUM");
							}
						case -36 :
							break;
						case 35 :
							{
								return new Symbol(RULESymbols.METRIC, "METRIC");
							}
						case -37 :
							break;
						case 36 :
							{
								return new Symbol(RULESymbols.SUP_EQ, "SUP_EQ");
							}
						case -38 :
							break;
						case 37 :
							{
								return new Symbol(RULESymbols.STRUCT, "STRUCT");
							}
						case -39 :
							break;
						case 38 :
							{
								return new Symbol(
									RULESymbols.INHERIT,
									"INHERIT");
							}
						case -40 :
							break;
						case 39 :
							{
								return new Symbol(
									RULESymbols.SEMANTIC,
									"SEMANTIC");
							}
						case -41 :
							break;
						case 40 :
							{
								return new Symbol(
									RULESymbols.VERY_LOW,
									"VERY_LOW");
							}
						case -42 :
							break;
						case 41 :
							{
								return new Symbol(
									RULESymbols.RULE_CARD,
									"RULE_CARD");
							}
						case -43 :
							break;
						case 42 :
							{
								return new Symbol(
									RULESymbols.CLASSNAME,
									"CLASSNAME");
							}
						case -44 :
							break;
						case 43 :
							{
								return new Symbol(
									RULESymbols.FIELDNAME,
									"FIELDNAME");
							}
						case -45 :
							break;
						case 44 :
							{
								return new Symbol(
									RULESymbols.VERY_HIGH,
									"VERY_HIGH");
							}
						case -46 :
							break;
						case 45 :
							{
								return new Symbol(
									RULESymbols.ONE_METHOD,
									"ONE_METHOD");
							}
						case -47 :
							break;
						case 46 :
							{
								return new Symbol(
									RULESymbols.METHODNAME,
									"METHODNAME");
							}
						case -48 :
							break;
						case 47 :
							{
								return new Symbol(
									RULESymbols.IS_ABSTRACT,
									"IS_ABSTRACT");
							}
						case -49 :
							break;
						case 48 :
							{
								return new Symbol(
									RULESymbols.ONE_OR_MANY,
									"ONE_OR_MANY");
							}
						case -50 :
							break;
						case 49 :
							{
								return new Symbol(
									RULESymbols.PUBLIC_FIELD,
									"PUBLIC_FIELD");
							}
						case -51 :
							break;
						case 50 :
							{
								return new Symbol(
									RULESymbols.PRIVATE_FIELD,
									"PRIVATE_FIELD");
							}
						case -52 :
							break;
						case 51 :
							{
								return new Symbol(
									RULESymbols.OPTIONNALY_ONE,
									"OPTIONNALY_ONE");
							}
						case -53 :
							break;
						case 52 :
							{
								return new Symbol(
									RULESymbols.GLOBAL_VARIABLE,
									"GLOBAL_VARIABLE");
							}
						case -54 :
							break;
						case 53 :
							{
								return new Symbol(
									RULESymbols.METHOD_ACCESSOR,
									"METHOD_ACCESSOR");
							}
						case -55 :
							break;
						case 54 :
							{
								return new Symbol(
									RULESymbols.METHOD_NO_PARAM,
									"METHOD_NO_PARAM");
							}
						case -56 :
							break;
						case 55 :
							{
								return new Symbol(
									RULESymbols.MULTIPLE_INTERFACE,
									"MULTIPLE_INTERFACE");
							}
						case -57 :
							break;
						case 56 :
							{
								return new Symbol(
									RULESymbols.DIFFERENT_PARAMETER,
									"DIFFERENT_PARAMETER");
							}
						case -58 :
							break;
						case 57 :
							{
							}
						case -59 :
							break;
						case 58 :
							{
							}
						case -60 :
							break;
						case 60 :
							{
							}
						case -61 :
							break;
						case 61 :
							{
								this.comment_count = this.comment_count - 1;
								if (this.comment_count == 0) {
									this.yybegin(this.YYINITIAL);
								}
							}
						case -62 :
							break;
						case 62 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -63 :
							break;
						case 63 :
							{
							}
						case -64 :
							break;
						case 65 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -65 :
							break;
						case 66 :
							{
							}
						case -66 :
							break;
						case 68 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -67 :
							break;
						case 69 :
							{
							}
						case -68 :
							break;
						case 71 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -69 :
							break;
						case 72 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -70 :
							break;
						case 73 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -71 :
							break;
						case 74 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -72 :
							break;
						case 75 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -73 :
							break;
						case 76 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -74 :
							break;
						case 77 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -75 :
							break;
						case 78 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -76 :
							break;
						case 79 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -77 :
							break;
						case 80 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -78 :
							break;
						case 81 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -79 :
							break;
						case 82 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -80 :
							break;
						case 83 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -81 :
							break;
						case 84 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -82 :
							break;
						case 85 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -83 :
							break;
						case 86 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -84 :
							break;
						case 87 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -85 :
							break;
						case 88 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -86 :
							break;
						case 89 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -87 :
							break;
						case 90 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -88 :
							break;
						case 91 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -89 :
							break;
						case 92 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -90 :
							break;
						case 93 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -91 :
							break;
						case 94 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -92 :
							break;
						case 95 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -93 :
							break;
						case 96 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -94 :
							break;
						case 97 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -95 :
							break;
						case 98 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -96 :
							break;
						case 99 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -97 :
							break;
						case 100 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -98 :
							break;
						case 101 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -99 :
							break;
						case 102 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -100 :
							break;
						case 103 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -101 :
							break;
						case 104 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -102 :
							break;
						case 105 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -103 :
							break;
						case 106 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -104 :
							break;
						case 107 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -105 :
							break;
						case 108 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -106 :
							break;
						case 109 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -107 :
							break;
						case 110 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -108 :
							break;
						case 111 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -109 :
							break;
						case 112 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -110 :
							break;
						case 113 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -111 :
							break;
						case 114 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -112 :
							break;
						case 115 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -113 :
							break;
						case 116 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -114 :
							break;
						case 117 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -115 :
							break;
						case 118 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -116 :
							break;
						case 119 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -117 :
							break;
						case 120 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -118 :
							break;
						case 121 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -119 :
							break;
						case 122 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -120 :
							break;
						case 123 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -121 :
							break;
						case 124 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -122 :
							break;
						case 125 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -123 :
							break;
						case 126 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -124 :
							break;
						case 127 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -125 :
							break;
						case 128 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -126 :
							break;
						case 129 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -127 :
							break;
						case 130 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -128 :
							break;
						case 131 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -129 :
							break;
						case 132 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -130 :
							break;
						case 133 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -131 :
							break;
						case 134 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -132 :
							break;
						case 135 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -133 :
							break;
						case 136 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -134 :
							break;
						case 137 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -135 :
							break;
						case 138 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -136 :
							break;
						case 139 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -137 :
							break;
						case 140 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -138 :
							break;
						case 141 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -139 :
							break;
						case 142 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -140 :
							break;
						case 143 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -141 :
							break;
						case 144 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -142 :
							break;
						case 145 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -143 :
							break;
						case 146 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -144 :
							break;
						case 147 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -145 :
							break;
						case 148 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -146 :
							break;
						case 149 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -147 :
							break;
						case 150 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -148 :
							break;
						case 151 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -149 :
							break;
						case 152 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -150 :
							break;
						case 153 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -151 :
							break;
						case 154 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -152 :
							break;
						case 155 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -153 :
							break;
						case 156 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -154 :
							break;
						case 157 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -155 :
							break;
						case 158 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -156 :
							break;
						case 159 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -157 :
							break;
						case 160 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -158 :
							break;
						case 161 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -159 :
							break;
						case 162 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -160 :
							break;
						case 163 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -161 :
							break;
						case 164 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -162 :
							break;
						case 165 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -163 :
							break;
						case 166 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -164 :
							break;
						case 167 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -165 :
							break;
						case 168 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -166 :
							break;
						case 169 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -167 :
							break;
						case 170 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -168 :
							break;
						case 171 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -169 :
							break;
						case 172 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -170 :
							break;
						case 173 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -171 :
							break;
						case 174 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -172 :
							break;
						case 175 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -173 :
							break;
						case 176 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -174 :
							break;
						case 177 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -175 :
							break;
						case 178 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -176 :
							break;
						case 179 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -177 :
							break;
						case 180 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -178 :
							break;
						case 181 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -179 :
							break;
						case 182 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -180 :
							break;
						case 183 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -181 :
							break;
						case 184 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -182 :
							break;
						case 185 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -183 :
							break;
						case 186 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -184 :
							break;
						case 187 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -185 :
							break;
						case 188 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -186 :
							break;
						case 189 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -187 :
							break;
						case 190 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -188 :
							break;
						case 191 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -189 :
							break;
						case 192 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -190 :
							break;
						case 193 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -191 :
							break;
						case 194 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -192 :
							break;
						case 195 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -193 :
							break;
						case 196 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -194 :
							break;
						case 197 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -195 :
							break;
						case 198 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -196 :
							break;
						case 199 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -197 :
							break;
						case 200 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -198 :
							break;
						case 201 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -199 :
							break;
						case 202 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -200 :
							break;
						case 203 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -201 :
							break;
						case 204 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -202 :
							break;
						case 205 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -203 :
							break;
						case 206 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -204 :
							break;
						case 207 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -205 :
							break;
						case 208 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -206 :
							break;
						case 209 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -207 :
							break;
						case 210 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -208 :
							break;
						case 211 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -209 :
							break;
						case 212 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -210 :
							break;
						case 213 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -211 :
							break;
						case 214 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -212 :
							break;
						case 215 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -213 :
							break;
						case 216 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -214 :
							break;
						case 217 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -215 :
							break;
						case 218 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -216 :
							break;
						case 219 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -217 :
							break;
						case 220 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -218 :
							break;
						case 221 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -219 :
							break;
						case 222 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -220 :
							break;
						case 223 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -221 :
							break;
						case 224 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -222 :
							break;
						case 225 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -223 :
							break;
						case 226 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -224 :
							break;
						case 227 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -225 :
							break;
						case 228 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -226 :
							break;
						case 229 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -227 :
							break;
						case 230 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -228 :
							break;
						case 231 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -229 :
							break;
						case 232 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -230 :
							break;
						case 233 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -231 :
							break;
						case 234 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -232 :
							break;
						case 235 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -233 :
							break;
						case 236 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -234 :
							break;
						case 237 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -235 :
							break;
						case 238 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -236 :
							break;
						case 239 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -237 :
							break;
						case 240 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -238 :
							break;
						case 241 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -239 :
							break;
						case 242 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -240 :
							break;
						case 243 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -241 :
							break;
						case 244 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -242 :
							break;
						case 245 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -243 :
							break;
						case 246 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -244 :
							break;
						case 247 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -245 :
							break;
						case 248 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -246 :
							break;
						case 249 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -247 :
							break;
						case 250 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -248 :
							break;
						case 251 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -249 :
							break;
						case 252 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -250 :
							break;
						case 253 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -251 :
							break;
						case 254 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -252 :
							break;
						case 255 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -253 :
							break;
						case 256 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -254 :
							break;
						case 257 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -255 :
							break;
						case 258 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -256 :
							break;
						case 259 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -257 :
							break;
						case 260 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -258 :
							break;
						case 261 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -259 :
							break;
						case 262 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -260 :
							break;
						case 263 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -261 :
							break;
						case 264 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -262 :
							break;
						case 265 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -263 :
							break;
						case 266 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -264 :
							break;
						case 267 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -265 :
							break;
						case 268 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -266 :
							break;
						case 269 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -267 :
							break;
						case 270 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -268 :
							break;
						case 271 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -269 :
							break;
						case 272 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -270 :
							break;
						case 273 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -271 :
							break;
						case 274 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -272 :
							break;
						case 275 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -273 :
							break;
						case 276 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -274 :
							break;
						case 277 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -275 :
							break;
						case 278 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -276 :
							break;
						case 279 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -277 :
							break;
						case 280 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -278 :
							break;
						case 281 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -279 :
							break;
						case 282 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -280 :
							break;
						case 283 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -281 :
							break;
						case 284 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -282 :
							break;
						case 285 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -283 :
							break;
						case 286 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -284 :
							break;
						case 287 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -285 :
							break;
						case 288 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -286 :
							break;
						case 289 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -287 :
							break;
						case 290 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -288 :
							break;
						case 291 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -289 :
							break;
						case 292 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -290 :
							break;
						case 293 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -291 :
							break;
						case 294 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -292 :
							break;
						case 295 :
							{
								return new Symbol(
									RULESymbols.IDENTIFIER,
									this.yychar,
									this.yychar + this.yytext().length(),
									this.yytext());
							}
						case -293 :
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
	//	private int yylength () {
	//		return this.yy_buffer_end - this.yy_buffer_start;
	//	}
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

	private java.lang.String yytext() {
		return new java.lang.String(
			this.yy_buffer,
			this.yy_buffer_start,
			this.yy_buffer_end - this.yy_buffer_start);
	}
}
