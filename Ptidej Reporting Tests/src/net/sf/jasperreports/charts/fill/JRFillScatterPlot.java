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

import java.awt.Color;

import net.sf.jasperreports.charts.JRScatterPlot;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.base.JRBaseFont;
import net.sf.jasperreports.engine.fill.JRFillChartPlot;
import net.sf.jasperreports.engine.fill.JRFillObjectFactory;
import net.sf.jasperreports.engine.util.JRStyleResolver;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRFillScatterPlot.java,v 1.1 2008/09/29 16:21:41 guehene Exp $
 */
public class JRFillScatterPlot extends JRFillChartPlot implements JRScatterPlot {
	
	/**
	 *
	 */
	protected JRFont xAxisLabelFont = null;
	protected Color xAxisLabelColor = null;
	protected JRFont xAxisTickLabelFont = null;
	protected Color xAxisTickLabelColor = null;
	protected Color xAxisLineColor = null;

	protected JRFont yAxisLabelFont = null;
	protected Color yAxisLabelColor = null;
	protected JRFont yAxisTickLabelFont = null;
	protected Color yAxisTickLabelColor = null;
	protected Color yAxisLineColor = null;

	
	/**
	 *
	 */
	public JRFillScatterPlot( JRScatterPlot plot, JRFillObjectFactory factory ){
		super( plot, factory );
		
		this.xAxisLabelFont = new JRBaseFont(null, null, plot.getChart(), plot.getXAxisLabelFont());
		this.xAxisLabelColor = plot.getOwnXAxisLabelColor();
		this.xAxisTickLabelFont = new JRBaseFont(null, null, plot.getChart(), plot.getXAxisTickLabelFont());
		this.xAxisTickLabelColor = plot.getOwnXAxisTickLabelColor();
		this.xAxisLineColor = plot.getXAxisLineColor();
		
		this.yAxisLabelFont = new JRBaseFont(null, null, plot.getChart(), plot.getYAxisLabelFont());
		this.yAxisLabelColor = plot.getOwnYAxisLabelColor();
		this.yAxisTickLabelFont = new JRBaseFont(null, null, plot.getChart(), plot.getYAxisTickLabelFont());
		this.yAxisTickLabelColor = plot.getOwnYAxisTickLabelColor();
		this.yAxisLineColor = plot.getYAxisLineColor();
	}
	
	/**
	 *
	 */
	public JRExpression getXAxisLabelExpression()
	{
		return ((JRScatterPlot)this.parent).getXAxisLabelExpression();
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
		return ((JRScatterPlot)this.parent).getXAxisTickLabelMask();
	}

	/**
	 *
	 */
	public void setXAxisTickLabelMask(String mask)
	{
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
	public JRExpression getYAxisLabelExpression()
	{
		return ((JRScatterPlot)this.parent).getYAxisLabelExpression();
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
		return ((JRScatterPlot)this.parent).getYAxisTickLabelMask();
	}

	/**
	 *
	 */
	public void setYAxisTickLabelMask(String mask)
	{
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
		return ((JRScatterPlot)this.parent).isShowShapes();
	}
	
	/**
	 *
	 */
	public void setShowShapes( boolean value ){
	}
	
	/**
	 *
	 */
	public boolean isShowLines(){
		return ((JRScatterPlot)this.parent).isShowLines();
	}
	
	/**
	 *
	 */
	public void setShowLines( boolean value ){
	}
	
}
