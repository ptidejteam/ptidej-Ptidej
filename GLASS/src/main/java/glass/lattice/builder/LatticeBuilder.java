package glass.lattice.builder;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import glass.lattice.model.ILattice;
import glass.lattice.model.ILatticeNode;
import glass.lattice.model.IRelation;
import glass.lattice.model.impl.Lattice;
import glass.lattice.model.impl.LatticeNode;
import glass.lattice.visitor.impl.BucketFillerVisitor;


public class LatticeBuilder implements ILatticeBuilder{
	@Override
	public ILattice buildLattice(IRelation aRelation) {

		ILattice lattice = new Lattice();

		// first, initialize top
		initializeTopBottom(lattice, aRelation);

		// next, build the lattice incrementally by adding the elements of the
		// relation one by one
		for (Object domainElement : aRelation.getDomain()) {
			// get the list of methods
			Set<Object> elementImageSet = aRelation.getImage(domainElement);
			add(lattice, domainElement, elementImageSet);

		}
		return lattice;
	}

	@Override
	public void initializeTopBottom(ILattice lattice, IRelation aRelation) {
		// 1. create top LatticeNode

		// 1.a instantiate
		ILatticeNode topNode = new LatticeNode();

		// 1.b extent is full domain and intent is null
		Set<Object> domain = aRelation.getDomain();
		topNode.addCollectionToExtent(domain);

		// 1.c set topNode as top of lattice
		lattice.setTop(topNode);

		// 2. create bottom lattice node
		// 2.a instantiate
		ILatticeNode bottomNode = new LatticeNode();

		// 2.b intent is full image
		Set<Object> allImages = aRelation.getAllImages();
		bottomNode.addCollectionToIntent(allImages);

		// 2.c set bottomNode as bottom of lattice
		lattice.setBottom(bottomNode);

		// 3. connect top to bottom
		topNode.addChild(bottomNode);
		bottomNode.addParent(topNode);

	}

