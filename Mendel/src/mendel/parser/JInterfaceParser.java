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
package mendel.parser;

import mendel.model.JInterfaceEntity;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;


public class JInterfaceParser extends DelegateParser {

	public JInterfaceParser(JParser aParser) {
		super(aParser);
	}

	@Override
	protected JInterfaceEntity currentEntity() {
		return (JInterfaceEntity) super.currentEntity();
	}

	/*
	 * Visit
	 */

	@Override
	public JInterfaceEntity createEntity(JavaClass aClass) {
		return new JInterfaceEntity(aClass.getClassName());
	}

	@Override
	public void visitMethod(Method aMethod) {
		currentEntity().addLocalMethod(getFullSignature(aMethod));
	}

}
