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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.sf.jasperreports.engine.JRAnchor;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRDefaultStyleProvider;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JRHyperlinkHelper;
import net.sf.jasperreports.engine.JRHyperlinkParameter;
import net.sf.jasperreports.engine.JRTextField;
import net.sf.jasperreports.engine.JRVisitor;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.base.JRBaseTextField;
import net.sf.jasperreports.engine.util.JRStyleResolver;

//import java.text.Format;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRDesignTextField.java,v 1.1 2008/09/29 16:20:08 guehene Exp $
 */
public class JRDesignTextField extends JRDesignTextElement implements JRTextField
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	
	/*
	 * Text field properties
	 */
	
	public static final String PROPERTY_ANCHOR_NAME_EXPRESSION = "anchorNameExpression";
	
	public static final String PROPERTY_BOOKMARK_LEVEL = "bookmarkLevel";
	
	public static final String PROPERTY_EVALUATION_GROUP = "evaluationGroup";
	
	public static final String PROPERTY_EVALUATION_TIME = "evaluationTime";
	
	public static final String PROPERTY_EXPRESSION = "expression";

	/**
	 *
	 */
	protected boolean isStretchWithOverflow = false;
	protected byte evaluationTime = JRExpression.EVALUATION_TIME_NOW;
	protected String pattern = null;
	protected Boolean isBlankWhenNull = null;
	protected byte hyperlinkType = JRHyperlink.HYPERLINK_TYPE_NULL;
	protected String linkType;
	protected byte hyperlinkTarget = JRHyperlink.HYPERLINK_TARGET_SELF;
	private List hyperlinkParameters;

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
	 *
	 */
	public JRDesignTextField()
	{
		super(null);
		
		this.hyperlinkParameters = new ArrayList();
	}
		
	/**
	 *
	 */
	public JRDesignTextField(JRDefaultStyleProvider defaultStyleProvider)
	{
		super(defaultStyleProvider);
		
		this.hyperlinkParameters = new ArrayList();
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
	public void setStretchWithOverflow(boolean isStretch)
	{
		boolean old = this.isStretchWithOverflow;
		this.isStretchWithOverflow = isStretch;
		getEventSupport().firePropertyChange(JRBaseTextField.PROPERTY_STRETCH_WITH_OVERFLOW, old, this.isStretchWithOverflow);
	}
		
	/**
	 *
	 */
	public void setEvaluationTime(byte evaluationTime)
	{
		byte old = this.evaluationTime;
		this.evaluationTime = evaluationTime;
		getEventSupport().firePropertyChange(PROPERTY_EVALUATION_TIME, (float) old, (float) this.evaluationTime);
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
	public void setBlankWhenNull(boolean isBlank)
	{
		setBlankWhenNull(isBlank ? Boolean.TRUE : Boolean.FALSE);
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
	 * Sets the link type as a built-in hyperlink type.
	 * 
	 * @param hyperlinkType the built-in hyperlink type
	 * @see #getLinkType()
	 */
	public void setHyperlinkType(byte hyperlinkType)
	{
		setLinkType(JRHyperlinkHelper.getLinkType(hyperlinkType));
	}
		
	/**
	 *
	 */
	public void setHyperlinkTarget(byte hyperlinkTarget)
	{
		byte old = this.hyperlinkTarget;
		this.hyperlinkTarget = hyperlinkTarget;
		getEventSupport().firePropertyChange(JRDesignHyperlink.PROPERTY_HYPERLINK_TARGET, (float) old, (float) this.hyperlinkTarget);
	}
		
	/**
	 *
	 */
	public void setEvaluationGroup(JRGroup evaluationGroup)
	{
		Object old = this.evaluationGroup;
		this.evaluationGroup = evaluationGroup;
		getEventSupport().firePropertyChange(PROPERTY_EVALUATION_GROUP, old, this.evaluationGroup);
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
	public void setAnchorNameExpression(JRExpression anchorNameExpression)
	{
		Object old = this.anchorNameExpression;
		this.anchorNameExpression = anchorNameExpression;
		getEventSupport().firePropertyChange(PROPERTY_ANCHOR_NAME_EXPRESSION, old, this.anchorNameExpression);
	}

	/**
	 *
	 */
	public void setHyperlinkReferenceExpression(JRExpression hyperlinkReferenceExpression)
	{
		Object old = this.hyperlinkReferenceExpression;
		this.hyperlinkReferenceExpression = hyperlinkReferenceExpression;
		getEventSupport().firePropertyChange(JRDesignHyperlink.PROPERTY_HYPERLINK_REFERENCE_EXPRESSION, old, this.hyperlinkReferenceExpression);
	}

	/**
	 *
	 */
	public void setHyperlinkAnchorExpression(JRExpression hyperlinkAnchorExpression)
	{
		Object old = this.hyperlinkAnchorExpression;
		this.hyperlinkAnchorExpression = hyperlinkAnchorExpression;
		getEventSupport().firePropertyChange(JRDesignHyperlink.PROPERTY_HYPERLINK_ANCHOR_EXPRESSION, old, this.hyperlinkAnchorExpression);
	}

	/**
	 *
	 */
	public void setHyperlinkPageExpression(JRExpression hyperlinkPageExpression)
	{
		Object old = this.hyperlinkPageExpression;
		this.hyperlinkPageExpression = hyperlinkPageExpression;
		getEventSupport().firePropertyChange(JRDesignHyperlink.PROPERTY_HYPERLINK_PAGE_EXPRESSION, old, this.hyperlinkPageExpression);
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


	/**
	 * Sets the boomark level for the anchor associated with this field.
	 * 
	 * @param bookmarkLevel the bookmark level (starting from 1)
	 * or {@link JRAnchor#NO_BOOKMARK NO_BOOKMARK} if no bookmark should be created 
	 */
	public void setBookmarkLevel(int bookmarkLevel)
	{
		int old = this.bookmarkLevel;
		this.bookmarkLevel = bookmarkLevel;
		getEventSupport().firePropertyChange(PROPERTY_BOOKMARK_LEVEL, (float) old, (float) this.bookmarkLevel);
	}


	public String getLinkType()
	{
		return this.linkType;
	}


	/**
	 * Sets the hyperlink type.
	 * <p>
	 * The type can be one of the built-in types
	 * (Reference, LocalAnchor, LocalPage, RemoteAnchor, RemotePage),
	 * or can be an arbitrary type.
	 * </p>
	 * @param type the hyperlink type
	 */
	public void setLinkType(String type)
	{
		Object old = this.linkType;
		this.linkType = type;
		getEventSupport().firePropertyChange(JRDesignHyperlink.PROPERTY_LINK_TYPE, old, this.linkType);
	}


	public JRHyperlinkParameter[] getHyperlinkParameters()
	{
		JRHyperlinkParameter[] parameters;
		if (this.hyperlinkParameters.isEmpty())
		{
			parameters = null;
		}
		else
		{
			parameters = new JRHyperlinkParameter[this.hyperlinkParameters.size()];
			this.hyperlinkParameters.toArray(parameters);
		}
		return parameters;
	}
	
	
	/**
	 * Returns the list of custom hyperlink parameters.
	 * 
	 * @return the list of custom hyperlink parameters
	 */
	public List getHyperlinkParametersList()
	{
		return this.hyperlinkParameters;
	}
	
	
	/**
	 * Adds a custom hyperlink parameter.
	 * 
	 * @param parameter the parameter to add
	 */
	public void addHyperlinkParameter(JRHyperlinkParameter parameter)
	{
		this.hyperlinkParameters.add(parameter);
		getEventSupport().fireCollectionElementAddedEvent(JRDesignHyperlink.PROPERTY_HYPERLINK_PARAMETERS, 
				parameter, this.hyperlinkParameters.size() - 1);
	}
	

	/**
	 * Removes a custom hyperlink parameter.
	 * 
	 * @param parameter the parameter to remove
	 */
	public void removeHyperlinkParameter(JRHyperlinkParameter parameter)
	{
		int idx = this.hyperlinkParameters.indexOf(parameter);
		if (idx >= 0)
		{
			this.hyperlinkParameters.remove(idx);
			getEventSupport().fireCollectionElementRemovedEvent(JRDesignHyperlink.PROPERTY_HYPERLINK_PARAMETERS, 
					parameter, idx);
		}
	}
	
	
	/**
	 * Removes a custom hyperlink parameter.
	 * <p>
	 * If multiple parameters having the specified name exist, all of them
	 * will be removed
	 * </p>
	 * 
	 * @param parameterName the parameter name
	 */
	public void removeHyperlinkParameter(String parameterName)
	{
		for (ListIterator it = this.hyperlinkParameters.listIterator(); it.hasNext();)
		{
			JRHyperlinkParameter parameter = (JRHyperlinkParameter) it.next();
			if (parameter.getName() != null && parameter.getName().equals(parameterName))
			{
				it.remove();
				getEventSupport().fireCollectionElementRemovedEvent(JRDesignHyperlink.PROPERTY_HYPERLINK_PARAMETERS, 
						parameter, it.nextIndex());
			}
		}
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
	 * Sets the expression which will be used to generate the hyperlink tooltip.
	 * 
	 * @param hyperlinkTooltipExpression the expression which will be used to generate the hyperlink tooltip
	 * @see #getHyperlinkTooltipExpression()
	 */
	public void setHyperlinkTooltipExpression(JRExpression hyperlinkTooltipExpression)
	{
		Object old = this.hyperlinkTooltipExpression;
		this.hyperlinkTooltipExpression = hyperlinkTooltipExpression;
		getEventSupport().firePropertyChange(JRDesignHyperlink.PROPERTY_HYPERLINK_TOOLTIP_EXPRESSION, old, this.hyperlinkTooltipExpression);
	}
	
	/**
	 * 
	 */
	public Object clone() 
	{
		JRDesignTextField clone = (JRDesignTextField)super.clone();
		
		if (this.hyperlinkParameters != null)
		{
			clone.hyperlinkParameters = new ArrayList(this.hyperlinkParameters.size());
			for(int i = 0; i < this.hyperlinkParameters.size(); i++)
			{
				clone.hyperlinkParameters.add(((JRHyperlinkParameter)this.hyperlinkParameters.get(i)).clone());
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
