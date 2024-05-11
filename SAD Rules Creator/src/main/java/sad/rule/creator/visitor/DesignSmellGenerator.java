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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Writer;
import java.util.Iterator;
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
import sad.rule.creator.utils.Constants;
import util.io.ProxyConsole;
import util.io.ProxyDisk;

public class DesignSmellGenerator implements IVisitor {
	private static final String CLASSNAME_TAG = "<CLASSNAME>";
	private static final String RULE_FILE_TAG = "<RULE_FILE>";

	private final String codeSmellOutputDir =
			"../SAD/src/sad/designsmell/detection/repository/";
	private IRule currentRule;
	private IRuleCard currentRuleCard;
	// We need to get the id of the first rule to return the result on this set
	private String firstRule;
	private final StringBuffer generatedCode = new StringBuffer();
	// We count the number of rules
	private int nbRules;
	private final String outputFileName =
			"../SAD/src/sad/designsmell/detection/repository/";
	private final String ruleFileName;
	private final boolean shouldGenerateCode;
	private final String templatePath =
			"../SAD Rules Creator/rsc/templates/DesignSmellTemplate.txt";

	public DesignSmellGenerator(
		final String aFileName,
		final boolean shouldGenerateCode) {

		this.ruleFileName = aFileName;
		this.shouldGenerateCode = shouldGenerateCode;
	}
	public void close(final IRule aRule) {
		this.nbRules++;
		this.outputMessage("close IRule", aRule);
		// we get the id of the first rule
		if (this.nbRules == 1) {
			this.firstRule = aRule.getID();
		}
	}
	public void close(final IRuleCard aRuleCard) {
		this.outputMessage("close IRuleCard :", aRuleCard);

		// We instanciate the design smells
		if (this.shouldGenerateCode) {
			final StringBuffer tempGeneratedCodeFirst = new StringBuffer();
			// TODO: Should not be hard-coded!
			tempGeneratedCodeFirst
				.append("public void detect(final IAbstractLevelModel anAbstractLevelModel) {\n");
			tempGeneratedCodeFirst
				.append("final Set candidateDesignSmells = new HashSet();\n");
			this.generatedCode.insert(0, tempGeneratedCodeFirst);

			try {
				final StringBuffer buffer = this.readFile(this.templatePath);
				this.replaceTAG(
					buffer,
					DesignSmellGenerator.CLASSNAME_TAG,
					aRuleCard.getID());
				this.replaceTAG(
					buffer,
					DesignSmellGenerator.RULE_FILE_TAG,
					this.ruleFileName);

				// Ensure the directory is created
				// Yann 2013/05/30
				// Not necessary thanks to ProxyDisk 
				//	(new File(this.outputFileName + aRuleCard.getID())).mkdirs();

				final Writer fw =
					ProxyDisk.getInstance().fileAbsoluteOutput(
						this.outputFileName + aRuleCard.getID() + "/"
								+ aRuleCard.getID() + "Detection.java",
						false);
				fw.write(buffer.toString());
				fw.close();

			}
			catch (final FileNotFoundException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final IOException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final Exception e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}

			final StringBuffer tempGeneratedCodeEnd = new StringBuffer();
			tempGeneratedCodeEnd.append("\nfinal Iterator iterSet = ");
			tempGeneratedCodeEnd.append("set" + this.firstRule);
			tempGeneratedCodeEnd.append(".iterator();\n");
			tempGeneratedCodeEnd.append("while(iterSet.hasNext()) {\n");
			tempGeneratedCodeEnd
				.append("final ICodeSmell aCodeSmell = (ICodeSmell) iterSet.next();\n");
			tempGeneratedCodeEnd
				.append("final DesignSmell designSmell = new DesignSmell(aCodeSmell);\n");
			tempGeneratedCodeEnd.append("designSmell.setName(\"");
			tempGeneratedCodeEnd.append(aRuleCard.getID());
			tempGeneratedCodeEnd.append("\");\n");
			tempGeneratedCodeEnd
				.append("final String definition = \"To defined\";\n");
			tempGeneratedCodeEnd
				.append("designSmell.setDefinition(definition);\n");
			tempGeneratedCodeEnd
				.append("candidateDesignSmells.add(designSmell);\n");
			tempGeneratedCodeEnd.append("}\n");
			tempGeneratedCodeEnd
				.append("this.setSetOfDesignSmells(candidateDesignSmells);\n");
			tempGeneratedCodeEnd.append("}\n");
			tempGeneratedCodeEnd.append("}\n");

			this.generatedCode.append(tempGeneratedCodeEnd.toString());

			try {
				// Ensure the directory is created
				// Yann 2013/05/30
				// Not necessary thanks to ProxyDisk
				//	new File(this.outputFileName + aRuleCard.getID()).mkdirs();

				final Writer fw =
					ProxyDisk.getInstance().fileAbsoluteOutput(
						this.outputFileName + aRuleCard.getID() + "/"
								+ aRuleCard.getID() + "Detection.java",
						true);
				fw.write(this.generatedCode.toString());
				fw.close();
			}
			catch (final IOException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
	}
	private int countConstituents(final Iterator iteratorOnConstituents) {
		int numberOfConstituents = 0;
		while (iteratorOnConstituents.hasNext()) {
			iteratorOnConstituents.next();
			numberOfConstituents++;
		}
		return numberOfConstituents;
	}
	private int countNonTerminalRules(final Iterator iteratorOnConstituents) {
		int numberOfNonTerminalRules = 0;
		while (iteratorOnConstituents.hasNext()) {
			final IConstituent constituent =
				(IConstituent) iteratorOnConstituents.next();
			if (constituent instanceof IMetric
					|| constituent instanceof IStruct
					|| constituent instanceof ISemantic) {

				numberOfNonTerminalRules++;
			}
		}
		return numberOfNonTerminalRules;
	}

	private void generateRule(final IRule aRule) {
		final String ruleName = aRule.getID();
		final StringBuffer tempGeneratedCode = new StringBuffer();
		tempGeneratedCode.append("\nfinal ICodeSmellDetection cs");
		tempGeneratedCode.append(ruleName);
		tempGeneratedCode.append(" = new ");
		tempGeneratedCode.append(ruleName);
		tempGeneratedCode.append("Detection();\n");
		tempGeneratedCode.append("cs");
		tempGeneratedCode.append(ruleName);
		tempGeneratedCode.append(".detect(anAbstractLevelModel);\n");
		tempGeneratedCode.append("final Set set");
		tempGeneratedCode.append(ruleName);
		tempGeneratedCode.append(" = ((");
		tempGeneratedCode.append(ruleName);
		tempGeneratedCode.append("Detection) cs");
		tempGeneratedCode.append(ruleName);
		tempGeneratedCode.append(").getCodeSmells();\n");

		this.generatedCode.insert(0, tempGeneratedCode);
	}
	public Object getResult() {
		return this.generatedCode;
	}
	public void open(final IRule aRule) {
		this.outputMessage("open IRule :", aRule);

		if (this.shouldGenerateCode) {
			this.currentRule = aRule;

			final int numberOfConstituents =
				this.countConstituents(aRule.getIteratorOnConstituents());
			final int numberOfXX =
				this.countNonTerminalRules(aRule.getIteratorOnConstituents());
			// We are in a leaf
			if (numberOfConstituents == 0 || numberOfXX == 1) {
				this.generateRule(aRule);
			}

			// else Here, we are in non terminal rules with rule such as
			// RULE : mainClass {INTER LargeClassLowCohesion ControllerClass } ; 
		}
	}
	public void open(final IRuleCard aRuleCard) {
		this.currentRuleCard = aRuleCard;
		this.outputMessage("open IRuleCard :", aRuleCard);
		this.nbRules = 0;
		this.outputMessage(">>>>>>>>> " + this.nbRules, aRuleCard);

		// Purge output directory
		new File(this.codeSmellOutputDir + aRuleCard.getID()).delete();
	}
	private void outputMessage(final String typeElement, final Object o) {
		if (this.shouldGenerateCode) {
			System.out.print(typeElement);
			System.out.print(" : ");
			System.out.println(o);
		}
	}
	private StringBuffer readFile(final String fileName) {
		final StringBuffer buffer = new StringBuffer();
		try {
			final LineNumberReader reader =
				new LineNumberReader(new InputStreamReader(new FileInputStream(
					fileName)));

			String readLine;
			while ((readLine = reader.readLine()) != null) {
				buffer.append(readLine);
				buffer.append('\n');
			}
			reader.close();
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return buffer;
	}
	private void replaceTAG(
		final StringBuffer source,
		final String TAG,
		final String replaceWith) {

		while (source.indexOf(TAG) >= 0) {
			source.replace(
				source.indexOf(TAG),
				source.indexOf(TAG) + TAG.length(),
				replaceWith);
		}
	}
	public void visit(final IAggregation anAggregation) {
		this.outputMessage("visit IAggregation :", anAggregation);
		this.visit((IAssociation) anAggregation);
	}
	public void visit(final IAssociation anAssociation) {
		this
			.outputMessage("visit IAssociation in DesignSmell: ", anAssociation);

		final StringBuffer tempGeneratedCode = new StringBuffer();
		tempGeneratedCode.append("\nfinal Set set");
		tempGeneratedCode.append(this.currentRule.getID());

		// Association 1-n
		if (this.shouldGenerateCode
				&& anAssociation.getSourceCardinality() == 1
				&& anAssociation.getTargetCardinality() > 1) {

			tempGeneratedCode
				.append(" = this.relations.checkAssociationOneToMany(");
		}
		// Association 1-1
		else if (this.shouldGenerateCode
				&& anAssociation.getSourceCardinality() == 1
				&& anAssociation.getTargetCardinality() == 1) {
			tempGeneratedCode
				.append(" = this.relations.checkAssociationOneToOne(");
		}

		tempGeneratedCode.append(Constants.RELATION_ASSOC + ", ");

		tempGeneratedCode.append("set");
		tempGeneratedCode.append(anAssociation.getSourceConstituent().getID());
		tempGeneratedCode.append(",  set");
		tempGeneratedCode.append(anAssociation.getTargetConstituent().getID());
		tempGeneratedCode.append(");\n");
		// Code generated locally has to be inserted at the 
		// beginning because the tree is browsed from top to down.				
		this.generatedCode.insert(0, tempGeneratedCode.toString());

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

		final StringBuffer tempGeneratedCode = new StringBuffer();
		tempGeneratedCode.append("\nfinal Set set");
		tempGeneratedCode.append(this.currentRule.getID());

		// Inheritance 1-1
		if (this.shouldGenerateCode
				&& anInheritance.getSourceCardinality() == 1
				&& anInheritance.getTargetCardinality() == 1) {

			tempGeneratedCode
				.append(" = this.relations.checkAssociationOneToOne(");
		}
		// Inheritance 1-n
		else if (this.shouldGenerateCode
				&& anInheritance.getSourceCardinality() == 1
				&& anInheritance.getTargetCardinality() > 1) {

			tempGeneratedCode
				.append(" = this.relations.checkAssociationOneToMany(");
		}

		tempGeneratedCode.append(Constants.RELATION_INHERIT + ", ");

		tempGeneratedCode.append("set");
		tempGeneratedCode.append(anInheritance.getSourceConstituent().getID());
		tempGeneratedCode.append(",  set");
		tempGeneratedCode.append(anInheritance.getTargetConstituent().getID());
		tempGeneratedCode.append(");\n");
		// Code generated locally has to be inserted at the 
		// beginning because the tree is browsed from top to down.				
		this.generatedCode.insert(0, tempGeneratedCode.toString());

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

		if (this.shouldGenerateCode) {

			if (!(anOperator.getOperand1() instanceof IRule)) {
				this.generateRule(this.currentRule);
			}
			else {

				final StringBuffer tempGeneratedCode = new StringBuffer();

				tempGeneratedCode.append("\nfinal Set set");
				tempGeneratedCode.append(this.currentRule.getID());
				tempGeneratedCode.append(" = \n");
				if (anOperator.getOperatorType() == Constants.OPERATOR_INTER) {
					tempGeneratedCode.append("this.operators.intersection(set");
				}
				else if (anOperator.getOperatorType() == Constants.OPERATOR_UNION) {
					tempGeneratedCode.append("this.operators.union(set");
				}
				tempGeneratedCode.append(anOperator.getOperand1().getID());
				tempGeneratedCode.append(",set");
				tempGeneratedCode.append(anOperator.getOperand2().getID());
				tempGeneratedCode.append(");\n");

				this.generatedCode.insert(0, tempGeneratedCode.toString());
			}
		}

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
