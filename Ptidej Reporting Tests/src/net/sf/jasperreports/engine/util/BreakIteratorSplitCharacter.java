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
package net.sf.jasperreports.engine.util;

import java.text.BreakIterator;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import net.sf.jasperreports.engine.JRRuntimeException;

import com.lowagie.text.SplitCharacter;
import com.lowagie.text.pdf.PdfChunk;


/**
 * Implementation of {@link com.lowagie.text.SplitCharacter SplitCharacter} that
 * uses the same logic as AWT to break texts into lines.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: BreakIteratorSplitCharacter.java,v 1.1 2008/09/29 16:20:16 guehene Exp $
 * 
 * @see net.sf.jasperreports.engine.export.JRPdfExporterParameter#FORCE_LINEBREAK_POLICY
 * @see net.sf.jasperreports.engine.util.JRProperties#PDF_FORCE_LINEBREAK_POLICY
 */
public class BreakIteratorSplitCharacter implements SplitCharacter
{

	private char[] chars;
	private int start, end;
	private boolean[] boundary;
	private int lastBoundary;
	private final BreakIterator breakIter;
	
	public BreakIteratorSplitCharacter()
	{
		this(BreakIterator.getLineInstance());
	}
	
	public BreakIteratorSplitCharacter(BreakIterator breakIter)
	{
		this.breakIter = breakIter;
	}

	public boolean isSplitCharacter(int startIdx, int current, int endIdx, char[] cc, PdfChunk[] ck)
	{
		++current;
		if (current == endIdx)
		{
			return false;
		}

		if (!(this.chars == cc && this.start == startIdx && this.end == endIdx))
		{
			this.chars = cc;
			this.start = startIdx;
			this.end = endIdx;

			this.breakIter.setText(new ArrayCharIterator(cc, startIdx, endIdx));

			this.boundary = new boolean[endIdx - startIdx + 1];

			this.lastBoundary = this.breakIter.first();
			if (this.lastBoundary != BreakIterator.DONE)
			{
				this.boundary[this.lastBoundary - startIdx] = true;
			}
		}

		while (current > this.lastBoundary)
		{
			this.lastBoundary = this.breakIter.next();

			if (this.lastBoundary == BreakIterator.DONE)
			{
				this.lastBoundary = Integer.MAX_VALUE;
			}
			else
			{
				this.boundary[this.lastBoundary - startIdx] = true;
			}
		}

		return this.boundary[current - startIdx];
	}

	protected static class ArrayCharIterator implements CharacterIterator
	{

		private char[] chars;
		private int start;
		private int end;
		private int curr;

		public ArrayCharIterator(char[] chars, int start, int end)
		{
			this.chars = chars;
			this.start = start;
			this.end = end;
		}

		public char first()
		{
			this.curr = this.start;
			return current();
		}

		public char last()
		{
			if (this.end == this.start)
			{
				this.curr = this.end;
			}
			else
			{
				this.curr = this.end - 1;
			}
			return current();
		}

		public char setIndex(int position)
		{
			if (position < this.start || position > this.end)
			{
				throw new JRRuntimeException("Invalid index " + position + " (start = " + this.start + ", end = " + this.end + ")");
			}
			this.curr = position;
			return current();
		}

		public char current()
		{
			if (this.curr < this.start || this.curr >= this.end)
			{
				return DONE;
			}
			return this.chars[this.curr];
		}

		public char next()
		{
			if (this.curr >= this.end - 1)
			{
				this.curr = this.end;
				return DONE;
			}
			this.curr++;
			return this.chars[this.curr];
		}

		public char previous()
		{
			if (this.curr <= this.start)
			{
				return DONE;
			}
			this.curr--;
			return this.chars[this.curr];
		}

		public int getBeginIndex()
		{
			return this.start;
		}

		public int getEndIndex()
		{
			return this.end;
		}

		public int getIndex()
		{
			return this.curr;
		}

		public Object clone()
		{
			try
			{
				StringCharacterIterator other = (StringCharacterIterator) super.clone();
				return other;
			}
			catch (CloneNotSupportedException e)
			{
				throw new InternalError();
			}
		}
	}

}
