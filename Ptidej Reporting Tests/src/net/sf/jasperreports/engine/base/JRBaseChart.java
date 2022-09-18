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
import java.io.IOException;
import java.io.ObjectInputStream;

import net.sf.jasperreports.charts.JRAreaPlot;
import net.sf.jasperreports.charts.JRBar3DPlot;
import net.sf.jasperreports.charts.JRBarPlot;
import net.sf.jasperreports.charts.JRBubblePlot;
import net.sf.jasperreports.charts.JRCandlestickPlot;
import net.sf.jasperreports.charts.JRCategoryDataset;
import net.sf.jasperreports.charts.JRGanttDataset;
import net.sf.jasperreports.charts.JRHighLowDataset;
import net.sf.jasperreports.charts.JRHighLowPlot;
import net.sf.jasperreports.charts.JRLinePlot;
import net.sf.jasperreports.charts.JRMeterPlot;
import net.sf.jasperreports.charts.JRMultiAxisPlot;
import net.sf.jasperreports.charts.JRPie3DPlot;
import net.sf.jasperreports.charts.JRPieDataset;
import net.sf.jasperreports.charts.JRPiePlot;
import net.sf.jasperreports.charts.JRScatterPlot;
import net.sf.jasperreports.charts.JRThermometerPlot;
import net.sf.jasperreports.charts.JRTimePeriodDataset;
import net.sf.jasperreports.charts.JRTimeSeriesDataset;
import net.sf.jasperreports.charts.JRTimeSeriesPlot;
import net.sf.jasperreports.charts.JRValueDataset;
import net.sf.jasperreports.charts.JRXyDataset;
import net.sf.jasperreports.charts.JRXyzDataset;
import net.sf.jasperreports.engine.JRAnchor;
import net.sf.jasperreports.engine.JRBox;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartDataset;
import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.JRConstants;
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
import net.sf.jasperreports.engine.util.JRBoxUtil;
import net.sf.jasperreports.engine.util.JRPenUtil;
import net.sf.jasperreports.engine.util.JRStyleResolver;
import net.sf.jasperreports.engine.util.LineBoxWrapper;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRBaseChart.java,v 1.1 2008/09/29 16:21:16 guehene Exp $
 */
