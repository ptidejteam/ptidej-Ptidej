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
package padl.analysis.plantUMLGenerator;

import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import java.sql.Timestamp;
public class UMLCodeGenerator {
	public String modelGenerator(String path) throws UnsupportedSourceModelException {
		final ICodeLevelModel model =
			Factory.getInstance().createCodeLevelModel("");		
		String finUMLContent = new String();
		try {
			model
				.create(new CompleteClassFileCreator(
					new String[] {path},
					true));
			final IIdiomLevelModel model2 = (IIdiomLevelModel) new AACRelationshipsAnalysis().invoke(model);
			PlantUMLGenerator PlantUMLGeneratorNew = new PlantUMLGenerator();
			model2.generate(PlantUMLGeneratorNew);
			System.out.println(PlantUMLGeneratorNew.getCode());
			String umlContent = (String) PlantUMLGeneratorNew.getCode();
		//	String [] getNameOfDirectories = path.split("\\");		// might have to refactor this one and the next three lines because this is specific to windows. \\ Not going to write the files for now. 
			String timeStampForUMLTextFile =  new Timestamp(System.currentTimeMillis()).toString().split(" ")[0];
		//	String nameOfGeneratedUML = getNameOfDirectories[getNameOfDirectories.length-1] + "_" + getNameOfDirectories[getNameOfDirectories.length-2] + "_" + timeStampForUMLTextFile;
			System.out.println(timeStampForUMLTextFile);
		//	String filePath = "../PADL Analyses/src/test/resources/plantUMLGeneratedFiles/"+timeStampForUMLTextFile+".txt"; // Plant UML Generated Files are stored here. 
		//	UMLFileSaver.saveToFile(umlContent, filePath);
			finUMLContent =  umlContent;
		}
		catch (CreationException e) {
			e.printStackTrace();
		}
	return finUMLContent;
		
	}
}
