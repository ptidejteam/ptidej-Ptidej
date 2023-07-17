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
package sad.rule.creator.misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Writer;
import util.io.ProxyDisk;

/**
 * @author Yann-Gaï¿½l Guï¿½hï¿½neuc
 * @modified by Naouel Moha 2006/01/24
 */
public class GenerateRULEParser {
	public static void main(final String[] args) throws Exception {
		sad.rule.creator.javacup.Main.main(new String[] { "-parser",
				"RULEParser", "-symbols", "RULESymbols", "-interface",
				"-nonterms", "-progress", "rsc/SAD.cup" });

		final File previousParser =
			new File("src/rule/creator/RULEParser.java");
		previousParser.delete();
		final File generatedParser = new File("RULEParser.java");
		generatedParser.renameTo(previousParser);

		final File previousSymbols =
			new File("src/rule/creator/RULESymbols.java");
		previousSymbols.delete();
		final File generatedSymbols = new File("RULESymbols.java");
		generatedSymbols.renameTo(previousSymbols);

		/*
		 * Some code to replace reference to "java_cup.runtime"
		 * with "rule.creator.javacup.runtime".
		 */
		final LineNumberReader reader =
			new LineNumberReader(new InputStreamReader(new FileInputStream(
				"src/rule/creator/RULEParser.java")));
		final StringBuffer buffer = new StringBuffer();
		String readLine;
		while ((readLine = reader.readLine()) != null) {
			buffer.append(readLine);
			buffer.append('\n');
		}
		reader.close();

		final String toBeRemovedString = "java_cup.runtime";
		int pos;
		while ((pos = buffer.indexOf(toBeRemovedString)) > 0) {
			buffer.replace(
				pos,
				pos + toBeRemovedString.length(),
				"rule.creator.javacup.runtime");
		}

		final Writer writer =
			ProxyDisk.getInstance().fileAbsoluteOutput(
				"src/rule/creator/RULEParser.java");
		writer.write(buffer.toString());
		writer.close();
	}
}
