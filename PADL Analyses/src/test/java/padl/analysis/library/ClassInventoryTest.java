package padl.analysis.library;

import java.lang.reflect.Proxy;
import java.util.List;
import junit.framework.TestCase;
import padl.analysis.inventory.ClassInventory;
import padl.kernel.IPackage;
import padl.kernel.impl.Factory;

public class ClassInventoryTest extends TestCase {
    public void testLibraryAndReuse() throws Exception {
        var model = LibraryModelTest.readLibrary();
        var visitor = new ClassInventory(p -> true);
        var result = visitor.traverse(model);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of("library.Book", "library.EBook")));
        assertEquals(result, visitor.traverse(model));
        assertTrue(visitor.traverse(Factory.getInstance()
                .createCodeLevelModel("Empty")).isEmpty());
        assertEquals(2, result.size());
    }

    public void testPrunedPackageIsNeverRead() {
        var pkg = (IPackage) Proxy.newProxyInstance(IPackage.class.getClassLoader(),
                new Class<?>[] { IPackage.class }, (obj, method, args) -> {
                    throw new AssertionError(method.getName());
                });
        new ClassInventory(p -> false).open(pkg);
    }
}
