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
package padl.creator.classfile.test.innerclasses;

import org.junit.Assert;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.classfile.test.ClassFilePrimitive;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMemberGhost;
import padl.kernel.exception.CreationException;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2005/08/14
 */
public class TestInnerClasses extends ClassFilePrimitive {
	private IFirstClassEntity testedClass;
	private ICodeLevelModel testedModel;

	public TestInnerClasses(final String aName) {
		super(aName);
	}
	protected void setUp() throws CreationException {
		this.testedModel =
			ClassFilePrimitive.getFactory().createCodeLevelModel(
				"ptidej.example.innerclasses2");
		this.testedModel
			.create(new CompleteClassFileCreator(
				new String[] {
						"../PADL Creator ClassFile Tests/rsc/InternalClass/org/argouml/language/java/generator/GeneratorJava.class",
						"../PADL Creator ClassFile Tests/rsc/InternalClass/org/argouml/language/java/generator/GeneratorJava$1$TagExtractor.class" }));

		this.testedClass =
			(IFirstClassEntity) this.testedModel
				.getTopLevelEntityFromID("org.argouml.language.java.generator.GeneratorJava");
	}
	public void testMemberClass() {
		int numMembers =
			this.testedClass.getNumberOfConstituents(IMemberGhost.class);

		Assert.assertEquals(
			"Number of members contained should be ",
			0,
			numMembers);

		//    TestCase.assertNotNull("Could not find member class 'TagExtractor'", 
		//            this.testedClass.getConstituentFromID("TagExtractor"));
	}
}
