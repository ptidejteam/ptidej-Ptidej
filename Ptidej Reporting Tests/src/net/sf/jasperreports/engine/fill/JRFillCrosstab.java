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
package net.sf.jasperreports.engine.fill;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import net.sf.jasperreports.crosstabs.JRCellContents;
import net.sf.jasperreports.crosstabs.JRCrosstab;
import net.sf.jasperreports.crosstabs.JRCrosstabBucket;
import net.sf.jasperreports.crosstabs.JRCrosstabCell;
import net.sf.jasperreports.crosstabs.JRCrosstabColumnGroup;
import net.sf.jasperreports.crosstabs.JRCrosstabDataset;
import net.sf.jasperreports.crosstabs.JRCrosstabGroup;
import net.sf.jasperreports.crosstabs.JRCrosstabMeasure;
import net.sf.jasperreports.crosstabs.JRCrosstabParameter;
import net.sf.jasperreports.crosstabs.JRCrosstabRowGroup;
import net.sf.jasperreports.crosstabs.base.JRBaseCrosstab;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.crosstabs.fill.JRCrosstabExpressionEvaluator;
import net.sf.jasperreports.crosstabs.fill.JRFillCrosstabCell;
import net.sf.jasperreports.crosstabs.fill.JRFillCrosstabColumnGroup;
import net.sf.jasperreports.crosstabs.fill.JRFillCrosstabGroup;
import net.sf.jasperreports.crosstabs.fill.JRFillCrosstabMeasure;
import net.sf.jasperreports.crosstabs.fill.JRFillCrosstabParameter;
import net.sf.jasperreports.crosstabs.fill.JRFillCrosstabRowGroup;
import net.sf.jasperreports.crosstabs.fill.calculation.BucketDefinition;
import net.sf.jasperreports.crosstabs.fill.calculation.BucketingService;
import net.sf.jasperreports.crosstabs.fill.calculation.CrosstabCell;
import net.sf.jasperreports.crosstabs.fill.calculation.HeaderCell;
import net.sf.jasperreports.crosstabs.fill.calculation.MeasureDefinition;
import net.sf.jasperreports.crosstabs.fill.calculation.BucketDefinition.Bucket;
import net.sf.jasperreports.crosstabs.fill.calculation.MeasureDefinition.MeasureValue;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionChunk;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintFrame;
import net.sf.jasperreports.engine.JRPrintRectangle;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JRVariable;
import net.sf.jasperreports.engine.JRVisitor;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignRectangle;
import net.sf.jasperreports.engine.util.JRStyleResolver;

import org.jfree.data.general.Dataset;

/**
 * Fill-time implementation of a {@link net.sf.jasperreports.crosstabs.JRCrosstab crosstab}.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRFillCrosstab.java,v 1.1 2008/09/29 16:20:01 guehene Exp $
 */
public class JRFillCrosstab extends JRFillElement implements JRCrosstab
{
	final protected JRCrosstab parentCrosstab;

	protected JRFillCrosstabDataset dataset;

	protected JRFillCrosstabRowGroup[] rowGroups;

	protected Map rowGroupsMap;

	protected JRFillCrosstabColumnGroup[] columnGroups;

	protected Map columnGroupsMap;

	protected JRFillCrosstabMeasure[] measures;

	protected BucketingService bucketingService;

	protected JRFillVariable[] variables;

	protected Map variablesMap;
	
	protected JRFillVariable[][][] totalVariables;
	protected boolean[][] retrieveTotal;

	protected JRFillCrosstabParameter[] parameters;

	protected Map parametersMap;

	protected JRCrosstabExpressionEvaluator crosstabEvaluator;

	protected JRFillCrosstabCell[][] crossCells;
	protected JRFillCellContents headerCell;
	protected JRFillCellContents whenNoDataCell;

	protected boolean hasData;
	protected HeaderCell[][] columnHeadersData;
	protected HeaderCell[][] rowHeadersData;
	protected CrosstabCell[][] cellData;
	protected MeasureValue[] grandTotals;

	private boolean percentage;

	private CrosstabFiller crosstabFiller;
	
	public JRFillCrosstab(JRBaseFiller filler, JRCrosstab crosstab, JRFillObjectFactory factory)
	{
		super(filler, crosstab, factory);

		this.parentCrosstab = crosstab;

		loadEvaluator(filler.getJasperReport());

		JRFillObjectFactory crosstabFactory = new JRFillObjectFactory(factory, this.crosstabEvaluator);
		
		this.headerCell = crosstabFactory.getCell(crosstab.getHeaderCell());

		copyRowGroups(crosstab, crosstabFactory);
		copyColumnGroups(crosstab, crosstabFactory);
		
		copyMeasures(crosstab, crosstabFactory);
		copyCells(crosstab, crosstabFactory);
		this.whenNoDataCell = crosstabFactory.getCell(crosstab.getWhenNoDataCell());
		
		this.dataset = factory.getCrosstabDataset(crosstab.getDataset(), this);

		copyParameters(crosstab, factory);
		copyVariables(crosstab, crosstabFactory);

		this.crosstabFiller = new CrosstabFiller();
	}

	/**
	 *
	 */
	public byte getMode()
	{
		return JRStyleResolver.getMode(this, MODE_TRANSPARENT);
	}

	private void copyRowGroups(JRCrosstab crosstab, JRFillObjectFactory factory)
	{
		JRCrosstabRowGroup[] groups = crosstab.getRowGroups();
		this.rowGroups = new JRFillCrosstabRowGroup[groups.length];
		this.rowGroupsMap = new HashMap();
		for (int i = 0; i < groups.length; ++i)
		{
			JRFillCrosstabRowGroup group = factory.getCrosstabRowGroup(groups[i]);
			group.getFillHeader().setVerticalPositionType(groups[i].getPosition());

			this.rowGroups[i] = group;
			this.rowGroupsMap.put(group.getName(), Integer.valueOf(i));
		}
	}

	private void copyColumnGroups(JRCrosstab crosstab, JRFillObjectFactory factory)
	{
		JRCrosstabColumnGroup[] groups = crosstab.getColumnGroups();
		this.columnGroups = new JRFillCrosstabColumnGroup[groups.length];
		this.columnGroupsMap = new HashMap();
		for (int i = 0; i < groups.length; ++i)
		{
			JRFillCrosstabColumnGroup group = factory.getCrosstabColumnGroup(groups[i]);
			this.columnGroups[i] = group;
			this.columnGroupsMap.put(group.getName(), Integer.valueOf(i));
		}
	}

	private void copyMeasures(JRCrosstab crosstab, JRFillObjectFactory factory)
	{
		JRCrosstabMeasure[] crossMeasures = crosstab.getMeasures();
		this.measures = new JRFillCrosstabMeasure[crossMeasures.length];
		for (int i = 0; i < crossMeasures.length; i++)
		{
			this.measures[i] = factory.getCrosstabMeasure(crossMeasures[i]);
		}
	}

	private void copyParameters(JRCrosstab crosstab, JRFillObjectFactory factory)
	{
		JRCrosstabParameter[] crossParams = crosstab.getParameters();
		this.parameters = new JRFillCrosstabParameter[crossParams.length];
		this.parametersMap = new HashMap();
		for (int i = 0; i < crossParams.length; i++)
		{
			this.parameters[i] = factory.getCrosstabParameter(crossParams[i]);
			this.parametersMap.put(this.parameters[i].getName(), this.parameters[i]);
		}
	}

	private void copyCells(JRCrosstab crosstab, JRFillObjectFactory factory)
	{
		JRCrosstabCell[][] crosstabCells = crosstab.getCells();		
		this.crossCells = new JRFillCrosstabCell[this.rowGroups.length + 1][this.columnGroups.length + 1];
		for (int i = 0; i <= this.rowGroups.length; ++i)
		{
			for (int j = 0; j <= this.columnGroups.length; ++j)
			{
				if (crosstabCells[i][j] != null)
				{
					this.crossCells[i][j] = factory.getCrosstabCell(crosstabCells[i][j]);
				}
			}
		}
	}

