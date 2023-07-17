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
package padl.micropattern;

import java.util.ArrayList;
import java.util.List;

import com.ibm.toad.cfparse.ClassFile;

import util.io.ProxyConsole;
import util.io.SubtypeLoader;
import util.multilingual.MultilingualManager;
import util.repository.FileAccessException;
import util.repository.IRepository;
import util.repository.impl.FileRepositoryFactory;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/02/13
 */
public class MicroPatternDetectionsRepository implements IRepository {
	private static MicroPatternDetectionsRepository UniqueInstance;

	public static MicroPatternDetectionsRepository getInstance() {
		if (MicroPatternDetectionsRepository.UniqueInstance == null) {
			MicroPatternDetectionsRepository.UniqueInstance =
				new MicroPatternDetectionsRepository();
		}
		return MicroPatternDetectionsRepository.UniqueInstance;
	}

	private IMicroPatternDetection[] detections;
	private MicroPatternDetectionsRepository() {
	}
	public IMicroPatternDetection[] getMicroPatternDetections() {
		if (this.detections == null) {
			// Yann 2003/10/14: Demo!
			// I must catch the AccessControlException
			// thrown when attempting loading analyses
			// from the applet viewer.
			try {
				final ClassFile[] classFiles =
					SubtypeLoader.loadSubtypesFromStream(
						"padl.micropattern.IMicroPatternDetection",
						FileRepositoryFactory
							.getInstance()
							.getFileRepository(this)
							.getFiles(),
						"padl.micropattern.repository",
						".class");
				final List listOfDetections = new ArrayList(classFiles.length);
				for (int i = 0; i < classFiles.length; i++) {
					try {
						listOfDetections.add((IMicroPatternDetection) Class
							.forName(classFiles[i].getName())
							.getDeclaredConstructor().newInstance());
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
						System.err.println(MultilingualManager.getString(
							"LOAD_ANALYSIS",
							MicroPatternDetectionsRepository.class,
							new Object[] { classFiles[i].getName(),
									t.getMessage() }));
					}
				}

				this.detections =
					new IMicroPatternDetection[listOfDetections.size()];
				listOfDetections.toArray(this.detections);
			}
			catch (final FileAccessException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				this.detections = new IMicroPatternDetection[0];
			}
		}
		return this.detections;
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("Micro-pattern Detection Repository:\n");
		for (int x = 0; x < this.getMicroPatternDetections().length; x++) {
			buffer.append('\t');
			buffer.append(this.getMicroPatternDetections()[x].getName());
			buffer.append('\n');
		}
		return buffer.toString();
	}
}
