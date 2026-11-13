package padl.visitor;

import java.util.Iterator;

import padl.kernel.IAbstractModel;
import padl.kernel.IAggregation;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IComposition;
import padl.kernel.IConstituent;
import padl.kernel.IConstructor;
import padl.kernel.IContainer;
import padl.kernel.IContainerAggregation;
import padl.kernel.IContainerComposition;
import padl.kernel.ICreation;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGetter;
import padl.kernel.IGhost;
import padl.kernel.IInterface;
import padl.kernel.IMemberClass;
import padl.kernel.IMemberGhost;
import padl.kernel.IMemberInterface;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import padl.kernel.IPackage;
import padl.kernel.IPackageDefault;
import padl.kernel.IPackageGhost;
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;

/**
 * ModelTraverser class is a variant of the Visitor design pattern in which the traversal
 * of a PADL model is done by this class, not by the accept() methods of the constituents.
 *
 * In the current implementation, each constituent's accept() walks its own children, and it picks the visitor method to call by reflection (see padl.kernel.impl.Constituent). 
 * The ModelTraverser class does both jobs itself. It walks the model through the public IContainer iterators, and it calls the matching IVisitor method directly, 
 * testing the type of each constituent from the most specific interface to the least specific one.
 *
 * Any existing visitor (e.g. PlantUMLGenerator) can be used with this traverser unchanged.
 * It receives exactly the same calls, in the same order, as with accept(). 
 * Since the traversal is now in its own class, subclasses can change the strategy, which accept() does not allow (see BreadthFirstModelTraverser).
 *
 * @author Vinisha Manek
 * @since 2026/11/10
 */
public class ModelTraverser {
	protected final IVisitor visitor;

	public ModelTraverser(final IVisitor aVisitor) {
		this.visitor = aVisitor;
	}

	public String generate(final IAbstractModel anAbstractModel) {
		this.traverse(anAbstractModel);
		return ((IGenerator) this.visitor).getCode();
	}

	public Object walk(final IAbstractModel anAbstractModel) {
		this.traverse(anAbstractModel);
		return ((IWalker) this.visitor).getResult();
	}

	public void traverse(final IAbstractModel anAbstractModel) {
		this.visitor.open(anAbstractModel);
		this.traverseConstituentsOf(anAbstractModel);
		this.visitor.close(anAbstractModel);
	}

	/**
	 * Walks the constituents of a container, depth-first and in order, like the accept() methods do
	 * Subclasses may override this method (or traverse()) to change the strategy
	 */
	protected void traverseConstituentsOf(final IContainer aContainer) {
		final Iterator iterator =
			aContainer.getConcurrentIteratorOnConstituents();
		while (iterator.hasNext()) {
			this.dispatch((IConstituent) iterator.next());
		}
	}

	/**
	 * Processes one constituent: a container is opened, walked, and closed or a leaf is visited
	 */
	protected void dispatch(final IConstituent aConstituent) {
		if (this.isContainer(aConstituent)) {
			this.openContainer(aConstituent);
			this.traverseConstituentsOf((IContainer) aConstituent);
			this.closeContainer(aConstituent);
		}
		else {
			this.visitLeaf(aConstituent);
		}
	}

	/**
	 * A container is a constituent whose accept() opens it, walks its children, and closes it
	 * (A container aggregation is an IContainer too, but accept() treats it as a leaf, so this method does the same)
	 */
	protected boolean isContainer(final IConstituent aConstituent) {
		return aConstituent instanceof IPackage || aConstituent instanceof IFirstClassEntity || aConstituent instanceof IOperation;
	}

	/**
	 * Calls the visitor's most specific open() method for this constituent.
	 */
	protected void openContainer(final IConstituent aConstituent) {
		if (aConstituent instanceof IPackageGhost) {
			this.visitor.open((IPackageGhost) aConstituent);
		}
		else if (aConstituent instanceof IPackageDefault) {
			this.visitor.open((IPackageDefault) aConstituent);
		}
		else if (aConstituent instanceof IPackage) {
			this.visitor.open((IPackage) aConstituent);
		}
		else if (aConstituent instanceof IMemberClass) {
			this.visitor.open((IMemberClass) aConstituent);
		}
		else if (aConstituent instanceof IClass) {
			this.visitor.open((IClass) aConstituent);
		}
		else if (aConstituent instanceof IMemberGhost) {
			this.visitor.open((IMemberGhost) aConstituent);
		}
		else if (aConstituent instanceof IGhost) {
			this.visitor.open((IGhost) aConstituent);
		}
		else if (aConstituent instanceof IMemberInterface) {
			this.visitor.open((IMemberInterface) aConstituent);
		}
		else if (aConstituent instanceof IInterface) {
			this.visitor.open((IInterface) aConstituent);
		}
		// The kernel class Method extends Constructor, so every method is also an instanceof IConstructor. 
		// IConstructor must be tested last here, otherwise every method would be dispatched as a constructor.
		else if (aConstituent instanceof IDelegatingMethod) {
			this.visitor.open((IDelegatingMethod) aConstituent);
		}
		else if (aConstituent instanceof IGetter) {
			this.visitor.open((IGetter) aConstituent);
		}
		else if (aConstituent instanceof ISetter) {
			this.visitor.open((ISetter) aConstituent);
		}
		else if (aConstituent instanceof IMethod) {
			this.visitor.open((IMethod) aConstituent);
		}
		else if (aConstituent instanceof IConstructor) {
			this.visitor.open((IConstructor) aConstituent);
		}
		else {
			this.visitor.unknownConstituentHandler(
				"open(" + aConstituent.getClass().getName() + ')',
				aConstituent);
		}
	}

