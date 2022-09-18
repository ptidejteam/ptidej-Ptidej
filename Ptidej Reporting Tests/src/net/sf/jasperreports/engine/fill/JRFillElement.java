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

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRDefaultStyleProvider;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionChunk;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPropertiesHolder;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JRPropertyExpression;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JRStyleSetter;
import net.sf.jasperreports.engine.JRVariable;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.util.JRStyleResolver;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRFillElement.java,v 1.1 2008/09/29 16:20:00 guehene Exp $
 */
public abstract class JRFillElement implements JRElement, JRFillCloneable, JRStyleSetter
{


	/**
	 *
	 */
	protected JRElement parent = null;
	protected Map templates = new HashMap();

	/**
	 *
	 */
	protected JRBaseFiller filler = null;
	protected JRFillExpressionEvaluator expressionEvaluator = null;

	protected JRDefaultStyleProvider defaultStyleProvider;
	
	/**
	 *
	 */
	protected JRGroup printWhenGroupChanges = null;
	protected JRFillElementGroup elementGroup = null;

	/**
	 *
	 */
	protected JRFillBand band = null;

	/**
	 *
	 */
	private boolean isPrintWhenExpressionNull = true;
	private boolean isPrintWhenTrue = true;
	private boolean isToPrint = true;
	private boolean isReprinted = false;
	private boolean isAlreadyPrinted = false;
	private Collection dependantElements = new ArrayList();
	private int relativeY = 0;
	private int stretchHeight = 0;
	private int bandBottomY = 0;

	private int x;
	private int y;
	private int width;
	private int height;
	
	private boolean isValueRepeating = false;
	
	protected byte currentEvaluation;
	
	// used by elements that support evaluationTime=Auto
	protected Map delayedEvaluationsMap;

	protected JRFillElementContainer conditionalStylesContainer;
	
	protected JRStyle initStyle;
	
	/**
	 * Flag indicating whether the element is shrinkable.
	 * @see #setShrinkable(boolean)
	 */
	private boolean shrinkable;

	protected JRPropertiesMap staticProperties;
	protected JRPropertiesMap dynamicProperties;
	protected JRPropertiesMap mergedProperties;
	
	/**
	 *
	 *
	private JRElement topElementInGroup = null;
	private JRElement bottomElementInGroup = null;


	/**
	 *
	 */
	protected JRFillElement(
			JRBaseFiller filler,
			JRElement element,
			JRFillObjectFactory factory
			)
		{
			factory.put(element, this);

			this.parent = element;
			this.filler = filler;
			this.expressionEvaluator = factory.getExpressionEvaluator();
			this.defaultStyleProvider = factory.getDefaultStyleProvider();

			/*   */
			this.printWhenGroupChanges = factory.getGroup(element.getPrintWhenGroupChanges());
			this.elementGroup = (JRFillElementGroup)factory.getVisitResult(element.getElementGroup());
			
			this.x = element.getX();
			this.y = element.getY();
			this.width = element.getWidth();
			this.height = element.getHeight();
			
			this.staticProperties = element.hasProperties() ? element.getPropertiesMap().cloneProperties() : null;
			this.mergedProperties = this.staticProperties;
			
			factory.registerDelayedStyleSetter(this, this.parent);
		}

	
	protected JRFillElement(JRFillElement element, JRFillCloneFactory factory)
	{
		factory.put(element, this);

		this.parent = element.parent;
		this.filler = element.filler;
		this.expressionEvaluator = element.expressionEvaluator;
		this.defaultStyleProvider = element.defaultStyleProvider;

		/*   */
		this.printWhenGroupChanges = element.printWhenGroupChanges;
		this.elementGroup = (JRFillElementGroup) factory.getClone((JRFillElementGroup) element.getElementGroup());

		this.x = element.getX();
		this.y = element.getY();
		this.width = element.getWidth();
		this.height = element.getHeight();
		
		this.templates = element.templates;
		
		this.initStyle = element.initStyle;
		
		this.shrinkable = element.shrinkable;
		
		this.staticProperties = element.staticProperties == null ? null : element.staticProperties.cloneProperties();
		this.mergedProperties = this.staticProperties;
	}


