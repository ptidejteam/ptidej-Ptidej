package net.ptidej.comparison.moose;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import padl.generator.helper.ModelGenerator;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.util.ModelStatistics;
import pom.metrics.IUnaryMetric;
import pom.metrics.MetricsRepository;

// See http://www.lifl.fr/~etien/Lucene/testMaintenance.html

public class Main {
	public static void main(String[] args) {
		final ModelStatistics statistics = new ModelStatistics();

		//	Parsing
		//	Replacing "''" with """ - 1833 found
		//	Error: Syntax error (2)
		//	Info: #2
		//	Line: 6 Char: 115 Text: ":"
		//	Couldn't repair and continue parse
		//	java.lang.Exception: Can't recover from previous error(s)
		//		at padl.creator.msefile.javacup.runtime.lr_parser.report_fatal_error(lr_parser.java:1079)
		//		at padl.creator.msefile.javacup.runtime.lr_parser.unrecovered_syntax_error(lr_parser.java:1249)
		//		at padl.creator.msefile.javacup.runtime.lr_parser.parse(lr_parser.java:851)
		//		at padl.creator.msefile.MSECreator.parseMSEFile(MSECreator.java:78)
		//		at padl.creator.msefile.MSECreator.create(MSECreator.java:46)
		//		at padl.kernel.impl.CodeLevelModel.create(CodeLevelModel.java:41)
		//		at padl.generator.helper.ModelGenerator.generateModelFromMSEFiles(ModelGenerator.java:653)
		//		at padl.generator.helper.ModelGenerator.generateModelFromMSEFiles(ModelGenerator.java:642)
		//		at net.ptidej.comparison.moose.Main.main(Main.java:9)

		//	final IIdiomLevelModel luceneModel = ModelGenerator
		//		.generateModelFromMSEFiles(
		//			"Lucene",
		//			new String[] {
		//					"D:\\Documents\\Papers\\Journals\\2019\\MOOSE vs. Ptidej\\lucene.mse" });

		final IIdiomLevelModel luceneModel = ModelGenerator
			.generateModelFromJavaFilesDirectoryUsingEclipse(
				"Lucene",
				"D:/Documents/Papers/Journals/2019/MOOSE vs. Ptidej/lucene/src/java/org/apache/lucene/analysis/",
				statistics);
		System.out.println(statistics);

		// p.10
		final AllThingsCollector collector1 = new AllThingsCollector();
		luceneModel.walk(collector1);
		System.out.println(collector1.getResult());

		// p.41
		final List largeFirstClassEntities = new ArrayList();
		final IUnaryMetric nomMetric =
			(IUnaryMetric) MetricsRepository.getInstance().getMetric("NOM");
		final Iterator iterator = luceneModel.getIteratorOnTopLevelEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity entity =
				(IFirstClassEntity) iterator.next();
			final double nom = nomMetric.compute(luceneModel, entity);
			System.out.print(entity);
			System.out.print(" : ");
			System.out.println(nom);
			if (nom >= 10) {
				largeFirstClassEntities.add(entity);
			}
		}
		System.out.println(largeFirstClassEntities);

		// p.52
		final AllMainMethodsCollector collector2 =
			new AllMainMethodsCollector();
		luceneModel.walk(collector2);
		System.out.println(collector2.getResult());
	}
}
