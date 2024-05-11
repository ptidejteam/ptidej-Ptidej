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

package jct.test.cases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.junit.Assert;

import jct.kernel.IJCTRootNode;
import jct.test.common.JCTConstant;
import jct.test.common.JCTUtil;
import jct.tools.JCTPrettyPrinter;
import junit.framework.TestCase;

public final class JCTMisc extends TestCase {
	private final String outputPath;
	private final String expectedPath;
	private final File tmpDir;

	public JCTMisc(final String name) {
		super(name);
		this.outputPath = JCTConstant.TMP_PATH + JCTConstant.MISC_DIR;
		this.expectedPath = JCTConstant.REF_PATH + JCTConstant.MISC_DIR;
		this.tmpDir = new File(JCTConstant.TMP_PATH);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		if (!this.tmpDir.exists() && !this.tmpDir.mkdirs()) {
			Assert.fail("Cannot create tmp directory (" + JCTConstant.TMP_PATH
					+ ") !");
		}

	}

	private IJCTRootNode getJCTInstance(String serializedFile) {
		try {
			final File file = new File(this.expectedPath + serializedFile);
			final ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream(file));
			final IJCTRootNode ijctRootNote = (IJCTRootNode) ois.readObject();
			ois.close();
			return ijctRootNote;
		}
		catch (final IOException e) {
			Assert.fail(e.toString() + " : " + e.getMessage());
		}
		catch (final ClassNotFoundException e) {
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
			jct.accept(new JCTPrettyPrinter(this.tmpDir));

			for (int i = 0; i < fileNames.length; i++) {
				Assert.assertEquals(
						"difference between files "
								+ outputFiles[i].getCanonicalPath() + " and "
								+ expectedFiles[i].getCanonicalPath(),
						JCTUtil.getFileContent(expectedFiles[i]),
						JCTUtil.getFileContent(outputFiles[i]));
			}
		}
		catch (final IOException e) {
			Assert.fail(e.getMessage());
		}
	}

	public void testAnnonymousClasses() {
		this.testFiles("annonymousclasses/AnnonymousClasses.ser",
				"annonymousclasses/AnnonymousClasses.java");
	}

	public void testArrayLiterals() {
		this.testFiles("arrayliterals/ArrayLiterals.ser",
				"arrayliterals/ArrayLiterals.java");
	}

	public void testComments() {
		this.testFiles("comments/Comments.ser", "comments/Comments.java");
	}

	public void testSelectors() {
		this.testFiles("selectors/Selectors.ser", "selectors/Selectors.java");
	}
}
