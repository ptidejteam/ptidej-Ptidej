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
/* (c) Copyright 2009 and following years, Aminata SABANE,
 * Ecole Polytechnique de MontrÌ©al.
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
package padl.creator.javafile.eclipse.test.basic;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.javafile.eclipse.test.util.Utils;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IGhost;

public class WithAndWithoutSourcePathTest extends TestCase {

	public WithAndWithoutSourcePathTest(final String aName) {
		super(aName);
	}

	
	public void testWithSourcePath(){
		
		final String classPathEntry = "";
		final String sourceCodePath =
			"data/JHotDraw/src/";

		final String[] listOfFiles =
			new String[] {
					"data/JHotDraw/src/ch/ifa/draw/applet/DrawApplet.java"
					};

		final ICodeLevelModel model =
			Utils.createCompleteJavaFilesPadlModel(
				"",
				sourceCodePath,
				classPathEntry,
				listOfFiles);

		IClass clazz=(IClass) model.getTopLevelEntityFromID("ch.ifa.draw.applet.DrawApplet");
		Assert.assertNotNull(clazz);
		//Interface implemented by clazz
		IGhost interfaz=(IGhost) model.getTopLevelEntityFromID("ch.ifa.draw.framework.DrawingEditor".toCharArray());
		Assert.assertNotNull(interfaz);
	}
	
	
	
	public void testWithoutSourcePath(){
		
		final String classPathEntry = "";
		final String sourceCodePath =
			"";

		final String[] listOfFiles =
			new String[] {
					"data/JHotDraw/src/ch/ifa/draw/applet/DrawApplet.java"
					};

		final ICodeLevelModel model =
			Utils.createCompleteJavaFilesPadlModel(
				"",
				sourceCodePath,
				classPathEntry,
				listOfFiles);

		IClass clazz=(IClass) model.getTopLevelEntityFromID("ch.ifa.draw.applet.DrawApplet");
		Assert.assertNotNull(clazz);
		//Interface implemented by clazz
		IGhost interfaz=(IGhost) model.getTopLevelEntityFromID("ch.ifa.draw.framework.DrawingEditor".toCharArray());
		Assert.assertNull(interfaz);
		IGhost interfaz1=(IGhost) model.getTopLevelEntityFromID("unknown.ghost.packag.DrawingEditor".toCharArray());
		Assert.assertNotNull(interfaz1);
		
	}
	
	
	
}
