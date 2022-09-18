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

import java.util.TimeZone;

import net.sf.jasperreports.engine.JRDatasetRun;
import net.sf.jasperreports.engine.JRElementDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRGroup;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRFillElementDataset.java,v 1.1 2008/09/29 16:20:01 guehene Exp $
 */
public abstract class JRFillElementDataset implements JRElementDataset
{


	/**
	 *
	 */
	protected JRElementDataset parent = null;
	private final JRBaseFiller filler;

	protected JRGroup resetGroup = null;
	protected JRGroup incrementGroup = null;

	private boolean isIncremented = true;

	protected JRFillDatasetRun datasetRun;
	private boolean increment = false;

	/**
	 *
	 */
	protected JRFillElementDataset(
		JRElementDataset dataset, 
		JRFillObjectFactory factory
		)
	{
		factory.put(dataset, this);

		this.parent = dataset;
		this.filler = factory.getFiller();
		
		this.resetGroup = factory.getGroup(dataset.getResetGroup());
		this.incrementGroup = factory.getGroup(dataset.getIncrementGroup());
		
		this.datasetRun = factory.getDatasetRun(dataset.getDatasetRun());
	}


	/**
	 *
	 */
	public byte getResetType()
	{
		return this.parent.getResetType();
	}
		
	/**
	 *
	 */
	public byte getIncrementType()
	{
		return this.parent.getIncrementType();
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
		
	/**
	 *
	 */
	protected TimeZone getTimeZone()
	{
		return this.filler.getTimeZone();
	}
		
	/**
	 *
	 */
	protected void initialize()
	{
		customInitialize();
		this.isIncremented = false;
		this.increment = false;
	}

	/**
	 *
	 */
	protected void evaluate(JRCalculator calculator) throws JRExpressionEvalException
	{
		evaluateIncrementWhenExpression(calculator);
		
		if (this.increment)
		{
			customEvaluate(calculator);
		}

		this.isIncremented = false;
	}

	
	protected void evaluateIncrementWhenExpression(JRCalculator calculator) throws JRExpressionEvalException
	{
		JRExpression incrementWhenExpression = getIncrementWhenExpression();
		if (incrementWhenExpression == null)
		{
			this.increment = true;
		}
		else
		{
			Boolean evaluated = (Boolean) calculator.evaluate(incrementWhenExpression);
			this.increment = evaluated != null && evaluated.booleanValue();
		}
	}


	/**
	 *
	 */
	protected void increment()
	{
		if (!this.isIncremented && this.increment)
		{
			customIncrement();
		}
		this.isIncremented = true;
	}


	/**
	 *
	 */
	protected abstract void customInitialize();

	/**
	 *
	 */
	protected abstract void customEvaluate(JRCalculator calculator) throws JRExpressionEvalException;

	/**
	 *
	 */
	protected abstract void customIncrement();


	public JRDatasetRun getDatasetRun()
	{
		return this.datasetRun;
	}
	
	
	public void evaluateDatasetRun(byte evaluation) throws JRException
	{
		if (this.datasetRun != null)
		{
			this.datasetRun.evaluate(this, evaluation);
		}
	}
	
	public JRFillDataset getInputDataset()
	{
		JRFillDataset inputDataset;
		if (this.datasetRun != null)
		{
			inputDataset = this.datasetRun.getDataset();
		}
		else
		{
			inputDataset = this.filler.mainDataset;
		}
		
		return inputDataset;
	}


	public JRExpression getIncrementWhenExpression()
	{
		return this.parent.getIncrementWhenExpression();
	}
	
	/**
	 *
	 */
	public Object clone() 
	{
		return null;
	}
}
