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
package pom.metrics.repository;

import java.util.Iterator;

import com.ibm.toad.cfparse.utils.Access;

import padl.kernel.IAbstractModel;
import padl.kernel.IClass;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;
import util.io.ProxyConsole;

/**
 * 
 * @author Yann
 * @date 2012/03/27
 * 
 * See Foutse's TSE paper.
 * (This metric is a copy of LOC.java.) 
 *
 */
public class TLOC extends AbstractMetric implements IMetric, IUnaryMetric {
	protected double concretelyCompute(final IAbstractModel anAbstractModel,
			final IFirstClassEntity anEntity) {

		int loc = 0;

		if (anEntity instanceof IClass) {
			final IClass clazz = (IClass) anEntity;

			final Iterator iteratorOnMethods = clazz
					.getIteratorOnConstituents(IMethod.class);
			while (iteratorOnMethods.hasNext()) {
				final IMethod method = (IMethod) iteratorOnMethods.next();
				if (!method.isAbstract()
						&& !Access.isNative(method.getVisibility())) {

					final String[] codeLines = method.getCodeLines();
					if (codeLines.length != 0) {
						loc += method.getCodeLines().length;
					}
					else {
						ProxyConsole.getInstance().debugOutput()
								.print(this.getClass().getName());
						ProxyConsole.getInstance().debugOutput()
								.print(" reports that ");
						ProxyConsole.getInstance().debugOutput()
								.print(clazz.getName());
						ProxyConsole.getInstance().debugOutput().print('.');
						ProxyConsole.getInstance().debugOutput()
								.print(method.getName());
						ProxyConsole.getInstance().debugOutput()
								.println(" has no code lines!");
					}
				}
			}
		}

		return loc;
	}

	public String getDefinition() {
		return "Return the numbers of lines of code of all the methods of a class.";
	}

}
