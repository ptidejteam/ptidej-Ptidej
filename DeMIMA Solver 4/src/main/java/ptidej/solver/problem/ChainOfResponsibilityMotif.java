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
package ptidej.solver.problem;

import java.util.List;

import ptidej.solver.Problem;
import ptidej.solver.Variable;
import ptidej.solver.approximation.DefaultAssociationApproximations;
import ptidej.solver.approximation.DefaultNoApproximations;
import ptidej.solver.approximation.TSE07AbstractnessApproximations;
import ptidej.solver.approximation.TSE07ExtensibleInheritanceOrNoneApproximations;
import ptidej.solver.constraint.repository.AbstractEntityConstraint;
import ptidej.solver.constraint.repository.AssociationConstraint;
import ptidej.solver.constraint.repository.NoGhostEntityConstraint;
import ptidej.solver.constraint.repository.StrictInheritanceConstraint;

/**
 * @author Lucas Nelaupe, Ferrand Anthony, Tran Quang Dung, Verdier Frédéric 
 * @since  2014/06/01 
 */
public final class ChainOfResponsibilityMotif {
	public static Problem getProblem(final List allEntities) {
		final Problem pb = new Problem(90,"Chain of Responsibility Design Motif",allEntities);
		final Variable abstractElement = new Variable(pb, "AbstractElement", true);
		final Variable concreteElement = new Variable(pb, "ConcreteElement", false);
		
		pb.addVar(abstractElement);
		pb.addVar(concreteElement);
		
		pb.post(
				new NoGhostEntityConstraint(
					"AbstractElement <> ?",
					"",
					abstractElement,
					100,
					DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
				new NoGhostEntityConstraint(
					"ConcreteElement <> ?",
					"",
					concreteElement,
					100,
					DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
				new AbstractEntityConstraint(
						"AbstractElement <<abstract>>", 
						"", 
						abstractElement, 
						100, 
						TSE07AbstractnessApproximations.getDefaultApproximations()));
		pb.post(
				new StrictInheritanceConstraint(
					"ConcreteElement -|>- AbstractElement",
					"",
					concreteElement,
					abstractElement,
					100,
					TSE07ExtensibleInheritanceOrNoneApproximations
						.getDefaultApproximations()));
		pb.post(
				new AssociationConstraint(
						"AbstractElement ---> AbstractElement", 
						"", 
						abstractElement, 
						abstractElement, 
						100, 
						DefaultAssociationApproximations.getDefaultApproximations()));
		return pb;
	}
}
