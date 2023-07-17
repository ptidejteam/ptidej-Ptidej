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
package squad.quality;

import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import padl.util.Util;

public abstract class AbstractQualityAttribute {
	private final String name;

	public AbstractQualityAttribute() {
		this.name = Util.computeSimpleName(this.getClass().getName());
	}

	public final String computeNominalValue(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		final String result =
			this.concretelyComputeNomValue(anAbstractModel, anEntity);
		return result;
	}

	public final double computeNumericValue(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		final double result =
			this.concretelyComputeNumValue(anAbstractModel, anEntity);
		return result;
	}

	protected String concretelyComputeNomValue(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		return "N/A";
	}

	protected double concretelyComputeNumValue(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		return Double.NaN;
	}

	public final String getName() {
		return this.name;
	}
}
