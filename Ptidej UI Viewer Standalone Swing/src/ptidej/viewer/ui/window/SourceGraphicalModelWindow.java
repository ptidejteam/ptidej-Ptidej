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
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.concurrent.Callable;
import padl.event.IModelListener;
import padl.kernel.IAbstractModel;
import ptidej.ui.awt.AWTCanvas;
import ptidej.ui.canvas.Canvas;
import ptidej.ui.kernel.builder.Builder;
import ptidej.ui.layout.repository.SugiyamaLayout;
import ptidej.viewer.LaidoutModelGraph;
import ptidej.viewer.ui.AbstractRepresentationWindow;
import ptidej.viewer.ui.panel.CanvasPanel;
import ptidej.viewer.utils.SilentModelStatistics;
import ptidej.viewer.widget.ScrollPane;

public class SourceGraphicalModelWindow extends AbstractRepresentationWindow {
	private static final long serialVersionUID = 1L;

	private AWTCanvas awtCanvas;
	private Canvas canvas;
	private final CanvasPanel canvasPanel;
	private IModelListener modelStatistics;
	private ScrollPane scrollPane;
	private LaidoutModelGraph sourceGraph;

	public SourceGraphicalModelWindow() {
		// Common stuff...
		this.modelStatistics = new SilentModelStatistics();

		this.canvasPanel = new CanvasPanel();
		// Yann 2006/08/20: Layout.
		// The CanvasPanel will only hold the AWTCanvas resposible
		// for displaying source model. The AWTCanvas should be
		// positionned at (0,0) so I make sure that the CanvasPanel
		// does not use some fancy layout algorithm...
		this.canvasPanel.setLayout(null);
		this.scrollPane = new ScrollPane(this.canvasPanel);

		// Yann 2006/09/02: Deaf...
		// I force the canvas panel to repaint itself
		// when the scrollbars change because it does
		// not by itself, for some unknown reason...
		this.scrollPane.getHorizontalScrollBar().addAdjustmentListener(
			new AdjustmentListener() {
				public void adjustmentValueChanged(final AdjustmentEvent e) {
					SourceGraphicalModelWindow.this.canvasPanel.repaint();
				}
			});
		this.scrollPane.getVerticalScrollBar().addAdjustmentListener(
			new AdjustmentListener() {
				public void adjustmentValueChanged(final AdjustmentEvent e) {
					SourceGraphicalModelWindow.this.canvasPanel.repaint();
				}
			});

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(this.scrollPane, BorderLayout.CENTER);
	}
	public AWTCanvas getAWTCanvas() {
		return this.awtCanvas;
	}
	public Canvas getCanvas() {
		return this.canvas;
	}
	public IModelListener getModelStatistics() {
		return this.modelStatistics;
	}
	public LaidoutModelGraph getSourceGraph() {
		return this.sourceGraph;
	}
	public IAbstractModel getSourceModel() {
		return this.sourceModel;
	}
	protected void refreshSpecifics() {
		if (this.canvas != null) {
			this.canvas.setVisibleElements(this.getVisibleElements());
			this.canvas.build();

			// Yann 2007/0206: Size matters!
			// I make sure to tell the AWT Canvas
			// of the size of its Canvas so that it
			// paints its content properly.
			this.awtCanvas.setSize(this.canvas.getDimension());
			this.canvasPanel.setSize(this.awtCanvas.getSize());
			this.canvasPanel.setPreferredSize(this.awtCanvas.getSize());
		}
	}
	protected void setSourceModelSpecifics() {
		// Yann 2013/07/30: Useless and broken?
		// What did I use to set, again, the builder here?
		// It is broken because the builder maybe different
		// from the "default" one, for example CPPBuilder.
		//	this.setBuilder(Builder.getCurrentBuilder(this.getPrimitiveFactory()));
		if (this.getBuilder() == null) {
			this.setBuilder(Builder.getCurrentBuilder(this
				.getPrimitiveFactory()));
		}

		this.sourceGraph =
			new LaidoutModelGraph(
				this.getBuilder(),
				this.getSourceModel(),
				new SugiyamaLayout());
		this.sourceGraph.construct();

		this.canvas =
			new Canvas(this.getPrimitiveFactory(), this.getSourceGraph());
		this.canvas.addCanvasListener(new CanvasListenerForRefreshing(
			new Callable() {
				public Object call() throws Exception {
					SourceGraphicalModelWindow.this.revalidate();
					SourceGraphicalModelWindow.this.repaint();
					return null;
				}
			}));

		this.awtCanvas = new AWTCanvas(this.getCanvas());
		this.canvasPanel.removeAll();
		this.canvasPanel.add(this.awtCanvas);
		this.refresh();
	}
}
