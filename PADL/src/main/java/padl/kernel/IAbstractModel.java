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

import java.io.Serializable;
import java.util.Iterator;
import padl.visitor.IGenerator;
import padl.visitor.IWalker;

/**
 * @author Yann-Gaël Guéhéneuc
 */
public interface IAbstractModel extends IContainer, INavigable, IObservable, 
		Serializable {

	void addConstituent(final IConstituentOfModel aConstituent);
	Object clone() throws CloneNotSupportedException;
	boolean doesContainTopLevelEntityWithID(char[] anID);
	String generate(final IGenerator aBuilder);
	String getDisplayName();
	IFactory getFactory();
	Iterator getIteratorOnTopLevelEntities();
	char[] getName();
	int getNumberOfTopLevelEntities();
	int getNumberOfTopLevelEntities(final java.lang.Class aClass);
	IFirstClassEntity getTopLevelEntityFromID(final char[] anID);
	IFirstClassEntity getTopLevelEntityFromID(final String anID);
	// Yann 2013/05/22: Systematic interface
	// I use the Builder design pattern with the Creators, which
	// means that I give them a "empty" code-level model as input
	// and they fill it in. Now, I want to have the same kind of
	// behaviour for the Serialisers, but because of the way that
	// DB4O and others work, I must have a means to copy the content
	// of a deserialised model into an "empty" model, hence this
	// convience method...
	// TODO Is it the best way to implement this feature?
	void moveIn(final IAbstractModel aDestinationModel);
	void removeTopLevelEntityFromID(final char[] anID);
	// Yann 2013/05/23: Internal stuff!
	// No need to broadcast this method: it should be only used
	// by the Factory to attach itself to the abstract-level model.
	//	void setFactory(final IFactory aFactory);
	Object walk(final IWalker aWalker);
}
