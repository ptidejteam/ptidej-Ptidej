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
package dram.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author rachedsa
 */
public class TxtReaderSansExit {
	private FileReader rd;
	private BufferedReader buf;
	private FileOutputStream fos;
	private OutputStreamWriter osw;
	private File outputFile;

	public TxtReaderSansExit() {
		try {
			this.rd =
				new FileReader(
					"C:\\Documents and Settings\\rachedsa\\Bureau\\toto40.txt");
			this.buf = new BufferedReader(this.rd);

			this.outputFile =
				new File(
					"C:\\Documents and Settings\\rachedsa\\Bureau\\totoSansExit42.txt");
			this.fos = new FileOutputStream(this.outputFile);
			this.osw = new OutputStreamWriter(this.fos);

		}
		catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();

		}
	}

	//Cette méthode permet pour un ensemble de classes d'avoir les classes qui ont
	//été activé par l'envoi d'un message(constructorEntry ou methodEntry)

	public void classSearch(Vector classname) {
		try {
			String chaine = null;
			String token;
			String token1;
			Hashtable hashtable = new Hashtable();

			for (int i = 0; i < classname.size(); i++) {
				hashtable.put(classname.elementAt(i), Integer.toString(0));
			}
			while ((chaine = this.buf.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(chaine, "|");
				token = st.nextToken();
				if (token.equals("methodEntry0")
						| token.equals("constructorEntry0")) {
					token1 = st.nextToken();
					String n = (String) hashtable.get(token1);
					if (n != null) {
						int s = Integer.parseInt(n);
						s = s + 1;
						hashtable.put(token1, Integer.toString(s));
						writeLine(chaine);
					}
				}
			}
			this.buf.close();
			this.rd.close();
			this.osw.close();
			this.fos.close();
			printHashtable(hashtable);

		}
		catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();
		}
	}

	//Cette méthode permet pour une classe donné d'avoir tous les messages
	// qui lui sont envoyés a travers ses methodes et son constructeur

	public void methodSearchAll(String classname) {
		try {
			String chaine = null;
			boolean found = false;
			String token;
			int nbreEntry = 0;
			String token1;
			String token2;

			while ((chaine = this.buf.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(chaine, "|");
				token = st.nextToken();
				if (token.equals("methodEntry0")) {
					token1 = st.nextToken();
					token2 = st.nextToken();
					if (token2.equals(classname)) {
						writeLine(chaine);
						found = true;
						nbreEntry = nbreEntry + 1;
						continue;
					}
				}

				if (token.equals("constructorEntry0")) {
					token1 = st.nextToken();
					if (token1.equals(classname)) {
						writeLine(chaine);
						found = true;
						nbreEntry = nbreEntry + 1;
						continue;
					}
				}
				if (found == true) {
					writeLine(chaine);
					if (token.equals("methodExit0")) {
						token1 = st.nextToken();
						token2 = st.nextToken();
						if (token2.equals(classname)) {
							nbreEntry = nbreEntry - 1;
							if (nbreEntry == 0) {
								found = false;
							}
						}
					}
					if (token.equals("constructorExit0")) {
						token1 = st.nextToken();
						if (token1.equals(classname)) {
							nbreEntry = nbreEntry - 1;
							if (nbreEntry == 0) {
								found = false;
							}
						}
					}
				}
			}
			this.buf.close();
			this.rd.close();
			this.osw.close();
			this.fos.close();
		}
		catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();
		}
	}

	//Cette méthode permet pour une classe donné d'avoir les blocks d'information
	//qui sont activés par les méthodes choisies

	public void methodSearch(String classname, Vector methodname) {
		try {
			String chaine = null;
			boolean found = false;
			String token;
			int nbreEntry = 0;
			String token2;
			String token3;

			while ((chaine = this.buf.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(chaine, "|");
				token = st.nextToken();
				if (token.equals("methodEntry0")) {
					st.nextToken();
					token2 = st.nextToken();
					if (token2.equals(classname)) {
						token3 = st.nextToken();
						if ((methodname.contains(token3))) {
							writeLine(chaine);
							found = true;
							nbreEntry = nbreEntry + 1;
							continue;
						}
					}
				}
				if (found == true) {
					//writeLine(chaine);
					if (token.equals("methodExit0")) {
						st.nextToken();
						token2 = st.nextToken();
						if (token2.equals(classname)) {
							token3 = st.nextToken();
							if ((methodname.contains(token3))) {
								nbreEntry = nbreEntry - 1;
								if (nbreEntry == 0) {
									found = false;
								}
							}
						}
					}
					// Pour ne pas ecrire dans le fichier l'ExitMethod
					else {
						writeLine(chaine);
					}
				}
			}
			this.buf.close();
			this.rd.close();
			this.osw.close();
			this.fos.close();
		}
		catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();

		}
	}
	//Cette méthode permet pour une classe donné d'avoir les classes qui lui ont  
	//envoyés un message par les méthodes choisies

	public void methodSearchB(String classname, Vector methodname) {
		try {
			String chaine = null;
			String token;
			String token2;
			String token3;

			while ((chaine = this.buf.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(chaine, "|");
				token = st.nextToken();
				if (token.equals("methodEntry0")) {
					st.nextToken();
					token2 = st.nextToken();
					if (token2.equals(classname)) {
						token3 = st.nextToken();
						if ((methodname.contains(token3))) {
							writeLine(chaine);

						}
					}
				}
			}
			this.buf.close();
			this.rd.close();
			this.osw.close();
			this.fos.close();
		}
		catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();

		}
	}
	//Cette méthode permet pour une classe donné d'avoir les blocks d'information
	//qui sont activés par la méthode choisie

	public void methodSearch(String classname, String methodname) {
		try {
			String chaine = null;
			boolean found = false;
			String token;
			int nbreEntry = 0;
			String token2;
			String token3;

			while ((chaine = this.buf.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(chaine, "|");
				token = st.nextToken();
				if (token.equals("methodEntry0")) {
					st.nextToken();
					token2 = st.nextToken();
					if (token2.equals(classname)) {
						if (st.nextToken().equals(methodname)) {
							writeLine(chaine);
							found = true;
							nbreEntry = nbreEntry + 1;
							continue;
						}
					}
				}
				if (found == true) {
					writeLine(chaine);
					if (token.equals("methodExit0")) {
						st.nextToken();
						token2 = st.nextToken();
						if (token2.equals(classname)) {
							token3 = st.nextToken();
							if (token3.equals(methodname)) {
								nbreEntry = nbreEntry - 1;
								if (nbreEntry == 0) {
									found = false;
								}
							}
						}
					}
				}
			}
			this.buf.close();
			this.rd.close();
			this.osw.close();
			this.fos.close();
		}
		catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();

		}
	}
	//Cette méthode permet pour une classe donné d'avoir les blocks d'information
	//qui sont activés par son constructeur

	public void constructorSearch(String classname) {
		try {
			String chaine = null;
			boolean found = false;
			String token;
			int nbreEntry = 0;
			String token1;
			String token3;

			while ((chaine = this.buf.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(chaine, "|");
				token = st.nextToken();
				if (token.equals("constructorEntry0")) {
					token1 = st.nextToken();
					if (token1.equals(classname)) {
						st.nextToken();
						token3 = st.nextToken();
						Integer.parseInt(token3);
						writeLine(chaine);
						found = true;
						nbreEntry = nbreEntry + 1;
						continue;
					}
				}
				if (found == true) {
					writeLine(chaine);
					if (token.equals("constructorExit0")) {
						token1 = st.nextToken();
						if (token1.equals(classname)) {
							st.nextToken();
							token3 = st.nextToken();
							//if (token3.equals(Integer.toString(ReceiverID))) {
							nbreEntry = nbreEntry - 1;
							if (nbreEntry == 0) {
								found = false;
							}

							//}
						}
					}
				}
			}
			this.buf.close();
			this.rd.close();
			this.osw.close();
			this.fos.close();

		}
		catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();

		}
	}

	//Cette méthode permet pour une classe donné d'avoir les classes qui lui ont  
	//envoyés un message par son constructeur

	public void constructorSearchB(String classname) {
		try {
			String chaine = null;
			String token;
			String token2;

			while ((chaine = this.buf.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(chaine, "|");
				token = st.nextToken();
				if (token.equals("constructorEntry0")) {
					st.nextToken();
					token2 = st.nextToken();
					if (token2.equals(classname)) {
						writeLine(chaine);

					}
				}
			}
			this.buf.close();
			this.rd.close();
			this.osw.close();
			this.fos.close();
		}
		catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();

		}
	}
	public void printHashtable(Hashtable hashtable) {
		Enumeration valeurs = hashtable.elements();
		Enumeration cles = hashtable.keys();
		System.out.println("valeurs de la hashtable : ");

		while (valeurs.hasMoreElements() && cles.hasMoreElements()) {
			System.out.println(cles.nextElement());
			System.out.println(valeurs.nextElement());

		}
	}

	//Cette méthode permet d'ecrire les infos extraites dans un fichier de sortie

	public void writeLine(String chaine) {
		try {
			String token1;
			String token2;
			String record;
			StringTokenizer st1 = new StringTokenizer(chaine, "|");
			st1.nextToken();
			token1 = st1.nextToken();
			token2 = st1.nextToken();
			//record = token1 + " " + token2 + " 1" + " 1 " + token + '\n';
			record = token1 + " " + token2 + " 1" + " 1 " + '\n';
			System.out.println(chaine);
			this.osw.write(record);
		}
		catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();

		}
	}
	public String outputFilename() {
		return this.outputFile.getAbsolutePath();
	}

	public static void main(final String[] args) {
		Vector methodname = new Vector();
		try {
			TxtReaderSansExit txtReaderSansExit = new TxtReaderSansExit();

			//String methodname = "zorg";
			//classname.addElement("dram.example.test.A");
			//txtReader.classname.addElement("dram.example.test.B");
			//txtReader.classname.addElement("dram.example.test.C");
			//txtReader.classSearch(txtReader.classname);			
			//txtReader.constructorSearchB("dram.example.test.B");
			//txtReader.methodSearchAll("dram.example.test.A");

			methodname.addElement("run");
			//methodname.addElement("open");
			//methodname.addElement("createTools");
			//methodname.addElement("createSelectionTool");
			txtReaderSansExit.methodSearch(
				"junit.textui.TestRunner",
				methodname);
			//txtReader.methodSearch("CH.ifa.draw.application.DrawApplication", methodname);
			//txtReader.constructorSearch("junit.framework.TestSuite");
			//txtReader.methodSearchB("dram.example.test.B", methodname);

			txtReaderSansExit.buf.close();
			txtReaderSansExit.rd.close();
			txtReaderSansExit.osw.close();
			txtReaderSansExit.fos.close();
		}
		catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();

		}
	}

}
