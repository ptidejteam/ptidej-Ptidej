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

import java.io.Serializable;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRDatasetParameter;
import net.sf.jasperreports.engine.JRDatasetRun;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRRuntimeException;

/**
 * Base implementation of the {@link net.sf.jasperreports.engine.JRDatasetRun JRDatasetRun} interface.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRBaseDatasetRun.java,v 1.1 2008/09/29 16:21:16 guehene Exp $
 */
public class JRBaseDatasetRun implements JRDatasetRun, Serializable
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	protected String datasetName;
	protected JRExpression parametersMapExpression;
	protected JRDatasetParameter[] parameters;
	protected JRExpression connectionExpression;
	protected JRExpression dataSourceExpression;
	
	
	/**
	 * Creates an empty object.
	 */
	protected JRBaseDatasetRun()
	{
	}

	
	/**
	 * Creates a copy of a dataset instantiation.
	 * 
	 * @param datasetRun the dataset instantiation
	 * @param factory the base object factory
	 */
	protected JRBaseDatasetRun(JRDatasetRun datasetRun, JRBaseObjectFactory factory)
	{
		factory.put(datasetRun, this);
		
		this.datasetName = datasetRun.getDatasetName();
		this.parametersMapExpression = factory.getExpression(datasetRun.getParametersMapExpression());
		this.connectionExpression = factory.getExpression(datasetRun.getConnectionExpression());
		this.dataSourceExpression = factory.getExpression(datasetRun.getDataSourceExpression());
		
		JRDatasetParameter[] datasetParams = datasetRun.getParameters();
		if (datasetParams != null && datasetParams.length > 0)
		{
			this.parameters = new JRBaseDatasetParameter[datasetParams.length];
			for (int i = 0; i < this.parameters.length; i++)
			{
				this.parameters[i] = factory.getDatasetParameter(datasetParams[i]);
			}
		}
	}

	public String getDatasetName()
	{
		return this.datasetName;
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

	/**
	 * 
	 */
	public Object clone() 
	{
		JRBaseDatasetRun clone = null;
		
		try
		{
			clone = (JRBaseDatasetRun)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new JRRuntimeException(e);
		}

		if (this.parametersMapExpression != null)
		{
			clone.parametersMapExpression = (JRExpression)this.parametersMapExpression.clone();
		}
		if (this.connectionExpression != null)
		{
			clone.connectionExpression = (JRExpression)this.connectionExpression.clone();
		}
		if (this.dataSourceExpression != null)
		{
			clone.dataSourceExpression = (JRExpression)this.dataSourceExpression.clone();
		}

		if (this.parameters != null)
		{
			clone.parameters = new JRDatasetParameter[this.parameters.length];
			for(int i = 0; i < this.parameters.length; i++)
			{
				clone.parameters[i] = (JRDatasetParameter)this.parameters[i].clone();
			}
		}

		return clone;
	}
}
