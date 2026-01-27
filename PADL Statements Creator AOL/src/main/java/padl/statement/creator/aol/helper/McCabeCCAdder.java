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
package padl.statement.creator.aol.helper;

import java.util.Map;
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
import padl.kernel.IPackage;
import padl.kernel.IPackageDefault;
import padl.kernel.IPackageGhost;
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.statement.creator.aol.IMetricValueAdder;
import padl.statement.kernel.impl.StatementFactory;
import util.io.ProxyConsole;

public class McCabeCCAdder implements IMetricValueAdder {
	private static final char[] DUMMY = "Dummy".toCharArray();

	// Yann 2008/10/13: Members...
	// No need to take care of members classes/interfaces
	// using a Stack because we are in AOL...
	private IFirstClassEntity currentEntity;
	private Map qualifiedMethodsMetrics;

	public void close(final IAbstractModel anAbstractModel) {
	}
	public void close(final IClass aClass) {
	}
	public void close(final IConstructor constructor) {
	}
	public void close(final IDelegatingMethod delegatingMethod) {
	}
	public void close(final IGetter getter) {
	}
	public void close(final IGhost ghost) {
	}
	public void close(final IInterface anInterface) {
	}
	public void close(final IMemberClass memberClass) {
	}
	public void close(final IMemberGhost memberGhost) {
	}
	public void close(final IMemberInterface memberInterface) {
	}
	public void close(final IMethod method) {
	}
	public void close(final IPackage aPackage) {
	}
	public void close(final IPackageDefault aPackage) {
	}
	public void close(final IPackageGhost aPackageGhost) {
	}
	public void close(final ISetter setter) {
	}
	public String getName() {
		return "If/Switch Instructions Creator for AOL";
	}
	public Object getResult() {
		return null;
	}
	public void open(final IAbstractModel anAbstractModel) {
	}
	public void open(final IClass aClass) {
		this.currentEntity = aClass;
	}
	public void open(final IConstructor constructor) {
	}
	public void open(final IDelegatingMethod delegatingMethod) {
	}
	public void open(final IGetter getter) {
	}
	public void open(final IGhost ghost) {
	}
	public void open(final IInterface anInterface) {
	}
	public void open(final IMemberClass memberClass) {
	}
	public void open(final IMemberGhost memberGhost) {
	}
	public void open(final IMemberInterface memberInterface) {
	}
	public void open(final IMethod method) {
		final String qualifiedName =
			this.currentEntity.getDisplayName() + '.' + method.getDisplayName();
		final String metricValue =
			(String) this.qualifiedMethodsMetrics.get(qualifiedName);

		if (metricValue != null) {
			final int value = Integer.parseInt(metricValue) - 1;
			for (int i = 0; i < value; i++) {
				method.addConstituent(((StatementFactory) StatementFactory
					.getInstance()).createIfInstruction(McCabeCCAdder.DUMMY));
			}
		}
		else {
			ProxyConsole
				.getInstance()
				.errorOutput()
				.print("Cannot find McCabe CC value for ");
			ProxyConsole.getInstance().errorOutput().println(qualifiedName);
		}
	}
	public void open(final IPackage aPackage) {
	}
	public void open(final IPackageDefault aPackage) {
	}
	public void open(final IPackageGhost aPackageGhost) {
	}
	public void open(final ISetter setter) {
	}
	public void reset() {
	}
	public void setQualifiedMethodsMetrics(final Map someQualifiedMethodsMetrics) {
		this.qualifiedMethodsMetrics = someQualifiedMethodsMetrics;
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
	public void visit(final IComposition composition) {
	}
	public void visit(final IContainerAggregation containerAggregation) {
	}
	public void visit(final IContainerComposition containerComposition) {
	}
	public void visit(final ICreation creation) {
	}
	public void visit(final IField field) {
	}
	public void visit(final IMethodInvocation methodInvocation) {
	}
	public void visit(final IParameter parameter) {
	}
	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		// Do nothing for uninteresting primitive types.
	}
	public void visit(final IUseRelationship use) {
	}
}
