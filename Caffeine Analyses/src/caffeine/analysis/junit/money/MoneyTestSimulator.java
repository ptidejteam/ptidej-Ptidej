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
package caffeine.analysis.junit.money;

import caffeine.simulator.Caffeine;

/**
 * DO NOT FORGET TO INCREASE THE STACK SIZE WITH -Xss256k.
 * 
 * @version 0.2
 * @author	Yann-Gaël Guéhéneuc
 */
public class MoneyTestSimulator {
	public static void main(String[] args) {
		Caffeine.getUniqueInstance().run(
			// "Rules/Composition.pl",
			"Rules/MethodCalls.pl",
			// "Caffeine/Analysis/JUnit/Money/MoneyTest-MoneyBag.trace");
			// "Caffeine/Analysis/JUnit/Money/TestResult-TestFailure.trace");
			"Caffeine/Analysis/JUnit/Money/TestRunner-TestRunView.trace");
			// "Caffeine/Analysis/JUnit/Money/TestSuite-Test.trace");
			// "Caffeine/Analysis/JUnit/Money/TestSuite-Test_AWT.trace");
			// "Caffeine/Analysis/JUnit/Money/TestSuite-Test_Swing.trace");
	}
}
