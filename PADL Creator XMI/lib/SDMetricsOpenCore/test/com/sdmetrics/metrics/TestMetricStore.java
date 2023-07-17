package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.model.MetaModel;
import com.sdmetrics.model.MetaModelElement;
import com.sdmetrics.test.BooleanOperationXOR;
import com.sdmetrics.test.MetricProcedurePairwise;
import com.sdmetrics.test.RuleProcedureVerbObject;
import com.sdmetrics.test.ScalarOperationMax;
import com.sdmetrics.test.SetOperationSymmDiff;
import com.sdmetrics.test.SetProcedureCondition;
import com.sdmetrics.test.Utils;
import com.sdmetrics.util.XMLParser;

public class TestMetricStore {

	String dirBase = Utils.TEST_METRICS_DIR;
	MetaModel mm;
	MetricStore ms;
	XMLParser parser;
	MetaModelElement classType;

	@Before
	public void initParser() throws Exception {
		mm = new MetaModel();
		parser = new XMLParser();
		parser.parse(Utils.TEST_MODEL_DIR + "testMetaModel04.xml",
				mm.getSAXParserHandler());
		classType = mm.getType("class");
		ms = new MetricStore(mm);
		parser.parse(dirBase + "testMetrics.xml", ms.getSAXParserHandler());
		assertNull(parser.getErrorMessage(), parser.getErrorMessage());
	}

	@Test
	public void metricStoreProperties() throws Exception {
		assertSame(mm, ms.getMetaModel());
		assertEquals(mm.getType("taggedvalue"), ms.getRuleExemptionType());
		assertEquals("tagname", ms.getRuleExemptionTag());

		// Test empty rule exemption type and tag
		ms = new MetricStore(mm);
		parser.parse(dirBase + "testMetrics02.xml", ms.getSAXParserHandler());
		assertNull(ms.getRuleExemptionType());
		assertNull(ms.getRuleExemptionTag());
	}

	@Test
	public void metricsParsing() throws SDMetricsException {
		Collection<Metric> classMetrics = ms.getMetrics(classType);
		assertEquals(2, classMetrics.size());

		Iterator<Metric> it = classMetrics.iterator();
		Metric numAttr = it.next();
		assertEquals("NumAttr", numAttr.getName());
		assertSame(classType, numAttr.getType());
		assertEquals("Size", numAttr.getCategory());
		assertEquals("projection", numAttr.getProcedureName());
		assertFalse(numAttr.isInternal());
		assertFalse(numAttr.isInheritable());
		assertEquals(10, numAttr.getLocation());

		assertEquals("The number of attributes in the class.",
				numAttr.getBriefDescription());
		assertEquals("The number of attributes in the class.<p>"
				+ " Counts all properties regardless of their type.<p>"
				+ " <ul><li>Also known as: NV (Number of Variables per class)"
				+ " [<a href=\"ref://LK94/\">LK94</a>].</ul> ",
				numAttr.getFullDescription());

		ProcedureAttributes attrs = numAttr.getAttributes();
		assertEquals("ownedattributes", attrs.getStringValue("relset"));
		assertEquals("property", attrs.getStringValue("target"));
		assertEquals("=", attrs.getExpression("condition").getValue());

		Metric numOps = it.next();
		assertEquals("NumOps", numOps.getName());
		assertTrue(numOps.isInternal());
	}

	@Test
	public void setParsing() throws SDMetricsException {
		Collection<Set> classSets = ms.getSets(classType);
		assertEquals(2, classSets.size());

		Set realizedElement = null;
		Set interfaces = null;

		for (Set set : classSets)
			if ("RealizedElements".equals(set.getName())) {
				realizedElement = set;
				assertSame(classType, realizedElement.getType());
				assertTrue(realizedElement.isMultiSet());
				assertEquals(23, realizedElement.getLocation());

				assertEquals("The set of elements the class realizes.",
						realizedElement.getBriefDescription());
				assertEquals(set.getBriefDescription(),
						realizedElement.getFullDescription());
			} else if ("ImplInterfaces".equals(set.getName())) {
				interfaces = set;
				assertFalse(interfaces.isMultiSet());
			} else
				fail("Unexpected set: " + set.getName());

		assertNotNull(realizedElement);
		assertNotNull(interfaces);
	}

