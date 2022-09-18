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

import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.BreakIterator;
import java.text.CharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import net.sf.jasperreports.engine.JRCommonText;
import net.sf.jasperreports.engine.JRPropertiesHolder;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRTextElement;
import net.sf.jasperreports.engine.export.TextRenderer;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.util.JRStyledText;
import net.sf.jasperreports.engine.util.MaxFontSizeFinder;


/**
 * Default text measurer implementation.
 * 
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: TextMeasurer.java,v 1.1 2008/09/29 16:20:01 guehene Exp $
 */
public class TextMeasurer implements JRTextMeasurer
{

	/**
	 *
	 */
	private static final FontRenderContext FONT_RENDER_CONTEXT = TextRenderer.LINE_BREAK_FONT_RENDER_CONTEXT;

	private JRCommonText textElement;
	private JRPropertiesHolder propertiesHolder;

	/**
	 * 
	 */
	private MaxFontSizeFinder maxFontSizeFinder = null;

	private int width = 0;
	private int height = 0;
	private int topPadding = 0;
	private int leftPadding = 0;
	private int bottomPadding = 0;
	private int rightPadding = 0;
	private float lineSpacing = 0;

	private float formatWidth = 0;
	private int maxHeight = 0;
	private boolean canOverflow;
	private Map globalAttributes;
	private TextMeasuredState measuredState;
	private TextMeasuredState prevMeasuredState;
	
	protected static class TextMeasuredState implements JRMeasuredText, Cloneable
	{
		protected int textOffset = 0;
		protected int lines = 0;
		protected int fontSizeSum = 0;
		protected int firstLineMaxFontSize = 0;
		protected float textHeight = 0;
		protected float firstLineLeading = 0;
		protected boolean isLeftToRight = true;
		protected String textSuffix = null;
		
		public boolean isLeftToRight()
		{
			return this.isLeftToRight;
		}
		
		public int getTextOffset()
		{
			return this.textOffset;
		}
		
		public float getTextHeight()
		{
			return this.textHeight;
		}
		
		public float getLineSpacingFactor()
		{
			if (this.lines > 0)
			{
				return this.textHeight / this.fontSizeSum;
			}
			return 0;
		}
		
		public float getLeadingOffset()
		{
			return this.firstLineLeading - this.firstLineMaxFontSize * getLineSpacingFactor();
		}

		public String getTextSuffix()
		{
			return this.textSuffix;
		}
		
		public TextMeasuredState cloneState()
		{
			try
			{
				return (TextMeasuredState) super.clone();
			}
			catch (CloneNotSupportedException e)
			{
				//never
				throw new JRRuntimeException(e);
			}
		}
	}
	
	/**
	 * 
	 */
	public TextMeasurer(JRCommonText textElement)
	{
		this.textElement = textElement;
		this.propertiesHolder = textElement instanceof JRPropertiesHolder ? (JRPropertiesHolder) textElement : null;
	}
	
