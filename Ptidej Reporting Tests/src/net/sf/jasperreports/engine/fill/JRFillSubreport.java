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

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDatasetParameter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRPrintRectangle;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JRRewindableDataSource;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JRSubreport;
import net.sf.jasperreports.engine.JRSubreportParameter;
import net.sf.jasperreports.engine.JRSubreportReturnValue;
import net.sf.jasperreports.engine.JRVariable;
import net.sf.jasperreports.engine.JRVisitor;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignSubreportReturnValue;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.util.JRSingletonCache;
import net.sf.jasperreports.engine.util.JRStyleResolver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRFillSubreport.java,v 1.1 2008/09/29 16:20:00 guehene Exp $
 */
public class JRFillSubreport extends JRFillElement implements JRSubreport
{

	private static final Log log = LogFactory.getLog(JRFillSubreport.class);
	
	private static final JRSingletonCache runnerFactoryCache = new JRSingletonCache(JRSubreportRunnerFactory.class);

	/**
	 *
	 */
	private Map parameterValues = null;
	private JRSubreportParameter[] parameters = null;
	private Connection connection = null;
	private JRDataSource dataSource = null;
	private JasperReport jasperReport = null;

	private Map loadedEvaluators = null;
	
	/**
	 * Values to be copied from the subreport.
	 */
	private JRFillSubreportReturnValue[] returnValues = null;

	/**
	 *
	 */
	protected JRBaseFiller subreportFiller = null;
	private JRPrintPage printPage = null;

	private JRSubreportRunner runner;
	
	/**
	 * Set of checked reports.
	 */
	private Set checkedReports;


	/**
	 *
	 */
	protected JRFillSubreport(
		JRBaseFiller filler,
		JRSubreport subreport, 
		JRFillObjectFactory factory
		)
	{
		super(filler, subreport, factory);

		this.parameters = subreport.getParameters();
		JRSubreportReturnValue[] subrepReturnValues = subreport.getReturnValues();
		if (subrepReturnValues != null)
		{
			List returnValuesList = new ArrayList(subrepReturnValues.length * 2);
			
			this.returnValues = new JRFillSubreportReturnValue[subrepReturnValues.length];
			for (int i = 0; i < subrepReturnValues.length; i++)
			{
				addReturnValue(subrepReturnValues[i], returnValuesList, factory);
			}
			
			this.returnValues = new JRFillSubreportReturnValue[returnValuesList.size()];
			returnValuesList.toArray(this.returnValues);
		}
		
		this.loadedEvaluators = new HashMap();
		this.checkedReports = new HashSet();
	}


	/**
	 *
	 */
	public byte getMode()
	{
		return JRStyleResolver.getMode(this, MODE_TRANSPARENT);
	}

	/**
	 *
	 */
	public boolean isUsingCache()
	{
		return ((JRSubreport)this.parent).isUsingCache();
	}
		
	/**
	 * @deprecated Replaced by {@link #setUsingCache(Boolean)}.
	 */
	public void setUsingCache(boolean isUsingCache)
	{
	}
		
	/**
	 *
	 */
	public JRExpression getParametersMapExpression()
	{
		return ((JRSubreport)this.parent).getParametersMapExpression();
	}

	/**
	 *
	 */
	public JRSubreportParameter[] getParameters()
	{
		return this.parameters;
	}

	/**
	 *
	 */
	public JRExpression getConnectionExpression()
	{
		return ((JRSubreport)this.parent).getConnectionExpression();
	}

	/**
	 *
	 */
	public JRExpression getDataSourceExpression()
	{
		return ((JRSubreport)this.parent).getDataSourceExpression();
	}

	/**
	 *
	 */
	public JRExpression getExpression()
	{
		return ((JRSubreport)this.parent).getExpression();
	}

	/**
	 *
	 */
	protected JRTemplateRectangle getJRTemplateRectangle()
	{
		JRStyle style = getStyle();
		JRTemplateRectangle template = (JRTemplateRectangle) getTemplate(style);
		if (template == null)
		{
			template = new JRTemplateRectangle(this.band.getOrigin(), this.filler.getJasperPrint().getDefaultStyleProvider(), this);
			transferProperties(template);
			registerTemplate(style, template);
		}
		return template;
	}


	/**
	 *
	 */
	protected Collection getPrintElements()
	{
		Collection printElements = null;
		
		if (this.printPage != null)
		{
			printElements = this.printPage.getElements();
		}
		
		return printElements;
	}


