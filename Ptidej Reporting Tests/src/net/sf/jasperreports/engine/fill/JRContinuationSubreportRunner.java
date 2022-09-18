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


import org.apache.commons.javaflow.Continuation;


/**
 * Implemetation of {@link net.sf.jasperreports.engine.fill.JRSubreportRunner JRSubreportRunner}
 * using <a href="http://jakarta.apache.org/commons/sandbox/javaflow/">Javaflow</a> continuations.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRContinuationSubreportRunner.java,v 1.1 2008/09/29 16:20:01 guehene Exp $
 */
public class JRContinuationSubreportRunner extends JRSubreportRunnable implements JRSubreportRunner
{
	private Continuation continuation;

	public JRContinuationSubreportRunner(JRFillSubreport fillSubreport)
	{
		super(fillSubreport);
	}

	public boolean isFilling()
	{
		return this.continuation != null;
	}

	public JRSubreportRunResult start()
	{
		this.continuation = Continuation.startWith(this);
		return runResult();
	}

	public JRSubreportRunResult resume()
	{
		this.continuation = Continuation.continueWith(this.continuation);
		return runResult();
	}

	public void reset()
	{
		this.continuation = null;
	}

	public void cancel()
	{
	}

	public void suspend()
	{
		Continuation.suspend();
	}
}
