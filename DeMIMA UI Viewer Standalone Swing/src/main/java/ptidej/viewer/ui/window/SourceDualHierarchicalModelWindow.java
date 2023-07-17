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

import java.awt.Color;
import java.awt.Point;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import javax.swing.JComponent;
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
import padl.kernel.IClass;
import padl.kernel.IConstituent;
import padl.kernel.IContainer;
import padl.kernel.IFilter;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import padl.kernel.IInterfaceImplementer;
import padl.util.NotFilter;
import ptidej.ui.awt.AWTCanvas;
import ptidej.ui.canvas.Canvas;
import ptidej.ui.canvas.event.CanvasEvent;
import ptidej.ui.canvas.event.ICanvasListener;
import ptidej.ui.event.GraphEvent;
import ptidej.ui.kernel.Constituent;
import ptidej.ui.kernel.Entity;
import ptidej.ui.kernel.ModelGraph;
import ptidej.ui.kernel.builder.Builder;
import ptidej.ui.layout.repository.SugiyamaLayout;
import ptidej.viewer.LaidoutModelGraph;
import ptidej.viewer.ui.AbstractRepresentationWindow;
import ptidej.viewer.ui.panel.CanvasPanel;
import ptidej.viewer.utils.SilentModelStatistics;
import ptidej.viewer.widget.HierarchicalTreeCell;
import ptidej.viewer.widget.HierarchicalTreeCellCheckbox;
import ptidej.viewer.widget.HierarchicalTreeCellEditor;
import ptidej.viewer.widget.HierarchicalTreeCellRenderer;
import ptidej.viewer.widget.ScrollPane;

