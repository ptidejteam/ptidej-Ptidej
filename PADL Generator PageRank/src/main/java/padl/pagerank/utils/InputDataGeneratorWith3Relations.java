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
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import com.ibm.toad.cfparse.utils.Access;

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
import padl.kernel.IOperation;
import padl.kernel.IPackage;
import padl.kernel.IPackageDefault;
import padl.kernel.IPackageGhost;
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.visitor.IGenerator;
import util.io.ProxyConsole;

public class InputDataGeneratorWith3Relations implements IGenerator {
	private static final class OriginTargetEntitiesCouple {
		private final IFirstClassEntity originEntity;
		private final IFirstClassEntity targetEntity;

		public OriginTargetEntitiesCouple(
				final IFirstClassEntity anOriginEntity,
				final IFirstClassEntity aTargetEntity) {

			this.originEntity = anOriginEntity;
			this.targetEntity = aTargetEntity;
		}

		public IFirstClassEntity getOriginEntity() {
			return this.originEntity;
		}

		public IFirstClassEntity getTargetEntity() {
			return this.targetEntity;
		}
	}

	private final List attributesFields = new ArrayList();
	private final List attributesMethods = new ArrayList();
	private final Stack currentEntities = new Stack();
	private final List entities = new ArrayList();
	private final StringBuffer finalResult = new StringBuffer();
	private final List relationsAggregations = new ArrayList();
	private final List relationsAssociations = new ArrayList();
	private final List relationsCompositions = new ArrayList();
	private final List relationsCreations = new ArrayList();
	private final List relationsInheriting = new ArrayList();
	private final List relationsUses = new ArrayList();
	private int totalNumberOfRelations = 0;

	public InputDataGeneratorWith3Relations() {
	}

	private void addFieldSignature(final IField aField, final List someFields) {
		if (!this.currentEntities.empty()) {
			final StringBuffer buffer = new StringBuffer();

			final IFirstClassEntity declaringEntity = (IFirstClassEntity) this.currentEntities
					.peek();
			buffer.append(this.entities.indexOf(declaringEntity));
			buffer.append(',');
			buffer.append(Access.getFieldAsString(aField.getVisibility()));
			buffer.append('@');
			buffer.append(aField.getDisplayTypeName());
			buffer.append('@');
			buffer.append(aField.getDisplayName());

			someFields.add(buffer.toString());
		}
	}

	private void addMethodSignature(final IMethod aMethod,
			final List someMethods) {

		if (!this.currentEntities.empty()) {
			final StringBuffer buffer = new StringBuffer();
			final IFirstClassEntity declaringEntity = (IFirstClassEntity) this.currentEntities
					.peek();

			buffer.append(this.entities.indexOf(declaringEntity));
			buffer.append(',');
			buffer.append(Access.getMethodAsString(aMethod.getVisibility()));
			buffer.append('@');
			buffer.append(aMethod.getDisplayReturnType());
			buffer.append('@');
			buffer.append(aMethod.getDisplayName());
			buffer.append("@[");
			final Iterator iterator = aMethod
					.getIteratorOnConstituents(IParameter.class);
			while (iterator.hasNext()) {
				final IParameter parameter = (IParameter) iterator.next();
				buffer.append(parameter.getTypeName());
				if (iterator.hasNext()) {
					buffer.append(',');
				}
			}
			buffer.append(']');

			someMethods.add(buffer.toString());
		}
	}

	private void addRelation(final IFirstClassEntity aTargetEntity,
			final List someRelationships) {

		if (!this.currentEntities.empty()) {
			final IFirstClassEntity originEntity = (IFirstClassEntity) this.currentEntities
					.peek();
			someRelationships.add(new OriginTargetEntitiesCouple(originEntity,
					aTargetEntity));
		}
	}

	private void addRelation(final List someRelationships, final int aLabel,
			final StringBuffer aBuffer) {

		final Iterator relationsIterator = someRelationships.iterator();
		while (relationsIterator.hasNext()) {
			final OriginTargetEntitiesCouple couple = (OriginTargetEntitiesCouple) relationsIterator
					.next();
			final IFirstClassEntity originEntity = couple.getOriginEntity();
			final IFirstClassEntity targetEntity = couple.getTargetEntity();

			if (this.entities.contains(targetEntity)) {
				aBuffer.append("r,");
				aBuffer.append(this.entities.indexOf(originEntity));
				aBuffer.append(',');
				aBuffer.append(this.entities.indexOf(targetEntity));
				aBuffer.append(',');
				aBuffer.append(aLabel + "\n");
				this.totalNumberOfRelations++;
			}
		}
	}

	public void close(final IAbstractModel p) {
		this.finalResult.append(this.entities.size() + ","
				+ this.totalNumberOfRelations + "\n");

		Iterator iterator = this.entities.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			final IFirstClassEntity target = (IFirstClassEntity) iterator
					.next();
			this.finalResult.append("c,");
			this.finalResult.append(i++);
			this.finalResult.append(',');
			this.finalResult.append(target.getDisplayID());
			this.finalResult.append(',');
			this.finalResult
					.append(target.getNumberOfConstituents(IField.class));
			this.finalResult.append(',');
			this.finalResult
					.append(target.getNumberOfConstituents(IOperation.class));
			this.finalResult.append('\n');
		}

		iterator = this.attributesFields.iterator();
		while (iterator.hasNext()) {
			final String fieldSignature = (String) iterator.next();
			this.finalResult.append("a,");
			this.finalResult.append(fieldSignature);
			this.finalResult.append('\n');
		}

