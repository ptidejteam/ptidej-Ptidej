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
package padl.creator.classfile.util;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.MethodInfo;
import com.ibm.toad.cfparse.attributes.AttrInfoList;
import com.ibm.toad.cfparse.utils.Access;

/**
 * @version	0.1
 * @author 	Yann-Gaël Guéhéneuc
 */
public final class ExtendedMethodInfo {
	private final ConstantPool constantPool;
	private final char[] declaringClassFileName;
	private final AttrInfoList methodAttributes;
	private final char[] methodName;
	private final char[][] methodParameters;
	private final char[] methodReturnType;
	private final int methodVisibility;

	public ExtendedMethodInfo(final ClassFile declaringClassFile,
			final MethodInfo methodInfo) {

		this.constantPool = declaringClassFile.getCP();
		this.declaringClassFileName = declaringClassFile.getName()
				.toCharArray();
		this.methodAttributes = methodInfo.getAttrs();
		this.methodName = methodInfo.getName().toCharArray();
		this.methodParameters = new char[methodInfo.getParams().length][];
		for (int i = 0; i < methodInfo.getParams().length; i++) {
			final String paramDisplayName = methodInfo.getParams()[i];
			final char[] paramName = paramDisplayName.toCharArray();
			this.methodParameters[i] = paramName;
		}
		this.methodReturnType = methodInfo.getReturnType().toCharArray();
		this.methodVisibility = methodInfo.getAccess();
	}

	public AttrInfoList getAttributes() {
		return this.methodAttributes;
	}

	public ConstantPool getDeclaringClassConstantPool() {
		return this.constantPool;
	}

	public char[] getDeclaringClassName() {
		return this.declaringClassFileName;
	}

	public char[] getName() {
		return this.methodName;
	}

	public char[][] getParameters() {
		return this.methodParameters;
	}

	public char[] getReturnType() {
		return this.methodReturnType;
	}

	public int getVisibility() {
		return this.methodVisibility;
	}

	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(Access.getMethodAsString(this.getVisibility()));
		buffer.append(' ');
		buffer.append(this.getReturnType());
		buffer.append(' ');
		buffer.append(this.getDeclaringClassName());
		buffer.append('.');
		buffer.append(this.getName());
		buffer.append("(...)");
		return buffer.toString();
	}
}
