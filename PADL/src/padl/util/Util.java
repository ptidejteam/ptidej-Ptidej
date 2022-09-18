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
package padl.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.ArrayUtils;
import padl.kernel.Constants;
import padl.kernel.IAbstractModel;
import padl.kernel.IConstituent;
import padl.kernel.IContainer;
import padl.kernel.IElement;
import padl.kernel.IFilter;
import padl.kernel.IFirstClassEntity;
import util.io.ProxyConsole;

public class Util {
	private static final char[] JAVA_LANG_OBJECT = "java.lang.Object"
		.toCharArray();

	public static void addTabs(final int tab, final StringBuffer b) {
		for (int i = 0; i < tab; i++) {
			b.append("    ");
		}
	}

	public static void assertEquals(
		final int[] aFirstArray,
		final int[] aSecondArray) {

		if (aFirstArray == aSecondArray) {
			ProxyConsole
				.getInstance()
				.normalOutput()
				.println("Arrays are identical!");
		}
		if (aFirstArray.length != aSecondArray.length) {
			ProxyConsole
				.getInstance()
				.normalOutput()
				.println("Arrays have different length!");
		}

		for (int i = 0; i < aFirstArray.length; i++) {
			if (aFirstArray[i] != aSecondArray[i]) {
				ProxyConsole
					.getInstance()
					.errorOutput()
					.print("Arrays have different values at index ");
				ProxyConsole.getInstance().errorOutput().print(i);
				ProxyConsole.getInstance().errorOutput().println('!');
			}
		}
	}

	public static char[] capitalizeFirstLetter(final char[] aName) {
		final char[] newName = new char[aName.length];
		newName[0] = Character.toUpperCase(aName[0]);
		System.arraycopy(aName, 1, newName, 1, aName.length - 1);

		return newName;
	}

	public static char[] computeFullyQualifiedName(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		Iterator iterator = anAbstractModel.getIteratorOnTopLevelEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iterator.next();
			if (firstClassEntity.equals(anEntity)) {
				return firstClassEntity.getName();
			}
			else {
				final char[] fullyQualifiedName =
					Util.computeFullyQualifiedName(
						firstClassEntity,
						anEntity,
						firstClassEntity.getName());
				if (!fullyQualifiedName.equals("")) {
					return fullyQualifiedName;
				}
			}
		}

