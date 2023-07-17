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
package padl.statement.creator.classfiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import padl.analysis.IAnalysis;
import padl.analysis.UnsupportedSourceModelException;
import padl.kernel.IAbstractModel;
import ptidej.statement.creator.classfiles.loc.BCEL2PADLAdaptor;
import ptidej.statement.creator.classfiles.loc.BCELLOCFinder;
import ptidej.statement.creator.classfiles.loc.LOCSetter;
import util.io.ProxyConsole;

/**
 * @author Stéphane Vaucher
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/03/09
 */
public class LOCModelAnnotator implements IAnalysis {
	private final String[] fileNames;
	private final BCELLOCFinder instFinder;

	public LOCModelAnnotator(final String[] someFileNames) {
		this.fileNames = someFileNames;
		this.instFinder = new BCELLOCFinder();
		this.instFinder.setAdaptor(new BCEL2PADLAdaptor());
	}
	private void annotateFromFileOrDir(
		final String path,
		final IAbstractModel anAbstractModel) {

		try {
			// Yann 2006/03/09: Callback.
			// I make sure we can work on many directories at once.
			final File file = new File(path);
			if (file.isDirectory()) {
				final String[] paths = file.list();
				for (int i = 0; i < paths.length; i++) {
					final String newPath = path + '/' + paths[i];
					this.annotateFromFileOrDir(newPath, anAbstractModel);
				}
			}
			else if (path.endsWith(".class")) {
				final FileInputStream fis = new FileInputStream(path);
				final ClassParser parser = new ClassParser(fis, path);
				final JavaClass clazz = parser.parse();
				clazz.accept(this.instFinder);
				fis.close();
			}
		}
		catch (final IOException ioe) {
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	private void annotateFromJAR(
		final String jarFile,
		final IAbstractModel anAbstractModel) {

		try {
			if (new File(jarFile).exists()) {
				final JarFile jar = new JarFile(jarFile);
				final Enumeration enumeration = jar.entries();
				while (enumeration.hasMoreElements()) {
					final ZipEntry entry = (ZipEntry) enumeration.nextElement();

					if (!entry.isDirectory()
							&& entry.getName().endsWith(".class")) {

						final InputStream is = jar.getInputStream(entry);
						final ClassParser parser =
							new ClassParser(is, entry.getName());
						final JavaClass clazz = parser.parse();
						clazz.accept(this.instFinder);
						is.close();
					}
				}
				jar.close();
			}
		}
		catch (final IOException ioe) {
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	public String getName() {
		return "Model Annotator with LOC";
	}
	public IAbstractModel invoke(final IAbstractModel anAbstractModel)
			throws UnsupportedSourceModelException {

		for (int i = 0; i < this.fileNames.length; i++) {
			final String fileName = this.fileNames[i];
			if (fileName.endsWith(".jar")) {
				this.annotateFromJAR(fileName, anAbstractModel);
			}
			else {
				this.annotateFromFileOrDir(fileName, anAbstractModel);
			}
			//	else {
			//		Output.getInstance().errorOutput().print("Cannot understand: ");
			//		Output.getInstance().errorOutput().println(fileName);
			//	}
		}
		anAbstractModel.walk(new LOCSetter(this.instFinder));
		return anAbstractModel;
	}
}
