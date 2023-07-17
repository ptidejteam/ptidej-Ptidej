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
package sad.rule.creator.model.impl;

import java.util.List;
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

/**
 * @author Pierre Leduc
 */
public class Factory {
	private static Factory UniqueInstance;
	public static Factory getInstance() {
		if (Factory.UniqueInstance == null) {
			Factory.UniqueInstance = new Factory();
		}
		return Factory.UniqueInstance;
	}

	protected Factory() {
	}

	public IAggregation createAggregationRelationship(
		final String aName,
		final IConstituent aSourceEntity,
		final IConstituent aTargetEntity,
		final int aSourceCardinality,
		final int aTargetCardinality) {

		return new Aggregation(
			aName,
			aSourceEntity,
			aTargetEntity,
			aSourceCardinality,
			aTargetCardinality);
	}

	public IAssociation createAssociationRelationship(
		final String anID,
		final IConstituent aSourceEntity,
		final IConstituent aTargetEntity,
		final int aSourceCardinality,
		final int aTargetCardinality) {

		return new Association(
			anID,
			aSourceEntity,
			aTargetEntity,
			aSourceCardinality,
			aTargetCardinality);
	}

	public IComposition createCompositionRelationship(
		final String aName,
		final IConstituent aSourceEntity,
		final IConstituent aTargetEntity,
		final int aSourceCardinality,
		final int aTargetCardinality) {

		return new Composition(
			aName,
			aSourceEntity,
			aTargetEntity,
			aSourceCardinality,
			aTargetCardinality);
	}

	public IInheritance createInheritanceRelationship(
		final String aName,
		final IConstituent aSourceEntity,
		final IConstituent aTargetEntity,
		final int aSourceCardinality,
		final int aTargetCardinality) {

		return new Inheritance(
			aName,
			aSourceEntity,
			aTargetEntity,
			aSourceCardinality,
			aTargetCardinality);
	}

	public IMetric createMetric(
		final String aName,
		final double aValue,
		final double fuzziness) {

		return new Metric(aName, aValue, fuzziness);
	}

	public IMetric createMetric(
		final String aName,
		final int aValue_ordi,
		final double fuzziness) {

		return new Metric(aName, aValue_ordi, fuzziness);
	}

	public IMetric createMetric(
		final String aName,
		final int anOperator,
		final IMetric anOperand1,
		final IMetric anOperand2) {
		return new Metric(aName, anOperator, anOperand1, anOperand2);
	}

	public IOperator createOperator(
		final String aName,
		final int anOperator,
		final IConstituent op1,
		final IConstituent op2) {

		return new Operator(aName, anOperator, op1, op2);
	}

	public IRule createRule(final String aName) {

		return new Rule(aName);
	}

	public IRuleCard createRuleCard(final String anActirID) {
		return new RuleCard(anActirID);
	}

	public IRuleGhost createRuleGhost(final String aName) {

		return new RuleGhost(aName);
	}

	public ISemantic createSemantic(
		final String aName,
		final int aType,
		final List aValue) {

		return new Semantic(aName, aType, aValue);
	}

	public IStruct createStruct(final String aName, final int aType) {
		// TODO: Change the integer primitive for and object to be able to send "null" as parameter
		return new Struct(aName, aType, -1);
	}

	public IStruct createStruct(
		final String aName,
		final int aType,
		final int aValue) {
		return new Struct(aName, aType, aValue);
	}
}
