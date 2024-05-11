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
package padl.creator.javafile.eclipse.test.advanced;

import org.junit.Assert;
import junit.framework.TestCase;

public class ExclusionTest extends TestCase {

	public ExclusionTest(final String name) {
		super(name);

	}

	// une classe dans une methode ou dans une inner classe
	// une classe dans enum
	//rechercher des exemples
	//continuer sur padlutil et faire les differents tests e ce niveau
	//j'espere trouver facilement les exemples

	public void test1() {
		Assert.assertTrue(true);
	}
}
