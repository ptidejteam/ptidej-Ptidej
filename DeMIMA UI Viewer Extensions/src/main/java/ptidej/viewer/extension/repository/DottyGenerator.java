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
import padl.kernel.IPackageGhost;
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.visitor.IGenerator;
import ptidej.ui.IVisibility;
import util.io.ProxyConsole;

/**
 * @author 	Yann-Gaël Guéhéneuc 
 * @version	0.1
 * @since	2003/08/06
 */
public final class DottyGenerator implements IGenerator {
	private static String convertName(final String entityName) {
		return entityName.replace('.', '_');
	}

	private Map aggregations = new HashMap();
	private Map associations = new HashMap();
	private final StringBuffer buffer = new StringBuffer();
	private Map compositions = new HashMap();
	private Map creations = new HashMap();
	private String currentEntityName;
	private Map uses = new HashMap();
	private final int visibleElements;
	private boolean displayRelationshipsDetails;

	public DottyGenerator(
		final int visibleElements,
		final boolean shouldDisplayRelationshipsDetails) {

		this.visibleElements = visibleElements;
		this.displayRelationshipsDetails = shouldDisplayRelationshipsDetails;
	}
	private void addHierarchyRelationship(
		final Iterator anIteratorOnHierarchyRelationships,
		final String label) {

		while (anIteratorOnHierarchyRelationships.hasNext()) {
			final IFirstClassEntity targetEntity =
				(IFirstClassEntity) anIteratorOnHierarchyRelationships.next();

			if (!(targetEntity instanceof IGhost)
					|| (this.visibleElements & IVisibility.GHOST_ENTITIES_DISPLAY) == IVisibility.GHOST_ENTITIES_DISPLAY) {

				this.buffer.append('\t');
				this.buffer.append(this.currentEntityName);
				this.buffer.append(" -> ");
				this.buffer.append(DottyGenerator.convertName(targetEntity
					.getDisplayName()));
				this.buffer.append(" [label=\"");
				this.buffer.append(label);
				this.buffer.append("\"];\n");
			}
		}
	}
	private void addRelationship(
		final Map relationships,
		final String specialNodeAttributes) {

		final Iterator targetNameIterator = relationships.keySet().iterator();
		while (targetNameIterator.hasNext()) {
			final String targetName = (String) targetNameIterator.next();
			final StringBuffer relationshipNames =
				(StringBuffer) relationships.get(targetName);

			this.buffer.append('\t');
			this.buffer.append(this.currentEntityName);
			this.buffer.append(" -> ");
			this.buffer.append(DottyGenerator.convertName(targetName));
			this.buffer.append(" [label=\"");
			this.buffer.append(relationshipNames);
			this.buffer.append("\"");
			this.buffer.append(specialNodeAttributes);
			this.buffer.append("];\n");
		}
	}
	private void addRelationshipName(
		final IUseRelationship relationship,
		final Map relationships) {

		final IFirstClassEntity targetEntity = relationship.getTargetEntity();
		final String targetEntityName = targetEntity.getDisplayName();

		if (!(targetEntity instanceof IGhost)
				|| (this.visibleElements & IVisibility.GHOST_ENTITIES_DISPLAY) == IVisibility.GHOST_ENTITIES_DISPLAY) {

			if (this.displayRelationshipsDetails) {
				final StringBuffer relationshipNames;
				if (relationships.containsKey(targetEntityName)) {
					relationshipNames =
						(StringBuffer) relationships.get(targetEntityName);
					relationshipNames.append(" and:\\n");
				}
				else {
					relationshipNames = new StringBuffer();

					final String relationshipName =
						relationship.getClass().getName();
					relationshipNames.append(relationshipName
						.substring(relationshipName.lastIndexOf('.') + 1));
					relationshipNames.append(" through:\\n");

					relationships.put(targetEntityName, relationshipNames);
				}
				relationshipNames.append(relationship.getName());
				relationshipNames.append("\\n");
			}
			else {
				final StringBuffer relationshipNames;
				if (relationships.containsKey(targetEntityName)) {
					relationshipNames =
						(StringBuffer) relationships.get(targetEntityName);
					relationshipNames.append(' ');
				}
				else {
					relationshipNames = new StringBuffer();
					relationships.put(targetEntityName, relationshipNames);
				}
				final String relationshipName =
					relationship.getClass().getName();
				relationshipNames.append(relationshipName
					.substring(relationshipName.lastIndexOf('.') + 1));
			}
		}
	}
	public void close(final IAbstractModel p) {
		this.buffer.append("\n}");
	}
	public void close(final IClass p) {
		this.close((IFirstClassEntity) p);
	}
	public void close(final IConstructor aConstructor) {
	}
	public void close(final IDelegatingMethod aDelegatingMethod) {
	}
	private void close(final IFirstClassEntity firstClassEntity) {
		this.addRelationship(this.associations, "");
		this.addRelationship(this.aggregations, "");
		this.addRelationship(this.compositions, ",style=bold");
		this.addRelationship(this.creations, ",style=dotted");
		this.addRelationship(this.uses, "");

		this.aggregations.clear();
		this.associations.clear();
		this.compositions.clear();
		this.creations.clear();
		this.uses.clear();
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
	public void close(final IPackageDefault p) {
	}
	public void close(final IPackageGhost aPackageGhost) {
	}
	public void close(final ISetter aSetter) {
	}
	public String getCode() {
		return this.buffer.toString();
	}
	public String getName() {
		return "Dotty";
	}
	public void open(final IAbstractModel p) {
		this.buffer.append("digraph G {\n");
	}
	public void open(final IClass p) {
		this.open((IFirstClassEntity) p);

		if ((this.visibleElements & IVisibility.HIERARCHY_DISPLAY_ELEMENTS) == IVisibility.HIERARCHY_DISPLAY_ELEMENTS) {

			this.addHierarchyRelationship(
				p.getIteratorOnImplementedInterfaces(),
				"Implements");
			this.addHierarchyRelationship(
				p.getIteratorOnInheritedEntities(),
				"Inherits");
		}
	}
	public void open(final IConstructor p) {
	}
	public void open(final IDelegatingMethod p) {
	}
	private void open(final IFirstClassEntity anEntity) {
		this.currentEntityName =
			DottyGenerator.convertName(anEntity.getDisplayName());

		this.buffer.append('\t');
		this.buffer.append(this.currentEntityName);
		if (anEntity instanceof IGhost) {
			this.buffer.append(" [shape=box,label=\"");
			this.buffer.append(anEntity.getName());
			this.buffer.append("\",color=lightgray];\n");
		}
		else {
			this.buffer.append(" [shape=box,label=\"");
			this.buffer.append(anEntity.getName());
			this.buffer.append("\"];\n");
		}
	}
	public void open(final IGetter p) {
	}
	public void open(final IGhost p) {
		if ((this.visibleElements & IVisibility.GHOST_ENTITIES_DISPLAY) == IVisibility.GHOST_ENTITIES_DISPLAY) {
			this.open((IFirstClassEntity) p);
		}
	}
	public void open(final IInterface p) {
		this.open((IFirstClassEntity) p);

		if ((this.visibleElements & IVisibility.HIERARCHY_DISPLAY_ELEMENTS) == IVisibility.HIERARCHY_DISPLAY_ELEMENTS) {
			this.addHierarchyRelationship(
				p.getIteratorOnInheritedEntities(),
				"Inherits");
		}
	}
	public void open(final IMemberClass p) {
		this.open((IClass) p);
	}
	public void open(final IMemberGhost p) {
		this.open((IClass) p);
	}
	public void open(final IMemberInterface p) {
		this.open((IInterface) p);
	}
	public void open(final IMethod p) {
	}
	public void open(final IPackage p) {
	}
	public void open(final IPackageDefault p) {
	}
	public void open(final IPackageGhost aPackageGhost) {
	}
	public void open(final ISetter p) {
	}
	public void reset() {
		this.buffer.setLength(0);
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
		if ((this.visibleElements & IVisibility.AGGREGATION_DISPLAY_ELEMENTS) == IVisibility.AGGREGATION_DISPLAY_ELEMENTS) {
			this.addRelationshipName(p, this.aggregations);
		}
	}
	public void visit(final IAssociation p) {
		if ((this.visibleElements & IVisibility.ASSOCIATION_DISPLAY_ELEMENTS) == IVisibility.ASSOCIATION_DISPLAY_ELEMENTS) {
			this.addRelationshipName(p, this.associations);
		}
	}
	public void visit(final IComposition p) {
		if ((this.visibleElements & IVisibility.COMPOSITION_DISPLAY_ELEMENTS) == IVisibility.COMPOSITION_DISPLAY_ELEMENTS) {
			this.addRelationshipName(p, this.compositions);
		}
	}
	public void visit(final IContainerAggregation p) {
		if ((this.visibleElements & IVisibility.CONTAINER_AGGREGATION_DISPLAY_ELEMENTS) == IVisibility.CONTAINER_AGGREGATION_DISPLAY_ELEMENTS) {
			this.addRelationshipName(p, this.aggregations);
		}
	}
	public void visit(final IContainerComposition p) {
		if ((this.visibleElements & IVisibility.CONTAINER_COMPOSITION_DISPLAY_ELEMENTS) == IVisibility.CONTAINER_COMPOSITION_DISPLAY_ELEMENTS) {
			this.addRelationshipName(p, this.compositions);
		}
	}
	public void visit(final ICreation p) {
		if ((this.visibleElements & IVisibility.CREATION_DISPLAY_ELEMENTS) == IVisibility.CREATION_DISPLAY_ELEMENTS) {
			this.addRelationshipName(p, this.creations);
		}
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
		if ((this.visibleElements & IVisibility.USE_DISPLAY_ELEMENTS) == IVisibility.USE_DISPLAY_ELEMENTS) {
			this.addRelationshipName(p, this.uses);
		}
	}
}
