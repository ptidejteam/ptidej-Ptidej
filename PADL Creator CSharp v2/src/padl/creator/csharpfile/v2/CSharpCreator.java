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
package padl.creator.csharpfile.v2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.antlr.runtime.tree.TreeVisitor;
import org.antlr.runtime.tree.TreeVisitorAction;
import padl.creator.csharpfile.v2.parser.ANTLR2PADLInitialReader;
import padl.creator.csharpfile.v2.parser.ANTLR2PADLSecondPassReader;
import padl.creator.csharpfile.v2.parser.csLexer;
import padl.creator.csharpfile.v2.parser.csParser;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import util.io.ProxyConsole;

/**
 * Facade class that manages the Parsing and Converting of CSharp source code
 * to the PADL model.
 */
public class CSharpCreator implements ICodeLevelModelCreator {
	/**
	 * Parses the given File(s) (should be a C# source file) and return it's modelized version.
	 * @param source either the File object representing the C# source file or a File object
	 * representing a directory of C# source files.
	 * @return the PADL model of the given C# source(s) file(s).
	 * @throws CreationException 
	 * @throws java.io.IOException
	 * @throws org.antlr.runtime.RecognitionException
	 */
	public static ICodeLevelModel parse(final String aSourceFileOrDirectory)
			throws CreationException {

		return CSharpCreator.parse(aSourceFileOrDirectory, "C# Model");
	}
	/**
	 * Parses the given File(s) (should be a C# source file) and return it's modelized version.
	 * @param source either the File object representing the C# source file or a File object
	 * representing a directory of C# source files.
	 * @param aName the name given to the returned PADL model.
	 * @return the PADL model of the given C# source(s) file(s).
	 * @throws CreationException 
	 * @throws java.io.IOException
	 * @throws org.antlr.runtime.RecognitionException
	 */
	public static ICodeLevelModel parse(
		final String aSourceFileOrDirectory,
		final String aName) throws CreationException {

		final ICodeLevelModel codeLevelModel =
			Factory.getInstance().createCodeLevelModel(aName);
		codeLevelModel.create(new CSharpCreator(aSourceFileOrDirectory));
		return codeLevelModel;
	}

	private final csLexer lexer = new csLexer();
	private final File source;

	public CSharpCreator(final String aSourceFileOrDirectory) {
		this.source = new File(aSourceFileOrDirectory);
	}
	public void create(final ICodeLevelModel aCodeLevelModel)
			throws CreationException {

		try {
			// 1st pass that identifies the Classes and Interfaces
			final ANTLR2PADLInitialReader detector =
				new ANTLR2PADLInitialReader(aCodeLevelModel);
			if (this.source.isDirectory()) {
				for (final File input : this.source.listFiles()) {
					if (!input.isHidden()) {
						this.readFile(input, detector);
					}
				}
			}
			else {
				this.readFile(this.source, detector);
			}

			// 2nd pass that detects Interaction between Classes and Interfaces
			final ANTLR2PADLSecondPassReader associationConvertor =
				new ANTLR2PADLSecondPassReader(detector.getModel());
			if (this.source.isDirectory()) {
				for (final File input : this.source.listFiles()) {
					if (!input.isHidden()) {
						this.readFile(input, associationConvertor);
					}
				}
			}
			else {
				this.readFile(this.source, associationConvertor);
			}
		}
		catch (final IOException | RecognitionException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			throw new CreationException(e.getMessage());
		}
	}

	private void readFile(final File source, final TreeVisitorAction action)
			throws IOException, RecognitionException {
		// sanity check
		if (source == null || !source.exists() || source.isDirectory()) {
			// Yann 2013/05/10: Must let client know that something is wrong!
			throw new IOException("Cannot find C# source files in " + source);
		}

		final ANTLRInputStream in =
			new ANTLRInputStream(new FileInputStream(source));
		this.lexer.setCharStream(in);

		final csParser p = new csParser(new CommonTokenStream(this.lexer));
		final csParser.compilation_unit_return ast = p.compilation_unit();
		final Tree t = (Tree) ast.getTree();

		final TreeVisitor visitor = new TreeVisitor();
		visitor.visit(t, action);
	}
}
