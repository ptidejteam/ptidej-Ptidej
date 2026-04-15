/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.solver.claire;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import padl.kernel.IAbstractLevelModel;
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
import padl.kernel.IFirstClassEntity;
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
import padl.motif.IDesignMotifModel;
import padl.motif.visitor.IMotifWalker;

/**
 * @version	0.1
 * @author 	Yann-Gaël Guéhéneuc 
 * @since		2002/08/09
 */
public class DomainGenerator2AC4 extends
		DomainGenerator implements IMotifWalker {

	private static final boolean AGGREGATIONareCOMPOSITION = false;
	private static final boolean AGGREGATIONareCONTAINERAGGREGATION = false;
	private static final String NIL = "nil";
	private final List aggregationCouples = new ArrayList(1);
	private final List associationCouples = new ArrayList(1);

	private final StringBuffer buffer = new StringBuffer();
	private final List compositionCouples = new ArrayList(1);
	private final List containerAggregationCouples = new ArrayList(1);
	private final List containerCompositionCouples = new ArrayList(1);
	private final List creationCouples = new ArrayList(1);

	private IFirstClassEntity enclosingEntity;
	private final List inheritanceCouples = new ArrayList(1);
	private final List inheritancePathCouples = new ArrayList(1);
	private List listOfEntities;
	private final List strictInheritanceCouples = new ArrayList(1);
	private final List strictInheritancePathCouples = new ArrayList(1);
	private final List useCouples = new ArrayList(1);

	private void addAC4Constraint(
		final String name,
		final String replacement,
		final List setOfCouples,
		final boolean feasible,
		final StringBuffer buffer) {

		buffer.append('[');
		buffer.append(name);
		buffer
			.append("(\n\tname:string,\n\tcommand:string,\n\tv1:PtidejVar,\n\tv2:PtidejVar) : PtidejAC4Constraint\n");
		buffer.append("\t->");
		buffer
			.append("\tmakePtidejAC4Constraint(\n\t\tname,\n\t\tcommand,\n\t\tv1,\n\t\tv2,\n\t\t");
		buffer.append(feasible);
		buffer.append(",\n\t\t");
		buffer.append(this.getCoupleDeclaration());
		final Iterator iterator = setOfCouples.iterator();
		int numberOfCouples = 0;
		while (iterator.hasNext()) {
			buffer.append(iterator.next());
			numberOfCouples++;
			if (iterator.hasNext()) {
				buffer.append(',');
			}
			if (iterator.hasNext() && numberOfCouples % 5 == 0) {
				buffer.append("\n\t\t\t ");
			}
		}
		buffer.append("),\n\t\t");
		buffer.append(name);
		buffer.append(" @ string,\n\t\t");
		buffer.append(replacement);
		if (!replacement.equals(DomainGenerator2AC4.NIL)) {
			buffer.append(" @ string");
		}
		buffer.append(")\n]\n");
	}
	private void addAC4Constraint(
		final String name,
		final String replacement,
		final List setOfCouples,
		final StringBuffer buffer) {

		this.addAC4Constraint(name, replacement, setOfCouples, true, buffer);
	}
	private void addCouple(
		final List listOfEntities,
		final IFirstClassEntity entity1,
		final IFirstClassEntity entity2,
		final List set) {

		final String couple =
			this.createCouple(listOfEntities, entity1, entity2);
		if (!set.contains(couple)) {
			set.add(couple);
		}
	}
	private void addInheritanceCouple(
		final IFirstClassEntity origin,
		final Iterator anIteratorOnSuperEntities) {

		this.addCouple(
			this.listOfEntities,
			origin,
			origin,
			this.inheritanceCouples);
		while (anIteratorOnSuperEntities.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) anIteratorOnSuperEntities.next();
			this.addCouple(
				this.listOfEntities,
				origin,
				firstClassEntity,
				this.inheritanceCouples);
			this.addCouple(
				this.listOfEntities,
				origin,
				firstClassEntity,
				this.strictInheritanceCouples);
		}
	}
	private void addInheritancePathCouple(
		final IFirstClassEntity origin,
		final Iterator anIteratorOnSuperEntities) {

		this.addCouple(
			this.listOfEntities,
			origin,
			origin,
			this.inheritancePathCouples);
		while (anIteratorOnSuperEntities.hasNext()) {
			final IFirstClassEntity superEntity =
				(IFirstClassEntity) anIteratorOnSuperEntities.next();
			this.addCouple(
				this.listOfEntities,
				origin,
				superEntity,
				this.inheritancePathCouples);
			this.addCouple(
				this.listOfEntities,
				origin,
				superEntity,
				this.strictInheritancePathCouples);
			this.addInheritancePathCouple(
				origin,
				superEntity.getIteratorOnInheritedEntities());
			if (superEntity instanceof IClass) {
				this
					.addInheritancePathCouple(origin, ((IClass) superEntity)
						.getIteratorOnImplementedInterfaces());
			}
		}
	}
	public void close(final IAbstractLevelModel p) {
		this.close((IAbstractModel) p);
	}
	public void close(final IAbstractModel p) {
		// I create the declarations of the entities.
		for (int i = 0; i < this.listOfEntities.size(); i++) {
			final char[] id =
				((IFirstClassEntity) this.listOfEntities.get(i)).getID();
			final char[] claireIdentifier =
				DomainGenerator
					.convertToClaireIdentifier(id);

			this.buffer.append(claireIdentifier);
			this.buffer.append(":Entity := Entity(name = \"");
			this.buffer.append(id);
			this.buffer.append("\")\n");
		}

		// I create the list of entities.
		this.buffer.append("\nlistOfEntities: ");
		this.buffer.append(this.getListDeclaration());
		this.buffer.append(" := ");
		this.buffer.append(this.getListPrefix());
		this.buffer.append("\n\t");
		// Yann 2005/10/12: Iterator!
		// I have now an iterator able to iterate over a
		// specified type of constituent of a list.
		Iterator iterator = p.getIteratorOnTopLevelEntities();
		while (iterator.hasNext()) {
			final IConstituent constituent = (IConstituent) iterator.next();
			this.buffer.append(DomainGenerator
				.convertToClaireIdentifier(constituent.getID()));
			if (iterator.hasNext()) {
				this.buffer.append(",\n\t");
			}
		}
		this.buffer.append(this.getListSuffix());
		this.buffer.append("\n\n");

		// I build a list of awareness couples: A list of
		// couples of entities that do know about each other.
		final List awarenessCouples = new ArrayList(1);
		// Yann 2002/08/14: Size does matter!
		// For complexity reason, I do not fill up the list
		// of ignorance and awareness relationships. Those
		// list are way to big for Claire to handle them
		// (for example, on JUnit v3.7).
		iterator = this.listOfEntities.iterator();
		while (iterator.hasNext()) {
			final IFirstClassEntity entity1 =
				(IFirstClassEntity) iterator.next();
			final Iterator i = this.listOfEntities.iterator();
			while (i.hasNext()) {
				final IFirstClassEntity entity2 = (IFirstClassEntity) i.next();
				final String couple =
					this.createCouple(this.listOfEntities, entity1, entity2);

				if (this.aggregationCouples.contains(couple)
						|| this.associationCouples.contains(couple)
						|| this.compositionCouples.contains(couple)
						|| this.containerAggregationCouples.contains(couple)
						|| this.containerCompositionCouples.contains(couple)
						|| this.creationCouples.contains(couple)
						|| this.useCouples.contains(couple)) {

					awarenessCouples.add(couple);
				}
			}
		}

		//	final List noAggregationCouples =
		//		new ArrayList(this.associationCouples);
		//	noAggregationCouples.addAll(this.useCouples);
		//	final List noCompositionCouples =
		//		new ArrayList(this.aggregationCouples);
		//	noAggregationCouples.addAll(this.associationCouples);
		//	noAggregationCouples.addAll(this.useCouples);

		// I create the AC4 constraints.
		this.addAC4Constraint(
			"makeUseAC4Constraint",
			DomainGenerator2AC4.NIL,
			this.useCouples,
			this.buffer);
		this.addAC4Constraint(
			"makeAssociationAC4Constraint",
			"makeUseAC4Constraint",
			this.associationCouples,
			this.buffer);
		//	PtidejSolver2AC4DomainGenerator.addAC4Constraint(
		//		"makeAssociationAC4Constraint",
		//		"makeNoAssociationAC4Constraint",
		//		this.associationCouples,
		//		this.buffer);
		//	PtidejSolver2AC4DomainGenerator.addAC4Constraint(
		//		"makeNoAssociationAC4Constraint",
		//		"makeUseAC4Constraint",
		//		this.useCouples,
		//		this.buffer);
		this.addAC4Constraint(
			"makeAggregationAC4Constraint",
			"makeAssociationAC4Constraint",
			this.aggregationCouples,
			this.buffer);
		//	PtidejSolver2AC4DomainGenerator.addAC4Constraint(
		//		"makeAggregationAC4Constraint",
		//		"makeNoAggregationAC4Constraint",
		//		this.aggregationCouples,
		//		this.buffer);
		//	PtidejSolver2AC4DomainGenerator.addAC4Constraint(
		//		"makeNoAggregationAC4Constraint",
		//		"makeAssociationAC4Constraint",
		//		noCompositionCouples,
		//		this.buffer);
		this.addAC4Constraint(
			"makeCompositionAC4Constraint",
			"makeAggregationAC4Constraint",
			this.compositionCouples,
			this.buffer);
		//	PtidejSolver2AC4DomainGenerator.addAC4Constraint(
		//		"makeCompositionAC4Constraint",
		//		"makeNoCompositionAC4Constraint",
		//		this.compositionCouples,
		//		this.buffer);
		//	PtidejSolver2AC4DomainGenerator.addAC4Constraint(
		//		"makeNoCompositionAC4Constraint",
		//		"makeAggregationAC4Constraint",
		//		noCompositionCouples,
		//		this.buffer);
		this.addAC4Constraint(
			"makeContainerAggregationAC4Constraint",
			"makeAssociationAC4Constraint",
			this.containerAggregationCouples,
			this.buffer);
		this.addAC4Constraint(
			"makeContainerCompositionAC4Constraint",
			"makeContainerAggregationAC4Constraint",
			this.containerCompositionCouples,
			this.buffer);
		this.addAC4Constraint(
			"makeCreationAC4Constraint",
			DomainGenerator2AC4.NIL,
			this.creationCouples,
			this.buffer);
		this.addAC4Constraint(
			"makeInheritanceAC4Constraint",
			DomainGenerator2AC4.NIL,
			this.inheritanceCouples,
			this.buffer);
		this.addAC4Constraint(
			"makeInheritancePathAC4Constraint",
			DomainGenerator2AC4.NIL,
			this.inheritancePathCouples,
			this.buffer);
		this.addAC4Constraint(
			"makeStrictInheritanceAC4Constraint",
			DomainGenerator2AC4.NIL,
			this.strictInheritanceCouples,
			this.buffer);
		this.addAC4Constraint(
			"makeStrictInheritancePathAC4Constraint",
			DomainGenerator2AC4.NIL,
			this.strictInheritancePathCouples,
			this.buffer);
		this.addAC4Constraint(
			"makeAwarenessAC4Constraint",
			DomainGenerator2AC4.NIL,
			awarenessCouples,
			this.buffer);
		this.addAC4Constraint(
			"makeIgnoranceAC4Constraint",
			DomainGenerator2AC4.NIL,
			awarenessCouples,
			false,
			this.buffer);
	}
	public void close(final IClass p) {
	}
	public void close(final IConstructor aConstructor) {
	}
	public void close(final IDelegatingMethod aDelegatingMethod) {
	}
	public void close(final IDesignMotifModel p) {
		this.close((IAbstractModel) p);
	}
	public void close(final IGetter aGetter) {
	}
	public void close(final IGhost p) {
	}
	public void close(final IInterface p) {
	}
	public void close(final IMemberClass p) {
	}
	public void close(final IMemberGhost p) {
	}
	public void close(final IMemberInterface p) {
	}
	public void close(final IMethod aMethod) {
	}
	public void close(final IPackage p) {
	}
	public void close(final IPackageDefault aPackage) {
	}
	public void close(final IPackageGhost aPackageGhost) {
	}
	public void close(final ISetter aSetter) {
	}
	private String createCouple(
		final List listOfEntities,
		final IFirstClassEntity entity1,
		final IFirstClassEntity entity2) {

		final StringBuffer b = new StringBuffer(13);
		b.append(this.getCouplePrefix());
		b.append(listOfEntities.indexOf(entity1) + 1);
		b.append(',');
		b.append(listOfEntities.indexOf(entity2) + 1);
		b.append(')');
		return b.toString();
	}
	protected String getCoupleDeclaration() {
		return "list(";
	}
	protected String getCouplePrefix() {
		return "list(";
	}
	protected String getListDeclaration() {
		return "Entity[]";
	}
	protected String getListPrefix() {
		return "array!(list(";
	}
	protected String getListSuffix() {
		return "), Entity)";
	}
	public String getName() {
		return "PtidejSolver 2 AC4 Domain";
	}
	public Object getResult() {
		return this.buffer.toString();
	}
	public void open(final IAbstractLevelModel p) {
		this.open((IAbstractModel) p);
	}
	public void open(final IAbstractModel p) {
		//	ListenerManager.getCurrentListenerManager().getOutput().print("Exporting ");
		//	ListenerManager.getCurrentListenerManager().getOutput().print(p.getName());
		this.reset();
		this.listOfEntities = new ArrayList();
		// Yann 2005/10/12: Iterator!
		// I have now an iterator able to iterate over a
		// specified type of constituent of a list.
		final Iterator iterator = p.getIteratorOnTopLevelEntities();
		while (iterator.hasNext()) {
			this.listOfEntities.add(iterator.next());
		}
	}
	public void open(final IClass p) {
		//	ListenerManager.getCurrentListenerManager().getOutput().print("Exporting ");
		//	ListenerManager.getCurrentListenerManager().getOutput().print(p.getName());
		this.enclosingEntity = p;
		this.addInheritanceCouple(p, p.getIteratorOnInheritedEntities());
		this.addInheritancePathCouple(p, p.getIteratorOnInheritedEntities());
		this.addInheritanceCouple(p, p.getIteratorOnImplementedInterfaces());
		this
			.addInheritancePathCouple(p, p.getIteratorOnImplementedInterfaces());
	}
	public void open(final IConstructor p) {
	}
	public void open(final IDelegatingMethod p) {
	}
	public void open(final IDesignMotifModel p) {
		this.open((IAbstractModel) p);
	}
	public void open(final IGetter p) {
	}
	public void open(final IGhost p) {
		//	ListenerManager.getCurrentListenerManager().getOutput().print("Exporting ");
		//	ListenerManager.getCurrentListenerManager().getOutput().print(p.getName());
		this.enclosingEntity = p;
		this.addInheritanceCouple(p, p.getIteratorOnInheritedEntities());
		this.addInheritancePathCouple(p, p.getIteratorOnInheritedEntities());
	}
	public void open(final IInterface p) {
		//	ListenerManager.getCurrentListenerManager().getOutput().print("Exporting ");
		//	ListenerManager.getCurrentListenerManager().getOutput().print(p.getName());
		this.enclosingEntity = p;
		this.addInheritanceCouple(p, p.getIteratorOnInheritedEntities());
		this.addInheritancePathCouple(p, p.getIteratorOnInheritedEntities());
	}
	public void open(final IMemberClass p) {
		this.enclosingEntity = p;
		this.addInheritanceCouple(p, p.getIteratorOnInheritedEntities());
		this.addInheritancePathCouple(p, p.getIteratorOnInheritedEntities());
	}
	public void open(final IMemberGhost p) {
		this.enclosingEntity = p;
		this.addInheritanceCouple(p, p.getIteratorOnInheritedEntities());
		this.addInheritancePathCouple(p, p.getIteratorOnInheritedEntities());
	}
	public void open(final IMemberInterface p) {
		this.enclosingEntity = p;
		this.addInheritanceCouple(p, p.getIteratorOnInheritedEntities());
		this.addInheritancePathCouple(p, p.getIteratorOnInheritedEntities());
	}
	public void open(final IMethod p) {
		// Yann 2004/08/07: Associations!
		// (In the plane between Seoul and Vancouver...)
		// The association relationships induced by a method are
		// taken care of by the creator, not the visitor!
		//	final IEntity target =
		//		(IEntity) this.currentModel.getConstituentFromName(p.getReturnType());
		//	if (target != null) {
		//		this.addCouple(
		//			this.listOfEntities,
		//			this.enclosingEntity,
		//			target,
		//			this.associationCouples);
		//	}
	}
	public void open(final IPackage p) {
	}
	public void open(final IPackageDefault aPackage) {
	}
	public void open(final IPackageGhost aPackageGhost) {
	}
	public void open(final ISetter p) {
	}
	public void reset() {
		this.buffer.setLength(0);
		this.useCouples.clear();
		this.associationCouples.clear();
		this.aggregationCouples.clear();
		this.compositionCouples.clear();
		this.containerAggregationCouples.clear();
		this.containerCompositionCouples.clear();
		this.creationCouples.clear();
		this.inheritanceCouples.clear();
		this.inheritancePathCouples.clear();
		this.strictInheritanceCouples.clear();
		this.strictInheritancePathCouples.clear();
	}
	public void visit(final IAggregation p) {
		if (DomainGenerator2AC4.AGGREGATIONareCOMPOSITION) {
			this.addCouple(
				this.listOfEntities,
				this.enclosingEntity,
				p.getTargetEntity(),
				this.compositionCouples);
		}
		else {
			this.addCouple(
				this.listOfEntities,
				this.enclosingEntity,
				p.getTargetEntity(),
				this.aggregationCouples);
		}
	}
	public void visit(final IAssociation p) {
		this.addCouple(
			this.listOfEntities,
			this.enclosingEntity,
			p.getTargetEntity(),
			this.associationCouples);
	}
	public void visit(final IComposition p) {
		this.addCouple(
			this.listOfEntities,
			this.enclosingEntity,
			p.getTargetEntity(),
			this.compositionCouples);
	}
	public void visit(final IContainerAggregation p) {
		if (DomainGenerator2AC4.AGGREGATIONareCOMPOSITION) {
			if (DomainGenerator2AC4.AGGREGATIONareCONTAINERAGGREGATION) {
				this.addCouple(
					this.listOfEntities,
					this.enclosingEntity,
					p.getTargetEntity(),
					this.compositionCouples);
			}
			this.addCouple(
				this.listOfEntities,
				this.enclosingEntity,
				p.getTargetEntity(),
				this.containerCompositionCouples);
		}
		else {
			if (DomainGenerator2AC4.AGGREGATIONareCONTAINERAGGREGATION) {
				this.addCouple(
					this.listOfEntities,
					this.enclosingEntity,
					p.getTargetEntity(),
					this.aggregationCouples);
			}
			this.addCouple(
				this.listOfEntities,
				this.enclosingEntity,
				p.getTargetEntity(),
				this.containerAggregationCouples);
		}
	}
	public void visit(final IContainerComposition p) {
		this.addCouple(
			this.listOfEntities,
			this.enclosingEntity,
			p.getTargetEntity(),
			this.compositionCouples);
		this.addCouple(
			this.listOfEntities,
			this.enclosingEntity,
			p.getTargetEntity(),
			this.containerCompositionCouples);
	}
	public void visit(final ICreation p) {
		this.addCouple(
			this.listOfEntities,
			this.enclosingEntity,
			p.getTargetEntity(),
			this.creationCouples);
	}
	public void visit(final IField p) {
		// Yann 2004/08/07: Associations!
		// (In the plane between Seoul and Vancouver...)
		// The association relationships induced by a field are
		// taken care of by the creator, not the visitor!
		//	final IEntity target =
		//		(IEntity) this.currentModel.getConstituentFromName(p.getType());
		//	if (target != null) {
		//		this.addCouple(
		//			this.listOfEntities,
		//			this.enclosingEntity,
		//			target,
		//			this.associationCouples);
		//	}
	}
	public void visit(final IMethodInvocation aMethodInvocation) {
	}
	public void visit(final IParameter p) {
		// Yann 2004/08/07: Associations!
		// (In the plane between Seoul and Vancouver...)
		// The association relationships induced by a method are
		// taken care of by the creator, not the visitor!
		//	final IEntity target =
		//		(IEntity) this.currentModel.getConstituentFromName(p.getType());
		//	if (target != null) {
		//		this.addCouple(
		//			this.listOfEntities,
		//			this.enclosingEntity,
		//			target,
		//			this.associationCouples);
		//	}
	}
	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		// Do nothing for uninteresting primitive types.
	}
	public void visit(final IUseRelationship p) {
		this.addCouple(
			this.listOfEntities,
			this.enclosingEntity,
			p.getTargetEntity(),
			this.useCouples);
	}
}
