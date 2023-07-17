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
package ptidej.solver.fingerprint;

import java.util.Iterator;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.impl.Factory;
import pom.metrics.MetricsRepository;
import util.io.ProxyConsole;

/**
 * @author Jean-Yves Guillomarc'h
 * @since  2004/10/14
 */
public class ReducedDomainBuilder {
	private final IAbstractLevelModel abstractLevelModel;
	private final MetricsRepository metrics;

	// Yann 2004/12/03: Dependencies
	// I remove the following to constructors because they
	// depend on the CompleteClassFileCreator (from project
	// PADL ClassFile Creator) which we don't want: This
	// domain builder should work the same way with C++,
	// Java, or... languages.
	//	public ReducedDomainBuilder(
	//		final String path,
	//		final String[] packageNames) {
	//
	//		this.idiomLevelModel =
	//			Factory.getUniqueInstance().createIdiomLevelModel("localModel");
	//		this.idiomLevelModel.create(
	//			new CompleteClassFileCreator(
	//				DefaultFileRepository.getDefaultFileRepository(),
	//				new String[] { path },
	//				true));
	//
	//		this.packageNames = packageNames;
	//		this.metrics = Metrics.getUniqueInstance(idiomLevelModel);
	//		this.listOfMetrics = Metrics.class.getDeclaredMethods();
	//	}
	//	public ReducedDomainBuilder(final String path) {
	//		this(path, null);
	//
	//	}
	public ReducedDomainBuilder(final IAbstractLevelModel anAbstractLevelModel) {
		this.abstractLevelModel = anAbstractLevelModel;
		this.metrics = MetricsRepository.getInstance();
	}

	//	public List computeReducedDomain(final Rule rule) {
	//		final ArrayList restrictedList = new ArrayList();
	//
	//		// Test rules on each entity of the model
	//		final Iterator iterator =
	//			this.idiomLevelModel.listOfConstituents().iterator();
	//		try {
	//			while (iterator.hasNext()) {
	//				final IEntity entity = (IEntity) iterator.next();
	//				if (!rule
	//					.compute(this.metrics, this.listOfMetrics, entity)) {
	//
	//					restrictedList.add(entity);
	//				}
	//			}
	//		}
	//		catch (final Exception e) {
	//			e.printStackTrace();
	//		}
	//		finally {
	//			// Return restricted list
	//			return restrictedList;
	//		}
	//	}
	public IAbstractLevelModel computeReducedDomain(final Rule rule) {
		final ICodeLevelModel newCodeLevelModel =
			Factory.getInstance().createCodeLevelModel("restricted");
		try {
			// Yann 2005/10/07: Packages!
			// A model may now contrain entities and packages.
			// Yann 2005/10/12: Iterator!
			// I have now an iterator able to iterate over a
			// specified type of constituent of a list.
			final Iterator iterator =
				this.abstractLevelModel
					.getIteratorOnConstituents(IFirstClassEntity.class);
			while (iterator.hasNext()) {
				final IFirstClassEntity firstClassEntity =
					(IFirstClassEntity) iterator.next();
				//	if (packageNames != null) {
				//		if (rule
				//			.compute(
				//				this.metrics,
				//				this.listOfMetrics,
				//				(IEntity) entity)) {
				//			boolean accept = false;
				//			int i = 0;
				//			while (!accept && i < this.packageNames.length) {
				//				if (entity
				//					.getID()
				//					.startsWith(this.packageNames[i]))
				//					accept = true;
				//				i++;
				//			}
				//			if (accept) {
				//				newCodeLevelModel.addConstituent(entity);
				//			}
				//		}
				//	}
				//	else {
				if (rule.compute(
					this.metrics,
					this.abstractLevelModel,
					firstClassEntity)) {

					newCodeLevelModel.addConstituent(firstClassEntity);
				}
			}
		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return newCodeLevelModel;
	}
}