	private void copyVariables(JRCrosstab crosstab, JRFillObjectFactory factory)
	{
		JRVariable[] vars = crosstab.getVariables();
		this.variables = new JRFillVariable[vars.length];
		this.variablesMap = new HashMap();
		for (int i = 0; i < this.variables.length; i++)
		{
			this.variables[i] = factory.getVariable(vars[i]);
			this.variablesMap.put(this.variables[i].getName(), this.variables[i]);
		}
		
		Map totalVarPos = new HashMap();
		this.totalVariables = new JRFillVariable[this.rowGroups.length + 1][this.columnGroups.length + 1][this.measures.length];
		for (int row = 0; row <= this.rowGroups.length; ++row)
		{
			JRCrosstabRowGroup rowGroup = row == this.rowGroups.length ? null : this.rowGroups[row];
			for (int col = 0; col <= this.columnGroups.length; ++col)
			{
				JRCrosstabColumnGroup colGroup = col == this.columnGroups.length ? null : this.columnGroups[col];
				
				if (row < this.rowGroups.length || col < this.columnGroups.length)
				{
					for (int m = 0; m < this.measures.length; m++)
					{
						String totalVariableName = JRDesignCrosstab.getTotalVariableName(this.measures[m], rowGroup, colGroup);
						this.totalVariables[row][col][m] = (JRFillVariable) this.variablesMap.get(totalVariableName);
						totalVarPos.put(totalVariableName, new int[]{row, col});
					}
				}
			}
		}

		this.retrieveTotal = new boolean[this.rowGroups.length + 1][this.columnGroups.length + 1];
		
		//FIXME avoid this
		List expressions = JRExpressionCollector.collectExpressions(this.filler.getJasperReport(), crosstab);
		for (Iterator iter = expressions.iterator(); iter.hasNext();)
		{
			JRExpression expression = (JRExpression) iter.next();
			JRExpressionChunk[] chunks = expression.getChunks();
			if (chunks != null)
			{
				for (int i = 0; i < chunks.length; i++)
				{
					JRExpressionChunk chunk = chunks[i];
					if (chunk.getType() == JRExpressionChunk.TYPE_VARIABLE)
					{
						String varName = chunk.getText();
						int[] pos = (int[]) totalVarPos.get(varName);
						if (pos != null)
						{
							this.retrieveTotal[pos[0]][pos[1]] = true;
						}
					}
				}
			}
		}
	}

	protected void loadEvaluator(JasperReport jasperReport)
	{
		try
		{
			JREvaluator evaluator = JasperCompileManager.loadEvaluator(jasperReport, this.parentCrosstab);
			this.crosstabEvaluator = new JRCrosstabExpressionEvaluator(evaluator);
		}
		catch (JRException e)
		{
			throw new JRRuntimeException("Could not load evaluator for crosstab.", e);
		}
	}

	private BucketingService createService(byte evaluation) throws JRException
	{
		List rowBuckets = new ArrayList(this.rowGroups.length);
		for (int i = 0; i < this.rowGroups.length; ++i)
		{
			rowBuckets.add(createServiceBucket(this.rowGroups[i], evaluation));
		}

		List colBuckets = new ArrayList(this.columnGroups.length);
		for (int i = 0; i < this.columnGroups.length; ++i)
		{
			colBuckets.add(createServiceBucket(this.columnGroups[i], evaluation));
		}

		this.percentage = false;
		List measureList = new ArrayList(this.measures.length);
		for (int i = 0; i < this.measures.length; ++i)
		{
			measureList.add(createServiceMeasure(this.measures[i]));
			this.percentage |= this.measures[i].getPercentageOfType() == JRCrosstabMeasure.PERCENTAGE_TYPE_GRAND_TOTAL;
		}

		if (this.percentage)
		{
			((BucketDefinition) rowBuckets.get(0)).setComputeTotal();
			((BucketDefinition) colBuckets.get(0)).setComputeTotal();
		}
		
		return new BucketingService(rowBuckets, colBuckets, measureList, this.dataset.isDataPreSorted(), this.retrieveTotal);
	}

	private BucketDefinition createServiceBucket(JRCrosstabGroup group, byte evaluation) throws JRException
	{
		JRCrosstabBucket bucket = group.getBucket();

		Comparator comparator = null;
		JRExpression comparatorExpression = bucket.getComparatorExpression();
		if (comparatorExpression != null)
		{
			comparator = (Comparator) evaluateExpression(comparatorExpression, evaluation);
		}

		byte totalPosition = group.getTotalPosition();
		return new BucketDefinition(bucket.getExpression().getValueClass(), comparator, bucket.getOrder(), totalPosition);
	}

	private MeasureDefinition createServiceMeasure(JRFillCrosstabMeasure measure)
	{
		return new MeasureDefinition(
				measure.getValueClass(), 
				measure.getCalculation(), 
				measure.getIncrementerFactory()); 
	}

	protected void reset()
	{
		super.reset();

		for (int i = 0; i < this.variables.length; i++)
		{
			this.variables[i].setValue(null);
			this.variables[i].setInitialized(true);
		}
	}

	protected void evaluate(byte evaluation) throws JRException
	{
		reset();

		evaluatePrintWhenExpression(evaluation);
		evaluateProperties(evaluation);

		if (isPrintWhenExpressionNull() || isPrintWhenTrue())
		{
			this.dataset.evaluateDatasetRun(evaluation);

			initEvaluator(evaluation);

			this.bucketingService.processData();

			this.hasData = this.bucketingService.hasData();
			
			if (this.hasData)
			{
				this.columnHeadersData = this.bucketingService.getColumnHeaders();
				this.rowHeadersData = this.bucketingService.getRowHeaders();
				this.cellData = this.bucketingService.getCrosstabCells();
				if (this.percentage)
				{
					this.grandTotals = this.bucketingService.getGrandTotals();
				}

				this.crosstabFiller.initCrosstab();
			}			
		}
	}

	protected void initEvaluator(byte evaluation) throws JRException
	{
		Map parameterValues = 
			JRFillSubreport.getParameterValues(
				this.filler, 
				getParametersMapExpression(), 
				getParameters(), 
				evaluation, 
				true, 
				false,//hasResourceBundle
				false//hasFormatFactory
				);
		
		ResourceBundle resBdl = (ResourceBundle) parameterValues.get(JRParameter.REPORT_RESOURCE_BUNDLE);
		if (resBdl == null)
		{
			JRFillParameter resourceBundleParam = (JRFillParameter) this.filler.getParametersMap().get(JRParameter.REPORT_RESOURCE_BUNDLE);
			parameterValues.put(JRParameter.REPORT_RESOURCE_BUNDLE, resourceBundleParam.getValue());
		}
		
		parameterValues.put(JRParameter.REPORT_PARAMETERS_MAP, parameterValues);

		for (int i = 0; i < this.parameters.length; i++)
		{
			Object value = parameterValues.get(this.parameters[i].getName());
			this.parameters[i].setValue(value);
		}

		this.crosstabEvaluator.init(this.parametersMap, this.variablesMap, this.filler.getWhenResourceMissingType());
	}

	protected void initBucketingService()
	{
		if (this.bucketingService == null)
		{
			try
			{
				this.bucketingService = createService(JRExpression.EVALUATION_TIME_NOW);
			}
			catch (JRException e)
			{
				throw new JRRuntimeException("Could not create bucketing service", e);
			}
		}
		else
		{
			this.bucketingService.clear();
		}
	}

