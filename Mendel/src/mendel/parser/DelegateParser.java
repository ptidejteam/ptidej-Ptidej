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

import mendel.model.IEntity;
import mendel.model.JInterfaceEntity;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Type;


public abstract class DelegateParser {
	/*
	 * Only for reuse purpose
	 * 
	 * LifeCycle (partial, see subclasses for complete)
	 * - parseEntity: currentEntity builder (straight lc)
	 */

	
	private JParser parent; // delegation-based inheritance 

	private IEntity currentEntity;
	
	public DelegateParser(JParser aParser) {
		this.parent = aParser;
	}

	/*
	 * Basic protocol
	 */
	
	public IEntity parseEntity(JavaClass aClass, boolean deepParse) {
		this.currentEntity = createEntity(aClass);
		for (String superInt : aClass.getInterfaceNames()) {
			JInterfaceEntity sint = (JInterfaceEntity) getEntityByName(superInt);
			this.currentEntity.addSuperInterface(sint);
			sint.addSubentity(this.currentEntity);
		}
		if( deepParse )
			visitMethods(aClass);
		return currentEntity();
	}
	
	public abstract IEntity createEntity(JavaClass aClass);
	
	// warning, currentEntity ref is updated AFTER call to createEntity
	protected IEntity currentEntity() {
		return this.currentEntity;
	}
	

	/*
	 * Helper methods
	 */
	
	public boolean isConstructor(Method m) {
		return m.getName().equals("<init>") || m.getName().equals("<clinit>");
	}

	public void visitMethods(JavaClass aClass) {
		for (Method method : aClass.getMethods())
			if( !isConstructor(method) )
				visitMethod(method);
	}
	
	public abstract void visitMethod(Method m);

	
	/*
	 * Delegation-based inheritance
	 */
	
	public String getFullSignature(Method method) {
		return this.parent.getFullSignature(method);
	}

	public String buildTypeSignature(Type[] argumentTypes, Type returnType) {
		return this.parent.buildTypeSignature(argumentTypes, returnType);
	}

	public IEntity getEntityByName(String className) {
		return this.parent.getEntityByName(className);
	}

	// could implement more delegation...
	
}
