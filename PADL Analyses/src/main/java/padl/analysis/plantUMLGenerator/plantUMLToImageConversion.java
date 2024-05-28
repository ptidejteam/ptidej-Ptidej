package padl.analysis.plantUMLGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystemException;

import net.sourceforge.plantuml.SourceStringReader;
import padl.analysis.UnsupportedSourceModelException;
//import padl.analysis.UnsupportedSourceModelException;

public class plantUMLToImageConversion {
	public boolean plantUMLImageGenerator(File path){
	UMLCodeGenerator umlGen = new UMLCodeGenerator();
	final String finalPath =
			util.io.Files.normalizePath(path.getAbsolutePath())
					+ File.separatorChar;
	// ../DeMIMA/target/test-classes/ptidej/example/composite2/
	// ../PADL/target/classes/padl/event/
	// ../PADL/target/classes/padl/kernel/impl/
	// ../All Ptidej Tests/ptidej/Examples/ptidej.example.composite2.ptidej
	OutputStream png = null;
	boolean imageGeneratedFlag = false;
	try {
		png = new FileOutputStream("../OutputUML.png");
	} catch (FileNotFoundException e) {
		e.printStackTrace();
		imageGeneratedFlag = false;
	}
	try {
		String umlCode = umlGen.modelGenerator(finalPath);
		SourceStringReader reader = new SourceStringReader(umlCode);
		String desc = reader.outputImage(png).getDescription();
		if(desc.equals("Error")) {
			String errorMessage = "Unable to process UMLCode";
			throw new FileSystemException(errorMessage);
		}
		System.out.println("plantUML Image generated successfully.");
		//System.out.println(desc);
		imageGeneratedFlag = true;

	} catch (UnsupportedSourceModelException e) {
		imageGeneratedFlag = false;
		e.printStackTrace();
	}
	catch (IOException e) {
		imageGeneratedFlag = false;
		e.printStackTrace();
	}
	return imageGeneratedFlag;
	}
	
}
