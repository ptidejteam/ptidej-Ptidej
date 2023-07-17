package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.model.ModelElement;
import com.sdmetrics.test.MetricTestContext;

public class TestFilterAttributeProcessor {

	MetricTestContext mtc;
	ProcedureAttributes procAttr;

	ModelElement cls1;
	ModelElement cls2;
	ModelElement cls3;
	ModelElement cls4;
	ModelElement cls5;
	ModelElement cls6;
	ModelElement pck1;
	ModelElement pck1_1;
	ModelElement root;

	@Before
	public void loadTestFixture() throws Exception {
		mtc = MetricTestContext.getStandardContext04();

		procAttr = new ProcedureAttributes();
		cls1 = mtc.getElement("Class1", "class");
		cls2 = mtc.getElement("Class2", "class");
		cls3 = mtc.getElement("Class3", "class");
		cls4 = mtc.getElement("Class4", "class");
		cls5 = mtc.getElement("Class5", "class");
		cls6 = mtc.getElement("Class6", "class");
		pck1 = mtc.getElement("package1", "package");
		pck1_1 = mtc.getElement("package1_1", "package");
		root = mtc.getElement(0, "model");
	}

	@Test
	public void targetFiltering() throws SDMetricsException {
		checkTargetFiltering(null, true);
		checkTargetFiltering("class|model", true);
		checkTargetFiltering("model|+class", true);
		checkTargetFiltering("class|package", false);
		checkTargetFiltering("+package|class", true);
		checkTargetFiltering("-operation", false);
		try {
			checkTargetFiltering("attribute|+operation|+humdinger", true);
		} catch (SDMetricsException ex) {
			assertEquals("Unknown model element type 'humdinger'.",
					ex.getMessage());
		}
	}

	/*
	 * Apply class "Person" of the test model to a target filter string and
	 * check the expected outcome
	 */
	private void checkTargetFiltering(String targetFilter,
			boolean expectedResult) throws SDMetricsException {
		if (targetFilter != null)
			procAttr.setExpressionNode("target", mtc.parse(targetFilter));
		FilterAttributeProcessor fap = new FilterAttributeProcessor(mtc.me,
				procAttr);

		fap.applyFilters(null, mtc.getElement(0, "model"), null);
		assertEquals(Boolean.valueOf(expectedResult),
				Boolean.valueOf(fap.isValid()));
	}

	@Test
	public void targetConditionFiltering() throws SDMetricsException {
		procAttr.setExpressionNode("targetcondition",
				mtc.parse("name='Class2'"));
		FilterAttributeProcessor fap = new FilterAttributeProcessor(mtc.me,
				procAttr);
		ModelElement target = fap.applyFilters(null, cls1, null);
		assertSame(cls1, target);
		assertFalse(fap.isValid());

		target = fap.applyFilters(null, cls2, null);
		assertSame(cls2, target);
		assertTrue(fap.isValid());
	}

	@Test
	public void elementFiltering() throws SDMetricsException {
		procAttr.setExpressionNode("element", mtc.parse("context"));
		FilterAttributeProcessor fap = new FilterAttributeProcessor(mtc.me,
				procAttr);
		ModelElement target = fap.applyFilters(null, cls1, null);
		assertSame(pck1, target);
		assertTrue(fap.isValid());

		procAttr.setExpressionNode("eltype", mtc.parse("class"));
		fap = new FilterAttributeProcessor(mtc.me, procAttr);
		target = fap.applyFilters(null, cls1, null);
		assertSame(pck1, target);
		assertFalse(fap.isValid());

		assertNull(fap.applyFilters(null, root, null));
	}

	@Test
	public void conditionFiltering() throws SDMetricsException {
		procAttr.setExpressionNode("element", mtc.parse("context"));
		procAttr.setExpressionNode("condition", mtc.parse("name='package1'"));
		FilterAttributeProcessor fap = new FilterAttributeProcessor(mtc.me,
				procAttr);
		ModelElement target = fap.applyFilters(null, cls1, null);
		assertSame(pck1, target);
		assertTrue(fap.isValid());

		target = fap.applyFilters(null, cls3, null);
		assertSame(pck1_1, target);
		assertFalse(fap.isValid());
	}

