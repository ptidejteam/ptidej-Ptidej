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
package ptidej.ui.kernel;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import padl.kernel.IConstituentOfEntity;
import padl.kernel.IConstituentOfModel;
import padl.kernel.IConstituentOfOperation;
import padl.kernel.IFirstClassEntity;
import ptidej.ui.Constants;
import ptidej.ui.IVisibility;
import ptidej.ui.RGB;
import ptidej.ui.event.MouseEvent;
import ptidej.ui.kernel.builder.Builder;
import ptidej.ui.primitive.IButton;
import ptidej.ui.primitive.IPrimitiveFactory;

public abstract class Entity extends Constituent implements IVisibility {
	private Builder builder;
	private IButton[] buttons;
	private IFirstClassEntity firstClassEntity;
	private boolean isSuper;
	private final List listOfElements = new ArrayList();
	private final List listOfHierarchies = new ArrayList();
	private int numberOfButtons;

	protected Entity(
		final IPrimitiveFactory aPrimitiveFactory,
		final Builder aBuilder,
		final IConstituentOfModel anEntity) {

		super(aPrimitiveFactory);
		this.builder = aBuilder;
		this.buttons = new IButton[5];
		this.firstClassEntity = (IFirstClassEntity) anEntity;
	}
	protected void addElement(
		final IConstituentOfModel anEntity,
		final IConstituentOfEntity anElement) {

		final Element aGraphicalElement =
			this.builder.getElement(anEntity, anElement);
		if (aGraphicalElement != null) {
			this.listOfElements.add(aGraphicalElement);
		}
	}
	protected void addElement(
		final IConstituentOfModel anEntity,
		final IConstituentOfOperation aStatement) {

		final Element aGraphicalElement =
			this.builder.getElement(anEntity, aStatement);
		if (aGraphicalElement != null) {
			this.listOfElements.add(aGraphicalElement);
		}
	}
	protected void addHierarchy(final AbstractInheritance aHierarchy) {
		((Entity) aHierarchy.getTargetEntity()).isSuper(true);
		this.listOfHierarchies.add(aHierarchy);
	}
	public final void build() {
		// Yann 2007/10/24: Sort!
		// I now sort elements according to their names...
		Collections.sort(this.listOfElements, new Comparator() {
			public int compare(final Object o1, final Object o2) {
				if (o1 instanceof Constituent && o2 instanceof Constituent) {
					return ((Constituent) o1).getName().compareTo(
						((Constituent) o2).getName());
				}
				return 0;
			}
			public boolean equals(final Object obj) {
				return false;
			}
		});

		// Yann 2004/12/16: Flexibility
		// The stereotypes were hard-coded in this class.
		// This was really bad design. I improve the
		// flexibility by delegating the responsibility
		// to the sub-classes.

		final RGB colour;
		if (this instanceof Ghost) {
			colour = Constants.GHOST_ENTITY_DISPLAY_COLOR;
		}
		else {
			colour = Constants.FOREGROUND_COLOR;
		}

		this.numberOfButtons = 0;
		int xPos = this.getPosition().x;
		int yPos = this.getPosition().y;
		int width = 0;
		int height = 0;

		// Yann 2014/04/26: Cleanup!
		// I simplified and streamlines to code to create
		// the button when and if necessary...
		final StringBuffer text = new StringBuffer();

		text.setLength(0);
		text.append(this.getStereotype());
		if ((this.getVisibleElements() & IVisibility.FULLY_QUALIFIED_NAMES) == IVisibility.FULLY_QUALIFIED_NAMES) {
			text.append(this.firstClassEntity.getID());
		}
		else {
			text.append(padl.util.Util.computeSimpleName(this.firstClassEntity
				.getName()));
		}
		if (this.buttons[this.numberOfButtons] == null) {
			this.buttons[this.numberOfButtons] =
				this.getPrimitiveFactory().createButton(
					text.toString(),
					new Point(xPos, yPos),
					true,
					colour);

		}
		else {
			this.buttons[this.numberOfButtons].setLabel(text.toString());
		}
		width =
			Math.max(
				width,
				this.buttons[this.numberOfButtons].getDimension().width);
		height += this.buttons[this.numberOfButtons].getDimension().height;
		yPos += this.buttons[this.numberOfButtons].getDimension().height;
		this.numberOfButtons++;

		text.setLength(0);
		if ((this.getVisibleElements() & IVisibility.HIERARCHY_NAMES) == IVisibility.HIERARCHY_NAMES) {
			for (int i = 0; i < this.listOfHierarchies.size(); i++) {
				text.append(((AbstractInheritance) this.listOfHierarchies
					.get(i)).getName());
				text.append('\n');
			}
		}
		if (text.length() > 0) {
			if (this.buttons[this.numberOfButtons] == null) {
				this.buttons[this.numberOfButtons] =
					this.getPrimitiveFactory().createButton(
						text.toString(),
						new Point(xPos, yPos),
						false,
						colour);

			}
			else {
				this.buttons[this.numberOfButtons].setLabel(text.toString());
			}
			width =
				Math.max(
					width,
					this.buttons[this.numberOfButtons].getDimension().width);
			height += this.buttons[this.numberOfButtons].getDimension().height;
			yPos += this.buttons[this.numberOfButtons].getDimension().height;
			this.numberOfButtons++;
		}

		text.setLength(0);
		for (int i = 0; i < this.listOfElements.size(); i++) {
			final Element element = (Element) this.listOfElements.get(i);
			if (element instanceof Relationship) {
				final Relationship relationship = (Relationship) element;

				// TODO: Manage the Destructor somewhere else!
				// /* 2004/08/10: Sebastien Robidoux, Ward Flores */
				// || element instanceof Destructor /* END */
				if (relationship.getTargetEntity() != null
						&& (this.getVisibleElements() & relationship
							.getVisibilityName()) == relationship
							.getVisibilityName()) {

					// Yann 01/07/30: Hack!
					// The target entity might be null if we consider
					// a subset of the original model.
					// Yann 2002/09/18: Bug!!!
					// I must check the elementName with a '\n' at the end
					// for the case we have:
					// 	TestListener\n
					// And we try to add:
					// 	Test
					final String elementName = element.getName() + '\n';
					if (text.toString().indexOf(elementName) == -1) {
						text.append(elementName);
					}
				}
			}
		}
		if (text.length() > 0) {
			if (this.buttons[this.numberOfButtons] == null) {
				this.buttons[this.numberOfButtons] =
					this.getPrimitiveFactory().createButton(
						text.toString(),
						new Point(xPos, yPos),
						false,
						colour);
			}
			else {
				this.buttons[this.numberOfButtons].setLabel(text.toString());
			}
			width =
				Math.max(
					width,
					this.buttons[this.numberOfButtons].getDimension().width);
			height += this.buttons[this.numberOfButtons].getDimension().height;
			yPos += this.buttons[this.numberOfButtons].getDimension().height;
			this.numberOfButtons++;
		}

		text.setLength(0);
		for (int i = 0; i < this.listOfElements.size(); i++) {
			final Element element = (Element) this.listOfElements.get(i);

			if (element instanceof Field
					&& (this.getVisibleElements() & IVisibility.FIELD_NAMES) == IVisibility.FIELD_NAMES) {

				text.append(element.toString());
				text.append('\n');
			}
		}
		text.append(this.supplementalFieldText());
		if (text.length() > 0) {
			if (this.buttons[this.numberOfButtons] == null) {
				this.buttons[this.numberOfButtons] =
					this.getPrimitiveFactory().createButton(
						text.toString(),
						new Point(xPos, yPos),
						false,
						colour);

			}
			else {
				this.buttons[this.numberOfButtons].setLabel(text.toString());
			}
			width =
				Math.max(
					width,
					this.buttons[this.numberOfButtons].getDimension().width);
			height += this.buttons[this.numberOfButtons].getDimension().height;
			yPos += this.buttons[this.numberOfButtons].getDimension().height;
			this.numberOfButtons++;
		}

		text.setLength(0);
		for (int i = 0; i < this.listOfElements.size(); i++) {
			final Element element = (Element) this.listOfElements.get(i);

			// TODO: Manage the Destructor somewhere else!
			// /* 2004/08/10: Sebastien Robidoux, Ward Flores */
			// || element instanceof Destructor /* END */
			if ((element instanceof Constructor || element instanceof Method || element instanceof Delegation)
					&& (this.getVisibleElements() & IVisibility.METHOD_NAMES) == IVisibility.METHOD_NAMES) {

				text.append(element.toString());
				text.append('\n');
			}
		}
		text.append(this.supplementalMethodText());
		if (text.length() > 0) {
			if (this.buttons[this.numberOfButtons] == null) {
				this.buttons[this.numberOfButtons] =
					this.getPrimitiveFactory().createButton(
						text.toString(),
						new Point(xPos, yPos),
						false,
						colour);

			}
			else {
				this.buttons[this.numberOfButtons].setLabel(text.toString());
			}
			width =
				Math.max(
					width,
					this.buttons[this.numberOfButtons].getDimension().width);
			height += this.buttons[this.numberOfButtons].getDimension().height;
			yPos += this.buttons[this.numberOfButtons].getDimension().height;
			this.numberOfButtons++;
		}

		// I set the overall dimension of this entity 
		// based on the combined sizes of its buttons.
		this.setDimension(new Dimension(width, height));
	}
	// JYves 25/08/2005: Modified for AspectJ
	protected void computeElements() {
		this.listOfElements.clear();

		// Yann 2013/07/27: C++ global functions...
		// Global function are both first-class 
		// entities and operations that may contain 
		// ConstituentOfElement that I must handle...
		//	final Iterator iterator =
		//		this.firstClassEntity.getIteratorOnConstituents();
		//	while (iterator.hasNext()) {
		//		final IConstituentOfEntity pElement =
		//			(IConstituentOfEntity) iterator.next();
		//		this.addElement(this.firstClassEntity, pElement);
		//	}
		final Iterator iteratorOnConstituentOfEntity =
			this.firstClassEntity
				.getIteratorOnConstituents(IConstituentOfEntity.class);
		while (iteratorOnConstituentOfEntity.hasNext()) {
			final IConstituentOfEntity pElement =
				(IConstituentOfEntity) iteratorOnConstituentOfEntity.next();
			this.addElement(this.firstClassEntity, pElement);
		}
		final Iterator iteratorOnConstituentOfOperation =
			this.firstClassEntity
				.getIteratorOnConstituents(IConstituentOfOperation.class);
		while (iteratorOnConstituentOfOperation.hasNext()) {
			final IConstituentOfOperation pElement =
				(IConstituentOfOperation) iteratorOnConstituentOfOperation
					.next();
			this.addElement(this.firstClassEntity, pElement);
		}
	}
	protected void computeHierarchies() {
		this.listOfHierarchies.clear();
	}
	protected Builder getBuilder() {
		return this.builder;
	}
	public Element[] getElements() {
		final Element[] dElements = new Element[this.listOfElements.size()];
		this.listOfElements.toArray(dElements);
		return dElements;
	}
	protected IFirstClassEntity getFirstClassEntity() {
		return this.firstClassEntity;
	}
	public AbstractInheritance[] getHierarchies() {
		// TODO: To be improved.
		final AbstractInheritance[] hierarchies =
			new AbstractInheritance[this.listOfHierarchies.size()];
		this.listOfHierarchies.toArray(hierarchies);
		return hierarchies;
	}
	public String getName() {
		return this.firstClassEntity.getDisplayID();
	}
	public IFirstClassEntity getSourceEntity() {
		// TODO: This method should not exist!
		return this.firstClassEntity;
	}
	protected abstract String getStereotype();
	public boolean isSuper() {
		return this.isSuper;
	}
	private void isSuper(final boolean isSuper) {
		this.isSuper = isSuper;
	}
	public void paint(final int xOffset, final int yOffset) {
		for (int i = 0; i < this.numberOfButtons; i++) {
			this.buttons[i].paint(xOffset, yOffset);
		}
	}
	public boolean processMouseEvent(final MouseEvent me) {
		boolean hasChanged = false;
		for (int i = 0; i < this.numberOfButtons && !hasChanged; i++) {
			hasChanged |= this.buttons[i].processMouseEvent(me);
		}

		// Yann 2004/08/08: Selection!
		// I don't forget to propagate the change (if any)
		// to the entity itself (after its button took care
		// of itself on reception of the mouse event).
		if (hasChanged) {
			super.isSelected(!this.isSelected());
		}

		return hasChanged;
	}
	protected void setDimensionSpecifics(final Dimension dimension) {
		int width = dimension.width;

		for (int i = 0; i < this.numberOfButtons; i++) {
			final Dimension newDimension =
				new Dimension(width, this.buttons[i].getDimension().height);
			this.buttons[i].setDimension(newDimension);
		}
	}
	protected void setPositionSpecifics(final Point position) {
		int xPos = this.getPosition().x;
		int yPos = this.getPosition().y;

		for (int i = 0; i < this.numberOfButtons; i++) {
			final Point newPosition = new Point(xPos, yPos);
			yPos += this.buttons[i].getDimension().height;
			this.buttons[i].setPosition(newPosition);
		}
	}
	public void setSelectedSpecifics(final boolean isSelected) {
		this.buttons[0].setSelected(isSelected);
	}
	public final void setVisibleElementsSpecifics(final int visibility) {
		Iterator iterator;

		iterator = this.listOfElements.iterator();
		while (iterator.hasNext()) {
			((Element) iterator.next()).setVisibleElements(visibility);
		}

		iterator = this.listOfHierarchies.iterator();
		while (iterator.hasNext()) {
			((AbstractInheritance) iterator.next())
				.setVisibleElements(visibility);
		}
	}
	protected String supplementalFieldText() {
		return "";
	}
	protected String supplementalMethodText() {
		return "";
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		// if (this instanceof Class) {
		// buffer.append("Class ");
		// }
		// else if (this instanceof Ghost) {
		// buffer.append("Ghost ");
		// }
		// else if (this instanceof Interface) {
		// buffer.append("Interface ");
		// }
		// /*2004/09/28: Ward Flores */
		// else if (this instanceof Structure) {
		// buffer.append("Struct ");
		// }
		// else if (this instanceof Union) {
		// buffer.append("Union ");
		// }
		// else if (this instanceof Enum) {
		// buffer.append("Enum ");
		// }
		// else if (this instanceof GlobalField) {
		// buffer.append("GlobalField ");
		// }
		// /*END*/
		final String name = this.getClass().getName();
		buffer.append(name.substring(name.lastIndexOf('.') + 1));
		buffer.append(' ');
		buffer.append(this.firstClassEntity.getDisplayName());
		buffer.append("\n{");
		buffer.append(this.getPosition().x);
		buffer.append(", ");
		buffer.append(this.getPosition().y);
		buffer.append("} [");
		buffer.append(this.getDimension().width);
		buffer.append(", ");
		buffer.append(this.getDimension().height);
		buffer.append(']');
		return buffer.toString();
	}
}
