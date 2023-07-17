package com.sdmetrics.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.sdmetrics.test.Utils;

public class TestXMLParser {

	static class TestSaxHandler extends SAXHandler {

		String lastAttributeValueRead;
		boolean parseException = false;
		boolean saxException = false;
		boolean otherException = false;

		@Override
		public void startElement(String uri, String local, String raw,
				Attributes attrs) throws SAXException {
			lastAttributeValueRead = attrs.getValue("value");
			if ("fishy".equals(raw)) {
				if (otherException)
					throw new NullPointerException("something is fishy");
				if (saxException)
					throw new SAXException("something is very fishy");
				if (parseException)
					throw new SAXParseException("sushi alert", locator);
			}
		}
	}

	private XMLParser parser;
	private TestSaxHandler handler;
	private final static String TESTFILE = Utils.TEST_UTIL_DIR
			+ "tst%20 folder/test & file.xml";

	@Before
	public void initParser() throws Exception {
		parser = new XMLParser();
		handler = new TestSaxHandler();
	}

	@Test
	public void accessPlain() {
		assertParses(TESTFILE);
	}

	@Test
	public void accessURL() {
		URL url = getClass().getResource(
				"/com/sdmetrics/util/tst%20 folder/test & file.xml");
		assertParses(url.toString());
	}

	@Test
	public void accessZipped() {
		File archive = new File(Utils.TEST_UTIL_DIR
				+ "tst%20 folder/test archive.zip");
		String zippedFile = "jar:" + archive.toURI() + "!/test & file.xml";
		assertParses(zippedFile);
	}

	@Test
	public void parseError() {
		handler.parseException = true;
		assertParsesNot(TESTFILE, "Parse error in line 5: sushi alert");
		assertEquals("halibut", handler.lastAttributeValueRead);
	}

	@Test
	public void saxError() {
		handler.saxException = true;
		assertParsesNot(TESTFILE, "something is very fishy");
		assertEquals("halibut", handler.lastAttributeValueRead);
	}

	@Test
	public void unexpectedException() {
		handler.otherException = true;
		assertParsesNot(TESTFILE, "Internal parser error: something is fishy");
		assertEquals("halibut", handler.lastAttributeValueRead);
	}

	@Test
	public void invalidFiles() {
		assertParsesNot("", "No input file specified.");
		assertParsesNot(null, "No input file specified.");

		assertParsesNot("fish I like.xml",
				"Could not open file 'fish I like.xml'.");
	}

	private void assertParses(String file) {
		try {
			parser.parse(file, handler);
			assertEquals("chicken", handler.lastAttributeValueRead);
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	private void assertParsesNot(String file, String expectedError) {
		try {
			parser.parse(file, handler);
			fail("File " + file + " should not parse without errors");
		} catch (Exception ex) {
			assertEquals(expectedError, parser.getErrorMessage());
		}
	}
}
