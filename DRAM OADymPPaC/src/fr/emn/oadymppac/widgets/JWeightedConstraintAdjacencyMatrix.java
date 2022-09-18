package fr.emn.oadymppac.widgets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.LinkedList;
import salvo.jesus.graph.DirectedGraph;
import salvo.jesus.graph.Edge;
import salvo.jesus.graph.Graph;
import salvo.jesus.graph.GraphAddEdgeEvent;
import salvo.jesus.graph.GraphRemoveEdgeEvent;
import salvo.jesus.graph.Vertex;
import fr.emn.oadymppac.Constraint;
import fr.emn.oadymppac.DefaultUpdate;
import fr.emn.oadymppac.TimeManager;
import fr.emn.oadymppac.UpdateList;
import fr.emn.oadymppac.event.ConstraintChangedEvent;
import fr.emn.oadymppac.event.ReduceEvent;
import fr.emn.oadymppac.event.ReduceListener;
import fr.emn.oadymppac.event.RestoreEvent;
import fr.emn.oadymppac.event.RestoreListener;
import fr.emn.oadymppac.event.TimeEvent;
import fr.emn.oadymppac.event.TimeListener;
import fr.emn.oadymppac.event.WakeUpEvent;
import fr.emn.oadymppac.event.WakeUpListener;
import fr.emn.oadymppac.graph.EdgeWithHistory;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Ecole des Mines de Nantes</p>
 * @author Mohammad Ghoniem
 * @version $Revision: 1.2 $
 */

