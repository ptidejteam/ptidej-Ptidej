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

import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IMethod;
import padl.motif.IDesignMotifModel;
import padl.motif.models.StructuralMotifModel;
import util.multilingual.MultilingualManager;

public class Proxy extends StructuralMotifModel implements Cloneable,
		IDesignMotifModel {
	private static final char[] PROXY = "Proxy".toCharArray();
	private static final char[] REAL_SUBJECT = "realSubject".toCharArray();
	private static final char[] REQUEST = "request".toCharArray();
	private static final long serialVersionUID = -4494440619154091864L;
	private static final char[] STRING = "RealSubject".toCharArray();
	private static final char[] SUBJECT = "Subject".toCharArray();

	//	public static void main(final String[] args)
	//			throws CloneNotSupportedException, ModelDeclarationException {
	//		final Proxy proxy = new Proxy();
	//
	//		// I generate the constraints associated with this padl.pattern.
	//		final PtidejSolverAC4ConstraintGenerator constraintGenerator =
	//			new PtidejSolverAC4ConstraintGenerator();
	//		// I generate this padl.pattern as a domain for the constraints.
	//		final PtidejSolver2AC4DomainGenerator domainGenerator =
	//			new PtidejSolver2AC4DomainGenerator();
	//
	//		proxy.generate(constraintGenerator);
	//		ProxyConsole
	//			.getInstance()
	//			.normalOutput()
	//			.println(constraintGenerator.getCode());
	//		ProxyConsole.getInstance().normalOutput().println("----");
	//		proxy.walk(domainGenerator);
	//		ProxyConsole
	//			.getInstance()
	//			.normalOutput()
	//			.println(domainGenerator.getResult());
	//	}

	// public List compare(final IPatternModel aPattern) {
	// final List solutions = super.compare(aPattern);
	//
	// if (solutions.size() != 1) {
	// return solutions;
	// }
	//
	// final Map matched = (HashMap) solutions.get(0);
	// solutions.clear();
	// final Iterator iterator = ((List) matched.get("Subject")).iterator();
	// while (iterator.hasNext()) {
	// final IClass currentClass = (IClass) iterator.next();
	// final List tmpVector = new ArrayList();
	// final List tmpVector2 = new ArrayList();
	// tmpVector2.add(currentClass);
	// final Map currentSolution = new HashMap();
	// currentSolution.put("Subject", tmpVector2);
	//
	// Iterator iterator2 = ((List) matched.get("Proxy")).iterator();
	// while (iterator2.hasNext()) {
	// final IClass tempCurrentClass = (IClass) iterator2.next();
	// if (tempCurrentClass
	// .listOfInheritedEntities()
	// .contains(currentClass)) {
	//
	// tmpVector.add(tempCurrentClass);
	// }
	// }
	// if (tmpVector.size() > 0) {
	// currentSolution.put("Proxy", tmpVector);
	// }
	//
	// tmpVector.clear();
	// iterator2 = ((List) matched.get("RealSubject")).iterator();
	// while (iterator2.hasNext()) {
	// final IClass tempCurrentClass = (IClass) iterator2.next();
	// if (tempCurrentClass
	// .listOfInheritedEntities()
	// .contains(currentClass)) {
	//
	// tmpVector.add(tempCurrentClass);
	// }
	// }
	// if (tmpVector.size() > 0) {
	// currentSolution.put("RealSubject", tmpVector);
	// }
	// if (currentSolution.size() == listOfConstituents().size()) {
	// solutions.add(currentSolution);
	// }
	// }
	//
	// return solutions;
	// }

	public Proxy() {
		super(Proxy.PROXY);

		final IClass aSubject =
			this.getFactory().createClass(Proxy.SUBJECT, Proxy.SUBJECT);
		final IMethod aSubjectMethod =
			this.getFactory().createMethod(Proxy.REQUEST, Proxy.REQUEST);
		aSubject.addConstituent(aSubjectMethod);

		final IClass aRealSubject =
			this.getFactory().createClass(Proxy.STRING, Proxy.STRING);
		aRealSubject.addInheritedEntity(aSubject);
		final IMethod aRealSubjectMethod =
			this.getFactory().createMethod(Proxy.REQUEST, Proxy.REQUEST);
		aRealSubject.addConstituent(aRealSubjectMethod);

		final IClass aProxy =
			this.getFactory().createClass(Proxy.PROXY, Proxy.PROXY);
		aProxy.addInheritedEntity(aSubject);
		final IAssociation associationProxyRealSubject =
			this.getFactory().createAssociationRelationship(
				Proxy.REAL_SUBJECT,
				aRealSubject,
				1);
		aProxy.addConstituent(associationProxyRealSubject);
		final IMethod aProxyMethod =
			this.getFactory().createMethod(Proxy.REQUEST, Proxy.REQUEST);
		final IDelegatingMethod aPDelegatingMethod =
			this.getFactory().createDelegatingMethod(
				Proxy.REQUEST,
				associationProxyRealSubject,
				aRealSubjectMethod);
		aPDelegatingMethod.attachTo(aProxyMethod);
		aProxy.addConstituent(aPDelegatingMethod);

		aRealSubjectMethod.attachTo(aSubjectMethod);
		aProxyMethod.attachTo(aSubjectMethod);

		this.addConstituent(aSubject);
		this.addConstituent(aRealSubject);
		this.addConstituent(aProxy);
	}

	public String getIntent() {
		return MultilingualManager.getString("INTENT", Proxy.class);
	}

	public char[] getName() {
		return Proxy.PROXY;
	}
}
