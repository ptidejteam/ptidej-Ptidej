package ca.uqam.latece.aspects.extractor.lattice;

import java.util.Set;

import ca.uqam.latece.aspects.extractor.input.RelationBuilder;
import ca.uqam.latece.aspects.extractor.lattice.model.Lattice;
import ca.uqam.latece.aspects.extractor.lattice.model.Relation;

public interface LatticeBuilder {
	
	/**
	 * builds a lattice from a relation. We pass the relationbuilder along because it may carry
	 * some information that is useful during the lattice generation
	 * @param aRelation
	 * @param builder
	 * @return
	 */
	public Lattice buildLattice(Relation aRelation, RelationBuilder builder);
	
	/**
	 * this method initialises the top and bottom of the lattice using aRelation and any relevant/useful information
	 * found in aBuilder
	 * @param lattice
	 * @param aRelation
	 * @param aBuilder
	 */
	public void initializeTopBottom(Lattice lattice, Relation aRelation, RelationBuilder aBuilder);
	
	
	/**
	 * this method implements Godin et al.'s incremental algorithm
	 * @param entity
	 * @param image
	 */
	public void add(Lattice lattice, Object entity, Set<Object> image);
	
}
