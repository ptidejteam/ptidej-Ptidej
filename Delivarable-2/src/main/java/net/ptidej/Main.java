package net.ptidej;

import padl.analysis.plantUMLGenerator.PlantUMLGenerator;
import padl.analysis.selective.ServiceClassFilter;
import padl.visitor.FilteringVisitor;
import padl.kernel.ICodeLevelModel;
import padl.kernel.impl.Factory;
import padl.creator.classfile.CompleteClassFileCreator;

public class Main {
    public static void main(String[] args) throws Exception {
        final String path = "DeMIMA/target/test-classes/ptidej/example/composite1/";

        ICodeLevelModel codeLevelModel = Factory.getInstance().createCodeLevelModel(path);

        PlantUMLGenerator generator = new PlantUMLGenerator();
        codeLevelModel.create(new CompleteClassFileCreator(new String[]{path}));
        FilteringVisitor selective = new FilteringVisitor(generator, new ServiceClassFilter());
        codeLevelModel.generate(selective);

        System.out.println(selective.getCode());
    }
}
