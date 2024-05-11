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
package ptidej.ui.canvas;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import ptidej.ui.Constants;
import ptidej.ui.IDrawable;
import ptidej.ui.ISelectable;
import ptidej.ui.IVisibility;
import ptidej.ui.canvas.event.CanvasEvent;
import ptidej.ui.canvas.event.ICanvasListener;
import ptidej.ui.event.GraphEvent;
import ptidej.ui.event.ISelectionListener;
import ptidej.ui.event.MouseEvent;
import ptidej.ui.kernel.ModelGraph;
import ptidej.ui.primitive.IPrimitiveFactory;

public final class Canvas implements IVisibility {
	// Yann 2007/03/30: Better display but dirty hack!
	// I keep track for each entity of the number of
	// group-solutions to which it takes part to display
	// cute little boxes with numbers. This static field
	// should be stored within them...
	// TODO: Replace this hack with a proper implementation.
	public int GroupSolutionsNumber;
	public final Map GroupSolutionsCount = new HashMap();
	public final Map EntityRolesCount = new HashMap();

	// Yann 2004/08/08: Events!
	// I emit each selection event emitted by the abstract level graph
	// as a canvas-related event (distinguishing between constituents,
	// foreground, and background drawables).
	private final ISelectionListener backgroundConstituentSelectionListener =
		new ISelectionListener() {
			public void constituentSelected(final GraphEvent aGraphEvent) {
				final Iterator iterator = Canvas.this.listeners.iterator();
				while (iterator.hasNext()) {
					final ICanvasListener canvasListener =
						(ICanvasListener) iterator.next();
					canvasListener.backgroundElementSelected(new CanvasEvent(
						aGraphEvent.getSource()));
				}
			}
			public void constituentUnSelected(final GraphEvent aGraphEvent) {
				final Iterator iterator = Canvas.this.listeners.iterator();
				while (iterator.hasNext()) {
					final ICanvasListener canvasListener =
						(ICanvasListener) iterator.next();
					canvasListener.backgroundElementUnSelected(new CanvasEvent(
						aGraphEvent.getSource()));
				}
			}
		};
	private final List backgroundConstituents = new ArrayList();
	private final Dimension dimension = new Dimension();
	// Yann 2004/08/08: Events!
	// I emit each selection event emitted by the abstract level graph
	// as a canvas-related event (distinguishing between constituents,
	// foreground, and background drawables).
	private final ISelectionListener foregroundConstituentSelectionListener =
		new ISelectionListener() {
			public void constituentSelected(final GraphEvent aGraphEvent) {
				final Iterator iterator = Canvas.this.listeners.iterator();
				while (iterator.hasNext()) {
					final ICanvasListener canvasListener =
						(ICanvasListener) iterator.next();
					canvasListener.foregroundElementSelected(new CanvasEvent(
						aGraphEvent.getSource()));
				}
			}
			public void constituentUnSelected(final GraphEvent aGraphEvent) {
				final Iterator iterator = Canvas.this.listeners.iterator();
				while (iterator.hasNext()) {
					final ICanvasListener canvasListener =
						(ICanvasListener) iterator.next();
					canvasListener.foregroundElementUnSelected(new CanvasEvent(
						aGraphEvent.getSource()));
				}
			}
		};
	private final List foregroundConstituents = new ArrayList();
	// Yann 2004/08/08: Events!
	// I emit each selection event emitted by the abstract level graph
	// as a canvas-related event (distinguishing between constituents,
	// foreground, and background drawables).
	private final ISelectionListener constituentSelectionListener =
		new ISelectionListener() {
			public void constituentSelected(final GraphEvent graphEvent) {
				final Iterator iterator = Canvas.this.listeners.iterator();
				while (iterator.hasNext()) {
					final ICanvasListener canvasListener =
						(ICanvasListener) iterator.next();
					canvasListener.constituentSelected(graphEvent);
				}
			}
			public void constituentUnSelected(final GraphEvent graphEvent) {
				final Iterator iterator = Canvas.this.listeners.iterator();
				while (iterator.hasNext()) {
					final ICanvasListener canvasListener =
						(ICanvasListener) iterator.next();
					canvasListener.constituentUnSelected(graphEvent);
				}
			}
		};
	private final List listeners = new ArrayList();
	// private final IPrimitiveFactory primitiveFactory;
	private final ModelGraph sourceGraph;
	private boolean selected;

