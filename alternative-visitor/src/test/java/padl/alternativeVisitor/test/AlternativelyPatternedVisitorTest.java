package padl.alternativeVisitor.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import padl.alternativeVisitor.JavaAlternativelyPatterenedGenerator;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.AbstractModel;
import padl.kernel.impl.Factory;
import padl.visitor.repository.JavaGenerator;

import padl.kernel.IClass;
import padl.kernel.IMethod;
import padl.visitor.EmptyVisitor;
import padl.visitor.IGenerator;

public class AlternativelyPatternedVisitorTest {

	private static final String CLASS_PATH =
		"../PADL/target/classes/padl/kernel/impl/Factory.class";

	private static ICodeLevelModel codeLevelModel;

	@BeforeClass
	public static void createModel() {
		final ICodeLevelModelCreator creator =
			new CompleteClassFileCreator(new String[] { CLASS_PATH });

		try {
			codeLevelModel =
				Factory.getInstance().createCodeLevelModel("Test Model");
			codeLevelModel.create(creator);
		}
		catch (final CreationException e) {
			fail("Could not create the code-level model: " + e.getMessage());
		}
	}

	@Test
	public void modelIsCreated() {
		assertNotNull(codeLevelModel);
		assertTrue(
			"Model should contain at least one top-level entity",
			codeLevelModel.getNumberOfTopLevelEntities() > 0);
	}

	@Test
	public void alternativeGeneratorProducesSameCodeAsJavaGenerator() {
		final JavaGenerator javaGenerator = new JavaGenerator();
		final JavaAlternativelyPatterenedGenerator alternativeGenerator =
			new JavaAlternativelyPatterenedGenerator();

		codeLevelModel.generate(javaGenerator);
		((AbstractModel)codeLevelModel).generateAlternative(alternativeGenerator);

		assertEquals(
			"Both generators should produce identical code",
			javaGenerator.getCode(),
			alternativeGenerator.getCode());
	}

	public static class CountingGenerator extends EmptyVisitor implements IGenerator {
		public int classes = 0;
		public int methods = 0;

		@Override
		public void open(final IClass aClass) {
			this.classes++;
		}

		@Override
		public void open(final IMethod aMethod) {
			this.methods++;
		}

		public String getCode() {
			return this.classes + " classes, " + this.methods + " methods";
		}
	}

	@Test
	public void ownTraversalVisitsSameElementsAsAccept() {
		final CountingGenerator viaAccept = new CountingGenerator();
		codeLevelModel.generate(viaAccept);

		final CountingGenerator viaOwn = new CountingGenerator();
		((AbstractModel) codeLevelModel).generateAlternative(
				new JavaAlternativelyPatterenedGenerator(viaOwn));

		assertTrue(viaAccept.classes > 0);
		assertEquals(viaAccept.classes, viaOwn.classes);
		assertEquals(viaAccept.methods, viaOwn.methods);
	}

	@Test
	public void multiRunTest() {
		final JavaAlternativelyPatterenedGenerator generator =
				new JavaAlternativelyPatterenedGenerator();
		((AbstractModel) codeLevelModel).generateAlternative(generator);
		final String first = generator.getCode();
		((AbstractModel) codeLevelModel).generateAlternative(generator);
		assertEquals(first, generator.getCode());
	}
}
