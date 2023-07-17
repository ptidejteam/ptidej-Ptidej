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
package padl.csharp.kernel.impl;

import padl.csharp.kernel.ICSharpFactory;
import padl.kernel.IFactory;
import padl.kernel.impl.Factory;

//import padl.kernel.exception.ModelDeclarationException;

/**
 * @author Gerardo Cepeda
 * @since  2009/04/20
 */
public class CSharpFactory extends Factory implements ICSharpFactory {
	private static final long serialVersionUID = -1032539567080323691L;

	private static IFactory UniqueInstance;
	public static IFactory getInstance() {
		if (CSharpFactory.UniqueInstance == null) {
			CSharpFactory.UniqueInstance = new CSharpFactory();
		}
		return CSharpFactory.UniqueInstance;
	}
	private CSharpFactory() {
	}
}