	/**
	 * 
	 */
	public JRDefaultStyleProvider getDefaultStyleProvider()
	{
		return this.defaultStyleProvider;
	}


	/**
	 *
	 */
	public String getKey()
	{
		return this.parent.getKey();
	}

	/**
	 *
	 */
	public byte getPositionType()
	{
		return this.parent.getPositionType();//FIXME optimize this by consolidating style properties
	}

	/**
	 *
	 */
	public void setPositionType(byte positionType)
	{
	}

	/**
	 *
	 */
	public byte getStretchType()
	{
		return this.parent.getStretchType();
	}

	/**
	 *
	 */
	public void setStretchType(byte stretchType)
	{
	}

	/**
	 *
	 */
	public boolean isPrintRepeatedValues()
	{
		return this.parent.isPrintRepeatedValues();
	}

	/**
	 *
	 */
	public void setPrintRepeatedValues(boolean isPrintRepeatedValues)
	{
	}

	/**
	 *
	 */
	public byte getMode()
	{
		return JRStyleResolver.getMode(this, MODE_OPAQUE);
	}

	/**
	 *
	 */
	public Byte getOwnMode()
	{
		return this.parent.getOwnMode();
	}

	/**
	 *
	 */
	public void setMode(byte mode)
	{
	}

	/**
	 *
	 */
	public void setMode(Byte mode)
	{
	}

	/**
	 *
	 */
	public int getX()
	{
		return this.x;
	}

	/**
	 *
	 */
	public void setX(int x)
	{
		this.x = x;
	}
	
	/**
	 *
	 */
	public void setY(int y)
	{
		this.y = y;
	}

	/**
	 *
	 */
	public int getY()
	{
		return this.y;
	}

	/**
	 *
	 */
	public int getWidth()
	{
		return this.width;
	}

	/**
	 *
	 */
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	/**
	 *
	 */
	public void setHeight(int height)
	{
		this.height = height;
	}

	/**
	 *
	 */
	public int getHeight()
	{
		return this.height;
	}

	/**
	 *
	 */
	public boolean isRemoveLineWhenBlank()
	{
		return this.parent.isRemoveLineWhenBlank();
	}

	/**
	 *
	 */
	public void setRemoveLineWhenBlank(boolean isRemoveLine)
	{
	}

	/**
	 *
	 */
	public boolean isPrintInFirstWholeBand()
	{
		return this.parent.isPrintInFirstWholeBand();
	}

	/**
	 *
	 */
	public void setPrintInFirstWholeBand(boolean isPrint)
	{
	}

	/**
	 *
	 */
	public boolean isPrintWhenDetailOverflows()
	{
		return this.parent.isPrintWhenDetailOverflows();
	}

	/**
	 *
	 */
	public void setPrintWhenDetailOverflows(boolean isPrint)
	{
	}

	/**
	 *
	 */
	public Color getForecolor()
	{
		return JRStyleResolver.getForecolor(this);
	}

	public Color getOwnForecolor()
	{
		return this.parent.getOwnForecolor();
	}

	/**
	 *
	 */
	public void setForecolor(Color forecolor)
	{
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
		return this.parent.getOwnBackcolor();
	}

	/**
	 *
	 */
	public void setBackcolor(Color backcolor)
	{
	}

	/**
	 *
	 */
	public JRExpression getPrintWhenExpression()
	{
		return this.parent.getPrintWhenExpression();
	}

	/**
	 *
	 */
	public JRGroup getPrintWhenGroupChanges()
	{
		return this.printWhenGroupChanges;
	}

	/**
	 *
	 */
	public JRElementGroup getElementGroup()
	{
		return this.elementGroup;
	}

	/**
	 *
	 */
	protected boolean isPrintWhenExpressionNull()
	{
		return this.isPrintWhenExpressionNull;
	}

	/**
	 *
	 */
	protected void setPrintWhenExpressionNull(boolean isPrintWhenExpressionNull)
	{
		this.isPrintWhenExpressionNull = isPrintWhenExpressionNull;
	}

	/**
	 *
	 */
	protected boolean isPrintWhenTrue()
	{
		return this.isPrintWhenTrue;
	}

