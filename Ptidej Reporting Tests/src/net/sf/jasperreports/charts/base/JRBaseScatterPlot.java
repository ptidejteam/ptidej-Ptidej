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

import net.sf.jasperreports.charts.JRScatterPlot;
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
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRBaseScatterPlot.java,v 1.1 2008/09/29 16:20:32 guehene Exp $ 
 */
public class JRBaseScatterPlot extends JRBaseChartPlot implements JRScatterPlot {
	
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_SHOW_LINES = "showLines";
	
	public static final String PROPERTY_SHOW_SHAPES = "showShapes";
	
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
	
	boolean isShowShapes = true;
	boolean isShowLines = true;
	
	
	/**
	 * 
	 */
	public JRBaseScatterPlot(JRChartPlot scattedPlot, JRChart chart){
		super(scattedPlot, chart);
	}

	/**
	 * 
	 */
	public JRBaseScatterPlot( JRScatterPlot scattedPlot, JRBaseObjectFactory factory ){
		super( scattedPlot, factory );
		
		this.isShowShapes = scattedPlot.isShowShapes();
		this.isShowLines = scattedPlot.isShowLines();
		
		this.xAxisLabelExpression = factory.getExpression( scattedPlot.getXAxisLabelExpression() );
		this.xAxisLabelFont = new JRBaseFont(null, null, scattedPlot.getChart(), scattedPlot.getXAxisLabelFont());
		this.xAxisLabelColor = scattedPlot.getOwnXAxisLabelColor();
		this.xAxisTickLabelFont = new JRBaseFont(null, null, scattedPlot.getChart(), scattedPlot.getXAxisTickLabelFont());
		this.xAxisTickLabelColor = scattedPlot.getOwnXAxisTickLabelColor();
		this.xAxisTickLabelMask = scattedPlot.getXAxisTickLabelMask();
		this.xAxisLineColor = scattedPlot.getXAxisLineColor();
		
		this.yAxisLabelExpression = factory.getExpression( scattedPlot.getYAxisLabelExpression() );
		this.yAxisLabelFont = new JRBaseFont(null, null, scattedPlot.getChart(), scattedPlot.getYAxisLabelFont());
		this.yAxisLabelColor = scattedPlot.getOwnYAxisLabelColor();
		this.yAxisTickLabelFont = new JRBaseFont(null, null, scattedPlot.getChart(), scattedPlot.getYAxisTickLabelFont());
		this.yAxisTickLabelColor = scattedPlot.getOwnYAxisTickLabelColor();
		this.yAxisTickLabelMask = scattedPlot.getYAxisTickLabelMask();
		this.yAxisLineColor = scattedPlot.getYAxisLineColor();
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
	public JRExpression getYAxisLabelExpression() {
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
	public boolean isShowShapes(){
		return this.isShowShapes;
	}
	
	/**
	 * 
	 */
	public boolean isShowLines(){
		return this.isShowLines;
	}
	
	/**
	 * 
	 */
	public void setShowShapes( boolean value ){
		boolean old = this.isShowShapes;
		this.isShowShapes = value;
		getEventSupport().firePropertyChange(PROPERTY_SHOW_SHAPES, old, this.isShowShapes);
	}
	
	/**
	 * 
	 */
	public void setShowLines( boolean value ){
		boolean old = this.isShowLines;
		this.isShowLines = value;
		getEventSupport().firePropertyChange(PROPERTY_SHOW_LINES, old, this.isShowLines);
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
		JRBaseScatterPlot clone = (JRBaseScatterPlot)super.clone(parentChart);
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
