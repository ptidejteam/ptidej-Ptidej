package ptidej.detection.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import padl.generator.helper.ModelGenerator;
import padl.kernel.IIdiomLevelModel;
import padl.motif.repository.Composite;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;

public class PatternDetectionExample {

	public static void main(final String[] args) {
		final IIdiomLevelModel model = ModelGenerator.generateModelFromJAR(
				"../DeMIMA Solver Occurrence Generator/src/test/resources/Xercesv1.0.1.jar");

		// TODO Fix exception with OccurrenceGenerator.SOLVER_COMBINATORIAL_AUTOMATIC
		// TODO Fix long computation times with SOLVER_SIMPLE_AUTOMATIC
		final Properties compositeOccurrences = OccurrenceGenerator
				.getInstance().getOccurrences(new Composite(), model,
						OccurrenceGenerator.VERSION_PTIDEJSOLVER4,
						OccurrenceGenerator.SOLVER_AUTOMATIC, 0);
		final Occurrence[] occurrences = OccurrenceBuilder.getInstance()
				.getCanonicalOccurrences(compositeOccurrences);
		for (int i = 0; i < occurrences.length; i++) {
			final Occurrence occurrence = occurrences[i];
			System.out.println(occurrence);
		}

		System.out.println();
		System.out.println("###### Statistics #####");
		final Date today = new Date();
		final SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy'/'MM'/'dd' Heure ' hh':'mm':'ss");
		final String timeStamp = formatter.format(today);
		System.out.println(timeStamp);
		System.out.print("Number of Composites found: ");
		System.out.println(occurrences.length);
	}

}
