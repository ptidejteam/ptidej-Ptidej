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
package ptidej.ui.kernel.builder.test;

import java.util.Iterator;

import org.junit.Assert;

import padl.aspectj.kernel.IAdvice;
import padl.aspectj.kernel.IAspect;
import padl.aspectj.kernel.IAspectElement;
import padl.aspectj.kernel.IInterTypeConstructor;
import padl.aspectj.kernel.IInterTypeDeclareParents;
import padl.aspectj.kernel.IInterTypeField;
import padl.aspectj.kernel.IInterTypeMethod;
import padl.aspectj.kernel.IPointcut;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentOfModel;
import ptidej.ui.kernel.Advice;
import ptidej.ui.kernel.Aspect;
import ptidej.ui.kernel.InterTypeConstructor;
import ptidej.ui.kernel.InterTypeDeclareParents;
import ptidej.ui.kernel.InterTypeField;
import ptidej.ui.kernel.InterTypeMethod;
import ptidej.ui.kernel.Pointcut;

/**
 * @author Jean-Yves Guyomarc'h
 * @since 2005/08/16
 */
public class AspectJBuilderTest extends PrimitiveBuilder {
	public AspectJBuilderTest(final String aName) {
		super(aName);
	}

	public void testTypematching() {
		final Iterator<IConstituent> iterator = this.getCodeLevelModel()
				.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			final IConstituent c = iterator.next();
			if (c instanceof IAspect) {
				Assert.assertEquals("Test type building: Aspect", true,
						this.getBuilder().getEntity(
								(IConstituentOfModel) c) instanceof Aspect);

				final Iterator<IConstituent> iterAspect = ((IAspect) c)
						.getIteratorOnConstituents();
				while (iterAspect.hasNext()) {
					final IConstituent aElement = iterAspect.next();
					if (aElement instanceof IAdvice) {
						Assert.assertTrue("Test type building: Advice",
								this.getBuilder().getElement((IAspect) c,
										(IAspectElement) aElement) instanceof Advice);
					}
					if (aElement instanceof IPointcut) {
						Assert.assertTrue("Test type building: Pointcut",
								this.getBuilder().getElement((IAspect) c,
										(IAspectElement) aElement) instanceof Pointcut);
					}

					if (aElement instanceof IInterTypeField) {
						Assert.assertTrue("Test type building: InterTypeField",
								this.getBuilder().getElement((IAspect) c,
										(IAspectElement) aElement) instanceof InterTypeField);
					}
					if (aElement instanceof IInterTypeMethod) {
						Assert.assertTrue("Test type building: InterTypeMethod",
								this.getBuilder().getElement((IAspect) c,
										(IAspectElement) aElement) instanceof InterTypeMethod);
					}
					else {
						if (aElement instanceof IInterTypeConstructor) {
							Assert.assertTrue(
									"Test type building: InterTypeConstructor",
									this.getBuilder().getElement((IAspect) c,
											(IAspectElement) aElement) instanceof InterTypeConstructor);

						}
					}
					if (aElement instanceof IInterTypeDeclareParents) {
						Assert.assertTrue(
								"Test type building: InterTypeDeclareParents",
								this.getBuilder().getElement((IAspect) c,
										(IAspectElement) aElement) instanceof InterTypeDeclareParents);
					}
				}
			}
			else {
				System.out.println("Non Aspect Element: " + c.getDisplayName()
						+ " -> " + c.getClass());
			}
		}
	}
}