	/**
	 * 
	 */
	protected void initialize(JRStyledText styledText, int availableStretchHeight, boolean canOverflow)
	{
		this.width = this.textElement.getWidth();
		this.height = this.textElement.getHeight();
		
		this.topPadding = this.textElement.getLineBox().getTopPadding().intValue();
		this.leftPadding = this.textElement.getLineBox().getLeftPadding().intValue();
		this.bottomPadding = this.textElement.getLineBox().getBottomPadding().intValue();
		this.rightPadding = this.textElement.getLineBox().getRightPadding().intValue();

		switch (this.textElement.getRotation())
		{
			case JRTextElement.ROTATION_LEFT :
			{
				this.width = this.textElement.getHeight();
				this.height = this.textElement.getWidth();
				int tmpPadding = this.topPadding;
				this.topPadding = this.leftPadding;
				this.leftPadding = this.bottomPadding;
				this.bottomPadding = this.rightPadding;
				this.rightPadding = tmpPadding;
				break;
			}
			case JRTextElement.ROTATION_RIGHT :
			{
				this.width = this.textElement.getHeight();
				this.height = this.textElement.getWidth();
				int tmpPadding = this.topPadding;
				this.topPadding = this.rightPadding;
				this.rightPadding = this.bottomPadding;
				this.bottomPadding = this.leftPadding;
				this.leftPadding = tmpPadding;
				break;
			}
			case JRTextElement.ROTATION_UPSIDE_DOWN :
			{
				int tmpPadding = this.topPadding;
				this.topPadding = this.bottomPadding;
				this.bottomPadding = tmpPadding;
				tmpPadding = this.leftPadding;
				this.leftPadding = this.rightPadding;
				this.rightPadding = tmpPadding;
				break;
			}
			case JRTextElement.ROTATION_NONE :
			default :
			{
			}
		}
		
		/*   */
		switch (this.textElement.getLineSpacing())
		{
			case JRTextElement.LINE_SPACING_SINGLE : 
			{
				this.lineSpacing = 1f;
				break;
			}
			case JRTextElement.LINE_SPACING_1_1_2 : 
			{
				this.lineSpacing = 1.5f;
				break;
			}
			case JRTextElement.LINE_SPACING_DOUBLE : 
			{
				this.lineSpacing = 2f;
				break;
			}
			default : 
			{
				this.lineSpacing = 1f;
			}
		}

		this.maxFontSizeFinder = MaxFontSizeFinder.getInstance(!JRCommonText.MARKUP_NONE.equals(this.textElement.getMarkup()));

		this.formatWidth = this.width - this.leftPadding - this.rightPadding;
		this.formatWidth = this.formatWidth < 0 ? 0 : this.formatWidth;
		this.maxHeight = this.height + availableStretchHeight - this.topPadding - this.bottomPadding;
		this.maxHeight = this.maxHeight < 0 ? 0 : this.maxHeight;
		this.canOverflow = canOverflow;
		this.globalAttributes = styledText.getGlobalAttributes();
		this.measuredState = new TextMeasuredState();
		this.prevMeasuredState = null;
	}

	/**
	 * 
	 */
	public JRMeasuredText measure(
		JRStyledText styledText,
		int remainingTextStart,
		int availableStretchHeight,
		boolean canOverflow
		)
	{
		/*   */
		initialize(styledText, availableStretchHeight, canOverflow);

		AttributedCharacterIterator allParagraphs = styledText.getAwtAttributedString().getIterator();

		int tokenPosition = remainingTextStart;
		int lastParagraphStart = remainingTextStart;
		String lastParagraphText = null;

		String remainingText = styledText.getText().substring(remainingTextStart);
		StringTokenizer tkzer = new StringTokenizer(remainingText, "\n", true);

		boolean rendered = true;
		while(tkzer.hasMoreTokens() && rendered) 
		{
			String token = tkzer.nextToken();

			if ("\n".equals(token))
			{
				rendered = renderParagraph(allParagraphs, lastParagraphStart, lastParagraphText);

				lastParagraphStart = tokenPosition + (tkzer.hasMoreTokens() || tokenPosition == 0 ? 1 : 0);
				lastParagraphText = null;
			}
			else
			{
				lastParagraphStart = tokenPosition;
				lastParagraphText = token;
			}

			tokenPosition += token.length();
		}

		if (rendered && lastParagraphStart < remainingTextStart + remainingText.length())
		{
			renderParagraph(allParagraphs, lastParagraphStart, lastParagraphText);
		}
		
		return this.measuredState;
	}

	/**
	 * 
	 */
	protected boolean renderParagraph(
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

		LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(paragraph, FONT_RENDER_CONTEXT);
		
		this.measuredState.textOffset = lastParagraphStart;
		
		boolean rendered = true;
		boolean renderedLine = false;
		while (lineMeasurer.getPosition() < paragraph.getEndIndex() && rendered)
		{
			rendered = renderNextLine(lineMeasurer, paragraph);
			renderedLine = renderedLine || rendered;
		}
		
		//if we rendered at least one line, and the last line didn't fit 
		//and the text does not overflow
		if (!rendered && this.prevMeasuredState != null && !this.canOverflow)
		{
			//handle last rendered row
			processLastTruncatedRow(allParagraphs, lastParagraphText, lastParagraphStart, renderedLine);
		}
		
		return rendered;
	}
	
