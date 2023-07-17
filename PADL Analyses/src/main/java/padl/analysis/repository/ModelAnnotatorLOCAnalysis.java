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
package padl.analysis.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import javax.swing.JFileChooser;
import org.apache.bcel.Repository;
import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.util.ClassPath;
import org.apache.bcel.util.SyntheticRepository;
import padl.analysis.IAnalysis;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.modelannotatorloc.BCEL2PADLAdaptor;
import padl.analysis.repository.modelannotatorloc.BCELInstructionFinder;
import padl.analysis.repository.modelannotatorloc.InstructionSetter;
import padl.kernel.IAbstractModel;
import util.io.ProxyConsole;

/**
 * @author Stéphane Vaucher
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/03/09
 * @deprecated in favor of the new set of projects "PADL Statement Creator".
 * @since 2008/10/13
 */
public class ModelAnnotatorLOCAnalysis implements IAnalysis {
	private final BCELInstructionFinder instFinder;

	public ModelAnnotatorLOCAnalysis() {
		this.instFinder = new BCELInstructionFinder();
		this.instFinder.setAdaptor(new BCEL2PADLAdaptor());
	}
	public void annotateFromDirs(
		final String[] paths,
		final IAbstractModel anAbstractModel) {

		final ClassPath cp = ClassPath.SYSTEM_CLASS_PATH;
		final org.apache.bcel.util.Repository rep =
			SyntheticRepository.getInstance(cp);
		Repository.setRepository(rep);

		for (int i = 0; i < paths.length; i++) {
			final String path = paths[i];
			this.annotateFromDirs0(path, anAbstractModel);
		}

		// Yann 2006/03/09: Callback.
		// I now visit the model to set the code lines with
		// empty array of Strings but the right count of instructions.
		anAbstractModel.walk(new InstructionSetter(this.instFinder));
	}
	private void annotateFromDirs0(
		final String path,
		final IAbstractModel anAbstractModel) {

		// Yann 2006/03/09: Callback.
		// I make sure we can work on many directories at once.
		final File file = new File(path);
		if (file.isDirectory()) {
			final String[] paths = file.list();
			for (int i = 0; i < paths.length; i++) {
				final String newPath = path + '/' + paths[i];
				this.annotateFromDirs0(newPath, anAbstractModel);
			}
		}
		else if (path.endsWith(".class")) {
			try {
				final FileInputStream fis = new FileInputStream(path);
				final ClassParser parser = new ClassParser(fis, path);
				final JavaClass clazz = parser.parse();
				clazz.accept(this.instFinder);
				fis.close();
			}
			catch (final FileNotFoundException fnfe) {
				// Yann 2009/05/22: Windows...
				// It is possible that a file does not exist if its
				// path is greater than 256 characters... on Windows!

			}
			catch (final IOException ioe) {
				ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final ClassFormatException cfe) {
				ProxyConsole.getInstance().errorOutput().println(
					"This is most likely a bug in BCEL, cf. Google.");
				cfe.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
	}
	public void annotateFromJARs(
		final String[] jarFiles,
		final IAbstractModel anAbstractModel) {

		try {
			// Yann 2006/03/09: Callback.
			// I make sure we can work on many JAR files at once.
			for (int i = 0; i < jarFiles.length; i++) {
				final JarFile jar = new JarFile(jarFiles[i]);

				if (new File(jarFiles[i]).exists()) {
					final ClassPath cp = ClassPath.SYSTEM_CLASS_PATH;
					final org.apache.bcel.util.Repository rep =
						SyntheticRepository.getInstance(cp);
					Repository.setRepository(rep);

					final Enumeration enumeration = jar.entries();
					while (enumeration.hasMoreElements()) {
						final ZipEntry entry =
							(ZipEntry) enumeration.nextElement();

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
				}
				jar.close();
			}

			// Yann 2006/03/09: Callback.
			// I now visit the model to set the code lines with
			// empty array of Strings but the right count of instructions.
			anAbstractModel.walk(new InstructionSetter(this.instFinder));
		}
		catch (final IOException ioe) {
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	public IAbstractModel invoke(final IAbstractModel anAbstractModel)
			throws UnsupportedSourceModelException {

		final JFileChooser fileChooser = new JFileChooser();

		fileChooser
			.setDialogTitle("Please choose the directory where the bytcode of this model is");
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			final File directory = fileChooser.getSelectedFile();
			this.annotateFromDirs(
				new String[] { directory.getAbsolutePath() + '/' },
				anAbstractModel);
		}
		return anAbstractModel;
	}
	public String getName() {
		return "Analysis with LOC";
	}
}
