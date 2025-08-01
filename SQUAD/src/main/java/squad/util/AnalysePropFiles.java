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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;

import util.io.ProxyDisk;

@SuppressWarnings("unused")
public class AnalysePropFiles {

	/**
	 * @param args
	 */

	private IndexWriter writer;
	private ArrayList<?> queue = new ArrayList<Object>();
	private Vector<?> eltFields;

	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub
		// IndexWriter writer = new IndexWriter(indexDir, new StandardAnalyzer(), true);
		String dirName = "D:/Documents/Workspace/Defects evolution";

		//String dirName = "D:/Documents/Workspace/Defects evolution/Eclipse JDT";
		final File pathFile = new File(dirName);
		final String[] subPaths = pathFile.list();
		for (int i = 0; i < subPaths.length; i++) {
			final String fileName = dirName + "/" + subPaths[i];
			final File file = new File(fileName);
			int begin = fileName.indexOf("-");

			if (file.isDirectory() && (begin != -1)) {
				//System.out.println(fileName);	
				String version = fileName.substring(begin + "-".length());

				//				final Directory index =
				//					FSDirectory
				//						.getDirectory("D:/Documents/Workspace/Defects evolution/Eclipse JDT/Analysis/index"
				//								+ version + ".dat");
				//				AnalysePropFiles propindex =
				//					new AnalysePropFiles(
				//						"D:/Documents/Workspace/Defects evolution/Eclipse JDT/Analysis/index"
				//								+ version + ".dat");
				//
				//		//				propindex
				//		//					.indexFileOrDirectory("D:/Documents/Workspace/Defects evolution/Xerces-"
				//		//							+ version + "/blob.probs");
				//				
				//				propindex
				//					.indexFileOrDirectory("D:/Documents/Workspace/Defects evolution/Eclipse JDT/jdtclasses-"
				//							+ version + "/blob.probs");
				if (!(version.equals("J_1_0_1") || version.equals("J_2_9_0")
						|| version.equals("J_2_9_1")
						|| version.equals("J_1_1_0"))) {
					MergeChangesAndProbs(dirName,
							"D:/Documents/Workspace/Defects evolution/Xerces-"
									+ version);
				}
			}
		}

		//MergeResults(dirName);

