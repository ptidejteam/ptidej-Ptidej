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
package ptidej.viewer.extension;

import ptidej.viewer.IRepresentation;
import ptidej.viewer.event.ISourceModelListener;

public interface IViewerExtension extends ISourceModelListener {
	String getName();
	void invoke(final IRepresentation aRepresentation);
	boolean isVisible();
	void setVisible(boolean aVisibility);
}
