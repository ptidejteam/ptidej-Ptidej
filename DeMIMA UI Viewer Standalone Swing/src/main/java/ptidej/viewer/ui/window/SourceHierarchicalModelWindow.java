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
import java.awt.Color;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreeCellRenderer;
import padl.event.IModelListener;
import padl.kernel.IAbstractModel;
import padl.kernel.IConstituent;
import padl.kernel.IContainer;
import padl.kernel.IFirstClassEntity;
import ptidej.ui.awt.AWTCanvas;
import ptidej.ui.canvas.Canvas;
import ptidej.ui.kernel.Constituent;
import ptidej.ui.kernel.ModelGraph;
import ptidej.ui.kernel.builder.Builder;
import ptidej.ui.layout.repository.SugiyamaLayout;
import ptidej.viewer.LaidoutModelGraph;
import ptidej.viewer.event.IGraphModelListener;
import ptidej.viewer.event.SourceAndGraphModelEvent;
import ptidej.viewer.ui.AbstractRepresentationWindow;
import ptidej.viewer.ui.DesktopPane;
import ptidej.viewer.ui.panel.CanvasPanel;
import ptidej.viewer.utils.SilentModelStatistics;
import ptidej.viewer.widget.HierarchicalTreeCell;
import ptidej.viewer.widget.HierarchicalTreeCellCheckbox;
import ptidej.viewer.widget.HierarchicalTreeCellEditor;
import ptidej.viewer.widget.HierarchicalTreeCellRenderer;
import ptidej.viewer.widget.ScrollPane;

