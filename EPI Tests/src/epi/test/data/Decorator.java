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
public final class Decorator implements BitVectorPattern {
	private static Decorator UniqueInstance;
	public static Decorator getInstance() {
		if (Decorator.UniqueInstance == null) {
			Decorator.UniqueInstance = new Decorator();
		}
		return Decorator.UniqueInstance;
	}

	private static final String DECORATOR_NAME = "Decorator";
	private static final String DECORATOR_STRING_NONE =
		"EPI_Abstract_Decorator containerComposition EPI_Abstract_Component inheritance EPI_Abstract_Decorator inheritance ConcreteDecorator dummyRelationship EPI_Abstract_Component inheritance ConcreteComponent";
	private static final String DECORATOR_STRING_1 =
		"EPI_Abstract_Decorator containerComposition EPI_Abstract_Component inheritance EPI_Abstract_Decorator inheritance ConcreteDecorator dummyRelationship EPI_Abstract_Component inheritance ConcreteComponent";
	private static final String DECORATOR_STRING_2 =
		"EPI_Abstract_Decorator containerComposition EPI_Abstract_Component inheritance EPI_Abstract_Decorator inheritance ConcreteDecorator dummyRelationship EPI_Abstract_Component inheritance ConcreteComponent";
	private static final String DECORATOR_STRING_3 =
		"Decorator containerComposition Component inheritance Decorator inheritance ConcreteDecorator dummyRelationship Component inheritance ConcreteComponent";
	private static final String DECORATOR_STRING_4 =
		"EPI_Abstract_Decorator containerComposition EPI_Abstract_Component inheritance EPI_Abstract_Decorator inheritance ConcreteDecorator dummyRelationship EPI_Abstract_Component";
	private static final String DECORATOR_STRING_1_AND_2 =
		"EPI_Abstract_Decorator containerComposition EPI_Abstract_Component inheritance EPI_Abstract_Decorator inheritance ConcreteDecorator dummyRelationship EPI_Abstract_Component inheritance ConcreteComponent";
	private static final String DECORATOR_STRING_1_AND_3 =
		"Decorator containerComposition Component inheritance Decorator inheritance ConcreteDecorator dummyRelationship Component inheritance ConcreteComponent";
	private static final String DECORATOR_STRING_1_AND_4 =
		"EPI_Abstract_Decorator containerComposition EPI_Abstract_Component inheritance EPI_Abstract_Decorator inheritance ConcreteDecorator dummyRelationship EPI_Abstract_Component";
	private static final String DECORATOR_STRING_2_AND_3 =
		"Decorator containerComposition Component inheritance Decorator inheritance ConcreteDecorator dummyRelationship Component inheritance ConcreteComponent";
	private static final String DECORATOR_STRING_2_AND_4 =
		"EPI_Abstract_Decorator containerComposition EPI_Abstract_Component inheritance EPI_Abstract_Decorator inheritance ConcreteDecorator dummyRelationship EPI_Abstract_Component";
	private static final String DECORATOR_STRING_3_AND_4 =
		"EPI_Abstract_Decorator containerComposition EPI_Abstract_Component inheritance EPI_Abstract_Decorator inheritance ConcreteDecorator dummyRelationship EPI_Abstract_Component";
	private static final String DECORATOR_STRING_1_AND_2_AND_3 =
		"Decorator containerComposition Component inheritance Decorator inheritance ConcreteDecorator dummyRelationship Component inheritance ConcreteComponent";
	private static final String DECORATOR_STRING_ALL =
		"Decorator containerComposition Component inheritance Decorator inheritance ConcreteDecorator dummyRelationship Component";
	private static final String[] DECORATOR_STRINGS =
		new String[] {
			DECORATOR_STRING_NONE,
			DECORATOR_STRING_1,
			DECORATOR_STRING_2,
			DECORATOR_STRING_3,
			DECORATOR_STRING_4,
			DECORATOR_STRING_1_AND_2,
			DECORATOR_STRING_1_AND_3,
			DECORATOR_STRING_1_AND_4,
			DECORATOR_STRING_2_AND_3,
			DECORATOR_STRING_2_AND_4,
			DECORATOR_STRING_3_AND_4,
			DECORATOR_STRING_1_AND_2_AND_3,
			DECORATOR_STRING_ALL };

	public String getName(){
		return DECORATOR_NAME;
	}
	public String[] getStrings(){
		return DECORATOR_STRINGS;
	}
}
