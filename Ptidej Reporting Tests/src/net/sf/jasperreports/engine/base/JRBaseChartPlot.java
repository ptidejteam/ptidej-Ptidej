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
package net.sf.jasperreports.engine.base;

import java.awt.Color;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.design.events.JRChangeEventsSupport;
import net.sf.jasperreports.engine.design.events.JRPropertyChangeSupport;
import net.sf.jasperreports.engine.util.JRStyleResolver;

import org.jfree.chart.plot.PlotOrientation;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRBaseChartPlot.java,v 1.1 2008/09/29 16:21:16 guehene Exp $
 */
public abstract class JRBaseChartPlot implements JRChartPlot, Serializable, JRChangeEventsSupport
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_BACKCOLOR = "backcolor";
	
	public static final String PROPERTY_BACKGROUND_ALPHA = "backgroundAlpha";
	
	public static final String PROPERTY_FOREGROUND_ALPHA = "foregroundAlpha";
	
	public static final String PROPERTY_LABEL_ROTATION = "labelRotation";
	
	public static final String PROPERTY_ORIENTATION = "orientation";
	
	public static final String PROPERTY_SERIES_COLORS = "seriesColors";
	
	protected JRChart chart = null;
	protected Color backcolor = null;
	protected PlotOrientation orientation = PlotOrientation.VERTICAL;
	protected float backgroundAlpha = 1;
	protected float foregroundAlpha = 1;
	protected double labelRotation = 0.0;
	protected SortedSet  seriesColors = null;


	/**
	 *
	 */
	protected JRBaseChartPlot(JRChartPlot plot, JRChart chart)
	{
		this.chart = chart;

		if (plot != null) 
		{
			this.backcolor = plot.getOwnBackcolor();
			this.orientation = plot.getOrientation();
			this.backgroundAlpha = plot.getBackgroundAlpha();
			this.foregroundAlpha = plot.getForegroundAlpha();
			this.labelRotation = plot.getLabelRotation();
			this.seriesColors = new TreeSet(plot.getSeriesColors());
		}
		else
		{
			this.seriesColors = new TreeSet();
		}
	}


	/**
	 *
	 */
	protected JRBaseChartPlot(JRChartPlot plot, JRBaseObjectFactory factory)
	{
		factory.put(plot, this);

		this.chart = (JRChart)factory.getVisitResult(plot.getChart());

		this.backcolor = plot.getOwnBackcolor();
		this.orientation = plot.getOrientation();
		this.backgroundAlpha = plot.getBackgroundAlpha();
		this.foregroundAlpha = plot.getForegroundAlpha();
		this.labelRotation = plot.getLabelRotation();
		this.seriesColors = new TreeSet(plot.getSeriesColors());
	}

	
	/**
	 *
	 */
	public JRChart getChart()
	{
		return this.chart;
	}

	/**
	 *
	 */
	public Color getBackcolor()
	{
		return JRStyleResolver.getBackcolor(this);
	}

	/**
	 *
	 */
	public Color getOwnBackcolor()
	{
		return this.backcolor;
	}

	/**
	 *
	 */
	public void setBackcolor(Color backcolor)
	{
		Object old = this.backcolor;
		this.backcolor = backcolor;
		getEventSupport().firePropertyChange(PROPERTY_BACKCOLOR, old, this.backcolor);
	}

	/**
	 *
	 */
	public PlotOrientation getOrientation()
	{
		return this.orientation;
	}

	/**
	 *
	 */
	public void setOrientation(PlotOrientation orientation)
	{
		Object old = this.orientation;
		this.orientation = orientation;
		getEventSupport().firePropertyChange(PROPERTY_ORIENTATION, old, this.orientation);
	}

	/**
	 *
	 */
	public float getBackgroundAlpha()
	{
		return this.backgroundAlpha;
	}

	/**
	 *
	 */
	public void setBackgroundAlpha(float backgroundAlpha)
	{
		float old = this.backgroundAlpha;
		this.backgroundAlpha = backgroundAlpha;
		getEventSupport().firePropertyChange(PROPERTY_BACKGROUND_ALPHA, old, this.backgroundAlpha);
	}

	/**
	 *
	 */
	public float getForegroundAlpha()
	{
		return this.foregroundAlpha;
	}

	/**
	 *
	 */
	public void setForegroundAlpha(float foregroundAlpha)
	{
		float old = this.foregroundAlpha;
		this.foregroundAlpha = foregroundAlpha;
		getEventSupport().firePropertyChange(PROPERTY_FOREGROUND_ALPHA, old, this.foregroundAlpha);
	}

	/**
	 * Gets the angle in degrees to rotate the data axis labels.  The range is -360 to 360.  A positive value angles
	 * the label so it reads downwards wile a negative value angles the label so it reads upwards.  Only charts that
	 * use a category based axis (such as line or bar charts) support label rotation.
	 */
	public double getLabelRotation()
	{
		return this.labelRotation;
	}
	
	/**
	 * Sets the angle in degrees to rotate the data axis labels.  The range is -360 to 360.  A positive value angles
	 * the label so it reads downwards wile a negative value angles the label so it reads upwards.  Only charts that
	 * use a category based axis (such as line or bar charts) support label rotation.
	 */
	public void setLabelRotation(double labelRotation)
	{
		double old = this.labelRotation;
		this.labelRotation = labelRotation;
		getEventSupport().firePropertyChange(PROPERTY_LABEL_ROTATION, old, this.labelRotation);
	}
	
	
	/**
	 * Returns a list of all the defined series colors.  Every entry in the list is of type JRChartPlot.JRSeriesColor.
	 * If there are no defined series colors this method will return an empty list, not null. 
	 */
	public SortedSet getSeriesColors()
	{
		return this.seriesColors;
	}
	
	/**
	 * Removes all defined series colors.
	 */
	public void clearSeriesColors()
	{
		setSeriesColors(null);
	}
	
	/**
	 * Adds the specified series color to the plot.
	 */
	public void addSeriesColor(JRSeriesColor seriesColor)
	{
		this.seriesColors.add(seriesColor);
		getEventSupport().fireCollectionElementAddedEvent(PROPERTY_SERIES_COLORS, 
				seriesColor, this.seriesColors.size() - 1);
	}
	
	public void setSeriesColors(Collection colors)
	{
		Object old = new TreeSet(this.seriesColors);
		this.seriesColors.clear();
		if (colors != null)
		{
			this.seriesColors.addAll(colors);
		}
		getEventSupport().firePropertyChange(PROPERTY_SERIES_COLORS, old, this.seriesColors);
	}
	
	public static class JRBaseSeriesColor implements JRChartPlot.JRSeriesColor, Serializable, Comparable
	{
		/**
		 *
		 */
		private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
		
		protected int seriesOrder = -1;
		protected Color color = null;
		
		public JRBaseSeriesColor(int seriesOrder, Color color)
		{
			this.seriesOrder = seriesOrder;
			this.color = color;
		}
		
		/**
		 * Returns the series number (0 based) that this color applies to.
		 */
		public int getSeriesOrder()
		{
			return this.seriesOrder;
		}
		
		/**
		 * Returns the color to use for this series.
		 */
		public Color getColor()
		{
			return this.color;
		}

		public int compareTo(Object obj) {
			if (obj == null)
			{
				throw new NullPointerException();
			}
			
			return this.seriesOrder - ((JRBaseSeriesColor)obj).getSeriesOrder();
		}
		
		public Object clone()
		{
			try
			{
				return super.clone();
			}
			catch (CloneNotSupportedException e)
			{
				throw new JRRuntimeException(e);
			}
		}
	}
	

	/**
	 *
	 */
	public Object clone(JRChart parentChart) 
	{
		JRBaseChartPlot clone = null;

		try
		{
			clone = (JRBaseChartPlot)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new JRRuntimeException(e);
		}
		
		clone.chart = parentChart;
		
		if (this.seriesColors != null)
		{
			clone.seriesColors = new TreeSet();
			for(Iterator it = this.seriesColors.iterator(); it.hasNext();)
			{
				clone.seriesColors.add(((JRChartPlot.JRSeriesColor)it.next()).clone());
			}
		}
		
		return clone;
	}


	private transient JRPropertyChangeSupport eventSupport;
	
	public JRPropertyChangeSupport getEventSupport()
	{
		synchronized (this)
		{
			if (this.eventSupport == null)
			{
				this.eventSupport = new JRPropertyChangeSupport(this);
			}
		}
		
		return this.eventSupport;
	}
}
