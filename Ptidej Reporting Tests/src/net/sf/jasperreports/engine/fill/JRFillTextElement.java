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
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRBox;
import net.sf.jasperreports.engine.JRCommonText;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JRReportFont;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JRTextElement;
import net.sf.jasperreports.engine.util.JRFontUtil;
import net.sf.jasperreports.engine.util.JRPenUtil;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.util.JRSingletonCache;
import net.sf.jasperreports.engine.util.JRStringUtil;
import net.sf.jasperreports.engine.util.JRStyleResolver;
import net.sf.jasperreports.engine.util.JRStyledText;
import net.sf.jasperreports.engine.util.JRTextMeasurerUtil;
import net.sf.jasperreports.engine.util.LineBoxWrapper;
import net.sf.jasperreports.engine.util.MarkupProcessor;
import net.sf.jasperreports.engine.util.MarkupProcessorFactory;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRFillTextElement.java,v 1.1 2008/09/29 16:20:01 guehene Exp $
 */
public abstract class JRFillTextElement extends JRFillElement implements JRTextElement
{

	/**
	 *
	 */
	private static final JRSingletonCache markupProcessorFactoryCache = new JRSingletonCache(MarkupProcessorFactory.class);
	private static final Map markupProcessors = new HashMap();

	/**
	 *
	 */
	private boolean isLeftToRight = true;
	private JRTextMeasurer textMeasurer = null;
	private float lineSpacingFactor = 0;
	private float leadingOffset = 0;
	private float textHeight = 0;
	private int textStart = 0;
	private int textEnd = 0;
	private String textTruncateSuffix;
	private String rawText = null;
	private JRStyledText styledText = null;
	private Map styledTextAttributesMap = new HashMap();
	
	protected final JRReportFont reportFont;
	protected final JRLineBox lineBox;

	/**
	 *
	 */
	protected JRFillTextElement(
		JRBaseFiller filler,
		JRTextElement textElement, 
		JRFillObjectFactory factory
		)
	{
		super(filler, textElement, factory);

		this.reportFont = factory.getReportFont(textElement.getReportFont());
		
		this.lineBox = textElement.getLineBox().clone(this);
	}
	

	protected JRFillTextElement(JRFillTextElement textElement, JRFillCloneFactory factory)
	{
		super(textElement, factory);
		
		this.reportFont = textElement.reportFont;
		
		this.lineBox = textElement.getLineBox().clone(this);
	}


	private void createTextMeasurer()
	{
		this.textMeasurer = JRTextMeasurerUtil.createTextMeasurer(this);
	}

	protected void ensureTextMeasurer()
	{
		if (this.textMeasurer == null)
		{
			createTextMeasurer();
		}
	}


	/**
	 *
	 */
	public byte getMode()
	{
		return JRStyleResolver.getMode(this, JRElement.MODE_TRANSPARENT);
	}

	/**
	 * @deprecated Replaced by {@link #getHorizontalAlignment()}.
	 */
	public byte getTextAlignment()
	{
		return JRStyleResolver.getHorizontalAlignment(this);
	}

	/**
	 * @deprecated Replaced by {@link #setHorizontalAlignment(byte)}.
	 */
	public void setTextAlignment(byte horizontalAlignment)
	{
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
		return ((JRTextElement)this.parent).getOwnHorizontalAlignment();
	}

	/**
	 *
	 */
	public void setHorizontalAlignment(byte horizontalAlignment)
	{
	}
		
	/**
	 *
	 */
	public void setHorizontalAlignment(Byte horizontalAlignment)
	{
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
		return ((JRTextElement)this.parent).getOwnVerticalAlignment();
	}

	/**
	 *
	 */
	public void setVerticalAlignment(byte verticalAlignment)
	{
	}
		
	/**
	 *
	 */
	public void setVerticalAlignment(Byte verticalAlignment)
	{
	}
		
	/**
	 *
	 */
	public byte getRotation()
	{
		return JRStyleResolver.getRotation(this);
	}
		
	public Byte getOwnRotation()
	{
		return ((JRTextElement)this.parent).getOwnRotation();
	}

	/**
	 *
	 */
	public void setRotation(byte rotation)
	{
	}
		
	/**
	 *
	 */
	public void setRotation(Byte rotation)
	{
	}
		
