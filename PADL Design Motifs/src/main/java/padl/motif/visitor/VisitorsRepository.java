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
package padl.motif.visitor;

import java.util.ArrayList;
import java.util.List;

import com.ibm.toad.cfparse.ClassFile;

import padl.visitor.IGenerator;
import padl.visitor.IWalker;
import util.io.ProxyConsole;
import util.io.SubtypeLoader;
import util.multilingual.MultilingualManager;
import util.repository.FileAccessException;
import util.repository.IRepository;
import util.repository.impl.FileRepositoryFactory;

public class VisitorsRepository implements IRepository {
	private static VisitorsRepository UniqueInstance;

	public static VisitorsRepository getInstance() {
		if (VisitorsRepository.UniqueInstance == null) {
			VisitorsRepository.UniqueInstance = new VisitorsRepository();
		}
		return VisitorsRepository.UniqueInstance;
	}
	//	public static void main(final String args[]) {
	//		final Repository extensionManager =
	//			Repository.getCurrentExtensionManager();
	//		System.out.println(extensionManager);
	//	}

	private IMotifGenerator[] generators;
	private IMotifWalker[] walkers;

	private VisitorsRepository() {
		// Yann 2003/10/14: Demo!
		// I must catch the AccessControlException
		// thrown when attempting loading extensions
		// from the applet viewer.
		try {
			final ClassFile[] classFilesGenerators = SubtypeLoader
					.loadSubtypesFromStream(
							"padl.motif.visitor.IMotifGenerator",
							FileRepositoryFactory.getInstance()
									.getFileRepository(this)
									.getFiles("padl/motif/visitor/repository/",
											".class"),
							"padl.motif.visitor.repository", ".class");
			final List listOfGenerators = new ArrayList(
					classFilesGenerators.length);
			for (int i = 0; i < classFilesGenerators.length; i++) {
				try {
					listOfGenerators.add((IGenerator) Class
							.forName(classFilesGenerators[i].getName())
							.newInstance());
				}
				// Yann 2003/10/07: Protection!
				// I want to make sure that any problem in this
				// section won't break the program...
				//	catch (final ClassNotFoundException cnfe) {
				//		// cnfe.printStackTrace();
				//	}
				//	catch (final InstantiationException ie) {
				//		// ie.printStackTrace();
				//	}
				//	catch (final IllegalAccessException iae) {
				//		// iae.printStackTrace();
				//	}
				//	catch (final NoClassDefFoundError ncdfe) {
				catch (final Throwable t) {
					ProxyConsole.getInstance().errorOutput()
							.println(MultilingualManager.getString(
									"LOAD_EXTENSION", VisitorsRepository.class,
									new Object[] {
											classFilesGenerators[i].getName(),
											t.getMessage() }));
				}
			}

			this.generators = new IMotifGenerator[listOfGenerators.size()];
			listOfGenerators.toArray(this.generators);

			final ClassFile[] classFilesWalkers = SubtypeLoader
					.loadSubtypesFromStream("padl.motif.visitor.IMotifWalker",
							FileRepositoryFactory.getInstance()
									.getFileRepository(this)
									.getFiles("padl/motif/visitor/repository/",
											".class"),
							"padl.motif.visitor.repository", ".class");
			final List listOfWalkers = new ArrayList(classFilesWalkers.length);
			for (int i = 0; i < classFilesWalkers.length; i++) {
				try {
					listOfWalkers.add((IWalker) Class
							.forName(classFilesWalkers[i].getName())
							.newInstance());
				}
				// Yann 2003/10/07: Protection!
				// I want to make sure that any problem in this
				// section won't break the program...
				//	catch (final ClassNotFoundException cnfe) {
				//		// cnfe.printStackTrace();
				//	}
				//	catch (final InstantiationException ie) {
				//		// ie.printStackTrace();
				//	}
				//	catch (final IllegalAccessException iae) {
				//		// iae.printStackTrace();
				//	}
				//	catch (final NoClassDefFoundError ncdfe) {
				catch (final Throwable t) {
					ProxyConsole.getInstance().errorOutput()
							.println(MultilingualManager.getString(
									"LOAD_EXTENSION", VisitorsRepository.class,
									new Object[] {
											classFilesWalkers[i].getName(),
											t.getMessage() }));
				}
			}

			this.walkers = new IMotifWalker[listOfWalkers.size()];
			listOfWalkers.toArray(this.walkers);
		}
		catch (final FileAccessException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			this.walkers = new IMotifWalker[0];
		}
	}

	public IMotifGenerator[] getGenerators() {
		return this.generators;
	}

	public IMotifWalker[] getWalkers() {
		return this.walkers;
	}

	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("Visitor Repository:\n");
		for (int x = 0; x < this.getGenerators().length; x++) {
			buffer.append('\t');
			buffer.append(this.getGenerators()[x].getName());
			buffer.append('\n');
		}
		return buffer.toString();
	}
}
