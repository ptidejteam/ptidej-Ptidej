package com.sdmetrics.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.test.Utils;
import com.sdmetrics.util.XMLParser;

public class TestModel {

	// Test-Metamodel and element types
	MetaModel mm;
	MetaModelElement classType;
	MetaModelElement opType;
	MetaModelElement packageType;

	// Test-Model and elements
	Model model;
	ModelElement clsSystem;
	ModelElement clsXMLReader;
	ModelElement clsHashSet;
	ModelElement pckJavaLang;

	@Before
	public void createTestModel() throws Exception {
		// read metamodel
		mm = new MetaModel();
		new XMLParser().parse(Utils.TEST_MODEL_DIR + "testMetaModel03.xml",
				mm.getSAXParserHandler());
		classType = mm.getType("class");
		opType = mm.getType("operation");
		packageType = mm.getType("package");

		// create test model
		model = new Model(mm);
		ModelElement pckModel = addTestElement(packageType, "model", null);
		ModelElement pckJava = addTestElement(packageType, "java", pckModel);
		pckJavaLang = addTestElement(packageType, "lang", pckJava);
		ModelElement pckJavaUtil = addTestElement(packageType, "util", pckJava);
		ModelElement pckOrg = addTestElement(packageType, "org", pckModel);
		ModelElement pckOrgXml = addTestElement(packageType, "xml", pckOrg);
		ModelElement pckOrgXmlSax = addTestElement(packageType, "sax",
				pckOrgXml);

		clsSystem = addTestElement(classType, "System", pckJavaLang);
		clsXMLReader = addTestElement(classType, "XMLReader", pckOrgXmlSax);
		clsHashSet = addTestElement(classType, "HashSet", pckJavaUtil);
		addTestElement(opType, "add", clsHashSet);
		addTestElement(opType, "remove", clsHashSet);
		addTestElement(opType, "println", clsSystem);
	}

	private ModelElement addTestElement(MetaModelElement type, String name,
			ModelElement owner) {
		ModelElement me = new ModelElement(type);
		me.setAttribute(MetaModelElement.NAME, name);
		me.setRefAttribute(MetaModelElement.CONTEXT, owner);
		model.addElement(me);
		return me;
	}

	@Test
	public void elementAccess() {
		assertSame(mm, model.getMetaModel());

		List<ModelElement> classes = model.getElements(classType);
		assertEquals(3, classes.size());
		assertTrue(classes.contains(clsSystem));
		assertTrue(classes.contains(clsXMLReader));
		assertTrue(classes.contains(clsHashSet));
	}

	@SuppressWarnings("unused")
	@Test
	public void elementIteration() {
		int elementCount = 0;
		for (ModelElement me : model) {
			elementCount++;
		}
		assertEquals(13, elementCount);
	}

	@Test
	public void acceptMatchingFilter() {
		String[] filters = { "#.java.util.#", "#.java.lang" };
		model.setFilter(filters, true, true);
		List<ModelElement> acceptedClasses = model
				.getAcceptedElements(classType);
		assertEquals(2, acceptedClasses.size());
		assertTrue(acceptedClasses.contains(clsSystem));
		assertTrue(acceptedClasses.contains(clsHashSet));

		assertEquals(3, model.getAcceptedElements(opType).size());

		assertEquals(1, model.getAcceptedElements(packageType).size());
		assertTrue(model.getAcceptedElements(packageType).contains(pckJavaLang));
	}

	@Test
	public void acceptNonMatchingFilter() {
		String[] filters = { "#.java.util", "#.java.lang" };
		model.setFilter(filters, false, true);
		List<ModelElement> acceptedClasses = model
				.getAcceptedElements(classType);
		assertEquals(1, acceptedClasses.size());
		assertTrue(acceptedClasses.contains(clsXMLReader));

		assertTrue(model.getAcceptedElements(opType).isEmpty());
	}

	@Test
	public void filterKeepingElementLinks() {
		String[] filters = { "#.java.util", "#.java.lang" };
		model.setFilter(filters, false, false);
		assertFalse(clsSystem.getLinksIgnored());
		assertFalse(clsXMLReader.getLinksIgnored());

		model.setFilter(filters, false, true);
		assertTrue(clsSystem.getLinksIgnored());
		assertFalse(clsXMLReader.getLinksIgnored());

		model.setFilter(filters, true, true);
		assertFalse(clsSystem.getLinksIgnored());
		assertTrue(clsXMLReader.getLinksIgnored());

		model.setFilter(filters, true, false);
		assertFalse(clsSystem.getLinksIgnored());
		assertFalse(clsXMLReader.getLinksIgnored());
	}

	@Test
	public void longFilter() {
		String[] filters = { "#.javax.swing.text.html.parser.#" };
		model.setFilter(filters, true, true);

		assertTrue(model.getAcceptedElements(classType).isEmpty());
		assertTrue(model.getAcceptedElements(opType).isEmpty());
	}

	@Test
	public void inactiveFilter() {
		// Apply some filters
		String[] filters = { "#.java.util.HashSet" };
		model.setFilter(filters, true, true);

		// Clear filter with "null"
		model.setFilter(null, true, true);
		assertSame(model.getElements(classType),
				model.getAcceptedElements(classType));

		// Clear filter with empty list of filter strings
		model.setFilter(new String[0], true, true);
		assertSame(model.getElements(classType),
				model.getAcceptedElements(classType));
	}

	@SuppressWarnings("unused")
	@Test
	public void remove() {
		List<ModelElement> classes = model.getElements(classType);
		int sizeBefore = model.getElements(classType).size();
		assertTrue(model.getElements(classType).contains(clsXMLReader));

		model.removeElement(clsXMLReader);

		assertEquals(sizeBefore - 1, model.getElements(classType).size());
		assertFalse(model.getElements(classType).contains(clsXMLReader));
	}
}
