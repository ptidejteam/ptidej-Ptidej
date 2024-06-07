package padl.analysis.plantUMLGenerator.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import padl.analysis.plantUMLGenerator.test.Test1;

/**
 * @author Vishnu Rameshbabu
 * @since 2024/05/10
 */
public final class TestPackageBuilder
extends junit.framework.TestSuite {

public TestPackageBuilder() {
}
public TestPackageBuilder(final Class theClass) {
	super(theClass);
}
public TestPackageBuilder(final String name) {
	super(name);
}
public static Test suite() {
	final TestPackageBuilder suite =
		new TestPackageBuilder();
	suite.addTest(new TestSuite(Test1.class));
	return suite;
}
}