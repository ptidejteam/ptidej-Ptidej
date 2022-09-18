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

import net.sf.jasperreports.charts.JRTimePeriodSeries;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;

/**
 * @author Flavius Sana (flavius_sana@users.sourceforge.net)
 * @version $Id: JRBaseTimePeriodSeries.java,v 1.1 2008/09/29 16:20:32 guehene Exp $
 */
public class JRBaseTimePeriodSeries implements JRTimePeriodSeries, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 608;// too late to replace this now
	
	protected JRExpression seriesExpression;
	
	protected JRExpression startDateExpression;
	
	protected JRExpression endDateExpression;
	
	protected JRExpression valueExpression;
	
	protected JRExpression labelExpression;
	
	protected JRHyperlink itemHyperlink;
	
	
	protected JRBaseTimePeriodSeries(){
	}
	
	public JRBaseTimePeriodSeries( JRTimePeriodSeries timePeriodSeries, JRBaseObjectFactory factory ){
		factory.put( timePeriodSeries, factory );
		
		this.seriesExpression = factory.getExpression( timePeriodSeries.getSeriesExpression() );
		this.startDateExpression = factory.getExpression( timePeriodSeries.getStartDateExpression() );
		this.endDateExpression = factory.getExpression( timePeriodSeries.getEndDateExpression() );
		this.valueExpression = factory.getExpression( timePeriodSeries.getValueExpression() );
		this.labelExpression = factory.getExpression( timePeriodSeries.getLabelExpression() );
		this.itemHyperlink = factory.getHyperlink(timePeriodSeries.getItemHyperlink());
	}
	
	public JRExpression getSeriesExpression(){
		return this.seriesExpression;
	}
	
	public JRExpression getStartDateExpression(){
		return this.startDateExpression;
	}
	
	public JRExpression getEndDateExpression(){
		return this.endDateExpression;
	}
	
	public JRExpression getValueExpression(){
		return this.valueExpression;
	}
	
	public JRExpression getLabelExpression(){
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
		JRBaseTimePeriodSeries clone = null;
		
		try
		{
			clone = (JRBaseTimePeriodSeries)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new JRRuntimeException(e);
		}

		if (this.seriesExpression != null)
		{
			clone.seriesExpression = (JRExpression)this.seriesExpression.clone();
		}
		if (this.startDateExpression != null)
		{
			clone.startDateExpression = (JRExpression)this.startDateExpression.clone();
		}
		if (this.endDateExpression != null)
		{
			clone.endDateExpression = (JRExpression)this.endDateExpression.clone();
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
