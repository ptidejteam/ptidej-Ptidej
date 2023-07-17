package com.sdmetrics.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class TestHashMultiSet {

	HashMultiSet<String> set;

	@Before
	public void initTestSet() {
		set = new HashMultiSet<String>(4);
		set.add(null);
		set.add("b");
		set.add("b");
		set.add("c");
		set.add("c");
		set.add("c");
	}

	@Test
	public void contents() {
		assertTrue(set.contains("b"));
		assertFalse(set.contains("d"));

		assertEquals(0, set.getElementCount("0"));
		assertEquals(1, set.getElementCount(null));
		assertEquals(2, set.getElementCount("b"));
		assertEquals(3, set.getElementCount("c"));

		assertFalse(set.isEmpty());
		assertEquals(6, set.size());
		assertEquals(3, set.flatSetSize());
	}

	@Test
	public void iteration() {
		Iterator<String> it = set.iterator();
		int nullCount = 0;
		int bCount = 0;
		int cCount = 0;
		int total = 0;
		while (it.hasNext()) {
			String s = it.next();
			if (s == null)
				nullCount++;
			if ("b".equals(s))
				bCount++;
			if ("c".equals(s))
				cCount++;
			total++;
		}
		assertEquals(6, total);
		assertEquals(1, nullCount);
		assertEquals(2, bCount);
		assertEquals(3, cCount);

		try {
			it.next();
			fail("NoSuchElementException expected");
		} catch (NoSuchElementException ex) {
			// passes the test
		}
	}

	@Test
	public void flatIteration() {
		Iterator<String> it = set.getFlatIterator();
		int nullCount = 0;
		int bCount = 0;
		int cCount = 0;
		int total = 0;
		while (it.hasNext()) {
			String s = it.next();
			if (s == null)
				nullCount++;
			if ("b".equals(s))
				bCount++;
			if ("c".equals(s))
				cCount++;
			total++;
		}
		assertEquals(3, total);
		assertEquals(1, nullCount);
		assertEquals(1, bCount);
		assertEquals(1, cCount);

		try {
			it.next();
			fail("NoSuchElementException expected");
		} catch (NoSuchElementException ex) {
			// passes the test
		}
	}

	@Test(expected = UnsupportedOperationException.class)
	public void iteratorRemove() {
		set.iterator().remove();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void flatIteratorRemove() {
		set.getFlatIterator().remove();
	}

	@Test
	public void adding() {
		assertTrue(set.add("c"));
		assertTrue(set.add("d"));
		assertEquals(8, set.size());
		assertEquals(4, set.flatSetSize());
	}

	@Test
	public void addAll() {
		HashMultiSet<String> newSet = new HashMultiSet<String>(set);
		assertEquals(1, newSet.getElementCount(null));
		assertEquals(2, newSet.getElementCount("b"));
		assertEquals(3, newSet.getElementCount("c"));

		assertEquals(6, newSet.size());
		assertEquals(3, newSet.flatSetSize());

		newSet.addAll(set);
		assertEquals(4, newSet.getElementCount("b"));
		assertEquals(12, newSet.size());
		assertEquals(3, newSet.flatSetSize());
	}

	@Test
	public void removingOne() {

		assertTrue(set.remove("b"));
		assertEquals(1, set.getElementCount("b"));
		assertTrue(set.contains("b"));

		assertTrue(set.remove("b"));
		assertEquals(0, set.getElementCount("b"));
		assertFalse(set.contains("b"));

		assertEquals(4, set.size());
		assertEquals(2, set.flatSetSize());

		assertFalse(set.remove("d"));
	}

	@Test
	public void removingAny() {
		set.removeAny("b");
		assertEquals(4, set.size());
		assertEquals(2, set.flatSetSize());

		set.removeAny("b");
		assertEquals(4, set.size());
		assertEquals(2, set.flatSetSize());
	}

	@Test
	public void removingAll() {
		assertFalse(set.removeAll(Arrays.asList("d", "d", "e")));
		assertEquals(6, set.size());

		assertTrue(set.removeAll(new HashMultiSet<String>(Arrays.asList("d",
				"b", "b", "b", "c"))));
		assertEquals(3, set.size());
		assertEquals(2, set.flatSetSize());

		assertTrue(set.removeAll(set));
		assertTrue(set.isEmpty());
	}

	@Test
	public void retainAll() {
		HashMultiSet<String> list = new HashMultiSet<String>(Arrays.asList("b",
				"b", "b", "c", "d", "d"));
		set.retainAll(list);

		assertEquals(0, set.getElementCount(null));
		assertEquals(2, set.getElementCount("b"));
		assertEquals(1, set.getElementCount("c"));
		assertEquals(0, set.getElementCount("d"));
	}

	@Test
	public void clearout() {
		set.clear();
		assertTrue(set.isEmpty());
		assertFalse(set.contains(null));
		assertEquals(0, set.size());
		assertEquals(0, set.flatSetSize());
	}

	@Test
	public void comparisons() {
		assertFalse(set.equals(null));
		assertTrue(set.equals(set));
		assertFalse(set.equals("a,b,c"));
		assertTrue(new HashMultiSet<String>(16).equals(new ArrayList<Float>()));

		assertFalse(set.equals(Arrays.asList(null, "b", "c")));
		assertFalse(set.equals(Arrays.asList(null, "b", "c", "d", "e", "f")));
		assertFalse(set.equals(Arrays.asList(null, null, null, "b", "b", "c")));
		assertTrue(set.equals(Arrays.asList(null, "b", "b", "c", "c", "c")));
	}

	@Test
	public void hashing() {
		HashMultiSet<Object> empty = new HashMultiSet<Object>(1);
		HashMultiSet<String> equalSet = new HashMultiSet<String>(Arrays.asList(
				null, "b", "b", "c", "c", "c"));
		HashMultiSet<String> otherSet = new HashMultiSet<String>(Arrays.asList(
				null, "b", "b", "b", "c", "c"));

		assertEquals(0, empty.hashCode());
		assertEquals(set.hashCode(), equalSet.hashCode());
		assertTrue(set.hashCode() != otherSet.hashCode());
	}
}
