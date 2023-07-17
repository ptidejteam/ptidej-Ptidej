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
import java.util.Iterator;
import java.util.Properties;
import javax.swing.JPanel;
import padl.kernel.IFirstClassEntity;
import padl.micropattern.IMicroPatternDetection;
import padl.micropattern.MicroPatternDetectionsRepository;
import ptidej.viewer.ViewerCommons;
import ptidej.viewer.ui.DesktopPane;
import ptidej.viewer.utils.Controls;
import ptidej.viewer.utils.Resources;
import ptidej.viewer.widget.EmbeddedPanel;
import util.help.IHelpURL;
import util.io.ProxyConsole;

public class MicroPatternPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public MicroPatternPanel() {
		final EmbeddedPanel panel = new EmbeddedPanel();
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.NORTH);

		final EmbeddedPanel content = new EmbeddedPanel();
		final MicroPatternDetectionsRepository detectionRepository =
			MicroPatternDetectionsRepository.getInstance();
		final IMicroPatternDetection[] detections =
			detectionRepository.getMicroPatternDetections();

		for (int i = 0; i < detections.length; i++) {
			final IMicroPatternDetection detection = detections[i];
			content.addButton(detection.getName(), new ActionListener() {
				public void actionPerformed(final ActionEvent anActionEvent) {
					ProxyConsole
						.getInstance()
						.normalOutput()
						.print("\nMicro-pattern ");
					ProxyConsole
						.getInstance()
						.normalOutput()
						.println(detection.getName());

					final Properties solutions = new Properties();
					int count = 0;
					final Iterator iterator =
						DesktopPane
							.getInstance()
							.getAbstractRepresentationWindow()
							.getSourceModel()
							.getIteratorOnTopLevelEntities();
					while (iterator.hasNext()) {
						final IFirstClassEntity currentEntity =
							(IFirstClassEntity) iterator.next();
						final boolean result = detection.detect(currentEntity);
						if (result) {
							solutions.setProperty(
								count + ".100." + detection.getName(),
								currentEntity.getDisplayName());
							solutions.setProperty(
								count + ".100.Name",
								detection.getName());
						}
					}
					ViewerCommons.loadConstraintsData(DesktopPane
						.getInstance()
						.getAbstractRepresentationWindow(), solutions);
				}
			},
				false,
				false,
				Controls.getInstance().areMicroPatternsListening(),
				(IHelpURL) detection);
		}

		panel.addCollapsablePanel(
			Resources.MICRO_PATTERNS,
			MicroPatternPanel.class,
			content);
	}
}
