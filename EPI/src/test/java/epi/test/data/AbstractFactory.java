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
public final class AbstractFactory implements BitVectorPattern {
	private static AbstractFactory UniqueInstance;
	public static AbstractFactory getInstance() {
		if (AbstractFactory.UniqueInstance == null) {
			AbstractFactory.UniqueInstance = new AbstractFactory();
		}
		return AbstractFactory.UniqueInstance;
	}

	private static final String ABSTRACT_FACTORY_NAME = "AbstractFactory";
	private static final String ABSTRACT_FACTORY_STRING_NONE =
		"ConcreteFactory creation Product dummyRelationship ConcreteFactory association Product dummyRelationship EPI_Abstract_AbstractProduct inheritance Product dummyRelationship EPI_Abstract_AbstractFactory inheritance ConcreteFactory";
	private static final String ABSTRACT_FACTORY_STRING_1 =
		"ConcreteFactory creation Product dummyRelationship ConcreteFactory association Product dummyRelationship EPI_Abstract_AbstractProduct inheritance Product dummyRelationship EPI_Abstract_AbstractFactory inheritance ConcreteFactory";
	private static final String ABSTRACT_FACTORY_STRING_2 =
		"ConcreteFactory creation Product dummyRelationship ConcreteFactory association Product dummyRelationship EPI_Abstract_AbstractProduct inheritance Product dummyRelationship EPI_Abstract_AbstractFactory inheritance ConcreteFactory";
	private static final String ABSTRACT_FACTORY_STRING_3 =
		"ConcreteFactory creation Product dummyRelationship ConcreteFactory association Product dummyRelationship AbstractProduct inheritance Product dummyRelationship AbstractFactory inheritance ConcreteFactory";
	private static final String ABSTRACT_FACTORY_STRING_4 =
		"ConcreteFactory creation Product dummyRelationship ConcreteFactory association Product dummyRelationship EPI_Abstract_AbstractProduct inheritance Product dummyRelationship EPI_Abstract_AbstractFactory inheritance ConcreteFactory";
	private static final String ABSTRACT_FACTORY_STRING_1_AND_2 =
		"ConcreteFactory creation Product dummyRelationship ConcreteFactory association Product dummyRelationship EPI_Abstract_AbstractProduct inheritance Product dummyRelationship EPI_Abstract_AbstractFactory inheritance ConcreteFactory";
	private static final String ABSTRACT_FACTORY_STRING_1_AND_3 =
		"ConcreteFactory creation Product dummyRelationship ConcreteFactory association Product dummyRelationship AbstractProduct inheritance Product dummyRelationship AbstractFactory inheritance ConcreteFactory";
	private static final String ABSTRACT_FACTORY_STRING_1_AND_4 =
		"ConcreteFactory creation Product dummyRelationship ConcreteFactory association Product dummyRelationship EPI_Abstract_AbstractProduct inheritance Product dummyRelationship EPI_Abstract_AbstractFactory inheritance ConcreteFactory";
	private static final String ABSTRACT_FACTORY_STRING_2_AND_3 =
		"ConcreteFactory creation Product dummyRelationship ConcreteFactory association Product dummyRelationship AbstractProduct inheritance Product dummyRelationship AbstractFactory inheritance ConcreteFactory";
	private static final String ABSTRACT_FACTORY_STRING_2_AND_4 =
		"ConcreteFactory creation Product dummyRelationship ConcreteFactory association Product dummyRelationship EPI_Abstract_AbstractProduct inheritance Product dummyRelationship EPI_Abstract_AbstractFactory inheritance ConcreteFactory";
	private static final String ABSTRACT_FACTORY_STRING_3_AND_4 =
		"ConcreteFactory creation Product dummyRelationship ConcreteFactory association Product dummyRelationship AbstractProduct inheritance Product dummyRelationship AbstractFactory inheritance ConcreteFactory";
	private static final String ABSTRACT_FACTORY_STRING_1_AND_2_AND_3 =
		"ConcreteFactory creation Product dummyRelationship ConcreteFactory association Product dummyRelationship AbstractProduct inheritance Product dummyRelationship AbstractFactory inheritance ConcreteFactory";
	private static final String ABSTRACT_FACTORY_STRING_ALL =
		"ConcreteFactory creation Product dummyRelationship ConcreteFactory association Product dummyRelationship AbstractProduct inheritance Product dummyRelationship AbstractFactory inheritance ConcreteFactory";
	private static final String[] ABSTRACT_FACTORY_STRINGS =
		new String[] {
			ABSTRACT_FACTORY_STRING_NONE,
			ABSTRACT_FACTORY_STRING_1,
			ABSTRACT_FACTORY_STRING_2,
			ABSTRACT_FACTORY_STRING_3,
			ABSTRACT_FACTORY_STRING_4,
			ABSTRACT_FACTORY_STRING_1_AND_2,
			ABSTRACT_FACTORY_STRING_1_AND_3,
			ABSTRACT_FACTORY_STRING_1_AND_4,
			ABSTRACT_FACTORY_STRING_2_AND_3,
			ABSTRACT_FACTORY_STRING_2_AND_4,
			ABSTRACT_FACTORY_STRING_3_AND_4,
			ABSTRACT_FACTORY_STRING_1_AND_2_AND_3,
			ABSTRACT_FACTORY_STRING_ALL };

	public String getName() {
		return ABSTRACT_FACTORY_NAME;
	}
	public String[] getStrings() {
		return ABSTRACT_FACTORY_STRINGS;
	}
}
