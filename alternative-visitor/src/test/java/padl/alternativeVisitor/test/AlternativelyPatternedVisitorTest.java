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
}
