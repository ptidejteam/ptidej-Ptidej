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

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;

import net.sf.jasperreports.engine.JRAbstractScriptlet;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPropertiesHolder;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JRQuery;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRSortField;
import net.sf.jasperreports.engine.JRVariable;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRSortableDataSource;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.query.JRQueryExecuter;
import net.sf.jasperreports.engine.query.JRQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRClassLoader;
import net.sf.jasperreports.engine.util.JRQueryExecuterUtils;
import net.sf.jasperreports.engine.util.JRResourcesUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRFillDataset.java,v 1.1 2008/09/29 16:20:01 guehene Exp $
 */
public class JRFillDataset implements JRDataset
{
	
	private static final Log log = LogFactory.getLog(JRFillDataset.class);
	
	/**
	 * The filler that created this object.
	 */
	private final JRBaseFiller filler;
	
	/**
	 * The template dataset.
	 */
	private final JRDataset parent;
	
	/**
	 * Whether this is the main dataset of the report.
	 */
	private final boolean isMain;
	
	/**
	 * The dataset query.
	 */
	protected JRQuery query = null;
	
	private boolean useDatasourceParamValue = false;
	private boolean useConnectionParamValue = false;
	
	/**
	 * The dataset parameter.
	 */
	protected JRFillParameter[] parameters = null;

	/**
	 * The dataset parameters indexed by name.
	 */
	protected Map parametersMap = null;

	/**
	 * The dataset fields.
	 */
	protected JRFillField[] fields = null;
	
	/**
	 * The dataset fields indexed by name.
	 */
	protected Map fieldsMap = null;
	
	/**
	 * The dataset variables.
	 */
	protected JRFillVariable[] variables = null;
	
	/**
	 * The dataset variables indexed by name.
	 */
	protected Map variablesMap = null;
	
	/**
	 * Set of {@link VariableCalculationReq VariableCalculationReq} objects.
	 */
	protected Set variableCalculationReqs;

	/**
	 * The element datasets.
	 */
	protected JRFillElementDataset[] elementDatasets;
	
	/**
	 * Used to save the original element datasets when
	 * {@link #filterElementDatasets(JRFillElementDataset) filterElementDatasets} is called.
	 */
	protected JRFillElementDataset[] origElementDatasets;

	/**
	 * The dataset groups.
	 */
	protected JRFillGroup[] groups = null;

	/**
	 * The resource bundle base name.
	 */
	protected String resourceBundleBaseName = null;
	
	/**
	 * The resource missing handle type.
	 */
	protected byte whenResourceMissingType;
	
	/**
	 * The scriptlet class name.
	 */
	protected String scriptletClassName = null;

	/**
	 * The data source. 
	 */
	protected JRDataSource dataSource = null;
	
	/**
	 * The {@link Locale Locale} to be used by the dataset.
	 */
	protected Locale locale = null;
	
	/**
	 * The loaded resource bundle.
	 */
	protected ResourceBundle resourceBundle = null;

	/**
	 * The {@link TimeZone TimeZone} to be used by the dataset.
	 */
	protected TimeZone timeZone = null;
	
	/**
	 * The cursor used when iterating the data source.
	 */
	protected int reportCount = 0;

	/**
	 * The calculator used by the dataset.
	 */
	protected JRCalculator calculator = null;

	/**
	 * The scriptlet used by the dataset.
	 */
	protected JRAbstractScriptlet scriptlet = null;

	/**
	 * The value of the {@link JRParameter#REPORT_MAX_COUNT max count} parameter.
	 */
	protected Integer reportMaxCount = null;

