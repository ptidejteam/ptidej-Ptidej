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
package dram.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.net.URL;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.BoundedRangeModel;
import salvo.jesus.graph.DirectedGraph;
import fr.emn.oadymppac.event.TimeEvent;
import fr.emn.oadymppac.event.TimeListener;
import fr.emn.oadymppac.graph.DirectedWeightedEdgeWithHistory;
import fr.emn.oadymppac.graph.EdgeWithHistory;
import fr.emn.oadymppac.graph.NamedGraphDelegator;
import fr.emn.oadymppac.utils.IntervalList;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.8 $
 *
 * This class provides a loader for static constraint graphs. 
 * The graph is loaded once and for all. The vertices of the
 * graph may represent constraints only, variables only or
 * both constraints and variables. The user may indicate which
 * mode he whishes to have, the default mode handles constraints 
 * only. The vertices of this graph are linked explanation-wise ie.
 * in the CONSCONS mode, every pair of constraints enclosed in 
 * the same explanation are linked, in the VARVAR mode,
 * every pair of variables belonging to two constraints enclosed
 * in the same explanation are linked together and, lastly, in the
 * CONSVAR mode the variables of a given constraint are linked to
 * the other constraints enclosed in the same explanation.  
 */
/**
 * @author rachedsa
 */
public class GraphLoader {
	/*
	 * Created on 7 juil. 2003
	 *
	 */

	//private int mode = ConfigurationManager.GRAPH_CONSCONS;

	NamedGraphDelegator graph;
	BoundedRangeModel maxModel;
	BoundedRangeModel visibleEdgeModel;
	IntervalList intervalList;
	int maxWeight = 0;

	HashMap map = new HashMap();

	private int n;
	private boolean directed;
	//private HashSet set;
	private TimeEvent timeEvent = new TimeEvent(this);
	double weight;

	//	private MultiNameManager nameManager = MultiNameManager.getNameManager();
	//	private MultiNameManager.NameManager varnameManager =
	//		nameManager.getNameManagerFor(MultiNameManager.varManagerProp);
	//	private MultiNameManager.NameManager consnameManager =
	//		nameManager.getNameManagerFor(MultiNameManager.consManagerProp);

	String sliceEvent = "solution";

	Vector timeListeners = new Vector();

	/* (non-Javadoc)
	 * @see fr.emn.oadymppac.graph.GraphLoader#load(java.net.URL, fr.emn.oadymppac.graph.NamedGraphDelegator)
	 */
	public float load(URL url, NamedGraphDelegator graph) {
		int line = 0;
		//		try {
		//			mode =
		//				Integer.parseInt(
		//					ConfigurationManager.getConfigurationManager().get(
		//						ConfigurationManager.KEY_GRAPH_TYPE));
		//
		//			linkStrategy =
		//				Integer.parseInt(
		//					ConfigurationManager.getConfigurationManager().get(
		//						ConfigurationManager.KEY_LINK_TYPE));
		//
		//		} catch (NumberFormatException nex) {
		//			nex.printStackTrace();
		//		} catch (NullPointerException ex) {
		//			System.err.println(
		//				"No mapping for " + ConfigurationManager.KEY_GRAPH_TYPE);
		//		}
		this.graph = graph;
		this.directed = graph.getUnderlyingGraph() instanceof DirectedGraph;

		try {
			addVertex("org.junit.AssertionFailedError");
			addVertex("junit.framework.TestFailure");

			System.out.println("url " + url);
			InputStream istream = url.openStream();
			Reader r = new BufferedReader(new InputStreamReader(istream));

			StreamTokenizer st = new StreamTokenizer(r);
			st.eolIsSignificant(true);
			st.wordChars('$', '$');
			st.wordChars('_', '_');

			// characters that may appear in URLs
			st.wordChars('?', '?');
			st.wordChars('=', '=');
			st.wordChars('#', '#');
			st.wordChars(':', ':');
			st.wordChars('/', '/');
			st.wordChars('&', '&');
			st.wordChars('+', '+');
			this.intervalList.addValue(0);

			for (int ttype = st.nextToken(); ttype != StreamTokenizer.TT_EOF; ttype =
				st.nextToken()) {
				if (ttype != StreamTokenizer.TT_EOL) {
					readLine(graph, st, ttype);
				}
				else {
					line++;
				}
			}

			//intervalList.createListIterator();
		}
		catch (Exception ex) {
			System.err.println("Syntax error line " + line);
			ex.printStackTrace();
			return 0;
		}

		return this.maxWeight;
	}

	protected void readLine(
		NamedGraphDelegator graph,
		StreamTokenizer st,
		int ttype) throws Exception {

		String n1 = null;
		String n2 = null;

		if (ttype == StreamTokenizer.TT_WORD) {
			n1 = st.sval;
		}
		else if (ttype == StreamTokenizer.TT_NUMBER) {
			n1 = "" + (int) st.nval;
		}
		else
			throw new IOException("unexpected token " + st.toString()
					+ " read from stream, a word or number was expected");
		addVertex(n1.intern());

		ttype = st.nextToken();

		if (ttype == StreamTokenizer.TT_WORD) {
			n2 = st.sval;
		}
		else if (ttype == StreamTokenizer.TT_NUMBER) {
			n2 = "" + (int) st.nval;
		}
		else
			throw new IOException("unexpected token " + st.toString()
					+ " read from stream, a word or number was expected");
		addVertex(n2.intern());

		ttype = st.nextToken();

		if (ttype != StreamTokenizer.TT_NUMBER)
			throw new IOException("unexpected token " + st.toString()
					+ " read from stream, a number was expected");
		{
			this.weight = (int) st.nval;
		}

		ttype = st.nextToken();
		if (ttype == StreamTokenizer.TT_NUMBER) {
			this.n = (int) st.nval;
			this.intervalList.addValue(this.n);
		} //
			//throw new IOException(
			//	"unexpected token "
			//		+ st.toString()
			//		+ " read from stream, a number was expected");
		fireTimeChanged();
		updateEdge(n1, n2);

	}

