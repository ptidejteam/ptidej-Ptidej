package padl.alternativeVisitor;

import java.util.Iterator;
import padl.kernel.*;
import padl.visitor.*;
import padl.visitor.repository.*;
import java.util.Optional;

/**
 * SOEN 6461, Deliverable 2, Question 3: a Visitor that does its own traversal
 * based on the strategy chosen by the user.
 *
 * @author Ali Maher, Timéo Klein, and Vinicius Mioto
 */
public final class JavaAlternativelyPatterenedGenerator<T extends IVisitor> implements IGenerator {
    public interface ActionIterator<T extends IVisitor> {
        Optional<Action> getNext();
        void enter(final IConstituent aConstituent, final boolean supportsOpenClose);
        void leave(final IConstituent aConstituent, final boolean supportsOpenClose);
        /* Schedules the children of a container, in the order of the strategy */
        void addChildren(final java.util.List<IConstituent> children, final java.util.function.Predicate<IConstituent> supportsOpenClose);
    }

    private final JavaGenerator renderer;
    private final ActionIterator<T> iterator;

    /* Default constructor: depth-first, as required by the test of PR #521 */
    public JavaAlternativelyPatterenedGenerator() {
        this(new DfsIterator<>());
    }

    /* Simple constructor to just choose which iteration order */
    public JavaAlternativelyPatterenedGenerator(final ActionIterator<T> iterator) {
        this(iterator, new JavaGenerator());
    }

    /* Complex constructor to select the JavaGenerator in addition to the iterator */
    public JavaAlternativelyPatterenedGenerator(final ActionIterator<T> iterator, final JavaGenerator renderer) {
        this.iterator = iterator;
        this.renderer = renderer;
    }

    public String getCode() {return this.renderer.getCode();}
    public String getName() {return "Java (self-traversing)";}
    public void reset() {this.renderer.reset();}

    /* Main traversal function */
    public void traverse(final IConstituent aConstituent) throws Exception {
        this.iterator.enter(aConstituent, this.supportsOpenClose(aConstituent));
        this.iterator.leave(aConstituent, this.supportsOpenClose(aConstituent));
        for (;;) {
            final Optional<Action> elt = this.iterator.getNext();
            if (!elt.isPresent()) return;
            final Action action = elt.get();
            switch (action.getKind()) {
                case Open:
                    if (this.supportsOpenClose(action.getConstituent()))
                        this.dispatchOpen(action.getConstituent());
                    else throw new IllegalArgumentException(String.format("The class `%s` doesn't support the operation `open`.", action.getConstituent()));
                    break;
                case Close:
                    if (this.supportsOpenClose(action.getConstituent()))
                        this.dispatchClose(action.getConstituent());
                    else throw new IllegalArgumentException(String.format("The class `%s` doesn't support the operation `close`.", action.getConstituent()));
                    break;
                case Visit:
                    if (this.supportsOpenClose(action.getConstituent()))
                        throw new IllegalArgumentException(String.format("The class `%s` doesn't support the operation `visit`.", action.getConstituent()));
                    else this.dispatchVisit(action.getConstituent());
                    break;
            }
            // Only an opened container schedules its own children.
            if (action.getKind() == Action.Kind.Open && action.getConstituent() instanceof IContainer container) {
                @SuppressWarnings("unchecked")
                final Iterator<IConstituent> iter = container instanceof IFirstClassEntity || container instanceof IOperation
                    ? container.getConcurrentIteratorOnConstituents()
                    : container.getIteratorOnConstituents();
                final java.util.List<IConstituent> children = new java.util.ArrayList<>();
                iter.forEachRemaining(children::add);
                this.iterator.addChildren(children, this::supportsOpenClose);
            }
        }
    }

    private boolean supportsOpenClose(final IConstituent aConstituent) {
        return aConstituent instanceof IPackage || aConstituent instanceof IFirstClassEntity || aConstituent instanceof IOperation;
    }

    // Most specific types first. IMethod must precede IConstructor because
    // padl.kernel.impl.Method extends padl.kernel.impl.Constructor.
    private void dispatchOpen(final IConstituent aConstituent) {
        switch (aConstituent) {
            case final IPackageGhost p -> this.open(p);
            case final IPackageDefault p -> this.open(p);
            case final IPackage p -> this.open(p);
            case final IMemberClass c -> this.open(c);
            case final IClass c -> this.open(c);
            case final IMemberInterface i -> this.open(i);
            case final IInterface i -> this.open(i);
            case final IMemberGhost g -> this.open(g);
            case final IGhost g -> this.open(g);
            case final IDelegatingMethod m -> this.open(m);
            case final IGetter m -> this.open(m);
            case final ISetter m -> this.open(m);
            case final IMethod m -> this.open(m);
            case final IConstructor c -> this.open(c);
            default -> this.unknownConstituentHandler("open", aConstituent);
        }
    }

    private void dispatchClose(final IConstituent aConstituent) {
        switch (aConstituent) {
            case final IPackageGhost p -> this.close(p);
            case final IPackageDefault p -> this.close(p);
            case final IPackage p -> this.close(p);
            case final IMemberClass c -> this.close(c);
            case final IClass c -> this.close(c);
            case final IMemberInterface i -> this.close(i);
            case final IInterface i -> this.close(i);
            case final IMemberGhost g -> this.close(g);
            case final IGhost g -> this.close(g);
            case final IDelegatingMethod m -> this.close(m);
            case final IGetter m -> this.close(m);
            case final ISetter m -> this.close(m);
            case final IMethod m -> this.close(m);
            case final IConstructor c -> this.close(c);
            default -> this.unknownConstituentHandler("close", aConstituent);
        }
    }