	/**
	 *
	 */
	protected void evaluate(
		byte evaluation
		) throws JRException
	{
		reset();
		
		evaluatePrintWhenExpression(evaluation);
		evaluateProperties(evaluation);

		if (
			(isPrintWhenExpressionNull() ||
			(!isPrintWhenExpressionNull() && 
			isPrintWhenTrue()))
			)
		{
			evaluateSubreport(evaluation);
		}
	}


	/**
	 *
	 */
	protected void evaluateSubreport(
		byte evaluation
		) throws JRException
	{
		JRExpression expression = getExpression();
		Object source = evaluateExpression(expression, evaluation);
		if (source != null) // FIXME put some default broken image like in browsers
		{
			JREvaluator evaluator = null;
			
			if (isUsingCache() && this.filler.fillContext.hasLoadedSubreport(source))
			{
				this.jasperReport = this.filler.fillContext.getLoadedSubreport(source);
				evaluator = (JREvaluator)this.loadedEvaluators.get(this.jasperReport);

				if (evaluator == null)
				{
					evaluator = JasperCompileManager.loadEvaluator(this.jasperReport);
					this.loadedEvaluators.put(this.jasperReport, evaluator);
				}
			}
			else
			{
				Class expressionClass = expression.getValueClass();
				
				if (expressionClass.equals(net.sf.jasperreports.engine.JasperReport.class))
				{
					this.jasperReport = (JasperReport)source;
				}
				else if (expressionClass.equals(java.io.InputStream.class))
				{
					this.jasperReport = (JasperReport)JRLoader.loadObject((InputStream)source);
				}
				else if (expressionClass.equals(java.net.URL.class))
				{
					this.jasperReport = (JasperReport)JRLoader.loadObject((URL)source);
				}
				else if (expressionClass.equals(java.io.File.class))
				{
					this.jasperReport = (JasperReport)JRLoader.loadObject((File)source);
				}
				else if (expressionClass.equals(java.lang.String.class))
				{
					this.jasperReport = 
						(JasperReport)JRLoader.loadObjectFromLocation(
							(String)source, 
							this.filler.reportClassLoader,
							this.filler.urlHandlerFactory,
							this.filler.fileResolver
							);
				}
				
				if (this.jasperReport != null)
				{
					evaluator = JasperCompileManager.loadEvaluator(this.jasperReport);
				}
				
				if (isUsingCache())
				{
					this.filler.fillContext.registerLoadedSubreport(source, this.jasperReport);
					this.loadedEvaluators.put(this.jasperReport, evaluator);
				}
			}
			
			if (this.jasperReport != null)
			{
				/*   */
				expression = getConnectionExpression();
				this.connection = (Connection) evaluateExpression(expression, evaluation);
		
				/*   */
				expression = getDataSourceExpression();
				this.dataSource = (JRDataSource) evaluateExpression(expression, evaluation);
				
				this.parameterValues = 
					getParameterValues(
						this.filler, 
						getParametersMapExpression(), 
						getParameters(), 
						evaluation, 
						false, 
						this.jasperReport.getResourceBundle() != null,//hasResourceBundle 
						this.jasperReport.getFormatFactoryClass() != null//hasFormatFactory
						);

				if (this.subreportFiller != null)
				{
					this.filler.unregisterSubfiller(this.subreportFiller);
				}
	
				/*   */
				initSubreportFiller(evaluator);
				
				checkReturnValues();
				
				saveReturnVariables();
			}
		}
	}


	protected void initSubreportFiller(JREvaluator evaluator) throws JRException
	{
		if (log.isDebugEnabled())
		{
			log.debug("Fill " + this.filler.fillerId + ": creating subreport filler");
		}
		
		switch (this.jasperReport.getPrintOrder())
		{
			case JRReport.PRINT_ORDER_HORIZONTAL :
			{
				this.subreportFiller = new JRHorizontalFiller(this.jasperReport, evaluator, this.filler);
				break;
			}
			case JRReport.PRINT_ORDER_VERTICAL :
			{
				this.subreportFiller = new JRVerticalFiller(this.jasperReport, evaluator, this.filler);
				break;
			}
			default :
			{
				throw new JRRuntimeException("Unkown print order " + this.jasperReport.getPrintOrder() + ".");
			}
		}
		
		this.runner = getRunnerFactory().createSubreportRunner(this, this.subreportFiller);
		this.subreportFiller.setSubreportRunner(this.runner);
	}


