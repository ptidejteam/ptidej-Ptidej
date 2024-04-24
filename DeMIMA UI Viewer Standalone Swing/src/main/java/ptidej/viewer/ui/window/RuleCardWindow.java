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
package ptidej.viewer.ui.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import ptidej.ui.awt.occurrence.PrimitiveFactory;
import ptidej.viewer.ui.AbstractInternalWindow;
import ptidej.viewer.ui.DesktopPane;
import ptidej.viewer.ui.panel.CanvasPanel;
import ptidej.viewer.ui.rulecard.RuleCardEvent;
import ptidej.viewer.widget.ScrollPane;
import sad.rule.creator.RULECreator;
import sad.rule.creator.utils.DetectionAlgorithmGenerator;
import util.io.ProxyConsole;

public final class RuleCardWindow extends AbstractInternalWindow {
	private static final String CLOSE_ACTION = "CANCEL_ACTION";
	private static final String SAVE_ACTION = "SAVE_ACTION";
	private static final long serialVersionUID = 1L;

	private final JPanel canvasPanel;
	private final ScrollPane canvasScrollPane;
	private final JTextArea canvasTextarea;
	private File currentRuleCard;
	private String oldName;

	public RuleCardWindow(final String aRuleCardName) {
		super(aRuleCardName + " [Rulecard]");

		this.canvasPanel = new CanvasPanel();
		this.canvasPanel.setPreferredSize(new Dimension(500, 500));
		this.canvasScrollPane = new ScrollPane(this.canvasPanel);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(this.canvasScrollPane, BorderLayout.CENTER);

		this.canvasTextarea = new JTextArea();
		this.canvasScrollPane.setViewportView(this.canvasTextarea);

		// Button initialisation
		final JPanel buttonPanel = new JPanel();
		final JButton saveButton = new JButton("Save");
		saveButton.setActionCommand(RuleCardWindow.SAVE_ACTION);
		final JButton closeButton = new JButton("Close");
		closeButton.setActionCommand(RuleCardWindow.CLOSE_ACTION);

		final ActionListener actionListener = new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (RuleCardWindow.SAVE_ACTION.equals(e.getActionCommand())) {
					// Yann 2007/02/15: Path!
					// I do not forget to replace any '\' by a '/'
					// to make sure the generated code can compile.
					final String ruleCardPath =
						RuleCardWindow.this.currentRuleCard.getPath().replace(
							'\\',
							'/');

					DetectionAlgorithmGenerator.outputRuleCard(
						ruleCardPath,
						RuleCardWindow.this.canvasTextarea.getText());

					// Yann 2007/03/02: Syntax error
					// Before carring out anything, I make sure
					// that there is a rule card that I can parse.
					final RULECreator ruleCreator =
						new RULECreator(new String[] { ruleCardPath });
					final String[] ruleCards = ruleCreator.parse();
					if (ruleCards.length > 0) {
						DetectionAlgorithmGenerator
							.deleteDetectionAlgorithm(RuleCardWindow.this.oldName);

						final String[] newRuleCard =
							DetectionAlgorithmGenerator
								.generateDetectionAlgorithm(ruleCardPath);
						RuleCardWindow.this.oldName = newRuleCard[0];

						DesktopPane.getInstance().notifyRuleCardChange(
							new RuleCardEvent());
					}
				}
				else if (RuleCardWindow.CLOSE_ACTION.equals(e
					.getActionCommand())) {
					RuleCardWindow.this.doDefaultCloseAction();
				}
			}
		};
		saveButton.addActionListener(actionListener);
		closeButton.addActionListener(actionListener);

		buttonPanel.add(saveButton, BorderLayout.EAST);
		buttonPanel.add(closeButton, BorderLayout.EAST);

		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}
	public void paint(final Graphics g) {
		((ptidej.ui.awt.primitive.PrimitiveFactory) PrimitiveFactory
			.getInstance()).setGraphics(g);
		super.paint(g);
	}
	public final void repaint() {
		// AWT uses validate to cause a container to lay out its subcomponents
		// again after the components it contains have been added to or modified.
		// scrollPane.setSize(c.getSize().width, c.getSize().height);
		this.validate();

		// I repaint the only component of the ScrollPane: the Canvas.
		if (this.canvasPanel != null) {
			this.canvasPanel.validate();
			this.canvasPanel.repaint();
		}
	}
	public void setPreferredSize() {
		this.canvasPanel.revalidate();
	}
	public void setRuleCard(final File ruleCardFile, final String oldName) {
		StringBuffer buffer = new StringBuffer();

		this.currentRuleCard = ruleCardFile;
		this.oldName = oldName;

		try {
			final LineNumberReader reader =
				new LineNumberReader(new InputStreamReader(new FileInputStream(
					ruleCardFile)));

			String readLine;
			while ((readLine = reader.readLine()) != null) {
				buffer.append(readLine);
				buffer.append('\n');
			}
			reader.close();
		}
		catch (final IOException e) {
			// No file found.
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			return;
		}

		this.canvasTextarea.setText(buffer.toString());
	}
}