	protected boolean prepare(int availableStretchHeight, boolean isOverflow) throws JRException
	{
		super.prepare(availableStretchHeight, isOverflow);

		if (!isToPrint())
		{
			return false;
		}

		if (availableStretchHeight < getRelativeY() - getY() - getBandBottomY())
		{
			setToPrint(false);
			return true;
		}

		if (isOverflow && this.crosstabFiller.ended() && isAlreadyPrinted())
		{
			if (isPrintWhenDetailOverflows())
			{
				rewind();
				setReprinted(true);
			}
			else
			{
				setStretchHeight(getHeight());
				setToPrint(false);

				return false;
			}
		}

		if (isOverflow && isPrintWhenDetailOverflows())
		{
			setReprinted(true);
		}

		int availableHeight = getHeight() + availableStretchHeight - getRelativeY() + getY() + getBandBottomY();
		this.crosstabFiller.fill(availableHeight);
		
		boolean willOverflow = this.crosstabFiller.willOverflow();
		setStretchHeight(willOverflow ? availableHeight : this.crosstabFiller.getUsedHeight());
		
		return willOverflow;
	}

	protected JRPrintElement fill()
	{
		JRPrintRectangle printRectangle = null;

		printRectangle = new JRTemplatePrintRectangle(getJRTemplateRectangle());
		printRectangle.setX(getX());
		printRectangle.setY(getRelativeY());
		printRectangle.setWidth(getWidth());
		printRectangle.setHeight(getStretchHeight());

		return printRectangle;
	}

	protected JRTemplateRectangle getJRTemplateRectangle()
	{
		JRStyle style = getStyle();
		JRTemplateRectangle template = (JRTemplateRectangle) getTemplate(style);
		if (template == null)
		{
			JRDesignRectangle rectangle = new JRDesignRectangle();

			rectangle.setKey(getKey());
			rectangle.setPositionType(getPositionType());
			// rectangle.setPrintRepeatedValues(isPrintRepeatedValues());
			rectangle.setMode(getMode());
			rectangle.setX(getX());
			rectangle.setY(getY());
			rectangle.setWidth(getWidth());
			rectangle.setHeight(getHeight());
			rectangle.setRemoveLineWhenBlank(isRemoveLineWhenBlank());
			rectangle.setPrintInFirstWholeBand(isPrintInFirstWholeBand());
			rectangle.setPrintWhenDetailOverflows(isPrintWhenDetailOverflows());
			rectangle.setPrintWhenGroupChanges(getPrintWhenGroupChanges());
			rectangle.setForecolor(getForecolor());
			rectangle.setBackcolor(getBackcolor());
			rectangle.getLinePen().setLineWidth(0f);

			template = new JRTemplateRectangle(this.band.getOrigin(), this.filler.getJasperPrint().getDefaultStyleProvider(), rectangle);
			
			registerTemplate(style, template);
		}

		return template;
	}

	protected void rewind()
	{
		this.crosstabFiller.initCrosstab();
	}

	protected List getPrintElements()
	{
		List printElements = this.crosstabFiller.getPrintElements();
		
		if (getRunDirection() == RUN_DIRECTION_RTL)
		{
			mirrorPrintElements(printElements);
		}
		
		return printElements;
	}

	protected void mirrorPrintElements(List printElements)
	{
		for (Iterator it = printElements.iterator(); it.hasNext();)
		{
			JRPrintElement element = (JRPrintElement) it.next();
			int mirrorX = getWidth() - element.getX() - element.getWidth();
			element.setX(mirrorX);
		}
	}

	protected void resolveElement(JRPrintElement element, byte evaluation)
	{
		// nothing
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
		visitor.visitCrosstab(this);
	}

	public int getId()
	{
		return this.parentCrosstab.getId();
	}

	public JRCrosstabDataset getDataset()
	{
		return this.dataset;
	}

	public JRCrosstabRowGroup[] getRowGroups()
	{
		return this.rowGroups;
	}

	public JRCrosstabColumnGroup[] getColumnGroups()
	{
		return this.columnGroups;
	}

	public JRCrosstabMeasure[] getMeasures()
	{
		return this.measures;
	}

	
	/**
	 * Fill-time crosstab input dataset implementation.
	 *  
	 * @author Lucian Chirita (lucianc@users.sourceforge.net)
	 */
	public class JRFillCrosstabDataset extends JRFillElementDataset implements JRCrosstabDataset
	{
		private Object[] bucketValues;

		private Object[] measureValues;

		public JRFillCrosstabDataset(JRCrosstabDataset dataset, JRFillObjectFactory factory)
		{
			super(dataset, factory);

			this.bucketValues = new Object[JRFillCrosstab.this.rowGroups.length + JRFillCrosstab.this.columnGroups.length];
			this.measureValues = new Object[JRFillCrosstab.this.measures.length];
		}

		protected void customInitialize()
		{
			initBucketingService();
		}

		protected void customEvaluate(JRCalculator calculator) throws JRExpressionEvalException
		{
			for (int i = 0; i < JRFillCrosstab.this.rowGroups.length; i++)
			{
				this.bucketValues[i] = calculator.evaluate(JRFillCrosstab.this.rowGroups[i].getBucket().getExpression());
			}

			for (int i = 0; i < JRFillCrosstab.this.columnGroups.length; ++i)
			{
				this.bucketValues[i + JRFillCrosstab.this.rowGroups.length] = calculator.evaluate(JRFillCrosstab.this.columnGroups[i].getBucket().getExpression());
			}

			for (int i = 0; i < JRFillCrosstab.this.measures.length; i++)
			{
				this.measureValues[i] = calculator.evaluate(JRFillCrosstab.this.measures[i].getValueExpression());
			}
		}

		protected void customIncrement()
		{
			try
			{
				JRFillCrosstab.this.bucketingService.addData(this.bucketValues, this.measureValues);
			}
			catch (JRException e)
			{
				throw new JRRuntimeException("Error incrementing crosstab dataset", e);
			}
		}

		protected Dataset getCustomDataset()
		{
			return null;
		}

		public void collectExpressions(JRExpressionCollector collector)
		{
		}

		public boolean isDataPreSorted()
		{
			return ((JRCrosstabDataset) this.parent).isDataPreSorted();
		}
	}
	
	/**
	 * Crosstab filler class.
	 *  
	 * @author Lucian Chirita (lucianc@users.sourceforge.net)
	 */
	protected class CrosstabFiller
	{
		private int yOffset;
		private boolean willOverflow;

		private int[] rowHeadersXOffsets;
		
		private boolean[] columnBreakable;
		private boolean[] rowBreakable;
		private int[] columnCount;
		private int[] rowCount;
		private int[] columnXOffsets;
		
		private boolean noDataCellPrinted;
		
		private int startRowIndex;
		private int startColumnIndex;
		private int lastColumnIndex;
		private List columnHeaders;

		private List printRows;

		private HeaderCell[] spanHeaders;
		private int[] spanHeadersStart;

		private List rowYs = new ArrayList();
		private int rowIdx;
		
		private List preparedRow = new ArrayList();
		private int preparedRowHeight;
		
		private boolean printRowHeaders;
		private boolean printColumnHeaders;
		
		private JRFillVariable rowCountVar;
		private JRFillVariable colCountVar;

		protected CrosstabFiller()
		{
			setRowHeadersXOffsets();

			this.printRows = new ArrayList();
			
			this.rowCountVar = (JRFillVariable) JRFillCrosstab.this.variablesMap.get(JRCrosstab.VARIABLE_ROW_COUNT);
			this.colCountVar = (JRFillVariable) JRFillCrosstab.this.variablesMap.get(JRCrosstab.VARIABLE_COLUMN_COUNT);
		}
		