	/**
	 *
	 */
	public byte getLineSpacing()
	{
		return JRStyleResolver.getLineSpacing(this);
	}
		
	public Byte getOwnLineSpacing()
	{
		return ((JRTextElement)this.parent).getOwnLineSpacing();
	}

	/**
	 *
	 */
	public void setLineSpacing(byte lineSpacing)
	{
	}
		
	/**
	 *
	 */
	public void setLineSpacing(Byte lineSpacing)
	{
	}
		
	/**
	 * @deprecated Replaced by {@link #getMarkup()}
	 */
	public boolean isStyledText()
	{
		return JRCommonText.MARKUP_STYLED_TEXT.equals(getMarkup());
	}
		
	/**
	 * @deprecated Replaced by {@link #getOwnMarkup()}
	 */
	public Boolean isOwnStyledText()
	{
		String mkp = getOwnMarkup();
		return JRCommonText.MARKUP_STYLED_TEXT.equals(mkp) ? Boolean.TRUE : (mkp == null ? null : Boolean.FALSE);
	}

	/**
	 * @deprecated Replaced by {@link #setMarkup(String)}
	 */
	public void setStyledText(boolean isStyledText)
	{
	}
		
	/**
	 * @deprecated Replaced by {@link #setMarkup(String)}
	 */
	public void setStyledText(Boolean isStyledText)
	{
	}
		
	/**
	 *
	 */
	public String getMarkup()
	{
		return JRStyleResolver.getMarkup(this);
	}
		
	/**
	 *
	 */
	public String getOwnMarkup()
	{
		return ((JRTextElement)this.parent).getOwnMarkup();
	}

