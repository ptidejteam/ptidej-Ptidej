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
package ptidej.ui.awt;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import ptidej.ui.canvas.Canvas;

public final class AWTCanvas extends Component {
	private static final long serialVersionUID = 1L;

	private final Canvas canvas;

	public AWTCanvas(final Canvas canvas) {
		this.canvas = canvas;
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(final MouseEvent me) {
				int id;
				switch (me.getID()) {
					case MouseEvent.MOUSE_CLICKED :
						id = ptidej.ui.event.MouseEvent.MOUSE_CLICKED;
						break;
					case MouseEvent.MOUSE_PRESSED :
						id = ptidej.ui.event.MouseEvent.MOUSE_PRESSED;
						break;
					case MouseEvent.MOUSE_RELEASED :
						id = ptidej.ui.event.MouseEvent.MOUSE_RELEASED;
						break;
					case MouseEvent.MOUSE_MOVED :
						id = ptidej.ui.event.MouseEvent.MOUSE_MOVED;
						break;
					case MouseEvent.MOUSE_ENTERED :
						id = ptidej.ui.event.MouseEvent.MOUSE_ENTERED;
						break;
					case MouseEvent.MOUSE_EXITED :
						id = ptidej.ui.event.MouseEvent.MOUSE_EXITED;
						break;
					case MouseEvent.MOUSE_DRAGGED :
						id = ptidej.ui.event.MouseEvent.MOUSE_DRAGGED;
						break;
					default :
						id = 0;
				}

				final int x = me.getX();
				final int y = me.getY();

				if (AWTCanvas.this.canvas
					.processMouseEvent(new ptidej.ui.event.MouseEvent(
						AWTCanvas.this.canvas,
						id,
						x,
						y))) {
					// Now that the ptidej.ui.Canvas has dealt with the MouseEvent,
					// I must set the size of the AWTCanvas accordingly to the
					// new Canvas size to make sure that the Scrollbars are
					// updated correctly.

					// Yann 2003/08/05: Scrollbars
					// Before computing the size of the canvas (for the
					// scrollbars, I must rebuild the canvas to take into
					// account the case where the click bring to display
					// constituents that go beyond the view.
					AWTCanvas.this.canvas.build();
					AWTCanvas.this.setSize(AWTCanvas.this.canvas.getDimension());
					AWTCanvas.this.getParent().repaint();
				}
			}
		});
	}
	public int getHeight() {
		return (int) this.canvas.getDimension().getHeight();
	}
	public Dimension getPreferredSize() {
		return this.canvas.getDimension();
	}
	public Dimension getSize() {
		return this.canvas.getDimension();
	}
	public int getWidth() {
		return (int) this.canvas.getDimension().getWidth();
	}
	public void goTo(final Point position) {
		// Yann 2007/05/14: ScrollPane
		// I make the assumption that somewhere, the Canvas and CanvasPanel
		// are included in a ScrollPane.
		// TODO: What happens if this assumption is wrong?
		Component component = this;
		while (component != null
				&& !(component instanceof ScrollPane || component instanceof JScrollPane)) {

			component = component.getParent();
		}
		if (component != null) {
			if (component instanceof ScrollPane) {
				((ScrollPane) component).setScrollPosition(position);
			}
			else if (component instanceof JScrollPane) {
				final JScrollPane scrollPane = ((JScrollPane) component);
				scrollPane.getHorizontalScrollBar().setValue(
					(int) position.getX() - scrollPane.getViewport().getWidth()
							/ 2);
				scrollPane.getVerticalScrollBar().setValue(
					(int) position.getY()
							- scrollPane.getViewport().getHeight() / 2);
			}
		}
	}
	public void paint(final Graphics graphics) {
		// Yann 2013/07/30: Nasty bug!
		// I could not understand for more than 3 hours
		// why the graph would be painted at the "right"
		// place but mouse event coordinates would be
		// off or, worse, not emmitted... I realised
		// that I used,
		//	this.canvas.paint(0, 0);
		// even when this.getX() and this.getY() may
		// be different from 0! Hence, the weird 
		// offsets. If I want my canvas to be always
		// displayed in the top-left corner, I must
		// change the layout of the CanvasPanel, not
		// play with the paint method... The solution
		// is as simple as changing the constructor
		// of CanvasPanel, see it to believe it!
		this.canvas.paint(this.getX(), this.getY());
	}
}
