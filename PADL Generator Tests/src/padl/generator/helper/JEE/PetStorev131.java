package padl.generator.helper.JEE;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.event.IModelListener;
import padl.generator.helper.ModelGenerator;
import padl.kernel.IIdiomLevelModel;
import padl.util.ModelStatistics;

public class PetStorev131 extends TestCase {
	public PetStorev131(final String aName) {
		super(aName);
	}
	public void testGenerateModelFromClassFiles() {
		final IModelListener statistics = new ModelStatistics();
		final IIdiomLevelModel idiomLevelModel =
			ModelGenerator
				.generateModelFromClassFilesDirectories(
					"PetStore v1.3.1",
					new String[] { "C:/Data/Java Programs/petstore1.3.1_02/src/bin/" },
					statistics);
		Assert.assertEquals(
			"Number of classes",
			502,
			idiomLevelModel.getNumberOfTopLevelEntities());
	}
}
