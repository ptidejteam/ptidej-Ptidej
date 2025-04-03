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
import padl.kernel.IClass;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentExtension;
import padl.kernel.IConstituentOfEntity;
import padl.kernel.IElement;
import padl.kernel.IFilter;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterfaceActor;
import padl.kernel.impl.Factory;
import padl.visitor.IVisitor;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/12/17
 */
public abstract class ClassAdapter extends FirstClassAdapter implements IClass {
	private static final long serialVersionUID = 5999424242925482925L;

	private IClass wrappedClass;

	public ClassAdapter(final char[] anID, final char[] aName) {
		this(Factory.getInstance().createClass(anID, aName));
	}

	public ClassAdapter(final IClass aClass) {
		super(aClass);
		this.wrappedClass = aClass;
	}
	
	
	public void addExtension(final IConstituentExtension anExtension) {
		this.wrappedClass.addExtension(anExtension);
	}
	public void addImplementedInterface(final IInterfaceActor anInterface) {
		this.wrappedClass.addImplementedInterface(anInterface);
	}
	public void addInheritedEntity(final IFirstClassEntity anEntity) {
		this.wrappedClass.addInheritedEntity(anEntity);
	}
	public void assumeAllInterfaces() {
		this.wrappedClass.assumeAllInterfaces();
	}
	public void assumeInterface(final IInterfaceActor anInterface) {
		this.wrappedClass.assumeInterface(anInterface);
	}

	
	
	
	

	public Iterator getConcurrentIteratorOnConstituents(final IFilter aFilter) {
		return this.getConcurrentIteratorOnConstituents(aFilter);
	}
	

	public IConstituentExtension getExtension(final char[] anExtensionName) {
		return this.wrappedClass.getExtension(anExtensionName);
	}
	public char[] getID() {
		return this.wrappedClass.getID();
	}
	public IInterfaceActor getImplementedInterface(final char[] aName) {
		return this.wrappedClass.getImplementedInterface(aName);
	}

	public Iterator getIteratorOnImplementedInterfaces() {
		return this.wrappedClass.getIteratorOnImplementedInterfaces();
	}



	public int getNumberOfImplementedInterfaces() {
		return this.wrappedClass.getNumberOfImplementedInterfaces();
	}




	public boolean isForceAbstract() {
		return this.wrappedClass.isForceAbstract();
	}
	



	public void removeImplementedInterface(final IInterfaceActor anInterface) {
		this.wrappedClass.removeImplementedInterface(anInterface);
	}



}
