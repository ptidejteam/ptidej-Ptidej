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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRDefaultStyleProvider;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRSubreport;
import net.sf.jasperreports.engine.JRSubreportParameter;
import net.sf.jasperreports.engine.JRSubreportReturnValue;
import net.sf.jasperreports.engine.JRVisitor;
import net.sf.jasperreports.engine.base.JRBaseSubreport;
import net.sf.jasperreports.engine.util.JRStyleResolver;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRDesignSubreport.java,v 1.1 2008/09/29 16:20:08 guehene Exp $
 */
public class JRDesignSubreport extends JRDesignElement implements JRSubreport
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_CONNECTION_EXPRESSION = "connectionExpression";
	
	public static final String PROPERTY_DATASOURCE_EXPRESSION = "dataSourceExpression";
	
	public static final String PROPERTY_EXPRESSION = "expression";
	
	public static final String PROPERTY_PARAMETERS_MAP_EXPRESSION = "parametersMapExpression";
	
	public static final String PROPERTY_PARAMETERS = "parameters";
	
	public static final String PROPERTY_RETURN_VALUES = "returnValues";

	/**
	 *
	 */
	protected Boolean isUsingCache = null;

	/**
	 *
	 */
	protected Map parametersMap = new HashMap();
	
	/**
	 * Values to be copied from the subreport into the master report.
	 */
	protected List returnValues = new ArrayList();

	/**
	 *
	 */
	protected JRExpression parametersMapExpression = null;
	protected JRExpression connectionExpression = null;
	protected JRExpression dataSourceExpression = null;
	protected JRExpression expression = null;


	/**
	 *
	 */
	public JRDesignSubreport(JRDefaultStyleProvider defaultStyleProvider)
	{
		super(defaultStyleProvider);
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
		if (this.isUsingCache == null)
		{
			JRExpression subreportExpression = getExpression();
			if (subreportExpression != null)
			{
				return String.class.getName().equals(subreportExpression.getValueClassName());
			}
			return true;
		}
		return this.isUsingCache.booleanValue();
	}

	/**
	 * @deprecated Replaced by {@link #setUsingCache(Boolean)}.
	 */
	public void setUsingCache(boolean isUsingCache)
	{
		setUsingCache(isUsingCache ? Boolean.TRUE : Boolean.FALSE);
	}

	/**
	 *
	 */
	public JRExpression getParametersMapExpression()
	{
		return this.parametersMapExpression;
	}

	/**
	 *
	 */
	public void setParametersMapExpression(JRExpression parametersMapExpression)
	{
		Object old = this.parametersMapExpression;
		this.parametersMapExpression = parametersMapExpression;
		getEventSupport().firePropertyChange(PROPERTY_PARAMETERS_MAP_EXPRESSION, old, this.parametersMapExpression);
	}

	/**
	 *
	 */
	public JRSubreportParameter[] getParameters()
	{
		JRSubreportParameter[] parametersArray = new JRSubreportParameter[this.parametersMap.size()];
		
		this.parametersMap.values().toArray(parametersArray);

		return parametersArray;
	}
	
	/**
	 *
	 */
	public Map getParametersMap()
	{
		return this.parametersMap;
	}
	
	/**
	 *
	 */
	public void addParameter(JRSubreportParameter subreportParameter) throws JRException
	{
		if (this.parametersMap.containsKey(subreportParameter.getName()))
		{
			throw new JRException("Duplicate declaration of subreport parameter : " + subreportParameter.getName());
		}

		this.parametersMap.put(subreportParameter.getName(), subreportParameter);
		getEventSupport().fireCollectionElementAddedEvent(PROPERTY_PARAMETERS, 
				subreportParameter, this.parametersMap.size() - 1);
	}
	
	/**
	 *
	 */
	public JRSubreportParameter removeParameter(String name)
	{
		JRSubreportParameter removed = (JRSubreportParameter)this.parametersMap.remove(name);
		if (removed != null)
		{
			getEventSupport().fireCollectionElementRemovedEvent(PROPERTY_PARAMETERS, removed, -1);
		}
		return removed;
	}

	/**
	 *
	 */
	public JRExpression getConnectionExpression()
	{
		return this.connectionExpression;
	}

	/**
	 *
	 */
	public void setConnectionExpression(JRExpression connectionExpression)
	{
		Object old = this.connectionExpression;
		this.connectionExpression = connectionExpression;
		if (this.connectionExpression != null)
		{
			setDataSourceExpression(null);
		}
		getEventSupport().firePropertyChange(PROPERTY_CONNECTION_EXPRESSION, old, this.connectionExpression);
	}

	/**
	 *
	 */
	public JRExpression getDataSourceExpression()
	{
		return this.dataSourceExpression;
	}

	/**
	 *
	 */
	public void setDataSourceExpression(JRExpression dataSourceExpression)
	{
		Object old = this.dataSourceExpression;
		this.dataSourceExpression = dataSourceExpression;
		if (this.dataSourceExpression != null)
		{
			setConnectionExpression(null);
		}
		getEventSupport().firePropertyChange(PROPERTY_DATASOURCE_EXPRESSION, old, this.dataSourceExpression);
	}

	/**
	 *
	 */
	public JRExpression getExpression()
	{
		return this.expression;
	}

	/**
	 *
	 */
	public void setExpression(JRExpression expression)
	{
		Object old = this.expression;
		this.expression = expression;
		getEventSupport().firePropertyChange(PROPERTY_EXPRESSION, old, this.expression);
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
	
	
	/**
	 * Adds a return value to the subreport.
	 * 
	 * @param returnValue the return value to be added.
	 */
	public void addReturnValue(JRSubreportReturnValue returnValue)
	{
		this.returnValues.add(returnValue);
		getEventSupport().fireCollectionElementAddedEvent(PROPERTY_RETURN_VALUES, 
				returnValue, this.returnValues.size() - 1);
	}

	
	/**
	 * Returns the list of values to be copied from the subreport into the master.
	 * 
	 * @return the list of values to be copied from the subreport into the master.
	 */
	public JRSubreportReturnValue[] getReturnValues()
	{
		JRSubreportReturnValue[] returnValuesArray = new JRSubreportReturnValue[this.returnValues.size()];

		this.returnValues.toArray(returnValuesArray);

		return returnValuesArray;
	}

	
	/**
	 * Returns the list of values to be copied from the subreport into the master.
	 * 
	 * @return list of {@link JRSubreportReturnValue JRSubreportReturnValue} objects
	 */
	public List getReturnValuesList()
	{
		return this.returnValues;
	}

	
	/**
	 * Removes a return value from the subreport.
	 * 
	 * @param returnValue the return value to be removed
	 * @return <code>true</code> if the return value was found and removed 
	 */
	public boolean removeReturnValue(JRSubreportReturnValue returnValue)
	{
		int idx = this.returnValues.indexOf(returnValue);
		if (idx >= 0)
		{
			this.returnValues.remove(idx);
			getEventSupport().fireCollectionElementRemovedEvent(PROPERTY_RETURN_VALUES, returnValue, idx);
			return true;
		}
		return false;
	}


	public Boolean isOwnUsingCache()
	{
		return this.isUsingCache;
	}


	public void setUsingCache(Boolean isUsingCache)
	{
		Object old = this.isUsingCache;
		this.isUsingCache = isUsingCache;
		getEventSupport().firePropertyChange(JRBaseSubreport.PROPERTY_USING_CACHE, old, this.isUsingCache);
	}

	/**
	 * 
	 */
	public Object clone() 
	{
		JRDesignSubreport clone = (JRDesignSubreport)super.clone();
		
		if (this.parametersMap != null)
		{
			clone.parametersMap = new HashMap(this.parametersMap.size());
			for(Iterator it = this.parametersMap.keySet().iterator(); it.hasNext();)
			{
				String key = (String)it.next();
				JRSubreportParameter parameter = (JRSubreportParameter)this.parametersMap.get(key);
				clone.parametersMap.put(key, parameter.clone());
			}
		}

		if (this.returnValues != null)
		{
			clone.returnValues = new ArrayList(this.returnValues.size());
			for(int i = 0; i < this.returnValues.size(); i++)
			{
				clone.returnValues.add(((JRSubreportReturnValue)this.returnValues.get(i)).clone());
			}
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
		if (this.expression != null)
		{
			clone.expression = (JRExpression)this.expression.clone();
		}

		return clone;
	}
}
