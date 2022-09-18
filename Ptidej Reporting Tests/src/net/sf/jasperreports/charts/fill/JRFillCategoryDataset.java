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

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.charts.JRCategoryDataset;
import net.sf.jasperreports.charts.JRCategorySeries;
import net.sf.jasperreports.charts.util.CategoryLabelGenerator;
import net.sf.jasperreports.engine.JRChartDataset;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.design.JRVerifier;
import net.sf.jasperreports.engine.fill.JRCalculator;
import net.sf.jasperreports.engine.fill.JRExpressionEvalException;
import net.sf.jasperreports.engine.fill.JRFillChartDataset;
import net.sf.jasperreports.engine.fill.JRFillObjectFactory;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRFillCategoryDataset.java,v 1.1 2008/09/29 16:21:42 guehene Exp $
 */
public class JRFillCategoryDataset extends JRFillChartDataset implements JRCategoryDataset
{

	/**
	 *
	 */
	protected JRFillCategorySeries[] categorySeries = null;

	private DefaultCategoryDataset dataset = null;
	private Map labelsMap = null;
	
	private Map itemHyperlinks;

	
	/**
	 *
	 */
	public JRFillCategoryDataset(
		JRCategoryDataset categoryDataset, 
		JRFillObjectFactory factory
		)
	{
		super(categoryDataset, factory);

		/*   */
		JRCategorySeries[] srcCategorySeries = categoryDataset.getSeries();
		if (srcCategorySeries != null && srcCategorySeries.length > 0)
		{
			this.categorySeries = new JRFillCategorySeries[srcCategorySeries.length];
			for(int i = 0; i < this.categorySeries.length; i++)
			{
				this.categorySeries[i] = (JRFillCategorySeries)factory.getCategorySeries(srcCategorySeries[i]);
			}
		}
	}
	
	
	/**
	 *
	 */
	public JRCategorySeries[] getSeries()
	{
		return this.categorySeries;
	}


	/**
	 *
	 */
	protected void customInitialize()
	{
		this.dataset = null;
		this.labelsMap = null;
		this.itemHyperlinks = null;
	}

	/**
	 *
	 */
	protected void customEvaluate(JRCalculator calculator) throws JRExpressionEvalException
	{
		if (this.categorySeries != null && this.categorySeries.length > 0)
		{
			for(int i = 0; i < this.categorySeries.length; i++)
			{
				this.categorySeries[i].evaluate(calculator);
			}
		}
	}

	/**
	 *
	 */
	protected void customIncrement()
	{
		if (this.categorySeries != null && this.categorySeries.length > 0)
		{
			if (this.dataset == null)
			{
				this.dataset = new DefaultCategoryDataset();
				this.labelsMap = new HashMap();
				this.itemHyperlinks = new HashMap();
			}
			
			for(int i = 0; i < this.categorySeries.length; i++)
			{
				JRFillCategorySeries crtCategorySeries = this.categorySeries[i];
				
				Comparable seriesName = crtCategorySeries.getSeries();
				if (seriesName == null)
				{
					throw new JRRuntimeException("Category series name is null.");
				}

				this.dataset.addValue(
					crtCategorySeries.getValue(), 
					crtCategorySeries.getSeries(), 
					crtCategorySeries.getCategory()
					);

				if (crtCategorySeries.getLabelExpression() != null)
				{
					Map seriesLabels = (Map)this.labelsMap.get(seriesName);
					if (seriesLabels == null)
					{
						seriesLabels = new HashMap();
						this.labelsMap.put(seriesName, seriesLabels);
					}
					
					seriesLabels.put(crtCategorySeries.getCategory(), crtCategorySeries.getLabel());
				}
				
				if (crtCategorySeries.hasItemHyperlinks())
				{
					Map seriesLinks = (Map) this.itemHyperlinks.get(seriesName);
					if (seriesLinks == null)
					{
						seriesLinks = new HashMap();
						this.itemHyperlinks.put(seriesName, seriesLinks);
					}
					seriesLinks.put(crtCategorySeries.getCategory(), crtCategorySeries.getPrintItemHyperlink());
				}
			}
		}
	}

	/**
	 *
	 */
	public Dataset getCustomDataset()
	{
		return this.dataset;
	}


	/**
	 * 
	 */
	public byte getDatasetType() {
		return JRChartDataset.CATEGORY_DATASET;
	}

	
	/**
	 *
	 */
	public CategoryLabelGenerator getLabelGenerator()
	{
		return new CategoryLabelGenerator(this.labelsMap);
	}


	/**
	 *
	 */
	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}

	
	public Map getItemHyperlinks()
	{
		return this.itemHyperlinks;
	}
	
	
	public boolean hasItemHyperlinks()
	{
		boolean foundLinks = false;
		if (this.categorySeries != null && this.categorySeries.length > 0)
		{
			for (int i = 0; i < this.categorySeries.length && !foundLinks; i++)
			{
				JRFillCategorySeries serie = this.categorySeries[i];
				foundLinks = serie.hasItemHyperlinks();
			}
		}
		return foundLinks;
	}


	public void validate(JRVerifier verifier)
	{
		verifier.verify(this);
	}

}
