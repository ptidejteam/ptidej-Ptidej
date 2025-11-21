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
package padl.visitor.repository;

import java.util.Iterator;

import padl.kernel.IAbstractLevelModel;
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
import padl.util.Util;
import padl.visitor.IGenerator;
import util.io.ProxyConsole;
import util.lang.Modifier;

public final class JavaGenerator implements IGenerator {
	private final DepthFirstTraverser traverser = new DepthFirstTraverser();
	private final StringBuffer buffer = new StringBuffer();
	private int indentation = 0;

	public void close(final IAbstractLevelModel p) {
	}

	public void close(IAbstractModel anAbstractModel) {
	}

	public void close(final IClass p) {
		this.buffer.append('\n');
		this.buffer.append('}');
		this.buffer.append('\n');

		this.indentation--;
	}

	public void close(final IConstructor aConstructor) {
	}

	public void close(final IDelegatingMethod aDelegatingMethod) {
	}

	public void close(final IGetter aGetter) {
	}

	public void close(final IGhost p) {
		this.buffer.append('\n');
		this.buffer.append('}');
		this.buffer.append('\n');
		this.buffer.append('\n');

		this.indentation--;
	}

	public void close(final IInterface p) {
		this.buffer.append('\n');
		this.buffer.append('}');
		this.buffer.append('\n');
		this.buffer.append('\n');

		this.indentation--;
	}

	public void close(final IMemberClass aMemberClass) {
	}

	public void close(final IMemberGhost aMemberGhost) {
	}

	public void close(final IMemberInterface aMemberInterface) {
	}

	public void close(final IMethod aMethod) {
	}

	public void close(final IPackage aPackage) {
	}

	public void close(final IPackageDefault aPackage) {
	}

	public void close(final ISetter aSetter) {
	}

	private void commentsAndVisibility(final IConstituent p) {
		Util.addTabs(this.indentation, this.buffer);
		this.buffer.append("//id= ");
		this.buffer.append(p.getDisplayID());
		this.buffer.append('\n');
		Util.addTabs(this.indentation, this.buffer);
		if (p.getComment() != null) {
			this.buffer.append("/* ");
			this.buffer.append(p.getComment());
			this.buffer.append(" */\n");
			Util.addTabs(this.indentation, this.buffer);
		}
		this.buffer.append(Modifier.toString(p.getVisibility()));
	}

	public String getCode() {
		return this.buffer.toString();
	}

	public String getName() {
		return "Java";
	}

	public Object getResult() {
		return this.buffer.toString();
	}