public class SourceHierarchicalModelWindow extends AbstractRepresentationWindow {
	private static final long serialVersionUID = 1526202856491831532L;
	private AWTCanvas awtCanvas;
	private Canvas canvas;
	private final CanvasPanel canvasPanel;
	private final ItemListener DISPLAY_ALL_LISTENER = new ItemListener() {
		public void itemStateChanged(final ItemEvent anEvent) {
			final SourceHierarchicalModelWindow window =
				(SourceHierarchicalModelWindow) DesktopPane
					.getInstance()
					.getAbstractRepresentationWindow();

			final DefaultMutableTreeNode root =
				SourceHierarchicalModelWindow.this.treeRoot;

			// I do not forget to deselect the other checkboxes.
			if (anEvent.getStateChange() == ItemEvent.DESELECTED) {
				((HierarchicalTreeCell) root.getUserObject())
					.setSelectedWithoutNotification(false);
				((HierarchicalTreeCell) root.getUserObject())
					.setSpecialedWithoutNotification(false);
			}

			final Enumeration enumeration = root.depthFirstEnumeration();
			while (enumeration.hasMoreElements()) {
				final DefaultMutableTreeNode node =
					(DefaultMutableTreeNode) enumeration.nextElement();
				final HierarchicalTreeCell cell =
					(HierarchicalTreeCell) node.getUserObject();

				final IConstituent sourceConstituent = cell.getConstituent();

				if (sourceConstituent instanceof IFirstClassEntity) {
					if (anEvent.getStateChange() == ItemEvent.SELECTED) {
						cell.setDisplayedWithoutNotification(true);
						window.setOfEntitiesToDisplay.add(sourceConstituent);
					}
					else if (anEvent.getStateChange() == ItemEvent.DESELECTED) {
						cell.setDisplayedWithoutNotification(false);
						window.setOfEntitiesToDisplay.remove(sourceConstituent);

						cell.setSelectedWithoutNotification(false);
						window.setOfEntitiesToSelect.remove(sourceConstituent);
					}
				}
			}

			SourceHierarchicalModelWindow.this.updateWindowDisplay();
		}
	};
	private final ItemListener DISPLAY_LISTENER = new ItemListener() {
		public void itemStateChanged(final ItemEvent anEvent) {
			final SourceHierarchicalModelWindow window =
				(SourceHierarchicalModelWindow) DesktopPane
					.getInstance()
					.getAbstractRepresentationWindow();

			final HierarchicalTreeCellCheckbox checkbox =
				(HierarchicalTreeCellCheckbox) anEvent.getItem();
			final HierarchicalTreeCell cell =
				checkbox.getHierarchicalTreeCell();
			final IConstituent sourceConstituent = cell.getConstituent();

			if (anEvent.getStateChange() == ItemEvent.SELECTED) {
				window.setOfEntitiesToDisplay.add(sourceConstituent);
			}
			else if (anEvent.getStateChange() == ItemEvent.DESELECTED) {
				window.setOfEntitiesToDisplay.remove(sourceConstituent);

				cell.setSelectedWithoutNotification(false);
				window.setOfEntitiesToSelect.remove(sourceConstituent);
			}

			// TODO: Why not add/remove constituent directly from the model?
			//	final ICodeLevelModel newCodeLevelModel =
			//		Factory.getInstance().createCodeLevelModel("");
			//
			//	final Iterator iterator =
			//		window.listOfSelectedEntities.iterator();
			//	while (iterator.hasNext()) {
			//		final IConstituent constituent = (IConstituent) iterator.next();
			//		window.addHierachyOfEntities(newCodeLevelModel, constituent);
			//	}

			SourceHierarchicalModelWindow.this.updateWindowDisplay();
		}
	};
	private IModelListener modelStatistics;
	private final ItemListener SELECTION_ALL_LISTENER = new ItemListener() {
		public void itemStateChanged(final ItemEvent anEvent) {
			final DefaultMutableTreeNode root =
				SourceHierarchicalModelWindow.this.treeRoot;

			final Enumeration enumeration = root.depthFirstEnumeration();
			while (enumeration.hasMoreElements()) {
				final DefaultMutableTreeNode node =
					(DefaultMutableTreeNode) enumeration.nextElement();
				final HierarchicalTreeCell cell =
					(HierarchicalTreeCell) node.getUserObject();

				final IConstituent sourceConstituent = cell.getConstituent();

				if (sourceConstituent instanceof IFirstClassEntity) {
					final Constituent graphConstituent =
						SourceHierarchicalModelWindow.this.sourceGraph
							.getEntity(sourceConstituent.getDisplayID());
					if (graphConstituent != null) {
						if (anEvent.getStateChange() == ItemEvent.SELECTED) {
							cell.setSelectedWithoutNotification(true);
							graphConstituent.isSelected(true);
						}
						else if (anEvent.getStateChange() == ItemEvent.DESELECTED) {
							cell.setSelectedWithoutNotification(false);
							graphConstituent.isSelected(false);
						}
					}
				}
			}
		}
	};
	private final ItemListener SELECTION_LISTENER = new ItemListener() {
		public void itemStateChanged(final ItemEvent anEvent) {
			final SourceHierarchicalModelWindow window =
				(SourceHierarchicalModelWindow) DesktopPane
					.getInstance()
					.getAbstractRepresentationWindow();

			final HierarchicalTreeCellCheckbox checkbox =
				(HierarchicalTreeCellCheckbox) anEvent.getItem();
			final HierarchicalTreeCell cell =
				checkbox.getHierarchicalTreeCell();
			final IConstituent sourceConstituent = cell.getConstituent();
			final Constituent graphConstituent =
				SourceHierarchicalModelWindow.this.sourceGraph
					.getEntity(sourceConstituent.getDisplayID());

			if (anEvent.getStateChange() == ItemEvent.SELECTED) {
				window.setOfEntitiesToSelect.add(sourceConstituent);
				if (graphConstituent != null) {
					graphConstituent.isSelected(true);
				}
			}
			else if (anEvent.getStateChange() == ItemEvent.DESELECTED) {
				window.setOfEntitiesToSelect.remove(sourceConstituent);
				if (graphConstituent != null) {
					graphConstituent.isSelected(false);
				}
			}
		}
	};
	private final Set setOfEntitiesToDisplay = new HashSet();
	private final Set setOfEntitiesToSelect = new HashSet();
	private LaidoutModelGraph sourceGraph;
	private JTree tree;
	private DefaultMutableTreeNode treeRoot;

