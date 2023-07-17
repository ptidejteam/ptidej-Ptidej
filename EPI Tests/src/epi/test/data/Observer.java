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
package epi.test.data;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  08/01/15
 */
public final class Observer implements BitVectorPattern {
	private static Observer UniqueInstance;
	public static Observer getInstance() {
		if (Observer.UniqueInstance == null) {
			Observer.UniqueInstance = new Observer();
		}
		return Observer.UniqueInstance;
	}

	private static final String OBSERVER_NAME = "Observer";
	private static final String OBSERVER_STRING_NONE =
		"Subject association EPI_Abstract_Observer inheritance ConcreteObserver association Subject dummyRelationship EPI_Abstract_Observer ignorance Subject ignorance ConcreteObserver";
	private static final String OBSERVER_STRING_1 =
		"Subject association EPI_Abstract_Observer inheritance ConcreteObserver association Subject dummyRelationship EPI_Abstract_Observer ignorance Subject ignorance ConcreteObserver";
	private static final String OBSERVER_STRING_2 =
		"Subject association EPI_Abstract_Observer inheritance ConcreteObserver association Subject dummyRelationship EPI_Abstract_Observer ignorance Subject ignorance ConcreteObserver";
	private static final String OBSERVER_STRING_3 =
		"Subject association Observer inheritance ConcreteObserver association Subject dummyRelationship Observer ignorance Subject ignorance ConcreteObserver";
	private static final String OBSERVER_STRING_4 = "";
	// Same as NONE because all roles must be played.
	private static final String OBSERVER_STRING_1_AND_2 =
		"Subject association EPI_Abstract_Observer inheritance ConcreteObserver association Subject dummyRelationship EPI_Abstract_Observer ignorance Subject ignorance ConcreteObserver";
	private static final String OBSERVER_STRING_1_AND_3 =
		"Subject association Observer inheritance ConcreteObserver association Subject dummyRelationship Observer ignorance Subject ignorance ConcreteObserver";
	private static final String OBSERVER_STRING_1_AND_4 = "";
	// Same as 1 and 4.
	private static final String OBSERVER_STRING_2_AND_3 =
		"Subject association Observer inheritance ConcreteObserver association Subject dummyRelationship Observer ignorance Subject ignorance ConcreteObserver";
	private static final String OBSERVER_STRING_2_AND_4 = "";
	// Same as 2 and 4.
	private static final String OBSERVER_STRING_3_AND_4 = "";
	// Same as 3 and 4.
	private static final String OBSERVER_STRING_1_AND_2_AND_3 =
		"Subject association Observer inheritance ConcreteObserver association Subject dummyRelationship Observer ignorance Subject ignorance ConcreteObserver";
	private static final String OBSERVER_STRING_ALL = "";
	// Same as 1 and 2 and 3.
	private static final String[] OBSERVER_STRINGS =
		new String[] {
			OBSERVER_STRING_NONE,
			OBSERVER_STRING_1,
			OBSERVER_STRING_2,
			OBSERVER_STRING_3,
			OBSERVER_STRING_4,
			OBSERVER_STRING_1_AND_2,
			OBSERVER_STRING_1_AND_3,
			OBSERVER_STRING_1_AND_4,
			OBSERVER_STRING_2_AND_3,
			OBSERVER_STRING_2_AND_4,
			OBSERVER_STRING_3_AND_4,
			OBSERVER_STRING_1_AND_2_AND_3,
			OBSERVER_STRING_ALL };

	public String getName(){
		return OBSERVER_NAME;
	}
	public String[] getStrings(){
		return OBSERVER_STRINGS;
	}
}
