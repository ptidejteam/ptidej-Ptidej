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
package padl.creator.classfile;

import java.util.jar.JarInputStream;
import padl.kernel.ICodeLevelModelCreator;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/07/30
 * 
 * This idiom-level creator analyses Java class files to create
 * a model of a Java programs, including method invocations
 * between classes.
 */
public class CompleteClassFileCreator extends AbstractClassFileCreator
		implements ICodeLevelModelCreator {
	public CompleteClassFileCreator(final String[] someClassFiles) {

		this(someClassFiles, false);
	}
	public CompleteClassFileCreator(
		final String[] someClassFiles,
		final boolean recurseIntoDirectories) {

		// Yann 2004/07/30: Object-orientation.
		// The flag "storeMethodInvocation" is *not* object-oriented
		// programming. However, it helps in increasing performances
		// by avoiding to recompute many variables to compute then
		// method invocations.
		super(someClassFiles, recurseIntoDirectories, true);
	}
	public CompleteClassFileCreator(final JarInputStream aJARInputStream) {
		super(aJARInputStream, true);
	}
}
