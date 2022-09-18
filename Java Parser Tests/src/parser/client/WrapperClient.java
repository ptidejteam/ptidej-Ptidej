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
package parser.client;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;

import parser.input.SourceInputsHolder;
import parser.reader.NamedReader;
import parser.wrapper.EclipseJDTParserWrapper;

public class WrapperClient {

	protected SourceInputsHolder javaProject;
	protected EclipseJDTParserWrapper parser;

	public WrapperClient(final SourceInputsHolder javaProject) {
		this.javaProject = javaProject;
		this.parser = new EclipseJDTParserWrapper(this.javaProject);
	}

	public void parse(final NamedReader[] javaSourceList,
			final ASTVisitor[] visitors) {

		for (final NamedReader javaFilePath : javaSourceList) {
			this.parseAJavaSource(javaFilePath, visitors);
		}
	}

	private void parseAJavaSource(final NamedReader javaSourcePath,
			final ASTVisitor[] visitors) {

		final ASTNode node = this.parser.parseJavaSourceCode(javaSourcePath);

		if (visitors != null) {
			for (final ASTVisitor visitor : visitors) {
				node.accept(visitor);
			}
		}
	}

	public void parseAllJavaSources(final ASTVisitor[] visitors) {
		this.parse(this.javaProject.getCompilationUnitList(), visitors);
	}

}
