package padl.analysis.assignment;

import junit.framework.TestCase;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.plantUMLGenerator.PlantUMLGenerator;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.IAbstractModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;

/**
 * SOEN 6461, Deliverable 2, Question 2: applies the PlantUMLGenerator visitor
 * on the PADL model of the Composite example.
 *
 * @author Ali Maher, Timéo Klein, and Vinicius Mioto
 */
public class TestPlantUMLGenerator extends TestCase {
	private static final String PATH = "../PADL Creator ClassFile/target/test-classes/padl/example/composite1/";

	static String generateCode() throws CreationException, UnsupportedSourceModelException {
		final ICodeLevelModel model = Factory.getInstance().createCodeLevelModel("Composite example");
		model.create(new CompleteClassFileCreator(new String[] { PATH }, true));
		final IAbstractModel idiomModel = new AACRelationshipsAnalysis().invoke(model);
		return idiomModel.generate(new PlantUMLGenerator());
	}

	private String code;

	protected void setUp() throws CreationException, UnsupportedSourceModelException {
		this.code = generateCode();
	}

	// open(IClass), open(IInterface), and close(model)
	public void testClassesAndInterfaces() {
		assertTrue(this.code.trim().startsWith("@startuml"));
		assertTrue(this.code.trim().endsWith("@enduml"));
		assertTrue(this.code.contains("class Document {"));
		assertTrue(this.code.contains("interface Element {"));
	}

	// open(IClass) buffers inheritance and implementation
	public void testInheritanceAndImplementation() {
		assertTrue(this.code.contains("Paragraph --^ IndentedParagraph"));
		assertTrue(this.code.contains("Element ..^ Paragraph"));
	}

	// visit(IAssociation) and visit(IAggregation)
	public void testAssociations() {
		assertTrue(this.code.contains("Main -- Document"));
		assertTrue(this.code.contains("Document o-- Vector : aggregation"));
	}

	public static void main(final String[] args) throws CreationException, UnsupportedSourceModelException {
		System.out.println(generateCode());
	}
}
