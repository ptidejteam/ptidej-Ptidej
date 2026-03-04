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
package padl.test.helper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Stack;
import org.junit.Assert;
import padl.kernel.IAbstractModel;
import padl.kernel.IAggregation;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IComposition;
import padl.kernel.IConstituent;
import padl.kernel.IConstructor;
import padl.kernel.IContainer;
import padl.kernel.IContainerAggregation;
import padl.kernel.IContainerComposition;
import padl.kernel.ICreation;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IElement;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGetter;
import padl.kernel.IGhost;
import padl.kernel.IInterface;
import padl.kernel.IMemberClass;
import padl.kernel.IMemberEntity;
import padl.kernel.IMemberGhost;
import padl.kernel.IMemberInterface;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IPackage;
import padl.kernel.IPackageDefault;
import padl.kernel.IPackageGhost;
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.visitor.IWalker;
import util.io.ProxyConsole;

public class ModelComparator implements IWalker {
	private final IAbstractModel anotherAbstractModel;
	private final Stack stackOfEnclosingConstituentsOfAModel = new Stack();
	private final Stack stackOfEnclosingConstituentsOfAnotherModel =
		new Stack();

	public ModelComparator(final IAbstractModel anAbstractModel) {
		this.anotherAbstractModel = anAbstractModel;
	}

	public void close(final IAbstractModel anAbstractModel) {
	}

	public final void close(final IClass aClass) {
		this.close((IFirstClassEntity) aClass);
	}

	public final void close(final IConstructor aConstructor) {
		this.close((IElement) aConstructor);
	}

	public final void close(final IDelegatingMethod aDelegatingMethod) {
		this.close((IElement) aDelegatingMethod);
	}

	private void close(final IElement anElement) {
		this.stackOfEnclosingConstituentsOfAModel.pop();
		this.stackOfEnclosingConstituentsOfAnotherModel.pop();
	}

	private void close(final IFirstClassEntity anEntity) {
		this.stackOfEnclosingConstituentsOfAModel.pop();
		this.stackOfEnclosingConstituentsOfAnotherModel.pop();
	}

	public final void close(final IGetter aGetter) {
		this.close((IElement) aGetter);
	}

	public final void close(final IGhost aGhost) {
		this.close((IFirstClassEntity) aGhost);
	}

	public final void close(final IInterface anInterface) {
		this.close((IFirstClassEntity) anInterface);
	}

	public final void close(final IMemberClass aMemberClass) {
		this.close((IFirstClassEntity) aMemberClass);
	}

	public final void close(final IMemberGhost aMemberGhost) {
		this.close((IFirstClassEntity) aMemberGhost);
	}

	public final void close(final IMemberInterface aMemberInterface) {
		this.close((IFirstClassEntity) aMemberInterface);
	}

	public final void close(final IMethod aMethod) {
		this.close((IElement) aMethod);
	}

	public final void close(final IPackage aPackage) {
	}

	public final void close(final IPackageDefault aPackage) {
	}
	public final void close(final IPackageGhost aPackageGhost) {
	}

	public final void close(final ISetter aSetter) {
		this.close((IElement) aSetter);
	}

