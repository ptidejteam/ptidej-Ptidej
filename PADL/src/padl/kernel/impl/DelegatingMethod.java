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
import padl.kernel.IAssociation;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IElementMarker;
import padl.kernel.IMethod;
import padl.kernel.exception.ModelDeclarationException;
import padl.util.Util;
import util.io.ProxyConsole;
import util.multilingual.MultilingualManager;
import com.ibm.toad.cfparse.utils.Access;

class DelegatingMethod extends Method implements IElementMarker,
		IDelegatingMethod {

	private static final long serialVersionUID = 3999083087438818257L;
	private IAssociation targetAssoc;
	private IMethod targetMethod;

	// Yann: This is a patch for the clone().
	// The problem is that when cloning a model, it may happen
	// that the target association (respectively target method)
	// is cloned before this DelegatingMethod (that is linked to it)
	// is cloned. A clean implementation would be to generalize
	// the use of PropertyChangeEvents: a model would notify
	// its PEntities that a clone event happened, in turn, the 
	// PEntities would notify their PElements of this clone event.
	// Thus, if a Association is cloned before the DelegatingMethod
	// that is linked to it, the Association will sent a clone
	// event to this DelegatingMethod, that will in turn clone itself,
	// and then accept the clone of its target Association.
	// Wonderful, isn't it?
	private Association temporaryTargetAssociation;
	private Method temporaryTargetMethod;

	// Yann 2011/09/06: Duh!
	// A DelegatingMethod cannot be delegating to itself, 
	// else it would create a kind of circular call...
	//	public DelegatingMethod(final char[] anID, final IAssociation targetAssoc)
	//			 {
	//
	//		this(anID, targetAssoc, null);
	//	}
	public DelegatingMethod(
		final char[] anID,
		final IAssociation aTargetAssociation,
		final IMethod aTargetMethod) {

		super(anID, aTargetMethod);

		this.setTargetAssoc(aTargetAssociation);
		// Yann 2011/09/06: Duh!
		// A DelegatingMethod cannot be delegating to itself, 
		// else it would create a kind of circular call...
		//	if (aTargetMethod == null) {
		//		this.setTargetMethod(this);
		//	}
		//	else {
		this.setTargetMethod(aTargetMethod);
		//	}
	}
	public void endCloneSession() {
		super.endCloneSession();

		this.temporaryTargetAssociation = null;
		this.temporaryTargetMethod = null;
	}
	public IAssociation getTargetAssoc() {
		return this.targetAssoc;
	}
	public IMethod getTargetMethod() {
		return this.targetMethod;
	}
	public void setTargetAssoc(final IAssociation anAssociation) {
		this.targetAssoc = anAssociation;
	}
	public void setTargetMethod(final IMethod aMethod) {
		this.targetMethod = aMethod;
	}
	public void setVisibility(int visibility) {
		if (Access.isAbstract(visibility)) {
			throw new ModelDeclarationException(MultilingualManager.getString(
				"ABSTRACT",
				IDelegatingMethod.class,
				new Object[] { this.getClass() }));
		}
		super.setVisibility(visibility);
	}
	public void startCloneSession() {
		super.startCloneSession();
		DelegatingMethod clone = (DelegatingMethod) this.getClone();

		// Yann: This is a beautiful, beautiful hack...
		if (this.temporaryTargetAssociation != null) {
			clone.targetAssoc = this.temporaryTargetAssociation;
		}
		if (this.temporaryTargetMethod != null) {
			clone.targetMethod = this.temporaryTargetMethod;
		}
	}
	public String toString() {
		if (Constants.DEBUG) {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("// PDelegatingMethod.toString()");
		}
		return this.toString(0);
	}
	public String toString(final int tab) {
		if (getTargetAssoc().getCardinality() > 1) {
			setCodeLines("for (Enumeration enum = "
					+ this.targetAssoc.getDisplayName()
					+ ".elements(); enum.hasMoreElements(); (("
					+ this.getTargetAssoc().getTargetEntity().getDisplayName()
					+ ") enum.nextElement())."
					+ this.getTargetMethod().getCallDeclaration() + ");");
		}
		else {
			setCodeLines(this.getTargetAssoc().getDisplayName() + "."
					+ this.getTargetMethod().getCallDeclaration() + ";");
		}

		StringBuffer codeEq = new StringBuffer();
		Util.addTabs(tab, codeEq);
		codeEq.append("// Method linked to: ");
		codeEq.append(getTargetAssoc().getName());
		codeEq.append('\n');
		codeEq.append(super.toString(tab));

		return codeEq.toString();
	}
}
