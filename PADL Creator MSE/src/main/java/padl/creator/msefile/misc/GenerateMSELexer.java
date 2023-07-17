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
package padl.creator.msefile.misc;

import java.io.File;
import padl.creator.msefile.jlex.JLex;

/**
 * @author Yann-Gaël Guéhéneuc
 */
public class GenerateMSELexer {
	public static void main(final String[] args) throws Exception {
		JLex.main(new String[] { "rsc/MSE.lex" });

		final File previousLexer = new File("src/padl/creator/MSELexer.java");
		previousLexer.delete();
		final File generatedFile = new File("rsc/MSE.lex.java");
		generatedFile.renameTo(previousLexer);
	}
}
