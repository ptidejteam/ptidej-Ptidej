package padl.generator.test.deliverable2;

import java.io.File;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import padl.generator.helper.ModelGenerator;
import padl.kernel.IClass;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;

/**
 * Demonstrates how PADL obtains a model from compiled Java class files.
 *
 * The helper delegates class-file discovery and bytecode analysis to
 * CompleteClassFileCreator.
 */
public class Question1ClassFileCreatorTest {
	@Test
	public void createsPadlModelFromCompiledClassFiles() {
		final File fixtureDirectory = new File(
				"target/test-classes/"
						+ "padl/generator/test/deliverable2/fixtures");

		final IIdiomLevelModel model = ModelGenerator
				.generateModelFromClassFilesDirectory(
						"Deliverable 2 Question 1",
						fixtureDirectory.getAbsolutePath() + File.separator);

		Assert.assertNotNull(model);

		System.out.println("PADL model: " + model.getDisplayName());
		System.out.println("Top-level entities: "
				+ model.getNumberOfTopLevelEntities());

		boolean foundAlpha = false;
		boolean foundBeta = false;
		final Iterator iterator = model.getIteratorOnTopLevelEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity entity = (IFirstClassEntity) iterator
					.next();
			System.out.println(entity.getDisplayID() + " ["
					+ entity.getClass().getSimpleName() + "]");
			foundAlpha |= entity.getDisplayID().endsWith(".Alpha");
			foundBeta |= entity.getDisplayID().endsWith(".Beta");
			if (entity instanceof IClass) {
				System.out.println("  members: "
						+ ((IClass) entity).getNumberOfConstituents());
			}
		}
		Assert.assertTrue(foundAlpha);
		Assert.assertTrue(foundBeta);
	}
}
