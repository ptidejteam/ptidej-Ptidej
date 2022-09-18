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
package net.sf.jasperreports.engine.design;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.sf.jasperreports.charts.design.JRDesignAreaPlot;
import net.sf.jasperreports.charts.design.JRDesignBar3DPlot;
import net.sf.jasperreports.charts.design.JRDesignBarPlot;
import net.sf.jasperreports.charts.design.JRDesignBubblePlot;
import net.sf.jasperreports.charts.design.JRDesignCandlestickPlot;
import net.sf.jasperreports.charts.design.JRDesignCategoryDataset;
import net.sf.jasperreports.charts.design.JRDesignGanttDataset;
import net.sf.jasperreports.charts.design.JRDesignHighLowDataset;
import net.sf.jasperreports.charts.design.JRDesignHighLowPlot;
import net.sf.jasperreports.charts.design.JRDesignLinePlot;
import net.sf.jasperreports.charts.design.JRDesignMeterPlot;
import net.sf.jasperreports.charts.design.JRDesignMultiAxisPlot;
import net.sf.jasperreports.charts.design.JRDesignPie3DPlot;
import net.sf.jasperreports.charts.design.JRDesignPieDataset;
import net.sf.jasperreports.charts.design.JRDesignPiePlot;
import net.sf.jasperreports.charts.design.JRDesignScatterPlot;
import net.sf.jasperreports.charts.design.JRDesignThermometerPlot;
import net.sf.jasperreports.charts.design.JRDesignTimePeriodDataset;
import net.sf.jasperreports.charts.design.JRDesignTimeSeriesDataset;
import net.sf.jasperreports.charts.design.JRDesignTimeSeriesPlot;
import net.sf.jasperreports.charts.design.JRDesignValueDataset;
import net.sf.jasperreports.charts.design.JRDesignXyDataset;
import net.sf.jasperreports.charts.design.JRDesignXyzDataset;
import net.sf.jasperreports.engine.JRAnchor;
import net.sf.jasperreports.engine.JRBox;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartDataset;
import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRDefaultStyleProvider;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JRHyperlinkHelper;
import net.sf.jasperreports.engine.JRHyperlinkParameter;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRVisitor;
import net.sf.jasperreports.engine.base.JRBaseChart;
import net.sf.jasperreports.engine.base.JRBaseLineBox;
import net.sf.jasperreports.engine.util.JRBoxUtil;
import net.sf.jasperreports.engine.util.JRPenUtil;
import net.sf.jasperreports.engine.util.JRStyleResolver;
import net.sf.jasperreports.engine.util.LineBoxWrapper;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRDesignChart.java,v 1.1 2008/09/29 16:20:08 guehene Exp $
 */
