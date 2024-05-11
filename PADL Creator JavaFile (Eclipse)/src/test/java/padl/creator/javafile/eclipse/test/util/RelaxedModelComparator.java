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
import org.junit.Assert;
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
import padl.test.helper.ModelComparator;

public class RelaxedModelComparator extends ModelComparator {
	public RelaxedModelComparator(final IAbstractModel anAbstractModel) {
		super(anAbstractModel);
	}

	protected void compare(
		final IConstituent aConstituent,
		final IConstituent anotherConstituent) {

		if (aConstituent instanceof IField) {
			Assert.assertEquals(
				aConstituent.getDisplayID(),
				anotherConstituent.getDisplayID());
			Assert.assertEquals(
				aConstituent.getDisplayName(),
				anotherConstituent.getDisplayName());
			Assert.assertEquals(
				aConstituent.getDisplayPath(),
				anotherConstituent.getDisplayPath());
			// I disable this test because the PADLCreatorJavaFile
			// may not have the correct type name (wrong package)
			// if this type is a Ghost.
			// TODO: Enable this test when the type is not a ghost
			// Assert.assertEquals(
			// ((IField) aConstituent).getDisplayType(),
			// ((IField) anotherConstituent).getDisplayType());
			final String aFieldTypeName =
				((IField) aConstituent).getDisplayTypeName();
			final String anotherFieldTypeName =
				((IField) anotherConstituent).getDisplayTypeName();

			/*
			 * System.out.println("Field type :" + ((IField)
			 * aConstituent).getDisplayID
			 * ()+" "+aFieldTypeName+" another constituent "
			 * +anotherFieldTypeName); System.out.println();
			 */

			Assert.assertEquals(
				aFieldTypeName.substring(Math.max(
					aFieldTypeName.lastIndexOf('.'),
					0)),
				anotherFieldTypeName.substring(Math.max(
					anotherFieldTypeName.lastIndexOf('.'),
					0)));
			Assert.assertEquals(
				aConstituent.getVisibility(),
				anotherConstituent.getVisibility());
		}
		else if (aConstituent instanceof IConstructor) {

			// I delete this test because padl .class take interface methods as
			// abstract...
			//	final int v1 = aConstituent.getVisibility();
			//	final int v2 = anotherConstituent.getVisibility();

			// Assert.assertEquals(aConstituent.getVisibility(),
			// anotherConstituent.getVisibility());

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
				/*
				 * System.out.println("Return Type constituent " +
				 * ((IMethod)aConstituent
				 * ).getDisplayName()+" : "+aReturnTypeName
				 * +" another constituent "+anotherReturnTypeName );
				 * System.out.println();
				 */
				Assert.assertEquals(aReturnTypeName.substring(aReturnTypeName
					.lastIndexOf('.') + 1), anotherReturnTypeName
					.substring(anotherReturnTypeName.lastIndexOf('.') + 1));

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
			if (anotherMethod == null) {// relaxe finding corresponding
				// method
				final Iterator iterator =
					anotherContainer
						.getIteratorOnConstituents(IConstructor.class);
				while (iterator.hasNext() && anotherMethod == null) {
					final IConstructor tempMethod =
						(IConstructor) iterator.next();

					// I delete this test because padl .class take interface
					// methods as
					// abstract...
					// if (tempMethod.getVisibility() == method.getVisibility()

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
							/*
							 * System.out.println(" Param constituent "+param.getDisplayID
							 * ()+" "+param.getDisplayName()+" "+
							 * param.getType().getDisplayID());
							 * System.out.println(" anotherParam constituent "+
							 * anotherParam
							 * .getDisplayID()+anotherParam.getDisplayName
							 * ()+" "+ anotherParam.getType().getDisplayID());
							 * System.out.println();
							 */
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
							anotherMethod = tempMethod;
						}
						// anotherMethod = tempMethod;
					}
				}
			}
			/*
			 * if(anotherMethod!=null){
			 * System.out.println("method constituent :"
			 * +method.getDisplayID()+" another constituent "
			 * +anotherMethod.getDisplayID()); }else{
			 * System.out.println("method constituent :"
			 * +method.getDisplayID()+" another constituent null why?"); }
			 * System.out.println();
			 */

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
			/*
			 * if (!(anotherGhost instanceof IGhost)){ System.out.println(""); }
			 */
			if (anotherGhost == null) {
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
					}
				}
			}
			/*
			 * if(anotherGhost==null){ anotherGhost=ghost;
			 * System.out.println("Ghost :" +ghost.getDisplayID());
			 * System.out.println(); }
			 */
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
					}
				}
			}
			/*
			 * if(anotherGhost==null){ anotherGhost=ghost;
			 * System.out.println("Ghost :" +ghost.getDisplayID());
			 * System.out.println(); }
			 */
			return anotherMemberGhost;

		}
		else {
			return super.getRelatedConstituentInOtherModel(
				aConstituent,
				anotherContainer);
		}
	}
}
