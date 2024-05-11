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
package sad.codesmell.property.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import sad.codesmell.property.ICodeSmellProperty;
import sad.codesmell.property.IPropertyContainer;

/**
 * This class encapsulate the fonctionnalty to
 * manage nested properties
 *
 * @author Pierre Leduc
 * @version 1.0
 * @since 2006/05/29
 */
public class PropertyContainer implements IPropertyContainer {
	final private Set contents;

	public PropertyContainer() {
		super();

		this.contents = new HashSet();
	}

	public Iterator getIteratorOnProperty() {
		return this.contents.iterator();
	}

	public void addProperty(final ICodeSmellProperty prop) throws Exception {
		this.contents.add(prop);
	}

	public void addProperties(final Set propSet) throws Exception {
		this.contents.addAll(propSet);
	}

	public String toString(final int count, final String codesmellName) {
		final StringBuffer buffer = new StringBuffer();
		final Iterator iter = this.contents.iterator();

		int propertyCount = 0;
		while (iter.hasNext()) {
			
			final ICodeSmellProperty prop = (ICodeSmellProperty) iter.next();
			buffer.append(prop.toString(count, propertyCount, codesmellName));
			
			propertyCount ++;
		}

		return buffer.toString();
	}
}