	/**
	 *
	 */
	protected void setPrintWhenTrue(boolean isPrintWhenTrue)
	{
		this.isPrintWhenTrue = isPrintWhenTrue;
	}

	/**
	 *
	 */
	protected boolean isToPrint()
	{
		return this.isToPrint;
	}

	/**
	 *
	 */
	protected void setToPrint(boolean isToPrint)
	{
		this.isToPrint = isToPrint;
	}

	/**
	 *
	 */
	protected boolean isReprinted()
	{
		return this.isReprinted;
	}

	/**
	 *
	 */
	protected void setReprinted(boolean isReprinted)
	{
		this.isReprinted = isReprinted;
	}

	/**
	 *
	 */
	protected boolean isAlreadyPrinted()
	{
		return this.isAlreadyPrinted;
	}

	/**
	 *
	 */
	protected void setAlreadyPrinted(boolean isAlreadyPrinted)
	{
		this.isAlreadyPrinted = isAlreadyPrinted;
	}

	/**
	 *
	 */
	protected JRElement[] getGroupElements()
	{
		JRElement[] groupElements = null;

		if (this.elementGroup != null)
		{
			groupElements = this.elementGroup.getElements();
		}

		return groupElements;
	}

	/**
	 *
	 */
	protected Collection getDependantElements()
	{
		return this.dependantElements;
	}

	/**
	 *
	 */
	protected void addDependantElement(JRElement element)
	{
		this.dependantElements.add(element);
	}

	/**
	 *
	 */
	protected int getRelativeY()
	{
		return this.relativeY;
	}

	/**
	 *
	 */
	protected void setRelativeY(int relativeY)
	{
		this.relativeY = relativeY;
	}

	/**
	 *
	 */
	protected int getStretchHeight()
	{
		return this.stretchHeight;
	}

	/**
	 *
	 */
	protected void setStretchHeight(int stretchHeight)
	{
		if (stretchHeight > getHeight() || (this.shrinkable && isRemoveLineWhenBlank()))
		{
			this.stretchHeight = stretchHeight;
		}
		else
		{
			this.stretchHeight = getHeight();
		}
	}

	/**
	 *
	 */
	protected int getBandBottomY()
	{
		return this.bandBottomY;
	}

	/**
	 *
	 */
	protected void setBandBottomY(int bandBottomY)
	{
		this.bandBottomY = bandBottomY;
	}

	/**
	 *
	 */
	protected JRFillBand getBand()
	{
		return this.band;
	}

	/**
	 *
	 */
	protected void setBand(JRFillBand band)
	{
		this.band = band;
	}


	/**
	 *
	 */
	protected void reset()
	{
		this.relativeY = this.y;
		this.stretchHeight = this.height;

		if (this.elementGroup != null)
		{
			this.elementGroup.reset();
		}
	}

	protected void setCurrentEvaluation(byte evaluation)
	{
		this.currentEvaluation = evaluation;
	}

	/**
	 *
	 */
	protected abstract void evaluate(
		byte evaluation
		) throws JRException;


	/**
	 *
	 */
	protected void evaluatePrintWhenExpression(
		byte evaluation
		) throws JRException
	{
		boolean isExprNull = true;
		boolean isExprTrue = false;

		JRExpression expression = getPrintWhenExpression();
		if (expression != null)
		{
			isExprNull = false;
			Boolean printWhenExpressionValue = (Boolean) evaluateExpression(expression, evaluation);
			if (printWhenExpressionValue == null)
			{
				isExprTrue = false;
			}
			else
			{
				isExprTrue = printWhenExpressionValue.booleanValue();
			}
		}

		setPrintWhenExpressionNull(isExprNull);
		setPrintWhenTrue(isExprTrue);
	}


	/**
	 *
	 */
	protected abstract void rewind() throws JRException;


	/**
	 *
	 */
	protected abstract JRPrintElement fill() throws JRException;


	/**
	 *
	 */
	protected boolean prepare(
		int availableStretchHeight,
		boolean isOverflow
		) throws JRException
	{
		if (
			isPrintWhenExpressionNull() ||
			( !isPrintWhenExpressionNull() &&
			  isPrintWhenTrue() )
			)
		{
			setToPrint(true);
		}
		else
		{
			setToPrint(false);
		}

		setReprinted(false);

		return false;
	}



