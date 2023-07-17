package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.test.MetricTestContext;

public class TestAbstractProcedure {

	MetricsEngine me;

	MetricTestContext mtc;

	@Before
	public void loadTestFixture() throws Exception {
		mtc = MetricTestContext.getStandardContext04();
		me = mtc.me;
	}

	@Test
	public void rigging() {
		MetricProcedureProjection procedure = new MetricProcedureProjection();
		procedure.setMetricsEngine(me);
		procedure.setName("marvin");

		assertSame(me, procedure.getMetricsEngine());
		assertSame(mtc.mm, procedure.getMetaModel());
		assertSame(mtc.model, procedure.getModel());
		assertEquals("marvin", procedure.getName());

		procedure.clear();
		assertNull(procedure.getMetricsEngine());
	}

	@Test
	public void getRelation() throws SDMetricsException {
		MetricProcedureProjection procedure = new MetricProcedureProjection();
		procedure.setMetricsEngine(me);

		ProcedureAttributes procAttrs = new ProcedureAttributes();
		try {
			procedure.getRelationOrSet(null, procAttrs, null);
			fail("Exception expected");
		} catch (SDMetricsException ex) {
			assertEquals(
					"Neither 'relation' nor 'relset' attribute specified.",
					ex.getMessage());
		}

		procAttrs.setExpressionNode("relset", mtc.parse("AttrSet"));
		Collection<?> attributes = procedure.getRelationOrSet(
				mtc.getElement("Person", "class"), procAttrs, null);
		assertEquals(2, attributes.size());

		procAttrs.setExpressionNode("relation", mtc.parse("context"));
		Collection<?> ownedElements = procedure.getRelationOrSet(
				mtc.getElement("package1", "package"), procAttrs, null);
		assertEquals(4, ownedElements.size());
	}
}
