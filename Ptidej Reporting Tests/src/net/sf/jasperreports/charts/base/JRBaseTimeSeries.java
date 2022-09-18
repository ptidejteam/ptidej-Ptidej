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

import java.io.Serializable;

import net.sf.jasperreports.charts.JRTimeSeries;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRBaseTimeSeries.java,v 1.1 2008/09/29 16:20:32 guehene Exp $
 */
public class JRBaseTimeSeries implements JRTimeSeries, Serializable
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	protected JRExpression seriesExpression = null;
	protected JRExpression timePeriodExpression = null;
	protected JRExpression valueExpression = null;
	protected JRExpression labelExpression = null;
	protected JRHyperlink itemHyperlink;

	
	/**
	 *
	 */
	protected JRBaseTimeSeries()
	{
	}
	
	
	/**
	 *
	 */
	public JRBaseTimeSeries(JRTimeSeries timeSeries, JRBaseObjectFactory factory)
	{
		factory.put(timeSeries, this);

		this.seriesExpression = factory.getExpression(timeSeries.getSeriesExpression());
		this.timePeriodExpression = factory.getExpression(timeSeries.getTimePeriodExpression());
		this.valueExpression = factory.getExpression(timeSeries.getValueExpression());
		this.labelExpression = factory.getExpression(timeSeries.getLabelExpression());
		this.itemHyperlink = factory.getHyperlink(timeSeries.getItemHyperlink());
	}

	
	/**
	 *
	 */
	public JRExpression getSeriesExpression()
	{
		return this.seriesExpression;
	}
		
	/**
	 *
	 */
	public JRExpression getTimePeriodExpression()
	{
		return this.timePeriodExpression;
	}
		
	/**
	 *
	 */
	public JRExpression getValueExpression()
	{
		return this.valueExpression;
	}
		
	/**
	 *
	 */
	public JRExpression getLabelExpression()
	{
		return this.labelExpression;
	}

	
	public JRHyperlink getItemHyperlink()
	{
		return this.itemHyperlink;
	}
		
	/**
	 * 
	 */
	public Object clone() 
	{
		JRBaseTimeSeries clone = null;
		
		try
		{
			clone = (JRBaseTimeSeries)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new JRRuntimeException(e);
		}

		if (this.seriesExpression != null)
		{
			clone.seriesExpression = (JRExpression)this.seriesExpression.clone();
		}
		if (this.timePeriodExpression != null)
		{
			clone.timePeriodExpression = (JRExpression)this.timePeriodExpression.clone();
		}
		if (this.valueExpression != null)
		{
			clone.valueExpression = (JRExpression)this.valueExpression.clone();
		}
		if (this.labelExpression != null)
		{
			clone.labelExpression = (JRExpression)this.labelExpression.clone();
		}
		if (this.itemHyperlink != null)
		{
			clone.itemHyperlink = (JRHyperlink)this.itemHyperlink.clone();
		}
		
		return clone;
	}
}
