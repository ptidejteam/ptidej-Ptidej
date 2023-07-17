package com.sdmetrics.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class TestMetaModelElement {

	MetaModelElement base;
	MetaModelElement child;

	@Before
	public void initTestElements() {
		// define type 'base' with data attribute 'id' and
		// reference attribute 'context'
		base = new MetaModelElement("base", null);
		base.addAttribute("id", false, false);
		base.addAttribute("context", true, false);
		base.addAttributeDescription("id", "The identifier ");
		base.addAttributeDescription("id", "of the element");

		// define type 'child' derived from base with additional
		// set attribute 'notes'
		child = new MetaModelElement("child", base);
		child.addAttribute("notes", false, true);
	}

	@Test
	public void attributeAccess() {
		assertEquals("base", base.getName());
		assertNull(base.getParent());

		Iterator<String> attrIter = base.getAttributeNames().iterator();
		assertEquals("id", attrIter.next());
		assertEquals("context", attrIter.next());
		assertFalse(attrIter.hasNext());

		checkBaseAttributes(base);
	}

	private void checkBaseAttributes(MetaModelElement me) {
		assertTrue(me.hasAttribute("id"));
		assertFalse(me.hasAttribute("name"));
		assertFalse(me.isRefAttribute("id"));
		assertTrue(me.isRefAttribute("context"));
		assertFalse(me.isSetAttribute("id"));
		assertEquals("The identifier of the element",
				me.getAttributeDescription("id"));
		assertEquals("", me.getAttributeDescription("context"));
		assertEquals(0, me.getAttributeIndex("id"));
		assertEquals(1, me.getAttributeIndex("context"));
	}

	@Test
	public void unknownAttribute() {
		try {
			child.getAttributeIndex("maxvelocity");
			fail("Exception expected");
		} catch (IllegalArgumentException ex) {
			assertEquals(
					"No attribute \"maxvelocity\" defined for elements of type \"child\".",
					ex.getMessage());
		}
	}

	@Test
	public void attributeInheritance() {
		Iterator<String> attrIter = child.getAttributeNames().iterator();
		assertEquals("id", attrIter.next()); // inherited attributes come first
		assertEquals("context", attrIter.next());
		assertEquals("notes", attrIter.next());
		assertFalse(attrIter.hasNext());

		checkBaseAttributes(child);
		assertFalse(child.isRefAttribute("notes"));
		assertTrue(child.isSetAttribute("notes"));
	}

	@Test
	public void specializes() {
		MetaModelElement grandChild = new MetaModelElement("grandChild", child);
		assertTrue(grandChild.specializes(child));
		assertTrue(grandChild.specializes(base));
		assertTrue(grandChild.specializes(grandChild));
		assertFalse(child.specializes(grandChild));

		MetaModelElement sibling = new MetaModelElement("sibling", base);
		assertFalse(sibling.specializes(child));
	}

	@Test
	public void extensionReference() {
		assertNull(base.getExtensionReference());
		base.setExtensionReference("baseCls");
		assertEquals("baseCls", base.getExtensionReference());

		MetaModelElement child2 = new MetaModelElement("child2", base);
		assertEquals("baseCls", child2.getExtensionReference());
	}
}
