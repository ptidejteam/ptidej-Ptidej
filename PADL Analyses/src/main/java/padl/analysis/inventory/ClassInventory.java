package padl.analysis.inventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import padl.kernel.*;
import padl.util.adapter.WalkerAdapter;

public final class ClassInventory extends WalkerAdapter {
    private final Predicate<IPackage> include;
    private final List<String> classes = new ArrayList<>();

    public ClassInventory(Predicate<IPackage> include) {
        this.include = include;
    }

    public List<String> traverse(IAbstractModel model) {
        reset();
        visitChildren(model.getIteratorOnConstituents());
        return getResult();
    }

    private void visitChildren(Iterator<?> children) {
        while (children.hasNext()) {
            Object child = children.next();
            if (child instanceof IPackage pkg) open(pkg);
            else if (child instanceof IClass cls) open(cls);
        }
    }

    @Override public void open(IPackage pkg) {
        if (include.test(pkg)) visitChildren(pkg.getIteratorOnConstituents());
    }

    @Override public void open(IClass cls) {
        classes.add(cls.getDisplayID());
    }

    @Override public void reset() { classes.clear(); }
    @Override public List<String> getResult() { return List.copyOf(classes); }
    @Override public String getName() { return "Package class inventory"; }
}