	/**
	 *
	 */
	public void setMarkup(String markup)
	{
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
	public byte getBorder()
	{
		return JRPenUtil.getPenFromLinePen(getLineBox().getPen());
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public Byte getOwnBorder()
	{
		return JRPenUtil.getOwnPenFromLinePen(getLineBox().getPen());
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setBorder(byte border)
	{
		JRPenUtil.setLinePenFromPen(border, getLineBox().getPen());
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setBorder(Byte border)
	{
		JRPenUtil.setLinePenFromPen(border, getLineBox().getPen());
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public Color getBorderColor()
	{
		return getLineBox().getPen().getLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public Color getOwnBorderColor()
	{
		return getLineBox().getPen().getOwnLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setBorderColor(Color borderColor)
	{
		getLineBox().getPen().setLineColor(borderColor);
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public int getPadding()
	{
		return getLineBox().getPadding().intValue();
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public Integer getOwnPadding()
	{
		return getLineBox().getOwnPadding();
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setPadding(int padding)
	{
		getLineBox().setPadding(padding);
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setPadding(Integer padding)
	{
		getLineBox().setPadding(padding);
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public byte getTopBorder()
	{
		return JRPenUtil.getPenFromLinePen(getLineBox().getTopPen());
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public Byte getOwnTopBorder()
	{
		return JRPenUtil.getOwnPenFromLinePen(getLineBox().getTopPen());
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setTopBorder(byte topBorder)
	{
		JRPenUtil.setLinePenFromPen(topBorder, getLineBox().getTopPen());
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setTopBorder(Byte topBorder)
	{
		JRPenUtil.setLinePenFromPen(topBorder, getLineBox().getTopPen());
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public Color getTopBorderColor()
	{
		return getLineBox().getTopPen().getLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public Color getOwnTopBorderColor()
	{
		return getLineBox().getTopPen().getOwnLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setTopBorderColor(Color topBorderColor)
	{
		getLineBox().getTopPen().setLineColor(topBorderColor);
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public int getTopPadding()
	{
		return getLineBox().getTopPadding().intValue();
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public Integer getOwnTopPadding()
	{
		return getLineBox().getOwnTopPadding();
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setTopPadding(int topPadding)
	{
		getLineBox().setTopPadding(topPadding);
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setTopPadding(Integer topPadding)
	{
		getLineBox().setTopPadding(topPadding);
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public byte getLeftBorder()
	{
		return JRPenUtil.getPenFromLinePen(getLineBox().getLeftPen());
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public Byte getOwnLeftBorder()
	{
		return JRPenUtil.getOwnPenFromLinePen(getLineBox().getLeftPen());
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setLeftBorder(byte leftBorder)
	{
		JRPenUtil.setLinePenFromPen(leftBorder, getLineBox().getLeftPen());
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setLeftBorder(Byte leftBorder)
	{
		JRPenUtil.setLinePenFromPen(leftBorder, getLineBox().getLeftPen());
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public Color getLeftBorderColor()
	{
		return getLineBox().getLeftPen().getLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public Color getOwnLeftBorderColor()
	{
		return getLineBox().getLeftPen().getOwnLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setLeftBorderColor(Color leftBorderColor)
	{
		getLineBox().getLeftPen().setLineColor(leftBorderColor);
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public int getLeftPadding()
	{
		return getLineBox().getLeftPadding().intValue();
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public Integer getOwnLeftPadding()
	{
		return getLineBox().getOwnLeftPadding();
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setLeftPadding(int leftPadding)
	{
		getLineBox().setLeftPadding(leftPadding);
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setLeftPadding(Integer leftPadding)
	{
		getLineBox().setLeftPadding(leftPadding);
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public byte getBottomBorder()
	{
		return JRPenUtil.getPenFromLinePen(getLineBox().getBottomPen());
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public Byte getOwnBottomBorder()
	{
		return JRPenUtil.getOwnPenFromLinePen(getLineBox().getBottomPen());
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setBottomBorder(byte bottomBorder)
	{
		JRPenUtil.setLinePenFromPen(bottomBorder, getLineBox().getBottomPen());
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setBottomBorder(Byte bottomBorder)
	{
		JRPenUtil.setLinePenFromPen(bottomBorder, getLineBox().getBottomPen());
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public Color getBottomBorderColor()
	{
		return getLineBox().getBottomPen().getLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public Color getOwnBottomBorderColor()
	{
		return getLineBox().getBottomPen().getOwnLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setBottomBorderColor(Color bottomBorderColor)
	{
		getLineBox().getBottomPen().setLineColor(bottomBorderColor);
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public int getBottomPadding()
	{
		return getLineBox().getBottomPadding().intValue();
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public Integer getOwnBottomPadding()
	{
		return getLineBox().getOwnBottomPadding();
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setBottomPadding(int bottomPadding)
	{
		getLineBox().setBottomPadding(bottomPadding);
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setBottomPadding(Integer bottomPadding)
	{
		getLineBox().setBottomPadding(bottomPadding);
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public byte getRightBorder()
	{
		return JRPenUtil.getPenFromLinePen(getLineBox().getRightPen());
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public Byte getOwnRightBorder()
	{
		return JRPenUtil.getOwnPenFromLinePen(getLineBox().getRightPen());
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setRightBorder(byte rightBorder)
	{
		JRPenUtil.setLinePenFromPen(rightBorder, getLineBox().getRightPen());
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setRightBorder(Byte rightBorder)
	{
		JRPenUtil.setLinePenFromPen(rightBorder, getLineBox().getRightPen());
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public Color getRightBorderColor()
	{
		return getLineBox().getRightPen().getLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public Color getOwnRightBorderColor()
	{
		return getLineBox().getRightPen().getOwnLineColor();
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setRightBorderColor(Color rightBorderColor)
	{
		getLineBox().getRightPen().setLineColor(rightBorderColor);
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public int getRightPadding()
	{
		return getLineBox().getRightPadding().intValue();
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public Integer getOwnRightPadding()
	{
		return getLineBox().getOwnRightPadding();
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setRightPadding(int rightPadding)
	{
		getLineBox().setRightPadding(rightPadding);
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public void setRightPadding(Integer rightPadding)
	{
		getLineBox().setRightPadding(rightPadding);
	}

	/**
	 * @deprecated
	 */
	public JRFont getFont()
	{
		return this;
	}

	
	/**
	 *
	 */
	protected Map getStyledTextAttributes()
	{
		JRStyle style = getStyle();
		Map styledTextAttributes = (Map)this.styledTextAttributesMap.get(style);
		if (styledTextAttributes == null)
		{
			styledTextAttributes = new HashMap(); 
			JRFontUtil.setAttributes(styledTextAttributes, this);
			styledTextAttributes.put(TextAttribute.FOREGROUND, getForecolor());
			if (getMode() == JRElement.MODE_OPAQUE)
			{
				styledTextAttributes.put(TextAttribute.BACKGROUND, getBackcolor());
			}
			this.styledTextAttributesMap.put(style, styledTextAttributes);
		}
		
		return styledTextAttributes;
	}

	/**
	 *
	 */
	protected float getLineSpacingFactor()
	{
		return this.lineSpacingFactor;
	}
		
	/**
	 *
	 */
	protected void setLineSpacingFactor(float lineSpacingFactor)
	{
		this.lineSpacingFactor = lineSpacingFactor;
	}

	/**
	 *
	 */
	protected float getLeadingOffset()
	{
		return this.leadingOffset;
	}
		
	/**
	 *
	 */
	protected void setLeadingOffset(float leadingOffset)
	{
		this.leadingOffset = leadingOffset;
	}

	/**
	 *
	 */
	protected byte getRunDirection()
	{
		return this.isLeftToRight ? JRPrintText.RUN_DIRECTION_LTR : JRPrintText.RUN_DIRECTION_RTL;
	}
		
	/**
	 *
	 */
	protected float getTextHeight()
	{
		return this.textHeight;
	}
		
	/**
	 *
	 */
	protected void setTextHeight(float textHeight)
	{
		this.textHeight = textHeight;
	}

	/**
	 *
	 */
	protected int getTextStart()
	{
		return this.textStart;
	}
		
	/**
	 *
	 */
	protected void setTextStart(int textStart)
	{
		this.textStart = textStart;
	}

	/**
	 *
	 */
	protected int getTextEnd()
	{
		return this.textEnd;
	}
		
	/**
	 *
	 */
	protected void setTextEnd(int textEnd)
	{
		this.textEnd = textEnd;
	}

	/**
	 *
	 */
	protected String getRawText()
	{
		return this.rawText;
	}

	/**
	 *
	 */
	protected void setRawText(String rawText)
	{
		this.rawText = rawText;
		this.styledText = null;
	}


	/**
	 *
	 */
	protected void reset()
	{
		super.reset();
		
		this.isLeftToRight = true;
		this.lineSpacingFactor = 0;
		this.leadingOffset = 0;
		this.textHeight = 0;
	}


	/**
	 *
	 */
	protected void rewind()
	{
		this.textStart = 0;
		this.textEnd = 0;
	}


	/**
	 *
	 */
	protected JRStyledText getStyledText()
	{
		if (this.styledText == null)
		{
			String text = getRawText();
			if (text != null)
			{
				this.styledText = this.filler.getStyledTextParser().getStyledText(
						getStyledTextAttributes(), 
						text, 
						!JRCommonText.MARKUP_NONE.equals(getMarkup()));
			}
		}
		
		return this.styledText;
	}

	/**
	 *
	 */
	public String getText()
	{
		JRStyledText tmpStyledText = getStyledText();

		if (tmpStyledText == null)
		{
			return null;
		}

		return tmpStyledText.getText();
	}
	

	/**
	 *
	 */
	protected void chopTextElement(
		int availableStretchHeight
		)
	{
		ensureTextMeasurer();
		
		JRStyledText tmpStyledText = getStyledText();

		if (tmpStyledText == null)
		{
			return;
		}

		if (getTextEnd() == tmpStyledText.getText().length())
		{
			return;
		}

		/*   */
		JRMeasuredText measuredText = this.textMeasurer.measure(
			tmpStyledText,
			getTextEnd(),
			availableStretchHeight,
			canOverflow()
			);
		
		this.isLeftToRight = measuredText.isLeftToRight();
		setTextHeight(measuredText.getTextHeight());
		if (getRotation() == ROTATION_NONE)
		{
			setStretchHeight((int)getTextHeight() + getLineBox().getTopPadding().intValue() + getLineBox().getBottomPadding().intValue());
		}
		else
		{
			setStretchHeight(getHeight());
		}
		setTextStart(getTextEnd());
		setTextEnd(measuredText.getTextOffset());
		setTextTruncateSuffix(measuredText.getTextSuffix());
		setLineSpacingFactor(measuredText.getLineSpacingFactor());
		setLeadingOffset(measuredText.getLeadingOffset());
	}
	
	protected abstract boolean canOverflow();


	/**
	 *
	 */
	public JRReportFont getReportFont()
	{
		return this.reportFont;
	}

	public void setReportFont(JRReportFont reportFont)
	{
	}
	
	/**
	 *
	 */
	public String getFontName()
	{
		return JRStyleResolver.getFontName(this);
	}

	/**
	 *
	 */
	public String getOwnFontName()
	{
		return ((JRFont)this.parent).getOwnFontName();
	}

	/**
	 *
	 */
	public void setFontName(String fontName)
	{
	}


	/**
	 *
	 */
	public boolean isBold()
	{
		return JRStyleResolver.isBold(this);
	}

	/**
	 *
	 */
	public Boolean isOwnBold()
	{
		return ((JRFont)this.parent).isOwnBold();
	}

	/**
	 *
	 */
	public void setBold(boolean isBold)
	{
	}

	/**
	 * Alternative setBold method which allows also to reset
	 * the "own" isBold property.
	 */
	public void setBold(Boolean isBold)
	{
	}


	/**
	 *
	 */
	public boolean isItalic()
	{
		return JRStyleResolver.isItalic(this);
	}

	/**
	 *
	 */
	public Boolean isOwnItalic()
	{
		return ((JRFont)this.parent).isOwnItalic();
	}

	/**
	 *
	 */
	public void setItalic(boolean isItalic)
	{
	}

	/**
	 * Alternative setItalic method which allows also to reset
	 * the "own" isItalic property.
	 */
	public void setItalic(Boolean isItalic)
	{
	}

	/**
	 *
	 */
	public boolean isUnderline()
	{
		return JRStyleResolver.isUnderline(this);
	}

	/**
	 *
	 */
	public Boolean isOwnUnderline()
	{
		return ((JRFont)this.parent).isOwnUnderline();
	}

	/**
	 *
	 */
	public void setUnderline(boolean isUnderline)
	{
	}

	/**
	 * Alternative setUnderline method which allows also to reset
	 * the "own" isUnderline property.
	 */
	public void setUnderline(Boolean isUnderline)
	{
	}

	/**
	 *
	 */
	public boolean isStrikeThrough()
	{
		return JRStyleResolver.isStrikeThrough(this);
	}

	/**
	 *
	 */
	public Boolean isOwnStrikeThrough()
	{
		return ((JRFont)this.parent).isOwnStrikeThrough();
	}

	/**
	 *
	 */
	public void setStrikeThrough(boolean isStrikeThrough)
	{
	}

	/**
	 * Alternative setStrikeThrough method which allows also to reset
	 * the "own" isStrikeThrough property.
	 */
	public void setStrikeThrough(Boolean isStrikeThrough)
	{
	}

	/**
	 *
	 */
	public int getFontSize()
	{
		return JRStyleResolver.getFontSize(this);
	}

	/**
	 *
	 */
	public Integer getOwnFontSize()
	{
		return ((JRFont)this.parent).getOwnFontSize();
	}

	/**
	 *
	 */
	public void setFontSize(int size)
	{
	}

	/**
	 * Alternative setSize method which allows also to reset
	 * the "own" size property.
	 */
	public void setFontSize(Integer size)
	{
	}

	/**
	 * @deprecated Replaced by {@link #getFontSize()}.
	 */
	public int getSize()
	{
		return getFontSize();
	}

	/**
	 * @deprecated Replaced by {@link #getOwnFontSize()}.
	 */
	public Integer getOwnSize()
	{
		return getOwnFontSize();
	}

	/**
	 * @deprecated Replaced by {@link #setFontSize(int)}.
	 */
	public void setSize(int size)
	{
	}

	/**
	 * @deprecated Replaced by {@link #setFontSize(Integer)}.
	 */
	public void setSize(Integer size)
	{
	}

	/**
	 *
	 */
	public String getPdfFontName()
	{
		return JRStyleResolver.getPdfFontName(this);
	}

	/**
	 *
	 */
	public String getOwnPdfFontName()
	{
		return ((JRFont)this.parent).getOwnPdfFontName();
	}

	/**
	 *
	 */
	public void setPdfFontName(String pdfFontName)
	{
	}


	/**
	 *
	 */
	public String getPdfEncoding()
	{
		return JRStyleResolver.getPdfEncoding(this);
	}

	/**
	 *
	 */
	public String getOwnPdfEncoding()
	{
		return ((JRFont)this.parent).getOwnPdfEncoding();
	}

	/**
	 *
	 */
	public void setPdfEncoding(String pdfEncoding)
	{
	}


	/**
	 *
	 */
	public boolean isPdfEmbedded()
	{
		return JRStyleResolver.isPdfEmbedded(this);
	}

	/**
	 *
	 */
	public Boolean isOwnPdfEmbedded()
	{
		return ((JRFont)this.parent).isOwnPdfEmbedded();
	}

	/**
	 *
	 */
	public void setPdfEmbedded(boolean isPdfEmbedded)
	{
		setPdfEmbedded(isPdfEmbedded ? Boolean.TRUE : Boolean.FALSE);
	}

	/**
	 * Alternative setPdfEmbedded method which allows also to reset
	 * the "own" isPdfEmbedded property.
	 */
	public void setPdfEmbedded(Boolean isPdfEmbedded)
	{
	}

	
	/**
	 * 
	 */
	public Color getDefaultLineColor() 
	{
		return getForecolor();
	}

	
	/**
	 *
	 */
	public void setHeight(int height)
	{
		super.setHeight(height);
		
		createTextMeasurer();
	}


	public void setWidth(int width)
	{
		super.setWidth(width);
		
		createTextMeasurer();
	}

	protected String processMarkupText(String text)
	{
		text = JRStringUtil.replaceCRwithLF(text);
		
		if (text != null)
		{
			String markup = getMarkup();
			if (
				!JRCommonText.MARKUP_NONE.equals(markup)
				&& !JRCommonText.MARKUP_STYLED_TEXT.equals(markup)
				)
			{
				text = getMarkupProcessor(markup).convert(text);
			}
		}
		
		return text;
	}

	protected static MarkupProcessor getMarkupProcessor(String markup)
	{
		MarkupProcessor markupProcessor = (MarkupProcessor)markupProcessors.get(markup);
		
		if (markupProcessor == null)
		{
			String factoryClass = JRProperties.getProperty(MarkupProcessorFactory.PROPERTY_MARKUP_PROCESSOR_FACTORY_PREFIX + markup);
			if (factoryClass == null)
			{
				throw new JRRuntimeException("No markup processor factory specifyed for '" + markup + "' markup.");
			}

			MarkupProcessorFactory factory = null;
			try
			{
				factory = (MarkupProcessorFactory) markupProcessorFactoryCache.getCachedInstance(factoryClass);
			}
			catch (JRException e)
			{
				throw new JRRuntimeException(e);
			}
			
			markupProcessor = factory.createMarkupProcessor();
			markupProcessors.put(markup, markupProcessor);
		}
		
		return markupProcessor;
	}

	protected void setPrintText(JRPrintText printText)
	{
		int startIndex = getTextStart();
		int endIndex = getTextEnd();
		JRStyledText fullStyledText = getStyledText();
		String fullText = fullStyledText.getText();
		
		boolean keepAllText = !canOverflow() 
				&& JRProperties.getBooleanProperty(this, JRTextElement.PROPERTY_PRINT_KEEP_FULL_TEXT, false);
		if (keepAllText)
		{
			//assert getTextStart() == 0
			if (startIndex != 0)
			{
				throw new JRRuntimeException("Text start index != 0 on keep all text.");
			}
			
			if (!JRCommonText.MARKUP_NONE.equals(getMarkup()))
			{
				//rewrite as styled text
				String styledText = this.filler.getStyledTextParser().write(
						fullStyledText);
				printText.setText(styledText);
			}
			else
			{
				printText.setText(fullText);
			}
			
			if (endIndex < fullText.length())
			{
				printText.setTextTruncateIndex(Integer.valueOf(endIndex));
			}
		}
		else
		{
			String printedText;
			if (!JRCommonText.MARKUP_NONE.equals(getMarkup()))
			{
				printedText = this.filler.getStyledTextParser().write(
						fullStyledText, 
						startIndex, endIndex);
			}
			else
			{
				printedText = fullText.substring(startIndex, endIndex);
			}
			printText.setText(printedText);
		}
		
		printText.setTextTruncateSuffix(getTextTruncateSuffix());
	}

	protected String getTextTruncateSuffix()
	{
		return this.textTruncateSuffix;
	}

	protected void setTextTruncateSuffix(String textTruncateSuffix)
	{
		this.textTruncateSuffix = textTruncateSuffix;
	}

}