	private JRQueryExecuter queryExecuter;

	
	/**
	 * Creates a fill dataset object.
	 * @param filler the filelr
	 * @param dataset the template dataset
	 * @param factory the fill object factory
	 */
	protected JRFillDataset(JRBaseFiller filler, JRDataset dataset, JRFillObjectFactory factory)
	{
		factory.put(dataset, this);
		
		this.filler = filler;
		this.parent = dataset;
		this.isMain = dataset.isMainDataset();
		
		this.scriptletClassName = dataset.getScriptletClass();
		this.resourceBundleBaseName = dataset.getResourceBundle();
		this.whenResourceMissingType = dataset.getWhenResourceMissingType();
		
		this.query = dataset.getQuery();
		
		setParameters(dataset, factory);

		setFields(dataset, factory);
		
		setVariables(dataset, factory);
		
		setGroups(dataset, factory);
	}

	
	private void setParameters(JRDataset dataset, JRFillObjectFactory factory)
	{
		JRParameter[] jrParameters = dataset.getParameters();
		if (jrParameters != null && jrParameters.length > 0)
		{
			this.parameters = new JRFillParameter[jrParameters.length];
			this.parametersMap = new HashMap();
			for (int i = 0; i < this.parameters.length; i++)
			{
				this.parameters[i] = factory.getParameter(jrParameters[i]);
				this.parametersMap.put(this.parameters[i].getName(), this.parameters[i]);
			}
		}
	}


	private void setGroups(JRDataset dataset, JRFillObjectFactory factory)
	{
		JRGroup[] jrGroups = dataset.getGroups();
		if (jrGroups != null && jrGroups.length > 0)
		{
			this.groups = new JRFillGroup[jrGroups.length];
			for (int i = 0; i < this.groups.length; i++)
			{
				this.groups[i] = factory.getGroup(jrGroups[i]);
			}
		}
	}


	private void setVariables(JRDataset dataset, JRFillObjectFactory factory)
	{
		JRVariable[] jrVariables = dataset.getVariables();
		if (jrVariables != null && jrVariables.length > 0)
		{
			List variableList = new ArrayList(jrVariables.length * 3);

			this.variablesMap = new HashMap();
			for (int i = 0; i < jrVariables.length; i++)
			{
				addVariable(jrVariables[i], variableList, factory);
			}

			setVariables(variableList);
		}
	}
	
	
	private JRFillVariable addVariable(JRVariable parentVariable, List variableList, JRFillObjectFactory factory)
	{
		JRFillVariable variable = factory.getVariable(parentVariable);

		byte calculation = variable.getCalculation();
		switch (calculation)
		{
			case JRVariable.CALCULATION_AVERAGE:
			case JRVariable.CALCULATION_VARIANCE:
			{
				JRVariable countVar = createHelperVariable(parentVariable, "_COUNT", JRVariable.CALCULATION_COUNT);
				JRFillVariable fillCountVar = addVariable(countVar, variableList, factory);
				variable.setHelperVariable(fillCountVar, JRCalculable.HELPER_COUNT);

				JRVariable sumVar = createHelperVariable(parentVariable, "_SUM", JRVariable.CALCULATION_SUM);
				JRFillVariable fillSumVar = addVariable(sumVar, variableList, factory);
				variable.setHelperVariable(fillSumVar, JRCalculable.HELPER_SUM);

				break;
			}
			case JRVariable.CALCULATION_STANDARD_DEVIATION:
			{
				JRVariable varianceVar = createHelperVariable(parentVariable, "_VARIANCE", JRVariable.CALCULATION_VARIANCE);
				JRFillVariable fillVarianceVar = addVariable(varianceVar, variableList, factory);
				variable.setHelperVariable(fillVarianceVar, JRCalculable.HELPER_VARIANCE);

				break;
			}
			case JRVariable.CALCULATION_DISTINCT_COUNT:
			{
				JRVariable countVar = createDistinctCountHelperVariable(parentVariable);
				JRFillVariable fillCountVar = addVariable(countVar, variableList, factory);
				variable.setHelperVariable(fillCountVar, JRCalculable.HELPER_COUNT);

				break;
			}
		}

		variableList.add(variable);
		return variable;
	}

