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
package net.sf.jasperreports.crosstabs.base;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import net.sf.jasperreports.crosstabs.JRCellContents;
import net.sf.jasperreports.engine.JRBox;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRDefaultStyleProvider;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.base.JRBaseLineBox;
import net.sf.jasperreports.engine.base.JRBaseElementGroup;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;
import net.sf.jasperreports.engine.util.JRBoxUtil;
import net.sf.jasperreports.engine.util.LineBoxWrapper;

/**
 * Base read-only implementation of {@link net.sf.jasperreports.crosstabs.JRCellContents JRCellContents}.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRBaseCellContents.java,v 1.1 2008/09/29 16:22:09 guehene Exp $
 */
public class JRBaseCellContents extends JRBaseElementGroup implements JRCellContents, Serializable
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	protected JRDefaultStyleProvider defaultStyleProvider;
	protected JRStyle style;
	protected String styleNameReference;
	
	protected Byte mode;
	protected Color backcolor;
	protected JRLineBox lineBox;
	protected int width;
	protected int height;

	public JRBaseCellContents(JRCellContents cell, JRBaseObjectFactory factory)
	{
		super(cell, factory);
		
		this.defaultStyleProvider = factory.getDefaultStyleProvider();
		this.style = factory.getStyle(cell.getStyle());
		this.styleNameReference = cell.getStyleNameReference();
		this.mode = cell.getMode();
		this.backcolor = cell.getBackcolor();
		this.lineBox = cell.getLineBox().clone(this);
		this.width = cell.getWidth();
		this.height = cell.getHeight();
	}

	public Color getBackcolor()
	{
		return this.backcolor;
	}

	/**
	 * @deprecated Replaced by {@link #getLineBox()}
	 */
	public JRBox getBox()
	{
		return new LineBoxWrapper(getLineBox());
	}

	public JRLineBox getLineBox()
	{
		return this.lineBox;
	}

	public int getWidth()
	{
		return this.width;
	}

	public int getHeight()
	{
		return this.height;
	}

	public JRDefaultStyleProvider getDefaultStyleProvider()
	{
		return this.defaultStyleProvider;
	}

	public JRStyle getStyle()
	{
		return this.style;
	}

	public Byte getMode()
	{
		return this.mode;
	}

	public String getStyleNameReference()
	{
		return this.styleNameReference;
	}

	/**
	 * 
	 */
	public Color getDefaultLineColor() 
	{
		return Color.black;
	}


	/**
	 * These fields are only for serialization backward compatibility.
	 */
	private JRBox box = null;
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();

		if (this.lineBox == null)
		{
			this.lineBox = new JRBaseLineBox(this);
			JRBoxUtil.setBoxToLineBox(
				this.box,
				this.lineBox
				);
			this.box = null;
		}
	}
}
