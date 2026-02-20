/**
 * @author Mathieu Lemoine
 * @created 2009-03-02
 * 
 * Licensed under 3-clause BSD License:
 * Copyright © 2009, Mathieu Lemoine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *  * Neither the name of Mathieu Lemoine nor the
 *    names of contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY Mathieu Lemoine ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Mathieu Lemoine BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package padl.creator.javafile.javac;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;

import jct.kernel.IJCTRootNode;
import jct.tools.JCTCommentAttachor;
import jct.tools.JCTCommentExtractor;
import jct.tools.JCTCreatorFromSourceCode;
import padl.creator.javafile.javac.util.JCTtoPADLTranslator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.exception.CreationException;
import util.io.ProxyConsole;
import util.lang.OpenedModulesGuard;

/**
 * This class is used to create a PADL Model, from a set of Java Source Files, using JCT.
 *
 * @author Mathieu Lemoine
 * @author Yann-Gaël Guéhéneuc
 */
public class JavaFileCreator implements ICodeLevelModelCreator {
	private DiagnosticListener<? super JavaFileObject> diag;
	private Iterable<String> options;
	private File[] files;

	//	/**
	//	 * @param files List of Path to each of the java source file want to put in the PADL Model.
	//	 * @param diag DiagnosticListener used to report error during compilation pass.
	//	 * @param options Map of options, options flags (without the leading '-') as keys, options values as values. if an option flag does not have or need a value, just set the value to null
	//	 */
	//	public JavaFileCreator(
	//		final DiagnosticListener<? super JavaFileObject> diag,
	//		final Map<String, String> options,
	//		final File... files) {
	//
	//		this.diag = diag;
	//		this.files = files;
	//		final List<String> myOptions = new LinkedList<String>();
	//
	//		for (final Map.Entry<String, String> e : options.entrySet()) {
	//			myOptions.add("-" + e.getKey());
	//			if (null != e.getValue())
	//				myOptions.add(e.getValue());
	//		}
	//		this.options = Collections.unmodifiableList(myOptions);
	//	}

	//	/**
	//	 * @param files List of Path to each of the java source file want to put in the PADL Model.
	//	 * @param diag DiagnosticListener used to report error during compilation pass.
	//	 * @param options Options to pass to JavaC, splited as in a command line.
	//	 */
	//	public JavaFileCreator(
	//		final DiagnosticListener<? super JavaFileObject> diag,
	//		final List<String> options,
	//		final File... files) {
	//
	//		this.diag = diag;
	//		this.files = files;
	//		this.options = options;
	//	}

	/**
	 * @param aSourcePath List to the root of a path containing Java source files to put in the PADL Model.
	 * @param diag DiagnosticListener used to report error during compilation pass.
	 * @param someOptions Options to pass to JavaC, splited as in a command line.
	 */
	public JavaFileCreator(final List<String> someOptions,
			final String aSourcePath, final String[] someFilesInThePath) {

		this.initialise(someOptions, aSourcePath, someFilesInThePath);
	}

