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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
import padl.kernel.IElement;
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
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.IRelationship;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.visitor.IGenerator;
import ptidej.ui.IVisibility;
import util.io.ProxyConsole;
import util.lang.Modifier;

/**
 * @version	0.1
 * @author 	Yann-Gaël Guéhéneuc
 * @since	2002/09/17
 */
public final class UMLScriptGenerator implements IGenerator {
	private static String computeMethodName(
		final IFirstClassEntity firstClassEntity,
		final IOperation method) {

		if (method.getDisplayName().startsWith("<")) {
			return UMLScriptGenerator.convertName(firstClassEntity
				.getDisplayName());
		}
		else {
			return UMLScriptGenerator.convertName(method.getDisplayName());
		}
	}
	private static String computeMethodName(
		final IFirstClassEntity firstClassEntity,
		final IRelationship relationship) {

		final String name = relationship.getDisplayName();
		if (name.startsWith("<")) {
			return UMLScriptGenerator.convertName(firstClassEntity
				.getDisplayName()) + name.substring(name.lastIndexOf('>') + 1);
		}
		else {
			return UMLScriptGenerator.convertName(name);
		}
	}
	private static String computeVisibility(final IElement anElement) {
		final StringBuffer visibility = new StringBuffer(4);

		if ((anElement.getVisibility() & Modifier.PUBLIC) == Modifier.PUBLIC) {

			visibility.append("+ ");
		}
		else if ((anElement.getVisibility() & Modifier.PROTECTED) == Modifier.PROTECTED) {

			visibility.append("# ");
		}
		else if ((anElement.getVisibility() & Modifier.PRIVATE) == Modifier.PRIVATE) {

			visibility.append("- ");
		}

		if ((anElement.getVisibility() & Modifier.STATIC) == Modifier.STATIC) {

			visibility.append("$ ");
		}

		return visibility.toString();
	}
	private static String convertName(final String entityName) {
		return entityName.replace('$', '.');
	}
	private static String convertType(final String javaType) {
		final StringBuffer buffer = new StringBuffer(javaType.length());
		int index = 0;
		while ((index = javaType.indexOf('[', index + 1)) != -1) {
			buffer.append("array [*] of ");
		}
		index = javaType.indexOf('[');
		if (index == -1) {
			index = javaType.length();
		}
		buffer.append(UMLScriptGenerator.convertName(javaType).substring(
			0,
			index));

		return buffer.toString();
	}
	private static List findAllDirectSubEntities(
		final IAbstractModel model,
		final IFirstClassEntity superEntity) {

		final List listOfSubEntities = new ArrayList();
		Iterator iterator = superEntity.getIteratorOnInheritingEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iterator.next();
			listOfSubEntities.add(firstClassEntity);
		}
		if (iterator instanceof IInterface) {
			iterator =
				((IInterface) superEntity).getIteratorOnImplementingClasses();
			while (iterator.hasNext()) {
				final IFirstClassEntity firstClassEntity =
					(IFirstClassEntity) iterator.next();
				listOfSubEntities.add(firstClassEntity);
			}
		}

