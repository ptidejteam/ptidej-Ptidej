package com.sdmetrics.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

public class TestExpressionParser {

	private static final HashSet<String> FUNCTIONNAMES = new HashSet<String>(
			Arrays.asList("ln", "exp"));

	private static final HashSet<String> HIGHPRECRELATIONS = new HashSet<String>(
			Arrays.asList("startswith", "endswith"));

	private static final HashSet<String> LOWPRECRELATIONS = new HashSet<String>(
			Arrays.asList("upto", "topmost"));

	private ExpressionParser parser = new ExpressionParser(FUNCTIONNAMES,
			HIGHPRECRELATIONS, LOWPRECRELATIONS);

	@Test
	public void numbers() {
		parser = new ExpressionParser();
		assertParsing("n[3]", "3");
		assertParsing("n[03.1415]", "03.1415");
		assertParsing("n[3E2]", "3E2");
		assertParsing("n[3e-4]", "3e-4");
		assertParsing("n[0.345E-6]", "0.345E-6");
		assertParsing("( o[-] n[3e-4] n[4E+5] )", "3e-4-4E+5");
	}

	@Test
	public void identifiers() {
		assertParsing("i[a]", "a");
		assertParsing("i[arbitrary]", "arbitrary");
		assertParsing("i[fct_x]", "fct_x");
		assertParsing("i[COOL_WATER24]", "COOL_WATER24");
	}

	@Test
	public void stringConstants() {
		assertParsing("s[hello]", "'hello'");
		assertParsing("s[What 0.23 is\t\"This]", "'What 0.23 is\t\"This'");
		assertParsing("( o[+] s[sugar] s[spice] )", "'sugar'+'spice'");
	}

	@Test
	public void operators() {

		assertParsing("( o[+] n[4] i[b] )", "4+b");
		assertParsing("( o[-] i[a] n[2] )", "a-2");
		assertParsing("( o[*] i[a] i[b] )", "a*b");
		assertParsing("( o[/] i[a] i[b] )", "a/b");
		assertParsing("( o[^] i[a] i[b] )", "a^b");

		assertParsing("( o[.] i[a] i[b] )", "a.b");

		assertParsing("( o[->] i[a] i[b] )", "a->b");
		assertParsing("( o[->] i[a] i[b] )", "a in b");

		assertParsing("( o[upto] i[a] i[b] )", "a upto b");
		assertParsing("( o[topmost] i[a] i[b] )", "a topmost b");

		// Verify operator precedence
		assertParsing("( o[+] i[a] ( o[*] i[b] i[c] ) )", "a+b*c");
		assertParsing("( o[/] i[a] ( o[^] i[b] i[c] ) )", "a/b^c");
		assertParsing("( o[^] i[a] ( o[.] i[b] i[c] ) )", "a^b.c");

		assertParsing("( o[*] ( o[-] i[a] ) i[b] )", "-a*b");
		assertParsing("( o[*] i[a] ( o[-] i[b] ) )", "a*-b");
		assertParsing("( o[^] ( o[+] i[a] ) i[b] )", "+a^b");
		assertParsing("( o[^] i[a] ( o[+] i[b] ) )", "a^+b");

		// Verify left associativity
		assertParsing("( o[-] ( o[+] s[three] n[4] ) n[5] )", "'three'+4-5");
		assertParsing("( o[/] ( o[*] i[a] i[b] ) i[c] )", "a*b/c");
		assertParsing("( o[->] ( o[^] i[a] i[b] ) i[c] )", "a^b->c");
		assertParsing("( o[.] ( o[.] i[a] i[b] ) i[c] )", "a.b.c");
		assertParsing("( o[topmost] ( o[upto] i[a] i[b] ) i[c] )",
				"a upto b topmost c");
		assertParsing("( o[+] ( o[-] i[a] ) )", "+-a");
	}

	@Test
	public void relations() {
		// relations
		assertParsing("( o[=] i[a] i[b] )", "a=b");
		assertParsing("( o[!=] i[a] i[b] )", "a!=b");
		assertParsing("( o[<] i[a] i[b] )", "a<b");
		assertParsing("( o[>] i[a] i[b] )", "a>b");
		assertParsing("( o[<=] i[a] i[b] )", "a<=b");
		assertParsing("( o[>=] i[a] i[b] )", "a>=b");
		assertParsing("( o[<] i[a] i[b] )", "a&lt;b");
		assertParsing("( o[<] i[a] i[b] )", "a lt b");
		assertParsing("( o[>] i[a] i[b] )", "a gt b");
		assertParsing("( o[<=] i[a] i[b] )", "a le b");
		assertParsing("( o[>=] i[a] i[b] )", "a ge b");

		assertParsing("( o[startswith] i[a] i[b] )", "a startswith b");
		assertParsing("( o[endswith] i[a] i[b] )", "a endswith b");

		// Verify operator precedence
		assertParsing("( o[=] i[a] ( o[>] i[b] i[c] ) )", "a=b>c");
		assertParsing("( o[!=] i[a] ( o[<=] i[b] i[c] ) )", "a!=b<=c");
		assertParsing("( o[=] i[a] ( o[startswith] i[b] i[c] ) )",
				"a=b startswith c");
		assertParsing("( o[>] i[a] ( o[+] i[b] i[c] ) )", "a>b+c");
		assertParsing("( o[*] n[0.5] ( o[^] ( o[exp] i[x] ) n[2] ) )",
				"0.5*exp(x)^2");
	}

