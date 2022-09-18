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
package net.sf.jasperreports.charts.design;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.charts.JRCategoryDataset;
import net.sf.jasperreports.charts.JRCategorySeries;
import net.sf.jasperreports.engine.JRChartDataset;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.design.JRDesignChartDataset;
import net.sf.jasperreports.engine.design.JRVerifier;



/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRDesignCategoryDataset.java,v 1.1 2008/09/29 16:21:32 guehene Exp $
 */
public class JRDesignCategoryDataset extends JRDesignChartDataset implements JRCategoryDataset
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_CATEGORY_SERIES = "categorySeries";

	private List categorySeriesList = new ArrayList();


	/**
	 *
	 */
	public JRDesignCategoryDataset(JRChartDataset dataset)
	{
		super(dataset);
	}


	/**
	 *
	 */
	public JRCategorySeries[] getSeries()
	{
		JRCategorySeries[] categorySeriesArray = new JRCategorySeries[this.categorySeriesList.size()];
		
		this.categorySeriesList.toArray(categorySeriesArray);

		return categorySeriesArray;
	}
	

	/**
	 * 
	 */
	public List getSeriesList()
	{
		return this.categorySeriesList;
	}

	
	/**
	 *
	 */
	public void addCategorySeries(JRCategorySeries categorySeries)
	{
		this.categorySeriesList.add(categorySeries);
		getEventSupport().fireCollectionElementAddedEvent(PROPERTY_CATEGORY_SERIES, 
				categorySeries, this.categorySeriesList.size() - 1);
	}
	

	/**
	 *
	 */
	public JRCategorySeries removeCategorySeries(JRCategorySeries categorySeries)
	{
		if (categorySeries != null)
		{
			int idx = this.categorySeriesList.indexOf(categorySeries);
			if (idx >= 0)
			{
				this.categorySeriesList.remove(idx);
				getEventSupport().fireCollectionElementRemovedEvent(PROPERTY_CATEGORY_SERIES, 
						categorySeries, idx);
			}
		}
		
		return categorySeries;
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
	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}


	public void validate(JRVerifier verifier)
	{
		verifier.verify(this);
	}


	/**
	 * 
	 */
	public Object clone() 
	{
		JRDesignCategoryDataset clone = (JRDesignCategoryDataset)super.clone();
		
		if (this.categorySeriesList != null)
		{
			clone.categorySeriesList = new ArrayList(this.categorySeriesList.size());
			for(int i = 0; i < this.categorySeriesList.size(); i++)
			{
				clone.categorySeriesList.add(((JRCategorySeries)this.categorySeriesList.get(i)).clone());
			}
		}

		return clone;
	}
	
}
