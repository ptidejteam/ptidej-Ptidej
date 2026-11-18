package padl.creator.classfile.assignment;

import java.util.Iterator;
import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.event.AnalysisEvent;
import padl.event.IModelListener;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IConstituent;
import padl.kernel.IContainer;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IPackage;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.util.adapter.ModelListenerAdapter;

/**
 * SOEN 6461, Deliverable 2, Question 1: creates a PADL model from the
 * class files of the Composite example and checks each pass of the creator.
 *
 * @author Ali Maher, Timéo Klein, and Vinicius Mioto
 */
public class TestPADLModelCreation extends TestCase {
	private static final String PATH = "target/test-classes/padl/example/composite1/";

	static ICodeLevelModel createModel(final IModelListener listener) throws CreationException {
		final ICodeLevelModel model = Factory.getInstance().createCodeLevelModel("Composite example");
		model.addModelListener(listener);
		model.create(new CompleteClassFileCreator(new String[] { PATH }, true));
		return model;
	}

	private ICodeLevelModel model;

	protected void setUp() throws CreationException {
		this.model = createModel(new ModelListenerAdapter());
	}

	// Pass 1: createPackages
	public void testPackages() {
		final IPackage padl = (IPackage) this.model.getConstituentFromID("padl");
		final IPackage example = (IPackage) padl.getConstituentFromID("example");
		assertNotNull(example.getConstituentFromID("composite1"));
	}

	// Pass 2: createEntities
	public void testEntities() {
		final IFirstClassEntity paragraph = this.model.getTopLevelEntityFromID("padl.example.composite1.Paragraph");
		final IFirstClassEntity indented = this.model.getTopLevelEntityFromID("padl.example.composite1.IndentedParagraph");
		final IFirstClassEntity element = this.model.getTopLevelEntityFromID("padl.example.composite1.Element");

		assertTrue(paragraph instanceof IClass);
		assertEquals(element, ((IClass) paragraph).getIteratorOnImplementedInterfaces().next());
		assertEquals(paragraph, indented.getIteratorOnInheritedEntities().next());
		assertTrue(this.model.getTopLevelEntityFromID("java.lang.Object") instanceof IGhost);
	}

	// Pass 3: createElements
	public void testElements() {
		final IFirstClassEntity document = this.model.getTopLevelEntityFromID("padl.example.composite1.Document");
		final IMethod printAll = (IMethod) document.getConstituentFromID("printAll()");
		assertTrue(printAll.getNumberOfConstituents(IMethodInvocation.class) > 0);
	}

	// Observer: the model notifies its listeners during the creation
	public void testEvents() throws CreationException {
		final int[] count = { 0 };
		createModel(new ModelListenerAdapter() {
			public void entityAnalyzed(final AnalysisEvent event) {
				count[0]++;
			}
		});
		assertTrue(count[0] > 0);
	}

	public static void main(final String[] args) throws CreationException {
		print(createModel(new ModelListenerAdapter()), "");
	}

	private static void print(final IContainer container, final String indent) {
		final Iterator iterator = container.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			final IConstituent constituent = (IConstituent) iterator.next();
			System.out.println(indent + "<" + constituent.getClass().getSimpleName() + "> "
					+ constituent.getDisplayID());
			if (constituent instanceof IContainer) {
				print((IContainer) constituent, indent + "  ");
			}
		}
	}
}