	private JRVariable createHelperVariable(JRVariable variable, String nameSuffix, byte calculation)
	{
		JRDesignVariable helper = new JRDesignVariable();
		helper.setName(variable.getName() + nameSuffix);
		helper.setValueClassName(variable.getValueClassName());
		helper.setIncrementerFactoryClassName(variable.getIncrementerFactoryClassName());
		helper.setResetType(variable.getResetType());
		helper.setResetGroup(variable.getResetGroup());
		helper.setIncrementType(variable.getIncrementType());
		helper.setIncrementGroup(variable.getIncrementGroup());
		helper.setCalculation(calculation);
		helper.setSystemDefined(true);
		helper.setExpression(variable.getExpression());

		return helper;
	}

	private JRVariable createDistinctCountHelperVariable(JRVariable variable)
	{
		JRDesignVariable helper = new JRDesignVariable();
		helper.setName(variable.getName() + "_DISTINCT_COUNT");
		helper.setValueClassName(variable.getValueClassName());
		helper.setIncrementerFactoryClassName(JRDistinctCountIncrementerFactory.class.getName());
		helper.setResetType(JRVariable.RESET_TYPE_REPORT);

		if (variable.getIncrementType() != JRVariable.RESET_TYPE_NONE)
			helper.setResetType(variable.getIncrementType());
			
		helper.setResetGroup(variable.getIncrementGroup());
		helper.setCalculation(JRVariable.CALCULATION_NOTHING);
		helper.setSystemDefined(true);
		helper.setExpression(variable.getExpression());
		
		return helper;
	}

	private void setVariables(List variableList)
	{
		this.variables = new JRFillVariable[variableList.size()];
		this.variables = (JRFillVariable[]) variableList.toArray(this.variables);

		for (int i = 0; i < this.variables.length; i++)
		{
			this.variablesMap.put(this.variables[i].getName(), this.variables[i]);
		}
	}


	private void setFields(JRDataset dataset, JRFillObjectFactory factory)
	{
		JRField[] jrFields = dataset.getFields();
		if (jrFields != null && jrFields.length > 0)
		{
			this.fields = new JRFillField[jrFields.length];
			this.fieldsMap = new HashMap();
			for (int i = 0; i < this.fields.length; i++)
			{
				this.fields[i] = factory.getField(jrFields[i]);
				this.fieldsMap.put(this.fields[i].getName(), this.fields[i]);
			}
		}
	}


	/**
	 * Creates the calculator
	 * @param jasperReport the report
	 * @throws JRException
	 */
	protected void createCalculator(JasperReport jasperReport) throws JRException
	{
		setCalculator(createCalculator(jasperReport, this));
	}

	protected void setCalculator(JRCalculator calculator)
	{
		this.calculator = calculator;
	}

	protected static JRCalculator createCalculator(JasperReport jasperReport, JRDataset dataset) throws JRException
	{
		JREvaluator evaluator = JasperCompileManager.loadEvaluator(jasperReport, dataset);
		return new JRCalculator(evaluator);
	}


	/**
	 * Initializes the calculator.
	 * 
	 * @throws JRException
	 */
	protected void initCalculator() throws JRException
	{
		this.calculator.init(this);
	}


	/**
	 * Inherits properties from the report.
	 */
	protected void inheritFromMain()
	{
		if (this.resourceBundleBaseName == null && !this.isMain)
		{
			this.resourceBundleBaseName = this.filler.mainDataset.resourceBundleBaseName;
			this.whenResourceMissingType = this.filler.mainDataset.whenResourceMissingType;
		}
	}
	
	
	/**
	 * Creates the scriptlet.
	 * 
	 * @return the scriptlet
	 * @throws JRException
	 */
	protected JRAbstractScriptlet createScriptlet() throws JRException
	{
		JRAbstractScriptlet tmpScriptlet = null;

		if (this.scriptletClassName != null)
		{
			try
			{
				Class scriptletClass = JRClassLoader.loadClassForName(this.scriptletClassName);	
				tmpScriptlet = (JRAbstractScriptlet) scriptletClass.getDeclaredConstructor().newInstance();
			}
			catch (ClassNotFoundException e)
			{
				throw new JRException("Error loading scriptlet class : " + this.scriptletClassName, e);
			}
			catch (Exception e)
			{
				throw new JRException("Error creating scriptlet class instance : " + this.scriptletClassName, e);
			}
		}
		else
		{
			tmpScriptlet = new JRDefaultScriptlet();
		}

		return tmpScriptlet;
	}


