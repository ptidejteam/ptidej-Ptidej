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

import padl.kernel.IAbstractLevelModel;
import padl.kernel.IFirstClassEntity;
import pom.metrics.MetricsRepository;

/**
 * @author Jean-Yves Guillomarc'h
 */
public class FingerprintSet {
	private final Fingerprint[] rules;

	public FingerprintSet(final Fingerprint[] rules) {
		this.rules = rules;
	}
	public boolean compute(
		final MetricsRepository metrics,
		final IAbstractLevelModel anAbstractLevelModel,
		final IFirstClassEntity firstClassEntity) {

		boolean accept = true;
		for (int i = 0; i < this.rules.length && accept; i++) {
			accept =
				this.rules[i].compute(
					metrics,
					anAbstractLevelModel,
					firstClassEntity);
		}
		return accept;
	}
	public Fingerprint[] getRules() {
		return this.rules;
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < this.rules.length; i++) {
			buffer.append('(');
			buffer.append(this.rules[i]);
			buffer.append(')');
		}

		return buffer.toString();
	}
}
