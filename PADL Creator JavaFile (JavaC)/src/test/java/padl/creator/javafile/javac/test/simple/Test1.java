/* (c) Copyright 2001 and following years, Yann-Gaël Guéhéneuc,
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
package padl.creator.javafile.javac.test.simple;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.creator.javafile.javac.JavaFileCreator;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IConstructor;
import padl.kernel.IFirstClassEntity;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;

public class Test1 extends TestCase {
	private static final String PATH = "../PADL Creator JavaFile (JavaC)/target/test-classes/";
	private static final String FILE = Test1.PATH + "CreatorJava.java";
	private static final String TEST_B = "*\n * This class is used to create a PADL Model, from a set of Java Source Files, using JCT.\n *\n * @author Mathieu Lemoine\n ";
	private static final String TEST_C = "*\n	 * @param files List of Path to each of the java source file want to put in the PADL Model.\n	 * @param diag DiagnosticListener used to report error during compilation pass.\n	 * @param options Options to pass to JavaC, splited as in a command line.\n	 ";

	private static ICodeLevelModel CodeLevelModel;

	public Test1(final String name) {
		super(name);
	}

	protected void setUp() {
		if (Test1.CodeLevelModel == null) {
			try {
				Test1.CodeLevelModel = Factory.getInstance().createCodeLevelModel("");
				Test1.CodeLevelModel.create(new JavaFileCreator(Test1.PATH, new String[] { Test1.FILE }));
			} catch (final CreationException e) {
				e.printStackTrace();
			}
		}
	}

	public void testA() {
		Assert.assertEquals("Number of classes", 1, Test1.CodeLevelModel.getNumberOfTopLevelEntities(IClass.class));
	}

	public void testB() {
		final IFirstClassEntity entity = Test1.CodeLevelModel.getTopLevelEntityFromID("padl.creator.CreatorJava");
		Assert.assertNotNull("Class CreatorJava does not exist", entity);
		Assert.assertEquals("Class CreatorJava name", "CreatorJava", entity.getDisplayName());
		final String comment = entity.getComment().replaceAll("\\r", "").replaceAll("\\n", "");
		Assert.assertEquals("Class CreatorJava comment", Test1.TEST_B.replaceAll("\\r", "").replaceAll("\\n", ""),
				comment);
	}

	public void testC() {
		final IFirstClassEntity entity = Test1.CodeLevelModel.getTopLevelEntityFromID("padl.creator.CreatorJava");
		final IConstructor constructor = (IConstructor) entity
				.getConstituentFromID("<init>(javax.tools.DiagnosticListener, java.lang.Iterable, Ljava.io.File)");
		Assert.assertNotNull("Constructor does not exist", constructor);
		final String comment = constructor.getComment().replaceAll("\\r", "").replaceAll("\\n", "");
		Assert.assertEquals("Constructor comment", Test1.TEST_C.replaceAll("\\r", "").replaceAll("\\n", ""), comment);
	}
}
