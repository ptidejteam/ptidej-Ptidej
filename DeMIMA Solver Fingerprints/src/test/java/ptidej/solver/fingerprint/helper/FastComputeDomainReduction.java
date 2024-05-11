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
import java.util.ArrayList;

import util.io.ProxyDisk;

/**
 * @author Jean-Yves Guillomarc'h
 * @since 2004/11/12
 */
public class FastComputeDomainReduction extends TestSolver {

	public FastComputeDomainReduction(
		final String path,
		final String name,
		final String motif) {
		this(path, null, name, motif);
	}
	public FastComputeDomainReduction(
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
		// args: -src path [-pkg [packageName]] -name programName -motif motif
		FastComputeDomainReduction compute = null;
		if (args[2].equals("-pkg")) {

			//Creation of pkg tab
			int cpt = 3;
			ArrayList pkgList = new ArrayList();
			while (!args[cpt].equals("-name")) {
				pkgList.add(args[cpt]);
				cpt++;
			}
			String[] pkgTab = new String[pkgList.size()];
			for (int i = 0; i < pkgList.size(); i++)
				pkgTab[i] = (String) pkgList.get(i);

			compute =
				new FastComputeDomainReduction(
					args[1],
					pkgTab,
					args[cpt + 1],
					args[cpt + 3]);

		}
		else {
			if (args.length == 6) {
				compute =
					new FastComputeDomainReduction(args[1], args[3], args[5]);
			}
			else {
				System.out
					.println("Usage: FastComputeDomainReduction -src path [-pkg [packageName]] -name programName -motif motif");
				System.exit(0);
			}
		}

		try {
			final PrintWriter out =
				new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
					"rsc/DomainReductionFor" + compute.getMotifName() + "On"
							+ compute.getProgramName() + ".txt"));
			compute.computeDomainReduction(out);
			out.flush();
			out.close();
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
