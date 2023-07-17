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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.LockObtainFailedException;

public class CsvFileIndexer {

	/**
	 * @author Foutse Khomh
	 * @since 2008/12/09
	 * 
	 */

	private IndexWriter writer;
	@SuppressWarnings("unused")
	private ArrayList<?> queue = new ArrayList<Object>();

	/**
	 * Constructor
	 * 
	 * @param indexDir
	 *            the name of the folder in which the index should be created
	 */
	public CsvFileIndexer(String indexDir) throws CorruptIndexException,
			LockObtainFailedException, IOException {
		// the boolean true parameter means to create a new index everytime,
		// potentially overwriting any existing files there.
		this.writer = new IndexWriter(indexDir, new StandardAnalyzer(), true);
	}

	//def writer = new IndexWriter(new File("index"), new StandardAnalyzer(), true)

	/**
	 * Indexes a csv file * 
	 * @param fileName
	 *            the name of a csv file we wish to add to the
	 *            index
	 * the structure of the csv  file should contain the columns number, version1, version2, filename,time,author,type, id
	 * 
	 */
	public void indexFileOrDirectory(String fileName)
			throws FileNotFoundException, CorruptIndexException, IOException {

		LineNumberReader fr = null;
		try {

			// ===================================================
			// add contents of file
			// ===================================================
			fr = new LineNumberReader(new FileReader(fileName));

			String line;
			while ((line = fr.readLine()) != null) {

				Document doc = new Document();

				String[] ucharFields = line.split(",", 8);

				doc.add(new Field(
					"number",
					ucharFields[0],
					Field.Store.COMPRESS,
					Field.Index.TOKENIZED));
				doc.add(new Field(
					"version1",
					ucharFields[1],
					Field.Store.COMPRESS,
					Field.Index.TOKENIZED));
				doc.add(new Field(
					"version2",
					ucharFields[2],
					Field.Store.COMPRESS,
					Field.Index.TOKENIZED));
				doc.add(new Field(
					"filename",
					ucharFields[3],
					Field.Store.COMPRESS,
					Field.Index.TOKENIZED));
				doc.add(new Field(
					"time",
					ucharFields[4],
					Field.Store.COMPRESS,
					Field.Index.TOKENIZED));
				doc.add(new Field(
					"author",
					ucharFields[5],
					Field.Store.COMPRESS,
					Field.Index.TOKENIZED));
				doc.add(new Field(
					"type",
					ucharFields[6],
					Field.Store.COMPRESS,
					Field.Index.TOKENIZED));

				doc.add(new Field(
					"id",
					ucharFields[7],
					Field.Store.COMPRESS,
					Field.Index.TOKENIZED));

				this.writer.addDocument(doc);
			}
			System.out.println("Added: " + fileName);
			this.writer.optimize();
			this.writer.close();
		}
		catch (Exception e) {
			System.out.println("Could not add: " + fileName);
		}
		finally {
			fr.close();
		}

	}
}
