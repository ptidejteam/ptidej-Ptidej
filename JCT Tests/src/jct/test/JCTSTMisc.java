/**
 * @author Mathieu Lemoine
 * @created 2009-08-31 (月)
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import jct.kernel.IJCTRootNode;
import jct.test.rsc.Constant;
import jct.test.rsc.misc.MiscConstant;
import jct.tools.JCTCreatorFromSourceCode;
import org.junit.Assert;
import junit.framework.TestCase;

public final class JCTSTMisc extends TestCase {
	private final String srcPath;
	private final String outputPath;
	private final File garbageDir;

	public JCTSTMisc(final String name) {
		super(name);
		this.srcPath = Constant.SRC_PATH + MiscConstant.MISC_DIR;
		this.outputPath = Constant.TMP_PATH + MiscConstant.MISC_DIR;
		this.garbageDir = new File(Constant.TMP_PATH);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		if (!this.garbageDir.exists() && !this.garbageDir.mkdirs()) {
			Assert.fail("Cannot create garbage directory (" + Constant.TMP_PATH
					+ ") !");
		}

	}

	private IJCTRootNode getJCTInstance(
		final boolean extractActualSourceCode,
		String... fileNames) {

		final File srcFiles[] = new File[fileNames.length];

		for (int i = 0; i < fileNames.length; i++) {
			srcFiles[i] = new File(this.srcPath + fileNames[i]);
		}

		try {
			return JCTCreatorFromSourceCode.createJCT(
				"Test",
				extractActualSourceCode,
				null,
				Constant.OPTIONS,
				srcFiles);
		}
		catch (final IOException e) {
			Assert.fail(e.getMessage());
			return null;
		}
	}

	private void testFiles(String serializedFile, String... fileNames) {
		this.testJCT(this.getJCTInstance(false, fileNames), serializedFile);
	}

	private void testJCT(final IJCTRootNode jct, String serializedFile) {
		final File outputFile = new File(this.outputPath + serializedFile);

		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputFile));
            oos.writeObject(jct);
            oos.flush();
            oos.close();
		}
		catch (final IOException e) {
			Assert.fail(e.toString() + " : " + e.getMessage());
		}
	}

	public void testArrayLiterals() {
		this.testFiles("arrayliterals/ArrayLiterals.ser", "arrayliterals/ArrayLiterals.java");
	}

	public void testAnnonymousClasses() {
		this.testFiles("annonymousclasses/AnnonymousClasses.ser", "annonymousclasses/AnnonymousClasses.java");
	}

	public void testComments() {
		this.testFiles("comments/Comments.ser", "comments/Comments.java");
	}
}
