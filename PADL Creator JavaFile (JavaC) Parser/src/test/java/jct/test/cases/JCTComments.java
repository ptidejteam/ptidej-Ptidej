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
import java.io.IOException;
import java.util.Collection;

import org.junit.Assert;

import jct.kernel.IJCTClass;
import jct.kernel.IJCTClassMember;
import jct.kernel.IJCTCompilationUnit;
import jct.kernel.IJCTField;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTSourceCodePart;
import jct.kernel.JCTKind;
import jct.test.common.JCTConstant;
import jct.tools.JCTCommentAttachor;
import jct.tools.JCTCommentExtractor;
import jct.tools.JCTCreatorFromSourceCode;
import jct.util.IJCTContainer;
import junit.framework.TestCase;

public final class JCTComments extends TestCase {
	private final String srcPath;
	private final String tmpDir;

	public JCTComments(final String name) {
		super(name);
		this.srcPath = JCTConstant.REF_PATH + JCTConstant.MISC_DIR;
		this.tmpDir = JCTConstant.TMP_PATH;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		final File tmpDirFile = new File(this.tmpDir);
		if (!tmpDirFile.exists() && !tmpDirFile.mkdirs()) {
			Assert.fail("Cannot create temp directory (" + JCTConstant.TMP_PATH
					+ ") !");
		}

	}

	private IJCTRootNode getJCTInstance(final boolean extractActualSourceCode,
			final String... fileNames) {

		final File srcFiles[] = new File[fileNames.length];

		for (int i = 0; i < fileNames.length; i++) {
			srcFiles[i] = new File(this.srcPath + fileNames[i]);
		}

		try {
			final IJCTRootNode jct = JCTCreatorFromSourceCode.createJCT("Test",
					extractActualSourceCode, null, JCTConstant.OPTIONS,
					srcFiles);
			return jct;
		}
		catch (final IOException e) {
			Assert.fail(e.getMessage());
			return null;
		}
	}

	private void testComments(final IJCTSourceCodePart part,
			final int expectedQuantity, final String name) {

		if (expectedQuantity != part.getComments().size()) {
			Assert.fail("There should be " + expectedQuantity + " comments "
					+ (part instanceof IJCTCompilationUnit ? "in"
							: "attached to")
					+ " " + name + ", but there are "
					+ part.getComments().size());
		}
	}

	public void testComments() {
		final IJCTRootNode jct = this.getJCTInstance(true,
				"comments/Comments.java");

		jct.accept(new JCTCommentExtractor());
		jct.accept(new JCTCommentAttachor());

		@SuppressWarnings("unchecked")
		final Collection<IJCTCompilationUnit> cus = ((IJCTContainer) jct)
				.getAllEnclosedElements(JCTKind.COMPILATION_UNIT,
						IJCTCompilationUnit.class, true);

		IJCTCompilationUnit cu = null;
		for (final IJCTCompilationUnit it : cus) {
			if ("Comments.java".equals(it.getSourceFile().getName())) {
				cu = it;
				// Yann 2009/07/22: Clean up!
				// Should not be necessary, the loop should be rewritten cleanly.
				// In particular, the "cu" variable should be final.
				break;
			}
		}

		/*
		  IJCTCompilationUnit cu = null;
		  final Iterator<IJCTCompilationUnit> it = cus.iterator();
		  while(null == cu && it.hasNext())
		  {
		     IJCTCompilationUnit cuIt = it.next();
		     if ("Comments.java".equals(it.getSourceFile().getName()))
		        cu = cuIt;
		  }
		 */
		if (null == cu) {
			throw new IllegalStateException(
					"Cannot find tested compilation unit \"Comments.java\".");
		}
		this.testComments(cu, 4, "the Compilation Unit");

		final IJCTClass c = cu.getClazzs().iterator().next();
		this.testComments(c, 2, "the Class");

		for (final IJCTClassMember cm : c.getDeclaredMembers()) {
			this.testComments(cm, 1, cm.getName());
			if (cm instanceof IJCTField) {
				this.testComments(((IJCTField) cm).getInitialValue(), 1,
						"the field initial value");
			}
		}
	}
}
