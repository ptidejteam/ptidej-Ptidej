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

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.charts.JRXyzDataset;
import net.sf.jasperreports.charts.JRXyzSeries;
import net.sf.jasperreports.charts.util.DefaultXYZDataset;
import net.sf.jasperreports.engine.JRChartDataset;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.design.JRVerifier;
import net.sf.jasperreports.engine.fill.JRCalculator;
import net.sf.jasperreports.engine.fill.JRExpressionEvalException;
import net.sf.jasperreports.engine.fill.JRFillChartDataset;
import net.sf.jasperreports.engine.fill.JRFillObjectFactory;
import net.sf.jasperreports.engine.util.Pair;

import org.jfree.data.general.Dataset;

/**
 * @author Flavius Sana (flavius_sana@users.sourceforge.net)
 * @version $Id: JRFillXyzDataset.java,v 1.1 2008/09/29 16:21:42 guehene Exp $
 */
public class JRFillXyzDataset extends JRFillChartDataset implements JRXyzDataset {

	protected JRFillXyzSeries[] xyzSeries = null;

	private DefaultXYZDataset dataset = null;
	
	private Map itemHyperlinks;
	
	
	public JRFillXyzDataset(JRXyzDataset xyzDataset, JRFillObjectFactory factory)
	{
		super( xyzDataset, factory );
		
		JRXyzSeries[] srcXyzSeries = xyzDataset.getSeries();
		if(srcXyzSeries != null && srcXyzSeries.length > 0)
		{
			this.xyzSeries = new JRFillXyzSeries[srcXyzSeries.length];
			for(int i = 0; i < this.xyzSeries.length; i++)
			{
				this.xyzSeries[i] = (JRFillXyzSeries)factory.getXyzSeries( srcXyzSeries[i]);
			}
		}
	}
	
	public JRXyzSeries[] getSeries(){
		return this.xyzSeries;
	}
	
	protected void customInitialize()
	{
		this.dataset = new DefaultXYZDataset();
		this.itemHyperlinks = new HashMap();
	}
	
	protected void customEvaluate( JRCalculator calculator ) throws JRExpressionEvalException 
	{
		if (this.xyzSeries != null && this.xyzSeries.length > 0)
		{
			for (int i = 0; i < this.xyzSeries.length; i++)
			{
				this.xyzSeries[i].evaluate( calculator );
			}
		}
	}
	
	protected void customIncrement()
	{
		if (this.xyzSeries != null && this.xyzSeries .length > 0)
		{
			for (int i = 0; i < this.xyzSeries.length; i++)
			{
				JRFillXyzSeries crtXyzSeries = this.xyzSeries[i];
				
				Comparable seriesName = crtXyzSeries.getSeries();
				if (seriesName == null)
				{
					throw new JRRuntimeException("XYZ series name is null.");
				}

				this.dataset.addValue(
					crtXyzSeries.getSeries(), 
					crtXyzSeries.getXValue(),
					crtXyzSeries.getYValue(),
					crtXyzSeries.getZValue()
					);
				
				if (crtXyzSeries.hasItemHyperlinks())
				{
					Map seriesLinks = (Map) this.itemHyperlinks.get(crtXyzSeries.getSeries());
					if (seriesLinks == null)
					{
						seriesLinks = new HashMap();
						this.itemHyperlinks.put(crtXyzSeries.getSeries(), seriesLinks);
					}
					Pair xyKey = new Pair(crtXyzSeries.getXValue(), crtXyzSeries.getYValue());
					seriesLinks.put(xyKey, crtXyzSeries.getPrintItemHyperlink());
				}
			}
		}
	}
	
	public Dataset getCustomDataset() {
		return this.dataset;
	}

	/**
	 * 
	 */
	public byte getDatasetType() {
		return JRChartDataset.XYZ_DATASET;
	}


	/**
	 *
	 */
	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}
	
	
	public boolean hasItemHyperlinks()
	{
		boolean foundLinks = false;
		if (this.xyzSeries != null && this.xyzSeries.length > 0)
		{
			for (int i = 0; i < this.xyzSeries.length && !foundLinks; i++)
			{
				JRFillXyzSeries serie = this.xyzSeries[i];
				foundLinks = serie.hasItemHyperlinks();
			}
		}
		return foundLinks;
	}

	
	public Map getItemHyperlinks()
	{
		return this.itemHyperlinks;
	}


	public void validate(JRVerifier verifier)
	{
		verifier.verify(this);
	}


}
