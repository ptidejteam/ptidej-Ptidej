package ptidej.mixed;

import java.util.Iterator;
import padl.event.IModelListener;
import padl.generator.helper.ModelGenerator;
import padl.kernel.IConstituent;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.impl.Factory;
import padl.util.ModelStatistics;

public class Main {
	public static void main(String[] args) {
		final IModelListener listener = new ModelStatistics();

		final IIdiomLevelModel modelCpp =
			ModelGenerator.generateModelFromCppFilesUsingEclipse(
				"Model from C++",
				"../Z - Mixed/rsc/C++/",
				listener);

		final IIdiomLevelModel modelJava =
			ModelGenerator.generateModelFromClassFilesDirectories(
				"Model from Java",
				new String[] {
						"../Z - Mixed/rsc/Java/padl/example/composite1/" },
				listener);

		final IIdiomLevelModel modelMixed =
			Factory.getInstance().createIdiomLevelModel(
				"Model from C++ and Java".toCharArray());
		modelCpp.moveIn(modelMixed);
		modelJava.moveIn(modelMixed);

		System.out.println(listener);
		System.out.println(modelMixed.getNumberOfTopLevelEntities());
		final Iterator<?> iterator = modelMixed.getIteratorOnTopLevelEntities();
		while (iterator.hasNext()) {
			final IConstituent constituent = (IConstituent) iterator.next();
			System.out.println(constituent);
		}

		// What now?
	}
}
