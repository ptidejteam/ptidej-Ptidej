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
package padl.creator.classfile.util;

import java.util.List;
import padl.kernel.IFactory;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/08/02
 */
public class ExtendedMethodInvocation {
	private final IFirstClassEntity enclosingEntity;
	private final IOperation enclosingMethod;
	private IMethodInvocation methodInvocation;

	public ExtendedMethodInvocation(
		final IFactory aFactory,
		final IFirstClassEntity enclosingEntity,
		final IOperation enclosingMethod,
		final int type,
		final int cardinality,
		final IFirstClassEntity targetEntity) {

		this.enclosingEntity = enclosingEntity;
		this.enclosingMethod = enclosingMethod;
		this.methodInvocation =
			aFactory.createMethodInvocation(
				type,
				cardinality,
				enclosingMethod.getVisibility(),
				targetEntity);
	}
	public ExtendedMethodInvocation(
		final IFactory aFactory,
		final IFirstClassEntity enclosingEntity,
		final IOperation enclosingMethod,
		final int type,
		final int cardinality,
		final IFirstClassEntity targetEntity,
		final IFirstClassEntity entityDeclaringField) {

		this.enclosingEntity = enclosingEntity;
		this.enclosingMethod = enclosingMethod;
		this.methodInvocation =
			aFactory.createMethodInvocation(
				type,
				cardinality,
				enclosingMethod.getVisibility(),
				targetEntity,
				entityDeclaringField);
	}
	public int getCardinality() {
		return this.methodInvocation.getCardinality();
	}
	public IOperation getEnclosingMethod() {
		return this.enclosingMethod;
	}
	public IMethodInvocation getMethodInvocation() {
		return this.methodInvocation;
	}
	public IFirstClassEntity getOriginEntity() {
		return this.enclosingEntity;
	}
	public IFirstClassEntity getTargetEntity() {
		return this.methodInvocation.getTargetEntity();
	}
	public int getType() {
		return this.methodInvocation.getType();
	}
	public int getVisibility() {
		return this.methodInvocation.getVisibility();
	}
	public void setCalledMethod(final IOperation calledMethod) {
		this.methodInvocation.setCalledMethod(calledMethod);
	}
	public void setCallingField(final List callingFields) {
		this.methodInvocation.setCallingField(callingFields);
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("Enclosing entity: ");
		buffer.append(this.enclosingEntity.getName());
		buffer.append("\nEnclosing method: ");
		buffer.append(this.enclosingMethod.getName());
		buffer.append('\n');
		buffer.append(this.methodInvocation.toString());
		return buffer.toString();
	}
}
