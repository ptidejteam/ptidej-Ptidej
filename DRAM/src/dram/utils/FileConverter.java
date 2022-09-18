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
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author rachedsa
 */
public class FileConverter {

	private FileOutputStream fos;

	private Vector vectStack = new Vector();

	private OutputStreamWriter osw;

	private FileOutputStream fosall;

	private OutputStreamWriter oswall;

	private FileReader rd;

	private BufferedReader buf;

	public FileConverter() {
		try {
			this.rd =
				new FileReader("C:\\Documents and Settings\\rachedsa\\Bureau\\RJhotDrawRectCercle.txt");
			this.buf = new BufferedReader(this.rd);

			this.fos =
				new FileOutputStream(
					new File("C:\\Documents and Settings\\rachedsa\\Bureau\\TraceC1.txt"));
			this.osw = new OutputStreamWriter(this.fos);
			this.fosall =
				new FileOutputStream(
					new File("C:\\Documents and Settings\\rachedsa\\Bureau\\TraceCAll1.txt"));
			this.oswall = new OutputStreamWriter(this.fosall);

			fileTreated(this.buf);

			this.osw.close();
			this.fos.close();
			this.oswall.close();
			this.fosall.close();
		}
		catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();

		}
	}

	public void fileTreated(BufferedReader buf) {

		int level = 0;
		String token;
		String token1;
		String token3;
		String evenement = null;

		try {
			String chaine = null;
			while ((chaine = buf.readLine()) != null) {
				Vector vectevent = new Vector();
				vectevent.removeAllElements();
				StringTokenizer st = new StringTokenizer(chaine, "|");
				token = st.nextToken();
				token1 = st.nextToken();
				st.nextToken();
				token3 = st.nextToken();
				st.nextToken();

				if (token.equals("methodEntry0")) {
					if (this.vectStack.size() == 0) {
						level = 1;
						vectevent.addElement(token1);
						vectevent.addElement(token3);
						vectevent.addElement(Integer.toString(level));
						push(this.vectStack, vectevent);
					}
					else {
						Vector vv = new Vector();
						vv =
							(Vector) this.vectStack.elementAt(
								this.vectStack.size() - 1);
						String ss = (String) vv.elementAt(2);
						level = Integer.parseInt(ss);
						level = level + 1;
						vectevent.addElement(token1);
						vectevent.addElement(token3);
						vectevent.addElement(Integer.toString(level));
						push(this.vectStack, vectevent);
					}
					st.nextToken();
					evenement =
						1
							+ ","
							+ level
							+ ","
							+ token1
							+ ","
							+ token3
							+ ","
							+ "In"
							+ '\n';
				}
				if (token.equals("methodExit0")) {
					Vector vv = new Vector();

					vv = (Vector) this.vectStack.elementAt(this.vectStack.size() - 1);
					pop(this.vectStack);
					if ((vv.elementAt(0).equals(token1))
						& (vv.elementAt(1).equals(token3))) {
						String ss = (String) vv.elementAt(2);
						level = Integer.parseInt(ss);
					}
					st.nextToken();
					evenement =
						1
							+ ","
							+ level
							+ ","
							+ token1
							+ ","
							+ token3
							+ ","
							+ "Out"
							+ '\n';

				}
				if (token.equals("constructorEntry0")) {
					continue;
				}
				if (token.equals("constructorExit0")) {
					continue;
				}
				System.out.print(evenement);
				this.osw.write(evenement);
			}
		}
		catch (Exception wri) {
			System.out.println("Impossible d'écrire dans le fichier");
		}
	}
	public void push(Vector vect, String element) {
		vect.addElement(element);
	}
	public void push(Vector vect, Vector vect1) {
		vect.addElement(vect1);
	}
	public void pop(Vector vect) {
		vect.removeElementAt(vect.size() - 1);
	}
	public void print(Vector vect) {
		for (int i = 0; i < vect.size(); i++) {
			System.out.print(vect.elementAt(i) + "    " + i);
		}
		System.out.print("\n");
	}
	public static void main(final String[] args) {
		new FileConverter();
	}
}
