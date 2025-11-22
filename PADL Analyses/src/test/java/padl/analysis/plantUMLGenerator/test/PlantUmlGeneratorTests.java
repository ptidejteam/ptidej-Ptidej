package padl.analysis.plantUMLGenerator.test;

import padl.analysis.plantUMLGenerator.PlantUMLGenerator;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.classfile.test.ClassFilePrimitive;
import padl.creator.classfile.test.creator.InheritanceImplementationCounter;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.BreadthFirstTraverser;
import padl.kernel.impl.DepthFirstTraverser;
import padl.kernel.impl.PruningConditions;
import padl.util.ModelStatistics;
import padl.visitor.IPruningConditions;
import padl.visitor.ITraverser;
import padl.visitor.IWalker;
import util.io.NullWriter;
import util.io.ProxyConsole;

import java.io.*;

public class PlantUmlGeneratorTests {

	public static void main(final String[] args) {

		ProxyConsole.getInstance().setDebugOutput(new PrintWriter(new NullWriter()));
		ProxyConsole.getInstance().setNormalOutput(new NullWriter());
		ProxyConsole.getInstance().setErrorOutput(new NullWriter());

		final IWalker walker = new InheritanceImplementationCounter();

		final String path = "SOEN6461\\target\\classes\\soen6461\\";
		//final String path = "PADL\\target\\classes";

		try {
			final ModelStatistics modelStatistics = new ModelStatistics();

			final ICodeLevelModel codeLevelModel = ClassFilePrimitive.getFactory().createCodeLevelModel("");

			codeLevelModel.addModelListener(modelStatistics);

			CompleteClassFileCreator completeClassFileCreator = new CompleteClassFileCreator(new String[] { path }, true);

			codeLevelModel.create(completeClassFileCreator);

			System.out.println("---------- DepthFirstTraverser ----------");

			{
				ITraverser iTraverser = new DepthFirstTraverser();

				IPruningConditions iPruningConditions = new PruningConditions("TestClass2");

				PlantUMLGenerator plantUMLGenerator = new PlantUMLGenerator(iTraverser, iPruningConditions);

				codeLevelModel.generate(plantUMLGenerator);

				String umlContent = plantUMLGenerator.getCode();

				System.out.println("\n---------- DepthFirstTraverser umlContent ----------");
				System.out.println(umlContent);
			}

			System.out.println("\n\n");
			System.out.println("---------- BreadthFirstTraverser ----------");

			{
				ITraverser iTraverser = new BreadthFirstTraverser();

				IPruningConditions iPruningConditions = new PruningConditions("TestClass2");

				PlantUMLGenerator plantUMLGenerator = new PlantUMLGenerator(iTraverser, iPruningConditions);

				codeLevelModel.generate(plantUMLGenerator);

				String umlContent = plantUMLGenerator.getCode();

				System.out.println("\n---------- umlContent ----------");
				System.out.println(umlContent);
			}

		} catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}

}
