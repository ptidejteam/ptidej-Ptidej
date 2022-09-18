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
package mendel.part.tool;

import java.util.Map;
import java.util.Properties;

import mendel.part.AbstractPart;

/**
 * Initializating Properties:
 * - selectTag: the tag which points to entities to select (value "Y")
 *
 * Input: Map<metric name, metric result>
 * Output: Map<metric name, metric result>
 * 
 * @author Simon Denier
 * @since Mar 17, 2008
 *
 */
public class SelectionTool extends AbstractPart {
	
	private String tag;
	

	public void initialize(String tag) {
		setTag(tag);
	}
	
	@Override
	public void initialize(Properties prop) {
		super.initialize(prop);
		setTag(prop.getProperty("selectTag"));
	}
	
	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	

	public Object compute(Object data) {
		Map record = (Map) data;
		String bool = (String) record.get(this.tag);
		if( bool.equals("Y") ) {
			return record;
		} else
			return null;
	}

}