	protected void saveReturnVariables()
	{
		if (this.returnValues != null)
		{
			for (int i = 0; i < this.returnValues.length; i++)
			{
				String varName = this.returnValues[i].getToVariable();
				this.band.saveVariable(varName);
			}
		}
	}


	/**
	 * Utility method used for constructing a parameter values map for subreports, sub datasets and crosstabs.
	 * 
	 * @param filler report filler
	 * @param parametersMapExpression expression that yields bulk parameter values map
	 * @param subreportParameters list of individual parameter values
	 * @param evaluation evaluation type
	 * @param ignoreNullExpressions whether to ignore individual parameter value expressions
	 * @param removeResourceBundle whether to remove the {@link JRParameter#REPORT_RESOURCE_BUNDLE REPORT_RESOURCE_BUNDLE}
	 * 	value from the bulk values map
	 * @return the parameter values map
	 * @throws JRException
	 */
	public static Map getParameterValues(
			JRBaseFiller filler, 
			JRExpression parametersMapExpression, 
			JRDatasetParameter[] subreportParameters, 
			byte evaluation, 
			boolean ignoreNullExpressions, 
			boolean removeResourceBundle,
			boolean removeFormatFactory
			) throws JRException
	{
		Map parameterValues = null;
		if (parametersMapExpression != null)
		{
			parameterValues = (Map) filler.evaluateExpression(parametersMapExpression, evaluation);
		}		
		
		if (parameterValues != null)
		{
			//if the expression evaluates to the master parameters map
			if (parameterValues == filler.getParameterValuesMap())
			{
				//create a clone of the map so that the master map is not altered
				parameterValues = new HashMap(parameterValues);
			}
			
			//parameterValues.remove(JRParameter.REPORT_LOCALE);
			if (removeResourceBundle)
			{
				parameterValues.remove(JRParameter.REPORT_RESOURCE_BUNDLE);
			}
			if (removeFormatFactory)
			{
				parameterValues.remove(JRParameter.REPORT_FORMAT_FACTORY);
			}
			//parameterValues.remove(JRParameter.REPORT_TIME_ZONE);
			parameterValues.remove(JRParameter.REPORT_CONNECTION);
			parameterValues.remove(JRParameter.REPORT_MAX_COUNT);
			parameterValues.remove(JRParameter.REPORT_DATA_SOURCE);
			parameterValues.remove(JRParameter.REPORT_SCRIPTLET);
			parameterValues.remove(JRParameter.REPORT_VIRTUALIZER);
			//parameterValues.remove(JRParameter.REPORT_CLASS_LOADER);
			parameterValues.remove(JRParameter.IS_IGNORE_PAGINATION);
			parameterValues.remove(JRParameter.REPORT_PARAMETERS_MAP);
		}
		
		if (parameterValues == null)
		{
			parameterValues = new HashMap();
		}
		
		/*   */
		if (subreportParameters != null && subreportParameters.length > 0)
		{
			Object parameterValue = null;
			for(int i = 0; i < subreportParameters.length; i++)
			{
				JRExpression expression = subreportParameters[i].getExpression();
				if (expression != null || !ignoreNullExpressions)
				{
					parameterValue = filler.evaluateExpression(expression, evaluation);
					if (parameterValue == null)
					{
						parameterValues.remove(subreportParameters[i].getName());
					}
					else
					{
						parameterValues.put(subreportParameters[i].getName(), parameterValue);
					}
				}
			}
		}
		
		if (!parameterValues.containsKey(JRParameter.REPORT_LOCALE))
		{
			parameterValues.put(JRParameter.REPORT_LOCALE, filler.getLocale());
		}

		if (!parameterValues.containsKey(JRParameter.REPORT_TIME_ZONE))
		{
			parameterValues.put(JRParameter.REPORT_TIME_ZONE, filler.getTimeZone());
		}

		if (
			!parameterValues.containsKey(JRParameter.REPORT_FORMAT_FACTORY)
			&& !removeFormatFactory
			)
		{
			parameterValues.put(JRParameter.REPORT_FORMAT_FACTORY, filler.getFormatFactory());
		}

		if (!parameterValues.containsKey(JRParameter.REPORT_CLASS_LOADER) &&
				filler.reportClassLoader != null)
		{
			parameterValues.put(JRParameter.REPORT_CLASS_LOADER, filler.reportClassLoader);
		}

		if (!parameterValues.containsKey(JRParameter.REPORT_URL_HANDLER_FACTORY) &&
				filler.urlHandlerFactory != null)
		{
			parameterValues.put(JRParameter.REPORT_URL_HANDLER_FACTORY, filler.urlHandlerFactory);
		}
		
		if (!parameterValues.containsKey(JRParameter.REPORT_FILE_RESOLVER) &&
				filler.fileResolver != null)
		{
			parameterValues.put(JRParameter.REPORT_FILE_RESOLVER, filler.fileResolver);
		}
		
		return parameterValues;
	}

