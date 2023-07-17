package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.test.Graph;


public class TestStronglyConnectedComponents  {

	StronglyConnectedComponents<String> scc=new StronglyConnectedComponents<String>();
	Graph graph=new Graph();

	@Before
	public void initTestGraph() {
		graph.addEdge("A", "B");
		graph.addEdge("B", "C");
		graph.addEdge("C", "A");
		graph.addEdge("C", "D");
		graph.addEdge("D", "E");
		
		graph.addEdge("1", "2");
		graph.addEdge("2", "1");
		graph.addEdge("1", "1");

		graph.addEdge("me, myself, and I", "me, myself, and I");

		graph.addIsolatedNode("Crusoe");
	}
	
	@Test
	public void withAnyIsolatedNodes() throws Exception {
		
		scc.calculateConnectedComponents(graph, 1, false);
		
		assertEquals(5, scc.getConnectedComponentCount());
		assertSCC("A", "A","B","C");
		assertSCC("1", "1", "2");
		assertSCC("me, myself, and I", "me, myself, and I");
		assertSCC("Crusoe", "Crusoe");
		assertSCC("D", "D");
		assertSCCEmpty("E");
	}

	@Test
	public void withSelfReferencingIsolatedNodes() throws Exception {
		
		scc.calculateConnectedComponents(graph, 1, true);
		assertEquals(3, scc.getConnectedComponentCount());
		assertSCC("B", "A","B","C");
		assertSCC("2", "1", "2");
		assertSCC("me, myself, and I", "me, myself, and I");
		assertSCCEmpty("D");
	}

	@Test
	public void withMinsize() throws Exception {
		scc.calculateConnectedComponents(graph, 2, true);
		assertEquals(2, scc.getConnectedComponentCount());
		assertSCC("C", "A","B","C");
		assertSCC("1", "1", "2");
		assertSCCEmpty("me, myself, and I");
		
		scc.calculateConnectedComponents(graph, 3, false);
		assertEquals(1, scc.getConnectedComponentCount());
		assertSCC("A", "A","B","C");
		assertSCCEmpty("1");
		
		scc.calculateConnectedComponents(graph, 4, false);
		assertEquals(0, scc.getConnectedComponentCount());
		assertSCCEmpty("B");
	}
	
	private void assertSCC(String node, String... expectedCCNodes) {
		Collection<String> cc=new HashSet<String>(); // Connected component of the node
		int index=scc.getIndexFor(node);
		if(index>=0)
			cc=scc.getConnectedComponent(index);

		assertEquals("Connected component size", expectedCCNodes.length, cc.size());
		for(String ccMember : expectedCCNodes)
			assertTrue("Connected component contains "+ccMember, cc.contains(ccMember));
	}
	
	private void assertSCCEmpty(String node) {
		assertTrue(scc.getIndexFor(node)<0);
	}
}

