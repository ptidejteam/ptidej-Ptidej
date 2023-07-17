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
package squad.util;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import pom.metrics.IUnaryMetric;
import pom.metrics.MetricsRepository;
import util.io.ProxyDisk;

public class MetricsGenerator {

	/**
	 * @param args
	 */
	private static Pattern pattern;
	private static Matcher matcher;

	public List<String> extractJarsFromDir(String path, List<String> jarfiles) {
		final File pathFile = new File(path);
		final String[] subPaths = pathFile.list();
		for (int i = 0; i < subPaths.length; i++) {
			final String fileName = path + "/" + subPaths[i];
			final File file = new File(fileName);
			if (file.isDirectory()) {
				this.extractJarsFromDir(fileName, jarfiles);
			}
			else {

				if (fileName.endsWith(".jar") == true) {
					jarfiles.add(fileName);
					// j++;
					// System.out.println(fileName);
				}
			}

		}
		// System.out.println(jarfiles.size());

		return jarfiles;
	}

	public String[] createArrayOfJars(List<String> l) {
		String[] files = new String[l.size()];
		for (int i = 0; i < l.size(); i++) {
			files[i] = (String) l.get(i);

		}
		return files;
	}

	// /////////
	public static String[] createArrayOfJars(List<String> l, Object s) {
		String[] files = new String[l.size() + 1];
		for (int i = 0; i < l.size(); i++) {
			files[i] = (String) l.get(i);

		}
		files[l.size()] = (String) s;
		return files;
	}

	public static boolean containsOrgEclipse(String className) {
		final String t = "org.eclipse(.[a-z]*)+";
		boolean bool = false;
		StringTokenizer st = new StringTokenizer(className);
		while (st.hasMoreTokens()) {

			pattern = Pattern.compile(t);
			matcher = pattern.matcher(st.nextToken());
			if (matcher.find()) {

				bool = true;
			}
		}
		return bool;

	}

