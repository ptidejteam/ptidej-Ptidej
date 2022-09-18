package fr.emn.oadymppac.widgets;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import fr.emn.oadymppac.graph.aggregation.AbstractGraphGetter;
import fr.emn.oadymppac.graph.aggregation.AggregationFunction;
import fr.emn.oadymppac.graph.aggregation.DefaultAggregationFunctionFactory;
import fr.emn.oadymppac.graph.clustering.AbstractMatrixClusterizer;
import fr.emn.oadymppac.graph.clustering.DefaultMatrixClusterizer;
import fr.emn.oadymppac.graph.clustering.KMedoidClusterizer;
import fr.emn.oadymppac.utils.ClusteringFileFilter;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * This panel provides control for clustering. It makes 
 * it possible to set several clustering parameters
 * such as grouping tolerance, cluster order, the strategy 
 * for choosing a pivot, clustering algorithm etc.
 */
public class ClusterControlPanel extends JPanel {
	/**
	 * Custom cell renderer used to display appropriate labels
	 * related to data types and aggregation functions.
	 */
	class MyCellRenderer extends JLabel implements ListCellRenderer {
		private static final long serialVersionUID = 3283163754558579502L;

		public MyCellRenderer() {
			this.setOpaque(false);
		}
		public Component getListCellRendererComponent(
			final JList list,
			final Object value,
			final int index,
			final boolean isSelected,
			final boolean cellHasFocus) {
			if (value instanceof AggregationFunction) {
				this.setText(((AggregationFunction) value)
					.getFunctionDescription());
			}
			else if (value instanceof Class) {
				this.setText(((Class) value).getName().substring(
					((Class) value).getName().lastIndexOf(".") + 1));
			}
			else {
				System.out.println("value is " + value);
			}
			return this;
		}
	}

	private static final long serialVersionUID = -996028521946183099L;
	JSlider clusterToleranceSlider;
	GLAdjacencyMatrix glMatrix;
	IcicleTreeBrowser horizontal, vertical;

	final JComboBox functionBox;