		protected void initCrosstab()
		{
			this.columnXOffsets = computeOffsets(JRFillCrosstab.this.columnHeadersData, JRFillCrosstab.this.columnGroups, true);
			this.columnBreakable = computeBreakableHeaders(JRFillCrosstab.this.columnHeadersData, JRFillCrosstab.this.columnGroups, this.columnXOffsets, true, true);
			this.columnCount = computeCounts(JRFillCrosstab.this.columnHeadersData);
			
			int[] rowYOffsets = computeOffsets(JRFillCrosstab.this.rowHeadersData, JRFillCrosstab.this.rowGroups, false);
			this.rowBreakable = computeBreakableHeaders(JRFillCrosstab.this.rowHeadersData, JRFillCrosstab.this.rowGroups, rowYOffsets, false, false);
			this.rowCount = computeCounts(JRFillCrosstab.this.rowHeadersData);
			
			this.spanHeaders = new HeaderCell[JRFillCrosstab.this.rowGroups.length - 1];
			this.spanHeadersStart = new int[JRFillCrosstab.this.rowGroups.length - 1];
			
			this.startRowIndex = 0;
			this.startColumnIndex = 0;
			this.lastColumnIndex = 0;
			this.noDataCellPrinted = false;
		}

		protected void setRowHeadersXOffsets()
		{
			this.rowHeadersXOffsets = new int[JRFillCrosstab.this.rowGroups.length + 1];
			this.rowHeadersXOffsets[0] = 0;
			for (int i = 0; i < JRFillCrosstab.this.rowGroups.length; i++)
			{
				this.rowHeadersXOffsets[i + 1] = this.rowHeadersXOffsets[i] + JRFillCrosstab.this.rowGroups[i].getWidth();
			}
		}

		protected int[] computeOffsets(HeaderCell[][] headersData, JRFillCrosstabGroup[] groups, boolean width)
		{
			int[] offsets = new int[headersData[0].length + 1];
			offsets[0] = 0;
			for (int i = 0; i < headersData[0].length; i++)
			{
				int size = 0;
				for (int j = groups.length - 1; j >= 0; --j)
				{
					if (headersData[j][i] != null)
					{
						JRFillCellContents cell = headersData[j][i].isTotal() ? groups[j].getFillTotalHeader() : groups[j].getFillHeader();
						size = cell == null ? 0 : (width ? cell.getWidth() : cell.getHeight());
						break;
					}
				}

				offsets[i + 1] = offsets[i] + size;
			}

			return offsets;
		}

		protected boolean[] computeBreakableHeaders(HeaderCell[][] headersData, JRFillCrosstabGroup[] groups, int[] offsets, boolean width, boolean startHeaders)
		{
			boolean[] breakable = new boolean[headersData[0].length];
			for (int i = 0; i < breakable.length; i++)
			{
				breakable[i] = true;
			}

			for (int j = 0; j < groups.length; ++j)
			{
				JRFillCellContents fillHeader = groups[j].getFillHeader();
				
				if (fillHeader != null)
				{
					int size = width ? fillHeader.getWidth() : fillHeader.getHeight();
					
					for (int i = 0; i < headersData[0].length; i++)
					{
						HeaderCell header = headersData[j][i];
						if (header != null && !header.isTotal() && header.getLevelSpan() > 1)
						{
							int span = header.getLevelSpan();
							
							if (startHeaders)
							{
								for (int k = i + 1; k < i + span && offsets[k] - offsets[i] < size; ++k)
								{
									breakable[k] = false;
								}
							}
							
							for (int k = i + span - 1; k > i && offsets[i + span] - offsets[k] < size; --k)
							{
								breakable[k] = false;
							}
						}
					}
				}
			}

			return breakable;
		}

		private int[] computeCounts(HeaderCell[][] headersData)
		{
			int[] counts = new int[headersData[0].length];
			
			HeaderCell[] lastHeaders = headersData[headersData.length - 1];
			for (int i = 0, c = 0; i < counts.length; ++i)
			{
				HeaderCell lastHeader = lastHeaders[i];
				if (lastHeader != null && !lastHeader.isTotal())
				{
					++c;
				}
				
				counts[i] = c;
			}
			
			return counts;
		}

		protected void fill(int availableHeight) throws JRException
		{
			this.printRows.clear();

			this.yOffset = 0;
			this.willOverflow = false;
			
			fillVerticalCrosstab(availableHeight);
		}
		
		protected boolean willOverflow()
		{
			return this.willOverflow;
		}
		
		protected int getUsedHeight()
		{
			return this.yOffset;
		}
		
		protected boolean ended()
		{
			return JRFillCrosstab.this.hasData ? (this.startRowIndex >= JRFillCrosstab.this.rowHeadersData[0].length && this.startColumnIndex >= JRFillCrosstab.this.columnHeadersData[0].length) : this.noDataCellPrinted;
		}
		
		protected void fillVerticalCrosstab(int availableHeight) throws JRException
		{
			if (!JRFillCrosstab.this.hasData)
			{
				fillNoDataCell(availableHeight);			
				return;
			}
			
			this.printRowHeaders = this.startColumnIndex == 0 || isRepeatRowHeaders();
			int rowHeadersXOffset = this.printRowHeaders ? this.rowHeadersXOffsets[JRFillCrosstab.this.rowGroups.length] : 0;

			if (this.startColumnIndex == this.lastColumnIndex)
			{
				int availableWidth = getWidth();

				this.columnHeaders = getGroupHeaders(availableWidth - rowHeadersXOffset, this.columnXOffsets, this.columnBreakable, this.startColumnIndex, JRFillCrosstab.this.columnHeadersData, JRFillCrosstab.this.columnGroups);
				this.lastColumnIndex = this.startColumnIndex + this.columnHeaders.size();

				if (this.startColumnIndex == this.lastColumnIndex)
				{
					throw new JRRuntimeException("Not enough space to render the crosstab.");
				}
			}
			
			this.printColumnHeaders = this.startRowIndex == 0 || isRepeatColumnHeaders();
			List columnHeaderRows = null;
			if (this.printColumnHeaders)
			{
				columnHeaderRows = fillColumnHeaders(rowHeadersXOffset, availableHeight - this.yOffset);
				if (this.willOverflow)
				{
					return;
				}
			}

			int lastRowIndex = fillRows(rowHeadersXOffset, availableHeight - this.yOffset);

			if (lastRowIndex == this.startRowIndex)
			{
				this.willOverflow = true;
				return;
			}

			if (columnHeaderRows != null)
			{
				this.printRows.addAll(columnHeaderRows);
			}
			
			if (lastRowIndex >= JRFillCrosstab.this.rowHeadersData[0].length)
			{
				this.startColumnIndex = this.lastColumnIndex;

				if (this.startColumnIndex < JRFillCrosstab.this.columnHeadersData[0].length)
				{
					this.startRowIndex = lastRowIndex = 0;

					this.yOffset += getColumnBreakOffset();
					fillVerticalCrosstab(availableHeight);
					return;
				}
			}

			boolean fillEnded = lastRowIndex >= JRFillCrosstab.this.rowHeadersData[0].length && this.lastColumnIndex >= JRFillCrosstab.this.columnHeadersData[0].length;
			if (fillEnded)
			{
				setStretchHeight(this.yOffset);
			}
			else
			{
				setStretchHeight(availableHeight);
			}

			this.startRowIndex = lastRowIndex;

			this.willOverflow = !fillEnded;
		}

		
		protected List getGroupHeaders(int available, int[] offsets, boolean[] breakable, int firstIndex, HeaderCell[][] headersData, JRFillCrosstabGroup[] groups)
		{
			List headers = new ArrayList();

			int maxOffset = available + offsets[firstIndex];
			int lastIndex;
			for (lastIndex = firstIndex; lastIndex < headersData[0].length && offsets[lastIndex + 1] <= maxOffset; ++lastIndex)
			{
				HeaderCell[] groupHeaders = new HeaderCell[groups.length];

				for (int j = 0; j < groups.length; ++j)
				{
					groupHeaders[j] = headersData[j][lastIndex];
				}

				headers.add(groupHeaders);
			}

			
			if (lastIndex < headersData[0].length)
			{
				while(lastIndex > firstIndex && !breakable[lastIndex])
				{
					--lastIndex;
					headers.remove(headers.size() - 1);
				}
			}
			
			if (lastIndex > firstIndex)
			{
				if (firstIndex > 0)
				{
					HeaderCell[] firstHeaders = (HeaderCell[]) headers.get(0);

					for (int j = 0; j < groups.length; ++j)
					{
						HeaderCell header = headersData[j][firstIndex];

						if (header == null)
						{
							int spanIndex = getSpanIndex(firstIndex, j, headersData);
							if (spanIndex >= 0)
							{
								HeaderCell spanCell = headersData[j][spanIndex];
								int headerEndIdx = spanCell.getLevelSpan() + spanIndex;
								if (headerEndIdx > lastIndex)
								{
									headerEndIdx = lastIndex;
								}
								firstHeaders[j] = HeaderCell.createLevelSpanCopy(spanCell, headerEndIdx - firstIndex);
							}
						}
					}
				}

				if (lastIndex < headersData[0].length)
				{
					for (int j = 0; j < groups.length; ++j)
					{
						HeaderCell header = headersData[j][lastIndex];

						if (header == null)
						{
							int spanIndex = getSpanIndex(lastIndex, j, headersData);
							if (spanIndex >= firstIndex)
							{
								HeaderCell spanCell = headersData[j][spanIndex];
								HeaderCell[] headerCells = (HeaderCell[]) headers.get(spanIndex - firstIndex);
								headerCells[j] = HeaderCell.createLevelSpanCopy(spanCell, lastIndex - spanIndex);
							}
						}
					}
				}
			}

			return headers;
		}

		
		protected int getSpanIndex(int i, int j, HeaderCell[][] headersData)
		{
			int spanIndex = i - 1;
			while (spanIndex >= 0 && headersData[j][spanIndex] == null)
			{
				--spanIndex;
			}

			if (spanIndex >= 0)
			{
				HeaderCell spanCell = headersData[j][spanIndex];
				int span = spanCell.getLevelSpan();

				if (span > i - spanIndex)
				{
					return spanIndex;
				}
			}

			return -1;
		}
		
		
		protected void fillNoDataCell(int availableHeight) throws JRException
		{
			if (JRFillCrosstab.this.whenNoDataCell == null)
			{
				this.noDataCellPrinted = true;
			}
			else
			{
				if (availableHeight < JRFillCrosstab.this.whenNoDataCell.getHeight())
				{
					this.willOverflow = true;
				}
				else
				{
					JRFillCrosstab.this.whenNoDataCell.evaluate(JRExpression.EVALUATION_DEFAULT);
					JRFillCrosstab.this.whenNoDataCell.prepare(availableHeight - JRFillCrosstab.this.whenNoDataCell.getHeight());
					
					this.willOverflow = JRFillCrosstab.this.whenNoDataCell.willOverflow();
					
					if (!this.willOverflow)
					{
						JRFillCrosstab.this.whenNoDataCell.setX(0);
						JRFillCrosstab.this.whenNoDataCell.setY(0);
						
						JRPrintFrame printCell = JRFillCrosstab.this.whenNoDataCell.fill();
						List noDataRow = new ArrayList(1);
						noDataRow.add(printCell);
						addPrintRow(noDataRow);
						
						this.yOffset += JRFillCrosstab.this.whenNoDataCell.getPrintHeight();
						this.noDataCellPrinted = true;
					}
				}
			}
		}
		

