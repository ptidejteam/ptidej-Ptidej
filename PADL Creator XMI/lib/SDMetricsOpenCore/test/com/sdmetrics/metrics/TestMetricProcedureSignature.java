package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sdmetrics.model.ModelElement;
import com.sdmetrics.test.MetricTestContext;


public class TestMetricProcedureSignature {

	@Test 
	public void defaultSignature() throws Exception {
		MetricTestContext mtc=MetricTestContext.getStandardContext04();
		assertEquals("Vehicle()", mtc.getMetricStrValue("ClientSignature","Vehicle","class"));
		
		ModelElement suppl=mtc.getElement("Supplier", "class");
		ModelElement logist=mtc.getElement("Logistics", "interface");
		String expectedSig="Manufacturer("+suppl.getXMIID()+","+logist.getXMIID()+")";
		assertEquals(expectedSig, mtc.getMetricStrValue("SupplierSignature","Manufacturer","class"));
	}
	
	@Test 
	public void customSignature() throws Exception {
		MetricTestContext mtc=MetricTestContext.getStandardContext04();
		assertEquals("Manufacturer<([Supplier/Logistics])>Manufacturer",
				mtc.getMetricStrValue("CustomSignature","Manufacturer","class"));
		assertEquals("Enduro<([])>Enduro",
				mtc.getMetricStrValue("CustomSignature","Enduro","class"));
	}
}
