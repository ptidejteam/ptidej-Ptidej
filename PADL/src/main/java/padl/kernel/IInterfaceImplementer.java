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

import java.util.Iterator;

/**
 * 
 * @author Yann
 * @since  2009/05/01
 *
 */
public interface IInterfaceImplementer extends IFirstClassEntity {
	void addImplementedInterface(final IInterfaceActor anInterface);
	void assumeAllInterfaces();
	void assumeInterface(final IInterfaceActor anInterface);
	IInterfaceActor getImplementedInterface(final char[] aName);
	Iterator getIteratorOnImplementedInterfaces();
	int getNumberOfImplementedInterfaces();
	void removeImplementedInterface(final IInterfaceActor anInterface);
}
