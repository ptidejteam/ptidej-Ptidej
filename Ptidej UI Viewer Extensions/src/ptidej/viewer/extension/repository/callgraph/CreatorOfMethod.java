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
package ptidej.viewer.extension.repository.callgraph;

import ptidej.viewer.extension.repository.callgraph.model.Call;

import org.apache.bcel.classfile.EmptyVisitor;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.MethodGen;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/10/04
 */
public class CreatorOfMethod extends EmptyVisitor {
	private JavaClass javaClass;
	private ptidej.viewer.extension.repository.callgraph.model.Method method;
	private String methodName;

	public CreatorOfMethod(final String aMethodName) {
		this.methodName = aMethodName;
	}
	public void visitJavaClass(final JavaClass aJavaClass) {
		this.javaClass = aJavaClass;
		final Method[] methods = aJavaClass.getMethods();
		boolean found = false;
		for (int i = 0; i < methods.length; i++) {
			final String methodName =
				CallGraphGenerator.createMethodName(
					methods[i].getReturnType(),
					methods[i].getName(),
					methods[i].getArgumentTypes());
			if (methodName.equals(this.methodName)) {
				methods[i].accept(this);
				found = true;
				break;
			}
		}
		if (!found) {
			// If I cannot found the method, 
			// I look into the superclasses.
			try {
				this.visitJavaClass(aJavaClass.getSuperClasses()[0]);
			}
			catch (final ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	public void visitMethod(final Method aMethod) {
		this.method =
			CallGraphGenerator.createMethod(
				this.javaClass.getClassName(),
				this.javaClass.isClass(),
				aMethod.getReturnType(),
				aMethod.getName(),
				aMethod.getArgumentTypes());

		final MethodGen methodGen =
			new MethodGen(
				aMethod,
				this.javaClass.getClassName(),
				new ConstantPoolGen(this.javaClass.getConstantPool()));
		final CreatorOfCalls callCreator = new CreatorOfCalls(methodGen);
		callCreator.start();
		for (int i = 0; i < callCreator.getCalls().length; i++) {
			final Call call = callCreator.getCalls()[i];
			this.method.addCall(call);
		}
	}
	public ptidej
		.viewer
		.extension
		.repository
		.callgraph
		.model
		.Method getMethod() {

		return this.method;
	}
}