	protected void processLastTruncatedRow(AttributedCharacterIterator allParagraphs,
			String paragraphText, int paragraphOffset,
			boolean lineTruncated)
	{
		if (lineTruncated && isToTruncateAtChar())
		{
			truncateLastLineAtChar(allParagraphs, paragraphText, paragraphOffset);
		}
		
		appendTruncateSuffix(allParagraphs);
	}

	protected void truncateLastLineAtChar(AttributedCharacterIterator allParagraphs, String paragraphText, int paragraphOffset)
	{
		//truncate the original line at char
		this.measuredState = this.prevMeasuredState.cloneState();
		AttributedCharacterIterator lineParagraph = new AttributedString(
				allParagraphs, 
				this.measuredState.textOffset,
				paragraphOffset + paragraphText.length()).getIterator();
		LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(
				lineParagraph, 
				BreakIterator.getCharacterInstance(), 
				FONT_RENDER_CONTEXT);
		//render again the last line
		//if the line does not fit now, it will remain empty
		renderNextLine(lineMeasurer, lineParagraph);
	}

	protected void appendTruncateSuffix(AttributedCharacterIterator allParagraphs)
	{
		String truncateSuffx = getTruncateSuffix();
		if (truncateSuffx == null)
		{
			return;
		}
		
		int lineStart = this.prevMeasuredState.textOffset;

		//advance from the line start until the next line start or the first newline
		StringBuffer lineText = new StringBuffer();
		allParagraphs.setIndex(lineStart);
		while (allParagraphs.getIndex() < this.measuredState.textOffset 
				&& allParagraphs.current() != '\n')
		{
			lineText.append(allParagraphs.current());
			allParagraphs.next();
		}
		int linePosition = allParagraphs.getIndex() - lineStart;
		
		//iterate to the beginning of the line
		boolean done = false;
		do
		{
			this.measuredState = this.prevMeasuredState.cloneState();

			String text = lineText.substring(0, linePosition) + truncateSuffx;
			AttributedString attributedText = new AttributedString(text);
			
			//set original attributes for the text part
			AttributedCharacterIterator lineAttributes = new AttributedString(
					allParagraphs, 
					this.measuredState.textOffset,
					this.measuredState.textOffset + linePosition).getIterator();
			setAttributes(attributedText, lineAttributes, 0);
			
			//set global attributes for the suffix part
			setAttributes(attributedText, this.globalAttributes, 
					text.length() - truncateSuffx.length(), text.length());
			
			AttributedCharacterIterator lineParagraph = attributedText.getIterator();
			
			BreakIterator breakIterator = 
				isToTruncateAtChar() 
				? BreakIterator.getCharacterInstance() 
				: BreakIterator.getLineInstance();
			LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(
					lineParagraph,
					breakIterator,
					FONT_RENDER_CONTEXT);

			if (renderNextLine(lineMeasurer, lineParagraph))
			{
				int lastPos = lineMeasurer.getPosition();
				//test if the entire suffix fit
				if (lastPos == linePosition + truncateSuffx.length())
				{
					//subtract the suffix from the offset
					this.measuredState.textOffset -= truncateSuffx.length();
					this.measuredState.textSuffix = truncateSuffx;
					done = true;
				}
				else
				{
					linePosition = breakIterator.preceding(linePosition);
					if (linePosition == BreakIterator.DONE)
					{
						//if the text suffix did not fit the line, only the part of it that fits will show

						//truncate the suffix
						String actualSuffix = truncateSuffx.substring(0, 
								this.measuredState.textOffset - this.prevMeasuredState.textOffset);
						//if the last text char is not a new line
						if (this.prevMeasuredState.textOffset > 0
								&& allParagraphs.setIndex(this.prevMeasuredState.textOffset - 1) != '\n')
						{
							//force a new line so that the suffix is displayed on the last line
							actualSuffix = '\n' + actualSuffix;
						}
						this.measuredState.textSuffix = actualSuffix;
						
						//restore the next to last line offset
						this.measuredState.textOffset = this.prevMeasuredState.textOffset;

						done = true;
					}
				}
			}
			else
			{
				//if the line did not fit, leave it empty
				done = true;
			}
		}
		while (!done);
	}

