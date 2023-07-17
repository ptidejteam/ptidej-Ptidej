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
package padl.creator.csharpfile.v1;

import java.io.File;
import java.io.IOException;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import padl.creator.csharpfile.v1.parser.CSharpLexer;
import padl.creator.csharpfile.v1.parser.CSharpParser;
import padl.csharp.kernel.ICSharpFactory;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.exception.CreationException;

/**
 * @author Gerardo Cepeda Porras
 * @since 2009/04/20
 *
 */
public class CSharpCreator implements ICodeLevelModelCreator {
	public static String CSHARP_PACKAGE_ID = "(C# default package)";

	// A list of files of the project to be parsed
	private String[] fileName;

	public CSharpCreator(final String[] afileName) {
		this.fileName = afileName;
	}

	public void create(final ICodeLevelModel aCodeLevelModel)
			throws CreationException {

		// Parse each file
		for (int i = 0; i < this.fileName.length; i++) {
			try {
				// Create a CSharpLexer that feeds from the file
				File file = new File("");
				CSharpLexer lexer =
					new CSharpLexer(new ANTLRFileStream(file.getAbsolutePath()
							+ File.separatorChar + this.fileName[i]));
				// Create a stream of tokens fed by the lexer
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				// Create a parser that feeds off the token stream
				CSharpParser parser = new CSharpParser(tokens);
				parser.setCodeLevelModel(aCodeLevelModel);
				parser.setFactory((ICSharpFactory) CSharpParser
					.getCodeLevelModel()
					.getFactory());
				// Begin parsing at rule compilationunit
				parser.compilationunit();
				//How to have a list of parameters?? do I need a reInitialize variable?
				//Maybe it's possible to handle the parser here in that case I need a
				//Factory here
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (RecognitionException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}

	}

}
