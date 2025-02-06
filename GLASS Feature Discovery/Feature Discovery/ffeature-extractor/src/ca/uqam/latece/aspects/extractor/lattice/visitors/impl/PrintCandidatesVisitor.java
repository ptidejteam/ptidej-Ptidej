package ca.uqam.latece.aspects.extractor.lattice.visitors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import ca.uqam.latece.aspects.extractor.lattice.graph.model.Node;
import ca.uqam.latece.aspects.extractor.lattice.graph.model.NodeFeatureType;
import ca.uqam.latece.aspects.extractor.lattice.model.LatticeNode;
import ca.uqam.latece.aspects.extractor.lattice.visitors.impl.FeatureDetectorVisitor.FeatureType;
import ca.uqam.latece.aspects.extractor.lattice.visitors.impl.FeatureDetectorVisitor.FeatureTypeName;
import ca.uqam.latece.aspects.extractor.lattice.visitors.impl.FeatureDetectorVisitor.FeatureTypeTag;

/**
 * this visitor pretty prints the candidate nodes in a lattice. It starts from either the top (increasing intent/feature size,
 * and decreasing extent/occurrences) or the bottom (decreasing feature/intent size, increasing extent/occurrences number) and 
 * only prints the contents of the candidate nodes. This provides a hierarchical printout of candidate features that is easier to read
 * than a simple list.
 * 
 * The difference between this visitor and the pretty printer:
 * 1) we only print non-visited AND candidate nodes
 * 2) non-candidate nodes are skipped
 * 3) candidate nodes that are already visited: we print the Node_ID
 * 4) we override the preprocessChildren() method so that it does not print anything since there may be several
 * levels between two consecutive candidate nodes. We will increment the indents to gives us an idea, but we
 * won't print "ITS CHILDREN:================="
 *  
 * @author Hafedh
 *
 */
public class PrintCandidatesVisitor extends LatticePrettyPrinter {
	/*
	 * Graph nodes returned by the printer to display in the view plugin
	 * 
	 * (imen) 
	 */
	private List<Node> nodes = new ArrayList<Node>();
		
	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}


	/** end (imen) **/
	/**
	 * instance variable containing list of candidate nodes
	 */
	private HashMap<LatticeNode,FeatureDetectorVisitor.FeatureType> candidateNodes;
	
	public PrintCandidatesVisitor(HashMap<LatticeNode,FeatureDetectorVisitor.FeatureType> typedFeatureNodes){
		printer = JAVA_ELEMENT_PRINTER;
		candidateNodes = typedFeatureNodes;
	}
	/**
	 * the preprocessing of the children of the node prints the node type, and
	 *  computes the indent for printing the children
	 */
	public void preprocessChildren(LatticeNode node){
		String nodeIndent = getNodeIndents().get(node);
		// if this is the first node, use an empty string as an indent
		if (nodeIndent == null) {
			nodeIndent="";
			getNodeIndents().put(node, nodeIndent);
		}
		// if candidate node, print what kind of node it is
		if (candidateNodes.containsKey(node)){
			nodeIndent = nodeIndent.replaceAll("->", "  ");
			System.out.println(nodeIndent+ "FEATURE TYPE: "+ candidateNodes.get(node));			
		}
		String childrenIndent = nodeIndent + "\t->";
		for (LatticeNode child: node.getChildren()){
			getNodeIndents().put(child,childrenIndent);
		}
	}
	
	@Override
	/**
	 * if the node is a candidate node, print it. Otherwise, call process non-candidate nodes,
	 * which increases indents but prints nothing
	 */
	public void processNode(LatticeNode node) {
		
		//adding the visited node to list of node graph
		// (imen)
		Node graphNode= new Node(getIds().get(node));
		int findNode = nodes.indexOf(graphNode);
		
		if (findNode>=0)
			graphNode = nodes.get(findNode);
		else 
			nodes.add(graphNode);
		for (LatticeNode child: node.getChildren()){
			/* get the id of the child or create it */
			String childId = getIds().get(child);
			if (childId == null) {
				/* hiding the word node to display only the id in the graph node*/
				childId =/* "NODE_"+ */ ""+globalCounter++;
				//childId = String.valueOf(node.hashCode());
				getIds().put(child, childId);
			}
			
			Node graphChild = new Node(childId);
			
			if (!nodes.contains(graphChild)) {
				
				nodes.add(graphChild);
			}
			graphNode.addChild( graphChild);
		}
		// end (imen)
						
		// check if candidate node:
		if (candidateNodes.containsKey(node)){
			// then call inherited version
			super.processNode(node);
			// assign the type, extent and intent of the node
			
			//(imen)
			//graphNode.setType(candidateNodes.get(node));
			FeatureType types  =  candidateNodes.get(node);
			Collection<FeatureTypeTag> tags = types.featureTags;
			for (FeatureTypeTag tag: tags) {
				
					NodeFeatureType  nodeType = new NodeFeatureType();
					nodeType.setFeatureTypeName(tag.name.toString());
					
					if (tag.name != FeatureTypeName.ADHOC) {
						nodeType.setAnchor(tag.anchorType.getFullyQualifiedName());
						nodeType.setCoverage(tag.anchorTypeBehaviorCoverage);
					}
					
					graphNode.getTypes().add(nodeType);
					
			}
			graphNode.setExtent(super.printExtent(node));
			graphNode.setIntent(super.printIntent(node));
			graphNode.setID(Objects.hash(super.printExtent(node),super.printIntent(node))& 0xfffffff);
			// end (imen)
			
			System.out.println("###########feature "+ candidateNodes.get(node));
			
		}
	}
	
	@Override
	public void processVisitedNode(LatticeNode node) {
		// check if candidate node:
		if (candidateNodes.containsKey(node)){
			// then call inherited version
			super.processVisitedNode(node);
		}
	}
	
	

}