    private void dispatchVisit(final IConstituent aConstituent) {
        switch (aConstituent) {
            case final IContainerComposition r -> this.visit(r);
            case final IContainerAggregation r -> this.visit(r);
            case final IComposition r -> this.visit(r);
            case final IAggregation r -> this.visit(r);
            case final IAssociation r -> this.visit(r);
            case final ICreation r -> this.visit(r);
            case final IUseRelationship r -> this.visit(r);
            case final IField f -> this.visit(f);
            case final IParameter p -> this.visit(p);
            case final IMethodInvocation m -> this.visit(m);
            case final IPrimitiveEntity e -> this.visit(e);
            default -> this.unknownConstituentHandler("visit", aConstituent);
        }
    }


    public void unknownConstituentHandler(final String aCalledMethodName, final IConstituent aConstituent) {
        this.renderer.unknownConstituentHandler(aCalledMethodName, aConstituent);
    }

    public void open(final IAbstractModel anAbstractModel) {
        this.renderer.open(anAbstractModel);
        // **HORRIBLE HACK**
        // We should better avoid raw types.
        //
        // This code doesn't compile without the cast
        // because getConcurrentIteratorOnConstituents
        // return a raw `Iterator` type.
        @SuppressWarnings("unchecked")
        final Iterator<IConstituent> iter = anAbstractModel.getConcurrentIteratorOnConstituents();
        iter.forEachRemaining(v -> {
            try {this.traverse(v);
            } catch (final Exception ex) {throw new Error("A visitor has returned an invalid action kind.");}
        });
    }

    // The remaining methods only delegate the rendering to the JavaGenerator.
    public void open(final IPackage aPackage) {this.renderer.open(aPackage);}
    public void open(final IPackageDefault aPackage) {this.renderer.open(aPackage);}
    public void open(final IPackageGhost aPackageGhost) {this.renderer.open(aPackageGhost);}
    public void open(final IClass aClass) {this.renderer.open(aClass);}
    public void open(final IMemberClass aMemberClass) {this.renderer.open(aMemberClass);}
    public void open(final IInterface anInterface) {this.renderer.open(anInterface);}
    public void open(final IMemberInterface aMemberInterface) {this.renderer.open(aMemberInterface);}
    public void open(final IGhost aGhost) {this.renderer.open(aGhost);}
    public void open(final IMemberGhost aMemberGhost) {this.renderer.open(aMemberGhost);}
    public void open(final IConstructor aConstructor) {this.renderer.open(aConstructor);}
    public void open(final IMethod aMethod) {this.renderer.open(aMethod);}
    public void open(final IDelegatingMethod aDelegatingMethod) {this.renderer.open(aDelegatingMethod);}
    public void open(final IGetter aGetter) {this.renderer.open(aGetter);}
    public void open(final ISetter aSetter) {this.renderer.open(aSetter);}

    public void close(final IAbstractModel anAbstractModel) {this.renderer.close(anAbstractModel);}
    public void close(final IPackage aPackage) {this.renderer.close(aPackage);}
    public void close(final IPackageDefault aPackage) {this.renderer.close(aPackage);}
    public void close(final IPackageGhost aPackageGhost) {this.renderer.close(aPackageGhost);}
    public void close(final IClass aClass) {this.renderer.close(aClass);}
    public void close(final IMemberClass aMemberClass) {this.renderer.close(aMemberClass);}
    public void close(final IInterface anInterface) {this.renderer.close(anInterface);}
    public void close(final IMemberInterface aMemberInterface) {this.renderer.close(aMemberInterface);}
    public void close(final IGhost aGhost) {this.renderer.close(aGhost);}
    public void close(final IMemberGhost aMemberGhost) {this.renderer.close(aMemberGhost);}
    public void close(final IConstructor aConstructor) {this.renderer.close(aConstructor);}
    public void close(final IMethod aMethod) {this.renderer.close(aMethod);}
    public void close(final IDelegatingMethod aDelegatingMethod) {this.renderer.close(aDelegatingMethod);}
    public void close(final IGetter aGetter) {this.renderer.close(aGetter);}
    public void close(final ISetter aSetter) {this.renderer.close(aSetter);}

    public void visit(final IAggregation anAggregation) {this.renderer.visit(anAggregation);}
    public void visit(final IAssociation anAssociation) {this.renderer.visit(anAssociation);}
    public void visit(final IComposition aComposition) {this.renderer.visit(aComposition);}
    public void visit(final IContainerAggregation aContainerAggregation) {this.renderer.visit(aContainerAggregation);}
    public void visit(final IContainerComposition aContainerComposition) {this.renderer.visit(aContainerComposition);}
    public void visit(final ICreation aCreation) {this.renderer.visit(aCreation);}
    public void visit(final IField aField) {this.renderer.visit(aField);}
    public void visit(final IMethodInvocation aMethodInvocation) {this.renderer.visit(aMethodInvocation);}
    public void visit(final IParameter aParameter) {this.renderer.visit(aParameter);}
    public void visit(final IPrimitiveEntity aPrimitiveEntity) {this.renderer.visit(aPrimitiveEntity);}
    public void visit(final IUseRelationship aUse) {this.renderer.visit(aUse);}
}
