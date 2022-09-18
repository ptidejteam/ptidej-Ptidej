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
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;

/**
 * @author rachedsa
 */
public class FileComparaison {

	private static FileReader rd1;

	private static BufferedReader buf1;

	private static FileReader rd2;

	private static BufferedReader buf2;

	private static FileOutputStream fos1;

	private static OutputStreamWriter osw1;

	private static FileOutputStream fos2;

	private static OutputStreamWriter osw2;

	public static void main(final String[] args) {

		try {

			rd1 =
				new FileReader("C:\\Documents and Settings\\rachedsa\\Bureau\\RJhotDrawSimple.txt");
			//resJunitSERun.txt
			//rd1 = new FileReader(
			//"C:\\Documents and Settings\\rachedsa\\Bureau\\f1.txt");
			//"C:\\Documents and
			// Settings\\rachedsa\\Bureau\\RJhotDrawRectSimple.txt");
			buf1 = new BufferedReader(rd1);

			rd2 =
				new FileReader("C:\\Documents and Settings\\rachedsa\\Bureau\\RJhotDrawRectSimple.txt");
			//resJunitAERun
			//rd2 = new FileReader(
			//"C:\\Documents and Settings\\rachedsa\\Bureau\\f2.txt");
			//"C:\\Documents and
			// Settings\\rachedsa\\Bureau\\RJhotDrawRectCercle.txt");
			buf2 = new BufferedReader(rd2);

			fos1 =
				new FileOutputStream("C:\\Documents and Settings\\rachedsa\\Bureau\\jhtotdraw.txt");
			osw1 = new OutputStreamWriter(fos1);

			fos2 =
				new FileOutputStream("C:\\Documents and Settings\\rachedsa\\Bureau\\jhtotdraw10.txt");
			osw2 = new OutputStreamWriter(fos2);

			//difference();
			differenceSimilar();
			System.out.println("fini ");
			rd1.close();
			buf1.close();
			rd2.close();
			buf2.close();

			osw1.close();
			fos1.close();

			osw2.close();
			fos2.close();

		}
		catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();

		}
	}

	static public void writeinfile(String line, OutputStreamWriter osw) {
		try {
			String writeline;
			if (line != null) {
				writeline = line + '\n';
				osw.write(writeline);
			}

		}
		catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();

		}

	}

	static public void difference() {
		boolean equality = true;
		String line1;
		String line2;
		int linenbre = 0;
		try {
			while (equality) {
				linenbre = linenbre + 1;

				line1 = buf1.readLine();
				line2 = buf2.readLine();

				System.out.println("line1 " + line1);
				System.out.println("line2 " + line2);
				if ((line1 != null)
					&& (line2 != null)
					&& (line1.equals(line2))) {

				}
				else {
					equality = false;
					System.out.println("nbreline " + linenbre);
					writeinfile(line1, osw1);
					writeinfile(line2, osw2);
					for (line1 = buf1.readLine();
						line1 != null;
						line1 = buf1.readLine()) {
						writeinfile(line1, osw1);
					}
					for (line2 = buf2.readLine();
						line2 != null;
						line2 = buf2.readLine()) {
						writeinfile(line2, osw2);
					}
				}

			}
		}
		catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();

		}

	}

	static public void differenceSimilar() {
		boolean equality = true;
		boolean passed = false;
		String namefile1 = "JUnitSE";
		String namefile2 = "JUnitAE";
		int time = 1;
		String line1;
		String line2 = null;
		String linexx = null;
		int linenbreFile1 = 0;
		int linenbreFilexx = 0;
		int linenbreFile2 = 0;
		FileOutputStream fos;
		OutputStreamWriter osw;
		StringBuffer bufferFile2;
		StringBuffer bufferFile1;

		try {
			String filename =
				"C:\\Documents and Settings\\rachedsa\\Bureau\\diffJunit\\diff4.txt";
			//Rjavadrawdiff
			fos = new FileOutputStream(filename);
			osw = new OutputStreamWriter(fos);
			while (equality) {
				bufferFile2 = new StringBuffer();
				bufferFile1 = new StringBuffer();
				//bufferFile1 = null;
				//bufferFile2 = null;
				line1 = buf1.readLine();
				linenbreFile1 = linenbreFile1 + 1;
				if (passed == false) {
					line2 = buf2.readLine();
					linexx = line2;
					linenbreFile2 = linenbreFile2 + 1;
					linenbreFilexx = linenbreFile2;
					buf2.mark(1800046);
				}

				if ((line1 != null)
					&& (line2 != null)
					&& (line1.equals(line2))) {
					passed = false;
				}
				else {
					passed = false;
					equality = false;
					System.out.println("linenbreFile1 " + linenbreFile1);
					System.out.println("linenbreFile2 " + linenbreFile2);

					//					version = version + 1;
					//					filename1 = filename + version + ".txt";
					//					fos = new FileOutputStream(filename1);
					//					osw = new OutputStreamWriter(fos);
					if (line1 != null || line1 != "") {
						bufferFile2.append(namefile2);
						bufferFile2.append("|");
						bufferFile2.append(time);
						bufferFile2.append("|");
						bufferFile2.append(line2);
						bufferFile2.append('\n');
					}
					//writediffinfile(line2, osw);

					for (line2 = buf2.readLine();
						line2 != null;
						line2 = buf2.readLine()) {

						linenbreFile2 = linenbreFile2 + 1;
						if ((line1 != null) && (line1.equals(line2))) {
							equality = true;
							time = time + 1;
							writediffinfile(bufferFile2, osw);
							//bufferFile2 = new StringBuffer();
							bufferFile2 = null;
							break;
						}
						else {
							//writediffinfile(line2, osw);
							if (line2 != null || line2 != "") {
								bufferFile2.append(namefile2);
								bufferFile2.append("|");
								bufferFile2.append(time);
								bufferFile2.append("|");
								bufferFile2.append(line2);
								bufferFile2.append('\n');
							}
						}
					}

					if ((line2 == null) && (line1 != null)) {
						if (buf2.markSupported() == true) {
							buf2.reset();
						}

						line2 = linexx;
						linenbreFile2 = linenbreFilexx;
						//bufferFile2 = new StringBuffer();
						bufferFile2 = null;
						equality = true;
						passed = true;
						if (line1 != null || line1 != "") {
							bufferFile1.append(namefile1);
							bufferFile1.append("|");
							bufferFile1.append(time);
							bufferFile1.append("|");
							bufferFile1.append(line1);
							bufferFile1.append('\n');
						}
						for (line1 = buf1.readLine();
							line1 != null;
							line1 = buf1.readLine()) {
							linenbreFile1 = linenbreFile1 + 1;
							if ((line1.equals(line2))) {
								//equality = true;								
								time = time + 1;
								writediffinfile(bufferFile1, osw);
								//bufferFile1 = new StringBuffer();
								bufferFile1 = null;
								passed = false;
								break;
							}
							else {
								if (line1 != null || line1 != "") {
									bufferFile1.append(namefile1);
									bufferFile1.append("|");
									bufferFile1.append(time);
									bufferFile1.append("|");
									bufferFile1.append(line1);
									bufferFile1.append('\n');
								}
							}
						}
						//writediffinfile(line1, osw);
					}
					if (bufferFile2 != null) {
						time = time + 1;
						writediffinfile(bufferFile2, osw);
					}

					if ((bufferFile1 != null)
						&& (bufferFile1.toString() != null)) {

						time = time + 1;
						writediffinfile(bufferFile1, osw);
					}
					//osw.close();
					//fos.close();

				}

			}
			osw.close();
			fos.close();

		}
		catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();

		}
	}

	//	static public void writediffinfile(String line, OutputStreamWriter osw1) {
	//		try {
	//			String writeline;
	//			if (line != null) {
	//				writeline = line + '\n';
	//				osw1.write(writeline);
	//			}
	//
	//		} catch (Exception ex) {
	//			System.err.println("Syntax error line ");
	//			ex.printStackTrace();
	//
	//		}
	//
	//	}

	static public void writediffinfile(
		StringBuffer buff,
		OutputStreamWriter osw1) {

		try {
			if (buff != null) {
				if (buff.toString() != null) {
					osw1.write(buff.toString());
					//osw1.flush();
				}
			}
		}
		catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();

		}

	}

	static public void writediffinfile(
		String name,
		int tt,
		StringBuffer buff,
		OutputStreamWriter osw1) {
		try {

			if (buff != null) {
				if (buff.toString() != null) {
					osw1.write(buff.toString());

				}
			}
		}
		catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();

		}

	}
}
