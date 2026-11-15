package padl.alternativeVisitor;

import java.util.Iterator;

import padl.kernel.IAbstractModel;
import padl.kernel.IAggregation;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IComposition;
import padl.kernel.IConstituent;
import padl.kernel.IConstructor;
import padl.kernel.IContainerAggregation;
import padl.kernel.IContainerComposition;
import padl.kernel.ICreation;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IField;
import padl.kernel.IGetter;
import padl.kernel.IGhost;
import padl.kernel.IInterface;
import padl.kernel.IMemberClass;
import padl.kernel.IMemberGhost;
import padl.kernel.IMemberInterface;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IPackage;
import padl.kernel.IPackageDefault;
import padl.kernel.IPackageGhost;
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.visitor.IGenerator;
import padl.visitor.repository.JavaGenerator;

public class JavaAlternativelyPatterenedGenerator implements IGenerator {
	private final JavaGenerator emitter = new JavaGenerator();

	private void traverse(final Iterator<?> someConstituents) {
		while (someConstituents.hasNext()) {
			this.dispatch((IConstituent) someConstituents.next());
		}
	}

	private void dispatch(final IConstituent aConstituent) {
		switch (aConstituent) {
			case IPackageGhost p -> { this.open(p); this.traverse(p.getIteratorOnConstituents()); this.close(p); }
			case IPackageDefault p -> { this.open(p); this.traverse(p.getIteratorOnConstituents()); this.close(p); }
			case IPackage p -> { this.open(p); this.traverse(p.getIteratorOnConstituents()); this.close(p); }
			case IMemberClass c -> { this.open(c); this.traverse(c.getConcurrentIteratorOnConstituents()); this.close(c); }
			case IMemberInterface i -> { this.open(i); this.traverse(i.getConcurrentIteratorOnConstituents()); this.close(i); }
			case IMemberGhost g -> { this.open(g); this.traverse(g.getConcurrentIteratorOnConstituents()); this.close(g); }
			case IClass c -> { this.open(c); this.traverse(c.getConcurrentIteratorOnConstituents()); this.close(c); }
			case IInterface i -> { this.open(i); this.traverse(i.getConcurrentIteratorOnConstituents()); this.close(i); }
			case IGhost g -> { this.open(g); this.traverse(g.getConcurrentIteratorOnConstituents()); this.close(g); }
			case IGetter m -> { this.open(m); this.traverse(m.getConcurrentIteratorOnConstituents()); this.close(m); }
			case ISetter m -> { this.open(m); this.traverse(m.getConcurrentIteratorOnConstituents()); this.close(m); }
			case IDelegatingMethod m -> { this.open(m); this.traverse(m.getConcurrentIteratorOnConstituents()); this.close(m); }
			// impl.Method extends impl.Constructor, so IMethod must come before IConstructor.
			case IMethod m -> { this.open(m); this.traverse(m.getConcurrentIteratorOnConstituents()); this.close(m); }
			case IConstructor c -> { this.open(c); this.traverse(c.getConcurrentIteratorOnConstituents()); this.close(c); }
			case IContainerComposition r -> this.visit(r);
			case IContainerAggregation r -> this.visit(r);
			case IComposition r -> this.visit(r);
			case IAggregation r -> this.visit(r);
			case IAssociation r -> this.visit(r);
			case ICreation r -> this.visit(r);
			case IUseRelationship r -> this.visit(r);
			case IField f -> this.visit(f);
			case IParameter p -> this.visit(p);
			case IMethodInvocation i -> this.visit(i);
			case IPrimitiveEntity p -> this.visit(p);
			default -> this.unknownConstituentHandler("dispatch", aConstituent);
		}
	}

	public void close(IAbstractModel anAbstractModel) {
		this.emitter.close(anAbstractModel);
	}

	public void close(IClass aClass) {
		this.emitter.close(aClass);
	}

	public void close(IConstructor aConstructor) {
		this.emitter.close(aConstructor);
	}

	public void close(IDelegatingMethod aDelegatingMethod) {
		this.emitter.close(aDelegatingMethod);
	}

