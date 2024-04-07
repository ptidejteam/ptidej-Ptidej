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
package caffeine.analysis.toplevelfields;

/**
 * @version 0.1
 * @author	Yann-Gaël Guéhéneuc
 */
public class ContainerObject {
	private ContainedObject object1;
	private ContainedObject object2;

	public ContainerObject(
		final ContainedObject object1,
		final ContainedObject object2) {

		this.object1 = object1;
		this.object2 = object2;
	}
	public void operation() {
		this.object1.operation();
		this.object2.operation();
	}
}
