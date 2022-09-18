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
package padl.statement.creator.aol;

import padl.statement.creator.aol.helper.McCabeCCAdder;

public class ConditionalModelAnnotator extends ModelAnnotator {
	public ConditionalModelAnnotator(final String[] someFileNames) {
		super(someFileNames);
	}
	public String getMetricHeader() {
		return "			CYCLO ";
	}
	public IMetricValueAdder getMetricValueAdder() {
		return new McCabeCCAdder();
	}
}