public class JWeightedConstraintAdjacencyMatrix extends
		JConstraintAdjacencyMatrix implements ReduceListener, RestoreListener,
		WakeUpListener, TimeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5955155826865357631L;
	int historyOrigin = 0;
	int lastN;
	int historyRange = 100;
	// keeps track of the maximum number of hits received by an edge in the graph
	int maxhits = 0;
	int start, end;
	TMRangeSlider slider;
	LinkedList edgeList = new LinkedList();

	public JWeightedConstraintAdjacencyMatrix(final Graph g) {
		super(g);
	}

	public void activateConstraint(final Constraint c) {
	}

	/**
	 * This method adds the constraint as a column in the matrix, adds the given
	 * constraint as a vertex of the graph and links it to the related variables,
	 * previously inserted in the graph. It also adds an entry in
	 * <code>nameVertex</code>.
	 */
	public void addConstraint(final Constraint c) {
		final String cname = c.getCName();
		this.addColumn(cname);

		final Vertex vertex = this.findVertex(cname);
		for (int i = 0; i < c.getVariableCount(); i++) {
			final Vertex other = this.getVertex(c.getVariableAt(i));
			if (other != null) {
				try {
					this.graph.addEdge(new EdgeWithHistory(vertex, other));
				}
				catch (final Exception e) {
				}
			}
		}
		this.nameVertex.put(cname, vertex);
		this.repaint();
	}

	/**
	 * This method adds the constraint as a column in the matrix, adds the given
	 * constraint as a vertex of the graph and links it to the related variables,
	 * previously inserted in the graph.  At its creation, each edge is given a
	 * sorted map of weights having initially one entry set to 1. It also adds
	 * an entry in <code>nameVertex</code> association map.
	 */
	public void addConstraint(final Constraint c, final int n) {
		this.lastN = n;
		final String name = c.getCName();
		System.out.println("adding constraint " + name);

		this.addColumn(name);
		final Vertex vertex = this.findVertex(name);

		for (int i = 0; i < c.getVariableCount(); i++) {
			final Vertex other = this.getVertex(c.getVariableAt(i));
			if (other != null) {
				final EdgeWithHistory edge = new EdgeWithHistory(vertex, other);
				edge.add(n);
				try {
					this.graph.addEdge(edge);
					this.edgeList.add(edge);
				}
				catch (final Exception e) {
				}
				//maxhits = Math.max(maxhits, edge.getHistoryLength());
			}
		}
		this.repaint();
	}
	public void constraintChanged(final ConstraintChangedEvent ev) {
		if (this.slider == null) {
			this.slider =
				(TMRangeSlider) TimeManager
					.getTimeManager(ev.getSolver())
					.getHandler();
			this.slider.setMinimum(ev.getN());
			this.slider.setMaximum(ev.getN());
		}
		this.slider.setMaximum(ev.getN());
		switch (ev.getAction()) {
			case ConstraintChangedEvent.ADDED :
				this.addConstraint(ev.getConstraint(), ev.getN());
				break;
			case ConstraintChangedEvent.REMOVED :
				this.removeConstraint(ev.getConstraint());
				break;
			case ConstraintChangedEvent.ACTIVATED :
				this.activateConstraint(ev.getConstraint());
				break;
			case ConstraintChangedEvent.DEACTIVATED :
				this.deactivateConstraint(ev.getConstraint());
				break;
		}
	}

	public void deactivateConstraint(final Constraint c) {
	}

	/**
	 * When an edge is added, the matrix is redisplayed.
	 */
	public void edgeAdded(final GraphAddEdgeEvent e) {
		this.repaint();
	}

	/**
	 * When an edge is removed, the matrix is redisplayed.
	 */
	public void edgeRemoved(final GraphRemoveEdgeEvent e) {
		this.repaint();
	}

	/**
	 * returns a float reflecting the activity of the edge in the time frame of
	 * interest.
	 * @param e
	 * @return
	 */
	public float getIntensity(final Edge e) {
		final EdgeWithHistory eh = (EdgeWithHistory) e;
		/* int delta;
		delta = e.getDelta(lastN/100, lastN);
		System.out.println("last n : "+lastN);
		System.out.println("delta is : "+delta + " maxhits is : "+maxhits);
		return (1 - (float)delta/(float)maxhits);
		*/
		//return 1 - (float)eh.getHistoryLength()/(float)maxhits;
		return 1 - (float) eh.getDelta(this.start, this.end)
				/ (float) this.maxhits;
	}

	/**
	 * This method iterates throw the edges of the graph and finds out
	 * the maximum number of hits per edge in the given interval
	 * @param start
	 * @param end
	 * @return
	 */
	public int getMaxHits(final int start, final int end) {
		int max = 0;
		System.out.println("edge list contains " + this.edgeList.size()
				+ " edges");
		for (final Iterator i = this.edgeList.iterator(); i.hasNext();) {
			final EdgeWithHistory e = (EdgeWithHistory) i.next();
			max = Math.max(max, e.getDelta(start, end));
		}
		return max;
	}

	public void paintComponent(final Graphics g) {
		//maxhits = getMaxHits(start, end);
		final int labelsize = 30;
		final float w =
			((float) this.getSize().getWidth() - labelsize)
					/ this.getColumnCount();
		final float h =
			((float) this.getSize().getHeight() - labelsize)
					/ this.getRowCount();
		float x = 0;
		float y = 0;
		String columnName, rowName;

		final Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.white);
		g2.fill(new Rectangle2D.Float(
			0f,
			0f,
			(float) this.getSize().getWidth(),
			(float) this.getSize().getHeight()));
		g2.setFont(this.getFont());
		g2.setColor(Color.black);
		g2.getFontMetrics();
		for (int j = 0; j < this.getRowCount(); j++) {
			//g2.setClip(0, (int)(j*h), labelsize, labelsize);
			g2.drawString(
				(String) this.rows.elementAt(j),
				(labelsize - ((String) this.rows.elementAt(j)).length()) / 2,
				j * h + 3 * labelsize / 2);
			rowName = (String) this.rows.elementAt(j);
			y = j * h + labelsize;
			if (rowName.equals(this.pickedRow)) {
				g2.setColor(this.highlightColor);
				g2.fill(new Rectangle2D.Float(labelsize, y, (float) this
					.getSize()
					.getWidth() - labelsize, h));
			}
			else if (rowName.equals(this.pickedColumn)) {
				g2.setColor(this.matchColor);
				g2.fill(new Rectangle2D.Float(labelsize, y, (float) this
					.getSize()
					.getWidth() - labelsize, h));
			}

		}

		Vertex vertex;
		for (int i = 0; i < this.getColumnCount(); i++) {
			columnName = (String) this.columns.elementAt(i);
			vertex = (Vertex) this.nameVertex.get(columnName);
			java.util.List l;
			if (this.graph instanceof DirectedGraph) {
				l =
					((DirectedGraph) this.graph)
						.getOutgoingAdjacentVertices(vertex);
			}
			else {
				l = this.graph.getAdjacentVertices(vertex);
			}

			x = i * w + labelsize;
			/*if (w > 3){
			    //g2.setClip((int)w*i, 0, labelsize, labelsize);
			    g2.drawString(columnName,
			                  x + (labelsize-columnName.length())/2,
			                  labelsize/2);
			}*/
			if (columnName.equals(this.pickedColumn)) {
				g2.setColor(this.highlightColor);
				g2.fill(new Rectangle2D.Float(x, labelsize, w, (float) this
					.getSize()
					.getHeight() - labelsize));
			}
			else if (columnName.equals(this.pickedRow)) {
				g2.setColor(this.matchColor);
				g2.fill(new Rectangle2D.Float(x, labelsize, w, (float) this
					.getSize()
					.getHeight() - labelsize));
			}
			for (final Iterator it = l.iterator(); it.hasNext();) {
				rowName = it.next().toString();
				y = this.indexOfRow(rowName) * h + labelsize;
				if (rowName.equals(this.pickedRow)
						|| columnName.equals(this.pickedColumn)) {
					g2.setColor(this.hightlightFilled);
				}
				else {
					g2.setColor(Color.getHSBColor(
						0.0f,
						0.0f,
						this.getIntensity(columnName, rowName)));
				}
				g2.fill(new Rectangle2D.Float(x, y, w, h));
			}
			x = x + w;
		}
	}

	/**
	 * Updates the history of the edge representing the involved pairs of
	 * variables and constraints provided in the <code>UpdateList</code> of this
	 * <code>ReduceEvent</code>.
	 */
	public void reduce(final ReduceEvent e) {
		this.updateConstraint(e.getCName(), e.getUpdateList(), e.getN());
	}

	/**
	 * Removes a constraint and its related column, vertex and potential edges.
	 */
	public void removeConstraint(final Constraint c) {
		/*String name = c.getCName();
		Vertex vertex = (Vertex)nameVertex.get(name);
		removeColumn(name);
		nameVertex.remove(name);
		synchronized(graph){
		    try {
		        graph.removeEdges(vertex);
		        graph.remove(vertex);
		    } catch (Exception e){}
		}*/
	}

	/**
	 * Updates the history of the edge representing the involved pairs of
	 * variables and constraints provided in the <code>UpdateList</code> of this
	 * <code>RestoreEvent</code>.
	 */
	public void restore(final RestoreEvent e) {
		this.updateConstraint(e.getCName(), e.getUpdateList(), e.getN());
	}

	public void timeChanged(final TimeEvent e) {
		this.start = ((TimeManager) e.getSource()).getLeftValue();
		this.end = ((TimeManager) e.getSource()).getRightValue();
		this.maxhits = this.getMaxHits(this.start, this.end);
		System.out.println("start = " + this.start + " end = " + this.end
				+ " # elements = " + (this.end - this.start));
		System.out.println("maxhits = " + this.maxhits);
		this.repaint();
	}

	public void updateConstraint(
		final String cname,
		final UpdateList ul,
		final int n) {
		this.lastN = n;
		if (ul == null) {
			return;
		}
		DefaultUpdate u;
		final Vertex cvertex = this.getVertex(cname);
		System.out.println("cname : " + cname);
		System.out.println("length of updatelist :" + ul.getLength());
		for (int i = 0; i < ul.getLength(); i++) {
			u = (DefaultUpdate) ul.getUpdate(i);
			System.out.println(u.toString());
			final Vertex vvertex = this.findVertex(u.getVName());
			System.out.println("vvertex=" + vvertex);
			System.out.println("cvertex=" + cvertex);
			final Edge edge = this.getEdge(cvertex, vvertex);
			System.err.println("Updating edge weight...");
			if (edge != null) {
				((EdgeWithHistory) edge).add(n);
				//maxhits = Math.max(maxhits, e.getHistoryLength());
				//System.err.println("maxhits="+maxhits);
			}
			else {
				System.err.println("problem with edge between cvertex="
						+ cvertex + " and vvertex=" + vvertex);
				System.err.println("edge=" + edge);
			}
		}
	}

	public void wakeUp(final WakeUpEvent e) {
		this.updateConstraint(e.getCName(), e.getCauseList(), e.getN());
	}
}