	/**
	 * Initializes the element datasets.
	 * 
	 * @param factory the fill object factory used by the filler
	 */
	protected void initElementDatasets(JRFillObjectFactory factory)
	{
		this.elementDatasets = factory.getElementDatasets(this);
	}


	/**
	 * Filters the element datasets, leaving only one.
	 * <p>
	 * This method is used when a dataset is instantiated by a chart or crosstab.
	 * 
	 * @param elementDataset the element dataset that should remain
	 */
	protected void filterElementDatasets(JRFillElementDataset elementDataset)
	{
		this.origElementDatasets = this.elementDatasets;
		this.elementDatasets = new JRFillElementDataset[]{elementDataset};
	}
	
	
	/**
	 * Restores the original element datasets.
	 * <p>
	 * This method should be called after {@link #filterElementDatasets(JRFillElementDataset) filterElementDatasets}.
	 */
	protected void restoreElementDatasets()
	{
		if (this.origElementDatasets != null)
		{
			this.elementDatasets = this.origElementDatasets;
			this.origElementDatasets = null;
		}
	}
	

	/**
	 * Loads the resource bundle corresponding to the resource bundle base name and locale.
	 */
	protected ResourceBundle loadResourceBundle()
	{
		ResourceBundle loadedBundle;
		if (this.resourceBundleBaseName == null)
		{
			loadedBundle = null;
		}
		else
		{
			loadedBundle = JRResourcesUtil.loadResourceBundle(this.resourceBundleBaseName, this.locale);
		}
		return loadedBundle;
	}


	/**
	 * Reads built-in parameter values from the value map.
	 * 
	 * @param parameterValues the parameter values
	 * @throws JRException 
	 */
	protected void setParameterValues(Map parameterValues) throws JRException
	{
		parameterValues.put(JRParameter.REPORT_PARAMETERS_MAP, parameterValues);
		
		this.reportMaxCount = (Integer) parameterValues.get(JRParameter.REPORT_MAX_COUNT);

		this.locale = (Locale) parameterValues.get(JRParameter.REPORT_LOCALE);
		if (this.locale == null)
		{
			this.locale = Locale.getDefault();
			parameterValues.put(JRParameter.REPORT_LOCALE, this.locale);
		}
		
		this.resourceBundle = (ResourceBundle) parameterValues.get(JRParameter.REPORT_RESOURCE_BUNDLE);
		if (this.resourceBundle == null)
		{
			this.resourceBundle = loadResourceBundle();
			if (this.resourceBundle != null)
			{
				parameterValues.put(JRParameter.REPORT_RESOURCE_BUNDLE, this.resourceBundle);
			}
		}
		
		this.timeZone = (TimeZone) parameterValues.get(JRParameter.REPORT_TIME_ZONE);
		if (this.timeZone == null)
		{
			this.timeZone = TimeZone.getDefault();
			parameterValues.put(JRParameter.REPORT_TIME_ZONE, this.timeZone);
		}
		
		this.scriptlet = (JRAbstractScriptlet) parameterValues.get(JRParameter.REPORT_SCRIPTLET);
		if (this.scriptlet == null)
		{
			this.scriptlet = createScriptlet();
			parameterValues.put(JRParameter.REPORT_SCRIPTLET, this.scriptlet);
		}
		this.scriptlet.setData(this.parametersMap, this.fieldsMap, this.variablesMap, this.groups);
		
		setFillParameterValues(parameterValues);
	}
	
	
	protected void initDatasource() throws JRException
	{
		this.queryExecuter = null;
		
		this.dataSource = (JRDataSource) getParameterValue(JRParameter.REPORT_DATA_SOURCE);
		if (!this.useDatasourceParamValue && (this.useConnectionParamValue || this.dataSource == null))
		{
			this.dataSource = createQueryDatasource();
			setParameter(JRParameter.REPORT_DATA_SOURCE, this.dataSource);
		}
		
		JRSortField[] sortFields = getSortFields();
		if (sortFields != null && sortFields.length > 0)
		{
			this.dataSource = new JRSortableDataSource(this.dataSource, this.fields, sortFields, this.locale);
			setParameter(JRParameter.REPORT_DATA_SOURCE, this.dataSource);
		}
	}


