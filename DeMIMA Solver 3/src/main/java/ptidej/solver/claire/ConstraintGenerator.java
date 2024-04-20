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
package ptidej.solver.claire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import padl.kernel.Constants;
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
import padl.kernel.IElement;
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
import padl.kernel.IRelationship;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.motif.IDesignMotifModel;
import padl.util.Util;
import util.io.ProxyConsole;

// Yann 2004/08/04: Reflection!
// This class must be public because its visitor-related
// methods are accessed using reflection (subject to
// access control).
abstract class ConstraintGenerator {
	private List aggregationTargets = new ArrayList();
	private StringBuffer buffer = new StringBuffer();
	private IAbstractModel currentModel;
	private IFirstClassEntity enclosingEntity;
	private int numberOfEntities;
	private IFirstClassEntity[] pEntities;
	private Map useRelationships = new HashMap();

	private String buildRuntimeExceptionXCommand(
		final String logo,
		final IFirstClassEntity targetEntity) {

		final StringBuffer buffer = new StringBuffer();
		buffer.append("throw new RuntimeException(\\\"");
		buffer.append(this.enclosingEntity.getID());
		buffer.append(" should ");
		buffer.append(logo);
		buffer.append(' ');
		buffer.append(targetEntity.getID());
		buffer.append("\\\");");

		return buffer.toString();
	}
	public final void close(final IAbstractModel p) {
		this.inheritances();
		this.ignorances();
		this.inequalities();

		/*
		this.buffer.append(
			"            pb.assignVarSelection := USERDEFINED,\n"); 
		this.buffer.append("            pb.varsToShow := list(");
		for (int i = 0; i < this.numberOfEntities; i++) {
			this.buffer.append(
				Misc.minimizeFirstLetter(this.pEntities[i].getID())); 
			if (i < this.numberOfEntities - 1) {
				this.buffer.append(", ");
			}
		}
		this.buffer.append("),\n");
		*/

		/*
		Vector editableProperties = Misc.getPatternProperties(p.getClass());
		this.buffer.append("            pb.vars := list(");
		for (int i = 0; i < this.numberOfEntities; i++) {
		    boolean toBeEnumerated = true;
		    for (int j = 0; j < editableProperties.size(); j++) {
		        if (this
		            .pEntities[i]
		            .getID()
		            .equals(editableProperties.elementAt(j))) {
		            toBeEnumerated = false;
		        }
		    }
		    if (toBeEnumerated) {
		        if (this.buffer.charAt(this.buffer.length() - 1) != '(') {
		            this.buffer.append(", ");
		        }
		        this.buffer.append(
		            Misc.minimizeFirstLetter(this.pEntities[i].getID())); 
		    }
		}
		this.buffer.append("),\n");
		this.buffer.append("            pb.vars := list(");
		for (int i = 0; i < this.numberOfEntities; i++) {
			this.buffer.append(
				Misc.minimizeFirstLetter(this.pEntities[i].getID())); 
			if (i < this.numberOfEntities - 1) {
				this.buffer.append(", ");
			}
		}
		this.buffer.append("),\n");
		*/

		this.buffer.append("            pb\n");
		this.buffer.append("        )\n");
		this.buffer.append("]\n");
	}
	public final void close(final IClass p) {
		this.close((IFirstClassEntity) p);
	}
	public void close(final IConstructor aConstructor) {
	}
	public void close(final IDelegatingMethod aDelegatingMethod) {
	}
	public final void close(final IDesignMotifModel p) {
		this.close((IAbstractModel) p);
	}
	private void close(final IFirstClassEntity p) {
		this.enclosingEntity = null;
	}
	public void close(final IGetter aGetter) {
	}
	public final void close(final IGhost p) {
		this.close((IFirstClassEntity) p);
	}
	public final void close(final IInterface p) {
		this.close((IFirstClassEntity) p);
	}
	public void close(final IMemberClass p) {
		this.close((IFirstClassEntity) p);
	}
	public void close(final IMemberGhost p) {
		this.close((IFirstClassEntity) p);
	}
	public void close(final IMemberInterface p) {
		this.close((IFirstClassEntity) p);
	}
	public void close(final IMethod aMethod) {
	}
	public void close(final IPackage p) {
	}
	public void close(final IPackageDefault aPackage) {
	}
	public void close(final ISetter aSetter) {
	}
	private void createConstraint(
		final IElement relation,
		final IFirstClassEntity target,
		final String constraintName,
		final String relationshipLogo,
		final String xCommand,
		final int weight) {

		// Yann 2001/07/10: Misunderstanding!
		// I think I mess up with my own constraints.
		// A composition constraint (or association,
		// or aggregation, or ...) is only between
		// two entities. The PaLM-based corresponding
		// constraint takes care of the subclasses.
		/*
		Enumeration enumeration = 
		    PaLMGenerator
		        .directSubclassesOf(p.getTargetEntity(), this.pEntities)
		        .elements(); 
		
		while (enumeration.hasMoreElements()) {
		    Entity pEntity = (Entity) enumeration.nextElement();
		*/
		// Yann 2002/09/24: Repeat...
		// I must not add a constraint if it already exits...
		final StringBuffer constraint = new StringBuffer();
		constraint.append("            post(pb,\n");
		constraint.append("                 make");
		constraint.append(constraintName);
		constraint.append(this.getSuffix());
		constraint.append("Constraint(\n");
		constraint.append("                    \"");
		constraint.append(this.enclosingEntity.getID());
		constraint.append(' ');
		constraint.append(relationshipLogo);
		constraint.append(' ');
		constraint.append(target.getID());
		constraint.append("\",\n");
		constraint.append("                    \"");
		constraint.append(xCommand);
		constraint.append("\",\n");
		constraint.append("                    ");
		constraint
			.append(Util.minimizeFirstLetter(this.enclosingEntity.getID()));
		constraint.append("Var,\n");
		constraint.append("                    ");
		constraint.append(Util.minimizeFirstLetter(target.getID()));
		constraint.append("Var),\n");
		constraint.append("                 ");
		constraint.append(weight);
		constraint.append("),\n");

		final String constraintString = constraint.toString();
		if (this.buffer.toString().indexOf(constraintString) == -1) {
			this.buffer.append(constraintString);
		}

		/*
		    // Yann 2001/06/27: This is an UGLY hack!
		    // I must differentiate between Use and
		    // Composition constraints: a Use constraint
		    // is exclusively between two entities; a
		    // Composition is between one entity, one target
		    // entity, and the target entity's subclasses.
		    // I can perform this test because I know that in
		    // PaLMGenerator.directSubclassesOf(), the first
		    // entity is the real target, the following, its
		    // direct subclasses.
		    if (constraintName.equals("Use")) {
		        break;
		    }
		}
		*/

		// Yann 2001/07/11: Improvements!
		// I need to post an IgnoranceConstraint whenever
		// a Entity does not know another one. So, I need
		// to keep track of which Entity knows which other
		// Entity, to add the IgnoranceConstraints at the
		// end of the visiting.
		if (this.useRelationships.containsKey(this.enclosingEntity.getID())) {

			((List) this.useRelationships.get(this.enclosingEntity.getID()))
				.add(target.getID());
		}
		else {
			final List list = new ArrayList();
			list.add(target.getID());
			this.useRelationships.put(this.enclosingEntity.getID(), list);
		}
	}
	private void elementWithTarget(
		final IRelationship relation,
		final String constraintName,
		final String relationshipLogo,
		final String xCommand,
		final int weight) {

		this.createConstraint(
			(IElement) relation,
			(IFirstClassEntity) relation.getTargetEntity(),
			constraintName,
			relationshipLogo,
			xCommand,
			weight);
	}
	public final String getCode() {
		return this.buffer.toString();
	}
	protected abstract String getPrefix();
	protected abstract String getSuffix();
	private void ignorances() {
		final List unknownEntities = new ArrayList();
		for (int i = 0; i < this.numberOfEntities; i++) {
			unknownEntities.clear();
			final char[] keyEntity = this.pEntities[i].getID();

			if (this.useRelationships.containsKey(keyEntity)) {
				final List knownEntities =
					(List) this.useRelationships.get(keyEntity);
				for (int j = 0; j < this.numberOfEntities; j++) {
					final char[] pEntity = this.pEntities[j].getID();
					if (!knownEntities.contains(pEntity)
							&& !keyEntity.equals(pEntity)) {
						unknownEntities.add(pEntity);
					}
				}
			}
			else {
				for (int j = 0; j < this.numberOfEntities; j++) {
					final char[] pEntity = this.pEntities[j].getID();
					if (!keyEntity.equals(pEntity)) {
						unknownEntities.add(this.pEntities[j].getID());
					}
				}
			}

			for (int j = 0; j < unknownEntities.size(); j++) {
				this.buffer.append("            post(pb,\n");
				this.buffer.append("                 makeIgnorance");
				this.buffer.append(this.getSuffix());
				this.buffer.append("Constraint(\n");
				this.buffer.append("                    \"");
				this.buffer.append(keyEntity);
				this.buffer.append(" -/--> ");
				this.buffer.append(unknownEntities.get(j));
				this.buffer.append("\",\n");
				this.buffer
					.append("                    \"throw new RuntimeException(\\\"");
				this.buffer.append(keyEntity);
				this.buffer.append(" -/--> ");
				this.buffer.append(unknownEntities.get(j));
				this.buffer.append("\\\");\",\n");
				this.buffer.append("                    ");
				this.buffer.append(Util.minimizeFirstLetter(keyEntity));
				this.buffer.append("Var,\n");
				this.buffer.append("                    ");
				this.buffer.append(Util
					.minimizeFirstLetter((char[]) unknownEntities.get(j)));
				this.buffer.append("Var),\n");
				this.buffer.append("                 ");
				this.buffer.append(Constants.STRONGLY_REQUIRED);
				this.buffer.append("),\n");
			}
		}
	}
	private void inequalities() {
		for (int i = 0; i < this.numberOfEntities; i++) {
			for (int j = i; j < this.numberOfEntities; j++) {
				if (i != j) {
					this.buffer.append("            post(pb,\n");
					this.buffer.append("                 makeNotEqual");
					this.buffer.append("Constraint(\n");
					this.buffer.append("                    \"");
					this.buffer.append(this.pEntities[i].getID());
					this.buffer.append(" <> ");
					this.buffer.append(this.pEntities[j].getID());
					this.buffer.append("\",\n");
					this.buffer
						.append("                    \"throw new RuntimeException(\\\"");
					this.buffer.append(this.pEntities[i].getID());
					this.buffer.append(" <> ");
					this.buffer.append(this.pEntities[j].getID());
					this.buffer.append("\\\");\",\n");
					this.buffer.append("                    ");
					this.buffer.append(Util
						.minimizeFirstLetter(this.pEntities[i].getID()));
					this.buffer.append("Var,\n");
					this.buffer.append("                    ");
					this.buffer.append(Util
						.minimizeFirstLetter(this.pEntities[j].getID()));
					this.buffer.append("Var),\n");
					this.buffer.append("                 ");
					this.buffer.append(Constants.MANDATORY);
					this.buffer.append("),\n");
				}
			}
		}
	}
	private void inheritances() {
		for (int i = 0; i < this.numberOfEntities; i++) {
			final Iterator iterator;

			if (this.pEntities[i] instanceof IClass) {
				final List superEntities = new ArrayList();
				Iterator iteratorTemp =
					this.pEntities[i].getIteratorOnInheritedEntities();
				while (iteratorTemp.hasNext()) {
					superEntities.add(iteratorTemp.next());
				}
				iteratorTemp =
					((IClass) this.pEntities[i])
						.getIteratorOnImplementedInterfaces();
				while (iteratorTemp.hasNext()) {
					superEntities.add(iteratorTemp.next());
				}
				iterator = superEntities.iterator();
			}
			else if (this.pEntities[i] instanceof IInterface) {
				iterator = this.pEntities[i].getIteratorOnInheritedEntities();
			}
			else {
				iterator = DomainGenerator.EMPTY_ITERATOR;
			}

			while (iterator.hasNext()) {
				final char[] superEntity =
					((IFirstClassEntity) iterator.next()).getID();

				// Yann 2001/07/11: Improvements!
				// Here, I want to make the distinction between regular
				// StrictInheritance and InheritancePath constraints.
				// The idea is that if one superclass / superinterface
				// of the Entity is the target of an aggregation or of
				// a composition, then the inheritance must be represented
				// by an InheritancePath constraint.
				if (this.aggregationTargets.contains(superEntity)) {
					this.buffer.append("            post(pb,\n");
					this.buffer.append("                 makeInheritancePath");
					this.buffer.append(this.getSuffix());
					this.buffer.append("Constraint(\n");
					this.buffer.append("                    \"");
					this.buffer.append(this.pEntities[i].getID());
					this.buffer.append(" -|>- ... -|>- ");
					this.buffer.append(superEntity);
					this.buffer.append("\",\n");
					this.buffer.append("                    \"");
					this.buffer.append(this.pEntities[i].getID());
					this.buffer.append(", ");
					this.buffer.append(superEntity);
					this.buffer
						.append(" | javaXL.XClass c1, javaXL.XClass c2 | c1.setSuperclass(c2.getName());\",\n");
					this.buffer.append("                    ");
					this.buffer.append(Util
						.minimizeFirstLetter(this.pEntities[i].getID()));
					this.buffer.append("Var,\n");
					this.buffer.append("                    ");
					this.buffer.append(Util.minimizeFirstLetter(superEntity));
					this.buffer.append("Var),\n");
					this.buffer.append("                 ");
					this.buffer.append(Constants.REQUIRED);
					this.buffer.append("),\n");
				}
				else {
					this.buffer.append("            post(pb,\n");
					this.buffer
						.append("                 makeStrictInheritance");
					this.buffer.append(this.getSuffix());
					this.buffer.append("Constraint(\n");
					this.buffer.append("                    \"");
					this.buffer.append(this.pEntities[i].getID());
					this.buffer.append(" -|>- ");
					this.buffer.append(superEntity);
					this.buffer.append("\",\n");
					this.buffer.append("                    \"");
					this.buffer.append(this.pEntities[i].getID());
					this.buffer.append(", ");
					this.buffer.append(superEntity);
					this.buffer
						.append(" | javaXL.XClass c1, javaXL.XClass c2 | c1.setSuperclass(c2.getName());\",\n");
					this.buffer.append("                    ");
					this.buffer.append(Util
						.minimizeFirstLetter(this.pEntities[i].getID()));
					this.buffer.append("Var,\n");
					this.buffer.append("                    ");
					this.buffer.append(Util.minimizeFirstLetter(superEntity));
					this.buffer.append("Var),\n");
					this.buffer.append("                 ");
					this.buffer.append(Constants.REQUIRED);
					this.buffer.append("),\n");
				}
			}
		}
	}
	public void open(final IAbstractModel anAbstractModel) {
		this.reset();

		this.currentModel = anAbstractModel;
		this.numberOfEntities = anAbstractModel.getNumberOfConstituents();
		this.pEntities = new IFirstClassEntity[this.numberOfEntities];
		// Yann 2005/10/12: Iterator!
		// I have now an iterator able to iterate over a
		// specified type of constituent of a list.
		final Iterator iterator =
			anAbstractModel.getIteratorOnTopLevelEntities();
		int i = 0;
		while (iterator.hasNext()) {
			this.pEntities[i++] = (IFirstClassEntity) iterator.next();
		}

		//	this.buffer.append('[');
		//	this.buffer.append(this.getPrefix());
		//	this.buffer.append(p.getName());
		//	this.buffer.append("Model()");
		//	this.buffer.append("\n    ->  printf(\"Solving...\\n\"),");
		//	this.buffer.append("\n        time_set(),");
		//	// this.buffer.append("\n        interactiveSolve(problemFor");
		//	this.buffer.append("\n        automaticSolve(");
		//	this.buffer.append(this.getPrefix());
		//	this.buffer.append("ProblemFor");
		//	this.buffer.append(p.getName());
		//	this.buffer.append("Model()),");
		//	this.buffer.append(
		//		"\n        let t := time_get() in printf(\"Solved in ~Sms.\\n\", t)");
		//	this.buffer.append("\n]\n\n");
		this.buffer.append('[');
		this.buffer.append(this.getPrefix());
		this.buffer.append("ProblemFor");
		this.buffer.append(anAbstractModel.getName());
		this.buffer.append("Model() : PtidejProblem");
		this.buffer.append("\n    ->  verbose() := 0,");
		this.buffer.append("\n        let pb := makePtidejProblem(\"");
		this.buffer.append(anAbstractModel.getName());
		this.buffer.append(" Model Problem\", length(listOfEntities), 90)");

		for (i = 0; i < this.numberOfEntities; i++) {
			this.buffer.append(",\n            ");
			this.buffer.append(Util.minimizeFirstLetter(this.pEntities[i]
				.getID()));
			// Yann 2002/12/21: PaLM!
			// I add the suffix "Var" to all variables
			// because PaLM declares a global named...
			// "component"!
			this.buffer.append("Var");
			this.buffer.append(" := makePtidejVar(pb, \"");
			this.buffer.append(this.pEntities[i].getID());
			this.buffer.append("\", 1, length(listOfEntities))");
		}

		this.buffer.append(" in (\n\n");
		this.buffer
			.append("            setVarsToShow(pb.globalSearchSolver, pb.vars),\n\n");
	}
	public final void open(final IClass p) {
		this.open((IFirstClassEntity) p);
	}
	public final void open(final IConstructor p) {
	}
	public final void open(final IDelegatingMethod p) {
		this.open((IMethod) p);
	}
	public final void open(final IDesignMotifModel p) {
		this.open((IAbstractModel) p);
	}
	private void open(final IFirstClassEntity p) {
		this.enclosingEntity = p;
	}
	public final void open(final IGetter p) {
		this.open((IMethod) p);
	}
	public final void open(final IGhost p) {
		this.open((IFirstClassEntity) p);
	}
	public final void open(final IInterface p) {
		this.open((IFirstClassEntity) p);
	}
	public void open(final IMemberClass p) {
		this.open((IFirstClassEntity) p);
	}
	public void open(final IMemberGhost p) {
		this.open((IFirstClassEntity) p);
	}
	public void open(final IMemberInterface p) {
		this.open((IFirstClassEntity) p);
	}
	public final void open(final IMethod p) {
		final IFirstClassEntity target =
			(IFirstClassEntity) this.currentModel.getTopLevelEntityFromID(p
				.getReturnType());
		if (target != null) {
			this.createConstraint(
				p,
				target,
				"Association",
				IAssociation.LOGO,
				this.buildRuntimeExceptionXCommand(IAssociation.LOGO, target),
				p.getWeight());
		}
	}
	public void open(final IPackage p) {
	}
	public void open(final IPackageDefault aPackage) {
	}
	public final void open(final ISetter p) {
		this.open((IMethod) p);
	}
	public final void reset() {
		this.buffer.setLength(0);
		this.useRelationships.clear();
		this.aggregationTargets.clear();
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
	public void visit(final IAggregation p) {
		this.elementWithTarget(
			p,
			"Aggregation",
			IAggregation.LOGO,
			this.buildRuntimeExceptionXCommand(
				IAggregation.LOGO,
				p.getTargetEntity()),
			p.getWeight());
		this.aggregationTargets.add(p.getTargetEntity().getID());
	}
	public void visit(final IAssociation p) {
		this.elementWithTarget(
			p,
			"Association",
			IAssociation.LOGO,
			this.buildRuntimeExceptionXCommand(
				IAssociation.LOGO,
				p.getTargetEntity()),
			p.getWeight());
	}
	public void visit(final IComposition p) {
		this.elementWithTarget(
			p,
			"Composition",
			IComposition.LOGO,
			this.buildRuntimeExceptionXCommand(
				IComposition.LOGO,
				p.getTargetEntity()),
			p.getWeight());
		this.aggregationTargets.add(p.getTargetEntity().getID());
	}
	public void visit(final IContainerAggregation p) {
		this.elementWithTarget(
			p,
			"ContainerAggregation",
			IContainerAggregation.LOGO,
			this.buildRuntimeExceptionXCommand(
				IContainerAggregation.LOGO,
				p.getTargetEntity()),
			p.getWeight());
		this.aggregationTargets.add(p.getTargetEntity().getID());
	}
	public void visit(final IContainerComposition p) {
		this.elementWithTarget(
			p,
			"ContainerComposition",
			IContainerComposition.LOGO,
			this.buildRuntimeExceptionXCommand(
				IContainerComposition.LOGO,
				p.getTargetEntity()),
			p.getWeight());
		this.aggregationTargets.add(p.getTargetEntity().getID());
	}
	public final void visit(final ICreation p) {
		this.elementWithTarget(
			p,
			"Creation",
			ICreation.LOGO,
			this.buildRuntimeExceptionXCommand(
				ICreation.LOGO,
				p.getTargetEntity()),
			p.getWeight());
	}
	public final void visit(final IField p) {
	}
	public void visit(final IMethodInvocation aMethodInvocation) {
	}
	public final void visit(final IParameter p) {
		final IFirstClassEntity target =
			(IFirstClassEntity) this.currentModel.getTopLevelEntityFromID(p
				.getTypeName());
		if (target != null) {
			this.createConstraint(
				p,
				target,
				"Association",
				IAssociation.LOGO,
				this.buildRuntimeExceptionXCommand(IAssociation.LOGO, target),
				100);
		}
	}
	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		// Do nothing for uninteresting primitive types.
	}
	public final void visit(final IUseRelationship p) {
		this.elementWithTarget(
			p,
			"Use",
			IUseRelationship.LOGO,
			this.buildRuntimeExceptionXCommand(
				IUseRelationship.LOGO,
				p.getTargetEntity()),
			p.getWeight());
	}
}
