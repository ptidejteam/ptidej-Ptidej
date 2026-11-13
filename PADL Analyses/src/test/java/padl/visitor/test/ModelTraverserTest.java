package padl.visitor.test;

import org.junit.Assert;

import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import padl.analysis.plantUMLGenerator.PlantUMLGenerator;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IClass;
import padl.kernel.IConstructor;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.kernel.IPackage;
import padl.kernel.impl.Factory;
import padl.visitor.BreadthFirstModelTraverser;
import padl.visitor.EmptyVisitor;
import padl.visitor.ModelTraverser;

/**
 * Tests that the self-traversing Visitor variant produces the same results (visits) as the original
 *
 * @author Vinisha Manek
 * @since 2026/11/10
 */
public class ModelTraverserTest extends TestCase {
	private static final String PATH =
		"../PADL Analyses/target/test-classes/padl/test/";

	public ModelTraverserTest(final String aName) {
		super(aName);
	}

	/**
	 * On a model built by hand with the Factory: a package containing a class with a method,  
	 * visited through the accept() methods and through the ModelTraverser
	 */
	public void testSameOutputOnFactoryBuiltModel() {
		final char[] className = "C".toCharArray();
		final IClass aClass =
			Factory.getInstance().createClass(className, className);
		final IMethod aMethod = Factory.getInstance()
			.createMethod("m".toCharArray(), "m".toCharArray());
		aClass.addConstituent(aMethod);

		final IPackage aPackage =
			Factory.getInstance().createPackage("p".toCharArray());
		aPackage.addConstituent((IFirstClassEntity) aClass);

		final ICodeLevelModel aModel = Factory.getInstance()
			.createCodeLevelModel("Model for ModelTraverserTest");
		aModel.addConstituent(aPackage);

		final String expectedCode =
			aModel.generate(new PlantUMLGenerator());
		final String actualCode =
			new ModelTraverser(new PlantUMLGenerator()).generate(aModel);

		Assert.assertEquals(expectedCode, actualCode);
	}

	/**
	 * Checks that the dispatch calls the most specific overload for operations
	 */
	public void testMethodsAreNotDispatchedAsConstructors() {
		final char[] className = "C".toCharArray();
		final IClass aClass =
			Factory.getInstance().createClass(className, className);
		aClass.addConstituent(Factory.getInstance()
			.createConstructor("<init>".toCharArray(), "C".toCharArray()));
		aClass.addConstituent(Factory.getInstance()
			.createMethod("m".toCharArray(), "m".toCharArray()));

		final ICodeLevelModel aModel = Factory.getInstance()
			.createCodeLevelModel("Model for ModelTraverserTest");
		aModel.addConstituent((IFirstClassEntity) aClass);

		final List calls = new ArrayList();
		new ModelTraverser(new EmptyVisitor() {
			public void open(final IConstructor aConstructor) {
				calls.add("open(IConstructor) "
						+ String.valueOf(aConstructor.getName()));
			}
			public void open(final IMethod aMethod) {
				calls.add("open(IMethod) "
						+ String.valueOf(aMethod.getName()));
			}
		}).traverse(aModel);

		Assert.assertTrue(
			calls.contains("open(IConstructor) C"));
		Assert.assertTrue(
			calls.contains("open(IMethod) m"));
		Assert.assertFalse(
			calls.contains("open(IConstructor) m"));
	}

	/**
	 * On a model created by the CompleteClassFileCreator from the compiled test classes.
	 * The ModelTraverser runs first on purpose: Package.accept() sorts a package in place,
	 * so running accept() first would hand the traverser an already reordered model and weaken the comparison
	 */
	public void testSameOutputOnClassFileModel() throws Exception {
		final ICodeLevelModel aModel = Factory.getInstance()
			.createCodeLevelModel("Model for ModelTraverserTest");
		aModel.create(new CompleteClassFileCreator(
			new String[] { ModelTraverserTest.PATH }, true));

		final String actualCode =
			new ModelTraverser(new PlantUMLGenerator()).generate(aModel);
		final String expectedCode =
			aModel.generate(new PlantUMLGenerator());

		Assert.assertEquals(expectedCode, actualCode);
	}

	/**
	 * Shows that the traversal strategy is now replaceable, which accept() does not allow.
	 * On the model p { A { m }, B }, depth-first opens m before B, while breadth-first
	 * opens every entity of a level before their constituents, so B before m.
	 * The same recording visitor is driven both ways, unchanged
	 */
	public void testBreadthFirstTraversalVisitsLevelByLevel() {
		final char[] nameA = "A".toCharArray();
		final IClass classA =
			Factory.getInstance().createClass(nameA, nameA);
		classA.addConstituent(Factory.getInstance()
			.createMethod("m".toCharArray(), "m".toCharArray()));
		final char[] nameB = "B".toCharArray();
		final IClass classB =
			Factory.getInstance().createClass(nameB, nameB);

		final IPackage aPackage =
			Factory.getInstance().createPackage("p".toCharArray());
		aPackage.addConstituent((IFirstClassEntity) classA);
		aPackage.addConstituent((IFirstClassEntity) classB);

		final ICodeLevelModel aModel = Factory.getInstance()
			.createCodeLevelModel("Model for ModelTraverserTest");
		aModel.addConstituent(aPackage);

		final List depthFirstOpens = new ArrayList();
		new ModelTraverser(
			new OpenRecorder(depthFirstOpens)).traverse(aModel);
		final List breadthFirstOpens = new ArrayList();
		new BreadthFirstModelTraverser(
			new OpenRecorder(breadthFirstOpens)).traverse(aModel);

		Assert.assertEquals(
			Arrays.asList("p", "A", "m", "B"), depthFirstOpens);
		Assert.assertEquals(
			Arrays.asList("p", "A", "B", "m"), breadthFirstOpens);
	}

	/**
	 * Records the names of the opened packages, classes, and methods in order.
	 */
	private static class OpenRecorder extends EmptyVisitor {
		private final List opens;

		OpenRecorder(final List someOpens) {
			this.opens = someOpens;
		}
		public void open(final IPackage aPackage) {
			this.opens.add(String.valueOf(aPackage.getName()));
		}
		public void open(final IClass aClass) {
			this.opens.add(String.valueOf(aClass.getName()));
		}
		public void open(final IMethod aMethod) {
			this.opens.add(String.valueOf(aMethod.getName()));
		}
	}
}
