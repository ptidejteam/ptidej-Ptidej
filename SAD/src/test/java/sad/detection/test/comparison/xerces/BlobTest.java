/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package sad.detection.test.comparison.xerces;

import java.io.PrintWriter;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.generator.helper.ModelGenerator;
import padl.kernel.IIdiomLevelModel;
import sad.designsmell.detection.IDesignSmellDetection;
import sad.designsmell.detection.repository.Blob.BlobDetection;
import util.io.ProxyDisk;

/**
 * @author Yann-Gaël Guéhéneuc
 * @author Naouel Moha
 * @author Aminata Sabané
 * @since  2005/12/04
 */
public final class BlobTest extends TestCase {
	private static IIdiomLevelModel IdiomLevelModelFromJavaFiles;
	private static IIdiomLevelModel IdiomLevelModelFromClassFiles;

	public BlobTest(final String name) {
		super(name);
	}

	//	public void testCompareModels() {
	//		BlobTest.IdiomLevelModelFromClassFiles.walk(new ModelComparator(
	//			BlobTest.IdiomLevelModelFromJavaFiles));
	//	}
	private void blobDetection(final IIdiomLevelModel anIdiomLevelModel,
			final int theExpectedNumberOfBlobs) {

		final IDesignSmellDetection ad = new BlobDetection();
		// TODO Necessary?
		//	ad.setMetricsFileRepository(ClassFileRepository
		//		.getInstance(MetricRepository.class));
		ad.detect(anIdiomLevelModel);
		((BlobDetection) ad).output(new PrintWriter(ProxyDisk.getInstance()
				.fileTempOutput("Xerces v1.0.1_Blob.ini")));
		Assert.assertEquals("Number of Blobs", theExpectedNumberOfBlobs,
				ad.getDesignSmells().size());
	}

	protected void setUp() throws Exception {
		if (BlobTest.IdiomLevelModelFromClassFiles == null
				|| BlobTest.IdiomLevelModelFromJavaFiles == null) {

			BlobTest.IdiomLevelModelFromClassFiles = ModelGenerator
					.generateModelFromClassFilesDirectories(new String[] {
							"../SAD/target/test-classes/Xercesv1.0.1.jar" });
			BlobTest.IdiomLevelModelFromJavaFiles = ModelGenerator
					.generateModelFromJavaFilesDirectoriesUsingEclipse(
							"../SAD/target/test-classes/Xercesv1.0.1/src/");
		}
	}

	public void testBlobDetectionInClassFiles() {
		this.blobDetection(BlobTest.IdiomLevelModelFromClassFiles, 9);
	}

	public void testBlobDetectionInJavaFiles() {
		this.blobDetection(BlobTest.IdiomLevelModelFromJavaFiles, 6);
	}
}
