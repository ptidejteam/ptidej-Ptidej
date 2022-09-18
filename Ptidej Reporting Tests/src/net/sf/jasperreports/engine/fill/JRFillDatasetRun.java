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
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDatasetParameter;
import net.sf.jasperreports.engine.JRDatasetRun;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRQuery;
import net.sf.jasperreports.engine.JRScriptletException;
import net.sf.jasperreports.engine.JRVariable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class used to instantiate sub datasets.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRFillDatasetRun.java,v 1.1 2008/09/29 16:20:01 guehene Exp $
 */
public class JRFillDatasetRun implements JRDatasetRun
{
	
	private static final Log log = LogFactory.getLog(JRFillDatasetRun.class);
	
	private final JRBaseFiller filler;

	private final JRFillDataset dataset;

	private JRExpression parametersMapExpression;

	private JRDatasetParameter[] parameters;

	private JRExpression connectionExpression;

	private JRExpression dataSourceExpression;

	
	/**
	 * Construct an instance for a dataset run.
	 * 
	 * @param filler the filler
	 * @param datasetRun the dataset run
	 * @param factory the fill object factory
	 */
	public JRFillDatasetRun(JRBaseFiller filler, JRDatasetRun datasetRun, JRFillObjectFactory factory)
	{
		factory.put(datasetRun, this);

		this.filler = filler;
		this.dataset = (JRFillDataset) filler.datasetMap.get(datasetRun.getDatasetName());

		this.parametersMapExpression = datasetRun.getParametersMapExpression();
		this.parameters = datasetRun.getParameters();
		this.connectionExpression = datasetRun.getConnectionExpression();
		this.dataSourceExpression = datasetRun.getDataSourceExpression();
	}

	
	/**
	 * Instantiates and iterates the sub dataset for a chart dataset evaluation.
	 * 
	 * @param elementDataset the chart dataset
	 * @param evaluation the evaluation type
	 * @throws JRException
	 */
	public void evaluate(JRFillElementDataset elementDataset, byte evaluation) throws JRException
	{
		Map parameterValues = 
			JRFillSubreport.getParameterValues(
				this.filler, 
				this.parametersMapExpression, 
				this.parameters, 
				evaluation, 
				false, 
				this.dataset.getResourceBundle() != null,//hasResourceBundle
				false//hasFormatFactory
				);

		try
		{
			if (this.dataSourceExpression != null)
			{
				JRDataSource dataSource = (JRDataSource) this.filler.evaluateExpression(this.dataSourceExpression, evaluation);
				this.dataset.setDatasourceParameterValue(parameterValues, dataSource);
			}
			else if (this.connectionExpression != null)
			{
				Connection connection = (Connection) this.filler.evaluateExpression(this.connectionExpression, evaluation);
				this.dataset.setConnectionParameterValue(parameterValues, connection);
			}

			copyConnectionParameter(parameterValues);
			
			this.dataset.setParameterValues(parameterValues);
			this.dataset.initDatasource();
			
			this.dataset.filterElementDatasets(elementDataset);
			
			this.dataset.initCalculator();//FIXME too late for cascade param evaluations?

			iterate();
		}
		finally
		{
			this.dataset.closeDatasource();
			this.dataset.restoreElementDatasets();
		}
	}

	protected void copyConnectionParameter(Map parameterValues)
	{
		JRQuery query = this.dataset.getQuery();
		if (query != null)
		{
			String language = query.getLanguage();
			if (this.connectionExpression == null && 
					(language.equals("sql") || language.equals("SQL")) &&
					!parameterValues.containsKey(JRParameter.REPORT_CONNECTION))
			{
				JRFillParameter connParam = (JRFillParameter) this.filler.getParametersMap().get(JRParameter.REPORT_CONNECTION);
				Connection connection = (Connection) connParam.getValue();
				parameterValues.put(JRParameter.REPORT_CONNECTION, connection);
			}
		}
	}

	protected void iterate() throws JRException
	{
		this.dataset.start();

		init();

		if (this.dataset.next())
		{
			detail();

			while (this.dataset.next())
			{
				checkInterrupted();

				group();

				detail();
			}
		}

	}

	
	protected void checkInterrupted()
	{
		if (Thread.currentThread().isInterrupted() || this.filler.isInterrupted())
		{
			if (log.isDebugEnabled())
			{
				log.debug("Fill " + this.filler.fillerId + ": interrupting");
			}

			this.filler.setInterrupted(true);

			throw new JRFillInterruptedException();
		}
	}

	
	protected void group() throws JRException, JRScriptletException
	{
		this.dataset.calculator.estimateGroupRuptures();

		this.dataset.scriptlet.callBeforeGroupInit();
		this.dataset.calculator.initializeVariables(JRVariable.RESET_TYPE_GROUP);
		this.dataset.scriptlet.callAfterGroupInit();
	}

	protected void init() throws JRScriptletException, JRException
	{
		this.dataset.scriptlet.callBeforeReportInit();
		this.dataset.calculator.initializeVariables(JRVariable.RESET_TYPE_REPORT);
		this.dataset.scriptlet.callAfterReportInit();
	}

	protected void detail() throws JRScriptletException, JRException
	{
		this.dataset.scriptlet.callBeforeDetailEval();
		this.dataset.calculator.calculateVariables();
		this.dataset.scriptlet.callAfterDetailEval();
	}

	public String getDatasetName()
	{
		return this.dataset.getName();
	}

	public JRExpression getParametersMapExpression()
	{
		return this.parametersMapExpression;
	}

	public JRDatasetParameter[] getParameters()
	{
		return this.parameters;
	}

	public JRExpression getConnectionExpression()
	{
		return this.connectionExpression;
	}

	public JRExpression getDataSourceExpression()
	{
		return this.dataSourceExpression;
	}
	
	protected JRFillDataset getDataset()
	{
		return this.dataset;
	}
	
	/**
	 *
	 */
	public Object clone() 
	{
		return null;
	}
}
