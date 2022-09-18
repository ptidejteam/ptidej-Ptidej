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
package mendel.model;

import java.util.List;
import java.util.Set;

public interface IEntity {
	
	public String getEntityName();
	
	public String getBaseName();
	
	public Set<JInterfaceEntity> getSuperInterfaces();

	public Set<JInterfaceEntity> getAllSuperInterfaces();
	
	public String getSuperInterfacesNames();

	public Set<String> getLocalMethods();
	
	public Set<String> getSuperMethods();
	
	public Set<String> getAllMethods();

	public Set<String> getNewMethods();	
	
	public Set<String> getInheritedMethods();	
	
	public Set<String> getSharedMethods();
	
	public Set<String> getAbstractMethods();
	
	public String toString(boolean recursive);

	public void addSuperInterface(JInterfaceEntity superInterface);
	
	public void addSubentity(IEntity entity);
	
	public List<IEntity> getSubentities();
	
	public boolean hasSubentities();

	public boolean recursiveLookup();

	public boolean recursiveLookup(final boolean recursive);

}
