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
package padl.micropattern.repository;

import java.util.HashMap;
import java.util.Iterator;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.micropattern.IMicroPatternDetection;

public final class AugmentedTypeDetection extends AbstractMicroPatternDetection
		implements IMicroPatternDetection {

	public String getName() {
		return "AugmentedTypeDetection";
	}

	/*
	 *  23. Augmented Type. There are many interfaces and classes which
	 *	declare a type, but the definition of this type is not complete without
	 *	an auxiliary definition of an enumeration. An enumeration is
	 *	a means for making a new type by restricting the (usually infinite)
	 *	set of values of an existing type to smaller list whose members are
	 *	individually enumerated.
	 *	Typically, the restricted set is of size at least three (a set of cardinality
	 *	two is in many cases best represented as boolean).
	 *	For example, methods execute and getMoreResults in interface
	 *	java.sql.Statement take an int parameter that sets
	 *	their mode of operation. Obviously, this parameter cannot assume
	 *	any integral value, since the set of distinct behaviors of these methods
	 *	must be limited and small. This is the reason that this interface
	 *	gives symbolic names to the permissible values of this parameter.
	 *	Formally, an Augmented Type is a Pure Type except that it makes
	 *	three or more static final definitions of the same type.
	 *	Pattern Augmented Type pattern is quite rare (0.5%), probably thanks
	 *	to the advent of the Enum mechanism to the language.
	 */

	public boolean detect(final IFirstClassEntity anEntity) {
		int nbMethod = 0;
		HashMap typeMap = new HashMap();
		boolean AugmentedField = false;

		// Must be an interface or an Abstract Class
		if (anEntity instanceof IFirstClassEntity
				&& ((IFirstClassEntity) anEntity).isAbstract()) {
			final Iterator iterator = anEntity.getIteratorOnConstituents();

			while (iterator.hasNext()) {
				final Object anOtherEntity = iterator.next();

				// Need at least one method
				if (anOtherEntity instanceof IMethod) {
					// All method need to be abstract
					if (((IMethod) anOtherEntity).isAbstract()) {
						nbMethod++;
					}
					else {
						return false;
					}
				}

				// All field must be static
				if (anOtherEntity instanceof IField) {
					if (!((IField) anOtherEntity).isStatic()) {
						return false;
					}
					else if (!AugmentedField) {
						// Check Field type and count occurence of this type
						if (typeMap.containsKey(((IField) anOtherEntity)
							.getDisplayTypeName())) {

							final Integer curentValue =
								(Integer) typeMap.get(((IField) anOtherEntity)
									.getDisplayTypeName());
							int intValue = curentValue.intValue();
							intValue++;

							typeMap.remove(((IField) anOtherEntity)
								.getDisplayTypeName());
							typeMap.put(
								((IField) anOtherEntity).getDisplayTypeName(),
								Integer.valueOf(intValue));

							if (intValue == 3) {
								AugmentedField = true;
							}
						}
						else {
							typeMap.put(
								((IField) anOtherEntity).getDisplayTypeName(),
								Integer.valueOf(1));
						}
					}
				}
			}

			if (nbMethod > 0 && AugmentedField) {
				this.addEntities(anEntity);
				return true;
			}

			return false;
		}

		return false;
	}
}
