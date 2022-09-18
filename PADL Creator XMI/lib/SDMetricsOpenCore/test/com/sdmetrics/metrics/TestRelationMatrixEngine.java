package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.model.MetaModelElement;
import com.sdmetrics.model.ModelElement;
import com.sdmetrics.test.MetricTestContext;


public class TestRelationMatrixEngine {

	MetricTestContext mtc;
	MetaModelElement cls;
	MatrixEngine engine;

	@Before
	public void loadFixture() throws Exception {
		mtc = MetricTestContext.getStandardContext04();
		cls = mtc.getType("class");
		engine = new MatrixEngine(mtc.me);
	}

	@Test
	public void noElementFiltering() throws SDMetricsException {
		Matrix depElementMatrix = mtc.ms.getMatrices().get(0);
		MatrixData mat = engine.calculate(depElementMatrix);
		int classCount = mtc.model.getAcceptedElements(cls).size();
		assertEquals(classCount, mat.getNumberOfRows());
		assertEquals(classCount, mat.getNumberOfColumns());
		assertFalse(mat.isEmpty());
		assertSame(depElementMatrix, mat.getMatrixDefinition());

		int manufacturerIndex = findRowElement(mat,
				mtc.getElement("Manufacturer", "class"));
		int supplierIndex = findColumnElement(mat,
				mtc.getElement("Supplier", "class"));
		assertEquals(Integer.valueOf(1),
				mat.getValueAt(manufacturerIndex, supplierIndex));
		assertEquals(Integer.valueOf(0),
				mat.getValueAt(supplierIndex, manufacturerIndex));

	}

	@Test
	public void filtering() throws SDMetricsException {
		MatrixData mat = engine.calculate(mtc.ms.getMatrices().get(2));
		int opCount = mtc.model.getAcceptedElements(mtc.getType("operation"))
				.size();
		int classCount = mtc.model.getAcceptedElements(cls).size();
		assertTrue(classCount > mat.getNumberOfRows());
		assertEquals(opCount - 1, mat.getNumberOfColumns());

		int derailIndex = findColumnElement(mat,
				mtc.getElement("derail", "operation"));
		assertEquals(-1, derailIndex);
		int polishIndex = findColumnElement(mat,
				mtc.getElement("polish", "operation"));
		int manufacturerIndex = findRowElement(mat,
				mtc.getElement("Manufacturer", "class"));
		assertEquals(-1, manufacturerIndex);
		int cruiserIndex = findRowElement(mat,
				mtc.getElement("Cruiser", "class"));

		assertEquals(Integer.valueOf(1),
				mat.getValueAt(cruiserIndex, polishIndex));
		assertEquals(Integer.valueOf(0), mat.getValueAt(
				findRowElement(mat, mtc.getElement("Car", "class")),
				polishIndex));
	}

	private int findRowElement(MatrixData mat, ModelElement elem) {
		for (int i = 0; i < mat.getNumberOfRows(); i++)
			if (mat.getRowElement(i) == elem)
				return i;
		return -1;
	}

	private int findColumnElement(MatrixData mat, ModelElement elem) {
		for (int i = 0; i < mat.getNumberOfColumns(); i++)
			if (mat.getColumnElement(i) == elem)
				return i;
		return -1;
	}
}
