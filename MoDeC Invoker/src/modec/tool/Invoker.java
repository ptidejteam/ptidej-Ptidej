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
package modec.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import org.apache.bcel.Repository;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.util.ClassPath;
import org.apache.bcel.util.SyntheticRepository;
import org.apache.commons.lang.ArrayUtils;
import util.io.OutputMonitor;

/**
 * @author Janice
 * @author Yann
 * @since 2007/10/24
 */
public class Invoker {
	private static final String PATH_SEPARATOR = "path.separator";
	private static final String JAVA_CLASS_PATH = "java.class.path";

	// "java.class.path";
	private static final String USER_DIR = "user.dir";
	private static final String SEPARATOR = System
		.getProperty(Invoker.PATH_SEPARATOR);

	public static void main(final String[] args) {

		// Invoker.invokeMainClass("../MoDeC Bytecode Instrumentation Tests/input files/Evaluation_SearchManager_Builder_GetSQLStatement.txt");
		// Invoker.invokeMainClass("../MoDeC Bytecode Instrumentation Tests/input files/Evaluation_JHotDraw_Visitor_CutAndPasteRectangle.txt");
		// Invoker.invokeMainClass("../MoDeC Bytecode Instrumentation Tests/input files/Evaluation_QuickUML_Command_ResizeADiagram.txt");
		// Invoker.invokeMainClass("../MoDeC Bytecode Instrumentation Tests/input files/Evaluation_SupervisorView_Observer_SelectDepartment.txt");
		// Invoker
		// .invokeMainClass("../MoDeC Bytecode Instrumentation Tests/input files/Evaluation of Rhino.txt");
		// Invoker
		// .invokeMainClass("../MoDeC Bytecode Instrumentation Tests/Input Files/Evaluation of JHotDraw v5.4b1.txt");
		// Invoker
		// .invokeMainClass("../MoDeC Bytecode Instrumentation Tests/Input Files/Evaluation of JHotDraw v5.1.txt");
		//		 Invoker
		//		 .invkeMainClass("/Users/Neni/Documents/Research/2013-LabalingTraceSegm/eclipseWS/MoDeCBytecodeInstrumentationTests/InputFiles/Evaluation of JEdit v4.2.txt",0);
		//		Invoker
		//				.invokeMainClass("../MoDeCBytecodeInstrumentationTests/InputFiles/EvaluationofArgoUMLv0.19.8.txt");
		// Invoker
		// .invokeMainClass("../MoDeC Bytecode Instrumentation Tests/Input Files/Evaluation of ArgoUML v0.19.8 (without tracing the UI).txt");
		// Invoker
		// .invokeMainClass("../MoDeC Bytecode Instrumentation Tests/Input Files/Evaluation of ArgoUML v0.19.8 (without tracing the Cognitive, Configuration, Events, UI).txt");
		// Invoker
		// .invokeMainClass("../MoDeC Bytecode Instrumentation Tests/Input Files/Evaluation of JFreeChart v1.0.13.txt");
		// Invoker
		// .invokeMainClass("../MoDeC Bytecode Instrumentation Tests/Input Files/JGAP-3.4.4");
		// 1) MARS : Screen magnifier, 2) MARS : Keyboard and Display simulator
		//		 Invoker
		//		 .invkeMainClass("../MoDeCBytecodeInstrumentationTests/InputFiles/Mars-4.3",0);
		// 3)  Micro mouse simulator
		//		 Invoker
		//		 .invkeMainClass("../MoDeCBytecodeInstrumentationTests/InputFiles/MicroMouse",0);
		// 4) Kohonen visualizer
		//		Invoker
		//		 .invkeMainClass("../MoDeCBytecodeInstrumentationTests/InputFiles/Neurons",0);
		//		Invoker
		//		 .invkeMainClass("../MoDeCBytecodeInstrumentationTests/InputFiles/JGAP-3.4.4-MinimumChange");
		//		Invoker
		//		 .invkeMainClass("../MoDeCBytecodeInstrumentationTests/InputFiles/JGAP-3.4.4-Fibonacci",0);
		//		Invoker
		//		 .invkeMainClass("../MoDeCBytecodeInstrumentationTests/InputFiles/Javaclient2",0);
		//		Invoker
		//		 .invkeMainClass("../MoDeCBytecodeInstrumentationTests/InputFiles/wro4j",0);
		Invoker.invokeMainClass(
			"../MoDeCBytecodeInstrumentationTests/InputFiles/Pooka",
			0);

	}