	private int visibleElements;
	public Canvas(
		final IPrimitiveFactory aPrimitiveFactory,
		final ModelGraph anAbstractLevelGraph) {

		// this.primitiveFactory = aPrimitiveFactory;
		this.sourceGraph = anAbstractLevelGraph;

		// Yann 2004/08/08: Events!
		// The canvas listens to its source 
		// graph and forwards any event...
		this.sourceGraph
			.addSelectionListener(this.constituentSelectionListener);

		this.reComputeDimension();
	}
	public void addCanvasListener(final ICanvasListener aCanvasListener) {
		this.listeners.add(aCanvasListener);
	}
	public void addToBackground(final IDrawable aConstituent) {
		this.backgroundConstituents.add(aConstituent);
		this.setDimension(
			aConstituent.getPosition(),
			aConstituent.getDimension());

		// Yann 2004/08/08: Events!
		// The canvas listens to its source graph
		//  and forwards any event...
		if (aConstituent instanceof ISelectable) {
			((ISelectable) aConstituent)
				.addSelectionListener(this.backgroundConstituentSelectionListener);
		}
	}
	public void addToForeground(final IDrawable aConstituent) {
		this.foregroundConstituents.add(aConstituent);
		this.setDimension(
			aConstituent.getPosition(),
			aConstituent.getDimension());

		// Yann 2004/08/08: Events!
		// The canvas listens to its source graph
		//  and forwards any event...
		if (aConstituent instanceof ISelectable) {
			((ISelectable) aConstituent)
				.addSelectionListener(this.foregroundConstituentSelectionListener);
		}
	}
	public void build() {
		this.EntityRolesCount.clear();

		IDrawable[] drawables;

		// First, I rebuild the constituents (recalculation of their position and size).
		drawables = this.getBackgroundElements();
		for (int i = 0; i < drawables.length; i++) {
			drawables[i].build();
		}

		drawables = this.getForegroundElements();
		for (int i = 0; i < drawables.length; i++) {
			drawables[i].build();
		}

		// Second, I recompute the Canvas size (length of the scrollbars).
		this.reComputeDimension();
	}
	public IDrawable[] getBackgroundElements() {
		final IDrawable[] elements =
			new IDrawable[this.backgroundConstituents.size()];
		this.backgroundConstituents.toArray(elements);
		return elements;
	}
	public Dimension getDimension() {
		return this.dimension;
	}
	public IDrawable[] getForegroundElements() {
		final IDrawable[] elements =
			new IDrawable[this.foregroundConstituents.size()];
		this.foregroundConstituents.toArray(elements);
		return elements;
	}
	public String getName() {
		return this.getClass().getName();
	}
	public Point getPosition() {
		return Constants.NULL_POSITION;
	}
	public int getVisibleElements() {
		return this.visibleElements;
	}
	public boolean isSelected() {
		return this.selected;
	}
	public final void paint(final int xOffset, final int yOffset) {
		// getDimension() represents the size of the DCanvas within the Ptidej framework.
		// this.getWidth() and this.getHeight() represent the visible size of this
		// DCanvas within the AWT framework. The idea is to set the dimension of the
		// DCanvas accordingly to the minimum size proposed by the AWT framework
		// and the minimum size to include all the elements and entities.

		// No need to clear up the screen, the BufferedScrollPane takes
		// care of this for us...
		// graphics.clearRect(0, 0, this.getWidth(), this.getHeight());
		IDrawable[] drawables;

		drawables = this.getBackgroundElements();
		for (int i = drawables.length - 1; i >= 0; i--) {
			drawables[i].paint(xOffset, yOffset);
		}

		this.sourceGraph.paint(xOffset, yOffset);

		drawables = this.getForegroundElements();
		for (int i = drawables.length - 1; i >= 0; i--) {
			drawables[i].paint(xOffset, yOffset);
		}
	}
	public boolean processMouseEvent(final MouseEvent mouseEvent) {
		IDrawable[] drawables;
		boolean hasChanged = false;

		// I process the mouse event from the front to the back.
		// (From the front-most graphical element to the one at the far rear)
		drawables = this.getForegroundElements();
		int i;
		for (i = 0; i < drawables.length && hasChanged == false; i++) {
			hasChanged = drawables[i].processMouseEvent(mouseEvent);
		}
		if (!hasChanged) {
			hasChanged = this.sourceGraph.processMouseEvent(mouseEvent);

			if (!hasChanged) {
				drawables = this.getBackgroundElements();
				for (i = 0; i < drawables.length && hasChanged == false; i++) {
					hasChanged = drawables[i].processMouseEvent(mouseEvent);
				}
			}
		}

		return hasChanged;
	}
	private void reComputeDimension() {
		// I found a bug in the implementation of AWT in VAJ.
		// The method:
		//	public void java.awt.Dimension.setSize(double width, double height) {
		//		width = (int) Math.ceil(width);
		//		height = (int) Math.ceil(height);
		//	}
		// is obviously bogus! (For example, it should be this.width = ...)
		this.dimension.setSize(
			(int) Constants.NULL_DIMENSION.getWidth(),
			(int) Constants.NULL_DIMENSION.getHeight());

		IDrawable[] drawables;

		drawables = this.getBackgroundElements();
		for (int i = 0; i < drawables.length; i++) {
			this.setDimension(
				drawables[i].getPosition(),
				drawables[i].getDimension());
		}

		this.setDimension(
			Constants.NULL_POSITION,
			this.sourceGraph.getDimension());

		drawables = this.getForegroundElements();
		for (int i = 0; i < drawables.length; i++) {
			this.setDimension(
				drawables[i].getPosition(),
				drawables[i].getDimension());
		}
	}
	public void removeCanvasListener(final ICanvasListener aCanvasListener) {
		this.listeners.remove(aCanvasListener);
	}
	public void removeFromBackground(final IDrawable component) {
		this.GroupSolutionsNumber = 0;

		this.backgroundConstituents.remove(component);
		this.reComputeDimension();
	}
	public void removeFromForeground(final IDrawable component) {
		this.GroupSolutionsNumber = 0;

		this.foregroundConstituents.remove(component);
		this.reComputeDimension();
	}
	private void setDimension(final Point origin, final Dimension dimension) {
		double tempWidth =
			origin.x + dimension.width + Constants.CANVAS_INNER_GAP.width;
		tempWidth =
			this.getDimension().getWidth() < tempWidth ? tempWidth : this
				.getDimension()
				.getWidth();

		double tempHeight =
			origin.y + dimension.height + Constants.CANVAS_INNER_GAP.height;
		tempHeight =
			this.getDimension().getHeight() < tempHeight ? tempHeight : this
				.getDimension()
				.getHeight();

		// I found a bug in the implementation of AWT in VAJ.
		// The method:
		//	public void java.awt.Dimension.setSize(double width, double height) {
		//		width = (int) Math.ceil(width);
		//		height = (int) Math.ceil(height);
		//	}
		// is obviously bogus! (For example, it should be this.width = ...)
		this.dimension.setSize((int) tempWidth, (int) tempHeight);
	}
	public void setSelected(final boolean isSelected) {
		this.selected = isSelected;

		IDrawable[] drawables;

		drawables = this.getBackgroundElements();
		for (int i = 0; i < drawables.length; i++) {
			if (drawables[i] instanceof IVisibility) {
				((ISelectable) drawables[i]).isSelected(this.selected);
			}
		}

		this.sourceGraph.areElementsSelected(this.selected);
		this.sourceGraph.build();

		drawables = this.getForegroundElements();
		for (int i = 0; i < drawables.length; i++) {
			if (drawables[i] instanceof IVisibility) {
				((ISelectable) drawables[i]).isSelected(this.selected);
			}
		}
	}
	public void setVisibleElements(int visibility) {
		this.visibleElements = visibility;

		IDrawable[] drawables;

		drawables = this.getBackgroundElements();
		for (int i = 0; i < drawables.length; i++) {
			if (drawables[i] instanceof IVisibility) {
				((IVisibility) drawables[i]).setVisibleElements(visibility);
			}
		}

		this.sourceGraph.setVisibleElements(visibility);
		this.sourceGraph.build();

		drawables = this.getForegroundElements();
		for (int i = 0; i < drawables.length; i++) {
			if (drawables[i] instanceof IVisibility) {
				((IVisibility) drawables[i]).setVisibleElements(visibility);
			}
		}
	}
}
