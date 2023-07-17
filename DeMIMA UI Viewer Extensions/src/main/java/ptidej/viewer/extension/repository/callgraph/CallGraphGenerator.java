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

import java.util.HashMap;
import java.util.Map;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.Type;

import ptidej.viewer.extension.repository.callgraph.model.Method;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/10/04
 */
public class CallGraphGenerator {
	private static boolean IsEntryMethod;
	private static final Map MapMethodNamesMethods = new HashMap();
	private static CallGraphGenerator UniqueInstance;

	static Method createMethod(
		final String anEnclosingClassName,
		final boolean isClass,
		final Type aReturnType,
		final String aName,
		final Type[] someArgumentsTypes) {

		final String methodName =
			CallGraphGenerator.createMethodName(
				aReturnType,
				aName,
				someArgumentsTypes);
		Method method;
		if (!CallGraphGenerator
			.doesMethodExist(anEnclosingClassName, methodName)) {

			method =
				new Method(
					anEnclosingClassName,
					isClass,
					methodName,
					CallGraphGenerator.IsEntryMethod);
			CallGraphGenerator.put(anEnclosingClassName, methodName, method);
		}
		else {
			method = CallGraphGenerator.get(anEnclosingClassName, methodName);
		}
		CallGraphGenerator.IsEntryMethod = false;
		return method;
	}
	public static CallGraphGenerator getInstance() {
		if (CallGraphGenerator.UniqueInstance == null) {
			CallGraphGenerator.UniqueInstance = new CallGraphGenerator();
		}
		CallGraphGenerator.IsEntryMethod = true;
		return CallGraphGenerator.UniqueInstance;
	}
	static String createMethodName(
		final Type aReturnType,
		final String aName,
		final Type[] someArgumentsTypes) {

		final StringBuffer buffer = new StringBuffer();
		buffer.append(aReturnType);
		buffer.append(' ');
		buffer.append(aName);
		buffer.append('(');
		for (int j = 0; j < someArgumentsTypes.length; j++) {
			final Type argType = someArgumentsTypes[j];
			buffer.append(argType);
			if (j < someArgumentsTypes.length - 1) {
				buffer.append(", ");
			}
		}
		buffer.append(')');
		return buffer.toString();
	}
	static boolean doesMethodExist(
		final String anEnclosingClassName,
		final String aMethodName) {

		return CallGraphGenerator.MapMethodNamesMethods.containsKey(
			anEnclosingClassName + '.' + aMethodName);
	}
	static Method get(
		final String anEnclosingClassName,
		final String aMethodName) {

		return (Method) CallGraphGenerator.MapMethodNamesMethods.get(
			anEnclosingClassName + '.' + aMethodName);
	}
	static void put(
		final String anEnclosingClassName,
		final String aMethodName,
		final Method aMethod) {

		CallGraphGenerator.MapMethodNamesMethods.put(
			anEnclosingClassName + '.' + aMethodName,
			aMethod);
	}

	private CallGraphGenerator() {
	}
	public Method create(
		final JavaClass aJavaClass,
		final String aMethodName) {

		final CreatorOfMethod visitor = new CreatorOfMethod(aMethodName);
		aJavaClass.accept(visitor);
		return visitor.getMethod();
	}
}
