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
package ptidej.ui.analysis.repository.comparator.model;

import padl.kernel.IConstituent;
import padl.kernel.IInterface;
import padl.util.adapter.InterfaceAdapter;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/12/20
 */
public class AddedInterface extends InterfaceAdapter implements IInterface {
	private static final long serialVersionUID = -5601544368426921658L;

	public AddedInterface(final IInterface anInterface) {
		super(anInterface);
	}
	public IConstituent getClone() {
		return new AddedInterface((IInterface) super.getClone());
	}
}
