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
package ptidej.solver.occurrence;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/03/10
 */
public class PrincipalComponents {
	public static final char[][][] Composite =
		new char[][][] { new char[][] { "composite".toCharArray(),
				"component".toCharArray() } };
	public static final char[][][] AbstractFactory =
		new char[][][] { new char[][] { "abstractfactory".toCharArray(),
				"abstractproduct".toCharArray() } };
	public static final char[][][] Blob =
		new char[][][] { new char[][] { "LargeClass".toCharArray() },
				new char[][] { "LowCohesion".toCharArray() },
				new char[][] { "ControllerClass".toCharArray() } };
	public static final char[][][] FactoryMethod =
		new char[][][] { new char[][] { "creator".toCharArray(),
				"product".toCharArray() } };
	public static final char[][][] FunctionalDecomposition =
		new char[][][] { new char[][] { "NoInheritance".toCharArray() },
				new char[][] { "FunctionMethodClass".toCharArray() },
				new char[][] { "FunctionClass".toCharArray() } };
	public static final char[][][] SpaghettiCode =
		new char[][][] { new char[][] { "ClassGlobalVariable".toCharArray() },
				new char[][] { "LongMethod".toCharArray() } };
	public static final char[][][] SwissArmyKnife =
		new char[][][] { new char[][] { "MultipleInterface".toCharArray() },
				new char[][] { "StaticMethodClass".toCharArray() },
				new char[][] { "DefaultProtectedMethodClass".toCharArray() } };
	public static void print(final ReducedOccurrence[] someReducedOccurrences) {
		for (int i = 0; i < someReducedOccurrences.length; i++) {
			final ReducedOccurrence principalComponent =
				someReducedOccurrences[i];
			System.out.println(principalComponent);
		}
	}
}
