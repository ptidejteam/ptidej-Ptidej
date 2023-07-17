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
package padl.kernel.impl;

import padl.kernel.Constants;
import padl.kernel.IElementMarker;
import padl.kernel.IMethod;
import util.io.ProxyConsole;

public class Method extends Constructor implements IElementMarker, IMethod {
	// I used to have static counters in MethodInvocation and Parameter to
	// build their IDs. I replaced these counters with the following counters
	// so that their are dependent on the method, to ease the comparison of
	// subset of models.
	//	private static int NumberOfMethodInvocations = 0;
	//	private static int NumberOfParameters = 0;
	//	static int getUniqueMethodInvocationNumber() {
	//		return Method.NumberOfMethodInvocations++;
	//	}
	//	static int getUniqueParameterNumber() {
	//		return Method.NumberOfParameters++;
	//	}
	// Yann 2006/02/24: UniqueID...
	// I need a UniqueID for IMethodInvocation and IParemeter in IMethod
	// and for any IRelationship in IEntity. So, when using the method
	// concretelyAddConstituent(), I take care of the unique ID.

	private static final long serialVersionUID = -4174549756886894781L;
	static final char[] VOID = "void".toCharArray();

	// String used instead of Class because the class may not be compiled yet.
	// TODO: Class should be available, at least as Ghost, so should remove this String...
	private char[] returnType = Method.VOID;

	public Method(final char[] anID) {
		super(anID);
	}
	public Method(final char[] anID, final IMethod attachedMethod) {
		super(anID);
		this.attachTo(attachedMethod);
	}
	//	public Method(final String anID, final MethodInfo aMethod) {
	//		super(anID);
	//
	//		try {
	//			this.setVisibility(aMethod.getAccess());
	//		}
	//		catch (final ModelDeclarationException e) {
	//			// No error can occur.
	//		}
	//
	//		this.setReturnType(aMethod.getReturnType());
	//
	//		final String[] detectedParameters = aMethod.getParams();
	//		for (int i = 0; i < detectedParameters.length; i++) {
	//			try {
	//				this.addConstituent(
	//					new Parameter(
	//						Util.stripAndCapQualifiedName(
	//							detectedParameters[i])));
	//			}
	//			catch (final ModelDeclarationException e) {
	//			}
	//		}
	//	}
	public Method(final IMethod attachedMethod) {
		this(attachedMethod.getID(), attachedMethod);
	}
	//	public Method(final MethodInfo aMethod) {
	//		this(aMethod.getName(), aMethod);
	//	}
	public Method(final String anID) {
		super(anID.toCharArray());
	}

	public String getDisplayReturnType() {
		return String.valueOf(this.getReturnType());
	}
	public char[] getReturnType() {
		if (this.getAttachedElement() == null) {
			return this.returnType;
		}
		return ((IMethod) this.getAttachedElement()).getReturnType();
	}
	public void setReturnType(final char[] aType) {
		this.returnType = aType;
	}
	public String toString() {
		if (Constants.DEBUG) {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("// Method.toString()");
		}
		return this.toString(0);
	}
	public String toString(final int tab) {
		if (Constants.DEBUG) {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("// Method.toString(int)");
		}
		final StringBuffer codeEq = new StringBuffer();
		this.toStringStart(tab, codeEq);
		codeEq.append(this.getReturnType());
		codeEq.append(' ');
		this.toStringSignature(codeEq);
		if (isAbstract()) {
			return codeEq.toString() + ';';
		}
		this.toStringBody(tab, codeEq);
		return codeEq.toString();
	}
}