	public void close(IGetter aGetter) {
		this.emitter.close(aGetter);
	}

	public void close(IGhost aGhost) {
		this.emitter.close(aGhost);
	}

	public void close(IInterface anInterface) {
		this.emitter.close(anInterface);
	}

	public void close(IMemberClass aMemberClass) {
		this.emitter.close(aMemberClass);
	}

	public void close(IMemberGhost aMemberGhost) {
		this.emitter.close(aMemberGhost);
	}

	public void close(IMemberInterface aMemberInterface) {
		this.emitter.close(aMemberInterface);
	}

	public void close(IMethod aMethod) {
		this.emitter.close(aMethod);
	}

	public void close(IPackage aPackage) {
		this.emitter.close(aPackage);
	}

	public void close(IPackageDefault aPackage) {
		this.emitter.close(aPackage);
	}

	public void close(IPackageGhost aPackageGhost) {
		this.emitter.close(aPackageGhost);
	}

	public void close(ISetter aSetter) {
		this.emitter.close(aSetter);
	}

	public String getName() {
		return "Java (self-traversing visitor)";
	}

	public void open(IAbstractModel anAbstractModel) {
		this.emitter.open(anAbstractModel);
		this.traverse(anAbstractModel.getIteratorOnConstituents());
	}

	public void open(IClass aClass) {
		this.emitter.open(aClass);
	}

	public void open(IConstructor aConstructor) {
		this.emitter.open(aConstructor);
	}

	public void open(IDelegatingMethod aDelegatingMethod) {
		this.emitter.open(aDelegatingMethod);
	}

	public void open(IGetter aGetter) {
		this.emitter.open(aGetter);
	}

	public void open(IGhost aGhost) {
		this.emitter.open(aGhost);
	}

	public void open(IInterface anInterface) {
		this.emitter.open(anInterface);
	}

	public void open(IMemberClass aMemberClass) {
		this.emitter.open(aMemberClass);
	}

	public void open(IMemberGhost aMemberGhost) {
		this.emitter.open(aMemberGhost);
	}

	public void open(IMemberInterface aMemberInterface) {
		this.emitter.open(aMemberInterface);
	}

	public void open(IMethod aMethod) {
		this.emitter.open(aMethod);
	}

	public void open(IPackage aPackage) {
		this.emitter.open(aPackage);
	}

	public void open(IPackageDefault aPackage) {
		this.emitter.open(aPackage);
	}

	public void open(IPackageGhost aPackageGhost) {
		this.emitter.open(aPackageGhost);
	}

	public void open(ISetter aSetter) {
		this.emitter.open(aSetter);
	}

	public void reset() {
		this.emitter.reset();
	}

	public void unknownConstituentHandler(String aCalledMethodName, IConstituent aConstituent) {
		this.emitter.unknownConstituentHandler(aCalledMethodName, aConstituent);
	}

	public void visit(IAggregation anAggregation) {
		this.emitter.visit(anAggregation);
	}

	public void visit(IAssociation anAssociation) {
		this.emitter.visit(anAssociation);
	}

	public void visit(IComposition aComposition) {
		this.emitter.visit(aComposition);
	}

	public void visit(IContainerAggregation aContainerAggregation) {
		this.emitter.visit(aContainerAggregation);
	}

	public void visit(IContainerComposition aContainerComposition) {
		this.emitter.visit(aContainerComposition);
	}

	public void visit(ICreation aCreation) {
		this.emitter.visit(aCreation);
	}

	public void visit(IField aField) {
		this.emitter.visit(aField);
	}

	public void visit(IMethodInvocation aMethodInvocation) {
		this.emitter.visit(aMethodInvocation);
	}

	public void visit(IParameter aParameter) {
		this.emitter.visit(aParameter);
	}

	public void visit(IPrimitiveEntity aPrimitiveEntity) {
		this.emitter.visit(aPrimitiveEntity);
	}

	public void visit(IUseRelationship aUse) {
		this.emitter.visit(aUse);
	}

	public String getCode() {
		return this.emitter.getCode();
	}

}
