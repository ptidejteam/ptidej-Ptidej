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
package sad.rule.creator.visitor;

import sad.rule.creator.model.IAggregation;
import sad.rule.creator.model.IAssociation;
import sad.rule.creator.model.IComposition;
import sad.rule.creator.model.IConstituent;
import sad.rule.creator.model.IInheritance;
import sad.rule.creator.model.IMetric;
import sad.rule.creator.model.IOperator;
import sad.rule.creator.model.IRule;
import sad.rule.creator.model.IRuleCard;
import sad.rule.creator.model.IRuleGhost;
import sad.rule.creator.model.ISemantic;
import sad.rule.creator.model.IStruct;
import sad.rule.creator.model.IVisitor;

public class GhostRuleCleaner implements IVisitor {
	private IRuleCard currentRuleCard;
	// We count the number of rules
	private int nbRules;

	public GhostRuleCleaner() {
	}
	public void close(final IRule aRule) {
		this.nbRules++;
		this.outputMessage("close IRule", aRule);
		// we get the id of the first rule
		if (this.nbRules == 1) {
			aRule.getID();
		}
	}
	public void close(final IRuleCard aRuleCard) {
		this.outputMessage("close IRuleCard :", aRuleCard);
	}
	public Object getResult() {
		return null;
	}
	public void open(final IRule aRule) {
		this.outputMessage("open IRule :", aRule);
	}
	public void open(final IRuleCard aRuleCard) {
		this.currentRuleCard = aRuleCard;
		this.outputMessage("open IRuleCard :", aRuleCard);
		this.nbRules = 0;
		this.outputMessage(">>>>>>>>> " + this.nbRules, aRuleCard);
	}
	private void outputMessage(final String typeElement, final Object o) {
	}
	public void visit(final IAggregation anAggregation) {
		this.outputMessage("visit IAggregation :", anAggregation);
		this.visit((IAssociation) anAggregation);
	}
	public void visit(final IAssociation anAssociation) {
		this.outputMessage("visit IAssociation dans GHOST!!!:", anAssociation);

		// Check for RuleGhost in the association
		if (anAssociation.getSourceConstituent() instanceof IRuleGhost) {
			// Try to substitute the RuleGhost
			final IConstituent rule =
				this.currentRuleCard.getConstituentFromID(anAssociation
					.getSourceConstituent()
					.getID());
			if (rule != null) {
				anAssociation.setSourceConstituent(rule);
			}
		}

		if (anAssociation.getTargetConstituent() instanceof IRuleGhost) {
			// Try to substitute the RuleGhost
			final IConstituent rule =
				this.currentRuleCard.getConstituentFromID(anAssociation
					.getTargetConstituent()
					.getID());
			if (rule != null) {
				anAssociation.setTargetConstituent(rule);
			}
		}
	}
	public void visit(final IComposition aComposition) {
		this.outputMessage("visit IComposition :", aComposition);
		this.visit((IAssociation) aComposition);
	}
	public void visit(final IInheritance anInheritance) {
		this.outputMessage("visit IInheritance :", anInheritance);

		// Check for RuleGhost in the association
		if (anInheritance.getSourceConstituent() instanceof IRuleGhost) {
			// Try to substitute the RuleGhost
			final IConstituent rule =
				this.currentRuleCard.getConstituentFromID(anInheritance
					.getSourceConstituent()
					.getID());
			if (rule != null) {
				anInheritance.setSourceConstituent(rule);
			}
		}

		if (anInheritance.getTargetConstituent() instanceof IRuleGhost) {
			// Try to substitute the RuleGhost
			final IConstituent rule =
				this.currentRuleCard.getConstituentFromID(anInheritance
					.getTargetConstituent()
					.getID());
			if (rule != null) {
				anInheritance.setTargetConstituent(rule);
			}
		}
	}
	public void visit(final IMetric aMetric) {
		this.outputMessage("visit IMetric : ", aMetric);
	}
	public void visit(final IOperator anOperator) {
		this.outputMessage("visit IOperator :", anOperator);

		// Check for RuleGhost in the Operator
		if (anOperator.getOperand1() instanceof IRuleGhost) {
			// Try to substitute the RuleGhost
			final IConstituent rule =
				this.currentRuleCard.getConstituentFromID(anOperator
					.getOperand1()
					.getID());
			if (rule != null) {
				anOperator.setOperand1(rule);
			}
		}

		if (anOperator.getOperand2() instanceof IRuleGhost) {
			// Try to substitute the RuleGhost
			final IConstituent rule =
				this.currentRuleCard.getConstituentFromID(anOperator
					.getOperand2()
					.getID());
			if (rule != null) {
				anOperator.setOperand2(rule);
			}
		}
	}
	public void visit(final ISemantic aSemantic) {
		this.outputMessage("visit ISemantic : ", aSemantic);
	}
	public void visit(final IStruct aStruct) {
		this.outputMessage("visit IStruct : ", aStruct);
	}
}