public class JRBaseChart extends JRBaseElement implements JRChart
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	/*
	 * Chart properties
	 */
	
	public static final String PROPERTY_LEGEND_BACKGROUND_COLOR = "legendBackgroundColor";
	
	public static final String PROPERTY_LEGEND_COLOR = "legendColor";
	
	public static final String PROPERTY_LEGEND_POSITION = "legendPosition";
	
	public static final String PROPERTY_SHOW_LEGEND = "showLegend";
	
	public static final String PROPERTY_SUBTITLE_COLOR = "subtitleColor";
	
	public static final String PROPERTY_TITLE_COLOR = "titleColor";
	
	public static final String PROPERTY_TITLE_POSITION = "titlePosition";
	
	public static final String PROPERTY_RENDER_TYPE = "renderType";
	
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
	private JRHyperlinkParameter[] hyperlinkParameters;
	
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
	protected JRLineBox lineBox = null;
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

	protected JRChartDataset dataset = null;
	protected JRChartPlot plot = null;

	/**
	 * The bookmark level for the anchor associated with this chart.
	 * @see JRAnchor#getBookmarkLevel()
	 */
	protected int bookmarkLevel = JRAnchor.NO_BOOKMARK;

	
	/**
	 *
	 */
	protected JRBaseChart(JRChart chart, JRBaseObjectFactory factory)
	{
		super(chart, factory);

		this.chartType = chart.getChartType();

		switch(this.chartType) {
			case CHART_TYPE_AREA:
				this.dataset = factory.getCategoryDataset((JRCategoryDataset) chart.getDataset());
				this.plot = factory.getAreaPlot((JRAreaPlot) chart.getPlot());
				break;
			case CHART_TYPE_BAR:
				this.dataset = factory.getCategoryDataset((JRCategoryDataset) chart.getDataset());
				this.plot = factory.getBarPlot((JRBarPlot) chart.getPlot());
				break;
			case CHART_TYPE_BAR3D:
				this.dataset = factory.getCategoryDataset((JRCategoryDataset) chart.getDataset());
				this.plot = factory.getBar3DPlot((JRBar3DPlot) chart.getPlot());
				break;
			case CHART_TYPE_BUBBLE:
				this.dataset = factory.getXyzDataset((JRXyzDataset) chart.getDataset());
				this.plot = factory.getBubblePlot((JRBubblePlot) chart.getPlot());
				break;
			case CHART_TYPE_CANDLESTICK:
				this.dataset = factory.getHighLowDataset((JRHighLowDataset) chart.getDataset());
				this.plot = factory.getCandlestickPlot((JRCandlestickPlot) chart.getPlot());
				break;
			case CHART_TYPE_HIGHLOW:
				this.dataset = factory.getHighLowDataset((JRHighLowDataset) chart.getDataset());
				this.plot = factory.getHighLowPlot((JRHighLowPlot) chart.getPlot());
				break;
			case CHART_TYPE_LINE:
				this.dataset = factory.getCategoryDataset((JRCategoryDataset) chart.getDataset());
				this.plot = factory.getLinePlot((JRLinePlot) chart.getPlot());
				break;
			case CHART_TYPE_METER:
				this.dataset = factory.getValueDataset((JRValueDataset) chart.getDataset());
				this.plot = factory.getMeterPlot((JRMeterPlot) chart.getPlot());
				break;
			case CHART_TYPE_MULTI_AXIS:
				this.dataset = null;
				this.plot = factory.getMultiAxisPlot((JRMultiAxisPlot) chart.getPlot());
				break;
			case CHART_TYPE_PIE:
				this.dataset = factory.getPieDataset((JRPieDataset) chart.getDataset());
				this.plot = factory.getPiePlot((JRPiePlot) chart.getPlot());
				break;
			case CHART_TYPE_PIE3D:
				this.dataset = factory.getPieDataset((JRPieDataset) chart.getDataset());
				this.plot = factory.getPie3DPlot((JRPie3DPlot) chart.getPlot());
				break;
			case CHART_TYPE_SCATTER:
				this.dataset = factory.getXyDataset((JRXyDataset) chart.getDataset());
				this.plot = factory.getScatterPlot((JRScatterPlot) chart.getPlot());
				break;
			case CHART_TYPE_STACKEDBAR:
				this.dataset = factory.getCategoryDataset((JRCategoryDataset) chart.getDataset());
				this.plot = factory.getBarPlot((JRBarPlot) chart.getPlot());
				break;
			case CHART_TYPE_STACKEDBAR3D:
				this.dataset = factory.getCategoryDataset((JRCategoryDataset) chart.getDataset());
				this.plot = factory.getBar3DPlot((JRBar3DPlot) chart.getPlot());
				break;
			case CHART_TYPE_THERMOMETER:
				this.dataset = factory.getValueDataset((JRValueDataset) chart.getDataset());
				this.plot = factory.getThermometerPlot((JRThermometerPlot) chart.getPlot());
				break;
			case CHART_TYPE_TIMESERIES:
				this.dataset = factory.getTimeSeriesDataset((JRTimeSeriesDataset)chart.getDataset());
				this.plot = factory.getTimeSeriesPlot( (JRTimeSeriesPlot)chart.getPlot() );
				break;
			case CHART_TYPE_XYAREA:
				this.dataset = factory.getXyDataset((JRXyDataset) chart.getDataset());
				this.plot = factory.getAreaPlot((JRAreaPlot) chart.getPlot());
				break;
			case CHART_TYPE_XYBAR:
				
				switch (chart.getDataset().getDatasetType()){
					case JRChartDataset.TIMESERIES_DATASET:
						this.dataset = factory.getTimeSeriesDataset((JRTimeSeriesDataset) chart.getDataset());
						break;
					case JRChartDataset.TIMEPERIOD_DATASET:
						this.dataset = factory.getTimePeriodDataset((JRTimePeriodDataset) chart.getDataset() );
						break;
					case JRChartDataset.XY_DATASET:
						this.dataset = factory.getXyDataset( (JRXyDataset)chart.getDataset() );
						break;
				}
				this.plot = factory.getBarPlot((JRBarPlot)chart.getPlot());
				break;
			case CHART_TYPE_XYLINE:
				this.dataset = factory.getXyDataset((JRXyDataset) chart.getDataset());
				this.plot = factory.getLinePlot((JRLinePlot) chart.getPlot());
				break;
			case CHART_TYPE_STACKEDAREA:
				this.dataset = factory.getCategoryDataset((JRCategoryDataset) chart.getDataset());
				this.plot = factory.getAreaPlot((JRAreaPlot) chart.getPlot());
				break;
			case CHART_TYPE_GANTT:
				this.dataset = factory.getGanttDataset((JRGanttDataset) chart.getDataset());
				this.plot = factory.getBarPlot((JRBarPlot) chart.getPlot());
				break;
			default:
				throw new JRRuntimeException("Chart type not supported.");
		}

		this.isShowLegend = chart.isShowLegend();
		this.evaluationTime = chart.getEvaluationTime();
		this.linkType = chart.getLinkType();
		this.hyperlinkTarget = chart.getHyperlinkTarget();
		this.titlePosition = chart.getTitlePosition();
		this.titleColor = chart.getOwnTitleColor();
		this.subtitleColor = chart.getOwnSubtitleColor();
		this.legendColor = chart.getOwnLegendColor();
		this.legendBackgroundColor = chart.getOwnLegendBackgroundColor();
		this.legendPosition = chart.getLegendPosition();
		this.renderType = chart.getRenderType();
		
		this.titleFont = new JRBaseFont(null, null, this, chart.getTitleFont());
		this.subtitleFont = new JRBaseFont(null, null, this, chart.getSubtitleFont());
		this.legendFont = new JRBaseFont(null, null, this, chart.getLegendFont());

		this.evaluationGroup = factory.getGroup(chart.getEvaluationGroup());
		this.titleExpression = factory.getExpression(chart.getTitleExpression());
		this.subtitleExpression = factory.getExpression(chart.getSubtitleExpression());
		this.anchorNameExpression = factory.getExpression(chart.getAnchorNameExpression());
		this.hyperlinkReferenceExpression = factory.getExpression(chart.getHyperlinkReferenceExpression());
		this.hyperlinkAnchorExpression = factory.getExpression(chart.getHyperlinkAnchorExpression());
		this.hyperlinkPageExpression = factory.getExpression(chart.getHyperlinkPageExpression());
		this.hyperlinkTooltipExpression = factory.getExpression(chart.getHyperlinkTooltipExpression());
		this.bookmarkLevel = chart.getBookmarkLevel();
		this.hyperlinkParameters = JRBaseHyperlink.copyHyperlinkParameters(chart, factory);

		this.customizerClass = chart.getCustomizerClass();

		this.lineBox = chart.getLineBox().clone(this);
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
		getEventSupport().firePropertyChange(PROPERTY_SHOW_LEGEND, old, this.isShowLegend);
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
	public JRGroup getEvaluationGroup()
	{
		return this.evaluationGroup;
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
	 *
	 */
	public JRFont getTitleFont()
	{
		return this.titleFont;
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
		getEventSupport().firePropertyChange(PROPERTY_TITLE_POSITION, (float) old, (float) this.titlePosition);
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
		getEventSupport().firePropertyChange(PROPERTY_TITLE_COLOR, old, this.titleColor);
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
	public Color getOwnSubtitleColor()
	{
		return this.subtitleColor;
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
	public void setSubtitleColor(Color subtitleColor)
	{
		Object old = this.subtitleColor;
		this.subtitleColor = subtitleColor;
		getEventSupport().firePropertyChange(PROPERTY_SUBTITLE_COLOR, old, this.subtitleColor);
	}

	public Color getLegendBackgroundColor() {
		return JRStyleResolver.getLegendBackgroundColor(this);
	}

	public Color getOwnLegendBackgroundColor() {
		return this.legendBackgroundColor;
	}


	public Color getOwnLegendColor() {
		return this.legendColor;
	}

	public Color getLegendColor() {
		return JRStyleResolver.getLegendColor(this);
	}

	public JRFont getLegendFont() {
		return this.legendFont;
	}


	public void setLegendBackgroundColor(Color legendBackgroundColor) {
		Object old = this.legendBackgroundColor;
		this.legendBackgroundColor = legendBackgroundColor;
		getEventSupport().firePropertyChange(PROPERTY_LEGEND_BACKGROUND_COLOR, old, this.legendBackgroundColor);
	}


	public void setLegendColor(Color legendColor) {
		Object old = this.legendColor;
		this.legendColor = legendColor;
		getEventSupport().firePropertyChange(PROPERTY_LEGEND_COLOR, old, this.legendColor);
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
		getEventSupport().firePropertyChange(PROPERTY_LEGEND_POSITION, (float) old, (float) this.legendPosition);
	}

	/**
	 *
	 */
	public byte getHyperlinkType()
	{
		return JRHyperlinkHelper.getHyperlinkType(this);
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
	public JRExpression getTitleExpression()
	{
		return this.titleExpression;
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
	public JRExpression getAnchorNameExpression()
	{
		return this.anchorNameExpression;
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
	public JRExpression getHyperlinkAnchorExpression()
	{
		return this.hyperlinkAnchorExpression;
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
		getEventSupport().firePropertyChange(PROPERTY_RENDER_TYPE, old, this.renderType);
	}

	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}


	public void visit(JRVisitor visitor)
	{
		visitor.visitChart(this);
	}


	public int getBookmarkLevel()
	{
		return this.bookmarkLevel;
	}


	/**
	 *
	 */
	public String getCustomizerClass()
	{
		return this.customizerClass;
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


	public JRHyperlinkParameter[] getHyperlinkParameters()
	{
		return this.hyperlinkParameters;
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
		JRBaseChart clone = (JRBaseChart)super.clone();
		
		clone.lineBox = (JRLineBox)this.lineBox.clone(this);

		if (this.hyperlinkParameters != null)
		{
			clone.hyperlinkParameters = new JRHyperlinkParameter[this.hyperlinkParameters.length];
			for(int i = 0; i < this.hyperlinkParameters.length; i++)
			{
				clone.hyperlinkParameters[i] = (JRHyperlinkParameter)this.hyperlinkParameters[i].clone();
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
