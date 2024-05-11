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
package padl.creator.cppfile.antlr.misc;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2005/08/05
 */
public class GenerateCPPParser {
	public static void main(final String[] args) {
		try {
			org.javacc.parser.Main.main(new String[] {
					"-OUTPUT_DIRECTORY=src/padl/creator/parser/",
					"grammar/cpp.jj" });
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