		//		MergeChangesAndProbs(
		//			dirName,
		//			"D:/Documents/Workspace/Defects evolution/Xerces-J_1_0_2");
	}

	public AnalysePropFiles(String indexDir) throws CorruptIndexException,
			LockObtainFailedException, IOException {
		// the boolean true parameter means to create a new index everytime,
		// potentially overwriting any existing files there.
		this.writer = new IndexWriter(indexDir, new StandardAnalyzer(), true);
	}

	public void indexFileOrDirectory(String fileName)
			throws FileNotFoundException, CorruptIndexException, IOException {

		LineNumberReader fr = null;
		try {

			// ===================================================
			// add contents of file
			// ===================================================

			fr = new LineNumberReader(new FileReader(fileName));

			fr.readLine();
			String line;
			while ((line = fr.readLine()) != null) {

				Document doc = new Document();

				String[] ucharFields = line.split(",", 3);

				doc.add(new Field("Entity", ucharFields[0],
						Field.Store.COMPRESS, Field.Index.TOKENIZED));

				// now i extract probabilities from the second field
				String substring1 = "TRUE=";
				String substring2 = "]";

				String substring3 = "FALSE=";

				int begin1 = ucharFields[2].indexOf(substring1);
				int end1 = ucharFields[2].indexOf(substring2);
				String ProbTrue = ucharFields[2]
						.substring(begin1 + substring1.length(), end1);

				int begin2 = ucharFields[1].indexOf(substring3);
				String ProbFalse = ucharFields[1]
						.substring(begin2 + substring3.length());
				//				System.out.println("Name:" + ucharFields[0]);
				//				System.out.println("Prop true:" + ProbTrue);
				//				System.out.println("Prop false:" + ProbFalse);

				doc.add(new Field("PropTrue", ProbTrue, Field.Store.COMPRESS,
						Field.Index.TOKENIZED));

				doc.add(new Field("PropFalse", ProbFalse, Field.Store.COMPRESS,
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
			if (fr != null) {
				fr.close();
			}
		}

	}

	/*
	 * Merge all the results
	 * 
	 */
	public static void MergeResults(String dirName)
			throws FileNotFoundException, CorruptIndexException, IOException {
		final Analyzer analyser = new StandardAnalyzer();
		final Vector<String> versions = new Vector<String>();
		final Writer out = ProxyDisk.getInstance()
				.fileTempOutput("Analysis/Blobs_all.csv", false);
		String header = "Entity";
		final File pathFile = new File(dirName);
		final String[] subPaths = pathFile.list();
		for (int i = 0; i < subPaths.length; i++) {
			final String fileName = dirName + "/" + subPaths[i];
			final File file = new File(fileName);
			int begin = fileName.indexOf("-");

			if (file.isDirectory() && (begin != -1)) {
				//System.out.println(fileName);	
				header = header + ","
						+ fileName.substring(begin + "-".length());
				versions.addElement(fileName.substring(begin + "-".length()));
			}
		}

		out.write(header + '\n');
		out.flush();
		//System.out.println(versions.size());
		TreeSet<String> ClassSet = new TreeSet<String>();
		for (int i = 0; i < subPaths.length; i++) {
			final String fileName = dirName + "/" + subPaths[i];
			final File file = new File(fileName);
			int begin = fileName.indexOf("-");

			if (file.isDirectory() && (begin != -1)) {

				LineNumberReader fr = null;
				try {

					// ===================================================
					// add contents of file
					// ===================================================

					//					fr =
					//						new LineNumberReader(new FileReader(
					//							"D:/Documents/Workspace/Defects evolution/Xerces-"
					//									+ fileName.substring(begin + "-".length())
					//									+ "/blob.probs"));
					fr = new LineNumberReader(new FileReader(
							"D:/Documents/Workspace/Defects evolution/Eclipse JDT/jdtclasses-"
									+ fileName.substring(begin + "-".length())
									+ "/blob.probs"));

					fr.readLine();
					String line;
					while ((line = fr.readLine()) != null) {

						String[] ucharFields = line.split(",", 3);

						// now with the entity name ucharFields[0] i will look for probabilities for the different versions
						if (!ClassSet.contains(ucharFields[0])) {
							ClassSet.add(ucharFields[0]);
							String res = ucharFields[0];

							for (int j = 0; j < versions.size(); j++) {
								//								final Directory index =
								//									FSDirectory
								//										.getDirectory("D:/Documents/Workspace/Defects evolution/Analysis/index"
								//												+ (String) versions
								//													.elementAt(j) + ".dat");
								final Directory index = FSDirectory
										.getDirectory(
												"D:/Documents/Workspace/Defects evolution/Eclipse JDT/Analysis/index"
														+ (String) versions
																.elementAt(j)
														+ ".dat");

								Query q = new QueryParser("Entity", analyser)
										.parse(ucharFields[0]);

								IndexSearcher s = new IndexSearcher(index);

								Hits hits = s.search(q);

								if (hits.length() == 0) {

									res = res + ","

											+ " N/C";

								}
								else {

									for (int k = 0; k < hits.length(); ++k) {

										res = res + ","
										//												+ (String) versions
										//													.elementAt(j)
										//												+ ": "
												+ (hits.doc(k).get("PropTrue"));
									}
								}

							} //end for all the versions 
							out.write(res + '\n');
							out.flush();

						} // end of the if test on ClassSet

					}

				}
				catch (Exception e) {
					e.printStackTrace();
				}
				finally {
					if (fr != null) {
						fr.close();
					}
				}
			}
		}
		out.close();

	}

	/*
	 * Merge all the results
	 * 
	 */
	public static void MergeChangesAndProbs(String dirName, String ProbRepName)
			throws FileNotFoundException, CorruptIndexException, IOException,
			ParseException {
		final Analyzer analyser = new StandardAnalyzer();
		Vector<String> versions = new Vector<String>();

		// I start by collecting all the versions in the directory		
		String header = "";
		final File pathFile = new File(dirName);
		final String[] subPaths = pathFile.list();
		for (int i = 0; i < subPaths.length; i++) {
			final String fileName = dirName + "/" + subPaths[i];
			final File file = new File(fileName);
			int begin = fileName.indexOf("-");

			if (file.isDirectory() && (begin != -1)) {
				versions.addElement(fileName.substring(begin + "-".length()));
			}
		}
		// now I get the directory of the file of interest		
		String FileVer = "";

		final File file = new File(ProbRepName);
		int begin = ProbRepName.indexOf("-");

		if (file.isDirectory() && (begin != -1)) {
			FileVer = ProbRepName.substring(begin + "-".length());
		}

		String[] LoCalVers = new String[] {
				(String) (versions.elementAt(versions.indexOf(FileVer) - 1)),
				FileVer };

		final Writer out = ProxyDisk.getInstance().fileTempOutput(
				"Changes and Blobs/BlobsProb_" + FileVer + ".csv", false);
		// I transformed the versions to fit the one of Stephane's files....

		String TrsfrdVer0 = (LoCalVers[0]
				.substring(LoCalVers[0].indexOf("J_") + "J_".length()))
				.replace('_', '.');
		String TrsfrdVer1 = (LoCalVers[1]
				.substring(LoCalVers[1].indexOf("J_") + "J_".length()))
				.replace('_', '.');

		//System.out.println(TrsfrdVer0+ "-" + TrsfrdVer1);

		DataFileTableModel changes = new DataFileTableModel(
				"D:/Documents/Workspace/Defects evolution/Analysis/metrics of change/Xerces-class-struct-evol/class-evolution-"
						+ TrsfrdVer0 + "-" + TrsfrdVer1 + ".csv");
		for (int i = 0; i < changes.getColumnCount(); i++) {
			header = header + changes.getColumnName(i) + ",";

		}

		out.write(header + "BlobProb " + LoCalVers[0] + ", BlobProb "
				+ LoCalVers[1] + '\n');
		out.flush();

		for (int r = 0; r < changes.getRowCount(); r++) {
			String resLine = "";

			for (int c = 0; c < changes.getColumnCount(); c++) {

				resLine = resLine + (String) changes.getValueAt(r, c) + ",";

			}

			for (int j = 0; j < LoCalVers.length; j++) {
				final Directory index = FSDirectory.getDirectory(
						"D:/Documents/Workspace/Defects evolution/Analysis/index"
								+ LoCalVers[j] + ".dat");

				Query q = new QueryParser("Entity", analyser)
						.parse((String) changes.getValueAt(r, 0));

				IndexSearcher s = new IndexSearcher(index);

				Hits hits = s.search(q);

				if (hits.length() == 0) {

					resLine = resLine

							+ " N/C,";

				}
				else {

					for (int k = 0; k < hits.length(); ++k) {

						resLine = resLine + (hits.doc(k).get("PropTrue")) + ",";
					}
				}

			} //end of the two versions 
			out.write(resLine + '\n');
			out.flush();

		} // end of the table

		out.close();

	}
}
