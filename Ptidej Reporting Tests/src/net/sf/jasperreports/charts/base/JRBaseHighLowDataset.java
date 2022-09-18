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
package net.sf.jasperreports.charts.base;

import net.sf.jasperreports.charts.JRHighLowDataset;
import net.sf.jasperreports.engine.JRChartDataset;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.base.JRBaseChartDataset;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;
import net.sf.jasperreports.engine.design.JRVerifier;


/**
 * @author Ionut Nedelcu (ionutned@users.sourceforge.net)
 * @version $Id: JRBaseHighLowDataset.java,v 1.1 2008/09/29 16:20:32 guehene Exp $
 */
public class JRBaseHighLowDataset extends JRBaseChartDataset implements JRHighLowDataset
{

	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	protected JRExpression seriesExpression;
	protected JRExpression dateExpression;
	protected JRExpression highExpression;
	protected JRExpression lowExpression;
	protected JRExpression openExpression;
	protected JRExpression closeExpression;
	protected JRExpression volumeExpression;
	private JRHyperlink itemHyperlink;


	/**
	 *
	 */
	public JRBaseHighLowDataset(JRChartDataset dataset)
	{
		super(dataset);
	}


	/**
	 *
	 */
	public JRBaseHighLowDataset(JRHighLowDataset dataset, JRBaseObjectFactory factory)
	{
		super(dataset, factory);

		this.seriesExpression = factory.getExpression(dataset.getSeriesExpression());
		this.dateExpression = factory.getExpression(dataset.getDateExpression());
		this.highExpression = factory.getExpression(dataset.getHighExpression());
		this.lowExpression = factory.getExpression(dataset.getLowExpression());
		this.openExpression = factory.getExpression(dataset.getOpenExpression());
		this.closeExpression = factory.getExpression(dataset.getCloseExpression());
		this.volumeExpression = factory.getExpression(dataset.getVolumeExpression());
		this.itemHyperlink = factory.getHyperlink(dataset.getItemHyperlink());
	}



	public JRExpression getSeriesExpression()
	{
		return this.seriesExpression;
	}


	public JRExpression getDateExpression()
	{
		return this.dateExpression;
	}


	public JRExpression getHighExpression()
	{
		return this.highExpression;
	}


	public JRExpression getLowExpression()
	{
		return this.lowExpression;
	}


	public JRExpression getOpenExpression()
	{
		return this.openExpression;
	}


	public JRExpression getCloseExpression()
	{
		return this.closeExpression;
	}

	public JRExpression getVolumeExpression()
	{
		return this.volumeExpression;
	}


	/* (non-Javadoc)
	 * @see net.sf.jasperreports.engine.JRChartDataset#getDatasetType()
	 */
	public byte getDatasetType() {
		return JRChartDataset.HIGHLOW_DATASET;
	}


	/**
	 *
	 */
	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}

	
	public JRHyperlink getItemHyperlink()
	{
		return this.itemHyperlink;
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
		JRBaseHighLowDataset clone = (JRBaseHighLowDataset)super.clone();
		
		if (this.seriesExpression != null)
		{
			clone.seriesExpression = (JRExpression)this.seriesExpression.clone();
		}
		if (this.dateExpression != null)
		{
			clone.dateExpression = (JRExpression)this.dateExpression.clone();
		}
		if (this.highExpression != null)
		{
			clone.highExpression = (JRExpression)this.highExpression.clone();
		}
		if (this.lowExpression != null)
		{
			clone.lowExpression = (JRExpression)this.lowExpression.clone();
		}
		if (this.openExpression != null)
		{
			clone.openExpression = (JRExpression)this.openExpression.clone();
		}
		if (this.closeExpression != null)
		{
			clone.closeExpression = (JRExpression)this.closeExpression.clone();
		}
		if (this.volumeExpression != null)
		{
			clone.volumeExpression = (JRExpression)this.volumeExpression.clone();
		}
		if (this.itemHyperlink != null)
		{
			clone.itemHyperlink = (JRHyperlink)this.itemHyperlink.clone();
		}
		
		return clone;
	}
}
