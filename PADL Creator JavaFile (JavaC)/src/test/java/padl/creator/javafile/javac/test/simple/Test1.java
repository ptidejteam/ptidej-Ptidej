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

import java.util.Iterator;
import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.javafile.javac.JavaFileCreator;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IConstructor;
import padl.kernel.IFirstClassEntity;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import util.io.Files;

public class Test1 extends TestCase {
	private static final String PATH =
		"../PADL Creator JavaFile (Eclipse)/target/test-classes/";
	private static final String FILE = Test1.PATH + "CreatorJava.java";

	private static ICodeLevelModel CodeLevelModel;

	public Test1(final String name) {
		super(name);
	}
	protected void setUp() {
		if (Test1.CodeLevelModel == null) {
			try {
				Test1.CodeLevelModel =
					Factory.getInstance().createCodeLevelModel("");
				Test1.CodeLevelModel.create(new JavaFileCreator(
					Test1.PATH,
					new String[] { Test1.FILE }));
			}
			catch (final CreationException e) {
				e.printStackTrace();
			}
		}
	}
	public void testA() {
		Assert.assertEquals(
			"Number of classes",
			1,
			Test1.CodeLevelModel.getNumberOfTopLevelEntities(IClass.class));
	}
	public void testB() {
		IFirstClassEntity entity = null;
		final Iterator<?> iterator =
			Test1.CodeLevelModel.getIteratorOnTopLevelEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity e = (IFirstClassEntity) iterator.next();
			if (e instanceof IClass) {
				entity = e;
				break;
			}
		}
		Assert.assertNotNull("Class CreatorJava does not exist", entity);
		Assert.assertEquals(
			"Class CreatorJava name",
			"CreatorJava",
			entity.getDisplayName());
		Assert
			.assertEquals(
				"Class CreatorJava comment",
				"*\n * This class is used to create a PADL Model, from a set of Java Source Files, using JCT.\n *\n * @author Mathieu Lemoine\n ",
				entity.getComment());
	}
	public void testC() {
		IFirstClassEntity entity = null;
		final Iterator<?> iterator =
			Test1.CodeLevelModel.getIteratorOnTopLevelEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity e = (IFirstClassEntity) iterator.next();
			if (e instanceof IClass) {
				entity = e;
				break;
			}
		}
		final IConstructor constructor =
			(IConstructor) entity.getConstituentFromID(new char[] { '<', 'i',
					'n', 'i', 't', '>', '(', 'j', 'a', 'v', 'a', 'x', '.', 't',
					'o', 'o', 'l', 's', '.', 'D', 'i', 'a', 'g', 'n', 'o', 's',
					't', 'i', 'c', 'L', 'i', 's', 't', 'e', 'n', 'e', 'r', ',',
					' ', 'j', 'a', 'v', 'a', '.', 'l', 'a', 'n', 'g', '.', 'I',
					't', 'e', 'r', 'a', 'b', 'l', 'e', ',', ' ', 'L', 'j', 'a',
					'v', 'a', '.', 'i', 'o', '.', 'F', 'i', 'l', 'e', ')' });
		Assert.assertNotNull("Constructor does not exist", constructor);
		Assert
			.assertEquals(
				"Constructor comment",
				"*\n	 * @param files List of Path to each of the java source file want to put in the PADL Model.\n	 * @param diag DiagnosticListener used to report error during compilation pass.\n	 * @param options Options to pass to JavaC, splited as in a command line.\n	 ",
				constructor.getComment());
	}
}