public class SourceDualHierarchicalModelWindow extends
		AbstractRepresentationWindow {

	private static final class CanvasListenerForPairing implements
			ICanvasListener {

		private final SourceDualHierarchicalModelWindow window;

		public CanvasListenerForPairing(
			final SourceDualHierarchicalModelWindow aWindow) {

			this.window = aWindow;
		}
		public void backgroundElementSelected(final CanvasEvent aCanvasEvent) {
		}
		public void backgroundElementUnSelected(final CanvasEvent aCanvasEvent) {
		}
		public void constituentSelected(final GraphEvent aGraphEvent) {
			this.constituentSelectionChanged(aGraphEvent, true);
		}
		private void constituentSelectionChanged(
			final GraphEvent aGraphEvent,
			final boolean aDesiredSelectedState) {

			final Entity selectedEntity = (Entity) aGraphEvent.getSource();
			final IFirstClassEntity firstClassEntity =
				selectedEntity.getSourceEntity();

			if (FilterTypes.getInstance().isFiltered(firstClassEntity)) {
				final Iterator implementingClasses;
				if (firstClassEntity instanceof IInterface) {
					final IInterface interfaze = (IInterface) firstClassEntity;
					implementingClasses =
						interfaze.getIteratorOnImplementingClasses();
				}
				else if (firstClassEntity instanceof IClass) {
					final IClass clazz = (IClass) firstClassEntity;
					implementingClasses =
						clazz.getIteratorOnInheritingEntities();
				}
				else {
					implementingClasses = new Iterator() {
						public boolean hasNext() {
							return false;
						}
						public Object next() {
							return null;
						}
						public void remove() {
						}
					};
				}

				this.constituentSelectionChangedOnTheLeft(
					aDesiredSelectedState,
					implementingClasses);
			}
			else if (FilterImplementationClasses.getInstance().isFiltered(
				firstClassEntity)) {

				final IInterfaceImplementer interfaceImplementer =
					(IInterfaceImplementer) firstClassEntity;
				// First, implemented interfaces.
				final Iterator implementedInterfaces =
					interfaceImplementer.getIteratorOnImplementedInterfaces();
				this.constituentSelectionChangedOnTheRight(
					aDesiredSelectedState,
					implementedInterfaces);
				// Second, extended abstract classes.
				final Iterator extendedAbstractClasses =
					interfaceImplementer
						.getIteratorOnInheritedEntities(FilterImplementationClasses
							.getInstance());
				this.constituentSelectionChangedOnTheRight(
					aDesiredSelectedState,
					extendedAbstractClasses);
			}
		}
		private void constituentSelectionChangedOnTheLeft(
			final boolean aDesiredSelectedState,
			final Iterator implementingClasses) {

			while (implementingClasses.hasNext()) {
				final IConstituent sourceConstituent =
					(IConstituent) implementingClasses.next();
				if (aDesiredSelectedState) {
					this.window.setOfEntitiesToSelectRight
						.add(sourceConstituent);
				}
				else {
					this.window.setOfEntitiesToSelectRight
						.remove(sourceConstituent);
				}

				final Entity entity =
					this.window.sourceGraphRight.getEntity(sourceConstituent
						.getDisplayID());
				if (entity != null) {
					// We are not trying to work with an entity that
					// is not being displayed currently (e.g., a Ghost).
					if (aDesiredSelectedState == true && !entity.isSelected()
							|| aDesiredSelectedState == false
							&& entity.isSelected()) {

						entity.isSelected(aDesiredSelectedState);
						this.window.awtCanvasRight.goTo(entity.getPosition());
					}
				}
			}
		}
		private void constituentSelectionChangedOnTheRight(
			final boolean aDesiredSelectedState,
			final Iterator implementedInterfaces) {

			while (implementedInterfaces.hasNext()) {
				final IConstituent sourceConstituent =
					(IConstituent) implementedInterfaces.next();
				if (aDesiredSelectedState) {
					this.window.setOfEntitiesToSelectLeft
						.add(sourceConstituent);
				}
				else {
					this.window.setOfEntitiesToSelectLeft
						.remove(sourceConstituent);
				}

				final Entity entity =
					this.window.sourceGraphLeft.getEntity(sourceConstituent
						.getDisplayID());
				if (entity != null) {
					// We are not trying to work with an entity that
					// is not being displayed currently (e.g., a Ghost).
					if (aDesiredSelectedState == true && !entity.isSelected()
							|| aDesiredSelectedState == false
							&& entity.isSelected()) {

						entity.isSelected(aDesiredSelectedState);
						// Because I set the ScrollPane to be Left-to-Right
						// to have the slider on the left, I must mirror
						// the X position of the entity wrt. the middle
						// of the canvas... Java is not very LtoR friendly!
						final double middleX =
							this.window.awtCanvasLeft.getSize().getWidth() / 2;
						final double newX =
							entity.getPosition().getX() - 2
									* (entity.getPosition().getX() - middleX);
						final Point newPosition =
							new Point((int) newX, (int) entity
								.getPosition()
								.getY());
						this.window.awtCanvasLeft.goTo(newPosition);
					}
				}
			}
		}
		public void constituentUnSelected(final GraphEvent aGraphEvent) {
			this.constituentSelectionChanged(aGraphEvent, false);
		}
		public void foregroundElementSelected(final CanvasEvent aCanvasEvent) {
		}
		public void foregroundElementUnSelected(final CanvasEvent aCanvasEvent) {
		}
	}
	private static final class DisplayListenerForAllConstituent implements
			ItemListener {

		private final Callable callbackMethod;
		private final IFilter filter;
		private final DefaultMutableTreeNode rootNote;
		private final Set setOfEntitiesToDisplay;
		private final Set setOfEntitiesToSelect;

		public DisplayListenerForAllConstituent(
			final DefaultMutableTreeNode aRootNote,
			final Set aSetOfEntitiesToDisplay,
			final Set aSetOfEntitiesToSelect,
			final IFilter aFilter,
			final Callable aMethodToCallback) {

			this.callbackMethod = aMethodToCallback;
			this.filter = aFilter;
			this.rootNote = aRootNote;
			this.setOfEntitiesToDisplay = aSetOfEntitiesToDisplay;
			this.setOfEntitiesToSelect = aSetOfEntitiesToSelect;
		}
		public void itemStateChanged(final ItemEvent anEvent) {
			final DefaultMutableTreeNode root = this.rootNote;

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

				if (sourceConstituent instanceof IFirstClassEntity
						&& this.filter.isFiltered(sourceConstituent)) {

					if (anEvent.getStateChange() == ItemEvent.SELECTED) {
						cell.setDisplayedWithoutNotification(true);
						this.setOfEntitiesToDisplay.add(sourceConstituent);
					}
					else if (anEvent.getStateChange() == ItemEvent.DESELECTED) {
						cell.setDisplayedWithoutNotification(false);
						this.setOfEntitiesToDisplay.remove(sourceConstituent);

						cell.setSelectedWithoutNotification(false);
						this.setOfEntitiesToSelect.remove(sourceConstituent);
					}
				}
			}

			try {
				this.callbackMethod.call();
			}
			catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}
	private static final class DisplayListenerForSingleConstituent implements
			ItemListener {

		private final Callable callbackMethod;
		private final Set setOfEntitiesToDisplay;
		private final Set setOfEntitiesToSelect;

		public DisplayListenerForSingleConstituent(
			final Set aSetOfEntitiesToDisplay,
			final Set aSetOfEntitiesToSelect,
			final Callable aCallBackMethod) {

			this.callbackMethod = aCallBackMethod;
			this.setOfEntitiesToDisplay = aSetOfEntitiesToDisplay;
			this.setOfEntitiesToSelect = aSetOfEntitiesToSelect;
		}
		public void itemStateChanged(final ItemEvent anEvent) {
			final HierarchicalTreeCellCheckbox checkbox =
				(HierarchicalTreeCellCheckbox) anEvent.getItem();
			final HierarchicalTreeCell cell =
				checkbox.getHierarchicalTreeCell();
			final IConstituent sourceConstituent = cell.getConstituent();

			if (anEvent.getStateChange() == ItemEvent.SELECTED) {
				this.setOfEntitiesToDisplay.add(sourceConstituent);
			}
			else if (anEvent.getStateChange() == ItemEvent.DESELECTED) {
				this.setOfEntitiesToDisplay.remove(sourceConstituent);

				cell.setSelectedWithoutNotification(false);
				this.setOfEntitiesToSelect.remove(sourceConstituent);
			}

			try {
				this.callbackMethod.call();
			}
			catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}
	private static final class FilterImplementationClasses implements IFilter {
		private static FilterImplementationClasses UniqueInstance;
		public static IFilter getInstance() {
			if (FilterImplementationClasses.UniqueInstance == null) {
				FilterImplementationClasses.UniqueInstance =
					new FilterImplementationClasses();
			}
			return FilterImplementationClasses.UniqueInstance;
		}

		private final IFilter notFilter = new NotFilter(
			FilterTypes.getInstance());
		public boolean isFiltered(final IConstituent aConstituent) {
			return this.notFilter.isFiltered(aConstituent);
		}
	}
	private static final class FilterTypes implements IFilter {
		private static FilterTypes UniqueInstance;
		public static IFilter getInstance() {
			if (FilterTypes.UniqueInstance == null) {
				FilterTypes.UniqueInstance = new FilterTypes();
			}
			return FilterTypes.UniqueInstance;
		}

		// A filter to get all the IInterface and types
		// represented by abstract classes that DO NOT
		// implement any interfaces.
		public boolean isFiltered(final IConstituent aConstituent) {
			if (aConstituent instanceof IInterface) {
				return true;
			}
			else if (aConstituent instanceof IClass
					&& aConstituent.isAbstract()
					&& ((IClass) aConstituent)
						.getNumberOfImplementedInterfaces() == 0) {

				return true;
			}

			return false;
		}
	}
	private static class SelectionListenerForAllConstituents implements
			ItemListener {

		private final ModelGraph modelGraph;
		public SelectionListenerForAllConstituents(final ModelGraph aModelGraph) {
			this.modelGraph = aModelGraph;
		}
		public void itemStateChanged(final ItemEvent anEvent) {
			final DefaultMutableTreeNode root =
				(DefaultMutableTreeNode) ((JTree) ((HierarchicalTreeCellCheckbox) anEvent
					.getSource()).getParent().getParent()).getModel().getRoot();
			final Enumeration enumeration = root.depthFirstEnumeration();
			while (enumeration.hasMoreElements()) {
				final DefaultMutableTreeNode node =
					(DefaultMutableTreeNode) enumeration.nextElement();
				final HierarchicalTreeCell cell =
					(HierarchicalTreeCell) node.getUserObject();

				final IConstituent sourceConstituent = cell.getConstituent();

				if (sourceConstituent instanceof IFirstClassEntity) {
					final Constituent graphConstituent =
						this.modelGraph.getEntity(sourceConstituent
							.getDisplayID());
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
	private static class SelectionListenerForSingleConstituent implements
			ItemListener {

		private final ModelGraph modelGraphLeft;
		private final ModelGraph modelGraphRight;
		private Set setOfEntitiesToSelect;

		public SelectionListenerForSingleConstituent(
			final ModelGraph aModelGraphLeft,
			final ModelGraph aModelGraphRight,
			final Set aSetOfEntitiesToSelect) {

			this.modelGraphLeft = aModelGraphLeft;
			this.modelGraphRight = aModelGraphRight;
			this.setOfEntitiesToSelect = aSetOfEntitiesToSelect;
		}
		public void itemStateChanged(final ItemEvent anEvent) {
			final HierarchicalTreeCellCheckbox checkbox =
				(HierarchicalTreeCellCheckbox) anEvent.getItem();
			final HierarchicalTreeCell cell =
				checkbox.getHierarchicalTreeCell();
			final IConstituent sourceConstituent = cell.getConstituent();
			final Constituent graphConstituentLeft =
				this.modelGraphLeft.getEntity(sourceConstituent.getDisplayID());
			final Constituent graphConstituentRight =
				this.modelGraphRight
					.getEntity(sourceConstituent.getDisplayID());

			if (anEvent.getStateChange() == ItemEvent.SELECTED) {
				this.setOfEntitiesToSelect.add(sourceConstituent);
				if (graphConstituentLeft != null) {
					graphConstituentLeft.isSelected(true);
				}
				if (graphConstituentRight != null) {
					graphConstituentRight.isSelected(true);
				}
			}
			else if (anEvent.getStateChange() == ItemEvent.DESELECTED) {
				this.setOfEntitiesToSelect.remove(sourceConstituent);
				if (graphConstituentLeft != null) {
					graphConstituentLeft.isSelected(false);
				}
				if (graphConstituentRight != null) {
					graphConstituentRight.isSelected(false);
				}
			}
		}
	};
	private static final long serialVersionUID = 1526202856491831532L;
	private static JTree createTree(final DefaultMutableTreeNode aTreeRoot) {
		final JTree tree = new JTree(aTreeRoot);
		tree.addTreeWillExpandListener(new TreeWillExpandListener() {
			public void treeWillCollapse(
				final TreeExpansionEvent aTreeExpansionEvent)
					throws ExpandVetoException {

				if (aTreeExpansionEvent.getPath().getPathCount() < 2) {
					throw new ExpandVetoException(new TreeExpansionEvent(
						tree,
						null));
				}
			}
			public void treeWillExpand(TreeExpansionEvent event)
					throws ExpandVetoException {
			}
		});
		final TreeCellRenderer renderer = new HierarchicalTreeCellRenderer();
		tree.setCellRenderer(renderer);
		tree.setCellEditor(new HierarchicalTreeCellEditor());
		tree.setEditable(true);
		return tree;
	}
	private static void populateTree(
		final DefaultMutableTreeNode aRootNode,
		final ModelGraph aModelGraphLeft,
		final ModelGraph aModelGraphRight,
		final Builder aBuilder,
		final AWTCanvas anAWTCanvas,
		final Canvas aCanvas,
		final IConstituent aConstituent,
		final IFirstClassEntity theParentFirstClassEntity,
		final Set aSetOfEntitiesToDisplay,
		final Set aSetOfEntitiesToSelect,
		final Callable aMethodToCallback) {

		final DefaultMutableTreeNode node =
			new DefaultMutableTreeNode(new HierarchicalTreeCell(
				aBuilder,
				anAWTCanvas,
				aCanvas,
				aModelGraphLeft,
				aConstituent,
				theParentFirstClassEntity,
				new DisplayListenerForSingleConstituent(
					aSetOfEntitiesToDisplay,
					aSetOfEntitiesToSelect,
					aMethodToCallback),
				new SelectionListenerForSingleConstituent(
					aModelGraphLeft,
					aModelGraphRight,
					aSetOfEntitiesToSelect)));
		aRootNode.add(node);

		if (aConstituent instanceof IContainer) {
			final Iterator iterator =
				((IContainer) aConstituent).getIteratorOnConstituents();
			while (iterator.hasNext()) {
				final IConstituent constituent = (IConstituent) iterator.next();

				SourceDualHierarchicalModelWindow.populateTree(
					node,
					aModelGraphLeft,
					aModelGraphRight,
					aBuilder,
					anAWTCanvas,
					aCanvas,
					constituent,
					theParentFirstClassEntity,
					aSetOfEntitiesToDisplay,
					aSetOfEntitiesToSelect,
					aMethodToCallback);
			}
		}
	}
	private static void populateTree(
		final JTree aTree,
		final DefaultMutableTreeNode aRootNode,
		final ModelGraph aModelGraphLeft,
		final ModelGraph aModelGraphRight,
		final Builder aBuilder,
		final AWTCanvas anAWTCanvas,
		final Canvas aCanvas,
		final IAbstractModel aModel,
		final Set aSetOfEntitiesToDisplay,
		final Set aSetOfEntitiesToSelect,
		final IFilter aFilter,
		final Callable aMethodToCallback) {

		final HierarchicalTreeCell cell =
			new HierarchicalTreeCell(
				aBuilder,
				aModel,
				new DisplayListenerForAllConstituent(
					aRootNode,
					aSetOfEntitiesToDisplay,
					aSetOfEntitiesToSelect,
					new IFilter() {
						// A filter to get all the IFirstClassEntities.
						public boolean isFiltered(
							final IConstituent aConstituent) {
							return IFirstClassEntity.class
								.isAssignableFrom(aConstituent.getClass());
						}
					}, aMethodToCallback),
				new SelectionListenerForAllConstituents(aModelGraphLeft),
				new DisplayListenerForAllConstituent(
					aRootNode,
					aSetOfEntitiesToDisplay,
					aSetOfEntitiesToSelect,
					aFilter,
					aMethodToCallback));
		aRootNode.setUserObject(cell);
		aRootNode.removeAllChildren();

		final Iterator iterator = aModel.getIteratorOnTopLevelEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iterator.next();
			SourceDualHierarchicalModelWindow.populateTree(
				aRootNode,
				aModelGraphLeft,
				aModelGraphRight,
				aBuilder,
				anAWTCanvas,
				aCanvas,
				firstClassEntity,
				firstClassEntity,
				aSetOfEntitiesToDisplay,
				aSetOfEntitiesToSelect,
				aMethodToCallback);
		}

		aTree.expandRow(0);
	}

	private AWTCanvas awtCanvasLeft;
	private AWTCanvas awtCanvasRight;
	private Canvas canvasLeft;
	private CanvasPanel canvasPanelLeft;
	private CanvasPanel canvasPanelRight;
	private Canvas canvasRight;
	private final IModelListener modelStatistics;
	protected boolean refreshOnlyLeft;
	protected boolean refreshOnlyRight;
	private final Set setOfEntitiesToDisplayLeft = new HashSet();
	private final Set setOfEntitiesToDisplayRight = new HashSet();
	private final Set setOfEntitiesToSelectLeft = new HashSet();
	private final Set setOfEntitiesToSelectRight = new HashSet();
	private LaidoutModelGraph sourceGraphLeft;
	private LaidoutModelGraph sourceGraphRight;
	private JTree treeLeft;
	private JTree treeRight;
	private DefaultMutableTreeNode treeRootLeft;
	private DefaultMutableTreeNode treeRootRight;

	public SourceDualHierarchicalModelWindow() {
		// Common stuff...
		this.modelStatistics = new SilentModelStatistics();
		//	this.getContentPane().setLayout(new PercentLayout());

		//	this.createTreeAndGraphPanelLeft();
		//	this.createTreeAndGraphPanelRight();
		final JComponent component1 = this.helperToCreateFirstTreeAndGraph();
		final JComponent component2 = this.createTreeAndGraphPanelBottom();

		//	this.getContentPane().add(
		//		component1,
		//		new PercentLayout.Constraint(0, 0, 100, 50));
		//	this.getContentPane().add(
		//		component2,
		//		new PercentLayout.Constraint(0, 50, 100, 50));

		final JSplitPane splitPane =
			new JSplitPane(JSplitPane.VERTICAL_SPLIT, component1, component2);
		splitPane.setOneTouchExpandable(true);
		//	splitPane.setDividerLocation(200);
		splitPane.setResizeWeight(0.5);

		this.getContentPane().add(splitPane);
	}
	private JComponent createTreeAndGraphPanelBottom() {
		this.treeRootRight = new DefaultMutableTreeNode();
		this.treeRootRight.setUserObject(new JLabel(""));
		this.treeRight =
			SourceDualHierarchicalModelWindow.createTree(this.treeRootRight);
		//	treeAndGraphSplitPane.add(
		//		new ScrollPane(this.treeRight),
		//		new PercentLayout.Constraint(0, 0, 40, 100));

		this.canvasPanelRight = new CanvasPanel();
		// Yann 2006/08/20: Layout.
		// The CanvasPanel will only hold the AWTCanvas responsible
		// for displaying source model. The AWTCanvas should be
		// positioned at (0,0) so I make sure that the CanvasPanel
		// does not use some fancy layout algorithm...
		// this.canvasPanel.setLayout(null);
		final ScrollPane scrollPane = new ScrollPane(this.canvasPanelRight);
		scrollPane.getViewport().setBackground(Color.WHITE);

		// Yann 2006/09/02: Deaf...
		// I force the canvas panel to repaint itself
		// when the scrollbars change because it does
		// not by itself, for some unknown reason...
		scrollPane.getHorizontalScrollBar().addAdjustmentListener(
			new AdjustmentListener() {
				public void adjustmentValueChanged(final AdjustmentEvent e) {
					SourceDualHierarchicalModelWindow.this.canvasPanelRight
						.repaint();
				}
			});
		scrollPane.getVerticalScrollBar().addAdjustmentListener(
			new AdjustmentListener() {
				public void adjustmentValueChanged(final AdjustmentEvent e) {
					SourceDualHierarchicalModelWindow.this.canvasPanelRight
						.repaint();
				}
			});
		//	treeAndGraphSplitPane.add(scrollPane, new PercentLayout.Constraint(
		//		40,
		//		0,
		//		60,
		//		100));

		final JSplitPane treeAndGraphSplitPane =
			new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new ScrollPane(
				this.treeRight), scrollPane);
		treeAndGraphSplitPane.setOneTouchExpandable(true);
		treeAndGraphSplitPane.setDividerLocation(200);

		return treeAndGraphSplitPane;
	}
	//	private void createTreeAndGraphPanelLeft() {
	//		final JSplitPane treeAndGraphSplitPane =
	//			this.helperToCreateFirstTreeAndGraph();
	//		this.getContentPane().add(
	//			treeAndGraphSplitPane,
	//			new PercentLayout.Constraint(0, 0, 50, 100));
	//	}
	//	private void createTreeAndGraphPanelRight() {
	//		this.canvasPanelRight = new CanvasPanel();
	//		// Yann 2006/08/20: Layout.
	//		// The CanvasPanel will only hold the AWTCanvas responsible
	//		// for displaying source model. The AWTCanvas should be
	//		// positioned at (0,0) so I make sure that the CanvasPanel
	//		// does not use some fancy layout algorithm...
	//		// this.canvasPanel.setLayout(null);
	//		final ScrollPane scrollPane = new ScrollPane(this.canvasPanelRight);
	//		// Yann 2006/09/02: Deaf...
	//		// I force the canvas panel to repaint itself
	//		// when the scrollbars change because it does
	//		// not by itself, for some unknown reason...
	//		scrollPane.getHorizontalScrollBar().addAdjustmentListener(
	//			new AdjustmentListener() {
	//				public void adjustmentValueChanged(final AdjustmentEvent e) {
	//					SourceDualHierarchicalModelWindow.this.canvasPanelRight
	//						.repaint();
	//				}
	//			});
	//		scrollPane.getVerticalScrollBar().addAdjustmentListener(
	//			new AdjustmentListener() {
	//				public void adjustmentValueChanged(final AdjustmentEvent e) {
	//					SourceDualHierarchicalModelWindow.this.canvasPanelRight
	//						.repaint();
	//				}
	//			});
	//		//	treeAndGraphSplitPane.add(scrollPane, new PercentLayout.Constraint(
	//		//		0,
	//		//		0,
	//		//		60,
	//		//		100));
	//
	//		this.treeRootRight = new DefaultMutableTreeNode();
	//		this.treeRootRight.setUserObject(new JLabel(""));
	//		this.treeRight =
	//			SourceDualHierarchicalModelWindow.createTree(this.treeRootRight);
	//		//	treeAndGraphSplitPane.add(
	//		//		new ScrollPane(this.treeRight),
	//		//		new PercentLayout.Constraint(60, 0, 40, 100));
	//
	//		final JSplitPane treeAndGraphSplitPane =
	//			new JSplitPane(
	//				JSplitPane.HORIZONTAL_SPLIT,
	//				scrollPane,
	//				new ScrollPane(this.treeRight));
	//
	//		this.getContentPane().add(
	//			treeAndGraphSplitPane,
	//			new PercentLayout.Constraint(50, 0, 50, 100));
	//	}
	public AWTCanvas getAWTCanvas() {
		return this.awtCanvasLeft;
	}
	public Canvas getCanvas() {
		return this.canvasLeft;
	}
	public IModelListener getModelStatistics() {
		return this.modelStatistics;
	}
	public LaidoutModelGraph getSourceGraph() {
		return this.sourceGraphLeft;
	}
	public IAbstractModel getSourceModel() {
		return this.sourceModel;
	}
	private JSplitPane helperToCreateFirstTreeAndGraph() {
		this.treeRootLeft = new DefaultMutableTreeNode();
		this.treeRootLeft.setUserObject(new JLabel(""));
		this.treeLeft =
			SourceDualHierarchicalModelWindow.createTree(this.treeRootLeft);
		//	treeAndGraphSplitPane.add(
		//		new ScrollPane(this.treeLeft),
		//		new PercentLayout.Constraint(0, 0, 40, 100));

		this.canvasPanelLeft = new CanvasPanel();
		// Yann 2006/08/20: Layout.
		// The CanvasPanel will only hold the AWTCanvas responsible
		// for displaying source model. The AWTCanvas should be
		// positioned at (0,0) so I make sure that the CanvasPanel
		// does not use some fancy layout algorithm...
		// this.canvasPanel.setLayout(null);
		final ScrollPane scrollPane = new ScrollPane(this.canvasPanelLeft);
		scrollPane.getViewport().setBackground(Color.WHITE);

		// Yann 2006/09/02: Deaf...
		// I force the canvas panel to repaint itself
		// when the scrollbars change because it does
		// not by itself, for some unknown reason...
		scrollPane.getHorizontalScrollBar().addAdjustmentListener(
			new AdjustmentListener() {
				public void adjustmentValueChanged(final AdjustmentEvent e) {
					SourceDualHierarchicalModelWindow.this.canvasPanelLeft
						.repaint();
				}
			});
		scrollPane.getVerticalScrollBar().addAdjustmentListener(
			new AdjustmentListener() {
				public void adjustmentValueChanged(final AdjustmentEvent e) {
					SourceDualHierarchicalModelWindow.this.canvasPanelLeft
						.repaint();
				}
			});
		//	treeAndGraphSplitPane.add(scrollPane, new PercentLayout.Constraint(
		//		40,
		//		0,
		//		60,
		//		100));

		final JSplitPane treeAndGraphSplitPane =
			new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new ScrollPane(
				this.treeLeft), scrollPane);
		treeAndGraphSplitPane.setOneTouchExpandable(true);
		treeAndGraphSplitPane.setDividerLocation(200);

		return treeAndGraphSplitPane;
	}
	protected void refreshSpecifics() {
		if (!this.refreshOnlyRight) {
			this.refreshCanvasLeft();
		}
		if (!this.refreshOnlyLeft) {
			this.refreshCanvasRight();
		}

		this.refreshOnlyLeft = false;
		this.refreshOnlyRight = false;
	}
	protected void setSourceModelSpecifics() {
		this.setOfEntitiesToDisplayLeft.clear();
		this.setOfEntitiesToDisplayRight.clear();
		this.setOfEntitiesToSelectLeft.clear();
		this.setOfEntitiesToSelectRight.clear();

		// Yann 2013/05/23: Builder!
		// If there is no builder, I use the default one...
		// TODO Why should I care about a default builder
		// when there is nothing to build or display...
		if (this.getBuilder() == null) {
			this.setBuilder(Builder.getCurrentBuilder(this
				.getPrimitiveFactory()));
		}

		// Yann 2013/09/29: Initialisation
		// I must make sure to provide a source graph
		// even if there is nothing to show yet...
		this.sourceGraphLeft =
			new LaidoutModelGraph(
				this.getBuilder(),
				this.sourceModel,
				this.setOfEntitiesToDisplayLeft,
				new SugiyamaLayout());
		this.sourceGraphLeft.construct();
		this.sourceGraphLeft.addSelectionListener(new SelectionListener(
			this.treeRootLeft));
		this.canvasLeft =
			new Canvas(this.getPrimitiveFactory(), this.sourceGraphLeft);
		this.canvasLeft.addCanvasListener(new CanvasListenerForRefreshing(
			new Callable() {
				public Object call() throws Exception {
					SourceDualHierarchicalModelWindow.this.revalidate();
					SourceDualHierarchicalModelWindow.this.repaint();
					return null;
				}
			}));
		this.canvasLeft.addCanvasListener(new CanvasListenerForPairing(this));
		this.awtCanvasLeft = new AWTCanvas(this.canvasLeft);
		this.canvasPanelLeft.removeAll();
		this.canvasPanelLeft.add(this.awtCanvasLeft);

		this.sourceGraphRight =
			new LaidoutModelGraph(
				this.getBuilder(),
				this.sourceModel,
				this.setOfEntitiesToDisplayRight,
				new SugiyamaLayout());
		this.sourceGraphRight.construct();
		this.sourceGraphRight.addSelectionListener(new SelectionListener(
			this.treeRootRight));
		this.canvasRight =
			new Canvas(this.getPrimitiveFactory(), this.sourceGraphRight);
		this.canvasRight.addCanvasListener(new CanvasListenerForRefreshing(
			new Callable() {
				public Object call() throws Exception {
					SourceDualHierarchicalModelWindow.this.revalidate();
					SourceDualHierarchicalModelWindow.this.repaint();
					return null;
				}
			}));
		this.canvasRight.addCanvasListener(new CanvasListenerForPairing(this));
		this.awtCanvasRight = new AWTCanvas(this.canvasRight);
		this.canvasPanelRight.removeAll();
		this.canvasPanelRight.add(this.awtCanvasRight);

		SourceDualHierarchicalModelWindow.populateTree(
			this.treeLeft,
			this.treeRootLeft,
			this.sourceGraphLeft,
			this.sourceGraphRight,
			this.getBuilder(),
			this.awtCanvasLeft,
			this.canvasLeft,
			this.getSourceModel(),
			this.setOfEntitiesToDisplayLeft,
			this.setOfEntitiesToSelectLeft,
			FilterTypes.getInstance(),
			new Callable() {
				public Object call() throws Exception {
					SourceDualHierarchicalModelWindow.this.refreshOnlyLeft =
						true;
					SourceDualHierarchicalModelWindow.this.refresh();
					return null;
				}
			});

		SourceDualHierarchicalModelWindow.populateTree(
			this.treeRight,
			this.treeRootRight,
			this.sourceGraphRight,
			this.sourceGraphLeft,
			this.getBuilder(),
			this.awtCanvasRight,
			this.canvasRight,
			this.getSourceModel(),
			this.setOfEntitiesToDisplayRight,
			this.setOfEntitiesToSelectRight,
			FilterImplementationClasses.getInstance(),
			new Callable() {
				public Object call() throws Exception {
					SourceDualHierarchicalModelWindow.this.refreshOnlyRight =
						true;
					SourceDualHierarchicalModelWindow.this.refresh();
					return null;
				}
			});
	}
	private void refreshCanvasLeft() {
		// Yann 07/09/27: Builder!
		// I don't forget to initialise the Builder
		// because it keeps track of links between
		// abstract model entities and graph entities!
		final Builder builder = this.getBuilder();
		builder.initialize();
		// Yann 2014/03/27: Reset!
		// I now keep the same ModelGraph but reset it...
		//	this.sourceGraphLeft =
		//		new LaidoutModelGraph(
		//			builder,
		//			this.sourceModel,
		//			this.setOfSelectedEntitiesLeft,
		//			new SugiyamaLayout());
		this.sourceGraphLeft.reset(
			builder,
			this.sourceModel,
			this.setOfEntitiesToDisplayLeft,
			this.setOfEntitiesToSelectLeft,
			new SugiyamaLayout());
		this.sourceGraphLeft.construct();
		// ... and because I reset, I don't need all this!!!
		//	this.sourceGraphLeft.addSelectionListener(new SelectionListener(
		//		this.treeRootLeft));
		//	this.canvasLeft =
		//		new Canvas(this.getPrimitiveFactory(), this.sourceGraphLeft);
		//	this.canvasLeft.addCanvasListener(new CanvasListenerForRefreshing(
		//		new Callable() {
		//			public Object call() throws Exception {
		//				SourceDualHierarchicalModelWindow.this.refreshCanvasLeft();
		//				SourceDualHierarchicalModelWindow.super.refresh();
		//				return null;
		//			}
		//		}));
		//	this.canvasLeft.addCanvasListener(new CanvasListenerForPairing(this));
		//	this.awtCanvasLeft = new AWTCanvas(this.canvasLeft);
		//	this.canvasPanelLeft.removeAll();
		//	this.canvasPanelLeft.add(this.awtCanvasLeft);

		this.canvasLeft.setVisibleElements(this.getVisibleElements());
		this.canvasLeft.build();

		// Yann 2007/0206: Size matters!
		// I make sure to tell the AWT Canvas
		// of the size of its Canvas so that it
		// paints its content properly.
		this.awtCanvasLeft.setSize(this.canvasLeft.getDimension());
		this.canvasPanelLeft.setSize(this.awtCanvasLeft.getSize());
		this.canvasPanelLeft.setPreferredSize(this.awtCanvasLeft.getSize());

		// Yann 2007/10/01: Adding occurrences...
		// I don't forget to add the occurrences to the new
		// model graph of the new code level model.
		// Yann 2014/05/09: Useless!
		// It was weird anyways to *create* something
		// while refreshing the display, wasn't it?
		//	ViewerCommons.createGroupSolutions(this);
	}
	private void refreshCanvasRight() {
		// Yann 07/09/27: Builder!
		// I don't forget to initialise the Builder
		// because it keeps track of links between
		// abstract model entities and graph entities!
		final Builder builder = this.getBuilder();
		builder.initialize();
		// Yann 2014/03/27: Reset!
		// I now keep the same ModelGraph but reset it...
		//	this.sourceGraphRight =
		//		new LaidoutModelGraph(
		//			builder,
		//			this.sourceModel,
		//			this.setOfSelectedEntitiesRight,
		//			new SugiyamaLayout());
		this.sourceGraphRight.reset(
			builder,
			this.sourceModel,
			this.setOfEntitiesToDisplayRight,
			this.setOfEntitiesToSelectRight,
			new SugiyamaLayout());
		this.sourceGraphRight.construct();
		// ... and because I reset, I don't need all this!!!
		//	this.sourceGraphRight.addSelectionListener(new SelectionListener(
		//		this.treeRootRight));
		//	this.canvasRight =
		//		new Canvas(this.getPrimitiveFactory(), this.sourceGraphRight);
		//	this.canvasRight.addCanvasListener(new CanvasListenerForRefreshing(
		//		new Callable() {
		//			public Object call() throws Exception {
		//				SourceDualHierarchicalModelWindow.this.refreshCanvasRight();
		//				SourceDualHierarchicalModelWindow.super.refresh();
		//				return null;
		//			}
		//		}));
		//	this.canvasRight.addCanvasListener(new CanvasListenerForPairing(this));
		//	this.awtCanvasRight = new AWTCanvas(this.canvasRight);
		//	this.canvasPanelRight.removeAll();
		//	this.canvasPanelRight.add(this.awtCanvasRight);

		this.canvasRight.setVisibleElements(this.getVisibleElements());
		this.canvasRight.build();

		// Yann 2007/0206: Size matters!
		// I make sure to tell the AWT Canvas
		// of the size of its Canvas so that it
		// paints its content properly.
		this.awtCanvasRight.setSize(this.canvasRight.getDimension());
		this.canvasPanelRight.setSize(this.awtCanvasRight.getSize());
		this.canvasPanelRight.setPreferredSize(this.awtCanvasRight.getSize());

		// Yann 2007/10/01: Adding occurrences...
		// I don't forget to add the occurrences to the new
		// model graph of the new code level model.
		// Yann 2014/05/09: Useless!
		// It was weird anyways to *create* something
		// while refreshing the display, wasn't it?
		//	ViewerCommons.createGroupSolutions(this);
	}
}
