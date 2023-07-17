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
import java.util.Iterator;
import java.util.List;
import ptidej.ui.Constants;
import ptidej.ui.IDrawable;
import ptidej.ui.ISelectable;
import ptidej.ui.event.GraphEvent;
import ptidej.ui.event.ISelectionListener;
import ptidej.ui.event.MouseEvent;
import ptidej.ui.primitive.IPrimitiveFactory;
import util.multilingual.MultilingualManager;

public abstract class Constituent implements IDrawable, ISelectable {
	private Dimension dimension = new Dimension(Constants.DEFAULT_DIMENSION);
	private boolean isSelected;
	private final List listOfSelectionListeners = new ArrayList();
	private Point position = new Point(Constants.DEFAULT_POSITION);
	private final IPrimitiveFactory primitiveFactory;
	private int visibility;

	public Constituent(final IPrimitiveFactory primitiveFactory) {
		this.primitiveFactory = primitiveFactory;
	}
	public void addSelectionListener(final ISelectionListener aSelectionListener) {
		this.listOfSelectionListeners.add(aSelectionListener);
	}
	public abstract void build();
	public final Dimension getDimension() {
		return this.dimension;
	}
	protected final Iterator getIteratorOnSelectionListeners() {
		return this.listOfSelectionListeners.iterator();
	}
	public String getName() {
		return MultilingualManager.getString(
			"NAME",
			Constituent.class,
			new Object[] { this.getClass() });
	}
	public final Point getPosition() {
		return this.position;
	}
	public final IPrimitiveFactory getPrimitiveFactory() {
		return this.primitiveFactory;
	}
	public final int getVisibleElements() {
		return this.visibility;
	}
	public boolean isSelected() {
		return this.isSelected;
	}
	public boolean processMouseEvent(final MouseEvent me) {
		return false;
	}
	public void removeSelectionListener(
		final ISelectionListener aSelectionListener) {

		this.listOfSelectionListeners.remove(aSelectionListener);
	}
	public final void setDimension(final Dimension dimension) {
		this.dimension = dimension;

		// Yann 2014/04/25: Happy... fixing!
		// By cleaning the code I found out that the position and dimension
		// of the entity could be (re)set without that of their related
		// graphical entities, such as buttons... This discrepancy made
		// for weird bugs where the entity would be displayed correctly
		// but the lines built to show their relation would not... Silly!
		// So, now, I make sure that each entity forward position and 
		// dimension appropriately.
		this.setDimensionSpecifics(dimension);
	}
	protected abstract void setDimensionSpecifics(final Dimension dimension);
	public final void setPosition(final Point position) {
		this.position = position;
		this.setPositionSpecifics(position);
	}
	protected abstract void setPositionSpecifics(final Point position);
	public final void isSelected(final boolean isSelected) {
		this.isSelected = isSelected;
		final Iterator iterator = this.listOfSelectionListeners.iterator();
		while (iterator.hasNext()) {
			final ISelectionListener selectionListener =
				(ISelectionListener) iterator.next();
			if (this.isSelected()) {
				selectionListener.constituentSelected(new GraphEvent(this));
			}
			else {
				selectionListener.constituentUnSelected(new GraphEvent(this));
			}
		}
		this.setSelectedSpecifics(isSelected);
	}
	protected abstract void setSelectedSpecifics(final boolean isSelected);
	public final void setVisibleElements(final int visibility) {
		this.visibility = visibility;
		this.setVisibleElementsSpecifics(visibility);
	}
	protected abstract void setVisibleElementsSpecifics(final int visibility);
}
