package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.model.MetaModel;
import com.sdmetrics.model.Model;
import com.sdmetrics.model.ModelElement;
import com.sdmetrics.test.MetricTestContext;

public class TestMetricsEngine {

	MetricsEngine me;

	MetricTestContext mtc;

	Variables vars;

	@Before
	public void loadTestFixture() throws Exception {
		mtc = MetricTestContext.getStandardContext04();
		me = mtc.me;
		vars = new Variables(mtc.getElement("Person", "class"));
	}

	@Test
	public void initialization() {
		assertSame(mtc.mm, me.getMetaModel());
		assertSame(mtc.ms, me.getMetricStore());
		assertSame(mtc.model, me.getModel());
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void metaModelMismatch() {
		MetaModel mm2 = new MetaModel();
		Model model2 = new Model(mm2);
		new MetricsEngine(mtc.ms, model2);
	}

	@Test
	public void metricAccess() throws SDMetricsException {
		ModelElement pers = mtc.getElement("Person", "class");
		Metric numAttrMetric = mtc.getMetric("NumAttr", "class");

		Number attrCount = (Number) me.getMetricValue(pers, numAttrMetric);
		assertEquals(Integer.valueOf(2), attrCount);
		assertSame(attrCount, me.getMetricValue(pers, numAttrMetric));

		Metric faulty = mtc.getMetric("FaultyMetric", "class");
		try {
			me.getMetricValue(pers, faulty);
			fail("Exception expected");
		} catch (SDMetricsException ex) {
			assertEquals("Missing required attribute 'term'.", ex.getMessage());
			assertSame(faulty, ex.getMetricEntry());
			assertSame(pers, ex.getElement());
		}
	}

	@Test
	public void illegalMetric() {
		ModelElement pers = mtc.getElement("Person", "class");
		Metric numAttrMetric = mtc.getMetric("NumAttr", "class");
		numAttrMetric.setAttributes(null);

		try {
			me.getMetricValue(pers, numAttrMetric);
			fail("Exception expected");
		} catch (SDMetricsException ex) {
			assertTrue(ex.getMessage().startsWith(
					"Internal metrics engine failure"));
			assertSame(numAttrMetric, ex.getMetricEntry());
			assertSame(pers, ex.getElement());
			assertEquals(NullPointerException.class, ex.getCause().getClass());
		}
	}

	@Test
	public void setAccess() throws SDMetricsException {
		ModelElement pers = mtc.getElement("Person", "class");
		Set attrSet = mtc.getSet("AttrSet", "class");

		Collection<?> attributes = me.getSet(pers, attrSet);
		assertEquals(2, attributes.size());
		assertSame(attributes, me.getSet(pers, attrSet));

		Set faulty = mtc.getSet("FaultySet", "class");
		try {
			me.getSet(pers, faulty);
			fail("Exception expected");
		} catch (SDMetricsException ex) {
			assertEquals("Missing required attribute 'set'.", ex.getMessage());
			assertSame(faulty, ex.getMetricEntry());
			assertSame(pers, ex.getElement());
		}
	}

	@Test
	public void illegalSet() {
		ModelElement pers = mtc.getElement("Person", "class");
		Set attrSet = mtc.getSet("AttrSet", "class");
		attrSet.setAttributes(null);

		try {
			me.getSet(pers, attrSet);
			fail("Exception expected");
		} catch (SDMetricsException ex) {
			assertTrue(ex.getMessage().startsWith(
					"Internal metrics engine failure"));
			assertSame(attrSet, ex.getMetricEntry());
			assertSame(pers, ex.getElement());
			assertEquals(NullPointerException.class, ex.getCause().getClass());
		}
	}

	@Test
	public void scalarIdentifiers() throws SDMetricsException {
		ModelElement pers = mtc.getElement("Person", "class");
		ModelElement model = mtc.getElement(0, "model");
		assertEquals(Integer.valueOf(1),
				me.evalExpression(pers, mtc.parse("NumOps"), null));
		assertSame(pers.getOwner(),
				me.evalExpression(pers, mtc.parse("context"), null));
		assertEquals("", me.evalExpression(model, mtc.parse("context"), null));
		assertEquals("Person", me.evalExpression(pers, mtc.parse("name"), null));
		assertSame(pers, me.evalExpression(pers, mtc.parse("_self"), null));
		vars.setVariable("_string", "Hi there!");
		assertEquals("Hi there!",
				me.evalExpression(pers, mtc.parse("_string"), vars));

		assertSame(pers, me.evalExpression(pers, mtc.parse("_principal"), vars));
		vars.setVariable("_principal", model);
		assertSame(model,
				me.evalExpression(pers, mtc.parse("_principal"), vars));

		try {
			me.evalExpression(pers, mtc.parse("stereotypes"), null);
			fail("Exception expected");
		} catch (SDMetricsException ex) {
			assertEquals(
					"No metric or single-valued attribute 'stereotypes' for elements of type 'class'.",
					ex.getMessage());
		}
		try {
			me.evalExpression(pers, mtc.parse("salary"), vars);
			fail("Exception expected");
		} catch (SDMetricsException ex) {
			// failed with non-null valuemap
		}
	}

	@Test
	public void setIdentifiers() throws SDMetricsException {
		ModelElement pers = mtc.getElement("Person", "class");

		Collection<?> stereotypes = me.evalSetExpression(pers,
				mtc.parse("stereotypes"), null);
		assertSame(stereotypes.iterator().next(),
				mtc.getElement("variant", "stereotype"));

		vars.setVariable("_tmpvar", stereotypes);
		assertSame(stereotypes,
				me.evalSetExpression(pers, mtc.parse("_tmpvar"), vars));

		vars.setVariable("_string", "false type");
		try {
			me.evalSetExpression(pers, mtc.parse("_string"), vars);
			fail("Exception expected");
		} catch (SDMetricsException ex) {
			assertEquals("Variable '_string' is not a set.", ex.getMessage());
		}

		vars.setVariable("_nullSet", null);
		try {
			me.evalSetExpression(pers, mtc.parse("_nullSet"), vars);
			fail("Exception expected");
		} catch (SDMetricsException ex) {
			assertEquals(
					"Unknown set identifier '_nullSet' for elements of type 'class'.",
					ex.getMessage());
		}

		try {
			me.evalSetExpression(pers, mtc.parse("name"), null);
			fail("Exception expected");
		} catch (SDMetricsException ex) {
			assertEquals(
					"Unknown set identifier 'name' for elements of type 'class'.",
					ex.getMessage());
		}

		try {
			me.evalSetExpression(pers, mtc.parse("'giggles'"), vars);
			fail("Exception expected");
		} catch (SDMetricsException ex) {
			assertEquals("Illegal set expression.", ex.getMessage());
		}
	}

	@Test
	public void setExpressions() throws SDMetricsException {
		ModelElement pers = mtc.getElement("Person", "class");

		Collection<?> set = me.evalSetExpression(pers,
				mtc.parse("AttrSet*OpSet"), vars);
		assertEquals(0, set.size());

		try {
			me.evalSetExpression(pers, mtc.parse("simpleSet/multiSet"), vars);
			fail("Exception expected");
		} catch (SDMetricsException ex) {
			assertEquals("Unknown set operation '/'.", ex.getMessage());
		}

		try {
			me.evalSetExpression(pers, mtc.parse("!simpleSet"), vars);
			fail("Exception expected");
		} catch (SDMetricsException ex) {
			assertEquals("Unknown set operation '!'.", ex.getMessage());
		}
	}

	@Test
	public void scalarExpressions() throws SDMetricsException {
		ModelElement pers = mtc.getElement("Person", "class");
		vars.setVariable("_val", Integer.valueOf(3));
		assertEquals(Float.valueOf(7),
				me.evalExpression(pers, mtc.parse("_val+4"), vars));

		assertEquals("QP", me.evalExpression(pers, mtc.parse("'Q'+'P'"), vars));

		assertEquals(Integer.valueOf(1),
				me.evalExpression(pers, mtc.parse("NumOps"), vars));
		assertSame(pers, me.evalExpression(pers, mtc.parse("self"), vars));
		assertSame(pers, me.evalExpression(pers, mtc.parse("_self"), vars));
	}

	@Test
	public void modelElementExpression() throws SDMetricsException {
		ModelElement elem = new ModelElement(mtc.getType("class"));
		assertNull(me.evalModelElementExpression(elem, mtc.parse("5"), vars));
		assertSame(elem,
				me.evalModelElementExpression(elem, mtc.parse("_self"), vars));
		assertSame(mtc.getElement("Person", "class"),
				me.evalModelElementExpression(elem, mtc.parse("_principal"),
						vars));
	}

	@Test
	public void booleanExpression() throws SDMetricsException {
		assertTrue(me.evalBooleanExpression(null, mtc.parse("1>0"), null));
	}

}
