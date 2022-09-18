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

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import mendel.Util;
import mendel.part.AbstractPart;

import org.jfree.data.statistics.Statistics;

/**
 * Initializating Properties:
 *
 * Input: Map<metric name, metric result>
 * Output: Map<metric name, metric result>
 * 
 * @author Simon Denier
 * @since Mar 17, 2008
 *
 */
public class ThresholdTool extends AbstractPart {
	// TODO: implements BatchOnly

	private String key;
	
	private String tag;
	
	private double threshold;
	

	public void initialize(String key, String tag) {
		setKey(key);
		setTag(tag);
	}
	
	@Override
	public void initialize(Properties prop) {
		super.initialize(prop);
		setKey(prop.getProperty("key"));
		setTag(prop.getProperty("tag"));
	}
	
	
	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}


	@Override
	public void run() {
		System.err.println("Warning! Should not run this tool until all input data is collected. False results");
		super.run();
	}

	
	@Override
	public void startMe() {
		super.startMe();
		// TODO: yet another bad-looking metadata management
		// shoud modify MapToArray to manage that
		// or use a Headers class as metadata
		String[] headers = (String[]) getDriver().getProperty("headers");
		String[] newHeaders = new String[headers.length + 1];
		System.arraycopy(headers, 0, newHeaders, 0, headers.length);
		newHeaders[headers.length] = "Name";
		getDriver().setProperty("headers", newHeaders);
	}


	public Object compute(Object data) {
		Map record = (Map) data;
		Double value = Double.valueOf((String) record.get(this.key));
		if( value.doubleValue() >= this.threshold ) {
			addTagToRecord(record);
			return record;
		} else
			return null;
	}

	public void addTagToRecord(Map record) {
		record.put("Name", this.tag);
	}

	
	@Override
	public void computeAll() {
		String[] data = selectDataByKey(getData());
		this.threshold = standardThreshold(Util.convertToNumbers(data));
		System.out.println(this.tag + " threshold: " + this.threshold); // TODO: add it as a record?
		super.computeAll();
	}
	
	public String[] selectDataByKey(Collection objects) {
		return (String[]) Util.selectDataByKey(objects, this.key).toArray(new String[0]);
	}

	
	public double standardThreshold(Number[] data) {
		double mean = Statistics.calculateMean(data);
		double dev = Statistics.getStdDev(data);
		return mean + dev;
	}

}
