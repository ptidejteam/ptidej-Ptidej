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
package parser.defuse;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

// DefinitionAndUse is a singleton class
public class DefinitionAndUseSymboleTable {

	static private DefinitionAndUseSymboleTable DefinitionAndUse_instance =
		null;

	private static TreeMap<char[], TreeMap<char[], Vector<Entity>>> ListOfDefinitionAndUse;

	static public DefinitionAndUseSymboleTable getInstance() {
		if (DefinitionAndUseSymboleTable.DefinitionAndUse_instance == null) {
			DefinitionAndUseSymboleTable.DefinitionAndUse_instance =
				new DefinitionAndUseSymboleTable();
		}

		return DefinitionAndUseSymboleTable.DefinitionAndUse_instance;

	}

	private DefinitionAndUseSymboleTable() {
		DefinitionAndUseSymboleTable.ListOfDefinitionAndUse =
			new TreeMap<char[], TreeMap<char[], Vector<Entity>>>(
				new CharComparator());
	}

	public Iterator<Entity> getEntityDeclarationsOfLine(
		final char[] fileName,
		final char[] lineNumber) {
		return this.getLineNumberOfFile(fileName).get(lineNumber).iterator();
	}

	public TreeMap<char[], Vector<Entity>> getLineNumberOfFile(
		final char[] fileName) {
		return DefinitionAndUseSymboleTable.ListOfDefinitionAndUse
			.get(fileName);
	}

	public TreeMap<char[], TreeMap<char[], Vector<Entity>>> getListOfDefinitionAndUse() {
		return DefinitionAndUseSymboleTable.ListOfDefinitionAndUse;
	}

	public Iterator<ModificationAndUseStatement> getModifications(
		final char[] fileName,
		final char[] lineNumber,
		final char[] entityName) {
		final Iterator<Entity> entities =
			this.getEntityDeclarationsOfLine(fileName, lineNumber);
		final Vector<ModificationAndUseStatement> modificationList =
			new Vector<ModificationAndUseStatement>();
		while (entities.hasNext()) {
			final Entity e = entities.next();
			if (new String(e.getName()).compareTo(new String(entityName)) == 0) {
				for (final ModificationAndUseStatement modification : e
					.getModificationList()) {
					modificationList.add(modification);
				}
			}
		}
		return modificationList.iterator();
	}

	public String toString() {

		System.out.println("\n\n\n -------------------- \n");
		String result = "";

		final Iterator<char[]> fileIt =
			DefinitionAndUseSymboleTable
				.getInstance()
				.getListOfDefinitionAndUse()
				.keySet()
				.iterator();
		char[] currentFile;
		while (fileIt.hasNext()) {
			currentFile = fileIt.next();
			result = result + new String(currentFile) + "\n";

			final Iterator<char[]> lineIt =
				DefinitionAndUseSymboleTable
					.getInstance()
					.getLineNumberOfFile(currentFile)
					.keySet()
					.iterator();
			char[] currentLine;
			while (lineIt.hasNext()) {
				currentLine = lineIt.next();
				result =
					result + "\t" + "line" + new String(currentLine) + ": "
							+ "\n";

				final Iterator<Entity> entityIt =
					DefinitionAndUseSymboleTable
						.getInstance()
						.getEntityDeclarationsOfLine(currentFile, currentLine);
				Entity currentEntity;
				while (entityIt.hasNext()) {
					currentEntity = entityIt.next();
					result = result + currentEntity.toString();
				}
			}
		}
		return result;
	}

}
