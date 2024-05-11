/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package padl.pagerank.helper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import org.junit.Assert;
import padl.generator.helper.ModelGenerator;
import padl.kernel.IIdiomLevelModel;
import padl.pagerank.PageRankRankingGenerator;
import padl.pagerank.utils.InputDataGeneratorWith9Relations;
import padl.pagerank.utils.InputDataGeneratorWith9RelationsForCPP;
import padl.visitor.IGenerator;
import util.io.ProxyConsole;
import util.io.ProxyDisk;

public class PageRankCallerWithNoParameters {
	public static void callForSomeClassFiles(
		final String[] someNames,
		final String[] somePaths) {

		Assert.assertEquals(someNames.length, somePaths.length);

		final IGenerator generator =
			new InputDataGeneratorWith9Relations(false, true);

		for (int i = 0; i < someNames.length; i++) {
			final String name = someNames[i];

			final IIdiomLevelModel idomLevelModel =
				ModelGenerator.generateModelFromClassFilesDirectory(
					name,
					somePaths[i]);

			final long startTime = System.currentTimeMillis();
			idomLevelModel.generate(generator);
			try {
				final String outputFile = "rsc/" + name + ".txt";
				final Writer fw =
					ProxyDisk.getInstance().fileTempOutput(outputFile);
				final BufferedWriter out = new BufferedWriter(fw);
				out.write(generator.getCode());
				out.close();
				generator.reset();
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
			ProxyConsole
				.getInstance()
				.debugOutput()
				.print("Model generated in ");
			ProxyConsole
				.getInstance()
				.debugOutput()
				.print(System.currentTimeMillis() - startTime);
			ProxyConsole.getInstance().debugOutput().println(" ms.");
		}
	}
	public static void callForSomeCPPFiles(
		final String aName,
		final String aPath,
		final IGenerator aGenerator,
		final Writer aResultWriter) {

		long startTime = System.currentTimeMillis();
		final IIdiomLevelModel idomLevelModel = null;
		// TODO Add this code back
		//	ModelGenerator.generateModelFromCppFilesUsingEclipse(
		//		aName,
		//		new String[] { aPath });
		ProxyConsole.getInstance().debugOutput().print("Model generated in ");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(System.currentTimeMillis() - startTime);
		ProxyConsole.getInstance().debugOutput().println(" ms.");

		startTime = System.currentTimeMillis();
		idomLevelModel.generate(aGenerator);
		try {
			aResultWriter.write(aGenerator.getCode());
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		ProxyConsole.getInstance().debugOutput().print("Model analysed in ");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(System.currentTimeMillis() - startTime);
		ProxyConsole.getInstance().debugOutput().println(" ms.");
	}
	public static void callForSomeCPPFiles(
		final String[] someNames,
		final String[] somePaths) {

		Assert.assertEquals(someNames.length, somePaths.length);

		final IGenerator generator =
			new InputDataGeneratorWith9Relations(false, true);

		for (int i = 0; i < someNames.length; i++) {
			final String name = someNames[i];
			final String path = somePaths[i];
			final String file = "rsc/" + name + ".txt";

			try {
				final Writer fw = ProxyDisk.getInstance().fileTempOutput(file);
				final BufferedWriter out = new BufferedWriter(fw);
				PageRankCallerWithNoParameters.callForSomeCPPFiles(
					name,
					path,
					generator,
					out);
				out.close();
				generator.reset();
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(final String[] args) {
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Rhino v1.4R3/bin/",
		//		"rsc/Rhino v1.4R3",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Rhino v1.5R1/bin/",
		//		"rsc/Rhino v1.5R1",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Rhino v1.5R2/bin/",
		//		"rsc/Rhino v1.5R2",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Rhino v1.5R3/bin/",
		//		"rsc/Rhino v1.5R3",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Rhino v1.5R4/bin/",
		//		"rsc/Rhino v1.5R4",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Rhino v1.5R4.1/bin/",
		//		"rsc/Rhino v1.5R4.1",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Rhino v1.5R5/bin/",
		//		"rsc/Rhino v1.5R5",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Rhino v1.6R1/bin/",
		//		"rsc/Rhino v1.6R1",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Rhino v1.6R2/bin/",
		//		"rsc/Rhino v1.6R2",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Rhino v1.6R3/bin/",
		//		"rsc/Rhino v1.6R3",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Rhino v1.6R4/bin/",
		//		"rsc/Rhino v1.6R4",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Rhino v1.6R5/bin/",
		//		"rsc/Rhino v1.6R5",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Rhino v1.6R6/bin/",
		//		"rsc/Rhino v1.6R6",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Rhino v1.6R7/bin/",
		//		"rsc/Rhino v1.6R7",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Rhino v1.7R1/bin/",
		//		"rsc/Rhino v1.7R1",
		//		generator);
		//	generator.reset();

		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/ArgoUML v0.10/bin/",
		//		"rsc/ArgoUML v0.10",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/ArgoUML v0.12/bin/",
		//		"rsc/ArgoUML v0.12",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/ArgoUML v0.14/bin/",
		//		"rsc/ArgoUML v0.14",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/ArgoUML v0.15.6/bin/",
		//		"rsc/ArgoUML v0.15.6",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/ArgoUML v0.16.1/bin/",
		//		"rsc/ArgoUML v0.16.1",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/ArgoUML v0.17.5/bin/",
		//		"rsc/ArgoUML v0.17.5",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/ArgoUML v0.18.1/bin/",
		//		"rsc/ArgoUML v0.18.1",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/ArgoUML v0.19.8/bin/",
		//		"rsc/ArgoUML v0.19.8",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/ArgoUML v0.20/bin/",
		//		"rsc/ArgoUML v0.20",
		//		generator);
		//	generator.reset();

		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Azureus v2.0.3.2/bin/",
		//		"rsc/Azureus v2.0.3.2",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Azureus v2.0.4.0/bin/",
		//		"rsc/Azureus v2.0.4.0",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Azureus v2.0.4.2/bin/",
		//		"rsc/Azureus v2.0.4.2",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Azureus v2.0.6.0/bin/",
		//		"rsc/Azureus v2.0.6.0",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Azureus v2.0.7.0/bin/",
		//		"rsc/Azureus v2.0.7.0",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Azureus v2.0.8.0/bin/",
		//		"rsc/Azureus v2.0.8.0",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Azureus v2.0.8.2/bin/",
		//		"rsc/Azureus v2.0.8.2",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Azureus v2.0.8.4/bin/",
		//		"rsc/Azureus v2.0.8.4",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Azureus v2.1.0.0/bin/",
		//		"rsc/Azureus v2.1.0.0",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Azureus v2.1.0.4/bin/",
		//		"rsc/Azureus v2.1.0.4",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Azureus v2.2.0.0/bin/",
		//		"rsc/Azureus v2.2.0.0",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Azureus v2.2.0.2/bin/",
		//		"rsc/Azureus v2.2.0.2",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Azureus v2.3.0.0/bin/",
		//		"rsc/Azureus v2.3.0.0",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Azureus v2.3.0.2/bin/",
		//		"rsc/Azureus v2.3.0.2",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Azureus v2.3.0.4/bin/",
		//		"rsc/Azureus v2.3.0.4",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Azureus v2.3.0.6/bin/",
		//		"rsc/Azureus v2.3.0.6",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Azureus v2.4.0.0/bin/",
		//		"rsc/Azureus v2.4.0.0",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Azureus v2.4.0.2/bin/",
		//		"rsc/Azureus v2.4.0.2",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/Azureus v2.5.0.0/bin/",
		//		"rsc/Azureus v2.5.0.0",
		//		generator);
		//	generator.reset();

		//	PageRankRankingGenerator.getInstance().generateModelFromEclipse(
		//		"../../P-MARt Workspace/Eclipse JDT v1.0/libs/plugins/",
		//		"rsc/Eclipse JDT v1.0",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromEclipse(
		//		"../../P-MARt Workspace/Eclipse JDT v2.0/libs/plugins/",
		//		"rsc/Eclipse JDT v2.0",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromEclipse(
		//		"../../P-MARt Workspace/Eclipse JDT v2.1/libs/plugins/",
		//		"rsc/Eclipse JDT v2.1",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromEclipse(
		//		"../../P-MARt Workspace/Eclipse JDT v2.1.2/libs/plugins/",
		//		"rsc/Eclipse JDT v2.1.2",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromEclipse(
		//		"../../P-MARt Workspace/Eclipse JDT v2.1.3/libs/plugins/",
		//		"rsc/Eclipse JDT v2.1.3",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromEclipse(
		//		"../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/",
		//		"rsc/Eclipse JDT v3.0",
		//		generator);
		//	generator.reset();

		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-1.0-win32/plugins/",
		//			"rsc/Eclipse v1.0",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-2.0.1-win32/plugins/",
		//			"rsc/Eclipse v2.0.1",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-2.0.2-win32/plugins/",
		//			"rsc/Eclipse v2.0.2",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-2.0-win32/plugins/",
		//			"rsc/Eclipse v2.0",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-2.1.1-win32/plugins/",
		//			"rsc/Eclipse v2.1.1",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-2.1.2-win32/plugins/",
		//			"rsc/Eclipse v2.1.2",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-2.1.3-win32/plugins/",
		//			"rsc/Eclipse v2.1.3",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-2.1-win32/plugins/",
		//			"rsc/Eclipse v2.1",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.0.1-win32/plugins/",
		//			"rsc/Eclipse v3.0.1",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.0.2-win32/plugins/",
		//			"rsc/Eclipse v3.0.2",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.0-win32/plugins/",
		//			"rsc/Eclipse v3.0",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.1.1-win32/plugins/",
		//			"rsc/Eclipse v3.1.1",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.1.2-win32/plugins/",
		//			"rsc/Eclipse v3.1.2",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.1-win32/plugins/",
		//			"rsc/Eclipse v3.1",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.2.1-win32/plugins/",
		//			"rsc/Eclipse v3.2.1",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.2.2-win32/plugins/",
		//			"rsc/Eclipse v3.2.2",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.2-win32/plugins/",
		//			"rsc/Eclipse v3.2",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.3.1.1-win32/plugins/",
		//			"rsc/Eclipse v3.3.1.1",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.3.1-win32/plugins/",
		//			"rsc/Eclipse v3.3.1",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.3.2-win32/plugins/",
		//			"rsc/Eclipse v3.3.2",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.3-win32/plugins/",
		//			"rsc/Eclipse v3.3",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.4-win32/plugins/",
		//			"rsc/Eclipse v3.4",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.5M1-win32/plugins/",
		//			"rsc/Eclipse v3.5M1",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.5M2-win32/plugins/",
		//			"rsc/Eclipse v3.5M2",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.5M3-win32/plugins/",
		//			"rsc/Eclipse v3.5M3",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.5M4-win32/plugins/",
		//			"rsc/Eclipse v3.5M4",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.5M5-win32/plugins/",
		//			"rsc/Eclipse v3.5M5",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.5M6-win32/plugins/",
		//			"rsc/Eclipse v3.5M6",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.5M7-win32/plugins/",
		//			"rsc/Eclipse v3.5M7",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.5RC1-win32/plugins/",
		//			"rsc/Eclipse v3.5RC1",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.5RC2-win32/plugins/",
		//			"rsc/Eclipse v3.5RC2",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.5RC3-win32/plugins/",
		//			"rsc/Eclipse v3.5RC3",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromJARs(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.5RC4-win32/plugins/",
		//			"rsc/Eclipse v3.5RC4",
		//			generator);
		//	generator.reset();

		//	final String root = "../../P-MARt Workspace/";
		//	final String[] list = new File(root).list();
		//	for (int i = 0; i < list.length; i++) {
		//		final String directory = list[i];
		//		if (directory.startsWith("ArgoUML")) {
		//			System.out.print("Analysing ");
		//			System.out.print(directory);
		//			System.out.println(" ...");
		//
		//			PageRankRankingGenerator.getInstance().generateModelFromDir(
		//				root + directory + "/bin/",
		//				"rsc/" + directory,
		//				generator);
		//			generator.reset();
		//		}
		//	}

		//	final String root = "../../P-MARt Workspace/";
		//	final String[] list = new File(root).list();
		//	for (int i = 0; i < list.length; i++) {
		//		final String file = list[i];
		//
		//		//	if (file.toLowerCase().startsWith("azureus v")) {
		//		//		System.out.print("Analysing ");
		//		//		System.out.print(file);
		//		//		System.out.println(" ...");
		//		//
		//		//		PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		//			root + file + "/bin/",
		//		//			"rsc/" + file,
		//		//			generator);
		//		//		generator.reset();
		//		//	}
		//		//	if (file.toLowerCase().startsWith("argouml v")) {
		//		//		System.out.print("Analysing ");
		//		//		System.out.print(file);
		//		//		System.out.println(" ...");
		//		//
		//		//		PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		//			root + file + "/bin/",
		//		//			"rsc/" + file,
		//		//			generator);
		//		//		generator.reset();
		//		//	}
		//		//	if (file.toLowerCase().startsWith("dyndns v")) {
		//		//		System.out.print("Analysing ");
		//		//		System.out.print(file);
		//		//		System.out.println(" ...");
		//		//
		//		//		PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		//			root + file + '/',
		//		//			"rsc/" + file,
		//		//			generator);
		//		//		generator.reset();
		//		//	}
		//		//	if (file.toLowerCase().startsWith("eclipse jdt v3.3")
		//		//			|| file.toLowerCase().startsWith("eclipse jdt v3.4")) {
		//		//		System.out.print("Analysing ");
		//		//		System.out.print(file);
		//		//		System.out.println(" ...");
		//		//
		//		//		PageRankRankingGenerator.getInstance().generateModelFromJARs(
		//		//			root + file + "/plugins/",
		//		//			"rsc/" + file,
		//		//			generator);
		//		//		generator.reset();
		//		//	}
		//		//	if (file.toLowerCase().startsWith("jfreechart v")) {
		//		//		System.out.print("Analysing ");
		//		//		System.out.print(file);
		//		//		System.out.println(" ...");
		//		//
		//		//		PageRankRankingGenerator.getInstance().generateModelFromDirs(
		//		//			new String[] { root + file + "/bin/",
		//		//					root + file + "/classes/" },
		//		//			"rsc/" + file,
		//		//			generator);
		//		//		generator.reset();
		//		//	}
		//		//	if (file.toLowerCase().startsWith("mylyn v")) {
		//		//		System.out.print("Analysing ");
		//		//		System.out.print(file);
		//		//		System.out.println(" ...");
		//		//
		//		//		PageRankRankingGenerator.getInstance().generateModelFromJARs(
		//		//			root + file + "/plugins/",
		//		//			"rsc/" + file,
		//		//			generator);
		//		//		generator.reset();
		//		//	}
		//		//	if (file.startsWith("POI")) {
		//		//		System.out.print("Analysing ");
		//		//		System.out.print(file);
		//		//		System.out.println(" ...");
		//		//
		//		//		PageRankRankingGenerator.getInstance().generateModelFromJARs(
		//		//			root + file + "/build/",
		//		//			"rsc/" + file,
		//		//			generator);
		//		//		generator.reset();
		//		//	}
		//		//	if (file.toLowerCase().startsWith("rhino v")) {
		//		//		System.out.print("Analysing ");
		//		//		System.out.print(file);
		//		//		System.out.println(" ...");
		//		//
		//		//		PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		//			root + file + "/bin/",
		//		//			"rsc/" + file,
		//		//			generator);
		//		//		generator.reset();
		//		//	}
		//		//	if (file.startsWith("Velocity")) {
		//		//		System.out.print("Analysing ");
		//		//		System.out.print(file);
		//		//		System.out.println(" ...");
		//		//
		//		//		// For Velocity Engine, I must find the JAR file starting with "velocity"...
		//		//		final String[] list2 = new File(root + file).list();
		//		//		for (int j = 0; j < list2.length; j++) {
		//		//			final String subfile = list2[j];
		//		//
		//		//			if (subfile.startsWith("velocity")
		//		//					&& subfile.endsWith(".jar")) {
		//		//
		//		//				PageRankRankingGenerator
		//		//					.getInstance()
		//		//					.generateModelFromDir(
		//		//						root + file + '/' + subfile,
		//		//						"rsc/" + file,
		//		//						generator);
		//		//				generator.reset();
		//		//			}
		//		//		}
		//		//	}
		//		//	if (file.toLowerCase().startsWith("xerces v")) {
		//		//		System.out.print("Analysing ");
		//		//		System.out.print(file);
		//		//		System.out.println(" ...");
		//		//
		//		//		PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		//			root + file + "/bin/",
		//		//			"rsc/" + file,
		//		//			generator);
		//		//		generator.reset();
		//		//	}
		//		if (file.toLowerCase().startsWith("argouml-")) {
		//			System.out.print("Analysing ");
		//			System.out.print(file);
		//			System.out.println(" ...");
		//
		//			PageRankRankingGenerator.getInstance().generateModelFromDir(
		//				root + file + "/bin/",
		//				"rsc/" + file,
		//				generator);
		//			generator.reset();
		//		}
		//		if (file.toLowerCase().startsWith("jfreechart-")) {
		//			System.out.print("Analysing ");
		//			System.out.print(file);
		//			System.out.println(" ...");
		//
		//			PageRankRankingGenerator.getInstance().generateModelFromDir(
		//				root + file + "/bin/",
		//				"rsc/" + file,
		//				generator);
		//			generator.reset();
		//		}
		//	}

		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromDirs(
		//			new String[] {
		//					"D:/Software/P-MARt Workspace/jakarta-struts-1.1-lib/jakarta-oro.jar",
		//					"D:/Software/P-MARt Workspace/jakarta-struts-1.1-lib/struts.jar",
		//					"D:/Software/P-MARt Workspace/jakarta-struts-1.1-lib/struts-legacy.jar" },
		//			"rsc/jakarta-struts-1.1-lib.txt",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromDirs(
		//			new String[] {
		//					"D:/Software/P-MARt Workspace/jakarta-struts-1.2.4-lib/jakarta-oro.jar",
		//					"D:/Software/P-MARt Workspace/jakarta-struts-1.2.4-lib/struts.jar" },
		//			"rsc/jakarta-struts-1.2.4-lib.txt",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromDirs(
		//			new String[] { "D:/Software/P-MARt Workspace/JEdit v4.1 (From JAR)/jedit.jar" },
		//			"rsc/JEdit v4.1 (From JAR).txt",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromDirs(
		//			new String[] { "D:/Software/P-MARt Workspace/JEdit v4.2 (From JAR)/jedit.jar" },
		//			"rsc/JEdit v4.2 (From JAR).txt",
		//			generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/JHotDraw v5.2/bin/",
		//		"rsc/JHotDraw v5.2.txt",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/JHotDraw v5.3/bin/",
		//		"rsc/JHotDraw v5.3.txt",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/jakarta-struts-1.1-src/bin/",
		//		"rsc/jakarta-struts-1.1-src.txt",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/jakarta-struts-1.2.4-src/bin/",
		//		"rsc/jakarta-struts-1.2.4-src.txt",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/JEdit v4.1 (From Source)/bin/",
		//		"rsc/JEdit v4.1 (From Source).txt",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/JEdit v4.2 (From Source)/bin/",
		//		"rsc/JEdit v4.2 (From Source).txt",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/ArgoUML v0.18.1/bin/",
		//		"rsc/ArgoUML v0.18.1.txt",
		//		generator);
		//	generator.reset();
		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"D:/Software/P-MARt Workspace/JFreeChart v0.9.21/bin/",
		//		"rsc/JFreeChar v0.9.21.txt",
		//		generator);
		//	generator.reset();

		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromAOLFile(
		//			"../PADL Creator AOL Tests/rsc/Chrome/results-15.0.837.0/15.0.837.0-concat_des_2011-09-20123633.aol",
		//			"rsc/Chrome 15.0.837.0",
		//			generator);
		//	generator.reset();

		//	PageRankRankingGenerator
		//		.getInstance()
		//		.generateModelFromAOLFile(
		//			"../PADL Creator AOL Tests/rsc/Chrome/results-15.0.838.0/15.0.838.0-concat_des_2011-09-22130420.aol",
		//			"rsc/Chrome 15.0.838.0",
		//			generator);
		//	generator.reset();

		//	PageRankRankingGenerator.getInstance().generateModelFromDir(
		//		"rsc/Lucene Core v3.0.3.jar",
		//		"rsc/Lucene Core v3.0.3",
		//		generator);
		//	generator.reset();

		//	//	"jEdit v3.0", "jEdit v3.1", "jEdit v3.2", "jEdit v4.0", "jEdit v4.1", "jEdit v4.2"
		//	final String[] names =
		//		new String[] { "JHD v5.2", "JHD v5.3", "JHD v5.41", "JHD v5.42",
		//				"JHD v6.01" };
		//	final String[] paths =
		//		new String[] { "D:/Software/P-MARt Workspace/JHotDraw v5.2/",
		//				"D:/Software/P-MARt Workspace/JHotDraw v5.3/",
		//				"D:/Software/P-MARt Workspace/JHotDraw v5.4b1/",
		//				"D:/Software/P-MARt Workspace/JHotDraw v5.4b2/",
		//				"D:/Software/P-MARt Workspace/JHotDraw v6.0b1/" };
		//	PageRankCallerWithNoParameters.callForSomeClassFiles(names, paths);

		//	final String[] names =
		//		new String[] { "Log4CPP v0.2.7", "Log4CPP v0.2.8",
		//				"Log4CPP v0.3.3", "Log4CPP v0.3.4b", "Log4CPP v0.3.5rc1",
		//				"Log4CPP v0.3.5rc2", "Log4CPP v0.3.5rc3", "Log4CPP v1.0",
		//				"Log4CPP v1.1", "Log4CPP v1.1rc1", "Log4CPP v1.1rc2",
		//				"Log4CPP v1.1rc3" };
		//	final String[] paths =
		//		new String[] { "D:/Software/C++ Programs/Log4CPP/log4cpp-0.2.7/",
		//				"D:/Software/C++ Programs/Log4CPP/log4cpp-0.2.8/",
		//				"D:/Software/C++ Programs/Log4CPP/log4cpp-0.3.3/",
		//				"D:/Software/C++ Programs/Log4CPP/log4cpp-0.3.4b/",
		//				"D:/Software/C++ Programs/Log4CPP/log4cpp-0.3.5rc1/",
		//				"D:/Software/C++ Programs/Log4CPP/log4cpp-0.3.5rc2/",
		//				"D:/Software/C++ Programs/Log4CPP/log4cpp-0.3.5rc3/",
		//				"D:/Software/C++ Programs/Log4CPP/log4cpp-1.0",
		//				"D:/Software/C++ Programs/Log4CPP/log4cpp-1.1/",
		//				"D:/Software/C++ Programs/Log4CPP/log4cpp-1.1rc1/",
		//				"D:/Software/C++ Programs/Log4CPP/log4cpp-1.1rc2/",
		//				"D:/Software/C++ Programs/Log4CPP/log4cpp-1.1rc3/" };

		//	final String[] names =
		//		new String[] { "Chrome v1.0.154.53 - Browser",
		//				"Chrome v15.0.837.0 - Browser" };
		//	final String[] paths =
		//		new String[] {
		//				"D:/Software/C++ Programs/Chrome/Chrome v1.0.154.53/browser/",
		//				"D:/Software/C++ Programs/Chrome/Chrome v15.0.837.0/browser/" };

		final String[] names = new String[] { "files_before", "files_after" };
		final String[] paths =
			new String[] { "C:/Data/Change Types/files_before/",
					"C:/Data/Change Types/files_after/" };

		final IGenerator generator =
			new InputDataGeneratorWith9RelationsForCPP(false, true);

		for (int i = 1; i < names.length; i++) {
			final IIdiomLevelModel idiomLevelModel1 = null;
			// TODO Add this code back
			//	ModelGenerator.generateModelFromCppFilesUsingEclipse(
			//		names[i - 1],
			//		new String[] { paths[i - 1] });
			
			final IIdiomLevelModel idiomLevelModel2 = null;
			// TODO Add this code back
			//	ModelGenerator.generateModelFromCppFilesUsingEclipse(
			//		names[i],
			//		new String[] { paths[i] });
			PageRankRankingGenerator.getInstance().compareModels(
				idiomLevelModel1,
				idiomLevelModel2,
				generator);
		}
	}
}
