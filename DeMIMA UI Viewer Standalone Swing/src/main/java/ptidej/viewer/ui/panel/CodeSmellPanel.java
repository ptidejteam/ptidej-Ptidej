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
package ptidej.viewer.ui.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;
import javax.swing.JPanel;
import padl.kernel.IAbstractLevelModel;
import ptidej.viewer.ViewerCommons;
import ptidej.viewer.ui.DesktopPane;
import ptidej.viewer.utils.Resources;
import ptidej.viewer.utils.Controls;
import ptidej.viewer.widget.EmbeddedPanel;
import sad.codesmell.detection.CodeSmellDetectionsRepository;
import sad.codesmell.detection.ICodeSmellDetection;
import util.help.IHelpURL;
import util.io.ProxyConsole;
import util.io.ReaderInputStream;

public class CodeSmellPanel extends JPanel {
	private static final long serialVersionUID = 4643065977148057370L;

	public CodeSmellPanel() {
		final EmbeddedPanel panel = new EmbeddedPanel();
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.NORTH);
		final EmbeddedPanel content = new EmbeddedPanel();

		final CodeSmellDetectionsRepository detectionRepository =
			CodeSmellDetectionsRepository.getInstance();
		final ICodeSmellDetection[] detections =
			detectionRepository.getCodeSmellDetections();

		for (int i = 0; i < detections.length; i++) {
			final ICodeSmellDetection detection = detections[i];
			content.addButton(detection.getName(), new ActionListener() {
				public void actionPerformed(final ActionEvent anActionEvent) {
					ProxyConsole
						.getInstance()
						.normalOutput()
						.print("\nCode smell ");
					ProxyConsole
						.getInstance()
						.normalOutput()
						.println(detection.getName());

					try {
						detection.detect((IAbstractLevelModel) DesktopPane
							.getInstance()
							.getAbstractRepresentationWindow()
							.getSourceModel());

						final StringWriter writer = new StringWriter();
						detection.output(new PrintWriter(writer));
						writer.close();

						final Properties solutions = new Properties();
						solutions.load(new ReaderInputStream(new StringReader(
							writer.toString())));

						// Yann 2014/06/20: What's in a name?
						final int numberOfSolutions = solutions.size();
						for (int j = 0; j < numberOfSolutions; j++) {
							solutions.put(j + ".100.Name", detection.getName());
						}

						ViewerCommons.loadConstraintsData(DesktopPane
							.getInstance()
							.getAbstractRepresentationWindow(), solutions);
					}
					catch (final IOException e) {
						e.printStackTrace(ProxyConsole
							.getInstance()
							.errorOutput());
					}
				}
			},
				false,
				false,
				Controls.getInstance().areCodeSmellsListening(),
				(IHelpURL) detection);
		}

		panel.addCollapsablePanel(
			Resources.CODE_SMELLS,
			CodeSmellPanel.class,
			content);
	}
}
