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
package ptidej.viewer.extension.repository.callgraph.visitor;

import java.util.Stack;

import padl.kernel.IAbstractModel;
import padl.kernel.IConstituent;
import padl.kernel.IGhost;
import ptidej.viewer.extension.repository.callgraph.model.Call;
import ptidej.viewer.extension.repository.callgraph.model.IVisitor;
import ptidej.viewer.extension.repository.callgraph.model.Method;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/10/06
 */
public class DottyGenerator implements IVisitor {
	private static String convertName(final String entityName) {
		String newEntityName = entityName.replace('.', '_');
		newEntityName = newEntityName.replace(' ', '_');
		newEntityName = newEntityName.replace(',', '_');
		newEntityName = newEntityName.replace('$', '_');
		newEntityName = newEntityName.replace('(', '_');
		newEntityName = newEntityName.replace(')', '_');
		newEntityName = newEntityName.replace('[', '_');
		newEntityName = newEntityName.replace(']', '_');
		newEntityName = newEntityName.replace('<', '_');
		newEntityName = newEntityName.replace('>', '_');
		return newEntityName;
	}

	private final IAbstractModel abtractModel;
	private final StringBuffer buffer;
	private final Stack stackOfMethod;

	public DottyGenerator(final IAbstractModel anAbstractlModel) {
		this.abtractModel = anAbstractlModel;
		this.buffer = new StringBuffer();
		this.stackOfMethod = new Stack();
	}
	public void enter(final Call aCall) {
		final Method targetMethod = aCall.getMethod();
		final IConstituent targetType =
			this.abtractModel.getTopLevelEntityFromID(targetMethod.getType());

		if (targetType != null && !(targetType instanceof IGhost)) {
			this.buffer.append('\t');
			this.buffer.append(this.stackOfMethod.peek());
			this.buffer.append(" -> ");
			this.buffer.append(DottyGenerator.convertName(targetMethod.getType()));
			this.buffer.append('_');
			this.buffer.append(DottyGenerator.convertName(targetMethod.getName()));
			this.buffer.append(" [label=\"calls\"]\n");
		}
	}
	public void exit(final Call aCall) {
	}
	public void enter(final Method aMethod) {
		final IConstituent type =
			this.abtractModel.getTopLevelEntityFromID(aMethod.getType());

		if (type != null && !(type instanceof IGhost)) {
			final StringBuffer methodFullName = new StringBuffer();
			methodFullName.append(
				DottyGenerator.convertName(aMethod.getType()));
			methodFullName.append('_');
			methodFullName.append(
				DottyGenerator.convertName(aMethod.getName()));

			this.stackOfMethod.push(methodFullName);

			this.buffer.append('\t');
			this.buffer.append(methodFullName);
			this.buffer.append(" [shape=box,label=\"");
			this.buffer.append(aMethod.getType());
			this.buffer.append("\\n");
			this.buffer.append(aMethod.getName());
			this.buffer.append("\"]\n");
		}
	}
	public void exit(final Method aMethod) {
		final IConstituent type =
			this.abtractModel.getTopLevelEntityFromID(aMethod.getType());

		if (type != null && !(type instanceof IGhost)) {
			this.stackOfMethod.pop();
		}
	}
	public Object getResult() {
		this.buffer.insert(0, "digraph G {\n");
		this.buffer.append('}');
		return this.buffer.toString();
	}
}
