/*
 * (c) Copyright 2001-2007 Yann-Gaël Guéhéneuc,
 * University of Montréal.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the author, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package padl.creator;

import padl.creator.javacup.runtime.Symbol;
import util.multilingual.MultilingualManager;

%%
%implements padl.creator.javacup.runtime.Scanner
%function next_token
%type padl.creator.javacup.runtime.Symbol
%char
%unicode
%line
%class MSELexer
%public
%state COMMENT
%{
	public void reportErrorMessage(final String aMessage, padl.creator.javacup.runtime.Symbol info) {
		System.err.println(
			MultilingualManager.getString(
				"Err_REPORT",
				MSELexer.class,
				new Object[] {
					aMessage,
					info,
					new Integer(this.yyline + 1),
					new Integer(this.yychar),
					this.yytext()}));
	}
%}

ALPHA=[A-Za-z]
DIGIT=[0-9]
NON_WHITE_SPACE_CHAR=[^\n\ \r\t\b\012]
COMMENT_TEXT=([^/*\n]|[^*\n]"/"[^*\n]|[^/\n]"*"[^/\n]|"*"[^/\n]|"/"[^*\n])*
WHITE_SPACE_CHAR=[\n\ \t\b\012]
STRING_TEXT=([^\n\']|{WHITE_SPACE_CHAR}+)*

%%

<YYINITIAL> ":"					{ return new Symbol(MSESymbols.COLON, ":"); } 
<YYINITIAL> "id" 				{ return new Symbol(MSESymbols.ID, "id"); }
<YYINITIAL> "idref" 			{ return new Symbol(MSESymbols.IDREF, "idref"); }
<YYINITIAL> "primitive" 		{ return new Symbol(MSESymbols.PRIMITIVE, "primitive"); }
<YYINITIAL> "value" 			{ return new Symbol(MSESymbols.VALUE, "value"); }
<YYINITIAL> "path" 				{ return new Symbol(MSESymbols.PATH, "path"); }
<YYINITIAL> "true"				{ return new Symbol(MSESymbols.TRUE, "true"); } 
<YYINITIAL> "false"				{ return new Symbol(MSESymbols.FALSE, "false"); } 
<YYINITIAL> "*"					{ return new Symbol(MSESymbols.STAR, "*"); } 
<YYINITIAL> "("					{ return new Symbol(MSESymbols.LPAREN, "("); } 
<YYINITIAL> ")"					{ return new Symbol(MSESymbols.RPAREN, ")"); } 
<YYINITIAL> [ \f\n\r\t]			{ /* Ignore white space */ }
<YYINITIAL> "'"{STRING_TEXT}"'"			     	{ return new Symbol(MSESymbols.STRING, yychar, yychar + this.yytext().length(), this.yytext().substring(1, this.yytext().length() - 2)); } 
<YYINITIAL> {ALPHA}({ALPHA}|{DIGIT}|".")*		{ return new Symbol(MSESymbols.NAME, yychar, yychar + this.yytext().length(), this.yytext()); } 
<YYINITIAL> ("+"|"-")?{DIGIT}*("."{DIGIT}+)?	{ return new Symbol(MSESymbols.INTEGER, yychar, yychar + this.yytext().length(), this.yytext()); } 
<YYINITIAL> "#" 				{ yybegin(COMMENT); }
<YYINITIAL> .					{ System.err.println("Illegal character: " + yytext()); }

<COMMENT> "#" 					{ }
<COMMENT> [\n\r] 				{ yybegin(YYINITIAL); }
<COMMENT> {COMMENT_TEXT} 		{ }
<COMMENT> .						{ }