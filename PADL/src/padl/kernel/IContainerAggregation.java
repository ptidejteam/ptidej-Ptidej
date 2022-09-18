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
package padl.kernel;

/**
 * @author Yann-Gaël Guéhéneuc
 */
public interface IContainerAggregation extends IAssociation, IContainer {
	void addConstituent(final IConstituentOfEntity aConstituent);

	String LOGO = "<>-->";
	char[] ID1 = "~ID1".toCharArray();
	char[] ID2 = "~ID2".toCharArray();
	char[] ID3 = "~ID3".toCharArray();
}
