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
package ptidej.ui.layout;

import java.util.ArrayList;
import java.util.List;
import util.io.ProxyConsole;
import util.io.SubtypeLoader;
import util.multilingual.MultilingualManager;
import util.repository.FileAccessException;
import util.repository.IRepository;
import util.repository.impl.FileRepositoryFactory;
import com.ibm.toad.cfparse.ClassFile;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/04/22
 */
public class UILayoutsRepository implements IRepository {
	private static UILayoutsRepository UniqueInstance;
	public static UILayoutsRepository getInstance() {
		if (UILayoutsRepository.UniqueInstance == null) {
			UILayoutsRepository.UniqueInstance = new UILayoutsRepository();
		}
		return UILayoutsRepository.UniqueInstance;
	}

	private IUILayout[] uiLayouts;
	private UILayoutsRepository() {
		// Yann 2003/10/14: Demo!
		// I must catch the AccessControlException
		// thrown when attempting loading analyses
		// from the applet viewer.
		try {
			final ClassFile[] classFiles =
				SubtypeLoader.loadSubtypesFromStream(
					"ptidej.ui.layout.IUILayout",
					FileRepositoryFactory
						.getInstance()
						.getFileRepository(this)
						.getFiles(),
					"ptidej.ui.layout.repository",
					".class");
			final List listOfAnalyses = new ArrayList(classFiles.length);
			for (int i = 0; i < classFiles.length; i++) {
				try {
					listOfAnalyses.add((IUILayout) Class.forName(
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
					System.err.println(MultilingualManager
						.getString(
							"Err_LOAD_GRAPH_LAYOUTS",
							UILayoutsRepository.class,
							new Object[] { classFiles[i].getName(),
									t.getMessage() }));
				}
			}

			this.uiLayouts = new IUILayout[listOfAnalyses.size()];
			listOfAnalyses.toArray(this.uiLayouts);
		}
		catch (final FileAccessException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			this.uiLayouts = new IUILayout[0];
		}
	}
	public IUILayout[] getUILayouts() {
		return this.uiLayouts;
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("Graph Layout Repository:\n");
		for (int x = 0; x < this.getUILayouts().length; x++) {
			buffer.append('\t');
			buffer.append(this.getUILayouts()[x].getName());
			buffer.append('\n');
		}
		return buffer.toString();
	}

}
