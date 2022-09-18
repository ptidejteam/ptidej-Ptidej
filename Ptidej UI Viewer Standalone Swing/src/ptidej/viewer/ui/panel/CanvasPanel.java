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
package ptidej.viewer.ui.panel;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import ptidej.ui.occurrence.awt.PrimitiveFactory;

public class CanvasPanel extends JPanel {
	private static final long serialVersionUID = -6512157689860956726L;
	public CanvasPanel() {
		// Yann 2013/07/30: Layout...
		// I was bugged for a long time by a bug in the
		// way mouse events were emitted and their 
		// coordinates or, rather, the offsets in their
		// coordinates. Finally, look at 
		// 	AWTCanvas.paint(Graphics)
		// for the solution. The hint was this 5 pixel
		// gap due to the *default* FlowLayout, then
		// by setting no layout (null value), everything
		// is beautiful and simple.
		super(null);
		this.setBackground(Color.WHITE);
		//	((FlowLayout) this.getLayout()).setHgap(0);
		//	((FlowLayout) this.getLayout()).setVgap(0);
	}
	public void paint(final Graphics aGraphics) {
		// Yann 2007/02/02: Graphics!
		// Ensure setting the primitive factory graphics object before
		// painting the current component. This is the only place
		// where I can do that because I am sure at this point that
		// a Graphics has been assigned to the canvas.
		((ptidej.ui.primitive.awt.PrimitiveFactory) PrimitiveFactory
			.getInstance()).setGraphics(aGraphics);
		super.paint(aGraphics);
	}
}
