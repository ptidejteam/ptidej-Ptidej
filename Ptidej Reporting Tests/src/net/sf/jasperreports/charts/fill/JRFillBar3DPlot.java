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

import net.sf.jasperreports.charts.JRBar3DPlot;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.base.JRBaseFont;
import net.sf.jasperreports.engine.fill.JRFillChartPlot;
import net.sf.jasperreports.engine.fill.JRFillObjectFactory;
import net.sf.jasperreports.engine.util.JRStyleResolver;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRFillBar3DPlot.java,v 1.1 2008/09/29 16:21:42 guehene Exp $ 
 */
public class JRFillBar3DPlot extends JRFillChartPlot implements JRBar3DPlot 
{
	
	/**
	 *
	 */
	protected JRFont categoryAxisLabelFont = null;
	protected Color categoryAxisLabelColor = null;
	protected JRFont categoryAxisTickLabelFont = null;
	protected Color categoryAxisTickLabelColor = null;
	protected Color categoryAxisLineColor = null;

	protected JRFont valueAxisLabelFont = null;
	protected Color valueAxisLabelColor = null;
	protected JRFont valueAxisTickLabelFont = null;
	protected Color valueAxisTickLabelColor = null;
	protected Color valueAxisLineColor = null;

	
	/**
	 *
	 */
	public JRFillBar3DPlot( JRBar3DPlot barPlot, JRFillObjectFactory factory ){
		super( barPlot, factory );

		this.categoryAxisLabelFont = new JRBaseFont(null, null, barPlot.getChart(), barPlot.getCategoryAxisLabelFont()); 
		this.categoryAxisLabelColor = barPlot.getOwnCategoryAxisLabelColor();
		this.categoryAxisTickLabelFont = new JRBaseFont(null, null, barPlot.getChart(), barPlot.getCategoryAxisTickLabelFont());
		this.categoryAxisTickLabelColor = barPlot.getOwnCategoryAxisTickLabelColor();
		this.categoryAxisLineColor = barPlot.getOwnCategoryAxisLineColor();
		
		this.valueAxisLabelFont = new JRBaseFont(null, null, barPlot.getChart(), barPlot.getValueAxisLabelFont());
		this.valueAxisLabelColor = barPlot.getOwnValueAxisLabelColor();
		this.valueAxisTickLabelFont = new JRBaseFont(null, null, barPlot.getChart(), barPlot.getValueAxisTickLabelFont());
		this.valueAxisTickLabelColor = barPlot.getOwnValueAxisTickLabelColor();
		this.valueAxisLineColor = barPlot.getOwnValueAxisLineColor();
	}
	
	/**
	 *
	 */
	public JRExpression getCategoryAxisLabelExpression()
	{
		return ((JRBar3DPlot)this.parent).getCategoryAxisLabelExpression();
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
	public void setCategoryAxisLabelFont(JRFont font)
	{
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
	public void setCategoryAxisLabelColor(Color color)
	{
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
	public void setCategoryAxisTickLabelFont(JRFont font)
	{
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
	public void setCategoryAxisTickLabelColor(Color color)
	{
	}

	/**
	 *
	 */
	public String getCategoryAxisTickLabelMask()
	{
		return ((JRBar3DPlot)this.parent).getCategoryAxisTickLabelMask();
	}

	/**
	 *
	 */
	public void setCategoryAxisTickLabelMask(String mask)
	{
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
	public void setCategoryAxisLineColor(Color color)
	{
	}

	/**
	 *
	 */
	public JRExpression getValueAxisLabelExpression()
	{
		return ((JRBar3DPlot)this.parent).getValueAxisLabelExpression();
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
	public void setValueAxisLabelFont(JRFont font)
	{
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
	public void setValueAxisLabelColor(Color color)
	{
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
	public void setValueAxisTickLabelFont(JRFont font)
	{
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
	public void setValueAxisTickLabelColor(Color color)
	{
	}

	/**
	 *
	 */
	public String getValueAxisTickLabelMask()
	{
		return ((JRBar3DPlot)this.parent).getValueAxisTickLabelMask();
	}

	/**
	 *
	 */
	public void setValueAxisTickLabelMask(String mask)
	{
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
	public void setValueAxisLineColor(Color color)
	{
	}
	
	/**
	 *
	 */
	public double getXOffset(){
		return ((JRBar3DPlot)this.parent).getXOffset();
	}
	
	/**
	 *
	 */
	public void setXOffset( double xOffset ){
	}
	
	/**
	 *
	 */
	public double getYOffset(){
		return ((JRBar3DPlot)this.parent).getYOffset();
	}
	
	/**
	 *
	 */
	public void setYOffset( double yOffset ){
	}
	
	/**
	 *
	 */
	public boolean isShowLabels(){
		return ((JRBar3DPlot)this.parent).isShowLabels();
	}
	
	/**
	 *
	 */
	public void setShowLabels( boolean isShowLabels ){
	}
}
