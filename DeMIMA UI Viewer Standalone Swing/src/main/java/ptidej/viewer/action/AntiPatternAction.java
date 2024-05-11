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
package ptidej.viewer.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import javax.swing.AbstractAction;
import padl.kernel.IAbstractLevelModel;
import ptidej.viewer.ViewerCommons;
import ptidej.viewer.ui.DesktopFrame;
import ptidej.viewer.ui.DesktopPane;
import ptidej.viewer.ui.rulecard.RuleCardEvent;
import ptidej.viewer.utils.Resources;
import ptidej.viewer.utils.Utils;
import sad.rule.creator.utils.DetectionAlgorithmGenerator;
import sad.util.ClassLoader;
import util.io.ProxyConsole;
import util.io.ProxyDisk;
import util.io.ReaderInputStream;

public class AntiPatternAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private static AntiPatternAction UniqueInstance;
	public static AntiPatternAction getInstance() {
		return (AntiPatternAction.UniqueInstance == null) ? AntiPatternAction.UniqueInstance =
			new AntiPatternAction()
				: AntiPatternAction.UniqueInstance;
	}

	private AntiPatternAction() {
	}
	public void actionPerformed(final ActionEvent e) {
		final String action = e.getActionCommand();

		if (action.equals(Resources.NEW_RULECARD)) {
			this.newRuleCard();
		}
		else if (action.equals(Resources.DELETE_RULECARD)) {
			this.deleteDesignDefect();
		}
		else if (action.equals(Resources.DETECT_DESIGN_SMELLS)) {
			this.detectDesignDefect();
		}
	}
	private void deleteDesignDefect() {
		final Set designDefects = DesktopPane.getInstance().getDesignDefects();
		final Iterator iterator = designDefects.iterator();
		while (iterator.hasNext()) {
			final String name = (String) iterator.next();
			DetectionAlgorithmGenerator.deleteDetectionAlgorithm(name);
		}

		DesktopPane.getInstance().setDesignDefects(new HashSet());
		DesktopPane.getInstance().notifyRuleCardChange(new RuleCardEvent());
	}
	private void detectDesignDefect() {
		final Set designDefects = DesktopPane.getInstance().getDesignDefects();
		final Iterator iterator = designDefects.iterator();
		while (iterator.hasNext()) {
			this.detectDesignDefect((String) iterator.next());
		}
	}
	private void detectDesignDefect(final String name) {
		try {
			final ClassLoader loader = new ClassLoader();

			// Force loading from hard-drive using custom SAD ClassLoader
			final Class aClass =
				loader.loadClass("sad.designsmell.detection.repository." + name
						+ "." + name + "Detection");

			// Create a new instance
			final Object instance = aClass.getDeclaredConstructor().newInstance();

			// Set model
			final Method detectMethod =
				aClass.getSuperclass().getMethod(
					"detect",
					new Class[] { IAbstractLevelModel.class });
			detectMethod.invoke(instance, new Object[] { DesktopPane
				.getInstance()
				.getAbstractRepresentationWindow()
				.getSourceModel() });

			// Write result to file
			final String fileName =
				ProxyDisk.getInstance().fileTempOutputString(name + ".ini");

			final Method outputMethod =
				aClass.getSuperclass().getMethod(
					"output",
					new Class[] { PrintWriter.class });
			outputMethod.invoke(instance, new Object[] { new PrintWriter(
				new FileWriter(fileName)) });

			try {
				Properties solutions = new Properties();
				solutions.load(new ReaderInputStream(new FileReader(fileName)));

				ViewerCommons.loadConstraintsData(DesktopPane
					.getInstance()
					.getAbstractRepresentationWindow(), solutions);
			}
			catch (final IOException ioe) {
				ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	private void newRuleCard() {
		final File ruleCardFile =
			Utils.saveFile(
				DesktopFrame.getInstance(),
				"../SAD Rules Creator/rsc/",
				"Save rule file",
				"rules",
				"Rule files");
		if (ruleCardFile != null) {
			if (!ruleCardFile.exists()) {
				DetectionAlgorithmGenerator.outputRuleCard(
					"../SAD Rules Creator/rsc/" + ruleCardFile.getName(),
					"");
			}
			DesktopPane.getInstance().createRuleCardWindow(
				ruleCardFile,
				ruleCardFile.getName());
		}
	}
}
