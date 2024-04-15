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
package padl.pagerank.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import padl.cpp.kernel.IDestructor;
import padl.cpp.kernel.IEnumValue;
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
import padl.kernel.IEntity;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGetter;
import padl.kernel.IGhost;
import padl.kernel.IInterface;
import padl.kernel.IMemberClass;
import padl.kernel.IMemberEntity;
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
import padl.kernel.impl.Factory;
import padl.visitor.IGenerator;
import util.io.ProxyConsole;
import util.lang.Modifier;

public class InputDataGeneratorWith9Relations implements IGenerator {
	private static final class ConstituentCouple {
		private final IConstituent originConstituent;
		private final IConstituent targetConstituent;

		public ConstituentCouple(
			final IConstituent anOriginConstituent,
			final IConstituent aTargetConstituent) {

			this.originConstituent = anOriginConstituent;
			this.targetConstituent = aTargetConstituent;

			if (this.originConstituent == null
					|| this.targetConstituent == null) {

				throw new NullPointerException(
					"A member of OriginTargetEntitiesCouple cannot be null.");
			}
		}
		public boolean equals(final Object anotherObject) {
			if (!(anotherObject instanceof ConstituentCouple)) {
				return false;
			}

			final ConstituentCouple anotherCouple =
				(ConstituentCouple) anotherObject;
			return this.originConstituent
				.equals(anotherCouple.getOriginConstituent())
					&& this.targetConstituent
						.equals(anotherCouple.getTargetConstituent());
		}
		public IConstituent getOriginConstituent() {
			return this.originConstituent;
		}
		public IConstituent getTargetConstituent() {
			return this.targetConstituent;
		}
		public String toString() {
			StringBuffer buffer = new StringBuffer();
			buffer.append('<');
			buffer.append(this.originConstituent.getPath());
			buffer.append(", ");
			buffer.append(this.targetConstituent.getPath());
			buffer.append('>');
			return buffer.toString();
		}
	}

	private IAbstractModel abstractModel;
	private final List constituents = new ArrayList();
	private final Stack currentConstituents = new Stack();
	private final StringBuffer finalResult = new StringBuffer();
	private final TObjectIntMap mapOfConstituentsAndIndexes =
		new TObjectIntHashMap();
	private final Map mapOfFieldsSignatures = new HashMap();
	private final Map mapOfOperationsSignatures = new HashMap();
	private final List relationsAggregations = new ArrayList();
	private final List relationsAssociations = new ArrayList();
	protected final List relationsCalledMethods = new ArrayList();
	private final List relationsCompositions = new ArrayList();
	private final List relationsContains = new ArrayList();
	private final List relationsCreations = new ArrayList();
	private final List relationsFieldAccesses = new ArrayList();
	private final List relationsInheritances = new ArrayList();
	private final List relationsParameterTypesOfMethods = new ArrayList();
	private final List relationsReturnTypesOfMethods = new ArrayList();
	private final List relationsTypesOfFields = new ArrayList();
	private final List relationsUses = new ArrayList();
	private int totalNumberOfRelations = 0;
	private final boolean withGhosts;
	private final boolean withMembers;

