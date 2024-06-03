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
package ptidej.solver.fingerprint.helper;

/**
 * @author Jean-Yves Guyomarc'h
 * @since 2004/11/12
 */
public class ComputeSolutions extends Solver {
	public ComputeSolutions(
		final String path,
		final String name,
		final String motif) {
		this(path, null, name, motif);
	}
	public ComputeSolutions(
		final String path,
		final String[] packageNames,
		final String name,
		final String motif) {
		super(path, packageNames, name, motif);
	}
	public Class getMotif(final int mode) {
		Class motif = null;
		try {
			if (mode == Logger.WITH_RULES) {
				motif =
					Class.forName(Solver.FingerprintPackageName
							+ this.getMotifName());
			}
			else {
				motif =
					Class.forName(Solver.NoFingerprintPackageName
							+ this.getMotifName());
			}
		}
		catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}

		return motif;
	}
	public static void main(final String[] args) {
		//			final ComputeSolutions ganttProject =
		//				new ComputeSolutions(
		//					"../GanttProject v1.10.2/bin/",
		//					"AF time for GanttProject",
		//					"AbstractFactoryMotif");
		//			final ComputeSolutions holubSQL =
		//				new ComputeSolutions("../HolubSQL v1.0/bin/", "AF for HolubSQL", "AbstractFactoryMotif");
		//				new ComputeSolutions("../JHotDraw v5.1/bin/", new String[] { "CH.ifa.draw.figures.", "CH.ifa.draw.framework.", "CH.ifa.draw.standard."},"C for JHotDraw Auto (fig,frame, std)", "CompositeMotif");
		//		final ComputeSolutions jsettler =
		//			new ComputeSolutions(
		//				"../JSettlers v1.0.5/bin/",
		//				"A for JSettlers Auto",
		//				"AdapterMotif");
		//			final ComputeSolutions jtans1 =
		//				new ComputeSolutions("../jTans v1.0/bin/", "C time for jTans Auto", "CompositeMotif");
		//				final ComputeSolutions jtans2 =
		//				new ComputeSolutions("../jTans v1.0/bin/", "A for jTans Auto", "AdapterMotif");
		//				final ComputeSolutions jtans3 =
		//				new ComputeSolutions("../jTans v1.0/bin/", "AF for jTans Auto", "AbstractFactoryMotif");
		//			final ComputeSolutions junit =
		//				new ComputeSolutions("../JUnit v3.7/bin/", new String[] { "junit.samples."}, "C for JUnit(samples)", "CompositeMotif");
		//					final ComputeSolutions juzzle =
		//						new ComputeSolutions("../Juzzle v0.5/bin/", "C for Juzzle", "CompositeMotif");
		//					juzzle.computeSolutions();
		//			final ComputeSolutions lexi =
		//				new ComputeSolutions("../Lexi v0.1.1 alph/bin/", "AF for Lexi Auto", "AbstractFactoryMotif");
		//			final ComputeSolutions quick =
		//				new ComputeSolutions("../QuickUML 2001/bin/", "C for QuickUML Auto", "CompositeMotif");
		//			final ComputeSolutions risk =
		//				new ComputeSolutions("../Risk v1.0.7.5/bin/", "C time for Risk Auto", "CompositeMotif");

		ComputeSolutions test = null;
		//		
		//DONE		for (int i = 0; i < 7; i++) {
		//			test =
		//				new ComputeSolutions(
		//					"../Lexi v0.1.1 alph/bin/", "A for Lexi" + i, "AdapterMotif");
		//			test.computeSolutions();
		//		}
		//		gc();
		//		for (int i = 0; i < 7; i++) {
		//DONE			test =
		//				new ComputeSolutions(
		//					"../JUnit v3.7/bin/", "A for JUnit", "AdapterMotif");
		//			test.computeSolutions();
		//		}
		//		gc();
		//		for (int i = 0; i < 7; i++) {
		//			test =
		//				new ComputeSolutions(
		//					"../GanttProject v1.10.2/bin/", "A for Gant" + i, "AdapterMotif");
		//			test.computeSolutions();
		//		}
		//		gc();
		//		for (int i = 0; i < 7; i++) {
		//			test =
		//				new ComputeSolutions(
		//					"../JSettlers v1.0.5/bin/", "A for JSettler", "AdapterMotif");
		//			test.computeSolutions();
		//		}
		//		
		//		
		//		gc();
		//DONE		for (int i = 0; i < 7; i++) {
		//			test =
		//				new ComputeSolutions(
		//					"../JUnit v3.7/bin/", "AF for JUnit" + i, "AbstractFactoryMotif");
		//			test.computeSolutions();
		//		}
		//		
		//		gc();
		//		for (int i = 0; i < 7; i++) {
		test =
			new ComputeSolutions(
				"../GanttProject v1.10.2/bin/",
				"C for Gant",
				"CompositeMotif");
		test.computeSolutions();
		//		}
		//		gc();
		//		for (int i = 0; i < 7; i++) {
		//DONE			test =
		//				new ComputeSolutions(
		//					"../HolubSQL v1.0/bin/", "C for Holub", "CompositeMotif");
		//			test.computeSolutions();
		//		}
		//		gc();
		//		for (int i = 0; i < 7; i++) {
		//DONE			test =
		//				new ComputeSolutions(
		//					"../Lexi v0.1.1 alph/bin/", "C for Lexi", "CompositeMotif");
		//			test.computeSolutions();
		//			gc();
		//		}
	}

	public static void gc() {
		for (int i = 0; i < 3; i++)
			System.gc();
	}
}
