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
package sad.rule.creator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sad.rule.creator.model.IRuleCard;
import sad.rule.creator.model.IVisitor;
import sad.rule.creator.visitor.CodeSmellGenerator;
import sad.rule.creator.visitor.DesignSmellGenerator;
import util.io.ProxyConsole;

public class RULECreator {
	public static void main(final String[] args) throws Exception {
		final String[] listOfRuleCards = new File(Paths.RESOURCES_PATH).list();
		for (int i = 0; i < listOfRuleCards.length; i++) {
			final String ruleCard = listOfRuleCards[i];
			if (ruleCard.endsWith(".rules")) {
				final RULECreator ruleCreator = new RULECreator(
						new String[] { Paths.RESOURCES_PATH + ruleCard });
				ruleCreator.parse();
			}
		}
		//	ruleCreator =
		//		new RULECreator(
		//			new String[] {
		//				PropertyManager.getRulecardDirectory()
		//					+ "SpaghettiCode.rules" });
		//	ruleCreator.parse();
		//
		//	ruleCreator =
		//		new RULECreator(
		//			new String[] {
		//				PropertyManager.getRulecardDirectory()
		//					+ "SwissArmyKnife.rules" });
		//	ruleCreator.parse();
		//
		//	ruleCreator =
		//		new RULECreator(
		//			new String[] {
		//				PropertyManager.getRulecardDirectory()
		//					+ "FunctionalDecomposition.rules" });
		//	ruleCreator.parse();
		//
		//	ruleCreator =
		//		new RULECreator(
		//			new String[] {
		//				PropertyManager.getRulecardDirectory()
		//					+ "DummyRuleCard.rules" });
		//	ruleCreator.parse();
	}

	private final String fileName;

	public RULECreator(final String[] fileNames) {
		// TODO: Must deal with multiple files!
		this.fileName = fileNames[0];
	}

	public String[] parse() {
		final ArrayList listOfRuleCard = new ArrayList();

		try {
			final LineNumberReader reader = new LineNumberReader(
					new InputStreamReader(new FileInputStream(this.fileName)));

			final StringBuffer buffer = new StringBuffer();
			String readLine;
			while ((readLine = reader.readLine()) != null) {
				buffer.append(readLine);
				buffer.append('\n');
			}
			reader.close();

			final RULEParser parser = new RULEParser(
					new RULELexer(new StringReader(buffer.toString())));
			parser.parse();
			if (parser.hasError()) {
				return new String[0];
			}

			final IVisitor visitor = new DesignSmellGenerator(this.fileName,
					true);
			final List rulesSet = parser.getRulesSet();
			final Iterator iterRule = rulesSet.iterator();
			while (iterRule.hasNext()) {
				final IRuleCard aRuleCard = (IRuleCard) iterRule.next();
				aRuleCard.accept(visitor);

				final StringBuffer generatedCode = (StringBuffer) ((DesignSmellGenerator) visitor)
						.getResult();
				System.out.println(generatedCode);
				generatedCode.delete(0, generatedCode.length());

				// ADD TO TEST CodeSmellGenerator
				final IVisitor visitor2 = new CodeSmellGenerator(
						aRuleCard.getID());

				listOfRuleCard.add(aRuleCard.getID());

				// ADD TO TEST CodeSmellGenerator
				aRuleCard.accept(visitor2);
			}
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

		final String[] temp = (String[]) listOfRuleCard
				.toArray(new String[listOfRuleCard.size()]);
		return temp;
	}
}
