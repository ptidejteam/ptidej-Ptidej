package glass.lattice.visitor.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import glass.ast.IMethod;
import glass.ast.IType;
import glass.lattice.model.ILatticeNode;
import glass.lattice.model.impl.ReverseInheritanceRelationBuilder;
import glass.lattice.visitor.AbstractVisitor;
import glass.lattice.visitor.IVisitor;

public class ComplexPurgeExtentsVisitor extends AbstractVisitor implements IVisitor{
	/**
	 * we need a reference to the builder that built the relation to have access to the local
	 * interfaces and the cumulative interfaces of the various nodes
	 */
	private ReverseInheritanceRelationBuilder relationBuilder;
	
	
	public ComplexPurgeExtentsVisitor (ReverseInheritanceRelationBuilder builder){
		relationBuilder = builder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.uqam.latece.aspects.extractor.lattice.visitors.impl.AbstractVisitor#
	 * processNode(ca.uqam.latece.aspects.extractor.lattice.model.LatticeNode)
	 */
	@Override
	/**
	 * This method purges extents from classes that are ancestors of other
	 * classes in the extent.
	 * 
	 * The way we do this: we iterate over the contents. For each class: 1) we
	 * get its ancestors 2) we compute the intersection between the ancestors
	 * and the extent 3) we remove the classes in the intersection from the
	 * extent-- and from further consideration by the method
	 * 
	 * Note that going from class to ancestors is more efficient than going from
	 * class to subclasses, for two reasons: 1) for most classes, there are more
	 * descendants than ancestors to look at 2) if the descendants of a class
	 * intersect with the extent, the only thing we know is that we must remove
	 * the class, whereas with the ancestors, I can remove all the ancestors in
	 * a single swoop
	 * 
	 * If the extent consists of a chain of n elements, by going from class to
	 * ancestors, on the average, I can hope to perform the test n/2 times. With
	 * the descendants, the worst case is the best case is n
	 */
	public void processNode(ILatticeNode node) {
		
		// first, if this is the top node, exit
		if (node.getIntent().isEmpty()) return;

		Set<Object> intersection = null, extent = node.getExtent(), intent = node.getIntent();

		ArrayList<Object> classesToProcess = new ArrayList<Object>();
		classesToProcess.addAll(extent);

		// while there are still classes to process from the extent
		while (!classesToProcess.isEmpty()) {

			// first remove first element from classesToProcess
			IType nextClass = (IType) classesToProcess.remove(0);

			IType[] itsAncestors = nextClass.getAllSupertypes();

			// compute the intersection between the extent and the list of
			// ancestors
			intersection = new HashSet<Object>();
			intersection.addAll(extent);
			intersection.retainAll(Arrays.asList(itsAncestors));

			// now, go over the elements of the intersection.
			// if an element's local interface contains the intent, we should not remove
			// it because it has the intent, NOT by virtue of cumulating the interfaces
			// of its children, but has them independently, and should be counted as an
			// independent occurrence.
			for (Object element: intersection){
				IType type = (IType)element;
				IMethod [] localDomainInterface = relationBuilder.getLocalDomainInterfaces().get(type);
				Set<Object> localDomainInterfaceAsSet = new HashSet<Object>();
				localDomainInterfaceAsSet.addAll(Arrays.asList(localDomainInterface));
				if (!localDomainInterfaceAsSet.containsAll(node.getIntent())){
					// indeed, this is the case where we need to remove the element from the extent
					extent.remove(element);
				} else {
					// just print a node
					//System.out.println("Class " + type.getElementName() + " was not purged from a node extent even though it is not a minimum. Is it an interface? "+ type.isInterface());
				}
				
				// either way, remove it from classes to process
				// classes to process
				classesToProcess.remove(element);
			}


		}
	}
}
