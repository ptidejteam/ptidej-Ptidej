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
package padl.creator.msefile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import padl.creator.msefile.misc.Element;
import padl.event.IModelListener;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.IConstituentOfModel;
import util.io.ProxyConsole;
import util.multilingual.MultilingualManager;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/06/10
 */
public class MSECreator implements ICodeLevelModelCreator {
	private final String fileName;

	public MSECreator(final String[] someFileNames) {
		// TODO: Must deal with multiple MSE files!
		this.fileName = someFileNames[0];
	}
	private ICodeLevelModel buildModel(final Element[] someElements) {
		return new FAMIXBuilder().build(someElements);
	}
	public void create(final ICodeLevelModel aCodeLevelModel) {
		final Element[] elements = this.parseMSEFile();
		final ICodeLevelModel codeLevelModel = this.buildModel(elements);
		this.updateModel(aCodeLevelModel, codeLevelModel);
	}
	private Element[] parseMSEFile() {
		System.out.println(MultilingualManager.getString(
			"PARSING",
			MSECreator.class));

		try {
			final LineNumberReader reader =
				new LineNumberReader(new InputStreamReader(new FileInputStream(
					this.fileName)));

			// Yann 2007/02/01: Classes and ghosts
			// I first populate the model with classes
			// so that I can build ghosts when appropriate.
			final StringBuffer buffer = new StringBuffer();
			String readLine;
			while ((readLine = reader.readLine()) != null) {
				buffer.append(readLine);
				buffer.append('\n');
			}
			reader.close();

			// Yann 2007/06/12: Quotes.
			// I replace double single-quotes by real double quotes
			// to prevent problem when analysing the MSE file.
			this.replace(buffer, "''", "\"");

			final MSEParser parser =
				new MSEParser(new MSELexer(new StringReader(buffer.toString())));
			final Element[] elements = (Element[]) parser.parse().value;

			System.out.println(MultilingualManager.getString(
				"PARSING_DONE",
				MSECreator.class));

			return elements;
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return null;
	}
	private void replace(
		final StringBuffer buffer,
		final String anOldString,
		final String aNewString) {

		System.out.print("Replacing \"");
		System.out.print(anOldString.replaceAll("\\n", "\\n").substring(
			0,
			Math.min(anOldString.length(), 35)));
		System.out.print("\" with \"");
		System.out.print(aNewString.replaceAll("\\n", "\\n").substring(
			0,
			Math.min(aNewString.length(), 35)));
		System.out.print('\"');

		final int length = anOldString.length();
		int pos = 0;
		int changeCount = 0;
		while ((pos = buffer.indexOf(anOldString, pos)) > -1) {
			buffer.replace(pos, pos + length, aNewString);
			pos++;
			changeCount++;
		}
		if (changeCount == 0) {
			System.out.println(" - Not found");
		}
		else {
			System.out.print(" - ");
			System.out.print(changeCount);
			System.out.println(" found");
		}
		System.out.flush();
	}
	private void updateModel(
		final IAbstractLevelModel originModel,
		final IAbstractLevelModel destination) {

		// Yann 2004/12/14: Listeners... again!
		// Once the idiom-level model is built by the parser, I copy
		// all the entities in the given idiom-level model (from the
		// arguments). Thus, I count twice every entities! (Once at
		// creation/addition time, second at copy time.) To solve
		// this problem, I remove temporarily all listeners before
		// copying and then add them back.

		final Iterator listeners = originModel.getIteratorOnModelListeners();
		final List formerListeners = new ArrayList();
		while (listeners.hasNext()) {
			final IModelListener listener = (IModelListener) listeners.next();
			formerListeners.add(listener);
			originModel.removeModelListener(listener);
		}

		final Iterator iterator = destination.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			originModel.addConstituent((IConstituentOfModel) iterator.next());
		}

		originModel.addModelListeners(formerListeners);
	}
}
