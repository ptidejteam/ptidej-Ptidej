/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package padl.creator.classfile.util;

import java.util.Map;
import java.util.StringTokenizer;
import org.apache.commons.lang3.ArrayUtils;
import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.utils.Access;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IPackage;
import padl.kernel.impl.Factory;
import padl.util.Util;

/**
 * @author Yann-Gaël Guéhéneuc 
 * @since  2004/08/01
 */
public class Utils {
	private static final String DEFAULT_PACKAGE_NAME_STRING =
		"(Default Package)";
	private static final char[] DEFAULT_PACKAGE_NAME =
		DEFAULT_PACKAGE_NAME_STRING.toCharArray();
	public static boolean ENABLE_BUILT_IN_AAC_ANALYSIS = false;

	/**
	 * Constructs the method signature according to the pattern.:
	 * methodName(param1, param2, param3,...)
	 * 
	 * @param a method info 
	 * @return the method signature
	 */
	public static char[] computeSignature(
		final ExtendedMethodInfo aMethodInfo) {
		final StringBuffer signature = new StringBuffer();
		signature.append(aMethodInfo.getName());
		signature.append('(');
		final char[][] params = aMethodInfo.getParameters();
		for (int i = 0; i < params.length; i++) {
			signature.append(params[i]);
			if (i != (params.length - 1))
				// Farouk 2004/04/14:
				// A space was missing. So allMethods and newMethods
				// in POM tests did not work properly.
				signature.append(", ");
		}
		signature.append(')');
		return signature.toString().toCharArray();
	}
	public static char[] computeSimpleName(final char[] aJVMClassName) {
		int index = ArrayUtils.lastIndexOf(aJVMClassName, '$');
		if (index == -1) {
			index = ArrayUtils.lastIndexOf(aJVMClassName, '.');
			if (index == -1) {
				return aJVMClassName;
			}
		}
		return ArrayUtils
			.subarray(aJVMClassName, index + 1, aJVMClassName.length);
	}
	public static IFirstClassEntity createGhost(
		final IAbstractLevelModel anAbstractLevelModel,
		final char[] anEntityName) {

		final IFirstClassEntity firstClassEntity =
			anAbstractLevelModel.getFactory().createGhost(
				anEntityName,
				Utils.computeSimpleName(anEntityName));
		final IPackage enclosingPackage = Utils.searchForPackage(
			anAbstractLevelModel,
			anEntityName,
			new PackageCreator() {
				public IPackage create(final char[] aName) {
					return Factory.getInstance().createPackageGhost(aName);
				}
			});
		enclosingPackage.addConstituent(firstClassEntity);
		return firstClassEntity;
	}
	public static IFirstClassEntity createMemberGhost(
		final IAbstractLevelModel anAbstractLevelModel,
		final IFirstClassEntity aFirstClassEntity,
		final char[] anEntityName) {

		final IFirstClassEntity firstClassEntity =
			anAbstractLevelModel.getFactory().createMemberGhost(
				anEntityName,
				Utils.computeSimpleName(anEntityName));
		aFirstClassEntity.addConstituent(firstClassEntity);
		return firstClassEntity;
	}
	public static char[] extractPackageName(final char[] aJVMClassName) {
		if (ArrayUtils.indexOf(aJVMClassName, '.') == -1) {
			return Utils.DEFAULT_PACKAGE_NAME;
		}
		else {
			return ArrayUtils.subarray(
				aJVMClassName,
				0,
				ArrayUtils.lastIndexOf(aJVMClassName, '.'));
		}
	}
	public static IFirstClassEntity getEntityOrCreateGhost(
		final IAbstractLevelModel anAbstractLevelModel,
		final char[] anEntityName,
		final Map aMapOfIDsEntities) {

		IFirstClassEntity firstClassEntity;

		// Yann 2010/06/30: Arrays...
		// For some reason, when analysing Xercesv2.7.0.jar,
		// the DeepRelationshipAnalyser attemps to add the
		// array java.lang.String[]... Don't know why!
		if (Util.isArray(anEntityName)) {
			firstClassEntity = (IFirstClassEntity) anAbstractLevelModel
				.getTopLevelEntityFromID(
					ArrayUtils.subarray(
						anEntityName,
						0,
						ArrayUtils.indexOf(anEntityName, '[')));
		}
		else {
			//	IFirstClassEntity firstClassEntity =
			//		(IFirstClassEntity) aMapOfIDsEntities.get(String
			//			.valueOf(anEntityName));
			firstClassEntity = (IFirstClassEntity) anAbstractLevelModel
				.getTopLevelEntityFromID(anEntityName);
		}

		if (firstClassEntity == null) {
			if (ArrayUtils.indexOf(anEntityName, '$') == -1) {
				firstClassEntity =
					Utils.createGhost(anAbstractLevelModel, anEntityName);
			}
			else {
				firstClassEntity =
					Utils.searchForEntity(anAbstractLevelModel, anEntityName);
			}
			aMapOfIDsEntities
				.put(String.valueOf(anEntityName), firstClassEntity);
		}
		return firstClassEntity;
	}
	public static IPackage getPackage(
		final Map aMapOfPackageNamesPackages,
		final char[] aJVMClassName) {

		return (IPackage) aMapOfPackageNamesPackages
			.get(String.valueOf(Utils.extractPackageName(aJVMClassName)));
	}
	public static boolean isAnonymousOrLocalEntity(final char[] anEntityName) {
		final int first = ArrayUtils.indexOf(anEntityName, '$');
		int last = ArrayUtils.lastIndexOf(anEntityName, '$');
		if (first > -1) {
			if (first == last) {
				last = anEntityName.length;
			}

			try {
				Integer.parseInt(
					String.valueOf(
						ArrayUtils.subarray(anEntityName, first + 1, last)));
			}
			catch (final NumberFormatException nfe) {
				return false;
			}
			return true;
		}

		return false;
	}
	public static boolean isClass(final ClassFile classFile) {
		return !Utils.isInterface(classFile);
	}
	public static boolean isInterface(final ClassFile classFile) {
		return (classFile.getAccess()
				& Access.ACC_INTERFACE) == Access.ACC_INTERFACE;
	}
	public static boolean isLocalOrLocalMemberEntity(
		final char[] anEntityName) {
		final int first = ArrayUtils.indexOf(anEntityName, '$');
		int next = ArrayUtils.indexOf(anEntityName, '$', first + 1);
		if (first > -1 && next > -1) {
			try {
				Integer.parseInt(
					String.valueOf(
						ArrayUtils.subarray(anEntityName, first + 1, next)));
			}
			catch (final NumberFormatException nfe) {
				return false;
			}
			return true;
		}

		return false;
	}
	public static boolean isMemberEntity(final char[] anEntityName) {
		int first = ArrayUtils.indexOf(anEntityName, '$') + 1;
		if (first > 0) {
			final int second = ArrayUtils.indexOf(anEntityName, '$', first);
			if (second == -1) {
				return true;
			}

			try {
				Integer.parseInt(
					String.valueOf(
						ArrayUtils.subarray(anEntityName, first, second)));
			}
			catch (final NumberFormatException nfe) {
				return true;
			}
		}

		return false;
	}
	public static boolean isMemberNameEmpty(final String s) {
		if (s == null || s.trim().equals("")) {
			return true;
		}
		char c = s.charAt(0);
		if (c >= '0' && c <= '9') {
			return true;
		}
		if (s.endsWith("this") || s.equals("instanceof")) {
			return true;
		}
		return false;
	}
	public static boolean isMemberNameEmptyOrEqual(final String s) {
		if (s == null || s.trim().equals("")) {
			return true;
		}
		char c = s.charAt(0);
		if (c >= '0' && c <= '9') {
			return true;
		}
		if (s.endsWith("this") || s.equals("instanceof") || s.equals("=")) {
			return true;
		}
		return false;
	}
	public static boolean isSpecialMethod(final char[] aMethodName) {
		return ArrayUtils.indexOf(aMethodName, '<') > -1;
	}
	private static IFirstClassEntity searchForEnclosedEntity(
		final IAbstractLevelModel anAbstractModel,
		final IFirstClassEntity anEnclosingEntity,
		final char[] anEntityName) {

		final char[] nameFirstPart;
		if (Utils.isMemberEntity(anEntityName)) {
			nameFirstPart = ArrayUtils.subarray(
				anEntityName,
				0,
				ArrayUtils.indexOf(anEntityName, '$'));
		}
		else {
			nameFirstPart =
				ArrayUtils.subarray(anEntityName, 0, anEntityName.length);
		}

		// Yann 2016/09/18: Member IDs!
		// Member entities have their fully-qualified names (with $) as IDs...
		final char[] id = ArrayUtils.addAll(
			ArrayUtils.add(anEnclosingEntity.getID(), '$'),
			nameFirstPart);

		IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) anEnclosingEntity.getConstituentFromID(id);
		if (firstClassEntity == null) {
			firstClassEntity =
				Utils.createMemberGhost(anAbstractModel, anEnclosingEntity, id);
		}

