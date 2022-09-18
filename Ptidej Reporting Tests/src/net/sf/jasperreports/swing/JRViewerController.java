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
package net.sf.jasperreports.swing;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRPrintXmlLoader;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRViewerController.java,v 1.1 2008/09/29 16:22:11 guehene Exp $
 */
public class JRViewerController
{
	protected static final int TYPE_FILE_NAME = 1;
	protected static final int TYPE_INPUT_STREAM = 2;
	protected static final int TYPE_OBJECT = 3;
	
	private ResourceBundle resourceBundle = null;
	private Locale locale = null;
	private final List listeners = new ArrayList();
	
	protected int type = TYPE_FILE_NAME;
	protected boolean isXML = false;
	protected String reportFileName = null;
	protected boolean reloadSupported;
	
	private JasperPrint jasperPrint = null;
	private int pageIndex = 0;
	private float zoom = 0f;
	private boolean fitPage;
	private boolean fitWidth;

	public JRViewerController(Locale locale, ResourceBundle resBundle)
	{
		if (locale != null)
		{
			this.locale = locale;
		}
		else
		{
			this.locale = Locale.getDefault();
		}

		if (resBundle == null)
		{
			this.resourceBundle = ResourceBundle.getBundle("net/sf/jasperreports/view/viewer", getLocale());
		}
		else
		{
			this.resourceBundle = resBundle;
		}
	}

	public void addListener(JRViewerListener listener)
	{
		this.listeners.add(listener);
	}

	public boolean removeListener(JRViewerListener listener)
	{
		return this.listeners.remove(listener);
	}
	
	protected void fireListeners(int eventCode)
	{
		if (!this.listeners.isEmpty())
		{
			JRViewerEvent event = new JRViewerEvent(this, eventCode);
			for (Iterator it = this.listeners.iterator(); it.hasNext();)
			{
				JRViewerListener listener = (JRViewerListener) it.next();
				listener.viewerEvent(event);
			}
		}
	}
	
	public void loadReport(String fileName, boolean isXmlReport) throws JRException
	{
		if (isXmlReport)
		{
			this.jasperPrint = JRPrintXmlLoader.load(fileName);
		}
		else
		{
			this.jasperPrint = (JasperPrint)JRLoader.loadObject(fileName);
		}

		this.type = TYPE_FILE_NAME;
		this.isXML = isXmlReport;
		this.reportFileName = fileName;
		this.reloadSupported = true;
		fireListeners(JRViewerEvent.EVENT_REPORT_LOADED);
		setPageIndex(0);
	}

	public void loadReport(InputStream is, boolean isXmlReport) throws JRException
	{
		if (isXmlReport)
		{
			this.jasperPrint = JRPrintXmlLoader.load(is);
		}
		else
		{
			this.jasperPrint = (JasperPrint)JRLoader.loadObject(is);
		}

		this.type = TYPE_INPUT_STREAM;
		this.isXML = isXmlReport;
		this.reloadSupported = false;
		fireListeners(JRViewerEvent.EVENT_REPORT_LOADED);
		setPageIndex(0);
	}

	public void loadReport(JasperPrint jrPrint)
	{
		this.jasperPrint = jrPrint;
		this.type = TYPE_OBJECT;
		this.isXML = false;
		this.reloadSupported = false;
		fireListeners(JRViewerEvent.EVENT_REPORT_LOADED);
		setPageIndex(0);
	}

	public void reload()
	{
		if (this.type == TYPE_FILE_NAME)
		{
			try
			{
				loadReport(this.reportFileName, this.isXML);
			}
			catch (JRException e)
			{
				e.printStackTrace();

				this.jasperPrint = null;
				setPageIndex(0);
				refreshPage();

				fireListeners(JRViewerEvent.EVENT_REPORT_LOAD_FAILED);
			}

			forceRefresh();
		}
	}

	public boolean hasPages()
	{
		return this.jasperPrint != null &&
			this.jasperPrint.getPages() != null &&
			this.jasperPrint.getPages().size() > 0;
	}
	
	public void refreshPage()
	{
		fireListeners(JRViewerEvent.EVENT_REFRESH_PAGE);
	}

	protected void forceRefresh()
	{
		this.zoom = 0;//force pageRefresh()
		setZoomRatio(1);
	}

	public void setZoomRatio(float newZoom)
	{
		if (newZoom > 0)
		{
			this.fitPage = false;
			this.fitWidth = false;
			
			float old = this.zoom;
			this.zoom = newZoom;

			fireListeners(JRViewerEvent.EVENT_ZOOM_CHANGED);
			
			if (this.zoom != old)
			{
				refreshPage();
			}
		}
	}
	
	public void setPageIndex(int index)
	{
		if (hasPages())
		{
			if (index >= 0 && index < this.jasperPrint.getPages().size())
			{
				this.pageIndex = index;
				fireListeners(JRViewerEvent.EVENT_PAGE_CHANGED);
			}
		}
		else
		{
			fireListeners(JRViewerEvent.EVENT_PAGE_CHANGED);
		}
	}
	
	public ResourceBundle getResourceBundle()
	{
		return this.resourceBundle;
	}

	public Locale getLocale()
	{
		return this.locale;
	}
	
	public String getBundleString(String key)
	{
		return this.resourceBundle.getString(key);
	}

	public JasperPrint getJasperPrint()
	{
		return this.jasperPrint;
	}

	public int getPageCount()
	{
		return this.jasperPrint.getPages().size();
	}
	
	public void clear()
	{
		this.jasperPrint = null;
	}

	public int getPageIndex()
	{
		return this.pageIndex;
	}
	
	public float getZoom()
	{
		return this.zoom;
	}

	public boolean isReloadSupported()
	{
		return this.reloadSupported;
	}

	public boolean isFitPage()
	{
		return this.fitPage;
	}

	public boolean isFitWidth()
	{
		return this.fitWidth;
	}

	public void fitPage()
	{
		fireListeners(JRViewerEvent.EVENT_FIT_PAGE);
		this.fitPage = true;
	}

	public void fitWidth()
	{
		fireListeners(JRViewerEvent.EVENT_FIT_WIDTH);
		this.fitWidth = true;
	}
}
