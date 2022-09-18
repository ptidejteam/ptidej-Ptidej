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
package mendel.part.output;

import mendel.part.AbstractPart;

/**
 * 
 * Initializating Properties:
 * 
 * Input: Object
 * Output: Object
 * 
 * @author Simon Denier
 * @since Feb 15, 2008
 * 
 */
public class ConsoleOutput extends AbstractPart {
	
	public ConsoleOutput() {}


	public Object compute(Object data) {
		System.out.println(data.toString());
		return data;
	}

}
