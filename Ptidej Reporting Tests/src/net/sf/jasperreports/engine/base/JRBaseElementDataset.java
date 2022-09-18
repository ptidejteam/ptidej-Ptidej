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
import net.sf.jasperreports.engine.JRDatasetRun;
import net.sf.jasperreports.engine.JRElementDataset;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRVariable;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRBaseElementDataset.java,v 1.1 2008/09/29 16:21:16 guehene Exp $
 */
public abstract class JRBaseElementDataset implements JRElementDataset, Serializable
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	protected byte resetType = JRVariable.RESET_TYPE_REPORT;
	protected byte incrementType = JRVariable.RESET_TYPE_NONE;
	protected JRGroup resetGroup = null;
	protected JRGroup incrementGroup = null;
	
	protected JRDatasetRun datasetRun;
	protected JRExpression incrementWhenExpression;

	
	protected JRBaseElementDataset()
	{
	}

	
	/**
	 *
	 */
	protected JRBaseElementDataset(JRElementDataset dataset)
	{
		if (dataset != null) {
			this.resetType = dataset.getResetType();
			this.incrementType = dataset.getIncrementType();
			this.resetGroup = dataset.getResetGroup();
			this.incrementGroup = dataset.getIncrementGroup();
			this.datasetRun = dataset.getDatasetRun();
			this.incrementWhenExpression = dataset.getIncrementWhenExpression();
		}
	}


	/**
	 *
	 */
	protected JRBaseElementDataset(JRElementDataset dataset, JRBaseObjectFactory factory)
	{
		factory.put(dataset, this);

		this.resetType = dataset.getResetType();
		this.incrementType = dataset.getIncrementType();
		this.resetGroup = factory.getGroup(dataset.getResetGroup());
		this.incrementGroup = factory.getGroup(dataset.getIncrementGroup());
		
		this.datasetRun = factory.getDatasetRun(dataset.getDatasetRun());
		this.incrementWhenExpression = factory.getExpression(dataset.getIncrementWhenExpression());
	}

	
	/**
	 *
	 */
	public byte getResetType()
	{
		return this.resetType;
	}
		
	/**
	 *
	 */
	public byte getIncrementType()
	{
		return this.incrementType;
	}
		
	/**
	 *
	 */
	public JRGroup getResetGroup()
	{
		return this.resetGroup;
	}
		
	/**
	 *
	 */
	public JRGroup getIncrementGroup()
	{
		return this.incrementGroup;
	}


	public JRDatasetRun getDatasetRun()
	{
		return this.datasetRun;
	}


	public JRExpression getIncrementWhenExpression()
	{
		return this.incrementWhenExpression;
	}
	
	
	/**
	 *
	 */
	public Object clone() 
	{
		JRBaseElementDataset clone = null;

		try
		{
			clone = (JRBaseElementDataset)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new JRRuntimeException(e);
		}

		if (this.incrementWhenExpression != null)
		{
			clone.incrementWhenExpression = (JRExpression)this.incrementWhenExpression.clone();
		}
		if (this.datasetRun != null)
		{
			clone.datasetRun = (JRDatasetRun)this.datasetRun.clone();
		}
		return clone;
	}

	
}
