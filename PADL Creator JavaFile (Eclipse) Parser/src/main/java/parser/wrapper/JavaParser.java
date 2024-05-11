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
package parser.wrapper;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import parser.input.SourceInputsHolder;
import parser.reader.NamedReader;

public class JavaParser {
	private final SourceInputsHolder javaProject;
	private final EclipseJDTParserWrapper wrapperClient;

	public JavaParser(final SourceInputsHolder javaProject) {
		this.javaProject = javaProject;
		this.wrapperClient = new EclipseJDTParserWrapper(this.javaProject);
	}

	public NamedCompilationUnit[] parse() {
		final List<NamedCompilationUnit> namedCompilationUnits =
			new ArrayList<NamedCompilationUnit>();

		for (final NamedReader compilationUnit : this.javaProject
			.getCompilationUnitList()) {
			final ASTNode astNode =
				this.wrapperClient.parseJavaSourceCode(compilationUnit);

			if (astNode instanceof CompilationUnit) {
				final NamedCompilationUnit namedCU =
					new NamedCompilationUnit(
						compilationUnit.getName(),
						(CompilationUnit) astNode);
				namedCompilationUnits.add(namedCU);
			}
		}

		return namedCompilationUnits.toArray(new NamedCompilationUnit[0]);
	}

	/**
	 * @param visitor
	 *            Set to null if not parsing the NamedCompilationUnit[]. *
	 */
	//	public void parse(final ExtendedASTVisitor visitor) {
	//
	//		for (final NamedReader compilationUnit : this.javaProject
	//				.getCompilationUnitList()) {
	//			final ASTNode astNode = this.wrapperClient
	//					.parseJavaSourceCode(compilationUnit);
	//
	//			if (astNode instanceof CompilationUnit) {
	//				final NamedCompilationUnit namedCU = new NamedCompilationUnit(
	//						compilationUnit.getName(), (CompilationUnit) astNode);
	//				namedCU.accept(visitor);
	//			}
	//		}
	//	}

	/**
	 * @param visitor
	 *            Set to null if not parsing the NamedCompilationUnit[]. *
	 */
	public void parse(final ExtendedASTVisitor visitor) {
		final NamedReader[] filesToParse =
			this.javaProject.getCompilationUnitList();
		for (int i = 0; i < filesToParse.length; i++) {
			final NamedReader fileToParse = filesToParse[i];

			final ASTNode astNode =
				this.wrapperClient.parseJavaSourceCode(fileToParse);

			if (astNode instanceof CompilationUnit) {
				final NamedCompilationUnit namedCU =
					new NamedCompilationUnit(
						fileToParse.getName(),
						(CompilationUnit) astNode);
				namedCU.accept(visitor);
			}
		}
	}
}
