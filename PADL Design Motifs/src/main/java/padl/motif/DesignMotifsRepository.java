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
package padl.motif;

import java.util.ArrayList;
import java.util.List;

import com.ibm.toad.cfparse.ClassFile;

import util.io.ProxyConsole;
import util.io.SubtypeLoader;
import util.repository.FileAccessException;
import util.repository.IRepository;
import util.repository.impl.FileRepositoryFactory;

public class DesignMotifsRepository implements IRepository {
	private static DesignMotifsRepository UniqueInstance;

	public static DesignMotifsRepository getInstance() {
		if (DesignMotifsRepository.UniqueInstance == null) {
			DesignMotifsRepository.UniqueInstance = new DesignMotifsRepository();
		}

		return DesignMotifsRepository.UniqueInstance;
	}
	//	public static void main(final String args[]) {
	//		final PatternRepository aPatternRepository =
	//			PatternRepository.getCurrentPatternRepository();
	//		OutputManager.getCurrentOutputManager().getNormalOutput().println(
	//			aPatternRepository.toString());
	//	}

	private IDesignMotifModel[] designMotifs;

	private DesignMotifsRepository() {
		ClassFile[] classFiles;
		try {
			classFiles = SubtypeLoader.loadSubtypesFromStream(
					"padl.motif.IDesignMotifModel", FileRepositoryFactory
							.getInstance().getFileRepository(this).getFiles(),
					"padl.motif.repository", ".class");

			// Yann 2003/10/14: Demo!
			// I must catch the AccessControlException
			// thrown when attempting loading patterns
			// from the applet viewer.

			// Yann 2004/07/28: Demo!
			// From now on, using the IFileRepository class,
			// I only need to check if the list is empty.

			// Yann 2004/12/04: FileRepository.
			// The ConstraintViewerPanel from the Applet demo now provides its
			// own and dedicated implementation of IFileRepository to give
			// access to the Composite design motif.

			//	if (classFiles.length == 0) {
			//		// Yann 2002/11/08: Applet!
			//		// I load hard-coded pattern if I cannot access the file-system.
			//		// Yann 2003/12/08: Bad!
			//		// We should be client-independent.
			//		try {
			//			this.listOfPatterns =
			//				new IPatternModel[] {
			//					(IPatternModel) Class
			//						.forName("padl.pattern.repository.Composite")
			//						.getConstructor(null)
			//						.newInstance(null)};
			//		}
			//		catch (final ClassNotFoundException e) {
			//			e.printStackTrace(
			//				OutputManager.getCurrentOutputManager().getErrorOutput());
			//		}
			//		catch (final InstantiationException e) {
			//			e.printStackTrace(
			//				OutputManager.getCurrentOutputManager().getErrorOutput());
			//		}
			//		catch (final IllegalAccessException e) {
			//			e.printStackTrace(
			//				OutputManager.getCurrentOutputManager().getErrorOutput());
			//		}
			//		catch (final InvocationTargetException e) {
			//			e.printStackTrace(
			//				OutputManager.getCurrentOutputManager().getErrorOutput());
			//		}
			//		catch (final NoSuchMethodException e) {
			//			e.printStackTrace(
			//				OutputManager.getCurrentOutputManager().getErrorOutput());
			//		}
			//	}
			//	else {

			final List listOfPatterns = new ArrayList(classFiles.length);
			for (int i = 0; i < classFiles.length; i++) {
				try {
					listOfPatterns.add((IDesignMotifModel) Class
							.forName(classFiles[i].getName()).newInstance());
				}
				catch (final ClassNotFoundException cnfe) {
					cnfe.printStackTrace(
							ProxyConsole.getInstance().errorOutput());
				}
				catch (final InstantiationException ie) {
					ie.printStackTrace(
							ProxyConsole.getInstance().errorOutput());
				}
				catch (final IllegalAccessException iae) {
					iae.printStackTrace(
							ProxyConsole.getInstance().errorOutput());
				}
				catch (final IllegalArgumentException e) {
					e.printStackTrace();
				}
				catch (final SecurityException e) {
					e.printStackTrace();
				}
			}

			this.designMotifs = new IDesignMotifModel[classFiles.length];
			listOfPatterns.toArray(this.designMotifs);
		}
		catch (final FileAccessException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			this.designMotifs = new IDesignMotifModel[0];
		}
	}

	public IDesignMotifModel[] getDesignMotifs() {
		return this.designMotifs;
	}

	//	public void resetAbstractModel(int modelIndex) {
	//		try {
	//			this.listOfPatterns[modelIndex] =
	//				(IDesignMotifModel) this.listOfPatterns[modelIndex]
	//					.getClass()
	//					.getConstructor(null)
	//					.newInstance(null);
	//		}
	//		catch (final Exception e) {
	//			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
	//		}
	//	}
	//	public void resetAbstractModel(String aName) {
	//		for (int x = 0; x < this.listOfPatterns.length; x++)
	//			if (this.listOfPatterns[x].getName().equals(aName)) {
	//				this.resetAbstractModel(x);
	//				return;
	//			}
	//	}
	//	public void resetModels() {
	//		for (int x = 0; x < this.listOfPatterns.length; this
	//			.resetAbstractModel(x++))
	//			;
	//	}
	public String toString() {
		final StringBuffer stringEq = new StringBuffer();
		stringEq.append("Patterns Repository:\n");
		for (int x = 0; x < this.designMotifs.length; x++) {
			stringEq.append("\t");
			stringEq.append(this.designMotifs[x].getName());
			stringEq.append("\n");
		}
		return stringEq.toString();
	}
}
