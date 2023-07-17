package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.sdmetrics.test.MetricTestContext;

public class TestRuleFilter {

	RuleFilter rf;

	@Test
	public void validation() {
		try {
			rf = new RuleFilter("a+b");
		} catch (SDMetricsException ex) {
			assertEquals(
					"Invalid design rule filter: a+b\nOperation '+' not allowed in filter expressions.",
					ex.getMessage());
		}

		try {
			rf = new RuleFilter("a|12");
		} catch (SDMetricsException ex) {
			assertEquals(
					"Invalid design rule filter: a|12\nUnexpected operand '12'.",
					ex.getMessage());
		}
	}

	@Test
	public void parsing() {
		try {
			rf = new RuleFilter("(a|b");
		} catch (SDMetricsException ex) {
			assertEquals(
					"Invalid design rule filter: (a|b\nParse error at position 4: Missing closing parenthesis opened at position 1.",
					ex.getMessage());
		}
	}

	@Test
	public void emptyFilter() throws SDMetricsException {
		rf = new RuleFilter("");
		assertMatch(true);
		assertMatch(true, "a", "b", "c");

		rf = new RuleFilter(null);
		assertMatch(true);
		assertMatch(true, "a", "b", "c");
	}

	@Test
	public void constants() throws SDMetricsException {
		rf = new RuleFilter("b");
		assertMatch(true);
		assertMatch(true, "a", "b", "c");
		assertMatch(false, "a", "c");

		rf = new RuleFilter("'b'");
		assertMatch(false);
		assertMatch(true, "a", "b", "c");
		assertMatch(false, "a", "c");
	}

	@Test
	public void andOperation() throws SDMetricsException {
		rf = new RuleFilter("a&b");
		assertMatch(true);
		assertMatch(true, "a", "b", "c");
		assertMatch(false, "a", "c");
		assertMatch(false, "b", "c");
	}

	@Test
	public void orOperation() throws SDMetricsException {
		rf = new RuleFilter("a|b");
		assertMatch(true);
		assertMatch(true, "a", "b", "c");
		assertMatch(true, "a", "c");
		assertMatch(true, "b", "c");
		assertMatch(false, "c");
	}

	@Test
	public void notOperation() throws SDMetricsException {
		rf = new RuleFilter("!a");
		assertMatch(false);
		assertMatch(false, "a", "b", "c");
		assertMatch(true, "b", "c");
	}

	@Test
	public void compoundTerm() throws SDMetricsException {
		rf = new RuleFilter("!a&(b|c)");
		assertMatch(false, "a", "b", "c");
		assertMatch(true, "c");
		assertMatch(false, "d");
	}

	@Test
	public void identifierCheck() throws Exception {
		MetricTestContext mtc = MetricTestContext.getStandardContext04();

		rf = new RuleFilter();
		assertEquals("", rf.checkIdentifiers(mtc.ms));

		rf = new RuleFilter("");
		assertEquals("", rf.checkIdentifiers(mtc.ms));

		rf = new RuleFilter("analysis|'design'");
		assertEquals("", rf.checkIdentifiers(mtc.ms));

		rf = new RuleFilter("analysis|design&(!implementation)");
		assertEquals("implementation", rf.checkIdentifiers(mtc.ms));
	}

	private void assertMatch(boolean expectedMatch, String... areas) {
		List<String> areaList = areas.length > 0 ? Arrays.asList(areas) : null;
		Rule r = new Rule("test", null, null, null, areaList, true);
		assertEquals(Boolean.valueOf(expectedMatch),
				Boolean.valueOf(rf.match(r)));
	}
}
