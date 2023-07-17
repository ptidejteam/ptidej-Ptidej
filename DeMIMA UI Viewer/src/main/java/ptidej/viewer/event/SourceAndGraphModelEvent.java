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
package ptidej.viewer.event;

import ptidej.viewer.IRepresentation;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/08/08
 */
public class SourceAndGraphModelEvent {
	private final IRepresentation representation;

	public SourceAndGraphModelEvent(final IRepresentation aRepresentation) {
		this.representation = aRepresentation;
	}
	public IRepresentation getRepresentation() {
		return this.representation;
	}
}
