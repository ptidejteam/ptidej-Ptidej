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
package ptidej.viewer.extension.repository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
import padl.visitor.IWalker;
import util.io.ProxyConsole;

/**
 * Visitor that produces an input file for the matrix-agency display
 * system from Mohammad Ghoniem (OADymPPaC).
 * 
 * @version	0.2
 * @author 	Yann-Gaël Guéhéneuc
 */
public final class OADymPPaCGenerator implements IWalker {
	private static final int AGGREGATION_WEIGHT = 3;
	private static final int ASSOCIATION_WEIGHT = 2;
	private static final int COMPOSITION_WEIGHT = 4;
	// Yann 2002/08/01: Weights!
	// For the moment, I hard-code the weights in the visitors,
	// although they should be included in the meta-model.
	private static final char SEPARATOR_CHAR = '#';
	private static final int USE_WEIGHT = 1;

	private IFirstClassEntity enclosingPEntity;
	private Map interClassRelationships = new HashMap();

	private void addKey(final IRelationship relationship, final int weight) {
		// Yann 2003/06/26: Ghosts!
		// I remove the gosts from the clustering
		// to save some space and enhance visibility!
		if (!(this.enclosingPEntity instanceof IGhost)
				&& !(relationship.getTargetEntity() instanceof IGhost)) {

			final StringBuffer relationshipNameBuffer =
				new StringBuffer(this.enclosingPEntity.getDisplayName());
			relationshipNameBuffer.append(OADymPPaCGenerator.SEPARATOR_CHAR);
			relationshipNameBuffer.append(relationship
				.getTargetEntity()
				.getName());

			final String relationshipName = relationshipNameBuffer.toString();
			if (this.interClassRelationships.containsKey(relationshipName)) {
				final int oldWeight =
					((Integer) this.interClassRelationships
						.get(relationshipName)).intValue();
				this.interClassRelationships.put(relationshipName, Integer.valueOf(
					oldWeight + weight));
			}
			else {
				this.interClassRelationships.put(relationshipName, Integer.valueOf(
					weight));
			}
		}
	}
	public void close(final IAbstractModel p) {
	}
	public void close(final IClass p) {
		this.enclosingPEntity = null;
	}
	public void close(final IConstructor aConstructor) {
	}
	public void close(final IDelegatingMethod aDelegatingMethod) {
	}
	public void close(final IGetter aGetter) {
	}
	public void close(final IGhost p) {
		this.enclosingPEntity = null;
	}
	public void close(final IInterface p) {
		this.enclosingPEntity = null;
	}
	public void close(final IMemberClass p) {
		this.enclosingPEntity = null;
	}
	public void close(final IMemberGhost p) {
		this.enclosingPEntity = null;
	}
	public void close(final IMemberInterface p) {
		this.enclosingPEntity = null;
	}
	public void close(final IMethod aMethod) {
	}
	public void close(final IPackage p) {
	}
	public void close(final IPackageDefault p) {
	}
	public void close(final ISetter aSetter) {
	}
	public String getName() {
		return "Graph (OADymPPaC)";
	}
	public Object getResult() {
		final StringBuffer buffer = new StringBuffer();
		final Iterator iterator =
			this.interClassRelationships.keySet().iterator();

		while (iterator.hasNext()) {
			final String key = (String) iterator.next();
			buffer.append(key.replace(OADymPPaCGenerator.SEPARATOR_CHAR, ' '));
			buffer.append(' ');
			buffer.append(this.interClassRelationships.get(key));
			buffer.append('\n');
		}

		return buffer.toString();
	}
	public void open(final IAbstractModel p) {
		this.reset();
	}
	public void open(final IClass p) {
		this.enclosingPEntity = p;
	}
	public void open(final IConstructor p) {
	}
	public void open(final IDelegatingMethod p) {
	}
	public void open(final IGetter p) {
	}
	public void open(final IGhost p) {
		this.enclosingPEntity = p;
	}
	public void open(final IInterface p) {
		this.enclosingPEntity = p;
	}
	public void open(final IMemberClass p) {
		this.enclosingPEntity = p;
	}
	public void open(final IMemberGhost p) {
		this.enclosingPEntity = p;
	}
	public void open(final IMemberInterface p) {
		this.enclosingPEntity = p;
	}
	public void open(final IMethod p) {
	}
	public void open(final IPackage p) {
	}
	public void open(final IPackageDefault p) {
	}
	public void open(final ISetter p) {
	}
	public void reset() {
		this.interClassRelationships.clear();
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
	public void visit(final IAggregation p) {
		this.addKey(p, OADymPPaCGenerator.AGGREGATION_WEIGHT);
	}
	public void visit(final IAssociation p) {
		this.addKey(p, OADymPPaCGenerator.ASSOCIATION_WEIGHT);
	}
	public void visit(final IComposition p) {
		this.addKey(p, OADymPPaCGenerator.COMPOSITION_WEIGHT);
	}
	public void visit(final IContainerAggregation p) {
		this.addKey(p, OADymPPaCGenerator.AGGREGATION_WEIGHT);
	}
	public void visit(final IContainerComposition p) {
		this.addKey(p, OADymPPaCGenerator.COMPOSITION_WEIGHT);
	}
	public void visit(final ICreation p) {
	}
	public void visit(final IField p) {
	}
	public void visit(final IMethodInvocation aMethodInvocation) {
	}
	public void visit(final IParameter p) {
	}
	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		// Do nothing for uninteresting primitive types.
	}
	public void visit(final IUseRelationship p) {
		this.addKey(p, OADymPPaCGenerator.USE_WEIGHT);
	}
}
