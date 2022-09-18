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
package padl.creator.javafile.eclipse.test.util;

import java.util.Arrays;
import java.util.Iterator;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.IAbstractModel;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentOfOperation;
import padl.kernel.IConstructor;
import padl.kernel.IContainer;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.IMemberGhost;
import padl.kernel.IMethod;
import padl.kernel.IParameter;

public class RelaxedModelComparatorReporter extends ModelComparatorReporter {
	public RelaxedModelComparatorReporter(final IAbstractModel anAbstractModel) {
		super(anAbstractModel);
	}

	protected void compare(
		final IConstituent aConstituent,
		final IConstituent anotherConstituent) {

		if (aConstituent instanceof IField) {
			// are IDs and name checks necessary? because another field is
			// gotten by ID
			if (!aConstituent.getDisplayID().equals(
				anotherConstituent.getDisplayID())) {
				this.writer
					.println(" Differences between padl fields IDs :  Constituent "
							+ aConstituent.getDisplayID()
							+ "  anotherConstituent "
							+ anotherConstituent.getDisplayID());

				return;
			}

			if (!aConstituent.getDisplayName().equals(
				anotherConstituent.getDisplayName())) {
				this.writer
					.println(" Differences between padl fields names :  Constituent "
							+ aConstituent.getDisplayName()
							+ "  anotherConstituent "
							+ anotherConstituent.getDisplayName());

				return;
			}
			if (!aConstituent.getDisplayPath().equals(
				anotherConstituent.getDisplayPath())) {
				this.writer
					.println(" Differences between padl fields paths :  Constituent "
							+ aConstituent.getDisplayPath()
							+ "  anotherConstituent "
							+ anotherConstituent.getDisplayPath());

				return;
			}

			if (!((IField) aConstituent).getDisplayTypeName().equals(
				((IField) anotherConstituent).getDisplayTypeName())) {
				this.writer
					.println(" Differences between padl fields types ( check if names after last . are same) :  Constituent "
							+ ((IField) aConstituent).getDisplayTypeName()
							+ "  anotherConstituent "
							+ ((IField) anotherConstituent).getDisplayTypeName());

			}

			if (aConstituent.getVisibility() != anotherConstituent
				.getVisibility()) {
				this.writer
					.println(" Differences between padl fields visibility :  Constituent "
							+ aConstituent.getVisibility()
							+ "  anotherConstituent "
							+ anotherConstituent.getVisibility());

				return;
			}
		}
		else if (aConstituent instanceof IConstructor) {

			if (aConstituent.getVisibility() != anotherConstituent
				.getVisibility()) {
				this.writer
					.println(" Differences between padl methods or constructors visibility :  Constituent "
							+ aConstituent.getVisibility()
							+ "  anotherConstituent "
							+ anotherConstituent.getVisibility());

			}

			// it seems that the following three checks are done during the
			// choice of another constructor
			// in the signature (id) comparison or in the relaxed finding
			/*
			 * Assert.assertEquals(aConstituent.getDisplayName(),
			 * anotherConstituent.getDisplayName());
			 * Assert.assertEquals(((IConstructor) aConstituent)
			 * .getNumberOfConstituents(IParameter.class), ((IConstructor)
			 * anotherConstituent) .getNumberOfConstituents(IParameter.class));
			 */
			// TODO: Test return type and parameter types, distinguishing
			// ghosts
			// for others.
			// now test only the return type and take only the end... (others
			// tests, for instance types of paramters are done during the choice
			// of the corresponding method
			// (Aminata 05/05/11)
			if (aConstituent instanceof IMethod) {

				final String aReturnTypeName =
					((IMethod) aConstituent).getDisplayReturnType();
				final String anotherReturnTypeName =
					((IMethod) anotherConstituent).getDisplayReturnType();
				if (!aReturnTypeName.equals(anotherReturnTypeName)) {
					//*************log
					this.writer
						.println(" Differences between padl methods return types visibility( check if names after last . are same):  Constituent "
								+ aReturnTypeName
								+ "  anotherConstituent "
								+ anotherReturnTypeName);

				}

			}

			// } else if (aConstituent instanceof IGhost) {
			// already done , I think, during the choice Aminata 06/05/11
			// Assert.assertEquals(aConstituent.getDisplayName(),
			// anotherConstituent.getDisplayName());

			// In relaxe comparator, we will not check in deeper the methods
			// constituents
			// Some checkings on them will be done during another constructor
			// searching
		}
		else if (!(aConstituent instanceof IConstituentOfOperation
				|| aConstituent instanceof IGhost || aConstituent instanceof IMemberGhost)) {
			super.compare(aConstituent, anotherConstituent);
		}
	}

