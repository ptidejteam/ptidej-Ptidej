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

import java.awt.Color;

import net.sf.jasperreports.charts.JRAreaPlot;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.base.JRBaseChartPlot;
import net.sf.jasperreports.engine.base.JRBaseFont;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;
import net.sf.jasperreports.engine.util.JRStyleResolver;

/**
 * @author Flavius Sana (flavius_sana@users.sourceforge.net)
 * @version $Id: JRBaseAreaPlot.java,v 1.1 2008/09/29 16:20:33 guehene Exp $
 */
public class JRBaseAreaPlot extends JRBaseChartPlot implements JRAreaPlot {

	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	protected JRExpression categoryAxisLabelExpression = null;
	protected JRFont categoryAxisLabelFont = null;
	protected Color categoryAxisLabelColor = null;
	protected JRFont categoryAxisTickLabelFont = null;
	protected Color categoryAxisTickLabelColor = null;
	protected String categoryAxisTickLabelMask = null;
	protected Color categoryAxisLineColor = null;

	protected JRExpression valueAxisLabelExpression = null;
	protected JRFont valueAxisLabelFont = null;
	protected Color valueAxisLabelColor = null;
	protected JRFont valueAxisTickLabelFont = null;
	protected Color valueAxisTickLabelColor = null;
	protected String valueAxisTickLabelMask = null;
	protected Color valueAxisLineColor = null;
	

	/**
	 * 
	 */
	public JRBaseAreaPlot( JRChartPlot areaPlot, JRChart chart){
		super( areaPlot, chart);
	}


	/**
	 * 
	 */
	public JRBaseAreaPlot( JRAreaPlot areaPlot, JRBaseObjectFactory factory ){
		super( areaPlot, factory );
		
		this.categoryAxisLabelExpression = factory.getExpression( areaPlot.getCategoryAxisLabelExpression() );
		this.categoryAxisLabelFont = new JRBaseFont(null, null, areaPlot.getChart(), areaPlot.getCategoryAxisLabelFont()); 
		this.categoryAxisLabelColor = areaPlot.getOwnCategoryAxisLabelColor();
		this.categoryAxisTickLabelFont = new JRBaseFont(null, null, areaPlot.getChart(), areaPlot.getCategoryAxisTickLabelFont());
		this.categoryAxisTickLabelColor = areaPlot.getOwnCategoryAxisTickLabelColor();
		this.categoryAxisTickLabelMask = areaPlot.getCategoryAxisTickLabelMask();
		this.categoryAxisLineColor = areaPlot.getOwnCategoryAxisLineColor();
		
		this.valueAxisLabelExpression = factory.getExpression( areaPlot.getValueAxisLabelExpression() );
		this.valueAxisLabelFont = new JRBaseFont(null, null, areaPlot.getChart(), areaPlot.getValueAxisLabelFont());
		this.valueAxisLabelColor = areaPlot.getOwnValueAxisLabelColor();
		this.valueAxisTickLabelFont = new JRBaseFont(null, null, areaPlot.getChart(), areaPlot.getValueAxisTickLabelFont());
		this.valueAxisTickLabelColor = areaPlot.getOwnValueAxisTickLabelColor();
		this.valueAxisTickLabelMask = areaPlot.getValueAxisTickLabelMask();
		this.valueAxisLineColor = areaPlot.getOwnValueAxisLineColor();
	}
	
	/**
	 * 
	 */
	public JRExpression getCategoryAxisLabelExpression(){
		return this.categoryAxisLabelExpression;
	}
	
	/**
	 * 
	 */
	public JRFont getCategoryAxisLabelFont()
	{
		return this.categoryAxisLabelFont;
	}
	
	/**
	 * 
	 */
	public Color getCategoryAxisLabelColor()
	{
		return JRStyleResolver.getCategoryAxisLabelColor(this, this);
	}
	
	/**
	 * 
	 */
	public Color getOwnCategoryAxisLabelColor()
	{
		return this.categoryAxisLabelColor;
	}
	
	/**
	 * 
	 */
	public JRFont getCategoryAxisTickLabelFont()
	{
		return this.categoryAxisTickLabelFont;
	}
	
	/**
	 * 
	 */
	public Color getCategoryAxisTickLabelColor()
	{
		return JRStyleResolver.getCategoryAxisTickLabelColor(this, this);
	}

	/**
	 * 
	 */
	public Color getOwnCategoryAxisTickLabelColor()
	{
		return this.categoryAxisTickLabelColor;
	}

	/**
	 * 
	 */
	public String getCategoryAxisTickLabelMask()
	{
		return this.categoryAxisTickLabelMask;
	}

	/**
	 * 
	 */
	public Color getCategoryAxisLineColor()
	{
		return JRStyleResolver.getCategoryAxisLineColor(this, this);
	}
		
	/**
	 * 
	 */
	public Color getOwnCategoryAxisLineColor()
	{
		return this.categoryAxisLineColor;
	}
		
	/**
	 * 
	 */
	public JRExpression getValueAxisLabelExpression(){
		return this.valueAxisLabelExpression;
	}

	/**
	 * 
	 */
	public JRFont getValueAxisLabelFont()
	{
		return this.valueAxisLabelFont;
	}
	
	/**
	 * 
	 */
	public Color getValueAxisLabelColor()
	{
		return JRStyleResolver.getValueAxisLabelColor(this, this);
	}
	
	/**
	 * 
	 */
	public Color getOwnValueAxisLabelColor()
	{
		return this.valueAxisLabelColor;
	}
	
	/**
	 * 
	 */
	public JRFont getValueAxisTickLabelFont()
	{
		return this.valueAxisTickLabelFont;
	}
	
	/**
	 * 
	 */
	public Color getValueAxisTickLabelColor()
	{
		return JRStyleResolver.getValueAxisTickLabelColor(this, this);
	}

	/**
	 * 
	 */
	public Color getOwnValueAxisTickLabelColor()
	{
		return this.valueAxisTickLabelColor;
	}

	/**
	 * 
	 */
	public String getValueAxisTickLabelMask()
	{
		return this.valueAxisTickLabelMask;
	}

	/**
	 * 
	 */
	public Color getValueAxisLineColor()
	{
		return JRStyleResolver.getValueAxisLineColor(this, this);
	}
	
	/**
	 * 
	 */
	public Color getOwnValueAxisLineColor()
	{
		return this.valueAxisLineColor;
	}
	
	/**
	 *
	 */
	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}

	/**
	 *
	 */
	public Object clone(JRChart parentChart) 
	{
		JRBaseAreaPlot clone = (JRBaseAreaPlot)super.clone(parentChart);
		if (this.categoryAxisLabelExpression != null)
		{
			clone.categoryAxisLabelExpression = (JRExpression)this.categoryAxisLabelExpression.clone();
		}
		if (this.valueAxisLabelExpression != null)
		{
			clone.valueAxisLabelExpression = (JRExpression)this.valueAxisLabelExpression.clone();
		}
		return clone;
	}
}
