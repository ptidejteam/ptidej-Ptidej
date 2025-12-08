package jct.test.listofunique;

import jct.util.collection.ListOfUnique;
import junit.framework.TestCase;

public class SanityTest extends TestCase {

	public void testInstantiation() {
		final ListOfUnique<String> lou = new ListOfUnique<>();
		TestCase.assertNotNull(lou);
	}

	public void testAddSimple() {
		final ListOfUnique<String> lou = new ListOfUnique<>();
		lou.add("1");
		lou.add("2");
		lou.add("3");
		TestCase.assertEquals(3, lou.size());
	}

	public void testAddSame() {
		final ListOfUnique<String> lou = new ListOfUnique<>();
		lou.add("1");
		lou.add("1");
		lou.add("1");
		TestCase.assertEquals(1, lou.size());
	}
}
