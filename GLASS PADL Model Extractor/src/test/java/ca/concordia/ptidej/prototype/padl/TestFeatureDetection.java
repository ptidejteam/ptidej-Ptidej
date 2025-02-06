package ca.concordia.ptidej.prototype.padl;

import java.util.Collection;

import ca.concordia.ptidej.prototype.ast.IMethod;
import ca.concordia.ptidej.prototype.ast.IProject;
import ca.concordia.ptidej.prototype.ast.IType;
import ca.concordia.ptidej.prototype.lattice.builder.ILatticeBuilder;
import ca.concordia.ptidej.prototype.lattice.builder.LatticeBuilder;
import ca.concordia.ptidej.prototype.lattice.model.ILattice;
import ca.concordia.ptidej.prototype.lattice.model.IRelation;
import ca.concordia.ptidej.prototype.lattice.model.IRelationBuilder;
import ca.concordia.ptidej.prototype.lattice.model.impl.ReverseInheritanceRelationBuilder;
import ca.concordia.ptidej.prototype.lattice.visitor.impl.FeatureDetectorVisitor;
import ca.concordia.ptidej.prototype.lattice.visitor.impl.LatticePrettyPrinter;
import ca.concordia.ptidej.prototype.lattice.visitor.impl.PrintCandidatesVisitor;
import ca.concordia.ptidej.prototype.padl.ast.PADLProject;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class TestFeatureDetection 
    extends TestCase
{

    public TestFeatureDetection( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TestFeatureDetection.class );
    }

    public void testApp()
    {
        String filePath = "C:\\Users\\notuk\\eclipse-workspace\\prototype.all\\prototype.padl\\src\\test\\ressources";
        IProject project = new PADLProject(filePath);
        Collection<IType> types = project.getDefinedTypes();
        for (IType type : types) {
        	//System.out.println(type.getFullyQualifiedName());
        	IMethod[] methods = type.getMethods();
        	for (IMethod method : methods) {
        		//System.out.println(method.getSignature());
        	}
        }
        
		IRelationBuilder relationBuilder = new ReverseInheritanceRelationBuilder();
		IRelation relation = relationBuilder.buildRelationFrom(project);
		System.out.println("Done building relation!");
		
		ILatticeBuilder latticeBuilder = new LatticeBuilder();
		ILattice lattice = latticeBuilder.buildLattice(relation);
		System.out.println("Done building lattice!");
		
		LatticePrettyPrinter printer = LatticePrettyPrinter.javaElementsLatticePrettyPrinter();
		lattice.acceptTopVisitor(printer);

		System.out.println("Done printing lattice!");
		/*
		System.out.println("Using complex purge");
		IVisitor purgeVisitor = new ComplexPurgeExtentsVisitor((ReverseInheritanceRelationBuilder) relationBuilder);
		lattice.acceptTopVisitor(purgeVisitor);
		//latticePrinter.reset();
		System.out.println("Printing lattice after purging extents");
		lattice.acceptTopVisitor(printer);
		System.out.println("Done printing lattice!");
		
		*/
		
		// 6.2 Second, extract candidate features
		FeatureDetectorVisitor featureDetector = new FeatureDetectorVisitor((ReverseInheritanceRelationBuilder) relationBuilder);
		lattice.acceptTopVisitor(featureDetector);


		// 6.3 Third, print candidate feature nodes
		PrintCandidatesVisitor printCandidatesVisitor = new PrintCandidatesVisitor(
				featureDetector.getCandidateFeatureNodes());
		System.out.println("Printing candidate nodes");
		lattice.acceptTopVisitor(printCandidatesVisitor);
		System.out.println("Done printing candidate nodes!");
    }
}