	private void initialise(final List<String> options,
			final String aSourcePath, final String[] someFilesInThePath) {

		// Yann 24/12/03: Recursivity
		// To simplify the use of this creator, if the array
		// someFilesInThePath is empty, then I look for Java
		// files into aSourcePath, else I use these files.
		final String[] filePaths;
		if (someFilesInThePath.length == 0) {
			final List<String> filePathsList = new ArrayList<>();
			try {
				Files.walkFileTree(Path.of(aSourcePath),
						new SimpleFileVisitor<Path>() {
							@Override
							public FileVisitResult visitFile(final Path file,
									final BasicFileAttributes attrs)
									throws IOException {

								Objects.requireNonNull(file);
								Objects.requireNonNull(attrs);

								filePathsList.add(file.toString());

								return FileVisitResult.CONTINUE;
							}
						});
			}
			catch (final IOException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			filePaths = filePathsList.toArray(new String[0]);
		}
		else {
			filePaths = someFilesInThePath;
		}

		this.options = options;
		final List<File> files = new ArrayList<File>();
		for (int i = 0; i < filePaths.length; i++) {
			if (filePaths[i].endsWith(".java")) {
				files.add(new File(filePaths[i]));
			}
		}
		this.files = files.toArray(new File[0]);

		// Yann 24/04/20: Checking for opened modules
		OpenedModulesGuard.getInstance().addOpenedModuleCheck("jdk.compiler",
				"com.sun.tools.javac.api.JavacTool");
		OpenedModulesGuard.getInstance().addOpenedModuleCheck("jdk.compiler",
				"com.sun.tools.javac.code.Symbol");
		OpenedModulesGuard.getInstance().addOpenedModuleCheck("jdk.compiler",
				"com.sun.tools.javac.model.JavacElements");
		OpenedModulesGuard.getInstance().addOpenedModuleCheck("jdk.compiler",
				"com.sun.tools.javac.tree.JCTree");
		OpenedModulesGuard.getInstance().addOpenedModuleCheck("jdk.compiler",
				"com.sun.tools.javac.util.Pair");

		final Optional<String> check = OpenedModulesGuard.getInstance()
				.checkOpenedModules();
		if (check.isPresent()) {
			throw new RuntimeException(check.get());
		}
	}

	private void initialise(String aSourcePath, String[] someFilesInThePath) {
		// From jct.test.common.JCTConstant.OPTIONS
		this.initialise(
				Arrays.asList(new String[] { "-classpath",
						System.getProperty("java.class.path", ".") + ":"
								+ aSourcePath,
						"-d", "tmp/" }),
				aSourcePath, someFilesInThePath);
	}

	public JavaFileCreator(final String aSourcePath) {
		final List<String> listOfFilenames = JavaFileCreator.castList(
				String.class,
				util.io.Files.getRecursivelyFilenamesFromDirectory(aSourcePath,
						new FilenameFilter() {
							public boolean accept(final File aDirectory,
									final String aFilename) {

								return aFilename.endsWith(".java");
							}
						}));
		final String[] arrayOfFilenames = new String[listOfFilenames.size()];
		listOfFilenames.toArray(arrayOfFilenames);

		this.initialise(aSourcePath, arrayOfFilenames);
	}

	public JavaFileCreator(final String aSourcePath,
			final String[] someFilesInThePath) {

		this.initialise(aSourcePath, someFilesInThePath);
	}

	private static <T> List<T> castList(Class<? extends T> clazz,
			Collection<?> c) {
		List<T> r = new ArrayList<T>(c.size());
		for (Object o : c)
			r.add(clazz.cast(o));
		return r;
	}

	public void create(final ICodeLevelModel model) throws CreationException {
		final JCTtoPADLTranslator creator = new JCTtoPADLTranslator(model);
		try {
			final IJCTRootNode rootNode = JCTCreatorFromSourceCode.createJCT("",
					true, this.diag, this.options, this.files);
			// TODO: Crashed with Juzzle!
			rootNode.accept(new JCTCommentExtractor(), null);
			rootNode.accept(new JCTCommentAttachor(), null);
			rootNode.accept(creator, null);

			//	final Collection<IJCTCompilationUnit> cus =
			//		((IJCTContainer) rootNode).getAllEnclosedElements(
			//			JCTKind.COMPILATION_UNIT,
			//			IJCTCompilationUnit.class,
			//			true);
			//
			//	IJCTCompilationUnit cu = null;
			//	for (final IJCTCompilationUnit it : cus) {
			//		if ("Comments.java".equals(it.getSourceFile().getName())) {
			//			cu = it;
			//			// Yann 2009/07/22: Clean up!
			//			// Should not be necessary, the loop should be rewritten cleanly.
			//			// In particular, the "cu" variable should be final.
			//			break;
			//		}
			//	}
			//	if (null == cu) {
			//		throw new IllegalStateException(
			//			"Cannot find tested compilation unit \"Comments.java\".");
			//	}
			//	final IJCTClass c = cu.getClazzs().iterator().next();
			//	System.out.println(c);
		}
		catch (final IOException e) {
			throw new CreationException(
					"An I/O error occured during the creation.");
		}
	}

	public void setDiagnosticListener(
			DiagnosticListener<? super JavaFileObject> diag) {

		this.diag = diag;
	}
}
