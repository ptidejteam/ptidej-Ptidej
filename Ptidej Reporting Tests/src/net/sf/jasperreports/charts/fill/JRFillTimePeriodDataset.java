/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * JasperReports - Free Java report-generating library.
 * Copyright (C) 2001-2006 JasperSoft Corporation http://www.jaspersoft.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 * 
 * JasperSoft Corporation
 * 303 Second Street, Suite 450 North
 * San Francisco, CA 94107
 * http://www.jaspersoft.com
 */
package net.sf.jasperreports.charts.fill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.charts.JRTimePeriodDataset;
import net.sf.jasperreports.charts.JRTimePeriodSeries;
import net.sf.jasperreports.charts.util.TimePeriodDatasetLabelGenerator;
import net.sf.jasperreports.engine.JRChartDataset;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.design.JRVerifier;
import net.sf.jasperreports.engine.fill.JRCalculator;
import net.sf.jasperreports.engine.fill.JRExpressionEvalException;
import net.sf.jasperreports.engine.fill.JRFillChartDataset;
import net.sf.jasperreports.engine.fill.JRFillObjectFactory;

import org.jfree.data.general.Dataset;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.time.TimePeriodValues;
import org.jfree.data.time.TimePeriodValuesCollection;


/**
 * @author Flavius Sana (flavius_sana@users.sourceforge.net)
 * @version $Id: JRFillTimePeriodDataset.java,v 1.1 2008/09/29 16:21:42 guehene Exp $
 */
public class JRFillTimePeriodDataset extends JRFillChartDataset implements JRTimePeriodDataset
{

	/**
	 * 
	 */
	protected JRFillTimePeriodSeries[] timePeriodSeries = null;

	private List seriesNames = null;
	private Map seriesMap = null;
	private Map labelsMap = null;
	private Map itemHyperlinks;


	/**
	 * 
	 */
	public JRFillTimePeriodDataset(
		JRTimePeriodDataset timePeriodDataset,
		JRFillObjectFactory factory
		)
	{
		super(timePeriodDataset, factory);

		JRTimePeriodSeries[] srcTimePeriodSeries = timePeriodDataset.getSeries();
		if (srcTimePeriodSeries != null && srcTimePeriodSeries.length > 0)
		{
			this.timePeriodSeries = new JRFillTimePeriodSeries[srcTimePeriodSeries.length];
			for (int i = 0; i < this.timePeriodSeries.length; i++)
			{
				this.timePeriodSeries[i] = 
					(JRFillTimePeriodSeries)factory.getTimePeriodSeries(srcTimePeriodSeries[i]);
			}
		}
	}

	public JRTimePeriodSeries[] getSeries()
	{
		return this.timePeriodSeries;
	}

	protected void customInitialize()
	{
		this.seriesNames = null;
		this.seriesMap = null;
		this.labelsMap = null;
		this.itemHyperlinks = null;
	}

	protected void customEvaluate(JRCalculator calculator)
			throws JRExpressionEvalException
	{
		if (this.timePeriodSeries != null && this.timePeriodSeries.length > 0)
		{
			for (int i = 0; i < this.timePeriodSeries.length; i++)
			{
				this.timePeriodSeries[i].evaluate(calculator);
			}
		}
	}

	protected void customIncrement()
	{
		if (this.timePeriodSeries != null && this.timePeriodSeries.length > 0)
		{
			if (this.seriesNames == null)
			{
				this.seriesNames = new ArrayList();
				this.seriesMap = new HashMap();
				this.labelsMap = new HashMap();
				this.itemHyperlinks = new HashMap();
			}

			for (int i = 0; i < this.timePeriodSeries.length; i++)
			{
				JRFillTimePeriodSeries crtTimePeriodSeries = this.timePeriodSeries[i];

				Comparable seriesName = crtTimePeriodSeries.getSeries();
				if (seriesName == null)
				{
					throw new JRRuntimeException("Time period series name is null.");
				}

				TimePeriodValues timePeriodValues = (TimePeriodValues)this.seriesMap.get(seriesName);
				if (timePeriodValues == null)
				{
					timePeriodValues = new TimePeriodValues(seriesName.toString());
					this.seriesNames.add(seriesName);
					this.seriesMap.put(seriesName, timePeriodValues);
				}

				SimpleTimePeriod stp = 
					new SimpleTimePeriod(
						crtTimePeriodSeries.getStartDate(), 
						crtTimePeriodSeries.getEndDate()
						);
				
				timePeriodValues.add(stp, crtTimePeriodSeries.getValue());
				
				if (crtTimePeriodSeries.getLabelExpression() != null)
				{
					Map seriesLabels = (Map)this.labelsMap.get(seriesName);
					if (seriesLabels == null)
					{
						seriesLabels = new HashMap();
						this.labelsMap.put(seriesName, seriesLabels);
					}
					
					seriesLabels.put(stp, crtTimePeriodSeries.getLabel());
				}
				
				if (crtTimePeriodSeries.hasItemHyperlink())
				{
					Map seriesLinks = (Map) this.itemHyperlinks.get(seriesName);
					if (seriesLinks == null)
					{
						seriesLinks = new HashMap();
						this.itemHyperlinks.put(seriesName, seriesLinks);
					}
					
					seriesLinks.put(stp, crtTimePeriodSeries.getPrintItemHyperlink());
				}
			}
		}
	}

	public Dataset getCustomDataset()
	{
		TimePeriodValuesCollection dataset = new TimePeriodValuesCollection();
		if (this.seriesNames != null)
		{
			for(int i = 0; i < this.seriesNames.size(); i++)
			{
				Comparable seriesName = (Comparable)this.seriesNames.get(i);
				dataset.addSeries((TimePeriodValues)this.seriesMap.get(seriesName));
			}
		}
		return dataset;
	}

	/**
	 * 
	 */
	public byte getDatasetType()
	{
		return JRChartDataset.TIMEPERIOD_DATASET;
	}

	/**
	 * 
	 */
	public TimePeriodDatasetLabelGenerator getLabelGenerator()
	{
		return new TimePeriodDatasetLabelGenerator(this.labelsMap);
	}

	/**
	 * 
	 */
	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}
	
	
	public boolean hasItemHyperlinks()
	{
		boolean foundLinks = false;
		if (this.timePeriodSeries != null && this.timePeriodSeries.length > 0)
		{
			for (int i = 0; i < this.timePeriodSeries.length && !foundLinks; i++)
			{
				foundLinks = this.timePeriodSeries[i].hasItemHyperlink();
			}
		}
		return foundLinks;
	}
	
	
	public Map getItemHyperlinks()
	{
		return this.itemHyperlinks;
	}


	public void validate(JRVerifier verifier)
	{
		verifier.verify(this);
	}

}
