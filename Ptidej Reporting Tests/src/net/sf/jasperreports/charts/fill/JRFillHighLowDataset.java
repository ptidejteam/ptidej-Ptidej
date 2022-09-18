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
package net.sf.jasperreports.charts.fill;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.charts.JRHighLowDataset;
import net.sf.jasperreports.engine.JRChartDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JRHyperlinkHelper;
import net.sf.jasperreports.engine.JRPrintHyperlink;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.design.JRVerifier;
import net.sf.jasperreports.engine.fill.JRCalculator;
import net.sf.jasperreports.engine.fill.JRExpressionEvalException;
import net.sf.jasperreports.engine.fill.JRFillChartDataset;
import net.sf.jasperreports.engine.fill.JRFillHyperlinkHelper;
import net.sf.jasperreports.engine.fill.JRFillObjectFactory;

import org.jfree.data.general.Dataset;
import org.jfree.data.xy.DefaultHighLowDataset;


/**
 * @author Ionut Nedelcu (ionutned@users.sourceforge.net)
 * @version $Id: JRFillHighLowDataset.java,v 1.1 2008/09/29 16:21:41 guehene Exp $
 */
public class JRFillHighLowDataset extends JRFillChartDataset implements JRHighLowDataset
{

	/**
	 *
	 */
	private String series = null;
	private List elements = new ArrayList();
	private Date date = null;
	private Number high = null;
	private Number low = null;
	private Number open = null;
	private Number close = null;
	private Number volume = null;
	
	private JRPrintHyperlink itemHyperlink;
	private List itemHyperlinks;

	
	/**
	 *
	 */
	public JRFillHighLowDataset(JRHighLowDataset dataset, JRFillObjectFactory factory)
	{
		super(dataset, factory);
	}


	protected void customInitialize()
	{
		this.elements = new ArrayList();
		this.itemHyperlinks = new ArrayList();
	}


	protected void customEvaluate(JRCalculator calculator) throws JRExpressionEvalException
	{
		this.series = (String) calculator.evaluate(getSeriesExpression());
		this.date = (Date) calculator.evaluate(getDateExpression());
		this.high = (Number) calculator.evaluate(getHighExpression());
		this.low = (Number) calculator.evaluate(getLowExpression());
		this.open = (Number) calculator.evaluate(getOpenExpression());
		this.close = (Number) calculator.evaluate(getCloseExpression());
		this.volume = (Number) calculator.evaluate(getVolumeExpression());
		
		if (hasItemHyperlink())
		{
			evaluateSectionHyperlink(calculator);
		}
	}


	protected void evaluateSectionHyperlink(JRCalculator calculator) throws JRExpressionEvalException
	{
		try
		{
			this.itemHyperlink = JRFillHyperlinkHelper.evaluateHyperlink(getItemHyperlink(), calculator, JRExpression.EVALUATION_DEFAULT);
		}
		catch (JRExpressionEvalException e)
		{
			throw e;
		}
		catch (JRException e)
		{
			throw new JRRuntimeException(e);
		}
	}


	protected void customIncrement()
	{
		this.elements.add(new HighLowElement(this.date, this.high, this.low, this.open, this.close, this.volume));
		
		if (hasItemHyperlink())
		{
			this.itemHyperlinks.add(this.itemHyperlink);
		}
	}


	public Dataset getCustomDataset()
	{
		int size = this.elements.size();
		if (size > 0)
		{
			Date[] dateArray = new Date[size];
			double[] highArray = new double[size];
			double[] lowArray = new double[size];
			double[] openArray = new double[size];
			double[] closeArray = new double[size];
			double[] volumeArray = new double[size];

			for (int i = 0; i < this.elements.size(); i++) {
				HighLowElement bean = (HighLowElement) this.elements.get(i);
				dateArray[i] = new Date(bean.getDate().getTime());
				highArray[i] = bean.getHigh().doubleValue();
				lowArray[i] = bean.getLow().doubleValue();
				openArray[i] = bean.getOpen().doubleValue();
				closeArray[i] = bean.getClose().doubleValue();
				volumeArray[i] = bean.getVolume().doubleValue();
			}

			return new DefaultHighLowDataset(this.series, dateArray, highArray, lowArray, openArray, closeArray, volumeArray);
		}
		
		return null;
	}


	public JRExpression getSeriesExpression()
	{
		return ((JRHighLowDataset)this.parent).getSeriesExpression();
	}


	public JRExpression getDateExpression()
	{
		return ((JRHighLowDataset)this.parent).getDateExpression();
	}


	public JRExpression getHighExpression()
	{
		return ((JRHighLowDataset)this.parent).getHighExpression();
	}


	public JRExpression getLowExpression()
	{
		return ((JRHighLowDataset)this.parent).getLowExpression();
	}


	public JRExpression getOpenExpression()
	{
		return ((JRHighLowDataset)this.parent).getOpenExpression();
	}


	public JRExpression getCloseExpression()
	{
		return ((JRHighLowDataset)this.parent).getCloseExpression();
	}


	public JRExpression getVolumeExpression()
	{
		return ((JRHighLowDataset)this.parent).getVolumeExpression();
	}

	/**
	 *
	 */
	private static class HighLowElement
	{
		Date date;
		Number high;
		Number low;
		Number open;
		Number close;
		Number volume;


		public HighLowElement(
			Date date,
			Number high,
			Number low,
			Number open,
			Number close,
			Number volume
			)
		{
			if (date == null)
				throw new JRRuntimeException("Date value is null in high-low series.");
			this.date = date;

			if (high == null)
				throw new JRRuntimeException("High value is null in high-low series.");
			this.high = high;
			
			if (low == null)
				throw new JRRuntimeException("Low value is null in high-low series.");
			this.low = low;
			
			if (open == null)
				throw new JRRuntimeException("Open value is null in high-low series.");
			this.open = open;
			
			if (close == null)
				throw new JRRuntimeException("Close value is null in high-low series.");
			this.close = close;
			
			if (volume == null)
				throw new JRRuntimeException("Volume value is null in high-low series.");
			this.volume = volume;
		}


		public Date getDate()
		{
			return this.date;
		}


		public void setDate(Date date)
		{
			this.date = date;
		}


		public Number getHigh()
		{
			return this.high;
		}


		public void setHigh(Number high)
		{
			this.high = high;
		}


		public Number getLow()
		{
			return this.low;
		}


		public void setLow(Number low)
		{
			this.low = low;
		}


		public Number getOpen()
		{
			return this.open;
		}


		public void setOpen(Number open)
		{
			this.open = open;
		}


		public Number getClose()
		{
			return this.close;
		}


		public void setClose(Number close)
		{
			this.close = close;
		}


		public Number getVolume()
		{
			return this.volume;
		}


		public void setVolume(Number volume)
		{
			this.volume = volume;
		}
	}

	/**
	 * 
	 */
	public byte getDatasetType() {
		return JRChartDataset.HIGHLOW_DATASET;
	}

	
	/**
	 *
	 */
	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}


	public JRHyperlink getItemHyperlink()
	{
		return ((JRHighLowDataset) this.parent).getItemHyperlink();
	}


	public boolean hasItemHyperlink()
	{
		return !JRHyperlinkHelper.isEmpty(getItemHyperlink()); 
	}

	
	public List getItemHyperlinks()
	{
		return this.itemHyperlinks;
	}


	public void validate(JRVerifier verifier)
	{
		verifier.verify(this);
	}

}
