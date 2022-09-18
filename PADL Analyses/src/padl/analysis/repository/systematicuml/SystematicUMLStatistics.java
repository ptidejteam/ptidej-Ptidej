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

import java.util.ArrayList;
import java.util.List;

import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/05/20
 */
public class SystematicUMLStatistics {
	private final List listOfDataTypes;
	private final List listOfEnumerations;
	private final List listOfImplementationClasses;
	private final List listOfOperations;
	private final List listOfTemplateMethods;
	private final List listOfTypes;
	private final List listOfUtilityClasses;

	public SystematicUMLStatistics() {
		this.listOfDataTypes = new ArrayList();
		this.listOfTypes = new ArrayList();
		this.listOfImplementationClasses = new ArrayList();
		this.listOfOperations = new ArrayList();
		this.listOfTemplateMethods = new ArrayList();
		this.listOfUtilityClasses = new ArrayList();
		this.listOfEnumerations = new ArrayList();
	}
	public void dataTypeRecognized(final IFirstClassEntity anEntity) {
		this.listOfDataTypes.add(anEntity);
	}
	public void dataTypeUnRecognized(final IFirstClassEntity anEntity) {
		this.listOfDataTypes.remove(anEntity);
	}
	public void enumerationRecognized(final IFirstClassEntity anEntity) {
		this.listOfEnumerations.add(anEntity);
	}
	public void implementationClassRecognized(final IFirstClassEntity anEntity) {
		this.listOfImplementationClasses.add(anEntity);
	}
	public void operationRecognized(final IMethod aMethod) {
		this.listOfOperations.add(aMethod);
	}
	public void templateMethodRecognized(final IMethod aMethod) {
		this.listOfTemplateMethods.add(aMethod);
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("Number of types: ");
		buffer.append(this.listOfTypes.size());
		buffer.append("\nNumber of implementation classes: ");
		buffer.append(this.listOfImplementationClasses.size());
		buffer.append("\nNumber of utility classes: ");
		buffer.append(this.listOfUtilityClasses.size());
		buffer.append("\nNumber of enumeration classes: ");
		buffer.append(this.listOfEnumerations.size());
		buffer.append("\nNumber of data types: ");
		buffer.append(this.listOfDataTypes.size());
		buffer.append("\nNumber of operations: ");
		buffer.append(this.listOfOperations.size());
		buffer.append("\nNumber of template methods: ");
		buffer.append(this.listOfTemplateMethods.size());
		buffer.append('\n');
		return buffer.toString();
	}
	public void typeRecognized(final IFirstClassEntity anEntity) {
		this.listOfTypes.add(anEntity);
	}
	public void utilityClassRecognized(final IFirstClassEntity anEntity) {
		this.listOfUtilityClasses.add(anEntity);
	}
}
