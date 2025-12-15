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
package padl.analysis.repository.systematicuml;

import com.ibm.toad.cfparse.utils.Access;

import padl.kernel.IAbstractModel;
import padl.kernel.IAggregation;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IComposition;
import padl.kernel.IConstituent;
import padl.kernel.IConstructor;
import padl.kernel.IContainerAggregation;
import padl.kernel.IContainerComposition;
import padl.kernel.ICreation;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IFactory;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGetter;
import padl.kernel.IGhost;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.IInterface;
import padl.kernel.IMemberClass;
import padl.kernel.IMemberGhost;
import padl.kernel.IMemberInterface;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IPackage;
import padl.kernel.IPackageDefault;
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.motif.IDesignMotifModel;
import padl.visitor.IWalker;
import util.io.ProxyConsole;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/05/21
 */
public class SystematicUMLEntityGenerator implements IWalker {
	private final IFactory factory;
	private boolean hasConcreteMethods;
	private boolean hasFieldAssignments;
	private boolean hasFields;
	private boolean hasInstanceMethods;
	private boolean hasMethods;
	private boolean hasPrivateNonStaticFieldsOnly;
	private boolean hasPublicStaticFinalFieldsOnly;
	private boolean isMethod;
	private final IIdiomLevelModel newIdiomLevelModel;
	private final SystematicUMLStatistics statistics;

	public SystematicUMLEntityGenerator(final IFactory aFactory,
			final IIdiomLevelModel anIdiomLevelModel,
			final SystematicUMLStatistics aStatistics) {

		this.factory = aFactory;
		this.newIdiomLevelModel = anIdiomLevelModel;
		this.statistics = aStatistics;
	}

	public void close(final IAbstractModel anAbstractModel) {
	}

	public void close(final IClass aClass) {
		final IClass newClass = this.factory.createClass(aClass.getID(),
				aClass.getName());
		this.close(aClass, newClass);
	}

	public void close(final IConstructor aConstructor) {
	}

	public void close(final IDelegatingMethod aDelegatingMethod) {
	}

	public void close(final IDesignMotifModel aPatternModel) {
	}

	private void close(final IFirstClassEntity originalEntity,
			final IFirstClassEntity newEntity) {

		final StringBuffer displayName = new StringBuffer();
		// If an entity is either an interface or an abstract class...
		// If this entity has no concrete methods but declares methods,
		// then this entity is a type.
		if (!this.hasConcreteMethods && this.hasMethods) {
			displayName.append("<<type>>\n");
			this.statistics.typeRecognized(newEntity);
		}
		// If this entity declares public static final fields only
		// (no methods), then this entity is an enumeration.
		else if (this.hasFields && this.hasPublicStaticFinalFieldsOnly
				&& !this.hasMethods) {

			displayName.append("<<enumeration>>\n");
			this.statistics.enumerationRecognized(newEntity);
		}
		// If an entity is a class...
		else if (originalEntity instanceof IClass) {
			// If a class has no instance method but has (static) methods,
			// then this class is a utility class.
			if (!this.hasInstanceMethods && this.hasMethods) {
				displayName.append("<<utility>>\n");
				this.statistics.utilityClassRecognized(newEntity);
			}
			// If a class has no field write-access,
			// then this class is a data type.
			else if (this.hasPrivateNonStaticFieldsOnly && this.hasFields
					&& !this.hasFieldAssignments
					&& !originalEntity.isAbstract()) {

				displayName.append("<<datatype>>\n");
				this.statistics.dataTypeRecognized(newEntity);
			}
			// If a class is not abstract (has no abstract method,
			// i.e., does not define operations), then this class
			// is an implementation class.
			else if (!originalEntity.isAbstract()) {
				displayName.append("<<implementation>>\n");
				this.statistics.implementationClassRecognized(newEntity);
			}
		}
		displayName.append(originalEntity.getName());
		newEntity.setName(originalEntity.getName());
		newEntity.setDisplayName(displayName.toString());
		this.newIdiomLevelModel.addConstituent(newEntity);
	}

	public void close(final IGetter aGetter) {
	}

	public void close(final IGhost aGhost) {
		this.newIdiomLevelModel.addConstituent(
				this.factory.createGhost(aGhost.getID(), aGhost.getName()));
	}

	public void close(final IInterface anInterface) {
		final IInterface newInterface = this.factory
				.createInterface(anInterface.getID(), anInterface.getName());
		this.close(anInterface, newInterface);
	}

