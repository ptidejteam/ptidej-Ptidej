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
import sad.rule.creator.jlex.JLex;
import util.io.ProxyDisk;

/**
 * @author Yann-Gal Guhneuc
 * @modified by Naouel Moha 2006/01/24
 */
public class GenerateRULELexer {
	public static void main(final String[] args) throws Exception {
		JLex.main(new String[] { "rsc/SAD.lex" });
		final File previousLexer = new File("src/rule/creator/RULELexer.java");
		previousLexer.delete();
		final File generatedFile = new File("rsc/SAD.lex.java");
		generatedFile.renameTo(previousLexer);

		/*
		 * Some code to replace reference to "java_cup.runtime"
		 * with "rule.creator.javacup.runtime".
		 */
		final LineNumberReader reader =
			new LineNumberReader(new InputStreamReader(new FileInputStream(
				"src/rule/creator/RULELexer.java")));
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
				"src/rule/creator/RULELexer.java");
		writer.write(buffer.toString());
		writer.close();
	}
}
