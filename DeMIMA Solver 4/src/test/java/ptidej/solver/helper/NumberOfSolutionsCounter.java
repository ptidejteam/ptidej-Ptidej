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
package ptidej.solver.helper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import util.io.ReaderInputStream;

/**
 * @author guehene
 */
public class NumberOfSolutionsCounter {
	public static void main(final String[] args) {
		try {
			final String[] solutionFiles =
				new String[] {
				// "../Ptidej Solver Data/TSE'07 (Open Source Systems)/070303 - ConstraintResults in MapperXML v1.9.7 for Composite WITH CARDINALITY CONSTRAINT.ini",
				// "../Ptidej Solver Data/TSE'07 (Open Source Systems)/070303 - ConstraintResults in QuickUML 2001 for Composite WITH CARDINALITY CONSTRAINT.ini",
				// "../Ptidej Solver Data/ConstraintResults in JHotDraw v5.1 for Observer1.ini",
				// "../Ptidej Solver Data/ConstraintResults in JHotDraw v5.1 for Observer2.ini",
				// "../Ptidej Solver Data/TSE'07 (Open Source Systems)/070817 - ConstraintResults in JHotDraw v5.1 for Observer1.ini",
				// "../Ptidej Solver Data/TSE'07 (Open Source Systems)/070817 - ConstraintResults in JHotDraw v5.1 for Observer2.ini",
				// "../Ptidej Solver Data/TSE'07 (Open Source Systems)/070310 - ConstraintResults in JHotDraw v5.1 for Command.ini",
				"../Ptidej Solver Data/TSE'07 (Open Source Systems)/070303 - ConstraintResults in JHotDraw v5.1 for State.ini",
				// "../Ptidej Solver Data/TSE'07 (Open Source Systems)/070310 - ConstraintResults in JHotDraw v5.1 for Composite.ini",
				// "../Ptidej Solver Data/TSE'07 (Open Source Systems)/070817 - ConstraintResults in JRefactory v2.6.24 for Observer1.ini",
				//"../Ptidej Solver Data/TSE'07 (Open Source Systems)/070817 - ConstraintResults in JRefactory v2.6.24 for Observer2.ini",
				// "../Ptidej Solver Data/TSE'07 (Open Source Systems)/070817 - ConstraintResults in JUnit v3.7 for Observer1.ini",
				// "../Ptidej Solver Data/TSE'07 (Open Source Systems)/070817 - ConstraintResults in JUnit v3.7 for Observer2.ini",
				// "../Ptidej Solver Data/TSE'07 (Open Source Systems)/070817 - ConstraintResults in MapperXML v1.9.7 for Observer1.ini",
				// "../Ptidej Solver Data/TSE'07 (Open Source Systems)/070817 - ConstraintResults in MapperXML v1.9.7 for Observer2.ini",
				// "../Ptidej Solver Data/TSE'07 (Open Source Systems)/070817 - ConstraintResults in QuickUML 2001 for Observer1.ini",
				// "../Ptidej Solver Data/TSE'07 (Open Source Systems)/070817 - ConstraintResults in QuickUML 2001 for Observer2.ini" 
				};

			final OccurrenceBuilder solutionBuilder =
				OccurrenceBuilder.getInstance();
			final Properties allOccurrences = new Properties();
			for (int i = 0; i < solutionFiles.length; i++) {
				final String file = solutionFiles[i];
				final Properties properties = new Properties();
				properties.load(new ReaderInputStream(new FileReader(file)));
				allOccurrences.putAll(properties);
			}
			final Occurrence[] occurrences =
				solutionBuilder.getCanonicalOccurrences(allOccurrences);
			System.out.println(occurrences.length);
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
