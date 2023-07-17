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
package ptidej.ui.kernel.builder;

import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;
import padl.kernel.IAggregation;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IComposition;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentOfEntity;
import padl.kernel.IConstituentOfModel;
import padl.kernel.IConstituentOfOperation;
import padl.kernel.IConstructor;
import padl.kernel.IContainerAggregation;
import padl.kernel.IContainerComposition;
import padl.kernel.ICreation;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.IInterface;
import padl.kernel.IMemberClass;
import padl.kernel.IMemberEntity;
import padl.kernel.IMemberInterface;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import padl.kernel.IParameter;
import padl.kernel.IRelationship;
import padl.kernel.IStatement;
import padl.kernel.IUseRelationship;
import padl.util.Util;
import ptidej.ui.Utils;
import ptidej.ui.kernel.Aggregation;
import ptidej.ui.kernel.Association;
import ptidej.ui.kernel.Class;
import ptidej.ui.kernel.Composition;
import ptidej.ui.kernel.Constructor;
import ptidej.ui.kernel.ContainerAggregation;
import ptidej.ui.kernel.ContainerComposition;
import ptidej.ui.kernel.Creation;
import ptidej.ui.kernel.Delegation;
import ptidej.ui.kernel.Element;
import ptidej.ui.kernel.Entity;
import ptidej.ui.kernel.Field;
import ptidej.ui.kernel.Ghost;
import ptidej.ui.kernel.Interface;
import ptidej.ui.kernel.Method;
import ptidej.ui.kernel.Use;
import ptidej.ui.primitive.IPrimitiveFactory;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/12/16
 */
public class Builder {
	private static Map UniqueInstances;
	public static Builder getCurrentBuilder(
		final IPrimitiveFactory aPrimitiveFactory) {
		if (Builder.UniqueInstances == null) {
			Builder.UniqueInstances = new HashMap();
		}
		if (Builder.UniqueInstances.get(aPrimitiveFactory) == null) {
			Builder.UniqueInstances
				.put(aPrimitiveFactory, new Builder(aPrimitiveFactory));
		}
		return (Builder) Builder.UniqueInstances.get(aPrimitiveFactory);
	}

	private final Map cacheOfGraphicalConstituents = new HashMap();
	private final Map cacheOfLabelIcons = new HashMap();
	private final Map cacheOfLabelTexts = new HashMap();
	private final IPrimitiveFactory primitiveFactory;

	protected Builder(final IPrimitiveFactory aPrimitiveFactory) {
		this.primitiveFactory = aPrimitiveFactory;
	}

