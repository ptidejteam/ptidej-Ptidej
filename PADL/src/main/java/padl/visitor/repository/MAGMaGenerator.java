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
package padl.visitor.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
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
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.IRelationship;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.util.Util;
import padl.visitor.IWalker;
import util.io.ProxyConsole;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2008/03/14
 */
public class MAGMaGenerator implements IWalker {
	private final StringBuffer edges;
	private final StringBuffer header;
	private List listOfEntities;
	private IAbstractModel model;
	private int numberOfEdges;
	private int numberOfVertices;
	private Stack stackOfEnclosingEntities;

	private final StringBuffer vertices;

	public MAGMaGenerator() {
		this.header = new StringBuffer();
		this.vertices = new StringBuffer();
		this.edges = new StringBuffer();
		this.stackOfEnclosingEntities = new Stack();
		this.listOfEntities = new ArrayList();
	}
	public void close(final IAbstractLevelModel anAbstractLevelModel) {
		this.close((IAbstractModel) anAbstractLevelModel);
	}
	public void close(final IAbstractModel anAbstractModel) {
		this.header.append(this.numberOfVertices);
		this.header.append(',');
		this.header.append(this.numberOfEdges);
		this.header.append('\n');
	}
	public void close(final IClass aClass) {
		this.close((IFirstClassEntity) aClass);
	}
	public void close(final IConstructor aConstructor) {
	}
	public void close(final IDelegatingMethod aDelegatingMethod) {
	}
	private void close(final IFirstClassEntity anEntity) {
		this.stackOfEnclosingEntities.pop();
	}
	public void close(final IGetter aGetter) {
	}
	public void close(final IGhost aGhost) {
		this.close((IFirstClassEntity) aGhost);
	}
	public void close(final IInterface anInterface) {
		this.close((IFirstClassEntity) anInterface);
	}
	public void close(final IMemberClass aMemberClass) {
		this.close((IFirstClassEntity) aMemberClass);
	}
	public void close(final IMemberGhost aMemberGhost) {
		this.close((IFirstClassEntity) aMemberGhost);
	}
	public void close(final IMemberInterface aMemberInterface) {
		this.close((IFirstClassEntity) aMemberInterface);
	}
	public void close(final IMethod aMethod) {
	}
	public void close(final IPackage aPackage) {
	}
	public void close(final IPackageDefault aPackage) {
	}
	public void close(final ISetter aSetter) {
	}
	private String getCurrentEntityName() {
		final StringBuffer buffer = new StringBuffer();
		final Iterator iterator = this.stackOfEnclosingEntities.iterator();
		while (iterator.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iterator.next();
			buffer.append(firstClassEntity.getID());
			if (iterator.hasNext()) {
				buffer.append('.');
			}
		}
		return buffer.toString();
	}
	public String getName() {
		return "MAGMa Domain";
	}
	public Object getResult() {
		return new StringBuffer(this.header.toString())
			.append(this.vertices.toString())
			.append(this.edges.toString())
			.toString();
	}
	public void open(final IAbstractLevelModel anAbstractLevelModel) {
	}
	public void open(IAbstractModel anAbstractModel) {
		this.model = anAbstractModel;

		// Yann 2008/03/14: Entities -> Verticies
		// To make sure I know in advance the index of each entities,
		// I iterate first through them all...
		final MAGMaGeneratorVertices mgv = new MAGMaGeneratorVertices();
		this.model.walk(mgv);
		this.vertices.append((StringBuffer) mgv.getResult());
		this.listOfEntities = mgv.getListOfEntities();
		this.numberOfVertices = this.listOfEntities.size();
	}
	public void open(final IClass aClass) {
		this.stackOfEnclosingEntities.push(aClass);

		final Iterator iteratorOnInheritedEntities =
			aClass.getIteratorOnInheritedEntities();
		while (iteratorOnInheritedEntities.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iteratorOnInheritedEntities.next();

			this.edges.append(this.listOfEntities.indexOf(this
				.getCurrentEntityName()));
			this.edges.append(',');
			this.edges.append(this.listOfEntities.indexOf(Util
				.computeFullyQualifiedName(this.model, firstClassEntity)));
			this.edges.append(",Specialisation");
			this.edges.append('\n');

			this.numberOfEdges++;
		}

		final Iterator iteratorOnImplementedEntities =
			aClass.getIteratorOnImplementedInterfaces();
		while (iteratorOnImplementedEntities.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iteratorOnImplementedEntities.next();

			this.edges.append(this.listOfEntities.indexOf(this
				.getCurrentEntityName()));
			this.edges.append(',');
			this.edges.append(this.listOfEntities.indexOf(Util
				.computeFullyQualifiedName(this.model, firstClassEntity)));
			this.edges.append(",Implementation");
			this.edges.append('\n');

			this.numberOfEdges++;
		}
	}
	public void open(final IConstructor aConstructor) {
	}
	public void open(final IDelegatingMethod aDelegatingMethod) {
	}
	public void open(final IGetter aGetter) {
	}
	public void open(final IGhost aGhost) {
		this.stackOfEnclosingEntities.push(aGhost);
	}
	public void open(final IInterface anInterface) {
		this.stackOfEnclosingEntities.push(anInterface);

		final Iterator iteratorOnInheritedEntities =
			anInterface.getIteratorOnInheritedEntities();
		while (iteratorOnInheritedEntities.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iteratorOnInheritedEntities.next();

			this.edges.append(this.listOfEntities.indexOf(this
				.getCurrentEntityName()));
			this.edges.append(',');
			this.edges.append(this.listOfEntities.indexOf(Util
				.computeFullyQualifiedName(this.model, firstClassEntity)));
			this.edges.append(",Specialisation");
			this.edges.append('\n');

			this.numberOfEdges++;
		}
	}
	public void open(final IMemberClass aMemberClass) {
		this.open((IClass) aMemberClass);
	}
	public void open(final IMemberGhost aMemberGhost) {
		this.open((IGhost) aMemberGhost);
	}
	public void open(final IMemberInterface aMemberInterface) {
		this.open((IInterface) aMemberInterface);
	}
	public void open(final IMethod aMethod) {
	}
	public void open(final IPackage aPackage) {
	}
	public void open(final IPackageDefault aPackage) {
	}
	public void open(final ISetter aSetter) {
	}
	public void reset() {
		this.header.setLength(0);
		this.vertices.setLength(0);
		this.edges.setLength(0);
		this.stackOfEnclosingEntities.clear();
		this.listOfEntities.clear();
		this.numberOfEdges = 0;
		this.numberOfVertices = 0;
	}
	public final void unknownConstituentHandler(
		final String aCalledMethodName,
		final IConstituent aConstituent) {

		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(this.getClass().getName());
		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(" does not know what to do for \"");
		ProxyConsole.getInstance().debugOutput().print(aCalledMethodName);
		ProxyConsole.getInstance().debugOutput().print("\" (");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(aConstituent.getDisplayID());
		ProxyConsole.getInstance().debugOutput().println(')');
	}
	public void visit(final IAggregation anAggregation) {
		this.visit((IRelationship) anAggregation, "Aggregation");
	}
	public void visit(final IAssociation anAssociation) {
		this.visit((IRelationship) anAssociation, "Association");
	}
	public void visit(final IComposition aComposition) {
		this.visit((IRelationship) aComposition, "Composition");
	}
	public void visit(final IContainerAggregation aContainerAggregation) {
		this.visit(
			(IRelationship) aContainerAggregation,
			"ContainerAggregation");
	}
	public void visit(final IContainerComposition aContainerComposition) {
		this.visit(
			(IRelationship) aContainerComposition,
			"ContainerComposition");
	}
	public void visit(final ICreation aCreation) {
		this.visit((IRelationship) aCreation, "Creation");
	}
	public void visit(final IField aField) {
	}
	public void visit(final IMethodInvocation aMethodInvocation) {
	}
	public void visit(final IParameter aParameter) {
	}
	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		// Do nothing for uninteresting primitive types.
	}
	private void visit(
		final IRelationship aRelationship,
		final String aRelationName) {

		final String targetEntityName =
			String.valueOf(Util.computeFullyQualifiedName(
				this.model,
				aRelationship.getTargetEntity()));

		if (!targetEntityName.equals("")) {
			this.edges.append(this.listOfEntities.indexOf(this
				.getCurrentEntityName()));
			this.edges.append(',');
			this.edges.append(this.listOfEntities.indexOf(targetEntityName));
			this.edges.append(',');
			this.edges.append(aRelationName);
			this.edges.append('\n');

			this.numberOfEdges++;
		}
		else {
			// TODO: How is it possible that the entity be ""???
		}
	}
	public void visit(final IUseRelationship aUse) {
		this.visit((IRelationship) aUse, "Use");
	}
}