	protected IConstituent getRelatedConstituentInOtherModel(
		final IConstituent aConstituent,
		final IContainer anotherContainer) {

		if (aConstituent instanceof IConstructor) {
			final IConstructor method = (IConstructor) aConstituent;
			IConstructor anotherMethod = null;
			anotherMethod =
				(IConstructor) super.getRelatedConstituentInOtherModel(
					aConstituent,
					anotherContainer);
			if (anotherMethod == null) {
				// relaxe finding corresponding method
				// log this info
				this.writer
					.println(" Another method not found by ID  :  Constituent "
							+ method.getDisplayID());

				final Iterator iterator =
					anotherContainer
						.getIteratorOnConstituents(IConstructor.class);
				while (iterator.hasNext() && anotherMethod == null) {
					final IConstructor tempMethod =
						(IConstructor) iterator.next();

					if (Arrays.equals(tempMethod.getName(), method.getName())
							&& tempMethod
								.getNumberOfConstituents(IParameter.class) == method
								.getNumberOfConstituents(IParameter.class)) {
						// check parameters here
						boolean sameParameters = true;
						final Iterator iter =
							method
								.getConcurrentIteratorOnConstituents(IParameter.class);

						while (sameParameters && iter.hasNext()) {
							// while (iter.hasNext()) {
							final IParameter param = (IParameter) iter.next();
							final IParameter anotherParam =
								(IParameter) tempMethod
									.getConstituentFromID(param.getID());

							if (anotherParam == null
									|| param.getCardinality() != anotherParam
										.getCardinality()) {
								sameParameters = false;
								break;
							}
							if (!Arrays.equals(
								param.getType().getID(),
								anotherParam.getType().getID())) {
								if (param.getType() instanceof IGhost
										|| param.getType() instanceof IMemberGhost) {
									if (!Arrays.equals(param
										.getType()
										.getName(), anotherParam
										.getType()
										.getName())) {
										sameParameters = false;
										break;
									}
								}

							}
						}
						if (sameParameters) {
							//*************log
							this.writer
								.println(" Another method found by relaxe finding  :  anotherConstituent "
										+ tempMethod.getDisplayID());
							anotherMethod = tempMethod;
						}

					}
				}
			}
			return anotherMethod;
		}
		else if (aConstituent instanceof IGhost) {
			final IGhost ghost = (IGhost) aConstituent;
			// padl .class,
			IFirstClassEntity anotherGhost = null;
			anotherGhost =
				(IFirstClassEntity) super.getRelatedConstituentInOtherModel(
					aConstituent,
					anotherContainer);

			if (anotherGhost == null) {
				// relaxe finding corresponding method
				// log this info
				this.writer
					.println(" Another ghost not found by ID  :  Constituent "
							+ ghost.getDisplayID());

				final Iterator iterator =
					((IAbstractLevelModel) anotherContainer)
						.getIteratorOnTopLevelEntities();
				while (iterator.hasNext() && anotherGhost == null) {
					final IFirstClassEntity tempFirstClassEntity =
						(IFirstClassEntity) iterator.next();
					if (tempFirstClassEntity instanceof IGhost
							&& tempFirstClassEntity.getVisibility() == ghost
								.getVisibility()
							&& Arrays.equals(
								tempFirstClassEntity.getName(),
								ghost.getName())) {

						anotherGhost = tempFirstClassEntity;
						this.writer
							.println(" Another ghost found by relaxe finding  :  anotherConstituent "
									+ anotherGhost.getDisplayID());
					}
				}
			}
			else if (!(anotherGhost instanceof IGhost)) {
				//*************log
				this.writer
					.println(" Another entity found by ID but it is not a ghost :  Constituent "
							+ ghost.getDisplayID()
							+ " another entity "
							+ anotherGhost.getClass().getName()
							+ " id "
							+ anotherGhost);
			}

			return anotherGhost;

		}
		else if (aConstituent instanceof IMemberGhost) {
			final IMemberGhost memberGhost = (IMemberGhost) aConstituent;
			IFirstClassEntity anotherMemberGhost = null;
			anotherMemberGhost =
				(IMemberGhost) super.getRelatedConstituentInOtherModel(
					aConstituent,
					anotherContainer);
			if (anotherMemberGhost == null) {
				//*************log
				this.writer
					.println(" Another member ghost not found by ID  :  Constituent "
							+ memberGhost.getDisplayID());
				final Iterator iterator =
					((IFirstClassEntity) anotherContainer)
						.getConcurrentIteratorOnConstituents(IFirstClassEntity.class);
				while (iterator.hasNext() && anotherMemberGhost == null) {
					final IFirstClassEntity tempFirstClassEntity =
						(IFirstClassEntity) iterator.next();
					if (tempFirstClassEntity instanceof IMemberGhost
							&& tempFirstClassEntity.getVisibility() == memberGhost
								.getVisibility()
							&& Arrays.equals(
								tempFirstClassEntity.getName(),
								memberGhost.getName())) {

						anotherMemberGhost = tempFirstClassEntity;
						//*************log
						this.writer
							.println(" Another ghost found by relaxe finding  :  anotherConstituent "
									+ anotherMemberGhost.getDisplayID());
					}
				}
			}
			else if (!(anotherMemberGhost instanceof IMemberGhost)) {
				//*************log
				this.writer
					.println(" Another entity found by ID but it is not a member ghost :  Constituent "
							+ memberGhost.getDisplayID()
							+ " another entity "
							+ anotherMemberGhost.getClass().getName()
							+ " id "
							+ anotherMemberGhost);
			}

			return anotherMemberGhost;

		}
		else {
			return super.getRelatedConstituentInOtherModel(
				aConstituent,
				anotherContainer);
		}
	}
}
