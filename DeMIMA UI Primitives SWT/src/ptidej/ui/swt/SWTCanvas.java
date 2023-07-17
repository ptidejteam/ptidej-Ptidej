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
/*
 * (c) Copyright 2002, 2003
 * Matthieu Bacqueville   (Ecole des Mines de Nantes),
 * Nicolas Benoit         (Ecole des Mines de Nantes),
 * Jean-Sébastien Brunner (Ecole des Mines de Nantes),
 * Christian Gossart      (Ecole des Mines de Nantes),
 * Yann-Gaël Guéhéneuc    (Ecole des Mines de Nantes and
 *                         Object Technology International, Inc.)
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the authors, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN, ANY
 * LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHORS IS ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package ptidej.ui.swt;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Scrollable;
import ptidej.ui.Constants;
import ptidej.ui.canvas.Canvas;
import ptidej.ui.primitive.swt.Primitive;

/**
 * Canvas where graphical constituants of Ptitdej UI are drawn.
 * 
 * @author	Jean-Sébastien Brunner
 * @author 	Yann-Gaël Guéhéneuc
 */
public final class SWTCanvas extends org.eclipse.swt.widgets.Canvas {
	private final GC graphics;
	private Canvas canvas;
	private int xOffset, yOffset;

	public SWTCanvas(final Composite parent, final int flags) {
		super(parent, flags);

		this.getVerticalBar().addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(
				final SelectionEvent selectionEvent) {
				SWTCanvas.this.yOffset =
					-SWTCanvas.this.getVerticalBar().getSelection();
				SWTCanvas.this.redraw();
			}
			public void widgetSelected(final SelectionEvent selectionEvent) {
				this.widgetDefaultSelected(selectionEvent);
			}
		});
		this.getVerticalBar().setIncrement(20);

		this.getHorizontalBar().addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(
				final SelectionEvent selectionEvent) {
				SWTCanvas.this.xOffset =
					-SWTCanvas.this.getHorizontalBar().getSelection();
				SWTCanvas.this.redraw();
			}
			public void widgetSelected(final SelectionEvent selectionEvent) {
				this.widgetDefaultSelected(selectionEvent);
			}
		});
		this.getHorizontalBar().setIncrement(20);

		this.addMouseListener(new MouseAdapter() {
			public void mouseUp(final MouseEvent me) {
				if (SWTCanvas.this.canvas != null
						&& SWTCanvas.this.canvas
							.processMouseEvent(new ptidej.ui.event.MouseEvent(
								SWTCanvas.this.canvas,
								ptidej.ui.event.MouseEvent.MOUSE_CLICKED,
								me.x - SWTCanvas.this.xOffset,
								me.y - SWTCanvas.this.yOffset))) {
					// Now that the ptidej.ui.Canvas has dealt with the MouseEvent,
					// I must set the size of the SWTCanvas accordingly to the
					// new Canvas size to make sure that the Scrollbars are
					// updated correctly.
					SWTCanvas.this.canvas.build();

					//	SWTCanvas.this.setSize(
					//		SWTCanvas.this.canvas.getDimension().width,
					//		SWTCanvas.this.canvas.getDimension().height);
					SWTCanvas.this.redraw();
				}
			}
		});

		this.addPaintListener(new PaintListener() {
			public void paintControl(final PaintEvent paintEvent) {
				SWTCanvas.this.redraw();
			}
		});

		// The instance of GC in which the entities
		// and elements paint themselves.
		this.graphics = new GC(this);
	}
	public Canvas getCanvas() {
		return this.canvas;
	}
	public GC getGraphics() {
		return this.graphics;
	}
	public void goTo(final Point position) {
		((Scrollable) this.getParent()).setLocation(position.x, position.y);
	}
	public void redraw() {
		this.redraw(this.xOffset, this.yOffset);
	}
	private void redraw(final int xOffset, final int yOffset) {
		// Yann 2003/01/30: Disposal!
		// I add a new test to check wether this
		// canvas has been disposed or not...
		// This is a quick fix... The *good* solution
		// is to remove the DiagramEditor from the
		// GUIOptions event handler on closing!
		// Actually, see method DiagramEditor.dispose().
		//	if (!this.getDisplay().isDisposed()) {

		// System.out.println("redraw(final int xOffset, final int yOffset)");
		// System.out.println("this.canvas = " + this.canvas);
		//	System.out.println("this.graphics = " + this.graphics);
		//	(
		//		(PrimitiveFactory) PrimitiveFactory.getPrimitiveFactory(
		//			this.getDisplay(),
		//			this.graphics)).setGraphics(
		//		this.graphics);
		//	System.out.println(
		//		"PF   graphics = "
		//			+ ((PrimitiveFactory) PrimitiveFactory
		//				.getPrimitiveFactory(this.getDisplay(), this.graphics))
		//				.getGraphics());
		// System.out.println("this.size = " + this.getSize());
		// System.out.println("this.canvas.size = " + this.canvas.getDimension());

		if (this.canvas != null && !this.isDisposed()) {
			if (this.canvas.getDimension().getHeight() > this.getClientArea().height) {

				this.getVerticalBar().setMaximum(
					(int) this.canvas.getDimension().getHeight()
							- this.getClientArea().height);
			}
			else {
				this.getVerticalBar().setMaximum(1);
			}
			if (this.canvas.getDimension().getWidth() > this.getClientArea().width) {

				this.getHorizontalBar().setMaximum(
					(int) this.canvas.getDimension().getWidth()
							- this.getClientArea().width);
			}
			else {
				this.getHorizontalBar().setMaximum(1);
			}
		}

		// Yann 2002/10/04: Question?
		// This is all good but not that nice:
		// How could I keep method convertColor(...)
		// protected while clearing the background
		// anyway?	

		this.graphics.setBackground(Primitive.convertColor(
			this.getDisplay(),
			Constants.BACKGROUND_COLOR));
		this.graphics.fillRectangle(0, 0, this.getSize().x, this.getSize().y);

		//System.out.println(this.getSize());

		if (this.canvas != null) {
			this.canvas.paint(xOffset, yOffset);
		}
	}
	public void setCanvas(final Canvas canvas) {
		this.canvas = canvas;
	}

}
