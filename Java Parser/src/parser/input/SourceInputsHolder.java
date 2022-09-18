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
package parser.input;

import java.util.Arrays;
import parser.reader.NamedReader;

public abstract class SourceInputsHolder {
	private NamedReader[] classpathEntries;
	private NamedReader[] sourcepathEntries;
	private NamedReader[] compilationUnitList;
	private String compilerCompliance = "1.7";

	abstract protected NamedReader[] buildCompilationUnitList();

	public NamedReader[] getClasspathEntries() {
		if (this.classpathEntries == null) {
			this.classpathEntries = new NamedReader[0];
		}
		return this.classpathEntries;
	}

	public NamedReader[] getCompilationUnitList() {

		if (this.compilationUnitList == null) {
			this.setCompilationUnitList(this.buildCompilationUnitList());
		}
		return this.compilationUnitList;
	}

	public String getCompilerCompliance() {
		return this.compilerCompliance;
	}

	public NamedReader[] getSourcepathEntries() {
		if (this.sourcepathEntries == null) {
			this.sourcepathEntries = new NamedReader[0];
		}
		return this.sourcepathEntries;
	}

	protected SourceInputsHolder setClasspathEntries(
		final NamedReader[] classpathEntries) {
		this.classpathEntries = classpathEntries;
		return this;
	}

	protected SourceInputsHolder setCompilationUnitList(
		final NamedReader[] compilationUnitList) {
		Arrays.sort(compilationUnitList);
		this.compilationUnitList = compilationUnitList;
		return this;
	}

	public SourceInputsHolder setCompilerCompliance(
		final String compilerCompliance) {

		this.compilerCompliance = compilerCompliance;

		return this;
	}

	protected SourceInputsHolder setSourcepathEntries(
		final NamedReader[] sourcepathEntries) {
		this.sourcepathEntries = sourcepathEntries;
		return this;
	}
}
