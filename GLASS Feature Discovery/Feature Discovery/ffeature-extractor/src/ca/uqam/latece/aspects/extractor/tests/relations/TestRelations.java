package ca.uqam.latece.aspects.extractor.tests.relations;

import java.util.Arrays;

import ca.uqam.latece.aspects.extractor.lattice.model.Relation;
import ca.uqam.latece.aspects.extractor.lattice.model.impl.RelationImpl;
import ca.uqam.latece.aspects.extractor.tests.lattice.TestBuildingLattices.Attributes;
import ca.uqam.latece.aspects.extractor.tests.lattice.TestBuildingLattices.Objects;

public class TestRelations {

	public static void main(String[] args) {
		
		Relation relation = buildSampleRelation();
		
		// print domain
		System.out.println("Domain of relation:" + relation.getDomain());
		
		// print images of various objects
		for (Object anObject: relation.getDomain()){
			System.out.println("Image of " + anObject + " is" + relation.getImage(anObject));
		}
		// TODO Auto-generated method stub
		Arrays.asList(null);

	}
	
	/**
	 * build the relation described in Godin et al., (1995), published in Computational Intelligence
	 * The relation is as follows
	 * 
	 * R	|	a	b	c	d	e	f	g	h	i
	 * --------------------------------------------
	 *	1	|	1	0	1	0	0	1	0	1	0
	 *	2	|	1	0	1	0	0	0	1	0	1
	 *	3	|	1	0	0	1	0	0	1	0	1
	 *	4	|	0	1	1	0	0	1	0	1	0
	 *	5	|	0	1	0	0	1	0	1	0	0
	 *	6	|	0	1	1	0	0	1	0	0	1
	 * @return
	 */
	public static Relation buildSampleRelation() {
		Relation relation = new RelationImpl(null);
		
		// add the row 
		// 1	|	1	0	1	0	0	1	0	1	0
		relation.addRelation(Objects.O1, Attributes.a );
		relation.addRelation(Objects.O1, Attributes.c );
		relation.addRelation(Objects.O1, Attributes.f );
		relation.addRelation(Objects.O1, Attributes.h );
		
		// add the row
		// 	2	|	1	0	1	0	0	0	1	0	1
		relation.addRelation(Objects.O2, Attributes.a );
		relation.addRelation(Objects.O2, Attributes.c );
		relation.addRelation(Objects.O2, Attributes.g );
		relation.addRelation(Objects.O2, Attributes.i );
		
		// add the row
		// 3	|	1	0	0	1	0	0	1	0	1
		relation.addRelation(Objects.O3, Attributes.a );
		relation.addRelation(Objects.O3, Attributes.d );
		relation.addRelation(Objects.O3, Attributes.g );
		relation.addRelation(Objects.O3, Attributes.i );
		
		// add the row
		// 	4	|	0	1	1	0	0	1	0	1	0
		relation.addRelation(Objects.O4, Attributes.b );
		relation.addRelation(Objects.O4, Attributes.c );
		relation.addRelation(Objects.O4, Attributes.f );
		relation.addRelation(Objects.O4, Attributes.h );
		
		// add the row
		// 
		relation.addRelation(Objects.O5, Attributes.b );
		relation.addRelation(Objects.O5, Attributes.e );
		relation.addRelation(Objects.O5, Attributes.g );
		
		// add the row
		// 
		relation.addRelation(Objects.O6, Attributes.b );
		relation.addRelation(Objects.O6, Attributes.c );
		relation.addRelation(Objects.O6, Attributes.f );
		relation.addRelation(Objects.O6, Attributes.i );

		
		return relation;
	}

}
