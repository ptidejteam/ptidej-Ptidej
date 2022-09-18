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

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 * Class used to perform report filling asychronously.
 * <p>
 * An instance of this type can be used as a handle to an asychronous fill process.
 * The main benefit of this method is that the filling process can be cancelled.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: AsynchronousFillHandle.java,v 1.1 2008/09/29 16:20:01 guehene Exp $
 */
public class AsynchronousFillHandle
{	
	protected final JasperReport jasperReport;
	protected final Map parameters;
	protected final JRDataSource dataSource;
	protected final Connection conn;
	protected final JRBaseFiller filler;
	protected final List listeners;
	protected Thread fillThread;
	protected boolean started = false;
	protected boolean running = false;
	protected boolean cancelled = false;
	protected final Object lock;
	
	protected Integer priority = null;
	
	protected String threadName;
	
	protected AsynchronousFillHandle (
			JasperReport jasperReport,
			Map parameters,
			JRDataSource dataSource
			) throws JRException
	{
		this(jasperReport, parameters, dataSource, null);
	}
	
	protected AsynchronousFillHandle (
			JasperReport jasperReport,
			Map parameters,
			Connection conn
			) throws JRException
	{
		this(jasperReport, parameters, null, conn);
	}
	
	protected AsynchronousFillHandle (
			JasperReport jasperReport,
			Map parameters
			) throws JRException
	{
		this(jasperReport, parameters, null, null);
	}
	
	protected AsynchronousFillHandle (
			JasperReport jasperReport,
			Map parameters,
			JRDataSource dataSource,
			Connection conn
			) throws JRException
	{
		this.jasperReport = jasperReport;
		this.parameters = parameters;
		this.dataSource = dataSource;
		this.conn = conn;
		this.filler = JRFiller.createFiller(jasperReport);
		this.listeners = new ArrayList();
		this.lock = this;
	}

	
	/**
	 * Adds a listener to the filling process.
	 * 
	 * @param listener the listener to be added
	 */
	public void addListener(AsynchronousFilllListener listener)
	{
		this.listeners.add(listener);
	}


	/**
	 * Removes a listener from the filling process.
	 * 
	 * @param listener the listener to be removed
	 * @return <tt>true</tt> if the listener was found and removed
	 */
	public boolean removeListener(AsynchronousFilllListener listener)
	{
		return this.listeners.remove(listener);
	}

	
	protected class ReportFiller implements Runnable
	{
		public void run()
		{
			synchronized (AsynchronousFillHandle.this.lock)
			{
				AsynchronousFillHandle.this.running = true;
			}
			
			try
			{
				JasperPrint print;
				if (AsynchronousFillHandle.this.conn != null)
				{
					print = AsynchronousFillHandle.this.filler.fill(AsynchronousFillHandle.this.parameters, AsynchronousFillHandle.this.conn);
				}
				else if (AsynchronousFillHandle.this.dataSource != null)
				{
					print = AsynchronousFillHandle.this.filler.fill(AsynchronousFillHandle.this.parameters, AsynchronousFillHandle.this.dataSource);
				}
				else
				{
					print = AsynchronousFillHandle.this.filler.fill(AsynchronousFillHandle.this.parameters);
				}
				
				notifyFinish(print);
			}
			catch (Exception e)
			{
				synchronized (AsynchronousFillHandle.this.lock)
				{
					if (AsynchronousFillHandle.this.cancelled)
					{
						notifyCancel();
					}
					else
					{
						notifyError(e);
					}
				}
			}
			finally
			{
				synchronized (AsynchronousFillHandle.this.lock)
				{
					AsynchronousFillHandle.this.running = false;
				}
			}
		}
	}
	
	
	/**
	 * Starts the filling process asychronously.
	 * <p>
	 * The filling is launched on a new thread and the method exits
	 * after the thread is started.
	 * <p>
	 * When the filling finishes either in success or failure, the listeners
	 * are notified.  
	 */
	public void startFill()
	{
		synchronized (this.lock)
		{
			if (this.started)
			{
				throw new IllegalStateException("Fill already started.");
			}

			this.started = true;
		}
		
		ReportFiller reportFiller = new ReportFiller();
		
		if (this.threadName == null)
		{
			this.fillThread = new Thread(reportFiller);
		}
		else
		{
			this.fillThread = new Thread(reportFiller, this.threadName);
		}
		
		if (this.priority != null)
		{
			this.fillThread.setPriority(this.priority.intValue());
		}
		
		this.fillThread.start();
	}

	
	/**
	 * Cancels the fill started by the handle.
	 * <p>
	 * The method sends a cancel signal to the filling process.
	 * When the filling process will end, the listeners will be notified 
	 * that the filling has been cancelled.
	 * 
	 * @throws JRException
	 */
	public void cancellFill() throws JRException
	{
		synchronized (this.lock)
		{
			if (!this.running)
			{
				throw new IllegalStateException("Fill not running.");
			}
			
			this.filler.cancelFill();
			this.cancelled = true;
		}
	}
	
	
	protected void notifyFinish(JasperPrint print)
	{
		for (Iterator i = this.listeners.iterator(); i.hasNext();)
		{
			AsynchronousFilllListener listener = (AsynchronousFilllListener) i.next();
			listener.reportFinished(print);
		}
	}
	
	
	protected void notifyCancel()
	{
		for (Iterator i = this.listeners.iterator(); i.hasNext();)
		{
			AsynchronousFilllListener listener = (AsynchronousFilllListener) i.next();
			listener.reportCancelled();
		}
	}
	
	
	protected void notifyError(Throwable e)
	{
		for (Iterator i = this.listeners.iterator(); i.hasNext();)
		{
			AsynchronousFilllListener listener = (AsynchronousFilllListener) i.next();
			listener.reportFillError(e);
		}
	}


