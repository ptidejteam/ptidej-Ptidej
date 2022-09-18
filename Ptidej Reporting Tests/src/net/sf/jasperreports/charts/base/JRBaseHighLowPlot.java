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

import net.sf.jasperreports.charts.JRHighLowPlot;
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
 * @author Ionut Nedelcu (ionutned@users.sourceforge.net)
 * @version $Id: JRBaseHighLowPlot.java,v 1.1 2008/09/29 16:20:32 guehene Exp $
 */
public class JRBaseHighLowPlot extends JRBaseChartPlot implements JRHighLowPlot
{

	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_SHOW_CLOSE_TICKS = "showCloseTicks";
	
	public static final String PROPERTY_SHOW_OPEN_TICKS = "showOpenTicks";

	protected JRExpression timeAxisLabelExpression = null;
	protected JRFont timeAxisLabelFont = null;
	protected Color timeAxisLabelColor = null;
	protected JRFont timeAxisTickLabelFont = null;
	protected Color timeAxisTickLabelColor = null;
	protected String timeAxisTickLabelMask = null;
	protected Color timeAxisLineColor = null;

	protected JRExpression valueAxisLabelExpression = null;
	protected JRFont valueAxisLabelFont = null;
	protected Color valueAxisLabelColor = null;
	protected JRFont valueAxisTickLabelFont = null;
	protected Color valueAxisTickLabelColor = null;
	protected String valueAxisTickLabelMask = null;
	protected Color valueAxisLineColor = null;

	protected boolean isShowOpenTicks = false;
	protected boolean isShowCloseTicks = false;


	/**
	 *
	 */
	public JRBaseHighLowPlot(JRChartPlot highLowPlot, JRChart chart)
	{
		super(highLowPlot, chart);
	}


	/**
	 *
	 */
	public JRBaseHighLowPlot(JRHighLowPlot highLowPlot, JRBaseObjectFactory factory)
	{
		super(highLowPlot, factory);

		this.isShowOpenTicks = highLowPlot.isShowOpenTicks();
		this.isShowCloseTicks = highLowPlot.isShowCloseTicks();

		this.timeAxisLabelExpression = factory.getExpression( highLowPlot.getTimeAxisLabelExpression() );
		this.timeAxisLabelFont = new JRBaseFont(null, null, highLowPlot.getChart(), highLowPlot.getTimeAxisLabelFont());
		this.timeAxisLabelColor = highLowPlot.getOwnTimeAxisLabelColor();
		this.timeAxisTickLabelFont = new JRBaseFont(null, null, highLowPlot.getChart(), highLowPlot.getTimeAxisTickLabelFont());
		this.timeAxisTickLabelColor = highLowPlot.getOwnTimeAxisTickLabelColor();
		this.timeAxisTickLabelMask = highLowPlot.getTimeAxisTickLabelMask();
		this.timeAxisLineColor = highLowPlot.getTimeAxisLineColor();
		
		this.valueAxisLabelExpression = factory.getExpression( highLowPlot.getValueAxisLabelExpression() );
		this.valueAxisLabelFont = new JRBaseFont(null, null, highLowPlot.getChart(), highLowPlot.getValueAxisLabelFont());
		this.valueAxisLabelColor = highLowPlot.getOwnValueAxisLabelColor();
		this.valueAxisTickLabelFont = new JRBaseFont(null, null, highLowPlot.getChart(), highLowPlot.getValueAxisTickLabelFont());
		this.valueAxisTickLabelColor = highLowPlot.getOwnValueAxisTickLabelColor();
		this.valueAxisTickLabelMask = highLowPlot.getValueAxisTickLabelMask();
		this.valueAxisLineColor = highLowPlot.getValueAxisTickLabelColor();
	}


	/**
	 * 
	 */
	public JRExpression getTimeAxisLabelExpression(){
		return this.timeAxisLabelExpression;
	}
	
	/**
	 * 
	 */
	public JRFont getTimeAxisLabelFont()
	{
		return this.timeAxisLabelFont;
	}
	
	/**
	 * 
	 */
	public Color getTimeAxisLabelColor()
	{
		return JRStyleResolver.getTimeAxisLabelColor(this, this);
	}
	
	/**
	 * 
	 */
	public Color getOwnTimeAxisLabelColor()
	{
		return this.timeAxisLabelColor;
	}
	
	/**
	 * 
	 */
	public JRFont getTimeAxisTickLabelFont()
	{
		return this.timeAxisTickLabelFont;
	}
	
	/**
	 * 
	 */
	public Color getTimeAxisTickLabelColor()
	{
		return JRStyleResolver.getTimeAxisTickLabelColor(this, this);
	}

	/**
	 * 
	 */
	public Color getOwnTimeAxisTickLabelColor()
	{
		return this.timeAxisTickLabelColor;
	}

	/**
	 * 
	 */
	public String getTimeAxisTickLabelMask()
	{
		return this.timeAxisTickLabelMask;
	}

	/**
	 * 
	 */
	public Color getTimeAxisLineColor()
	{
		return JRStyleResolver.getTimeAxisLineColor(this, this);
	}

	/**
	 * 
	 */
	public Color getOwnTimeAxisLineColor()
	{
		return this.timeAxisLineColor;
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
	public boolean isShowOpenTicks()
	{
		return this.isShowOpenTicks;
	}


	/**
	 * 
	 */
	public void setShowOpenTicks(boolean showOpenTicks)
	{
		boolean old = this.isShowOpenTicks;
		this.isShowOpenTicks = showOpenTicks;
		getEventSupport().firePropertyChange(PROPERTY_SHOW_OPEN_TICKS, old, this.isShowOpenTicks);
	}


	/**
	 * 
	 */
	public boolean isShowCloseTicks()
	{
		return this.isShowCloseTicks;
	}


	/**
	 * 
	 */
	public void setShowCloseTicks(boolean showCloseTicks)
	{
		boolean old = this.isShowCloseTicks;
		this.isShowCloseTicks = showCloseTicks;
		getEventSupport().firePropertyChange(PROPERTY_SHOW_CLOSE_TICKS, old, this.isShowCloseTicks);
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
		JRBaseHighLowPlot clone = (JRBaseHighLowPlot)super.clone(parentChart);
		if (this.timeAxisLabelExpression != null)
		{
			clone.timeAxisLabelExpression = (JRExpression)this.timeAxisLabelExpression.clone();
		}
		if (this.valueAxisLabelExpression != null)
		{
			clone.valueAxisLabelExpression = (JRExpression)this.valueAxisLabelExpression.clone();
		}
		return clone;
	}
}
