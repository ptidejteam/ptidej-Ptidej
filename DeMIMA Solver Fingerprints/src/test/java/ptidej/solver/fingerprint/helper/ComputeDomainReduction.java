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

import java.io.PrintWriter;

import util.io.ProxyDisk;

/**
 * @author Jean-Yves Guyomarc'h
 * @since 2004/11/12
 */
public class ComputeDomainReduction extends TestSolver {
	public ComputeDomainReduction(
		final String path,
		final String name,
		final String motif) {
		this(path, null, name, motif);
	}
	public ComputeDomainReduction(
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
					Class.forName(TestSolver.FingerprintPackageName
							+ this.getMotifName());
			}
			else {
				motif =
					Class.forName(TestSolver.NoFingerprintPackageName
							+ this.getMotifName());
			}
		}
		catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}

		return motif;
	}
	public static void main(final String[] args) {
		final ComputeDomainReduction ganttProject =
			new ComputeDomainReduction(
				"../GanttProject v1.10.2/bin/",
				"GanttProject",
				"CompositeMotif");
		final ComputeDomainReduction holubSQL =
			new ComputeDomainReduction(
				"../HolubSQL v1.0/bin/",
				"HolubSQL",
				"CompositeMotif");
		final ComputeDomainReduction johtdraw =
			new ComputeDomainReduction(
				"../JHotDraw v5.1/bin/",
				new String[] { "CH.ifa.draw.figures.", "CH.ifa.draw.standard." },
				"JHotDraw ( limited )",
				"CompositeMotif");
		final ComputeDomainReduction jsettler =
			new ComputeDomainReduction(
				"../JSettlers v1.0.5/bin/",
				new String[] { "soc.robot." },
				"JSettler( soc.robot )",
				"CompositeMotif");
		final ComputeDomainReduction jtans =
			new ComputeDomainReduction(
				"../jTans v1.0/bin/",
				"jTans",
				"CompositeMotif");
		final ComputeDomainReduction junit =
			new ComputeDomainReduction(
				"../JUnit v3.7/bin/",
				"JUnit",
				"CompositeMotif");
		final ComputeDomainReduction juzzle =
			new ComputeDomainReduction(
				"../Juzzle v0.5/bin/",
				"Juzzle",
				"CompositeMotif");
		final ComputeDomainReduction lexi =
			new ComputeDomainReduction(
				"../Lexi v0.1.1 alph/bin/",
				"Lexi",
				"CompositeMotif");
		final ComputeDomainReduction quick =
			new ComputeDomainReduction(
				"../QuickUML 2001/bin/",
				"QuickUML",
				"CompositeMotif");
		final ComputeDomainReduction risk =
			new ComputeDomainReduction(
				"../Risk v1.0.7.5/bin/",
				"Risk",
				"CompositeMotif");

		final PrintWriter out =
			new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
				"rsc/DomainReductionFor" + junit.getMotifName() + "2.txt"));

		ganttProject.computeDomainReduction(out);
		out.println("--------------------------------------------------\n");
		out.flush();

		holubSQL.computeDomainReduction(out);
		out.println("--------------------------------------------------\n");
		out.flush();

		johtdraw.computeDomainReduction(out);
		out.println("--------------------------------------------------\n");
		out.flush();

		jsettler.computeDomainReduction(out);
		out.println("--------------------------------------------------\n");
		out.flush();

		jtans.computeDomainReduction(out);
		out.println("--------------------------------------------------\n");
		out.flush();

		junit.computeDomainReduction(out);
		out.println("--------------------------------------------------\n");
		out.flush();

		juzzle.computeDomainReduction(out);
		out.println("--------------------------------------------------\n");
		out.flush();

		lexi.computeDomainReduction(out);
		out.println("--------------------------------------------------\n");
		out.flush();

		quick.computeDomainReduction(out);
		out.println("--------------------------------------------------\n");
		out.flush();

		risk.computeDomainReduction(out);
		out.println("--------------------------------------------------\n");
		out.flush();

		out.close();
	}
}
