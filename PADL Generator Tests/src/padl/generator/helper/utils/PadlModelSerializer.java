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
package padl.generator.helper.utils;

import padl.kernel.IAbstractModel;
import padl.kernel.IAbstractModelSerialiser;
import padl.serialiser.DB4OSerialiser;

public class PadlModelSerializer {
	public static String serializeModel(
		final IAbstractModel aModel,
		final String aTargetPath) {

		final IAbstractModelSerialiser serializer =
			DB4OSerialiser.getInstance();
		return serializer.serialiseWithAutomaticNaming(aModel, aTargetPath);
	}
	public static IAbstractModel deserializeModel(
		final String aSerialisedPADLModelFileName) {

		final IAbstractModelSerialiser serializer =
			DB4OSerialiser.getInstance();
		return serializer.deserialise(aSerialisedPADLModelFileName);
	}
}
