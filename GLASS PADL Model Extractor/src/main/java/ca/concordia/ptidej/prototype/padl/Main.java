package ca.concordia.ptidej.prototype.padl;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Set;

import ca.concordia.ptidej.prototype.ast.IMethod;
import ca.concordia.ptidej.prototype.ast.IProject;
import ca.concordia.ptidej.prototype.ast.IType;
import ca.concordia.ptidej.prototype.lattice.builder.ILatticeBuilder;
import ca.concordia.ptidej.prototype.lattice.builder.LatticeBuilder;
import ca.concordia.ptidej.prototype.lattice.model.ILattice;
import ca.concordia.ptidej.prototype.lattice.model.IRelation;
import ca.concordia.ptidej.prototype.lattice.model.IRelationBuilder;
import ca.concordia.ptidej.prototype.lattice.model.impl.ReverseInheritanceRelationBuilder;
import ca.concordia.ptidej.prototype.lattice.visitor.impl.ComplexPurgeExtentsVisitor;
import ca.concordia.ptidej.prototype.lattice.visitor.impl.FeatureDetectorVisitor;
import ca.concordia.ptidej.prototype.lattice.visitor.impl.LatticePrettyPrinter;
import ca.concordia.ptidej.prototype.lattice.visitor.impl.PrintCandidatesVisitor;
import ca.concordia.ptidej.prototype.padl.ast.PADLProject;
import ca.concordia.ptidej.prototype.lattice.visitor.IVisitor;

public class Main 
{
    public static void main( String[] args )
    {

    	try {
			System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("output.txt"))));
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
    	
        String filePath_test = "../prototype.padl/src/test/ressources/testProject/src";
        String filePath = "C:/Users/notuk/git/featurediscovery/qualitativeEvaluation/data/input projects/javawebmail-0.7/src";
        String projectName = "javawebmail";
        IProject project = new PADLProject(filePath);
        
        /*
        Collection<IType> definedTypes = project.getDefinedTypes();
        for(IType type: definedTypes) {
        	System.out.println(type.getFullyQualifiedName());
        }
        System.out.println(definedTypes.size());
         */
        
		IRelationBuilder relationBuilder = new ReverseInheritanceRelationBuilder();
		IRelation relation = relationBuilder.buildRelationFrom(project);
		//System.out.println("Done building relation!");
		
		//System.out.println("Printing the relation");
		//System.out.println(relation.printString());
		
		ILatticeBuilder latticeBuilder = new LatticeBuilder();
		ILattice lattice = latticeBuilder.buildLattice(relation);
		System.out.println("Done building lattice!");
		
		LatticePrettyPrinter printer = LatticePrettyPrinter.javaElementsLatticePrettyPrinter();
		lattice.acceptTopVisitor(printer);

		System.out.println("Done printing lattice!");

		//System.out.println("Using complex purge");
		IVisitor purgeVisitor = new ComplexPurgeExtentsVisitor((ReverseInheritanceRelationBuilder) relationBuilder);
		lattice.acceptTopVisitor(purgeVisitor);
		printer.reset();
		//System.out.println("Printing lattice after purging extents");
		//lattice.acceptTopVisitor(printer);
		//System.out.println("Done printing lattice!");
		
		
		// 6.2 Second, extract candidate features
		FeatureDetectorVisitor featureDetector = new FeatureDetectorVisitor((ReverseInheritanceRelationBuilder) relationBuilder);
		lattice.acceptTopVisitor(featureDetector);


		// 6.3 Third, print candidate feature nodes
		PrintCandidatesVisitor printCandidatesVisitor = new PrintCandidatesVisitor(
				featureDetector.getCandidateFeatureNodes());
		System.out.println("Printing candidate nodes");
		lattice.acceptTopVisitor(printCandidatesVisitor);
		System.out.println("Done printing candidate nodes!");

		FileOutputStream fos;
		ObjectOutputStream oos;
		try {
			fos = new FileOutputStream(filePath.toString()+"/"+projectName+"_"+"lattice.ltc");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(printCandidatesVisitor.getNodes());
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
    }
}
