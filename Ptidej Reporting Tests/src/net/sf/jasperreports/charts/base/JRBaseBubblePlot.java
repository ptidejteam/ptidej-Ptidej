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

import net.sf.jasperreports.charts.JRBubblePlot;
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

import org.jfree.chart.renderer.xy.XYBubbleRenderer;

/**
 * @author Flavius Sana (flavius_sana@users.sourceforge.net)
 * @version $Id: JRBaseBubblePlot.java,v 1.1 2008/09/29 16:20:32 guehene Exp $
 */
public class JRBaseBubblePlot extends JRBaseChartPlot implements JRBubblePlot {

	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_SCALE_TYPE = "scaleType";
	
	protected JRExpression xAxisLabelExpression = null;
	protected JRFont xAxisLabelFont = null;
	protected Color xAxisLabelColor = null;
	protected JRFont xAxisTickLabelFont = null;
	protected Color xAxisTickLabelColor = null;
	protected String xAxisTickLabelMask = null;
	protected Color xAxisLineColor = null;

	protected JRExpression yAxisLabelExpression = null;
	protected JRFont yAxisLabelFont = null;
	protected Color yAxisLabelColor = null;
	protected JRFont yAxisTickLabelFont = null;
	protected Color yAxisTickLabelColor = null;
	protected String yAxisTickLabelMask = null;
	protected Color yAxisLineColor = null;
	
	protected int scaleType = XYBubbleRenderer.SCALE_ON_RANGE_AXIS;
	
	
	/**
	 * 
	 */
	public JRBaseBubblePlot(JRChartPlot bubblePlot, JRChart chart){
		super(bubblePlot, chart);
	}


	/**
	 * 
	 */
	public JRBaseBubblePlot( JRBubblePlot bubblePlot, JRBaseObjectFactory factory ){
		super( bubblePlot, factory );
		
		this.scaleType = bubblePlot.getScaleType();
		
		this.xAxisLabelExpression = factory.getExpression( bubblePlot.getXAxisLabelExpression() );
		this.xAxisLabelFont = new JRBaseFont(null, null, bubblePlot.getChart(), bubblePlot.getXAxisLabelFont());
		this.xAxisLabelColor = bubblePlot.getOwnXAxisLabelColor();
		this.xAxisTickLabelFont = new JRBaseFont(null, null, bubblePlot.getChart(), bubblePlot.getXAxisTickLabelFont());
		this.xAxisTickLabelColor = bubblePlot.getOwnXAxisTickLabelColor();
		this.xAxisTickLabelMask = bubblePlot.getXAxisTickLabelMask();
		this.xAxisLineColor = bubblePlot.getXAxisLineColor();
		
		this.yAxisLabelExpression = factory.getExpression( bubblePlot.getYAxisLabelExpression() );
		this.yAxisLabelFont = new JRBaseFont(null, null, bubblePlot.getChart(), bubblePlot.getYAxisLabelFont());
		this.yAxisLabelColor = bubblePlot.getOwnYAxisLabelColor();
		this.yAxisTickLabelFont = new JRBaseFont(null, null, bubblePlot.getChart(), bubblePlot.getYAxisTickLabelFont());
		this.yAxisTickLabelColor = bubblePlot.getOwnYAxisTickLabelColor();
		this.yAxisTickLabelMask = bubblePlot.getYAxisTickLabelMask();
		this.yAxisLineColor = bubblePlot.getYAxisLineColor();
	}
	
	/**
	 * 
	 */
	public JRExpression getXAxisLabelExpression(){
		return this.xAxisLabelExpression;
	}
	
	/**
	 * 
	 */
	public JRFont getXAxisLabelFont()
	{
		return this.xAxisLabelFont;
	}
	
	/**
	 * 
	 */
	public Color getXAxisLabelColor()
	{
		return JRStyleResolver.getXAxisLabelColor(this, this);
	}
		
	/**
	 * 
	 */
	public Color getOwnXAxisLabelColor()
	{
		return this.xAxisLabelColor;
	}
		
	/**
	 * 
	 */
	public JRFont getXAxisTickLabelFont()
	{
		return this.xAxisTickLabelFont;
	}
	
	/**
	 * 
	 */
	public Color getXAxisTickLabelColor()
	{
		return JRStyleResolver.getXAxisTickLabelColor(this, this);
	}

	/**
	 * 
	 */
	public Color getOwnXAxisTickLabelColor()
	{
		return this.xAxisTickLabelColor;
	}

	/**
	 * 
	 */
	public String getXAxisTickLabelMask()
	{
		return this.xAxisTickLabelMask;
	}

	/**
	 * 
	 */
	public Color getXAxisLineColor()
	{
		return JRStyleResolver.getXAxisLineColor(this, this);
	}

	/**
	 * 
	 */
	public Color getOwnXAxisLineColor()
	{
		return this.xAxisLineColor;
	}

	/**
	 * 
	 */
	public JRExpression getYAxisLabelExpression(){
		return this.yAxisLabelExpression;
	}
	
	/**
	 * 
	 */
	public JRFont getYAxisLabelFont()
	{
		return this.yAxisLabelFont;
	}
	
	/**
	 * 
	 */
	public Color getYAxisLabelColor()
	{
		return JRStyleResolver.getYAxisLabelColor(this, this);
	}
	
	/**
	 * 
	 */
	public Color getOwnYAxisLabelColor()
	{
		return this.yAxisLabelColor;
	}
	
	/**
	 * 
	 */
	public JRFont getYAxisTickLabelFont()
	{
		return this.yAxisTickLabelFont;
	}
	
	/**
	 * 
	 */
	public Color getYAxisTickLabelColor()
	{
		return JRStyleResolver.getYAxisTickLabelColor(this, this);
	}
	
	/**
	 * 
	 */
	public Color getOwnYAxisTickLabelColor()
	{
		return this.yAxisTickLabelColor;
	}
	
	/**
	 * 
	 */
	public String getYAxisTickLabelMask()
	{
		return this.yAxisTickLabelMask;
	}

	/**
	 * 
	 */
	public Color getYAxisLineColor()
	{
		return JRStyleResolver.getYAxisLineColor(this, this);
	}
	
	/**
	 * 
	 */
	public Color getOwnYAxisLineColor()
	{
		return this.yAxisLineColor;
	}
	
	/**
	 * 
	 */
	public int getScaleType(){
		return this.scaleType;
	}
	
	/**
	 * 
	 */
	public void setScaleType( int scaleType ){
		int old = this.scaleType;
		this.scaleType = scaleType;
		getEventSupport().firePropertyChange(PROPERTY_SCALE_TYPE, (float) old, (float) this.scaleType);
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
		JRBaseBubblePlot clone = (JRBaseBubblePlot)super.clone(parentChart);
		if (this.xAxisLabelExpression != null)
		{
			clone.xAxisLabelExpression = (JRExpression)this.xAxisLabelExpression.clone();
		}
		if (this.yAxisLabelExpression != null)
		{
			clone.yAxisLabelExpression = (JRExpression)this.yAxisLabelExpression.clone();
		}
		return clone;
	}
}
