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

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.ibm.toad.cfparse.utils.Access;

import padl.event.IEvent;
import padl.event.IModelListener;
import padl.kernel.Constants;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentOfOperation;
import padl.kernel.IFilter;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import padl.kernel.IParameter;
import padl.kernel.exception.ModelDeclarationException;
import padl.util.Util;
import padl.visitor.IVisitor;
import util.io.ProxyConsole;
import util.multilingual.MultilingualManager;

//Sebastien Colladon 23/04/2012 : Change the visibility to public in order to allow other project to extend from this class in the particular case of eclipse bundle loader (avoid IllegalAccessError).
public abstract class Operation extends Element
		implements IOperation, IPrivateModelObservable {
	private static final long serialVersionUID = 8145249048497089055L;

	// Yann 2013/07/18: Duplication of intent!
	// The subclasses of this class declared containers, which
	// should be declared here, while this class declared an
	// empty container: cleanup this mess!
	//	private GenericObservable container = new GenericObservable();
	// Yann 2009/04/29: Not anymore but...
	// I don't need the field protected for testing but for
	// access in padl.cpp.kernel.impl.GlobalFunction...
	protected AbstractGenericContainerOfConstituents container = new GenericContainerOfInsertionOrderedConstituents(
			this);

	public Operation(final char[] anID) {
		super(anID);
		// Yann 2016/09/21: Important naming comvention!
		// I must update the parameters even if none to
		// make sure that the name and ID of the operation
		// follows my naming convention for operations:
		// their names and IDs must include "()".
		this.updatePathWithParameters();
	}

	public Operation(final char[] anID, final IMethod anAttachedMethod) {
		super(anID);
		this.attachTo(anAttachedMethod);
		// Useless call?
		//	this.updatePathWithParameters();
	}

	public Operation(final IMethod anAttachedMethod) {
		this(anAttachedMethod.getID(), anAttachedMethod);
		// Useless call?
		//	this.updatePathWithParameters();
	}

	public void accept(final IVisitor visitor) {
		this.accept(visitor, "open");
		final Iterator iterator = this.getConcurrentIteratorOnConstituents();
		while (iterator.hasNext()) {
			final IConstituent constituent = (IConstituent) iterator.next();
			// System.out.println(constituent.toString());
			constituent.accept(visitor);
		}
		this.accept(visitor, "close");
	}

	public void addConstituent(final IConstituent aConstituent) {
		if (aConstituent instanceof IConstituentOfOperation) {
			this.addConstituent((IConstituentOfOperation) aConstituent);
		}
		else {
			throw new ModelDeclarationException(MultilingualManager
					.getString("PARAM_OR_METHOD_ADD", IOperation.class));
		}
	}

	public void addConstituent(
			final IConstituentOfOperation aMethodConstituent) {
		// Yann 2004/07/31: Test and order II!
		// I cannot use the addConstituent() method here because I don't
		// want the method invocations to be sorted!
		//	super.addConstituent(aMethodInvocation);
		// TODO Is this comment still true with the GenericContainerOfInsertionOrderedConstituents?
		this.container.directlyAddConstituentWithUniqueID(aMethodConstituent);

		// Yann 2014/04/17: Law and order!
		// Of course, I update the path *after* having added the parameters...
		if (aMethodConstituent instanceof IParameter) {
			this.updatePathWithParameters();
		}
	}

	public void addModelListener(final IModelListener aModelListener) {
		this.container.addModelListener(aModelListener);
	}

	public void addModelListeners(final List aListOfModelListeners) {
		this.container.addModelListeners(aListOfModelListeners);
	}

	public boolean doesContainConstituentWithID(final char[] anID) {
		return this.container.doesContainConstituentWithID(anID);
	}

	public boolean doesContainConstituentWithName(final char[] aName) {
		return this.container.doesContainConstituentWithName(aName);
	}

	// Farouk 2004/02/13
	// Method added to compare two methods.
	/**
	 * Return if two methods are equal.
	 * This method compares the names, the number
	 * of parameters and the parameter types.
	 * 
	 * @param m	a method
	 * @return <code>true</code> if the methods are equal,
	 *         <code>false</code> otherwise.
	 */
	public boolean equals(final Object o) {
		if (!(o instanceof IOperation)) {
			return false;
		}

		// Yann 2004/03/31: ConstituentID!
		// This method needs to test the actor ID only.
		// Method might be different if they don't
		// have the same sets of method invocations!
		// Yann 2011/04/12: Pot-pourri
		// If by chance operations from different class are put
		// in a same list, then to find them back, I must test
		// also their names, not just their signatures...
		final IOperation otherMethod = (IOperation) o;
		return Arrays.equals(this.getID(), otherMethod.getID())
				&& Arrays.equals(this.getName(), otherMethod.getName());
		// Yann 2006/02/06: Revert change
		// I remove this test because it does not make real sense 
		// to compare the number of actors... would be (much) better
		// to compare the real signature of the methods... their ID?
		//	&& this.getNumberOfConstituents(IParameter.class)
		//		== otherMethod.getNumberOfConstituents(IParameter.class);
	}

	public void fireModelChange(final String anEventType,
			final IEvent anEvent) {
		this.container.fireModelChange(anEventType, anEvent);
	}

	public String getCallDeclaration() {
		final StringBuffer codeEq = new StringBuffer();
		codeEq.append(this.getName());
		codeEq.append('(');
		final Iterator iterator = this
				.getIteratorOnConstituents(IParameter.class);
		// Yann 2004/04/10: Method invocation!
		// A method may now contain instances of IMethodInvocation
		// in addition to instances of IParameter.
		// Yann 2005/10/12: Iterator!
		// I have now an iterator able to iterate over a
		// specified type of constituent of a list.
		//	boolean hasPredecessor = false;
		//	while (enum.hasNext()) {
		//		final IConstituent constituent = (IConstituent) enum.next();
		//		if (constituent instanceof IParameter) {
		//			if (hasPredecessor) {
		//				codeEq.append(",");
		//			}
		//			else {
		//				hasPredecessor = true;
		//			}
		//			codeEq.append(constituent.getName());
		//		}
		//	}
		while (iterator.hasNext()) {
			final IParameter parameter = (IParameter) iterator.next();
			codeEq.append(parameter.getName());
			if (iterator.hasNext()) {
				codeEq.append(',');
			}
		}
		codeEq.append(')');
		return codeEq.toString();
	}

	public Iterator getConcurrentIteratorOnConstituents() {
		return this.container.getConcurrentIteratorOnConstituents();
	}

	public Iterator getConcurrentIteratorOnConstituents(final IFilter filter) {
		return this.container.getConcurrentIteratorOnConstituents();
	}

	public Iterator getConcurrentIteratorOnConstituents(
			final java.lang.Class aConstituentType) {

		return this.container
				.getConcurrentIteratorOnConstituents(aConstituentType);
	}

	public IConstituent getConstituentFromID(final char[] anID) {
		return this.container.getConstituentFromID(anID);
	}

	public IConstituent getConstituentFromID(final String anID) {
		return this.getConstituentFromID(anID.toCharArray());
	}

	public IConstituent getConstituentFromName(final char[] aName) {
		return this.container.getConstituentFromName(aName);
	}

	public IConstituent getConstituentFromName(final String aName) {
		return this.container.getConstituentFromName(aName.toCharArray());
	}

	public Iterator getIteratorOnConstituents() {
		return this.container.getIteratorOnConstituents();
	}

	public Iterator getIteratorOnConstituents(final IFilter aFilter) {
		return this.container.getIteratorOnConstituents(aFilter);
	}

	public Iterator getIteratorOnConstituents(
			java.lang.Class aConstituentType) {
		return this.container.getIteratorOnConstituents(aConstituentType);
	}

	public Iterator getIteratorOnModelListeners() {
		return this.container.getIteratorOnModelListeners();
	}

	public int getNumberOfConstituents() {
		return this.container.getNumberOfConstituents();
	}

	public int getNumberOfConstituents(final java.lang.Class aConstituentType) {
		return this.container.getNumberOfConstituents(aConstituentType);
	}

	/**
	 * This methods is used by the clone protocol.
	 */
	public void performCloneSession() {
		super.performCloneSession();

		// Yann 2005/05/09: Clones of parameters and other stuff...
		// I should not forget to clone any constituents contained
		// in a method, such as parameters, method invocations...
		final Iterator iterator = this.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			final IConstituent constituent = (IConstituent) iterator.next();

			constituent.startCloneSession();
			constituent.performCloneSession();

			((Operation) this.getClone())
					.addConstituent(constituent.getClone());

			constituent.endCloneSession();
		}
	}

	public void removeConstituentFromID(final char[] anID) {
		this.container.removeConstituentFromID(anID);
		this.updatePathWithParameters();
	}

	public void removeModelListener(final IModelListener aModelListener) {
		this.container.removeModelListener(aModelListener);
	}

	public void removeModelListeners(final List aListOfModelListeners) {
		this.container.removeModelListeners(aListOfModelListeners);
	}

	public void startCloneSession() {
		super.startCloneSession();
		// Yann 2010/10/03: Objects!
		// The "container" is now an instance of a class
		// and must be assigned a new instance independently.
		//	((Operation) this.getClone()).container.resetListOfConstituents();
		((Operation) this
				.getClone()).container = new GenericContainerOfInsertionOrderedConstituents(
						((Operation) this.getClone()));

		// Yann 2015/09/01: Clone of listeners!
		// I don't forget to clone the listners too...
		// TODO To implement
	}

	public String toString() {
		if (Constants.DEBUG) {
			ProxyConsole.getInstance().debugOutput()
					.println("// Operation.toString()");
		}
		return this.toString(0);
	}

	public String toString(final int tab) {
		if (Constants.DEBUG) {
			ProxyConsole.getInstance().debugOutput()
					.println("// Operation.toString(int)");
		}
		final StringBuffer codeEq = new StringBuffer();
		this.toStringStart(tab, codeEq);
		this.toStringSignature(codeEq);
		this.toStringBody(tab, codeEq);
		return codeEq.toString();
	}

	protected void toStringBody(final int tab, final StringBuffer codeEq) {
		codeEq.append(" {\n");
		final Iterator iterator = this
				.getIteratorOnConstituents(IMethodInvocation.class);
		// Yann 2005/10/12: Iterator!
		// I have now an iterator able to iterate over a
		// specified type of constituent of a list.
		//	while (iterator.hasNext()) {
		//		final IElement element = (IElement) iterator.next();
		//		if (element instanceof IMethodInvocation) {
		//			Util.addTabs(tab + 1, codeEq);
		//			codeEq.append("// Method invocation: ");
		//			codeEq.append(element.toString());
		//			codeEq.append('\n');
		//		}
		//	}
		while (iterator.hasNext()) {
			final IMethodInvocation methodInvocation = (IMethodInvocation) iterator
					.next();
			Util.addTabs(tab + 1, codeEq);
			codeEq.append("// Method invocation: ");
			codeEq.append(methodInvocation.toString());
			codeEq.append('\n');
		}
		String[] codeLines = this.getCodeLines();
		// Yann: Can be null. The case "empty array" is dealt with in the loop. 
		if (codeLines != null) {
			for (int i = 0; i < codeLines.length; i++) {
				Util.addTabs(tab + 1, codeEq);
				codeEq.append(codeLines[i]);
				codeEq.append('\n');
			}
		}
		Util.addTabs(tab, codeEq);
		codeEq.append('}');
	}

	protected void toStringSignature(final StringBuffer codeEq) {
		codeEq.append(this.getName());
		codeEq.append('(');
		final Iterator iterator = this
				.getIteratorOnConstituents(IParameter.class);
		// Yann 2005/10/12: Iterator!
		// I have now an iterator able to iterate over a
		// specified type of constituent of a list.
		//	while (iterator.hasNext()) {
		//		final IElement element = (IElement) iterator.next();
		//		if (element instanceof IParameter) {
		//			codeEq.append(element.toString());
		//			// The list of actors of a methods contains
		//			// both parameters and method invocations, so the
		//			// following test while succeeds even if there is
		//			// no more parameters...
		//			if (iterator.hasNext()) {
		//				codeEq.append(", ");
		//			}
		//		}
		//	}
		while (iterator.hasNext()) {
			final IParameter parameter = (IParameter) iterator.next();
			codeEq.append(parameter.toString());
			if (iterator.hasNext()) {
				codeEq.append(", ");
			}
		}
		codeEq.append(')');
	}

	protected void toStringStart(final int tab, final StringBuffer codeEq) {
		Util.addTabs(tab, codeEq);
		if (this.getComment() != null) {
			codeEq.append("/* ");
			codeEq.append(this.getComment());
			codeEq.append(" */\n");
			Util.addTabs(tab, codeEq);
		}
		codeEq.append(Access.getMethodAsString(this.getVisibility()));
		if (this.getVisibility() != 0) {
			codeEq.append(' ');
		}
	}

	private void updatePathWithParameters() {
		// Yann 2009/09/11: Paths of method need parameters
		// I add the parameter types to the path of method
		// as appropriate :-)
		final StringBuffer buffer = new StringBuffer();
		buffer.append(this.getPath());
		int indexOfLastOpeningParenthesis = buffer.lastIndexOf("(");
		if (indexOfLastOpeningParenthesis > -1) {
			buffer.delete(indexOfLastOpeningParenthesis, buffer.length());
		}
		buffer.append('(');
		Iterator iterator = this.getIteratorOnConstituents(IParameter.class);
		while (iterator.hasNext()) {
			final IParameter parameter = (IParameter) iterator.next();
			buffer.append(parameter.getTypeName());
			for (int i = 1; i < parameter.getCardinality(); i++) {
				buffer.append("[]");
			}
			if (iterator.hasNext()) {
				buffer.append(", ");
			}
		}
		buffer.append(')');
		this.setPath(buffer.toString().toCharArray());

		// Yann 2014/04/18: ID too...
		// I may happen that the ID does not correspond to the path
		// as build using the parameter. The path and ID should be
		// consistent, which is not a problem anymore with v6 but
		// is in v5... so I update the ID too.
		// This code duplicate the code above to highlight that it
		// does the same thing but is not really necessary in v6.
		// Yann 2016/09/21: Not necessary...
		// IDs should mimic the names as much as possible.
		/*
		buffer.setLength(0);
		buffer.append(this.getID());
		indexOfLastOpeningParenthesis = buffer.lastIndexOf("(");
		if (indexOfLastOpeningParenthesis > -1) {
			buffer.delete(indexOfLastOpeningParenthesis, buffer.length());
		}
		buffer.append('(');
		iterator = this.getIteratorOnConstituents(IParameter.class);
		while (iterator.hasNext()) {
			final IParameter parameter = (IParameter) iterator.next();
			buffer.append(parameter.getTypeName());
			for (int i = 1; i < parameter.getCardinality(); i++) {
				buffer.append("[]");
			}
			if (iterator.hasNext()) {
				buffer.append(", ");
			}
		}
		buffer.append(')');
		this.setID(buffer.toString().toCharArray());
		*/
	}
}
