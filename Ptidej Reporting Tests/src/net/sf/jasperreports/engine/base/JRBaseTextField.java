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

import java.io.IOException;
import java.io.ObjectInputStream;

import net.sf.jasperreports.engine.JRAnchor;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JRHyperlinkHelper;
import net.sf.jasperreports.engine.JRHyperlinkParameter;
import net.sf.jasperreports.engine.JRTextField;
import net.sf.jasperreports.engine.JRVisitor;
import net.sf.jasperreports.engine.util.JRStyleResolver;


/**
 * This class is used for representing a text field.
 *
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRBaseTextField.java,v 1.1 2008/09/29 16:21:17 guehene Exp $
 */
public class JRBaseTextField extends JRBaseTextElement implements JRTextField
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_STRETCH_WITH_OVERFLOW = "stretchWithOverflow";

	/**
	 *
	 */
	protected boolean isStretchWithOverflow = false;
	protected byte evaluationTime = JRExpression.EVALUATION_TIME_NOW;
	protected String pattern;
	protected Boolean isBlankWhenNull = null;
	protected byte hyperlinkType = JRHyperlink.HYPERLINK_TYPE_NULL;
	protected String linkType;
	protected byte hyperlinkTarget = JRHyperlink.HYPERLINK_TARGET_SELF;
	private JRHyperlinkParameter[] hyperlinkParameters;

	/**
	 *
	 */
	protected JRGroup evaluationGroup = null;
	protected JRExpression expression = null;
	protected JRExpression anchorNameExpression = null;
	protected JRExpression hyperlinkReferenceExpression = null;
	protected JRExpression hyperlinkAnchorExpression = null;
	protected JRExpression hyperlinkPageExpression = null;
	private JRExpression hyperlinkTooltipExpression;

	/**
	 * The bookmark level for the anchor associated with this field.
	 * @see JRAnchor#getBookmarkLevel()
	 */
	protected int bookmarkLevel = JRAnchor.NO_BOOKMARK;

	/**
	 * Initializes the text field properties.
	 */
	protected JRBaseTextField(JRTextField textField, JRBaseObjectFactory factory)
	{
		super(textField, factory);
		
		this.isStretchWithOverflow = textField.isStretchWithOverflow();
		this.evaluationTime = textField.getEvaluationTime();
		this.pattern = textField.getOwnPattern();
		this.isBlankWhenNull = textField.isOwnBlankWhenNull();
		this.linkType = textField.getLinkType();
		this.hyperlinkTarget = textField.getHyperlinkTarget();
		this.hyperlinkParameters = JRBaseHyperlink.copyHyperlinkParameters(textField, factory);

		this.evaluationGroup = factory.getGroup(textField.getEvaluationGroup());
		this.expression = factory.getExpression(textField.getExpression());
		this.anchorNameExpression = factory.getExpression(textField.getAnchorNameExpression());
		this.hyperlinkReferenceExpression = factory.getExpression(textField.getHyperlinkReferenceExpression());
		this.hyperlinkAnchorExpression = factory.getExpression(textField.getHyperlinkAnchorExpression());
		this.hyperlinkPageExpression = factory.getExpression(textField.getHyperlinkPageExpression());
		this.hyperlinkTooltipExpression = factory.getExpression(textField.getHyperlinkTooltipExpression());
		this.bookmarkLevel = textField.getBookmarkLevel();
	}
		

	/**
	 *
	 */
	public boolean isStretchWithOverflow()
	{
		return this.isStretchWithOverflow;
	}
		
	/**
	 *
	 */
	public void setStretchWithOverflow(boolean isStretchWithOverflow)
	{
		boolean old = this.isStretchWithOverflow;
		this.isStretchWithOverflow = isStretchWithOverflow;
		getEventSupport().firePropertyChange(PROPERTY_STRETCH_WITH_OVERFLOW, old, this.isStretchWithOverflow);
	}
		
	/**
	 *
	 */
	public byte getEvaluationTime()
	{
		return this.evaluationTime;
	}
		
	/**
	 *
	 */
	public String getPattern()
	{
		return JRStyleResolver.getPattern(this);
	}
		
	public String getOwnPattern()
	{
		return this.pattern;
	}

	/**
	 *
	 */
	public void setPattern(String pattern)
	{
		Object old = this.pattern;
		this.pattern = pattern;
		getEventSupport().firePropertyChange(JRBaseStyle.PROPERTY_PATTERN, old, this.pattern);
	}
		
	/**
	 *
	 */
	public boolean isBlankWhenNull()
	{
		return JRStyleResolver.isBlankWhenNull(this);
	}

	/**
	 *
	 */
	public Boolean isOwnBlankWhenNull()
	{
		return this.isBlankWhenNull;
	}

	/**
	 *
	 */
	public void setBlankWhenNull(Boolean isBlank)
	{
		Object old = this.isBlankWhenNull;
		this.isBlankWhenNull = isBlank;
		getEventSupport().firePropertyChange(JRBaseStyle.PROPERTY_BLANK_WHEN_NULL, old, this.isBlankWhenNull);
	}

	/**
	 *
	 */
	public void setBlankWhenNull(boolean isBlank)
	{
		setBlankWhenNull(isBlank ? Boolean.TRUE : Boolean.FALSE);
	}

	/**
	 *
	 */
	public byte getHyperlinkType()
	{
		return JRHyperlinkHelper.getHyperlinkType(this);
	}
		
	/**
	 *
	 */
	public byte getHyperlinkTarget()
	{
		return this.hyperlinkTarget;
	}
		
	/**
	 *
	 */
	public JRGroup getEvaluationGroup()
	{
		return this.evaluationGroup;
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
	public JRExpression getAnchorNameExpression()
	{
		return this.anchorNameExpression;
	}

	/**
	 *
	 */
	public JRExpression getHyperlinkReferenceExpression()
	{
		return this.hyperlinkReferenceExpression;
	}

	/**
	 *
	 */
	public JRExpression getHyperlinkAnchorExpression()
	{
		return this.hyperlinkAnchorExpression;
	}

	/**
	 *
	 */
	public JRExpression getHyperlinkPageExpression()
	{
		return this.hyperlinkPageExpression;
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
		visitor.visitTextField(this);
	}


	public int getBookmarkLevel()
	{
		return this.bookmarkLevel;
	}


	public String getLinkType()
	{
		return this.linkType;
	}


	public JRHyperlinkParameter[] getHyperlinkParameters()
	{
		return this.hyperlinkParameters;
	}
	
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		normalizeLinkType();
	}


	protected void normalizeLinkType()
	{
		if (this.linkType == null)
		{
			 this.linkType = JRHyperlinkHelper.getLinkType(this.hyperlinkType);
		}
		this.hyperlinkType = JRHyperlink.HYPERLINK_TYPE_NULL;
	}

	
	public JRExpression getHyperlinkTooltipExpression()
	{
		return this.hyperlinkTooltipExpression;
	}
	
	/**
	 * 
	 */
	public Object clone() 
	{
		JRBaseTextField clone = (JRBaseTextField)super.clone();
		
		if (this.hyperlinkParameters != null)
		{
			clone.hyperlinkParameters = new JRHyperlinkParameter[this.hyperlinkParameters.length];
			for(int i = 0; i < this.hyperlinkParameters.length; i++)
			{
				clone.hyperlinkParameters[i] = (JRHyperlinkParameter)this.hyperlinkParameters[i].clone();
			}
		}

		if (this.expression != null)
		{
			clone.expression = (JRExpression)this.expression.clone();
		}
		if (this.anchorNameExpression != null)
		{
			clone.anchorNameExpression = (JRExpression)this.anchorNameExpression.clone();
		}
		if (this.hyperlinkReferenceExpression != null)
		{
			clone.hyperlinkReferenceExpression = (JRExpression)this.hyperlinkReferenceExpression.clone();
		}
		if (this.hyperlinkAnchorExpression != null)
		{
			clone.hyperlinkAnchorExpression = (JRExpression)this.hyperlinkAnchorExpression.clone();
		}
		if (this.hyperlinkPageExpression != null)
		{
			clone.hyperlinkPageExpression = (JRExpression)this.hyperlinkPageExpression.clone();
		}
		if (this.hyperlinkTooltipExpression != null)
		{
			clone.hyperlinkTooltipExpression = (JRExpression)this.hyperlinkTooltipExpression.clone();
		}

		return clone;
	}
}
