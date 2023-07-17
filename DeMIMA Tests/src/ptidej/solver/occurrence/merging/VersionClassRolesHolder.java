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
package ptidej.solver.occurrence.merging;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class VersionClassRolesHolder {
	private final Map versionClassRoles = new HashMap();
	public void addDPRole(
		final String aClassName,
		final String aPatternName,
		final String aRole) {

		this.addRole(aClassName, aPatternName, aRole, true);
	}
	public void addAPRole(
		final String aClassName,
		final String aPatternName,
		final String aRole) {

		this.addRole(aClassName, aPatternName, aRole, false);
	}
	private void addRole(
		final String aClassName,
		final String aPatternName,
		final String aRole,
		final boolean isPositive) {

		ClassRolesHolder classRolesHolder =
			(ClassRolesHolder) this.versionClassRoles.get(aClassName);
		if (classRolesHolder == null) {
			classRolesHolder = new ClassRolesHolder(aClassName);
			this.versionClassRoles.put(aClassName, classRolesHolder);
		}
		if (isPositive) {
			classRolesHolder.addDPRole(aPatternName, aRole);
		}
		else {
			classRolesHolder.addAPRole(aPatternName, aRole);
		}
	}
	public int getNumberOfClassesWithPositiveAndNegativeRoles() {
		int count = 0;
		final Iterator iteratorOnClasses =
			this.versionClassRoles.keySet().iterator();
		while (iteratorOnClasses.hasNext()) {
			final String className = (String) iteratorOnClasses.next();
			final ClassRolesHolder classRoles =
				(ClassRolesHolder) this.versionClassRoles.get(className);
			final Set setOfNegativeRoles = classRoles.getAPRoles();
			final Set setOfPositiveRoles = classRoles.getDPRoles();
			if (setOfNegativeRoles.size() > 0 && setOfPositiveRoles.size() > 0) {
				count++;
			}
		}
		return count;
	}
	public String getClassesWithPositiveAndNegativeRoles() {
		final StringBuffer buffer = new StringBuffer();
		final Iterator iteratorOnClasses =
			this.versionClassRoles.keySet().iterator();
		while (iteratorOnClasses.hasNext()) {
			final String className = (String) iteratorOnClasses.next();
			final ClassRolesHolder classRoles =
				(ClassRolesHolder) this.versionClassRoles.get(className);
			final Set setOfNegativeRoles = classRoles.getAPRoles();
			final Set setOfPositiveRoles = classRoles.getDPRoles();
			if (setOfNegativeRoles.size() > 0 && setOfPositiveRoles.size() > 0) {
				buffer.append(classRoles.getClassName());
				buffer.append(';');
				Iterator iterator = setOfNegativeRoles.iterator();
				while (iterator.hasNext()) {
					String role = (String) iterator.next();
					buffer.append("Has_");
					buffer.append(role);
					if (iterator.hasNext()) {
						buffer.append(';');
					}
				}
				buffer.append(';');
				iterator = setOfPositiveRoles.iterator();
				while (iterator.hasNext()) {
					String role = (String) iterator.next();
					buffer.append("PlaysRoleIn_");
					buffer.append(role);
					if (iterator.hasNext()) {
						buffer.append(';');
					}
				}
				buffer.append('\n');
			}
		}
		return buffer.toString();
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		final Iterator iteratorOnClasses =
			this.versionClassRoles.keySet().iterator();
		while (iteratorOnClasses.hasNext()) {
			final String className = (String) iteratorOnClasses.next();
			final ClassRolesHolder classRoles =
				(ClassRolesHolder) this.versionClassRoles.get(className);
			buffer.append(classRoles.toString());
			buffer.append('\n');
		}
		return buffer.toString();
	}
}
