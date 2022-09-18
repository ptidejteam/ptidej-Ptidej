package iwoca2017.test;

import junit.framework.TestCase;

public class FirstTest extends TestCase {
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}
	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
	public void test1() {
		int[] input = new int[] { 18, 2, 3, 25, 14, 6, 12, 27 };
		int[] expectedOutput = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
		int[] obtainedOutput;

		obtainedOutput = input;

		assertEquals(expectedOutput, obtainedOutput);
	}
	public void test2() {
	}
}
