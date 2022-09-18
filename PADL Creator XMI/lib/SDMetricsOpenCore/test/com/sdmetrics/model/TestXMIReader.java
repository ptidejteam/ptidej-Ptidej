package com.sdmetrics.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.model.XMIReader.ProgressMessageHandler;
import com.sdmetrics.test.Utils;
import com.sdmetrics.util.XMLParser;

public class TestXMIReader {

	String dirBase = Utils.TEST_MODEL_DIR;
	MetaModel mm;
	XMLParser parser;
	Model model;
	XMITransformations trans;
	XMIReader reader;

	@Before
	public void parseTestXMIFile() throws Exception {
		mm = new MetaModel();
		parser = new XMLParser();
		parser.parse(dirBase + "testMetaModel04.xml", mm.getSAXParserHandler());
		trans = new XMITransformations(mm);
		parser.parse(dirBase + "testXMITransformations04.xml",
				trans.getSAXParserHandler());
		model = new Model(mm);
		reader = new XMIReader(trans, model);
		parser.parse(dirBase + "testModel01.xmi", reader);
	}

	@Test
	public void attrvalTrigger() {
		ModelElement cls = getElements("class").get(0);
		assertEquals("public", cls.getPlainAttribute("visibility"));
	}

	private List<ModelElement> getElements(String typeName) {
		return model.getElements(mm.getType(typeName));
	}

	@Test
	public void cattrvalTrigger() throws Exception {
		model = new Model(mm);
		reader = new XMIReader(trans, model);
		parser.parse(dirBase + "testModel03.xmi", reader);

		ModelElement cls = getElements("class").get(0);
		assertEquals("5 miles", cls.getPlainAttribute("visibility"));
	}

	@Test
	public void ctextTrigger() {
		ModelElement taggedValue = getElements("taggedvalue").get(0);
		assertEquals("This is some documentation pertaining to class Person.",
				taggedValue.getPlainAttribute("value"));
	}

	@Test
	public void gcattrvalTrigger() {
		List<ModelElement> generalizations = getElements("generalization");
		ModelElement nitPicker = generalizations.get(0).getRefAttribute(
				"genchild");
		assertEquals("Nitpicker", nitPicker.getName());

		Collection<?> nitPickerStereotypes = nitPicker
				.getSetAttribute("stereotypes");
		assertEquals(2, nitPickerStereotypes.size());

		List<ModelElement> stereoTypes = getElements("stereotype");
		assertTrue(nitPickerStereotypes.contains(stereoTypes.get(1)));
		assertTrue(nitPickerStereotypes.contains(stereoTypes.get(2)));
	}

	@Test
	public void gcattrvalTriggerMissingIDRef() throws Exception {
		model = new Model(mm);
		reader = new XMIReader(trans, model);
		parser.parse(dirBase + "testModel03.xmi", reader);

		ModelElement st = getElements("stereotype").get(0);
		assertEquals("Oliver", st.getName());

		ModelElement cls = st.getOwner();
		assertEquals(1, cls.getOwnedElements().size());
		assertTrue(cls.getOwnedElements().contains(st));
	}

	@Test
	public void gcattrvalTriggerForContextAttr() throws Exception {
		model = new Model(mm);
		reader = new XMIReader(trans, model);
		parser.parse(dirBase + "testModel03.xmi", reader);

		ModelElement st = getElements("stereotype").get(1);
		assertEquals("cantownaclass", st.getName());

		ModelElement cls = st.getOwner();
		assertEquals("testModel.stowned", cls.getFullName());
		assertEquals(1, cls.getOwnedElements().size());
		assertTrue(cls.getOwnedElements().contains(st));
	}

	@Test
	public void ignoreTriggerAndDefaultIDs() {
		// attributes ignore XMI ID and should receive artificial ones
		ModelElement attribute = getElements("attribute").get(0);
		assertEquals("SDMetricsID.0", attribute.getXMIID());
	}

	@Test
	public void constantTriggerAndDefaultContext() {
		ModelElement operation = getElements("operation").get(0);
		assertEquals("Halibut", operation.getName());
		assertEquals(getElements("class").get(0), operation.getOwner());
	}

	@Test
	public void linkbackAttribute() {
		ModelElement geekStereotype = getElements("stereotype").get(1);
		Collection<?> geeks = geekStereotype
				.getSetAttribute("extendedelements");
		assertEquals(2, geeks.size());

		List<ModelElement> classes = getElements("class");
		assertTrue(geeks.contains(classes.get(1)));
		assertTrue(geeks.contains(classes.get(2)));
	}

	@Test
	public void xmiAssocTrigger() throws Exception {
		parser.parse(dirBase + "testMetaModel05.xml", mm.getSAXParserHandler());
		trans = new XMITransformations(mm);
		parser.parse(dirBase + "testXMITransformations05.xml",
				trans.getSAXParserHandler());
		model = new Model(mm);
		reader = new XMIReader(trans, model);
		parser.parse(dirBase + "testModel02.xmi", reader);

		ModelElement interaction = getElements("interaction").get(0);
		assertEquals(2, interaction.getSetAttribute("lifelines").size());

		List<ModelElement> lifeLines = getElements("lifeline");
		ModelElement lifeLine1 = lifeLines.get(0);
		ModelElement lifeLine2 = lifeLines.get(1);
		assertEquals(3, lifeLine1.getSetAttribute("coveredBy").size());
		assertEquals(4, lifeLine2.getSetAttribute("coveredBy").size());

		ModelElement fragment = getElements("occurrencespec").get(1);
		assertSame(lifeLine1, fragment.getRefAttribute("covered"));
		assertNull(fragment.getRefAttribute("event"));

		assertSame(getElements("package").get(0), interaction.getOwner());
	}

