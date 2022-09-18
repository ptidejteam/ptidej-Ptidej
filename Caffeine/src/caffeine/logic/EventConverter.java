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
package caffeine.logic;

import java.util.HashMap;
import java.util.Map;

import caffeine.Constants;

/**
 * version	1.0
 * @author 	Yann-Gaël Guéhéneuc
 */
public final class EventConverter {
	//	Constants
	//		.class
	//		.getField(
	//			atomName
	//				.substring(0, 1)
	//				.toUpperCase()
	//				+ atomName.substring(1))
	//		.getInt(null)
	private static final Map MAP = new HashMap();
	static {
		EventConverter.MAP.put(
			"generateClassLoadEvent",
			Integer.valueOf(Constants.GENERATE_CLASS_LOAD_EVENT));
		EventConverter.MAP.put(
			"generateClassUnloadEvent",
			Integer.valueOf(Constants.GENERATE_CLASS_UNLOAD_EVENT));
		EventConverter.MAP.put(
			"generateCollectionEvent",
			Integer.valueOf(Constants.GENERATE_COLLECTION_EVENT));
		EventConverter.MAP.put(
			"generateConstructorEntryEvent",
			Integer.valueOf(Constants.GENERATE_CONSTRUCTOR_ENTRY_EVENT));
		EventConverter.MAP.put(
			"generateConstructorExitEvent",
			Integer.valueOf(Constants.GENERATE_CONSTRUCTOR_EXIT_EVENT));
		EventConverter.MAP.put(
			"generateFieldAccessEvent",
			Integer.valueOf(Constants.GENERATE_FIELD_ACCESS_EVENT));
		EventConverter.MAP.put(
			"generateFieldModificationEvent",
			Integer.valueOf(Constants.GENERATE_FIELD_MODIFICATION_EVENT));
		EventConverter.MAP.put(
			"generateFinalizerEntryEvent",
			Integer.valueOf(Constants.GENERATE_FINALIZER_ENTRY_EVENT));
		EventConverter.MAP.put(
			"generateFinalizerExitEvent",
			Integer.valueOf(Constants.GENERATE_FINALIZER_EXIT_EVENT));
		EventConverter.MAP.put(
			"generateMethodEntryEvent",
			Integer.valueOf(Constants.GENERATE_METHOD_ENTRY_EVENT));
		EventConverter.MAP.put(
			"generateMethodExitEvent",
			Integer.valueOf(Constants.GENERATE_METHOD_EXIT_EVENT));
		EventConverter.MAP.put(
			"generateMethodReturnedValueEvent",
			Integer.valueOf(Constants.GENERATE_METHOD_RETURNED_VALUE_EVENT));
		EventConverter.MAP.put(
			"generateProgramEndEvent",
			Integer.valueOf(Constants.GENERATE_PROGRAM_END_EVENT));
	}
	public static int convertRequiredEvent(final String prologEventName) {
		try {
			final int value =
				((Integer) EventConverter.MAP.get(prologEventName))
					.intValue();
			return value;
		}
		catch (final Exception e) {
			System.err.print("Warning! Unknown required event: ");
			System.err.println(prologEventName);
			return 0;
		}
	}
}
