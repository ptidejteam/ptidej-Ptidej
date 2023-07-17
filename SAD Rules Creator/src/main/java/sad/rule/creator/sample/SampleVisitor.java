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
package sad.rule.creator.sample;

import sad.rule.creator.model.IAggregation;
import sad.rule.creator.model.IAssociation;
import sad.rule.creator.model.IComposition;
import sad.rule.creator.model.IInheritance;
import sad.rule.creator.model.IMetric;
import sad.rule.creator.model.IOperator;
import sad.rule.creator.model.IRule;
import sad.rule.creator.model.IRuleCard;
import sad.rule.creator.model.ISemantic;
import sad.rule.creator.model.IStruct;
import sad.rule.creator.model.IVisitor;

public class SampleVisitor implements IVisitor {

	public void close(final IRule aRule) {
		System.out.println("Close Rule");
	}

	public void close(final IRuleCard aRuleCard) {
		System.out.println("Close RuleCard");
	}

	public Object getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	public void open(final IRule aRule) {
		System.out.println("Open Rule");
	}

	public void open(final IRuleCard aRuleCard) {
		System.out.println("Open RuleCard");
	}

	public void setSourceFile(final String fileName) {
	}

	public void visit(final IAggregation anAggregation) {
		System.out.println("Visit Aggregation");
	}

	public void visit(final IAssociation anAssociation) {
		System.out.println("Visit Association");
	}

	public void visit(final IComposition aComposition) {
		System.out.println("Visit Composition");
	}

	public void visit(final IInheritance anInheritance) {
		System.out.println("Visit Inheritance");
	}

	public void visit(final IMetric aMetric) {
		System.out.println("Visit Metric");
	}

	public void visit(final IOperator anOperator) {
		System.out.println("Visit Operator");
	}

	public void visit(final ISemantic aSemantic) {
		System.out.println("Visit Semantic");
	}

	public void visit(final IStruct aStruct) {
		System.out.println("Visit Struct");
	}
}