		protected List fillColumnHeaders(int rowHeadersXOffset, int availableHeight) throws JRException
		{
			JRFillCellContents[][] columnHeaderRows = new JRFillCellContents[JRFillCrosstab.this.columnGroups.length][this.lastColumnIndex - this.startColumnIndex + 1];
			
			this.rowYs.clear();
			this.rowYs.add(Integer.valueOf(0));
			
			if (this.printRowHeaders && JRFillCrosstab.this.headerCell != null)
			{
				JRFillCellContents contents = fillHeader(availableHeight);

				if (this.willOverflow)
				{
					return null;
				}

				columnHeaderRows[JRFillCrosstab.this.columnGroups.length - 1][0] = contents;
			}
			
			rows:
			for (this.rowIdx = 0; this.rowIdx < JRFillCrosstab.this.columnGroups.length; this.rowIdx++)
			{
				for (int columnIdx = this.startColumnIndex; columnIdx < this.lastColumnIndex; ++columnIdx)
				{
					HeaderCell[] headers = (HeaderCell[]) this.columnHeaders.get(columnIdx - this.startColumnIndex);
					HeaderCell cell = headers[this.rowIdx];
					
					if (cell != null)
					{
						JRFillCellContents contents = prepareColumnHeader(cell, columnIdx, rowHeadersXOffset, availableHeight);
						columnHeaderRows[this.rowIdx + cell.getDepthSpan() - 1][columnIdx - this.startColumnIndex + 1] = contents;
						
						if (this.willOverflow)
						{
							break rows;
						}
					}
				}

				int rowStretchHeight = stretchColumnHeadersRow(columnHeaderRows[this.rowIdx]);
				this.rowYs.add(Integer.valueOf(((Integer) this.rowYs.get(this.rowIdx)).intValue() + rowStretchHeight));
			}
			
			List headerRows;
			if (this.willOverflow)
			{
				headerRows = null;				
				releaseColumnHeaderCells(columnHeaderRows);
			}
			else
			{
				headerRows = fillColumnHeaders(columnHeaderRows);
				this.yOffset += ((Integer) this.rowYs.get(JRFillCrosstab.this.columnGroups.length)).intValue();
			}

			resetVariables();
			
			return headerRows;
		}

		
		private void setCountVars(int rowIdx, int colIdx)
		{
			if (rowIdx == -1)
			{
				this.rowCountVar.setValue(null);
			}
			else
			{
				this.rowCountVar.setValue(Integer.valueOf(this.rowCount[rowIdx]));
			}
			
			if (colIdx == -1)
			{
				this.colCountVar.setValue(null);
			}
			else
			{
				this.colCountVar.setValue(Integer.valueOf(this.columnCount[colIdx]));
			}
		}
		
		private JRFillCellContents fillHeader(int availableHeight) throws JRException
		{
			setCountVars(-1, -1);
			
			JRFillCellContents contents = JRFillCrosstab.this.headerCell.getWorkingClone();
			contents.evaluate(JRExpression.EVALUATION_DEFAULT);
			contents.prepare(availableHeight - JRFillCrosstab.this.headerCell.getHeight());
			
			this.willOverflow = contents.willOverflow();
			
			if (!this.willOverflow)
			{
				contents.setX(0);
				contents.setY(this.yOffset);
				contents.setVerticalSpan(JRFillCrosstab.this.columnGroups.length);
			}
			return contents;
		}
		
