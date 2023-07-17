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
package padl.creator.javafile.eclipse.astVisitors;

import padl.kernel.IConstituent;
import padl.kernel.IConstructor;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IGetter;
import padl.kernel.IMethod;
import padl.kernel.ISetter;
import padl.util.adapter.WalkerAdapter;

public class DefaultCodeLineSetter extends WalkerAdapter {
	private void open(final IConstituent aConstituent) {
		if (!aConstituent.isAbstract()) {
			aConstituent.setCodeLines(new String[0]);
		}
	}
	public void open(final IConstructor aMethod) {
		this.open((IConstituent) aMethod);
	}
	public void open(final IDelegatingMethod aMethod) {
		this.open((IConstituent) aMethod);
	}
	public void open(final IGetter aMethod) {
		this.open((IConstituent) aMethod);
	}
	public void open(final IMethod aMethod) {
		this.open((IConstituent) aMethod);
	}
	public void open(final ISetter aMethod) {
		this.open((IConstituent) aMethod);
	}
}
