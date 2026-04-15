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
import jct.test.rsc.snpsht.SnpShtConstant;
import jct.tools.JCTPrettyPrinter;
import junit.framework.TestCase;

public final class JCTUsingSnpShtTest extends TestCase {
	private final File outputFiles[] = new File[SnpShtConstant.FILES.length];
	private final File expectedFiles[] = new File[SnpShtConstant.FILES.length];
	private final File tmpDir;
	private final File serializedFile;

	public JCTUsingSnpShtTest(final String name) {
		super(name);
		this.tmpDir = new File(JCTConstant.TMP_PATH);
		this.serializedFile = new File(JCTConstant.REF_PATH + SnpShtConstant.SER_FILE);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		if (!this.tmpDir.exists() && !this.tmpDir.mkdirs())
			Assert.fail("Cannot create temp directory (" + JCTConstant.TMP_PATH + ") !");

		for (int i = 0; i < SnpShtConstant.FILES.length; ++i) {
			this.outputFiles[i] = new File(JCTConstant.TMP_PATH + SnpShtConstant.FILES[i]);
			this.expectedFiles[i] = new File(JCTConstant.REF_PATH + SnpShtConstant.FILES[i]);
		}
	}

	public void testCreatorAndPrettyPrinter() {
		try {
			final ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.serializedFile));
			final IJCTRootNode jct = (IJCTRootNode) ois.readObject();
			ois.close();

			jct.accept(new JCTPrettyPrinter(this.tmpDir));

			for (int i = 0; i < SnpShtConstant.FILES.length; ++i)
				Assert.assertEquals(
						"difference between files " + this.outputFiles[i].getCanonicalPath() + " and "
								+ this.expectedFiles[i].getCanonicalPath(),
						JCTUtil.getFileContent(this.expectedFiles[i]), JCTUtil.getFileContent(this.outputFiles[i]));
		} catch (final ClassNotFoundException e) {
			Assert.fail(e.toString());
		} catch (final IOException e) {
			Assert.fail(e.getMessage());
		}
	}
}
