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
package ptidej.viewer.extension.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import org.apache.bcel.Repository;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.util.ClassPath;
import org.apache.bcel.util.SyntheticRepository;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.IAbstractModel;
import ptidej.viewer.IRepresentation;
import ptidej.viewer.event.SourceAndGraphModelEvent;
import ptidej.viewer.extension.IViewerExtension;
import ptidej.viewer.extension.repository.conditionals.BCEL2PADLAdaptor;
import ptidej.viewer.extension.repository.conditionals.BCELInstructionFinder;
import ptidej.viewer.extension.repository.conditionals.InstructionSetter;
import util.io.ProxyConsole;

/**
 * @author Stéphane Vaucher
 * @author Yann-Gaël Guéhéneuc
 * @since  2008/10/01
 * 
 * I built this annotator as a UI Viewer Extension but it 
 * could also be akin to a PADL Creator!
 * TODO: Make it similar to a Creator.
 */
public final class ConditionalModelAnnotator implements IViewerExtension {
	private final BCELInstructionFinder instFinder;

	public ConditionalModelAnnotator() {
		this.instFinder = new BCELInstructionFinder();
		this.instFinder.setAdaptor(new BCEL2PADLAdaptor());
	}
	private void annotateFromDir(
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
					this.annotateFromDir(newPath, anAbstractModel);
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
		final IAbstractLevelModel anAbstractLevelModel) {

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
		return "Model Annotator with Conditionals";
	}
	public void invoke(final IRepresentation aRepresentation) {
		final IAbstractLevelModel anAbstractLevelModel =
			(IAbstractLevelModel) aRepresentation.getSourceModel();
		final ClassPath cp = ClassPath.SYSTEM_CLASS_PATH;
		final org.apache.bcel.util.Repository rep =
			SyntheticRepository.getInstance(cp);
		Repository.setRepository(rep);

		final Iterator files =
			aRepresentation
				.getSourceFiles(IRepresentation.TYPE_JAVA_CLASSFILES)
				.iterator();
		while (files.hasNext()) {
			final String file = (String) files.next();
			if (file.endsWith(".jar")) {
				this.annotateFromJAR(file, anAbstractLevelModel);
			}
			else if (file.endsWith("/")) {
				this.annotateFromDir(file, anAbstractLevelModel);
			}
		}

		//	this.annotateFromDirs(
		//		new String[] { directory.getAbsolutePath() + '/' },
		//		anAbstractModel);
		//	return anAbstractModel;

		// Yann 2006/03/09: Callback.
		// I now visit the model to set the code lines with
		// empty array of Strings but the right count of instructions.
		anAbstractLevelModel.walk(new InstructionSetter(this.instFinder));
	}
	public boolean isVisible() {
		return false;
	}
	public void setVisible(final boolean aVisibility) {
	}
	public void sourceModelAvailable(
		final SourceAndGraphModelEvent aSourceModelEvent) {
	}
	public void sourceModelChanged(
		final SourceAndGraphModelEvent aSourceModelEvent) {
	}
	public void sourceModelUnavailable() {
	}
}