	@Test
	public void inheritance() {
		MetaModelElement packageType = mm.getType("package");
		MetaModelElement modelType = mm.getType("model");
		MetaModelElement supermodelType = mm.getType("supermodel");

		// Check inheritance of metric "Elements"
		Metric pckgMetric = ms.getMetric(packageType, "Elements");
		Metric modelMetric = ms.getMetric(modelType, "Elements");

		assertEquals(pckgMetric.getFullDescription(),
				modelMetric.getFullDescription());
		assertEquals(pckgMetric.getProcedureName(),
				modelMetric.getProcedureName());
		assertSame(pckgMetric.getAttributes(), modelMetric.getAttributes());
		assertTrue(modelMetric.isInheritable());
		assertEquals(pckgMetric.getLocation(), modelMetric.getLocation());
		assertSame(modelType, modelMetric.getType());
		assertFalse(modelMetric.isInternal());
		assertEquals(pckgMetric.getCategory(), modelMetric.getCategory());
		assertNotNull(ms.getMetric(supermodelType, "Elements"));

		// Metric "ClassCount" not inherited
		assertNotNull(ms.getMetric(packageType, "ClassCount"));
		assertNull(ms.getMetric(modelType, "ClassCount"));
		assertNull(ms.getMetric(supermodelType, "ClassCount"));

		// Check inheritance of set "ClassSet"
		Set modelSet = ms.getSet(modelType, "ClassSet");
		assertTrue(modelSet.isMultiSet());
		assertSame(modelType, modelSet.getType());
		assertNotNull(ms.getSet(modelType, "ClassSet"));

		// Check inheritance of rule "EmptyPackage"
		Rule pckgRule = ms.getRule(packageType, "EmptyPackage");
		Rule modelRule = ms.getRule(modelType, "EmptyPackage");
		assertSame(modelType, modelRule.getType());
		assertEquals(pckgRule.getCategory(), modelRule.getCategory());
		assertEquals(pckgRule.getCriticality(), modelRule.getCriticality());
		assertTrue(modelRule.isEnabled());
		assertSame(pckgRule.getApplicableAreas(),
				modelRule.getApplicableAreas());
		assertNotNull(ms.getRule(modelType, "EmptyPackage"));

		// Check "overriding" of rule "SmallPackage"
		pckgRule = ms.getRule(packageType, "SmallPackage");
		assertEquals("The package has less than 10 elements.",
				pckgRule.getBriefDescription());
		assertEquals("1-low", pckgRule.getCriticality());
		modelRule = ms.getRule(modelType, "SmallPackage");
		assertEquals("The model has less than 5 elements.",
				modelRule.getBriefDescription());
		assertEquals("", modelRule.getCriticality());
		assertFalse(modelRule.isInheritable());
		assertNull(ms.getRule(supermodelType, "SmallPackage"));
	}

	@Test
	public void missingMetricName() {
		assertParsesNot("testMetricsMissingMetricName.xml",
				"Error in line 4: No name specified for metric.");
	}

	@Test
	public void unknownMetricDomain() {
		assertParsesNot("testMetricsUnknownDomain.xml",
				"Error in line 4: Unknown domain 'economics' for metric 'GNP'.");
	}

	@Test
	public void missingMetricDomain() {
		assertParsesNot("testMetricsMissingDomain.xml",
				"Error in line 4: No domain specified for metric 'NumOps'.");
	}

	@Test
	public void duplicateMetric() {
		assertParsesNot(
				"testMetricsDuplicateMetricName.xml",
				"Error in line 9: Duplicate definition of metric 'NumOps' for elements of type 'class'.");
	}

	@Test
	public void duplicateSet() {
		assertParsesNot(
				"testMetricsDuplicateSetName.xml",
				"Error in line 9: Duplicate definition of set 'Operations' for elements of type 'class'.");
	}

	@Test
	public void matrixParsing() throws SDMetricsException {
		Collection<Matrix> matrices = ms.getMatrices();
		assertEquals(2, matrices.size());

		Iterator<Matrix> it = matrices.iterator();
		Matrix classImpl = it.next();
		assertEquals("ClassImpl", classImpl.getName());
		assertSame(classType, classImpl.getRowType());
		assertSame(mm.getType("interface"), classImpl.getColumnType());
		assertEquals(33, classImpl.getLocation());
		assertEquals("projection", classImpl.getProcedureName());

		assertEquals("Class implements interface.",
				classImpl.getFullDescription());

		Matrix matrix2 = it.next();
		assertEquals("Class2Class", matrix2.getName());
		assertEquals(1, matrix2.getID());
		assertEquals(">", matrix2.getRowCondition().getValue());
		assertEquals("<", matrix2.getColumnCondition().getValue());
	}

