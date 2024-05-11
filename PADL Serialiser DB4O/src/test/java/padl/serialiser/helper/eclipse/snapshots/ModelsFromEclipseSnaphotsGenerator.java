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
package padl.serialiser.helper.eclipse.snapshots;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import padl.generator.helper.ModelGenerator;
import padl.generator.helper.ModelGeneratorCaller;
import padl.generator.helper.utils.FilesUtils;
import padl.generator.helper.utils.Untar;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import util.io.ProxyConsole;
import util.io.ProxyDisk;

public class ModelsFromEclipseSnaphotsGenerator {

	public static void main(String[] args) {

		String snapshotsPath = "F:/Snapshots/Test/";

		//Lire la liste des zips e analyser
		List zipsList = FilesUtils.getZipsList(snapshotsPath);

		String serializationDirPath = "F:/Snapshots/Serialization/";
		String destPath = "F:/Snapshots/Dezip/";
		//	String runInfosPath= "F:/Snapshots/Run Infos/";

		System.out.println("Nombre de zips" + zipsList.size());

		//boucler sur cette liste
		for (int i = 0; i < zipsList.size(); i++) {

			System.out.println(i + " " + zipsList.get(i));

			String tarName = (String) zipsList.get(i);
			tarName = tarName.substring(tarName.lastIndexOf("\\") + 1);
			tarName = tarName.substring(0, tarName.length() - 7);
			System.out.println(" tarName " + tarName);

			String untaredPath = getString(new String[] { destPath, tarName });
			System.out.println(" untaredPath " + untaredPath);

			//detarrer
			Untar untar =
				new Untar((String) zipsList.get(i), new File(destPath));
			try {
				untar.untar();
			}
			catch (IOException e) {

				e.printStackTrace();
			}
			//processer pour ne garder que les fichiers java et .jar e la rigueur
			//ne doit on pas cibler certains repertoires et eviter les repertoires exemples pour les tests

			final Writer errorWriter =
				new BufferedWriter(ProxyDisk.getInstance().fileAbsoluteOutput(
					"rsc/errorOutput/" + tarName + "_errorOutput.txt"));
			final Writer normalWriter =
				new BufferedWriter(ProxyDisk.getInstance().fileAbsoluteOutput(
					"rsc/normalOutput/" + tarName + "_normalOutput.txt"));
			ProxyConsole.getInstance().setErrorOutput(errorWriter);
			ProxyConsole.getInstance().setNormalOutput(normalWriter);

			//creer le modele avec le generateur de Yann
			IIdiomLevelModel idiomLevelModel =
				ModelGenerator
					.generateModelFromJavaFilesDirectoryUsingEclipse(untaredPath);

			String outputPath =
				getString(new String[] { "rsc/output/", tarName,
						" (From Java Files, No Ghosts).classes)" });
			ModelGeneratorCaller.output(idiomLevelModel, outputPath);

			//serialiser le modele

			String modelSerializedPath =
				getString(new String[] { serializationDirPath, tarName });

			String deserializedPath =
				PADLModelSerializer.serializeModel(
					idiomLevelModel,
					modelSerializedPath);
			System.out.println("serialization " + deserializedPath);
			//deserializer le modele et le comparer au modele en cours
			ICodeLevelModel idiomLevelModelDeserialized =
				(ICodeLevelModel) PADLModelSerializer
					.deserializeModel(deserializedPath);

			System.out.println("before serialization "
					+ idiomLevelModel.getNumberOfTopLevelEntities()
					+ " constituents"
					+ idiomLevelModel.getNumberOfConstituents());
			System.out.println("before serialization "
					+ idiomLevelModelDeserialized.getNumberOfTopLevelEntities()
					+ " constituents"
					+ idiomLevelModelDeserialized.getNumberOfConstituents());
			//supprimer le repertoire dezippe

			//FilesUtils.deleteDir(new File(untaredPath));
		}

		//voir avec Yann s'il faut mettre cela dans une autre classe pour ne pas creer trop de liens avec d'autres projets

		//ne vaut il pas mieux faire une autre classe pour modelGeneratorfromjavafiles
	}
	private static String getString(String[] tokens) {
		StringBuffer tmpString = new StringBuffer();
		for (int i = 0; i < tokens.length; i++) {
			tmpString.append(tokens[i]);
		}

		return tmpString.toString();

	}
}
