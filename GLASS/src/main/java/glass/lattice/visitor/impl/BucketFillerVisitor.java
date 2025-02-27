package glass.lattice.visitor.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import glass.lattice.model.ILatticeNode;
import glass.lattice.visitor.AbstractVisitor;

public class BucketFillerVisitor extends AbstractVisitor{
	private HashMap<Integer,Set<ILatticeNode>> bucketsPerSize = new HashMap<Integer,Set<ILatticeNode>>();

	@Override
	public void processNode(ILatticeNode node) {
		// get size of intent
		int intentSize = node.getIntent().size();
		
		// check if we already have a bucket for this size
		Integer wrapperObject = new Integer(intentSize);
		
		Set<ILatticeNode> bucket = bucketsPerSize.get(wrapperObject);
		
		// if no bucket, create one and insert it into bucketsPerSize
		if (bucket == null){
			bucket = new HashSet<ILatticeNode>();
			bucketsPerSize.put(wrapperObject, bucket);
		}
		
		// add current node to bucket
		bucket.add(node);
	}
	
	/**
	 * 
	 */
	public void processVisitedNode(ILatticeNode node){
		
	}
	
	/**
	 * returns the bucket containing lattice nodes whose intent has cardinality size.
	 * This is a convenience method that takes an int as an integer. It calls
	 * the method that takes the int object wrapper (Integer) as an argument, which is the
	 * key used in <code>bucketsPerSize</code>
	 * @param size
	 * @return
	 */
	public Set<ILatticeNode> getBucketForSize(int size){
		return getBucketForSize(new Integer(size));
	}

	/**
	 * returns the bucket containing lattice nodes whose intent has cardinality sizeObject.
	 * @param size
	 * @return
	 */
	public Set<ILatticeNode> getBucketForSize(Integer sizeObject){
		return bucketsPerSize.get(sizeObject);
	}
	
	/**
	 * returns a sorted set of bucket sizes
	 * @return
	 */
	public SortedSet<Integer> getBucketSizesSorted() {
		return  new TreeSet<Integer>(bucketsPerSize.keySet());
	}
}
