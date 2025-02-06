package ffeatureextractor.viewpart;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.zest.core.viewers.AbstractZoomableViewer;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IZoomableWorkbenchPart;
import org.eclipse.zest.core.viewers.ZoomContributionViewItem;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.CompositeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.DirectedGraphLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalShift;
import org.eclipse.zest.layouts.algorithms.HorizontalTreeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalLayoutAlgorithm;

import ca.uqam.latece.aspects.extractor.lattice.graph.model.Connection;
import ca.uqam.latece.aspects.extractor.lattice.graph.model.Node;
import ca.uqam.latece.aspects.extractor.lattice.model.Lattice;

public class LatticeGraph extends ViewPart implements IZoomableWorkbenchPart {
	private GraphViewer viewer;

	public LatticeGraph() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub ù
		viewer = new GraphViewer(parent, SWT.BORDER);

		// myLabelInView = new Label(parent, SWT.BORDER);
		// myLabelInView.setText("This is a sample E4 view");

		viewer.getGraphControl().setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		viewer.getGraphControl().HIGHLIGHT_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);

		// Selection listener on graphConnect or GraphNode is not supported
		// see https://bugs.eclipse.org/bugs/show_bug.cgi?id=236528
		viewer.getGraphControl().addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(e);
				System.out.println(((Node) (((GraphNode) (e.item)).getData())).toString());
				String featureType = ((Node) (((GraphNode) (e.item)).getData())).getTypes().toString();
				String extent = ((Node) (((GraphNode) (e.item)).getData())).getExtent();
				String intent = ((Node) (((GraphNode) (e.item)).getData())).getIntent();
				int nodeID = ((Node) (((GraphNode) (e.item)).getData())).getID();
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				IViewPart viewPart = page.findView("ffeatureExtractor.viewpart.LatticeNode");
				LatticeNode graphNode = (LatticeNode) viewPart;
				graphNode.update(featureType, intent, extent, nodeID);

			}

		});

		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(listener);
	}

	public void setFocus() {
	}

	private void fillToolBar() {
		ZoomContributionViewItem toolbarZoomContributionViewItem = new ZoomContributionViewItem(this);
		IActionBars bars = getViewSite().getActionBars();
		bars.getMenuManager().add(toolbarZoomContributionViewItem);

	}

	@Override
	public AbstractZoomableViewer getZoomableViewer() {
		return viewer;
	}

	public void updateLabel(List<Node> nodes) {

		viewer.setContentProvider(new ZestNodeContentProvider());
		viewer.setLabelProvider(new ZestLabelProvider());

		// NodeModelContentProvider model = new NodeModelContentProvider();
		viewer.setInput(nodes);
		System.out.println("nodes.size()" + nodes.size());
		LayoutAlgorithm layout = setLayout();
		viewer.setLayoutAlgorithm(layout, true);
		viewer.applyLayout();
		// fillToolBar();
		// GraphNode node2 = new GraphNode(graph, SWT.NONE, "Jack");
		// GraphNode node3 = new GraphNode(graph, SWT.NONE, "Joe");
		// GraphNode node4 = new GraphNode(graph, SWT.NONE, "Bill");
		// Lets have a directed connection
		// new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, node1,
		// node2);
		// Lets have a dotted graph connection
		// new GraphConnection(graph, ZestStyles.CONNECTIONS_DOT, node2, node3);
		// Standard connection
		// new GraphConnection(graph, SWT.NONE, node3, node1);
		// Change line color and line width

		// graph.setLayoutAlgorithm(new TreeLayoutAlgorithm(
		// LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);

	}

	private LayoutAlgorithm setLayout() {
		LayoutAlgorithm layout;
		// layout = new RadialLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);

		//layout = new TreeLayoutAlgorithm(LayoutStyles.NONE);
		// layout = new GridLayoutAlgorithm(LayoutStyles.NONE);
	//	layout = new CompositeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING, new
	//					LayoutAlgorithm[] { new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), new
	//						HorizontalShift(LayoutStyles.NO_LAYOUT_NODE_RESIZING) });
		layout = new  HorizontalTreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		// layout = new RadialLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		return layout;

	}

	// the listener we register with the selection service
	private ISelectionListener listener = new ISelectionListener() {
		public void selectionChanged(IWorkbenchPart sourcepart, ISelection selection) {
			// we ignore our own selections
			if (sourcepart != LatticeGraph.this) {
				showSelection(sourcepart, selection);
			}
		}
	};

	/**
	 * Shows the given selection in this view.
	 */
	public void showSelection(IWorkbenchPart sourcepart, ISelection selection) {
		//setContentDescription(sourcepart.getTitle() + " (" + selection.getClass().getName() + ")");
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;

			if ((ss.getFirstElement() instanceof IFile)) {
				IFile file = (IFile) ss.getFirstElement();
				if (file.getName().endsWith(".ltc")) {
					FileInputStream fis;
					ObjectInputStream ois;
					try {
						fis = new FileInputStream(file.getLocation().toFile());
						ois = new ObjectInputStream(fis);
						List<Node> Nodes = ((List<Node>) ois.readObject());
						
						
						updateLabel(Nodes);
						ois.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

		}

	}

	public void dispose() {
		// important: We need do unregister our listener when the view is disposed
		getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(listener);
		super.dispose();
	}
}
