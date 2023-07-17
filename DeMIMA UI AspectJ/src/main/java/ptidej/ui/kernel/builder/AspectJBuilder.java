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
package ptidej.ui.kernel.builder;

import java.util.HashMap;
import java.util.Map;
import padl.aspectj.kernel.IAdvice;
import padl.aspectj.kernel.IAspect;
import padl.aspectj.kernel.IInterTypeConstructor;
import padl.aspectj.kernel.IInterTypeDeclareParents;
import padl.aspectj.kernel.IInterTypeField;
import padl.aspectj.kernel.IInterTypeMethod;
import padl.aspectj.kernel.IPointcut;
import padl.kernel.IConstituentOfEntity;
import padl.kernel.IConstituentOfModel;
import ptidej.ui.kernel.Advice;
import ptidej.ui.kernel.Aspect;
import ptidej.ui.kernel.Element;
import ptidej.ui.kernel.Entity;
import ptidej.ui.kernel.InterTypeConstructor;
import ptidej.ui.kernel.InterTypeDeclareParents;
import ptidej.ui.kernel.InterTypeField;
import ptidej.ui.kernel.InterTypeMethod;
import ptidej.ui.kernel.Pointcut;
import ptidej.ui.primitive.IPrimitiveFactory;

/**
 * @author Jean-Yves Guyomarc'h
 * @since 2005/08/15
 */
public final class AspectJBuilder extends Builder {
	private static Map UniqueInstances;
	public static Builder getCurrentBuilder(
		final IPrimitiveFactory aPrimitiveFactory) {
		if (AspectJBuilder.UniqueInstances == null) {
			AspectJBuilder.UniqueInstances = new HashMap();
		}
		if (AspectJBuilder.UniqueInstances.get(aPrimitiveFactory) == null) {
			AspectJBuilder.UniqueInstances.put(
				aPrimitiveFactory,
				new AspectJBuilder(aPrimitiveFactory));
		}
		return (AspectJBuilder) AspectJBuilder.UniqueInstances
			.get(aPrimitiveFactory);
	}

	private AspectJBuilder(IPrimitiveFactory aPrimitiveFactory) {
		super(aPrimitiveFactory);
	}
	protected Entity createEntity(final IConstituentOfModel anEntity) {
		final Entity aGraphicalEntity;

		if (anEntity instanceof IAspect) {
			aGraphicalEntity =
				new Aspect(this.getPrimitiveFactory(), this, (IAspect) anEntity);
		}
		else {
			aGraphicalEntity = null;
		}

		return aGraphicalEntity;
	}
	protected Element createElement(
		final IConstituentOfModel anEntity,
		final IConstituentOfEntity anElement) {

		final Element aGraphicalElement;

		if (anElement instanceof IAdvice) {
			aGraphicalElement =
				new Advice(this.getPrimitiveFactory(), (IAdvice) anElement);
		}
		else if (anElement instanceof IPointcut) {
			aGraphicalElement =
				new Pointcut(this.getPrimitiveFactory(), (IPointcut) anElement);
		}
		else if (anElement instanceof IInterTypeMethod) {
			aGraphicalElement =
				new InterTypeMethod(
					this.getPrimitiveFactory(),
					(IInterTypeMethod) anElement);
		}
		else if (anElement instanceof IInterTypeDeclareParents) {
			aGraphicalElement =
				new InterTypeDeclareParents(
					this.getPrimitiveFactory(),
					(IInterTypeDeclareParents) anElement);
		}
		else if (anElement instanceof IInterTypeField) {
			aGraphicalElement =
				new InterTypeField(
					this.getPrimitiveFactory(),
					(IInterTypeField) anElement);
		}
		else if (anElement instanceof IInterTypeConstructor) {
			aGraphicalElement =
				new InterTypeConstructor(
					this.getPrimitiveFactory(),
					(IInterTypeConstructor) anElement);
		}
		else {
			aGraphicalElement = null;
		}

		return aGraphicalElement;
	}
}
