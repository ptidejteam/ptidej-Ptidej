/**
 * 
 */
package ca.uqam.latece.aspects.extractor.lattice.visitors.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaModelException;

import ca.uqam.latece.aspects.extractor.lattice.model.LatticeNode;
import ca.uqam.latece.aspects.extractor.lattice.visitors.Visitor;

/**
 * This visitor traverses the lattice, and for each node, purges the extent of
 * those classes that are not minimal from an inheritance point of view
 * 
 * For example, consider a lattice node with extent {C1, C2, C3} and intent
 * {Method1, Method2, Method3, Method4}. If any of the three classes are
 * hierarchically related, the higher one is removed from the extent.
 * 
 * For example, if C1 extends ... C2 extends ... C3, then we leave only C1 in
 * the extent.
 * 
 * If C1 extends ... C2, but C3 belongs to a different branch, then we remove
 * C2, and we leave C1 and C3
 * 
 * If none of the nodes is a parent of the others, then the purge leaves all
 * three.
 * 
 * A PurgeExtentsVisitor needs a reference to the containing Java project to be
 * able to navigate the type hierarchy
 * 
 * @author Hafedh
 *
 */
public class PurgeExtentsVisitor extends AbstractVisitor implements Visitor {

	/**
	 * the java project from which the lattice was constructed
	 */
	private IJavaProject javaProject;

	public PurgeExtentsVisitor(IJavaProject project) {
		javaProject = project;
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
	public void processNode(LatticeNode node) {

		Set<Object> intersection = null, extent = node.getExtent();

		ArrayList<Object> classesToProcess = new ArrayList<Object>();
		classesToProcess.addAll(extent);

		// first, create a type hierarchy to get the inheritance relationships
		IProgressMonitor pMonitor = new NullProgressMonitor();
		ITypeHierarchy typeHierarchy = null;

		// while there are still classes to process from the extent
		while (!classesToProcess.isEmpty()) {

			// first remove first element from classesToProcess
			IType nextClass = (IType) classesToProcess.remove(0);

			try {
				typeHierarchy = nextClass.newTypeHierarchy(pMonitor);
				IType[] itsAncestors = typeHierarchy.getAllSupertypes(nextClass);

				// compute the intersection between the extent and the list of
				// ancestors
				intersection = new HashSet<Object>();
				intersection.addAll(extent);
				intersection.retainAll(Arrays.asList(itsAncestors));

				// now, remove the intersection from the extent, and from the
				// classes to process
				extent.removeAll(intersection);
				classesToProcess.removeAll(intersection);
			} catch (JavaModelException jme) {
				jme.printStackTrace();
			}

		}
	}

	public IJavaProject getJavaProject() {
		return javaProject;
	}
}
