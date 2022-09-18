/*
 * Created on 2004-04-05
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.emn.oadymppac.graph;

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
import fr.emn.oadymppac.event.TimeEvent;
import fr.emn.oadymppac.event.TimeListener;
import fr.emn.oadymppac.utils.IntervalList;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.2 $
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
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TxtGraphLoader {
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
	//	private boolean seeStructure;
	//private HashSet set;
	private final TimeEvent timeEvent = new TimeEvent(this);

	//	private MultiNameManager nameManager = MultiNameManager.getNameManager();
	//	private MultiNameManager.NameManager varnameManager =
	//		nameManager.getNameManagerFor(MultiNameManager.varManagerProp);
	//	private MultiNameManager.NameManager consnameManager =
	//		nameManager.getNameManagerFor(MultiNameManager.consManagerProp);

	String sliceEvent = "solution";

	Vector timeListeners = new Vector();

	public void addTimeListener(final TimeListener l) {
		this.timeListeners.addElement(l);
	}

	void addVertex(final String name) {
		try {
			this.graph.findVertex(name);
		}
		catch (final Exception e) {
			System.err.println("insertion error : could not insert vertex "
					+ name);
			e.printStackTrace();
		}
	}

	/**
	 * Notifies all time listeners of <code>TimeEvent</code>s.
	 *
	 */
	public void fireTimeChanged() {
		this.timeEvent.setN(this.n);
		for (int i = 0; i < this.timeListeners.size(); i++) {
			((TimeListener) this.timeListeners.elementAt(i))
				.timeChanged(this.timeEvent);
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
	 * @return
	 */
	public BoundedRangeModel getMaxModel() {
		return this.maxModel;
	}

	/**
	 * @return
	 */
	public String getSliceEvent() {
		return this.sliceEvent;
	}

	/**
	 * @return
	 */
	public BoundedRangeModel getVisibleEdgeModel() {
		return this.visibleEdgeModel;
	}

	/* (non-Javadoc)
	 * @see fr.emn.oadymppac.graph.GraphLoader#load(java.net.URL, fr.emn.oadymppac.graph.NamedGraphDelegator)
	 */
	public float load(final URL url, final NamedGraphDelegator graph) {

		int line = 0;
		//		try {
		////			mode =
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
		//	this.directed = graph.getUnderlyingGraph() instanceof DirectedGraph;

		try {
			System.out.println("url " + url);
			final InputStream istream = url.openStream();
			final Reader r = new BufferedReader(new InputStreamReader(istream));

			final StreamTokenizer st = new StreamTokenizer(r);
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
					this.readLine(graph, st, ttype);
				}
				else {
					line++;
				}
			}

			//intervalList.createListIterator();
		}
		catch (final Exception ex) {
			System.err.println("Syntax error line " + line);
			ex.printStackTrace();
			return 0;
		}

		return this.maxWeight;
	}

	protected void readLine(
		final NamedGraphDelegator graph,
		final StreamTokenizer st,
		int ttype) throws Exception {

		String n1 = null;
		String n2 = null;

		if (ttype == StreamTokenizer.TT_WORD) {
			n1 = st.sval;
		}
		else if (ttype == StreamTokenizer.TT_NUMBER) {
			n1 = "" + (int) st.nval;
		}
		else {
			throw new IOException("unexpected token " + st.toString()
					+ " read from stream, a word or number was expected");
		}
		this.addVertex(n1.intern());

		ttype = st.nextToken();

		if (ttype == StreamTokenizer.TT_WORD) {
			n2 = st.sval;
		}
		else if (ttype == StreamTokenizer.TT_NUMBER) {
			n2 = "" + (int) st.nval;
		}
		else {
			throw new IOException("unexpected token " + st.toString()
					+ " read from stream, a word or number was expected");
		}
		this.addVertex(n2.intern());

		ttype = st.nextToken();

		if (ttype != StreamTokenizer.TT_NUMBER) {
			throw new IOException("unexpected token " + st.toString()
					+ " read from stream, a number was expected");
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
		this.fireTimeChanged();
		this.updateEdge(n1, n2);

	}

	public void removeAllListeners() {
		this.timeListeners.clear();
	}

	public void removeTimeListener(final TimeListener l) {
		this.timeListeners.removeElement(l);
	}

	/**
	 * Returns the variables related to the passed
	 * constraint.
	 */

	public void setIntervalList(final IntervalList list) {
		this.intervalList = list;
	}

	/**
	 * @param model
	 */
	public void setMaxModel(final BoundedRangeModel model) {
		this.maxModel = model;
	}

	/**
	 * @param b
	 */
	//	public void setSeeStructure(final boolean b) {
	//		this.seeStructure = b;
	//	}

	/**
	 * @param string
	 */
	public void setSliceEvent(final String string) {
		this.sliceEvent = string;
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
	 * @param model
	 */
	public void setVisibleEdgeModel(final BoundedRangeModel model) {
		this.visibleEdgeModel = model;
	}

	/**
	 * Returns the list of constraints related to 
	 * the passed variable.
	 */

	/**
	 * This method updates the history of the edge
	 * specified by the passed vertices. The vertices
	 * and the edge are created when necessary.
	 * 
	 * @param v1
	 * @param v2
	 */
	void updateEdge(final String v1, final String v2) {
		EdgeWithHistory edge =
			(EdgeWithHistory) this.graph.getEdge(
				this.graph.findVertex(v1),
				this.graph.findVertex(v2));
		if (edge == null) {
			try {
				edge =
					this.directed ? new DirectedEdgeWithHistory(
						this.graph.findVertex(v1),
						this.graph.findVertex(v2)) : new EdgeWithHistory(
						this.graph.findVertex(v1),
						this.graph.findVertex(v2));
				this.graph.addEdge(edge);
			}
			catch (final Exception e) {
				e.printStackTrace();
			}
		}
		edge.add(this.n);
		this.updateMax(edge.getHistoryLength());
		this.updateVisible();
	}

	/**
	 * Updates the maximum bound of the intensity model.
	 * 
	 * @param m
	 */
	public void updateMax(final int m) {
		if (this.maxWeight < m) {
			this.maxWeight = m;
			if (this.maxModel != null) {
				this.maxModel.setMaximum(m);
			}
		}
	}

	/**
	 * Updates the visible edge model.
	 */
	public void updateVisible() {
		if (this.visibleEdgeModel != null) {
			this.visibleEdgeModel.setMaximum(this.graph.getEdgesCount());
		}
	}

}
