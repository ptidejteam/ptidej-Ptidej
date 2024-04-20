/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.viewer.extension.repository.callgraph.model;

/**
 * @author Yann-Gaël Guéhéneuc 
 * @since  2007/09/24
 */
public class Call {
	private final Method calledMethod;

	public Call(final Method aCalledMethod) {
		this.calledMethod = aCalledMethod;
	}
	public Method getMethod() {
		return this.calledMethod;
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(this.calledMethod.toString());
		return buffer.toString();
	}
	public void accept(final IVisitor aVisitor) {
		aVisitor.enter(this);
		this.calledMethod.accept(aVisitor);
		aVisitor.exit(this);
	}
}
