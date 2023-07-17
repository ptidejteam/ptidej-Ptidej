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
/*
 * Copyright  2000-2004 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License. 
 *
 */
package ptidej.viewer.extension.repository.callgraph;

import java.util.ArrayList;
import java.util.List;
import org.apache.bcel.Repository;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.ConstantPushInstruction;
import org.apache.bcel.generic.EmptyVisitor;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionConstants;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.ReturnInstruction;
import ptidej.viewer.extension.repository.callgraph.model.Call;
import ptidej.viewer.extension.repository.callgraph.model.Method;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/10/04
 */
class CreatorOfCalls extends EmptyVisitor {
	private final MethodGen methodGen;
	private final ConstantPoolGen constantPoolGen;
	private final List listOfCalls;

	CreatorOfCalls(final MethodGen aMethodGen) {
		this.methodGen = aMethodGen;
		this.constantPoolGen = aMethodGen.getConstantPool();
		this.listOfCalls = new ArrayList();
	}

	public void start() {
		if (!this.methodGen.isAbstract() && !this.methodGen.isNative()) {
			for (InstructionHandle ih =
				this.methodGen.getInstructionList().getStart(); ih != null; ih =
				ih.getNext()) {

				final Instruction i = ih.getInstruction();
				if (!this.visitInstruction(i)) {
					i.accept(this);
				}
			}
		}
	}
	private boolean visitInstruction(final Instruction i) {
		short opcode = i.getOpcode();
		if ((InstructionConstants.INSTRUCTIONS[opcode] != null)
				&& !(i instanceof ConstantPushInstruction)
				&& !(i instanceof ReturnInstruction)) {

			return true;
		}
		return false;
	}
	public void visitInvokeInstruction(final InvokeInstruction i) {
		final String className =
			i.getReferenceType(this.constantPoolGen).getSignature();
		final String methodName =
			CallGraphGenerator.createMethodName(
				i.getReturnType(this.constantPoolGen),
				i.getMethodName(this.constantPoolGen),
				i.getArgumentTypes(this.constantPoolGen));

		Method method;
		if (CallGraphGenerator.doesMethodExist(className, methodName)) {
			method = CallGraphGenerator.get(className, methodName);
		}
		else {
			try {
				final CreatorOfMethod visitor = new CreatorOfMethod(methodName);
				Repository.lookupClass(className).accept(visitor);
				method = visitor.getMethod();
				CallGraphGenerator.put(className, methodName, method);
			}
			catch (final ClassNotFoundException e) {
				e.printStackTrace();
				method = null;
			}
		}

		final Call call = new Call(method);
		this.listOfCalls.add(call);
	}
	public Call[] getCalls() {
		final Call[] calls = new Call[this.listOfCalls.size()];
		this.listOfCalls.toArray(calls);
		return calls;
	}
}
