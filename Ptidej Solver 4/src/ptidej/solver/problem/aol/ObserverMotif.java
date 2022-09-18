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
package ptidej.solver.problem.aol;

import java.util.List;

import ptidej.solver.Problem;
import ptidej.solver.Variable;
import ptidej.solver.approximation.DefaultAssociationApproximations;
import ptidej.solver.approximation.DefaultNoApproximations;
import ptidej.solver.approximation.TSE07CollapsableInheritanceApproximations;
import ptidej.solver.approximation.TSE07ExtendedAssociationApproximations;
import ptidej.solver.constraint.repository.AssociationConstraint;
import ptidej.solver.constraint.repository.NoAbstractEntityConstraint;
import ptidej.solver.constraint.repository.NoGhostEntityConstraint;
import ptidej.solver.constraint.repository.NotEqualConstraint;
import ptidej.solver.constraint.repository.StrictInheritanceConstraint;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/02/18 
 */
public final class ObserverMotif {
	public static Problem getProblem(final List allEntities) {
		final Problem pb =
			new Problem(90, "Observer Design Motif", allEntities);

		final Variable subject = new Variable(pb, "subject", true);
		final Variable observer = new Variable(pb, "observer", true);
		final Variable concreteSubject =
			new Variable(pb, "concreteSubject", false);
		final Variable concreteObserver =
			new Variable(pb, "concreteObserver", false);

		pb.addVar(subject);
		pb.addVar(observer);
		pb.addVar(concreteSubject);
		pb.addVar(concreteObserver);

		// Constraints
		pb.post(
			new StrictInheritanceConstraint(
				"ConcreteSubject -|>- Subject",
				"",
				concreteSubject,
				subject,
				50,
				TSE07CollapsableInheritanceApproximations
					.getDefaultApproximations()));
		pb.post(
			new StrictInheritanceConstraint(
				"ConcreteObserver -|>- Observer",
				"",
				concreteObserver,
				observer,
				100,
				TSE07CollapsableInheritanceApproximations
					.getDefaultApproximations()));
		pb.post(
			new NoGhostEntityConstraint(
				"Observer <> ?",
				"",
				observer,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NoAbstractEntityConstraint(
				"ConcreteObserver is not abstract",
				"",
				concreteObserver,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NotEqualConstraint(
				"Subject <> Observer",
				"",
				subject,
				observer,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NotEqualConstraint(
				"Subject <> ConcreteSubject",
				"",
				subject,
				concreteSubject,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NotEqualConstraint(
				"Observer <> ConcreteObserver",
				"",
				observer,
				concreteObserver,
				100,
				DefaultNoApproximations.getDefaultApproximations()));

		pb.post(
			new AssociationConstraint(
				"Subject ---­> Observer",
				"",
				subject,
				observer,
				100,
				TSE07ExtendedAssociationApproximations
					.getDefaultApproximations()));

		pb.post(
			new AssociationConstraint(
				"ConcreteObserver ---­> ConcreteSubject",
				"",
				concreteObserver,
				concreteSubject,
				100,
				DefaultAssociationApproximations.getDefaultApproximations()));

		//	pb.post(
		//		new IgnoranceConstraint(
		//			"Subject -/-­> ConcreteObserver",
		//			"",
		//			subject,
		//			concreteObserver,
		//			100,
		//			DefaultNoApproximations.getDefaultApproximations()));
		//	pb.post(
		//		new IgnoranceConstraint(
		//			"ConcreteSubject -/-­> Observer",
		//			"",
		//			concreteSubject,
		//			observer,
		//			100,
		//			DefaultNoApproximations.getDefaultApproximations()));
		//	pb.post(
		//		new IgnoranceConstraint(
		//			"ConcreteSubject -/-­> ConcreteObserver",
		//			"",
		//			concreteSubject,
		//			concreteObserver,
		//			100,
		//			DefaultNoApproximations.getDefaultApproximations()));
		//	pb.post(
		//		new IgnoranceConstraint(
		//			"Observer -/--> Subject",
		//			"",
		//			observer,
		//			subject,
		//			100,
		//			DefaultNoApproximations.getDefaultApproximations()));
		//	pb.post(
		//		new IgnoranceConstraint(
		//			"Observer -/--> ConcreteSubject",
		//			"",
		//			observer,
		//			concreteSubject,
		//			100,
		//			DefaultNoApproximations.getDefaultApproximations()));
		//	pb.post(
		//		new IgnoranceConstraint(
		//			"ConcreteObserver -/--> Subject",
		//			"",
		//			concreteObserver,
		//			subject,
		//			100,
		//			DefaultNoApproximations.getDefaultApproximations()));

		return pb;
	}
}
