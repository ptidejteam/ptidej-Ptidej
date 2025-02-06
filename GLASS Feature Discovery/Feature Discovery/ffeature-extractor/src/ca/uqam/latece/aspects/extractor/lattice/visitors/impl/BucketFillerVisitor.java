package ca.uqam.latece.aspects.extractor.lattice.visitors.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import ca.uqam.latece.aspects.extractor.lattice.model.LatticeNode;

/**
 * this visitor constructs the set C[i] in line 12 of the algorithm named Algorithm 1
 * in the paper by Godin et al. that appeared in Comûtational Intelligence (1995),
 * vol. 11, no. 2, pp. 246-267.
 * 
 * It creates buckets of lattice nodes grouped by size of intent.
 * 
 * The bucket structure will be stored in a HashMap<Integer,Set<LatticeNode>>.
 * 
 * We can get the list of bucket sizes sorted. We can also get the bucket (set of lattice nodes)
 * for a particular size
 *  
 * @author Hafedh
 *
 */
public class BucketFillerVisitor extends AbstractVisitor {
	
	private HashMap<Integer,Set<LatticeNode>> bucketsPerSize = new HashMap<Integer,Set<LatticeNode>>();

	@Override
	public void processNode(LatticeNode node) {
		// get size of intent
		int intentSize = node.getIntent().size();
		
		// check if we already have a bucket for this size
		Integer wrapperObject = new Integer(intentSize);
		
		Set<LatticeNode> bucket = bucketsPerSize.get(wrapperObject);
		
		// if no bucket, create one and insert it into bucketsPerSize
		if (bucket == null){
			bucket = new HashSet<LatticeNode>();
			bucketsPerSize.put(wrapperObject, bucket);
		}
		
		// add current node to bucket
		bucket.add(node);
	}
	
	/**
	 * 
	 */
	public void processVisitedNode(LatticeNode node){
		
	}
	
	/**
	 * returns the bucket containing lattice nodes whose intent has cardinality size.
	 * This is a convenience method that takes an int as an integer. It calls
	 * the method that takes the int object wrapper (Integer) as an argument, which is the
	 * key used in <code>bucketsPerSize</code>
	 * @param size
	 * @return
	 */
	public Set<LatticeNode> getBucketForSize(int size){
		return getBucketForSize(new Integer(size));
	}

	/**
	 * returns the bucket containing lattice nodes whose intent has cardinality sizeObject.
	 * @param size
	 * @return
	 */
	public Set<LatticeNode> getBucketForSize(Integer sizeObject){
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
