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
package padl.pagerank;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import util.io.ProxyConsole;

public class PageRankRankingBuilder {
	private static PageRankRankingBuilder UniqueInstance;
	public static PageRankRankingBuilder getInstance() {
		if (PageRankRankingBuilder.UniqueInstance == null) {
			PageRankRankingBuilder.UniqueInstance =
				new PageRankRankingBuilder();
		}
		return PageRankRankingBuilder.UniqueInstance;
	}
	private PageRankRankingBuilder() {
	}
	public Ranking[] getRankings(final FileReader aPageRankResultsReader) {
		try {
			final LineNumberReader pageRankResultsReader =
				new LineNumberReader(aPageRankResultsReader);

			// Skip the header.
			pageRankResultsReader.readLine();

			String line;
			final Set setOfRankings = new HashSet();
			while ((line = pageRankResultsReader.readLine()) != null) {
				final StringTokenizer tokenizer =
					new StringTokenizer(line, ";");
				final int count = Integer.parseInt(tokenizer.nextToken());
				final String entityName = tokenizer.nextToken();
				final float pageRankValue =
					Float.parseFloat(tokenizer.nextToken());
				final int rank = Integer.parseInt(tokenizer.nextToken());
				final int allEdges = Integer.parseInt(tokenizer.nextToken());
				final int incomingEdges =
					Integer.parseInt(tokenizer.nextToken());
				final int outgoingEdges =
					Integer.parseInt(tokenizer.nextToken());

				setOfRankings.add(new Ranking(
					count,
					entityName,
					pageRankValue,
					rank,
					allEdges,
					incomingEdges,
					outgoingEdges));
			}

			final Ranking[] rankings = new Ranking[setOfRankings.size()];
			setOfRankings.toArray(rankings);
			return rankings;
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			return new Ranking[0];
		}
	}
}