	protected void fillSubreport() throws JRException
	{
		if (getConnectionExpression() != null)
		{
			this.subreportFiller.fill(this.parameterValues, this.connection);
		}
		else if (getDataSourceExpression() != null)
		{
			this.subreportFiller.fill(this.parameterValues, this.dataSource);
		}
		else
		{
			this.subreportFiller.fill(this.parameterValues);
		}
	}
	

	/**
	 *
	 */
	protected boolean prepare(
		int availableStretchHeight,
		boolean isOverflow
		) throws JRException
	{
		boolean willOverflow = false;

		super.prepare(availableStretchHeight, isOverflow);
		
		if (this.subreportFiller == null)
		{
			setToPrint(false);
		}

		if (!isToPrint())
		{
			return willOverflow;
		}

		if (availableStretchHeight < getRelativeY() - getY() - getBandBottomY())
		{
			setToPrint(false);
			return true;//willOverflow;
		}
			
		//willOverflow = prepareTextField((JRFillTextField)fillElement, availableStretchHeight);
		
		//subreportFiller.setPageHeight(getHeight() + availableStretchHeight);
		
		boolean filling = this.runner.isFilling();
		boolean toPrint = !isOverflow || isPrintWhenDetailOverflows() || !isAlreadyPrinted();
		boolean reprinted = isOverflow && isPrintWhenDetailOverflows();
		
		if (!filling && toPrint && reprinted)
		{
			rewind();
		}
		
		int availableHeight = getHeight() + availableStretchHeight - getRelativeY() + getY() + getBandBottomY();
		this.subreportFiller.setPageHeight(availableHeight);

		synchronized (this.subreportFiller)
		{
			JRSubreportRunResult result;
			if (filling)
			{
				if (log.isDebugEnabled())
				{
					log.debug("Fill " + this.filler.fillerId + ": resuming " + this.subreportFiller.fillerId);
				}

				result = this.runner.resume();
			}
			else if (toPrint)
			{
				setReprinted(reprinted);

				if (log.isDebugEnabled())
				{
					log.debug("Fill " + this.filler.fillerId + ": starting " + this.subreportFiller.fillerId);
				}

				result = this.runner.start();
			}
			else
			{
				this.printPage = null;
				setStretchHeight(getHeight());
				setToPrint(false);

				return willOverflow;
			}
			
			if (result.getException() != null)
			{
				Throwable error = result.getException();
				
				if (log.isErrorEnabled())
				{
					log.error("Fill " + this.filler.fillerId + ": exception", error);
				}
				
				if (error instanceof RuntimeException)
				{
					throw (RuntimeException) error;
				}

				throw new JRRuntimeException(error);
			}

			if (result.hasFinished())
			{
				if (log.isDebugEnabled())
				{
					log.debug("Fill " + this.filler.fillerId + ": subreport " + this.subreportFiller.fillerId + " finished");
				}
				
				copyValues();
			}
			else
			{
				if (log.isDebugEnabled())
				{
					log.debug("Fill " + this.filler.fillerId + ": subreport " + this.subreportFiller.fillerId + " to continue");
				}
			}

			this.printPage = this.subreportFiller.getCurrentPage();
			setStretchHeight(result.hasFinished() ? this.subreportFiller.getCurrentPageStretchHeight() : availableHeight);

			//if the subreport fill thread has not finished, 
			// it means that the subreport will overflow on the next page
			willOverflow = !result.hasFinished();
			
			if (!willOverflow)
			{
				//the subreport fill thread has finished and the next time we shall create a new one
				this.runner.reset();
			}
		}// synchronized
		
		Collection printElements = getPrintElements();
		if (
			(printElements == null || printElements.size() == 0) &&
			isRemoveLineWhenBlank() //FIXME if the line won't be removed, the background does not appear
			)
		{
			setToPrint(false);
		}

		return willOverflow;
	}


