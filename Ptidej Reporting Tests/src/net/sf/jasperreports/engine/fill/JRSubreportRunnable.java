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


/**
 * Abstract base for {@link java.lang.Runnable Runnable}-based
 * {@link net.sf.jasperreports.engine.fill.JRSubreportRunner JRSubreportRunner}
 * implementations.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRSubreportRunnable.java,v 1.1 2008/09/29 16:20:01 guehene Exp $
 */
public abstract class JRSubreportRunnable implements Runnable
{
	private final JRFillSubreport fillSubreport;
	
	private Throwable error;
	private boolean running;
	
	protected JRSubreportRunnable(JRFillSubreport fillSubreport)
	{
		this.fillSubreport = fillSubreport;
	}

	protected JRSubreportRunResult runResult()
	{
		return new JRSubreportRunResult(!this.running, this.error);
	}
	
	public void run()
	{
		this.running = true;		
		this.error = null;
		
		try
		{
			this.fillSubreport.fillSubreport();
		}
		catch(JRFillInterruptedException e)
		{
			//If the subreport filler was interrupted, we should remain silent
		}
		catch(Throwable t)
		{
			this.error = t;
		}

		this.running = false;
	}
	
	protected boolean isRunning()
	{
		return this.running;
	}
}