	@Override
	/**
	 * this method implements the method Add of the Algorithm 1, starting with
	 * line 12
	 */
	public void add(ILattice lattice, Object entity, Set<Object> image) {
		// 12 C[i] <- {H: ||X'(H)|| = i}; {Class pairs in buckets with same
		// cardinality of the X's sets}
		BucketFillerVisitor currentBucketsVisitor = new BucketFillerVisitor();
		currentBucketsVisitor.visitLatticeFromTop(lattice);

		// 13 C'[i] <- 0; {Initialize the C' sets}
		BucketFillerVisitor newBucketsVisitor = new BucketFillerVisitor();

		// 14 {Treat each bucket in ascending cardinality order}
		// 15 FOR i:0 TO maximum cardinality DO
		SortedSet<Integer> currentSortedCardinalities = currentBucketsVisitor.getBucketSizesSorted();
		for (Integer bucketSize : currentSortedCardinalities) {
			// 16 FOR each pair H in C[i]
			Set<ILatticeNode> bucketOfCurrentSize = currentBucketsVisitor.getBucketForSize(bucketSize);
			for (ILatticeNode node : bucketOfCurrentSize) {
				// this will be used in step 22 and later
				Set<Object> intersection = null;

				// 17 IF X'(H) <= f({x*}} THEN {modified pair}
				// this is saying, if the image of entity contains the intent of
				// the current node ...
				if (image.containsAll(node.getIntent())) {
					// then we can add entity to the extent of node, and leave
					// the intent as is
					// 18 Add x* to X(H)
					node.addToExtent(entity);
					// 19 Add H to C'[i]
					// this is, in effect, saying that the modified node will
					// remain with
					// the same intent cardinality. Thus, we will have the
					// <code>newBucketsVisitor</code>
					// process this node, which in our case visits nothing: it
					// just builds the new buckets incrementally
					newBucketsVisitor.processNode(node);

					// 20 IF X'(H) = f({x*}) THEN exist algorithm
					// if, in fact, the image of entity equals the intent of
					// this node, then we are done
					// because

					// we can test for equality just by testing for cardinality
					// since we already know that image
					// CONTAINS the intent of node
					if (image.size() == node.getIntent().size())
						return;

				} else {
					// 21 ELSE { old pair}
					// 22 int <- X'(H) INTER f({x*})
					// initialize intersection to image
					intersection = new HashSet<Object>(image);
					intersection.retainAll(node.getIntent());

					// 23 IF NOT EXIST H1 e C'[||int||] such that X'(H1) = Int
					// THEN {H is a generator}
					int intersectionSize = intersection.size();
					Set<ILatticeNode> newBucket = newBucketsVisitor.getBucketForSize(intersectionSize);
					boolean nodeIsGenerator = true;
					if (newBucket != null) {
						for (ILatticeNode newNode : newBucket) {
							if (newNode.getIntent().equals(intersection))
								nodeIsGenerator = false;
						}
					}
					//
					if (nodeIsGenerator) {
						// 24 Create new pair Hn = (X(H) UNION {x*}, int) and
						// add to C'[||int||]
						ILatticeNode newNode = new LatticeNode();
						// first take care of extension
						newNode.addCollectionToExtent(node.getExtent());
						newNode.addToExtent(entity);

						// then intent
						newNode.addCollectionToIntent(intersection);

						// then add to C'[||int||]
						newBucketsVisitor.processNode(newNode);

						// 25 Add edge Hn -> H
						// link newNode to node
						newNode.addChild(node);
						node.addParent(newNode);

						// 26 {Modify edges}

						// 27 FOR j:0 TO ||int|| -1
						SortedSet<Integer> newSortedCardinalities = newBucketsVisitor.getBucketSizesSorted();
						SortedSet<Integer> newSortedCardinalitiesLessThanIntersectionSize = newSortedCardinalities
								.headSet(new Integer(intersectionSize));
						for (Integer newBucketSize : newSortedCardinalitiesLessThanIntersectionSize) {
							Set<ILatticeNode> newBucketOfCurrentSize = newBucketsVisitor.getBucketForSize(newBucketSize);
							// 28 FOR each Ha e C'[j]
							for (ILatticeNode potentialParent : newBucketOfCurrentSize) {
								// 29 IF X'(Ha) <= int {Ha is a potential parent
								// of Hn}
								if (newNode.getIntent().containsAll(potentialParent.getIntent())) {
									// 30 parent <- true
									boolean isParent = true;
									// 31 FOR each Hd child of Ha
									for (ILatticeNode childOfPotentialParent : potentialParent.getChildren()) {
										// 32 IF X'(Hd) <= Int parent <- false;
										// exit FOR END IF
										// ... except that we are not exiting
										// FOR in this case
										if (newNode.getIntent().containsAll(childOfPotentialParent.getIntent())) {
											// it is not a parent since one of
											// its children qualifies as a
											// parent
											isParent = false;
										}
									}
									// 33 END FOR

									// 34 IF parent
									if (isParent) {
										// 35 IF Ha is a parent of H
										if (node.hasParent(potentialParent)) {
											// 36 eliminate edge Ha -> H END IF
											node.removeParent(potentialParent);
											potentialParent.removeChild(node);
										}

										// 37 Add edge Ha -> Hn
										newNode.addParent(potentialParent);
										potentialParent.addChild(newNode);
									}

									// 38 END IF {IF parent}
								}
								// 39 END IF {IF X'(Ha) <= int}, i.e. potential
								// parent
							}
							// 40 END FOR {FOR each Ha e C'[j]}
						}
						// 41 END FOR {FOR j:0 TO ||int|| -1 }

						// 42 IF Int=f*({x*}) THEN exit algorithm END IF
						if (intersection.equals(image))
							return;
					}
					// 43 END IF {IF X'(H) <= f({x*})
				}
			}
		}
	}

}
