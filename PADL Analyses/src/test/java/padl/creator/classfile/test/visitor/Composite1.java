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
package padl.creator.classfile.test.visitor;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.StringTokenizer;

import org.junit.Assert;

import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.classfile.test.ClassFilePrimitive;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.visitor.IGenerator;
import util.io.ProxyDisk;

public class Composite1 extends ClassFilePrimitive {
	private static final String EXPECTED_FILE_PATH_SRC = "../PADL Analyses/src/test/resources/padl/creator/classfile/test/visitor/Composite1.expected.txt";
	private static final String EXPECTED_FILE_PATH_TST = "../PADL Analyses/target/test-classes/padl/creator/classfile/test/visitor/Composite1.expected.txt";

	private static IAbstractLevelModel AbstractLevelModel;

	public Composite1(final String name) {
		super(name);
	}

	protected void setUp()
			throws IllegalAccessException, InstantiationException,
			CreationException, UnsupportedSourceModelException {

		if (Composite1.AbstractLevelModel == null) {
			final ICodeLevelModel codeLevelModel = ClassFilePrimitive
					.getFactory()
					.createCodeLevelModel("padl.example.composite1");
			codeLevelModel.create(new CompleteClassFileCreator(new String[] {
					"../PADL Creator ClassFile/target/test-classes/padl/example/composite1/" }));

			Composite1.AbstractLevelModel = (IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);
		}
	}

	// FLAKY TEST
	// TODO Make it steady!
	public void testVisitor() {
		try {
			final IGenerator generator = new SimpleGenerator();
			Composite1.AbstractLevelModel.generate(generator);

			// To generate a new expected file.
			/*
			FileWriter writer = ProxyDisk.getInstance()
					.fileAbsoluteOutput(Composite1.EXPECTED_FILE_PATH_SRC);
			writer.write(generator.getCode());
			writer.close();
			writer = ProxyDisk.getInstance()
					.fileAbsoluteOutput(Composite1.EXPECTED_FILE_PATH_TST);
			writer.write(generator.getCode());
			writer.close();
			*/

			final LineNumberReader reader = new LineNumberReader(
					ProxyDisk.getInstance().fileAbsoluteInput(
							Composite1.EXPECTED_FILE_PATH_TST));

			int i = 0;
			final StringTokenizer tokenizer = new StringTokenizer(
					generator.getCode(), "\n");
			Assert.assertEquals(150, tokenizer.countTokens());
			while (tokenizer.hasMoreTokens()) {
				Assert.assertEquals("TestVisitor output (line " + i + ')',
						"\n" + reader.readLine(), "\n" + tokenizer.nextToken());
				i++;
			}

			reader.close();
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		catch (final IOException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}
