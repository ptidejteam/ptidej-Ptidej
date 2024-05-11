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
package padl.creator.javafile.eclipse.helper;

import padl.creator.javafile.eclipse.CompleteJavaFileCreator;
import padl.creator.javafile.eclipse.visitor.PADLPrinterVisitor;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import util.io.ProxyConsole;

public class Helper {
	// TODO: This class should not exist, should be a test case.
	public static void main(final String[] args) throws ClassNotFoundException {
		//the folder of the source code to analyse well organized like a project
		//final String sourcePathEntry = "./rsc/src/";
		final String sourcePathEntry = "./../PADL Creator JavaFile (Eclipse) Parser/src/";

		//using librairies?
		final String classPathEntry = "";

		final ICodeLevelModel padlModelFromJavaFiles = Factory.getInstance().createCodeLevelModel("");
		try {
			padlModelFromJavaFiles.create(new CompleteJavaFileCreator(sourcePathEntry, classPathEntry));
		} catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		padlModelFromJavaFiles.walk(new PADLPrinterVisitor(false));
	}
}
