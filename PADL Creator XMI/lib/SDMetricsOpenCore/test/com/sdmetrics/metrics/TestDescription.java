package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestDescription {

	Description testDescription = new Description();

	@Test
	public void briefDescription() {
		addString("The number of operations in a class.((p))");
		assertEquals("The number of operations in a class.",
				testDescription.getBriefDescription());
	}

	@Test
	public void fullDescription() {
		addString("  \n\t\r  ");
		addString("   The number of operations in a class.((p))");
		testDescription.add(new char[0], 0, 0);
		addString("Includes overriding glossary://Operation/operations/. ");
		addString("See also glossary://WFR//, ref://CK94/ and metric://class/NumAttr/. ");
		addString("Not to forget((b))rule://class/Rule8/((/b)) and matrix://classgen/.");
		assertEquals(
				"The number of operations in a class.<p>"
						+ "Includes overriding <a href=\"glossary://Operation/operations/\">operations</a>. "
						+ "See also <a href=\"glossary://WFR//\">WFR</a>, [<a href=\"ref://CK94/\">CK94</a>] and <a href=\"metric://class/NumAttr/\">NumAttr</a>. "
						+ "Not to forget<b><a href=\"rule://class/Rule8/\">Rule8</a></b> and <a href=\"matrix://classgen/\">classgen</a>.",
				testDescription.getDescription());
	}

	@Test
	public void invalidDescription() {
		addString("See ref:/// and metric://class");
		assertEquals("See [<a href=\"ref:///\"></a>] and metric://class",
				testDescription.getDescription());

	}

	@Test
	public void emptyDescription() {
		assertEquals("", testDescription.getBriefDescription());
		assertEquals("", testDescription.getDescription());

		Description empty2 = new Description();
		assertEquals("", empty2.getDescription());
	}

	@Test
	public void alternativeSeparator() {
		addString("To be or not to be--- that is the question:");

		assertEquals("To be or not to be--- that is the question:",
				testDescription.getBriefDescription());
		testDescription.setSeparator("-");
		assertEquals("To be or not to be-",
				testDescription.getBriefDescription());
		testDescription.setSeparator("---");
		assertEquals("To be or not to be---",
				testDescription.getBriefDescription());
	}

	private void addString(String s) {
		testDescription.add(s.toCharArray(), 0, s.length());
	}
}
