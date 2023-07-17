/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package padl.creator.classfile.test.method;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.classfile.LightClassFileCreator;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IOperation;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;

/**
 * Test for methods wrongly included in an IClass
 * after being parsed by the PADL Creator ClassFile.
 * 
 * @author Stephane Vaucher
 * @author Yann-Gaël Guéhéneuc
 * @since  2005/11/11
 */
public class TestMethodInclusion extends TestCase {
	private static final char[] CLASS_ID = "padl.test.method.B".toCharArray();
	private static final char[] METHOD_ID = "foo".toCharArray();

	public TestMethodInclusion(final String aName) {
		super(aName);
	}
	public void setUp() throws Exception {
	}
	public void testInclusionCompiledIn1p3() throws CreationException {
		final ICodeLevelModel codeLevelModel =
			Factory.getInstance().createCodeLevelModel("");
		codeLevelModel
			.create(new LightClassFileCreator(
				new String[] { "../PADL Creator ClassFile Tests/rsc/CompiledIn1.4with1.1classfile/padl/test/method/B.class" }));

		final boolean elementsExist =
			((IClass) codeLevelModel
				.getTopLevelEntityFromID(TestMethodInclusion.CLASS_ID))
				.getNumberOfConstituents() > 0;
		Assert.assertTrue("Elements exist", elementsExist);

		final IOperation method =
			(IOperation) ((IClass) codeLevelModel
				.getTopLevelEntityFromID(TestMethodInclusion.CLASS_ID))
				.getConstituentFromName(TestMethodInclusion.METHOD_ID);

		Assert
			.assertNull(
				"Method '"
						+ String.valueOf(METHOD_ID)
						+ "' of class  '"
						+ String.valueOf(CLASS_ID)
						+ "' is included through the inheritance mecanism wrongly.",
				method);
	}
	public void testInclusionCompiledIn1p4() throws CreationException {
		final ICodeLevelModel codeLevelModel =
			Factory.getInstance().createCodeLevelModel("");
		codeLevelModel
			.create(new LightClassFileCreator(
				new String[] { "../PADL Creator ClassFile Tests/rsc/CompiledIn1.4/padl/test/method/B.class" }));

		final boolean elementsExist =
			((IClass) codeLevelModel
				.getTopLevelEntityFromID(TestMethodInclusion.CLASS_ID))
				.getNumberOfConstituents() > 0;
		Assert.assertTrue("Elements exist", elementsExist);

		final IOperation method =
			(IOperation) ((IClass) codeLevelModel
				.getTopLevelEntityFromID(TestMethodInclusion.CLASS_ID))
				.getConstituentFromName(TestMethodInclusion.METHOD_ID);

		Assert
			.assertNull(
				"Method '"
						+ String.valueOf(METHOD_ID)
						+ "' of class  '"
						+ String.valueOf(CLASS_ID)
						+ "' is included through the inheritance mecanism wrongly.",
				method);
	}
	public void testInclusionCompiledIn1p4with1p1classfile()
			throws CreationException {

		final ICodeLevelModel codeLevelModel =
			Factory.getInstance().createCodeLevelModel("");
		codeLevelModel
			.create(new LightClassFileCreator(
				new String[] { "../PADL Creator ClassFile Tests/rsc/CompiledIn1.4with1.1classfile/padl/test/method/B.class" }));

		final boolean elementsExist =
			((IClass) codeLevelModel
				.getTopLevelEntityFromID(TestMethodInclusion.CLASS_ID))
				.getNumberOfConstituents() > 0;
		Assert.assertTrue("Elements exist", elementsExist);

		final IOperation method =
			(IOperation) ((IClass) codeLevelModel
				.getTopLevelEntityFromID(TestMethodInclusion.CLASS_ID))
				.getConstituentFromName(TestMethodInclusion.METHOD_ID);

		Assert
			.assertNull(
				"Method '"
						+ String.valueOf(METHOD_ID)
						+ "' of class  '"
						+ String.valueOf(CLASS_ID)
						+ "' is included through the inheritance mecanism wrongly.",
				method);
	}
	public void testInclusionCompiledIn1p4with1p2classfile()
			throws CreationException {

		final ICodeLevelModel codeLevelModel =
			Factory.getInstance().createCodeLevelModel("");
		codeLevelModel
			.create(new LightClassFileCreator(
				new String[] { "../PADL Creator ClassFile Tests/rsc/CompiledIn1.4with1.2classfile/padl/test/method/B.class" }));

		final boolean elementsExist =
			((IClass) codeLevelModel
				.getTopLevelEntityFromID(TestMethodInclusion.CLASS_ID))
				.getNumberOfConstituents() > 0;
		Assert.assertTrue("Elements exist", elementsExist);

		final IOperation method =
			(IOperation) ((IClass) codeLevelModel
				.getTopLevelEntityFromID(TestMethodInclusion.CLASS_ID))
				.getConstituentFromName(TestMethodInclusion.METHOD_ID);

		Assert
			.assertNull(
				"Method '"
						+ String.valueOf(METHOD_ID)
						+ "' of class  '"
						+ String.valueOf(CLASS_ID)
						+ "' is included through the inheritance mecanism wrongly.",
				method);
	}
	public void testInclusionCompiledIn1p4with1p3classfile()
			throws CreationException {

		final ICodeLevelModel codeLevelModel =
			Factory.getInstance().createCodeLevelModel("");
		codeLevelModel
			.create(new LightClassFileCreator(
				new String[] { "../PADL Creator ClassFile Tests/rsc/CompiledIn1.4with1.3classfile/padl/test/method/B.class" }));

		final boolean elementsExist =
			((IClass) codeLevelModel
				.getTopLevelEntityFromID(TestMethodInclusion.CLASS_ID))
				.getNumberOfConstituents() > 0;
		Assert.assertTrue("Elements exist", elementsExist);

		final IOperation method =
			(IOperation) ((IClass) codeLevelModel
				.getTopLevelEntityFromID(TestMethodInclusion.CLASS_ID))
				.getConstituentFromName(TestMethodInclusion.METHOD_ID);

		Assert
			.assertNull(
				"Method '"
						+ String.valueOf(METHOD_ID)
						+ "' of class  '"
						+ String.valueOf(CLASS_ID)
						+ "' is included through the inheritance mecanism wrongly.",
				method);
	}
}
