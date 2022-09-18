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
package padl.pagerank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import padl.kernel.IIdiomLevelModel;
import padl.pagerank.utils.InputDataGeneratorWithoutMembersAndGhostsAnd3Relations;
import padl.visitor.IGenerator;
import util.io.ProxyConsole;
import util.io.ProxyDisk;
import util.process.CallerHelper;

public class PageRankRankingGenerator {
	private static PageRankRankingGenerator UniqueInstance;
	public static PageRankRankingGenerator getInstance() {
		if (PageRankRankingGenerator.UniqueInstance == null) {
			PageRankRankingGenerator.UniqueInstance =
				new PageRankRankingGenerator();
		}
		return PageRankRankingGenerator.UniqueInstance;
	}
	private PageRankRankingGenerator() {
	}
	public void compareModels(
		final IIdiomLevelModel anIdiomLevelModel1,
		final IIdiomLevelModel anIdiomLevelModel2,
		final IGenerator aGenerator) {

		try {
			aGenerator.reset();
			final String model1 = anIdiomLevelModel1.generate(aGenerator);
			final String outputFileName1 =
			// "C:/Temp/File1";
			// ProxyDisk.getInstance().fileTempString();
				anIdiomLevelModel1.getDisplayName();
			final Writer writer1 =
				ProxyDisk
					.getInstance()
					.fileTempOutput(outputFileName1 + ".txt");
			writer1.write(model1);
			writer1.close();

			aGenerator.reset();
			final String model2 = anIdiomLevelModel2.generate(aGenerator);
			final String outputFileName2 =
			// "C:/Temp/File2";
			// ProxyDisk.getInstance().fileTempString();
				anIdiomLevelModel2.getDisplayName();
			final Writer writer2 =
				ProxyDisk
					.getInstance()
					.fileTempOutput(outputFileName2 + ".txt");
			writer2.write(model2);
			writer2.close();

			final String outputDirectory =
			// "C:/Temp";
				"\""
						+ ProxyDisk
							.getInstance()
							.directoryTempOutput(
								outputFileName1 + '-' + outputFileName2)
							.getAbsolutePath() + '"';

			CallerHelper
				.execute(
					"padl.pagerank.PageRankRankingGenerator.compareModels(IIdiomLevelModel, IIdiomLevelModel, IGenerator)",
					("\"../PADL Generator PageRank/20150728/madmatch2.exe\" -h 12 -o \""
							+ ProxyDisk.getInstance().fileTempOutputString(
								outputFileName1 + ".txt")
							+ "\" -n \""
							+ ProxyDisk.getInstance().fileTempOutputString(
								outputFileName2 + ".txt") + "\" -p " + outputDirectory)
						.replace('\\', '/'));
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
	public void computeRanking(
		final IIdiomLevelModel anIdiomLevelModel,
		final String anOutputFileName) {

		try {
			this.generateModel(
				anIdiomLevelModel,
				anOutputFileName,
				new InputDataGeneratorWithoutMembersAndGhostsAnd3Relations());

			final Runtime run = Runtime.getRuntime();
			final Process process =
				run
					.exec("../PADL Generator PageRank/ClassRank/ClassRank.exe -i \""
							+ anOutputFileName
							+ ".txt\" -o \""
							+ anOutputFileName + ".csv\"");

			final InputStream stderr = process.getErrorStream();
			final InputStreamReader isr = new InputStreamReader(stderr);
			final BufferedReader br = new BufferedReader(isr);
			System.out
				.println("\nIf any error occurred, it will be printed out between the two following tags:");
			System.out.println("<ERROR>");
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			System.out.println("</ERROR>");

			final int exitVal = process.waitFor();
			System.out.println("Process exitValue: " + exitVal);
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final InterruptedException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	public void computeRanking(
		final IIdiomLevelModel anIdiomLevelModel,
		final String aProgramName,
		final String anOutputDirName) {

		this.computeRanking(anIdiomLevelModel, anOutputDirName + aProgramName);
	}
	public void generateModel(
		final IIdiomLevelModel aModel,
		final String anOutputFileName,
		final IGenerator aGenerator) {

		aModel.generate(aGenerator);
		try {
			final String outputFile = anOutputFileName + ".txt";
			final Writer fw =
				ProxyDisk.getInstance().fileAbsoluteOutput(outputFile);
			final BufferedWriter out = new BufferedWriter(fw);
			out.write(aGenerator.getCode());
			out.close();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
