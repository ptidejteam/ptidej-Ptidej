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
/* (c) Copyright 2011 and following years, Aminata SABANÉ,
 * 
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the author, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package padl.creator.javafile.eclipse.test.others;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.javafile.eclipse.test.util.Utils;
import padl.kernel.ICodeLevelModel;
import util.io.ProxyConsole;
import util.io.ProxyDisk;

public class EclipseSnapshotTest extends TestCase {
	public EclipseSnapshotTest(final String aName) {
		super(aName);
	}
	public void testSourcePathIndirect() {
		final long start = System.currentTimeMillis();
		final String javaFilesFolderPath = "F:/eclipse_12-15-2009/";
		final String classPathEntry = "";

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("Beginning of the first pass");
		final Writer errorWriter =
			new BufferedWriter(ProxyDisk.getInstance().fileTempOutput(
				"errorOutput.txt"));
		final Writer normalWriter =
			new BufferedWriter(ProxyDisk.getInstance().fileTempOutput(
				"normalOutputInfos.txt"));

		ProxyConsole.getInstance().setErrorOutput(errorWriter);
		ProxyConsole.getInstance().setNormalOutput(normalWriter);

		final ICodeLevelModel padlModelFromJavaFiles =
			Utils.createLightJavaFilesPadlModel(
				"",
				javaFilesFolderPath,
				classPathEntry);

		final long end = System.currentTimeMillis();
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println(
				" nombre de paquages et de paquets"
						+ padlModelFromJavaFiles.getNumberOfConstituents()
						+ " time : " + (end - start));
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println(
				" nombre de paquages et de paquets"
						+ padlModelFromJavaFiles.getNumberOfConstituents()
						+ " time : " + (end - start));
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println(
				" nombre de paquages et de paquets"
						+ padlModelFromJavaFiles.getNumberOfConstituents()
						+ " time : " + (end - start));

		try {
			errorWriter.close();
			normalWriter.close();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}

		Assert.assertTrue(true);
	}
}