	protected void compare(
		final IConstituent aConstituent,
		final IConstituent anotherConstituent) {

		/*
		 * System.out.print("Comparing:\n\t");
		 * System.out.println(aConstituent.getName());
		 * System.out.print("with:\n\t");
		 * System.out.println(anotherConstituent.getName());
		 */

		final Class constituentClass = aConstituent.getClass();
		final Field[] constituentFields = constituentClass.getDeclaredFields();
		for (int i = 0; i < constituentFields.length; i++) {
			final Field field = constituentFields[i];
			field.setAccessible(true);

			if (!field
				.getType()
				.getName()
				.startsWith("padl.kernel.impl.GenericContainer")) {

				try {
					if (field.getType().isArray()) {
						final int currentLength =
							Array.getLength(field.get(aConstituent));
						final int anotherLength =
							Array.getLength(field.get(anotherConstituent));

						Assert.assertEquals(currentLength, anotherLength);

						for (int j = 0; j < currentLength; j++) {
							final Object currentObject =
								Array.get(field.get(aConstituent), j);
							final Object anotherObject =
								Array.get(field.get(anotherConstituent), j);
							Assert.assertEquals(currentObject, anotherObject);
						}
					}
					else {
						final Object aConstituentAttributeValue =
							field.get(aConstituent);
						final Object anotherConstituentAttributeValue =
							field.get(anotherConstituent);

						if (aConstituentAttributeValue instanceof IConstituent
								&& anotherConstituentAttributeValue instanceof IConstituent) {

							ProxyConsole
								.getInstance()
								.debugOutput()
								.print("Comparing: ");
							ProxyConsole
								.getInstance()
								.debugOutput()
								.print(
									((IConstituent) aConstituentAttributeValue)
										.getName());
							ProxyConsole
								.getInstance()
								.debugOutput()
								.print(" with: ");
							ProxyConsole
								.getInstance()
								.debugOutput()
								.println(
									((IConstituent) anotherConstituentAttributeValue)
										.getName());
							this
								.compare(
									(IConstituent) aConstituentAttributeValue,
									(IConstituent) anotherConstituentAttributeValue);
						}
						// Yann 2013/05/23: Sensible?
						// I don't believe that this test below is necessary
						// because all constituent will be compared one by one
						// using this visitor, so why also test their fields?
						//	else {
						//		Assert.assertEquals(field.get(aConstituent), field
						//			.get(anotherConstituent));
						//	}
					}
				}
				catch (final IllegalArgumentException e) {
					e.printStackTrace();
				}
				catch (final IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void compareWithSanityCheck(
		final IConstituent aConstituent,
		final IConstituent anotherConstituent) {

		Assert.assertNotNull("A constituent should not be null", aConstituent);
		Assert.assertNotNull(
			"Another constituent should not be null (in comparison to "
					+ aConstituent.toString() + ')',
			anotherConstituent);
		Assert.assertEquals(
			aConstituent.getClass(),
			anotherConstituent.getClass());

		this.compare(aConstituent, anotherConstituent);
	}

	public String getName() {
		return "ModelComparator";
	}

	protected IConstituent getRelatedConstituentInOtherModel(
		final IConstituent aConstituent,
		final IContainer anotherContainer) {

		if (anotherContainer instanceof IAbstractModel) {
			return (IConstituent) ((IAbstractModel) anotherContainer)
				.getTopLevelEntityFromID(aConstituent.getID());
		}
		else {
			final IConstituent constituent =
				anotherContainer.getConstituentFromID(aConstituent.getID());
			if (constituent == null) {
				// Yann 2011/09/06: Different ID expected!
				// The Java parser does not have as much type information as
				// does the class file parser, so it is expected that, where
				// the class file parser can specify a type such as int or
				// java.lang.Object for a parameter, the Java parser cannot do
				// better than "java.lang.Object". In such case, we use the 
				// name of the element...
				return anotherContainer.getConstituentFromName(aConstituent
					.getName());
			}
			return constituent;
		}
	}

	public Object getResult() {
		return null;
	}

	public final void open(final IAbstractModel anAbstractModel) {
	}

	public final void open(final IClass aClass) {
		this.open((IFirstClassEntity) aClass);
	}

	public final void open(final IConstructor aConstructor) {
		this.open((IElement) aConstructor);
	}

	public final void open(final IDelegatingMethod aDelegatingMethod) {
		this.open((IElement) aDelegatingMethod);
	}

	protected void open(final IElement anElement) {
		this.stackOfEnclosingConstituentsOfAModel.push(anElement);
		this.stackOfEnclosingConstituentsOfAnotherModel.push(this
			.getRelatedConstituentInOtherModel(
				anElement,
				(IContainer) this.stackOfEnclosingConstituentsOfAnotherModel
					.peek()));

		this.compareWithSanityCheck(
			(IConstituent) this.stackOfEnclosingConstituentsOfAModel.peek(),
			(IConstituent) this.stackOfEnclosingConstituentsOfAnotherModel
				.peek());
	}

	protected void open(final IFirstClassEntity anEntity) {
		this.stackOfEnclosingConstituentsOfAModel.push(anEntity);
		//030611- Aminata S. - This condition to handle member entities
		if (anEntity instanceof IMemberEntity) {
			this.stackOfEnclosingConstituentsOfAnotherModel
				.push(this
					.getRelatedConstituentInOtherModel(
						anEntity,
						(IContainer) this.stackOfEnclosingConstituentsOfAnotherModel
							.peek()));
		}
		else {
			this.stackOfEnclosingConstituentsOfAnotherModel.push(this
				.getRelatedConstituentInOtherModel(
					anEntity,
					this.anotherAbstractModel));
		}

		this.compareWithSanityCheck(
			(IConstituent) this.stackOfEnclosingConstituentsOfAModel.peek(),
			(IConstituent) this.stackOfEnclosingConstituentsOfAnotherModel
				.peek());
	}

	public final void open(final IGetter aGetter) {
		this.open((IElement) aGetter);
	}

	public final void open(final IGhost aGhost) {
		this.open((IFirstClassEntity) aGhost);
	}

	public final void open(final IInterface anInterface) {
		this.open((IFirstClassEntity) anInterface);
	}

	public final void open(final IMemberClass aMemberClass) {
		this.open((IFirstClassEntity) aMemberClass);
	}

	public final void open(final IMemberGhost aMemberGhost) {
		this.open((IFirstClassEntity) aMemberGhost);
	}

	public final void open(final IMemberInterface aMemberInterface) {
		this.open((IFirstClassEntity) aMemberInterface);
	}

	public final void open(final IMethod aMethod) {
		this.open((IElement) aMethod);
	}

	public final void open(final IPackage aPackage) {
	}

	public final void open(final IPackageDefault aPackage) {
	}

	public final void open(final IPackageGhost aPackageGhost) {
	}

	public final void open(final ISetter aSetter) {
		this.open((IElement) aSetter);
	}

	public final void reset() {
		this.stackOfEnclosingConstituentsOfAModel.clear();
		this.stackOfEnclosingConstituentsOfAnotherModel.clear();
	}

	public final void unknownConstituentHandler(
		final String aCalledMethodName,
		final IConstituent aConstituent) {

		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(this.getClass().getName());
		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(" does not know what to do for \"");
		ProxyConsole.getInstance().debugOutput().print(aCalledMethodName);
		ProxyConsole.getInstance().debugOutput().print("\" (");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(aConstituent.getDisplayID());
		ProxyConsole.getInstance().debugOutput().println(')');
	}

	public final void visit(final IAggregation anAggregation) {
		this.visit((IElement) anAggregation);
	}

	public final void visit(final IAssociation anAssociation) {
		this.visit((IElement) anAssociation);
	}

	public final void visit(final IComposition aComposition) {
		this.visit((IElement) aComposition);
	}

	public final void visit(final IContainerAggregation aContainerAggregation) {
		this.visit((IElement) aContainerAggregation);
	}

	public final void visit(final IContainerComposition aContainerComposition) {
		this.visit((IElement) aContainerComposition);
	}

	public final void visit(final ICreation aCreation) {
		this.visit((IElement) aCreation);
	}

	private void visit(final IElement anElement) {
		this.stackOfEnclosingConstituentsOfAModel.push(anElement);
		this.stackOfEnclosingConstituentsOfAnotherModel
			.push(((IContainer) this.stackOfEnclosingConstituentsOfAnotherModel
				.peek()).getConstituentFromID(anElement.getID()));

		this.compareWithSanityCheck(
			(IConstituent) this.stackOfEnclosingConstituentsOfAModel.peek(),
			(IConstituent) this.stackOfEnclosingConstituentsOfAnotherModel
				.peek());

		this.stackOfEnclosingConstituentsOfAModel.pop();
		this.stackOfEnclosingConstituentsOfAnotherModel.pop();
	}

	public final void visit(final IField aField) {
		this.visit((IElement) aField);
	}

	public final void visit(final IMethodInvocation aMethodInvocation) {
		// Yann 2011/09/06: Silly!
		// We definitely cannot expect the model from the Java parser
		// to have the same set of method invocation as the model
		// from the class file parser.
		// this.visit((IElement) aMethodInvocation);
	}

	public final void visit(final IParameter aParameter) {
		// Yann 2011/09/06: Silly!
		// We definitely cannot expect the model from the Java parser
		// to have the same set of method invocation as the model
		// from the class file parser.
		// this.visit((IElement) aParameter);
	}

	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		// Do nothing for uninteresting primitive types.
	}

	public final void visit(final IUseRelationship aUseRelationship) {
		this.visit((IElement) aUseRelationship);
	}
}
