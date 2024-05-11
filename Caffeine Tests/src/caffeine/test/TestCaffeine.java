/*
 * (c) Copyright 2002-2003 Yann-Gaël Guéhéneuc,
 * ecole des Mines de Nantes and Object Technology International, Inc.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the author, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package caffeine.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public final class TestCaffeine extends junit.framework.TestSuite {
	public TestCaffeine() {
		super();
	}
	public TestCaffeine(final Class theClass) {
		super(theClass);
	}
	public TestCaffeine(final String name) {
		super(name);
	}
	public static Test suite() {
		final TestCaffeine suite = new TestCaffeine();

			suite.addTest(new TestSuite(
				caffeine.test.aggregation3.CaffeineLauncher.class));
			suite
				.addTest(new TestSuite(caffeine.test.array.CaffeineLauncher.class));
			suite.addTest(new TestSuite(
				caffeine.test.association1.CaffeineLauncher.class));
			suite.addTest(new TestSuite(
				caffeine.test.association2.CaffeineLauncher.class));
			suite.addTest(new TestSuite(
				caffeine.test.collection.CaffeineLauncher.class));
			suite.addTest(new TestSuite(
				caffeine.test.composition1.CaffeineLauncher.class));
			suite.addTest(new TestSuite(
				caffeine.test.composition2.CaffeineLauncher.class));
		suite.addTest(new TestSuite(
			caffeine.test.composition3.CaffeineLauncher.class));
		suite.addTest(new TestSuite(
			caffeine.test.composition4.CaffeineLauncher.class));
			suite.addTest(new TestSuite(
				caffeine.test.composition5.CaffeineLauncher.class));
			suite.addTest(new TestSuite(
				caffeine.test.composition6.counterexample.CaffeineLauncher.class));
			suite.addTest(new TestSuite(
				caffeine.test.composition7.counterexample.CaffeineLauncher.class));
			suite.addTest(new TestSuite(
				caffeine.test.composition8.counterexample.CaffeineLauncher.class));
			suite.addTest(new TestSuite(
				caffeine.test.composition9.CaffeineLauncher.class));
			suite.addTest(new TestSuite(
				caffeine.test.interactiondiagram1.CaffeineLauncher.class));
			suite.addTest(new TestSuite(
				caffeine.test.interactiondiagram2.CaffeineLauncher.class));
			suite.addTest(new TestSuite(
				caffeine.test.multithreading1.CaffeineLauncher.class));
			suite.addTest(new TestSuite(
				caffeine.test.multithreading2.CaffeineLauncher.class));
			suite.addTest(new TestSuite(
				caffeine.test.nextEvent2.CaffeineLauncher.class));
			suite.addTest(new TestSuite(
				caffeine.test.nextEvent3.CaffeineLauncher.class));

		return suite;
	}
}
