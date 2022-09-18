package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.math.HashMultiSet;
import com.sdmetrics.model.MetaModelElement;
import com.sdmetrics.test.MetricTestContext;

public class TestCustomExtensions {

	MetricTestContext mtc;
	MetaModelElement classType;

	@Before
	public void init() throws Exception {
		mtc = MetricTestContext.getStandardContext04();
		classType = mtc.getType("class");
	}

	@Test
	public void verbOjectRule() throws SDMetricsException {
		mtc.assertCompliance("create bank account", "ActionNames", "action");
		mtc.assertCompliance("update customer", "ActionNames", "action");

		mtc.assertViolation("delete file", "ActionNames", "action",
				"Illegal name: delete file");
		mtc.assertViolation("verify_contract", "ActionNames", "action",
				"Element does not specify an object.");
	}

	@Test
	public void conditionalSet() throws SDMetricsException {

		Collection<?> persResult = mtc
				.getSetValue("AttrSet", "Person", "class");
		Collection<?> cruisResult = mtc
				.getSetValue("OpSet", "Cruiser", "class");

		assertSame(
				persResult,
				mtc.me.computeSet(mtc.getElement("Person", "class"),
						mtc.getSet("Majority", "class")));

		assertSame(
				cruisResult,
				mtc.me.computeSet(mtc.getElement("Cruiser", "class"),
						mtc.getSet("Majority", "class")));
	}

	@Test
	public void pairwiseMetric() throws SDMetricsException {
		assertEquals(2f,
				mtc.getMetricValue("ClsPairLength1", "relations", "package"), 0);
		assertEquals(3f,
				mtc.getMetricValue("ClsPairLength2", "relations", "package"), 0);
		assertEquals(5f,
				mtc.getMetricValue("ClsPairLength3", "relations", "package"), 0);

		float classCount = mtc.getMetricValue("ClassCount", "relations",
				"package");
		assertEquals(classCount * (classCount - 1) / 2,
				mtc.getMetricValue("ClsPair", "relations", "package"), 0);
	}

	@Test
	public void scalarOperations() throws SDMetricsException {
		assertEquals(2f,
				mtc.getMetricValue("MaxTestMetric", "Person", "class"), 0);
		assertEquals(4f,
				mtc.getMetricValue("MaxTestMetric", "Manufacturer", "class"), 0);
	}

	@Test
	public void booleanOperations() throws SDMetricsException {
		assertEquals(0f, mtc.getMetricValue("ExclusiveCls", "Person", "class"),
				0);
		assertEquals(1f,
				mtc.getMetricValue("ExclusiveCls", "Manufacturer", "class"), 0);
	}

	@Test
	public void setOperationsRegularSet() throws SDMetricsException {

		Variables vars = new Variables(null);
		Collection<String> testSet1 = new HashSet<String>(Arrays.asList("one",
				"two", "three", "four"));
		Collection<String> testSet2 = new HashSet<String>(Arrays.asList(
				"three", "four", "five", "six"));

		vars.setVariable("_set1", testSet1);
		vars.setVariable("_set2", testSet2);
		Collection<?> result = mtc.me.evalSetExpression(
				mtc.getElement("Person", "class"),
				mtc.parse("symmdiff(_set1,_set2)"), vars);

		assertEquals(4, result.size());
		assertTrue(result.contains("one"));
		assertTrue(result.contains("two"));
		assertTrue(result.contains("five"));
		assertTrue(result.contains("six"));
	}

	@Test
	public void setOperationsMultiSet() throws SDMetricsException {

		Variables vars = new Variables(null);
		Collection<String> testSet1 = new HashMultiSet<String>(Arrays.asList(
				"one", "one", "two", "three", "four", "four", "four"));
		Collection<String> testSet2 = new HashMultiSet<String>(Arrays.asList(
				"three", "four", "four", "five", "five", "six"));

		vars.setVariable("_set1", testSet1);
		vars.setVariable("_set2", testSet2);
		HashMultiSet<?> result = (HashMultiSet<?>) mtc.me.evalSetExpression(
				mtc.getElement("Person", "class"),
				mtc.parse("symmdiff(_set1,_set2)"), vars);

		assertEquals(7, result.size());
		assertEquals(2, result.getElementCount("one"));
		assertEquals(1, result.getElementCount("two"));
		assertEquals(1, result.getElementCount("four"));
		assertEquals(2, result.getElementCount("five"));
		assertEquals(1, result.getElementCount("six"));
	}
}
