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
/**
 * 
 */
package ptidej.ui.layout.repository.sugiyama;

/**
 * @author kahlamoh
 *
 */
// TODO: These constants could be directly defined in the Link class.
public interface LinkTypes {

	final int SPECIALISATION = 1;
	final int IMPLEMENTATION = 2;
	final int ASSOCIATION = 3;
	final int COMPOSITION = 4;
	final int AGGREGATION = 5;
	final int DELEGATION = 6;
	final int CONTAINER_AGGREGATION = 7;
	final int CONTAINER_ASSOCIATION = 8;
	final int CREATION = 9;
	final int USE = 10;

}
