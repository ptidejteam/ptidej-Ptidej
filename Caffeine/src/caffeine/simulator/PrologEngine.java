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
package caffeine.simulator;

import JIP.engine.JIPEngine;

/**
 * @version 	0.1
 * @author		Yann-Gaël Guéhéneuc
 */
public final class PrologEngine extends JIPEngine {
	private final String[] eventData;
	public PrologEngine(final String[] eventData) {
		super();
		this.eventData = eventData;
	}
	public String[] getEventData() {
		return this.eventData;
	}
}
