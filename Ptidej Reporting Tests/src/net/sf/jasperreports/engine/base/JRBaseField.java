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
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRPropertiesHolder;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.design.events.JRChangeEventsSupport;
import net.sf.jasperreports.engine.design.events.JRPropertyChangeSupport;
import net.sf.jasperreports.engine.util.JRClassLoader;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRBaseField.java,v 1.1 2008/09/29 16:21:16 guehene Exp $
 */
public class JRBaseField implements JRField, Serializable, JRChangeEventsSupport
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_DESCRIPTION = "description";

	/**
	 *
	 */
	protected String name = null;
	protected String description = null;
	protected String valueClassName = java.lang.String.class.getName();
	protected String valueClassRealName = null;

	protected transient Class valueClass = null;
	
	protected JRPropertiesMap propertiesMap;


	/**
	 *
	 */
	protected JRBaseField()
	{
		this.propertiesMap = new JRPropertiesMap();
	}
	
	
	/**
	 *
	 */
	protected JRBaseField(JRField field, JRBaseObjectFactory factory)
	{
		factory.put(field, this);
		
		this.name = field.getName();
		this.description = field.getDescription();
		this.valueClassName = field.getValueClassName();
		
		this.propertiesMap = field.getPropertiesMap().cloneProperties();
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
	public String getDescription()
	{
		return this.description;
	}
		
	/**
	 *
	 */
	public void setDescription(String description)
	{
		Object old = this.description;
		this.description = description;
		getEventSupport().firePropertyChange(PROPERTY_DESCRIPTION, old, this.description);
	}
	
	/**
	 *
	 */
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
				catch(ClassNotFoundException e)
				{
					throw new JRRuntimeException(e);
				}
			}
		}
		
		return this.valueClass;
	}
	
	/**
	 *
	 */
	public String getValueClassName()
	{
		return this.valueClassName;
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

	
	/**
	 *
	 */
	public Object clone() 
	{
		JRBaseField clone = null;
		
		try
		{
			clone = (JRBaseField)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new JRRuntimeException(e);
		}

		if (this.propertiesMap != null)
		{
			clone.propertiesMap = (JRPropertiesMap)this.propertiesMap.clone();
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
