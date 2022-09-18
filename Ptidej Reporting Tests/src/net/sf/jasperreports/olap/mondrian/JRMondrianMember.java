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
package net.sf.jasperreports.olap.mondrian;

import mondrian.olap.Member;
import net.sf.jasperreports.olap.result.JROlapMember;


/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRMondrianMember.java,v 1.1 2008/09/29 16:20:50 guehene Exp $
 */
public class JRMondrianMember implements JROlapMember
{

	private final Member member;
	private final JRMondrianMember parent;

	public JRMondrianMember(Member member, JRMondrianFactory factory)
	{
		this.member = member;
		this.parent = factory.createMember(member.getParentMember());
	}

	public int getDepth()
	{
		return this.member.getDepth();
	}

	public String getName()
	{
		return this.member.getName();
	}

	public JROlapMember getParentMember()
	{
		return this.parent;
	}

	public Object getPropertyValue(String propertyName)
	{
		return this.member.getPropertyValue(propertyName);
	}

	public String getUniqueName()
	{
		return this.member.getUniqueName();
	}

	public Member getMondrianMember()
	{
		return this.member;
	}

}
