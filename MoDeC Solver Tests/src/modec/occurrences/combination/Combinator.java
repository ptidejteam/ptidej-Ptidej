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
package modec.occurrences.combination;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.util.Util;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.OccurrenceComponent;
import util.io.Files;
import util.io.ProxyDisk;

public class Combinator {
	public static void main(final String[] args) {
		final String aName = "JHotDraw v5.4b1";
		final String aClassPath = "../../P-MARt Workspace/JHotDraw v5.4b1/bin/";
		final String someOccurrencesFile =
			"rsc/JSME'08 (bis)/ConstraintResults in JHotDraw v5.4b1 for Command.ini";
		final String anOutputFile =
			"rsc/JSME'08 (bis)/Cleaned ConstraintResults in JHotDraw v5.4b1 for Command.ini";

		try {
			final ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel(aName);
			codeLevelModel.create(new CompleteClassFileCreator(
				new String[] { aClassPath },
				true));

			final Properties occurrencesProperties = new Properties();
			occurrencesProperties
				.load(new FileInputStream(someOccurrencesFile));
			final OccurrenceBuilder builder = OccurrenceBuilder.getInstance();
			final Occurrence[] canonicalStaticOccurrences =
				builder.getCanonicalOccurrences(occurrencesProperties);

			System.out.println("Number of canonical static occurrences: "
					+ canonicalStaticOccurrences.length);

			final IFirstClassEntity superEntity =
				codeLevelModel
					.getTopLevelEntityFromID("CH.ifa.draw.application.DrawApplication");
			final List validOccurrences = new ArrayList();
			for (int i = 0; i < canonicalStaticOccurrences.length; i++) {
				final Occurrence occurrence = canonicalStaticOccurrences[i];
				final OccurrenceComponent occurrenceComponent =
					occurrence.getComponent("client".toCharArray());
				final String clientClass =
					occurrenceComponent.getDisplayValue();
				final IFirstClassEntity client =
					codeLevelModel.getTopLevelEntityFromID(clientClass);

				if (client != null
						&& (client.equals(superEntity) || Util
							.isEntityInheritingFrom(client, superEntity))) {
					validOccurrences.add(occurrence);
				}
			}

			System.out.println("Number of valid static occurrences: "
					+ validOccurrences.size());
			final Occurrence[] validOccurrencesArray =
				new Occurrence[validOccurrences.size()];
			validOccurrences.toArray(validOccurrencesArray);
			Occurrence.print(validOccurrencesArray, ProxyDisk
				.getInstance()
				.fileTempOutput(anOutputFile));
		}
		catch (final CreationException e) {
			e.printStackTrace();
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
