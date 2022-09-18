package demo.codeanalyzer.processor;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.processing.AbstractProcessor;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

/**
 * The controller class to initiate verification of java files using custom
 * annotation processor. The files to be verified can be supplied to this class
 * as comma-separated argument.
 * 
 * @author Seema Richard (Seema.Richard@ust-global.com)
 * @author Deepa Sobhana (Deepa.Sobhana@ust-global.com)
 * 
 */
public class CodeAnalyzerController {
	private static final String FILE_DELIMITER = ",";

	/**
	 * Invokes the annotation processor passing the list of file names
	 * 
	 * @param fileNames
	 *            Names of files to be verified
	 */
	public void invokeProcessor(String fileNames) {
		// Gets the Java programming language compiler
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		// Get a new instance of the standard file manager implementation
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				null, null, null);
		// Get the valid source files as a list
		List<File> files = getFilesAsList(fileNames);
		if (files.size() > 0) {
			// Get the list of java file objects
			Iterable<? extends JavaFileObject> compilationUnits1 = fileManager
					.getJavaFileObjectsFromFiles(files);
			// Create the compilation task
			CompilationTask task = compiler.getTask(null, fileManager, null,
					null, null, compilationUnits1);
			// Get the list of annotation processors
			LinkedList<AbstractProcessor> processors = new LinkedList<AbstractProcessor>();
			processors.add(new CodeAnalyzerProcessor());
			task.setProcessors(processors);
			// Perform the compilation task.
			task.call();
			try {
				fileManager.close();
			} catch (IOException e) {
				System.out.println(e.getLocalizedMessage());
			}
		} else {
			System.out.println("No valid source files to process. "
					+ "Extiting from the program");
			System.exit(0);
		}
	}

	/**
	 * This method accepts the comma-separated file names, splits it using the
	 * defined delimiter. A list of valid file objects will be created and
	 * returned to main method.
	 * 
	 * @param fileNames
	 *            Comma-separated file names
	 * @return List of valid source file objects
	 */
	private List<File> getFilesAsList(String fileNames) {
		List<File> files = new LinkedList<File>();
		// split the filenames using the delimiter
		String[] filesArr = fileNames.split(FILE_DELIMITER);
		File sourceFile = null;
		for (String fileName : filesArr) {
			sourceFile = new File(fileName);
			if (sourceFile != null && sourceFile.exists()) {
				files.add(sourceFile);
			} else {
				System.out.println(fileName + " is not a valid file. "
						+ "Ignoring the file ");
			}
		}
		return files;
	}
}