	/**
	 *
	 */
	protected void stretchElement(int bandStretch)
	{
		switch (getStretchType())
		{
			case JRElement.STRETCH_TYPE_RELATIVE_TO_BAND_HEIGHT :
			{
				setStretchHeight(getHeight() + bandStretch);
				break;
			}
			case JRElement.STRETCH_TYPE_RELATIVE_TO_TALLEST_OBJECT :
			{
				if (this.elementGroup != null)
				{
					//setStretchHeight(getHeight() + getStretchHeightDiff());
					setStretchHeight(getHeight() + this.elementGroup.getStretchHeightDiff());
				}

				break;
			}
			case JRElement.STRETCH_TYPE_NO_STRETCH :
			default :
			{
				break;
			}
		}
	}


	/**
	 *
	 */
	protected void moveDependantElements()
	{
		Collection elements = getDependantElements();
		if (elements != null && elements.size() > 0)
		{
			JRFillElement element = null;
			int diffY = 0;
			for(Iterator it = elements.iterator(); it.hasNext();)
			{
				element = (JRFillElement)it.next();

				diffY = element.getY() - getY() - getHeight() -
						(element.getRelativeY() - getRelativeY() - getStretchHeight());

				if (diffY < 0)
				{
					diffY = 0;
				}

				element.setRelativeY(element.getRelativeY() + diffY);
			}
		}
	}


	/**
	 * Resolves an element.
	 * 
	 * @param element the element
	 * @param evaluation the evaluation type
	 */
	protected abstract void resolveElement (JRPrintElement element, byte evaluation) throws JRException;


	/**
	 * Evaluates an expression.
	 * 
	 * @param expression the expression
	 * @param evaluation the evaluation type
	 * @return the evaluation result
	 * @throws JRException
	 */
	protected final Object evaluateExpression(JRExpression expression, byte evaluation) throws JRException
	{
		return this.expressionEvaluator.evaluate(expression, evaluation);
	}


	/**
	 * Decides whether the value for this element is repeating.
	 * <p>
	 * Dynamic elements should call {@link #setValueRepeating(boolean) setValueRepeating(boolean)} on
	 * {@link #evaluate(byte) evaluate(byte)}.  Static elements don't have to do anything, this method
	 * will return <code>true</code> by default.
	 * 
	 * @return whether the value for this element is repeating
	 * @see #setValueRepeating(boolean)
	 */
	protected boolean isValueRepeating()
	{
		return this.isValueRepeating;
	}


	/**
	 * Sets the repeating flag for this element.
	 * <p>
	 * This method should be called by dynamic elements on {@link #evaluate(byte) evaluate(byte)}.
	 * 
	 * @param isValueRepeating whether the value of the element is repeating
	 * @see #isValueRepeating()
	 */
	protected void setValueRepeating(boolean isValueRepeating)
	{
		this.isValueRepeating = isValueRepeating;
	}


	protected JRFillVariable getVariable(String variableName)
	{
		return this.filler.getVariable(variableName);
	}


	protected JRFillField getField(String fieldName)
	{
		return this.filler.getField(fieldName);
	}
	
	// default for elements not supporting evaluationTime
	protected byte getEvaluationTime()
	{
		return JRExpression.EVALUATION_TIME_NOW;
	}

	/**
	 * Resolves an element.
	 * 
	 * @param element the element
	 * @param evaluation the evaluation type
	 * @param evaluationTime the current evaluation time
	 */
	protected void resolveElement (JRPrintElement element, byte evaluation, JREvaluationTime evaluationTime) throws JRException
	{
		byte evaluationTimeType = getEvaluationTime();
		switch (evaluationTimeType)
		{
			case JRExpression.EVALUATION_TIME_NOW:
				break;
			case JRExpression.EVALUATION_TIME_AUTO:
				delayedEvaluate((JRRecordedValuesPrintElement) element, evaluationTime, evaluation);
				break;
			default:
				resolveElement(element, evaluation);
				break;
		}
	}

	private static class DelayedEvaluations implements Serializable
	{
		private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

		final Set fields;
		final Set variables;

		DelayedEvaluations()
		{
			this.fields = new HashSet();
			this.variables = new HashSet();
		}
	}

