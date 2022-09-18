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
package mendel;

import java.util.Collection;

import mendel.model.IEntity;


/**
 * 
 * TODO: genericity of IRepository and repository architecture of Mendel
 * 
 * - generalize IRepository with a type parameter instead of IEntity
 *   - use Item instead of Entity as a generic word
 * - create different implementations of IRepository: IEntity, Map, other model....
 * - the key format is unspecified
 * - each repository at instantiation registers itself in the Mendel object as a repository for:
 *   - a project
 *   - and the type of item stored
 *   - need perhaps some uniformization for the key format
 * - whenever a part needs access to a repository, he should give the project id and the item type.
 * 
 * @author Simon Denier
 * @since Jun 13, 2008
 *
 */
public interface IRepository extends Iterable<IEntity> {

	public IEntity getEntity(String qualifiedName);

	public boolean hasEntityRegistered(String qualifiedName);

	public void registerEntity(String qualifiedName, IEntity entity);

	public int size();

	public Collection getAllEntities();

}