	public InputDataGeneratorWith9Relations(
		final boolean withGhosts,
		final boolean withMembers) {

		this.withGhosts = withGhosts;
		this.withMembers = withMembers;
	}
	private void addFieldSignature(
		final IField aField,
		final Map aMapOfFieldsSignatures) {

		if (!this.currentConstituents.empty()) {
			final StringBuffer buffer = new StringBuffer();
			buffer.append(Modifier.toString(aField.getVisibility()));
			buffer.append('@');
			buffer.append(aField.getDisplayTypeName());
			buffer.append('@');
			buffer.append(aField.getDisplayName());

			aMapOfFieldsSignatures.put(aField, buffer.toString());
		}
		else {
			ProxyConsole.getInstance().errorOutput().println(
				"this.currentConstituents is empty!?");
		}
	}
	private void addMethodSignature(
		final IOperation anOperation,
		final Map aMapOfOperationsSignatures) {

		if (!this.currentConstituents.empty()) {
			final StringBuffer buffer = new StringBuffer();
			buffer.append(Modifier.toString(anOperation.getVisibility()));
			buffer.append('@');
			if (anOperation instanceof IMethod) {
				buffer.append(((IMethod) anOperation).getDisplayReturnType());
			}
			else {
				buffer.append("");
			}
			buffer.append('@');
			if (anOperation.getName().length > 0) {
				buffer.append(anOperation.getDisplayName());
			}
			else {
				final Iterator iteratorOnParameters =
					anOperation.getIteratorOnConstituents(IParameter.class);
				if (iteratorOnParameters.hasNext()) {
					buffer.append(
						((IParameter) iteratorOnParameters.next())
							.getType()
							.getDisplayName());
				}
				else {
					buffer.append("DUMMY");
				}
			}
			buffer.append("@[");
			final Iterator iterator =
				anOperation.getIteratorOnConstituents(IParameter.class);
			while (iterator.hasNext()) {
				final IParameter parameter = (IParameter) iterator.next();
				buffer.append(parameter.getTypeName());
				if (iterator.hasNext()) {
					buffer.append(',');
				}
			}
			buffer.append(']');

			aMapOfOperationsSignatures.put(anOperation, buffer.toString());
		}
		else {
			ProxyConsole.getInstance().errorOutput().println(
				"this.currentConstituents is empty!?");
		}
	}
	private void addRelationBetweenConstituents(
		final IConstituent anOriginConstituent,
		final IConstituent aTargetConstituent,
		final List someRelationships) {

		// Yann 2015/05/24: Hack!!!
		// If the relation is "contains" then I do not care
		// whether or not the target entity is interesting,
		// I just add it...
		if (this.isInterestingOriginEntity(anOriginConstituent)
				&& (this.isInterestingTargetEntity(aTargetConstituent)
						|| someRelationships == this.relationsContains)) {

			final ConstituentCouple couple =
				new ConstituentCouple(anOriginConstituent, aTargetConstituent);
			if (!someRelationships.contains(couple)) {
				someRelationships.add(couple);
			}
		}
	}
	private void addRelationFromCurrentConstituent(
		final IConstituent aTargetConstituent,
		final List someRelationships) {

		try {
			final IConstituent originConstituent =
				(IConstituent) this.currentConstituents.peek();
			this.addRelationBetweenConstituents(
				originConstituent,
				aTargetConstituent,
				someRelationships);
		}
		catch (final EmptyStackException e) {
			throw new Error(e);
		}
	}
	public void close(final IAbstractModel p) {
		final Iterator iterator = this.constituents.iterator();
		int index = 0;
		while (iterator.hasNext()) {
			final IConstituent target = (IConstituent) iterator.next();
			this.mapOfConstituentsAndIndexes.put(target.getPath(), index);

			if (target instanceof IPackage) {
				this.finalResult.append("p,");
				this.finalResult.append(index);
				this.finalResult.append(',');
				this.finalResult.append(target.getDisplayID());
			}
			else if (target instanceof IPrimitiveEntity) {
				this.finalResult.append("c,");
				this.finalResult.append(index);
				this.finalResult.append(',');
				this.finalResult.append(target.getDisplayID());
			}
			else if (target instanceof IFirstClassEntity
					&& !(target instanceof IOperation)) {

				// Yann 2015/07/07: Ségla's parser!
				// We must exclude global functions from the class declarations
				// to make Ségla's parser happy. These global functions should
				// be methods attached to a dummy class...

				this.finalResult.append("c,");
				this.finalResult.append(index);
				this.finalResult.append(',');
				this.finalResult.append(target.getDisplayID());
				this.finalResult.append(',');
				this.finalResult.append(
					((IFirstClassEntity) target)
						.getNumberOfConstituents(IField.class));
				this.finalResult.append(',');
				this.finalResult.append(
					((IFirstClassEntity) target)
						.getNumberOfConstituents(IOperation.class));
			}
			else if (target instanceof IOperation) {
				final String operationSignature =
					(String) this.mapOfOperationsSignatures.get(target);
				this.finalResult.append("m,");
				this.finalResult.append(index);
				this.finalResult.append(',');
				this.finalResult.append(operationSignature);
			}
			else if (target instanceof IField) {
				final String fieldSignature =
					(String) this.mapOfFieldsSignatures.get(target);
				this.finalResult.append("a,");
				this.finalResult.append(index);
				this.finalResult.append(',');
				this.finalResult.append(fieldSignature);
			}
			else {
				ProxyConsole.getInstance().errorOutput().println(
					"Does not know what to do with constituent of type "
							+ target.getClass());
			}
			this.finalResult.append('\n');
			index++;
		}

		this.outputRelation(this.relationsAssociations, 1, this.finalResult);
		this.outputRelation(this.relationsCreations, 1, this.finalResult);
		this.outputRelation(this.relationsUses, 1, this.finalResult);
		this.outputRelation(this.relationsAggregations, 2, this.finalResult);
		this.outputRelation(this.relationsCompositions, 2, this.finalResult);
		this.outputRelation(this.relationsInheritances, 3, this.finalResult);
		this.outputRelation(this.relationsCalledMethods, 4, this.finalResult);
		this.outputRelation(this.relationsFieldAccesses, 5, this.finalResult);
		this.outputRelation(this.relationsTypesOfFields, 6, this.finalResult);
		this.outputRelation(
			this.relationsReturnTypesOfMethods,
			7,
			this.finalResult);
		this.outputRelation(
			this.relationsParameterTypesOfMethods,
			8,
			this.finalResult);
		this.outputRelation(this.relationsContains, 9, this.finalResult);

		this.finalResult
			.insert(0, index + "," + this.totalNumberOfRelations + "\n");
	}
	public void close(final IClass p) {
		this.close((IConstituent) p);
	}
	protected void close(final IConstituent p) {
		this.popConstituent(p);
	}
	public void close(final IConstructor p) {
		this.close((IOperation) p);
	}
	public void close(final IDelegatingMethod p) {
		this.close((IOperation) p);
	}
	public void close(final IGetter p) {
		this.close((IOperation) p);
	}
	public void close(final IGhost p) {
		this.close((IFirstClassEntity) p);
	}
	public void close(final IInterface p) {
		this.close((IFirstClassEntity) p);
	}
	public void close(final IMemberClass p) {
		this.close((IMemberEntity) p);
	}
	public void close(final IMemberGhost p) {
		this.close((IMemberEntity) p);
	}
	public void close(final IMemberInterface p) {
		this.close((IMemberEntity) p);
	}
	public void close(final IMethod p) {
		this.close((IOperation) p);
	}
	public void close(final IPackage p) {
		this.close((IConstituent) p);
	}
	public void close(final IPackageDefault p) {
		this.close((IPackage) p);
	}
	public void close(final ISetter p) {
		this.close((IOperation) p);
	}
	public String getCode() {
		return this.finalResult.toString();
	}
	public String getName() {
		return "ClassRank";
	}
	public List getRelationsType1Associations() {
		// For testing.
		return this.relationsAssociations;
	}
	public List getRelationsType1Creations() {
		// For testing.
		return this.relationsCreations;
	}
	public List getRelationsType1Uses() {
		// For testing.
		return this.relationsUses;
	}
	public List getRelationsType2Aggregations() {
		// For testing.
		return this.relationsAggregations;
	}
	public List getRelationsType2Compositions() {
		// For testing.
		return this.relationsCompositions;
	}
	public List getRelationsType3Inheritances() {
		// For testing.
		return this.relationsInheritances;
	}
	public List getRelationsType4CalledMethods() {
		// For testing.
		return this.relationsCalledMethods;
	}
	public List getRelationsType5FieldAccesses() {
		// For testing.
		return this.relationsFieldAccesses;
	}
	public List getRelationsType6TypesOfFields() {
		// For testing.
		return this.relationsTypesOfFields;
	}
	public List getRelationsType7ReturnTypesOfMethods() {
		// For testing.
		return this.relationsReturnTypesOfMethods;
	}
	public List getRelationsType8ParameterTypesOfMethods() {
		// For testing.
		return this.relationsParameterTypesOfMethods;
	}
	protected boolean isInterestingOriginEntity(
		final IConstituent aConstituent) {
		return aConstituent != null
				&& (aConstituent instanceof IGhost && this.withGhosts
						|| !(aConstituent instanceof IGhost))
				&& (aConstituent instanceof IMemberEntity && this.withMembers
						|| !(aConstituent instanceof IMemberEntity));
	}
	protected boolean isInterestingTargetEntity(
		final IConstituent aConstituent) {
		return aConstituent != null
				&& (aConstituent instanceof IGhost && this.withGhosts
						|| !(aConstituent instanceof IGhost))
				&& (aConstituent instanceof IMemberEntity && this.withMembers
						|| !(aConstituent instanceof IMemberEntity));
	}
	public void open(final IAbstractModel p) {
		this.abstractModel = p;

		final IPackage rootPackage =
			Factory.getInstance().createPackage(new char[] { '.' });
		this.constituents.add(rootPackage);
		this.pushConstituent(rootPackage);
	}
	public void open(final IClass p) {
		this.open((IFirstClassEntity) p);

		Iterator iterator = p.getIteratorOnImplementedInterfaces();
		while (iterator.hasNext()) {
			final IFirstClassEntity targetEntity =
				(IFirstClassEntity) iterator.next();
			this.addRelationFromCurrentConstituent(
				targetEntity,
				this.relationsInheritances);
		}

		iterator = p.getIteratorOnInheritedEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity targetEntity =
				(IFirstClassEntity) iterator.next();
			this.addRelationFromCurrentConstituent(
				targetEntity,
				this.relationsInheritances);
		}
	}
	public void open(final IConstructor p) {
		this.open((IOperation) p);
	}
	public void open(final IDelegatingMethod p) {
		this.open((IOperation) p);
	}
	protected void open(final IEntity p) {
		if (this.isInterestingOriginEntity(p)) {
			this.constituents.add(p);
			this.addRelationFromCurrentConstituent(p, this.relationsContains);
		}

		this.pushConstituent(p);
	}
	public void open(final IGetter p) {
		this.open((IOperation) p);
	}
	public void open(final IGhost p) {
		this.open((IFirstClassEntity) p);
	}
	public void open(final IInterface p) {
		this.open((IFirstClassEntity) p);

		final Iterator iterator = p.getIteratorOnInheritedEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity targetEntity =
				(IFirstClassEntity) iterator.next();
			this.addRelationFromCurrentConstituent(
				targetEntity,
				this.relationsInheritances);
		}
	}
	public void open(final IMemberClass p) {
		this.open((IClass) p);
	}
	public void open(final IMemberGhost p) {
		this.open((IGhost) p);
	}
	public void open(final IMemberInterface p) {
		this.open((IInterface) p);
	}
	public void open(final IMethod p) {
		this.open((IOperation) p);
	}
	protected void open(final IOperation p) {
		if (this.isInterestingOriginEntity(this.peekCurrentConstituent())) {
			this.addMethodSignature(p, this.mapOfOperationsSignatures);
			this.constituents.add(p);
			this.addRelationFromCurrentConstituent(p, this.relationsContains);
			final Iterator iterator =
				p.getIteratorOnConstituents(IParameter.class);
			while (iterator.hasNext()) {
				final IParameter parameter = (IParameter) iterator.next();
				this.addRelationBetweenConstituents(
					parameter.getType(),
					p,
					this.relationsParameterTypesOfMethods);
			}
			if (p instanceof IMethod) {
				this.addRelationBetweenConstituents(
					this.abstractModel
						.getTopLevelEntityFromID(((IMethod) p).getReturnType()),
					p,
					this.relationsReturnTypesOfMethods);
			}
		}

		this.pushConstituent(p);
	}
	public void open(final IPackage p) {
		this.constituents.add(p);
		this.addRelationFromCurrentConstituent(p, this.relationsContains);

		this.pushConstituent(p);
	}
	public void open(final IPackageDefault p) {
		this.open((IPackage) p);
	}
	public void open(final IPackageGhost p) {
		this.open((IPackage) p);
	}
	public void open(final ISetter p) {
		this.open((IOperation) p);
	}
	private void outputRelation(
		final List someRelationships,
		final int aLabel,
		final StringBuffer aBuffer) {

		final Iterator relationsIterator = someRelationships.iterator();
		while (relationsIterator.hasNext()) {
			final ConstituentCouple couple =
				(ConstituentCouple) relationsIterator.next();
			final IConstituent originConstituent =
				couple.getOriginConstituent();
			final IConstituent targetConstituent =
				couple.getTargetConstituent();

			if (this.constituents.contains(targetConstituent)) {
				final int indexOfOriginConstituent =
					this.mapOfConstituentsAndIndexes
						.get(originConstituent.getPath());
				final int indexOfTargetConstituent =
					this.mapOfConstituentsAndIndexes
						.get(targetConstituent.getPath());

				aBuffer.append("r,");
				aBuffer.append(indexOfOriginConstituent);
				aBuffer.append(',');
				aBuffer.append(indexOfTargetConstituent);
				aBuffer.append(',');
				aBuffer.append(aLabel + "\n");
				this.totalNumberOfRelations++;
			}
		}
	}
	private IConstituent peekCurrentConstituent() {
		return (IConstituent) this.currentConstituents.peek();
	}
	protected IConstituent peekLastButOneConstituent() {
		final IConstituent constituent = (IConstituent) this.currentConstituents
			.get(this.currentConstituents.size() - 2);
		return constituent;
	}
	protected void popConstituent(final IConstituent aConstituent) {
		final IConstituent constituent =
			(IConstituent) this.currentConstituents.pop();

		//	ProxyConsole.getInstance().debugOutput().print("Poped : ");
		//	ProxyConsole
		//		.getInstance()
		//		.debugOutput()
		//		.print(aConstituent.getDisplayName());
		//	ProxyConsole.getInstance().debugOutput().print(" (");
		//	ProxyConsole.getInstance().debugOutput().print(aConstituent.getClass());
		//	ProxyConsole.getInstance().debugOutput().println(')');

		if (!constituent.getClass().equals(aConstituent.getClass())) {
			ProxyConsole.getInstance().errorOutput().println(
				"Constituent mismatch on stack!");
		}
	}
	protected void pushConstituent(final IConstituent aConstituent) {
		this.currentConstituents.push(aConstituent);

		//	ProxyConsole.getInstance().debugOutput().print("Pushed: ");
		//	ProxyConsole
		//		.getInstance()
		//		.debugOutput()
		//		.print(aConstituent.getDisplayName());
		//	ProxyConsole.getInstance().debugOutput().print(" (");
		//	ProxyConsole.getInstance().debugOutput().print(aConstituent.getClass());
		//	ProxyConsole.getInstance().debugOutput().println(')');
	}
	public void reset() {
		this.constituents.clear();
		this.currentConstituents.clear();
		this.finalResult.setLength(0);
		this.mapOfFieldsSignatures.clear();
		this.mapOfOperationsSignatures.clear();
		this.relationsAggregations.clear();
		this.relationsAssociations.clear();
		this.relationsCalledMethods.clear();
		this.relationsCompositions.clear();
		this.relationsContains.clear();
		this.relationsCreations.clear();
		this.relationsFieldAccesses.clear();
		this.relationsInheritances.clear();
		this.relationsUses.clear();
		this.totalNumberOfRelations = 0;
	}
	public final void unknownConstituentHandler(
		final String aCalledMethodName,
		final IConstituent aConstituent) {

		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(this.getClass().getName());
		ProxyConsole.getInstance().debugOutput().print(
			" does not know what to do for \"");
		ProxyConsole.getInstance().debugOutput().print(aCalledMethodName);
		ProxyConsole.getInstance().debugOutput().print("\" (");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(aConstituent.getDisplayID());
		ProxyConsole.getInstance().debugOutput().println(')');
	}
	public void visit(final IAggregation p) {
		this.addRelationFromCurrentConstituent(
			p.getTargetEntity(),
			this.relationsAggregations);
	}
	public void visit(final IAssociation p) {
		this.addRelationFromCurrentConstituent(
			p.getTargetEntity(),
			this.relationsAssociations);
	}
	public void visit(final IComposition p) {
		this.addRelationFromCurrentConstituent(
			p.getTargetEntity(),
			this.relationsCompositions);
	}
	public void visit(final IContainerAggregation p) {
		this.addRelationFromCurrentConstituent(
			p.getTargetEntity(),
			this.relationsAggregations);
	}
	public void visit(final IContainerComposition p) {
		this.addRelationFromCurrentConstituent(
			p.getTargetEntity(),
			this.relationsCompositions);
	}
	public void visit(final ICreation p) {
		this.addRelationFromCurrentConstituent(
			p.getTargetEntity(),
			this.relationsCreations);
	}
	public void visit(final IDestructor p) {
		// TODO Understand why the visitor wants to use both open()/close() and visit() on destructors.
		this.open(p);
		this.close(p);
	}
	public void visit(final IEnumValue p) {
		// Do nothing?
	}
	public void visit(final IField p) {
		if (this.isInterestingOriginEntity(this.peekCurrentConstituent())) {
			this.addFieldSignature(p, this.mapOfFieldsSignatures);
			this.constituents.add(p);
			this.addRelationFromCurrentConstituent(p, this.relationsContains);
			// Yann 2013/07/13: Weird code!
			// I must try first as a first-class entity and then as a primitive entity :-(
			// TODO Could I do better than that and... more importantly... not to have to
			// duplicate this piece of code in any situation where I have primitive types?
			IEntity entity =
				this.abstractModel.getTopLevelEntityFromID(p.getType());
			if (entity == null) {
				entity = (IEntity) this.abstractModel
					.getConstituentFromID(p.getType());
			}
			this.addRelationBetweenConstituents(
				entity,
				p,
				this.relationsTypesOfFields);
		}
	}
	public void visit(final IMethodInvocation p) {
		if (p.getCalledMethod() != null) {
			if (Arrays
				.equals(p.getCalledMethod().getName(), new char[] { '=' })) {

				final IFirstClassEntity entity = p.getFieldDeclaringEntity();
				if (this
					.isInterestingOriginEntity(this.peekLastButOneConstituent())
						&& this.isInterestingTargetEntity(entity)) {

					this.addRelationFromCurrentConstituent(
						p.getFirstCallingField(),
						this.relationsFieldAccesses);
				}
			}
			else {
				final IConstituent originEntity =
					(IConstituent) this.peekLastButOneConstituent();
				final IFirstClassEntity targetEntity = p.getTargetEntity();
				if (this.isInterestingOriginEntity(originEntity)
						&& this.isInterestingTargetEntity(targetEntity)) {

					// TODO: I must look for the method in the target entity in
					// case it was changed from a IMethod to, say, a IGetter and
					// its identityHashCode would be different... Should not have
					// to do that!
					final IConstituent constituent =
						p.getTargetEntity().getConstituentFromID(
							p.getCalledMethod().getID());
					if (constituent != null) {
						this.addRelationFromCurrentConstituent(
							constituent,
							this.relationsCalledMethods);
					}
				}
			}
		}
	}
	public void visit(final IParameter p) {
	}
	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		this.open((IEntity) aPrimitiveEntity);
		// Yann 2015/05/24: Forgotten and not forgiven!
		// I should not forget to "close" the primitive entity
		// after visiting it to make sure that whatever is visited
		// next is put in its right container entity!
		this.close((IEntity) aPrimitiveEntity);
	}
	public void visit(final IUseRelationship p) {
		this.addRelationFromCurrentConstituent(
			p.getTargetEntity(),
			this.relationsUses);
	}
}
