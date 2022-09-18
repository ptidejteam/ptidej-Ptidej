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
package net.sf.jasperreports.crosstabs.base;

import java.io.Serializable;

import net.sf.jasperreports.crosstabs.JRCrosstabMeasure;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRVariable;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;
import net.sf.jasperreports.engine.util.JRClassLoader;

/**
 * Base read-only crosstab measure implementation.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRBaseCrosstabMeasure.java,v 1.1 2008/09/29 16:22:09 guehene Exp $
 */
public class JRBaseCrosstabMeasure implements JRCrosstabMeasure, Serializable
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	protected String name;
	protected String valueClassName;
	protected String valueClassRealName = null;
	protected Class valueClass;
	protected JRExpression expression;
	protected byte calculation = JRVariable.CALCULATION_COUNT;
	protected String incrementerFactoryClassName;
	protected String incrementerFactoryClassRealName;
	protected Class incrementerFactoryClass;
	protected byte percentageOfType = JRCrosstabMeasure.PERCENTAGE_TYPE_NONE;
	protected String percentageCalculatorClassName;
	protected String percentageCalculatorClassRealName;
	protected Class percentageCalculatorClass;
	protected JRVariable variable;

	protected JRBaseCrosstabMeasure()
	{
	}
	
	public JRBaseCrosstabMeasure(JRCrosstabMeasure measure, JRBaseObjectFactory factory)
	{
		factory.put(measure, this);
		
		this.name = measure.getName();
		this.valueClassName = measure.getValueClassName();
		this.expression = factory.getExpression(measure.getValueExpression());
		this.calculation = measure.getCalculation();
		this.incrementerFactoryClassName = measure.getIncrementerFactoryClassName();
		this.percentageOfType = measure.getPercentageOfType();		
		this.percentageCalculatorClassName = measure.getPercentageCalculatorClassName();
		this.variable = factory.getVariable(measure.getVariable());
	}
	
	public String getName()
	{
		return this.name;
	}

	public String getValueClassName()
	{
		return this.valueClassName;
	}

	public JRExpression getValueExpression()
	{
		return this.expression;
	}

	public byte getCalculation()
	{
		return this.calculation;
	}

	public String getIncrementerFactoryClassName()
	{
		return this.incrementerFactoryClassName;
	}

	public byte getPercentageOfType()
	{
		return this.percentageOfType;
	}

	public Class getIncrementerFactoryClass()
	{
		if (this.incrementerFactoryClass == null)
		{
			String className = getIncrementerFactoryClassRealName();
			if (className != null)
			{
				try
				{
					this.incrementerFactoryClass = JRClassLoader.loadClassForName(className);
				}
				catch (ClassNotFoundException e)
				{
					throw new JRRuntimeException("Could not load measure incrementer class", e);
				}
			}
		}
		
		return this.incrementerFactoryClass;
	}

	/**
	 *
	 */
	private String getIncrementerFactoryClassRealName()
	{
		if (this.incrementerFactoryClassRealName == null)
		{
			this.incrementerFactoryClassRealName = JRClassLoader.getClassRealName(this.incrementerFactoryClassName);
		}
		
		return this.incrementerFactoryClassRealName;
	}

	public Class getValueClass()
	{
		if (this.valueClass == null)
		{
			String className = getValueClassRealName();
			if (className != null)
			{
				try
				{
					this.valueClass = JRClassLoader.loadClassForName(className);
				}
				catch (ClassNotFoundException e)
				{
					throw new JRRuntimeException("Could not load bucket value class", e);
				}
			}
		}
		
		return this.valueClass;
	}

	/**
	 *
	 */
	private String getValueClassRealName()
	{
		if (this.valueClassRealName == null)
		{
			this.valueClassRealName = JRClassLoader.getClassRealName(this.valueClassName);
		}
		
		return this.valueClassRealName;
	}

	public JRVariable getVariable()
	{
		return this.variable;
	}

	public String getPercentageCalculatorClassName()
	{
		return this.percentageCalculatorClassName;
	}

	public Class getPercentageCalculatorClass()
	{
		if (this.percentageCalculatorClass == null)
		{
			String className = getPercentageCalculatorClassRealName();
			if (className != null)
			{
				try
				{
					this.percentageCalculatorClass = JRClassLoader.loadClassForName(className);
				}
				catch (ClassNotFoundException e)
				{
					throw new JRRuntimeException("Could not load measure percentage calculator class", e);
				}
			}
		}
		
		return this.percentageCalculatorClass;
	}

	/**
	 *
	 */
	private String getPercentageCalculatorClassRealName()
	{
		if (this.percentageCalculatorClassRealName == null)
		{
			this.percentageCalculatorClassRealName = JRClassLoader.getClassRealName(this.percentageCalculatorClassName);
		}
		
		return this.percentageCalculatorClassRealName;
	}

	/**
	 * 
	 */
	public Object clone() 
	{
		return null;//FIXMECLONE: implement this");
	}
}