		iterator = this.attributesMethods.iterator();
		while (iterator.hasNext()) {
			final String methodSignature = (String) iterator.next();
			this.finalResult.append("m,");
			this.finalResult.append(methodSignature);
			this.finalResult.append('\n');
		}

		this.addRelation(this.relationsInheriting, 3, this.finalResult);
		this.addRelation(this.relationsAggregations, 2, this.finalResult);
		this.addRelation(this.relationsAssociations, 1, this.finalResult);
		this.addRelation(this.relationsCompositions, 2, this.finalResult);
		this.addRelation(this.relationsCreations, 1, this.finalResult);
		this.addRelation(this.relationsUses, 1, this.finalResult);
	}

	public void close(final IClass p) {
		this.close((IFirstClassEntity) p);
	}

	public void close(final IConstructor aConstructor) {
	}

	public void close(final IDelegatingMethod aDelegatingMethod) {
	}

	private void close(final IFirstClassEntity p) {
		if (!this.currentEntities.empty()) {
			this.currentEntities.pop();
		}
	}

	public void close(final IGetter aGetter) {
	}

	public void close(final IGhost p) {
		this.close((IFirstClassEntity) p);
	}

	public void close(final IInterface p) {
		this.close((IFirstClassEntity) p);
	}

	public void close(final IMemberClass p) {
		this.close((IFirstClassEntity) p);
	}

	public void close(final IMemberGhost p) {
		this.close((IFirstClassEntity) p);
	}

	public void close(final IMemberInterface p) {
		this.close((IFirstClassEntity) p);
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

	public String getCode() {
		return this.finalResult.toString();
	}

	public String getName() {
		return "ClassRank";
	}

	public void open(final IAbstractModel p) {
		//this.buffer.append("pageRank graph G {\n");
	}

	public void open(final IClass p) {
		this.entities.add(p);
		this.currentEntities.push(p);

		Iterator iterator = p.getIteratorOnImplementedInterfaces();
		while (iterator.hasNext()) {
			final IFirstClassEntity targetEntity = (IFirstClassEntity) iterator
					.next();
			this.addRelation(targetEntity, this.relationsInheriting);
		}

		iterator = p.getIteratorOnInheritedEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity targetEntity = (IFirstClassEntity) iterator
					.next();
			this.addRelation(targetEntity, this.relationsInheriting);
		}
	}

	public void open(final IConstructor p) {
	}

	public void open(final IDelegatingMethod p) {
	}

	public void open(final IGetter aGetter) {
		this.addMethodSignature(aGetter, this.attributesMethods);
	}

	public void open(final IGhost p) {
	}

	public void open(final IInterface p) {
		this.entities.add(p);
		this.currentEntities.push(p);

		final Iterator iterator = p.getIteratorOnInheritedEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity targetEntity = (IFirstClassEntity) iterator
					.next();
			this.addRelation(targetEntity, this.relationsInheriting);
		}
	}

	public void open(final IMemberClass p) {
		this.open((IClass) p);
	}

	public void open(final IMemberGhost p) {
	}

	public void open(final IMemberInterface p) {
		this.open((IInterface) p);
	}

	public void open(final IMethod aMethod) {
		this.addMethodSignature(aMethod, this.attributesMethods);
	}

	public void open(final IPackage p) {
	}

	public void open(final IPackageDefault aPackage) {
	}

	public void open(final IPackageGhost aPackageGhost) {
	}

	public void open(final ISetter aSetter) {
		this.addMethodSignature(aSetter, this.attributesMethods);
	}

	public void reset() {
		this.attributesFields.clear();
		this.attributesMethods.clear();
		this.currentEntities.clear();
		this.entities.clear();
		this.finalResult.setLength(0);
		this.relationsAggregations.clear();
		this.relationsAssociations.clear();
		this.relationsCompositions.clear();
		this.relationsCreations.clear();
		this.relationsInheriting.clear();
		this.relationsUses.clear();
		this.totalNumberOfRelations = 0;
	}

	public final void unknownConstituentHandler(final String aCalledMethodName,
			final IConstituent aConstituent) {

		ProxyConsole.getInstance().debugOutput()
				.print(this.getClass().getName());
		ProxyConsole.getInstance().debugOutput()
				.print(" does not know what to do for \"");
		ProxyConsole.getInstance().debugOutput().print(aCalledMethodName);
		ProxyConsole.getInstance().debugOutput().print("\" (");
		ProxyConsole.getInstance().debugOutput()
				.print(aConstituent.getDisplayID());
		ProxyConsole.getInstance().debugOutput().println(')');
	}

	public void visit(final IAggregation p) {
		this.addRelation(p.getTargetEntity(), this.relationsAggregations);
	}

	public void visit(final IAssociation p) {
		this.addRelation(p.getTargetEntity(), this.relationsAssociations);
	}

	public void visit(final IComposition p) {
		this.addRelation(p.getTargetEntity(), this.relationsCompositions);
	}

	public void visit(final IContainerAggregation p) {
		this.addRelation(p.getTargetEntity(), this.relationsAggregations);
	}

	public void visit(final IContainerComposition p) {
		this.addRelation(p.getTargetEntity(), this.relationsCompositions);
	}

	public void visit(final ICreation p) {
		this.addRelation(p.getTargetEntity(), this.relationsCreations);
	}

	public void visit(final IField aField) {
		this.addFieldSignature(aField, this.attributesFields);
	}

	public void visit(final IMethodInvocation aMethodInvocation) {
	}

	public void visit(final IParameter p) {
	}

	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		// Do nothing for uninteresting primitive types.
	}

	public void visit(final IUseRelationship p) {
		this.addRelation(p.getTargetEntity(), this.relationsUses);
	}
}
