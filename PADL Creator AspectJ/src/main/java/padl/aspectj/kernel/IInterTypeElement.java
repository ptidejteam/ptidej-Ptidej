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
package padl.aspectj.kernel;

import padl.kernel.IFirstClassEntity;

/**
 * @author Jean-Yves Guyomarc'h
 *
 */
public interface IInterTypeElement extends IAspectElement {

	public IFirstClassEntity getTargetEntity();

	public void setTargetEntity(final IFirstClassEntity anEntity);

}
