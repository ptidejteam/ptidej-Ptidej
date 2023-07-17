package com.sdmetrics.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.sdmetrics.test.SAXAttributes;
import com.sdmetrics.test.Utils;
import com.sdmetrics.util.XMLParser;

public class TestXMITransformations {

	String dirBase = Utils.TEST_MODEL_DIR;
	MetaModel mm;
	XMITransformations trans;
	XMLParser parser;
	SAXAttributes attr;

	@Before
	public void parseTestMetaModel() throws Exception {
		mm = new MetaModel();
		parser = new XMLParser();
		parser.parse(dirBase + "testMetaModel02.xml", mm.getSAXParserHandler());
		trans = new XMITransformations(mm);
		attr = new SAXAttributes();
	}

	@Test
	public void parsing() throws SAXException {
		parseFile("testXMITransformations01.xml");

		// XMI transformations correctly parsed
		XMITransformation base = trans.getTransformation("base", attr);
		assertEquals(mm.getType(MetaModel.BASE_ELEMENT), base.getType());
		assertEquals("base", base.getXMIPattern());
		assertTrue(base.getXMIRecurse());
		assertNull(base.getConditionExpression());

		// trigger attributes correctly parsed
		List<XMITrigger> triggers = base.getTriggerList();
		assertEquals(2, triggers.size());
		XMITrigger stereotypes = triggers.get(1);
		assertEquals("stereotypes", stereotypes.name);
		assertEquals(XMITrigger.TriggerType.GCATTRVAL, stereotypes.type);
		assertEquals("UML:ModelElement.stereotype", stereotypes.src);
		assertEquals("xmi.idref", stereotypes.attr);
		assertEquals("extendedelements", stereotypes.linkback);
	}

	@Test
	public void triggerInheritance() throws SAXException {
		parseFile("testXMITransformations01.xml");
		XMITransformation component = trans.getTransformation("UML:Component",
				attr);
		XMITransformation base = trans.getTransformation("base", attr);
		// component contains all triggers defined by the base element
		assertTrue(component.getTriggerList()
				.containsAll(base.getTriggerList()));
	}

	@Test
	public void triggerOverriding() throws SAXException {
		parseFile("testXMITransformations01.xml");
		XMITransformation component = trans.getTransformation("UML:Component",
				attr);
		assertEquals("component", component.getType().getName());
		// component overrides and thus does not inherit the visibility
		// triggers from "class"
		int triggersForVisibility = 0;
		for (XMITrigger trigger : component.getTriggerList()) {
			if ("visibility".equals(trigger.name)) {
				triggersForVisibility++;
				assertEquals(XMITrigger.TriggerType.CONSTANT, trigger.type);
			}
		}
		assertEquals(1, triggersForVisibility);
	}

	@Test
	public void conditionalTransformations() throws SAXException {
		parseFile("testXMITransformations02.xml");

		// Set attributes to match the condition for the "class" XMI
		// transformation
		attr.addAttribute("arg1", "hi");
		attr.addAttribute("arg2", "2");
		XMITransformation tf = trans.getTransformation("UML:Class", attr);
		assertEquals("class", tf.getType().getName());

		// Set attributes to match the condition for the "operation" XMI
		// transformation
		attr = new SAXAttributes();
		attr.addAttribute("arg1", "ho");
		attr.addAttribute("arg3", "4");
		tf = trans.getTransformation("UML:Class", attr);
		assertEquals("operation", tf.getType().getName());

		// Set attributes to match the condition for the "task" XMI
		// transformation
		attr = new SAXAttributes();
		attr.addAttribute("arg1", "hum");
		attr.addAttribute("arg2", "hee");
		tf = trans.getTransformation("UML:Class", attr);
		assertEquals("task", tf.getType().getName());

		// Set attributes to match none of the conditional XMI transformations,
		// which yields the unconditional transformation for "component"
		attr = new SAXAttributes();
		attr.addAttribute("arg1", "ho");
		attr.addAttribute("arg2", "hee");
		tf = trans.getTransformation("UML:Class", attr);
		assertEquals("component", tf.getType().getName());
	}