	public static void invokeMainClass(final String filename, int nbParams) {
		try {
			final FileReader fr = new FileReader(filename);
			final BufferedReader inFile = new BufferedReader(fr);

			// final String sourcePathname = inFile.readLine().trim();
			inFile.readLine();
			final String targetPathname = inFile.readLine().trim();
			final String extraJARsPath = inFile.readLine().trim();
			final String traceFilename = inFile.readLine().trim();
			final String[] classToExecuteWithParams =
				inFile.readLine().trim().split(" ");

			// I add to the CLASSPATH the path to the instrumented
			// classfiles to make sure that the Repository can find
			// them when need. I could have used a ClassLoadedRepository
			// instead of manipulating the CLASSPATH. Future work.
			final File absolutePathToInstrumentedFiles =
				new File(targetPathname);

			// System.setProperty(Invoker.JAVA_CLASS_PATH,
			// System.getProperty(Invoker.JAVA_CLASS_PATH).replaceAll(":",
			// ";"));

			final StringBuffer buffer = new StringBuffer();
			buffer.append(System.getProperty(Invoker.JAVA_CLASS_PATH));
			buffer.append(SEPARATOR);
			buffer.append(absolutePathToInstrumentedFiles.getAbsolutePath());
			final File extraJARsPathFile = new File(extraJARsPath);
			if (extraJARsPathFile.isDirectory()) {
				final String[] listOfFiles = extraJARsPathFile.list();
				for (int i = 0; i < listOfFiles.length; i++) {
					final String file = listOfFiles[i];
					if (file.endsWith(".jar")) {
						buffer.append(SEPARATOR);
						buffer.append(extraJARsPath);
						// buffer.append("\""+extraJARsPath);
						buffer.append(file);
					}
				}
			}

			System.setProperty(Invoker.JAVA_CLASS_PATH, buffer.toString());
			System.setProperty(
				Invoker.USER_DIR,
				absolutePathToInstrumentedFiles.getAbsolutePath());
			Repository.setRepository(SyntheticRepository
				.getInstance(ClassPath.SYSTEM_CLASS_PATH));
			Repository.clearCache();

			String classname;
			while ((classname = inFile.readLine()) != null) {
				// Yann 2007/11/11: .class!
				// We cannot do ".replaceAll(".class", "")"
				// because a class could be called:
				// "org.mozilla.classfile..."!
				// classname = classname.trim().replace('/', '.');
				classname = targetPathname + classname;
				try {
					Repository.addClass(new ClassParser(classname).parse());
					System.out.print(classname);
					System.out.println(" added.");
				}
				catch (final NullPointerException e) {
					System.err.println("Error occured while parsing file: "
							+ classname);
					e.printStackTrace();
				}
			}

			inFile.close();

			// String classFullName =
			// (targetPathname
			// + Repository.lookupClass(classToExecute).getClassName())
			// .replace('\\', '/')
			// .replace('.', '/');

			System.out.println("buffer:" + buffer);

			System.out.print("Invoking class ");
			System.out.print(targetPathname);
			System.out.print(classToExecuteWithParams[0]);
			if (nbParams > 0) {
				System.out.print("With parameters: ");
				for (int i = 1; i <= nbParams; i++) {
					System.out.println(classToExecuteWithParams[i] + " ");
				}

			}

			System.out.println('.');
			System.out.print("Generating trace in");
			System.out.print(targetPathname);
			System.out.print(traceFilename);
			System.out.println('.');
			new Thread(new Runnable() {
				public void run() {
					final UI ui = new UI(targetPathname + traceFilename);
					ui.setVisible(true);
				}
			}).run();

			// final JavaWrapper jw = new JavaWrapper(new ClassLoader());
			// // For Rhino...
			// // Repository.lookupClass(
			// // "org.mozilla.javascript.EvaluatorException");
			// // jw.runMain(
			// // Repository
			// // .lookupClass("org.mozilla.javascript.tools.shell.Main")
			// // .getClassName(),
			// // argListForInvokedMain);
			// jw.runMain(
			// Repository.lookupClass(classToExecute).getClassName(),
			// new String[] { traceFilename });
			//			final String commandLine = "-classpath \""
			//					+ System.getProperty(Invoker.JAVA_CLASS_PATH) + "\" "
			//					;
			final String[] first =
				new String[] { "java", "-classpath",
						System.getProperty(Invoker.JAVA_CLASS_PATH),
						classToExecuteWithParams[0] };
			final String[] both;
			if (nbParams > 0) {
				String[] second =
					(String[]) Arrays.copyOfRange(
						classToExecuteWithParams,
						1,
						classToExecuteWithParams.length);
				both = (String[]) ArrayUtils.addAll(first, second);
			}
			else {
				both = first;
			}
			final ProcessBuilder pb = new ProcessBuilder(both);
			pb.directory(absolutePathToInstrumentedFiles);
			final Process process = pb.start();
			final OutputMonitor monitor =
				new OutputMonitor(
					"Monitor",
					"",
					process.getErrorStream(),
					System.out);
			monitor.start();
			process.waitFor();
			System.out.println(process.exitValue());
		}
		catch (final InterruptedException e) {
			e.printStackTrace();
		}
		catch (final FileNotFoundException e) {
			System.err
				.println("Error occured while opening file (file not found) in: "
						+ filename);
			e.printStackTrace();
		}
		catch (final IOException e) {
			System.err.println("Error occured while opening file: " + filename);
			e.printStackTrace();
		}
		// catch (final ClassNotFoundException e) {
		// System.err
		// .println("Error occured while running program (class not found): "
		// + filename);
		// e.printStackTrace();
		// }
		catch (final NoClassDefFoundError e) {
			System.err
				.println("Error occured while running program (class not found): "
						+ filename);
			e.printStackTrace();
		}
	}
}