		return new char[0];
	}

	private static char[] computeFullyQualifiedName(
		final IFirstClassEntity anEnclosingEntity,
		final IFirstClassEntity anEntity,
		final char[] aFullyQualifiedName) {

		final Iterator iterator =
			anEnclosingEntity
				.getIteratorOnConstituents(IFirstClassEntity.class);
		while (iterator.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iterator.next();
			final char[] qualifiedName =
				new char[aFullyQualifiedName.length + 1
						+ firstClassEntity.getName().length];
			System.arraycopy(
				aFullyQualifiedName,
				0,
				qualifiedName,
				0,
				aFullyQualifiedName.length);
			qualifiedName[aFullyQualifiedName.length] = '.';
			System.arraycopy(
				firstClassEntity.getName(),
				0,
				qualifiedName,
				aFullyQualifiedName.length + 1,
				firstClassEntity.getName().length);

			if (firstClassEntity.equals(anEntity)) {
				return qualifiedName;
			}
			else {
				return Util.computeFullyQualifiedName(
					firstClassEntity,
					anEntity,
					qualifiedName);
			}
		}

		return new char[0];
	}

	public static char[] computeSimpleName(final char[] aFullyQualifiedName) {
		char[] simpleName = aFullyQualifiedName;
		int index;

		// Yann 2006/08/03: Member class!
		// When computing the simple name of a class, I don't
		// forget that this class could be a member class and
		// that I don't want to care about its declaring class
		// (preceding the '$' sign).
		index = ArrayUtils.lastIndexOf(simpleName, '$');
		if (index > 0) {
			simpleName =
				ArrayUtils.subarray(
					aFullyQualifiedName,
					index + 1,
					aFullyQualifiedName.length);
		}
		else {
			index = ArrayUtils.lastIndexOf(simpleName, '.');
			if (index > 0) {
				simpleName =
					ArrayUtils.subarray(
						simpleName,
						index + 1,
						simpleName.length);
			}
		}

		return simpleName;
	}

	public static String computeSimpleName(final String aFullyQualifiedName) {
		return String.valueOf(Util.computeSimpleName(aFullyQualifiedName
			.toCharArray()));
	}

	private static int displayDirectoryContent(
		final int numberOfFiles,
		final String directory,
		final String fileExtension) {

		File file = new File(directory);
		int n = numberOfFiles;
		final String[] list = file.list();
		for (int i = 0; i < list.length; i++) {
			file = new File(directory + list[i]);
			if (file.isDirectory()) {
				n =
					Util.displayDirectoryContent(n++, file.getAbsolutePath()
							+ File.separatorChar, fileExtension);
			}
			else {
				if (file.getName().endsWith(fileExtension)) {
					ProxyConsole.getInstance().normalOutput().print("file");
					ProxyConsole.getInstance().normalOutput().print(n++);
					ProxyConsole.getInstance().normalOutput().print('=');
					ProxyConsole
						.getInstance()
						.normalOutput()
						.println(file.getAbsoluteFile());
				}
			}
		}

		return n;
	}

	public static void displayDirectoryContent(
		final String directory,
		final String fileExtension) {

		Util.displayDirectoryContent(4, directory, fileExtension);
	}

	public static IConstituent[] getArrayOfConstituents(
		final IContainer aContainer) {

		final IConstituent[] constituents =
			new IConstituent[aContainer.getNumberOfConstituents()];
		final Iterator iteratorOnEntities =
			aContainer.getIteratorOnConstituents();
		int count = 0;
		while (iteratorOnEntities.hasNext()) {
			final IConstituent constituent =
				(IConstituent) iteratorOnEntities.next();
			constituents[count] = constituent;
			count++;
		}
		return constituents;
	}

	public static IElement[] getArrayOfElements(final IFirstClassEntity anEntity) {
		final IElement[] elements =
			new IElement[anEntity.getNumberOfConstituents()];
		final Iterator iteratorOnEntities =
			anEntity.getIteratorOnConstituents();
		int count = 0;
		while (iteratorOnEntities.hasNext()) {
			final IElement element = (IElement) iteratorOnEntities.next();
			elements[count] = element;
			count++;
		}
		return elements;
	}

	public static IFirstClassEntity[] getArrayOfTopLevelEntities(
		final IAbstractModel anAbstractModel) {

		final IFirstClassEntity[] entities =
			new IFirstClassEntity[anAbstractModel.getNumberOfTopLevelEntities()];
		final Iterator iteratorOnEntities =
			anAbstractModel.getIteratorOnTopLevelEntities();
		int count = 0;
		while (iteratorOnEntities.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iteratorOnEntities.next();
			entities[count] = firstClassEntity;
			count++;
		}
		return entities;
	}

	public static Iterator getFilteredConstituentsIterator(
		final IConstituent[] someConstituents,
		final int aSize,
		final IFilter aFilter) {

		return Util.getFilteredConstituentsList(
			someConstituents,
			aSize,
			aFilter).iterator();
	}

	private static List getFilteredConstituentsList(
		final IConstituent[] someConstituents,
		final int aSize,
		final IFilter aFilter) {

		final List list = new ArrayList();

		for (int i = 0; i < aSize; i++) {
			final IConstituent constituent = someConstituents[i];
			if (aFilter.isFiltered(constituent)) {
				list.add(constituent);
			}
		}

		return list;
	}

	public static List getListOfAllEntities(final IAbstractModel anAbstractModel) {
		final List entities =
			new ArrayList(anAbstractModel.getNumberOfTopLevelEntities());
		final Iterator iteratorOnEntities =
			anAbstractModel.getIteratorOnTopLevelEntities();
		while (iteratorOnEntities.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iteratorOnEntities.next();
			entities.add(firstClassEntity);
		}
		return entities;
	}

	public static int getNumberOfArguments(final String methodSignature) {
		final String args =
			methodSignature.substring(methodSignature.indexOf('('));
		if (args.equals("()")) {
			return 0;
		}
		else {
			int numberOfArguments = 1;
			for (int index = 0; (index = args.indexOf(',', index)) > -1; index++, numberOfArguments++)
				;
			return numberOfArguments;
		}
	}

	public static List getPatternProperties(final Class clazz) {
		final List roProperties = new ArrayList();
		final List rwProperties = new ArrayList();
		final List exProperties = new ArrayList();
		try {
			final PropertyDescriptor[] basicProperties =
				Introspector.getBeanInfo(clazz, null).getPropertyDescriptors();

			for (int x = 0; x < basicProperties.length; x++) {
				final Method writeMethod = basicProperties[x].getWriteMethod();
				final Method readMethod = basicProperties[x].getReadMethod();

				if (readMethod != null) {
					if (writeMethod != null) {
						rwProperties.add(basicProperties[x].getName());
					}
					else {
						roProperties.add(basicProperties[x].getName());
					}
				}
			}
		}
		catch (final Exception e) {
		}

		// I look for all suitable "add" and "remove" methods.
		try {
			final Map adds = new HashMap();
			final Map removes = new HashMap();
			final Method[] methods = clazz.getMethods();
			for (int i = 0; i < methods.length; i++) {
				final Method currentMethod = methods[i];
				final int mods = currentMethod.getModifiers();
				final String currentName = currentMethod.getName();
				final Class argTypes[] = currentMethod.getParameterTypes();
				if (Modifier.isStatic(mods) || !Modifier.isPublic(mods) // ||
																		// argTypes.length
																		// != 1
						|| currentMethod.getReturnType() != Void.TYPE) {

					continue; // RV: pour que Yann soit content !!!!
				}

				// Yann 2001/06/27: I check the number of arguments
				// depending on which type of method I am looking at
				// to handle correctly the add and remove methods
				// of the Façade pattern.
				if (currentName.startsWith("add") && argTypes.length > 0) {
					adds.put(new String(currentName.substring(3) + ":"
							+ argTypes[0]), currentMethod);
				}
				else if (currentName.startsWith("remove")
						&& argTypes.length == 1) {
					removes.put(new String(currentName.substring(6) + ":"
							+ argTypes[0]), currentMethod);
				}
			}

			// Now I look for matching add + remove pairs.
			final Iterator keys = adds.keySet().iterator();
			while (keys.hasNext()) {
				final String addKey = (String) keys.next();
				// Skip any "add" which doesn't have a matching "remove".

				if (removes.get(addKey) != null) {
					exProperties.add(addKey.substring(0, addKey.indexOf(":")));

				}
			}
		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		final List props = roProperties;
		props.addAll(rwProperties);
		props.addAll(exProperties);

		return props;
	}

	public static IConstituent[] getTypedConstituentsArray(
		final IConstituent[] someConstituents,
		final int aSize,
		final Class aConstituentType) {

		final List list =
			Util.getTypedConstituentsList(
				someConstituents,
				aSize,
				aConstituentType);
		final IConstituent[] constituents = new IConstituent[list.size()];
		list.toArray(constituents);

		return constituents;
	}

	public static Iterator getTypedConstituentsIterator(
		final IConstituent[] someConstituents,
		final int aSize,
		final Class aConstituentType) {

		return Util.getTypedConstituentsList(
			someConstituents,
			aSize,
			aConstituentType).iterator();
	}

	private static List getTypedConstituentsList(
		final IConstituent[] someConstituents,
		final int aSize,
		final Class aConstituentType) {

		final List list = new ArrayList();

		for (int i = 0; i < aSize; i++) {
			final IConstituent constituent = someConstituents[i];
			// TODO: Should it be isAssignableFrom or equals?
			if (aConstituentType.isAssignableFrom(constituent.getClass())) {
				list.add(constituent);
			}
		}

		return list;
	}

	public static boolean isArray(final char[] aTypeName) {
		if ( // TODO: Fix for eclipse_12-15-2009
		aTypeName.length > 0 && aTypeName[aTypeName.length - 1] == ']') {
			return true;
		}
		return false;
	}

	public static boolean isArrayOrCollection(final IFirstClassEntity anEntity) {
		if (Util.isArray(anEntity.getID())) {
			return true;
		}
		if (Util.isCollection(anEntity.getID())) {
			return true;
		}

		// Yann 2004/05/24: Superclasses!
		// The Misc.isArrayOrCollection() only checks the
		// direct superclass, I should certainly check the
		// complete hierarchy...
		// I check the complete inheritance tree (and
		// remove the use of reflection in isArrayOrCollection(),
		// everywhere I use this method!).
		// try {
		// // Yann 2003/07/24: Souvenir?
		// // I don't remember now why I use reflection
		// // to get the java.util.Collection class????
		// // final Class collectionInteface =
		// // Class.forName("java.util.Collection");
		// // final Class typeClass = Class.forName(typeName);
		//
		// final boolean resultForDebugPurpose =
		// Collection.class.isAssignableFrom(Class.forName(typeName));
		// return resultForDebugPurpose;
		// }
		// catch (final ClassNotFoundException cnfe) {
		// }
		// catch (final NoClassDefFoundError ncdfe) {
		// }

		boolean result = false;
		if (!Arrays.equals(
			anEntity.getName(),
			Constants.DEFAULT_HIERARCHY_ROOT_NAME)) {

			final Iterator iterator = anEntity.getIteratorOnInheritedEntities();
			while (iterator.hasNext() && !result) {
				result =
					Util.isArrayOrCollection((IFirstClassEntity) iterator
						.next());
			}
		}
		return result;
	}

	public static boolean isArrayOrCollection(final char[] aTypeName) {
		if (Util.isArray(aTypeName)) {
			return true;
		}
		if (Util.isCollection(aTypeName)) {
			return true;
		}
		return false;
	}

	public static boolean isClassException(final String aClassName) {
		return aClassName.endsWith("Exception")
				|| (aClassName.indexOf("Exception") != -1);
	}

	public static boolean isCollection(final char[] aTypeName) {
		// FIXME collados 16/04/2012 : I think it should take care about what
		// contains the name (ex : if the name contains List or Queue non case
		// sensitive) in order to match with the extended types
		final String aTypeDisplayName = String.valueOf(aTypeName);
		return aTypeDisplayName.equals("java.util.Collection")
				|| aTypeDisplayName.equals("java.util.AbstractList")
				|| aTypeDisplayName.equals("java.util.ArrayList")
				|| aTypeDisplayName.equals("java.util.LinkedList")
				|| aTypeDisplayName.equals("java.util.Vector")
				|| aTypeDisplayName.equals("java.util.Stack")
				|| aTypeDisplayName.equals("java.util.AbstractSequentialList")
				|| aTypeDisplayName.equals("java.util.List")
				|| aTypeDisplayName.equals("java.util.Set")
				|| aTypeDisplayName.equals("java.util.AbstractSet")
				|| aTypeDisplayName.equals("java.util.HashSet")
				|| aTypeDisplayName.equals("java.util.LinkedHashSet")
				|| aTypeDisplayName.equals("java.util.TreeSet")
				|| aTypeDisplayName.equals("java.util.SortedSet")
				|| aTypeDisplayName
					.equals("padl.creator.test.relationships.providers.VectorTest");
	}

	public static boolean isMethodFromObject(final String aMethodName) {
		return aMethodName.equals("clone") || aMethodName.equals("equals")
				|| aMethodName.equals("finalize")
				|| aMethodName.equals("hashCode")
				|| aMethodName.equals("toString");
	}

	public static boolean isObjectModelRoot(final char[] aTypeName) {
		return Arrays.equals(aTypeName, Util.JAVA_LANG_OBJECT);
	}

	public static boolean isPrimtiveType(final char[] aTypeName) {
		final String aTypeDisplayName = String.valueOf(aTypeName);
		return aTypeDisplayName.equals("boolean")
				|| aTypeDisplayName.equals("byte")
				|| aTypeDisplayName.equals("char")
				|| aTypeDisplayName.equals("short")
				|| aTypeDisplayName.equals("double")
				|| aTypeDisplayName.equals("float")
				|| aTypeDisplayName.equals("int")
				|| aTypeDisplayName.equals("long")
				|| aTypeDisplayName.equals("boolean[]")
				|| aTypeDisplayName.equals("byte[]")
				|| aTypeDisplayName.equals("char[]")
				|| aTypeDisplayName.equals("short[]")
				|| aTypeDisplayName.equals("double[]")
				|| aTypeDisplayName.equals("float[]")
				|| aTypeDisplayName.equals("int[]")
				|| aTypeDisplayName.equals("long[]")
				|| aTypeDisplayName.equals("void")
				|| aTypeDisplayName.indexOf('[') > -1;
	}

	public static boolean isEntityInheritingFrom(
		final IFirstClassEntity anInheritingEntity,
		final IFirstClassEntity aPotentialInheritedEntity) {

		final Iterator iterator =
			anInheritingEntity.getIteratorOnInheritedEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iterator.next();
			if (firstClassEntity.equals(aPotentialInheritedEntity)) {
				return true;
			}
			else {
				return Util.isEntityInheritingFrom(
					firstClassEntity,
					aPotentialInheritedEntity);
			}
		}
		return false;
	}

	public static char[] minimizeFirstLetter(final char[] name) {
		final char[] newName = new char[name.length];
		newName[0] = Character.toLowerCase(name[0]);
		System.arraycopy(name, 1, newName, 1, name.length - 1);
		return newName;
	}

	public static char[] stripAndCapQualifiedName(final char[] aQualifiedName) {
		final StringBuffer buffer = new StringBuffer();
		boolean previousCharWhitespace = false;
		for (int i = 0; i < aQualifiedName.length; i++) {
			final char c = aQualifiedName[i];
			// Yann 2002/12/17: Inner classes!
			// I must remove the $ sign from names
			// coming from inner classes!
			if (c != '.' && c != '$') {
				if (previousCharWhitespace) {
					buffer.append(Character.toUpperCase(c));
					previousCharWhitespace = false;
				}
				else {
					buffer.append(c);
				}
			}
			else {
				previousCharWhitespace = true;
			}
		}

		return buffer.toString().toCharArray();
	}

	private Util() {
	}
}
