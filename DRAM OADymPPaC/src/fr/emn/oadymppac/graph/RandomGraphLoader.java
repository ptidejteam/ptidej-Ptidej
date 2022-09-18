package fr.emn.oadymppac.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import salvo.jesus.graph.DirectedWeightedEdge;
import salvo.jesus.graph.DirectedWeightedEdgeImpl;
import salvo.jesus.graph.Vertex;

/**
 * Towards InstrumentalJazz
 * @author Jean-Daniel Fekete
 * @date 5 sept. 2002
 */
public class RandomGraphLoader implements GraphLoader {

	/**
	 * @see fr.emn.oadymppac.graph.GraphLoader#load(URL)
	 */
	public float load(final URL url, final NamedGraphDelegator graph) {
		int line = 0;
		float max = 0;
		try {
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

			for (int ttype = st.nextToken(); ttype != StreamTokenizer.TT_EOF; ttype =
				st.nextToken()) {
				if (ttype != StreamTokenizer.TT_EOL) {
					max = Math.max(max, this.readLine(graph, st, ttype));
				}
				else {
					line++;
				}
			}
		}
		catch (final Exception ex) {
			System.err.println("Syntax error line " + line);
			ex.printStackTrace();
			return 0;
		}
		return max;
	}

	protected float readLine(
		final NamedGraphDelegator graph,
		final StreamTokenizer st,
		int ttype) throws Exception {

		String n1 = null;
		String n2 = null;
		Vertex v1, v2;

		if (ttype == StreamTokenizer.TT_WORD) {
			n1 = st.sval;
		}
		else if (ttype == StreamTokenizer.TT_NUMBER) {
			n1 = "" + st.nval;
		}
		else {
			throw new IOException("unexpected token " + st.toString()
					+ " read from stream, a word or number was expected");
		}

		v1 = graph.findVertex(n1);
		ttype = st.nextToken();

		if (ttype == StreamTokenizer.TT_WORD) {
			n2 = st.sval;
		}
		else if (ttype == StreamTokenizer.TT_NUMBER) {
			n2 = "" + st.nval;
		}
		else {
			throw new IOException("unexpected token " + st.toString()
					+ " read from stream, a word or number was expected");
		}

		v2 = graph.findVertex(n2);
		ttype = st.nextToken();

		if (ttype != StreamTokenizer.TT_NUMBER) {
			throw new IOException("unexpected token " + st.toString()
					+ " read from stream, a number was expected");
		}
		final double w = st.nval;
		final List l = graph.getEdges(v1);
		DirectedWeightedEdge edge = null;
		for (final Iterator it = l.iterator(); it.hasNext();) {
			final DirectedWeightedEdge e = (DirectedWeightedEdge) it.next();
			if (e.getVertexA() == v1 && e.getVertexB() == v2 /*||
																(e.getVertexA() == v2 && e.getVertexB() == v1)*/) {
				edge = e;
				edge.setWeight(edge.getWeight() + w);
				break;
			}
		}

		if (edge == null) {
			edge = new DirectedWeightedEdgeImpl(v1, v2, w);
			graph.addEdge(edge);
		}
		return (float) edge.getWeight();
	}

}
