package padl.creator.classfile.test.syntheticBridge;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.classfile.test.ClassFilePrimitive;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IElement;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.util.Util;

/**
 * Tests the filtering of synthetic/bridge methods.
 * These methods may be included in a classfile for
 * type erasure/accessibility reasons.
 * @author Luca Scistri
 */
public class TestSyntheticBridge extends TestCase {
	private static IElement[] ElementsNonGenericImpl = null;
	private static IElement[] ElementsPublicSubClass = null;

	public TestSyntheticBridge(final String aName) {
		super(aName);
	}

	protected void setUp()
			throws CreationException, UnsupportedSourceModelException {
		final ICodeLevelModel codeLevelModel = ClassFilePrimitive.getFactory()
				.createCodeLevelModel("ptidej.example.syntheticBridge");
		codeLevelModel.create(new CompleteClassFileCreator(new String[] {
				"../PADL Creator ClassFile/target/test-classes/padl/example/syntheticBridge/NonGenericImplementation.class",
				"../PADL Creator ClassFile/target/test-classes/padl/example/syntheticBridge/PublicSubClass.class" }));

		final IIdiomLevelModel idiomLevelModel = (IIdiomLevelModel) new AACRelationshipsAnalysis()
				.invoke(codeLevelModel);

		final IFirstClassEntity[] firstClassEntities = Util
				.getArrayOfTopLevelEntities(idiomLevelModel);

		TestSyntheticBridge.ElementsNonGenericImpl = Util
				.getArrayOfElements(firstClassEntities[3]);
		TestSyntheticBridge.ElementsPublicSubClass = Util
				.getArrayOfElements(firstClassEntities[5]);

	}

	// The synthetic/bridge methods should be filtered out during model creation
	public void testNonGenericImpl() {
		Assert.assertEquals("Number of elements", 4,
				TestSyntheticBridge.ElementsNonGenericImpl.length);
	}

	public void testPublicSubClass() {
		Assert.assertEquals("Number of elements", 2,
				TestSyntheticBridge.ElementsPublicSubClass.length);
	}

}