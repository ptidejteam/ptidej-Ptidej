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
package ptidej.solver.fingerprint;

import padl.kernel.IAbstractLevelModel;
import padl.kernel.IFirstClassEntity;
import pom.metrics.MetricsRepository;

/**
 * @author Jean-Yves Guillomarc'h
 */
public class Rule {
	public final static Rule A_T = new Rule(
		"A_T",
		new FingerprintSet[] { new FingerprintSet(new Fingerprint[] {
				new Fingerprint("NOC", Fingerprint.GEQ, 1.0),
				new Fingerprint("CBO", Fingerprint.LEQ, 0.0) }) });
	public final static Rule AF_AF =
		new Rule(
			"AF_AF",
			new FingerprintSet[] { new FingerprintSet(
				new Fingerprint[] { new Fingerprint("CLD", Fingerprint.GEQ, 1.0) }) });
	public final static Rule AF_AP = new Rule("AF_AP", new FingerprintSet[] {
			new FingerprintSet(new Fingerprint[] {
					new Fingerprint("DIT", Fingerprint.LEQ, 1.0),
					new Fingerprint("CBO", Fingerprint.LEQ, 6.0) }),
			new FingerprintSet(new Fingerprint[] { new Fingerprint(
				"NCM",
				Fingerprint.GEQ,
				64.0) }) });
	public final static Rule AF_C = new Rule("AF_C", new FingerprintSet[] {
			new FingerprintSet(new Fingerprint[] { new Fingerprint(
				"NCM",
				Fingerprint.GEQ,
				56.0) }),
			new FingerprintSet(new Fingerprint[] {
					new Fingerprint("WMC", Fingerprint.GEQ, 166.0),
					new Fingerprint("CBO", Fingerprint.GEQ, 20.0) }),
			new FingerprintSet(new Fingerprint[] {
					new Fingerprint("NMI", Fingerprint.GEQ, 9.0),
					new Fingerprint("DIT", Fingerprint.LEQ, 1.0) }) });
	public final static Rule C_LEAF_ROLE_1 = new Rule(
		"C_L",
		new FingerprintSet[] {
				new FingerprintSet(new Fingerprint[] {
						new Fingerprint("NMI", Fingerprint.GEQ, 25.0),
						new Fingerprint("DIT", Fingerprint.GEQ, 5.0) }),
				new FingerprintSet(new Fingerprint[] {
						new Fingerprint("NMI", Fingerprint.GEQ, 25.0),
						new Fingerprint("NMO", Fingerprint.LEQ, 1.0) }),
				new FingerprintSet(new Fingerprint[] {
						new Fingerprint("CBO", Fingerprint.GEQ, 10.0),
						new Fingerprint("DIT", Fingerprint.GEQ, 3.0),
						new Fingerprint("NCM", Fingerprint.LEQ, 10.0) }) });
	public final static Rule C_LEAF_TEST =
		new Rule(
			"C_L Test",
			new FingerprintSet[] { new FingerprintSet(
				new Fingerprint[] { new Fingerprint("DIT", Fingerprint.GEQ, 1.0) }) });
	public final static Rule CM_C = new Rule("CM_C", new FingerprintSet[] {
			new FingerprintSet(new Fingerprint[] { new Fingerprint(
				"NMO",
				Fingerprint.GEQ,
				7.0) }),
			new FingerprintSet(new Fingerprint[] {
					new Fingerprint("connectivity", Fingerprint.GEQ, 1.130303),
					new Fingerprint("WMC", Fingerprint.GEQ, 31.0) }) });
	public final static Rule CM_CC = new Rule("CM_CC", new FingerprintSet[] {
			new FingerprintSet(new Fingerprint[] {
					new Fingerprint("CBO", Fingerprint.LEQ, 8.0),
					new Fingerprint("WMC", Fingerprint.GEQ, 10.0),
					new Fingerprint("DIT", Fingerprint.GEQ, 2.0) }),
			new FingerprintSet(new Fingerprint[] {
					new Fingerprint("CBO", Fingerprint.LEQ, 1.0),
					new Fingerprint("WMC", Fingerprint.GEQ, 7.0),
					new Fingerprint("DIT", Fingerprint.GEQ, 3.0) }) });
	public final static Rule CM_I = new Rule("CM_I", new FingerprintSet[] {
			new FingerprintSet(new Fingerprint[] { new Fingerprint(
				"NCM",
				Fingerprint.GEQ,
				56.0) }),
			new FingerprintSet(new Fingerprint[] {
					new Fingerprint("DIT", Fingerprint.LEQ, 1.0),
					new Fingerprint("LCOM5", Fingerprint.LEQ, 0.666667) }),
			new FingerprintSet(new Fingerprint[] { new Fingerprint(
				"WMC",
				Fingerprint.GEQ,
				415.0) }) });
	public final static Rule CM_R = new Rule(
		"CM_R",
		new FingerprintSet[] { new FingerprintSet(new Fingerprint[] {
				new Fingerprint("CBO", Fingerprint.LEQ, 5.0),
				new Fingerprint("DIT", Fingerprint.LEQ, 3.0) }) });
	public final static Rule FM_CC = new Rule("FM_CC", new FingerprintSet[] {
			new FingerprintSet(new Fingerprint[] { new Fingerprint(
				"DIT",
				Fingerprint.GEQ,
				5.0) }),
			new FingerprintSet(new Fingerprint[] {
					new Fingerprint("WMC", Fingerprint.GEQ, 158.0),
					new Fingerprint("WMC", Fingerprint.LEQ, 179.0) }),
			new FingerprintSet(new Fingerprint[] {
					new Fingerprint("NMI", Fingerprint.EQ, 3.0),
					new Fingerprint("DIT", Fingerprint.GEQ, 2.0) }) });
	public final static Rule FM_CP = new Rule("FM_CP", new FingerprintSet[] {
			new FingerprintSet(new Fingerprint[] { new Fingerprint(
				"DIT",
				Fingerprint.GEQ,
				5.0) }),
			new FingerprintSet(new Fingerprint[] {
					new Fingerprint("DIT", Fingerprint.GEQ, 3.0),
					new Fingerprint("CBO", Fingerprint.LEQ, 6.0),
					new Fingerprint("NMI", Fingerprint.LEQ, 16.0) }),
			new FingerprintSet(new Fingerprint[] { new Fingerprint(
				"NCM",
				Fingerprint.GEQ,
				57.0) }) });

