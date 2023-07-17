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
package ptidej.ui.analysis.repository;

import java.awt.FileDialog;
import java.awt.Frame;

import padl.creator.aolfile.AOLCreator;
import padl.kernel.IAbstractModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import ptidej.ui.analysis.IUIAnalysis;
import ptidej.ui.analysis.repository.comparator.Comparator;
import ptidej.ui.analysis.repository.comparator.Manager;
import ptidej.ui.kernel.builder.Builder;
import ptidej.ui.primitive.IPrimitiveFactory;
import util.io.ExtensionBasedFilenameFilter;
import util.io.ProxyConsole;
import util.multilingual.MultilingualManager;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/12/12
 */
public class ModelComparator implements IUIAnalysis {
	public Builder createBuilder(final IPrimitiveFactory aPrimitiveFactory) {
		return ptidej
			.ui
			.analysis
			.repository
			.comparator
			.AnalysesBuilder
			.getCurrentBuilder(
			aPrimitiveFactory);
	}
	public String getName() {
		return MultilingualManager.getString("NAME", ModelComparator.class);
	}
	public IAbstractModel invoke(final IAbstractModel abstractModel) {
		final FileDialog fileDialog = new FileDialog(new Frame());
		fileDialog.setTitle(
			MultilingualManager.getString(
				"DIALOG_TITLE",
				ModelComparator.class));
		fileDialog.setFilenameFilter(
			new ExtensionBasedFilenameFilter(".aol"));
		fileDialog.setFile("*.aol");
		fileDialog.setMode(FileDialog.LOAD);
		fileDialog.setModal(true);
		fileDialog.setVisible(true);
		if (fileDialog.getFile() == null) {
			return abstractModel;
		}
		final String path =
			util.io.Files.normalizePath(
				fileDialog.getDirectory() + fileDialog.getFile());

		final String[] aolFilesArray = new String[] { path };
		final ICodeLevelModel codeLevelModel =
			Factory.getInstance().createCodeLevelModel(
				"Dummy AOL code-level model");
		try {
			codeLevelModel.create(new AOLCreator(aolFilesArray));
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		final IAbstractModel newAbstractModel =
			(IAbstractModel) abstractModel.walk(
				new Comparator(codeLevelModel));

		ProxyConsole.getInstance().normalOutput().println(
			"Number of added elements: "
				+ Manager.getUniqueInstance().getNumberOfAddedElements());
		ProxyConsole.getInstance().normalOutput().println(
			"Number of added entities: "
				+ Manager.getUniqueInstance().getNumberOfAddedEntities());
		ProxyConsole.getInstance().normalOutput().println(
			"Number of added methods: "
				+ Manager.getUniqueInstance().getNumberOfAddedMethods());
		ProxyConsole.getInstance().normalOutput().println(
			"Number of modified entities: "
				+ Manager.getUniqueInstance().getNumberOfModifiedEntities());
		ProxyConsole.getInstance().normalOutput().println(
			"Number of removed elements: "
				+ Manager.getUniqueInstance().getNumberOfRemovedElements());
		ProxyConsole.getInstance().normalOutput().println(
			"Number of removed entities: "
				+ Manager.getUniqueInstance().getNumberOfRemovedEntities());
		ProxyConsole.getInstance().normalOutput().println(
			"Number of remove methods: "
				+ Manager.getUniqueInstance().getNumberOfRemovedMethods());

		return newAbstractModel;
	}
}
