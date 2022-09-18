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
import mendel.model.JClassEntity;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.INVOKESPECIAL;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.MethodGen;


public class JClassParser extends DelegateParser {

	/*
	 * LifeCycles (from general to inner most)
	 * - instance lifecycle: is a component of JParser (singleton nature ?)
	 *  - parseEntity: currentEntity builder (straight lc - see DelegateParser)
	 *   - detectSpecialized: MethodCallVisitor visit (multi-step LC - see MCV)
	 */

	// placeholder for Bytecode Visitor
	private MethodCallVisitor methodCallVisitor;

	public JClassParser(JParser aParser) {
		super(aParser);
		this.methodCallVisitor = new MethodCallVisitor();
		// MethodCallVisitor LifeCycle1/3: create and link bytecode Visitor
	}
	
	@Override
	protected JClassEntity currentEntity() {
		return (JClassEntity) super.currentEntity();
	}

	// A reminder of the data-flow passing style
	//	@Override
	//	public JClassEntity getEntityByName(String className) {
	//		return currentEntity();
	//	}

	/*
	 * Visit
	 */

	@Override
	public IEntity parseEntity(JavaClass aClass, boolean deepParse) {
		updateConstantPool(aClass);
		// MethodCallVisitor LifeCycle2/3: update constant pool with current class
		return super.parseEntity(aClass, deepParse);
	}

	@Override
	public JClassEntity createEntity(JavaClass aClass) {
		// Hypothesis: if root type, getEntity... will return null for super entity
		JClassEntity superEntity = (JClassEntity) getEntityByName(aClass.getSuperclassName());
		JClassEntity newEntity = new JClassEntity(aClass.getClassName(), superEntity);
		if( !newEntity.isRootClass() )
			superEntity.addChild(newEntity);
		return newEntity;
	}

	@Override
	public void visitMethod(Method aMethod) {
		// dispatch on Method attributes, no polymorphism :( 
		if (aMethod.isStatic()) {
			// static kw is prepend so there is no mismatch
			// between a static and a normal method with same signature 
			String staticSig = "static " + getFullSignature(aMethod);
			currentEntity().addStatic(staticSig);
			// static method are not added to local set, since they are not inherited
			// currentEntity().addLocalMethod(staticSig);
			return ;
		}
		if( aMethod.isPrivate() ) {
			currentEntity().addPrivate(getFullSignature(aMethod));
			return ;
		}
//		else { // normal case
		currentEntity().addLocalMethod(getFullSignature(aMethod));
		if (aMethod.isAbstract()) {
			currentEntity().addAbstract(getFullSignature(aMethod));
		}
		else if (!aMethod.isNative()) {
			detectDefining(aMethod);
			detectSpecialized(aMethod);
		}
//		}
	}

	private void detectDefining(Method aMethod) {
		String sig = getFullSignature(aMethod);
		JClassEntity declaringSuperEntity = currentEntity()
			.findSuperClassDeclaringMethod(sig);
		if (declaringSuperEntity != null
			&& declaringSuperEntity.hasAbstractMethod(sig)) {
			currentEntity().addDefining(sig);
		}
	}

	private void detectSpecialized(Method aMethod) {
		this.methodCallVisitor.detectSpecialized(aMethod, currentEntity()
			.getEntityName());
		// MethodCallVisitor LifeCycle3/3: launch detection in aMethod
	}

	private void updateConstantPool(JavaClass aClass) {
		this.methodCallVisitor.constantPoolGen = new ConstantPoolGen(aClass
			.getConstantPool());
	}

	class MethodCallVisitor extends org.apache.bcel.generic.EmptyVisitor {
		/*
		 * MethodCallVisitor Protocol
		 * 1) create one with each Parser
		 * 2) update constantPoolGen when switching between JavaClass
		 * 3) run inside an enclosingMethod to detect super calls in it
		 */

		private Method enclosingMethod;
		private ConstantPoolGen constantPoolGen;

		private void detectSpecialized(Method aMethod, String className) {
			this.enclosingMethod = aMethod;
			MethodGen mg = new MethodGen(aMethod, className,
				this.constantPoolGen);
			InstructionHandle ih = mg.getInstructionList().getStart();
			for (; ih != null; ih = ih.getNext()) {
				ih.getInstruction().accept(this);
			}
		}

		@Override
		public void visitINVOKESPECIAL(INVOKESPECIAL aSpecialCall) {
			String sig = getFullSignature(this.enclosingMethod);
			String callSig = getFullSignatureCP(aSpecialCall);
			if( sig.equals(callSig) // super invocation of method of same sig
// INVOKESPECIAL: not a super call but a recursive call in a private method
// not used since private methods are not visited by this visitor
//				&& !this.enclosingMethod.isPrivate()
				) {
				currentEntity().addSpecialized(sig);
			} 
		}

		private String getFullSignatureCP(INVOKESPECIAL specialCall) {
			return specialCall.getName(this.constantPoolGen)
				+ buildTypeSignature(specialCall
					.getArgumentTypes(this.constantPoolGen), specialCall
					.getReturnType(this.constantPoolGen));
		}

	}

}
