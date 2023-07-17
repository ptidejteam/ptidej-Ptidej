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
package padl.creator.csharpfile.v1.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Splitter {
	public static void main(final String[] args) {
		if (args.length == 2) {
			final Splitter splitter = new Splitter();

			// Maybe, implement a "clean(String)"...
			splitter.split(args[0], args[1]);
		}
		else {
			System.out.println("Not the right number of arguments.");
		}
	}

	private static final int NO_STATE = 0;
	private static final int STATE_TOKENS = 1;

	private int state = Splitter.NO_STATE;

	private void split(
		final String originalFileName,
		final String destinationFileName) {

		try {
			final PrintWriter destinationConstantParser =
				new PrintWriter(new FileWriter(destinationFileName
						+ "Constants.java"), true);
			destinationConstantParser
				.println("package padl.creator.parser;\nimport org.antlr.runtime.BitSet;\npublic interface Constants {");

			final PrintWriter destinationCSharpParser =
				new PrintWriter(new FileWriter(destinationFileName
						+ "CSharpParser.java"), true);

			// output= new BufferedWriter(Ecriturefichier);
			final BufferedReader input =
				new BufferedReader(new FileReader(originalFileName));
			String line;
			while ((line = input.readLine()) != null) {
				if (this.state == Splitter.NO_STATE) {
					if (line
						.startsWith("public class CSharpParser extends Parser {")) {
						destinationCSharpParser
							.write("public class CSharpParser extends Parser implements Constants {");
					}
					else if (line
						.startsWith("    public static final String[] tokenNames = new String[] {")) {
						destinationConstantParser.println(line);

						this.state = Splitter.STATE_TOKENS;
					}
					else if (line.startsWith("    public static final int")
							|| line
								.startsWith("    public static final BitSet")) {
						destinationConstantParser.println(line);
					}
					else {
						destinationCSharpParser.println(line);
					}
				}
				else if (this.state == Splitter.STATE_TOKENS) {
					if (line.startsWith("    };")) {
						destinationConstantParser.println(line);
						this.state = Splitter.NO_STATE;
					}
					else {
						destinationConstantParser.println(line);
					}
				}
				else {
					System.err.println("Unknown state!");
				}
			}
			destinationConstantParser.println("}");
			input.close();
			destinationCSharpParser.close();
			destinationConstantParser.close();
		}
		catch (final FileNotFoundException ex1) {
			// In English...
			System.out.println("Could not find the file !!");
		}

		catch (final IOException ex2) {
			// In English...
			System.out.println("Error in reading the file !!");
		}
	}
}
