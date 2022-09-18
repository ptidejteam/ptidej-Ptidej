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
package net.sf.jasperreports.olap.xmla;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.olap.result.JROlapCell;
import net.sf.jasperreports.olap.result.JROlapResult;
import net.sf.jasperreports.olap.result.JROlapResultAxis;


/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRXmlaResult.java,v 1.1 2008/09/29 16:21:36 guehene Exp $
 */
public class JRXmlaResult implements JROlapResult
{

	private final static Log log = LogFactory.getLog(JRXmlaResult.class);
	
	private List axesList = new ArrayList();
	private JRXmlaResultAxis[] axes;
	private int[] cellOrdinalFactors;
	private final List cells = new ArrayList();

	public JROlapResultAxis[] getAxes()
	{
		return ensureAxisArray();
	}

	public JROlapCell getCell(int[] axisPositions)
	{
		int cellOrdinal = getCellOrdinal(axisPositions);
		return (JROlapCell) this.cells.get(cellOrdinal);
	}
	
	protected int getCellOrdinal(int[] axisPositions)
	{
		ensureCellOrdinalFactors();

		if (axisPositions.length != this.axes.length)
		{
			throw new JRRuntimeException("Number of axis positions (" + axisPositions.length 
					+ ") doesn't match the number of axes (" + this.axes.length + ")");
		}
		
		int ordinal = 0;
		for (int i = 0; i < axisPositions.length; ++i)
		{
			ordinal += this.cellOrdinalFactors[i] * axisPositions[i];
		}
		return ordinal;
	}

	public void addAxis(JRXmlaResultAxis axis)
	{
		this.axesList.add(axis);
		resetAxisArray();
	}

	public JRXmlaResultAxis getAxisByName(String name)
	{
		JRXmlaResultAxis axis = null;
		for (Iterator iter = this.axesList.iterator(); axis == null && iter.hasNext();)
		{
			JRXmlaResultAxis ax = (JRXmlaResultAxis) iter.next();
			if (ax.getAxisName().equals(name))
			{
				axis = ax;
			}
		}
		return axis;
	}

	protected JRXmlaResultAxis[] ensureAxisArray()
	{
		if (this.axes == null)
		{
			this.axes = new JRXmlaResultAxis[this.axesList.size()];
			this.axes = (JRXmlaResultAxis[]) this.axesList.toArray(this.axes);
		}
		return this.axes;
	}

	protected void ensureCellOrdinalFactors()
	{
		ensureAxisArray();
		
		if (this.cellOrdinalFactors == null)
		{
			int axisCount = this.axes.length;
			this.cellOrdinalFactors = new int[axisCount];
			
			this.cellOrdinalFactors[0] = 1;
			for (int i = 1; i < axisCount; ++i)
			{
				this.cellOrdinalFactors[i] = this.cellOrdinalFactors[i - 1] * this.axes[i - 1].getTupleCount();
			}
		}
	}

	protected void resetAxisArray()
	{
		this.axes = null;
		this.cellOrdinalFactors = null;
	}
	
	public void setCell(JRXmlaCell cell, int cellOrdinal)
	{
		int cellsCount = this.cells.size();
		if (cellOrdinal == -1 || cellOrdinal == cellsCount)
		{
			this.cells.add(cell);
		}
		else if (cellOrdinal > cellsCount)
		{
			for (int i = cellsCount; i < cellOrdinal; ++i)
			{
				this.cells.add(null);
			}
			this.cells.add(cell);
		}
		else
		{
			if (log.isWarnEnabled())
			{
				log.warn("Overwriting cell at ordinal " + cellOrdinal);
			}
			
			this.cells.set(cellOrdinal, cell);
		}
	}
}
