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
import javax.swing.JPanel;
import padl.motif.visitor.IMotifGenerator;
import padl.motif.visitor.IMotifWalker;
import padl.visitor.IGenerator;
import padl.visitor.IWalker;
import ptidej.viewer.ui.DesktopPane;
import ptidej.viewer.utils.Controls;
import ptidej.viewer.widget.EmbeddedPanel;
import util.io.ProxyConsole;

public class VisitorPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public VisitorPanel() {
		final EmbeddedPanel panel = new EmbeddedPanel();
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.NORTH);

		final EmbeddedPanel contentPADLWalkers = new EmbeddedPanel();
		final IWalker[] walkers =
			padl.visitor.VisitorsRepository.getInstance().getWalkers();
		for (int i = 0; i < walkers.length; i++) {
			final IWalker walker = walkers[i];
			contentPADLWalkers.addButton(
				walker.getName(),
				new ActionListener() {
					public void actionPerformed(final ActionEvent anActionEvent) {
						walker.reset();
						DesktopPane
							.getInstance()
							.getAbstractRepresentationWindow()
							.getSourceModel()
							.walk(walker);
						ProxyConsole
							.getInstance()
							.normalOutput()
							.println(walker.getResult());
					}
				},
				false,
				false,
				Controls.getInstance().areVisitorsListening());
		}
		panel.addCollapsablePanel("PADL Walkers", contentPADLWalkers);

		final EmbeddedPanel contentPADLGenerators = new EmbeddedPanel();
		final IGenerator[] generators =
			padl.visitor.VisitorsRepository.getInstance().getGenerators();
		for (int i = 0; i < generators.length; i++) {
			final IGenerator generator = generators[i];
			contentPADLGenerators.addButton(
				generator.getName(),
				new ActionListener() {
					public void actionPerformed(final ActionEvent anActionEvent) {
						generator.reset();
						DesktopPane
							.getInstance()
							.getAbstractRepresentationWindow()
							.getSourceModel()
							.generate(generator);
						ProxyConsole
							.getInstance()
							.normalOutput()
							.println(generator.getCode());
					}
				},
				false,
				false,
				Controls.getInstance().areVisitorsListening());
		}
		panel.addCollapsablePanel("PADL Generators", contentPADLGenerators);

		final EmbeddedPanel contentMotifWalkers = new EmbeddedPanel();
		final IMotifWalker[] motifWalkers =
			padl.motif.visitor.VisitorsRepository.getInstance().getWalkers();
		for (int i = 0; i < motifWalkers.length; i++) {
			final IMotifWalker walker = motifWalkers[i];
			contentMotifWalkers.addButton(
				walker.getName(),
				new ActionListener() {
					public void actionPerformed(final ActionEvent anActionEvent) {
						walker.reset();
						DesktopPane
							.getInstance()
							.getAbstractRepresentationWindow()
							.getSourceModel()
							.walk(walker);
						ProxyConsole
							.getInstance()
							.normalOutput()
							.println(walker.getResult());
					}
				},
				false,
				false,
				Controls.getInstance().areVisitorsListening());
		}
		panel.addCollapsablePanel("Motif Walkers", contentMotifWalkers);

		final EmbeddedPanel contentMotifGenerators = new EmbeddedPanel();
		final IMotifGenerator[] motifGenerators =
			padl.motif.visitor.VisitorsRepository.getInstance().getGenerators();
		for (int i = 0; i < motifGenerators.length; i++) {
			final IMotifGenerator generator = motifGenerators[i];
			contentMotifGenerators.addButton(
				generator.getName(),
				new ActionListener() {
					public void actionPerformed(final ActionEvent anActionEvent) {
						generator.reset();
						DesktopPane
							.getInstance()
							.getAbstractRepresentationWindow()
							.getSourceModel()
							.generate(generator);
						ProxyConsole
							.getInstance()
							.normalOutput()
							.println(generator.getCode());
					}
				},
				false,
				false,
				Controls.getInstance().areVisitorsListening());
		}
		panel.addCollapsablePanel("Motif Generators", contentMotifGenerators);
	}
}