		private JRFillCellContents prepareColumnHeader(HeaderCell cell, int columnIdx, int xOffset, int availableHeight) throws JRException
		{
			JRFillCrosstabColumnGroup group = JRFillCrosstab.this.columnGroups[this.rowIdx];
			JRFillCellContents contents = cell.isTotal() ? group.getFillTotalHeader() : group.getFillHeader();

			int width = this.columnXOffsets[columnIdx + cell.getLevelSpan()] - this.columnXOffsets[columnIdx];
			int height = contents.getHeight();
			
			if (width <= 0 || height <= 0)
			{
				return null;
			}
			
			JRFillCellContents preparedContents = null;
			
			int rowY = ((Integer) this.rowYs.get(this.rowIdx)).intValue();
			int cellAvailableStretch = availableHeight - rowY - height;
			
			if (cellAvailableStretch >= 0)
			{
				setCountVars(-1, columnIdx);
				setGroupVariables(JRFillCrosstab.this.columnGroups, cell.getBucketValues());
				
				contents = contents.getTransformedContents(width, height, group.getPosition(), JRCellContents.POSITION_Y_TOP);
				boolean firstOnRow = columnIdx == this.startColumnIndex && (!this.printRowHeaders || JRFillCrosstab.this.headerCell == null);
				contents = contents.getBoxContents(
						firstOnRow && getRunDirection() == RUN_DIRECTION_LTR,
						firstOnRow && getRunDirection() == RUN_DIRECTION_RTL,
						false);
				contents = contents.getWorkingClone();

				contents.evaluate(JRExpression.EVALUATION_DEFAULT);
				contents.prepare(cellAvailableStretch);

				if (contents.willOverflow())
				{
					this.willOverflow = true;
				}
				else
				{
					contents.setX(this.columnXOffsets[columnIdx] - this.columnXOffsets[this.startColumnIndex] + xOffset);
					contents.setY(rowY + this.yOffset);
					contents.setVerticalSpan(cell.getDepthSpan());
					
					preparedContents = contents;
				}
			}
			else
			{
				this.willOverflow = true;
			}
			
			return preparedContents;
		}

		
		private int stretchColumnHeadersRow(JRFillCellContents[] headers)
		{
			int rowY = ((Integer) this.rowYs.get(this.rowIdx)).intValue();
			
			int rowStretchHeight = 0;
			for (int j = 0; j < headers.length; j++)
			{
				JRFillCellContents contents = headers[j];
				
				if (contents != null)
				{
					int startRowY = rowY;
					if (contents.getVerticalSpan() > 1)
					{
						startRowY = ((Integer) this.rowYs.get(this.rowIdx - contents.getVerticalSpan() + 1)).intValue();
					}
					
					int height = contents.getPrintHeight() - rowY + startRowY;
					
					if (height > rowStretchHeight)
					{
						rowStretchHeight = height;
					}
				}
			}
			
			for (int j = 0; j < headers.length; j++)
			{
				JRFillCellContents contents = headers[j];
				
				if (contents != null)
				{
					int startRowY = rowY;
					if (contents.getVerticalSpan() > 1)
					{
						startRowY = ((Integer) this.rowYs.get(this.rowIdx - contents.getVerticalSpan() + 1)).intValue();
					}
					
					contents.stretchTo(rowStretchHeight + rowY - startRowY);
				}
			}
			
			return rowStretchHeight;
		}

		
		private List fillColumnHeaders(JRFillCellContents[][] columnHeaderRows) throws JRException
		{
			List headerRows = new ArrayList(JRFillCrosstab.this.columnGroups.length);
			
			for (int i = 0; i < columnHeaderRows.length; ++i)
			{
				List headerRow = new ArrayList(this.lastColumnIndex - this.startColumnIndex);
				headerRows.add(headerRow);
				
				for (int j = 0; j < columnHeaderRows[i].length; j++)
				{
					JRFillCellContents contents = columnHeaderRows[i][j];
					
					if (contents != null)
					{
						headerRow.add(contents.fill());
						contents.releaseWorkingClone();
					}
				}
			}
			
			return headerRows;
		}

		private void releaseColumnHeaderCells(JRFillCellContents[][] columnHeaderRows) throws JRException
		{
			for (int i = 0; i < columnHeaderRows.length; ++i)
			{
				for (int j = 0; j < columnHeaderRows[i].length; j++)
				{
					JRFillCellContents contents = columnHeaderRows[i][j];
					
					if (contents != null)
					{
						contents.rewind();
						contents.releaseWorkingClone();
					}
				}
			}
		}
		
		protected int fillRows(int xOffset, int availableHeight) throws JRException
		{
			this.rowYs.clear();			
			this.rowYs.add(Integer.valueOf(0));

			for (this.rowIdx = 0; this.rowIdx < JRFillCrosstab.this.cellData.length - this.startRowIndex; ++this.rowIdx)
			{
				initPreparedRow();
				
				prepareRow(xOffset, availableHeight);

				if (this.willOverflow)
				{
					break;
				}
				
				fillRow();
				
				this.rowYs.add(Integer.valueOf(((Integer) this.rowYs.get(this.rowIdx)).intValue() + this.preparedRowHeight));
			}
			
			if (this.rowIdx < JRFillCrosstab.this.cellData.length - this.startRowIndex)//overflow
			{
				releasePreparedRow();
				
				if (this.printRowHeaders)
				{
					fillContinuingRowHeaders(xOffset, availableHeight);
				}
			}
			
			this.yOffset += ((Integer) this.rowYs.get(this.rowIdx)).intValue();

			return this.rowIdx + this.startRowIndex;
		}

		private void initPreparedRow()
		{
			this.preparedRow.clear();
			this.preparedRowHeight = 0;
		}

		private void removeFilledRows(int rowsToRemove)
		{
			if (rowsToRemove > 0)
			{
				for (int i = 0; i < rowsToRemove; ++i)
				{
					this.printRows.remove(this.printRows.size() - 1);
					this.rowYs.remove(this.rowYs.size() - 1);
				}
				
				this.rowIdx -= rowsToRemove;
			}
		}

		private void releasePreparedRow() throws JRException
		{
			for (Iterator it = this.preparedRow.iterator(); it.hasNext();)
			{
				JRFillCellContents cell = (JRFillCellContents) it.next();
				cell.rewind();
				cell.releaseWorkingClone();
			}
			
			this.preparedRow.clear();
		}

		private void fillRow() throws JRException
		{
			int rowY = ((Integer) this.rowYs.get(this.rowIdx)).intValue();
			
			List rowPrints = new ArrayList(this.preparedRow.size());
			for (Iterator it = this.preparedRow.iterator(); it.hasNext();)
			{
				JRFillCellContents cell = (JRFillCellContents) it.next();
				
				int spanHeight = 0;
				if (cell.getVerticalSpan() > 1)
				{
					spanHeight = rowY - ((Integer) this.rowYs.get(this.rowIdx - cell.getVerticalSpan() + 1)).intValue();
				}
				
				cell.stretchTo(this.preparedRowHeight + spanHeight);
				rowPrints.add(cell.fill());
				
				cell.releaseWorkingClone();
			}
			
			addPrintRow(rowPrints);
		}
		
		private void prepareRow(int xOffset, int availableHeight) throws JRException
		{
			for (int col = this.startColumnIndex; col < this.lastColumnIndex; ++col)
			{
				CrosstabCell data = JRFillCrosstab.this.cellData[this.rowIdx + this.startRowIndex][col];
				boolean overflow = prepareDataCell(data, col, availableHeight, xOffset);
				
				if (overflow)
				{
					this.willOverflow = true;
					return;
				}
			}
			
			resetVariables();
			
			if (this.printRowHeaders)
			{
				for (int j = 0; j < JRFillCrosstab.this.rowGroups.length; j++)
				{
					HeaderCell cell = JRFillCrosstab.this.rowHeadersData[j][this.rowIdx + this.startRowIndex];
					
					boolean overflow = false;
					if (cell == null)
					{
						overflow = prepareClosingRowHeader(j, availableHeight);
					}
					else
					{
						if (cell.getLevelSpan() > 1)
						{
							this.spanHeaders[j] = cell;
							this.spanHeadersStart[j] = this.rowIdx + this.startRowIndex;
							continue;
						}

						overflow = prepareRowHeader(j, cell, 1, availableHeight);
					}
					
					if (overflow)
					{
						this.willOverflow = true;
						return;
					}
				}
				
				resetVariables();
			}
		}
		
