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
package padl.serialiser.helper;

import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.IAbstractModel;
import padl.kernel.IAbstractModelSerialiser;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.serialiser.DB4OSerialiser;
import util.io.ProxyConsole;

public class DataBaseManager {
	public static void main(final String[] args) {
		long beginning = 0;
		long end = 0;

		beginning = System.currentTimeMillis();
		IAbstractModel abstractModel =
			DataBaseManager.createAbstractModel(
				"ArgoUML v0.20",
				"../../P-MARt Workspace/ArgoUML v0.20/");
		end = System.currentTimeMillis();
		System.out.print("Model created in ");
		System.out.print(end - beginning);
		System.out.println(" ms.");

		final IAbstractModelSerialiser serialiser =
			DB4OSerialiser.getInstance();
		beginning = System.currentTimeMillis();
		final String dataBaseName =
			serialiser.serialiseWithAutomaticNaming(abstractModel);
		end = System.currentTimeMillis();
		System.out.print("Model serialised in ");
		System.out.print(end - beginning);
		System.out.println(" ms.");

		beginning = System.currentTimeMillis();
		abstractModel = serialiser.deserialise(dataBaseName);
		end = System.currentTimeMillis();
		System.out.print("Model deserialised in ");
		System.out.print(end - beginning);
		System.out.println(" ms.");
	}
	private static IAbstractModel createAbstractModel(
		final String aName,
		final String aClassPath) {

		System.out.println("Creating code-level model...");
		ICodeLevelModel codeLevelModel;
		try {
			codeLevelModel = Factory.getInstance().createCodeLevelModel(aName);
			codeLevelModel.create(new CompleteClassFileCreator(
				new String[] { aClassPath },
				true));

			System.out.println("Creating idiom-level model...");
			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);
			System.out.println("Idiom-level model with "
					+ idiomLevelModel.getNumberOfTopLevelEntities()
					+ " entities.");

			return idiomLevelModel;
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		return null;
	}
}
