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
import net.sf.jasperreports.engine.JRDefaultStyleProvider;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JRHyperlinkHelper;
import net.sf.jasperreports.engine.JRImage;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.JRPrintHyperlinkParameter;
import net.sf.jasperreports.engine.JRPrintHyperlinkParameters;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.JRRenderable;
import net.sf.jasperreports.engine.util.JRBoxUtil;
import net.sf.jasperreports.engine.util.JRPenUtil;
import net.sf.jasperreports.engine.util.JRStyleResolver;
import net.sf.jasperreports.engine.util.LineBoxWrapper;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRBasePrintImage.java,v 1.1 2008/09/29 16:21:16 guehene Exp $
 */
public class JRBasePrintImage extends JRBasePrintGraphicElement implements JRPrintImage
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	/**
	 *
	 */
	protected JRRenderable renderer = null;
	protected Byte scaleImage = null;
	protected Boolean isUsingCache = Boolean.TRUE;
	protected Byte horizontalAlignment = null;
	protected Byte verticalAlignment = null;
	protected boolean isLazy = false;
	protected byte onErrorType = JRImage.ON_ERROR_TYPE_ERROR;
	protected JRLineBox lineBox = null;
	protected String anchorName = null;
	protected byte hyperlinkType = JRHyperlink.HYPERLINK_TYPE_NULL;
	private String linkType;
	protected byte hyperlinkTarget = JRHyperlink.HYPERLINK_TARGET_SELF;
	protected String hyperlinkReference = null;
	protected String hyperlinkAnchor = null;
	protected Integer hyperlinkPage = null;
	protected String hyperlinkTooltip;
	protected JRPrintHyperlinkParameters hyperlinkParameters;

	/**
	 * The bookmark level for the anchor associated with this field.
	 * @see JRAnchor#getBookmarkLevel()
	 */
	protected int bookmarkLevel = JRAnchor.NO_BOOKMARK;

	
	/**
	 *
	 */
	public JRBasePrintImage(JRDefaultStyleProvider defaultStyleProvider)
	{
		super(defaultStyleProvider);

		this.lineBox = new JRBaseLineBox(this);
	}


	/**
	 *
	 */
	public byte getMode()
	{
		return JRStyleResolver.getMode(this, JRElement.MODE_TRANSPARENT);
	}
		
	/**
	 *
	 */
	public JRRenderable getRenderer()
	{
		return this.renderer;
	}
		
	/**
	 *
	 */
	public void setRenderer(JRRenderable renderer)
	{
		this.renderer = renderer;
	}
		
	/**
	 *
	 */
	public byte getScaleImage()
	{
		return JRStyleResolver.getScaleImage(this);
	}

	/**
	 *
	 */
	public Byte getOwnScaleImage()
	{
		return this.scaleImage;
	}

	/**
	 *
	 */
	public void setScaleImage(byte scaleImage)
	{
		this.scaleImage = new Byte(scaleImage);
	}

	/**
	 *
	 */
	public void setScaleImage(Byte scaleImage)
	{
		this.scaleImage = scaleImage;
	}

	/**
	 *
	 */
	public boolean isUsingCache()
	{
		return this.isUsingCache == null ? true : this.isUsingCache.booleanValue();
	}

	/**
	 *
	 */
	public void setUsingCache(boolean isUsingCache)
	{
		this.isUsingCache = (isUsingCache ? Boolean.TRUE : Boolean.FALSE);
	}

	/**
	 *
	 */
	public byte getHorizontalAlignment()
	{
		return JRStyleResolver.getHorizontalAlignment(this);
	}
		
	/**
	 *
	 */
	public Byte getOwnHorizontalAlignment()
	{
		return this.horizontalAlignment;
	}
		
	/**
	 *
	 */
	public void setHorizontalAlignment(byte horizontalAlignment)
	{
		this.horizontalAlignment = new Byte(horizontalAlignment);
	}

	/**
	 *
	 */
	public void setHorizontalAlignment(Byte horizontalAlignment)
	{
		this.horizontalAlignment = horizontalAlignment;
	}

	/**
	 *
	 */
	public byte getVerticalAlignment()
	{
		return JRStyleResolver.getVerticalAlignment(this);
	}
		
	/**
	 *
	 */
	public Byte getOwnVerticalAlignment()
	{
		return this.verticalAlignment;
	}
		
	/**
	 *
	 */
	public void setVerticalAlignment(byte verticalAlignment)
	{
		this.verticalAlignment = new Byte(verticalAlignment);
	}

	/**
	 *
	 */
	public void setVerticalAlignment(Byte verticalAlignment)
	{
		this.verticalAlignment = verticalAlignment;
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
		this.isLazy = isLazy;
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
		this.onErrorType = onErrorType;
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
	 * @deprecated Replaced by {@link #copyBox(JRLineBox)}
	 */
	public void setBox(JRBox box)
	{
		JRBoxUtil.setBoxToLineBox(box, this.lineBox);
	}

	/**
	 *
	 */
	public void copyBox(JRLineBox lineBox)
	{
		this.lineBox = lineBox.clone(this);
	}

	/**
	 * 
	 */
	public Float getDefaultLineWidth() 
	{
		return JRPen.LINE_WIDTH_0;
	}

	/**
	 *
	 */
	public String getAnchorName()
	{
		return this.anchorName;
	}
		
	/**
	 *
	 */
	public void setAnchorName(String anchorName)
	{
		this.anchorName = anchorName;
	}
		
	/**
	 *
	 */
	public byte getHyperlinkType()
	{
		return JRHyperlinkHelper.getHyperlinkType(getLinkType());
	}
		
	/**
	 *
	 */
	public void setHyperlinkType(byte hyperlinkType)
	{
		setLinkType(JRHyperlinkHelper.getLinkType(hyperlinkType));
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
	public void setHyperlinkTarget(byte hyperlinkTarget)
	{
		this.hyperlinkTarget = hyperlinkTarget;
	}

	/**
	 *
	 */
	public String getHyperlinkReference()
	{
		return this.hyperlinkReference;
	}
		
	/**
	 *
	 */
	public void setHyperlinkReference(String hyperlinkReference)
	{
		this.hyperlinkReference = hyperlinkReference;
	}
		
	/**
	 *
	 */
	public String getHyperlinkAnchor()
	{
		return this.hyperlinkAnchor;
	}
		
	/**
	 *
	 */
	public void setHyperlinkAnchor(String hyperlinkAnchor)
	{
		this.hyperlinkAnchor = hyperlinkAnchor;
	}
		
	/**
	 *
	 */
	public Integer getHyperlinkPage()
	{
		return this.hyperlinkPage;
	}
		
	/**
	 *
	 */
	public void setHyperlinkPage(Integer hyperlinkPage)
	{
		this.hyperlinkPage = hyperlinkPage;
	}
		
	/**
	 *
	 */
	public void setHyperlinkPage(String hyperlinkPage)
	{
		this.hyperlinkPage = Integer.valueOf(hyperlinkPage);
	}


	public int getBookmarkLevel()
	{
		return this.bookmarkLevel;
	}


	public void setBookmarkLevel(int bookmarkLevel)
	{
		this.bookmarkLevel = bookmarkLevel;
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

	
	public JRPrintHyperlinkParameters getHyperlinkParameters()
	{
		return this.hyperlinkParameters;
	}

	
	public void setHyperlinkParameters(JRPrintHyperlinkParameters hyperlinkParameters)
	{
		this.hyperlinkParameters = hyperlinkParameters;
	}

	
	/**
	 * Adds a custom hyperlink parameter.
	 * 
	 * @param parameter the parameter to add
	 * @see #getHyperlinkParameters()
	 * @see JRPrintHyperlinkParameters#addParameter(JRPrintHyperlinkParameter)
	 */
	public void addHyperlinkParameter(JRPrintHyperlinkParameter parameter)
	{
		if (this.hyperlinkParameters == null)
		{
			this.hyperlinkParameters = new JRPrintHyperlinkParameters();
		}
		this.hyperlinkParameters.addParameter(parameter);
	}

	
	public String getLinkType()
	{
		return this.linkType;
	}

	
	public void setLinkType(String linkType)
	{
		this.linkType = linkType;
	}
	
	
	protected void normalizeLinkType()
	{
		if (this.linkType == null)
		{
			 this.linkType = JRHyperlinkHelper.getLinkType(this.hyperlinkType);
		}
		this.hyperlinkType = JRHyperlink.HYPERLINK_TYPE_NULL;
	}

	
	public String getHyperlinkTooltip()
	{
		return this.hyperlinkTooltip;
	}

	
	public void setHyperlinkTooltip(String hyperlinkTooltip)
	{
		this.hyperlinkTooltip = hyperlinkTooltip;
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
