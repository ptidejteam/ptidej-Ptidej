package sad.detection.helpers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import padl.generator.helper.ModelGenerator;
import padl.kernel.IIdiomLevelModel;
import sad.designsmell.detection.IDesignSmellDetection;
import sad.designsmell.detection.repository.Blob.BlobDetection;

public class SmellDetectionExample {

	public static void main(final String[] args) {
		final IIdiomLevelModel model = ModelGenerator.generateModelFromJAR(
				"../SAD/src/test/resources/Xercesv1.0.1.jar");
		final StringWriter outputString = new StringWriter();
		final PrintWriter outputWriter = new PrintWriter(outputString);

		final IDesignSmellDetection blobDetector = new BlobDetection();
		blobDetector.detect(model);
		blobDetector.output(outputWriter);

		outputWriter.println();
		outputWriter.println("###### Statistics #####");
		final Date today = new Date();
		final SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy'/'MM'/'dd' Heure ' hh':'mm':'ss");
		final String timeStamp = formatter.format(today);
		outputWriter.println(timeStamp);
		outputWriter.print("Number of Blobs found: ");
		outputWriter.println(blobDetector.getDesignSmells().size());
		outputWriter.close();

		System.out.println(outputString);
	}

}
