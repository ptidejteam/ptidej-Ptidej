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
package net.sf.jasperreports.engine.export;

import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.StringTokenizer;

import net.sf.jasperreports.engine.JRAlignment;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.util.JRStyledText;
import net.sf.jasperreports.engine.util.MaxFontSizeFinder;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: TextRenderer.java,v 1.1 2008/09/29 16:20:46 guehene Exp $
 */
public class TextRenderer
{
	public static final FontRenderContext LINE_BREAK_FONT_RENDER_CONTEXT = new FontRenderContext(null, true, true);

	private Graphics2D grx = null;
	private int x = 0;
	private int y = 0;
	private int topPadding = 0;
	private int leftPadding = 0;
	private float formatWidth = 0;
	private float verticalOffset = 0;
	private float lineSpacingFactor = 0;
	private float leadingOffset = 0;
	private float textHeight = 0;
	private float drawPosY = 0;
	private float drawPosX = 0;
	private boolean isMaxHeightReached = false;
	private byte horizontalAlignment = 0;
	private int fontSize = 0;
	
	/**
	 * 
	 */
	private MaxFontSizeFinder maxFontSizeFinder = null;
	
	/**
	 * 
	 */
	private boolean isMinimizePrinterJobSize = true;

	
	/**
	 * 
	 */
	public static TextRenderer getInstance()
	{
		return new TextRenderer(JRProperties.getBooleanProperty(JRGraphics2DExporter.MINIMIZE_PRINTER_JOB_SIZE));
	}
	
	
	/**
	 * 
	 */
	public TextRenderer(boolean isMinimizePrinterJobSize)
	{
		this.isMinimizePrinterJobSize = isMinimizePrinterJobSize;
	}
	
	
	/**
	 * 
	 */
	public void render(
		Graphics2D initGrx,
		int initX,
		int initY,
		int initWidth,
		int initHeight,
		int initTopPadding,
		int initLeftPadding,
		int initBottomPadding,
		int initRightPadding,
		float initTextHeight,
		byte initHorizontalAlignment,
		byte initVerticalAlignment,
		float initLineSpacingFactor,
		float initLeadingOffset,
		int initFontSize,
		boolean isStyledText,
		JRStyledText styledText,
		String allText
		)
	{
		/*   */
		initialize(
			initGrx, 
			initX, 
			initY, 
			initWidth, 
			initHeight, 
			initTopPadding,
			initLeftPadding,
			initBottomPadding,
			initRightPadding,
			initTextHeight, 
			initHorizontalAlignment, 
			initVerticalAlignment, 
			initLineSpacingFactor,
			initLeadingOffset,
			initFontSize,
			isStyledText
			);
		
		AttributedCharacterIterator allParagraphs = styledText.getAwtAttributedString().getIterator();

		int tokenPosition = 0;
		int lastParagraphStart = 0;
		String lastParagraphText = null;

		StringTokenizer tkzer = new StringTokenizer(allText, "\n", true);

		while(tkzer.hasMoreTokens() && !this.isMaxHeightReached) 
		{
			String token = tkzer.nextToken();

			if ("\n".equals(token))
			{
				renderParagraph(allParagraphs, lastParagraphStart, lastParagraphText);

				lastParagraphStart = tokenPosition;
				lastParagraphText = null;
			}
			else
			{
				lastParagraphStart = tokenPosition;
				lastParagraphText = token;
			}

			tokenPosition += token.length();
		}

		if (!this.isMaxHeightReached && lastParagraphStart < allText.length())
		{
			renderParagraph(allParagraphs, lastParagraphStart, lastParagraphText);
		}
	}