		private boolean prepareDataCell(CrosstabCell data, int column, int availableHeight, int xOffset) throws JRException
		{
			int rowY = ((Integer) this.rowYs.get(this.rowIdx)).intValue();
			
			JRFillCrosstabCell cell = JRFillCrosstab.this.crossCells[data.getRowTotalGroupIndex()][data.getColumnTotalGroupIndex()];
			JRFillCellContents contents = cell == null ? null : cell.getFillContents();
			if (contents == null || contents.getWidth() <= 0 || contents.getHeight() <= 0)
			{
				return false;
			}
			
			int cellAvailableStretch = availableHeight - rowY - contents.getHeight();
			boolean overflow = cellAvailableStretch < 0;
			if (!overflow)
			{
				boolean leftEmpty = this.startColumnIndex != 0 && !isRepeatRowHeaders();
				boolean topEmpty = this.startRowIndex != 0 && !isRepeatColumnHeaders();
				
				setCountVars(this.rowIdx + this.startRowIndex, column);
				setGroupVariables(JRFillCrosstab.this.rowGroups, data.getRowBucketValues());
				setGroupVariables(JRFillCrosstab.this.columnGroups, data.getColumnBucketValues());
				setMeasureVariables(data);
				
				boolean firstOnRow = leftEmpty && column == this.startColumnIndex;
				contents = contents.getBoxContents(
						firstOnRow && getRunDirection() == RUN_DIRECTION_LTR,
						firstOnRow && getRunDirection() == RUN_DIRECTION_RTL,
						topEmpty && this.rowIdx == 0);
				contents = contents.getWorkingClone();
				
				contents.evaluate(JRExpression.EVALUATION_DEFAULT);
				contents.prepare(cellAvailableStretch);
								
				this.preparedRow.add(contents);
				
				overflow = contents.willOverflow();
				
				if (!overflow)
				{
					contents.setX(this.columnXOffsets[column] - this.columnXOffsets[this.startColumnIndex] + xOffset);
					contents.setY(rowY + this.yOffset);

					int rowCellHeight = contents.getPrintHeight();
					if (rowCellHeight > this.preparedRowHeight)
					{
						this.preparedRowHeight = rowCellHeight;
					}
				}
			}
			
			return overflow;
		}

		private boolean prepareRowHeader(int rowGroup, HeaderCell cell, int vSpan, int availableHeight) throws JRException
		{
			JRFillCrosstabRowGroup group = JRFillCrosstab.this.rowGroups[rowGroup];
			JRFillCellContents contents = cell.isTotal() ? group.getFillTotalHeader() : group.getFillHeader();

			if (contents.getWidth() <= 0 || contents.getHeight() <= 0)
			{
				return false;
			}
			
			int spanHeight = 0;
			int headerY = ((Integer) this.rowYs.get(this.rowIdx - vSpan + 1)).intValue();
			if (vSpan > 1)
			{
				spanHeight += ((Integer) this.rowYs.get(this.rowIdx)).intValue() - headerY;
			}
			int rowHeight = spanHeight + this.preparedRowHeight;
			
			boolean stretchContents = group.getPosition() == JRCellContents.POSITION_Y_STRETCH;
			int contentsHeight = stretchContents ? rowHeight : contents.getHeight();
			
			int cellAvailableStretch = availableHeight - headerY - contentsHeight;
			boolean headerOverflow = cellAvailableStretch < 0 || rowHeight < contents.getHeight();
			
			if (!headerOverflow)
			{
				setCountVars(this.rowIdx + this.startRowIndex - vSpan + 1, -1);
				setGroupVariables(JRFillCrosstab.this.rowGroups, cell.getBucketValues());

				if (stretchContents)
				{
					contents = contents.getTransformedContents(contents.getWidth(), rowHeight, JRCellContents.POSITION_X_LEFT, JRCellContents.POSITION_Y_STRETCH);
				}
				contents = contents.getBoxContents(false, false, this.rowIdx + 1 == vSpan && (!this.printColumnHeaders || JRFillCrosstab.this.headerCell == null));
				contents.getWorkingClone();

				contents.evaluate(JRExpression.EVALUATION_DEFAULT);
				contents.prepare(cellAvailableStretch);
				
				this.preparedRow.add(contents);

				headerOverflow = contents.willOverflow();
				
				if (!headerOverflow)
				{
					contents.setX(this.rowHeadersXOffsets[rowGroup]);
					contents.setY(headerY + this.yOffset);
					contents.setVerticalSpan(vSpan);
					
					int rowCellHeight = contents.getPrintHeight() - spanHeight;
					if (rowCellHeight > this.preparedRowHeight)
					{
						this.preparedRowHeight = rowCellHeight;
					}
				}
			}
			
			if (headerOverflow)
			{
				removeFilledRows(vSpan - 1);
			}
			
			return headerOverflow;
		}

		private boolean prepareClosingRowHeader(int rowGroup, int availableHeight) throws JRException
		{
			if (rowGroup < JRFillCrosstab.this.rowGroups.length - 1 && 
					this.spanHeaders[rowGroup] != null && 
					this.spanHeaders[rowGroup].getLevelSpan() + this.spanHeadersStart[rowGroup] == this.rowIdx + this.startRowIndex + 1)
			{
				HeaderCell cell = this.spanHeaders[rowGroup];
				int vSpan = cell.getLevelSpan();
				if (this.spanHeadersStart[rowGroup] < this.startRowIndex)//continuing from the prev page
				{
					vSpan += this.spanHeadersStart[rowGroup] - this.startRowIndex;
				}
				this.spanHeaders[rowGroup] = null;
				
				return prepareRowHeader(rowGroup, cell, vSpan, availableHeight);
			}
			
			return false;
		}

		private void removeExceedingSpanHeaders()
		{
			for (int j = JRFillCrosstab.this.rowGroups.length - 2; j >= 0; --j)
			{
				if (this.spanHeaders[j] != null && this.spanHeadersStart[j] >= this.rowIdx + this.startRowIndex)
				{
					this.spanHeaders[j] = null;
				}
			}
		}

		private void setBackSpanHeaders()
		{
			for (int j = JRFillCrosstab.this.rowGroups.length - 2; j >= 0 && this.spanHeaders[j] == null; --j)
			{
				int spanIndex = getSpanIndex(this.rowIdx + this.startRowIndex, j, JRFillCrosstab.this.rowHeadersData);
				
				if (spanIndex >= 0)
				{
					this.spanHeaders[j] = JRFillCrosstab.this.rowHeadersData[j][spanIndex];
					this.spanHeadersStart[j] = spanIndex;
				}
			}
		}

		private void fillContinuingRowHeaders(int xOffset, int availableHeight) throws JRException
		{
			boolean done = false;
			breakCrosstab:
			do
			{
				removeExceedingSpanHeaders();
				
				if (!this.rowBreakable[this.rowIdx + this.startRowIndex])
				{
					removeFilledRows(1);
					setBackSpanHeaders();
					continue;
				}

				initPreparedRow();
				
				//fill continuing headers
				for (int j = 0; j < JRFillCrosstab.this.rowGroups.length - 1; ++j)
				{
					if (this.spanHeaders[j] != null)
					{
						boolean headerOverflow = prepareContinuingRowHeader(j, availableHeight);
						
						if (headerOverflow)
						{
							releasePreparedRow();
							continue breakCrosstab;
						}
					}
				}

				if (!this.preparedRow.isEmpty())
				{
					int lastRowHeight = ((Integer) this.rowYs.get(this.rowIdx)).intValue() - ((Integer) this.rowYs.get(this.rowIdx - 1)).intValue();
					
					if (this.preparedRowHeight > lastRowHeight)//need to stretch already filled row by refilling
					{
						refillLastRow(xOffset, availableHeight);
					}
					else
					{
						fillContinuingHeaders(lastRowHeight);
					}
				}
				
				done = true;
			}
			while (!done && this.rowIdx > 0);
		}

		private void fillContinuingHeaders(int lastRowHeight) throws JRException
		{
			int nextToLastHeaderY = ((Integer) this.rowYs.get(this.rowIdx - 1)).intValue();
			List lastPrintRow = getLastPrintRow();
			
			for (int j = 0; j < this.preparedRow.size(); ++j)
			{
				JRFillCellContents contents = (JRFillCellContents) this.preparedRow.get(j);
				
				int headerY = ((Integer) this.rowYs.get(this.rowIdx - contents.getVerticalSpan())).intValue();
				
				contents.stretchTo(nextToLastHeaderY - headerY + lastRowHeight);
				lastPrintRow.add(contents.fill());
				contents.releaseWorkingClone();
			}
		}

		private void refillLastRow(int xOffset, int availableHeight) throws JRException
		{
			removeFilledRows(1);
			setBackSpanHeaders();
			
			prepareRow(xOffset, availableHeight);
			fillRow();
			
			this.rowYs.add(Integer.valueOf(((Integer) this.rowYs.get(this.rowIdx)).intValue() + this.preparedRowHeight));
			++this.rowIdx;
		}

