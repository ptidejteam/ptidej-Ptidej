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
package padl.creator.classfile.test.compare;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.classfile.test.ClassFilePrimitive;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.impl.Factory;
import padl.util.ModelStatistics;

/**
 * @author Yann-Gäel Guéhéneuc
 * @since  2004/02/18
 */
public class Compare extends ClassFilePrimitive {
	public Compare(String aName) {
		super(aName);
	}
	public void testCompare() {
		final String path = "../Ptidej Tests/bin/ptidej/example/composite2/";
		final ICodeLevelModel codeLevelModel =
			Factory.getInstance().createCodeLevelModel(path);
		final ModelStatistics patternStatistics = new ModelStatistics();
		codeLevelModel.addModelListener(patternStatistics);

		try {
			// Building the program representation.
			codeLevelModel.create(new CompleteClassFileCreator(
				new String[] { path }));
			System.out.println();
			System.out.println(patternStatistics);

			// Detecting design pattern.
			System.out.println();
			final Map solutions = new HashMap();
			//	idiomLevelModel.compare(
			//		PatternRepository.getCurrentPatternRepository(
			//			fileRepository));

			// Display solutions... 
			final Iterator iterator = solutions.keySet().iterator();
			while (iterator.hasNext()) {
				final String currentPattern = (String) iterator.next();
				final List patternSolutions =
					(List) solutions.get(currentPattern);
				System.out.println("* Model: " + currentPattern + ": "
						+ patternSolutions.size() + " instance(s).");
				if (patternSolutions.size() > 0) {
					final Iterator iterator2 = patternSolutions.iterator();
					while (iterator2.hasNext()) {
						final Map currentSol = (Map) iterator2.next();
						final Iterator iterator3 =
							currentSol.keySet().iterator();
						while (iterator3.hasNext()) {
							final String currentConstituent =
								(String) iterator3.next();
							System.out.print(currentConstituent);
							System.out.println(':');
							final Iterator iterator4 =
								((List) currentSol.get(currentConstituent))
									.iterator();
							while (iterator4.hasNext()) {
								System.out.print('\t');
								System.out
									.println(((IFirstClassEntity) iterator4
										.next()).getName());
							}
							System.out.println();
						}
						System.out.println("----");
					}
				}
			}
		}
		catch (final Exception e) {
			e.printStackTrace();
		}

		// TODO: Convert the output into assertions...
	}
}