	@Test
	public void faultyMatrixRowCondition() {
		assertParsesNot(
				"testMetricsIllegalRowCondition.xml",
				"Error in line 5: Error parsing row_condition='NumOps>' for matrix 'ClassImpl':\n"
						+ "Parse error at position 7: Unexpected end of expression.");
	}

	@Test
	public void rulesParsing() throws SDMetricsException {
		Collection<Rule> classRules = ms.getRules(classType);
		assertEquals(3, classRules.size());

		Iterator<Rule> it = classRules.iterator();
		Rule unnamed = it.next();
		assertEquals("Unnamed", unnamed.getName());
		assertSame(classType, unnamed.getType());
		assertEquals("Completeness", unnamed.getCategory());
		assertEquals("1-high", unnamed.getCriticality());
		assertNull(unnamed.getApplicableAreas());
		assertEquals(46, unnamed.getLocation());
		assertTrue(unnamed.isEnabled());
		assertEquals("violation", unnamed.getProcedureName());

		assertEquals("Class has no name.", unnamed.getBriefDescription());
		assertEquals(
				"Class has no name.<p>"
						+ " Give the class a descriptive name that reflects its purpose.",
				unnamed.getFullDescription());

		ProcedureAttributes attrs = unnamed.getAttributes();
		assertEquals("name", attrs.getExpression("condition").getLeftNode()
				.getValue());

		Rule unused = it.next();
		assertEquals("Unused", unused.getName());
		assertEquals(1, unused.getID());
		assertEquals("", unused.getCategory());
		assertEquals("", unused.getCriticality());
		assertFalse(unused.isEnabled());

		Rule keyword = it.next();
		assertEquals("Keyword", keyword.getName());
		Iterator<String> areas = keyword.getApplicableAreas().iterator();
		assertEquals("design", areas.next());
		assertEquals("analysis", areas.next());
		assertEquals("me", areas.next());
		assertEquals("you", areas.next());
		assertEquals("everyone", areas.next());
		assertFalse(it.hasNext());
	}

	@Test
	public void duplicateRule() {
		assertParsesNot(
				"testMetricsDuplicateRuleName.xml",
				"Error in line 9: Duplicate definition of rule 'Unnamed' for elements of type 'class'.");
	}

	@Test
	public void missingMetricModel() {
		assertParsesNot("testMetricsMissingProcedure.xml",
				"Error in line 6: No calculation procedure specified for rule 'Undefined'.");
	}

	@Test
	public void referenceParsing() {
		Collection<Reference> refs = ms.getLiteratureReferences();
		assertEquals(2, refs.size());
		Iterator<Reference> it = refs.iterator();

		Reference bmw02 = it.next();
		assertEquals("BMW02", bmw02.getName());
		assertEquals(
				"L. Briand, W. Melo, J. Wuest, \"Assessing the Applicability of Fault-Proneness Models Across Object-Oriented Software Projects\", IEEE Transactions on Software Engineering, 28 (7), 706-720, 2002.  ",
				bmw02.getBriefDescription());
		assertEquals(
				bmw02.getBriefDescription()
						+ "<br> Also available from http://www.sdmetrics.com/Refs.html ",
				bmw02.getFullDescription());

		assertEquals("BW02", it.next().getName());
	}

	@Test
	public void duplicateReference() {
		assertParsesNot("testMetricsDuplicateReference.xml",
				"Error in line 7: Duplicate definition of reference 'Brooks75'.");
	}

	@Test
	public void unnamedReference() {
		assertParsesNot("testMetricsMissingReferenceTag.xml",
				"Error in line 3: Reference is missing its tag.");
	}

	@Test
	public void glossaryParsing() {
		Collection<Glossary> entries = ms.getGlossary();
		assertEquals(2, entries.size());
		Iterator<Glossary> it = entries.iterator();

		Glossary term1 = it.next();
		assertEquals("Completeness", term1.getName());
		assertEquals("Design rules of the \"Completeness\" category raise "
				+ "issues that hint at incomplete design.",
				term1.getBriefDescription());
		assertEquals(
				term1.getBriefDescription()
						+ "<p> See also <a href=\"glossary://Size/size/\">size</a> and [<a href=\"ref://BW02/\">BW02</a>]. ",
				term1.getFullDescription());

		assertEquals("Complexity", it.next().getName());
	}