	/**
	 * Sets the parameter values from the values map.
	 * 
	 * @param parameterValues the values map
	 * @throws JRException
	 */
	private void setFillParameterValues(Map parameterValues) throws JRException
	{
		if (this.parameters != null && this.parameters.length > 0)
		{
			for (int i = 0; i < this.parameters.length; i++)
			{
				Object value = null;
				if (parameterValues.containsKey(this.parameters[i].getName()))
				{
					value = parameterValues.get(this.parameters[i].getName());
				}
				else if (!this.parameters[i].isSystemDefined())
				{
					value = this.calculator.evaluate(this.parameters[i].getDefaultValueExpression(), JRExpression.EVALUATION_DEFAULT);
					if (value != null)
					{
						parameterValues.put(this.parameters[i].getName(), value);
					}
				}
				setParameter(this.parameters[i], value);
			}
		}
	}


	/**
	 * Returns the map of parameter values.
	 * 
	 * @return the map of parameter values
	 */
	protected Map getParameterValuesMap()
	{
		JRFillParameter paramValuesParameter = (JRFillParameter) this.parametersMap.get(
				JRParameter.REPORT_PARAMETERS_MAP);
		return (Map) paramValuesParameter.getValue();
	}
	
	/**
	 * Creates the data source from a connection.
	 * 
	 * @return the data source to be used
	 * @throws JRException
	 */
	private JRDataSource createQueryDatasource() throws JRException
	{
		if (this.query == null)
		{
			return null;
		}

		try
		{
			if (log.isDebugEnabled())
			{
				log.debug("Fill " + this.filler.fillerId + ": Creating " + this.query.getLanguage() + " query executer");
			}
			
			JRQueryExecuterFactory queryExecuterFactory = JRQueryExecuterUtils.getQueryExecuterFactory(this.query.getLanguage());
			this.queryExecuter = queryExecuterFactory.createQueryExecuter(this.parent, this.parametersMap);
			this.filler.fillContext.setRunningQueryExecuter(this.queryExecuter);
			
			return this.queryExecuter.createDatasource();
		}
		finally
		{
			this.filler.fillContext.clearRunningQueryExecuter();
		}
	}


	protected void reset()
	{
		this.useDatasourceParamValue = false;
		this.useConnectionParamValue = false;
	}

	
	/**
	 * Sets the data source to be used.
	 * 
	 * @param parameterValues the parameter values
	 * @param ds the data source
	 */
	protected void setDatasourceParameterValue(Map parameterValues, JRDataSource ds)
	{
		this.useDatasourceParamValue = true;
		
		if (ds != null)
		{
			parameterValues.put(JRParameter.REPORT_DATA_SOURCE, ds);
		}
	}


