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

/*
 * Contributors:
 * Ryan Johnson - delscovich@users.sourceforge.net
 * Carlton Moore - cmoore79@users.sourceforge.net
 *  Petr Michalek - pmichalek@users.sourceforge.net
 */
package net.sf.jasperreports.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JRImageMapRenderer;
import net.sf.jasperreports.engine.JRPrintAnchorIndex;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintFrame;
import net.sf.jasperreports.engine.JRPrintHyperlink;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.JRPrintImageAreaHyperlink;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRRenderable;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.JRGraphics2DExporterParameter;
import net.sf.jasperreports.engine.print.JRPrinterAWT;
import net.sf.jasperreports.engine.util.JRClassLoader;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.xml.JRPrintXmlLoader;
import net.sf.jasperreports.view.save.JRPrintSaveContributor;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRViewer.java,v 1.1 2008/09/29 16:21:04 guehene Exp $
 */
public class JRViewer extends javax.swing.JPanel implements JRHyperlinkListener
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	/**
	 * Maximum size (in pixels) of a buffered image that would be used by {@link JRViewer JRViewer} to render a report page.
	 * <p>
	 * If rendering a report page would require an image larger than this threshold
	 * (i.e. image width x image height > maximum size), the report page will be rendered directly on the viewer component.
	 * </p>
	 * <p>
	 * If this property is zero or negative, buffered images will never be user to render a report page.
	 * By default, this property is set to 0.
	 * </p>
	 */
	public static final String VIEWER_RENDER_BUFFER_MAX_SIZE = JRProperties.PROPERTY_PREFIX + "viewer.render.buffer.max.size";

	/**
	 *
	 */
	protected static final int TYPE_FILE_NAME = 1;
	protected static final int TYPE_INPUT_STREAM = 2;
	protected static final int TYPE_OBJECT = 3;

	/**
	 * The DPI of the generated report.
	 */
	public static final int REPORT_RESOLUTION = 72;

	protected float MIN_ZOOM = 0.5f;
	protected float MAX_ZOOM = 10f;
	protected int zooms[] = {50, 75, 100, 125, 150, 175, 200, 250, 400, 800};
	protected int defaultZoomIndex = 2;

	protected int type = TYPE_FILE_NAME;
	protected boolean isXML = false;
	protected String reportFileName = null;
	JasperPrint jasperPrint = null;
	private int pageIndex = 0;
	private boolean pageError;
	protected float zoom = 0f;

	private JRGraphics2DExporter exporter = null;

	/**
	 * the screen resolution.
	 */
	private int screenResolution = REPORT_RESOLUTION;

	/**
	 * the zoom ration adjusted to the screen resolution.
	 */
	protected float realZoom = 0f;

	private DecimalFormat zoomDecimalFormat = new DecimalFormat("#.##");
	private ResourceBundle resourceBundle = null;

	private int downX = 0;
	private int downY = 0;

	private java.util.List hyperlinkListeners = new ArrayList();
	private Map linksMap = new HashMap();
	private MouseListener mouseListener =
		new java.awt.event.MouseAdapter()
		{
			public void mouseClicked(java.awt.event.MouseEvent evt)
			{
				hyperlinkClicked(evt);
			}
		};

	protected KeyListener keyNavigationListener =
		new KeyListener() {
			public void keyTyped(KeyEvent evt)
			{
			}
			public void keyPressed(KeyEvent evt)
			{
				keyNavigate(evt);
			}
			public void keyReleased(KeyEvent evt)
			{
			}
		};

	protected List saveContributors = new ArrayList();
	protected File lastFolder = null;
	protected JRSaveContributor lastSaveContributor = null;

	/** Creates new form JRViewer */
	public JRViewer(String fileName, boolean isXML) throws JRException
	{
		this(fileName, isXML, null);
	}


	/** Creates new form JRViewer */
	public JRViewer(InputStream is, boolean isXML) throws JRException
	{
		this(is, isXML, null);
	}


	/** Creates new form JRViewer */
	public JRViewer(JasperPrint jrPrint)
	{
		this(jrPrint, null);
	}


	/** Creates new form JRViewer */
	public JRViewer(String fileName, boolean isXML, Locale locale) throws JRException
	{
		this(fileName, isXML, locale, null);
	}


	/** Creates new form JRViewer */
	public JRViewer(InputStream is, boolean isXML, Locale locale) throws JRException
	{
		this(is, isXML, locale, null);
	}


	/** Creates new form JRViewer */
	public JRViewer(JasperPrint jrPrint, Locale locale)
	{
		this(jrPrint, locale, null);
	}


	/** Creates new form JRViewer */
	public JRViewer(String fileName, boolean isXML, Locale locale, ResourceBundle resBundle) throws JRException
	{
		initResources(locale, resBundle);

		setScreenDetails();

		setZooms();

		initComponents();

		loadReport(fileName, isXML);

		this.cmbZoom.setSelectedIndex(this.defaultZoomIndex);

		initSaveContributors();

		addHyperlinkListener(this);
	}


	/** Creates new form JRViewer */
	public JRViewer(InputStream is, boolean isXML, Locale locale, ResourceBundle resBundle) throws JRException
	{
		initResources(locale, resBundle);

		setScreenDetails();

		setZooms();

		initComponents();

		loadReport(is, isXML);

		this.cmbZoom.setSelectedIndex(this.defaultZoomIndex);

		initSaveContributors();

		addHyperlinkListener(this);
	}


	/** Creates new form JRViewer */
	public JRViewer(JasperPrint jrPrint, Locale locale, ResourceBundle resBundle)
	{
		initResources(locale, resBundle);

		setScreenDetails();

		setZooms();

		initComponents();

		loadReport(jrPrint);

		this.cmbZoom.setSelectedIndex(this.defaultZoomIndex);

		initSaveContributors();

		addHyperlinkListener(this);
	}


	private void setScreenDetails()
	{
		this.screenResolution = Toolkit.getDefaultToolkit().getScreenResolution();
	}


	/**
	 *
	 */
	public void clear()
	{
		emptyContainer(this);
		this.jasperPrint = null;
	}


	/**
	 *
	 */
	protected void setZooms()
	{
	}


	/**
	 *
	 */
	public void addSaveContributor(JRSaveContributor contributor)
	{
		this.saveContributors.add(contributor);
	}


	/**
	 *
	 */
	public void removeSaveContributor(JRSaveContributor contributor)
	{
		this.saveContributors.remove(contributor);
	}


	/**
	 *
	 */
	public JRSaveContributor[] getSaveContributors()
	{
		return (JRSaveContributor[])this.saveContributors.toArray(new JRSaveContributor[this.saveContributors.size()]);
	}


	/**
	 * Replaces the save contributors with the ones provided as parameter. 
	 */
	public void setSaveContributors(JRSaveContributor[] saveContributors)
	{
		this.saveContributors = new ArrayList();
		if (saveContributors != null)
		{
			this.saveContributors.addAll(Arrays.asList(saveContributors));
		}
	}


	/**
	 *
	 */
	public void addHyperlinkListener(JRHyperlinkListener listener)
	{
		this.hyperlinkListeners.add(listener);
	}


	/**
	 *
	 */
	public void removeHyperlinkListener(JRHyperlinkListener listener)
	{
		this.hyperlinkListeners.remove(listener);
	}


	/**
	 *
	 */
	public JRHyperlinkListener[] getHyperlinkListeners()
	{
		return (JRHyperlinkListener[])this.hyperlinkListeners.toArray(new JRHyperlinkListener[this.hyperlinkListeners.size()]);
	}


	/**
	 *
	 */
	protected void initResources(Locale locale, ResourceBundle resBundle)
	{
		if (locale != null)
			setLocale(locale);
		else
			setLocale(Locale.getDefault());

		if (resBundle == null)
		{
			this.resourceBundle = ResourceBundle.getBundle("net/sf/jasperreports/view/viewer", getLocale());
		}
		else
		{
			this.resourceBundle = resBundle;
		}
	}


	/**
	 *
	 */
	protected String getBundleString(String key)
	{
		return this.resourceBundle.getString(key);
	}


	/**
	 *
	 */
	protected void initSaveContributors()
	{
		final String[] DEFAULT_CONTRIBUTORS =
			{
				"net.sf.jasperreports.view.save.JRPrintSaveContributor",
				"net.sf.jasperreports.view.save.JRPdfSaveContributor",
				"net.sf.jasperreports.view.save.JRRtfSaveContributor",
				"net.sf.jasperreports.view.save.JROdtSaveContributor",
				"net.sf.jasperreports.view.save.JRHtmlSaveContributor",
				"net.sf.jasperreports.view.save.JRSingleSheetXlsSaveContributor",
				"net.sf.jasperreports.view.save.JRMultipleSheetsXlsSaveContributor",
				"net.sf.jasperreports.view.save.JRCsvSaveContributor",
				"net.sf.jasperreports.view.save.JRXmlSaveContributor",
				"net.sf.jasperreports.view.save.JREmbeddedImagesXmlSaveContributor"
			};

		for(int i = 0; i < DEFAULT_CONTRIBUTORS.length; i++)
		{
			try
			{
				Class saveContribClass = JRClassLoader.loadClassForName(DEFAULT_CONTRIBUTORS[i]);
				Constructor constructor = saveContribClass.getConstructor(new Class[]{Locale.class, ResourceBundle.class});
				JRSaveContributor saveContrib = (JRSaveContributor)constructor.newInstance(new Object[]{getLocale(), this.resourceBundle});
				this.saveContributors.add(saveContrib);
			}
			catch (Exception e)
			{
			}
		}
	}


	/**
	 *
	 */
	public void gotoHyperlink(JRPrintHyperlink hyperlink)
	{
		switch(hyperlink.getHyperlinkType())
		{
			case JRHyperlink.HYPERLINK_TYPE_REFERENCE :
			{
				if (isOnlyHyperlinkListener())
				{
					System.out.println("Hyperlink reference : " + hyperlink.getHyperlinkReference());
					System.out.println("Implement your own JRHyperlinkListener to manage this type of event.");
				}
				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_LOCAL_ANCHOR :
			{
				if (hyperlink.getHyperlinkAnchor() != null)
				{
					Map anchorIndexes = this.jasperPrint.getAnchorIndexes();
					JRPrintAnchorIndex anchorIndex = (JRPrintAnchorIndex)anchorIndexes.get(hyperlink.getHyperlinkAnchor());
					if (anchorIndex.getPageIndex() != this.pageIndex)
					{
						setPageIndex(anchorIndex.getPageIndex());
						refreshPage();
					}
					Container container = this.pnlInScroll.getParent();
					if (container instanceof JViewport)
					{
						JViewport viewport = (JViewport) container;

						int newX = (int)(anchorIndex.getElementAbsoluteX() * this.realZoom);
						int newY = (int)(anchorIndex.getElementAbsoluteY() * this.realZoom);

						int maxX = this.pnlInScroll.getWidth() - viewport.getWidth();
						int maxY = this.pnlInScroll.getHeight() - viewport.getHeight();

						if (newX < 0)
						{
							newX = 0;
						}
						if (newX > maxX)
						{
							newX = maxX;
						}
						if (newY < 0)
						{
							newY = 0;
						}
						if (newY > maxY)
						{
							newY = maxY;
						}

						viewport.setViewPosition(new Point(newX, newY));
					}
				}

				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_LOCAL_PAGE :
			{
				int page = this.pageIndex + 1;
				if (hyperlink.getHyperlinkPage() != null)
				{
					page = hyperlink.getHyperlinkPage().intValue();
				}

				if (page >= 1 && page <= this.jasperPrint.getPages().size() && page != this.pageIndex + 1)
				{
					setPageIndex(page - 1);
					refreshPage();
					Container container = this.pnlInScroll.getParent();
					if (container instanceof JViewport)
					{
						JViewport viewport = (JViewport) container;
						viewport.setViewPosition(new Point(0, 0));
					}
				}

				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_REMOTE_ANCHOR :
			{
				if (isOnlyHyperlinkListener())
				{
					System.out.println("Hyperlink reference : " + hyperlink.getHyperlinkReference());
					System.out.println("Hyperlink anchor    : " + hyperlink.getHyperlinkAnchor());
					System.out.println("Implement your own JRHyperlinkListener to manage this type of event.");
				}
				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_REMOTE_PAGE :
			{
				if (isOnlyHyperlinkListener())
				{
					System.out.println("Hyperlink reference : " + hyperlink.getHyperlinkReference());
					System.out.println("Hyperlink page      : " + hyperlink.getHyperlinkPage());
					System.out.println("Implement your own JRHyperlinkListener to manage this type of event.");
				}
				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_CUSTOM:
			{
				if (isOnlyHyperlinkListener())
				{
					System.out.println("Hyperlink of type " + hyperlink.getLinkType());
					System.out.println("Implement your own JRHyperlinkListener to manage this type of event.");
				}
				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_NONE :
			default :
			{
				break;
			}
		}
	}


	protected boolean isOnlyHyperlinkListener()
	{
		int listenerCount;
		if (this.hyperlinkListeners == null)
		{
			listenerCount = 0;
		}
		else
		{
			listenerCount = this.hyperlinkListeners.size();
			if (this.hyperlinkListeners.contains(this))
			{
				--listenerCount;
			}
		}
		return listenerCount == 0;
	}


	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		this.tlbToolBar = new javax.swing.JPanel();
		this.btnSave = new javax.swing.JButton();
		this.btnPrint = new javax.swing.JButton();
		this.btnReload = new javax.swing.JButton();
		this.pnlSep01 = new javax.swing.JPanel();
		this.btnFirst = new javax.swing.JButton();
		this.btnPrevious = new javax.swing.JButton();
		this.btnNext = new javax.swing.JButton();
		this.btnLast = new javax.swing.JButton();
		this.txtGoTo = new javax.swing.JTextField();
		this.pnlSep02 = new javax.swing.JPanel();
		this.btnActualSize = new javax.swing.JToggleButton();
		this.btnFitPage = new javax.swing.JToggleButton();
		this.btnFitWidth = new javax.swing.JToggleButton();
		this.pnlSep03 = new javax.swing.JPanel();
		this.btnZoomIn = new javax.swing.JButton();
		this.btnZoomOut = new javax.swing.JButton();
		this.cmbZoom = new javax.swing.JComboBox();
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		for(int i = 0; i < this.zooms.length; i++)
		{
			model.addElement("" + this.zooms[i] + "%");
		}
		this.cmbZoom.setModel(model);

		this.pnlMain = new javax.swing.JPanel();
		this.scrollPane = new javax.swing.JScrollPane();
		this.scrollPane.getHorizontalScrollBar().setUnitIncrement(5);
		this.scrollPane.getVerticalScrollBar().setUnitIncrement(5);

		this.pnlInScroll = new javax.swing.JPanel();
		this.pnlPage = new javax.swing.JPanel();
		this.jPanel4 = new javax.swing.JPanel();
		this.pnlLinks = new javax.swing.JPanel();
		this.jPanel5 = new javax.swing.JPanel();
		this.jPanel6 = new javax.swing.JPanel();
		this.jPanel7 = new javax.swing.JPanel();
		this.jPanel8 = new javax.swing.JPanel();
		this.jLabel1 = new javax.swing.JLabel();
		this.jPanel9 = new javax.swing.JPanel();
		this.lblPage = new PageRenderer(this);
		this.pnlStatus = new javax.swing.JPanel();
		this.lblStatus = new javax.swing.JLabel();

		setLayout(new java.awt.BorderLayout());

		setMinimumSize(new java.awt.Dimension(450, 150));
		setPreferredSize(new java.awt.Dimension(450, 150));
		this.tlbToolBar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 2));

		this.btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/save.GIF")));
		this.btnSave.setToolTipText(getBundleString("save"));
		this.btnSave.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnSave.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnSave.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnSave.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSaveActionPerformed(evt);
			}
		});
		this.btnSave.addKeyListener(this.keyNavigationListener);
		this.tlbToolBar.add(this.btnSave);

		this.btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/print.GIF")));
		this.btnPrint.setToolTipText(getBundleString("print"));
		this.btnPrint.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnPrint.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnPrint.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnPrint.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnPrint.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnPrintActionPerformed(evt);
			}
		});
		this.btnPrint.addKeyListener(this.keyNavigationListener);
		this.tlbToolBar.add(this.btnPrint);

		this.btnReload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/reload.GIF")));
		this.btnReload.setToolTipText(getBundleString("reload"));
		this.btnReload.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnReload.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnReload.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnReload.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnReload.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnReloadActionPerformed(evt);
			}
		});
		this.btnReload.addKeyListener(this.keyNavigationListener);
		this.tlbToolBar.add(this.btnReload);

		this.pnlSep01.setMaximumSize(new java.awt.Dimension(10, 10));
		this.tlbToolBar.add(this.pnlSep01);

		this.btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/first.GIF")));
		this.btnFirst.setToolTipText(getBundleString("first.page"));
		this.btnFirst.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnFirst.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnFirst.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnFirst.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnFirst.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnFirstActionPerformed(evt);
			}
		});
		this.btnFirst.addKeyListener(this.keyNavigationListener);
		this.tlbToolBar.add(this.btnFirst);

		this.btnPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/previous.GIF")));
		this.btnPrevious.setToolTipText(getBundleString("previous.page"));
		this.btnPrevious.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnPrevious.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnPrevious.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnPrevious.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnPrevious.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnPreviousActionPerformed(evt);
			}
		});
		this.btnPrevious.addKeyListener(this.keyNavigationListener);
		this.tlbToolBar.add(this.btnPrevious);

		this.btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/next.GIF")));
		this.btnNext.setToolTipText(getBundleString("next.page"));
		this.btnNext.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnNext.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnNext.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnNext.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnNext.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnNextActionPerformed(evt);
			}
		});
		this.btnNext.addKeyListener(this.keyNavigationListener);
		this.tlbToolBar.add(this.btnNext);

		this.btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/last.GIF")));
		this.btnLast.setToolTipText(getBundleString("last.page"));
		this.btnLast.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnLast.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnLast.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnLast.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnLast.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnLastActionPerformed(evt);
			}
		});
		this.btnLast.addKeyListener(this.keyNavigationListener);
		this.tlbToolBar.add(this.btnLast);

		this.txtGoTo.setToolTipText(getBundleString("go.to.page"));
		this.txtGoTo.setMaximumSize(new java.awt.Dimension(40, 23));
		this.txtGoTo.setMinimumSize(new java.awt.Dimension(40, 23));
		this.txtGoTo.setPreferredSize(new java.awt.Dimension(40, 23));
		this.txtGoTo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				txtGoToActionPerformed(evt);
			}
		});
		this.txtGoTo.addKeyListener(this.keyNavigationListener);
		this.tlbToolBar.add(this.txtGoTo);

		this.pnlSep02.setMaximumSize(new java.awt.Dimension(10, 10));
		this.tlbToolBar.add(this.pnlSep02);

		this.btnActualSize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/actualsize.GIF")));
		this.btnActualSize.setToolTipText(getBundleString("actual.size"));
		this.btnActualSize.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnActualSize.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnActualSize.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnActualSize.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnActualSize.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnActualSizeActionPerformed(evt);
			}
		});
		this.btnActualSize.addKeyListener(this.keyNavigationListener);
		this.tlbToolBar.add(this.btnActualSize);

		this.btnFitPage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/fitpage.GIF")));
		this.btnFitPage.setToolTipText(getBundleString("fit.page"));
		this.btnFitPage.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnFitPage.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnFitPage.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnFitPage.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnFitPage.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnFitPageActionPerformed(evt);
			}
		});
		this.btnFitPage.addKeyListener(this.keyNavigationListener);
		this.tlbToolBar.add(this.btnFitPage);

		this.btnFitWidth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/fitwidth.GIF")));
		this.btnFitWidth.setToolTipText(getBundleString("fit.width"));
		this.btnFitWidth.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnFitWidth.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnFitWidth.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnFitWidth.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnFitWidth.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnFitWidthActionPerformed(evt);
			}
		});
		this.btnFitWidth.addKeyListener(this.keyNavigationListener);
		this.tlbToolBar.add(this.btnFitWidth);

		this.pnlSep03.setMaximumSize(new java.awt.Dimension(10, 10));
		this.tlbToolBar.add(this.pnlSep03);

		this.btnZoomIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/zoomin.GIF")));
		this.btnZoomIn.setToolTipText(getBundleString("zoom.in"));
		this.btnZoomIn.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnZoomIn.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnZoomIn.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnZoomIn.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnZoomIn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnZoomInActionPerformed(evt);
			}
		});
		this.btnZoomIn.addKeyListener(this.keyNavigationListener);
		this.tlbToolBar.add(this.btnZoomIn);

		this.btnZoomOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/zoomout.GIF")));
		this.btnZoomOut.setToolTipText(getBundleString("zoom.out"));
		this.btnZoomOut.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnZoomOut.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnZoomOut.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnZoomOut.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnZoomOut.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnZoomOutActionPerformed(evt);
			}
		});
		this.btnZoomOut.addKeyListener(this.keyNavigationListener);
		this.tlbToolBar.add(this.btnZoomOut);

		this.cmbZoom.setEditable(true);
		this.cmbZoom.setToolTipText(getBundleString("zoom.ratio"));
		this.cmbZoom.setMaximumSize(new java.awt.Dimension(80, 23));
		this.cmbZoom.setMinimumSize(new java.awt.Dimension(80, 23));
		this.cmbZoom.setPreferredSize(new java.awt.Dimension(80, 23));
		this.cmbZoom.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cmbZoomActionPerformed(evt);
			}
		});
		this.cmbZoom.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cmbZoomItemStateChanged(evt);
			}
		});
		this.cmbZoom.addKeyListener(this.keyNavigationListener);
		this.tlbToolBar.add(this.cmbZoom);

		add(this.tlbToolBar, java.awt.BorderLayout.NORTH);

		this.pnlMain.setLayout(new java.awt.BorderLayout());
		this.pnlMain.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent evt) {
				pnlMainComponentResized(evt);
			}
		});

		this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.pnlInScroll.setLayout(new java.awt.GridBagLayout());

		this.pnlPage.setLayout(new java.awt.BorderLayout());
		this.pnlPage.setMinimumSize(new java.awt.Dimension(100, 100));
		this.pnlPage.setPreferredSize(new java.awt.Dimension(100, 100));

		this.jPanel4.setLayout(new java.awt.GridBagLayout());
		this.jPanel4.setMinimumSize(new java.awt.Dimension(100, 120));
		this.jPanel4.setPreferredSize(new java.awt.Dimension(100, 120));

		this.pnlLinks.setLayout(null);
		this.pnlLinks.setMinimumSize(new java.awt.Dimension(5, 5));
		this.pnlLinks.setPreferredSize(new java.awt.Dimension(5, 5));
		this.pnlLinks.setOpaque(false);
		this.pnlLinks.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				pnlLinksMousePressed(evt);
			}
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				pnlLinksMouseReleased(evt);
			}
		});
		this.pnlLinks.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			public void mouseDragged(java.awt.event.MouseEvent evt) {
				pnlLinksMouseDragged(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		this.jPanel4.add(this.pnlLinks, gridBagConstraints);

		this.jPanel5.setBackground(java.awt.Color.gray);
		this.jPanel5.setMinimumSize(new java.awt.Dimension(5, 5));
		this.jPanel5.setPreferredSize(new java.awt.Dimension(5, 5));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		this.jPanel4.add(this.jPanel5, gridBagConstraints);

		this.jPanel6.setMinimumSize(new java.awt.Dimension(5, 5));
		this.jPanel6.setPreferredSize(new java.awt.Dimension(5, 5));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		this.jPanel4.add(this.jPanel6, gridBagConstraints);

		this.jPanel7.setBackground(java.awt.Color.gray);
		this.jPanel7.setMinimumSize(new java.awt.Dimension(5, 5));
		this.jPanel7.setPreferredSize(new java.awt.Dimension(5, 5));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		this.jPanel4.add(this.jPanel7, gridBagConstraints);

		this.jPanel8.setBackground(java.awt.Color.gray);
		this.jPanel8.setMinimumSize(new java.awt.Dimension(5, 5));
		this.jPanel8.setPreferredSize(new java.awt.Dimension(5, 5));
		this.jLabel1.setText("jLabel1");
		this.jPanel8.add(this.jLabel1);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		this.jPanel4.add(this.jPanel8, gridBagConstraints);

		this.jPanel9.setMinimumSize(new java.awt.Dimension(5, 5));
		this.jPanel9.setPreferredSize(new java.awt.Dimension(5, 5));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		this.jPanel4.add(this.jPanel9, gridBagConstraints);

		this.lblPage.setBackground(java.awt.Color.white);
		this.lblPage.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
		this.lblPage.setOpaque(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		this.jPanel4.add(this.lblPage, gridBagConstraints);

		this.pnlPage.add(this.jPanel4, java.awt.BorderLayout.CENTER);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
		this.pnlInScroll.add(this.pnlPage, gridBagConstraints);

		this.scrollPane.setViewportView(this.pnlInScroll);
		this.pnlMain.add(this.scrollPane, java.awt.BorderLayout.CENTER);
		add(this.pnlMain, java.awt.BorderLayout.CENTER);

		this.pnlStatus.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

		this.lblStatus.setFont(new java.awt.Font("Dialog", 1, 10));
		this.lblStatus.setText("Page i of n");
		this.pnlStatus.add(this.lblStatus);
		add(this.pnlStatus, java.awt.BorderLayout.SOUTH);
		addKeyListener(this.keyNavigationListener);
	}
	// </editor-fold>//GEN-END:initComponents

	void txtGoToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGoToActionPerformed
		try
		{
			int pageNumber = Integer.parseInt(this.txtGoTo.getText());
			if (
				pageNumber != this.pageIndex + 1
				&& pageNumber > 0
				&& pageNumber <= this.jasperPrint.getPages().size()
				)
			{
				setPageIndex(pageNumber - 1);
				refreshPage();
			}
		}
		catch(NumberFormatException e)
		{
		}
	}//GEN-LAST:event_txtGoToActionPerformed

	void cmbZoomItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbZoomItemStateChanged
		// Add your handling code here:
		this.btnActualSize.setSelected(false);
		this.btnFitPage.setSelected(false);
		this.btnFitWidth.setSelected(false);
	}//GEN-LAST:event_cmbZoomItemStateChanged

	void pnlMainComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnlMainComponentResized
		// Add your handling code here:
		if (this.btnFitPage.isSelected())
		{
			fitPage();
			this.btnFitPage.setSelected(true);
		}
		else if (this.btnFitWidth.isSelected())
		{
			setRealZoomRatio(((float)this.pnlInScroll.getVisibleRect().getWidth() - 20f) / this.jasperPrint.getPageWidth());
			this.btnFitWidth.setSelected(true);
		}

	}//GEN-LAST:event_pnlMainComponentResized

	void btnActualSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualSizeActionPerformed
		// Add your handling code here:
		if (this.btnActualSize.isSelected())
		{
			this.btnFitPage.setSelected(false);
			this.btnFitWidth.setSelected(false);
			this.cmbZoom.setSelectedIndex(-1);
			setZoomRatio(1);
			this.btnActualSize.setSelected(true);
		}
	}//GEN-LAST:event_btnActualSizeActionPerformed

	void btnFitWidthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFitWidthActionPerformed
		// Add your handling code here:
		if (this.btnFitWidth.isSelected())
		{
			this.btnActualSize.setSelected(false);
			this.btnFitPage.setSelected(false);
			this.cmbZoom.setSelectedIndex(-1);
			setRealZoomRatio(((float)this.pnlInScroll.getVisibleRect().getWidth() - 20f) / this.jasperPrint.getPageWidth());
			this.btnFitWidth.setSelected(true);
		}
	}//GEN-LAST:event_btnFitWidthActionPerformed

	void btnFitPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFitPageActionPerformed
		// Add your handling code here:
		if (this.btnFitPage.isSelected())
		{
			this.btnActualSize.setSelected(false);
			this.btnFitWidth.setSelected(false);
			this.cmbZoom.setSelectedIndex(-1);
			fitPage();
			this.btnFitPage.setSelected(true);
		}
	}//GEN-LAST:event_btnFitPageActionPerformed

	void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
		// Add your handling code here:

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setLocale(this.getLocale());
		fileChooser.updateUI();
		for(int i = 0; i < this.saveContributors.size(); i++)
		{
			fileChooser.addChoosableFileFilter((JRSaveContributor)this.saveContributors.get(i));
		}

		if (this.saveContributors.contains(this.lastSaveContributor))
		{
			fileChooser.setFileFilter(this.lastSaveContributor);
		}
		else if (this.saveContributors.size() > 0)
		{
			fileChooser.setFileFilter((JRSaveContributor)this.saveContributors.get(0));
		}
		
		if (this.lastFolder != null)
		{
			fileChooser.setCurrentDirectory(this.lastFolder);
		}
		
		int retValue = fileChooser.showSaveDialog(this);
		if (retValue == JFileChooser.APPROVE_OPTION)
		{
			FileFilter fileFilter = fileChooser.getFileFilter();
			File file = fileChooser.getSelectedFile();
			
			this.lastFolder = file.getParentFile();

			JRSaveContributor contributor = null;

			if (fileFilter instanceof JRSaveContributor)
			{
				contributor = (JRSaveContributor)fileFilter;
			}
			else
			{
				int i = 0;
				while(contributor == null && i < this.saveContributors.size())
				{
					contributor = (JRSaveContributor)this.saveContributors.get(i++);
					if (!contributor.accept(file))
					{
						contributor = null;
					}
				}

				if (contributor == null)
				{
					contributor = new JRPrintSaveContributor(getLocale(), this.resourceBundle);
				}
			}

			this.lastSaveContributor = contributor;
			
			try
			{
				contributor.save(this.jasperPrint, file);
			}
			catch (JRException e)
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, getBundleString("error.saving"));
			}
		}
	}//GEN-LAST:event_btnSaveActionPerformed

	void pnlLinksMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLinksMouseDragged
		// Add your handling code here:

		Container container = this.pnlInScroll.getParent();
		if (container instanceof JViewport)
		{
			JViewport viewport = (JViewport) container;
			Point point = viewport.getViewPosition();
			int newX = point.x - (evt.getX() - this.downX);
			int newY = point.y - (evt.getY() - this.downY);

			int maxX = this.pnlInScroll.getWidth() - viewport.getWidth();
			int maxY = this.pnlInScroll.getHeight() - viewport.getHeight();

			if (newX < 0)
			{
				newX = 0;
			}
			if (newX > maxX)
			{
				newX = maxX;
			}
			if (newY < 0)
			{
				newY = 0;
			}
			if (newY > maxY)
			{
				newY = maxY;
			}

			viewport.setViewPosition(new Point(newX, newY));
		}
	}//GEN-LAST:event_pnlLinksMouseDragged

	void pnlLinksMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLinksMouseReleased
		// Add your handling code here:
		this.pnlLinks.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}//GEN-LAST:event_pnlLinksMouseReleased

	void pnlLinksMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLinksMousePressed
		// Add your handling code here:
		this.pnlLinks.setCursor(new Cursor(Cursor.MOVE_CURSOR));

		this.downX = evt.getX();
		this.downY = evt.getY();
	}//GEN-LAST:event_pnlLinksMousePressed

	void btnPrintActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnPrintActionPerformed
	{//GEN-HEADEREND:event_btnPrintActionPerformed
		// Add your handling code here:

		Thread thread =
			new Thread(
				new Runnable()
				{
					public void run()
					{
						try
						{
							JasperPrintManager.printReport(JRViewer.this.jasperPrint, true);
						}
						catch (Exception ex)
						{
							ex.printStackTrace();
							JOptionPane.showMessageDialog(JRViewer.this, getBundleString("error.printing"));
						}
					}
				}
			);

		thread.start();

	}//GEN-LAST:event_btnPrintActionPerformed

	void btnLastActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnLastActionPerformed
	{//GEN-HEADEREND:event_btnLastActionPerformed
		// Add your handling code here:
		setPageIndex(this.jasperPrint.getPages().size() - 1);
		refreshPage();
	}//GEN-LAST:event_btnLastActionPerformed

	void btnNextActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnNextActionPerformed
	{//GEN-HEADEREND:event_btnNextActionPerformed
		// Add your handling code here:
		setPageIndex(this.pageIndex + 1);
		refreshPage();
	}//GEN-LAST:event_btnNextActionPerformed

	void btnPreviousActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnPreviousActionPerformed
	{//GEN-HEADEREND:event_btnPreviousActionPerformed
		// Add your handling code here:
		setPageIndex(this.pageIndex - 1);
		refreshPage();
	}//GEN-LAST:event_btnPreviousActionPerformed

	void btnFirstActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnFirstActionPerformed
	{//GEN-HEADEREND:event_btnFirstActionPerformed
		// Add your handling code here:
		setPageIndex(0);
		refreshPage();
	}//GEN-LAST:event_btnFirstActionPerformed

	void btnReloadActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnReloadActionPerformed
	{//GEN-HEADEREND:event_btnReloadActionPerformed
		// Add your handling code here:
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

				JOptionPane.showMessageDialog(this, getBundleString("error.loading"));
			}

			forceRefresh();
		}
	}//GEN-LAST:event_btnReloadActionPerformed

	protected void forceRefresh()
	{
		this.zoom = 0;//force pageRefresh()
		this.realZoom = 0f;
		setZoomRatio(1);
	}

	void btnZoomInActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnZoomInActionPerformed
	{//GEN-HEADEREND:event_btnZoomInActionPerformed
		// Add your handling code here:
		this.btnActualSize.setSelected(false);
		this.btnFitPage.setSelected(false);
		this.btnFitWidth.setSelected(false);

		int newZoomInt = (int)(100 * getZoomRatio());
		int index = Arrays.binarySearch(this.zooms, newZoomInt);
		if (index < 0)
		{
			setZoomRatio(this.zooms[- index - 1] / 100f);
		}
		else if (index < this.cmbZoom.getModel().getSize() - 1)
		{
			setZoomRatio(this.zooms[index + 1] / 100f);
		}
	}//GEN-LAST:event_btnZoomInActionPerformed

	void btnZoomOutActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnZoomOutActionPerformed
	{//GEN-HEADEREND:event_btnZoomOutActionPerformed
		// Add your handling code here:
		this.btnActualSize.setSelected(false);
		this.btnFitPage.setSelected(false);
		this.btnFitWidth.setSelected(false);

		int newZoomInt = (int)(100 * getZoomRatio());
		int index = Arrays.binarySearch(this.zooms, newZoomInt);
		if (index > 0)
		{
			setZoomRatio(this.zooms[index - 1] / 100f);
		}
		else if (index < -1)
		{
			setZoomRatio(this.zooms[- index - 2] / 100f);
		}
	}//GEN-LAST:event_btnZoomOutActionPerformed

	void cmbZoomActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cmbZoomActionPerformed
	{//GEN-HEADEREND:event_cmbZoomActionPerformed
		// Add your handling code here:
		float newZoom = getZoomRatio();

		if (newZoom < this.MIN_ZOOM)
		{
			newZoom = this.MIN_ZOOM;
		}

		if (newZoom > this.MAX_ZOOM)
		{
			newZoom = this.MAX_ZOOM;
		}

		setZoomRatio(newZoom);
	}//GEN-LAST:event_cmbZoomActionPerformed


	/**
	*/
	void hyperlinkClicked(MouseEvent evt)
	{
		JPanel link = (JPanel)evt.getSource();
		JRPrintHyperlink element = (JRPrintHyperlink)this.linksMap.get(link);
		hyperlinkClicked(element);
	}


	protected void hyperlinkClicked(JRPrintHyperlink hyperlink)
	{
		try
		{
			JRHyperlinkListener listener = null;
			for(int i = 0; i < this.hyperlinkListeners.size(); i++)
			{
				listener = (JRHyperlinkListener)this.hyperlinkListeners.get(i);
				listener.gotoHyperlink(hyperlink);
			}
		}
		catch(JRException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, getBundleString("error.hyperlink"));
		}
	}


	/**
	*/
	public int getPageIndex()
	{
		return this.pageIndex;
	}


	/**
	*/
	private void setPageIndex(int index)
	{
		if (
			this.jasperPrint != null &&
			this.jasperPrint.getPages() != null &&
			this.jasperPrint.getPages().size() > 0
			)
		{
			if (index >= 0 && index < this.jasperPrint.getPages().size())
			{
				this.pageIndex = index;
				this.pageError = false;
				this.btnFirst.setEnabled( (this.pageIndex > 0) );
				this.btnPrevious.setEnabled( (this.pageIndex > 0) );
				this.btnNext.setEnabled( (this.pageIndex < this.jasperPrint.getPages().size() - 1) );
				this.btnLast.setEnabled( (this.pageIndex < this.jasperPrint.getPages().size() - 1) );
				this.txtGoTo.setEnabled(this.btnFirst.isEnabled() || this.btnLast.isEnabled());
				this.txtGoTo.setText("" + (this.pageIndex + 1));
				this.lblStatus.setText(
					MessageFormat.format(
						getBundleString("page"),
						new Object[]{Integer.valueOf(this.pageIndex + 1), Integer.valueOf(this.jasperPrint.getPages().size())}
						)
					);
			}
		}
		else
		{
			this.btnFirst.setEnabled(false);
			this.btnPrevious.setEnabled(false);
			this.btnNext.setEnabled(false);
			this.btnLast.setEnabled(false);
			this.txtGoTo.setEnabled(false);
			this.txtGoTo.setText("");
			this.lblStatus.setText("");
		}
	}


	/**
	*/
	protected void loadReport(String fileName, boolean isXmlReport) throws JRException
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
		this.btnReload.setEnabled(true);
		setPageIndex(0);
	}


	/**
	*/
	protected void loadReport(InputStream is, boolean isXmlReport) throws JRException
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
		this.btnReload.setEnabled(false);
		setPageIndex(0);
	}


	/**
	*/
	protected void loadReport(JasperPrint jrPrint)
	{
		this.jasperPrint = jrPrint;
		this.type = TYPE_OBJECT;
		this.isXML = false;
		this.btnReload.setEnabled(false);
		setPageIndex(0);
	}

	/**
	*/
	protected void refreshPage()
	{
		if (
			this.jasperPrint == null ||
			this.jasperPrint.getPages() == null ||
			this.jasperPrint.getPages().size() == 0
			)
		{
			this.pnlPage.setVisible(false);
			this.btnSave.setEnabled(false);
			this.btnPrint.setEnabled(false);
			this.btnActualSize.setEnabled(false);
			this.btnFitPage.setEnabled(false);
			this.btnFitWidth.setEnabled(false);
			this.btnZoomIn.setEnabled(false);
			this.btnZoomOut.setEnabled(false);
			this.cmbZoom.setEnabled(false);

			if (this.jasperPrint != null)
			{
				JOptionPane.showMessageDialog(this, getBundleString("no.pages"));
			}

			return;
		}

		this.pnlPage.setVisible(true);
		this.btnSave.setEnabled(true);
		this.btnPrint.setEnabled(true);
		this.btnActualSize.setEnabled(true);
		this.btnFitPage.setEnabled(true);
		this.btnFitWidth.setEnabled(true);
		this.btnZoomIn.setEnabled(this.zoom < this.MAX_ZOOM);
		this.btnZoomOut.setEnabled(this.zoom > this.MIN_ZOOM);
		this.cmbZoom.setEnabled(true);

		Dimension dim = new Dimension(
			(int)(this.jasperPrint.getPageWidth() * this.realZoom) + 8, // 2 from border, 5 from shadow and 1 extra pixel for image
			(int)(this.jasperPrint.getPageHeight() * this.realZoom) + 8
			);
		this.pnlPage.setMaximumSize(dim);
		this.pnlPage.setMinimumSize(dim);
		this.pnlPage.setPreferredSize(dim);

		long maxImageSize = JRProperties.getLongProperty(VIEWER_RENDER_BUFFER_MAX_SIZE);
		boolean renderImage;
		if (maxImageSize <= 0)
		{
			renderImage = false;
		}
		else
		{
			long imageSize = JRPrinterAWT.getImageSize(this.jasperPrint, this.realZoom);
			renderImage = imageSize <= maxImageSize;
		}

		this.lblPage.setRenderImage(renderImage);

		if (renderImage)
		{
			setPageImage();
		}

		this.pnlLinks.removeAll();
		this.linksMap = new HashMap();

		createHyperlinks();

		if (!renderImage)
		{
			this.lblPage.setIcon(null);

			this.pnlMain.validate();
			this.pnlMain.repaint();
		}
	}


	protected void setPageImage()
	{
		Image image;
		if (this.pageError)
		{
			image = getPageErrorImage();
		}
		else
		{
			try
			{
				image = JasperPrintManager.printPageToImage(this.jasperPrint, this.pageIndex, this.realZoom);
			}
			catch (Exception e)
			{
				this.pageError = true;
				e.printStackTrace();

				image = getPageErrorImage();
				JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("error.displaying"));
			}
		}
		ImageIcon imageIcon = new ImageIcon(image);
		this.lblPage.setIcon(imageIcon);
	}

	protected Image getPageErrorImage()
	{
		Image image = new BufferedImage(
				(int) (this.jasperPrint.getPageWidth() * this.realZoom) + 1,
				(int) (this.jasperPrint.getPageHeight() * this.realZoom) + 1,
				BufferedImage.TYPE_INT_RGB
				);
		
		Graphics2D grx = (Graphics2D) image.getGraphics();
		AffineTransform transform = new AffineTransform();
		transform.scale(this.realZoom, this.realZoom);
		grx.transform(transform);

		drawPageError((Graphics2D) grx);
		
		return image;
	}

	protected void createHyperlinks()
	{
		java.util.List pages = this.jasperPrint.getPages();
		JRPrintPage page = (JRPrintPage)pages.get(this.pageIndex);
		createHyperlinks(page.getElements(), 0, 0);
	}

	protected void createHyperlinks(List elements, int offsetX, int offsetY)
	{
		if(elements != null && elements.size() > 0)
		{
			for(Iterator it = elements.iterator(); it.hasNext();)
			{
				JRPrintElement element = (JRPrintElement)it.next();

				JRImageMapRenderer imageMap = null;
				if (element instanceof JRPrintImage)
				{
					JRRenderable renderer = ((JRPrintImage) element).getRenderer();
					if (renderer instanceof JRImageMapRenderer)
					{
						imageMap = (JRImageMapRenderer) renderer;
						if (!imageMap.hasImageAreaHyperlinks())
						{
							imageMap = null;
						}
					}
				}
				boolean hasImageMap = imageMap != null;

				JRPrintHyperlink hyperlink = null;
				if (!hasImageMap && element instanceof JRPrintHyperlink)
				{
					hyperlink = (JRPrintHyperlink) element;
				}
				boolean hasHyperlink = hyperlink != null && hyperlink.getHyperlinkType() != JRHyperlink.HYPERLINK_TYPE_NONE;

				if (hasHyperlink || hasImageMap)
				{
					JPanel link;
					if (hasHyperlink)
					{
						link = new JPanel();
						link.addMouseListener(this.mouseListener);
					}
					else //hasImageMap
					{
						Rectangle renderingArea = new Rectangle(0, 0, element.getWidth(), element.getHeight());
						link = new ImageMapPanel(renderingArea, imageMap);
					}

					if (hasHyperlink)
					{
						link.setCursor(new Cursor(Cursor.HAND_CURSOR));
					}

					link.setLocation(
						(int)((element.getX() + offsetX) * this.realZoom),
						(int)((element.getY() + offsetY) * this.realZoom)
						);
					link.setSize(
						(int)(element.getWidth() * this.realZoom),
						(int)(element.getHeight() * this.realZoom)
						);
					link.setOpaque(false);

					String toolTip;
					if (hasHyperlink)
					{
						toolTip = getHyperlinkTooltip(hyperlink);
					}
					else //hasImageMap
					{
						toolTip = "";//not null to register the panel as having a tool tip
					}
					link.setToolTipText(toolTip);

					this.pnlLinks.add(link);
					this.linksMap.put(link, element);
				}

				if (element instanceof JRPrintFrame)
				{
					JRPrintFrame frame = (JRPrintFrame) element;
					int frameOffsetX = offsetX + frame.getX() + frame.getLineBox().getLeftPadding().intValue();
					int frameOffsetY = offsetY + frame.getY() + frame.getLineBox().getTopPadding().intValue();
					createHyperlinks(frame.getElements(), frameOffsetX, frameOffsetY);
				}
			}
		}
	}


	protected class ImageMapPanel extends JPanel implements MouseListener, MouseMotionListener
	{
		private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

		protected final List imageAreaHyperlinks;

		public ImageMapPanel(Rectangle renderingArea, JRImageMapRenderer imageMap)
		{
			try
			{
				this.imageAreaHyperlinks = imageMap.getImageAreaHyperlinks(renderingArea);//FIXMECHART
			}
			catch (JRException e)
			{
				throw new JRRuntimeException(e);
			}

			addMouseListener(this);
			addMouseMotionListener(this);
		}

		public String getToolTipText(MouseEvent event)
		{
			String tooltip = null;
			JRPrintImageAreaHyperlink imageMapArea = getImageMapArea(event);
			if (imageMapArea != null)
			{
				tooltip = getHyperlinkTooltip(imageMapArea.getHyperlink());
			}

			if (tooltip == null)
			{
				tooltip = super.getToolTipText(event);
			}

			return tooltip;
		}

		public void mouseDragged(MouseEvent e)
		{
			pnlLinksMouseDragged(e);
		}

		public void mouseMoved(MouseEvent e)
		{
			JRPrintImageAreaHyperlink imageArea = getImageMapArea(e);
			if (imageArea != null
					&& imageArea.getHyperlink().getHyperlinkType() != JRHyperlink.HYPERLINK_TYPE_NONE)
			{
				e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			else
			{
				e.getComponent().setCursor(Cursor.getDefaultCursor());
			}
		}

		protected JRPrintImageAreaHyperlink getImageMapArea(MouseEvent e)
		{
			return getImageMapArea((int) (e.getX() / JRViewer.this.realZoom), (int) (e.getY() / JRViewer.this.realZoom));
		}

		protected JRPrintImageAreaHyperlink getImageMapArea(int x, int y)
		{
			JRPrintImageAreaHyperlink image = null;
			if (this.imageAreaHyperlinks != null)
			{
				for (ListIterator it = this.imageAreaHyperlinks.listIterator(this.imageAreaHyperlinks.size()); image == null && it.hasPrevious();)
				{
					JRPrintImageAreaHyperlink area = (JRPrintImageAreaHyperlink) it.previous();
					if (area.getArea().containsPoint(x, y))
					{
						image = area;
					}
				}
			}
			return image;
		}

		public void mouseClicked(MouseEvent e)
		{
			JRPrintImageAreaHyperlink imageMapArea = getImageMapArea(e);
			if (imageMapArea != null)
			{
				hyperlinkClicked(imageMapArea.getHyperlink());
			}
		}

		public void mouseEntered(MouseEvent e)
		{
		}

		public void mouseExited(MouseEvent e)
		{
		}

		public void mousePressed(MouseEvent e)
		{
			e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			pnlLinksMousePressed(e);
		}

		public void mouseReleased(MouseEvent e)
		{
			e.getComponent().setCursor(Cursor.getDefaultCursor());
			pnlLinksMouseReleased(e);
		}
	}


	protected String getHyperlinkTooltip(JRPrintHyperlink hyperlink)
	{
		String toolTip;
		toolTip = hyperlink.getHyperlinkTooltip();
		if (toolTip == null)
		{
			toolTip = getFallbackTooltip(hyperlink);
		}
		return toolTip;
	}


	protected String getFallbackTooltip(JRPrintHyperlink hyperlink)
	{
		String toolTip = null;
		switch(hyperlink.getHyperlinkType())
		{
			case JRHyperlink.HYPERLINK_TYPE_REFERENCE :
			{
				toolTip = hyperlink.getHyperlinkReference();
				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_LOCAL_ANCHOR :
			{
				if (hyperlink.getHyperlinkAnchor() != null)
				{
					toolTip = "#" + hyperlink.getHyperlinkAnchor();
				}
				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_LOCAL_PAGE :
			{
				if (hyperlink.getHyperlinkPage() != null)
				{
					toolTip = "#page " + hyperlink.getHyperlinkPage();
				}
				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_REMOTE_ANCHOR :
			{
				toolTip = "";
				if (hyperlink.getHyperlinkReference() != null)
				{
					toolTip = toolTip + hyperlink.getHyperlinkReference();
				}
				if (hyperlink.getHyperlinkAnchor() != null)
				{
					toolTip = toolTip + "#" + hyperlink.getHyperlinkAnchor();
				}
				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_REMOTE_PAGE :
			{
				toolTip = "";
				if (hyperlink.getHyperlinkReference() != null)
				{
					toolTip = toolTip + hyperlink.getHyperlinkReference();
				}
				if (hyperlink.getHyperlinkPage() != null)
				{
					toolTip = toolTip + "#page " + hyperlink.getHyperlinkPage();
				}
				break;
			}
			default :
			{
				break;
			}
		}
		return toolTip;
	}


	/**
	*/
	private void emptyContainer(Container container)
	{
		Component[] components = container.getComponents();

		if (components != null)
		{
			for(int i = 0; i < components.length; i++)
			{
				if (components[i] instanceof Container)
				{
					emptyContainer((Container)components[i]);
				}
			}
		}

		components = null;
		container.removeAll();
		container = null;
	}


	/**
	*/
	private float getZoomRatio()
	{
		float newZoom = this.zoom;

		try
		{
			newZoom =
				this.zoomDecimalFormat.parse(
					String.valueOf(this.cmbZoom.getEditor().getItem())
					).floatValue() / 100f;
		}
		catch(ParseException e)
		{
		}

		return newZoom;
	}


	/**
	*/
	public void setZoomRatio(float newZoom)
	{
		if (newZoom > 0)
		{
			this.cmbZoom.getEditor().setItem(
				this.zoomDecimalFormat.format(newZoom * 100) + "%"
				);

			if (this.zoom != newZoom)
			{
				this.zoom = newZoom;
				this.realZoom = this.zoom * this.screenResolution / REPORT_RESOLUTION;

				refreshPage();
			}
		}
	}


	/**
	*/
	private void setRealZoomRatio(float newZoom)
	{
		if (newZoom > 0 && this.realZoom != newZoom)
		{
			this.zoom = newZoom * REPORT_RESOLUTION / this.screenResolution;
			this.realZoom = newZoom;

			this.cmbZoom.getEditor().setItem(
				this.zoomDecimalFormat.format(this.zoom * 100) + "%"
				);

			refreshPage();
		}
	}


	/**
	 *
	 */
	public void setFitWidthZoomRatio()
	{
		setRealZoomRatio(((float)this.pnlInScroll.getVisibleRect().getWidth() - 20f) / this.jasperPrint.getPageWidth());

	}

	public void setFitPageZoomRatio()
	{
		setRealZoomRatio(((float)this.pnlInScroll.getVisibleRect().getHeight() - 20f) / this.jasperPrint.getPageHeight());
	}


	/**
	 * 
	 */
	protected JRGraphics2DExporter getGraphics2DExporter() throws JRException
	{
		return new JRGraphics2DExporter();
	}

	/**
	 * 
	 */
	protected void paintPage(Graphics2D grx)
	{
		if (this.pageError)
		{
			paintPageError(grx);
			return;
		}
		
		try
		{
			if (this.exporter == null)
			{
				this.exporter = getGraphics2DExporter();
			}
			else
			{
				this.exporter.reset();
			}

			this.exporter.setParameter(JRExporterParameter.JASPER_PRINT, this.jasperPrint);
			this.exporter.setParameter(JRGraphics2DExporterParameter.GRAPHICS_2D, grx.create());
			this.exporter.setParameter(JRExporterParameter.PAGE_INDEX, Integer.valueOf(this.pageIndex));
			this.exporter.setParameter(JRGraphics2DExporterParameter.ZOOM_RATIO, Float.valueOf(this.realZoom));
			this.exporter.setParameter(JRExporterParameter.OFFSET_X, Integer.valueOf(1)); //lblPage border
			this.exporter.setParameter(JRExporterParameter.OFFSET_Y, Integer.valueOf(1));
			this.exporter.exportReport();
		}
		catch(Exception e)
		{
			this.pageError = true;
			e.printStackTrace();
			
			paintPageError(grx);
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					JOptionPane.showMessageDialog(JRViewer.this, getBundleString("error.displaying"));
				}
			});
		}

	}

	protected void paintPageError(Graphics2D grx)
	{
		AffineTransform origTransform = grx.getTransform();
		
		AffineTransform transform = new AffineTransform();
		transform.translate(1, 1);
		transform.scale(this.realZoom, this.realZoom);
		grx.transform(transform);
		
		try
		{
			drawPageError(grx);
		}
		finally
		{
			grx.setTransform(origTransform);
		}
	}

	protected void drawPageError(Graphics grx)
	{
		grx.setColor(Color.white);
		grx.fillRect(0, 0, this.jasperPrint.getPageWidth() + 1, this.jasperPrint.getPageHeight() + 1);
	}

	protected void keyNavigate(KeyEvent evt)
	{
		boolean refresh = true;
		switch (evt.getKeyCode())
		{
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_PAGE_DOWN:
			dnNavigate(evt);
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_PAGE_UP:
			upNavigate(evt);
			break;
		case KeyEvent.VK_HOME:
			homeEndNavigate(0);
			break;
		case KeyEvent.VK_END:
			homeEndNavigate(this.jasperPrint.getPages().size() - 1);
			break;
		default:
			refresh = false;
		}
		
		if (refresh)
		{
			refreshPage();
		}
	}

	private void dnNavigate(KeyEvent evt)
	{
		int bottomPosition = this.scrollPane.getVerticalScrollBar().getValue();
		this.scrollPane.dispatchEvent(evt);
		if((this.scrollPane.getViewport().getHeight() > this.pnlPage.getHeight() ||
				this.scrollPane.getVerticalScrollBar().getValue() == bottomPosition) &&
				this.pageIndex < this.jasperPrint.getPages().size() - 1)
		{
			setPageIndex(this.pageIndex + 1);
			if(this.scrollPane.isEnabled())
				this.scrollPane.getVerticalScrollBar().setValue(0);
		}
	}

	private void upNavigate(KeyEvent evt)
	{
		if((this.scrollPane.getViewport().getHeight() > this.pnlPage.getHeight() ||
				this.scrollPane.getVerticalScrollBar().getValue() == 0) &&
				this.pageIndex > 0)
		{
			setPageIndex(this.pageIndex - 1);
			if(this.scrollPane.isEnabled())
				this.scrollPane.getVerticalScrollBar().setValue(this.scrollPane.getVerticalScrollBar().getMaximum());
		}
		else
		{
			this.scrollPane.dispatchEvent(evt);
		}
	}

	private void homeEndNavigate(int pageNumber)
	{
		setPageIndex(pageNumber);
		if(this.scrollPane.isEnabled())
			this.scrollPane.getVerticalScrollBar().setValue(0);
	}

	/**
	 *
	*/
	private void fitPage(){
		float heightRatio = ((float)this.pnlInScroll.getVisibleRect().getHeight() - 20f) / this.jasperPrint.getPageHeight();
		float widthRatio = ((float)this.pnlInScroll.getVisibleRect().getWidth() - 20f) / this.jasperPrint.getPageWidth();
		setRealZoomRatio(heightRatio < widthRatio ? heightRatio : widthRatio);
	}

	/**
	*/
	class PageRenderer extends JLabel
	{
		private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

		private boolean renderImage;
		JRViewer viewer = null;

		public PageRenderer(JRViewer viewer)
		{
			this.viewer = viewer;
		}

		public void paintComponent(Graphics g)
		{
			if (isRenderImage())
			{
				super.paintComponent(g);
			}
			else
			{
				this.viewer.paintPage((Graphics2D)g.create());
			}
		}

		public boolean isRenderImage()
		{
			return this.renderImage;
		}

		public void setRenderImage(boolean renderImage)
		{
			this.renderImage = renderImage;
		}
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	protected javax.swing.JToggleButton btnActualSize;
	protected javax.swing.JButton btnFirst;
	protected javax.swing.JToggleButton btnFitPage;
	protected javax.swing.JToggleButton btnFitWidth;
	protected javax.swing.JButton btnLast;
	protected javax.swing.JButton btnNext;
	protected javax.swing.JButton btnPrevious;
	protected javax.swing.JButton btnPrint;
	protected javax.swing.JButton btnReload;
	protected javax.swing.JButton btnSave;
	protected javax.swing.JButton btnZoomIn;
	protected javax.swing.JButton btnZoomOut;
	protected javax.swing.JComboBox cmbZoom;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JPanel jPanel8;
	private javax.swing.JPanel jPanel9;
	private PageRenderer lblPage;
	protected javax.swing.JLabel lblStatus;
	private javax.swing.JPanel pnlInScroll;
	private javax.swing.JPanel pnlLinks;
	private javax.swing.JPanel pnlMain;
	private javax.swing.JPanel pnlPage;
	protected javax.swing.JPanel pnlSep01;
	protected javax.swing.JPanel pnlSep02;
	protected javax.swing.JPanel pnlSep03;
	protected javax.swing.JPanel pnlStatus;
	private javax.swing.JScrollPane scrollPane;
	protected javax.swing.JPanel tlbToolBar;
	protected javax.swing.JTextField txtGoTo;
	// End of variables declaration//GEN-END:variables

}
