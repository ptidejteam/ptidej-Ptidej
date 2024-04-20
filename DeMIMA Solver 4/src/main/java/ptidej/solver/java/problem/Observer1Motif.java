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
package ptidej.solver.java.problem;

import java.util.List;

import ptidej.solver.java.Problem;
import ptidej.solver.java.Variable;
import ptidej.solver.java.approximation.DefaultIgnoranceApproximations;
import ptidej.solver.java.approximation.DefaultNoApproximations;
import ptidej.solver.java.approximation.TSE07AssociationApproximations;
import ptidej.solver.java.approximation.TSE07AssociationOrNoneApproximations;
import ptidej.solver.java.approximation.TSE07ExtensibleStrictInheritanceApproximations;
import ptidej.solver.java.constraint.repository.AssociationConstraint;
import ptidej.solver.java.constraint.repository.IgnoranceConstraint;
import ptidej.solver.java.constraint.repository.InterfaceEntityConstraint;
import ptidej.solver.java.constraint.repository.NoAbstractEntityConstraint;
import ptidej.solver.java.constraint.repository.NoGhostEntityConstraint;
import ptidej.solver.java.constraint.repository.NotEqualConstraint;
import ptidej.solver.java.constraint.repository.StrictInheritanceConstraint;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/02/18 
 */
public final class Observer1Motif {
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
		pb.post(new NoGhostEntityConstraint(
			"Subject <> ?",
			"",
			subject,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		pb.post(new InterfaceEntityConstraint(
			"Subject is an interface",
			"",
			subject,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		pb.post(new NoGhostEntityConstraint(
			"Observer <> ?",
			"",
			observer,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		//	pb.post(
		//		new NoObjectEntityConstraint(
		//			"Observer <> java.lang.Object",
		//			"",
		//			observer,
		//			100,
		//			DefaultNoApproximations.getDefaultApproximations()));
		pb.post(new InterfaceEntityConstraint(
			"Observer is an interface",
			"",
			observer,
			100,
			DefaultNoApproximations.getDefaultApproximations()));

		pb.post(new NotEqualConstraint(
			"Observer <> ConcreteObserver",
			"",
			observer,
			concreteObserver,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		pb.post(new NotEqualConstraint(
			"Subject <> Observer",
			"",
			subject,
			observer,
			100,
			DefaultNoApproximations.getDefaultApproximations()));

		pb.post(new AssociationConstraint(
			"Subject ---­> Observer",
			"",
			subject,
			observer,
			50,
			TSE07AssociationApproximations.getDefaultApproximations()));
		pb.post(new StrictInheritanceConstraint(
			"ConcreteSubject -|>- Subject",
			"",
			concreteSubject,
			subject,
			50,
			TSE07ExtensibleStrictInheritanceApproximations
				.getDefaultApproximations()));
		pb.post(new NoAbstractEntityConstraint(
			"ConcreteSubject is not abstract",
			"",
			concreteSubject,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		pb.post(new StrictInheritanceConstraint(
			"ConcreteObserver -|>- Observer",
			"",
			concreteObserver,
			observer,
			50,
			TSE07ExtensibleStrictInheritanceApproximations
				.getDefaultApproximations()));
		pb.post(new NoAbstractEntityConstraint(
			"ConcreteObserver is not abstract",
			"",
			concreteObserver,
			100,
			DefaultNoApproximations.getDefaultApproximations()));

		pb.post(new NotEqualConstraint(
			"ConcreteSubject <> ConcreteObserver",
			"",
			concreteSubject,
			concreteObserver,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		pb.post(new AssociationConstraint(
			"ConcreteObserver ---­> Subject",
			"",
			concreteObserver,
			subject,
			100,
			TSE07AssociationOrNoneApproximations.getDefaultApproximations()));

		pb.post(new IgnoranceConstraint(
			"Observer -/-­> Subject",
			"",
			observer,
			subject,
			100,
			DefaultIgnoranceApproximations.getDefaultApproximations()));
		pb.post(new IgnoranceConstraint(
			"Observer -/-­> ConcreteSubject",
			"",
			observer,
			concreteSubject,
			100,
			DefaultIgnoranceApproximations.getDefaultApproximations()));
		pb.post(new IgnoranceConstraint(
			"ConcreteSubject -/-­> ConcreteObserver",
			"",
			concreteSubject,
			concreteObserver,
			100,
			DefaultIgnoranceApproximations.getDefaultApproximations()));

		return pb;
	}
}
