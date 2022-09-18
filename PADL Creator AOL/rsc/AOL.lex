/*
 * (c) Copyright 2001-2004 Yann-Gaël Guéhéneuc,
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
package padl.creator.parser;

import padl.creator.javacup.runtime.Symbol;
import util.multilingual.MultilingualManager;

%%
%implements padl.creator.javacup.runtime.Scanner
%function next_token
%type padl.creator.javacup.runtime.Symbol
%char
%line
%class AOLLexer
%public
%state COMMENT
%{
	public void reportErrorMessage(final String aMessage, padl.creator.javacup.runtime.Symbol info) {
		System.err.println(
			MultilingualManager.getString(
				"Err_REPORT",
				AOLLexer.class,
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

%%

<YYINITIAL> "*"					{ return new Symbol(AOLSymbols.POINTER, "POINTER"); } 
<YYINITIAL> "&"					{ return new Symbol(AOLSymbols.POINTER, "POINTER"); } 
<YYINITIAL> "ABSTRACT"			{ return new Symbol(AOLSymbols.ABSTRACT, "ABSTRACT"); } 
<YYINITIAL> "AGGREGATION"		{ return new Symbol(AOLSymbols.AGGREGATION, "AGGREGATION"); } 
<YYINITIAL> "ATTRIBUTES"		{ return new Symbol(AOLSymbols.ATTRIBUTES, "ATTRIBUTES"); } 
<YYINITIAL> "class"				{ return new Symbol(AOLSymbols.CLASS, "CLASS"); } 
<YYINITIAL> "CLASS"				{ return new Symbol(AOLSymbols.CLASS, "CLASS"); } 
<YYINITIAL> "const"				{ return new Symbol(AOLSymbols.CONST, "const"); } 
<YYINITIAL> ":"					{ return new Symbol(AOLSymbols.COLON, ":"); } 
<YYINITIAL> ","					{ return new Symbol(AOLSymbols.COMMA, ","); } 
<YYINITIAL> "CONTAINER"			{ return new Symbol(AOLSymbols.CONTAINER, "CONTAINER"); } 
<YYINITIAL> "GENERALIZATION"	{ return new Symbol(AOLSymbols.GENERALIZATION, "GENERALIZATION"); } 
<YYINITIAL> "MANY"				{ return new Symbol(AOLSymbols.MANY, "MANY"); } 
<YYINITIAL> "MULT"				{ return new Symbol(AOLSymbols.MULT, "MULT"); } 
<YYINITIAL> "NAME"				{ return new Symbol(AOLSymbols.NAME, "NAME"); } 
<YYINITIAL> "ONE"				{ return new Symbol(AOLSymbols.ONE, "ONE"); } 
<YYINITIAL> "ONE_OR_MANY"		{ return new Symbol(AOLSymbols.ONE_OR_MANY, "ONE_OR_MANY"); } 
<YYINITIAL> "OPERATIONS"		{ return new Symbol(AOLSymbols.OPERATIONS, "OPERATIONS"); } 
<YYINITIAL> "OPTIONALLY_ONE"	{ return new Symbol(AOLSymbols.OPTIONALLY_ONE, "OPTIONALLY_ONE"); } 
<YYINITIAL> "PARTS"				{ return new Symbol(AOLSymbols.PARTS, "PARTS"); } 
<YYINITIAL> "PRIVATE"			{ return new Symbol(AOLSymbols.PRIVATE, "PRIVATE"); } 
<YYINITIAL> "PROTECTED"			{ return new Symbol(AOLSymbols.PROTECTED, "PROTECTED"); } 
<YYINITIAL> "PUBLIC"			{ return new Symbol(AOLSymbols.PUBLIC, "PUBLIC"); } 
<YYINITIAL> "RELATION"			{ return new Symbol(AOLSymbols.RELATION, "RELATION"); } 
<YYINITIAL> "ROLES"				{ return new Symbol(AOLSymbols.ROLES, "ROLES"); } 
<YYINITIAL> "SHARED"			{ return new Symbol(AOLSymbols.SHARED, "SHARED"); } 
<YYINITIAL> "template"			{ return new Symbol(AOLSymbols.TEMPLATE, "template"); } 
<YYINITIAL> "typename"			{ return new Symbol(AOLSymbols.TYPENAME, "typename"); } 
<YYINITIAL> "UNDEFINED"			{ return new Symbol(AOLSymbols.UNDEFINED, "UNDEFINED"); } 
<YYINITIAL> "unsigned"			{ return new Symbol(AOLSymbols.UNSIGNED, "unsigned"); } 
<YYINITIAL> "volatile"			{ return new Symbol(AOLSymbols.VOLATILE, "volatile"); } 
<YYINITIAL> "("					{ return new Symbol(AOLSymbols.LPAREN, "("); } 
<YYINITIAL> ")"					{ return new Symbol(AOLSymbols.RPAREN, ")"); } 
<YYINITIAL> "<"					{ return new Symbol(AOLSymbols.LBRACE, "<"); } 
<YYINITIAL> ">"					{ return new Symbol(AOLSymbols.RBRACE, ">"); } 
<YYINITIAL> ";"					{ return new Symbol(AOLSymbols.SEMICOLON, ";"); } 
<YYINITIAL> "SUBCLASSES"		{ return new Symbol(AOLSymbols.SUBCLASSES, "SUBCLASSES"); } 
<YYINITIAL> "UNDEF_SCOPE"		{ return new Symbol(AOLSymbols.UNDEF_SCOPE, "UNDEF_SCOPE"); } 
<YYINITIAL> [ \f\n\r\t]			{ /* Ignore white space */ }
<YYINITIAL> ({ALPHA}|{DIGIT}|~|/|_|=|"."|"["|"]"|!|%|"|"|"^"|"+"|"-"|"@")*	{ return new Symbol(AOLSymbols.IDENTIFIER, yychar, yychar + this.yytext().length(), this.yytext()); } 
<YYINITIAL> "#" 				{ yybegin(COMMENT); }
<YYINITIAL> .					{ System.err.println("Illegal character: " + yytext()); }

<COMMENT> "#" 					{ }
<COMMENT> [\n\r] 				{ yybegin(YYINITIAL); }
<COMMENT> {COMMENT_TEXT} 		{ }
<COMMENT> .						{ }