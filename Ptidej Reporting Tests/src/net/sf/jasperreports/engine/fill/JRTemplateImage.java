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

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;

import net.sf.jasperreports.engine.JRAlignment;
import net.sf.jasperreports.engine.JRBox;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRCommonImage;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRDefaultStyleProvider;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRGraphicElement;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JRHyperlinkHelper;
import net.sf.jasperreports.engine.JRImage;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JROrigin;
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.base.JRBaseLineBox;
import net.sf.jasperreports.engine.base.JRBasePen;
import net.sf.jasperreports.engine.util.JRBoxUtil;
import net.sf.jasperreports.engine.util.JRPenUtil;
import net.sf.jasperreports.engine.util.JRStyleResolver;
import net.sf.jasperreports.engine.util.LineBoxWrapper;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRTemplateImage.java,v 1.1 2008/09/29 16:20:00 guehene Exp $
 */
public class JRTemplateImage extends JRTemplateGraphicElement implements JRAlignment, JRBox, JRCommonImage
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	/**
	 *
	 */
	private Byte scaleImage = null;
	private Boolean isUsingCache = Boolean.TRUE;
	private Byte horizontalAlignment = null;
	private Byte verticalAlignment = null;
	protected boolean isLazy = false;
	private byte onErrorType = JRImage.ON_ERROR_TYPE_ERROR;
	private byte hyperlinkType = JRHyperlink.HYPERLINK_TYPE_NULL;
	private String linkType;
	private byte hyperlinkTarget = JRHyperlink.HYPERLINK_TARGET_SELF;

	/**
	 *
	 */
	private JRLineBox lineBox = null;
	

	/**
	 *
	 */
	protected JRTemplateImage(JROrigin origin, JRDefaultStyleProvider defaultStyleProvider, JRImage image)
	{
		super(origin, defaultStyleProvider);
		
		setImage(image);
	}


	/**
	 *
	 */
	protected JRTemplateImage(JROrigin origin, JRDefaultStyleProvider defaultStyleProvider, JRChart chart)
	{
		super(origin, defaultStyleProvider);
		
		setChart(chart);
	}

	/**
	 *
	 */
	protected void setImage(JRImage image)
	{
		super.setGraphicElement(image);
		
		this.lineBox = image.getLineBox().clone(this);

		setScaleImage(image.getScaleImage());
		setUsingCache(image.isUsingCache());
		setHorizontalAlignment(image.getHorizontalAlignment());
		setVerticalAlignment(image.getVerticalAlignment());
		setLazy(image.isLazy());
		setOnErrorType(image.getOnErrorType());
		setLinkType(image.getLinkType());
		setHyperlinkTarget(image.getHyperlinkTarget());
	}

	/**
	 *
	 */
	protected void setChart(JRChart chart)
	{
		super.setElement(chart);
		
		this.linePen = new JRBasePen(this);
		
		getLinePen().setLineWidth(0f);
		setFill(JRGraphicElement.FILL_SOLID);
		
		this.lineBox = chart.getLineBox().clone(this);

		setLinkType(chart.getLinkType());
		setHyperlinkTarget(chart.getHyperlinkTarget());
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
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setBox(JRBox box)
	{
		JRBoxUtil.setBoxToLineBox(box, this.lineBox);
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
	protected void setScaleImage(byte scaleImage)
	{
		this.scaleImage = new Byte(scaleImage);
	}

	/**
	 *
	 */
	protected void setScaleImage(Byte scaleImage)
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
	 * Retrieves the hyperlink type for the element.
	 * <p>
	 * The actual hyperlink type is determined by {@link #getLinkType() getLinkType()}.
	 * This method can is used to determine whether the hyperlink type is one of the
	 * built-in types or a custom type. 
	 * When hyperlink is of custom type, {@link JRHyperlink#HYPERLINK_TYPE_CUSTOM HYPERLINK_TYPE_CUSTOM} is returned.
	 * </p>
	 * @return one of the hyperlink type constants
	 * @see #getLinkType()
	 */
	public byte getHyperlinkType()
	{
		return JRHyperlinkHelper.getHyperlinkType(getLinkType());
	}

	
	/**
	 * Sets the link type as a built-in hyperlink type.
	 * 
	 * @param hyperlinkType the built-in hyperlink type
	 * @see #getLinkType()
	 */
	protected void setHyperlinkType(byte hyperlinkType)
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
	protected void setHyperlinkTarget(byte hyperlinkTarget)
	{
		this.hyperlinkTarget = hyperlinkTarget;
	}

	
	/**
	 * Returns the hyperlink type.
	 * <p>
	 * The type can be one of the built-in types
	 * (Reference, LocalAnchor, LocalPage, RemoteAnchor, RemotePage),
	 * or can be an arbitrary type.
	 * </p>
	 * @return the hyperlink type
	 */
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
	 * @param linkType the hyperlink type
	 */
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
	private Integer padding;
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
