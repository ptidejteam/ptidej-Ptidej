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
import squad.quality.INominalQualityAttribute;
import squad.quality.INumericQualityAttribute;
import squad.quality.IQualityAttribute;
import squad.quality.qmood.QMOODRepository;
import util.io.ProxyDisk;

public class ComputeQMOOD {

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
	public static String[] createArrayOfJars(List<?> l, Object s) {
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

	public void computeQMOODValues(String[] someJARFiles, String arg) {

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
						"rsc/QMOOD/Mylyn/Mylyn.v" + arg + ".csv",
						true);

				final QMOODRepository qmoodRepository =
					QMOODRepository.getInstance();
				// final StringBuffer output = new StringBuffer();

				// Header.
				{
					final IQualityAttribute[] attributes =
						qmoodRepository.getQualityAttributes();
					for (int i = 0; i < attributes.length; i++) {
						final IQualityAttribute qualityAttribute =
							attributes[i];
						final String QAName = qualityAttribute.getName();
						out.write(QAName);
						out.flush();
						if (i < attributes.length - 1) {
							out.write(',');
							out.flush();
						}
					}
					out.write(",Entities\n");
					out.flush();
				}

				// QMOOD Values.
				final Iterator<?> entityIterator =
					codeLevelModel.getIteratorOnTopLevelEntities();
				while (entityIterator.hasNext()) {
					final IFirstClassEntity entity =
						(IFirstClassEntity) entityIterator.next();

					if (!(entity instanceof IGhost)) {
						System.out
							.print("Computing quality attributes with QMOOD for: ");
						System.out.println(entity.getName());
						System.out.print('\t');

						final IQualityAttribute[] attributes =
							qmoodRepository.getQualityAttributes();
						for (int i = 0; i < attributes.length; i++) {
							final IQualityAttribute qualityAttribute =
								attributes[i];
							final String QAName = qualityAttribute.getName();
							System.out.print(QAName);
							System.out.print(", ");
							try {
								final double value;
								if (qualityAttribute instanceof INominalQualityAttribute) {
									value = 0;
								}
								else if (qualityAttribute instanceof INumericQualityAttribute) {
									value =
										((INumericQualityAttribute) qmoodRepository
											.getQualityAttribute(QAName))
											.computeNumericValue(
												codeLevelModel,
												entity);
								}
								else {
									value = 0;

								}
								out.write(Double.toString(value));
								out.flush();
							}
							catch (final Exception e) {
								out.write("N/C");
								out.flush();
								e.printStackTrace();
							}

							if (i < attributes.length) {
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

		}

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stubfinal ICodeLevelModel codeLevelModel =

		ComputeQMOOD gen = new ComputeQMOOD();
		String dirName = "rsc/Mylyn/mylyn-" + args[0] + "-e3.3/e3.3/plugins";
		//		final String t =
		//			"org.eclipse.equinox(.[a-z]*)+|org.eclipse.core(.[a-z]*)+|org.eclipse.platform(.[a-z]*)+";

		List<String> files = new ArrayList<String>();

		List<String> jarfiles = gen.extractJarsFromDir(dirName, files);
		String[] someJARFiles = gen.createArrayOfJars(jarfiles);
		gen.computeQMOODValues(someJARFiles, args[0]);

		System.out
			.println("!!!! J'ai terminer le calcul de la qualite avec le modele QMOOD sur cette version!!!!!!"
					+ args[0]);
	}

}