	/**
	 * Sets the JDBC connection to be used.
	 * 
	 * @param parameterValues the parameter values
	 * @param conn the connection
	 */
	protected void setConnectionParameterValue(Map parameterValues, Connection conn)
	{
		this.useConnectionParamValue = true;
		
		if (conn != null)
		{
			parameterValues.put(JRParameter.REPORT_CONNECTION, conn);
		}
	}
	
	
	protected void closeDatasource()
	{
		if (this.queryExecuter != null)
		{
			if (log.isDebugEnabled())
			{
				log.debug("Fill " + this.filler.fillerId + ": closing query executer");
			}

			this.queryExecuter.close();
			this.queryExecuter = null;
		}
		
		reset();
	}

	
	/**
	 * Starts the iteration on the data source.
	 */
	protected void start()
	{
		this.reportCount = 0;
	}

	
	/**
	 * Moves to the next record in the data source.
	 * 
	 * @return <code>true</code> if the data source was not exhausted
	 * @throws JRException
	 */
	protected boolean next() throws JRException
	{
		boolean hasNext = false;

		if (this.dataSource != null)
		{
			boolean includeRow = true;
			JRExpression filterExpression = getFilterExpression();
			do
			{
				hasNext = advanceDataSource();
				if (hasNext)
				{
					setOldValues();

					this.calculator.estimateVariables();
					if (filterExpression != null)
					{
						Boolean filterExprResult = (Boolean) this.calculator.evaluate(filterExpression, JRExpression.EVALUATION_ESTIMATED);
						includeRow = filterExprResult != null && filterExprResult.booleanValue();
					}
					
					if (!includeRow)
					{
						revertToOldValues();
					}
				}
			}
			while(hasNext && !includeRow);
			
			if (hasNext)
			{
				++this.reportCount;
			}
		}

		return hasNext;
	}


	protected void setOldValues() throws JRException
	{
		if (this.fields != null && this.fields.length > 0)
		{
			for (int i = 0; i < this.fields.length; i++)
			{
				JRFillField field = this.fields[i];
				field.setPreviousOldValue(field.getOldValue());
				field.setOldValue(field.getValue());
				field.setValue(this.dataSource.getFieldValue(field));
			}
		}

		if (this.variables != null && this.variables.length > 0)
		{
			for (int i = 0; i < this.variables.length; i++)
			{
				JRFillVariable variable = this.variables[i];
				variable.setPreviousOldValue(variable.getOldValue());
				variable.setOldValue(variable.getValue());
			}
		}
	}


	protected void revertToOldValues()
	{
		if (this.fields != null && this.fields.length > 0)
		{
			for (int i = 0; i < this.fields.length; i++)
			{
				JRFillField field = this.fields[i];
				field.setValue(field.getOldValue());
				field.setOldValue(field.getPreviousOldValue());
			}
		}
		
		if (this.variables != null && this.variables.length > 0)
		{
			for (int i = 0; i < this.variables.length; i++)
			{
				JRFillVariable variable = this.variables[i];
				variable.setValue(variable.getOldValue());
				variable.setOldValue(variable.getPreviousOldValue());
			}
		}
	}


