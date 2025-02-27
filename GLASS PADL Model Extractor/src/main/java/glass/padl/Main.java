package glass.padl;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Set;

import glass.ast.IMethod;
import glass.ast.IProject;
import glass.ast.IType;
import glass.lattice.builder.ILatticeBuilder;
import glass.lattice.builder.LatticeBuilder;
import glass.lattice.model.ILattice;
import glass.lattice.model.IRelation;
import glass.lattice.model.IRelationBuilder;
import glass.lattice.model.impl.ReverseInheritanceRelationBuilder;
import glass.lattice.model.impl.UsualRelationBuilder;
import glass.lattice.visitor.impl.ComplexPurgeExtentsVisitor;
import glass.lattice.visitor.impl.FeatureDetectorVisitor;
import glass.lattice.visitor.impl.LatticePrettyPrinter;
import glass.lattice.visitor.impl.PrintCandidatesVisitor;
import glass.padl.ast.PADLProject;
import glass.lattice.visitor.IVisitor;

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
        String filePath = "C:\\Users\\notuk\\git\\ptidej-Ptidej\\GLASS Feature Discovery\\featurediscovery\\qualitativeEvaluation\\data\\input projects\\javawebmail-0.7\\src";
        String projectName = "javawebmail";
        IProject project = new PADLProject(filePath);
        
        /*
        Collection<IType> definedTypes = project.getDefinedTypes();
        for(IType type: definedTypes) {
        	System.out.println(type.getFullyQualifiedName());
        }
        System.out.println(definedTypes.size());
         */
        /*
        IRelationBuilder relationBuilder = new UsualRelationBuilder();
        IRelation relation = relationBuilder.buildRelationFrom(project);
        
		System.out.println("Printing the relation");
		System.out.println(relation.printString());
        
        ILatticeBuilder latticeBuilder = new LatticeBuilder();
        ILattice lattice = latticeBuilder.buildLattice(relation);
        
		LatticePrettyPrinter printer = LatticePrettyPrinter.javaElementsLatticePrettyPrinter();
		lattice.acceptTopVisitor(printer);

		System.out.println("Done printing lattice!");
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
