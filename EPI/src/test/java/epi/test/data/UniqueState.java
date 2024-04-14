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
public final class UniqueState implements BitVectorPattern {
	private static UniqueState UniqueInstance;
	public static UniqueState getInstance() {
		if (UniqueState.UniqueInstance == null) {
			UniqueState.UniqueInstance = new UniqueState();
		}
		return UniqueState.UniqueInstance;
	}

	private static final String UNIQUE_STATE_NAME = "UniqueState";
	private static final String UNIQUE_STATE_STRING_NONE =
		"Context aggregation EPI_Abstract_State inheritance ConcreteState dummyRelationship EPI_Abstract_State ignorance Context dummyRelationship ConcreteState ignorance Context";
	private static final String UNIQUE_STATE_STRING_1 =
		"Context aggregation EPI_Abstract_State inheritance ConcreteState dummyRelationship EPI_Abstract_State ignorance Context dummyRelationship ConcreteState ignorance Context";
	private static final String UNIQUE_STATE_STRING_2 =
		"Context aggregation EPI_Abstract_State inheritance ConcreteState dummyRelationship EPI_Abstract_State ignorance Context dummyRelationship ConcreteState ignorance Context";
	private static final String UNIQUE_STATE_STRING_3 =
		"Context aggregation State inheritance ConcreteState dummyRelationship State ignorance Context dummyRelationship ConcreteState ignorance Context";
	private static final String UNIQUE_STATE_STRING_4 = "";
	// Same as NONE because all roles must be played.
	private static final String UNIQUE_STATE_STRING_1_AND_2 =
		"Context aggregation EPI_Abstract_State inheritance ConcreteState dummyRelationship EPI_Abstract_State ignorance Context dummyRelationship ConcreteState ignorance Context";
	private static final String UNIQUE_STATE_STRING_1_AND_3 =
		"Context aggregation State inheritance ConcreteState dummyRelationship State ignorance Context dummyRelationship ConcreteState ignorance Context";
	private static final String UNIQUE_STATE_STRING_1_AND_4 = "";
	// Same as 1
	private static final String UNIQUE_STATE_STRING_2_AND_3 =
		"Context aggregation State inheritance ConcreteState dummyRelationship State ignorance Context dummyRelationship ConcreteState ignorance Context";
	private static final String UNIQUE_STATE_STRING_2_AND_4 = "";
	// Same as 2 and 4
	private static final String UNIQUE_STATE_STRING_3_AND_4 = "";
	// Same as 3 and 4
	private static final String UNIQUE_STATE_STRING_1_AND_2_AND_3 =
		"Context aggregation State inheritance ConcreteState dummyRelationship State ignorance Context dummyRelationship ConcreteState ignorance Context";
	private static final String UNIQUE_STATE_STRING_ALL = "";
	// Same as 1 and 2 and 3
	private static final String[] UNIQUE_STATE_STRINGS =
		new String[] {
			UNIQUE_STATE_STRING_NONE,
			UNIQUE_STATE_STRING_1,
			UNIQUE_STATE_STRING_2,
			UNIQUE_STATE_STRING_3,
			UNIQUE_STATE_STRING_4,
			UNIQUE_STATE_STRING_1_AND_2,
			UNIQUE_STATE_STRING_1_AND_3,
			UNIQUE_STATE_STRING_1_AND_4,
			UNIQUE_STATE_STRING_2_AND_3,
			UNIQUE_STATE_STRING_2_AND_4,
			UNIQUE_STATE_STRING_3_AND_4,
			UNIQUE_STATE_STRING_1_AND_2_AND_3,
			UNIQUE_STATE_STRING_ALL };

	public String getName(){
		return UNIQUE_STATE_NAME;
	}
	public String[] getStrings(){
		return UNIQUE_STATE_STRINGS;
	}
}
