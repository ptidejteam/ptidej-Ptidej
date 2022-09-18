package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.Test;

import com.sdmetrics.model.ModelElement;
import com.sdmetrics.test.MetricTestContext;

public class TestSetProcedureSubelements {

	@Test
	public void values() throws Exception {
		MetricTestContext mtc = MetricTestContext.getStandardContext04();

		HashSet<ModelElement> result = new HashSet<ModelElement>();
		assertEquals(result,
				mtc.getSetValue("Subclasses", "package2", "package"));

		result.add(mtc.getElement("Class3", "class"));
		result.add(mtc.getElement("Class4", "class"));
		result.add(mtc.getElement("Class5", "class"));
		assertEquals(result,
				mtc.getSetValue("Subclasses", "package1_1", "package"));
	}
}
