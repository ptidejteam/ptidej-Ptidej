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
package dram.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

import dram.ui.DRAMAdjacencyMatrix;

/**
 * @author rachedsa
 */
public class GroupingByClass {
	private BufferedReader buf;

	private FileOutputStream fos;

	private OutputStreamWriter osw;

	private File outputFile;

	public GroupingByClass(String filename, String className) {
		//public GroupingByClass(String filename, Vector vectorClassName) {
		try {
			FileReader rd = new FileReader(filename);
			this.buf = new BufferedReader(rd);
			this.outputFile = new File(DRAMAdjacencyMatrix.sortFile);

			this.fos = new FileOutputStream(this.outputFile);
			this.osw = new OutputStreamWriter(this.fos);
			classGroup(className);

			this.buf.close();
			rd.close();
			this.osw.close();
			this.fos.close();
		}
		catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();

		}
	}

	private void classGroup(String className) {
		try {
			int counter = 0;
			String oldtoken = null;
			int v = 0;
			String chaine = null;
			String token;
			String token1;
			int nbre = 0;
			while ((chaine = this.buf.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(chaine, "|");
				token = st.nextToken();
				token1 = st.nextToken();
				st.nextToken();
				if (token1.equals("org.junit.Assert")) {
					System.out.println("ffg");
				}
				if (className.equals(token1)) {
					if (nbre == 0) {
						if (token1.equals(oldtoken)) {

						}
						else {
							counter = counter + 1;
						}
					}
					v = 0;
					if (token.equals("methodEntry0")
						|| token.equals("constructorEntry0")) {
						nbre = nbre + 1;
					}
					System.out.println(chaine + "   " + counter);
					writeLine(chaine, counter);
					//					record = token + " " + st.nextToken() + " "
					//							+ st.nextToken() + " " + counter + '\n';
					//					osw.write(record);

					if (token.equals("methodExit0")
						|| token.equals("constructorExit0")) {
						nbre = nbre - 1;
					}
				}
				else {
					if (nbre == 0) {
						if (v == 0) {
							counter = counter + 1;
						}
					}
					v = 1;
					System.out.println(chaine + "   " + counter);
					writeLine(chaine, counter);
					//					record = token + " " + st.nextToken() + " "
					//							+ st.nextToken() + " " + counter + '\n';
					//					osw.write(record);
				}
				oldtoken = token1;
			}
		}
		catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();

		}
	}

	public String returnFilename() {
		return this.outputFile.getAbsolutePath();
	}
	public void writeLine(String chaine, int count) {
		try {
			String token;
			String token1;
			String token2;
			String token3;
			String token4;
			String record;
			StringTokenizer st1 = new StringTokenizer(chaine, "|");
			token = st1.nextToken();
			token1 = st1.nextToken();
			token2 = st1.nextToken();
			token3 = st1.nextToken();
			token4 = st1.nextToken();
			if (token.equals("constructorEntry0")
				|| token.equals("constructorExit0")) {
				record =
					token1 + " " + token2 + " " + token3 + " " + count + '\n';
			}
			else {
				st1.nextToken();
				record =
					token1 + " " + token2 + " " + token4 + " " + count + '\n';
			}

			System.out.println(chaine);
			this.osw.write(record);
		}
		catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();

		}
	}

	public static void main(final String[] args) {

		try {
			String ff = "../DRAM Tests/groups/diff4Mod.txt";
			new GroupingByClass(ff, "dram.example.testRelation.A");

		}
		catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();

		}
	}
}
