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
package mendel.part.output;

import java.util.Map;

/**
 * @author Simon Denier
 * @since May 16, 2008
 *
 */
public class VersoTagOutput extends VersoHierarchyOutput {

	public void initGhostProperties() {
		super.initGhostProperties();
		this.ghostProperties.put("Tag", "Other");
	}

	
	@Override
	public StringBuffer computeAssociations(StringBuffer buffer) {
		// TODO: internalize this generation method, use a declarative map instead
		buffer.append(association("Tag", "Blue to Red")).append("\n");
		buffer.append(association("Local G Count", "Twist")).append("\n");
		buffer.append(association("NVI", "height")).append("\n");
		buffer.append(association("DIT", "sort")).append("\n");
		return buffer;
	}

	@Override
	public StringBuffer computeDescriptions() {
		StringBuffer buffer = super.computeDescriptions();
		buffer.append(propDescription("Tag", "integer", "", "0", "4"));
		return buffer;
	}

	@Override
	public StringBuffer computeProperties(Map record, StringBuffer props) {
		super.computeProperties(record, props);
		props.append(property("Tag", tagCode(record)));
		return props;
	}

	public String tagCode(Map record) {
		String tag = (String) record.get("Tag");
		if (tag == "PureExtender") {
			return "4";
		}
		else if (tag == "Extender") {
			return "3";
		}
		else if (tag == "Other") {
			return "2";
		}
		else if (tag == "Overrider") {
			return "1";
		}
		else if (tag == "PureOverrider") {
			return "0";
		}
		return "2";
	}
	
	
}
