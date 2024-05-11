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

import padl.generator.helper.ModelGenerator;
import padl.kernel.IIdiomLevelModel;
import padl.pagerank.PageRankRankingGenerator;
import padl.pagerank.utils.InputDataGeneratorWith9Relations;
import padl.visitor.IGenerator;

public class PageRankCallerWithParameters {
	public static void main(final String[] args) {
		final IGenerator generator =
			new InputDataGeneratorWith9Relations(false, true);

		final IIdiomLevelModel idiomLevelModel =
			ModelGenerator.generateModelFromClassFilesDirectory(args[0]);

		PageRankRankingGenerator.getInstance().generateModel(
			idiomLevelModel,
			args[1],
			generator);
		generator.reset();
	}
}
