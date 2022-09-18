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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ClassRolesHolder {
	private final String className;
	private final Set setOfNegativeRoles;
	private final Set setOfPositiveRoles;
	public ClassRolesHolder(final String aClassName) {
		this.className = aClassName;
		this.setOfNegativeRoles = new HashSet();
		this.setOfPositiveRoles = new HashSet();
	}
	public String getClassName() {
		return this.className;
	}
	public void addDPRole(final String aPatternName, final String aRole) {
		this.addRole(aPatternName, aRole, true);
	}
	public void addAPRole(final String aPatternName, final String aRole) {
		this.addRole(aPatternName, aRole, false);
	}
	private void addRole(
		final String aPatternName,
		final String aRole,
		boolean isPositive) {

		final StringBuffer buffer = new StringBuffer();
		buffer.append('"');
		buffer.append(aPatternName);
		buffer.append("\"_");
		final int index;
		if ((index = aRole.indexOf('-')) == -1) {
			buffer.append(aRole);
		}
		else {
			buffer.append(aRole.substring(0, index));
		}
		if (isPositive) {
			this.setOfPositiveRoles.add(buffer.toString());
		}
		else {
			this.setOfNegativeRoles.add(buffer.toString());
		}
	}
	public Set getDPRoles() {
		return this.setOfPositiveRoles;
	}
	public Set getAPRoles() {
		return this.setOfNegativeRoles;
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(this.className);
		buffer.append("\n\tAntipatterns:    ");
		{
			final Iterator iterator = this.setOfNegativeRoles.iterator();
			while (iterator.hasNext()) {
				final String role = (String) iterator.next();
				buffer.append(role);
				if (iterator.hasNext()) {
					buffer.append("; ");
				}
			}
		}
		buffer.append("\n\tDesign patterns: ");
		{
			final Iterator iterator = this.setOfPositiveRoles.iterator();
			while (iterator.hasNext()) {
				final String role = (String) iterator.next();
				buffer.append(role);
				if (iterator.hasNext()) {
					buffer.append("; ");
				}
			}
		}
		buffer.append('\n');
		return buffer.toString();
	}
}