	@Test
	public void requireXMIIDDefault() throws SAXException {
		parseFile("testXMITransformations01.xml");

		assertTrue(trans.getTransformation("base", attr).requiresXMIID());
		assertTrue(trans.getTransformation("UML:Class", attr).requiresXMIID());
		assertFalse(trans.getTransformation("UML:Component", attr)
				.requiresXMIID());
	}

	@Test
	public void requireXMIIDDefaultFalse() throws SAXException {
		parseFile("testXMITransformations03.xml");

		assertFalse(trans.getTransformation("base", attr).requiresXMIID());
		assertTrue(trans.getTransformation("UML:Class", attr).requiresXMIID());
		assertFalse(trans.getTransformation("UML:Component", attr)
				.requiresXMIID());
	}

	@Test
	public void unknownPattern() throws SAXException {
		parseFile("testXMITransformations02.xml");
		assertNull(trans.getTransformation("UML:Diagrammm", attr));
	}

	@Test
	public void unavailablePattern() throws SAXException {
		parseFile("testXMITransformations02.xml");
		assertNull(trans.getTransformation("base", attr));
	}

	@Test
	public void invalidVersion() {
		parseFile("testXMITransInvalidVersion.xml",
				"Error in line 2: File version \"2002\" not supported.");
	}

	@Test
	public void unknownXMLElement() {
		parseFile("testXMITransIllegalTag.xml",
				"Error in line 4: Unexpected XML element <desc>.");
	}

	@Test
	public void missingModelElementType() {
		parseFile(
				"testXMITransMissingType.xml",
				"Error in line 4: XMI transformation is missing the \"modelelement\" attribute.");
	}

	@Test
	public void unknownModelElementType() {
		parseFile("testXMITransUnknownType.xml",
				"Error in line 4: Unknown metamodel element type \"process\".");
	}

	@Test
	public void conditionWithSyntaxError() {
		parseFile(
				"testXMITransConditionSyntaxError.xml",
				"Error in line 5: Invalid condition expression \"a=)\": Parse error at position 3: Unexpected token: )");
	}

	@Test
	public void triggerWithoutName() {
		parseFile("testXMITransMissingTriggerName.xml",
				"Error in line 5: Trigger is missing the \"name\" attribute.");
	}

	@Test
	public void triggerWithoutType() {
		parseFile("testXMITransMissingTriggerType.xml",
				"Error in line 5: Trigger is missing the \"type\" attribute.");
	}

	@Test
	public void reflistInvalidAttribute() {
		parseFile(
				"testXMITransUnknownReflistType.xml",
				"Error in line 5: Unknown metamodel element type \"state\" for reflist attribute \"attr\".");
	}

	@Test
	public void triggerInvalidAttribute() {
		parseFile(
				"testXMITransUnkownAttribute.xml",
				"Error in line 5: Attribute \"attr\": Unknown metamodel attribute \"ownedOperations\" for elements of type \"sdmetricsbase\".");
	}

	@Test
	public void invalidTrigger() {
		parseFile(
				"testXMITransMissingTriggerAttribute.xml",
				"Error in line 5: Attribute 'attr' must be specified for triggers of type 'attrval'.");
	}

	@Test
	public void triggerOutsideTransformation() {
		parseFile(
				"testXMITransLooseTrigger.xml",
				"Error in line 7: Trigger definition outside of an XMI transformation definition.");
	}

	@Test
	public void conditionWithSemanticError() {
		parseFile("testXMITransConditionSemanticError.xml");
		try {
			trans.getTransformation("base", attr);
			fail("Exception expected");
		} catch (SAXException ex) {
			assertEquals(
					"Error evaluating condition for XMI transformation \"base\" in line 4 of the XMI transformation file: Illegal boolean operation: +",
					ex.getMessage());
		}
	}

	@Test
	public void nestedTransformations() {
		parseFile("testXMITransNesting.xml",
				"Error in line 6: XMI transformations must not be nested.");
	}

	private void parseFile(String file) {
		parseFile(file, null);
	}

	private void parseFile(String file, String expectedMessage) {
		try {
			parser.parse(dirBase + file, trans.getSAXParserHandler());
			if (expectedMessage != null) {
				fail("File " + file + " should not parse without errors.");
			}
		} catch (Exception ex) {
			assertEquals(expectedMessage, parser.getErrorMessage());
		}
	}
}
