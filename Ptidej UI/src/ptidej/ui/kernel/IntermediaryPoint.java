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

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/04/22
 */
public class IntermediaryPoint {
	private int x;
	private int y;

	public IntermediaryPoint() {
		this(0, 0);
	}
	public IntermediaryPoint(final int x, final int y) {
		this.setPosition(x, y);
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public void setPosition(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
}
