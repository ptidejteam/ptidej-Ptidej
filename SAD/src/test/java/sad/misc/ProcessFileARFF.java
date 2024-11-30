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
package sad.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import util.io.ProxyConsole;
import util.io.ProxyDisk;

/**
 * @author Naouel Moha
 * @since 2008/07/14
 */
public class ProcessFileARFF {

	public static void main(final String[] args) throws Exception {
		final String sourcePath = "./rsc/bugs/rhino.elements-to-bugs.arff";
		final String destPath = "./rsc/bugs/rhino.classes-to-NbBugs.csv";

		final ProcessFileARFF fileARFF =
			new ProcessFileARFF(sourcePath, destPath);

		System.out.println("Parsing: " + sourcePath);
		fileARFF.extractClassesBugs();

		fileARFF.writeOuputFile();

	}

	private final String inputPath;

	private final String outputPath;

	/**
	 * Map<Name of Class ; Number of Bugs>
	 */
	private final Map classesNbBugs;

	public ProcessFileARFF(final String inputPath, final String outputPath) {
		this.inputPath = inputPath;
		this.outputPath = outputPath;
		this.classesNbBugs = new HashMap();
	}

	public void extractClassesBugs() {
		try {
			final BufferedReader buffer =
				new BufferedReader(new FileReader(this.inputPath));
			String line = buffer.readLine();
			while (line != null) {
				final StringTokenizer entry = new StringTokenizer(line, "\"");
				if (entry.hasMoreTokens() && !line.startsWith("@")) {
					final String nameOfEntity = entry.nextToken();
					final int index = nameOfEntity.lastIndexOf(".");
					if (index != -1) {
						final String nameOfClass =
							nameOfEntity.substring(0, index);
						this.extractNameClassCountBug(line, entry, nameOfClass);
					}
					else {
						System.err
							.println("ProcessFileARFF.java : extractNameClasses() : "
									+ "incorrect name of classes!");
					}
				}
				line = buffer.readLine();
			}
			buffer.close();

		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}

	private void extractNameClassCountBug(
		final String line,
		final StringTokenizer entry,
		final String nameOfClass) {

		final String correctNameOfClass = this.verifyNameClass(nameOfClass);
		entry.nextToken(); // token for the entityType

		int nbBugs = 0;
		if (entry.hasMoreTokens()) {
			entry.nextToken(); // token for the bugDescription
			nbBugs = 1;
		}
		else {
			nbBugs = 0;
		}
		if (this.classesNbBugs.containsKey(correctNameOfClass)) {
			final Integer currentNbBugs =
				(Integer) this.classesNbBugs.get(correctNameOfClass);
			final Integer anIntegerNbBugs =
				Integer.valueOf(currentNbBugs.intValue() + nbBugs);
			this.classesNbBugs.put(correctNameOfClass, anIntegerNbBugs);
		}
		else {
			final Integer anIntegerNbBugs = Integer.valueOf(nbBugs);
			this.classesNbBugs.put(correctNameOfClass, anIntegerNbBugs);
		}

	}

	public Map getClassesNbBugs() {
		return this.classesNbBugs;
	}

	private String verifyNameClass(final String nameOfClass) {
		String correctNameOfClass = null;

		if (nameOfClass.indexOf("(") > -1) {
			// Example of this case:
			// org.mozilla.javascript.IRFactory.createLoop(Node
			final int index1 = nameOfClass.lastIndexOf(".");
			correctNameOfClass = nameOfClass.substring(0, index1);
		}
		else if (nameOfClass.indexOf("$") > -1) {
			final int index2 = nameOfClass.indexOf("$");
			correctNameOfClass = nameOfClass.substring(0, index2);
		}
		else {
			correctNameOfClass = nameOfClass;
		}
		return correctNameOfClass;
	}

	public void writeOuputFile() {
		try {
			final File outputfile = new File(this.outputPath);
			if (!outputfile.exists()) {
				outputfile.createNewFile();
			}

			final PrintWriter aWriter =
				new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
					this.outputPath));
			final Iterator iter = this.classesNbBugs.keySet().iterator();
			while (iter.hasNext()) {
				final String nameOfClass = (String) iter.next();
				final Integer nbBugs =
					(Integer) this.classesNbBugs.get(nameOfClass);
				aWriter.println(nameOfClass + "; " + nbBugs);
			}
			aWriter.close();

		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}

}
