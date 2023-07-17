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
package sad.codesmell.property;

import java.util.Iterator;
import java.util.Set;

/**
 * Allow a property to contain other properties.
 *
 * @author Pierre Leduc
 * @version 1.0
 * @since 2006/05/26
 */
public interface IPropertyContainer {

	/**
	 * Get an Iterator on the contained property
	 * 
	 * @return The Iterator on the contained property
	 */
	public Iterator getIteratorOnProperty();

	/**
	 * Add a nested property to this property.
	 * 
	 * @param prop The property to be added
	 */
	public void addProperty(final ICodeSmellProperty prop) throws Exception;

	/**
	 * Add a collection of properties to this property.
	 * 
	 * @param propSet The collection to be added
	 */
	public void addProperties(final Set propSet) throws Exception;
}
