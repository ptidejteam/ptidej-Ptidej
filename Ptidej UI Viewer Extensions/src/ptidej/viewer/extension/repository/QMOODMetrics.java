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
package ptidej.viewer.extension.repository;

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
 * @author Foutse Khomh
 * @since  2007/03/01
 */
public class QMOODMetrics implements IWalker {
	private IAbstractModel abstractModel;
	private StringBuffer excelOutput = new StringBuffer();
	private StringBuffer listOfMetricNames = new StringBuffer();
	private MetricsRepository metrics;
	private StringBuffer results = new StringBuffer();

	public void close(final IAbstractModel anAbstractModel) {
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
	public String getName() {
		return null;
	}
	public Object getResult() {
		return this.results.toString();
	}
	public void open(final IAbstractModel anAbstractModel) {
		this.results.setLength(0);
		this.excelOutput.setLength(0);
		this.metrics = MetricsRepository.getInstance();
		this.abstractModel = anAbstractModel;
	}
	public void open(final IClass aClass) {
		this.open((IFirstClassEntity) aClass);
	}
	public void open(final IConstructor aConstructor) {
	}
	public void open(final IDelegatingMethod aDelegatingMethod) {
	}
	private void open(final IFirstClassEntity anEntity) {
		this.results.append(anEntity.getName());
		this.results.append('\n');

		final String[] metricNames =
			{ "DSC", "NOH", "ANA", "DAM", "DCC", "CAM", "MOA", "MFA", "NOPM",
					"CIS", "NOM" };

		for (int i = 0; i < metricNames.length; i++) {
			final double value =
				((IUnaryMetric) this.metrics.getMetric(metricNames[i]))
					.compute((IAbstractLevelModel) this.abstractModel, anEntity);
			this.listOfMetricNames.append(metricNames[i]);
			this.excelOutput.append(value);

			this.results.append("\t");
			this.results.append(metricNames[i]);
			this.results.append(":\t");
			this.results.append(value);
			this.results.append('\n');
			if (i < metricNames.length - 1) {
				this.listOfMetricNames.append('\t');
				this.excelOutput.append('\t');
			}
		}

		final double reus =
			(-0.25)
					* ((IUnaryMetric) this.metrics.getMetric("DCC")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity)
					+ (0.25)
					* ((IUnaryMetric) this.metrics.getMetric("CAM")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity)
					+ (0.5)
					* ((IUnaryMetric) this.metrics.getMetric("CIS")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity)
					+ (0.5)
					* ((IUnaryMetric) this.metrics.getMetric("DSC")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity);

		final double flex =
			(0.25)
					* ((IUnaryMetric) this.metrics.getMetric("DAM")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity)
					+ (-0.25)
					* ((IUnaryMetric) this.metrics.getMetric("DCC")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity)
					+ (0.5)
					* ((IUnaryMetric) this.metrics.getMetric("MOA")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity)
					+ (0.5)
					* ((IUnaryMetric) this.metrics.getMetric("NOPM")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity);

		final double under =
			(-0.33)
					* ((IUnaryMetric) this.metrics.getMetric("ANA")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity)
					+ (0.33)
					* ((IUnaryMetric) this.metrics.getMetric("DAM")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity)
					+ (-0.33)
					* ((IUnaryMetric) this.metrics.getMetric("DCC")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity)
					+ (0.33)
					* ((IUnaryMetric) this.metrics.getMetric("CAM")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity)
					+ (-0.33)
					* ((IUnaryMetric) this.metrics.getMetric("NOPM")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity)
					+ (-0.33)
					* ((IUnaryMetric) this.metrics.getMetric("NOM")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity)
					+ (-0.33)
					* ((IUnaryMetric) this.metrics.getMetric("DSC")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity);

		final double funct =
			(0.12)
					* ((IUnaryMetric) this.metrics.getMetric("CAM")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity)
					+ (0.22)
					* ((IUnaryMetric) this.metrics.getMetric("NOPM")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity)
					+ (0.22)
					* ((IUnaryMetric) this.metrics.getMetric("CIS")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity)
					+ (0.22)
					* ((IUnaryMetric) this.metrics.getMetric("DSC")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity)
					+ (0.22)
					* ((IUnaryMetric) this.metrics.getMetric("NOH")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity);

		final double ext =
			(0.5)
					* ((IUnaryMetric) this.metrics.getMetric("ANA")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity)
					+ (-0.5)
					* ((IUnaryMetric) this.metrics.getMetric("DCC")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity)
					+ (0.5)
					* ((IUnaryMetric) this.metrics.getMetric("MFA")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity)
					+ (0.5)
					* ((IUnaryMetric) this.metrics.getMetric("NOPM")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity);

		final double effect =
			(0.2)
					* ((IUnaryMetric) this.metrics.getMetric("ANA")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity)
					+ (0.2)
					* ((IUnaryMetric) this.metrics.getMetric("DAM")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity)
					+ (0.2)
					* ((IUnaryMetric) this.metrics.getMetric("MOA")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity)
					+ (0.2)
					* ((IUnaryMetric) this.metrics.getMetric("MFA")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity)
					+ (0.2)
					* ((IUnaryMetric) this.metrics.getMetric("NOPM")).compute(
						(IAbstractLevelModel) this.abstractModel,
						anEntity);

		this.results.append("\t");
		this.results.append("Reusability");
		this.results.append(":\t");
		this.results.append(reus);
		this.results.append("\t");
		this.results.append("Flexibility");
		this.results.append(":\t");
		this.results.append(flex);
		this.results.append("\t");
		this.results.append("Understandability");
		this.results.append(":\t");
		this.results.append(under);
		this.results.append("\t");
		this.results.append("Functionality");
		this.results.append(":\t");
		this.results.append(funct);
		this.results.append("\t");
		this.results.append("Extendibility");
		this.results.append(":\t");
		this.results.append(ext);
		this.results.append("\t");
		this.results.append("Effectiveness");
		this.results.append(":\t");
		this.results.append(effect);
		this.results.append('\n');
		this.results.append('\n');
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
	public void open(final IPackage aPackage) {
	}
	public void open(final IPackageDefault aPackage) {
	}
	public void open(final ISetter aSetter) {
	}
	public void reset() {
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
