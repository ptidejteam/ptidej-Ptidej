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
package padl.creator.aolfile.invocations;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import padl.analysis.IAnalysis;
import padl.analysis.UnsupportedSourceModelException;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.IAbstractModel;
import padl.kernel.IConstituentOfOperation;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import padl.kernel.impl.Factory;
import util.io.ProxyConsole;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/01/29
 */
@SuppressWarnings("rawtypes")
public class MethodInvocationAnalyser implements IAnalysis {
	private String cldFile;
	public String getCLDFile() {
		return this.cldFile;
	}
	public String getName() {
		return "Method invocation adder";
	}
	@SuppressWarnings("unchecked")
	public IAbstractModel invoke(final IAbstractModel anAbstractModel)
			throws UnsupportedSourceModelException {

		try {
			final IAbstractLevelModel newAbstractLevelModel =
				(IAbstractLevelModel) anAbstractModel.clone();
			final BufferedReader reader =
				new BufferedReader(new FileReader(this.cldFile));
			final List listOfModifiedMethods = new ArrayList();

			// A typical line looks like:
			// invoke  ADM_Process::checkConfiguration(IMSBoolean&)    "ADM_Process::getFileModificationTime"

			String line;
			int numberOfInvocations = 0;
			int numberOfFailures = 0;
			int firstIndex;
			int secondIndex;
			while ((line = reader.readLine()) != null) {
				numberOfInvocations++;

				firstIndex = line.indexOf('\t') + 1;
				secondIndex = line.indexOf("::", firstIndex);
				final String sourceTypeName =
					line.substring(firstIndex, secondIndex);

				firstIndex = secondIndex + 2;
				secondIndex = line.indexOf('(', firstIndex);
				final String sourceMethodName =
					line.substring(firstIndex, secondIndex);

				// Yann 2007/010/29: Lazy
				// For the sake of simplicity, I forget for now about parameters.

				firstIndex = line.indexOf('"', secondIndex) + 1;
				secondIndex = line.indexOf("::", firstIndex);
				final String targetTypeName =
					line.substring(firstIndex, secondIndex);

				firstIndex = secondIndex + 2;
				secondIndex = line.indexOf('"', firstIndex);
				final String targetMethodName =
					line.substring(firstIndex, secondIndex);

				IFirstClassEntity sourceType = null;
				IOperation sourceMethod = null;
				IFirstClassEntity targetType = null;
				IOperation targetMethod = null;

				try {
					sourceType =
						(IFirstClassEntity) newAbstractLevelModel
							.getConstituentFromName(sourceTypeName
								.toCharArray());

					sourceMethod =
						(IOperation) sourceType
							.getConstituentFromName(sourceMethodName
								.toCharArray());

					targetType =
						(IFirstClassEntity) newAbstractLevelModel
							.getConstituentFromName(targetTypeName
								.toCharArray());
					if (targetType == null) {
						targetType =
							Factory.getInstance().createGhost(
								targetTypeName.toCharArray(),
								targetTypeName.toCharArray());
						newAbstractLevelModel.addConstituent(targetType);
					}

					targetMethod =
						(IOperation) targetType
							.getConstituentFromName(targetMethodName
								.toCharArray());
					if (targetMethod == null) {
						targetMethod =
							Factory.getInstance().createMethod(
								targetMethodName.toCharArray(),
								targetMethodName.toCharArray());
						targetType.addConstituent(targetMethod);
					}

					final IMethodInvocation methodInvocation =
						Factory.getInstance().createMethodInvocation(
							IMethodInvocation.INSTANCE_INSTANCE,
							1,
							targetMethod.getVisibility(),
							targetType);
					sourceMethod.addConstituent(methodInvocation);

					listOfModifiedMethods.add(new Couple(
						sourceType,
						sourceMethod));
				}
				catch (final NullPointerException e) {
					numberOfFailures++;

					//	System.err.print(sourceTypeName);
					//	System.err.print(" -> ");
					//	System.err.println(sourceType);
					//	System.err.print(sourceMethodName);
					//	System.err.print(" -> ");
					//	if (sourceMethod == null) {
					//		System.err.println("null");
					//	}
					//	else {
					//		System.err.println(sourceMethod.getName());
					//	}
					//	System.err.print(targetTypeName);
					//	System.err.print(" -> ");
					//	System.err.println(targetType);
					//	System.err.print(targetMethodName);
					//	System.err.print(" -> ");
					//	if (targetMethod == null) {
					//		System.err.println("null");
					//	}
					//	else {
					//		System.err.println(targetMethod.getName());
					//	}
					//	System.err.println();
				}
			}

			reader.close();

			ProxyConsole.getInstance().debugOutput().print("Added ");
			ProxyConsole
				.getInstance()
				.debugOutput()
				.print(numberOfInvocations - numberOfFailures);
			ProxyConsole
				.getInstance()
				.debugOutput()
				.print(" method invocations (");
			ProxyConsole.getInstance().debugOutput().print(numberOfFailures);
			ProxyConsole.getInstance().debugOutput().print(" failures for ");
			ProxyConsole
				.getInstance()
				.debugOutput()
				.print(numberOfInvocations);
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println(" potential invocations)");

			// Yann 2007/02/02: Delegations
			// I go through the method in which only one method invocation 
			// has been added to convert them into delegations.
			int numberOfAddedDelegations = 0;
			final Iterator iteratorOnModifiedMethods =
				listOfModifiedMethods.iterator();
			while (iteratorOnModifiedMethods.hasNext()) {
				final Couple couple = (Couple) iteratorOnModifiedMethods.next();
				final IFirstClassEntity firstClassEntity =
					couple.getDeclaringEntity();
				final IOperation method = couple.getModifiedMethod();

				if (method instanceof IMethod
						&& method
							.getNumberOfConstituents(IMethodInvocation.class) == 1) {

					final IMethodInvocation methodInvocation =
						(IMethodInvocation) method.getIteratorOnConstituents(
							IMethodInvocation.class).next();

					final IDelegatingMethod delegation =
						Factory.getInstance().createDelegatingMethod(
							method.getName(),
							Factory
								.getInstance()
								.createAssociationRelationship(
									"DuMmY".toCharArray(),
									methodInvocation.getTargetEntity(),
									1),
							(IMethod) methodInvocation.getCalledMethod());

					final Iterator iterator =
						method.getIteratorOnConstituents();
					while (iterator.hasNext()) {
						final IConstituentOfOperation constituentOfOperation =
							(IConstituentOfOperation) iterator.next();
						delegation.addConstituent(constituentOfOperation);
					}

					firstClassEntity.removeConstituentFromID(method.getID());
					firstClassEntity.addConstituent(delegation);
					numberOfAddedDelegations++;
				}
			}

			ProxyConsole.getInstance().debugOutput().print("Added ");
			ProxyConsole
				.getInstance()
				.debugOutput()
				.print(numberOfAddedDelegations);
			ProxyConsole.getInstance().debugOutput().println(" delegations");

			return newAbstractLevelModel;
		}
		catch (final CloneNotSupportedException cnse) {
			cnse.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final FileNotFoundException e) {
			ProxyConsole
				.getInstance()
				.errorOutput()
				.println("No CLD file given?");
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		throw new UnsupportedSourceModelException();
	}
	public void setCLDFile(final String aCLDFile) {
		this.cldFile = aCLDFile;
	}
}
