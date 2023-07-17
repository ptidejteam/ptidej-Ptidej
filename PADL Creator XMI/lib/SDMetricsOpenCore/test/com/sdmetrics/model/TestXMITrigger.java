package com.sdmetrics.model;

import static com.sdmetrics.model.XMITrigger.TriggerType.GCATTRVAL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Locale;

import org.junit.Test;

public class TestXMITrigger {

	@Test
	public void constructor() {
		XMITrigger trigger = new XMITrigger("age", "gcattrval", "person",
				"Alter", "home");
		assertEquals("age", trigger.name);
		assertEquals(GCATTRVAL, trigger.type);
		assertEquals("person", trigger.src);
		assertEquals("Alter", trigger.attr);
		assertEquals("home", trigger.linkback);
	}

	@Test
	public void unkownTrigger() {
		assertEquals("Unknown trigger type 'random'.",
				instantiateExpectingException("random", "person", "age"));
	}

	@Test
	public void missingXMIAttribute() {
		assertEquals(
				"Attribute 'attr' must be specified for triggers of type 'constant'.",
				instantiateExpectingException("constant", "person", null));
		instantiateExpectingException("attrval", "person", null);
		instantiateExpectingException("xmi2assoc", "person", null);

		// instantiate type with optional attr argument - must not throw an
		// exception
		assertNotNull(new XMITrigger("age", "ignore", null, "Alter", null));
	}

	@Test
	public void missingXMISource() {
		assertEquals(
				"Attribute 'src' must be specified for triggers of type 'ctext'.",
				instantiateExpectingException("ctext", null, "age"));
		instantiateExpectingException("cattrval", null, "age");
		instantiateExpectingException("gcattrval", null, "age");
		instantiateExpectingException("reflist", null, "age");

		// instantiate type with optional source attribute - must not throw an
		// exception
		assertNotNull(new XMITrigger("age", "constant", null, "Alter", null));
	}

	@Test
	public void turkishLocale() {
		// Test trigger instantiation with Turkish locale
		// (proper upper/lowercase conversions of i/I)
		Locale oldLocale = Locale.getDefault();
		try {
			Locale.setDefault(new Locale("tr"));
			XMITrigger trigger = new XMITrigger("age", "ignore", "person",
					"Alter", null);
			assertEquals("ignore", trigger.type.toString());
		} finally {
			Locale.setDefault(oldLocale);
		}
	}

	@SuppressWarnings("unused")
	private String instantiateExpectingException(String type, String src,
			String attr) {
		try {
			new XMITrigger("age", type, src, attr, null);
			fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException ex) {
			return ex.getMessage();
		}
		return null;
	}
}