	public static void main(final String args[]) {
		System.out.println(Rule.C_LEAF_ROLE_1);
		System.out.println(Rule.A_T);
		System.out.println(Rule.CM_C);
		System.out.println(Rule.CM_CC);
		System.out.println(Rule.CM_I);
		System.out.println(Rule.CM_R);
		System.out.println(Rule.FM_CC);
		System.out.println(Rule.FM_CP);
		System.out.println(Rule.AF_AF);
		System.out.println(Rule.AF_AP);
		System.out.println(Rule.AF_C);
	}

	private final String name;
	private final FingerprintSet[] ruleSets;
	public Rule(final String name, final FingerprintSet[] ruleSets) {
		this.ruleSets = ruleSets;
		this.name = name;
	}
	public boolean compute(
		final MetricsRepository metrics,
		final IAbstractLevelModel anAbstractLevelModel,
		final IFirstClassEntity firstClassEntity) {

		for (int i = 0; i < this.ruleSets.length; i++) {
			if (this.ruleSets[i].compute(
				metrics,
				anAbstractLevelModel,
				firstClassEntity)) {

				return true;
			}
		}

		return false;
	}
	public String getName() {
		return this.name;
	}
	public FingerprintSet[] getRuleSets() {
		return this.ruleSets;
	}
	public String toString() {
		final StringBuffer s = new StringBuffer("Role: ");
		s.append(this.name);
		s.append('\n');
		for (int i = 0; i < this.ruleSets.length; i++) {
			s.append('\t');
			s.append(this.ruleSets[i]);
			s.append('\n');
		}

		return s.toString();
	}
}
