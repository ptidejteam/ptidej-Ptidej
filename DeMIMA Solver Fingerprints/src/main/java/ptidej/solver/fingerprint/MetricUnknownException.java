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
package ptidej.solver.fingerprint;

import util.multilingual.MultilingualManager;

/**
 * @author Jean-Yves Guillomarc'h
 */
public class MetricUnknownException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MetricUnknownException() {
		this(MultilingualManager.getString("STR", MetricUnknownException.class));
	}
	public MetricUnknownException(final String aName) {
		super(MultilingualManager
			.getString("STR", MetricUnknownException.class) + aName);
	}
}
