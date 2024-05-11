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
public final class Adapter implements BitVectorPattern {
	private static Adapter UniqueInstance;
	public static Adapter getInstance() {
		if (Adapter.UniqueInstance == null) {
			Adapter.UniqueInstance = new Adapter();
		}
		return Adapter.UniqueInstance;
	}

	private static final String ADAPTER_NAME = "Adapter";
	private static final String ADAPTER_STRING_NONE =
		// "EPI_Abstract_Target inheritance Adapter association Adaptee ignorance Adapter dummyRelationship Adaptee ignorance Target ignorance Adaptee";
		"";
	private static final String ADAPTER_STRING_1 =
		"EPI_Abstract_Target inheritance Adapter association Adaptee ignorance Adapter dummyRelationship Adaptee ignorance Target ignorance Adaptee";
	private static final String ADAPTER_STRING_2 =
		"EPI_Abstract_Target inheritance Adapter association Adaptee ignorance Adapter dummyRelationship Adaptee ignorance Target ignorance Adaptee";
	private static final String ADAPTER_STRING_3 =
		"Target inheritance Adapter association Adaptee ignorance Adapter dummyRelationship Adaptee ignorance Target ignorance Adaptee";
	private static final String ADAPTER_STRING_4 = "";
	// Same as NONE because all roles must be played.
	private static final String ADAPTER_STRING_1_AND_2 =
		"EPI_Abstract_Target inheritance Adapter association Adaptee ignorance Adapter dummyRelationship Adaptee ignorance Target ignorance Adaptee";
	private static final String ADAPTER_STRING_1_AND_3 =
		"Target inheritance Adapter association Adaptee ignorance Adapter dummyRelationship Adaptee ignorance Target ignorance Adaptee";
	private static final String ADAPTER_STRING_1_AND_4 = ""; // Same as 1
	private static final String ADAPTER_STRING_2_AND_3 =
		"Target inheritance Adapter association Adaptee ignorance Adapter dummyRelationship Adaptee ignorance Target ignorance Adaptee";
	private static final String ADAPTER_STRING_2_AND_4 = "";
	// Same as 2 and 4
	private static final String ADAPTER_STRING_3_AND_4 = "";
	// Same as 3 and 4
	private static final String ADAPTER_STRING_1_AND_2_AND_3 =
		"Target inheritance Adapter association Adaptee ignorance Adapter dummyRelationship Adaptee ignorance Target ignorance Adaptee";
	private static final String ADAPTER_STRING_ALL = "";
	// Same as 1 and 2 and 3
	private static final String[] ADAPTER_STRINGS =
		new String[] {
			ADAPTER_STRING_NONE,
			ADAPTER_STRING_1,
			ADAPTER_STRING_2,
			ADAPTER_STRING_3,
			ADAPTER_STRING_4,
			ADAPTER_STRING_1_AND_2,
			ADAPTER_STRING_1_AND_3,
			ADAPTER_STRING_1_AND_4,
			ADAPTER_STRING_2_AND_3,
			ADAPTER_STRING_2_AND_4,
			ADAPTER_STRING_3_AND_4,
			ADAPTER_STRING_1_AND_2_AND_3,
			ADAPTER_STRING_ALL };

	public String getName(){
		return ADAPTER_NAME;
	}
	public String[] getStrings(){
		return ADAPTER_STRINGS;
	}
}