	public void close(final IMemberClass aMemberClass) {
		this.close((IClass) aMemberClass);
	}

	public void close(final IMemberGhost aMemberGhost) {
		this.close((IClass) aMemberGhost);
	}

	public void close(final IMemberInterface aMemberInterface) {
		this.close((IInterface) aMemberInterface);
	}

	public void close(final IMethod aMethod) {
	}

	public void close(final IPackage aPackage) {
	}

	public void close(final IPackageDefault aPackage) {
	}

	public void close(final ISetter aSetter) {
	}

	public String getName() {
		return "SystematicUMLAnalisysEntityGenerator";
	}

	public Object getResult() {
		return this.newIdiomLevelModel;
	}

	private void open() {
		this.hasConcreteMethods = false;
		this.hasInstanceMethods = false;
		this.hasMethods = false;
		this.hasPublicStaticFinalFieldsOnly = true;
		this.hasPrivateNonStaticFieldsOnly = true;
		this.hasFields = false;
		this.hasFieldAssignments = false;
	}

	public void open(final IAbstractModel anAbstractModel) {
	}

	public void open(final IClass aClass) {
		this.open();
	}

	public void open(final IConstructor aConstructor) {
		this.isMethod = false;
	}

	public void open(final IDelegatingMethod aDelegatingMethod) {
		this.open((IMethod) aDelegatingMethod);
	}

	public void open(final IDesignMotifModel aPatternModel) {
	}

	public void open(final IGetter aGetter) {
		this.open((IMethod) aGetter);
	}

	public void open(final IGhost aGhost) {
		this.open();
	}

	public void open(final IInterface anInterface) {
		this.open();
	}

	public void open(final IMemberClass aMemberClass) {
		this.open();
	}

	public void open(final IMemberGhost aMemberGhost) {
		this.open();
	}

	public void open(final IMemberInterface aMemberInterface) {
		this.open();
	}

	public void open(final IMethod aMethod) {
		if (!aMethod.isAbstract()) {
			this.hasConcreteMethods = true;
		}
		if (!aMethod.isStatic()) {
			this.hasInstanceMethods = true;
		}
		this.hasMethods = true;
		if (!aMethod.getDisplayName().equals("<clinit>")) {
			this.isMethod = true;
		}
	}

	public void open(final IPackage aPackage) {
	}

	public void open(final IPackageDefault aPackage) {
	}

	public void open(final ISetter aSetter) {
		this.open((IMethod) aSetter);
	}

	public void reset() {
	}

	public final void unknownConstituentHandler(final String aCalledMethodName,
			final IConstituent aConstituent) {

		ProxyConsole.getInstance().debugOutput()
				.print(this.getClass().getName());
		ProxyConsole.getInstance().debugOutput()
				.print(" does not know what to do for \"");
		ProxyConsole.getInstance().debugOutput().print(aCalledMethodName);
		ProxyConsole.getInstance().debugOutput().print("\" (");
		ProxyConsole.getInstance().debugOutput()
				.print(aConstituent.getDisplayID());
		ProxyConsole.getInstance().debugOutput().println(')');
	}

	public void visit(final IAggregation anAggregation) {
	}

	public void visit(final IAssociation anAssociation) {
	}

	public void visit(final IComposition aComposition) {
	}

	public void visit(final IContainerAggregation aContainerAggregation) {
	}

	public void visit(final IContainerComposition aContainerComposition) {
	}

	public void visit(final ICreation aCreation) {
	}

	public void visit(final IField aField) {
		if (!aField.isPublic() || !aField.isStatic()
				|| !Access.isFinal(aField.getVisibility())) {

			this.hasPublicStaticFinalFieldsOnly = false;
		}
		if (!aField.isStatic() && !aField.isPrivate()) {
			this.hasPrivateNonStaticFieldsOnly = false;
		}
		this.hasFields = true;
	}

	public void visit(final IMethodInvocation aMethodInvocation) {
		if (this.isMethod) {
			if (aMethodInvocation.getCalledMethod() != null && aMethodInvocation
					.getCalledMethod().getDisplayName().equals("=")) {

				this.hasFieldAssignments = true;
			}
		}
	}

	public void visit(final IParameter aParameter) {
	}

	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		// Do nothing for uninteresting primitive types.
	}

	public void visit(final IUseRelationship aUse) {
	}
}
