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
package padl.creator.classfile.util;

import padl.kernel.Constants;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.util.Util;
import com.ibm.toad.cfparse.utils.Access;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/08/01
 */
public final class CardinalityManyAccessorsData implements AccessorsData {
	private final ICodeLevelModel codeLevelModel;
	public CardinalityManyAccessorsData(final ICodeLevelModel aCodeLevelModel) {
		this.codeLevelModel = aCodeLevelModel;
	}
	public int getCardinality() {
		return Constants.CARDINALITY_MANY;
	}
	public boolean matches(
		final char[] targetName,
		final ExtendedFieldInfo fieldInfo) {

		final char[] typeName = fieldInfo.getType();
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) this.codeLevelModel
				.getConstituentFromID(typeName);
		boolean isArrayOrCollection = false;
		if (firstClassEntity != null) {
			isArrayOrCollection = Util.isArrayOrCollection(firstClassEntity);
		}
		else {
			isArrayOrCollection = Util.isArrayOrCollection(typeName);
		}
		return !Access.isPublic(fieldInfo.getVisibility())
				&& isArrayOrCollection;
	}
}
