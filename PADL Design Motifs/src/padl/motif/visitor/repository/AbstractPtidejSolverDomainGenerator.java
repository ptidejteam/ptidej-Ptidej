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
package padl.motif.visitor.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import padl.kernel.IClass;
import padl.kernel.IConstituent;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import util.io.ProxyConsole;

abstract class AbstractPtidejSolverDomainGenerator {
	protected static final Iterator EMPTY_ITERATOR = new ArrayList(0)
		.iterator();
	private static final String PREFIX = "pe";

	public static char[] convertToClaireIdentifier(
		final char[] aFullyQualifiedName) {

		final StringBuffer buffer = new StringBuffer();
		buffer.append(AbstractPtidejSolverDomainGenerator.PREFIX);
		// Yann 2002/09/24: Simplification!
		// I replace the fully qualified name of the entity by
		// its hascode: This is simpler and this saves space!
		buffer.append(aFullyQualifiedName.hashCode());
		//	buffer.append(Misc.stripAndCapQualifiedName(fullyQualifiedName));
		//	buffer.replace(
		//		PtidejSolverDomainGenerator.PREFIX.length(),
		//		PtidejSolverDomainGenerator.PREFIX.length() + 1,
		//		new String(
		//			new char[] {
		//				Character.toUpperCase(
		//					buffer.charAt(PtidejSolverDomainGenerator.PREFIX.length()))}));

		return buffer.toString().toCharArray();
	}
	public static List directSubclassesOf(
		final IFirstClassEntity superPEntity,
		final IFirstClassEntity[] pEntities) {

		final List subclasses = new ArrayList();

		// I am a subclass of myself.
		subclasses.add(superPEntity);

		// I look for my other direct subclasses.
		for (int i = 0; i < pEntities.length; i++) {
			if (AbstractPtidejSolverDomainGenerator.isSub(
				(IFirstClassEntity) pEntities[i],
				superPEntity)) {
				subclasses.add(pEntities[i]);
			}
		}

		return subclasses;
	}
	private static boolean isSub(
		final IClass pClass,
		final IFirstClassEntity superPEntity) {
		final List supers = new ArrayList();
		Iterator iterator = pClass.getIteratorOnInheritedEntities();
		while (iterator.hasNext()) {
			supers.add(iterator.next());
		}
		iterator = pClass.getIteratorOnImplementedInterfaces();
		while (iterator.hasNext()) {
			supers.add(iterator.next());
		}

		if (supers.contains(superPEntity)) {
			return true;
		}

		boolean isSub = false;
		if (supers.size() > 0) {
			iterator = supers.iterator();
			while (iterator.hasNext()) {
				isSub |=
					AbstractPtidejSolverDomainGenerator.isSub(
						(IFirstClassEntity) iterator.next(),
						superPEntity);
			}
		}

		return isSub;
	}
	private static boolean isSub(
		final IFirstClassEntity pEntity,
		final IFirstClassEntity superPEntity) {

		if (pEntity instanceof IClass) {
			return AbstractPtidejSolverDomainGenerator.isSub(
				(IClass) pEntity,
				superPEntity);
		}
		return AbstractPtidejSolverDomainGenerator.isSub(
			(IInterface) pEntity,
			superPEntity);
	}
	private static boolean isSub(
		final IInterface pInterface,
		final IFirstClassEntity superPEntity) {

		final Iterator iterator = pInterface.getIteratorOnInheritedEntities();
		boolean isSub = false;
		if (iterator.hasNext()) {
			while (iterator.hasNext()) {
				final IFirstClassEntity firstClassEntity =
					(IFirstClassEntity) iterator.next();
				if (firstClassEntity.equals(superPEntity)) {
					return true;
				}
				else {
					isSub |=
						AbstractPtidejSolverDomainGenerator.isSub(
							(IFirstClassEntity) iterator.next(),
							superPEntity);
				}
			}
		}
		return isSub;
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
}
