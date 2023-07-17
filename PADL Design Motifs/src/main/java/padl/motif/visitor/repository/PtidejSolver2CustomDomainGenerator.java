/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package padl.motif.visitor.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.IAbstractModel;
import padl.kernel.IAggregation;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IComposition;
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
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.motif.IDesignMotifModel;
import padl.motif.visitor.IMotifWalker;

public class PtidejSolver2CustomDomainGenerator extends
		AbstractPtidejSolverDomainGenerator implements IMotifWalker {

	private StringBuffer buffer = new StringBuffer(0);
	private IFirstClassEntity[] entities;
	private List listOfAggregations = new ArrayList(0);
	private List listOfAssociations = new ArrayList(0);
	private List listOfCompositions = new ArrayList(0);
	private List listOfContainerAggregations = new ArrayList(0);
	private List listOfContainerCompositions = new ArrayList(0);
	private List listOfCreations = new ArrayList(0);

	private List listOfUses = new ArrayList(0);

	private boolean belongsToDomain(final char[] anID) {
		for (int i = 0; i < this.entities.length; i++) {
			if (this.entities[i].getID().equals(anID)) {
				return true;
			}
		}
		return false;
	}
	private void close(
		final char[] name,
		final Iterator anIteratorOnSuperEntities) {
		final char[] claireIdentifier =
			AbstractPtidejSolverDomainGenerator.convertToClaireIdentifier(name);

		final StringBuffer header = new StringBuffer();
		header.append(claireIdentifier);
		header.append(":Entity := Entity(name = \"");
		header.append(name);
		header.append("\")\n");

		this.buffer.insert(0, header.toString());
		this.buffer.append('\n');
		this.buffer.append('(');
		this.buffer.append(claireIdentifier);
		this.buffer.append(".superEntities := ");
		this.buffer.append(this.getListPrefix());
		while (anIteratorOnSuperEntities.hasNext()) {
			this.buffer
				.append(AbstractPtidejSolverDomainGenerator
					.convertToClaireIdentifier(((IFirstClassEntity) (anIteratorOnSuperEntities
						.next())).getID()));
			if (anIteratorOnSuperEntities.hasNext()) {
				this.buffer.append(", ");
			}
		}
		this.buffer.append(this.getListSuffix());
		this.buffer.append(",\n ");

		// Aggregation.
		final StringBuffer temporaryBuffer = new StringBuffer();
		Iterator iterator = this.listOfAggregations.iterator();
		while (iterator.hasNext()) {
			temporaryBuffer.append((char[]) iterator.next());
			if (iterator.hasNext()) {
				temporaryBuffer.append(", ");
			}
		}
		this.buffer.append(claireIdentifier);
		this.buffer.append(".aggregatedEntities := ");
		this.buffer.append(this.getListPrefix());
		this.buffer.append(temporaryBuffer.toString());
		this.buffer.append(this.getListSuffix());
		this.buffer.append(",\n ");

		// Association.
		temporaryBuffer.setLength(0);
		iterator = this.listOfAssociations.iterator();
		while (iterator.hasNext()) {
			temporaryBuffer.append((char[]) iterator.next());
			if (iterator.hasNext()) {
				temporaryBuffer.append(", ");
			}
		}
		this.buffer.append(claireIdentifier);
		this.buffer.append(".associatedEntities := ");
		this.buffer.append(this.getListPrefix());
		this.buffer.append(temporaryBuffer.toString());
		this.buffer.append(this.getListSuffix());
		this.buffer.append(",\n ");

		// Composition.
		this.buffer.append(claireIdentifier);
		this.buffer.append(".componentsType := ");
		this.buffer.append(this.getListPrefix());

		temporaryBuffer.setLength(0);
		iterator = this.listOfCompositions.iterator();
		while (iterator.hasNext()) {
			// final Entity pEntity = (Entity) iterator.next();
			final char[] pEntityName = (char[]) iterator.next();
			this.buffer.append(pEntityName);

			temporaryBuffer.append(this.getListPrefix());
			// Yann 2001/07/11: Too much!
			// This was too much: The Composition constraint had to many
			// Entity to play with, I only keep the Entity corresponding
			// to the target of the Composition link. The subclasses will
			// be dealt with through the StrictInheritance or
			// InheritancePath constraints.
			/* 
			Enumeration s =
			    PaLMGenerator.directSubclassesOf(pEntity, this.pEntities).elements();
			while (s.hasMoreElements()) {
			    pEntity = (Entity) s.nextElement();
			    components.append(PaLMGenerator.convertToClaireIdentifier(pEntity.getName()));
			    if (s.hasMoreElements()) {
			        components.append(", ");
			    }
			}
			*/
			temporaryBuffer.append(pEntityName);
			temporaryBuffer.append(this.getListSuffix());

			if (iterator.hasNext()) {
				this.buffer.append(", ");
				temporaryBuffer.append(", ");
			}
		}
		this.buffer.append(this.getListSuffix());
		this.buffer.append(",\n ");
		this.buffer.append(claireIdentifier);
		this.buffer.append(".composedEntities := ");
		this.buffer.append(this.getListOfListPrefix());
		this.buffer.append(temporaryBuffer.toString());
		this.buffer.append(this.getListOfListSuffix());
		this.buffer.append(",\n ");

		// ContainerAggregation.
		temporaryBuffer.setLength(0);
		iterator = this.listOfContainerAggregations.iterator();
		while (iterator.hasNext()) {
			temporaryBuffer.append((char[]) iterator.next());
			if (iterator.hasNext()) {
				temporaryBuffer.append(", ");
			}
		}
		this.buffer.append(claireIdentifier);
		this.buffer.append(".containerAggregatedEntities := ");
		this.buffer.append(this.getListPrefix());
		this.buffer.append(temporaryBuffer.toString());
		this.buffer.append(this.getListSuffix());
		this.buffer.append(",\n ");

		// ContainerComposition.
		this.buffer.append(claireIdentifier);
		this.buffer.append(".containerComponentsType := ");
		this.buffer.append(this.getListPrefix());

		temporaryBuffer.setLength(0);
		iterator = this.listOfContainerCompositions.iterator();
		while (iterator.hasNext()) {
			// final Entity pEntity = (Entity) iterator.next();
			final char[] pEntityName = (char[]) iterator.next();
			this.buffer.append(pEntityName);

			temporaryBuffer.append(this.getListPrefix());
			// Yann 2001/07/11: Too much!
			// This was too much: The Composition constraint had to many
			// Entity to play with, I only keep the Entity corresponding
			// to the target of the Composition link. The subclasses will
			// be dealt with through the StrictInheritance or
			// InheritancePath constraints.
			/* 
			Enumeration s =
			    PaLMGenerator.directSubclassesOf(pEntity, this.pEntities).elements();
			while (s.hasMoreElements()) {
			    pEntity = (Entity) s.nextElement();
			    components.append(PaLMGenerator.convertToClaireIdentifier(pEntity.getName()));
			    if (s.hasMoreElements()) {
			        components.append(", ");
			    }
			}
			*/
			temporaryBuffer.append(pEntityName);
			temporaryBuffer.append(this.getListSuffix());

			if (iterator.hasNext()) {
				this.buffer.append(", ");
				temporaryBuffer.append(", ");
			}
		}
		this.buffer.append(this.getListSuffix());
		this.buffer.append(",\n ");
		this.buffer.append(claireIdentifier);
		this.buffer.append(".containerComposedEntities := ");
		this.buffer.append(this.getListOfListPrefix());
		this.buffer.append(temporaryBuffer.toString());
		this.buffer.append(this.getListOfListSuffix());
		this.buffer.append(",\n ");

		// Known entities.
		temporaryBuffer.setLength(0);
		iterator = this.listOfUses.iterator();
		while (iterator.hasNext()) {
			temporaryBuffer.append((char[]) iterator.next());
			if (iterator.hasNext()) {
				temporaryBuffer.append(", ");
			}
		}
		this.buffer.append(claireIdentifier);
		this.buffer.append(".knownEntities := ");
		this.buffer.append(this.getListPrefix());
		this.buffer.append(temporaryBuffer.toString());
		this.buffer.append(this.getListSuffix());
		this.buffer.append(",\n ");

		// Created entities.
		temporaryBuffer.setLength(0);
		iterator = this.listOfCreations.iterator();
		while (iterator.hasNext()) {
			temporaryBuffer.append((char[]) iterator.next());
			if (iterator.hasNext()) {
				temporaryBuffer.append(", ");
			}
		}
		this.buffer.append(claireIdentifier);
		this.buffer.append(".createdEntities := ");
		this.buffer.append(this.getListPrefix());
		this.buffer.append(temporaryBuffer.toString());
		this.buffer.append(this.getListSuffix());
		this.buffer.append(",\n ");

		// Unknown entities.
		// I need to treat the first one as a "special" case,
		// because I don't know if I will have another
		// unknown entity after this first one.
		temporaryBuffer.setLength(0);
		boolean firstDone = false;
		int i = 0;
		for (; i < this.entities.length && !firstDone; i++) {
			final char[] id =
				AbstractPtidejSolverDomainGenerator
					.convertToClaireIdentifier(this.entities[i].getID());

			if (!id.equals(claireIdentifier)
					&& !this.listOfAggregations.contains(id)
					&& !this.listOfAssociations.contains(id)
					&& !this.listOfCompositions.contains(id)
					&& !this.listOfContainerAggregations.contains(id)
					&& !this.listOfContainerCompositions.contains(id)
					&& !this.listOfCreations.contains(id)
					&& !this.listOfUses.contains(id)) {

				temporaryBuffer.append(id);

				firstDone = true;
			}
		}
		for (; i < this.entities.length; i++) {
			final char[] id =
				AbstractPtidejSolverDomainGenerator
					.convertToClaireIdentifier(this.entities[i].getID());

			if (!id.equals(claireIdentifier)
					&& !this.listOfAggregations.contains(id)
					&& !this.listOfAssociations.contains(id)
					&& !this.listOfCompositions.contains(id)
					&& !this.listOfContainerAggregations.contains(id)
					&& !this.listOfContainerCompositions.contains(id)
					&& !this.listOfCreations.contains(id)
					&& !this.listOfUses.contains(id)) {

				temporaryBuffer.append(", ");
				temporaryBuffer.append(id);
			}
		}
		this.buffer.append(claireIdentifier);
		this.buffer.append(".unknownEntities := ");
		this.buffer.append(this.getListPrefix());
		this.buffer.append(temporaryBuffer.toString());
		this.buffer.append(this.getListSuffix());
		this.buffer.append(")\n");

		// I clean up a little bit...

		this.listOfAggregations.clear();
		this.listOfAssociations.clear();
		this.listOfCompositions.clear();
		this.listOfContainerAggregations.clear();
		this.listOfContainerCompositions.clear();
		this.listOfCreations.clear();
		this.listOfUses.clear();
	}
	public void close(final IAbstractLevelModel p) {
		this.close((IAbstractModel) p);
	}
	public void close(final IAbstractModel p) {
		this.buffer.append("\nlistOfEntities: ");
		this.buffer.append(this.getListDeclaration());
		this.buffer.append(" := ");
		this.buffer.append(this.getListPrefix());
		// Yann 2005/10/07: Packages!
		// A model may now contrain entities and packages.
		// Yann 2005/10/12: Iterator!
		// I have now an iterator able to iterate over a
		// specified type of constituent of a list.
		final Iterator iterator = p.getIteratorOnTopLevelEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iterator.next();
			this.buffer.append(AbstractPtidejSolverDomainGenerator
				.convertToClaireIdentifier(firstClassEntity.getID()));
			if (iterator.hasNext()) {
				this.buffer.append(", ");
			}
		}
		this.buffer.append(this.getListSuffix());
		this.buffer.append("");
	}
	public void close(final IClass aClass) {
		// Yann 2002/08/09: Copies!
		// I create an intermediate List to hold 
		// both superclasses and superinterfaces.
		final List superEntities = new ArrayList();
		Iterator iterator = aClass.getIteratorOnInheritedEntities();
		while (iterator.hasNext()) {
			superEntities.add(iterator.next());
		}
		iterator = aClass.getIteratorOnImplementedInterfaces();
		while (iterator.hasNext()) {
			superEntities.add(iterator.next());
		}
		this.close(aClass.getID(), superEntities.iterator());
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
		this.close(p.getID(), p.getIteratorOnInheritedEntities());
	}
	public void close(final IInterface p) {
		this.close(p.getID(), p.getIteratorOnInheritedEntities());
	}
	public void close(final IMemberClass p) {
		this.close(p.getID(), p.getIteratorOnInheritedEntities());
	}
	public void close(final IMemberGhost p) {
		this.close(p.getID(), p.getIteratorOnInheritedEntities());
	}
	public void close(final IMemberInterface p) {
		this.close(p.getID(), p.getIteratorOnInheritedEntities());
	}
	public void close(final IMethod aMethod) {
	}
	public void close(final IPackage p) {
	}
	public void close(final IPackageDefault aPackage) {
	}
	public void close(final ISetter aSetter) {
	}
	protected String getListDeclaration() {
		return "Entity[]";
	}
	protected String getListOfListPrefix() {
		return "array!(list(";
	}
	protected String getListOfListSuffix() {
		return "), Entity[])";
	}
	protected String getListPrefix() {
		return "array!(list(";
	}
	protected String getListSuffix() {
		return "), Entity)";
	}
	public String getName() {
		return "PtidejSolver 2 Custom Domain";
	}
	public Object getResult() {
		return this.buffer.toString();
	}
	public void open(final IAbstractLevelModel p) {
		this.open((IAbstractModel) p);
	}
	public void open(final IAbstractModel p) {
		this.reset();
		this.entities = new IFirstClassEntity[p.getNumberOfTopLevelEntities()];
		// Yann 2005/10/12: Iterator!
		// I have now an iterator able to iterate over a
		// specified type of constituent of a list.
		final Iterator iterator = p.getIteratorOnTopLevelEntities();
		int i = 0;
		while (iterator.hasNext()) {
			this.entities[i++] = (IFirstClassEntity) iterator.next();
		}
	}
	public void open(final IClass p) {
	}
	public void open(final IConstructor p) {
	}
	public void open(final IDelegatingMethod p) {
		// Yann 2004/08/07: Associations!
		// (In the plane between Seoul and Vancouver...)
		// The association relationships induced by a method are
		// taken care of by the creator, not the visitor!
		//	if (this.belongsToDomain(p.getReturnType())) {
		//		String pEntityName =
		//			PtidejSolverDomainGenerator.convertToClaireIdentifier(
		//				p.getReturnType());
		//
		//		if (!this.listOfAssociations.contains(pEntityName)) {
		//			this.listOfAssociations.add(pEntityName);
		//		}
		//	}
	}
	public void open(final IDesignMotifModel p) {
		this.open((IAbstractModel) p);
	}
	public void open(final IGetter p) {
	}
	public void open(final IGhost p) {
	}
	public void open(final IInterface p) {
	}
	public void open(final IMemberClass p) {
	}
	public void open(final IMemberGhost p) {
	}
	public void open(final IMemberInterface p) {
	}
	public void open(final IMethod p) {
		// Yann 2004/08/07: Associations!
		// (In the plane between Seoul and Vancouver...)
		// The association relationships induced by a method are
		// taken care of by the creator, not the visitor!
		//	if (this.belongsToDomain(p.getReturnType())) {
		//		String pEntityName =
		//			PtidejSolverDomainGenerator.convertToClaireIdentifier(
		//				p.getReturnType());
		//
		//		if (!this.listOfAssociations.contains(pEntityName)) {
		//			this.listOfAssociations.add(pEntityName);
		//		}
		//	}
	}
	public void open(final IPackage p) {
	}
	public void open(final IPackageDefault aPackage) {
	}
	public void open(final ISetter p) {
	}
	public void reset() {
		this.buffer.setLength(0);
	}
	public void visit(final IAggregation p) {
		final char[] pEntityName =
			AbstractPtidejSolverDomainGenerator.convertToClaireIdentifier(p
				.getTargetEntity()
				.getID());

		if (!this.listOfAggregations.contains(pEntityName)) {
			this.listOfAggregations.add(pEntityName);
		}
	}
	public void visit(final IAssociation p) {
		final char[] pEntityName =
			AbstractPtidejSolverDomainGenerator.convertToClaireIdentifier(p
				.getTargetEntity()
				.getID());

		if (!this.listOfAssociations.contains(pEntityName)) {
			this.listOfAssociations.add(pEntityName);
		}
	}
	public void visit(final IComposition p) {
		final char[] pEntityName =
			AbstractPtidejSolverDomainGenerator.convertToClaireIdentifier(p
				.getTargetEntity()
				.getID());

		if (!this.listOfCompositions.contains(pEntityName)) {
			this.listOfCompositions.add(pEntityName);
			// Yann 2002/09/11
			// I must also add the composed entities
			// to the list of known entities.
			// Yann 2002/10/05: Why?
			//	this.listOfUses.add(pEntityName);
		}
	}
	public void visit(final IContainerAggregation p) {
		final char[] pEntityName =
			AbstractPtidejSolverDomainGenerator.convertToClaireIdentifier(p
				.getTargetEntity()
				.getID());

		if (!this.listOfContainerAggregations.contains(pEntityName)) {
			this.listOfContainerAggregations.add(pEntityName);
		}
	}
	public void visit(final IContainerComposition p) {
		final char[] pEntityName =
			AbstractPtidejSolverDomainGenerator.convertToClaireIdentifier(p
				.getTargetEntity()
				.getID());

		if (!this.listOfContainerCompositions.contains(pEntityName)) {
			this.listOfContainerCompositions.add(pEntityName);
			// Yann 2008/09/11
			// I must also add the composed entities
			// to the list of known entities.
			// Yann 2002/10/05: Why?
			//	this.listOfUses.add(pEntityName);
		}
	}
	public void visit(final ICreation p) {
		final char[] pEntityName =
			AbstractPtidejSolverDomainGenerator.convertToClaireIdentifier(p
				.getTargetEntity()
				.getID());

		if (!this.listOfCreations.contains(pEntityName)) {
			this.listOfCreations.add(pEntityName);
		}
	}
	public void visit(final IField p) {
		if (this.belongsToDomain(p.getType())) {
			char[] pEntityName =
				AbstractPtidejSolverDomainGenerator.convertToClaireIdentifier(p
					.getType());

			if (!this.listOfAssociations.contains(pEntityName)) {
				this.listOfAssociations.add(pEntityName);
			}
		}
	}
	public void visit(final IMethodInvocation aMethodInvocation) {
	}
	public void visit(final IParameter p) {
		// Yann 2004/08/07: Associations!
		// (In the plane between Seoul and Vancouver...)
		// The association relationships induced by a method are
		// taken care of by the creator, not the visitor!
		//	if (this.belongsToDomain(p.getType())) {
		//		String pEntityName =
		//			PtidejSolverDomainGenerator.convertToClaireIdentifier(
		//				p.getType());
		//
		//		if (!this.listOfAssociations.contains(pEntityName)) {
		//			this.listOfAssociations.add(pEntityName);
		//		}
		//	}
	}
	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		// Do nothing for uninteresting primitive types.
	}
	public void visit(final IUseRelationship p) {
		final char[] pEntityName =
			AbstractPtidejSolverDomainGenerator.convertToClaireIdentifier(p
				.getTargetEntity()
				.getID());

		if (!this.listOfUses.contains(pEntityName)) {
			this.listOfUses.add(pEntityName);
		}
	}
}