public class JRDesignChart extends JRDesignElement implements JRChart
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	/*
	 * Chart properties
	 */
	
	public static final String PROPERTY_ANCHOR_NAME_EXPRESSION = "anchorNameExpression";
	
	public static final String PROPERTY_BOOKMARK_LEVEL = "bookmarkLevel";
	
	public static final String PROPERTY_EVALUATION_GROUP = "evaluationGroup";
	
	public static final String PROPERTY_EVALUATION_TIME = "evaluationTime";
	
	public static final String PROPERTY_CHART_TYPE = "chartType";
	
	public static final String PROPERTY_CUSTOMIZER_CLASS = "customizerClass";
	
	public static final String PROPERTY_DATASET = "dataset";
	
	public static final String PROPERTY_LEGEND_FONT = "legendFont";
	
	public static final String PROPERTY_SUBTITLE_EXPRESSION = "subtitleExpression";
	
	public static final String PROPERTY_SUBTITLE_FONT = "subtitleFont";
	
	public static final String PROPERTY_TITLE_EXPRESSION = "titleExpression";
	
	public static final String PROPERTY_TITLE_FONT = "titleFont";
	
	/**
	 *
	 */
	protected byte chartType = 0;

	/**
	 *
	 */
	protected boolean isShowLegend = false;
	protected byte evaluationTime = JRExpression.EVALUATION_TIME_NOW;
	protected byte hyperlinkType = JRHyperlink.HYPERLINK_TYPE_NULL;
	protected String linkType;
	protected byte hyperlinkTarget = JRHyperlink.HYPERLINK_TARGET_SELF;
	protected byte titlePosition = JRChart.EDGE_TOP;
	protected Color titleColor = null;
	protected Color subtitleColor = null;
	protected Color legendColor = null;
	protected Color legendBackgroundColor = null;
	protected byte legendPosition = JRChart.EDGE_BOTTOM;
	protected String renderType;

	/**
	 *
	 */
	protected JRFont titleFont = null;
	protected JRFont subtitleFont = null;
	protected JRFont legendFont = null;

	protected String customizerClass;

	/**
	 *
	 */
	protected JRGroup evaluationGroup = null;
	protected JRExpression titleExpression = null;
	protected JRExpression subtitleExpression = null;
	protected JRExpression anchorNameExpression = null;
	protected JRExpression hyperlinkReferenceExpression = null;
	protected JRExpression hyperlinkAnchorExpression = null;
	protected JRExpression hyperlinkPageExpression = null;
	private JRExpression hyperlinkTooltipExpression;
	private List hyperlinkParameters;

	protected JRChartDataset dataset = null;
	protected JRChartPlot plot = null;

	/**
	 *
	 */
	protected JRLineBox lineBox;
	
	/**
	 * The bookmark level for the anchor associated with this chart.
	 * @see JRAnchor#getBookmarkLevel()
	 */
	protected int bookmarkLevel = JRAnchor.NO_BOOKMARK;


	/**
	 *
	 */
	public JRDesignChart(JRDefaultStyleProvider defaultStyleProvider, byte chartType)
	{
		super(defaultStyleProvider);
		
		setChartType(chartType);
		
		this.hyperlinkParameters = new ArrayList();
		
		this.lineBox = new JRBaseLineBox(this);
	}


	/**
	 *
	 */
	public boolean isShowLegend()
	{
		return this.isShowLegend;
	}

	/**
	 *
	 */
	public void setShowLegend(boolean isShowLegend)
	{
		boolean old = this.isShowLegend;
		this.isShowLegend = isShowLegend;
		getEventSupport().firePropertyChange(JRBaseChart.PROPERTY_SHOW_LEGEND, old, this.isShowLegend);
	}

	/**
	 *
	 */
	public String getRenderType()
	{
		return this.renderType;
	}

	/**
	 *
	 */
	public void setRenderType(String renderType)
	{
		String old = this.renderType;
		this.renderType = renderType;
		getEventSupport().firePropertyChange(JRBaseChart.PROPERTY_RENDER_TYPE, old, this.renderType);
	}

	/**
	 *
	 */
	public byte getEvaluationTime()
	{
		return this.evaluationTime;
	}
		
	/**
	 *
	 */
	public void setEvaluationTime(byte evaluationTime)
	{
		byte old = this.evaluationTime;
		this.evaluationTime = evaluationTime;
		getEventSupport().firePropertyChange(PROPERTY_EVALUATION_TIME, (float) old, (float) this.evaluationTime);
	}
		
	/**
	 *
	 */
	public JRGroup getEvaluationGroup()
	{
		return this.evaluationGroup;
	}
		
	/**
	 *
	 */
	public void setEvaluationGroup(JRGroup group)
	{
		Object old = this.evaluationGroup;
		this.evaluationGroup = group;
		getEventSupport().firePropertyChange(PROPERTY_EVALUATION_GROUP, old, this.evaluationGroup);
	}
		
	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public JRBox getBox()
	{
		return new LineBoxWrapper(getLineBox());
	}

	/**
	 *
	 */
	public JRLineBox getLineBox()
	{
		return this.lineBox;
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setBox(JRBox box)
	{
		JRBoxUtil.setBoxToLineBox(box, this.lineBox);
	}

	/**
	 *
	 */
	public JRFont getTitleFont()
	{
		return this.titleFont;
	}

	/**
	 *
	 */
	public void setTitleFont(JRFont font)
	{
		Object old = this.titleFont;
		this.titleFont = font;
		getEventSupport().firePropertyChange(PROPERTY_TITLE_FONT, old, this.titleFont);
	}
	
	/**
	 *
	 */
	public byte getTitlePosition()
	{
		return this.titlePosition;
	}

	/**
	 *
	 */
	public void setTitlePosition(byte titlePosition)
	{
		byte old = this.titlePosition;
		this.titlePosition = titlePosition;
		getEventSupport().firePropertyChange(JRBaseChart.PROPERTY_TITLE_POSITION, (float) old, (float) this.titlePosition);
	}

	/**
	 *
	 */
	public Color getTitleColor()
	{
		return JRStyleResolver.getTitleColor(this);
	}

	/**
	 *
	 */
	public Color getOwnTitleColor()
	{
		return this.titleColor;
	}

	/**
	 *
	 */
	public void setTitleColor(Color titleColor)
	{
		Object old = this.titleColor;
		this.titleColor = titleColor;
		getEventSupport().firePropertyChange(JRBaseChart.PROPERTY_TITLE_COLOR, old, this.titleColor);
	}

	/**
	 *
	 */
	public JRFont getSubtitleFont()
	{
		return this.subtitleFont;
	}

	/**
	 *
	 */
	public void setSubtitleFont(JRFont font)
	{
		Object old = this.subtitleFont;
		this.subtitleFont = font;
		getEventSupport().firePropertyChange(PROPERTY_SUBTITLE_FONT, old, this.subtitleFont);
	}
	
	/**
	 *
	 */
	public Color getSubtitleColor()
	{
		return JRStyleResolver.getSubtitleColor(this);
	}

	/**
	 *
	 */
	public Color getOwnSubtitleColor()
	{
		return this.subtitleColor;
	}

	/**
	 *
	 */
	public void setSubtitleColor(Color subtitleColor)
	{
		Object old = this.subtitleColor;
		this.subtitleColor = subtitleColor;
		getEventSupport().firePropertyChange(JRBaseChart.PROPERTY_SUBTITLE_COLOR, old, this.subtitleColor);
	}

	/**
	 *
	 */
	public Color getOwnLegendColor()
	{
		return this.legendColor;
	}

	/**
	 *
	 */
	public Color getLegendColor()
	{
		return JRStyleResolver.getLegendColor(this);
	}

	/**
	 *
	 */
	public void setLegendColor(Color legendColor)
	{
		Object old = this.legendColor;
		this.legendColor = legendColor;
		getEventSupport().firePropertyChange(JRBaseChart.PROPERTY_LEGEND_COLOR, old, this.legendColor);
	}
	
	/**
	 *
	 */
	public Color getOwnLegendBackgroundColor()
	{
		return this.legendBackgroundColor;
	}

	/**
	 *
	 */
	public Color getLegendBackgroundColor()
	{
		return JRStyleResolver.getLegendBackgroundColor(this);
	}

	/**
	 *
	 */
	public void setLegendBackgroundColor(Color legendBackgroundColor)
	{
		Object old = this.legendBackgroundColor;
		this.legendBackgroundColor = legendBackgroundColor;
		getEventSupport().firePropertyChange(JRBaseChart.PROPERTY_LEGEND_BACKGROUND_COLOR, old, this.legendBackgroundColor);
	}
	
	/**
	 *
	 */
	public JRFont getLegendFont()
	{
		return this.legendFont;
	}

	/**
	 *
	 */
	public void setLegendFont(JRFont legendFont)
	{
		Object old = this.legendFont;
		this.legendFont = legendFont;
		getEventSupport().firePropertyChange(PROPERTY_LEGEND_FONT, old, this.legendFont);
	}
	
	/**
	 *
	 */
	public byte getLegendPosition()
	{
		return this.legendPosition;
	}

	/**
	 *
	 */
	public void setLegendPosition(byte legendPosition)
	{
		byte old = this.legendPosition;
		this.legendPosition = legendPosition;
		getEventSupport().firePropertyChange(JRBaseChart.PROPERTY_LEGEND_POSITION, (float) old, (float) this.legendPosition);
	}

	/**
	 *
	 */
	public byte getHyperlinkType()
	{
		return JRHyperlinkHelper.getHyperlinkType(this);
	}
		
	/**
	 * Sets the link type as a built-in hyperlink type.
	 * 
	 * @param hyperlinkType the built-in hyperlink type
	 * @see #getLinkType()
	 */
	public void setHyperlinkType(byte hyperlinkType)
	{
		setLinkType(JRHyperlinkHelper.getLinkType(hyperlinkType));
	}
		
	/**
	 *
	 */
	public byte getHyperlinkTarget()
	{
		return this.hyperlinkTarget;
	}
		
	/**
	 *
	 */
	public void setHyperlinkTarget(byte hyperlinkTarget)
	{
		byte old = this.hyperlinkTarget;
		this.hyperlinkTarget = hyperlinkTarget;
		getEventSupport().firePropertyChange(JRDesignHyperlink.PROPERTY_HYPERLINK_TARGET, (float) old, (float) this.hyperlinkTarget);
	}
		
	/**
	 *
	 */
	public JRExpression getTitleExpression()
	{
		return this.titleExpression;
	}

	/**
	 *
	 */
	public void setTitleExpression(JRExpression expression)
	{
		Object old = this.titleExpression;
		this.titleExpression = expression;
		getEventSupport().firePropertyChange(PROPERTY_TITLE_EXPRESSION, old, this.titleExpression);
	}

	/**
	 *
	 */
	public JRExpression getSubtitleExpression()
	{
		return this.subtitleExpression;
	}

	/**
	 *
	 */
	public void setSubtitleExpression(JRExpression expression)
	{
		Object old = this.subtitleExpression;
		this.subtitleExpression = expression;
		getEventSupport().firePropertyChange(PROPERTY_SUBTITLE_EXPRESSION, old, this.subtitleExpression);
	}

	/**
	 *
	 */
	public JRExpression getAnchorNameExpression()
	{
		return this.anchorNameExpression;
	}

	/**
	 *
	 */
	public void setAnchorNameExpression(JRExpression anchorNameExpression)
	{
		Object old = this.anchorNameExpression;
		this.anchorNameExpression = anchorNameExpression;
		getEventSupport().firePropertyChange(PROPERTY_ANCHOR_NAME_EXPRESSION, old, this.anchorNameExpression);
	}

	/**
	 *
	 */
	public JRExpression getHyperlinkReferenceExpression()
	{
		return this.hyperlinkReferenceExpression;
	}

	/**
	 *
	 */
	public void setHyperlinkReferenceExpression(JRExpression hyperlinkReferenceExpression)
	{
		Object old = this.hyperlinkReferenceExpression;
		this.hyperlinkReferenceExpression = hyperlinkReferenceExpression;
		getEventSupport().firePropertyChange(JRDesignHyperlink.PROPERTY_HYPERLINK_REFERENCE_EXPRESSION, old, this.hyperlinkReferenceExpression);
	}

	/**
	 *
	 */
	public JRExpression getHyperlinkAnchorExpression()
	{
		return this.hyperlinkAnchorExpression;
	}

	/**
	 *
	 */
	public void setHyperlinkAnchorExpression(JRExpression hyperlinkAnchorExpression)
	{
		Object old = this.hyperlinkAnchorExpression;
		this.hyperlinkAnchorExpression = hyperlinkAnchorExpression;
		getEventSupport().firePropertyChange(JRDesignHyperlink.PROPERTY_HYPERLINK_ANCHOR_EXPRESSION, old, this.hyperlinkAnchorExpression);
	}

	/**
	 *
	 */
	public JRExpression getHyperlinkPageExpression()
	{
		return this.hyperlinkPageExpression;
	}
	
	/**
	 *
	 */
	public void setHyperlinkPageExpression(JRExpression hyperlinkPageExpression)
	{
		Object old = this.hyperlinkPageExpression;
		this.hyperlinkPageExpression = hyperlinkPageExpression;
		getEventSupport().firePropertyChange(JRDesignHyperlink.PROPERTY_HYPERLINK_PAGE_EXPRESSION, old, this.hyperlinkPageExpression);
	}

	/**
	 *
	 */
	public JRChartDataset getDataset()
	{
		return this.dataset;
	}

	/**
	 *
	 */
	public JRChartPlot getPlot()
	{
		return this.plot;
	}


	public byte getChartType()
	{
		return this.chartType;
	}


	/**
	 *
	 */
	public void setChartType(byte chartType)
	{
		byte old = this.chartType;
		this.chartType = chartType;
		
		switch(chartType) {
			case CHART_TYPE_AREA:
				this.dataset = new JRDesignCategoryDataset(this.dataset);
				this.plot = new JRDesignAreaPlot(this.plot, this);
				break;
			case CHART_TYPE_BAR:
				this.dataset = new JRDesignCategoryDataset(this.dataset);
				this.plot = new JRDesignBarPlot(this.plot, this);
				break;
			case CHART_TYPE_BAR3D:
				this.dataset = new JRDesignCategoryDataset(this.dataset);
				this.plot = new JRDesignBar3DPlot(this.plot, this);
				break;
			case CHART_TYPE_BUBBLE:
				this.dataset = new JRDesignXyzDataset(this.dataset);
				this.plot = new JRDesignBubblePlot(this.plot, this);
				break;
			case CHART_TYPE_CANDLESTICK:
				this.dataset = new JRDesignHighLowDataset(this.dataset);
				this.plot = new JRDesignCandlestickPlot(this.plot, this);
				break;
			case CHART_TYPE_HIGHLOW:
				this.dataset = new JRDesignHighLowDataset(this.dataset);
				this.plot = new JRDesignHighLowPlot(this.plot, this);
				break;
			case CHART_TYPE_LINE:
				this.dataset = new JRDesignCategoryDataset(this.dataset);
				this.plot = new JRDesignLinePlot(this.plot, this);
				break;
			case CHART_TYPE_METER:
				this.dataset = new JRDesignValueDataset(this.dataset);
				this.plot = new JRDesignMeterPlot(this.plot, this);
				break;
			case CHART_TYPE_MULTI_AXIS:
				this.plot = new JRDesignMultiAxisPlot(this.plot, this);
				this.dataset = null;
				break;
			case CHART_TYPE_PIE:
				this.dataset = new JRDesignPieDataset(this.dataset);
				this.plot = new JRDesignPiePlot(this.plot, this);
				break;
			case CHART_TYPE_PIE3D:
				this.dataset = new JRDesignPieDataset(this.dataset);
				this.plot = new JRDesignPie3DPlot(this.plot, this);
				break;
			case CHART_TYPE_SCATTER:
				this.dataset = new JRDesignXyDataset(this.dataset);
				this.plot = new JRDesignScatterPlot(this.plot, this);
				break;
			case CHART_TYPE_STACKEDBAR:
				this.dataset = new JRDesignCategoryDataset(this.dataset);
				this.plot = new JRDesignBarPlot(this.plot, this);
				break;
			case CHART_TYPE_STACKEDBAR3D:
				this.dataset = new JRDesignCategoryDataset(this.dataset);
				this.plot = new JRDesignBar3DPlot(this.plot, this);
				break;
			case CHART_TYPE_THERMOMETER:
				this.dataset = new JRDesignValueDataset(this.dataset);
				this.plot = new JRDesignThermometerPlot(this.plot, this);
				break;
			case CHART_TYPE_TIMESERIES:
				this.dataset = new JRDesignTimeSeriesDataset(this.dataset);//other datasets could be supported
				this.plot = new JRDesignTimeSeriesPlot(this.plot, this);
				break;
			case CHART_TYPE_XYAREA:
				this.dataset = new JRDesignXyDataset(this.dataset);
				this.plot = new JRDesignAreaPlot(this.plot, this);
				break;
			case CHART_TYPE_XYBAR:
				this.plot = new JRDesignBarPlot(this.plot, this);
				break;
			case CHART_TYPE_XYLINE:
				this.dataset = new JRDesignXyDataset(this.dataset);
				this.plot = new JRDesignLinePlot(this.plot, this);
				break;
			case CHART_TYPE_STACKEDAREA:
				this.dataset = new JRDesignCategoryDataset(this.dataset);
				this.plot = new JRDesignAreaPlot(this.plot, this);
				break;
			case CHART_TYPE_GANTT:
				this.dataset = new JRDesignGanttDataset(this.dataset);
				this.plot = new JRDesignBarPlot(this.plot, this);
				break;
			default:
				throw new JRRuntimeException("Chart type not supported.");
		}

		getEventSupport().firePropertyChange(PROPERTY_CHART_TYPE, (float) old, (float) this.chartType);
	}


	public void setDataset(JRChartDataset ds)
	{
		Object old = this.dataset;
		switch( ds.getDatasetType() ){
			case JRChartDataset.CATEGORY_DATASET:
				this.dataset = (JRDesignCategoryDataset)ds;
				break;
			case JRChartDataset.HIGHLOW_DATASET:
				this.dataset = (JRDesignHighLowDataset)ds;
				break;
			case JRChartDataset.PIE_DATASET:
				this.dataset = (JRDesignPieDataset)ds;
				break;
			case JRChartDataset.TIMEPERIOD_DATASET:
				this.dataset = (JRDesignTimePeriodDataset)ds;
				break;
			case JRChartDataset.TIMESERIES_DATASET:
				this.dataset = (JRDesignTimeSeriesDataset)ds;
				break;
			case JRChartDataset.VALUE_DATASET:
				this.dataset = (JRDesignValueDataset)ds;
				break;
			case JRChartDataset.XY_DATASET:
				this.dataset = (JRDesignXyDataset)ds;
				break;
			case JRChartDataset.XYZ_DATASET:
				this.dataset = (JRDesignXyzDataset)ds;
				break;
			case JRChartDataset.GANTT_DATASET:
				this.dataset = (JRDesignGanttDataset)ds;
				break;
		}
		getEventSupport().firePropertyChange(PROPERTY_DATASET, old, this.dataset);		
	}


	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}


	/**
	 *
	 */
	public void visit(JRVisitor visitor)
	{
		visitor.visitChart(this);
	}

	
	public int getBookmarkLevel()
	{
		return this.bookmarkLevel;
	}


	/**
	 * Sets the boomark level for the anchor associated with this chart.
	 * 
	 * @param bookmarkLevel the bookmark level (starting from 1)
	 * or {@link JRAnchor#NO_BOOKMARK NO_BOOKMARK} if no bookmark should be created
	 */
	public void setBookmarkLevel(int bookmarkLevel)
	{
		int old = this.bookmarkLevel;
		this.bookmarkLevel = bookmarkLevel;
		getEventSupport().firePropertyChange(PROPERTY_BOOKMARK_LEVEL, (float) old, (float) this.bookmarkLevel);
	}

	/**
	 *
	 */
	public String getCustomizerClass()
	{
		return this.customizerClass;
	}

	/**
	 * Sets a user specified chart customizer class name.
	 * @see net.sf.jasperreports.engine.JRChartCustomizer
 	 */
	public void setCustomizerClass(String customizerClass)
	{
		Object old = this.customizerClass;
		this.customizerClass = customizerClass;
		getEventSupport().firePropertyChange(PROPERTY_CUSTOMIZER_CLASS, old, this.customizerClass);
	}


	/**
	 *
	 */
	public byte getMode()
	{
		return JRStyleResolver.getMode(this, MODE_TRANSPARENT);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public byte getBorder()
	{
		return JRPenUtil.getPenFromLinePen(this.lineBox.getPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Byte getOwnBorder()
	{
		return JRPenUtil.getOwnPenFromLinePen(this.lineBox.getPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setBorder(byte border)
	{
		JRPenUtil.setLinePenFromPen(border, this.lineBox.getPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setBorder(Byte border)
	{
		JRPenUtil.setLinePenFromPen(border, this.lineBox.getPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Color getBorderColor()
	{
		return this.lineBox.getPen().getLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Color getOwnBorderColor()
	{
		return this.lineBox.getPen().getOwnLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setBorderColor(Color borderColor)
	{
		this.lineBox.getPen().setLineColor(borderColor);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public int getPadding()
	{
		return this.lineBox.getPadding().intValue();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Integer getOwnPadding()
	{
		return this.lineBox.getOwnPadding();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setPadding(int padding)
	{
		this.lineBox.setPadding(padding);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setPadding(Integer padding)
	{
		this.lineBox.setPadding(padding);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public byte getTopBorder()
	{
		return JRPenUtil.getPenFromLinePen(this.lineBox.getTopPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Byte getOwnTopBorder()
	{
		return JRPenUtil.getOwnPenFromLinePen(this.lineBox.getTopPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setTopBorder(byte topBorder)
	{
		JRPenUtil.setLinePenFromPen(topBorder, this.lineBox.getTopPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setTopBorder(Byte topBorder)
	{
		JRPenUtil.setLinePenFromPen(topBorder, this.lineBox.getTopPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Color getTopBorderColor()
	{
		return this.lineBox.getTopPen().getLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Color getOwnTopBorderColor()
	{
		return this.lineBox.getTopPen().getOwnLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setTopBorderColor(Color topBorderColor)
	{
		this.lineBox.getTopPen().setLineColor(topBorderColor);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public int getTopPadding()
	{
		return this.lineBox.getTopPadding().intValue();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Integer getOwnTopPadding()
	{
		return this.lineBox.getOwnTopPadding();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setTopPadding(int topPadding)
	{
		this.lineBox.setTopPadding(topPadding);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setTopPadding(Integer topPadding)
	{
		this.lineBox.setTopPadding(topPadding);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public byte getLeftBorder()
	{
		return JRPenUtil.getPenFromLinePen(this.lineBox.getLeftPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Byte getOwnLeftBorder()
	{
		return JRPenUtil.getOwnPenFromLinePen(this.lineBox.getLeftPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setLeftBorder(byte leftBorder)
	{
		JRPenUtil.setLinePenFromPen(leftBorder, this.lineBox.getLeftPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setLeftBorder(Byte leftBorder)
	{
		JRPenUtil.setLinePenFromPen(leftBorder, this.lineBox.getLeftPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Color getLeftBorderColor()
	{
		return this.lineBox.getLeftPen().getLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Color getOwnLeftBorderColor()
	{
		return this.lineBox.getLeftPen().getOwnLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setLeftBorderColor(Color leftBorderColor)
	{
		this.lineBox.getLeftPen().setLineColor(leftBorderColor);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public int getLeftPadding()
	{
		return this.lineBox.getLeftPadding().intValue();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Integer getOwnLeftPadding()
	{
		return this.lineBox.getOwnLeftPadding();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setLeftPadding(int leftPadding)
	{
		this.lineBox.setLeftPadding(leftPadding);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setLeftPadding(Integer leftPadding)
	{
		this.lineBox.setLeftPadding(leftPadding);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public byte getBottomBorder()
	{
		return JRPenUtil.getPenFromLinePen(this.lineBox.getBottomPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Byte getOwnBottomBorder()
	{
		return JRPenUtil.getOwnPenFromLinePen(this.lineBox.getBottomPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setBottomBorder(byte bottomBorder)
	{
		JRPenUtil.setLinePenFromPen(bottomBorder, this.lineBox.getBottomPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setBottomBorder(Byte bottomBorder)
	{
		JRPenUtil.setLinePenFromPen(bottomBorder, this.lineBox.getBottomPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Color getBottomBorderColor()
	{
		return this.lineBox.getBottomPen().getLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Color getOwnBottomBorderColor()
	{
		return this.lineBox.getBottomPen().getOwnLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setBottomBorderColor(Color bottomBorderColor)
	{
		this.lineBox.getBottomPen().setLineColor(bottomBorderColor);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public int getBottomPadding()
	{
		return this.lineBox.getBottomPadding().intValue();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Integer getOwnBottomPadding()
	{
		return this.lineBox.getOwnBottomPadding();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setBottomPadding(int bottomPadding)
	{
		this.lineBox.setBottomPadding(bottomPadding);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setBottomPadding(Integer bottomPadding)
	{
		this.lineBox.setBottomPadding(bottomPadding);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public byte getRightBorder()
	{
		return JRPenUtil.getPenFromLinePen(this.lineBox.getRightPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Byte getOwnRightBorder()
	{
		return JRPenUtil.getOwnPenFromLinePen(this.lineBox.getRightPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setRightBorder(byte rightBorder)
	{
		JRPenUtil.setLinePenFromPen(rightBorder, this.lineBox.getRightPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setRightBorder(Byte rightBorder)
	{
		JRPenUtil.setLinePenFromPen(rightBorder, this.lineBox.getRightPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Color getRightBorderColor()
	{
		return this.lineBox.getRightPen().getLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Color getOwnRightBorderColor()
	{
		return this.lineBox.getRightPen().getOwnLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setRightBorderColor(Color rightBorderColor)
	{
		this.lineBox.getRightPen().setLineColor(rightBorderColor);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public int getRightPadding()
	{
		return this.lineBox.getRightPadding().intValue();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Integer getOwnRightPadding()
	{
		return this.lineBox.getOwnRightPadding();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setRightPadding(int rightPadding)
	{
		this.lineBox.setRightPadding(rightPadding);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setRightPadding(Integer rightPadding)
	{
		this.lineBox.setRightPadding(rightPadding);
	}


	public String getLinkType()
	{
		return this.linkType;
	}


	/**
	 * Sets the hyperlink type.
	 * <p>
	 * The type can be one of the built-in types
	 * (Reference, LocalAnchor, LocalPage, RemoteAnchor, RemotePage),
	 * or can be an arbitrary type.
	 * </p>
	 * @param type the hyperlink type
	 */
	public void setLinkType(String type)
	{
		Object old = this.linkType;
		this.linkType = type;
		getEventSupport().firePropertyChange(JRDesignHyperlink.PROPERTY_LINK_TYPE, old, this.linkType);
	}


	public JRHyperlinkParameter[] getHyperlinkParameters()
	{
		JRHyperlinkParameter[] parameters;
		if (this.hyperlinkParameters.isEmpty())
		{
			parameters = null;
		}
		else
		{
			parameters = new JRHyperlinkParameter[this.hyperlinkParameters.size()];
			this.hyperlinkParameters.toArray(parameters);
		}
		return parameters;
	}
	
	
	/**
	 * Returns the list of custom hyperlink parameters.
	 * 
	 * @return the list of custom hyperlink parameters
	 */
	public List getHyperlinkParametersList()
	{
		return this.hyperlinkParameters;
	}
	
	
	/**
	 * Adds a custom hyperlink parameter.
	 * 
	 * @param parameter the parameter to add
	 */
	public void addHyperlinkParameter(JRHyperlinkParameter parameter)
	{
		this.hyperlinkParameters.add(parameter);
		getEventSupport().fireCollectionElementAddedEvent(JRDesignHyperlink.PROPERTY_HYPERLINK_PARAMETERS, 
				parameter, this.hyperlinkParameters.size() - 1);
	}
	

	/**
	 * Removes a custom hyperlink parameter.
	 * 
	 * @param parameter the parameter to remove
	 */
	public void removeHyperlinkParameter(JRHyperlinkParameter parameter)
	{
		int idx = this.hyperlinkParameters.indexOf(parameter);
		if (idx >= 0)
		{
			this.hyperlinkParameters.remove(idx);
			getEventSupport().fireCollectionElementRemovedEvent(JRDesignHyperlink.PROPERTY_HYPERLINK_PARAMETERS, 
					parameter, idx);
		}
	}
	
	
	/**
	 * Removes a custom hyperlink parameter.
	 * <p>
	 * If multiple parameters having the specified name exist, all of them
	 * will be removed
	 * </p>
	 * 
	 * @param parameterName the parameter name
	 */
	public void removeHyperlinkParameter(String parameterName)
	{
		for (ListIterator it = this.hyperlinkParameters.listIterator(); it.hasNext();)
		{
			JRHyperlinkParameter parameter = (JRHyperlinkParameter) it.next();
			if (parameter.getName() != null && parameter.getName().equals(parameterName))
			{
				it.remove();
				getEventSupport().fireCollectionElementRemovedEvent(JRDesignHyperlink.PROPERTY_HYPERLINK_PARAMETERS, 
						parameter, it.nextIndex());
			}
		}
	}
	
	
	protected void normalizeLinkType()
	{
		if (this.linkType == null)
		{
			 this.linkType = JRHyperlinkHelper.getLinkType(this.hyperlinkType);
		}
		this.hyperlinkType = JRHyperlink.HYPERLINK_TYPE_NULL;
	}

	
	public JRExpression getHyperlinkTooltipExpression()
	{
		return this.hyperlinkTooltipExpression;
	}

	
	/**
	 * Sets the expression which will be used to generate the hyperlink tooltip.
	 * 
	 * @param hyperlinkTooltipExpression the expression which will be used to generate the hyperlink tooltip
	 * @see #getHyperlinkTooltipExpression()
	 */
	public void setHyperlinkTooltipExpression(JRExpression hyperlinkTooltipExpression)
	{
		Object old = this.hyperlinkTooltipExpression;
		this.hyperlinkTooltipExpression = hyperlinkTooltipExpression;
		getEventSupport().firePropertyChange(JRDesignHyperlink.PROPERTY_HYPERLINK_TOOLTIP_EXPRESSION, old, this.hyperlinkTooltipExpression);
	}

	/**
	 * 
	 */
	public Color getDefaultLineColor() 
	{
		return getForecolor();
	}

	
	/**
	 * 
	 */
	public Object clone() 
	{
		JRDesignChart clone = (JRDesignChart)super.clone();
		
		clone.lineBox = (JRLineBox)this.lineBox.clone(this);

		if (this.hyperlinkParameters != null)
		{
			clone.hyperlinkParameters = new ArrayList(this.hyperlinkParameters.size());
			for(int i = 0; i < this.hyperlinkParameters.size(); i++)
			{
				clone.hyperlinkParameters.add(((JRHyperlinkParameter)this.hyperlinkParameters.get(i)).clone());
			}
		}

		if (this.titleExpression != null)
		{
			clone.titleExpression = (JRExpression)this.titleExpression.clone();
		}
		if (this.subtitleExpression != null)
		{
			clone.subtitleExpression = (JRExpression)this.subtitleExpression.clone();
		}
		if (this.anchorNameExpression != null)
		{
			clone.anchorNameExpression = (JRExpression)this.anchorNameExpression.clone();
		}
		if (this.hyperlinkReferenceExpression != null)
		{
			clone.hyperlinkReferenceExpression = (JRExpression)this.hyperlinkReferenceExpression.clone();
		}
		if (this.hyperlinkAnchorExpression != null)
		{
			clone.hyperlinkAnchorExpression = (JRExpression)this.hyperlinkAnchorExpression.clone();
		}
		if (this.hyperlinkPageExpression != null)
		{
			clone.hyperlinkPageExpression = (JRExpression)this.hyperlinkPageExpression.clone();
		}
		if (this.hyperlinkTooltipExpression != null)
		{
			clone.hyperlinkTooltipExpression = (JRExpression)this.hyperlinkTooltipExpression.clone();
		}

		if (this.dataset != null)
		{
			clone.dataset = (JRChartDataset)this.dataset.clone();
		}
		if (this.plot != null)
		{
			clone.plot = (JRChartPlot)this.plot.clone(clone);
		}

		return clone;
	}
	

	/**
	 * These fields are only for serialization backward compatibility.
	 */
	private Byte border = null;
	private Byte topBorder = null;
	private Byte leftBorder = null;
	private Byte bottomBorder = null;
	private Byte rightBorder = null;
	private Color borderColor = null;
	private Color topBorderColor = null;
	private Color leftBorderColor = null;
	private Color bottomBorderColor = null;
	private Color rightBorderColor = null;
	private Integer padding = null;
	private Integer topPadding = null;
	private Integer leftPadding = null;
	private Integer bottomPadding = null;
	private Integer rightPadding = null;
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();

		if (this.lineBox == null)
		{
			this.lineBox = new JRBaseLineBox(this);
			JRBoxUtil.setToBox(
				this.border,
				this.topBorder,
				this.leftBorder,
				this.bottomBorder,
				this.rightBorder,
				this.borderColor,
				this.topBorderColor,
				this.leftBorderColor,
				this.bottomBorderColor,
				this.rightBorderColor,
				this.padding,
				this.topPadding,
				this.leftPadding,
				this.bottomPadding,
				this.rightPadding,
				this.lineBox
				);
			this.border = null;
			this.topBorder = null;
			this.leftBorder = null;
			this.bottomBorder = null;
			this.rightBorder = null;
			this.borderColor = null;
			this.topBorderColor = null;
			this.leftBorderColor = null;
			this.bottomBorderColor = null;
			this.rightBorderColor = null;
			this.padding = null;
			this.topPadding = null;
			this.leftPadding = null;
			this.bottomPadding = null;
			this.rightPadding = null;
		}

		normalizeLinkType();
	}
}
