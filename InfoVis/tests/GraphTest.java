import infovis.Graph;
import infovis.graph.DefaultGraph;
import junit.framework.TestCase;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class GraphTest extends TestCase {
	public GraphTest(String name) {
		super(name);
	}
	
	public void testGraph() {
		Graph graph = new DefaultGraph();
		
		assertTrue(graph.getVerticesCount() == 0);
		int vertex = graph.addVertex();
		assertEquals(graph.getVerticesCount(), 1);
		int vertex2 = graph.addVertex();
		assertEquals(graph.getVerticesCount(), 2);
		int edge = graph.addEdge(vertex, vertex2);
		assertEquals(graph.getVerticesCount(), 2);
		assertEquals(graph.getFirstEdge(vertex), edge);
		assertEquals(graph.getEdgesCount(), 1);
		assertEquals(graph.getOutVertex(edge), vertex2);
		assertEquals(graph.getInVertex(edge), vertex);
		assertEquals(graph.getFirstEdge(vertex), edge);
		assertEquals(graph.getLastEdge(vertex), edge);
		assertEquals(graph.getNextEdge(edge), Graph.NIL);
		int vertex3 = graph.addVertex();
		assertEquals(graph.getVerticesCount(), 3);
		int edge2 = graph.addEdge(vertex, vertex3);
		assertEquals(graph.getEdgesCount(), 2);
		assertEquals(graph.getDegree(vertex), 2);
		assertEquals(graph.getVerticesCount(), 3);
		assertEquals(graph.getFirstEdge(vertex), edge);
		assertEquals(graph.getLastEdge(vertex), edge2);
		assertEquals(graph.getNextEdge(edge2), Graph.NIL);
		assertEquals(graph.getInVertex(edge2), vertex);
		assertEquals(graph.getOutVertex(edge2), vertex3);
		
	}
}
