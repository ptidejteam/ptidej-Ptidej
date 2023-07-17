package com.sdmetrics.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.sdmetrics.test.SAXAttributes;
import com.sdmetrics.test.Utils;

public class TestSAXHandler {

	private SAXAttributes attrib = new SAXAttributes();
	private SAXHandler saxHandler = new SAXHandler() {
		@Override
		public void startElement(String uri, String local, String raw,
				Attributes attrs) throws SAXException {
			checkVersion(attrs, null);
		}
	};

	@Test
	public void correctVersionAttribute() throws SAXException {
		attrib.addAttribute("version", "2.0");
		saxHandler.checkVersion(attrib, null);
		saxHandler.checkVersion(attrib, "1.1");
	}

	@Test
	public void missingVersionAttribute() {
		checkVersionExpectingSAXException(null,
				"Version number specification missing.");
	}

	@Test
	public void unsupportedVersion() {
		attrib.addAttribute("version", "0.9b");
		checkVersionExpectingSAXException(null,
				"File version \"0.9b\" not supported.");
	}

	@Test
	public void unknownSinceVersion() throws SAXException {
		attrib.addAttribute("version", "1.3");
		try {
			saxHandler.checkVersion(attrib, "0.9b");
			fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException ex) {
			assertEquals("Since file version \"0.9b\" unknown.",
					ex.getMessage());
		}
	}

	@Test
	public void expiredVersion() {
		attrib.addAttribute("version", "1.3");
		checkVersionExpectingSAXException("2.0",
				"File version \"1.3\" not supported anymore.");
	}

	@Test
	public void errorReporting() throws Exception {
		XMLParser parser = new XMLParser();
		try {
			parser.parse(
					Utils.TEST_UTIL_DIR + "testSAXHandlerWrongVersion.xml",
					saxHandler);
			fail("SAXException expected");
		} catch (SAXException ex) {
			assertEquals("Error in line 3: File version \"97\" not supported.",
					parser.getErrorMessage());
		}
	}

	private void checkVersionExpectingSAXException(String version,
			String expectedMessage) {
		try {
			saxHandler.checkVersion(attrib, version);
			fail("SAX Exception expected");
		} catch (SAXException ex) {
			assertEquals(expectedMessage, ex.getMessage());
		}
	}
}