	protected boolean isToTruncateAtChar()
	{
		return JRProperties.getBooleanProperty(this.propertiesHolder, 
				JRTextElement.PROPERTY_TRUNCATE_AT_CHAR, false);
	}

	protected String getTruncateSuffix()
	{
		String truncateSuffx = JRProperties.getProperty(this.propertiesHolder,
				JRTextElement.PROPERTY_TRUNCATE_SUFFIX);
		if (truncateSuffx != null)
		{
			truncateSuffx = truncateSuffx.trim();
		}
		if (truncateSuffx.length() == 0)
		{
			truncateSuffx = null;
		}
		return truncateSuffx;
	}
	
	protected boolean renderNextLine(LineBreakMeasurer lineMeasurer, AttributedCharacterIterator paragraph)
	{
		int lineStartPosition = lineMeasurer.getPosition();

		TextLayout layout = lineMeasurer.nextLayout(this.formatWidth);

		float newTextHeight = this.measuredState.textHeight + layout.getLeading() + this.lineSpacing * layout.getAscent();
		boolean fits = newTextHeight + layout.getDescent() <= this.maxHeight;
		if (fits)
		{
			this.prevMeasuredState = this.measuredState.cloneState();
			
			this.measuredState.isLeftToRight = this.measuredState.isLeftToRight && layout.isLeftToRight();
			this.measuredState.textHeight = newTextHeight;
			this.measuredState.lines++;

			this.measuredState.fontSizeSum += 
				this.maxFontSizeFinder.findMaxFontSize(
					new AttributedString(
						paragraph, 
						lineStartPosition, 
						lineStartPosition + layout.getCharacterCount()
						).getIterator(),
					this.textElement.getFontSize()
					);

			if (this.measuredState.lines == 1)
			{
				this.measuredState.firstLineLeading = this.measuredState.textHeight;
				this.measuredState.firstLineMaxFontSize = this.measuredState.fontSizeSum;
			}

			// here is the Y offset where we would draw the line
			//lastDrawPosY = drawPosY;
			//
			this.measuredState.textHeight += layout.getDescent();
			
			this.measuredState.textOffset += lineMeasurer.getPosition() - lineStartPosition;
		}
		
		return fits;
	}
	
	protected JRPropertiesHolder getTextPropertiesHolder()
	{
		return this.propertiesHolder;
	}
	
	protected void setAttributes(
			AttributedString string,
			AttributedCharacterIterator attributes, 
			int stringOffset)
	{
		for (char c = attributes.first(); c != CharacterIterator.DONE; c = attributes.next())
		{
			for (Iterator it = attributes.getAttributes().entrySet().iterator(); it.hasNext();)
			{
				Map.Entry attributeEntry = (Map.Entry) it.next();
				AttributedCharacterIterator.Attribute attribute = (Attribute) attributeEntry.getKey();
				if (attributes.getRunStart(attribute) == attributes.getIndex())
				{
					Object attributeValue = attributeEntry.getValue();
					string.addAttribute(attribute, attributeValue, 
							attributes.getIndex() + stringOffset,
							attributes.getRunLimit(attribute) + stringOffset);
				}
			};
		}
	}
	
	protected void setAttributes(
			AttributedString string,
			Map attributes, 
			int startIndex, int endIndex)
	{
		for (Iterator it = attributes.entrySet().iterator(); it.hasNext();)
		{
			Map.Entry entry = (Map.Entry) it.next();
			AttributedCharacterIterator.Attribute attribute = (Attribute) entry.getKey();
			Object attributeValue = entry.getValue();
			string.addAttribute(attribute, attributeValue, startIndex, endIndex);
		}
	}
}
