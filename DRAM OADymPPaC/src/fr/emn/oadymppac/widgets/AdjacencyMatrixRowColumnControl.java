package fr.emn.oadymppac.widgets;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import fr.emn.oadymppac.graph.AdjacencyMatrixRowColumn;
import fr.emn.oadymppac.graph.GraphUtils;
import fr.emn.oadymppac.graph.NamedGraphDelegator;

/**
 * Controls for AdjacencyMatrix rows and columns
 * 
 * @version $Revision: 1.2 $
 * @author Jean-Daniel Fekete
 */
public class AdjacencyMatrixRowColumnControl extends JPanel implements
		ItemListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7483107860731562861L;
	AdjacencyMatrixRowColumn rowOrColumn;
	JComboBox sortCombo;
	Map sortMethod;

	/**
	 * Creates a new AdjacencyMatrixRowColumnControl object.
	 *
	 * @param label The name of the axis
	 * @param m the row or column
	 */
	public AdjacencyMatrixRowColumnControl(
		final String label,
		final AdjacencyMatrixRowColumn m) {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setBorder(BorderFactory.createTitledBorder(label));

		this.rowOrColumn = m;
		this.sortMethod = new HashMap();
		this.sortCombo = new JComboBox();
		this.sortCombo.setMaximumSize(new Dimension(
			Integer.MAX_VALUE,
			(int) this.sortCombo.getPreferredSize().getHeight()));
		this.sortCombo.addItemListener(this);
		this.add(this.sortCombo);
		this.registerSortMethods();
	}

	/**
	 * Add a sort method
	 *
	 * @param name name of the sort method
	 * @param order comparator for the sort method, taking two vertex names for comparisons.
	 */
	public void addSortMethod(final String name, final Comparator order) {
		final DefaultComboBoxModel model =
			(DefaultComboBoxModel) this.sortCombo.getModel();
		this.sortMethod.put(name, order);
		model.addElement(name);
	}

	public NamedGraphDelegator getGraph() {
		return this.rowOrColumn.getGraph();
	}

	/**
	 * @see java.awt.event.ItemListener#itemStateChanged(ItemEvent)
	 */
	public void itemStateChanged(final ItemEvent e) {
		final String name = (String) e.getItem();
		final Comparator comp = (Comparator) this.sortMethod.get(name);

		if (comp != null) {
			this.rowOrColumn.sort(comp);
		}
	}

	/**
	 * Register standard sort methods
	 */
	protected void registerSortMethods() {
		this.addSortMethod("none", null);
		this.addSortMethod("alpha", new Comparator() {
			/**
			 * @see java.util.Comparator#compare(Object, Object)
			 */
			public int compare(final Object o1, final Object o2) {
				return ((String) o1).compareTo((String) o2);
			}

			/**
			 * @see java.util.Comparator#equals(Object)
			 */
			public boolean equals(final Object obj) {
				return false;
			}
		});
		this.addSortMethod("degree", new Comparator() {
			/**
			 * @see java.util.Comparator#compare(Object, Object)
			 */
			public int compare(final Object o1, final Object o2) {
				final String name1 = (String) o1;
				final String name2 = (String) o2;

				return GraphUtils.vertexDegree(
					AdjacencyMatrixRowColumnControl.this.getGraph(),
					name1)
						- GraphUtils.vertexDegree(
							AdjacencyMatrixRowColumnControl.this.getGraph(),
							name2);
			}

			/**
			 * @see java.util.Comparator#equals(Object)
			 */
			public boolean equals(final Object obj) {
				return false;
			}
		});

		this.addSortMethod("weighted degree", new Comparator() {
			/**
			 * @see java.util.Comparator#compare(Object, Object)
			 */
			public int compare(final Object o1, final Object o2) {
				final String name1 = (String) o1;
				final String name2 = (String) o2;

				final double w1 =
					GraphUtils.vertexWeightedDegree(
						AdjacencyMatrixRowColumnControl.this.getGraph(),
						name1);
				final double w2 =
					GraphUtils.vertexWeightedDegree(
						AdjacencyMatrixRowColumnControl.this.getGraph(),
						name2);
				if (w1 == w2) {
					return 0;
				}
				else if (w1 < w2) {
					return -1;
				}
				else {
					return 1;
				}
			}

			/**
			 * @see java.util.Comparator#equals(Object)
			 */
			public boolean equals(final Object obj) {
				return false;
			}
		});

		this.addSortMethod("max weighted degree", new Comparator() {
			/**
			 * @see java.util.Comparator#compare(Object, Object)
			 */
			public int compare(final Object o1, final Object o2) {
				final String name1 = (String) o1;
				final String name2 = (String) o2;

				final double w1 =
					GraphUtils.vertexMaxWeightedDegree(
						AdjacencyMatrixRowColumnControl.this.getGraph(),
						name1);
				final double w2 =
					GraphUtils.vertexMaxWeightedDegree(
						AdjacencyMatrixRowColumnControl.this.getGraph(),
						name2);
				if (w1 == w2) {
					return 0;
				}
				else if (w1 < w2) {
					return -1;
				}
				else {
					return 1;
				}
			}

			/**
			 * @see java.util.Comparator#equals(Object)
			 */
			public boolean equals(final Object obj) {
				return false;
			}
		});
	}
}