/*
 * (c) Copyright 2003-2006 Jean-Yves Guyomarc'h,
 * University of Montreal.
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
package padl.creator.javafile.javac.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import padl.creator.javafile.javac.test.simple.SanityTest;

public class TestCreatorJavaFileUsingJavaC extends TestSuite {
	public static Test suite() {
		final TestCreatorJavaFileUsingJavaC suite = new TestCreatorJavaFileUsingJavaC();
		suite.setName(TestCreatorJavaFileUsingJavaC.class.getName());

		suite.addTestSuite(SanityTest.class);

		return suite;
	}
}