	/**
	 * Constructor for ClusterControlPanel.
	 */
	public ClusterControlPanel(
		final GLAdjacencyMatrix matrix,
		final IcicleTreeBrowser htree,
		final IcicleTreeBrowser vtree) {
		super();
		this.glMatrix = matrix;
		this.horizontal = htree;
		this.vertical = vtree;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		this.clusterToleranceSlider =
			new JSlider(
				SwingConstants.HORIZONTAL,
				0,
				20,
				AbstractMatrixClusterizer.DEFAULT_TOLERANCE);
		this.clusterToleranceSlider.setMajorTickSpacing(5);
		this.clusterToleranceSlider.setMinorTickSpacing(1);
		this.clusterToleranceSlider.setPaintTicks(true);
		this.clusterToleranceSlider.setPaintLabels(true);
		this.clusterToleranceSlider.setSnapToTicks(true);
		this.clusterToleranceSlider.setBorder(BorderFactory
			.createTitledBorder("Grouping Tolerance"));
		this.clusterToleranceSlider.addChangeListener(new ChangeListener() {
			/**
			 * @see javax.swing.event.ChangeListener#stateChanged(ChangeEvent)
			 */
			public void stateChanged(final ChangeEvent e) {
				// modify the clusterization tolerance
				// perform the clusterization
				((AbstractMatrixClusterizer) ClusterControlPanel.this.glMatrix
					.getClusterizer())
					.setTolerance(ClusterControlPanel.this.clusterToleranceSlider
						.getValue());
				if (!ClusterControlPanel.this.clusterToleranceSlider
					.getValueIsAdjusting()) {
					ClusterControlPanel.this.glMatrix
						.getClusterizer()
						.clusterize();
					ClusterControlPanel.this.glMatrix.repaint();
				}
			}
		});
		this.clusterToleranceSlider.setAlignmentX(Component.LEFT_ALIGNMENT);

		/***************************************************
		 * combo boxes for attribute aggregation
		 ***************************************************/
		final JComboBox dataTypeBox =
			new JComboBox(DefaultAggregationFunctionFactory
				.sharedInstance()
				.getTypes());
		dataTypeBox.setRenderer(new MyCellRenderer());

		this.functionBox =
			new JComboBox(DefaultAggregationFunctionFactory
				.sharedInstance()
				.getFunctions((Class) dataTypeBox.getSelectedItem()));
		this.functionBox.setRenderer(new MyCellRenderer());

		dataTypeBox.addItemListener(new ItemListener() {
			public void itemStateChanged(final ItemEvent e) {
				if (e.getSource().equals(dataTypeBox)) {
					ClusterControlPanel.this.functionBox.removeAllItems();
					final AggregationFunction[] functions =
						DefaultAggregationFunctionFactory
							.sharedInstance()
							.getFunctions((Class) e.getItem());
					for (int i = 0; i < functions.length; i++) {
						ClusterControlPanel.this.functionBox
							.addItem(functions[i]);
					}
				}
			}
		});

		/****************************************************
		 * Save, load and cluster buttons
		 ****************************************************/
		final JButton clusterBtn = new JButton("cluster it !");
		clusterBtn.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent ev) {
				ClusterControlPanel.this.glMatrix.getClusterizer().clusterize();
			}
		});

		final JFileChooser jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File(System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "traces"));
		final JButton saveClusteringBtn = new JButton("Save");
		saveClusteringBtn.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				final int res =
					jfc.showSaveDialog(ClusterControlPanel.this.getParent());

				if (res == JFileChooser.APPROVE_OPTION) {
					ClusterControlPanel.this.glMatrix
						.getClusterizer()
						.storeClustering(jfc.getSelectedFile());
				}

			}
		});

		final JFileChooser loadjfc = new JFileChooser();
		loadjfc.setFileFilter(new ClusteringFileFilter());
		loadjfc.setCurrentDirectory(new File(System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "traces"));
		final JButton loadClusteringBtn = new JButton("Load");
		loadClusteringBtn.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				final int res =
					loadjfc.showOpenDialog(ClusterControlPanel.this.getParent());

				if (res == JFileChooser.APPROVE_OPTION) {
					ClusterControlPanel.this.glMatrix
						.getClusterizer()
						.loadClustering(loadjfc.getSelectedFile());
				}

			}
		});

		final JPanel clusterBtnPane = new JPanel();
		clusterBtnPane
			.setLayout(new BoxLayout(clusterBtnPane, BoxLayout.X_AXIS));
		clusterBtnPane.add(clusterBtn);
		clusterBtnPane.add(saveClusteringBtn);
		clusterBtnPane.add(loadClusteringBtn);
		clusterBtnPane.add(dataTypeBox);
		clusterBtnPane.add(this.functionBox);
		clusterBtnPane.setAlignmentX(Component.LEFT_ALIGNMENT);

		/******************************************************
		 * choice of metric and cluster order
		 ******************************************************/
		final JRadioButton scalarMetricBtn = new JRadioButton("Scalar Product");
		scalarMetricBtn
			.setSelected(AbstractMatrixClusterizer.DEFAULT_METRIC == AbstractMatrixClusterizer.SCALAR_PRODUCT);
		scalarMetricBtn.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				((AbstractMatrixClusterizer) ClusterControlPanel.this.glMatrix
					.getClusterizer())
					.setMetric(AbstractMatrixClusterizer.SCALAR_PRODUCT);
			}
		});

		final JRadioButton anyMetricBtn = new JRadioButton("other metric !");
		anyMetricBtn.setSelected(false);
		anyMetricBtn.setEnabled(false);
		anyMetricBtn.setToolTipText("suggest yours !");

		final ButtonGroup metricButtons = new ButtonGroup();

		metricButtons.add(scalarMetricBtn);
		metricButtons.add(anyMetricBtn);

		final ActionListener orderListerner = new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				if (ClusterControlPanel.this.glMatrix.getClusterizer() instanceof DefaultMatrixClusterizer) {
					((DefaultMatrixClusterizer) ClusterControlPanel.this.glMatrix
						.getClusterizer()).setOrder(Integer.parseInt(event
						.getActionCommand()));
				}
			}
		};

		final JRadioButton smallerFirstBtn = new JRadioButton("Smaller First");
		smallerFirstBtn
			.setSelected(DefaultMatrixClusterizer.DEFAULT_ORDER == DefaultMatrixClusterizer.SMALLER_FIRST);
		smallerFirstBtn.setActionCommand(""
				+ DefaultMatrixClusterizer.SMALLER_FIRST);
		smallerFirstBtn.addActionListener(orderListerner);

		final JRadioButton biggerFirstBtn = new JRadioButton("Bigger First");
		biggerFirstBtn
			.setSelected(DefaultMatrixClusterizer.DEFAULT_ORDER == DefaultMatrixClusterizer.BIGGER_FIRST);
		biggerFirstBtn.setActionCommand(""
				+ DefaultMatrixClusterizer.BIGGER_FIRST);
		biggerFirstBtn.addActionListener(orderListerner);

		final JRadioButton randomOrderBtn = new JRadioButton("Random Order");
		randomOrderBtn
			.setSelected(DefaultMatrixClusterizer.DEFAULT_ORDER == DefaultMatrixClusterizer.RANDOM_ORDER);
		randomOrderBtn.setActionCommand(""
				+ DefaultMatrixClusterizer.RANDOM_ORDER);
		randomOrderBtn.addActionListener(orderListerner);

		final JRadioButton firstVertexOrderBtn =
			new JRadioButton("First Vertex");
		firstVertexOrderBtn
			.setSelected(DefaultMatrixClusterizer.DEFAULT_ORDER == DefaultMatrixClusterizer.FIRST_VERTEX_ORDER);
		firstVertexOrderBtn.setActionCommand(""
				+ DefaultMatrixClusterizer.FIRST_VERTEX_ORDER);
		firstVertexOrderBtn.addActionListener(orderListerner);

		final ButtonGroup orderButtons = new ButtonGroup();

		orderButtons.add(smallerFirstBtn);
		orderButtons.add(biggerFirstBtn);
		orderButtons.add(randomOrderBtn);
		orderButtons.add(firstVertexOrderBtn);

		final JPanel metricPane = new JPanel();
		metricPane.setLayout(new BoxLayout(metricPane, BoxLayout.Y_AXIS));
		metricPane.setBorder(BorderFactory
			.createTitledBorder("clustering metric"));
		metricPane.add(scalarMetricBtn);
		metricPane.add(anyMetricBtn);
		metricPane.setAlignmentY(Component.TOP_ALIGNMENT);

		final JPanel orderPane = new JPanel();
		orderPane.setLayout(new BoxLayout(orderPane, BoxLayout.Y_AXIS));
		orderPane.setBorder(BorderFactory
			.createTitledBorder("clustering order"));
		orderPane.add(smallerFirstBtn);
		orderPane.add(biggerFirstBtn);
		orderPane.add(randomOrderBtn);
		orderPane.add(firstVertexOrderBtn);
		orderPane.setAlignmentY(Component.TOP_ALIGNMENT);

		/**************************************************
		 * choice of clustering algorithm
		 **************************************************/
		final JRadioButton agglomerativeBtn = new JRadioButton("Agglomerative");
		agglomerativeBtn.setSelected(true);
		agglomerativeBtn.setActionCommand("ALGO_AGGLOMERATIVE");
		agglomerativeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				ClusterControlPanel.this.glMatrix
					.setClusterizer(new DefaultMatrixClusterizer(
						ClusterControlPanel.this.glMatrix.getColumn(),
						ClusterControlPanel.this.glMatrix.getRow(),
						new AbstractMatrixClusterizer.ScalarProduct(),
						ClusterControlPanel.this.horizontal.getTreeModel(),
						ClusterControlPanel.this.vertical.getTreeModel()));
			}
		});

		final JRadioButton KMedoidBtn = new JRadioButton("KMedoid");
		KMedoidBtn.setSelected(false);
		KMedoidBtn.setActionCommand("ALGO_KMEDOID");
		KMedoidBtn.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				ClusterControlPanel.this.glMatrix
					.setClusterizer(new KMedoidClusterizer(
						ClusterControlPanel.this.glMatrix.getColumn(),
						ClusterControlPanel.this.glMatrix.getRow(),
						new AbstractMatrixClusterizer.ScalarProduct(),
						ClusterControlPanel.this.horizontal.getTreeModel(),
						ClusterControlPanel.this.vertical.getTreeModel()));
			}
		});

		final ButtonGroup algoButtons = new ButtonGroup();

		algoButtons.add(agglomerativeBtn);
		algoButtons.add(KMedoidBtn);

		final JPanel algoPanel = new JPanel();
		algoPanel.setLayout(new BoxLayout(algoPanel, BoxLayout.Y_AXIS));
		algoPanel.setBorder(BorderFactory.createTitledBorder("algorithm"));
		algoPanel.add(agglomerativeBtn);
		algoPanel.add(KMedoidBtn);
		algoPanel.setAlignmentY(Component.TOP_ALIGNMENT);

		final JPanel configPane = new JPanel();
		configPane.add(orderPane);
		configPane.add(metricPane);
		configPane.add(algoPanel);
		configPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		configPane.setLayout(new BoxLayout(configPane, BoxLayout.X_AXIS));

		/************************************************
		 * cluster tree layout controls
		 ************************************************/
		final JTabbedPane colorPane = new JTabbedPane();

		final JPanel horizontalTreeControl =
			new TreeControlPanel(this.horizontal);
		colorPane.addTab("Horizontal Clusters", horizontalTreeControl);

		final JPanel verticalTreeControl = new TreeControlPanel(this.vertical);
		colorPane.addTab("Vertical Clusters", verticalTreeControl);

		colorPane.setAlignmentX(Component.LEFT_ALIGNMENT);

		this.add(this.clusterToleranceSlider);
		this.add(configPane);
		this.add(clusterBtnPane);
		this.add(colorPane);
	}

	public void register(final AbstractGraphGetter agg) {
		this.functionBox.addItemListener(new ItemListener() {
			public void itemStateChanged(final ItemEvent e) {
				if (e.getSource().equals(ClusterControlPanel.this.functionBox)) {
					agg
						.setAggregationFunction((AggregationFunction) ClusterControlPanel.this.functionBox
							.getSelectedItem());
					// update the current graph with the new weights and redisplay the matrix
					ClusterControlPanel.this.glMatrix.setMinMaxIntensity();
					ClusterControlPanel.this.glMatrix.repaint();
				}
			}
		});
	}
}
