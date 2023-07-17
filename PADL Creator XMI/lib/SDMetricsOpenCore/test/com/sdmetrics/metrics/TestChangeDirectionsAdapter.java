package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.sdmetrics.test.Graph;


public class TestChangeDirectionsAdapter  {

	Graph graph=new Graph();

	@Before
	public void initTestGraph() {
		graph.addIsolatedNode("C");
		graph.addEdge("A", "B");
		graph.addEdge("B", "C");
		graph.addEdge("B", "D");
	}
	
	@Test
	public void directed() throws Exception {
		ChangeDirectionsAdapter<String> cda=new ChangeDirectionsAdapter<String>(graph, false);
		assertNodes(cda.getNodes(), "A", "B", "C");
		assertNodes(cda.getNeighbors("A"));
		assertNodes(cda.getNeighbors("B"),"A");
		assertNodes(cda.getNeighbors("C"),"B");
	}

	@Test
	public void undirected() throws Exception {
		
		ChangeDirectionsAdapter<String> cda=new ChangeDirectionsAdapter<String>(graph, true);
		assertNodes(cda.getNodes(), "A", "B", "C");
		assertNodes(cda.getNeighbors("A"),"B");
		assertNodes(cda.getNeighbors("B"),"A","C");
		assertNodes(cda.getNeighbors("C"),"B");
	}

	private void assertNodes(Collection<String> set, String... expectedNodes) {
		assertEquals(expectedNodes.length, set.size());
		for(String node : expectedNodes)
			assertTrue("Set contains node "+node, set.contains(node));
	}
}