	private final Element createDefaultElement(
		final IConstituentOfModel anEntity,
		final IConstituentOfEntity anElement) {

		final Entity anOriginGraphicalEntity =
			(Entity) this.getEntity(anEntity);
		final IFirstClassEntity aTargetEntity;
		final Entity aTargetGraphicalEntity;

		Element aGraphicalElement = null;
		if (anElement instanceof IDelegatingMethod) {
			aTargetEntity = ((IDelegatingMethod) anElement)
				.getTargetAssoc()
				.getTargetEntity();

			// Yann 01/07/31: Hack!
			// Because a subset of a pattern may have elements with invalid targets.
			if (aTargetEntity != null) {
				aTargetGraphicalEntity = (Entity) this.getEntity(aTargetEntity);

				if (aTargetGraphicalEntity != null) {
					aGraphicalElement = new Delegation(
						this.primitiveFactory,
						this.getEntity(anEntity),
						aTargetGraphicalEntity,
						(IDelegatingMethod) anElement);
				}
			}
		}
		else if (anElement instanceof IAssociation) {
			final IAssociation association = (IAssociation) anElement;
			aTargetEntity = association.getTargetEntity();

			// Yann 01/07/31: Hack!
			// Because a subset of a pattern may have elements with invalid targets.
			if (aTargetEntity != null) {
				aTargetGraphicalEntity = this.getEntity(aTargetEntity);

				// Yann 2004/12/16: Possible?
				// Are these two conditions still necessary?
				//	&& origin != null
				//	&& aTargetGraphicalEntity != null
				if (anOriginGraphicalEntity != aTargetGraphicalEntity) {
					if (anElement instanceof IComposition) {
						aGraphicalElement = new Composition(
							this.primitiveFactory,
							((IComposition) anElement).getDisplayName(),
							((IComposition) anElement).getCardinality(),
							anOriginGraphicalEntity,
							aTargetGraphicalEntity);
					}
					else if (anElement instanceof IAggregation) {
						aGraphicalElement = new Aggregation(
							this.primitiveFactory,
							((IAggregation) anElement).getDisplayName(),
							((IAggregation) anElement).getCardinality(),
							anOriginGraphicalEntity,
							aTargetGraphicalEntity);
					}
					else if (anElement instanceof IContainerComposition) {
						aGraphicalElement = new ContainerComposition(
							this.primitiveFactory,
							((IContainerComposition) anElement)
								.getDisplayName(),
							((IContainerComposition) anElement)
								.getCardinality(),
							anOriginGraphicalEntity,
							aTargetGraphicalEntity);
					}
					else if (anElement instanceof IContainerAggregation) {
						aGraphicalElement = new ContainerAggregation(
							this.primitiveFactory,
							((IContainerAggregation) anElement)
								.getDisplayName(),
							((IContainerAggregation) anElement)
								.getCardinality(),
							anOriginGraphicalEntity,
							aTargetGraphicalEntity);
					}
					else if (anElement instanceof IAssociation) {
						aGraphicalElement = new Association(
							this.primitiveFactory,
							((IAssociation) anElement).getDisplayName(),
							((IAssociation) anElement).getCardinality(),
							anOriginGraphicalEntity,
							aTargetGraphicalEntity);
					}
				}
			}
		}
		// Yann 2007/03/31: Orgin != Target
		// For some reason, the origin of the
		// creations and uses where set to be
		// their targets...
		else if (anElement instanceof ICreation) {
			aTargetEntity = ((ICreation) anElement).getTargetEntity();

			// Yann 2001/07/31: Hack!
			// Because a subset of a pattern may have elements with invalid targets.
			if (aTargetEntity != null) {
				aTargetGraphicalEntity = this.getEntity(aTargetEntity);

				if (aTargetGraphicalEntity != null) {
					aGraphicalElement = new Creation(
						this.primitiveFactory,
						anOriginGraphicalEntity,
						((ICreation) anElement).getDisplayName(),
						aTargetGraphicalEntity);
				}
			}
		}
		else if (anElement instanceof IUseRelationship) {
			aTargetEntity = ((IUseRelationship) anElement).getTargetEntity();

			// Yann 2001/07/31: Hack!
			// Because a subset of a pattern may have elements with invalid targets.
			if (aTargetEntity != null) {
				aTargetGraphicalEntity = this.getEntity(aTargetEntity);

				if (aTargetGraphicalEntity != null) {
					aGraphicalElement = new Use(
						this.primitiveFactory,
						anOriginGraphicalEntity,
						((IUseRelationship) anElement).getDisplayName(),
						aTargetGraphicalEntity);
				}
			}
		}
		else if (anElement instanceof IMethod) {
			aGraphicalElement =
				new Method(this.primitiveFactory, (IMethod) anElement);
		}
		else if (anElement instanceof IConstructor) {
			aGraphicalElement = new Constructor(
				this.primitiveFactory,
				(IConstructor) anElement);
		}
		else if (anElement instanceof IField) {
			aGraphicalElement =
				new Field(this.primitiveFactory, (IField) anElement);
		}
		else {
			aGraphicalElement = this.createElement(anEntity, anElement);
		}

		return aGraphicalElement;
	}
	private final Entity createDefaultEntity(
		final IConstituentOfModel anEntity) {
		final Entity aGraphicalEntity;

		if (anEntity instanceof IClass) {
			aGraphicalEntity =
				new Class(this.primitiveFactory, this, (IClass) anEntity);
		}
		else if (anEntity instanceof IGhost) {
			aGraphicalEntity =
				new Ghost(this.primitiveFactory, this, (IGhost) anEntity);
		}
		else if (anEntity instanceof IInterface) {
			aGraphicalEntity = new Interface(
				this.primitiveFactory,
				this,
				(IInterface) anEntity);
		}
		else {
			// Yann 2004/16/12: Null!
			// This should never happens!
			// Yann 2014/06/20: Template Method!
			// Let us see if my subclasses know what to do...
			aGraphicalEntity = this.createEntity(anEntity);
		}

		return aGraphicalEntity;
	}
	private final Icon createDefaultLabelIcon(final IConstituent aConstituent) {
		String iconImageName = null;

		if (aConstituent instanceof IField) {
			if (aConstituent.isPrivate()) {
				iconImageName = "Field_Private.gif";
			}
			else if (aConstituent.isProtected()) {
				iconImageName = "Field_Protected.gif";
			}
			else if (aConstituent.isPublic()) {
				iconImageName = "Field_Public.gif";
			}
			else {
				iconImageName = "Field_Default.gif";
			}
		}
		else if (aConstituent instanceof IOperation) {
			if (aConstituent.isPrivate()) {
				iconImageName = "Method_Private.gif";
			}
			else if (aConstituent.isProtected()) {
				iconImageName = "Method_Protected.gif";
			}
			else if (aConstituent.isPublic()) {
				iconImageName = "Method_Public.gif";
			}
			else {
				iconImageName = "Method_Default.gif";
			}
		}
		else if (aConstituent instanceof IMemberClass) {
			if (aConstituent.isPrivate()) {
				iconImageName = "Class_Private.gif";
			}
			else if (aConstituent.isProtected()) {
				iconImageName = "Class_Protected.gif";
			}
			else if (aConstituent.isPublic()) {
				iconImageName = "Class_Public.gif";
			}
			else {
				iconImageName = "Class_Default.gif";
			}
		}
		else if (aConstituent instanceof IMemberInterface) {
			if (aConstituent.isPrivate()) {
				iconImageName = "Interface_Private.gif";
			}
			else if (aConstituent.isProtected()) {
				iconImageName = "Interface_Protected.gif";
			}
			else if (aConstituent.isPublic()) {
				iconImageName = "Interface_Public.gif";
			}
			else {
				iconImageName = "Interface_Default.gif";
			}
		}
		else if (aConstituent instanceof IRelationship) {
			iconImageName = "Relationship.gif";
		}
		else if (aConstituent instanceof IClass) {
			iconImageName = "Class.gif";
		}
		else if (aConstituent instanceof IInterface) {
			iconImageName = "Interface.gif";
		}
		else if (aConstituent instanceof IGhost) {
			iconImageName = "Ghost.gif";
		}
		else if (aConstituent instanceof IMethodInvocation
				|| aConstituent instanceof IStatement) {

			iconImageName = "Statement.gif";
		}
		else if (aConstituent instanceof IParameter) {
			iconImageName = "Parameter.gif";
		}

		if (iconImageName == null) {
			return this.createLabelIcon(aConstituent);
		}
		else {
			return Utils.getIcon(iconImageName);
		}
	}
	private final String createDefaultLabelText(
		final IConstituent aConstituent) {
		String labelText;
		if (aConstituent instanceof IField) {
			labelText = ((IField) aConstituent).getDisplayTypeName() + " : "
					+ aConstituent.getDisplayName();
		}
		else if (aConstituent instanceof IMethodInvocation) {
			final IMethodInvocation invocation =
				(IMethodInvocation) aConstituent;
			if (invocation.getTargetEntity() == null) {
				final IField field = invocation.getFirstCallingField();
				if (field == null) {
					labelText = "... = ...";
				}
				else {
					labelText = field.getDisplayName() + " = ...";
				}
			}
			else {
				labelText = invocation.getTargetEntity().getDisplayID();
				if (invocation.getFirstCallingField() != null) {
					labelText += "." + invocation
						.getFirstCallingField()
						.getDisplayName();
				}
				if (invocation.getCalledMethod() != null) {
					labelText +=
						"." + invocation.getCalledMethod().getDisplayName();
				}
			}
		}
		else if (aConstituent instanceof IParameter) {
			labelText = ((IParameter) aConstituent).getDisplayTypeName();
		}
		else if (aConstituent instanceof IFirstClassEntity) {
			labelText = aConstituent.getDisplayID();
		}
		else if (aConstituent instanceof IOperation) {
			labelText = aConstituent.toString().substring(
				0,
				aConstituent.toString().lastIndexOf(')') + 1);
		}
		else if (aConstituent instanceof IStatement) {
			labelText =
				Util.computeSimpleName(aConstituent.getClass().getName());
		}
		else if (aConstituent instanceof IRelationship) {
			labelText = aConstituent.getDisplayName();
		}
		else {
			labelText = this.createLabelText(aConstituent);
		}

		return labelText;
	}
	private final Element createDefaultStatement(
		final IConstituentOfModel anEntity,
		final IConstituentOfOperation aStatement) {

		final Entity anOriginGraphicalEntity =
			(Entity) this.getEntity(anEntity);
		final IFirstClassEntity aTargetEntity;
		final Entity aTargetGraphicalEntity;

		Element aGraphicalStatement = null;
		if (aStatement instanceof IMethodInvocation) {
			aTargetEntity = ((IMethodInvocation) aStatement).getTargetEntity();

			// Yann 2001/07/31: Hack!
			// Because a subset of a pattern may have elements with invalid targets.
			// Yann 2013/07/27: C++ global functions
			// I treat IMethodInvocations as IUseRelationships.
			if (aTargetEntity != null) {
				aTargetGraphicalEntity = this.getEntity(aTargetEntity);

				if (aTargetGraphicalEntity != null) {
					aGraphicalStatement = new Use(
						this.primitiveFactory,
						anOriginGraphicalEntity,
						((IMethodInvocation) aStatement).getDisplayName(),
						aTargetGraphicalEntity);
				}
			}
		}
		else {
			aGraphicalStatement = this.createStatement(anEntity, aStatement);
		}

		return aGraphicalStatement;
	}
	private final Element createElement(
		final IConstituentOfModel anEntity,
		final IConstituentOfEntity anElement) {

		return null;
	}
	protected Entity createEntity(final IConstituentOfModel anEntity) {
		return null;
	}
	protected Icon createLabelIcon(final IConstituent aConstituent) {
		return null;
	}
	protected String createLabelText(final IConstituent aConstituent) {
		return aConstituent.getDisplayName();
	}
	protected Element createStatement(
		final IConstituentOfModel anEntity,
		final IConstituentOfOperation aStatement) {

		return null;
	}
	public final Element getElement(
		final IConstituentOfModel anEntity,
		final IConstituentOfEntity anElement) {

		final String id = anElement.getDisplayPath();
		if (this.cacheOfGraphicalConstituents.get(id) == null) {
			this.cacheOfGraphicalConstituents
				.put(id, this.createDefaultElement(anEntity, anElement));
		}
		return (Element) this.cacheOfGraphicalConstituents.get(id);
	}
	public final Element getElement(
		final IConstituentOfModel anEntity,
		final IConstituentOfOperation aStatement) {

		final String id = aStatement.getDisplayPath();
		if (this.cacheOfGraphicalConstituents.get(id) == null) {
			this.cacheOfGraphicalConstituents
				.put(id, this.createDefaultStatement(anEntity, aStatement));
		}
		return (Element) this.cacheOfGraphicalConstituents.get(id);
	}
	public final Entity getEntity(final IConstituentOfModel anEntity) {
		String id = anEntity.getDisplayPath();

		// Yann 2016/09/26: Members!
		// The graphical library does not support member entities,
		// it has not constituents for these. So, if something
		// (say, an association) points to a member entity, I get
		// the enclosing first-class entity of the member entity.
		if (anEntity instanceof IMemberEntity) {
			// TODO This is a hack! because the Builder should not
			// know of the peculiarities of the path system! It
			// should call:
			//	id = Finder.findContainer(id, this.abstractLevelModel);
			// but the Builder currently does not know about the model.
			id = id.substring(0, id.indexOf('$'));
		}

		if (this.cacheOfGraphicalConstituents.get(id) == null) {
			this.cacheOfGraphicalConstituents
				.put(id, this.createDefaultEntity(anEntity));
		}
		return (Entity) this.cacheOfGraphicalConstituents.get(id);
	}
	public final Icon getLabelIcon(final IConstituent aConstituent) {
		final String id = aConstituent.getDisplayPath();
		if (this.cacheOfLabelIcons.get(id) == null) {
			this.cacheOfLabelIcons
				.put(id, this.createDefaultLabelIcon(aConstituent));
		}
		return (Icon) this.cacheOfLabelIcons.get(id);
	}
	public final String getLabelText(final IConstituent aConstituent) {
		final String id = aConstituent.getDisplayPath();
		if (this.cacheOfLabelTexts.get(id) == null) {
			this.cacheOfLabelTexts
				.put(id, this.createDefaultLabelText(aConstituent));
		}
		return (String) this.cacheOfLabelTexts.get(id);
	}
	public final IPrimitiveFactory getPrimitiveFactory() {
		return this.primitiveFactory;
	}

	public final void initialize() {
		this.cacheOfGraphicalConstituents.clear();
	}
}
