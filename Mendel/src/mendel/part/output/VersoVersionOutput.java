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
package mendel.part.output;

import mendel.model.IEntity;

/**
 * @author Simon Denier
 * @since May 16, 2008
 *
 */
public class VersoVersionOutput extends VersoOutput {

	/* (non-Javadoc)
	 * @see mendel.part.output.VersoOutput#getObjectId(mendel.model.IEntity)
	 */
	@Override
	public String getObjectId(IEntity entity) {
		return getProject().getProjectname() + "." + super.getObjectId(entity);
	}
	
	

}
