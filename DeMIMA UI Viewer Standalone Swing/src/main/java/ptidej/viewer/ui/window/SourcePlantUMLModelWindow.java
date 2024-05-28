package ptidej.viewer.ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
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


public class SourcePlantUMLModelWindow extends AbstractRepresentationWindow{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6957699945451772032L;
	private AWTCanvas awtCanvas;
	private Canvas canvas;
	private final CanvasPanel canvasPanel;
	private final Set setOfEntitiesToDisplay = new HashSet();
	private final Set setOfEntitiesToSelect = new HashSet();
	private LaidoutModelGraph sourceGraph;
	private JTree tree;
	private DefaultMutableTreeNode treeRoot;
	private IModelListener modelStatistics;
	private String filePath;
	private JLabel imageLabel;
	public SourcePlantUMLModelWindow() {

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
							SourcePlantUMLModelWindow.this.tree,
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
		this.canvasPanel = new CanvasPanel();
		final ScrollPane scrollPane = new ScrollPane(this.canvasPanel);
		scrollPane.getViewport().setBackground(Color.WHITE);

		scrollPane.getHorizontalScrollBar().addAdjustmentListener(
			new AdjustmentListener() {
				public void adjustmentValueChanged(final AdjustmentEvent e) {
					SourcePlantUMLModelWindow.this.canvasPanel.repaint();
				}
			});
		scrollPane.getVerticalScrollBar().addAdjustmentListener(
			new AdjustmentListener() {
				public void adjustmentValueChanged(final AdjustmentEvent e) {
					SourcePlantUMLModelWindow.this.canvasPanel.repaint();
				}
			});
        this.imageLabel = new JLabel();
        this.setImage();
        scrollPane.setViewportView(imageLabel);
		final JSplitPane treeAndGraphSplitPane =
			new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new ScrollPane(
				this.tree), scrollPane);
		treeAndGraphSplitPane.setOneTouchExpandable(true);
		treeAndGraphSplitPane.setDividerLocation(200);

    //    this.getContentPane().add(new JScrollPane(imageLabel), BorderLayout.CENTER);
		this.getContentPane().add(treeAndGraphSplitPane, BorderLayout.CENTER);
	
	}
	private final ItemListener DISPLAY_ALL_LISTENER = new ItemListener() {
		public void itemStateChanged(final ItemEvent anEvent) {
			final SourcePlantUMLModelWindow window =
				(SourcePlantUMLModelWindow) DesktopPane
					.getInstance()
					.getAbstractRepresentationWindow();

			final DefaultMutableTreeNode root =
					SourcePlantUMLModelWindow.this.treeRoot;

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

			SourcePlantUMLModelWindow.this.updateWindowDisplay();
		}
	};
    public void setImage() {
        File imageFile = new File(DesktopPane
				.getInstance().getPlantUMLImagePath()); 
        Image image = null;
		try {
			image = ImageIO.read(imageFile);
	        this.imageLabel.setIcon(new ImageIcon(image));
//	        Image scaledImage = image.getScaledInstance(500, 400, Image.SCALE_SMOOTH); // Scaling resolution
//	        this.imageLabel.setIcon(new ImageIcon(scaledImage));
	        this.imageLabel.setHorizontalAlignment(JLabel.CENTER);
	        this.imageLabel.setVerticalAlignment(JLabel.CENTER);
		} catch (IOException e) {
			e.printStackTrace();
		}

        this.revalidate();
        this.repaint();
    }
	private final ItemListener DISPLAY_LISTENER = new ItemListener() {
		public void itemStateChanged(final ItemEvent anEvent) {
			final SourcePlantUMLModelWindow window =
				(SourcePlantUMLModelWindow) DesktopPane
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

			SourcePlantUMLModelWindow.this.updateWindowDisplay();
		}
	};
	private final ItemListener SELECTION_ALL_LISTENER = new ItemListener() {
		public void itemStateChanged(final ItemEvent anEvent) {
			final DefaultMutableTreeNode root =
					SourcePlantUMLModelWindow.this.treeRoot;

			final Enumeration enumeration = root.depthFirstEnumeration();
			while (enumeration.hasMoreElements()) {
				final DefaultMutableTreeNode node =
					(DefaultMutableTreeNode) enumeration.nextElement();
				final HierarchicalTreeCell cell =
					(HierarchicalTreeCell) node.getUserObject();

				final IConstituent sourceConstituent = cell.getConstituent();

				if (sourceConstituent instanceof IFirstClassEntity) {
					final Constituent graphConstituent =
							SourcePlantUMLModelWindow.this.sourceGraph
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
			final SourcePlantUMLModelWindow window =
				(SourcePlantUMLModelWindow) DesktopPane
					.getInstance()
					.getAbstractRepresentationWindow();

			final HierarchicalTreeCellCheckbox checkbox =
				(HierarchicalTreeCellCheckbox) anEvent.getItem();
			final HierarchicalTreeCell cell =
				checkbox.getHierarchicalTreeCell();
			final IConstituent sourceConstituent = cell.getConstituent();
			final Constituent graphConstituent =
				SourcePlantUMLModelWindow.this.sourceGraph
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
	public AWTCanvas getAWTCanvas() {
		return this.awtCanvas;
	}
	protected void updateWindowDisplay() {
		// TODO Auto-generated method stub
		
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
			this.awtCanvas.setSize(this.canvas.getDimension());
			this.canvasPanel.setSize(this.awtCanvas.getSize());
			this.canvasPanel.setPreferredSize(this.awtCanvas.getSize());
		}
	}
	@Override
	protected void setSourceModelSpecifics() {
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
					SourcePlantUMLModelWindow.this.revalidate();
					SourcePlantUMLModelWindow.this.repaint();
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
	private void processSourceModel(
			final DefaultMutableTreeNode aRootNode,
			final AWTCanvas anAWTCanvas,
			final Canvas aCanvas,
			final ModelGraph aModelGraph,
			final IConstituent aConstituent,
			final IFirstClassEntity theParentFirstClassEntity)  {
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

}
