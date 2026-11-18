package padl.creator.classfile.assignment;

import junit.framework.TestCase;
import padl.alternativeVisitor.DfsIterator;
import padl.alternativeVisitor.JavaAlternativelyPatterenedGenerator;
import padl.alternativeVisitor.SkipIterator;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.IConstituent;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IGhost;
import padl.kernel.IPackageGhost;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.AbstractModel;
import padl.kernel.impl.Factory;
import padl.visitor.repository.JavaGenerator;

/**
 * SOEN 6461, Deliverable 2, Question 3: checks the self-traversing Visitor on
 * the PADL model of the Composite example.
 *
 * @author Ali Maher, Timéo Klein, and Vinicius Mioto
 */
public class TestAlternativeVisitor extends TestCase {
    private static final String PATH = "../PADL Creator ClassFile/target/test-classes/padl/example/composite1/";
    private ICodeLevelModel model;

    protected void setUp() throws CreationException {
        this.model = createModel();
    }

    private static ICodeLevelModel createModel() throws CreationException {
        final ICodeLevelModel model = Factory.getInstance().createCodeLevelModel("Composite example");
        model.create(new CompleteClassFileCreator(new String[] {PATH}, true));
        return model;
    }

    public void testSameCodeAsJavaGenerator() {
        final String expected = this.model.generate(new JavaGenerator());
        final String actual = ((AbstractModel) this.model).generateAlternative(new JavaAlternativelyPatterenedGenerator<>(new DfsIterator<>()));
        assertEquals(expected, actual);
    }

    public void testSkipGhosts() {
        final String code = ((AbstractModel) this.model).generateAlternative(new JavaAlternativelyPatterenedGenerator<>(new SkipIterator<>(new DfsIterator<>()) {
            protected boolean shouldKeep(final IConstituent aConstituent) {
                return !(aConstituent instanceof IGhost || aConstituent instanceof IPackageGhost);
            }
        }));
        assertTrue(code.contains("class Document"));
        assertFalse(code.contains(" ghost "));
    }

    public static void main(final String[] args) throws CreationException {
        System.out.println(((AbstractModel) createModel()).generateAlternative(new JavaAlternativelyPatterenedGenerator<>(new DfsIterator<>())));
    }
}