	protected void initDelayedEvaluations()
	{
		if (getEvaluationTime() == JRExpression.EVALUATION_TIME_AUTO && this.delayedEvaluationsMap == null)
		{
			this.delayedEvaluationsMap = new HashMap();
			collectDelayedEvaluations();
		}
	}
	
	protected void collectDelayedEvaluations()
	{
		//to be overridden by elements that support "Auto" evaluation
	}

	protected void collectDelayedEvaluations(JRExpression expression)
	{
		if (expression != null)
		{
			JRExpressionChunk[] chunks = expression.getChunks();
			if (chunks != null)
			{
				for (int i = 0; i < chunks.length; i++)
				{
					JRExpressionChunk chunk = chunks[i];
					switch (chunk.getType())
					{
						case JRExpressionChunk.TYPE_FIELD:
						{
							DelayedEvaluations delayedEvaluations = getDelayedEvaluations(JREvaluationTime.EVALUATION_TIME_NOW);
							delayedEvaluations.fields.add(chunk.getText());
							break;
						}
						case JRExpressionChunk.TYPE_VARIABLE:
						{
							JREvaluationTime time = autogetVariableEvaluationTime(chunk.getText());
							DelayedEvaluations delayedEvaluations = getDelayedEvaluations(time);
							delayedEvaluations.variables.add(chunk.getText());
							break;
						}
					}
				}
			}
		}
	}


	private DelayedEvaluations getDelayedEvaluations(JREvaluationTime time)
	{
		DelayedEvaluations delayedEvaluations = (DelayedEvaluations) this.delayedEvaluationsMap.get(time);
		if (delayedEvaluations == null)
		{
			delayedEvaluations = new DelayedEvaluations();
			this.delayedEvaluationsMap.put(time, delayedEvaluations);
		}
		return delayedEvaluations;
	}


	private JREvaluationTime autogetVariableEvaluationTime(String variableName)
	{
		JRFillVariable variable = getVariable(variableName);
		JREvaluationTime evaluationTime;
		switch (variable.getResetType())
		{
			case JRVariable.RESET_TYPE_REPORT:
				evaluationTime = JREvaluationTime.EVALUATION_TIME_REPORT;
				break;
			case JRVariable.RESET_TYPE_PAGE:
				evaluationTime = JREvaluationTime.EVALUATION_TIME_PAGE;
				break;
			case JRVariable.RESET_TYPE_COLUMN:
				evaluationTime = JREvaluationTime.EVALUATION_TIME_COLUMN;
				break;
			case JRVariable.RESET_TYPE_GROUP:
				evaluationTime = JREvaluationTime.getGroupEvaluationTime(variable.getResetGroup().getName());
				break;
			default:
				evaluationTime = JREvaluationTime.EVALUATION_TIME_NOW;
				break;
		}
		
		if (!evaluationTime.equals(JREvaluationTime.EVALUATION_TIME_NOW) &&
				this.band.isNowEvaluationTime(evaluationTime))
		{
			evaluationTime = JREvaluationTime.EVALUATION_TIME_NOW;
		}
		
		if (variable.getCalculation() == JRVariable.CALCULATION_SYSTEM &&
				evaluationTime.equals(JREvaluationTime.EVALUATION_TIME_NOW) &&
				this.band.isVariableUsedInSubreportReturns(variableName))
		{
			evaluationTime = JREvaluationTime.getBandEvaluationTime(this.band);
		}

		return evaluationTime;
	}
	
	
	protected void initDelayedEvaluationPrint(JRRecordedValuesPrintElement printElement) throws JRException
	{
		for (Iterator it = this.delayedEvaluationsMap.keySet().iterator(); it.hasNext();)
		{
			JREvaluationTime evaluationTime = (JREvaluationTime) it.next();
			if (!evaluationTime.equals(JREvaluationTime.EVALUATION_TIME_NOW))
			{
				this.filler.addBoundElement(this, printElement, evaluationTime);
			}
		}
		
		printElement.initRecordedValues(this.delayedEvaluationsMap.keySet());
		
		if (this.delayedEvaluationsMap.containsKey(JREvaluationTime.EVALUATION_TIME_NOW))
		{
			delayedEvaluate(printElement, JREvaluationTime.EVALUATION_TIME_NOW, this.currentEvaluation);
		}
	}


