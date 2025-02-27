package glass.lattice.builder;

import java.util.Set;

import glass.lattice.model.ILattice;
import glass.lattice.model.IRelation;

public interface ILatticeBuilder {
	
	/**
	 * builds a lattice from a relation.
	 * @param aRelation
	 * @param builder
	 * @return
	 */
	public ILattice buildLattice(IRelation aRelation);
	
	/**
	 * this method initialises the top and bottom of the lattice using aRelation
	 * @param lattice
	 * @param aRelation
	 * @param aBuilder
	 */
	public void initializeTopBottom(ILattice lattice, IRelation aRelation);
	
	
	/**
	 * this method implements Godin et al.'s incremental algorithm
	 * @param entity
	 * @param image
	 */
	public void add(ILattice lattice, Object entity, Set<Object> image);
	
}
