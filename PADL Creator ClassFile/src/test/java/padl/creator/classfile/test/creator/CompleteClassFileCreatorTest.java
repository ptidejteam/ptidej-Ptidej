package padl.creator.classfile.test.creator;

import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.util.ModelStatistics;
import padl.visitor.IWalker;

public class CompleteClassFileCreatorTest {
    public static void main(String[] args) {
        String[] classFiles = {"PADL/target/classes/padl/kernel/impl/AbstractClass.class"};
        final IWalker walker = new InheritanceImplementationCounter();
        try {
            final ModelStatistics modelStatistics = new ModelStatistics();
            final ICodeLevelModel codeLevelModel = Factory.getInstance().createCodeLevelModel("myModel");
            codeLevelModel.addModelListener(modelStatistics);
            CompleteClassFileCreator completeClassFileCreator = new CompleteClassFileCreator(classFiles, false);
            codeLevelModel.create(completeClassFileCreator);
            System.out.println("Model created");
            walker.reset();
            codeLevelModel.walk(walker);
            System.out.println("Model walked");
            System.out.println("Model Stats :");
            System.out.println(modelStatistics);
            System.out.println("Walker Results: ");
            System.out.println(walker.getResult());
        } catch (CreationException e) {
            throw new RuntimeException(e);
        }
    }
}
