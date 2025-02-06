package ca.uqam.latece.aspects.extractor.tests.lattice;

import ca.uqam.latece.aspects.extractor.lattice.LatticeBuilder;
import ca.uqam.latece.aspects.extractor.lattice.impl.LatticeBuilderImpl;
import ca.uqam.latece.aspects.extractor.lattice.model.Lattice;
import ca.uqam.latece.aspects.extractor.lattice.model.Relation;
import ca.uqam.latece.aspects.extractor.lattice.visitors.impl.LatticePrettyPrinter;
import ca.uqam.latece.aspects.extractor.tests.relations.TestRelations;

public class TestBuildingLattices {
	
	public static enum Objects {O1, O2, O3, O4, O5, O6};
	
	public static enum Attributes {a,b,c,d,e,f,g,h,i};

	public static void main(String[] args) {

		Relation aRelation = TestRelations.buildSampleRelation();
		
		LatticeBuilder builder = new LatticeBuilderImpl();
		
		Lattice aLattice = builder.buildLattice(aRelation, null);
		
		System.out.println("Done building lattice!");
		
		LatticePrettyPrinter printer = LatticePrettyPrinter.defaultPrettyPrinter();
		aLattice.acceptTopVisitor(printer);
		
		System.out.println("Done printing lattice!");

	}
	
	

}
