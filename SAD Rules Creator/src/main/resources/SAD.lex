package rule.creator;

import rule.creator.RULESymbols;
import rule.creator.javacup.runtime.*;
import rule.creator.javacup.runtime.Symbol;
%%
%class RULELexer
%cup
%char

%{
  private int comment_count = 0;
%} 
%state COMMENT

ALPHA=[A-Za-z]
DIGIT=[0-9]
NON_WHITE_SPACE_CHAR=[^\n\ \r\t\b\012]


COMMENT_TEXT=([^/*\n]|[^*\n]"/"[^*\n]|[^/\n]"*"[^/\n]|"*"[^/\n]|"/"[^*\n])*


%%

<YYINITIAL> ";" { return new Symbol(RULESymbols.SEMI, ";"); } 
<YYINITIAL> ":" { return new Symbol(RULESymbols.TWO_DOT, ":"); } 
<YYINITIAL> "{" { return new Symbol(RULESymbols.LBRACE, "{"); }
<YYINITIAL> "}" { return new Symbol(RULESymbols.RBRACE, "}"); }
<YYINITIAL> "(" { return new Symbol(RULESymbols.LPAREN, "("); }
<YYINITIAL> ")" { return new Symbol(RULESymbols.RPAREN, ")"); }
<YYINITIAL> "," { return new Symbol(RULESymbols.COLON, ","); } 
<YYINITIAL> "RULE_CARD" { return new Symbol(RULESymbols.RULE_CARD, "RULE_CARD"); }
<YYINITIAL> "RULE" { return new Symbol(RULESymbols.RULE, "RULE"); }
<YYINITIAL> "INTER" { return new Symbol(RULESymbols.INTER, "INTER"); }
<YYINITIAL> "UNION" { return new Symbol(RULESymbols.UNION, "UNION"); }
<YYINITIAL> "DIFF" { return new Symbol(RULESymbols.DIFF, "DIFF"); }
<YYINITIAL> "INCL" { return new Symbol(RULESymbols.INCL, "INCL"); }
<YYINITIAL> "NEG" { return new Symbol(RULESymbols.NEG, "NEG"); }
<YYINITIAL> "METRIC" { return new Symbol(RULESymbols.METRIC, "METRIC"); }
<YYINITIAL> "SEMANTIC" { return new Symbol(RULESymbols.SEMANTIC, "SEMANTIC"); }
<YYINITIAL> "STRUCT" { return new Symbol(RULESymbols.STRUCT, "STRUCT"); }
<YYINITIAL> "VERY_HIGH" { return new Symbol(RULESymbols.VERY_HIGH, "VERY_HIGH"); }
<YYINITIAL> "HIGH" { return new Symbol(RULESymbols.HIGH, "HIGH"); }
<YYINITIAL> "MEDIUM" { return new Symbol(RULESymbols.MEDIUM, "MEDIUM"); }
<YYINITIAL> "LOW" { return new Symbol(RULESymbols.LOW, "LOW"); }
<YYINITIAL> "VERY_LOW" { return new Symbol(RULESymbols.VERY_LOW, "VERY_LOW"); }
<YYINITIAL> "NONE" { return new Symbol(RULESymbols.NONE, "NONE"); }
<YYINITIAL> "CLASSNAME" { return new Symbol(RULESymbols.CLASSNAME, "CLASSNAME"); }
<YYINITIAL> "METHODNAME" { return new Symbol(RULESymbols.METHODNAME, "METHODNAME"); }
<YYINITIAL> "FIELDNAME" { return new Symbol(RULESymbols.FIELDNAME, "FIELDNAME"); }

<YYINITIAL> "METHOD_ACCESSOR" { return new Symbol(RULESymbols.METHOD_ACCESSOR, "METHOD_ACCESSOR"); }
<YYINITIAL> "METHOD_NO_PARAM" { return new Symbol(RULESymbols.METHOD_NO_PARAM, "METHOD_NO_PARAM"); }
<YYINITIAL> "GLOBAL_VARIABLE" { return new Symbol(RULESymbols.GLOBAL_VARIABLE, "GLOBAL_VARIABLE"); }
<YYINITIAL> "IS_ABSTRACT" { return new Symbol(RULESymbols.IS_ABSTRACT, "IS_ABSTRACT"); }
<YYINITIAL> "DIFFERENT_PARAMETER" { return new Symbol(RULESymbols.DIFFERENT_PARAMETER, "DIFFERENT_PARAMETER"); }
<YYINITIAL> "ONE_METHOD" { return new Symbol(RULESymbols.ONE_METHOD, "ONE_METHOD"); }
<YYINITIAL> "PRIVATE_FIELD" { return new Symbol(RULESymbols.PRIVATE_FIELD, "PRIVATE_FIELD"); }
<YYINITIAL> "PUBLIC_FIELD" { return new Symbol(RULESymbols.PUBLIC_FIELD, "PUBLIC_FIELD"); }
<YYINITIAL> "MULTIPLE_INTERFACE" { return new Symbol(RULESymbols.MULTIPLE_INTERFACE, "MULTIPLE_INTERFACE"); }

<YYINITIAL> "ASSOC" { return new Symbol(RULESymbols.ASSOC, "ASSOC"); }
<YYINITIAL> "AGGREG" { return new Symbol(RULESymbols.AGGREG, "AGGREG"); }
<YYINITIAL> "COMPOS" { return new Symbol(RULESymbols.COMPOS, "COMPOS"); }
<YYINITIAL> "INHERIT" { return new Symbol(RULESymbols.INHERIT, "INHERIT"); }
<YYINITIAL> "FROM" { return new Symbol(RULESymbols.FROM, "FROM"); }
<YYINITIAL> "TO" { return new Symbol(RULESymbols.TO, "TO"); }
<YYINITIAL> "ONE" { return new Symbol(RULESymbols.ONE, "ONE"); }
<YYINITIAL> "MANY" { return new Symbol(RULESymbols.MANY, "MANY"); }
<YYINITIAL> "ONE_OR_MANY" { return new Symbol(RULESymbols.ONE_OR_MANY, "ONE_OR_MANY"); }
<YYINITIAL> "OPTIONNALY_ONE" { return new Symbol(RULESymbols.OPTIONNALY_ONE, "OPTIONNALY_ONE"); }


<YYINITIAL> "+" { return new Symbol(RULESymbols.PLUS, "+"); }
<YYINITIAL> "-" { return new Symbol(RULESymbols.MINUS, "-"); }

<YYINITIAL> "INF" { return new Symbol(RULESymbols.INF, "INF"); }
<YYINITIAL> "INF_EQ" { return new Symbol(RULESymbols.INF_EQ, "INF_EQ"); }
<YYINITIAL> "EQ" { return new Symbol(RULESymbols.EQ, "EQ"); }
<YYINITIAL> "SUP" { return new Symbol(RULESymbols.SUP, "SUP"); }
<YYINITIAL> "SUP_EQ" { return new Symbol(RULESymbols.SUP_EQ, "SUP_EQ"); }
<YYINITIAL> "NOT_EQ" { return new Symbol(RULESymbols.NOT_EQ, "NOT_EQ"); }
	
<YYINITIAL> [ \f\n\r\t]			{ /* Ignore white space */ }

<YYINITIAL,COMMENT> \n { }

<YYINITIAL> "/*" { yybegin(COMMENT); comment_count = comment_count + 1; }

<COMMENT> "/*" {  }
<COMMENT> "*/" { 
	comment_count = comment_count - 1; 
	if (comment_count == 0) {
    		yybegin(YYINITIAL);
	}
}
<COMMENT> {COMMENT_TEXT} { }



<YYINITIAL> ({ALPHA}|{DIGIT}|~|/|_|"-"|&|"*"|"+"|=|"."|<|>|"["|"]"|!|%|"|"|"^")*	{ return new Symbol(RULESymbols.IDENTIFIER, yychar, yychar + this.yytext().length(), this.yytext()); } 