		if (Utils.isMemberEntity(anEntityName)) {
			return Utils.searchForEnclosedEntity(
				anAbstractModel,
				firstClassEntity,
				ArrayUtils.subarray(
					anEntityName,
					ArrayUtils.indexOf(anEntityName, '$') + 1,
					anEntityName.length));
		}
		else {
			return firstClassEntity;
		}
	}
	public static IFirstClassEntity searchForEntity(
		final IAbstractLevelModel anAbstractModel,
		final char[] anEntityName) {

		final char[] nameFirstPart;
		if (Utils.isMemberEntity(anEntityName)) {
			nameFirstPart = ArrayUtils.subarray(
				anEntityName,
				0,
				ArrayUtils.indexOf(anEntityName, '$'));
		}
		else {
			nameFirstPart =
				ArrayUtils.subarray(anEntityName, 0, anEntityName.length);
		}

		IFirstClassEntity firstClassEntity = (IFirstClassEntity) anAbstractModel
			.getTopLevelEntityFromID(nameFirstPart);
		if (firstClassEntity == null) {
			firstClassEntity =
				Utils.createGhost(anAbstractModel, nameFirstPart);
		}

		if (Utils.isMemberEntity(anEntityName)) {
			return Utils.searchForEnclosedEntity(
				anAbstractModel,
				firstClassEntity,
				ArrayUtils.subarray(
					anEntityName,
					ArrayUtils.indexOf(anEntityName, '$') + 1,
					anEntityName.length));
		}
		else {
			return firstClassEntity;
		}
	}
	public static IPackage searchForPackage(
		final IAbstractLevelModel anAbstractLevelModel,
		final char[] anEntityName,
		final PackageCreator aPackageCreator) {

		// Yann 2008/11/04: Packages can be ghost, too!
		// Now that I handle packages, I must also create
		// appropriate ghost packages...
		final char[] packageName = Utils.extractPackageName(anEntityName);
		final StringTokenizer packageNameTokenizer =
			new StringTokenizer(String.valueOf(packageName), ".");
		IPackage enclosingPackage = null;

		if (!packageNameTokenizer.hasMoreTokens()) {
			enclosingPackage =
				aPackageCreator.create(Utils.DEFAULT_PACKAGE_NAME);
		}

		while (packageNameTokenizer.hasMoreTokens()) {
			final String packagePartDisplayName =
				packageNameTokenizer.nextToken();
			final char[] packagePartName = packagePartDisplayName.toCharArray();
			if (enclosingPackage == null) {
				enclosingPackage = (IPackage) anAbstractLevelModel
					.getConstituentFromID(packagePartName);

				if (enclosingPackage == null) {
					enclosingPackage = aPackageCreator.create(packagePartName);
					anAbstractLevelModel.addConstituent(enclosingPackage);
				}
			}
			else {
				IPackage newEnclosingPackage = (IPackage) enclosingPackage
					.getConstituentFromID(packagePartName);

				if (newEnclosingPackage == null) {
					newEnclosingPackage =
						aPackageCreator.create(packagePartName);
					enclosingPackage.addConstituent(newEnclosingPackage);
				}
				enclosingPackage = newEnclosingPackage;
			}
		}

		return enclosingPackage;
	}
	public static int[] sortByPriority(int[] listOfPriority) {
		int[] output = new int[listOfPriority.length];
		for (int x = 0; x < listOfPriority.length; x++) {
			output[x] = x;
		}
		for (int max = 0; max < listOfPriority.length - 1; max++) {
			for (int x = max + 1; x < listOfPriority.length; x++) {
				if (listOfPriority[output[x]] > listOfPriority[output[max]]) {
					int tmp = output[max];
					output[max] = output[x];
					output[x] = tmp;
				}
			}
		}
		return output;
	}
	private Utils() {
	}
}
