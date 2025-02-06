package ca.uqam.latece.aspects.extractor.lattice.visitors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;


import ca.uqam.latece.aspects.extractor.lattice.graph.model.Node;
import ca.uqam.latece.aspects.extractor.lattice.graph.model.NodeFeatureType;
import ca.uqam.latece.aspects.extractor.lattice.model.LatticeNode;
import ca.uqam.latece.aspects.extractor.lattice.visitors.Visitor;
import ca.uqam.latece.aspects.extractor.lattice.visitors.impl.FeatureDetectorVisitor.FeatureType;
import ca.uqam.latece.aspects.extractor.lattice.visitors.impl.FeatureDetectorVisitor.FeatureTypeName;
import ca.uqam.latece.aspects.extractor.lattice.visitors.impl.FeatureDetectorVisitor.FeatureTypeTag;

public class LatticeGraphGenerator extends AbstractVisitor implements Visitor {
	// private List<Node> nodes = new ArrayList<Node>();

	private HashMap<LatticeNode, FeatureDetectorVisitor.FeatureType> candidateNodes;

	public LatticeGraphGenerator(HashMap<LatticeNode, FeatureDetectorVisitor.FeatureType> typedFeatureNodes) {

		candidateNodes = typedFeatureNodes;
	}

	@Override
	public void processNode(LatticeNode node) {
		// TODO Auto-generated method stub
		
		if (candidateNodes.containsKey(node)) {
			FeatureType types = candidateNodes.get(node);
			Collection<FeatureTypeTag> tags = types.featureTags;
			for (FeatureTypeTag tag : tags) {

				NodeFeatureType nodeType = new NodeFeatureType();
				nodeType.setFeatureTypeName(tag.name.toString());

				if (tag.name != FeatureTypeName.ADHOC) {
					nodeType.setAnchor(tag.anchorType.getFullyQualifiedName());
					nodeType.setCoverage(tag.anchorTypeBehaviorCoverage);
				}

				node.getTypes().add(nodeType);

			}
		}else {
			Set<LatticeNode>  parents = node.getParents();
			for(LatticeNode parent : parents ) {
				parent.getChildren().remove(node);
			}
		}
		
		List<LatticeNode> childToRemove = new ArrayList<>();
		for (LatticeNode child : node.getChildren()) {
			if (!candidateNodes.containsKey(child)) {
				childToRemove.add(child);
			}
		}

		node.getChildren().removeAll(childToRemove);

		Set<Object> stringExtents = new TreeSet<Object>();
		for (Object extentElement : node.getExtent()) {
			String extent = "";
			try {
				extent =  ((IType) extentElement).getFullyQualifiedParameterizedName();
			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			stringExtents.add(extent);

		}
		node.setExtent(stringExtents);

		Set<Object> stringIntents = new TreeSet<Object>();
		for (Object intentElement : node.getIntent()) {
			String intent = "";
			IMethod method = (IMethod)intentElement;
			try {
				intent = Signature.toString(method.getSignature(),method.getElementName(),method.getParameterNames(),true,true);
			} catch (JavaModelException e) {
				e.printStackTrace();
				
			}			
			stringIntents.add(intent);
		}
		node.setIntent(stringIntents);
		node.setName(String.valueOf(Objects.hash(node.getExtent(), node.getIntent())& 0xfffffff));

		
		
	}

}
