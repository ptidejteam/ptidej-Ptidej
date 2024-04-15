/**
 * @author Mathieu Lemoine
 * @created 2009-03-02
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
package padl.creator.javafile.javac;

import jct.kernel.IJCTRootNode;
import padl.creator.javafile.javac.util.JCTtoPADLTranslator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.IFactory;
import padl.kernel.exception.CreationException;

/**
 * This class is used to create a PADL Model from a JCT.
 *
 * @author Mathieu Lemoine
 */
public class JCTCreator implements ICodeLevelModelCreator {
	private final IJCTRootNode jctRootNode;

	/**
	 * @param aRootNode The JCT from which the PADL Model will be made.
	 */
	@SuppressWarnings("unused")
	public JCTCreator(
		final IJCTRootNode aRootNode,
		final IFactory PADLImplementationProvider) {
		this.jctRootNode = aRootNode;
	}

	@Override
	public void create(final ICodeLevelModel model) throws CreationException {
		final JCTtoPADLTranslator creator = new JCTtoPADLTranslator(model);
		this.jctRootNode.accept(creator, null);
	}
}