	public void computeMetrics(String[] someJARFiles, List<String> cls, String arg) {

		final ICodeLevelModel codeLevelModel =
			Factory.getInstance().createCodeLevelModel("Model");

		try {
			codeLevelModel.create(new CompleteClassFileCreator(
				someJARFiles,
				true));
		}
		catch (final CreationException e) {
			e.printStackTrace();
		}

		if (codeLevelModel.getNumberOfTopLevelEntities() == 0) {
			System.err.println("--");
			System.err.print("Problem with the model.... ");
			System.err.println("--");
		}
		else {

			try {
				final Writer out =
					ProxyDisk.getInstance().fileTempOutput(
						"FinalResults/MetricsV" + arg + ".csv",
						true);

				final MetricsRepository metricsRepository =
					MetricsRepository.getInstance();
				// final StringBuffer output = new StringBuffer();

				// Header.
				{
					final IUnaryMetric[] metrics =
						metricsRepository.getUnaryMetrics();
					for (int i = 0; i < metrics.length; i++) {
						final IUnaryMetric unaryMetric = metrics[i];
						final String metricName = unaryMetric.getName();
						out.write(metricName);
						out.flush();
						if (i < metrics.length - 1) {
							out.write(',');
							out.flush();
						}
					}
					out.write(",Entities\n");
					out.flush();
				}

				// Metric Values.
				final Iterator<?> entityIterator =
					codeLevelModel.getIteratorOnTopLevelEntities();
				while (entityIterator.hasNext()) {
					final IFirstClassEntity entity =
						(IFirstClassEntity) entityIterator.next();

					if (!(entity instanceof IGhost)
							&& (containsOrgEclipse(entity.getDisplayName()) == true)
							&& (!(cls.contains(entity.getDisplayName())))) {
						System.out.print("Computing metrics for: ");
						System.out.println(entity.getName());
						System.out.print('\t');
						cls.add(entity.getDisplayName());

						final IUnaryMetric[] metrics =
							metricsRepository.getUnaryMetrics();
						for (int i = 0; i < metrics.length; i++) {
							final IUnaryMetric metric = metrics[i];
							final String metricName = metric.getName();
							// System.out.print(metricName);
							// System.out.print(", ");
							try {
								final double value =
									((IUnaryMetric) metricsRepository
										.getMetric(metricName)).compute(
										codeLevelModel,
										entity);
								out.write(Double.toString(value));
								out.flush();
							}
							catch (final Exception e) {
								out.write("N/C");
								out.flush();
								e.printStackTrace();
							}

							if (i < metrics.length - 1) {
								out.write(',');
								out.flush();
							}
						}
						System.out.println();

						out.write(',');
						out.flush();
						out.write(entity.getName());
						out.flush();
						if (entityIterator.hasNext()) {
							out.write('\n');
							out.flush();
						}
					}
				}

				out.close();
			}
			catch (final IOException e1) {
				e1.printStackTrace();
			}
			// try {
			// final int firstSlashIndex =
			// DataGenerator.PATHS[i].indexOf('/') + 1;
			// final int secondSlashIndex =
			// DataGenerator.PATHS[i].indexOf('/', firstSlashIndex);

			// final Writer writer =
			// new FileWriter("rsc/"
			// + DataGenerator.PATHS[i].substring(
			// firstSlashIndex,
			// secondSlashIndex) + ".metrics");
			// final Writer writer =
			// new FileWriter("FinalResults/MetricsValues v"+args[0]+ " .csv");
			// writer.write(output.toString());
			// writer.close();
			// }
			// catch (final IOException ioe) {
			// ioe.printStackTrace();
			// }
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stubfinal ICodeLevelModel codeLevelModel =

		MetricsGenerator gen = new MetricsGenerator();
		String dirName = "rsc/eclipse-SDK-" + args[0] + "-win32/plugins";
		final String t =
			"org.eclipse.equinox(.[a-z]*)+|org.eclipse.core(.[a-z]*)+|org.eclipse.platform(.[a-z]*)+";

		List<String> files = new ArrayList<String>();
		List<String> cls = new ArrayList<String>();
		// cls.add("org.eclipse.jdt.internal.corext.codemanipulation.ImportsStructure");

		List<String> jarfiles = gen.extractJarsFromDir(dirName, files);
		// String [] someJARFiles= gen.createArrayOfJars(jarfiles);

		List<String> equinoxfiles = new ArrayList<String>();
		List<String> elsefiles = new ArrayList<String>();

		for (int i = 0; i < jarfiles.size(); i++) {
			StringTokenizer st = new StringTokenizer((String) jarfiles.get(i));
			while (st.hasMoreTokens()) {

				pattern = Pattern.compile(t);
				matcher = pattern.matcher(st.nextToken());
				if (matcher.find()) {
					equinoxfiles.add((String) jarfiles.get(i));
				}
				else {

					elsefiles.add((String) jarfiles.get(i));

				}

			}
		}
		if (elsefiles.size() > 0) {
			for (int i = 0; i < elsefiles.size(); i++) {

				gen.computeMetrics(
					MetricsGenerator.createArrayOfJars(equinoxfiles, elsefiles.get(i)),
					cls,
					args[0]);
				// // printing metrics values
				// printCodelevelModelMetrics(detection.createArrayOfJars(
				// equinoxfiles,
				// elsefiles.get(i)), aName, file,cls);
				//				

			}
		}
		else {
			gen.computeMetrics(
				gen.createArrayOfJars(equinoxfiles),
				cls,
				args[0]);

			// // printing metrics values
			// printCodelevelModelMetrics(
			// detection.createArrayOfJars(equinoxfiles),
			// aName,
			// file,cls);
		}
		System.out
			.println("!!!! J'ai terminer le calcul des metriques pour cette version!!!!!!"
					+ args[0]);
	}

}