	/**
	 * 
	 */
	private void initialize(
		Graphics2D initGrx,
		int initX,
		int initY,
		int initWidth,
		int initHeight,
		int initTopPadding,
		int initLeftPadding,
		int initBottomPadding,
		int initRightPadding,
		float initTextHeight,
		byte initHorizontalAlignment,
		byte initVerticalAlignment,
		float initLineSpacingFactor,
		float initLeadingOffset,
		int initFontSize,
		boolean isStyledText
		)
	{
		this.grx = initGrx;
		
		this.horizontalAlignment = initHorizontalAlignment;

		this.verticalOffset = 0f;
		switch (initVerticalAlignment)
		{
			case JRAlignment.VERTICAL_ALIGN_TOP :
			{
				this.verticalOffset = 0f;
				break;
			}
			case JRAlignment.VERTICAL_ALIGN_MIDDLE :
			{
				this.verticalOffset = (initHeight - initTopPadding - initBottomPadding - initTextHeight) / 2f;
				break;
			}
			case JRAlignment.VERTICAL_ALIGN_BOTTOM :
			{
				this.verticalOffset = initHeight - initTopPadding - initBottomPadding - initTextHeight;
				break;
			}
			default :
			{
				this.verticalOffset = 0f;
			}
		}

		this.lineSpacingFactor = initLineSpacingFactor;
		this.leadingOffset = initLeadingOffset;

		this.x = initX;
		this.y = initY;
		this.topPadding = initTopPadding;
		this.leftPadding = initLeftPadding;
		this.formatWidth = initWidth - initLeftPadding - initRightPadding;
		this.formatWidth = this.formatWidth < 0 ? 0 : this.formatWidth;
		this.textHeight = initTextHeight;

		this.drawPosY = 0;
		this.drawPosX = 0;
	
		this.isMaxHeightReached = false;
		
		this.fontSize = initFontSize;
		this.maxFontSizeFinder = MaxFontSizeFinder.getInstance(isStyledText);
	}
	
	/**
	 * 
	 */
	private void renderParagraph(
		AttributedCharacterIterator allParagraphs,
		int lastParagraphStart,
		String lastParagraphText
		)
	{
		AttributedCharacterIterator paragraph = null;
		
		if (lastParagraphText == null)
		{
			paragraph = 
				new AttributedString(
					" ",
					new AttributedString(
						allParagraphs, 
						lastParagraphStart, 
						lastParagraphStart + 1
						).getIterator().getAttributes()
					).getIterator();
		}
		else
		{
			paragraph = 
				new AttributedString(
					allParagraphs, 
					lastParagraphStart, 
					lastParagraphStart + lastParagraphText.length()
					).getIterator();
		}

		LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(paragraph, LINE_BREAK_FONT_RENDER_CONTEXT);//grx.getFontRenderContext()
	
		while (lineMeasurer.getPosition() < paragraph.getEndIndex() && !this.isMaxHeightReached)
		{
			//eugene fix - start
			int startIndex = lineMeasurer.getPosition();
			//eugene fix - end

			TextLayout layout = lineMeasurer.nextLayout(this.formatWidth);

			if (this.isMinimizePrinterJobSize)
			{
				//eugene fix - start
				AttributedString tmpText = 
					new AttributedString(
						paragraph, 
						startIndex, 
						startIndex + layout.getCharacterCount()
						);
				layout = new TextLayout(tmpText.getIterator(), this.grx.getFontRenderContext());
				//eugene fix - end
			}

			float lineHeight = this.lineSpacingFactor * 
				this.maxFontSizeFinder.findMaxFontSize(
					new AttributedString(
						paragraph, 
						startIndex, 
						startIndex + layout.getCharacterCount()
						).getIterator(),
					this.fontSize
					);

			if (this.drawPosY + lineHeight <= this.textHeight)
			{
				this.drawPosY += lineHeight;
				
				switch (this.horizontalAlignment)
				{
					case JRAlignment.HORIZONTAL_ALIGN_JUSTIFIED :
					{
						if (layout.isLeftToRight())
						{
							this.drawPosX = 0;
						}
						else
						{
							this.drawPosX = this.formatWidth - layout.getAdvance();
						}
						if (lineMeasurer.getPosition() < paragraph.getEndIndex())
						{
							layout = layout.getJustifiedLayout(this.formatWidth);
						}

						break;
					}
					case JRAlignment.HORIZONTAL_ALIGN_RIGHT :
					{
						this.drawPosX = this.formatWidth - layout.getAdvance();
						break;
					}
					case JRAlignment.HORIZONTAL_ALIGN_CENTER :
					{
						this.drawPosX = (this.formatWidth - layout.getAdvance()) / 2;
						break;
					}
					case JRAlignment.HORIZONTAL_ALIGN_LEFT :
					default :
					{
						this.drawPosX = 0;
					}
				}

				draw(layout);
			}
			else
			{
				this.isMaxHeightReached = true;
			}
		}
	}
	
	/**
	 * 
	 */
	public void draw(TextLayout layout)
	{
		layout.draw(
			this.grx,
			this.drawPosX + this.x + this.leftPadding,
			this.drawPosY + this.y + this.topPadding + this.verticalOffset + this.leadingOffset
			);
	}
	
}
