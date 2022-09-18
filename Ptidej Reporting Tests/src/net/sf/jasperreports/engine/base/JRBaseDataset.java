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
import net.sf.jasperreports.engine.JRDataset;
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
import net.sf.jasperreports.engine.design.events.JRChangeEventsSupport;
import net.sf.jasperreports.engine.design.events.JRPropertyChangeSupport;

/**
 * The base implementation of {@link net.sf.jasperreports.engine.JRDataset JRDataset}.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRBaseDataset.java,v 1.1 2008/09/29 16:21:16 guehene Exp $
 */
public class JRBaseDataset implements JRDataset, Serializable, JRChangeEventsSupport
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_WHEN_RESOURCE_MISSING_TYPE = "whenResourceMissingType";

	protected final boolean isMain;
	protected String name = null;
	protected String scriptletClass = null;
	protected JRParameter[] parameters = null;
	protected JRQuery query = null;
	protected JRField[] fields = null;
	protected JRSortField[] sortFields = null;
	protected JRVariable[] variables = null;
	protected JRGroup[] groups = null;
	protected String resourceBundle = null;
	protected byte whenResourceMissingType = WHEN_RESOURCE_MISSING_TYPE_NULL;
	protected JRPropertiesMap propertiesMap;
	protected JRExpression filterExpression;
	
	protected JRBaseDataset(boolean isMain)
	{
		this.isMain = isMain;
		
		this.propertiesMap = new JRPropertiesMap();
	}
	
	protected JRBaseDataset(JRDataset dataset, JRBaseObjectFactory factory)
	{
		factory.put(dataset, this);
		
		this.name = dataset.getName();
		this.scriptletClass = dataset.getScriptletClass();
		this.resourceBundle = dataset.getResourceBundle();
		this.whenResourceMissingType = dataset.getWhenResourceMissingType();

		/*   */
		this.propertiesMap = dataset.getPropertiesMap().cloneProperties();

		this.query = factory.getQuery(dataset.getQuery());

		this.isMain = dataset.isMainDataset();
		
		/*   */
		JRParameter[] jrParameters = dataset.getParameters();
		if (jrParameters != null && jrParameters.length > 0)
		{
			this.parameters = new JRParameter[jrParameters.length];
			for(int i = 0; i < this.parameters.length; i++)
			{
				this.parameters[i] = factory.getParameter(jrParameters[i]);
			}
		}
		
		/*   */
		JRField[] jrFields = dataset.getFields();
		if (jrFields != null && jrFields.length > 0)
		{
			this.fields = new JRField[jrFields.length];
			for(int i = 0; i < this.fields.length; i++)
			{
				this.fields[i] = factory.getField(jrFields[i]);
			}
		}

		/*   */
		JRSortField[] jrSortFields = dataset.getSortFields();
		if (jrSortFields != null && jrSortFields.length > 0)
		{
			this.sortFields = new JRSortField[jrSortFields.length];
			for(int i = 0; i < this.sortFields.length; i++)
			{
				this.sortFields[i] = factory.getSortField(jrSortFields[i]);
			}
		}

		/*   */
		JRVariable[] jrVariables = dataset.getVariables();
		if (jrVariables != null && jrVariables.length > 0)
		{
			this.variables = new JRVariable[jrVariables.length];
			for(int i = 0; i < this.variables.length; i++)
			{
				this.variables[i] = factory.getVariable(jrVariables[i]);
			}
		}

		/*   */
		JRGroup[] jrGroups = dataset.getGroups();
		if (jrGroups != null && jrGroups.length > 0)
		{
			this.groups = new JRGroup[jrGroups.length];
			for(int i = 0; i < this.groups.length; i++)
			{
				this.groups[i] = factory.getGroup(jrGroups[i]);
			}
		}
		
		this.filterExpression = factory.getExpression(dataset.getFilterExpression());
	}

	
	/**
	 *
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 *
	 */
	public String getScriptletClass()
	{
		return this.scriptletClass;
	}

	/**
	 *
	 */
	public JRQuery getQuery()
	{
		return this.query;
	}

	/**
	 *
	 */
	public JRParameter[] getParameters()
	{
		return this.parameters;
	}

	/**
	 *
	 */
	public JRField[] getFields()
	{
		return this.fields;
	}

	/**
	 *
	 */
	public JRSortField[] getSortFields()
	{
		return this.sortFields;
	}

	/**
	 *
	 */
	public JRVariable[] getVariables()
	{
		return this.variables;
	}

	/**
	 *
	 */
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
		return this.resourceBundle;
	}

	public byte getWhenResourceMissingType()
	{
		return this.whenResourceMissingType;
	}

	public void setWhenResourceMissingType(byte whenResourceMissingType)
	{
		byte old = this.whenResourceMissingType;
		this.whenResourceMissingType = whenResourceMissingType;
		getEventSupport().firePropertyChange(PROPERTY_WHEN_RESOURCE_MISSING_TYPE, (float) old, (float) this.whenResourceMissingType);
	}

	public boolean hasProperties()
	{
		return this.propertiesMap != null && this.propertiesMap.hasProperties();
	}

	public JRPropertiesMap getPropertiesMap()
	{
		return this.propertiesMap;
	}

	public JRPropertiesHolder getParentProperties()
	{
		return null;
	}

	public JRExpression getFilterExpression()
	{
		return this.filterExpression;
	}
	
	/**
	 * 
	 */
	public Object clone() 
	{
		JRBaseDataset clone = null;

		try
		{
			clone = (JRBaseDataset)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new JRRuntimeException(e);
		}
		
		if (this.query != null)
		{
			clone.query = (JRQuery)this.query.clone();
		}
		if (this.filterExpression != null)
		{
			clone.filterExpression = (JRExpression)this.filterExpression.clone();
		}
		if (this.propertiesMap != null)
		{
			clone.propertiesMap = (JRPropertiesMap)this.propertiesMap.clone();
		}

		if (this.parameters != null)
		{
			clone.parameters = new JRParameter[this.parameters.length];
			for(int i = 0; i < this.parameters.length; i++)
			{
				clone.parameters[i] = (JRParameter)this.parameters[i].clone();
			}
		}

		if (this.fields != null)
		{
			clone.fields = new JRField[this.fields.length];
			for(int i = 0; i < this.fields.length; i++)
			{
				clone.fields[i] = (JRField)this.fields[i].clone();
			}
		}

		if (this.sortFields != null)
		{
			clone.sortFields = new JRSortField[this.sortFields.length];
			for(int i = 0; i < this.sortFields.length; i++)
			{
				clone.sortFields[i] = (JRSortField)this.sortFields[i].clone();
			}
		}

		if (this.variables != null)
		{
			clone.variables = new JRVariable[this.variables.length];
			for(int i = 0; i < this.variables.length; i++)
			{
				clone.variables[i] = (JRVariable)this.variables[i].clone();
			}
		}

		if (this.groups != null)
		{
			clone.groups = new JRGroup[this.groups.length];
			for(int i = 0; i < this.groups.length; i++)
			{
				clone.groups[i] = (JRGroup)this.groups[i].clone();
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
