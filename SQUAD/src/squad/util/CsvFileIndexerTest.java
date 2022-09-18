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
package squad.util;

import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class CsvFileIndexerTest {

	/**
	 * @author Foutse Khomh
	 * @since 2008/12/09
	 * 
	 */
	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub
		/*final Directory index = FSDirectory.getDirectory("rsc/Bugs/raw_bugs/index.dat");
		ResultsFileIndexer csvindex = new ResultsFileIndexer("rsc/Bugs/raw_bugs/index.dat");
		*///csvindex.indexFileOrDirectory("D:/Documents/Workspace/ToDo/linkssuperset.csv");

		//final Directory indexbug = FSDirectory.getDirectory("rsc/Bugs/raw_bugs/indexbug.dat");
		//ResultsFileIndexer csvindex1 = new ResultsFileIndexer("rsc/Bugs/raw_bugs/indexbug.dat");
		//csvindex.indexFileOrDirectory("D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/Bugs/JDT.bug.mapping");

		//csvindex.indexFileOrDirectory("D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/Bugs/Eclipse.bug.mapping");
		//csvindex1.indexFileOrDirectory("D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/Bugs/raw_bugs/bug-classification_max.csv");

		final Directory index =
			FSDirectory
				.getDirectory("D:/Documents/Workspace/changes/index1.dat");
		ResultsFileIndexer csvindex =
			new ResultsFileIndexer("D:/Documents/Workspace/changes/index1.dat");

		csvindex
			.indexFileOrDirectory("D:/Documents/Workspace/changes/JHD.potentialbug.mapping");

		final Analyzer analyser = new StandardAnalyzer();
		Query q = new QueryParser("filename", analyser).parse("equinox");
		IndexSearcher s = new IndexSearcher(index);
		Hits hits = s.search(q);
		System.out.println("Found " + hits.length() + " hits.");
		for (int i = 0; i < hits.length(); ++i) {
			System.out.println((i + 1) + ". " + hits.doc(i).get("filename"));
		}
		s.close();
	}

}