	public SourceHierarchicalModelWindow() {
		// Common stuff...
		this.modelStatistics = new SilentModelStatistics();

		this.getContentPane().setLayout(new BorderLayout());
		this.treeRoot = new DefaultMutableTreeNode();
		this.treeRoot.setUserObject(new JLabel(""));
		this.tree = new JTree(this.treeRoot);
		this.tree.addTreeWillExpandListener(new TreeWillExpandListener() {
			public void treeWillCollapse(
				final TreeExpansionEvent aTreeExpansionEvent)
					throws ExpandVetoException {

				if (aTreeExpansionEvent.getPath().getPathCount() < 2) {
					throw new ExpandVetoException(new TreeExpansionEvent(
						SourceHierarchicalModelWindow.this.tree,
						null));
				}
			}
			public void treeWillExpand(TreeExpansionEvent event)
					throws ExpandVetoException {
			}
		});
		final TreeCellRenderer renderer = new HierarchicalTreeCellRenderer();
		this.tree.setCellRenderer(renderer);
		this.tree.setCellEditor(new HierarchicalTreeCellEditor());
		this.tree.setEditable(true);
		//	treeAndGraphSplitPane.add(
		//		new ScrollPane(this.tree),
		//		new PercentLayout.Constraint(0, 0, 40, 100));
		//	treeAndGraphPanel.add(new ScrollPane(this.tree), BorderLayout.WEST);

		this.canvasPanel = new CanvasPanel();
		// Yann 2006/08/20: Layout.
		// The CanvasPanel will only hold the AWTCanvas responsible
		// for displaying source model. The AWTCanvas should be
		// positioned at (0,0) so I make sure that the CanvasPanel
		// does not use some fancy layout algorithm...
		// this.canvasPanel.setLayout(null);
		final ScrollPane scrollPane = new ScrollPane(this.canvasPanel);
		scrollPane.getViewport().setBackground(Color.WHITE);

		// Yann 2006/09/02: Deaf...
		// I force the canvas panel to repaint itself
		// when the scrollbars change because it does
		// not by itself, for some unknown reason...
		scrollPane.getHorizontalScrollBar().addAdjustmentListener(
			new AdjustmentListener() {
				public void adjustmentValueChanged(final AdjustmentEvent e) {
					SourceHierarchicalModelWindow.this.canvasPanel.repaint();
				}
			});
		scrollPane.getVerticalScrollBar().addAdjustmentListener(
			new AdjustmentListener() {
				public void adjustmentValueChanged(final AdjustmentEvent e) {
					SourceHierarchicalModelWindow.this.canvasPanel.repaint();
				}
			});
		//	treeAndGraphSplitPane.add(scrollPane, new PercentLayout.Constraint(
		//		40,
		//		0,
		//		60,
		//		100));
		//	treeAndGraphPanel.add(scrollPane, BorderLayout.CENTER);

		final JSplitPane treeAndGraphSplitPane =
			new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new ScrollPane(
				this.tree), scrollPane);
		treeAndGraphSplitPane.setOneTouchExpandable(true);
		treeAndGraphSplitPane.setDividerLocation(200);

		this.getContentPane().add(treeAndGraphSplitPane, BorderLayout.CENTER);

