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
import java.util.List;
import padl.event.IEvent;
import padl.event.IModelListener;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/02/19
 */
public interface IObservable {
	void addModelListener(final IModelListener aModelListener);
	void addModelListeners(final List aListOfModelListeners);
	/**
	 *  @deprecated
	 *  TODO Remove method fireModelChange(), should not be replaced by anything!
	 */
	void fireModelChange(final String anEventType, final IEvent anEvent);
	Iterator getIteratorOnModelListeners();
	void removeModelListener(final IModelListener aModelListener);
	void removeModelListeners(final List modelListeners);
}
