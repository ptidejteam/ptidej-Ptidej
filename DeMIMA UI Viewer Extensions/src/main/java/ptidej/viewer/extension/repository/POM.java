/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.viewer.extension.repository;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import padl.kernel.IAbstractModel;
import ptidej.viewer.IRepresentation;
import ptidej.viewer.event.SourceAndGraphModelEvent;
import ptidej.viewer.extension.IViewerExtension;
import util.help.IHelpURL;

/**
 * @author Yann-Gaël Guéhéneuc 
 * @since  2004/03/23
 */
public final class POM extends Frame implements IViewerExtension, IHelpURL {
	private static final long serialVersionUID = 1L;
	private static final POMCalculator pomCalculator = new POMCalculator();
	private TextArea textArea;

	public POM() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosed(final WindowEvent e) {
				POM.this.setVisible(false);
			}
			public void windowClosing(final WindowEvent e) {
				this.windowClosed(e);
			}
		});
	}
	private void computeMetrics(final IAbstractModel model) {
		model.walk(pomCalculator);
		this.textArea.setText((String) pomCalculator.getResult());
	}
	public String getName() {
		return "POM-based metrics";
	}
	public void invoke(final IRepresentation aRepresentation) {
		// Positioning and display.
		this.setSize(new Dimension(300, 500));
		this.setLocation(this.getX() + 50, this.getY() + 50);
		this.setTitle(this.getName());

		// I build the ScrollPane into which all the buttons and labels
		// will be displayed.
		final ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBackground(Color.lightGray);
		scrollPane.getHAdjustable().setUnitIncrement(20);
		scrollPane.getVAdjustable().setUnitIncrement(20);
		this.add(scrollPane);

		this.textArea = new TextArea();
		scrollPane.add(this.textArea);

		this.computeMetrics(aRepresentation.getSourceModel());

		this.setVisible(true);
	}
	public void sourceModelAvailable(SourceAndGraphModelEvent aViewerEvent) {
		if (this.isVisible()) {
			this.computeMetrics(aViewerEvent
				.getRepresentation()
				.getSourceModel());

			// I must refresh the window.
			this.validate();
			this.repaint();
		}
	}
	public void sourceModelChanged(final SourceAndGraphModelEvent aViewerEvent) {
		this.sourceModelAvailable(aViewerEvent);
	}
	public void sourceModelUnavailable() {
		// Nothing to do...
	}
	public String getHelpURL() {
		return "http://www.ptidej.net/publications/documents/WCRE04.doc.pdf";
	}
}
