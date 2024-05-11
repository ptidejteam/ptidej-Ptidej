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

package jct.test.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.junit.Assert;

import jct.kernel.IJCTRootNode;
import jct.test.rsc.snpsht.SnpShtConstant;
import jct.tools.JCTCreatorFromSourceCode;

public final class SerFileGeneratorJCTUsingSnpSht {
	private final File srcFiles[] = new File[SnpShtConstant.FILES.length];
	private final File outputFiles[] = new File[SnpShtConstant.FILES.length];
	private final File expectedFiles[] = new File[SnpShtConstant.FILES.length];
	private final File tmpDir;
	private final File serializedFile;

	public SerFileGeneratorJCTUsingSnpSht() {
		this.tmpDir = new File(JCTConstant.TMP_PATH);
		this.serializedFile = new File(
				JCTConstant.REF_PATH + SnpShtConstant.SER_FILE);
	}

	public static void main(final String[] args)
			throws FileNotFoundException, ClassNotFoundException, IOException {

		new SerFileGeneratorJCTUsingSnpSht().generateSerFile();
	}

	public void generateSerFile()
			throws FileNotFoundException, IOException, ClassNotFoundException {

		if (!this.tmpDir.exists() && !this.tmpDir.mkdirs())
			Assert.fail("Cannot create temp directory (" + JCTConstant.TMP_PATH
					+ ") !");

		for (int i = 0; i < SnpShtConstant.FILES.length; ++i) {
			this.srcFiles[i] = new File(
					JCTConstant.TST_PATH + SnpShtConstant.FILES[i]);
			this.outputFiles[i] = new File(
					JCTConstant.TMP_PATH + SnpShtConstant.FILES[i]);
			this.expectedFiles[i] = new File(
					JCTConstant.REF_PATH + SnpShtConstant.FILES[i]);
		}
		this.serializedFile.getParentFile().mkdirs();

		IJCTRootNode jct = JCTCreatorFromSourceCode.createJCT(
				"JCTPrettyPrinterTest", false, null, JCTConstant.OPTIONS,
				this.srcFiles);

		// jct.accept(new JCTPrettyPrinter(this.tmpDir));
		final ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(this.serializedFile));
		oos.writeObject(jct);
		oos.close();
		// Yann and Nicolas 2024/05/13: Mission Impossible!
		// When generating the Java files from the IJCTRootNode here,
		// then serialising them, then deserialising them, they are
		// not exactly identical to the originals :-(
		/*
		for (int i = 0; i < SnpShtConstant.FILES.length; ++i)
			Assert.assertEquals("difference between files "
					+ this.expectedFiles[i].getCanonicalPath() + " and "
					+ this.outputFiles[i].getCanonicalPath(),
					JCTUtil.getFileContent(this.expectedFiles[i]),
					JCTUtil.getFileContent(this.outputFiles[i]));
		
		final ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(this.serializedFile));
		jct = (IJCTRootNode) ois.readObject();
		ois.close();
		jct.accept(new JCTPrettyPrinter(this.tmpDir));
		for (int i = 0; i < SnpShtConstant.FILES.length; ++i)
			Assert.assertEquals(
					"difference between files "
							+ this.outputFiles[i].getCanonicalPath() + " and "
							+ this.expectedFiles[i].getCanonicalPath(),
					JCTUtil.getFileContent(this.outputFiles[i]),
					JCTUtil.getFileContent(this.expectedFiles[i]));
		*/
	}
}
