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
package padl.creator.test.comparison.eclipse;

import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.javafile.javac.JavaFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.test.helper.ModelComparator;

public class Composite1 extends TestCase {
	private static final String SRC_PATH =
		"../PADL Creator JavaFile-ClassFile Tests/src/padl/example/composite1/";
	private static final String BIN_PATH =
		"../PADL Creator JavaFile-ClassFile Tests/bin/padl/example/composite1/";
	//	private static final List OPTIONS = Arrays.asList(new String[] {
	//			"-classpath",
	//			System.getProperty("java.class.path", ".") + ":"
	//					+ Composite1.SRC_PATH, "-d", Composite1.SRC_PATH,
	//			"-source", "6" });
	private static ICodeLevelModel JavaFileCodeLevelModel;
	private static ICodeLevelModel ClassFileCodeLevelModel;

	public Composite1(final String name) {
		super(name);
	}
	protected void setUp() {
		if (Composite1.JavaFileCodeLevelModel == null) {
			try {
				Composite1.JavaFileCodeLevelModel =
					Factory.getInstance().createCodeLevelModel("");
				Composite1.JavaFileCodeLevelModel.create(new JavaFileCreator(
					Composite1.SRC_PATH));
			}
			catch (final CreationException e) {
				e.printStackTrace();
			}
		}
		if (Composite1.ClassFileCodeLevelModel == null) {
			try {
				Composite1.ClassFileCodeLevelModel =
					Factory.getInstance().createCodeLevelModel("");
				Composite1.ClassFileCodeLevelModel
					.create(new CompleteClassFileCreator(
						new String[] { Composite1.BIN_PATH }));
			}
			catch (final CreationException e) {
				e.printStackTrace();
			}
		}
	}
	public void testCompare() {
		Composite1.ClassFileCodeLevelModel.walk(new ModelComparator(
			Composite1.JavaFileCodeLevelModel));
	}
}
