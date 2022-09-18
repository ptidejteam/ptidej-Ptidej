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

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;

import net.sf.jasperreports.engine.JRAnchor;
import net.sf.jasperreports.engine.JRBox;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JRHyperlinkHelper;
import net.sf.jasperreports.engine.JRHyperlinkParameter;
import net.sf.jasperreports.engine.JRImage;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.JRVisitor;
import net.sf.jasperreports.engine.util.JRBoxUtil;
import net.sf.jasperreports.engine.util.JRPenUtil;
import net.sf.jasperreports.engine.util.JRStyleResolver;
import net.sf.jasperreports.engine.util.LineBoxWrapper;


/**
 * The actual implementation of a graphic element representing an image.
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRBaseImage.java,v 1.1 2008/09/29 16:21:16 guehene Exp $
 */
public class JRBaseImage extends JRBaseGraphicElement implements JRImage
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	/*
	 * Image properties
	 */

	public static final String PROPERTY_LAZY = "lazy";
	
	public static final String PROPERTY_ON_ERROR_TYPE = "onErrorType";
	
	public static final String PROPERTY_USING_CACHE = "usingCache";
	
	
	/**
	 *
	 */
	protected Byte scaleImage;
	protected Byte horizontalAlignment;
	protected Byte verticalAlignment;
	protected Boolean isUsingCache = null;
	protected boolean isLazy = false;
	protected byte onErrorType = ON_ERROR_TYPE_ERROR;
	protected byte evaluationTime = JRExpression.EVALUATION_TIME_NOW;
	protected byte hyperlinkType = JRHyperlink.HYPERLINK_TYPE_NULL;
	protected String linkType;
	protected byte hyperlinkTarget = JRHyperlink.HYPERLINK_TARGET_SELF;
	private JRHyperlinkParameter[] hyperlinkParameters;

	/**
	 *
	 */
	protected JRLineBox lineBox = null;

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
	 * The bookmark level for the anchor associated with this image.
	 * @see JRAnchor#getBookmarkLevel()
	 */
	protected int bookmarkLevel = JRAnchor.NO_BOOKMARK;

	/**
	 *
	 *
	protected JRBaseImage()
	{
		super();
	}
		

	/**
	 * Initializes properties that are specific to images. Common properties are initialized by its
	 * parent constructors.
	 * @param image an element whose properties are copied to this element. Usually it is a
	 * {@link net.sf.jasperreports.engine.design.JRDesignImage} that must be transformed into an
	 * <tt>JRBaseImage</tt> at compile time.
	 * @param factory a factory used in the compile process
	 */
	protected JRBaseImage(JRImage image, JRBaseObjectFactory factory)
	{
		super(image, factory);
		
		this.scaleImage = image.getOwnScaleImage();
		this.horizontalAlignment = image.getOwnHorizontalAlignment();
		this.verticalAlignment = image.getOwnVerticalAlignment();
		this.isUsingCache = image.isOwnUsingCache();
		this.isLazy = image.isLazy();
		this.onErrorType = image.getOnErrorType();
		this.evaluationTime = image.getEvaluationTime();
		this.linkType = image.getLinkType();
		this.hyperlinkTarget = image.getHyperlinkTarget();
		this.hyperlinkParameters = JRBaseHyperlink.copyHyperlinkParameters(image, factory);

		this.lineBox = image.getLineBox().clone(this);

		this.evaluationGroup = factory.getGroup(image.getEvaluationGroup());
		this.expression = factory.getExpression(image.getExpression());
		this.anchorNameExpression = factory.getExpression(image.getAnchorNameExpression());
		this.hyperlinkReferenceExpression = factory.getExpression(image.getHyperlinkReferenceExpression());
		this.hyperlinkAnchorExpression = factory.getExpression(image.getHyperlinkAnchorExpression());
		this.hyperlinkPageExpression = factory.getExpression(image.getHyperlinkPageExpression());
		this.hyperlinkTooltipExpression = factory.getExpression(image.getHyperlinkTooltipExpression());
		this.bookmarkLevel = image.getBookmarkLevel();
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
	public byte getScaleImage()
	{
		return JRStyleResolver.getScaleImage(this);
	}

	public Byte getOwnScaleImage()
	{
		return this.scaleImage;
	}

	/**
	 *
	 */
	public void setScaleImage(byte scaleImage)
	{
		setScaleImage(new Byte(scaleImage));
	}

	/**
	 *
	 */
	public void setScaleImage(Byte scaleImage)
	{
		Object old = this.scaleImage;
		this.scaleImage = scaleImage;
		getEventSupport().firePropertyChange(JRBaseStyle.PROPERTY_SCALE_IMAGE, old, this.scaleImage);
	}

	/**
	 *
	 */
	public byte getHorizontalAlignment()
	{
		return JRStyleResolver.getHorizontalAlignment(this);
	}

	public Byte getOwnHorizontalAlignment()
	{
		return this.horizontalAlignment;
	}

	/**
	 *
	 */
	public void setHorizontalAlignment(byte horizontalAlignment)
	{
		setHorizontalAlignment(new Byte(horizontalAlignment));
	}
		
	/**
	 *
	 */
	public void setHorizontalAlignment(Byte horizontalAlignment)
	{
		Object old = this.horizontalAlignment;
		this.horizontalAlignment = horizontalAlignment;
		getEventSupport().firePropertyChange(JRBaseStyle.PROPERTY_HORIZONTAL_ALIGNMENT, old, this.horizontalAlignment);
	}
		
	/**
	 *
	 */
	public byte getVerticalAlignment()
	{
		return JRStyleResolver.getVerticalAlignment(this);
	}

	public Byte getOwnVerticalAlignment()
	{
		return this.verticalAlignment;
	}

	/**
	 *
	 */
	public void setVerticalAlignment(byte verticalAlignment)
	{
		setVerticalAlignment(new Byte(verticalAlignment));
	}
		
	/**
	 *
	 */
	public void setVerticalAlignment(Byte verticalAlignment)
	{
		Object old = this.verticalAlignment;
		this.verticalAlignment = verticalAlignment;
		getEventSupport().firePropertyChange(JRBaseStyle.PROPERTY_VERTICAL_ALIGNMENT, old, this.verticalAlignment);
	}
		
	/**
	 *
	 */
	public boolean isUsingCache()
	{
		if (this.isUsingCache == null)
		{
			if (getExpression() != null)
			{
				return String.class.getName().equals(getExpression().getValueClassName());
			}
			return true;
		}
		return this.isUsingCache.booleanValue();
	}

	/**
	 *
	 */
	public Boolean isOwnUsingCache()
	{
		return this.isUsingCache;
	}

	/**
	 *
	 */
	public void setUsingCache(boolean isUsingCache)
	{
		setUsingCache(isUsingCache ? Boolean.TRUE : Boolean.FALSE);
	}

	/**
	 *
	 */
	public void setUsingCache(Boolean isUsingCache)
	{
		Object old = this.isUsingCache;
		this.isUsingCache = isUsingCache;
		getEventSupport().firePropertyChange(PROPERTY_USING_CACHE, old, this.isUsingCache);
	}

	/**
	 *
	 */
	public boolean isLazy()
	{
		return this.isLazy;
	}

	/**
	 *
	 */
	public void setLazy(boolean isLazy)
	{
		boolean old = this.isLazy;
		this.isLazy = isLazy;
		getEventSupport().firePropertyChange(PROPERTY_LAZY, old, this.isLazy);
	}

	/**
	 *
	 */
	public byte getOnErrorType()
	{
		return this.onErrorType;
	}

	/**
	 *
	 */
	public void setOnErrorType(byte onErrorType)
	{
		byte old = this.onErrorType;
		this.onErrorType = onErrorType;
		getEventSupport().firePropertyChange(PROPERTY_ON_ERROR_TYPE, (float) old, (float) this.onErrorType);
	}

	/**
	 *
	 */
	public byte getEvaluationTime()
	{
		return this.evaluationTime;
	}
		
	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public JRBox getBox()
	{
		return new LineBoxWrapper(getLineBox());
	}

	/**
	 *
	 */
	public JRLineBox getLineBox()
	{
		return this.lineBox;
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
		visitor.visitImage(this);
	}

	
	public int getBookmarkLevel()
	{
		return this.bookmarkLevel;
	}

	
	/**
	 * 
	 */
	public Float getDefaultLineWidth() 
	{
		return JRPen.LINE_WIDTH_0;
	}

	
	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public byte getBorder()
	{
		return JRPenUtil.getPenFromLinePen(this.lineBox.getPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Byte getOwnBorder()
	{
		return JRPenUtil.getOwnPenFromLinePen(this.lineBox.getPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setBorder(byte border)
	{
		JRPenUtil.setLinePenFromPen(border, this.lineBox.getPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setBorder(Byte border)
	{
		JRPenUtil.setLinePenFromPen(border, this.lineBox.getPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Color getBorderColor()
	{
		return this.lineBox.getPen().getLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Color getOwnBorderColor()
	{
		return this.lineBox.getPen().getOwnLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setBorderColor(Color borderColor)
	{
		this.lineBox.getPen().setLineColor(borderColor);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public int getPadding()
	{
		return this.lineBox.getPadding().intValue();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Integer getOwnPadding()
	{
		return this.lineBox.getOwnPadding();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setPadding(int padding)
	{
		this.lineBox.setPadding(padding);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setPadding(Integer padding)
	{
		this.lineBox.setPadding(padding);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public byte getTopBorder()
	{
		return JRPenUtil.getPenFromLinePen(this.lineBox.getTopPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Byte getOwnTopBorder()
	{
		return JRPenUtil.getOwnPenFromLinePen(this.lineBox.getTopPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setTopBorder(byte topBorder)
	{
		JRPenUtil.setLinePenFromPen(topBorder, this.lineBox.getTopPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setTopBorder(Byte topBorder)
	{
		JRPenUtil.setLinePenFromPen(topBorder, this.lineBox.getTopPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Color getTopBorderColor()
	{
		return this.lineBox.getTopPen().getLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Color getOwnTopBorderColor()
	{
		return this.lineBox.getTopPen().getOwnLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setTopBorderColor(Color topBorderColor)
	{
		this.lineBox.getTopPen().setLineColor(topBorderColor);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public int getTopPadding()
	{
		return this.lineBox.getTopPadding().intValue();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Integer getOwnTopPadding()
	{
		return this.lineBox.getOwnTopPadding();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setTopPadding(int topPadding)
	{
		this.lineBox.setTopPadding(topPadding);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setTopPadding(Integer topPadding)
	{
		this.lineBox.setTopPadding(topPadding);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public byte getLeftBorder()
	{
		return JRPenUtil.getPenFromLinePen(this.lineBox.getLeftPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Byte getOwnLeftBorder()
	{
		return JRPenUtil.getOwnPenFromLinePen(this.lineBox.getLeftPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setLeftBorder(byte leftBorder)
	{
		JRPenUtil.setLinePenFromPen(leftBorder, this.lineBox.getLeftPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setLeftBorder(Byte leftBorder)
	{
		JRPenUtil.setLinePenFromPen(leftBorder, this.lineBox.getLeftPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Color getLeftBorderColor()
	{
		return this.lineBox.getLeftPen().getLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Color getOwnLeftBorderColor()
	{
		return this.lineBox.getLeftPen().getOwnLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setLeftBorderColor(Color leftBorderColor)
	{
		this.lineBox.getLeftPen().setLineColor(leftBorderColor);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public int getLeftPadding()
	{
		return this.lineBox.getLeftPadding().intValue();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Integer getOwnLeftPadding()
	{
		return this.lineBox.getOwnLeftPadding();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setLeftPadding(int leftPadding)
	{
		this.lineBox.setLeftPadding(leftPadding);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setLeftPadding(Integer leftPadding)
	{
		this.lineBox.setLeftPadding(leftPadding);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public byte getBottomBorder()
	{
		return JRPenUtil.getPenFromLinePen(this.lineBox.getBottomPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Byte getOwnBottomBorder()
	{
		return JRPenUtil.getOwnPenFromLinePen(this.lineBox.getBottomPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setBottomBorder(byte bottomBorder)
	{
		JRPenUtil.setLinePenFromPen(bottomBorder, this.lineBox.getBottomPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setBottomBorder(Byte bottomBorder)
	{
		JRPenUtil.setLinePenFromPen(bottomBorder, this.lineBox.getBottomPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Color getBottomBorderColor()
	{
		return this.lineBox.getBottomPen().getLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Color getOwnBottomBorderColor()
	{
		return this.lineBox.getBottomPen().getOwnLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setBottomBorderColor(Color bottomBorderColor)
	{
		this.lineBox.getBottomPen().setLineColor(bottomBorderColor);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public int getBottomPadding()
	{
		return this.lineBox.getBottomPadding().intValue();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Integer getOwnBottomPadding()
	{
		return this.lineBox.getOwnBottomPadding();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setBottomPadding(int bottomPadding)
	{
		this.lineBox.setBottomPadding(bottomPadding);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setBottomPadding(Integer bottomPadding)
	{
		this.lineBox.setBottomPadding(bottomPadding);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public byte getRightBorder()
	{
		return JRPenUtil.getPenFromLinePen(this.lineBox.getRightPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Byte getOwnRightBorder()
	{
		return JRPenUtil.getOwnPenFromLinePen(this.lineBox.getRightPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setRightBorder(byte rightBorder)
	{
		JRPenUtil.setLinePenFromPen(rightBorder, this.lineBox.getRightPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setRightBorder(Byte rightBorder)
	{
		JRPenUtil.setLinePenFromPen(rightBorder, this.lineBox.getRightPen());
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Color getRightBorderColor()
	{
		return this.lineBox.getRightPen().getLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Color getOwnRightBorderColor()
	{
		return this.lineBox.getRightPen().getOwnLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setRightBorderColor(Color rightBorderColor)
	{
		this.lineBox.getRightPen().setLineColor(rightBorderColor);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public int getRightPadding()
	{
		return this.lineBox.getRightPadding().intValue();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public Integer getOwnRightPadding()
	{
		return this.lineBox.getOwnRightPadding();
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setRightPadding(int rightPadding)
	{
		this.lineBox.setRightPadding(rightPadding);
	}

	/**
	 * @deprecated Replaced by {@link #getBox()}
	 */
	public void setRightPadding(Integer rightPadding)
	{
		this.lineBox.setRightPadding(rightPadding);
	}


	public String getLinkType()
	{
		return this.linkType;
	}


	public JRHyperlinkParameter[] getHyperlinkParameters()
	{
		return this.hyperlinkParameters;
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
		JRBaseImage clone = (JRBaseImage)super.clone();
		
		clone.lineBox = (JRLineBox)this.lineBox.clone(this);

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


	/**
	 * These fields are only for serialization backward compatibility.
	 */
	private Byte border = null;
	private Byte topBorder = null;
	private Byte leftBorder = null;
	private Byte bottomBorder = null;
	private Byte rightBorder = null;
	private Color borderColor = null;
	private Color topBorderColor = null;
	private Color leftBorderColor = null;
	private Color bottomBorderColor = null;
	private Color rightBorderColor = null;
	private Integer padding = null;
	private Integer topPadding = null;
	private Integer leftPadding = null;
	private Integer bottomPadding = null;
	private Integer rightPadding = null;
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();

		if (this.lineBox == null)
		{
			this.lineBox = new JRBaseLineBox(this);
			JRBoxUtil.setToBox(
				this.border,
				this.topBorder,
				this.leftBorder,
				this.bottomBorder,
				this.rightBorder,
				this.borderColor,
				this.topBorderColor,
				this.leftBorderColor,
				this.bottomBorderColor,
				this.rightBorderColor,
				this.padding,
				this.topPadding,
				this.leftPadding,
				this.bottomPadding,
				this.rightPadding,
				this.lineBox
				);
			this.border = null;
			this.topBorder = null;
			this.leftBorder = null;
			this.bottomBorder = null;
			this.rightBorder = null;
			this.borderColor = null;
			this.topBorderColor = null;
			this.leftBorderColor = null;
			this.bottomBorderColor = null;
			this.rightBorderColor = null;
			this.padding = null;
			this.topPadding = null;
			this.leftPadding = null;
			this.bottomPadding = null;
			this.rightPadding = null;
		}

		normalizeLinkType();
	}
}
