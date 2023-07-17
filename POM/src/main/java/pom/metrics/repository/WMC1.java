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
/**
 * WMC - Weight of Methods Computed with method's complexity considered
 * to be unity [Chidamber and Kememerer, 1994]
 * 
 * @author Jean-Yves Guyomarc'h
 * @since  2006/03/10
 * 
 */

package pom.metrics.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IOperation;
import pom.metrics.IDependencyIndependentMetric;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

public class WMC1 extends AbstractMetric implements IMetric, IUnaryMetric, IDependencyIndependentMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		return this.listOfElements(firstClassEntity).size();
	}
	public String getDefinition() {
		final String def =
			"Weight of an entity considering the complexity of each of its method as being 1. (Default constructors are considered even if not explicitely declared).";
		return def;
	}
	private List listOfElements(IFirstClassEntity firstClassEntity) {
		final List list = new ArrayList();
		final Iterator iterAbstractMethod =
			firstClassEntity.getIteratorOnConstituents(IOperation.class);

		while (iterAbstractMethod.hasNext()) {
			list.add(iterAbstractMethod.next());
		}
		return list;
	}
}
