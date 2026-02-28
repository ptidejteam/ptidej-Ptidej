package net.ptidej;

import padl.analysis.plantUMLGenerator.PlantUMLGenerator;
import padl.analysis.selective.ServiceClassFilter;
import padl.visitor.BFVisitor;
import padl.visitor.FilteringVisitor;
import padl.kernel.ICodeLevelModel;
import padl.kernel.impl.Factory;
import padl.creator.classfile.CompleteClassFileCreator;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
        final String path = "DeMIMA/target/test-classes/ptidej/example/composite1/";

        try {
            ICodeLevelModel codeLevelModel = Factory.getInstance().createCodeLevelModel(path);
            PlantUMLGenerator generator = new PlantUMLGenerator();
            codeLevelModel.create(new CompleteClassFileCreator(new String[]{path}));

            FilteringVisitor selective = new FilteringVisitor(generator, new ServiceClassFilter());
            BFVisitor bfs = new BFVisitor(generator);

            try {
                codeLevelModel.generate(selective);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error", e);
            }
            try {
                codeLevelModel.generate(bfs);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error", e);
            }

            System.out.println("Selective Visitor Decorator");
            System.out.println(selective.getCode());
            System.out.println("==========================");
            System.out.println("BFS Visitor Decorator");
            System.out.println(bfs.getCode());

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error", e);
        }
    }
}
