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
/**
 * @author Farouk ZAIDI - 2004-02-09
 * zaidifar-program-MetricsByPrimitives
 *
 */

package pom.primitives;

/**
 * The class Primitives has been created to be a common class 
 * for different primitives. It is true that there is a few primitives. 
 * But it is a common basis for future sets of primitives.
 */
abstract class Primitives {
	// Yann 2013/07/10: Dependencies!
	// I remove any dependence of the primitives on IAbstractLevelModel,
	// to have a much cleaner API that allows me to create metrics in
	// the user-interface without problems and to call them on the
	// current models as need :-)
	//	protected IAbstractLevelModel abstractLevelModel;
	//
	//	/**
	//	 * Constructor: handles the Idiom level model that allows to manipulate the program
	//	 * 
	//	 * @param primitiveIntrospector
	//	 */
	//	public Primitives(final IAbstractLevelModel anAbstactLevelModel) {
	//		this.abstractLevelModel = anAbstactLevelModel;
	//	}
}
