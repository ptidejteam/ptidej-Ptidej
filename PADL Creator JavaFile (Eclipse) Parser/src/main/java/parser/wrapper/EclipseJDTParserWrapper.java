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
import java.util.Hashtable;
import java.util.List;
import org.eclipse.core.internal.preferences.EclipsePreferences;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.internal.core.JavaModelManager;
import parser.input.SourceInputsHolder;
import parser.reader.NamedReader;
import common.tools.constants.Constants;

public class EclipseJDTParserWrapper {
	private final SourceInputsHolder javaProject;
	private final ASTParser parser;

	@SuppressWarnings("deprecation")
	public EclipseJDTParserWrapper(final SourceInputsHolder aJavaProject) {
		this.javaProject = aJavaProject;

		// Yann 2015/01/06: Change how to set compliance...
		// See http://help.eclipse.org/juno/index.jsp?topic=%2Forg.eclipse.jdt.doc.isv%2Fguide%2Fjdt_api_options.htm&anchor=javacore
		JavaModelManager.getJavaModelManager().preferencesLookup[0] =
			new EclipsePreferences();
		JavaModelManager.getJavaModelManager().preferencesLookup[1] =
			new EclipsePreferences();
		// Get the default options
		final Hashtable<String, String> options = JavaCore.getDefaultOptions();
		//	// Change the value of an option
		options.put(
			JavaCore.COMPILER_COMPLIANCE,
			this.javaProject.getCompilerCompliance());
		options.put(
			JavaCore.COMPILER_SOURCE,
			this.javaProject.getCompilerCompliance());
		// Set the new options
		//	JavaCore.setComplianceOptions(
		//		this.javaProject.getCompilerCompliance(),
		//		JavaCore.getOptions());
		JavaCore.setOptions(options);

		this.parser = ASTParser.newParser(AST.JLS4);
	}

	/**
	 * Covert NamedReades' name to the form that the Eclipse JDT parser accept.
	 * For now, they are in the same format.
	 * 
	 * @param namedReaders
	 * @return
	 */
	private final String[] getNamesFromNamedReaders(
		final NamedReader[] namedReaders) {
		final String[] names = new String[namedReaders.length];
		for (int i = namedReaders.length - 1; i >= 0; i--) {
			names[i] = namedReaders[i].getName();
		}
		return names;
	}

	/**
	 * The source code stored in the name of the only NamedReader gotten from
	 * compilationUnit.
	 * 
	 * @param compilationUnit
	 * @return SourceCode
	 */
	private String getSourceCode(final NamedReader compilationUnit) {
		final StringBuffer sourceCode = new StringBuffer(Constants.EMPTY_STR);

		for (final NamedReader reader : compilationUnit.read()) {
			sourceCode.append(reader.getName());
		}

		return sourceCode.toString();
	}

	public ASTNode[] parse() {
		final List<ASTNode> astNodes = new ArrayList<ASTNode>();

		for (final NamedReader compilationUnit : this.javaProject
			.getCompilationUnitList()) {
			astNodes.add(this.parseJavaSourceCode(compilationUnit));
		}
		return astNodes.toArray(new ASTNode[0]);
	}

	/*
	 * Parse a given Java File of the Project
	 */
	public ASTNode parseJavaSourceCode(final NamedReader compilationUnit) {
		this.parser.setResolveBindings(true);
		this.parser.setBindingsRecovery(true);
		this.parser.setEnvironment(
			this.getNamesFromNamedReaders(this.javaProject
				.getClasspathEntries()),
			this.getNamesFromNamedReaders(this.javaProject
				.getSourcepathEntries()),
			null,
			true);

		this.parser
			.setSource(this.getSourceCode(compilationUnit).toCharArray());
		this.parser.setUnitName(compilationUnit.getName());

		final ASTNode node = this.parser.createAST(null);
		return node;
	}
}
