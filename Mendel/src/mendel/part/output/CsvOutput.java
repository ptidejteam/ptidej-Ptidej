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

import mendel.MapToArray;


/**
 * Suffix: csv.
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
public class CsvOutput extends FileOutput {

	private MapToArray converter;
	
	public CsvOutput() {
		setSuffix(".csv");
	}
	
	
	@Override
	public void startMe() {
		super.startMe();
		String[] headers = (String[]) getDriver().getProperty("headers"); // TODO: another hard-coded parameter
		this.converter = new MapToArray(headers);
		write(buildCsvLine(headers));
	}


	@Override
	public Object compute(Object data) {
//		setupHeaders((Map) data);
		Object[] record = this.converter.record((Map) data);
		return super.compute(buildCsvLine(record));
	}


//	public void setupHeaders(Map data) {
//		if( this.converter==null ) {
//			this.converter = new MapToArray(data);
//			super.compute(buildCsvLine(this.converter.keys()));
//		}
//	}

	public StringBuffer buildCsvLine(Object[] record) {
		StringBuffer buffer = new StringBuffer(record[0].toString());
		for (int i = 1; i < record.length; i++) {
			buffer.append(',').append(record[i]);
		}
//		buffer.append("\n");
		return buffer;
	}

	
}
