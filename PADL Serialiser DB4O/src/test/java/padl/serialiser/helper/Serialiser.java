/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Ga�l Gu�h�neuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Ga�l Gu�h�neuc and others, see in file; API and its implementation
 ******************************************************************************/
package padl.serialiser.helper;

import java.io.File;
import java.io.IOException;
import padl.analysis.UnsupportedSourceModelException;
import padl.generator.helper.ModelGenerator;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.exception.CreationException;
import padl.serialiser.DB4OSerialiser;
import padl.test.helper.ModelComparator;
import padl.util.ModelStatistics;
import util.io.Files;
import util.io.ProxyConsole;
import util.io.ProxyDisk;

public class Serialiser {
	public static void main(String[] args) throws CreationException,
			UnsupportedSourceModelException, IOException {

		final String mainPath = "../../P-MARt Workspace/";
		final String[] programNames = new File(mainPath).list();
		for (int i = 0; i < programNames.length; i++) {
			final String programName = programNames[i];

			if (programName.startsWith("Eclipse JDT")) {
				final String programPath = mainPath + programName + "/libs/";

				ProxyConsole.getInstance().setDebugOutput(
					ProxyDisk.getInstance().fileTempOutput(
						"rsc/" + programName + ".dbg"));
				ProxyConsole.getInstance().setErrorOutput(
					ProxyDisk.getInstance().fileTempOutput(
						"rsc/" + programName + ".err"));
				ProxyConsole.getInstance().setNormalOutput(
					ProxyDisk.getInstance().fileTempOutput(
						"rsc/" + programName + ".out"));

				ProxyConsole
					.getInstance()
					.normalOutput()
					.println("Creating model...");
				System.out.println(programName);
				long beginning = System.currentTimeMillis();

				final ModelStatistics statistics = new ModelStatistics();
				final IAbstractLevelModel abstractLevelModel =
					ModelGenerator.generateModelFromJARs(
						programName,
						programPath,
						statistics);

				long end = System.currentTimeMillis();
				ProxyConsole.getInstance().normalOutput().println(statistics);
				ProxyConsole
					.getInstance()
					.normalOutput()
					.print("Model created in ");
				ProxyConsole
					.getInstance()
					.normalOutput()
					.print(end - beginning);
				ProxyConsole.getInstance().normalOutput().println(" ms.");
				beginning = System.currentTimeMillis();

				final String serialisedFileName =
					DB4OSerialiser.getInstance().serialiseWithAutomaticNaming(
						abstractLevelModel);

				end = System.currentTimeMillis();
				ProxyConsole
					.getInstance()
					.normalOutput()
					.print("Model serialised in ");
				ProxyConsole
					.getInstance()
					.normalOutput()
					.print(end - beginning);
				ProxyConsole.getInstance().normalOutput().println(" ms.");
				beginning = System.currentTimeMillis();

				final IAbstractLevelModel serialisedAbstractLevelModel =
					(IAbstractLevelModel) DB4OSerialiser
						.getInstance()
						.deserialise(serialisedFileName);

				end = System.currentTimeMillis();
				ProxyConsole
					.getInstance()
					.normalOutput()
					.print("Model deserialised in ");
				ProxyConsole
					.getInstance()
					.normalOutput()
					.print(end - beginning);
				ProxyConsole.getInstance().normalOutput().println(" ms.");

				abstractLevelModel.walk(new ModelComparator(
					serialisedAbstractLevelModel));

				ProxyConsole.getInstance().debugOutput().close();
				ProxyConsole.getInstance().errorOutput().close();
				ProxyConsole.getInstance().normalOutput().close();
			}
		}
	}
}
