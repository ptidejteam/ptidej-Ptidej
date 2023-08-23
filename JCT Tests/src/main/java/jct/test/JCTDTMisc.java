/**
 * @author Mathieu Lemoine
 * @created 2009-03-23 (月)
 *
 * Licensed under 3-clause BSD License:
 * Copyright © 2009, Mathieu Lemoine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *  * Neither the name of Mathieu Lemoine nor the
 *    names of contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY Mathieu Lemoine ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Mathieu Lemoine BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package jct.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.Arrays;

import jct.kernel.IJCTRootNode;
import jct.test.rsc.Constant;
import jct.test.rsc.misc.MiscConstant;
import jct.tools.JCTPrettyPrinter;
import org.junit.Assert;
import junit.framework.TestCase;

public final class JCTDTMisc extends TestCase {
	private final String rscPath;
	private final String outputPath;
	private final String expectedPath;
	private final File garbageDir;

	private static String getFileContent(final File file) {
		try {
			final char characters[] = new char[(int) file.length()];
			final BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(file)));
			final int length =
				bufferedReader.read(characters, 0, characters.length);
			bufferedReader.close();
			return new String(characters, 0, length);
		}
		catch (final IOException e) {
			Assert.fail("Cannot read file " + file.getAbsolutePath());
		}
		throw new RuntimeException(
			"Assert.fail() failed to fail... (Cannot read file + "
					+ file.getAbsolutePath() + ")");
	}

	public JCTDTMisc(final String name) {
		super(name);
		this.rscPath = Constant.RSC_PATH + MiscConstant.MISC_DIR;
		this.outputPath = Constant.TMP_PATH + MiscConstant.MISC_DIR;
		this.expectedPath = Constant.RSC_PATH + MiscConstant.MISC_DIR;
		this.garbageDir = new File(Constant.TMP_PATH);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		if (!this.garbageDir.exists() && !this.garbageDir.mkdirs()) {
			Assert.fail(
				"Cannot create garbage directory (" + Constant.TMP_PATH
						+ ") !");
		}

	}

	private IJCTRootNode getJCTInstance(String serializedFile) {
		try {
			final File file = new File(this.rscPath + serializedFile);
			final ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			final IJCTRootNode ijctRootNote = (IJCTRootNode) ois.readObject();
			ois.close();
			return ijctRootNote;
		}
		catch (final IOException e) {
			Assert.fail(e.toString() + " : " + e.getMessage());
		}
		catch (ClassNotFoundException e) {
			Assert.fail(e.toString() + " : " + e.getMessage());
		}
		return null;
	}

	private void testFiles(String serializedFile, String... fileNames) {
		this.testJCT(this.getJCTInstance(serializedFile), fileNames);
	}

	private void testJCT(final IJCTRootNode jct, String... fileNames) {
		final File outputFiles[] = new File[fileNames.length];
		final File expectedFiles[] = new File[fileNames.length];

		for (int i = 0; i < fileNames.length; ++i) {
			outputFiles[i] = new File(this.outputPath + fileNames[i]);
			expectedFiles[i] = new File(this.expectedPath + fileNames[i]);
		}

		try {
			jct.accept(new JCTPrettyPrinter(this.garbageDir));

			for (int i = 0; i < fileNames.length; i++) {
				Assert.assertEquals(
					"difference between files "
							+ outputFiles[i].getCanonicalPath() + " and "
							+ expectedFiles[i].getCanonicalPath(),
					JCTDTMisc.getFileContent(expectedFiles[i]),
					JCTDTMisc.getFileContent(outputFiles[i]));
			}
		}
		catch (final IOException e) {
			Assert.fail(e.getMessage());
		}
	}

	public void testArrayLiterals() {
		this.testFiles(
			"arrayliterals/ArrayLiterals.ser",
			"arrayliterals/ArrayLiterals.java");
	}

	public void testAnnonymousClasses() {
		this.testFiles(
			"annonymousclasses/AnnonymousClasses.ser",
			"annonymousclasses/AnnonymousClasses.java");
	}

	public void testSelectors() {
		this.testFiles("selectors/Selectors.ser", "selectors/Selectors.java");
	}
}
