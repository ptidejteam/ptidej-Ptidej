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
package padl.pagerank.helper;

import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.cppfile.eclipse.test.helper.ModelGenerator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.pagerank.PageRankRankingGenerator;
import padl.pagerank.utils.InputDataGeneratorWith9Relations;
import padl.visitor.IGenerator;

public class PageRankCallerWithParameters {
	public static void main(final String[] args) throws UnsupportedSourceModelException {
		final IGenerator generator = new InputDataGeneratorWith9Relations(false,
				true);

		final ICodeLevelModel codeLevelModel = ModelGenerator
				.generateModelFromCppFilesUsingEclipse("", args[0]);
		final IIdiomLevelModel idiomLevelModel = (IIdiomLevelModel) new AACRelationshipsAnalysis()
				.invoke(codeLevelModel);

		PageRankRankingGenerator.getInstance().generateModel(idiomLevelModel,
				args[1], generator);
		generator.reset();
	}
}