	protected void delayedEvaluate(JRRecordedValuesPrintElement printElement, JREvaluationTime evaluationTime, byte evaluation) throws JRException
	{
		JRRecordedValues recordedValues = printElement.getRecordedValues();
		if (!recordedValues.lastEvaluationTime())
		{
			DelayedEvaluations delayedEvaluations = (DelayedEvaluations) this.delayedEvaluationsMap.get(evaluationTime);
			
			for (Iterator it = delayedEvaluations.fields.iterator(); it.hasNext();)
			{
				String fieldName = (String) it.next();
				JRFillField field = getField(fieldName);
				recordedValues.recordFieldValue(fieldName, field.getValue(evaluation));
			}

			for (Iterator it = delayedEvaluations.variables.iterator(); it.hasNext();)
			{
				String variableName = (String) it.next();
				JRFillVariable variable = getVariable(variableName);
				recordedValues.recordVariableValue(variableName, variable.getValue(evaluation));
			}
		}

		recordedValues.doneEvaluation(evaluationTime);
		
		if (recordedValues.finishedEvaluations())
		{
			overwriteWithRecordedValues(recordedValues, evaluation);
			resolveElement(printElement, evaluation);
			restoreValues(recordedValues, evaluation);
			printElement.deleteRecordedValues();
		}
	}

	
	private void overwriteWithRecordedValues(JRRecordedValues recordedValues, byte evaluation)
	{
		Map fieldValues = recordedValues.getRecordedFieldValues();
		if (fieldValues != null)
		{
			for (Iterator it = fieldValues.entrySet().iterator(); it.hasNext();)
			{
				Map.Entry entry = (Map.Entry) it.next();
				String fieldName = (String) entry.getKey();
				Object fieldValue = entry.getValue();
				JRFillField field = getField(fieldName);
				field.overwriteValue(fieldValue, evaluation);
			}
		}
		
		Map variableValues = recordedValues.getRecordedVariableValues();
		if (variableValues != null)
		{
			for (Iterator it = variableValues.entrySet().iterator(); it.hasNext();)
			{
				Map.Entry entry = (Map.Entry) it.next();
				String variableName = (String) entry.getKey();
				Object variableValue = entry.getValue();
				JRFillVariable variable = getVariable(variableName);
				variable.overwriteValue(variableValue, evaluation);
			}
		}
	}

	private void restoreValues(JRRecordedValues recordedValues, byte evaluation)
	{
		Map fieldValues = recordedValues.getRecordedFieldValues();
		if (fieldValues != null)
		{
			for (Iterator it = fieldValues.keySet().iterator(); it.hasNext();)
			{
				String fieldName = (String) it.next();
				JRFillField field = getField(fieldName);
				field.restoreValue(evaluation);
			}
		}
		
		Map variableValues = recordedValues.getRecordedVariableValues();
		if (variableValues != null)
		{
			for (Iterator it = variableValues.keySet().iterator(); it.hasNext();)
			{
				String variableName = (String) it.next();
				JRFillVariable variable = getVariable(variableName);
				variable.restoreValue(evaluation);
			}
		}
	}


	/**
	 * 
	 */
	protected void setConditionalStylesContainer(JRFillElementContainer conditionalStylesContainer)
	{
		this.conditionalStylesContainer = conditionalStylesContainer;
	}

	/**
	 * 
	 */
	public JRStyle getStyle()
	{
		JRStyle crtStyle = this.initStyle;
		
		boolean isUsingDefaultStyle = false;

		if (crtStyle == null)
		{
			crtStyle = this.filler.getDefaultStyle();
			isUsingDefaultStyle = true;
		}

		JRStyle evalStyle = crtStyle;

		if (this.conditionalStylesContainer != null)
			evalStyle = this.conditionalStylesContainer.getEvaluatedConditionalStyle(crtStyle);
		
		if (isUsingDefaultStyle && evalStyle == crtStyle)
			evalStyle = null;
		
		return evalStyle;
	}
	
	/**
	 * 
	 */
	protected JRTemplateElement getTemplate(JRStyle style)
	{
		return (JRTemplateElement) this.templates.get(style);
	}

