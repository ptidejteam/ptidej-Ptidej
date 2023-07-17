package com.sdmetrics.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.test.Utils;
import com.sdmetrics.util.XMLParser;

public class TestModelElement {

	MetaModelElement testType;
	ModelElement me;

	@Before
	public void parseTestMetaModel() throws Exception {
		MetaModel mm = new MetaModel();
		new XMLParser().parse(Utils.TEST_MODEL_DIR + "testMetaModel03.xml",
				mm.getSAXParserHandler());
		testType = mm.getType("test");
		me = new ModelElement(testType);
	}

	@Test
	public void construction() {
		assertEquals("", me.getPlainAttribute("context"));
		assertEquals("", me.getPlainAttribute("id"));
		assertEquals(0, me.getSetAttribute("valueSet").size());
		assertEquals(0, me.getSetAttribute("elemSet").size());
		assertSame(testType, me.getType());
	}

	@Test
	public void accessPlainAttributes() {
		me.setAttribute("id", "CRM114");
		assertEquals("CRM114", me.getPlainAttribute("id"));
		assertEquals("CRM114", me.getXMIID());
	}

	@Test
	public void accessSetAttributes() {
		me.setAttribute("valueSet", "Bob Dylan");
		me.setAttribute("valueSet", "Neil Young");
		Collection<?> set = me.getSetAttribute("valueSet");
		assertEquals(2, set.size());
		assertTrue(set.contains("Bob Dylan"));
		assertTrue(set.contains("Neil Young"));

		HashSet<ModelElement> elemSet = new HashSet<ModelElement>();
		me.setSetAttribute("elemSet", elemSet);
		assertSame(elemSet, me.getSetAttribute("elemSet"));
	}

	@Test
	public void accessRefAttributes() {
		me.setRefAttribute("context", null);
		assertNull(me.getRefAttribute("context"));

		ModelElement me2 = new ModelElement(testType);
		me.setRefAttribute("context", me2);

		assertFalse(me2.getLinksIgnored());
		assertSame(me2, me.getRefAttribute("context"));
		assertSame(me2, me.getOwner());

		me2.setLinksIgnored(true);
		assertTrue(me2.getLinksIgnored());
		assertNull(me.getRefAttribute("context"));
	}

	@Test
	public void elementNames() {
		me.setAttribute("name", "do");
		me.setRefAttribute("context", null);
		assertEquals("do", me.getFullName());
		ModelElement me2 = new ModelElement(testType);
		me2.setAttribute("name", "re");
		me2.setRefAttribute("context", me);
		ModelElement me3 = new ModelElement(testType);
		me3.setAttribute("name", "mi");
		me3.setRefAttribute("context", me2);

		assertEquals("mi", me3.getName());
		assertEquals("do.re.mi", me3.getFullName());
	}

	@Test
	public void elementRelations() {
		assertNull(me.getRelations("context"));
		ModelElement me2 = new ModelElement(testType);
		ModelElement me3 = new ModelElement(testType);
		me.addRelation("context", me2);
		me.addRelation("context", me3);
		Collection<ModelElement> set = me.getRelations("context");
		assertEquals(2, set.size());
		assertTrue(set.contains(me2));
		assertTrue(set.contains(me3));
		assertSame(set, me.getOwnedElements());
	}

	@Test
	public void elementComparator() {
		Comparator<ModelElement> comp = ModelElement.getComparator();
		me.setRunningID(1);
		ModelElement me2 = new ModelElement(testType);
		me2.setRunningID(2);
		assertEquals(-1, comp.compare(me, me2));
		assertEquals(1, comp.compare(me2, me));
		me2.setRunningID(1);
		assertEquals(0, comp.compare(me2, me));
	}

	@Test
	public void stringRepresentation() {
		assertEquals("", me.toString());
		me.setAttribute("id", "CRM114");
		assertEquals("CRM114", me.toString());
	}

	@Test
	public void merge() {
		ModelElement other = new ModelElement(testType);
		other.setAttribute("name", "other");
		other.setAttribute("id", "1234");
		Collection<ModelElement> elemSet = new HashSet<ModelElement>();
		other.setSetAttribute("elemSet", elemSet);
		other.setRunningID(100);
		other.setLinksIgnored(true);

		me.setAttribute("name", "this");
		me.setAttribute("id", "4711");
		me.setRunningID(0);
		me.setLinksIgnored(false);

		me.merge(other);
		assertEquals("other", me.getName());
		assertEquals("4711", me.getXMIID());
		assertSame(elemSet, me.getSetAttribute("elemSet"));
		assertEquals(0, ModelElement.getComparator().compare(me, other));
		assertTrue(me.getLinksIgnored());
	}
}
