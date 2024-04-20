/**
 * @author Mathieu Lemoine
 * @created 2009-03-02
 * 
 * Licensed under 3-clause BSD License:
 * Copyright 2009, Mathieu Lemoine
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
package padl.creator;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;
import jct.tools.JCTCreatorFromSourceCode;
import padl.creator.javafile.javac.util.JCTtoPADLTranslator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.exception.CreationException;

/**
 * This class is used to create a PADL Model, from a set of Java Source Files, using JCT.
 *
 * @author Mathieu Lemoine
 */
public class CreatorJava implements ICodeLevelModelCreator {
	private final DiagnosticListener<? super JavaFileObject> diag;
	private final Iterable<String> options;
	private final File[] files;

	/**
	 * @param files List of Path to each of the java source file want to put in the PADL Model.
	 * @param diag DiagnosticListener used to report error during compilation pass.
	 * @param options Map of options, options flags (without the leading '-') as keys, options values as values. if an option flag does not have or need a value, just set the value to null
	 */
	public CreatorJava(
		final DiagnosticListener<? super JavaFileObject> diag,
		final Map<String, String> options,
		final File... files) {

		this.diag = diag;
		this.files = files;
		final List<String> myOptions = new LinkedList<String>();

		for (final Map.Entry<String, String> e : options.entrySet()) {
			myOptions.add("-" + e.getKey());
			if (null != e.getValue())
				myOptions.add(e.getValue());
		}
		this.options = Collections.unmodifiableList(myOptions);
	}

	/**
	 * @param files List of Path to each of the java source file want to put in the PADL Model.
	 * @param diag DiagnosticListener used to report error during compilation pass.
	 * @param options Options to pass to JavaC, splited as in a command line.
	 */
	public CreatorJava(
		final DiagnosticListener<? super JavaFileObject> diag,
		final Iterable<String> options,
		final File... files) {

		this.diag = diag;
		this.files = files;
		this.options = options;
	}

	// Test comments 1
	// Test comments 2
	/**
	 * Test comments 3
	 */
	public void create(final ICodeLevelModel model) throws CreationException {
		final JCTtoPADLTranslator creator = new JCTtoPADLTranslator(model);
		try {
			JCTCreatorFromSourceCode.createJCT(
				"", false,
				this.diag,
				this.options,
				this.files).accept(creator, null);
		}
		catch (final IOException e) {
			throw new CreationException(
				"An IO Error Occurs during the creation.");
		}
	}
}