	/**
	 *
	 */
	public void rewind() throws JRException
	{
		if (this.subreportFiller == null)
		{
			return;
		}
		
		if (log.isDebugEnabled())
		{
			log.debug("Fill " + this.filler.fillerId + ": cancelling " + this.subreportFiller.fillerId);
		}
		
		// marking the subreport filler for interruption
		this.subreportFiller.setInterrupted(true);
		
		synchronized (this.subreportFiller)
		{
			// forcing the creation of a new thread and a new subreport filler
			this.runner.cancel();
			this.runner.reset();
		}

		this.filler.unregisterSubfiller(this.subreportFiller);
		
		initSubreportFiller(null);//FIXME used cached evaluator

		if (getConnectionExpression() == null && this.dataSource != null)
		{
			if(this.dataSource instanceof JRRewindableDataSource)
			{
				((JRRewindableDataSource) this.dataSource).moveFirst();
			}
			else
			{
//				if (log.isWarnEnabled())
//					log.warn("The subreport is placed on a non-splitting band, but it does not have a rewindable data source.");
				throw new JRException("The subreport is placed on a non-splitting band, but it does not have a rewindable data source.");
			}
		}
	}


	/**
	 *
	 */
	protected JRPrintElement fill()
	{
		JRPrintRectangle printRectangle = new JRTemplatePrintRectangle(getJRTemplateRectangle());

		printRectangle.setX(getX());
		printRectangle.setY(getRelativeY());
		printRectangle.setWidth(getWidth());
		printRectangle.setHeight(getStretchHeight());
		
		return printRectangle;
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
	public void visit(JRVisitor visitor)
	{
		visitor.visitSubreport(this);
	}
	

	private JRFillSubreportReturnValue addReturnValue (JRSubreportReturnValue parentReturnValue, List returnValueList, JRFillObjectFactory factory)
	{
		JRFillSubreportReturnValue returnValue = factory.getSubreportReturnValue(parentReturnValue);
		
		byte calculation = returnValue.getCalculation();
		switch (calculation)
		{
			case JRVariable.CALCULATION_AVERAGE:
			case JRVariable.CALCULATION_VARIANCE:
			{
				JRSubreportReturnValue countVal = createHelperReturnValue(parentReturnValue, "_COUNT", JRVariable.CALCULATION_COUNT);
				addReturnValue(countVal, returnValueList, factory);

				JRSubreportReturnValue sumVal = createHelperReturnValue(parentReturnValue, "_SUM", JRVariable.CALCULATION_SUM);
				addReturnValue(sumVal, returnValueList, factory);

				this.filler.addVariableCalculationReq(returnValue.getToVariable(), calculation);

				break;
			}
			case JRVariable.CALCULATION_STANDARD_DEVIATION:
			{
				JRSubreportReturnValue varianceVal = createHelperReturnValue(parentReturnValue, "_VARIANCE", JRVariable.CALCULATION_VARIANCE);
				addReturnValue(varianceVal, returnValueList, factory);
				
				this.filler.addVariableCalculationReq(returnValue.getToVariable(), calculation);
				break;
			}
			case JRVariable.CALCULATION_DISTINCT_COUNT:
			{
				JRSubreportReturnValue countVal = createDistinctCountHelperReturnValue(parentReturnValue);
				addReturnValue(countVal, returnValueList, factory);
				
				this.filler.addVariableCalculationReq(returnValue.getToVariable(), calculation);
				break;
			}
		}

		returnValueList.add(returnValue);
		return returnValue;

	}

	
	protected JRSubreportReturnValue createHelperReturnValue(JRSubreportReturnValue returnValue, String nameSuffix, byte calculation)
	{
		JRDesignSubreportReturnValue helper = new JRDesignSubreportReturnValue();
		helper.setToVariable(returnValue.getToVariable() + nameSuffix);
		helper.setSubreportVariable(returnValue.getSubreportVariable());
		helper.setCalculation(calculation);
		helper.setIncrementerFactoryClassName(helper.getIncrementerFactoryClassName());//FIXME shouldn't it be returnValue?
		
		return helper;
	}
	

	protected JRSubreportReturnValue createDistinctCountHelperReturnValue(JRSubreportReturnValue returnValue)
	{
		JRDesignSubreportReturnValue helper = new JRDesignSubreportReturnValue();
		helper.setToVariable(returnValue.getToVariable() + "_DISTINCT_COUNT");
		helper.setSubreportVariable(returnValue.getSubreportVariable());
		helper.setCalculation(JRVariable.CALCULATION_NOTHING);
		helper.setIncrementerFactoryClassName(helper.getIncrementerFactoryClassName());//FIXME shouldn't it be returnValue? tests required
		
		return helper;
	}
	

	public JRSubreportReturnValue[] getReturnValues()
	{
		return this.returnValues;
	}
	
	
	public boolean usesForReturnValue(String variableName)
	{
		boolean used = false;
		if (this.returnValues != null)
		{
			for (int j = 0; j < this.returnValues.length; j++)
			{
				JRSubreportReturnValue returnValue = this.returnValues[j];
				if (returnValue.getToVariable().equals(variableName))
				{
					used = true;
					break;
				}
			}
		}
		return used;
	}

	/**
	 * Copies the values from the subreport to the variables of the master report.
	 */
	protected void copyValues()
	{
		if (this.returnValues != null && this.returnValues.length > 0)
		{
			for (int i = 0; i < this.returnValues.length; i++)
			{
				copyValue(this.returnValues[i]);
			}
		}
	}


	protected void copyValue(JRFillSubreportReturnValue returnValue)
	{
		try
		{
			JRFillVariable variable = this.filler.getVariable(returnValue.getToVariable());
			Object value = this.subreportFiller.getVariableValue(returnValue.getSubreportVariable());
			
			Object newValue = returnValue.getIncrementer().increment(variable, value, AbstractValueProvider.getCurrentValueProvider());
			variable.setOldValue(newValue);
			variable.setValue(newValue);
			variable.setIncrementedValue(newValue);
		}
		catch (JRException e)
		{
			throw new JRRuntimeException(e);
		}
	}


	/**
	 * Verifies the list of copied values against the subreport.
	 * 
	 * @throws JRException
	 */
	private void checkReturnValues() throws JRException
	{
		if (this.returnValues != null && this.returnValues.length > 0 && !this.checkedReports.contains(this.jasperReport))
		{
			for (int i = 0; i < this.returnValues.length; i++)
			{
				JRSubreportReturnValue returnValue = this.returnValues[i];
				String subreportVariableName = returnValue.getSubreportVariable();
				JRVariable subrepVariable = this.subreportFiller.getVariable(subreportVariableName);
				if (subrepVariable == null)
				{
					throw new JRException("Subreport variable " + subreportVariableName + " not found.");
				}
				
				JRVariable variable = this.filler.getVariable(returnValue.getToVariable());
				if (
					returnValue.getCalculation() == JRVariable.CALCULATION_COUNT
					|| returnValue.getCalculation() == JRVariable.CALCULATION_DISTINCT_COUNT
					)
				{
					if (!Number.class.isAssignableFrom(variable.getValueClass()))
					{
						throw new JRException("Variable " + returnValue.getToVariable() + 
								" must have a numeric type.");
					}
				}
				else if (!variable.getValueClass().isAssignableFrom(subrepVariable.getValueClass()) &&
						!(Number.class.isAssignableFrom(variable.getValueClass()) && Number.class.isAssignableFrom(subrepVariable.getValueClass())))
				{
					throw new JRException("Variable " + returnValue.getToVariable() + 
							" is not assignable from subreport variable " + 
							subreportVariableName);
				}
			}
			
			if (isUsingCache())
				this.checkedReports.add(this.jasperReport);
		}
	}
	
	
	protected void resolveElement (JRPrintElement element, byte evaluation)
	{
		// nothing
	}


	public Boolean isOwnUsingCache()
	{
		return ((JRSubreport)this.parent).isOwnUsingCache();
	}


	public void setUsingCache(Boolean isUsingCache)
	{
	}


	public JRFillCloneable createClone(JRFillCloneFactory factory)
	{
		//not needed
		return null;
	}
	
	protected static JRSubreportRunnerFactory getRunnerFactory() throws JRException
	{
		String factoryClassName = JRProperties.getProperty(JRSubreportRunnerFactory.SUBREPORT_RUNNER_FACTORY);
		if (factoryClassName == null)
		{
			throw new JRException("Property \"" + JRSubreportRunnerFactory.SUBREPORT_RUNNER_FACTORY + "\" must be set");
		}
		return (JRSubreportRunnerFactory) runnerFactoryCache.getCachedInstance(factoryClassName);
	}
}
