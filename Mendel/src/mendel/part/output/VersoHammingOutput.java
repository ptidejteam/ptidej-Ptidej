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
public class VersoHammingOutput extends VersoTagOutput {
	
	/**
	 * 
	 */
	public VersoHammingOutput() {
	}

	public void initGhostProperties() {
		super.initGhostProperties();
		this.ghostProperties.put("Hamming", "0");
		this.ghostProperties.put("Jaccard", "0");
	}

	
	@Override
	public StringBuffer computeAssociations(StringBuffer buffer) {
		buffer.append(association("Tag", "Blue to Red")).append("\n");
		buffer.append(association("Local G Count", "height")).append("\n");
		buffer.append(association("Jaccard", "Twist")).append("\n");
		buffer.append(association("DIT", "sort")).append("\n");
		return buffer;
	}

	@Override
	public StringBuffer computeDescriptions() {
		StringBuffer buffer = super.computeDescriptions();
//		String[] minMax = findMinMax(getData(), "Hamming");
		String limit = findUpperLimit(getData(), "Hamming");
		buffer.append(propDescription("Hamming", "integer", "", "0", limit));
		buffer.append(propDescription("Jaccard", "double", "", "0.0", "1.0"));
		return buffer;
	}

	@Override
	public StringBuffer computeProperties(Map record, StringBuffer props) {
		super.computeProperties(record, props);
		props.append(property(record, "Hamming"));
		props.append(property(record, "Jaccard"));
		return props;
	}
	
}
