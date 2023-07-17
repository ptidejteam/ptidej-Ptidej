package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.model.ModelElement;
import com.sdmetrics.test.MetricTestContext;

public class TestSetSummationHelper {

	MetricTestContext mtc;
	Set set;
	ProcedureAttributes procAttrs;
	ModelElement class1;
	ModelElement class2;
	ModelElement pck1;
	ModelElement pck1_1;
	Variables vars;

	@Before
	public void loadTestFixture() throws Exception {
		mtc = MetricTestContext.getStandardContext04();
		procAttrs = new ProcedureAttributes();
		procAttrs.setExpressionNode("exclude_self", mtc.parse("true"));
		set = new Set("testSet", mtc.getType("class"));
		set.setAttributes(procAttrs);
		pck1 = mtc.getElement("package1", "package");
		pck1_1 = mtc.getElement("package1_1", "package");
		class1 = mtc.getElement("Class1", "class");
		class2 = mtc.getElement("Class2", "class");
		vars = new Variables(pck1);
	}

	@Test
	public void multisetManipulation() throws SDMetricsException {

		set.setMultiSet(true);
		SetSummationHelper ssh = new SetSummationHelper(mtc.me, set, null);
		assertEquals(0, ssh.getResultSet().size());

		ssh.add(class1, vars);
		ssh.add(class1, vars);
		ssh.add(class2, vars);
		assertEquals(3, ssh.getResultSet().size());

		ssh.add(pck1.getOwnedElements());
		assertEquals(7, ssh.getResultSet().size());

		ssh.excludeSelf(class1);
		assertEquals(4, ssh.getResultSet().size());
	}

	@Test
	public void simplesetManipulation() throws SDMetricsException {

		SetSummationHelper ssh = new SetSummationHelper(mtc.me, set, null);
		assertEquals(0, ssh.getResultSet().size());

		ssh.add(class1, vars);
		ssh.add(class1, vars);
		ssh.add(class2, vars);
		assertEquals(2, ssh.getResultSet().size());

		ssh.add(pck1.getOwnedElements());
		assertEquals(4, ssh.getResultSet().size());

		ssh.excludeSelf(class1);
		assertEquals(3, ssh.getResultSet().size());
	}

	@Test
	public void exludeSelfDisabled() throws SDMetricsException {
		set.setAttributes(new ProcedureAttributes());
		SetSummationHelper ssh = new SetSummationHelper(mtc.me, set, null);

		ssh.add(class1, vars);
		assertEquals(1, ssh.getResultSet().size());
		ssh.excludeSelf(class1);
		assertEquals(1, ssh.getResultSet().size());
	}

	@Test
	public void setExpressionSummation() throws SDMetricsException {
		procAttrs.setExpressionNode("testSetAttribute", mtc.parse("Classes"));

		SetSummationHelper ssh = new SetSummationHelper(mtc.me, set,
				"testSetAttribute");

		ssh.add(pck1, vars);
		assertEquals(2, ssh.getResultSet().size());
		assertTrue(ssh.getResultSet().contains(class1));
		assertTrue(ssh.getResultSet().contains(class2));

		ssh.add(pck1_1, vars);
		assertEquals(4, ssh.getResultSet().size());
	}

	@Test
	public void valuesetSummation() throws SDMetricsException {
		procAttrs.setExpressionNode("testSetAttribute", mtc.parse("Classes"));
		procAttrs.setExpressionNode("valueset", mtc.parse("NameLength"));

		SetSummationHelper ssh = new SetSummationHelper(mtc.me, set,
				"testSetAttribute");

		ssh.add(pck1, vars);
		assertEquals(1, ssh.getResultSet().size());
		assertTrue(ssh.getResultSet().contains(Integer.valueOf(8)));
		ssh.add(pck1_1, vars);
		assertEquals(2, ssh.getResultSet().size());
		assertTrue(ssh.getResultSet().contains(Integer.valueOf(10)));
	}
}