	void addVertex(String name) {
		try {
			this.graph.findVertex(name);
		}
		catch (Exception e) {
			System.err.println("insertion error : could not insert vertex "
					+ name);
			e.printStackTrace();
		}
	}

	/**
	 * This method links the constraints enclosed
	 * in the passed explanation.
	 * 
	 * @param exp
	 */

	/* The currently updated variable is pointed to
	* by the variables of the constraints enclosed
	* in the passed explanation.
	* 
	* @param exp
	*/

	//	/**
	//	 * Resets the buffers.
	//	 */
	//	private void reset() {
	//		clist = null;
	//		vlist = null;
	//		vlist2 = null;
	//		//vname = null;
	//		//cname = null;
	//		//	set = null;
	//	}

	/**
	 * This method updates the history of the edge
	 * specified by the passed vertices. The vertices
	 * and the edge are created when necessary.
	 * 
	 * @param v1
	 * @param v2
	 */
	void updateEdge(String v1, String v2) {
		EdgeWithHistory edge =
			(EdgeWithHistory) this.graph.getEdge(
				this.graph.findVertex(v1),
				this.graph.findVertex(v2));
		//		if (edge == null) {
		//			try {
		//				edge =
		//					directed
		//						? new DirectedEdgeWithHistory(
		//							graph.findVertex(v1),
		//							graph.findVertex(v2))
		//						: new EdgeWithHistory(
		//							graph.findVertex(v1),
		//							graph.findVertex(v2));
		//				graph.addEdge(edge);
		//			} catch (Exception e) {
		//				e.printStackTrace();
		//			}
		//		}

		if (edge == null) {
			try {
				edge =
					this.directed ? new DirectedWeightedEdgeWithHistory(
						this.graph.findVertex(v1),
						this.graph.findVertex(v2),
						this.weight) : new EdgeWithHistory(
						this.graph.findVertex(v1),
						this.graph.findVertex(v2));
				this.graph.addEdge(edge);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		edge.add(this.n);
		edge.setWeight(this.weight);
		//System.out.println(edge.toString() + "   " + edge.getWeight());
		updateMax(edge.getHistoryLength());
		updateVisible();
	}

	/**
	 * @return
	 */
	public BoundedRangeModel getMaxModel() {
		return this.maxModel;
	}

	/**
	 * @return
	 */
	public BoundedRangeModel getVisibleEdgeModel() {
		return this.visibleEdgeModel;
	}

	/**
	 * @param model
	 */
	public void setMaxModel(BoundedRangeModel model) {
		this.maxModel = model;
	}

	/**
	 * @param model
	 */
	public void setVisibleEdgeModel(BoundedRangeModel model) {
		this.visibleEdgeModel = model;
	}

	/**
	 * Updates the visible edge model.
	 */
	public void updateVisible() {
		if (this.visibleEdgeModel != null)
			this.visibleEdgeModel.setMaximum(this.graph.getEdgesCount());
	}

	/**
	 * Updates the maximum bound of the intensity model.
	 * 
	 * @param m
	 */
	public void updateMax(int m) {
		if (this.maxWeight < m) {
			this.maxWeight = m;
			if (this.maxModel != null)
				this.maxModel.setMaximum(m);
		}
	}

	public void addTimeListener(TimeListener l) {
		this.timeListeners.addElement(l);
	}

	public void removeTimeListener(TimeListener l) {
		this.timeListeners.removeElement(l);
	}

	public void removeAllListeners() {
		this.timeListeners.clear();
	}

	/**
	 * Notifies all time listeners of <code>TimeEvent</code>s.
	 *
	 */
	public void fireTimeChanged() {
		this.timeEvent.setN(this.n);
		for (int i = 0; i < this.timeListeners.size(); i++)
			((TimeListener) this.timeListeners.elementAt(i))
				.timeChanged(this.timeEvent);
	}

	//	/**
	//	 * Returns the internal / external names 
	//	 * association map.
	//	 * 
	//	 * @return
	//	 */
	//	public HashMap getExternalNames() {
	//		return externalNames;
	//	}
	//	
	//	/**
	//	 * Prints the contents of the internal / external names
	//	 * association map.
	//	 *
	//	 */
	//	public void printAllNames(){
	//		Set entries = externalNames.entrySet();
	//		Iterator iter = entries.iterator();
	//		Map.Entry tmp;
	//		while (iter.hasNext()){
	//			tmp = (Map.Entry)iter.next();
	//			System.out.println((String)tmp.getKey()+" : "+(String)tmp.getValue());
	//		}
	//	}

	/**
	 * @param b
	 */
	//	public void setSeeStructure(boolean b) {
	//		seeStructure = b;
	//	}

	/**
	 * Returns the list of constraints related to 
	 * the passed variable.
	 */

	/**
	 * Returns the variables related to the passed
	 * constraint.
	 */

	public void setIntervalList(IntervalList list) {
		this.intervalList = list;
	}

	/**
	 * @return
	 */
	public String getSliceEvent() {
		return this.sliceEvent;
	}

	/**
	 * @param string
	 */
	public void setSliceEvent(String string) {
		this.sliceEvent = string;
	}

}
