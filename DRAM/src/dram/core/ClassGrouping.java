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
import java.util.Vector;

/**
 * @author rachedsa
 */
public class ClassGrouping {

	private BufferedReader buf;
	private FileOutputStream fos;
	private OutputStreamWriter osw;
	private File outputFile;

	public ClassGrouping(String filename, Vector vectorClassName) {

		try {
			FileReader rd = new FileReader(filename);
			this.buf = new BufferedReader(rd);
			this.outputFile = new File(
					"C:\\Documents and Settings\\rachedsa\\Bureau\\toto1.txt");

			this.fos = new FileOutputStream(this.outputFile);
			this.osw = new OutputStreamWriter(this.fos);
			classGroup(vectorClassName);

			this.buf.close();
			rd.close();
			this.osw.close();
			this.fos.close();
		} catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();

		}
	}

	private void classGroup(Vector vectorClassName) {
		try {
			int counter = 0;
			String record;
			String oldtoken = null;
			int v = 0;
			String chaine = null;
			String token;
			while ((chaine = this.buf.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(chaine);
				token = st.nextToken();
				if ((vectorClassName.contains(token))) {
					if (token.equals(oldtoken)) {
						System.out.println(chaine + "   " + counter);
						record = token + " " + st.nextToken() + " "
								+ st.nextToken() + " " + counter + '\n';
						this.osw.write(record);
						v = 1;
					} else {
						if (oldtoken != (null)) {
							v = 1;
							counter = counter + 1;
							System.out.println(chaine + "   " + counter);
							record = token + " " + st.nextToken() + " "
									+ st.nextToken() + " " + counter + '\n';
							this.osw.write(record);
							oldtoken = token;

						} else {
							counter = 1;
							System.out.println(chaine + "   " + counter);
							record = token + " " + st.nextToken() + " "
									+ st.nextToken() + " " + counter + '\n';
							this.osw.write(record);
							oldtoken = token;
							v = 1;
						}
					}
				} else {
					if (v == 1) {
						counter = counter + 1;
						v = 0;
					}
					if (oldtoken == (null)) {
						counter = 1;
					}
					System.out.println(chaine + "   " + counter);
					record = token + " " + st.nextToken() + " "
							+ st.nextToken() + " " + counter + '\n';
					this.osw.write(record);
					oldtoken = token;

				}

			}
		} catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();

		}
	}

	public String returnFilename() {
		return this.outputFile.getAbsolutePath();
	}
}
