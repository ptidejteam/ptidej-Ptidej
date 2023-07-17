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
package padl.pagerank.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.pagerank.utils.InputDataGeneratorWithoutMembersAndGhostsAnd3Relations;
import util.io.ProxyConsole;
import util.io.ProxyDisk;


public class ExecutableCaller {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String root = "D:/Software/SNCF-Systems/SUIMAGARE.jar";
		//String root = "D:/Software/SNCF-Systems/refhorace.jar";
		//String root = "D:/Software/SNCF-Systems/Infogareng.jar";
		// final String root = "D:/Software/SNCF-Systems/thalyseo.jar";
		final String root = "D:/Software/P-MARt Workspace/JHotDraw v5.4b2/bin/";
		final String name = "JHotDraw v5.4b2";
		final ICodeLevelModel codeLevelModel =
			Factory.getInstance().createCodeLevelModel(name);
		try {
			codeLevelModel.create(new CompleteClassFileCreator(
				new String[] { root },
				true));

			final IIdiomLevelModel model =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			ExecutableCaller.generateModel(model, "rsc/" + name + ".txt");

			final Runtime run = Runtime.getRuntime();
			final Process pgRk =
				run.exec("../PADL Generator PageRank/PageRank/pagerank.exe -i \"rsc/" + name
						+ ".txt\" -o \"rsc/" + name + ".csv\"");

			final InputStream stderr = pgRk.getErrorStream();
			final InputStreamReader isr = new InputStreamReader(stderr);
			final BufferedReader br = new BufferedReader(isr);
			System.out.println("<ERROR>");
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			System.out.println("</ERROR>");
			final int exitVal = pgRk.waitFor();
			System.out.println("Process exitValue: " + exitVal);
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final InterruptedException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	private static void generateModel(final IIdiomLevelModel model, String file) {
		final InputDataGeneratorWithoutMembersAndGhostsAnd3Relations dgen =
			new InputDataGeneratorWithoutMembersAndGhostsAnd3Relations();
		model.generate(dgen);
		try {
			final Writer fw =
				ProxyDisk.getInstance().fileTempOutput(file, true);
			final BufferedWriter out = new BufferedWriter(fw);
			out.write(dgen.getCode());
			out.close();
			System.out.println(dgen.getCode());
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