	@Test
	public void logicalOperators() {
		assertParsing("( o[!] i[a] )", "!a");
		assertParsing("( o[!] i[a] )", "not a");
		assertParsing("( o[&] i[a] i[b] )", "a&b");
		assertParsing("( o[&] i[a] i[b] )", "a and b");
		assertParsing("( o[&] i[a] i[b] )", "a&amp;b");
		assertParsing("( o[|] i[a] i[b] )", "a|b");
		assertParsing("( o[|] i[a] i[b] )", "a or b");

		// Verify operator precedence
		assertParsing("( o[|] i[a] ( o[&] i[b] i[c] ) )", "a|b&c");
		assertParsing("( o[|] ( o[!] i[a] ) i[b] )", "!a|b");
		assertParsing("( o[&] i[a] ( o[!] i[b] ) )", "a&!b");
		assertParsing("( o[&] ( o[!] i[a] ) i[b] )", "!a&b");
		assertParsing("( o[&] i[a] ( o[!] i[b] ) )", "a&!b");
		assertParsing("( o[&] i[a] ( o[=] i[b] i[c] ) )", "a&b=c");

		// Verify left associativity
		assertParsing("( o[|] ( o[|] i[a] i[b] ) i[c] )", "a|b|c");
		assertParsing("( o[&] ( o[&] i[a] i[b] ) i[c] )", "a&b&c");
		assertParsing("( o[!] ( o[!] i[a] ) )", "!!a");
	}

	@Test
	public void functions() {
		assertParsing("( o[ln] i[x] )", "ln(x)");
		assertParsing("( o[ln] ( o[ln] i[x] ) )", "ln(ln(x))");
		assertParsing("( o[exp] ( o[+] i[x] n[1] ) )", "exp(x+1)");

		// function with argument list
		assertParsing("( o[ln] i[x] i[y] )", "ln(x,y)");
		assertParsing(
				"( o[+] ( o[ln] ( o[-] i[a] i[b] ) i[b] ( o[exp] i[c] ) ( o[^] i[x] n[2] ) ) ( o[exp] i[d] ) )",
				"ln((a-b),b,exp(c),x^2)+exp(d)");

		// legacy of early SDMetrics versions: functions had prefix fct_
		assertParsing("( o[ln] i[x] )", "fct_ln(x)");
		assertParsing("( o[exp] i[x] )", "fct_exp(x)");

	}

	@Test
	public void parentheses() {

		// override default precedence
		assertParsing("( o[*] ( o[+] i[a] i[b] ) i[c] )", "(a+b)*c");
		// override default associativity
		assertParsing("( o[&] i[a] ( o[&] i[b] i[c] ) )", "a&(b&c)");
		// nested and redundant parentheses
		assertParsing("( o[*] ( o[-] i[a] i[b] ) ( o[+] i[a] i[b] ) )",
				"((((a-b))*((a+b))))");

	}

	@Test
	public void errorHandling() {

		assertError(
				"Parse error at position 5: Unexpected token past end of expression: <=",
				"a>b<=c");
		assertError(
				"Parse error at position 3: Unexpected token past end of expression: b",
				"a b");
		assertError(
				"Parse error at position 2: Unexpected token past end of expression: !",
				"b!c");
		assertError("Parse error at position 2: Unexpected end of expression.",
				"a+");
		assertError("Parse error at position 4: Unexpected token: )", "(a+)");
		assertError("Parse error at position 3: Unexpected token: >", "a+>b");
		assertError("Parse error at position 3: Unexpected token: =", "a-=b");

		assertError(
				"Parse error at position 12: Missing closing apostrophe of string constant starting at position 3.",
				"a='forgot to");
		assertError(
				"Parse error at position 6: Missing closing parenthesis opened at position 3.",
				"a=(b+c");

		assertError(
				"Parse error at position 3: Missing fraction part of number constant.",
				"12.+4");
		assertError(
				"Parse error at position 5: Missing exponent of scientific notation number constant.",
				"3+12e");
		assertError("Parse error at position 3: Unexpected character: $",
				"a+${boooring}");

		assertError(
				"Parse error at position 4: Expected opening parenthesis following function call to 'ln'.",
				"ln x");
		assertError(
				"Parse error at position 7: Missing closing parenthesis following function call to 'exp' at position 3.",
				"exp(x+5");
	}

	private void assertError(String expectedMessage, String testExpression) {
		assertNull(parser.parseExpression(testExpression));
		assertEquals(expectedMessage, parser.getErrorInfo());
	}

	private void assertParsing(String expectedNormalForm, String testExpression) {
		ExpressionNode node = parser.parseExpression(testExpression);
		assertNotNull(node);
		assertEquals("", parser.getErrorInfo());
		assertEquals(expectedNormalForm, getNormalForm(node));
	}

	private String getNormalForm(ExpressionNode node) {
		if (node == null) {
			return "[null]";
		}
		if (node.isStringConstant()) {
			return "s[" + node.getValue() + "]";
		} else if (node.isNumberConstant()) {
			return "n[" + node.getValue() + "]";
		} else if (node.isIdentifier()) {
			return "i[" + node.getValue() + "]";
		} else if (node.isOperation()) {
			String result = "( o[" + node.getValue() + "] ";
			for (int i = 0; i < node.getOperandCount(); i++)
				result += getNormalForm(node.getOperand(i)) + " ";
			return result + ")";
		}
		throw new IllegalArgumentException("Unexpected type");
	}
}
