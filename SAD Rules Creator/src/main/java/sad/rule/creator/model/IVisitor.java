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
package sad.rule.creator.model;

public interface IVisitor {
	void close(final IRule aRule);
	void close(final IRuleCard aRuleCard);
	Object getResult();
	void open(final IRule aRule);
	void open(final IRuleCard aRuleCard);
	void visit(final IAggregation anAggregation);
	void visit(final IAssociation anAssociation);
	void visit(final IComposition aComposition);
	void visit(final IInheritance anInheritance);
	void visit(final IMetric aMetric);
	void visit(final IOperator anOperator);
	void visit(final ISemantic aSemantic);
	void visit(final IStruct aStruct);
}