		return listOfSubEntities;
	}

	private final StringBuffer buffer = new StringBuffer();
	private IAbstractModel currentModel;
	private final int visibleElements;

	public UMLScriptGenerator(final int visibleElements) {
		this.visibleElements = visibleElements;
	}
	private boolean checkTarget(final IFirstClassEntity firstClassEntity) {
		if (firstClassEntity instanceof IGhost) {
			return (this.visibleElements & IVisibility.GHOST_ENTITIES_DISPLAY) == IVisibility.GHOST_ENTITIES_DISPLAY;
		}
		return true;
	}
	public void close(final IAbstractModel p) {
		this.buffer.append("\nEND DIAGRAM");
	}
	public void close(final IClass p) {
		this.buffer.append("\n\tCLASS { *backgroundColor = 255255204 } ");
		this.buffer.append(UMLScriptGenerator.convertName(p.getDisplayName()));
		this.buffer.append('\n');

		this.generateRelations(p);
	}
	public void close(final IConstructor aConstructor) {
	}
	public void close(final IDelegatingMethod aDelegatingMethod) {
	}
	public void close(final IGetter aGetter) {
	}
	public void close(final IGhost p) {
		if ((this.visibleElements & IVisibility.GHOST_ENTITIES_DISPLAY) == IVisibility.GHOST_ENTITIES_DISPLAY) {
			this.buffer.append("\n\tCLASS { *Color = gray } ");
			this.buffer.append(UMLScriptGenerator.convertName(p
				.getDisplayName()));
			this.buffer.append('\n');

			this.generateRelations(p);
		}
	}
	public void close(final IInterface p) {
		this.buffer
			.append("\n\tCLASS << Interface >> { *backgroundColor = 255255204 } ");
		this.buffer.append(UMLScriptGenerator.convertName(p.getDisplayName()));
		this.buffer.append('\n');

		this.generateRelations(p);
	}
	public void close(final IMemberClass p) {
		this.close((IClass) p);
	}
	public void close(final IMemberGhost p) {
		this.close((IClass) p);
	}
	public void close(final IMemberInterface p) {
		this.close((IInterface) p);
	}
	public void close(final IMethod aMethod) {
	}
	public void close(final IPackage p) {
	}
	public void close(final IPackageDefault p) {
	}
	public void close(final ISetter aSetter) {
	}
	private void generateRelations(final IFirstClassEntity firstClassEntity) {
		final StringBuffer attributes = new StringBuffer(0);
		final StringBuffer operations = new StringBuffer(0);
		// Yann 2003/07/16: Unicity!
		// I use this set to single out one relation
		// out of the many equivalent exisiting relations.
		// (Two relations are equivalent if they originate
		// from a same entity, target a same entity, and
		// are of the same type.)
		// If a relation already exists in the set, then
		// the method Set.add(Object) returns false...
		final Set relations = new HashSet(0);
		final StringBuffer uniqueRelations = new StringBuffer(0);
		Iterator iterator;

		iterator = firstClassEntity.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			final IElement element = (IElement) iterator.next();

			if (element instanceof IField
					&& (this.visibleElements & IVisibility.FIELD_NAMES) == IVisibility.FIELD_NAMES) {

				final IField field = (IField) element;
				if (attributes.length() > 0) {
					attributes.append(",\n");
				}
				attributes.append("\t\t\t");
				attributes.append(UMLScriptGenerator.computeVisibility(field));
				attributes.append(UMLScriptGenerator.convertName(field
					.getDisplayName()));
				attributes.append(" : ");
				attributes.append(UMLScriptGenerator.convertType(field
					.getDisplayTypeName()));
			}
			else if (element instanceof IOperation
					&& (this.visibleElements & IVisibility.METHOD_NAMES) == IVisibility.METHOD_NAMES) {

				final IOperation method = (IOperation) element;
				if (operations.length() > 0) {
					operations.append(",\n");
				}
				operations.append("\t\t\t");
				operations.append(UMLScriptGenerator.computeVisibility(method));
				operations.append(UMLScriptGenerator.computeMethodName(
					firstClassEntity,
					method));
				operations.append('(');
				// Yann 2005/10/12: Iterator!
				// I have now an iterator able to iterate over a
				// specified type of constituent of a list.
				final Iterator elements =
					method.getIteratorOnConstituents(IParameter.class);
				while (elements.hasNext()) {
					final IParameter parameter = (IParameter) elements.next();
					operations.append(parameter.getName());
					operations.append(" : ");
					operations.append(UMLScriptGenerator.convertType(parameter
						.getDisplayTypeName()));
					if (elements.hasNext()) {
						operations.append(", ");
					}
				}
				operations.append(')');
				if (element instanceof IMethod) {
					operations.append(" : ");
					operations
						.append(UMLScriptGenerator
							.convertType(((IMethod) method)
								.getDisplayReturnType()));
				}
			}
			else if (element instanceof IContainerComposition
					&& (this.visibleElements & IVisibility.CONTAINER_COMPOSITION_DISPLAY_ELEMENTS) == IVisibility.CONTAINER_COMPOSITION_DISPLAY_ELEMENTS) {

				final IContainerComposition containerComposition =
					(IContainerComposition) element;

				if (this.checkTarget(containerComposition.getTargetEntity())
						&& relations.add("ContainerComposition to "
								+ containerComposition.getTargetEntity())) {

					uniqueRelations.append("\t\t\t");
					uniqueRelations.append("<< Container >> ASSOC TO ");
					if (containerComposition.getCardinality() > 1) {
						uniqueRelations.append("[*] ");
					}
					uniqueRelations.append("composite ROLE ");
					uniqueRelations.append(UMLScriptGenerator
						.computeMethodName(
							firstClassEntity,
							containerComposition));
					uniqueRelations.append(" '");
					uniqueRelations.append(UMLScriptGenerator
						.convertName(containerComposition
							.getTargetEntity()
							.getDisplayName()));
					uniqueRelations.append("'\n");
				}
			}
			else if (element instanceof IContainerAggregation
					&& (this.visibleElements & IVisibility.CONTAINER_AGGREGATION_DISPLAY_ELEMENTS) == IVisibility.CONTAINER_AGGREGATION_DISPLAY_ELEMENTS) {

				final IContainerAggregation containerAggregation =
					(IContainerAggregation) element;

				if (this.checkTarget(containerAggregation.getTargetEntity())
						&& relations.add("ContainerAggregation to "
								+ containerAggregation.getTargetEntity())) {

					uniqueRelations.append("\t\t\t");
					uniqueRelations.append("<< Container >> ASSOC TO ");
					if (containerAggregation.getCardinality() > 1) {
						uniqueRelations.append("[*] ");
					}
					uniqueRelations.append("aggregate ROLE ");
					uniqueRelations.append(UMLScriptGenerator
						.computeMethodName(
							firstClassEntity,
							containerAggregation));
					uniqueRelations.append(" '");
					uniqueRelations.append(UMLScriptGenerator
						.convertName(containerAggregation
							.getTargetEntity()
							.getDisplayName()));
					uniqueRelations.append("'\n");
				}
			}
			else if (element instanceof IComposition
					&& (this.visibleElements & IVisibility.COMPOSITION_DISPLAY_ELEMENTS) == IVisibility.COMPOSITION_DISPLAY_ELEMENTS) {

				final IComposition composition = (IComposition) element;

				if (this.checkTarget(composition.getTargetEntity())
						&& relations.add("Composition to "
								+ composition.getTargetEntity())) {

					uniqueRelations.append("\t\t\t");
					uniqueRelations.append("ASSOC TO ");
					if (composition.getCardinality() > 1) {
						uniqueRelations.append("[*] ");
					}
					uniqueRelations.append("composite ROLE ");
					uniqueRelations.append(UMLScriptGenerator
						.computeMethodName(firstClassEntity, composition));
					uniqueRelations.append(" '");
					uniqueRelations.append(UMLScriptGenerator
						.convertName(composition
							.getTargetEntity()
							.getDisplayName()));
					uniqueRelations.append("'\n");
				}
			}
			else if (element instanceof IAggregation
					&& (this.visibleElements & IVisibility.AGGREGATION_DISPLAY_ELEMENTS) == IVisibility.AGGREGATION_DISPLAY_ELEMENTS) {

				final IAggregation aggregation = (IAggregation) element;

				if (this.checkTarget(aggregation.getTargetEntity())
						&& relations.add("Aggregation to "
								+ aggregation.getTargetEntity())) {

					uniqueRelations.append("\t\t\t");
					uniqueRelations.append("ASSOC TO ");
					if (aggregation.getCardinality() > 1) {
						uniqueRelations.append("[*] ");
					}
					uniqueRelations.append("aggregate ROLE ");
					uniqueRelations.append(UMLScriptGenerator
						.computeMethodName(firstClassEntity, aggregation));
					uniqueRelations.append(" '");
					uniqueRelations.append(UMLScriptGenerator
						.convertName(aggregation
							.getTargetEntity()
							.getDisplayName()));
					uniqueRelations.append("'\n");
				}
			}
			else if (element instanceof IAssociation
					&& (this.visibleElements & IVisibility.ASSOCIATION_DISPLAY_ELEMENTS) == IVisibility.ASSOCIATION_DISPLAY_ELEMENTS) {

				final IAssociation association = (IAssociation) element;

				if (this.checkTarget(association.getTargetEntity())
						&& relations.add("Association to "
								+ association.getTargetEntity())) {

					uniqueRelations.append("\t\t\t");
					uniqueRelations.append("ASSOC TO ");
					if (association.getCardinality() > 1) {
						uniqueRelations.append("[*] ");
					}
					uniqueRelations.append("ROLE ");
					uniqueRelations.append(UMLScriptGenerator
						.computeMethodName(firstClassEntity, association));
					uniqueRelations.append(" '");
					uniqueRelations.append(UMLScriptGenerator
						.convertName(association
							.getTargetEntity()
							.getDisplayName()));
					uniqueRelations.append("'\n");
				}
			}
			else if (element instanceof ICreation
					&& (this.visibleElements & IVisibility.CREATION_DISPLAY_ELEMENTS) == IVisibility.CREATION_DISPLAY_ELEMENTS) {

				final ICreation creation = (ICreation) element;

				if (this.checkTarget(creation.getTargetEntity())
						&& relations.add("Creation of "
								+ creation.getTargetEntity())) {

					uniqueRelations.append("\t\t\t");
					uniqueRelations.append("<< Creation >> DEPENDS ");
					uniqueRelations.append(UMLScriptGenerator
						.computeMethodName(firstClassEntity, creation));
					uniqueRelations.append(" ON '");
					uniqueRelations.append(UMLScriptGenerator
						.convertName(creation
							.getTargetEntity()
							.getDisplayName()));
					uniqueRelations.append("'\n");
				}
			}
			else if (element instanceof IUseRelationship
					&& (this.visibleElements & IVisibility.USE_DISPLAY_ELEMENTS) == IVisibility.USE_DISPLAY_ELEMENTS) {

				final IUseRelationship useRelationship =
					(IUseRelationship) element;

				if (this.checkTarget(useRelationship.getTargetEntity())
						&& relations.add("Use relationship of "
								+ useRelationship.getTargetEntity())) {

					uniqueRelations.append("\t\t\t");
					uniqueRelations.append("<< Use >> DEPENDS ");
					uniqueRelations.append(UMLScriptGenerator
						.computeMethodName(firstClassEntity, useRelationship));
					uniqueRelations.append(" ON '");
					uniqueRelations.append(UMLScriptGenerator
						.convertName(useRelationship
							.getTargetEntity()
							.getDisplayName()));
					uniqueRelations.append("'\n");
				}
			}
		}

		if ((this.visibleElements & IVisibility.HIERARCHY_DISPLAY_ELEMENTS) == IVisibility.HIERARCHY_DISPLAY_ELEMENTS) {

			iterator =
				UMLScriptGenerator.findAllDirectSubEntities(
					this.currentModel,
					firstClassEntity).iterator();
			while (iterator.hasNext()) {
				uniqueRelations.append("\t\t\tGENERALIZE \'");
				uniqueRelations.append(UMLScriptGenerator
					.convertName(((IFirstClassEntity) iterator.next())
						.getDisplayName()));
				uniqueRelations.append("\'\n");
			}
		}

		// I copy the value into the main buffer.
		// These operations are compatible with
		// the JDK 1.4 (and greater) only!
		if (attributes.length() > 0) {
			this.buffer.append("\t\tATTRIBUTES\n");
			this.buffer.append(attributes);
			this.buffer.append('\n');
		}
		if (operations.length() > 0) {
			this.buffer.append("\t\tOPERATIONS\n");
			this.buffer.append(operations);
			this.buffer.append('\n');
		}
		if (uniqueRelations.length() > 0) {
			this.buffer.append("\t\tRELATIONS\n");
			this.buffer.append(uniqueRelations);
			this.buffer.append("\t\tEND\n");
		}
	}
	public String getCode() {
		return this.buffer.toString();
	}
	public String getName() {
		return "UMLScript";
	}
	public void open(final IAbstractModel p) {
		this.buffer
			.append("UMLscript VERSION 1 MINOR 14\n{ *inheritancesize = 10,\n  *associationsize = 100,\n  *backgroundColor = 255255255,\n  *defaultNodeColor = 000000000,\n  *defaultEdgeColor = 000000000,\n  *diamondxsize = 50 }\nDIAGRAM ");
		this.currentModel = p;
	}
	public void open(final IClass p) {
	}
	public void open(final IConstructor p) {
	}
	public void open(final IDelegatingMethod p) {
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
	}
	public void open(final IPackage p) {
	}
	public void open(final IPackageDefault p) {
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
	}
	public void visit(final IAssociation p) {
	}
	public void visit(final IComposition p) {
	}
	public void visit(final IContainerAggregation p) {
	}
	public void visit(final IContainerComposition p) {
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
	}
}