	protected boolean advanceDataSource() throws JRException
	{
		boolean hasNext;
		hasNext = (this.reportMaxCount == null || this.reportMaxCount.intValue() > this.reportCount) && this.dataSource.next();
		return hasNext;
	}
	
	
	/**
	 * Sets the value of a parameter.
	 * 
	 * @param parameterName the parameter name
	 * @param value the value
	 * @throws JRException
	 */
	protected void setParameter(String parameterName, Object value) throws JRException
	{
		JRFillParameter parameter = (JRFillParameter) this.parametersMap.get(parameterName);
		if (parameter != null)
		{
			setParameter(parameter, value);
		}
	}
	
	
	/**
	 * Sets the value of the parameter.
	 * 
	 * @param parameter the parameter
	 * @param value the value
	 * @throws JRException
	 */
	protected void setParameter(JRFillParameter parameter, Object value) throws JRException
	{
		if (value != null)
		{
			if (parameter.getValueClass().isInstance(value))
			{
				parameter.setValue(value);
			}
			else
			{
				throw new JRException(
					"Incompatible " 
					+ value.getClass().getName() 
					+ " value assigned to parameter " 
					+ parameter.getName() 
					+ " in the " + getName() + " dataset."
					);
			}
		}
		else
		{
			parameter.setValue(value);
		}
	}

	
	/**
	 * Returns the value of a variable.
	 * 
	 * @param variableName the variable name
	 * @return the variable value
	 */
	public Object getVariableValue(String variableName)
	{
		JRFillVariable var = (JRFillVariable) this.variablesMap.get(variableName);
		if (var == null)
		{
			throw new JRRuntimeException("No such variable " + variableName);
		}
		return var.getValue();
	}

	
	/**
	 * Returns the value of a parameter.
	 * 
	 * @param parameterName the parameter name
	 * @return the parameter value
	 */
	public Object getParameterValue(String parameterName)
	{
		return getParameterValue(parameterName, false);
	}

	
	/**
	 * Returns the value of a parameter.
	 * 
	 * @param parameterName the parameter name
	 * @param ignoreMissing if set, <code>null</code> will be returned for inexisting parameters
	 * @return the parameter value
	 */
	public Object getParameterValue(String parameterName, boolean ignoreMissing)
	{
		JRFillParameter param = (JRFillParameter) this.parametersMap.get(parameterName);
		Object value;
		if (param == null)
		{
			if (!ignoreMissing)
			{
				throw new JRRuntimeException("No such parameter " + parameterName);
			}
			
			value = null;//FIXME look into REPORT_PARAMETERS_MAP?  not yet required.
		}
		else
		{
			value = param.getValue();
		}
		return value;
	}

	
	/**
	 * Returns the value of a field.
	 * 
	 * @param fieldName the field name
	 * @return the field value
	 */
	public Object getFieldValue(String fieldName)
	{
		JRFillField var = (JRFillField) this.fieldsMap.get(fieldName);
		if (var == null)
		{
			throw new JRRuntimeException("No such field " + fieldName);
		}
		return var.getValue();
	}
	
	
	/**
	 * Class used to hold expression calculation  requirements.
	 */
	protected static class VariableCalculationReq
	{
		String variableName;

		byte calculation;

		VariableCalculationReq(String variableName, byte calculation)
		{
			this.variableName = variableName;
			this.calculation = calculation;
		}

		public boolean equals(Object o)
		{
			if (o == null || !(o instanceof VariableCalculationReq))
			{
				return false;
			}

			VariableCalculationReq r = (VariableCalculationReq) o;

			return this.variableName.equals(r.variableName) && this.calculation == r.calculation;
		}

