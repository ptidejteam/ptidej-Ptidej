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
package ptidej.viewer.extension;

import java.util.ArrayList;
import java.util.List;
import util.io.ProxyConsole;
import util.io.SubtypeLoader;
import util.multilingual.MultilingualManager;
import util.repository.FileAccessException;
import util.repository.IRepository;
import util.repository.impl.FileRepositoryFactory;
import com.ibm.toad.cfparse.ClassFile;

public class ViewerExtensionsRepository implements IRepository {
	private static ViewerExtensionsRepository UniqueInstance;
	public static ViewerExtensionsRepository getInstance() {
		if (ViewerExtensionsRepository.UniqueInstance == null) {
			ViewerExtensionsRepository.UniqueInstance =
				new ViewerExtensionsRepository();
		}
		return ViewerExtensionsRepository.UniqueInstance;
	}
	//	public static void main(final String args[]) {
	//		final Repository extensionManager =
	//			Repository.getCurrentExtensionManager();
	//		System.out.println(extensionManager);
	//	}

	private IViewerExtension[] viewerExtensions;
	private ViewerExtensionsRepository() {
		// Yann 2003/10/14: Demo!
		// I must catch the AccessControlException
		// thrown when attempting loading extensions
		// from the applet viewer.
		try {
			final ClassFile[] classFiles =
				SubtypeLoader.loadSubtypesFromStream(
					"ptidej.viewer.extension.IViewerExtension",
					FileRepositoryFactory
						.getInstance()
						.getFileRepository(this)
						.getFiles(),
					"ptidej.viewer.extension.repository",
					".class");
			final List listOfExtensions = new ArrayList(classFiles.length);
			for (int i = 0; i < classFiles.length; i++) {
				try {
					listOfExtensions.add((IViewerExtension) Class.forName(
						classFiles[i].getName()).getDeclaredConstructor().newInstance());
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
					ProxyConsole
						.getInstance()
						.errorOutput()
						.println(
							MultilingualManager.getString(
								"LOAD_EXTENSION",
								ViewerExtensionsRepository.class,
								new Object[] { classFiles[i].getName(),
										t.getMessage() }));
				}
			}

			this.viewerExtensions =
				new IViewerExtension[listOfExtensions.size()];
			listOfExtensions.toArray(this.viewerExtensions);
		}
		catch (final FileAccessException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			this.viewerExtensions = new IViewerExtension[0];
		}
	}
	public IViewerExtension[] getViewerExtensions() {
		return this.viewerExtensions;
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("Extension Repository:\n");
		for (int x = 0; x < this.getViewerExtensions().length; x++) {
			buffer.append('\t');
			buffer.append(this.getViewerExtensions()[x].getName());
			buffer.append('\n');
		}
		return buffer.toString();
	}
}
