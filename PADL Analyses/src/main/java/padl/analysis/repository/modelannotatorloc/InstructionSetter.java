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
/** (c) Copyright 2001 and following years, Yann-Gaël Guéhéneuc,
 * University of Montreal.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the author, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package padl.analysis.repository.modelannotatorloc;

import java.util.Iterator;
import java.util.Stack;

import padl.kernel.IAbstractModel;
import padl.kernel.IAggregation;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IComposition;
import padl.kernel.IConstituent;
import padl.kernel.IConstructor;
import padl.kernel.IContainerAggregation;
import padl.kernel.IContainerComposition;
import padl.kernel.ICreation;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGetter;
import padl.kernel.IGhost;
import padl.kernel.IInterface;
import padl.kernel.IMemberClass;
import padl.kernel.IMemberGhost;
import padl.kernel.IMemberInterface;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import padl.kernel.IPackage;
import padl.kernel.IPackageDefault;
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.kernel.impl.DepthFirstTraverser;
import padl.motif.IDesignMotifModel;
import padl.visitor.IWalker;
import util.io.ProxyConsole;
import util.lang.Modifier;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/03/09
 */
public class InstructionSetter implements IWalker {
	private final DepthFirstTraverser traverser = new DepthFirstTraverser();
	private final BCELInstructionFinder instFinder;
	private final Stack stackOfEntities;

	public InstructionSetter(final BCELInstructionFinder aBCELInstructionFinder) {
		this.instFinder = aBCELInstructionFinder;
		this.stackOfEntities = new Stack();
	}

	public void close(final IAbstractModel anAbstractModel) {
	}
	public void close(final IClass aClass) {
		this.stackOfEntities.pop();
	}
	public void close(final IConstructor aConstructor) {
	}
	public void close(final IDelegatingMethod aDelegatingMethod) {
	}
	public void close(final IDesignMotifModel aPatternModel) {
	}
	public void close(final IGetter aGetter) {
	}
	public void close(final IGhost aGhost) {
	}
	public void close(final IInterface anInterface) {
		// Yann 2007/03/06: Member classes in interface...
		// In QuickUML 2001, a member class Entry is
		// declared in interface MultiMap...
		this.stackOfEntities.pop();
	}
	public void close(final IMemberClass aMemberClass) {
		this.stackOfEntities.pop();
	}
	public void close(final IMemberGhost aMemberGhost) {
	}
	public void close(final IMemberInterface aMemberInterface) {
		// Yann 2007/03/06: Member classes in interface...
		// In QuickUML 2001, a member class Entry is
		// declared in interface MultiMap...
		this.stackOfEntities.pop();
	}
	public void close(final IMethod aMethod) {
	}
	public void close(final IPackage aPackage) {
	}
	public void close(final IPackageDefault aPackage) {
	}
	public void close(final ISetter aSetter) {
	}
	public String getName() {
		return "Setter of numbers of instructions";
	}
	public Object getResult() {
		return null;
	}
	public void open(final IAbstractModel anAbstractModel) {
	}
	public void open(final IClass aClass) {
		this.stackOfEntities.push(aClass);
	}
	public void open(final IConstructor aConstructor) {
		this.open((IOperation) aConstructor);
	}
	public void open(final IDelegatingMethod aDelegatingMethod) {
		this.open((IOperation) aDelegatingMethod);
	}
	public void open(final IDesignMotifModel aPatternModel) {
	}
	public void open(final IGetter aGetter) {
		this.open((IOperation) aGetter);
	}
	public void open(final IGhost aGhost) {
	}
	public void open(final IInterface anInterface) {
		this.stackOfEntities.push(anInterface);
	}
	public void open(final IMemberClass aMemberClass) {
		this.stackOfEntities.push(aMemberClass);
	}
	public void open(final IMemberGhost aMemberGhost) {
	}
	public void open(final IMemberInterface aMemberInterface) {
		// Yann 2007/03/06: Member classes in interface...
		// In QuickUML 2001, a member class Entry is
		// declared in interface MultiMap...
		this.stackOfEntities.push(aMemberInterface);
	}
	public void open(final IMethod aMethod) {
		this.open((IOperation) aMethod);
	}
	private void open(final IOperation aMethod) {
		// Yann: 2007/03/16: Ghosts!
		// The stack can be empty because we don't deal with Ghosts.
		if (!this.stackOfEntities.empty() && !aMethod.isAbstract()
				&& !Modifier.isNative(aMethod.getVisibility())) {

			final StringBuffer buffer = new StringBuffer();
			final Iterator iterator = this.stackOfEntities.iterator();
			buffer.append(((IFirstClassEntity) iterator.next()).getID());
			while (iterator.hasNext()) {
				buffer.append('.');
				final IFirstClassEntity entity =
					(IFirstClassEntity) iterator.next();
				buffer.append(entity.getName());
			}

			final Integer instCount =
				this.instFinder.getInstructionCount(
					buffer.toString(),
					aMethod.getDisplayID());

			if (instCount == null) {
				//	System.err.print(
				//		"Could not find instruction count for ");
				//	System.err.print(this.enclosingClass.getName());
				//	System.err.print('.');
				//	System.err.println(aMethod.getID());

				// Yann 2006/03/09: Compatibility 1.1 -> 1.2+
				// If the method is found through CFParse but
				// not by BCEL, then this is the famous
				// compatibility problem! We can remove
				// this method and obtain a clean model :-)
				((IFirstClassEntity) this.stackOfEntities.peek())
					.removeConstituentFromID(aMethod.getID());
			}
			else {
				aMethod.setCodeLines(new String[instCount.intValue()]);
			}
		}
	}
	public void open(final IPackage aPackage) {
	}
	public void open(final IPackageDefault aPackage) {
	}
	public void open(final ISetter aSetter) {
		this.open((IOperation) aSetter);
	}
	public void reset() {
		this.stackOfEntities.clear();
	}
	public final void unknownConstituentHandler(
		final String aCalledMethodName,
		final IConstituent aConstituent) {

		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(this.getClass().getName());
		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(" does not know what to do for \"");
		ProxyConsole.getInstance().debugOutput().print(aCalledMethodName);
		ProxyConsole.getInstance().debugOutput().print("\" (");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(aConstituent.getDisplayID());
		ProxyConsole.getInstance().debugOutput().println(')');
	}
	public void visit(final IAggregation anAggregation) {
	}
	public void visit(final IAssociation anAssociation) {
	}
	public void visit(final IComposition aComposition) {
	}
	public void visit(final IContainerAggregation aContainerAggregation) {
	}
	public void visit(final IContainerComposition aContainerComposition) {
	}
	public void visit(final ICreation aCreation) {
	}
	public void visit(final IField aField) {
	}
	public void visit(final IMethodInvocation aMethodInvocation) {
	}
	public void visit(final IParameter aParameter) {
	}
	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		// Do nothing for uninteresting primitive types.
	}
	public void visit(final IUseRelationship aUse) {
	}

	public void traverse(Iterator iterator) {

		this.traverser.traverse(this,
				iterator,
				null);
	}
}
