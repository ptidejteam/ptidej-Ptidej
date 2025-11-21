/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Ga�l Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Ga�l Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package padl.model.comparison;

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
import padl.kernel.IPackage;
import padl.kernel.IPackageDefault;
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.visitor.IWalker;
import pom.metrics.IUnaryMetric;
import pom.metrics.MetricsRepository;
import util.io.ProxyConsole;

/**
 * @author Yann-Ga�l Guéhéneuc
 * @since  2004/03/04
 */
public final class POMCalculator implements IWalker {
	private IAbstractModel abstractModel;
	private StringBuffer excelOutput = new StringBuffer();
	private StringBuffer listOfMetricNames = new StringBuffer();
	private MetricsRepository metrics;

	public void close(final IAbstractModel anAbstractModel) {
		this.listOfMetricNames.append('\n');
		this.excelOutput.insert(0, this.listOfMetricNames);
	}
	public void close(final IClass aClass) {
	}
	public void close(final IConstructor aConstructor) {
	}
	public void close(final IDelegatingMethod aDelegatingMethod) {
	}
	public void close(final IGetter aGetter) {
	}
	public void close(final IGhost aGhost) {
	}
	public void close(final IInterface anInterface) {
	}
	public void close(final IMemberClass p) {
	}
	public void close(final IMemberGhost p) {
	}
	public void close(final IMemberInterface p) {
	}
	public void close(final IMethod aMethod) {
	}
	public void close(final IPackage p) {
	}
	public void close(final IPackageDefault p) {
	}
	public void close(final ISetter aSetter) {
	}
	public String getName() {
		return "POM-based metrics";
	}
	public Object getResult() {
		return this.excelOutput.toString();
	}
	public void open(final IAbstractModel anAbstractModel) {
		this.abstractModel = anAbstractModel;
		this.excelOutput.setLength(0);
		this.listOfMetricNames.setLength(0);
		this.metrics = MetricsRepository.getInstance();

		final IUnaryMetric[] metrics = this.metrics.getUnaryMetrics();
		for (int i = 0; i < metrics.length; i++) {
			final IUnaryMetric metric = metrics[i];
			this.listOfMetricNames.append(metric.getName());
			if (i < metrics.length - 1) {
				this.listOfMetricNames.append('\t');
			}
		}
	}
	public void open(final IClass aClass) {
		this.open((IFirstClassEntity) aClass);
	}
	public void open(final IConstructor aConstructor) {
	}
	public void open(final IDelegatingMethod aDelegatingMethod) {
	}
	private void open(final IFirstClassEntity anEntity) {
		this.excelOutput.append(anEntity.getName());
		this.excelOutput.append('\t');

		final IUnaryMetric[] metrics = this.metrics.getUnaryMetrics();
		for (int i = 0; i < metrics.length; i++) {
			final IUnaryMetric metric = metrics[i];

			// TODO Check for type or change Metrics API.
			final double value =
				metric.compute(
					(IAbstractLevelModel) this.abstractModel,
					anEntity);

			this.excelOutput.append(value);

			if (i < metrics.length - 1) {
				this.excelOutput.append('\t');
			}
		}

		this.excelOutput.append('\n');
	}
	public void open(final IGetter aGetter) {
	}
	public void open(final IGhost aGhost) {
	}
	public void open(final IInterface anInterface) {
		this.open((IFirstClassEntity) anInterface);
	}
	public void open(final IMemberClass aMemberClass) {
		this.open((IFirstClassEntity) aMemberClass);
	}
	public void open(final IMemberGhost aMemberGhost) {
		this.open((IFirstClassEntity) aMemberGhost);
	}
	public void open(final IMemberInterface aMemberInterface) {
		this.open((IFirstClassEntity) aMemberInterface);
	}
	public void open(final IMethod aMethod) {
	}
	public void open(final IPackage p) {
	}
	public void open(final IPackageDefault p) {
	}
	public void open(final ISetter aSetter) {
	}
	public void reset() {
		this.excelOutput.setLength(0);
		this.listOfMetricNames.setLength(0);
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
}
