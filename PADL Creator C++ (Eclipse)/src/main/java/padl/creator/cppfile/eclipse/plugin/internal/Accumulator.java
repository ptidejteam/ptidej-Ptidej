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
package padl.creator.cppfile.eclipse.plugin.internal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.cdt.core.dom.ast.DOMException;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPClassType;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPFunction;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IOperation;
import util.io.ProxyConsole;

class Accumulator {
	private final Map<ICPPClassType, IFirstClassEntity> mapCPPClassTypesPADLEntities =
		new HashMap<ICPPClassType, IFirstClassEntity>();
	private final Map<ICPPFunction, IFirstClassEntity> mapCPPFunctionPADLEntities =
		new HashMap<ICPPFunction, IFirstClassEntity>();
	private final Map<ICPPFunction, IOperation> mapCPPFunctionPADLOperations =
		new HashMap<ICPPFunction, IOperation>();

	void addClassTypes(
		final ICPPClassType aCPPEntity,
		final IFirstClassEntity aFirstClassEntity) {

		this.mapCPPClassTypesPADLEntities.put(aCPPEntity, aFirstClassEntity);
	}
	void addClassTypesAndFunctions(final Accumulator anotherAccumulator) {
		this.mapCPPClassTypesPADLEntities
			.putAll(anotherAccumulator.mapCPPClassTypesPADLEntities);
		this.mapCPPFunctionPADLEntities
			.putAll(anotherAccumulator.mapCPPFunctionPADLEntities);
		this.mapCPPFunctionPADLOperations
			.putAll(anotherAccumulator.mapCPPFunctionPADLOperations);
	}
	void addFunction(
		final ICPPFunction aCPPFunction,
		final IFirstClassEntity anEntity,
		final IOperation aFunction) {

		this.mapCPPFunctionPADLEntities.put(aCPPFunction, anEntity);
		this.mapCPPFunctionPADLOperations.put(aCPPFunction, aFunction);
	}
	Set<ICPPClassType> getClassTypes() {
		return this.mapCPPClassTypesPADLEntities.keySet();
	}
	IFirstClassEntity getFirstClassEntity(final ICPPClassType aCPPClassType) {
		return this.mapCPPClassTypesPADLEntities.get(aCPPClassType);
	}
	IFirstClassEntity getFirstClassEntity(final ICPPFunction aCPPFunction) {
		final IFirstClassEntity firstClassEntity =
			this.mapCPPFunctionPADLEntities.get(aCPPFunction);
		if (firstClassEntity == null) {
			// Eclipse is doing something phony in our backs...
			try {
				return this
					.getFirstClassEntity(aCPPFunction.getQualifiedName());
			}
			catch (final DOMException e) {
				// Not much that we can do...
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
		return firstClassEntity;
	}
	private IFirstClassEntity getFirstClassEntity(final String[] qualifiedName)
			throws DOMException {

		final Iterator<Map.Entry<ICPPFunction, IFirstClassEntity>> iterator =
			this.mapCPPFunctionPADLEntities.entrySet().iterator();

		while (iterator.hasNext()) {
			final Map.Entry<ICPPFunction, IFirstClassEntity> entry =
				iterator.next();
			final ICPPFunction function = entry.getKey();
			final String[] functionQualifiedName = function.getQualifiedName();
			if (Arrays.equals(qualifiedName, functionQualifiedName)) {
				return entry.getValue();
			}
		}
		return null;
	}
	Set<ICPPFunction> getFunctions() {
		return this.mapCPPFunctionPADLOperations.keySet();
	}
	IOperation getOperation(final ICPPFunction aCPPFunction) {
		final IOperation operation =
			this.mapCPPFunctionPADLOperations.get(aCPPFunction);
		if (operation != null) {
			return operation;
		}

		// Eclipse is doing something phony in our backs...
		char[] functionSignature = Utils.computeSignature(aCPPFunction);
		// These weird PDOMCPP objects have at the end of their signature
		// some kind of tag that I must remove to make sense... because they
		// don't: no information on the Internet regarding these classes :-(
		functionSignature =
			ArrayUtils.subarray(
				functionSignature,
				0,
				ArrayUtils.lastIndexOf(functionSignature, ')') + 1);

		final Iterator<Map.Entry<ICPPFunction, IOperation>> iterator =
			this.mapCPPFunctionPADLOperations.entrySet().iterator();
		while (iterator.hasNext()) {
			final Map.Entry<ICPPFunction, IOperation> entry = iterator.next();
			final ICPPFunction candidateFunction = entry.getKey();
			final char[] candidateFunctionSignature =
				Utils.computeSignature(candidateFunction);
			if (Arrays.equals(candidateFunctionSignature, functionSignature)) {
				final IOperation candidateOperation =
					(IOperation) entry.getValue();
				this.mapCPPFunctionPADLOperations.put(
					aCPPFunction,
					candidateOperation);
				return candidateOperation;
			}
		}

		Utils.reportUnknownType(
			Accumulator.class,
			"operation",
			aCPPFunction.toString(),
			aCPPFunction.getClass());
		return null;
	}
	boolean hasClassTypes() {
		return !this.mapCPPClassTypesPADLEntities.keySet().isEmpty();
	}
}
