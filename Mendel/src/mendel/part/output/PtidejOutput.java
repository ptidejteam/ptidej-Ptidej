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

import java.util.Iterator;
import java.util.Map;

import mendel.Util;


/**
 * Suffix: ini.
 * 
 * Initializating Properties:
 * - see superclass for properties
 * 
 * Input: Map
 * Output: StringBuffer
 * 
 * @author Simon Denier
 * @since Feb 15, 2008
 * 
 */
public class PtidejOutput extends FileOutput {

	private int recordCount;
	
	private Integer colorIntensity;
	
	public PtidejOutput() {
		setSuffix(".ini");
		this.recordCount = 0;
		this.colorIntensity = 100;		
	}

	
	@Override
	public Object compute(Object data) {
		this.recordCount++;
		Map record = (Map) data;
		Number colorInt = (Number) ((record.get("ColorInt")==null)? this.colorIntensity : record.get("ColorInt")); 
		StringBuffer recBuf = new StringBuffer();
		for (Iterator it = record.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			// TODO: do not put ColorInt as attribute
			recBuf.append(this.recordCount + "." + colorInt + "." + Util.escape(key));
			recBuf.append(" = " + record.get(key).toString() + "\n"); 			
		}
		return super.compute(recBuf);
	}


	public int getRecordCount() {
		return this.recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}


	public int getColorIntensity() {
		return this.colorIntensity;
	}


	public void setColorIntensity(int colorIntensity) {
		this.colorIntensity = colorIntensity;
	}
	

}