		public int hashCode()
		{
			return 31 * this.calculation + this.variableName.hashCode();
		}
	}
	
	
	/**
	 * Adds a variable calculation requirement.
	 * 
	 * @param variableName the variable name
	 * @param calculation the required calculation
	 */
	protected void addVariableCalculationReq(String variableName, byte calculation)
	{
		if (this.variableCalculationReqs == null)
		{
			this.variableCalculationReqs = new HashSet();
		}

		this.variableCalculationReqs.add(new VariableCalculationReq(variableName, calculation));
	}

	
	/**
	 * Checks if there are variable calculation requirements and creates the required variables.
	 * 
	 * @param factory the fill object factory
	 */
	protected void checkVariableCalculationReqs(JRFillObjectFactory factory)
	{
		if (this.variableCalculationReqs != null && !this.variableCalculationReqs.isEmpty())
		{
			List variableList = new ArrayList(this.variables.length * 2);

			for (int i = 0; i < this.variables.length; i++)
			{
				JRFillVariable variable = this.variables[i];
				checkVariableCalculationReq(variable, variableList, factory);
			}

			setVariables(variableList);
		}
	}

	
	private void checkVariableCalculationReq(JRFillVariable variable, List variableList, JRFillObjectFactory factory)
	{
		if (hasVariableCalculationReq(variable, JRVariable.CALCULATION_AVERAGE) || hasVariableCalculationReq(variable, JRVariable.CALCULATION_VARIANCE))
		{
			if (variable.getHelperVariable(JRCalculable.HELPER_COUNT) == null)
			{
				JRVariable countVar = createHelperVariable(variable, "_COUNT", JRVariable.CALCULATION_COUNT);
				JRFillVariable fillCountVar = factory.getVariable(countVar);
				checkVariableCalculationReq(fillCountVar, variableList, factory);
				variable.setHelperVariable(fillCountVar, JRCalculable.HELPER_COUNT);
			}

			if (variable.getHelperVariable(JRCalculable.HELPER_SUM) == null)
			{
				JRVariable sumVar = createHelperVariable(variable, "_SUM", JRVariable.CALCULATION_SUM);
				JRFillVariable fillSumVar = factory.getVariable(sumVar);
				checkVariableCalculationReq(fillSumVar, variableList, factory);
				variable.setHelperVariable(fillSumVar, JRCalculable.HELPER_SUM);
			}
		}

		if (hasVariableCalculationReq(variable, JRVariable.CALCULATION_STANDARD_DEVIATION))
		{
			if (variable.getHelperVariable(JRCalculable.HELPER_VARIANCE) == null)
			{
				JRVariable varianceVar = createHelperVariable(variable, "_VARIANCE", JRVariable.CALCULATION_VARIANCE);
				JRFillVariable fillVarianceVar = factory.getVariable(varianceVar);
				checkVariableCalculationReq(fillVarianceVar, variableList, factory);
				variable.setHelperVariable(fillVarianceVar, JRCalculable.HELPER_VARIANCE);
			}
		}

		if (hasVariableCalculationReq(variable, JRVariable.CALCULATION_DISTINCT_COUNT))
		{
			if (variable.getHelperVariable(JRCalculable.HELPER_COUNT) == null)
			{
				JRVariable countVar = createDistinctCountHelperVariable(variable);
				JRFillVariable fillCountVar = factory.getVariable(countVar);
				checkVariableCalculationReq(fillCountVar, variableList, factory);
				variable.setHelperVariable(fillCountVar, JRCalculable.HELPER_COUNT);
			}
		}

		variableList.add(variable);
	}

	
	private boolean hasVariableCalculationReq(JRVariable var, byte calculation)
	{
		return this.variableCalculationReqs.contains(new VariableCalculationReq(var.getName(), calculation));
	}


	public String getName()
	{
		return this.parent.getName();
	}

	public String getScriptletClass()
	{
		return this.parent.getScriptletClass();
	}

	public JRParameter[] getParameters()
	{
		return this.parameters;
	}

	public Map getParametersMap()
	{
		return this.parametersMap;
	}

	public JRQuery getQuery()
	{
		return this.query;
	}

	public JRField[] getFields()
	{
		return this.fields;
	}

	public JRSortField[] getSortFields()
	{
		return this.parent.getSortFields();
	}

	public JRVariable[] getVariables()
	{
		return this.variables;
	}

	public JRGroup[] getGroups()
	{
		return this.groups;
	}

	public boolean isMainDataset()
	{
		return this.isMain;
	}

	public String getResourceBundle()
	{
		return this.parent.getResourceBundle();
	}


	public byte getWhenResourceMissingType()
	{
		return this.whenResourceMissingType;
	}


	public void setWhenResourceMissingType(byte whenResourceMissingType)
	{
		this.whenResourceMissingType = whenResourceMissingType;
	}

	
	public boolean hasProperties()
	{
		return this.parent.hasProperties();
	}


	public JRPropertiesMap getPropertiesMap()
	{
		return this.parent.getPropertiesMap();
	}

	
	public JRPropertiesHolder getParentProperties()
	{
		return null;
	}


	public JRExpression getFilterExpression()
	{
		return this.parent.getFilterExpression();
	}
	
	/**
	 *
	 */
	public Object clone() 
	{
		return null;
	}
}