		private boolean prepareContinuingRowHeader(int rowGroup, int availableHeight) throws JRException
		{
			HeaderCell cell = this.spanHeaders[rowGroup];
			int vSpan = this.rowIdx + this.startRowIndex - this.spanHeadersStart[rowGroup];

			if (this.spanHeadersStart[rowGroup] < this.startRowIndex)//continuing from the prev page
			{
				vSpan += this.spanHeadersStart[rowGroup] - this.startRowIndex;
			}

			int headerY = ((Integer) this.rowYs.get(this.rowIdx - vSpan)).intValue();
			int lastHeaderY = ((Integer) this.rowYs.get(this.rowIdx)).intValue();
			int headerHeight = lastHeaderY - headerY;
			int nextToLastHeaderY = ((Integer) this.rowYs.get(this.rowIdx - 1)).intValue();
			int stretchHeight = nextToLastHeaderY - headerY;
			
			JRFillCrosstabRowGroup group = JRFillCrosstab.this.rowGroups[rowGroup];
			JRFillCellContents contents = cell.isTotal() ? group.getFillTotalHeader() : group.getFillHeader();
			
			boolean stretchContents = group.getPosition() == JRCellContents.POSITION_Y_STRETCH;
			int contentsHeight = stretchContents ? headerHeight : contents.getHeight();
			
			int cellAvailableStretch = availableHeight - headerY - contentsHeight;
			boolean headerOverflow = cellAvailableStretch < 0 || headerHeight < contents.getHeight();
			if (!headerOverflow)
			{
				setCountVars(this.rowIdx + this.startRowIndex - vSpan, -1);
				setGroupVariables(JRFillCrosstab.this.rowGroups, cell.getBucketValues());

				if (stretchContents)
				{
					contents = contents.getTransformedContents(contents.getWidth(), headerHeight, JRCellContents.POSITION_X_LEFT, JRCellContents.POSITION_Y_STRETCH);
				}
				
				contents = contents.getBoxContents(false, false, this.rowIdx == vSpan && (!this.printColumnHeaders || JRFillCrosstab.this.headerCell == null));
				contents.getWorkingClone();

				contents.evaluate(JRExpression.EVALUATION_DEFAULT);
				contents.prepare(cellAvailableStretch);
				
				this.preparedRow.add(contents);

				headerOverflow = contents.willOverflow();

				if (!headerOverflow)
				{
					contents.setX(this.rowHeadersXOffsets[rowGroup]);
					contents.setY(headerY + this.yOffset);
					contents.setVerticalSpan(vSpan);
					
					int rowHeight = contents.getPrintHeight() - stretchHeight;
					if (rowHeight > this.preparedRowHeight)
					{
						this.preparedRowHeight = rowHeight;
					}
				}
			}

			if (headerOverflow)
			{
				removeFilledRows(vSpan);
			}
			
			return headerOverflow;
		}
		
		protected void addPrintRow(List printRow)
		{
			this.printRows.add(printRow);
		}
		
		protected List getLastPrintRow()
		{
			return (List) this.printRows.get(this.printRows.size() - 1);
		}

		protected List getPrintElements()
		{
			List prints = new ArrayList();
			
			for (Iterator it = this.printRows.iterator(); it.hasNext();)
			{
				List rowPrints = (List) it.next();
				prints.addAll(rowPrints);
			}
			
			return prints;
		}

		protected void setGroupVariables(JRFillCrosstabGroup[] groups, Bucket[] bucketValues)
		{
			for (int i = 0; i < groups.length; i++)
			{
				Object value = null;
				if (bucketValues[i] != null && !bucketValues[i].isTotal())
				{
					value = bucketValues[i].getValue();
				}
				groups[i].getFillVariable().setValue(value);
			}
		}

		protected void setMeasureVariables(CrosstabCell cell)
		{
			MeasureValue[] values = cell.getMesureValues();
			for (int i = 0; i < JRFillCrosstab.this.measures.length; i++)
			{
				Object value = measureValue(values, i);
				JRFillCrosstab.this.measures[i].getFillVariable().setValue(value);
			}
			
			MeasureValue[][][] totals = cell.getTotals();
			for (int row = 0; row <= JRFillCrosstab.this.rowGroups.length; row++)
			{
				for (int col = 0; col <= JRFillCrosstab.this.columnGroups.length; col++)
				{
					MeasureValue[] vals = totals[row][col];
					if (JRFillCrosstab.this.retrieveTotal[row][col])
					{
						for (int m = 0; m < JRFillCrosstab.this.measures.length; m++)
						{
							JRFillVariable totalVar = JRFillCrosstab.this.totalVariables[row][col][m];
							Object value = measureValue(vals, m);
							totalVar.setValue(value);
						}
					}
				}
			}
		}

		
		protected Object measureValue(MeasureValue[] values, int measureIdx)
		{
			Object value;
			if (JRFillCrosstab.this.measures[measureIdx].getPercentageOfType() == JRCrosstabMeasure.PERCENTAGE_TYPE_GRAND_TOTAL)
			{
				if (values[measureIdx].isInitialized())
				{
					value = values[measureIdx].getValue();
				}
				else
				{
					value = JRFillCrosstab.this.measures[measureIdx].getPercentageCalculator().calculatePercentage(values[measureIdx], JRFillCrosstab.this.grandTotals[measureIdx]);
				}
			}
			else
			{
				value = values[measureIdx].getValue();
			}
			return value;
		}

		
		protected void resetVariables()
		{
			for (int i = 0; i < JRFillCrosstab.this.rowGroups.length; i++)
			{
				JRFillCrosstab.this.rowGroups[i].getFillVariable().setValue(null);
			}
			
			for (int i = 0; i < JRFillCrosstab.this.columnGroups.length; i++)
			{
				JRFillCrosstab.this.columnGroups[i].getFillVariable().setValue(null);
			}
			
			for (int i = 0; i < JRFillCrosstab.this.measures.length; i++)
			{
				JRFillCrosstab.this.measures[i].getFillVariable().setValue(null);
			}
			
			for (int row = 0; row <= JRFillCrosstab.this.rowGroups.length; ++row)
			{
				for (int col = 0; col <= JRFillCrosstab.this.columnGroups.length; ++col)
				{
					if (JRFillCrosstab.this.retrieveTotal[row][col])
					{
						for (int i = 0; i < JRFillCrosstab.this.measures.length; i++)
						{
							JRFillCrosstab.this.totalVariables[row][col][i].setValue(null);
						}
					}
				}
			}
		}
	}

	public int getColumnBreakOffset()
	{
		return this.parentCrosstab.getColumnBreakOffset();
	}

	public boolean isRepeatColumnHeaders()
	{
		return this.parentCrosstab.isRepeatColumnHeaders();
	}

	public boolean isRepeatRowHeaders()
	{
		return this.parentCrosstab.isRepeatRowHeaders();
	}

	public JRCrosstabCell[][] getCells()
	{
		return this.crossCells;
	}

	public JRCellContents getWhenNoDataCell()
	{
		return this.whenNoDataCell;
	}

	public JRCrosstabParameter[] getParameters()
	{
		return this.parameters;
	}

	public JRExpression getParametersMapExpression()
	{
		return this.parentCrosstab.getParametersMapExpression();
	}

	
	public JRElement getElementByKey(String elementKey)
	{
		return JRBaseCrosstab.getElementByKey(this, elementKey);
	}

	public JRFillCloneable createClone(JRFillCloneFactory factory)
	{
		//not needed
		return null;
	}

	public JRCellContents getHeaderCell()
	{
		return this.headerCell;
	}

	public JRVariable[] getVariables()
	{
		return this.variables;
	}

	public byte getRunDirection()
	{
		return this.parentCrosstab.getRunDirection();
	}

	public void setRunDirection(byte direction)
	{
		// nothing
	}

}
