/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
import junit.framework.Test;
import junit.framework.TestSuite;

public class RunAllUnitTests {
	
	public static Test suite ( ) {
		TestSuite suite= new TestSuite();
		suite.addTest(new TestSuite(ColumnsTest.class));
		suite.addTest(new TestSuite(TreeTest.class));
		suite.addTest(new TestSuite(GraphTest.class));
		
		return suite;
	}

	public static void main (String[] args) {
		junit.textui.TestRunner.run(suite());
		System.exit(0);
	}
}
