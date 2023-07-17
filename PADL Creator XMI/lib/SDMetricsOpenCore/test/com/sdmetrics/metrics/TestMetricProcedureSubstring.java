package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.model.ModelElement;
import com.sdmetrics.test.MetricTestContext;
import com.sdmetrics.test.Utils;

public class TestMetricProcedureSubstring {

	MetricTestContext mtc;
	ModelElement cls;

	@Before
	public void loadTestFixture() throws Exception {
		mtc = MetricTestContext.getStandardContext04();
		cls = mtc.getElement("Person", "class");
	}

	@Test
	public void values() {
		checkValue("SubStrFirst", "206");
		checkValue("SubLast", "13");
		checkValue("SubStrLastWithFunction", Integer.valueOf(13));
		checkValue("SubStrLimit", "69, 13");
		checkValue("SubStrLastButOne", "69");
		checkValue("SubStrEndSep1", "620");
		checkValue("SubStrEndSep2", "783");
		checkValue("SubStrEndSepNotFound", "732 ; Bottom=783 ;");
	}

	@Test
	public void errorHandling() throws Exception {
		mtc = MetricTestContext.getStandardContext(Utils.TEST_METRICS_DIR
				+ "testMetricsIllegalSubstrings.xml");
		cls = mtc.getElement("Person", "class");
		try {
			checkValue("SubStrIllegal1", "dontcare");
			fail("Exception expected");
		} catch (SDMetricsException ex) {
			assertEquals("Illegal separator position 2 for source '206, 320'",
					ex.getMessage());
		}

		try {
			checkValue("SubStrIllegal2", "dontcare");
			fail("Exception expected");
		} catch (SDMetricsException ex) {
			assertEquals("Illegal separator position -3 for source '206, 320'",
					ex.getMessage());
		}
	}

	private void checkValue(String metric, Object expectedValue) {
		Metric substr = mtc.getMetric(metric, "class");
		assertEquals(expectedValue, mtc.me.getMetricValue(cls, substr));
	}
}