	/**
	 * Creates an asychronous filling handle.
	 * 
	 * @param jasperReport the report
	 * @param parameters the parameter map
	 * @param dataSource the data source
	 * @return the handle
	 * @throws JRException
	 */
	public static AsynchronousFillHandle createHandle(
		JasperReport jasperReport,
		Map parameters,
		JRDataSource dataSource
		) throws JRException
	{
		AsynchronousFillHandle filler = new AsynchronousFillHandle(jasperReport, parameters, dataSource);
		
		return filler;
	}


	/**
	 * Creates an asychronous filling handle.
	 * 
	 * @param jasperReport the report
	 * @param parameters the parameter map
	 * @param conn the connection
	 * @return the handle
	 * @throws JRException
	 */
	public static AsynchronousFillHandle createHandle(
		JasperReport jasperReport,
		Map parameters,
		Connection conn
		) throws JRException
	{
		AsynchronousFillHandle filler = new AsynchronousFillHandle(jasperReport, parameters, conn);
		
		return filler;
	}


	/**
	 * Creates an asychronous filling handle.
	 * 
	 * @param jasperReport the report
	 * @param parameters the parameter map
	 * @return the handle
	 * @throws JRException
	 */
	public static AsynchronousFillHandle createHandle(
		JasperReport jasperReport,
		Map parameters
		) throws JRException
	{
		AsynchronousFillHandle filler = new AsynchronousFillHandle(jasperReport, parameters);
		
		return filler;
	}
	
	
	/**
	 * Sets the priority of the filler thread.
	 * 
	 * @param priority the filler thread priority.
	 * @see Thread#setPriority(int)
	 */
	public void setPriority (int priority)
	{
		synchronized (this.lock)
		{
			this.priority = Integer.valueOf(priority);
			if (this.fillThread != null)
			{
				this.fillThread.setPriority(priority);
			}
		}
	}
	
	
	/**
	 * Sets the name of the filler thread.
	 * 
	 * @param name the filler thread name.
	 * @see Thread#setName(java.lang.String)
	 */
	public void setThreadName (String name)
	{
		synchronized (this.lock)
		{
			this.threadName = name;
			if (this.fillThread != null)
			{
				this.fillThread.setName(name);
			}
		}
	}
}
