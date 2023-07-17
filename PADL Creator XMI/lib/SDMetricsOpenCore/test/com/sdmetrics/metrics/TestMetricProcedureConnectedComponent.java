package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.model.ModelElement;
import com.sdmetrics.test.MetricTestContext;


public class TestMetricProcedureConnectedComponent {

	MetricTestContext mtc;
	ProcedureAttributes procAttrs;
	Metric metric;
	MetricProcedureConnectedComponents procedure;
	ModelElement ccPackage;

	@Before
	public void loadTestFixture() throws Exception {
		mtc = MetricTestContext.getStandardContext04();
		procAttrs = new ProcedureAttributes();
		procAttrs.setExpressionNode("set", mtc.parse("Classes"));
		procAttrs.setExpressionNode("nodes", mtc.parse("DependentElements"));

		metric = new Metric("test", mtc.getType("class"), null);
		metric.setAttributes(procAttrs);
		procedure = new MetricProcedureConnectedComponents();
		procedure.setMetricsEngine(mtc.me);
		ccPackage = mtc.getElement("conncomp", "package");
	}

	@Test
	public void directed() throws SDMetricsException {
		assertEquals(Integer.valueOf(5), procedure.calculate(ccPackage, metric));
	}

	@Test
	public void undirected() throws SDMetricsException {
		procAttrs.setExpressionNode("undirected", mtc.parse("true"));
		assertEquals(Integer.valueOf(4), procedure.calculate(ccPackage, metric));
	}

	@Test
	public void minnodes() throws SDMetricsException {
		procAttrs.setExpressionNode("minnodes", mtc.parse("2"));
		assertEquals(Integer.valueOf(1), procedure.calculate(ccPackage, metric));
	}

	@Test
	public void directCalculation() throws SDMetricsException {
		MetricProcedureConnectedComponents proc = new MetricProcedureConnectedComponents();
		StronglyConnectedComponents<ModelElement> scc = proc
				.getConnectedComponents(mtc.me, ccPackage, metric);
		assertEquals(5, scc.getConnectedComponentCount());
	}
}
