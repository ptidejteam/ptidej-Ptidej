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
package net.sf.jasperreports.renderers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

import net.sf.jasperreports.charts.util.PieChartHyperlinkProvider;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRPrintHyperlink;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.ChartEntity;


/**
 * Image map renderer used for pie charts.
 * 
 * @deprecated
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRPieChartImageMapRenderer.java,v 1.1 2008/09/29 16:22:00 guehene Exp $
 */
public class JRPieChartImageMapRenderer extends JRAbstractChartImageMapRenderer
{
	
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	private PieChartHyperlinkProvider pieChartHyperlinkProvider = null;
	
	public JRPieChartImageMapRenderer(JFreeChart chart, Map sectionHyperlinks)
	{
		super(chart);
		
		this.pieChartHyperlinkProvider = new PieChartHyperlinkProvider(sectionHyperlinks);
	}

	
	public JRPrintHyperlink getEntityHyperlink(ChartEntity entity)
	{
		return new PieChartHyperlinkProvider(this.sectionHyperlinks).getEntityHyperlink(entity);
	}


	public boolean hasHyperlinks()
	{
		return this.pieChartHyperlinkProvider.hasHyperlinks();
	}

	/**
	 * These fields are only for serialization backward compatibility.
	 */
	private Map sectionHyperlinks;

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		
		if (this.pieChartHyperlinkProvider == null)
		{
			this.pieChartHyperlinkProvider = new PieChartHyperlinkProvider(this.sectionHyperlinks);
			this.sectionHyperlinks = null;
		}
	}
}
