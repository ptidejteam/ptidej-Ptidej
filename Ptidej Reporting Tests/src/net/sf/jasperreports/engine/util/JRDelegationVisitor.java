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

import net.sf.jasperreports.crosstabs.JRCrosstab;
import net.sf.jasperreports.engine.JRBreak;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.JREllipse;
import net.sf.jasperreports.engine.JRFrame;
import net.sf.jasperreports.engine.JRImage;
import net.sf.jasperreports.engine.JRLine;
import net.sf.jasperreports.engine.JRRectangle;
import net.sf.jasperreports.engine.JRStaticText;
import net.sf.jasperreports.engine.JRSubreport;
import net.sf.jasperreports.engine.JRTextField;
import net.sf.jasperreports.engine.JRVisitor;


/**
 * Abstract delegation visitor.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRDelegationVisitor.java,v 1.1 2008/09/29 16:20:17 guehene Exp $
 */
public abstract class JRDelegationVisitor implements JRVisitor
{

	private final JRVisitor visitor;
	
	/**
	 * Creates a delegation visitor.
	 * 
	 * @param visitor the visitor to which calls would be delegated to
	 */
	public JRDelegationVisitor(JRVisitor visitor)
	{
		this.visitor = visitor;
	}
	
	/**
	 * Returns the visitor to which calls are delegated to.
	 * 
	 * @return the visitor to which calls are delegated to
	 */
	public JRVisitor getVisitor()
	{
		return this.visitor;
	}
	
	public void visitBreak(JRBreak breakElement)
	{
		this.visitor.visitBreak(breakElement);
	}

	public void visitChart(JRChart chart)
	{
		this.visitor.visitChart(chart);
	}

	public void visitCrosstab(JRCrosstab crosstab)
	{
		this.visitor.visitCrosstab(crosstab);
	}

	public void visitElementGroup(JRElementGroup elementGroup)
	{
		this.visitor.visitElementGroup(elementGroup);
	}

	public void visitEllipse(JREllipse ellipse)
	{
		this.visitor.visitEllipse(ellipse);
	}

	public void visitFrame(JRFrame frame)
	{
		this.visitor.visitFrame(frame);
	}

	public void visitImage(JRImage image)
	{
		this.visitor.visitImage(image);
	}

	public void visitLine(JRLine line)
	{
		this.visitor.visitLine(line);
	}

	public void visitRectangle(JRRectangle rectangle)
	{
		this.visitor.visitRectangle(rectangle);
	}

	public void visitStaticText(JRStaticText staticText)
	{
		this.visitor.visitStaticText(staticText);
	}

	public void visitSubreport(JRSubreport subreport)
	{
		this.visitor.visitSubreport(subreport);
	}

	public void visitTextField(JRTextField textField)
	{
		this.visitor.visitTextField(textField);
	}

}
