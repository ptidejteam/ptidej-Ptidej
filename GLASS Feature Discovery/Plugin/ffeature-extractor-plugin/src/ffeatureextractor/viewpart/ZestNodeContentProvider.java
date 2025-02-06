package ffeatureextractor.viewpart;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;

import ca.uqam.latece.aspects.extractor.lattice.graph.model.Node;
import ca.uqam.latece.aspects.extractor.lattice.model.LatticeNode;



public class ZestNodeContentProvider extends ArrayContentProvider  implements IGraphEntityContentProvider {

    @Override
    public Object[] getConnectedTo(Object entity) {
        if (entity instanceof Node) {
        	Node node = (Node) entity;
            return node.getChildren().toArray();
        }
        throw new RuntimeException("Type not supported");
    }
}
