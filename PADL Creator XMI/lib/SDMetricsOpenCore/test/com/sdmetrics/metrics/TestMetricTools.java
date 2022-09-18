package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.sdmetrics.math.HashMultiSet;

public class TestMetricTools {

	Collection<?> emptyPlainSet = MetricTools.createHashSet(false);
	Collection<?> emptyMultiSet = MetricTools.createHashSet(true);
	List<String> list = Arrays.asList("a", "c", "d", "c");

	Collection<?> plainSet = MetricTools.createHashSet(false, list);
	Collection<?> multiSet = MetricTools.createHashSet(true, list);

	@Test
	public void newEmptySet() {
		assertEquals(HashSet.class, emptyPlainSet.getClass());
		assertTrue(emptyPlainSet.isEmpty());
		assertEquals(HashMultiSet.class, emptyMultiSet.getClass());
		assertTrue(emptyMultiSet.isEmpty());
	}

	@Test
	public void newSetWithContents() {
		assertEquals(HashSet.class, plainSet.getClass());
		assertEquals(3, plainSet.size());
		assertEquals(HashMultiSet.class, multiSet.getClass());
		assertEquals(4, multiSet.size());
	}

	@Test
	public void testType() {
		assertFalse(MetricTools.isMultiSet(plainSet));
		assertTrue(MetricTools.isMultiSet(emptyMultiSet));
	}

	@Test
	public void elementCount() {
		assertEquals(1, MetricTools.elementCount(plainSet, "c"));
		assertEquals(2, MetricTools.elementCount(multiSet, "c"));
		assertEquals(0, MetricTools.elementCount(plainSet, "M"));
		assertEquals(0, MetricTools.elementCount(multiSet, "M"));
	}

	@Test
	public void removeAny() {
		MetricTools.removeAny(plainSet, "c");
		assertEquals(2, plainSet.size());
		MetricTools.removeAny(multiSet, "c");
		assertEquals(2, multiSet.size());
	}

	@Test
	public void flatIterator() {
		checkACDIteration(MetricTools.getFlatIterator(plainSet));
		checkACDIteration(MetricTools.getFlatIterator(multiSet));
	}

	private void checkACDIteration(Iterator<?> it) {
		HashSet<Object> values = new HashSet<Object>();
		int valueCount = 0;
		while (it.hasNext()) {
			valueCount++;
			values.add(it.next());
		}
		assertEquals(3, valueCount);
		assertEquals(new HashSet<String>(Arrays.asList("a", "c", "d")), values);
	}

	@Test
	public void numberConversion() {
		for (int i = -110000; i < 110000; i++) {
			float f = i;

			// Test integer conversion
			Number n = MetricTools.getNumber(f);
			if (i > -100000 && i < 100000) {
				assertTrue(n instanceof Integer);
				assertEquals(i, n.intValue());
			} else {
				assertTrue(n instanceof Float);
				assertEquals(Float.valueOf(i), n);
			}

			// Test "almost integers" remain floats
			n = MetricTools.getNumber(Math.nextAfter(f,
					Double.POSITIVE_INFINITY));
			assertTrue(n instanceof Float);

			n = MetricTools.getNumber(Math.nextAfter(f,
					Double.NEGATIVE_INFINITY));
			assertTrue(n instanceof Float);
		}
	}

	@Test
	public void emptyElementCheck() {
		assertTrue(MetricTools.isEmptyElement(""));
		assertFalse(MetricTools.isEmptyElement("xmi_id_5"));
		assertFalse(MetricTools.isEmptyElement(plainSet));
	}
}
