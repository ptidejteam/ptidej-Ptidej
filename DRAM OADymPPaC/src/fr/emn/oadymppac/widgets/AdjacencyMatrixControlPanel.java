package fr.emn.oadymppac.widgets;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;
import javax.swing.BoxLayout;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import dram.utils.salvo.MyGraphImpl;
import fr.emn.oadymppac.event.TimeEvent;
import fr.emn.oadymppac.event.TimeListener;
import fr.emn.oadymppac.graph.EdgeWithHistory;
import fr.emn.oadymppac.graph.NamedGraphDelegator;
import fr.emn.oadymppac.graph.aggregation.AbstractGraphGetter;
import fr.emn.oadymppac.graph.clustering.MatrixClusterizer;
import fr.emn.oadymppac.utils.IntervalList;

/**
 * Control Panel for GLAdjacencyMatrix
 * 
 * @author Jean-Daniel Fekete
 * @date 13 sept. 2002
 */
public class AdjacencyMatrixControlPanel extends JTabbedPane implements
		ChangeListener, TimeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3868554205581511106L;
	/**
	 * DOCUMENT ME!
	 * 
	 * @param args DOCUMENT ME!
	 */
	public static void main(final String[] args) {
		final JFrame frame = new JFrame("AdjacencyMatrixControlPanel");
		frame.getContentPane().add(
			new AdjacencyMatrixControlPanel(
				new GLAdjacencyMatrix(
					new NamedGraphDelegator(new MyGraphImpl())),
				new JTreeBrowser(),
				new JTreeBrowser()));
		frame.pack();
		frame.setVisible(true);
	}
	GLAdjacencyMatrix glMatrix;
	JSlider labelSizeSlider;
	JSlider clusterToleranceSlider;
	JProgressBar visibleEdges;
	AdjacencyMatrixRowColumnControl rowControl;
	AdjacencyMatrixRowColumnControl columnControl;
	MatrixClusterizer clusterizer;
	JSlider intensitySlider;
	//RangeSlider historySlider;
	HistoryControlPanel hcp;
	ClusterControlPanel clusterTab;

	HashMap externalNames;

	/**
	 * Creates a new AdjacencyMatrixControlPanel object.
	 * 
	 * @param glMatrix the adjacency matrix to control
	 */
	public AdjacencyMatrixControlPanel(
		final GLAdjacencyMatrix glMatrix,
		final IcicleTreeBrowser htree,
		final IcicleTreeBrowser vtree) {
		this.glMatrix = glMatrix;
		this.clusterizer = glMatrix.getClusterizer();

		final JPanel mainTab = new JPanel();
		this.initMainTab(mainTab);
		this.addTab("Main", new JScrollPane(mainTab));

		final JPanel filtersTab = new JPanel();
		this.initFiltersTab(filtersTab);
		this.addTab("Filters", filtersTab);

		this.clusterTab = new ClusterControlPanel(glMatrix, htree, vtree);
		this.addTab("Cluster", this.clusterTab);

		final JPanel fisheyePanel = new FisheyeControlPanel(glMatrix.getLens());

		this.addTab("Fisheye", fisheyePanel);

		final JPanel utilTab = new GLUtilityPanel(glMatrix);
		this.addTab("Util", utilTab);

	}

	/**
	 * Returns the columnControl.
	 * @return AdjacencyMatrixRowColumnControl
	 */
	public AdjacencyMatrixRowColumnControl getColumnControl() {
		return this.columnControl;
	}

	public String getExternalNameFor(final String internal) {
		if (this.externalNames != null) {
			return (String) this.externalNames.get(internal);
		}
		return "";
	}

	/**
	 * @return
	 */
	public HashMap getExternalNames() {
		return this.externalNames;
	}

	/**
	 * Returns the historyModel.
	 * 
	 * @return BoundedRangeModel
	 */
	public BoundedRangeModel getHistoryModel() {
		return this.hcp.slider.getModel();
	}

	/**
	 * Returns the intensityModel.
	 * 
	 * @return BoundedRangeModel
	 */
	public BoundedRangeModel getIntensityModel() {
		return this.intensitySlider.getModel();
	}

	public IntervalList getIntervalList() {
		return this.hcp.intervalList;
	}

	/**
	 * Returns the rowControl.
	 * @return AdjacencyMatrixRowColumnControl
	 */
	public AdjacencyMatrixRowColumnControl getRowControl() {
		return this.rowControl;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public BoundedRangeModel getVisibleEdgesModel() {
		return this.visibleEdges.getModel();
	}

	/**
	 * Returns <code>true</code> when the edge set 
	 * contains edges with history, <code>false</code> otherwise.
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean hasEdgesWithHistory() {
		final Iterator it = this.glMatrix.graph.getEdgeSet().iterator();

		while (it.hasNext()) {
			if (!(it.next() instanceof EdgeWithHistory)) {
				return false;
			}
		}

		return true;
	}

	void initFiltersTab(final JPanel panel) {
	}
	void initMainTab(final JPanel panel) {
		////////////////////////////////////////////////////////////
		// Set up panel.
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		this.labelSizeSlider =
			new JSlider(
				SwingConstants.HORIZONTAL,
				0,
				200,
				this.glMatrix.getLabelSize());
		this.labelSizeSlider.setMajorTickSpacing(50);
		this.labelSizeSlider.setMinorTickSpacing(10);
		this.labelSizeSlider.setPaintTicks(true);
		this.labelSizeSlider.setPaintLabels(true);
		this.labelSizeSlider.setBorder(BorderFactory
			.createTitledBorder("Label Size"));
		this.labelSizeSlider.addChangeListener(new ChangeListener() {
			/**
			 * @see javax.swing.event.ChangeListener#stateChanged(ChangeEvent)
			 */
			public void stateChanged(final ChangeEvent e) {
				AdjacencyMatrixControlPanel.this.glMatrix
					.setLabelSize(AdjacencyMatrixControlPanel.this.labelSizeSlider
						.getValue());
				AdjacencyMatrixControlPanel.this.glMatrix.repaint();
			}
		});
		this.labelSizeSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(this.labelSizeSlider);

		this.visibleEdges = new JProgressBar(SwingConstants.HORIZONTAL) {
			private static final long serialVersionUID = 2151703731449884443L;

			public String getString() {
				final Format format = NumberFormat.getIntegerInstance();

				return format.format(Double.valueOf(this.getModel().getValue()));
			}
		};

		this.visibleEdges.setBorder(BorderFactory
			.createTitledBorder("Visible Edges"));
		this.visibleEdges.setStringPainted(true);
		this.visibleEdges.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(this.visibleEdges);

		this.intensitySlider =
			new JSlider(new DefaultBoundedRangeModel(1, 0, 0, 2));
		this.intensitySlider.setOrientation(SwingConstants.HORIZONTAL);
		this.intensitySlider.setBorder(BorderFactory
			.createTitledBorder("Intensity"));
		this.intensitySlider.addChangeListener(new ChangeListener() {
			/**
			 * @see javax.swing.event.ChangeListener#stateChanged(ChangeEvent)
			 */
			public void stateChanged(final ChangeEvent e) {
				AdjacencyMatrixControlPanel.this.glMatrix
					.setMaxIntensity(AdjacencyMatrixControlPanel.this.intensitySlider
						.getValue());
				//glMatrix.setMaxIntensity(8.0f);
				//System.out.println("intensity " + intensitySlider.getValue() );
			}
		});
		this.intensitySlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(this.intensitySlider);

		if (this.hasEdgesWithHistory()) {
			this.hcp = new HistoryControlPanel(new IntervalList());
			this.hcp.register(this.glMatrix);
			this.hcp.setAlignmentX(Component.LEFT_ALIGNMENT);
			panel.add(this.hcp);
		}

		this.rowControl =
			new AdjacencyMatrixRowColumnControl(
				"Row Control",
				this.glMatrix.getRow());
		this.rowControl.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(this.rowControl);
		this.columnControl =
			new AdjacencyMatrixRowColumnControl(
				"Column Control",
				this.glMatrix.getColumn());
		this.columnControl.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(this.columnControl);

		final JPanel horizontal = new JPanel();
		final GridBagLayout gbl = new GridBagLayout();
		final GridBagConstraints c = new GridBagConstraints();
		horizontal.setLayout(gbl);

		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		final JButton dotButton = new JButton("Layout with DOT");
		dotButton.addActionListener(new ActionListener() {
			/**
			 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				final NodeLinkThread dotThread =
					new NodeLinkThread(
						AdjacencyMatrixControlPanel.this.glMatrix,
						"dot");
				dotThread.start();
			}
		});
		gbl.setConstraints(dotButton, c);
		dotButton.setAlignmentY(Component.TOP_ALIGNMENT);
		horizontal.add(dotButton);

		final JButton neatoButton = new JButton("Layout with NEATO");
		neatoButton.addActionListener(new ActionListener() {
			/**
			 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				final NodeLinkThread neatoThread =
					new NodeLinkThread(
						AdjacencyMatrixControlPanel.this.glMatrix,
						"neato");
				neatoThread.start();
			}
		});
		gbl.setConstraints(neatoButton, c);
		neatoButton.setAlignmentY(Component.TOP_ALIGNMENT);
		horizontal.add(neatoButton);

		c.gridwidth = GridBagConstraints.REMAINDER;
		final JButton twopiButton = new JButton("Layout with TWOPI");
		twopiButton.addActionListener(new ActionListener() {
			/**
			 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				final NodeLinkThread twopiThread =
					new NodeLinkThread(
						AdjacencyMatrixControlPanel.this.glMatrix,
						"twopi");
				twopiThread.start();
			}
		});
		gbl.setConstraints(twopiButton, c);
		twopiButton.setAlignmentY(Component.TOP_ALIGNMENT);
		horizontal.add(twopiButton);

		c.weightx = 0.0;
		final JButton exportDotButton = new JButton("Export DOT file");
		exportDotButton.addActionListener(new ActionListener() {
			/**
			 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
			 */
			public void actionPerformed(final ActionEvent e) {
				final NodeLinkThread exportThread =
					new NodeLinkThread(
						AdjacencyMatrixControlPanel.this.glMatrix,
						JNodeLinkManager.PGM_EXPORT);
				exportThread.start();
			}
		});
		gbl.setConstraints(exportDotButton, c);
		horizontal.add(exportDotButton);
		horizontal.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(horizontal);
	}

	public void register(final Object o) {
		if (o instanceof AbstractGraphGetter) {
			this.clusterTab.register((AbstractGraphGetter) o);
		}
	}

	/**
	 * @param map
	 */
	public void setExternalNames(final HashMap map) {
		this.externalNames = map;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param n DOCUMENT ME!
	 */
	public void setLast(final int n) {
		this.setLast(n, this.getHistoryModel());
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param n DOCUMENT ME!
	 * @param model DOCUMENT ME!
	 */
	public void setLast(final int n, final BoundedRangeModel model) {
		final boolean wasMax =
			model.getMaximum() == model.getValue() + model.getExtent();
		model.setMaximum(n);

		//glmatrix.setMaxIntensity(e.getN());
		if (wasMax) {
			model.setExtent(model.getMaximum() - model.getValue());
		}
	}

	/**
	 * @see javax.swing.event.ChangeListener#stateChanged(ChangeEvent)
	 */
	public void stateChanged(final ChangeEvent e) {
	}

	/* (non-Javadoc)
	 * @see fr.emn.oadymppac.event.TimeListener#timeChanged(fr.emn.oadymppac.event.TimeEvent)
	 */
	public void timeChanged(final TimeEvent e) {
		this.setLast(e.getN());
	}

}