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
package padl.util.adapter;

import java.util.Iterator;
import padl.event.IEvent;
import padl.event.IModelListener;
import padl.kernel.FirstClassAdapter;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentExtension;
import padl.kernel.IConstituentOfEntity;
import padl.kernel.IElement;
import padl.kernel.IFilter;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import padl.kernel.impl.Factory;
import padl.visitor.IVisitor;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/12/17
 */
public abstract class InterfaceAdapter extends FirstClassAdapter implements IInterface {
	private static final long serialVersionUID = -8302726844488400447L;

	private IInterface wrappedInterface;

	public InterfaceAdapter(final char[] anID, final char[] aName) {
		this(Factory.getInstance().createInterface(anID, aName));
	}

	public InterfaceAdapter(final IInterface anInterface) {
		super(anInterface);
		this.wrappedInterface = anInterface;
	}
	






	public Iterator getIteratorOnImplementingClasses() {
		return this.wrappedInterface.getIteratorOnImplementingClasses();
	}
	

	

	public void removeImplementedEntity(final IFirstClassEntity anEntity) {
		this.removeImplementedEntity(anEntity);
	}


}
