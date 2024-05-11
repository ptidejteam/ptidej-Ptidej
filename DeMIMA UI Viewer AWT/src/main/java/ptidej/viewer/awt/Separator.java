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
package ptidej.viewer.awt;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

public final class Separator extends Component {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 100;
	private static final int HEIGHT = 12;
	private static final Dimension PREFERRED_SIZE =
		new Dimension(Separator.WIDTH, Separator.HEIGHT);

	public Separator() {
		this.setName("Separator");
	}
	public Dimension getMinimumSize() {
		return this.getPreferredSize();
	}
	public Dimension getPreferredSize() {
		return Separator.PREFERRED_SIZE;
	}
	public void paint(final Graphics g) {
		final int y = this.getHeight() / 2 - 2;
		final int beg = 5;
		final int end = this.getWidth() - beg * 2;
		final int mid = 5 + end / 2;

		g.setColor(Color.BLACK);
		g.fillRect(beg, y + 1, mid - beg - 10, 2);
		g.fillRect(mid + 10, y + 1, end - mid - 10, 2);
		g.fillRect(mid - 2, y + 0, 4, 4);
		g.setColor(Color.WHITE);
		g.drawLine(beg, y + 1, mid - 12, y + 1);
		g.drawLine(beg, y + 2, beg, y + 2);
		g.drawLine(mid + 10, y + 1, end - 2, y + 1);
		g.drawLine(mid + 10, y + 2, mid + 10, y + 2);
		g.drawLine(mid - 2, y + 0, mid, y + 0);
		g.drawLine(mid - 2, y + 0, mid - 2, y + 3);
		g.setColor(this.getBackground());
		g.fillRect(mid - 1, y + 1, 2, 2);
	}
}