	@Test
	public void scopeFiltering() throws SDMetricsException {
		checkScope("idem", cls1, cls1, true);
		checkScope("idem", cls1, cls2, false);
		checkScope("notidem", cls1, cls1, false);
		checkScope("notidem", cls1, cls2, true);
		checkScope("containedin", pck1_1, cls2, false);
		checkScope("containedin", pck1_1, cls3, true);
		checkScope("containedin", pck1_1, cls5, true);
		checkScope("notcontainedin", pck1, pck1_1, false);
		checkScope("notcontainedin", pck1_1, pck1, true);

		checkScope("same", cls1, cls2, true);
		checkScope("same", cls1, cls5, false);
		checkScope("other", cls1, pck1_1, false);
		checkScope("other", cls1, cls3, true);

		checkScope("lower", cls1, cls2, false);
		checkScope("lower", cls1, cls3, true);
		checkScope("notlower", cls2, cls5, false);
		checkScope("notlower", cls3, cls6, true);
		checkScope("sameorlower", cls1, cls2, true);
		checkScope("sameorlower", cls2, cls5, true);
		checkScope("sameorlower", cls5, cls1, false);

		checkScope("higher", cls3, cls6, false);
		checkScope("higher", cls4, cls2, true);
		checkScope("nothigher", cls3, cls2, false);
		checkScope("nothigher", cls4, cls5, true);
		checkScope("sameorhigher", cls3, cls4, true);
		checkScope("sameorhigher", cls5, cls1, true);
		checkScope("sameorhigher", cls3, cls6, false);

		checkScope("samebranch", cls1, cls2, true);
		checkScope("samebranch", cls1, cls6, true);
		checkScope("samebranch", cls5, cls2, true);
		checkScope("samebranch", cls3, cls6, false);

		checkScope("notsamebranch", cls5, cls6, true);
		checkScope("notsamebranch", cls2, cls6, false);

		try {
			checkScope("ontopof", cls1, cls2, false);
			fail("Exception expected.");
		} catch (SDMetricsException ex) {
			assertEquals("Illegal scope criterion 'ontopof'.", ex.getMessage());
		}
	}

	private void checkScope(String scopeCriterion, ModelElement elem1,
			ModelElement elem2, boolean expectedResult)
			throws SDMetricsException {
		ProcedureAttributes model = new ProcedureAttributes();
		model.setExpressionNode("scope", mtc.parse(scopeCriterion));
		FilterAttributeProcessor fap = new FilterAttributeProcessor(mtc.me,
				model);
		assertSame(elem2, fap.applyFilters(elem1, elem2, null));
		assertEquals(Boolean.valueOf(expectedResult),
				Boolean.valueOf(fap.isValid()));
	}

	@Test
	public void iteration() throws Exception {

		String[] filters = { "#.#.#.package1_1" };
		mtc.model.setFilter(filters, true, true);

		procAttr.setExpressionNode("condition", mtc.parse("name!='Class4'"));

		FilterAttributeProcessor fap = new FilterAttributeProcessor(mtc.me,
				procAttr);
		Collection<ModelElement> set = mtc.model.getElements(mtc
				.getType("class"));
		Variables vars = new Variables(root);

		// Do a full iteration
		HashSet<ModelElement> matches = new HashSet<ModelElement>();
		for (ModelElement elem : fap.fullIteration(set, vars)) {
			assertTrue(elem.getName(), fap.isValid() == (elem != cls4));
			matches.add(elem);
		}
		assertEquals(3, matches.size());
		assertTrue(matches.contains(cls3));
		assertTrue(matches.contains(cls4));
		assertTrue(matches.contains(cls5));

		// Do one iteration with valid elements only
		matches.clear();
		for (ModelElement elem : fap.validIteration(set, vars)) {
			assertTrue(fap.isValid());
			matches.add(elem);
		}
		assertEquals(2, matches.size());
		assertTrue(matches.contains(cls3));
		assertTrue(matches.contains(cls5));

		// Test irregular use of next/hasNext
		Iterator<ModelElement> it = fap.fullIteration(set, vars).iterator();
		it.next();
		assertTrue(it.hasNext());
		assertTrue(it.hasNext());
		it.next();
		it.next();
		assertFalse(it.hasNext());
		assertFalse(it.hasNext());
		try {
			it.next();
			fail("NoSuchElementException expected");
		} catch (NoSuchElementException ex) {
			// passed the test
		}

	}
}
