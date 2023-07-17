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
package ptidej.ui.event;

/*
 * I use my own MouseEvent. There is a good reason to this.
 * I want my framework to be graphic-framework independent.
 * Thus, I don't want the class ptidej.ui.Canvas to depend on
 * a specific implementation of AWT or SWT MouseEvent.
 * What happens is that an implementation specific Canvas
 * class (see for instance ptidej.viewer.AWTCanvas) wraps the
 * ptidej.ui.Canvas class and creates an instance of
 * ptidej.ui.event.MouseEvent from an instance of
 * java.awt.event.MouseEvent (resiprocally for SWT).
 */
public final class MouseEvent {
	public static final int MOUSE_CLICKED = 1;
	public static final int MOUSE_PRESSED = 2;
	public static final int MOUSE_RELEASED = 3;
	public static final int MOUSE_MOVED = 4;
	public static final int MOUSE_ENTERED = 5;
	public static final int MOUSE_EXITED = 6;
	public static final int MOUSE_DRAGGED = 7;

	private final Object source;
	private final int id;
	private final int x;
	private final int y;
	public MouseEvent(
		final Object aSource,
		final int anID,
		final int x,
		final int y) {

		this.source = aSource;
		this.id = anID;
		this.x = x;
		this.y = y;
	}
	public int getID() {
		return this.id;
	}
	public Object getSource() {
		return this.source;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
}
