package glass.lattice.visitor.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import glass.lattice.model.ILatticeNode;
import glass.lattice.model.impl.Node;
import glass.lattice.model.impl.NodeFeatureType;
import glass.lattice.visitor.impl.FeatureDetectorVisitor.FeatureType;
import glass.lattice.visitor.impl.FeatureDetectorVisitor.FeatureTypeName;
import glass.lattice.visitor.impl.FeatureDetectorVisitor.FeatureTypeTag;

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



	private HashMap<ILatticeNode,FeatureDetectorVisitor.FeatureType> candidateNodes;
	
	public PrintCandidatesVisitor(HashMap<ILatticeNode,FeatureDetectorVisitor.FeatureType> typedFeatureNodes){
		printer = JAVA_ELEMENT_PRINTER;
		candidateNodes = typedFeatureNodes;
	}
	/**
	 * the preprocessing of the children of the node prints the node type, and
	 *  computes the indent for printing the children
	 */
	public void preprocessChildren(ILatticeNode node){
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
		for (ILatticeNode child: node.getChildren()){
			getNodeIndents().put(child,childrenIndent);
		}
	}
	
	@Override
	/**
	 * if the node is a candidate node, print it. Otherwise, call process non-candidate nodes,
	 * which increases indents but prints nothing
	 */
	public void processNode(ILatticeNode node) {
		
		//adding the visited node to list of node graph
		// (imen)
		Node graphNode= new Node(getIds().get(node));
		int findNode = nodes.indexOf(graphNode);
		
		if (findNode>=0)
			graphNode = nodes.get(findNode);
		else 
			nodes.add(graphNode);
		for (ILatticeNode child: node.getChildren()){
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
	public void processVisitedNode(ILatticeNode node) {
		// check if candidate node:
		if (candidateNodes.containsKey(node)){
			// then call inherited version
			super.processVisitedNode(node);
		}
	}
	
	

}

