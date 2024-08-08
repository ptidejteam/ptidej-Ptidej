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
package caffeine.logic;

import JIP.engine.JIPTerm;

/**
 * @version 0.2
 * @author	Yann-Gaël Guéhéneuc
 */
public interface EngineListener {
	public void engineInitialized(final Engine engine);
	public void engineStarted(final Engine engine);
	public void engineTerminated(
		final JIPTerm solution,
		final Engine engine,
		final long time,
		final long steps);
	public void eventEmitted(final Event event);
}
