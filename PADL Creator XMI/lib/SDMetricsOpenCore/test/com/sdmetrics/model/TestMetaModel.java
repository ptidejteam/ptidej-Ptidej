package com.sdmetrics.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.test.Utils;
import com.sdmetrics.util.XMLParser;

public class TestMetaModel {

	String dirBase = Utils.TEST_MODEL_DIR;
	MetaModel mm;
	XMLParser parser;

	@Before
	public void initParser() throws Exception {
		mm = new MetaModel();
		parser = new XMLParser();
	}

	@Test
	public void iterateMetaModelElements() throws Exception {
		parser.parse(dirBase + "testMetaModel01.xml", mm.getSAXParserHandler());
		assertEquals(4, mm.getNumberOfTypes());
		Iterator<MetaModelElement> it = mm.iterator();
		MetaModelElement base = mm.getType(MetaModel.BASE_ELEMENT);
		assertSame(base, it.next());
		MetaModelElement cls1 = mm.getType("class1");
		assertSame(cls1, it.next());
		MetaModelElement cls2 = mm.getType("class2");
		assertSame(cls2, it.next());
		MetaModelElement ext = mm.getType("extension");
		assertSame(ext, it.next());
		assertFalse(it.hasNext());
	}

	@Test
	public void attributeTypes() throws Exception {
		parser.parse(dirBase + "testMetaModel01.xml", mm.getSAXParserHandler());
		MetaModelElement base = mm.getType(MetaModel.BASE_ELEMENT);
		MetaModelElement cls1 = mm.getType("class1");
		assertFalse(base.isRefAttribute("id"));
		assertFalse(base.isSetAttribute("id"));
		assertFalse(base.isRefAttribute("name"));
		assertFalse(base.isSetAttribute("name"));
		assertTrue(base.isRefAttribute("context"));
		assertTrue(cls1.isSetAttribute("stereotypes"));
		assertEquals("Unique identifier of the model element.",
				base.getAttributeDescription("id"));
	}

	@Test
	public void attributeInheritance() throws Exception {
		parser.parse(dirBase + "testMetaModel01.xml", mm.getSAXParserHandler());
		MetaModelElement base = mm.getType(MetaModel.BASE_ELEMENT);
		MetaModelElement cls1 = mm.getType("class1");
		MetaModelElement cls2 = mm.getType("class2");
		assertSame(base, cls1.getParent());
		assertSame(cls1, cls2.getParent());
		assertEquals("[context, id, name, stereotypes, visibility]", cls2
				.getAttributeNames().toString());
	}

	@Test
	public void extensionAttribute() throws Exception {
		parser.parse(dirBase + "testMetaModel01.xml", mm.getSAXParserHandler());
		assertNull(mm.getType("class1").getExtensionReference());
		assertEquals("baseref", mm.getType("extension").getExtensionReference());
	}

	@Test
	public void invalidVersion() {
		assertParsesNot("testMetaModelInvalidVersion.xml",
				"Error in line 2: File version \"0.9alpha\" not supported.");
	}

	@Test
	public void missingTypeName() {
		assertParsesNot("testMetaModelMissingTypename.xml",
				"Error in line 11: Model element is missing \"name\" attribute.");
	}

	@Test
	public void missingSDMetricsBase() {
		assertParsesNot(
				"testMetaModelMissingSDMetricsBase.xml",
				"Error in line 5: The first metamodel element to be defined must be named \"sdmetricsbase\".");
	}

	@Test
	public void unkownParent() {
		assertParsesNot(
				"testMetaModelUnknownParent.xml",
				"Error in line 10: Unknown parent type \"uberclass\" for model element \"class\".");
	}

	@Test
	public void unnamedAttribute() {
		assertParsesNot(
				"testMetaModelMissingAttributeName.xml",
				"Error in line 5: Attribute without a name for model element \"sdmetricsbase\".");
	}

	@Test
	public void unkownElement() {
		assertParsesNot("testMetaModelIllegalTag.xml",
				"Error in line 5: Unexpected XML element <parentType>.");
	}

	@Test
	public void outsideAttributeDefinition() {
		assertParsesNot("testMetaModelLooseAttribute.xml",
				"Error in line 8: Attribute definition outside model element definition.");
	}

	@Test
	public void duplicateExtensionReference() {
		assertParsesNot("testMetaModelDuplicateExtension.xml",
				"Error in line 8: Duplicate extension reference attribute 'ref2'.");
	}

	@Test
	public void multiValuedExtensionReference() {
		assertParsesNot(
				"testMetaModelMultiValuedExtension.xml",
				"Error in line 7: Extension reference attribute 'mvextref' cannot be multi-valued.");
	}

	private void assertParsesNot(String file, String expectedMessage) {
		try {
			parser.parse(dirBase + file, mm.getSAXParserHandler());
			fail("File " + file + " should not parse without error.");
		} catch (Exception ex) {
			assertEquals(expectedMessage, ex.getMessage());
		}
	}
}