		//	final Panel controlPanel = new Panel(new FlowLayout());
		//	final Button selectAllButton = new Button("Select All");
		//	selectAllButton.addActionListener(new ActionListener() {
		//		public void actionPerformed(final ActionEvent e) {
		//			tree.setSelectionInterval(1, treeRoot.getLeafCount());
		//		}
		//	});
		//	controlPanel.add(selectAllButton);
		//	final Button unselectAllButton = new Button("Unselect All");
		//	unselectAllButton.addActionListener(new ActionListener() {
		//		public void actionPerformed(final ActionEvent e) {
		//			tree.setSelectionInterval(0, 0);
		//		}
		//	});
		//	controlPanel.add(unselectAllButton);
		//	this.getContentPane().add(
		//		new ScrollPane(controlPanel),
		//		BorderLayout.SOUTH);
	}
	//	private void addHierachyOfEntity(
	//		final ICodeLevelModel aCodeLevelModel,
	//		final IConstituent aConstituent) {
	//
	//		try {
	//			if (!aCodeLevelModel.doesContainConstituentWithID(aConstituent.getID())) {
	//				aCodeLevelModel.addConstituent(aConstituent);
	//			}
	//
	//			// I add recursively all super-entities.
	//			final Iterator iterator =
	//				((IEntity) aConstituent).listOfInheritedEntities().iterator();
	//			while (iterator.hasNext()) {
	//				final IEntity superEntity = (IEntity) iterator.next();
	//				this.addHierachyOfEntities(aCodeLevelModel, superEntity);
	//			}
	//
	//			if (aConstituent instanceof IClass) {
	//				final Iterator iterator2 =
	//					((IClass) aConstituent)
	//						.listOfImplementedEntities()
	//						.iterator();
	//				while (iterator2.hasNext()) {
	//					final IEntity superEntity = (IEntity) iterator2.next();
	//					this.addHierachyOfEntities(aCodeLevelModel, superEntity);
	//				}
	//			}
	//		}
	//		catch (final ModelDeclarationException e) {
	//			e.printStackTrace();
	//		}
	//	}
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
	private void processSourceModel(
		final DefaultMutableTreeNode aRootNode,
		final AWTCanvas anAWTCanvas,
		final Canvas aCanvas,
		final ModelGraph aModelGraph,
		final IConstituent aConstituent,
		final IFirstClassEntity theParentFirstClassEntity) {

		final DefaultMutableTreeNode node =
			new DefaultMutableTreeNode(new HierarchicalTreeCell(
				this.getBuilder(),
				anAWTCanvas,
				aCanvas,
				aModelGraph,
				aConstituent,
				theParentFirstClassEntity,
				this.DISPLAY_LISTENER,
				this.SELECTION_LISTENER));
		aRootNode.add(node);

		if (aConstituent instanceof IContainer) {
			final Iterator iterator =
				((IContainer) aConstituent).getIteratorOnConstituents();
			while (iterator.hasNext()) {
				final IConstituent constituent = (IConstituent) iterator.next();

				this.processSourceModel(
					node,
					anAWTCanvas,
					aCanvas,
					aModelGraph,
					constituent,
					theParentFirstClassEntity);
			}
		}
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
		// Yann 2013/05/23: Builder!
		// If there is no builder, I use the default one...
		// TODO Why should I care about a default builder
		// when there is nothing to build or display...
		if (this.getBuilder() == null) {
			this.setBuilder(Builder.getCurrentBuilder(this
				.getPrimitiveFactory()));
		}
		this.sourceGraph =
			new LaidoutModelGraph(
				this.getBuilder(),
				this.sourceModel,
				this.setOfEntitiesToDisplay,
				new SugiyamaLayout());
		this.sourceGraph.construct();
		this.sourceGraph.addSelectionListener(new SelectionListener(
			this.treeRoot));
		this.canvas = new Canvas(this.getPrimitiveFactory(), this.sourceGraph);
		this.canvas.addCanvasListener(new CanvasListenerForRefreshing(
			new Callable() {
				public Object call() throws Exception {
					SourceHierarchicalModelWindow.this.revalidate();
					SourceHierarchicalModelWindow.this.repaint();
					return null;
				}
			}));
		this.awtCanvas = new AWTCanvas(this.canvas);
		this.canvasPanel.removeAll();
		this.canvasPanel.add(this.awtCanvas);

		this.treeRoot.setUserObject(new HierarchicalTreeCell(
			this.getBuilder(),
			this.sourceModel,
			this.DISPLAY_ALL_LISTENER,
			this.SELECTION_ALL_LISTENER));
		this.treeRoot.removeAllChildren();

		final Iterator iterator =
			this.sourceModel.getIteratorOnTopLevelEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iterator.next();
			this.processSourceModel(
				this.treeRoot,
				this.awtCanvas,
				this.canvas,
				this.sourceGraph,
				firstClassEntity,
				firstClassEntity);
		}
		this.tree.expandRow(0);

		// Yann 2013/09/28: Availability!
		// I should not forget to let everyone know 
		// that a graph is now available. It is kind
		// of stupid but it seems the best to do...
		// Yann 2014/03/28: One year later!
		// Actually, it would be better to notify the 
		// listeners at more fine-grained places but 
		// that will do for the moment...
		final Iterator iteratorOnGraphModelListeners =
			DesktopPane.getInstance().getIteratorOnGraphModelListeners();
		while (iteratorOnGraphModelListeners.hasNext()) {
			final IGraphModelListener graphModelListener =
				(IGraphModelListener) iteratorOnGraphModelListeners.next();
			graphModelListener
				.graphModelAvailable(new SourceAndGraphModelEvent(DesktopPane
					.getInstance()
					.getAbstractRepresentationWindow()));
		}
	}
	private void updateWindowDisplay() {
		// Yann 07/09/27: Builder!
		// I don't forget to initialise the Builder
		// because it keeps track of links between
		// abstract model entities and graph entities!
		final Builder builder = this.getBuilder();
		builder.initialize();

		this.sourceGraph.reset(
			builder,
			this.sourceModel,
			this.setOfEntitiesToDisplay,
			this.setOfEntitiesToSelect,
			new SugiyamaLayout());
		this.sourceGraph.construct();

		// Yann 2007/10/01: Adding occurrences...
		// I don't forget to add the occurrences to the new
		// model graph of the new code level model.
		// Yann 2014/05/09: Useless!
		// It was weird anyways to *create* something
		// while refreshing the display, wasn't it?
		//	ViewerCommons.createGroupSolutions(this);

		this.refresh();
	}
}
