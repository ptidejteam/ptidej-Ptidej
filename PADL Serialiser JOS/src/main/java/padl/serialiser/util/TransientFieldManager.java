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
package padl.serialiser.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import padl.kernel.IConstituent;
import padl.kernel.IFirstClassEntity;
import util.io.ProxyConsole;

public class TransientFieldManager {
	static final String NULL_VALUE = "NULL";
	public static final String METHOD_INVOCATION_EXTENSION = ".methodinvocations";

	public static final Map getTransientEntityFieldNamesEntityIDs(
		final Field[] someTransientFieldsNames,
		final IConstituent aConstituent) {

		final Map mapOfFieldNamesIDs = new HashMap();
		for (int i = 0; i < someTransientFieldsNames.length; i++) {
			try {
				final Field field = someTransientFieldsNames[i];
				field.setAccessible(true);
				final IFirstClassEntity firstClassEntity = ((IFirstClassEntity) field.get(aConstituent));
				if (firstClassEntity == null) {
					mapOfFieldNamesIDs.put(
						field.getName(),
						TransientFieldManager.NULL_VALUE);
				}
				else {
					mapOfFieldNamesIDs.put(field.getName(), firstClassEntity.getDisplayID());
				}
			}
			catch (final IllegalArgumentException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final IllegalAccessException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final SecurityException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}

		return mapOfFieldNamesIDs;
	}
	public static final Field[] getTransientEntityFields(
		final Class aConstituentClass) {

		final Field[] declaredFields = aConstituentClass.getDeclaredFields();
		final List listOfTransientFields = new ArrayList();
		for (int i = 0; i < declaredFields.length; i++) {
			final Field field = declaredFields[i];
			if (Modifier.isTransient(field.getModifiers())) {
				listOfTransientFields.add(field);
			}
		}

		final Field[] transientFields = new Field[listOfTransientFields.size()];
		listOfTransientFields.toArray(transientFields);
		return transientFields;
	}
}
