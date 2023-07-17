package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.model.ModelElement;
import com.sdmetrics.test.MetricTestContext;


public class TestSetProcedureCompare {

	MetricTestContext mtc;
	ProcedureAttributes procAttrs;
	Set metric;
	SetProcedureCompare procedure;

	@Before
	public void loadTestFixture() throws Exception {
		mtc = MetricTestContext.getStandardContext04();
		procAttrs = new ProcedureAttributes();

		metric = new Set("test", mtc.getType("class"));
		metric.setAttributes(procAttrs);
		procedure = new SetProcedureCompare();
		procedure.setMetricsEngine(mtc.me);
	}

	@Test
	public void termWithDefined() throws SDMetricsException {
		procAttrs.setExpressionNode("set", mtc.parse("Descendents"));
		procAttrs.setExpressionNode("term", mtc.parse("NumOps"));
		procAttrs.setExpressionNode("with", mtc.parse("NumOps"));
		procAttrs.setExpressionNode("comp", mtc.parse("term<with"));

		HashSet<ModelElement> expected = new HashSet<ModelElement>();
		expected.add(mtc.getElement("Train", "class"));
		expected.add(mtc.getElement("Cruiser", "class"));

		assertEquals(expected,
				procedure.calculate(mtc.getElement("Vehicle", "class"), metric));
	}

	@Test
	public void termWithImplicit() throws SDMetricsException {
		procAttrs.setExpressionNode("set", mtc.parse("Descendents"));
		procAttrs.setExpressionNode("comp",
				mtc.parse("term.NumOps<with.NumOps"));

		HashSet<ModelElement> expected = new HashSet<ModelElement>();
		expected.add(mtc.getElement("Train", "class"));
		expected.add(mtc.getElement("Cruiser", "class"));
		assertEquals(expected,
				procedure.calculate(mtc.getElement("Vehicle", "class"), metric));
	}

	@Test
	public void comparisonImplicit() throws SDMetricsException {
		procAttrs.setExpressionNode("set", mtc.parse("Descendents"));
		procAttrs.setExpressionNode("term", mtc.parse("NumOps"));
		procAttrs.setExpressionNode("with", mtc.parse("NumOps"));

		HashSet<ModelElement> expected = new HashSet<ModelElement>();
		expected.add(mtc.getElement("Car", "class"));
		expected.add(mtc.getElement("Motorcycle", "class"));
		expected.add(mtc.getElement("Enduro", "class"));
		expected.add(mtc.getElement("Racecar", "class"));
		assertEquals(expected,
				procedure.calculate(mtc.getElement("Vehicle", "class"), metric));
	}

	@Test
	public void target() throws SDMetricsException {
		procAttrs.setExpressionNode("target", mtc.parse("class"));
		procAttrs.setExpressionNode("comp",
				mtc.parse("length(term.name)<length(with.name)"));

		HashSet<ModelElement> expected = new HashSet<ModelElement>();
		expected.add(mtc.getElement("Manufacturer", "class"));

		assertEquals(expected, procedure.calculate(
				mtc.getElement("Motorcycle", "class"), metric));

		procAttrs.setExpressionNode("target", mtc.parse("MacGuffin"));
		try {
			procedure.calculate(mtc.getElement("Motorcycle", "class"), metric);
		} catch (SDMetricsException ex) {
			assertEquals("Unknown target model element type 'MacGuffin'.",
					ex.getMessage());
		}
	}

	@Test
	public void excludeSelf() throws SDMetricsException {
		procAttrs.setExpressionNode("target", mtc.parse("class"));
		procAttrs.setExpressionNode("comp",
				mtc.parse("length(term.name)<=length(with.name)"));
		procAttrs.setExpressionNode("exclude_self", mtc.parse("true"));

		HashSet<ModelElement> expected = new HashSet<ModelElement>();
		expected.add(mtc.getElement("Manufacturer", "class"));

		assertEquals(expected, procedure.calculate(
				mtc.getElement("Motorcycle", "class"), metric));

		procAttrs.setExpressionNode("exclude_self", mtc.parse("false"));
		expected.add(mtc.getElement("Motorcycle", "class"));
		assertEquals(expected, procedure.calculate(
				mtc.getElement("Motorcycle", "class"), metric));
	}

	@Test
	public void missingSet() throws SDMetricsException {
		try {
			procedure.calculate(mtc.getElement("Motorcycle", "class"), metric);
		} catch (SDMetricsException ex) {
			assertEquals(
					"Either the 'target' or the 'set' attribute must be specified.",
					ex.getMessage());
		}
	}

	@Test
	public void testFiltering() throws SDMetricsException {
		procAttrs.setExpressionNode("set", mtc.parse("IncomingDependencies"));
		procAttrs.setExpressionNode("element", mtc.parse("depclient"));
		procAttrs.setExpressionNode("eltype", mtc.parse("class"));
		procAttrs.setExpressionNode("comp",
				mtc.parse("length(term.name)<length(with.name)"));
		assertEquals(0,
				procedure.calculate(mtc.getElement("Vehicle", "class"), metric)
						.size());
		assertEquals(1,
				procedure
						.calculate(mtc.getElement("Supplier", "class"), metric)
						.size());
	}

}
