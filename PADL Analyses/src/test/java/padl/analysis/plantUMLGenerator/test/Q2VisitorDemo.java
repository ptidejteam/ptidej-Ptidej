
package padl.analysis.plantUMLGenerator.test;


import padl.analysis.plantUMLGenerator.PlantUMLGenerator;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFactory;
import padl.kernel.impl.Factory;
import padl.kernel.IAssociation;
import padl.kernel.Cardinality;

public class Q2VisitorDemo {

    public static void main(String[] args) {

        // Step 1: Create a PADL model
        IFactory factory = Factory.getInstance();

        ICodeLevelModel model =
            factory.createCodeLevelModel("Q2Demo");

        // Step 2: Create a class
        IClass student = factory.createClass(
            "Student".toCharArray(),
            "Student".toCharArray());

        model.addConstituent(student);
        
     // Create another class
        IClass course = factory.createClass(
            "Course".toCharArray(),
            "Course".toCharArray());

        model.addConstituent(course);

        // Create an association: Student -> Course
        IAssociation association =
            factory.createAssociationRelationship(
                "enrolls".toCharArray(),
                course,
                Cardinality.One);

        student.addConstituent(association);

        // Step 3: Create a Visitor
        PlantUMLGenerator visitor =
            new PlantUMLGenerator();

        // Step 4: Apply the Visitor
        String result = model.generate(visitor);

        // Step 5: Print the output
        System.out.println(result);
    }
}