	private void nameAndParameters(final IOperation p) {
		this.buffer.append(p.getName());
		this.buffer.append('(');
		final Iterator iterator = p.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			this.buffer.append(iterator.next().toString());
			if (iterator.hasNext()) {
				this.buffer.append(", ");
			}
		}
		this.buffer.append(')');
		if (p.isAbstract()) {
			this.buffer.append(';');
			return;
		}
		this.buffer.append(" {\n");
		String[] codeLines = p.getCodeLines();
		// Yann: Can be null. The case "empty array" is dealt with in the loop. 
		if (codeLines != null) {
			for (int i = 0; i < codeLines.length; i++) {
				Util.addTabs(this.indentation + 1, this.buffer);
				this.buffer.append(codeLines[i]);
				this.buffer.append('\n');
			}
		}
		Util.addTabs(this.indentation, this.buffer);
		this.buffer.append('}');
		this.buffer.append('\n');
		this.buffer.append('\n');
	}

	public void open(final IAbstractLevelModel p) {
		this.open((IAbstractModel) p);
	}

	public void open(IAbstractModel anAbstractModel) {
		this.reset();
	}

	public void open(final IClass p) {
		this.commentsAndVisibility(p);
		this.buffer.append(" class ");
		this.buffer.append(p.getName());
		Iterator iterator = p.getIteratorOnInheritedEntities();
		if (iterator.hasNext()) {
			this.buffer.append(" extends ");
			while (iterator.hasNext()) {
				this.buffer.append(
						((IFirstClassEntity) (iterator.next())).getName());
				if (iterator.hasNext())
					this.buffer.append(", ");
			}
		}
		iterator = p.getIteratorOnImplementedInterfaces();
		if (iterator.hasNext()) {
			this.buffer.append(" implements ");
			while (iterator.hasNext()) {
				this.buffer.append(
						((IFirstClassEntity) (iterator.next())).getName());
				if (iterator.hasNext())
					this.buffer.append(", ");
			}
		}
		this.buffer.append(' ');
		this.buffer.append('{');
		this.buffer.append('\n');
		this.buffer.append('\n');

		this.indentation++;
	}

	public void open(final IConstructor p) {
		this.commentsAndVisibility(p);
		this.buffer.append(' ');
		this.nameAndParameters(p);
	}

	public void open(final IDelegatingMethod p) {
		if (p.getTargetAssoc().getCardinality() > 1) {
			p.setCodeLines(
					"for (java.util.Enumeration enum = "
							+ p.getTargetAssoc().getDisplayName()
							+ ".elements(); enum.hasMoreElements(); (("
							+ p.getTargetAssoc().getTargetEntity()
									.getDisplayID()
							+ ") enum.nextElement())."
							+ p.getTargetMethod().getCallDeclaration() + ");");
		}
		else {
			p.setCodeLines(p.getTargetAssoc().getDisplayName() + "."
					+ p.getTargetMethod().getCallDeclaration() + ";");
		}

		Util.addTabs(this.indentation, this.buffer);
		this.buffer.append("// Method linked to: ");
		this.buffer.append(p.getTargetAssoc().getName());
		this.buffer.append('\n');

		this.open((IMethod) p);
	}

	public void open(final IGetter p) {
		this.open((IMethod) p);
	}

	public void open(final IGhost p) {
		this.commentsAndVisibility(p);
		this.buffer.append(" ghost ");
		this.buffer.append(p.getName());
		Iterator iterator = p.getIteratorOnInheritedEntities();
		if (iterator.hasNext()) {
			this.buffer.append(" extends ");
			while (iterator.hasNext()) {
				this.buffer.append(
						((IFirstClassEntity) (iterator.next())).getName());
				if (iterator.hasNext())
					this.buffer.append(", ");
			}
		}
		iterator = p.getIteratorOnImplementedInterfaces();
		if (iterator.hasNext()) {
			this.buffer.append(" implements ");
			while (iterator.hasNext()) {
				this.buffer.append(
						((IFirstClassEntity) (iterator.next())).getName());
				if (iterator.hasNext())
					this.buffer.append(", ");
			}
		}
		this.buffer.append(' ');
		this.buffer.append('{');
		this.buffer.append('\n');

		this.indentation++;
	}

	public void open(final IInterface p) {
		commentsAndVisibility(p);
		this.buffer.append(" interface ");
		this.buffer.append(p.getName());
		final Iterator iterator = p.getIteratorOnInheritedEntities();
		if (iterator.hasNext()) {
			this.buffer.append(" extends ");
			while (iterator.hasNext()) {
				this.buffer.append(
						((IFirstClassEntity) (iterator.next())).getName());
				if (iterator.hasNext())
					this.buffer.append(", ");
			}
		}
		this.buffer.append(' ');
		this.buffer.append('{');
		this.buffer.append('\n');

		this.indentation++;
	}

	public void open(final IMemberClass aMemberClass) {
	}

	public void open(final IMemberGhost aMemberGhost) {
	}

	public void open(final IMemberInterface aMemberInterface) {
	}

	public void open(final IMethod p) {
		this.commentsAndVisibility(p);
		this.buffer.append(' ');
		this.buffer.append(p.getReturnType());
		this.buffer.append(' ');
		this.nameAndParameters(p);
	}

	public void open(final IPackage aPackage) {
	}

	public void open(final IPackageDefault aPackage) {
	}

	public void open(final ISetter p) {
		this.open((IMethod) p);
	}

	public void reset() {
		this.buffer.setLength(0);
		this.indentation = 0;
	}

	public final void unknownConstituentHandler(final String aCalledMethodName,
			final IConstituent aConstituent) {

		ProxyConsole.getInstance().debugOutput()
				.print(this.getClass().getName());
		ProxyConsole.getInstance().debugOutput()
				.print(" does not know what to do for \"");
		ProxyConsole.getInstance().debugOutput().print(aCalledMethodName);
		ProxyConsole.getInstance().debugOutput().print("\" (");
		ProxyConsole.getInstance().debugOutput()
				.print(aConstituent.getDisplayID());
		ProxyConsole.getInstance().debugOutput().println(')');
	}

	public void visit(final IAggregation p) {
		Util.addTabs(this.indentation, this.buffer);
		this.buffer.append("// Aggregation: ");
		this.buffer.append(p.getName());
		this.buffer.append('\n');
	}

	public void visit(final IAssociation p) {
		Util.addTabs(this.indentation, this.buffer);
		this.buffer.append("// Association: ");
		this.buffer.append(p.getName());
		this.buffer.append('\n');
	}

	public void visit(final IComposition p) {
		Util.addTabs(this.indentation, this.buffer);
		this.buffer.append("// Composition: ");
		this.buffer.append(p.getName());
		this.buffer.append('\n');
	}

	public void visit(final IContainerAggregation p) {
		Util.addTabs(this.indentation, this.buffer);
		this.buffer.append("// Aggregation: ");
		this.buffer.append(p.getName());
		this.buffer.append('\n');

		this.visit((IField) p.getConstituentFromName(p.getOriginFieldName()));
		this.buffer.append('\n');
		this.open((IMethod) p
				.getConstituentFromName(p.getOriginGetterMethodName()));
		this.buffer.append('\n');
		this.open((IMethod) p
				.getConstituentFromName(p.getOriginSetterMethodName()));
		this.buffer.append('\n');
	}

	public void visit(final IContainerComposition p) {
		this.visit((IContainerAggregation) p);
	}

	public void visit(final ICreation p) {
	}

	public void visit(final IField p) {
		this.commentsAndVisibility(p);
		this.buffer.append(' ');
		this.buffer.append(p.getType());
		this.buffer.append(' ');
		this.buffer.append(p.getName());
		final String[] codeLines = p.getCodeLines();
		// Yann: Can be null. The case "empty array" is dealt with in the loop. 
		if (codeLines != null) {
			this.buffer.append(" = ");
			for (int i = 0; i < codeLines.length; i++) {
				this.buffer.append('\n');
				Util.addTabs(this.indentation + 1, this.buffer);
				this.buffer.append(codeLines[i]);
			}
		}
		this.buffer.append(';');
		this.buffer.append('\n');
	}

	public void visit(final IMethodInvocation aMethodInvocation) {
	}

	public void visit(final IParameter p) {
		/*this.buffer.append(p.getTypeName());
		this.buffer.append(' ');
		this.buffer.append(p.getName());*/
	}

	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		// Do nothing for uninteresting primitive types.
	}

	public void visit(final IUseRelationship p) {
	}

	public void traverse(Iterator iterator) {

		this.traverser.traverse(this,
				iterator,
				null);
	}
}
