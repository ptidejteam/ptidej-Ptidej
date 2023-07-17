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
package padl.motif.repository;

import padl.kernel.IClass;
import padl.kernel.IMethod;
import padl.motif.IDesignMotifModel;
import padl.motif.models.BehaviouralMotifModel;
import util.multilingual.MultilingualManager;

/**
 * @author Foutse Khomh
 * @since  2007/03/01
 */
public class TemplateMethod extends BehaviouralMotifModel implements Cloneable,
		IDesignMotifModel {
	private static final char[] ABSTRACT_CLASS = "AbstractClass".toCharArray();
	private static final char[] CONCREATE_CLASS = "ConcreateClass"
		.toCharArray();
	private static final char[] PRIMITIVE_OPERATION = "PrimitiveOperation"
		.toCharArray();
	private static final long serialVersionUID = 8909819592555162188L;
	private static final char[] TEMPLATE_METHOD = "TemplateMethod"
		.toCharArray();

	public TemplateMethod() {
		super(TemplateMethod.TEMPLATE_METHOD);

		final IClass abstractClass =
			this.getFactory().createClass(
				TemplateMethod.ABSTRACT_CLASS,
				TemplateMethod.ABSTRACT_CLASS);
		abstractClass.setAbstract(true);
		final IMethod templateMethod =
			this.getFactory().createMethod(
				TemplateMethod.TEMPLATE_METHOD,
				TemplateMethod.TEMPLATE_METHOD);
		templateMethod.setAbstract(true);
		abstractClass.addConstituent(templateMethod);
		final IMethod primitiveOperation =
			this.getFactory().createMethod(
				TemplateMethod.PRIMITIVE_OPERATION,
				TemplateMethod.PRIMITIVE_OPERATION);
		primitiveOperation.setAbstract(true);
		abstractClass.addConstituent(primitiveOperation);

		final IClass concreateClass =
			this.getFactory().createClass(
				TemplateMethod.CONCREATE_CLASS,
				TemplateMethod.CONCREATE_CLASS);
		concreateClass.addInheritedEntity(abstractClass);

		final IMethod concreatePrimitiveOperation =
			this.getFactory().createMethod(
				TemplateMethod.PRIMITIVE_OPERATION,
				TemplateMethod.PRIMITIVE_OPERATION);
		concreateClass.addConstituent(concreatePrimitiveOperation);

		this.addConstituent(abstractClass);
		this.addConstituent(concreateClass);
	}

	public String getIntent() {
		return MultilingualManager.getString("INTENT", Flyweight.class);
	}

	public char[] getName() {
		return TemplateMethod.TEMPLATE_METHOD;
	}
}
