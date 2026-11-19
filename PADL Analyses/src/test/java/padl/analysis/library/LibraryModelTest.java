package padl.analysis.library;

import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.*;
import padl.kernel.impl.Factory;

public class LibraryModelTest extends TestCase {
    static ICodeLevelModel readLibrary() throws Exception {
        var model = Factory.getInstance().createCodeLevelModel("Library");
        String dir = "target/test-classes/library/";
        model.create(new CompleteClassFileCreator(new String[] {
                dir + "Book.class", dir + "EBook.class" }));
        return model;
    }

    public void testReadsInheritanceAndCall() throws Exception {
        var model = readLibrary();
        var book = model.getTopLevelEntityFromID("library.Book");
        var ebook = model.getTopLevelEntityFromID("library.EBook");
        assertTrue(book instanceof IClass);
        assertTrue(ebook instanceof IClass);
        assertSame(book, ebook.getInheritedEntityFromID(book.getID()));
        var preview = (IMethod) ebook.getConstituentFromName("previewPages");
        var pages = book.getConstituentFromName("pages");
        assertTrue(pages instanceof IMethod);
        var calls = preview.getIteratorOnConstituents(IMethodInvocation.class);
        while (calls.hasNext()) {
            var call = (IMethodInvocation) calls.next();
            if (call.getCalledMethod() == pages)
                return;
        }
        fail("Missing call from EBook.previewPages to Book.pages");
    }
}
