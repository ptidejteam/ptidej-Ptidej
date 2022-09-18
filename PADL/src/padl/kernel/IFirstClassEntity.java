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
/*
 * (c) Copyright 2001-2003 Yann-Gaël Guéhéneuc, École des Mines de Nantes and
 * Object Technology International, Inc.
 * 
 * Use and copying of this software and preparation of derivative works based
 * upon this software are permitted. Any copy of this software or of any
 * derivative work must include the above copyright notice of the author, this
 * paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS ALL
 * WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, AND NOT
 * WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN, ANY LIABILITY FOR DAMAGES
 * RESULTING FROM THE SOFTWARE OR ITS USE IS EXPRESSLY DISCLAIMED, WHETHER
 * ARISING IN CONTRACT, TORT (INCLUDING NEGLIGENCE) OR STRICT LIABILITY, EVEN
 * IF THE AUTHOR IS ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package padl.kernel;

import java.util.Iterator;

/**
 * @author Yann-Gaël Guéhéneuc
 */
public interface IFirstClassEntity extends IEntity, IContainer {
	void addConstituent(final IConstituentOfEntity anElement);
	void addInheritedEntity(final IFirstClassEntity anEntity);
	// Yann 2005/05/21: Usage!
	// The addition now happens in the addInheritedEntity() method.
	//	/**
	//	 * This method add a new entity to the list of entities inheriting from
	//	 * this entity.
	//	 * 
	//	 * @param anEntity
	//	 * @
	//	 */
	//	void addInheritingEntity(final IEntity anEntity)
	//		;
	IFirstClassEntity getInheritedEntityFromName(final char[] aName);
	IFirstClassEntity getInheritedEntityFromID(final char[] anID);
	Iterator getIteratorOnInheritedEntities();
	Iterator getIteratorOnInheritedEntities(final IFilter aFilter);
	Iterator getIteratorOnInheritingEntities();
	Iterator getIteratorOnInheritingEntities(final IFilter aFilter);
	/**
	 * This method returns the list of all entities inheriting from this
	 * entity.
	 * 
	 * @return list of inheriting actors
	 */
	int getNumberOfInheritedEntities();
	int getNumberOfInheritingEntities();
	String getPurpose();
	boolean isAboveInHierarchy(final IFirstClassEntity anEntity);
	void removeInheritedEntity(final IFirstClassEntity anEntity);
	void setPurpose(final String aPurpose);
}
