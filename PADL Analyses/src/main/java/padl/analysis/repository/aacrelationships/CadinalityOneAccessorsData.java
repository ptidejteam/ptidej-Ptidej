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
package padl.analysis.repository.aacrelationships;

import padl.kernel.Constants;
import padl.kernel.IField;
import com.ibm.toad.cfparse.utils.Access;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/08/01
 */
final class CadinalityOneAccessorsData implements IAccessorsData {
	public int getCardinality() {
		return Constants.CARDINALITY_ONE;
	}
	public boolean matches(final String aTargetName, final IField aField) {
		return (Access.isPrivate(aField.getVisibility()) || Access
			.isProtected(aField.getVisibility()))
				&& aField.getDisplayTypeName().equals(aTargetName);
	}
}
