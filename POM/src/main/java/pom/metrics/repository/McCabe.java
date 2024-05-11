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

import padl.kernel.IAbstractModel;
import padl.kernel.IConstituent;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IPrimitiveEntity;
import padl.statement.kernel.IIfInstruction;
import padl.statement.kernel.IStatementWalker;
import padl.statement.kernel.ISwitchInstruction;
import padl.statement.kernel.impl.StatementWalkerAdapter;
import pom.metrics.IDependencyIndependentMetric;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;
import util.io.ProxyConsole;

public class McCabe extends AbstractMetric implements IMetric, IUnaryMetric, IDependencyIndependentMetric {
	// Must be public because of reflection use to traverse the entity below...
	public final class McCabeComputer extends StatementWalkerAdapter {
		private int mcCabe;

		public String getName() {
			return "McCabe Computer";
		}
		public Object getResult() {
			return Integer.valueOf(this.mcCabe);
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

		public void visit(final IIfInstruction anIfInstruction) {
			this.mcCabe++;
		}

		public void visit(final IPrimitiveEntity aPrimitiveEntity) {
			// Do nothing for uninteresting primitive types.
		}
		public void visit(final ISwitchInstruction switchInstruction) {
			this.mcCabe += switchInstruction.getNumberOfCases();
		}
	}
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		final IStatementWalker walker = new McCabeComputer();
		firstClassEntity.accept(walker);
		return ((Integer) walker.getResult()).doubleValue();
	}
	public String getDefinition() {
		return "McCabe Complexity: Number of points of decision + 1";
	}
}
