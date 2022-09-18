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
package padl.test.helper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflector {
	public static void callMethodOnReflectedField(
		final Class aDeclaringClass,
		final String aFieldName,
		final Object aReceiver,
		final String aMethodName) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException {

		final Object object =
			Reflector.getObjectFromReflectedField(
				aDeclaringClass,
				aFieldName,
				aReceiver);
		final Method method =
			object.getClass().getMethod(aMethodName, new Class[0]);
		method.invoke(object, new Object[0]);
	}
	public static void callMethodOnReflectedField(
		final Class aDeclaringClass,
		final String aFieldName,
		final Object aReceiver,
		final String aMethodName,
		final Class aMethodParameterType,
		final Object aMethodParameterValue) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException {

		final Object object =
			Reflector.getObjectFromReflectedField(
				aDeclaringClass,
				aFieldName,
				aReceiver);
		final Method method =
			object.getClass().getMethod(
				aMethodName,
				new Class[] { aMethodParameterType });
		method.invoke(object, new Object[] { aMethodParameterValue });
	}
	public static Object getObjectFromReflectedField(
		final Class aDeclaringClass,
		final String aFieldName,
		final Object aReceiver) throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {

		final Field containerField =
			aDeclaringClass.getDeclaredField(aFieldName);
		containerField.setAccessible(true);
		final Object value = containerField.get(aReceiver);
		return value;
	}
	public static void setReflectedFieldValue(
		final Class aDeclaringClass,
		final String aFieldName,
		final Object aReceiver,
		final Object aValue) throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {

		final Field containerField =
			aDeclaringClass.getDeclaredField(aFieldName);
		containerField.setAccessible(true);
		containerField.set(aReceiver, aValue);
	}
}
