package com.sdmetrics.metrics;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.model.*;
import com.sdmetrics.test.MetricTestContext;


public class TestSDMetricsException {

	MetricTestContext mtc;
	ModelElement element;
	MetricEntry metric;
	
	@Before
	public void loadTestElements() throws Exception {
		mtc=MetricTestContext.getStandardContext04();
		element=mtc.getElement(0, "class");
		metric=mtc.getMetric("NumOps", "class");
	}
	
	@Test
	public void construction() {
		SDMetricsException ex=new SDMetricsException(
				element,metric,"Oopsy daisy");
		assertSame(metric, ex.getMetricEntry());
		assertSame(element, ex.getElement());
		assertEquals("Oopsy daisy", ex.getMessage());
	}
	
	@Test
	public void chaining() {
		IllegalArgumentException cause=
			new IllegalArgumentException("Man, that hurt!");
		SDMetricsException ex=new SDMetricsException(
				element,metric,cause);
		
		assertSame(metric, ex.getMetricEntry());
		assertSame(element, ex.getElement());
		assertSame(cause, ex.getCause());
		assertTrue(ex.getMessage().endsWith("Man, that hurt!"));
	}
	
	@Test
	public void fillInPerpetrators() {
		SDMetricsException ex=new SDMetricsException(null,null,
				"Don't know what I'm doing here");
		
		ex.fillInPerpetrators(element, metric);
		assertSame(metric, ex.getMetricEntry());
		assertSame(element, ex.getElement());
		
		ex.fillInPerpetrators(mtc.getElement(1, "class"),
				mtc.getRule("HasAttributes","class"));
		assertSame(metric, ex.getMetricEntry());
		assertSame(element, ex.getElement());
	}
	
}
