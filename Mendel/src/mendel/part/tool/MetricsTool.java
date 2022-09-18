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

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import mendel.Driver;
import mendel.Util;
import mendel.metric.IEntityMetric;
import mendel.model.IEntity;
import mendel.part.AbstractPart;

/**
 * Initializating Properties:
 * - metrics: comma-separated list of qualified classnames of IEntityMetric
 *
 * Input: IEntity
 * Output: Map<metric name, metric result>
 * 
 * @author Simon Denier
 * @since Mar 17, 2008
 *
 */
public class MetricsTool extends AbstractPart {
	
	private IEntityMetric[] metrics;
	
	private String[] headers;
	
	/**
	 * 
	 */
	public MetricsTool() {
//		setMetrics(new IEntityMetric[0]);
	}


	/* (non-Javadoc)
	 * @see mendel.IPart#initialize(java.util.Properties)
	 */
	public void initialize(Properties properties) {
		String[] metricsClassNames = Util.extractValues(properties, "metrics");
		IEntityMetric[] metrics = new IEntityMetric[metricsClassNames.length];
		for (int i = 0; i < metricsClassNames.length; i++) {
			metrics[i] = (IEntityMetric) Util.createInstance(metricsClassNames[i]);
		}
		setMetrics(metrics);
	}
	

	@Override
	public void initialize(Driver driver) {
		super.initialize(driver);
		initializeHeaders();
	}
	
	protected void initializeHeaders() {
		this.headers = new String[this.metrics.length];
		for (int i = 0; i < this.metrics.length; i++) {
			this.headers[i] = this.metrics[i].getName();
		}
		getDriver().setProperty("headers", this.headers);
	}


	/**
	 * @param metrics the metrics to set
	 */
	public void setMetrics(IEntityMetric[] metrics) {
		this.metrics = metrics;
	}
	
	/**
	 * @return the metrics
	 */
	public IEntityMetric[] getMetrics() {
		return this.metrics;
	}
	
	public IEntityMetric metricAt(int i) {
		return getMetrics()[i];
	}


	public String[] getHeaders() {
		return this.headers;
	}
	
	public String headerAt(int i) {
		return getHeaders()[i];
	}
	
	public Object compute(Object entry) {
		Map record = new HashMap();
		for (int i = 0; i < getMetrics().length; i++) {
			record.put(headerAt(i), metricAt(i).compute((IEntity) entry));
		}
		return record;
	}

}