	/**
	 * 
	 */
	protected void registerTemplate(JRStyle style, JRTemplateElement template)
	{
		this.templates.put(style, template);
	}
	
	
	/**
	 * Indicates whether an element is shrinkable.
	 * <p>
	 * This flag is only effective when {@link #isRemoveLineWhenBlank() isRemoveLineWhenBlank} is also set.
	 * 
	 * @param shrinkable whether the element is shrinkable
	 */
	protected final void setShrinkable(boolean shrinkable)
	{
		this.shrinkable = shrinkable;
	}


	/**
	 * Called when the stretch height of an element is final so that
	 * the element can perform any adjustments.
	 */
	protected void stretchHeightFinal()
	{
		// nothing		
	}
	
	
	protected boolean isEvaluateNow()
	{
		boolean evaluateNow;
		switch (getEvaluationTime())
		{
			case JRExpression.EVALUATION_TIME_NOW:
				evaluateNow = true;
				break;

			case JRExpression.EVALUATION_TIME_AUTO:
				evaluateNow = isAutoEvaluateNow();
				break;

			default:
				evaluateNow = false;
				break;
		}
		return evaluateNow;
	}
	
	
	protected boolean isAutoEvaluateNow()
	{
		return this.delayedEvaluationsMap == null || this.delayedEvaluationsMap.isEmpty() 
				|| (this.delayedEvaluationsMap.size() == 1 
						&& this.delayedEvaluationsMap.containsKey(JREvaluationTime.EVALUATION_TIME_NOW));
	}
	
	
	protected boolean isEvaluateAuto()
	{
		return getEvaluationTime() == JRExpression.EVALUATION_TIME_AUTO && !isAutoEvaluateNow();
	}

	public String getStyleNameReference()
	{
		return null;
	}

	public void setStyle(JRStyle style)
	{
		this.initStyle = style;
		this.conditionalStylesContainer.collectConditionalStyle(style);
	}

	public void setStyleNameReference(String name)
	{
		throw new UnsupportedOperationException("Style name references not allowed at fill time");
	}
	
	/**
	 *
	 */
	public Object clone() 
	{
		return null;
	}

	/**
	 *
	 */
	public Object clone(JRElementGroup parentGroup) 
	{
		return null;//FIXMECLONE throw
	}

	public boolean hasProperties()
	{
		return this.mergedProperties != null && this.mergedProperties.hasProperties();
	}

	public JRPropertiesMap getPropertiesMap()
	{
		return this.mergedProperties;
	}

	public JRPropertiesHolder getParentProperties()
	{
		//element properties default to report properties
		return this.filler.getJasperReport();
	}


	public JRPropertyExpression[] getPropertyExpressions()
	{
		return this.parent.getPropertyExpressions();
	}
	
	protected void transferProperties(JRTemplateElement template)
	{
		JRProperties.transferProperties(this.parent, template, 
				JasperPrint.PROPERTIES_PRINT_TRANSFER_PREFIX);
	}
	
	protected void transferProperties(JRPrintElement element)
	{
		JRProperties.transferProperties(this.dynamicProperties, element, 
				JasperPrint.PROPERTIES_PRINT_TRANSFER_PREFIX);
	}
	
	protected JRPropertiesMap getEvaluatedProperties()
	{
		return this.mergedProperties;
	}
	
	protected void evaluateProperties(byte evaluation) throws JRException
	{
		JRPropertyExpression[] propExprs = getPropertyExpressions();
		if (propExprs == null || propExprs.length == 0)
		{
			this.dynamicProperties = null;
			this.mergedProperties = this.staticProperties;
		}
		else
		{
			this.dynamicProperties = new JRPropertiesMap();
			
			for (int i = 0; i < propExprs.length; i++)
			{
				JRPropertyExpression prop = propExprs[i];
				String value = (String) evaluateExpression(prop.getValueExpression(), evaluation);
				if (value != null)
				{
					this.dynamicProperties.setProperty(prop.getName(), value);
				}
			}
			
			this.mergedProperties = this.dynamicProperties.cloneProperties();
			this.mergedProperties.setBaseProperties(this.staticProperties);
		}
	}
}
