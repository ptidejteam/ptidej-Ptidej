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
public final class Composite implements BitVectorPattern {
	private static Composite UniqueInstance;
	public static Composite getInstance() {
		if (Composite.UniqueInstance == null) {
			Composite.UniqueInstance = new Composite();
		}
		return Composite.UniqueInstance;
	}

	private static final String COMPOSITE_NAME = "Composite";
	private static final String COMPOSITE_STRING_NONE =
		"Composite containerComposition EPI_Abstract_Component inheritance Composite dummyRelationship EPI_Abstract_Component inheritance Leaf";
	private static final String COMPOSITE_STRING_1 =
		"Composite containerComposition EPI_Abstract_Component inheritance Composite dummyRelationship EPI_Abstract_Component inheritance Leaf";
	private static final String COMPOSITE_STRING_2 =
		"Composite containerComposition EPI_Abstract_Component inheritance Composite dummyRelationship EPI_Abstract_Component inheritance Leaf";
	private static final String COMPOSITE_STRING_3 =
		"Composite containerComposition Component inheritance Composite dummyRelationship Component inheritance Leaf";
	private static final String COMPOSITE_STRING_4 =
		"Composite containerComposition EPI_Abstract_Component inheritance2 Composite dummyRelationship EPI_Abstract_Component";
	private static final String COMPOSITE_STRING_1_AND_2 =
		"Composite containerComposition EPI_Abstract_Component inheritance Composite dummyRelationship EPI_Abstract_Component inheritance Leaf";
	private static final String COMPOSITE_STRING_1_AND_3 =
		"Composite containerComposition Component inheritance Composite dummyRelationship Component inheritance Leaf";
	private static final String COMPOSITE_STRING_1_AND_4 =
		"Composite containerComposition EPI_Abstract_Component inheritance2 Composite dummyRelationship EPI_Abstract_Component";
	private static final String COMPOSITE_STRING_2_AND_3 =
		"Composite containerComposition Component inheritance Composite dummyRelationship Component inheritance Leaf";
	private static final String COMPOSITE_STRING_2_AND_4 =
		"Composite containerComposition EPI_Abstract_Component inheritance2 Composite dummyRelationship EPI_Abstract_Component";
	private static final String COMPOSITE_STRING_3_AND_4 =
		"Composite containerComposition Component inheritance2 Composite dummyRelationship Component";
	private static final String COMPOSITE_STRING_1_AND_2_AND_3 =
		"Composite containerComposition Component inheritance Composite dummyRelationship Component inheritance Leaf";
	private static final String COMPOSITE_STRING_ALL =
		"Composite containerComposition Component inheritance2 Composite dummyRelationship Component";
	private static final String[] COMPOSITE_STRINGS =
		new String[] {
			COMPOSITE_STRING_NONE,
			COMPOSITE_STRING_1,
			COMPOSITE_STRING_2,
			COMPOSITE_STRING_3,
			COMPOSITE_STRING_4,
			COMPOSITE_STRING_1_AND_2,
			COMPOSITE_STRING_1_AND_3,
			COMPOSITE_STRING_1_AND_4,
			COMPOSITE_STRING_2_AND_3,
			COMPOSITE_STRING_2_AND_4,
			COMPOSITE_STRING_3_AND_4,
			COMPOSITE_STRING_1_AND_2_AND_3,
			COMPOSITE_STRING_ALL };

	public String getName(){
		return COMPOSITE_NAME;
	}
	public String[] getStrings(){
		return COMPOSITE_STRINGS;
	}
}
