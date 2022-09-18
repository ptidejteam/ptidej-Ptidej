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
package net.sf.jasperreports.charts.design;

import net.sf.jasperreports.charts.JRPieDataset;
import net.sf.jasperreports.engine.JRChartDataset;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.design.JRDesignChartDataset;
import net.sf.jasperreports.engine.design.JRVerifier;



/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRDesignPieDataset.java,v 1.1 2008/09/29 16:21:32 guehene Exp $
 */
public class JRDesignPieDataset extends JRDesignChartDataset implements JRPieDataset
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_KEY_EXPRESSION = "keyExpression";
	
	public static final String PROPERTY_LABEL_EXPRESSION = "labelExpression";
	
	public static final String PROPERTY_SECTION_HYPERLINK = "sectionHyperlink";
	
	public static final String PROPERTY_VALUE_EXPRESSION = "valueExpression";

	protected JRExpression keyExpression = null;
	protected JRExpression valueExpression = null;
	protected JRExpression labelExpression = null;
	private JRHyperlink sectionHyperlink;


	/**
	 *
	 */
	public JRDesignPieDataset(JRChartDataset dataset)
	{
		super(dataset);
	}


	/**
	 *
	 */
	public JRExpression getKeyExpression()
	{
		return this.keyExpression;
	}
		
	/**
	 *
	 */
	public void setKeyExpression(JRExpression keyExpression)
	{
		Object old = this.keyExpression;
		this.keyExpression = keyExpression;
		getEventSupport().firePropertyChange(PROPERTY_KEY_EXPRESSION, old, this.keyExpression);
	}

	/**
	 *
	 */
	public JRExpression getValueExpression()
	{
		return this.valueExpression;
	}
		
	/**
	 *
	 */
	public void setValueExpression(JRExpression valueExpression)
	{
		Object old = this.valueExpression;
		this.valueExpression = valueExpression;
		getEventSupport().firePropertyChange(PROPERTY_VALUE_EXPRESSION, old, this.valueExpression);
	}

	/**
	 *
	 */
	public JRExpression getLabelExpression()
	{
		return this.labelExpression;
	}
		
	/**
	 *
	 */
	public void setLabelExpression(JRExpression labelExpression)
	{
		Object old = this.labelExpression;
		this.labelExpression = labelExpression;
		getEventSupport().firePropertyChange(PROPERTY_LABEL_EXPRESSION, old, this.labelExpression);
	}


	/** 
	 * 
	 */
	public byte getDatasetType() {
		return JRChartDataset.PIE_DATASET;
	}
	
	
	/**
	 *
	 */
	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}

	
	public JRHyperlink getSectionHyperlink()
	{
		return this.sectionHyperlink;
	}


	/**
	 * Sets the hyperlink specification for chart sections.
	 * 
	 * @param sectionHyperlink the hyperlink specification
	 * @see #getSectionHyperlink()
	 */
	public void setSectionHyperlink(JRHyperlink sectionHyperlink)
	{
		Object old = this.sectionHyperlink;
		this.sectionHyperlink = sectionHyperlink;
		getEventSupport().firePropertyChange(PROPERTY_SECTION_HYPERLINK, old, this.sectionHyperlink);
	}


	public void validate(JRVerifier verifier)
	{
		verifier.verify(this);
	}


	/**
	 * 
	 */
	public Object clone() 
	{
		JRDesignPieDataset clone = (JRDesignPieDataset)super.clone();
		
		if (this.keyExpression != null)
		{
			clone.keyExpression = (JRExpression)this.keyExpression.clone();
		}
		if (this.valueExpression != null)
		{
			clone.valueExpression = (JRExpression)this.valueExpression.clone();
		}
		if (this.labelExpression != null)
		{
			clone.labelExpression = (JRExpression)this.labelExpression.clone();
		}
		if (this.sectionHyperlink != null)
		{
			clone.sectionHyperlink = (JRHyperlink)this.sectionHyperlink.clone();
		}
		
		return clone;
	}
}
