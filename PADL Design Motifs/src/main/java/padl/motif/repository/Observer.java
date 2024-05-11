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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import padl.kernel.Constants;
import padl.kernel.IClass;
import padl.kernel.IContainerAggregation;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import padl.kernel.IMethod;
import padl.motif.IDesignMotifModel;
import padl.motif.models.BehaviouralMotifModel;
import util.multilingual.MultilingualManager;

public class Observer extends BehaviouralMotifModel
		implements PropertyChangeListener, Cloneable, IDesignMotifModel {

	private static final char[] CONCRETE_OBSERVER = "ConcreteObserver"
			.toCharArray();
	private static final char[] CONCRETE_SUBJECT = "ConcreteSubject"
			.toCharArray();
	private static final char[] GET_STATE = "getState".toCharArray();
	private static final char[] NOTIFY = "Notify".toCharArray();
	private static final char[] OBSERVER = "Observer".toCharArray();
	private static final char[] OBSERVERS = "observers".toCharArray();
	private static final long serialVersionUID = 8984280250655412814L;
	private static final char[] STRING = "subject".toCharArray();
	private static final char[] SUBJECT = "Subject".toCharArray();
	private static final char[] UPDATE = "Update".toCharArray();

	//	public static void main(final String[] args)
	//			throws CloneNotSupportedException, ModelDeclarationException {
	//		final Observer observer = new Observer();
	//
	//		// I generate the constraints associated with this padl.pattern.
	//		final PtidejSolverAC4ConstraintGenerator constraintGenerator =
	//			new PtidejSolverAC4ConstraintGenerator();
	//		// I generate this padl.pattern as a domain for the constraints.
	//		final PtidejSolver2AC4DomainGenerator domainGenerator =
	//			new PtidejSolver2AC4DomainGenerator();
	//
	//		observer.generate(constraintGenerator);
	//		ProxyConsole
	//			.getInstance()
	//			.normalOutput()
	//			.println(constraintGenerator.getCode());
	//		ProxyConsole.getInstance().normalOutput().println("----");
	//		observer.walk(domainGenerator);
	//		ProxyConsole
	//			.getInstance()
	//			.normalOutput()
	//			.println(domainGenerator.getResult());
	//	}

	private final IMethod abMethod, acMethod;
	private final IClass aClass;
	private IClass a2Class;
	private final IInterface anInterface;

	private IDelegatingMethod aPDelegatingMethod;

	public Observer() {
		super(Observer.OBSERVER);

		// Interface Observer 
		this.anInterface = this.getFactory().createInterface(Observer.OBSERVER,
				Observer.OBSERVER);
		this.abMethod = this.getFactory().createMethod(Observer.UPDATE,
				Observer.UPDATE);
		this.anInterface.addConstituent(this.abMethod);
		this.anInterface.setPurpose(MultilingualManager
				.getString("Observer_PURPOSE", Observer.class));
		this.addConstituent(this.anInterface);

		// Association observers 
		final IContainerAggregation anAssoc = this.getFactory()
				.createContainerAggregationRelationship(Observer.OBSERVERS,
						this.anInterface, Constants.CARDINALITY_MANY);

		// Classe Subject 
		this.aClass = this.getFactory().createClass(Observer.SUBJECT,
				Observer.SUBJECT);
		this.aClass.setAbstract(true);
		this.aClass.addConstituent(anAssoc);
		this.aPDelegatingMethod = this.getFactory().createDelegatingMethod(
				Observer.NOTIFY, anAssoc, this.abMethod);
		this.aClass.addConstituent(this.aPDelegatingMethod);
		this.aClass.assumeAllInterfaces();
		this.aClass.setPurpose(MultilingualManager.getString("Subject_PURPOSE",
				Observer.class));
		this.addConstituent(this.aClass);

		// Classe Concrete Subject
		this.acMethod = this.getFactory().createMethod(Observer.GET_STATE,
				Observer.GET_STATE);
		this.a2Class = this.getFactory().createClass(Observer.CONCRETE_SUBJECT,
				Observer.CONCRETE_SUBJECT);
		this.a2Class.addInheritedEntity(this.aClass);
		this.a2Class.setPurpose(MultilingualManager
				.getString("ConcreteSubject_CLASS_PURPOSE", Observer.class));
		this.a2Class.addConstituent(this.acMethod);
		this.a2Class.assumeAllInterfaces();
		this.addConstituent(this.a2Class);

		// Assoc subject
		final IContainerAggregation a2Assoc = this.getFactory()
				.createContainerAggregationRelationship(Observer.STRING,
						this.a2Class, Constants.CARDINALITY_ONE);

		// Classe Concrete Observer
		this.aPDelegatingMethod = this.getFactory().createDelegatingMethod(
				Observer.UPDATE, a2Assoc, this.acMethod);
		this.aPDelegatingMethod.setComment(MultilingualManager
				.getString("DELEG_METHOD_COMMENT", Observer.class));
		this.aPDelegatingMethod.attachTo(this.abMethod);
		this.a2Class = this.getFactory().createClass(Observer.CONCRETE_OBSERVER,
				Observer.CONCRETE_OBSERVER);
		this.a2Class.setPurpose(MultilingualManager
				.getString("ConcreteObserver_CLASS_PURPOSE", Observer.class));
		this.a2Class.addImplementedInterface(this.anInterface);
		this.a2Class.addConstituent(a2Assoc);
		this.a2Class.addConstituent(this.aPDelegatingMethod);
		//	this.a2Class.assumeAllInterfaces();
		this.addConstituent(this.a2Class);
	}

	private void codeForSetMethod() {
		// Add code for the setMethod.
		if (this.getConstituentFromName(Observer.CONCRETE_OBSERVER) == null
				|| this.getConstituentFromName(Observer.SUBJECT) == null) {
			return;
		}

		final IContainerAggregation a2Assoc = (IContainerAggregation) ((IFirstClassEntity) this
				.getConstituentFromName(Observer.CONCRETE_OBSERVER))
				.getConstituentFromName(Observer.STRING);
		final IContainerAggregation anAssoc = (IContainerAggregation) ((IFirstClassEntity) this
				.getConstituentFromName(Observer.SUBJECT))
				.getConstituentFromName(Observer.OBSERVERS);

		final IMethod sMethod = (IMethod) a2Assoc
				.getConstituentFromID(a2Assoc.getOriginFieldName());
		final String[] codeLines = new String[] {
				"if (" + a2Assoc
						.getConstituentFromID(a2Assoc.getOriginFieldName())
						.getDisplayName() + " != null)",
				"     " + a2Assoc
						.getConstituentFromID(a2Assoc.getOriginFieldName())
						.getDisplayName() + '.'
						+ anAssoc
								.getConstituentFromID(
										a2Assoc.getOriginSetterMethodName())
								.getDisplayName()
						+ "(this);",
				a2Assoc.getConstituentFromID(a2Assoc.getOriginFieldName())
						.getDisplayName() + " = a"
						+ a2Assoc.getTargetEntity().getDisplayName() + ';',
				a2Assoc.getConstituentFromID(a2Assoc.getOriginFieldName())
						.getDisplayName()
						+ '.'
						+ anAssoc
								.getConstituentFromID(
										a2Assoc.getOriginGetterMethodName())
								.getDisplayName()
						+ "(this);" };
		sMethod.setCodeLines(codeLines);
	}

	public String getIntent() {
		return MultilingualManager.getString("INTENT", Observer.class);
	}

	public char[] getName() {
		return Observer.OBSERVER;
	}

	public void propertyChange(final PropertyChangeEvent e) {
		this.codeForSetMethod();
	}
}