	/**
	 * Calls the visitor's most specific close() method for this constituent
	 * (same ordering rules as openContainer() above, IConstructor last)
	 */
	protected void closeContainer(final IConstituent aConstituent) {
		if (aConstituent instanceof IPackageGhost) {
			this.visitor.close((IPackageGhost) aConstituent);
		}
		else if (aConstituent instanceof IPackageDefault) {
			this.visitor.close((IPackageDefault) aConstituent);
		}
		else if (aConstituent instanceof IPackage) {
			this.visitor.close((IPackage) aConstituent);
		}
		else if (aConstituent instanceof IMemberClass) {
			this.visitor.close((IMemberClass) aConstituent);
		}
		else if (aConstituent instanceof IClass) {
			this.visitor.close((IClass) aConstituent);
		}
		else if (aConstituent instanceof IMemberGhost) {
			this.visitor.close((IMemberGhost) aConstituent);
		}
		else if (aConstituent instanceof IGhost) {
			this.visitor.close((IGhost) aConstituent);
		}
		else if (aConstituent instanceof IMemberInterface) {
			this.visitor.close((IMemberInterface) aConstituent);
		}
		else if (aConstituent instanceof IInterface) {
			this.visitor.close((IInterface) aConstituent);
		}
		else if (aConstituent instanceof IDelegatingMethod) {
			this.visitor.close((IDelegatingMethod) aConstituent);
		}
		else if (aConstituent instanceof IGetter) {
			this.visitor.close((IGetter) aConstituent);
		}
		else if (aConstituent instanceof ISetter) {
			this.visitor.close((ISetter) aConstituent);
		}
		else if (aConstituent instanceof IMethod) {
			this.visitor.close((IMethod) aConstituent);
		}
		else if (aConstituent instanceof IConstructor) {
			this.visitor.close((IConstructor) aConstituent);
		}
		else {
			this.visitor.unknownConstituentHandler(
				"close(" + aConstituent.getClass().getName() + ')',
				aConstituent);
		}
	}

	/**
	 * Calls the visitor's most specific visit() method for this leaf
	 */
	protected void visitLeaf(final IConstituent aConstituent) {
		if (aConstituent instanceof IField) {
			this.visitor.visit((IField) aConstituent);
		}
		else if (aConstituent instanceof IParameter) {
			this.visitor.visit((IParameter) aConstituent);
		}
		else if (aConstituent instanceof IMethodInvocation) {
			this.visitor.visit((IMethodInvocation) aConstituent);
		}
		else if (aConstituent instanceof IContainerComposition) {
			this.visitor.visit((IContainerComposition) aConstituent);
		}
		else if (aConstituent instanceof IContainerAggregation) {
			this.visitor.visit((IContainerAggregation) aConstituent);
		}
		else if (aConstituent instanceof IComposition) {
			this.visitor.visit((IComposition) aConstituent);
		}
		else if (aConstituent instanceof IAggregation) {
			this.visitor.visit((IAggregation) aConstituent);
		}
		else if (aConstituent instanceof IAssociation) {
			this.visitor.visit((IAssociation) aConstituent);
		}
		else if (aConstituent instanceof ICreation) {
			this.visitor.visit((ICreation) aConstituent);
		}
		else if (aConstituent instanceof IUseRelationship) {
			this.visitor.visit((IUseRelationship) aConstituent);
		}
		else if (aConstituent instanceof IPrimitiveEntity) {
			this.visitor.visit((IPrimitiveEntity) aConstituent);
		}
		// Unknown constituents: same fall-back as accept().
		else {
			this.visitor.unknownConstituentHandler(
				"visit(" + aConstituent.getClass().getName() + ')',
				aConstituent);
		}
	}
}