	@Test
	public void reflistTrigger() throws Exception {
		trans = new XMITransformations(mm);
		parser.parse(dirBase + "testXMITransformations06.xml",
				trans.getSAXParserHandler());
		model = new Model(mm);
		reader = new XMIReader(trans, model);
		parser.parse(dirBase + "testModel01.xmi", reader);

		List<ModelElement> stapplications = getElements("stapplication");
		assertEquals(4, stapplications.size());

		ModelElement nitPicker = getElements("class").get(1);
		HashSet<String> nitPickerStereotypes = new HashSet<String>();
		for (ModelElement stapp : stapplications) {
			if (nitPicker == stapp.getOwner())
				nitPickerStereotypes.add(stapp.getRefAttribute("stapplication")
						.getName());
		}

		assertEquals(2, nitPickerStereotypes.size());
		assertTrue(nitPickerStereotypes.contains("Nerd"));
		assertTrue(nitPickerStereotypes.contains("GeekScientist"));
	}

	@Test
	public void progressReporting() throws Exception {
		model = new Model(mm);
		reader = new XMIReader(trans, model);

		final ArrayList<String> messages = new ArrayList<String>();
		reader.setProgressMessageHandler(new ProgressMessageHandler() {
			@Override
			public void reportXMIProgress(String msg) {
				messages.add(msg);
			}
		});

		parser.parse(dirBase + "testModel03.xmi", reader);
		int elemCount = reader.getNumberOfElements();
		assertEquals(1018, elemCount);
		assertEquals(
				"[Reading UML model. Elements processed: 1000, Processing 3 model extensions, Crossreferencing "
						+ elemCount + " model elements]", messages.toString());
	}

	@Test
	public void elementOwnership() throws Exception {
		model = new Model(mm);
		reader = new XMIReader(trans, model);
		parser.parse(dirBase + "testModel03.xmi", reader);

		// root element has no owner
		ModelElement root = getElements("model").get(0);
		assertNull(root.getOwner());

		// cls owns 'itself'; XMI reader must ignore this
		ModelElement cls = getElements("class").get(0);
		assertSame(cls.getOwner(), root);
		assertTrue(root.getOwnedElements().contains(cls));

		// cls2 defines its owner explicitly
		ModelElement cls2 = getElements("class").get(1);
		assertSame(cls2.getOwner(), root);
		assertTrue(root.getOwnedElements().contains(cls2));

		// cls3 defines an owner that does not exist
		ModelElement cls3 = getElements("class").get(2);
		assertSame(cls3.getOwner(), root);
		assertTrue(root.getOwnedElements().contains(cls3));
	}

	@Test
	public void profileProcessing() throws Exception {
		model = new Model(mm);
		reader = new XMIReader(trans, model);
		parser.parse(dirBase + "testModel04.xmi", reader);

		// There should be only two classes left
		assertEquals(2, getElements("class").size());
		ModelElement class1 = getElements("class").get(0);
		ModelElement class3 = getElements("class").get(1);
		assertEquals("TestClass1", class1.getName());
		assertEquals("TestClass3", class3.getName());

		// Check attributes of the light extension
		assertEquals(1, getElements("lightextension").size());
		ModelElement light = getElements("lightextension").get(0);
		assertEquals("TestClass1", light.getName());
		assertEquals("ex1", light.getXMIID());
		assertEquals("extension1", light.getPlainAttribute("extval"));
		assertSame(class1, light.getRefAttribute("baseclass"));

		// Check attributes of the full extensions
		assertEquals(2, getElements("fullextension").size());
		ModelElement heavy = getElements("fullextension").get(0);
		assertEquals("TestClass2", heavy.getName());
		assertEquals("ex2", heavy.getXMIID());
		assertEquals("extension2", heavy.getPlainAttribute("extval"));
		assertEquals("public", heavy.getPlainAttribute("visibility"));
		assertSame(heavy, heavy.getRefAttribute("baseclass"));

		// Check attributes of the unsatisfied full extension
		ModelElement orphan = getElements("fullextension").get(1);
		assertEquals("", orphan.getName());
		assertEquals("ex3", orphan.getXMIID());
		assertNull(orphan.getRefAttribute("baseclass"));
		assertEquals("id1", orphan.getOwner().getXMIID());
		assertEquals("orphan", orphan.getPlainAttribute("extval"));
		assertEquals("", orphan.getPlainAttribute("visibility"));

		// Check extension with inheritance
		ModelElement derived = getElements("derivedfullextension").get(0);
		assertEquals("TestClass4", derived.getName());
		assertEquals("ex4", derived.getXMIID());
		assertEquals("derived1", derived.getPlainAttribute("extval"));
		assertEquals("derived2", derived.getPlainAttribute("derivedextval"));

		// Testpackage owns the classes and the extensions
		ModelElement testPck = getElements("package").get(0);
		Collection<ModelElement> ownedElements = testPck.getOwnedElements();
		assertEquals(5, ownedElements.size());
		assertTrue(ownedElements.contains(light));
		assertTrue(ownedElements.contains(heavy));
		assertTrue(ownedElements.contains(derived));
		assertTrue(ownedElements.contains(class1));
		assertTrue(ownedElements.contains(class3));
		assertSame(testPck, class1.getOwner());
		assertSame(testPck, class3.getOwner());
		assertSame(testPck, light.getOwner());
		assertSame(testPck, heavy.getOwner());
		assertSame(testPck, derived.getOwner());

		// Keep references to light extensions, rewire references to
		// full extensions
		ModelElement generalization = getElements("generalization").get(0);
		assertSame(class1, generalization.getRefAttribute("genchild"));
		assertSame(heavy, generalization.getRefAttribute("genparent"));
	}
}
