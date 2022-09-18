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

import padl.event.IModelListener;
import ptidej.ui.awt.AWTCanvas;
import ptidej.viewer.IRepresentation;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/08/20
 */
public interface IAWTRepresentation extends IRepresentation {
	AWTCanvas getAWTCanvas();
	IModelListener getModelStatistics();
	// Yann 2013/09/09: Dual-hierarchical view!
	// By adding the dual-hierarchical representation, I realised
	// that it does not make sense for the representation to have
	// its AWTCanvas set outside, much to risky for inconsistency
	// so I removed this public method. 
	//	void setAWTCanvas(final AWTCanvas anAWTCanvas);
}
