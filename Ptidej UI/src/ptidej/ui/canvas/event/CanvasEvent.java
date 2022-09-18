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
package ptidej.ui.canvas.event;

import ptidej.ui.event.GraphEvent;
import ptidej.ui.kernel.Constituent;

public final class CanvasEvent extends GraphEvent {
	public CanvasEvent(final Constituent aConstituent) {
		super(aConstituent);
	}
}