	@Test
	public void duplicateGlossaryTerm() {
		assertParsesNot("testMetricsDuplicateGlossaryTerm.xml",
				"Error in line 6: Duplicate definition of glossary term 'Anterograde amnesia'.");
	}

	@Test
	public void wordListParsing() throws SDMetricsException {
		assertTrue(ms.isWordOnList("Ringo", "Beatles"));
		assertFalse(ms.isWordOnList("RINGO", "Beatles"));

		assertTrue(ms.isWordOnList("Keith", "Stones"));
		assertTrue(ms.isWordOnList("KEITH", "Stones"));
		assertFalse(ms.isWordOnList("Bruce", "Stones"));

		try {
			assertTrue(ms.isWordOnList("Roger", "The Who"));
			fail("Exception expected");
		} catch (SDMetricsException ex) {
			assertEquals("The Who: No such word list.", ex.getMessage());
		}
	}

	@Test
	public void duplicateWordlist() {
		assertParsesNot("testMetricsDuplicateWordList.xml",
				"Error in line 9: Duplicate definition of word list 'acronyms'.");
	}

	@Test
	public void wordEntryOutsideWordlist() {
		assertParsesNot("testMetricsEntryOutsideWordList.xml",
				"Error in line 4: Wordlist entry outside of a word list.");
	}

	@Test
	public void missingWordEntry() {
		assertParsesNot("testMetricsMissingWord.xml",
				"Error in line 6: Wordlist entry missing 'word' attribute.");
	}

	@Test
	public void descriptionContainingXML() {
		assertParsesNot("testMetricsTagsInDescription.xml",
				"Error in line 6: Unexpected XML element 'projection' in description.");
	}

	@Test
	public void unknownXMLElement() {
		assertParsesNot("testMetricsIllegalProcedure.xml",
				"Error in line 9: Unexpected XML element 'sett' at this point.");
	}

	@Test
	public void procedureDefinitionParsing() throws SDMetricsException {
		assertEquals(ms.getMetricProcedures().getProcedure("customtestmetric")
				.getClass(), MetricProcedurePairwise.class);

		assertEquals(ms.getSetProcedures().getProcedure("customtestset")
				.getClass(), SetProcedureCondition.class);

		assertEquals(ms.getRuleProcedures().getProcedure("customtestrule")
				.getClass(), RuleProcedureVerbObject.class);
	}

	@Test
	public void duplicateCalculationProcedure() {
		assertParsesNot(
				"testMetricsDuplicateProcedure.xml",
				"Error in line 7: Unexpected XML element 'compoundmetric' - metric 'NumOps' already has a calculation procedure.");
	}

	@Test
	public void missingProcedureClass() {
		assertParsesNot("testMetricsMissingProcedureClass.xml",
				"Error in line 4: No class defined for ruleprocedure definition 'classless'.");
	}

	@Test
	public void missingProcedureName() {
		assertParsesNot("testMetricsMissingProcedureName.xml",
				"Error in line 4: The metricprocedure definition requires a 'name' attribute.");
	}

	@Test
	public void duplicateProcedureDefinitions() {
		assertParsesNot("testMetricsDuplicateCustomProcedure.xml",
				"Error in line 5: The setprocedure 'myclone' is already defined.");
	}

	@Test
	public void badProcedureClass() {
		assertParsesNot(
				"testMetricsProcedureClassNotFound.xml",
				"Error in line 4: Could not load class 'sdmetrics.metrics.MetricProcedureSquaringTheCircle'");
	}

	@Test
	public void operationDefinitionParsing() throws SDMetricsException {
		assertEquals(ms.getExpressionOperations().getScalarOperations()
				.getProcedure("maximum").getClass(), ScalarOperationMax.class);
		assertEquals(ms.getExpressionOperations().getSetOperations()
				.getProcedure("symmdiff").getClass(),
				SetOperationSymmDiff.class);
		assertEquals(ms.getExpressionOperations().getBooleanOperations()
				.getProcedure("xor").getClass(), BooleanOperationXOR.class);
	}

	private void assertParsesNot(String file, String expectedError) {
		ms = new MetricStore(mm);
		try {
			parser.parse(dirBase + file, ms.getSAXParserHandler());
			fail("File " + file + " should not parse without errors.");
		} catch (Exception ex) {
			String msg = parser.getErrorMessage();
			if (msg.length() == expectedError.length()) {
				assertEquals(expectedError, msg);
			} else {
				assertTrue(msg.startsWith(expectedError));
			}
		}
	}
}
