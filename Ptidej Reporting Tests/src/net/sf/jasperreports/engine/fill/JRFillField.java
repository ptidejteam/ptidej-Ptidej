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

import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRPropertiesHolder;
import net.sf.jasperreports.engine.JRPropertiesMap;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRFillField.java,v 1.1 2008/09/29 16:20:01 guehene Exp $
 */
public class JRFillField implements JRField
{


	/**
	 *
	 */
	protected JRField parent = null;

	/**
	 *
	 */
	private Object previousOldValue = null;
	private Object oldValue = null;
	private Object value = null;
	private Object savedValue;


	/**
	 *
	 */
	protected JRFillField(
		JRField field, 
		JRFillObjectFactory factory
		)
	{
		factory.put(field, this);

		this.parent = field;
	}


	/**
	 *
	 */
	public String getName()
	{
		return this.parent.getName();
	}
		
	/**
	 *
	 */
	public String getDescription()
	{
		return this.parent.getDescription();
	}
		
	/**
	 *
	 */
	public void setDescription(String description)
	{
	}
	
	/**
	 *
	 */
	public Class getValueClass()
	{
		return this.parent.getValueClass();
	}
	
	/**
	 *
	 */
	public String getValueClassName()
	{
		return this.parent.getValueClassName();
	}
	
	/**
	 *
	 */
	public Object getOldValue()
	{
		return this.oldValue;
	}
		
	/**
	 *
	 */
	public void setOldValue(Object oldValue)
	{
		this.oldValue = oldValue;
	}

	/**
	 *
	 */
	public Object getValue()
	{
		return this.value;
	}
		
	/**
	 *
	 */
	public void setValue(Object value)
	{
		this.value = value;
	}
		
	public Object getValue(byte evaluation)
	{
		Object returnValue;
		switch (evaluation)
		{
			case JRExpression.EVALUATION_OLD:
				returnValue = this.oldValue;
				break;
			default:
				returnValue = this.value;
				break;
		}
		return returnValue;
	}
	
	public void overwriteValue(Object newValue, byte evaluation)
	{
		switch (evaluation)
		{
			case JRExpression.EVALUATION_OLD:
				this.savedValue = this.oldValue;
				this.oldValue = newValue;
				break;
			default:
				this.savedValue = this.value;
				this.value = newValue;
				break;
		}
	}
	
	public void restoreValue(byte evaluation)
	{
		switch (evaluation)
		{
			case JRExpression.EVALUATION_OLD:
				this.oldValue = this.savedValue;
				break;
			default:
				this.value = this.savedValue;
				break;
		}
		this.savedValue = null;
	}


	
	public Object getPreviousOldValue()
	{
		return this.previousOldValue;
	}


	
	public void setPreviousOldValue(Object previousOldValue)
	{
		this.previousOldValue = previousOldValue;
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
	
	/**
	 *
	 */
	public Object clone() 
	{
		return null;
	}
}
