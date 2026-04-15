/*
  This is the Main.java of the Main word processor.
  This is the main application
 */

package com.jmonkey.office.lexi;

// Java AWT Imports
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultDesktopManager;
import javax.swing.DesktopManager;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.jmonkey.export.Format;
import com.jmonkey.export.Registry;
import com.jmonkey.export.Runtime;
import com.jmonkey.office.help.OfficeHelp;
import com.jmonkey.office.lexi.support.ActionComboBox;
import com.jmonkey.office.lexi.support.ActionToolBar;
import com.jmonkey.office.lexi.support.Editor;
import com.jmonkey.office.lexi.support.EditorActionManager;
import com.jmonkey.office.lexi.support.FileActionListener;
import com.jmonkey.office.lexi.support.Mime;
import com.jmonkey.office.lexi.support.PropertySheetDialog;
import com.jmonkey.office.lexi.support.Splash;
import com.jmonkey.office.lexi.support.images.Loader;

public class Main extends JFrame implements ActionListener {
	// used by the document frame..
	// its up here because internal
	// classes can't have static members.
	private static int COUNTER = 0;
	private static int _FILE_HISTROY_COUNT = 0;

	private static MainDesktop _DESKTOP = null;
	private static JFileChooser _FILE_CHOOSER = null;
	private static JLabel _STATUS_LABEL = null;
	private static JToolBar _FILE_TOOL_BAR = null;
	private static JToolBar _FORMAT_TOOL_BAR = null;
	private static FileFilter[] _FILE_FILTERS = null;
	private static int _DOCUMENT_COUNT = 0;

	// Action listener for File Histroy items only.
	protected final ActionListener _FILE_HISTROY_ACTION =
		new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				//Code.debug("History: " + e.getActionCommand());
	try {
				getDesktopManager().editorOpen(new File(e.getActionCommand()));
			}
			catch (Throwable t) {
				//Code.failed("Open History File: " + e.getActionCommand());
			}
		}
	};

	// Action listener for Open Window items only.
	protected final ActionListener _OPEN_WINDOW_ACTION =
		new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				//Code.debug("Open Window: " + e.getActionCommand());
		// activate the window here...
	getDesktopManager().activateFrame(
		((DocumentManager) getDesktopManager()).getOpenDocument(
			e.getActionCommand()));
		}
	};

	// ====================================================

	// Use the Registry to store the previously opened files
	private Registry _REGISTRY = null;

	// The apps menu bar.
	private JMenuBar _MENU_BAR = null;

	// File Histroy menu.
	protected JMenu _FILE_HISTORY = null;

	// Open document list menu.
	private JMenu _OPEN_WINDOWS = null;

	/**
	 *DocumentFrame.class  provides the internal frame for our application.
	 *@see javax.swing.event.InternalFrameEvent
	*@see java.awt.event.FocusEvent
	*@author Matthew Schmidt
	**/
	protected final class DocumentFrame
		extends JInternalFrame
		implements InternalFrameListener, FocusListener, VetoableChangeListener {
		private Main _APP = null;
		// This can't be static, or all instances
		// will use the same StyledDocument.
		private Editor _EDITOR;
		private JLabel STATUS = null;
		private boolean _VETO = true;

		private int fileLen;
		private JFileChooser chooser;

		public DocumentFrame(Main app, String contentType) {
			super();
			_APP = app;

			this.setFrameIcon(new ImageIcon(Loader.load("text_window16.gif")));

			this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			//DISPOSE_ON_CLOSE

			this.setIconifiable(true);
			this.setMaximizable(true);
			this.setResizable(true);
			this.setClosable(true);

			this.addInternalFrameListener(this);
			this.addFocusListener(this);
			this.addVetoableChangeListener(this);

			_EDITOR = Editor.getEditorForContentType(contentType);

			// We need to check focus on the editor as well,
			// so the frame comes to the front when the editor
			// is clicked, as well as when the frame is clicked.
			_EDITOR.addFocusListener(this);
			this.setContentPane(_EDITOR);

			// Added... just to make it
			// get focus when first opened...
			_EDITOR.activate(); //requestFocus();
			//_EDITOR.requestFocus();
			//this.activate();
		}

		public String getName() {
			return this.getTitle();
		}

		/**
		 * Returns the editor of the DocumentFrame.
		 * @return com.jmonkey.common.StyledEditor
		 */
		protected Editor getEditor() {
			return _EDITOR;
		}

		public void vetoableChange(PropertyChangeEvent evt)
			throws PropertyVetoException {
			////Code.event(evt);
			////Code.message(evt.getPropertyName());
			if (evt.getPropertyName().equals("closed")) {
				if (_VETO) {
					if (!_APP.getDesktopManager().closeActiveDocument()) {
						throw new PropertyVetoException("closed", evt);
					}
					_VETO = !_VETO;
				}
				else {
					_VETO = !_VETO;
				}
			}
		}

		public void internalFrameOpened(InternalFrameEvent e) {
		}

		/**
		 * Should Handle saving whatever is in the editor.
		 **/
		public void internalFrameClosing(InternalFrameEvent e) {
			try {
				//Code.event(e); //paramString()
				//Code.message(e.paramString());
				//((DocumentManager)_APP.getDesktopManager())
				//this.setClosed(_APP.getDesktopManager().closeActiveDocument());
				this.dispose();
			}
			catch (java.lang.NullPointerException nullp) {
				return;
			}
			//    } catch  (java.beans.PropertyVetoException propV) {
			//	return;
			//    }
		}

		public void internalFrameClosed(InternalFrameEvent e) {
		}

		public void internalFrameIconified(InternalFrameEvent e) {

		}

		public void internalFrameDeiconified(InternalFrameEvent e) {
		}

		/**
		 *Calls this.activate() to make sure the editor receives focus along with the frame
		 **/
		public void internalFrameActivated(InternalFrameEvent e) {
			this.activate();
		}

		public void internalFrameDeactivated(InternalFrameEvent e) {
		}

		public void focusGained(FocusEvent e) {
			activate();
			if (!e.isTemporary()) {
				this.activate();
			}
		}

		public void focusLost(FocusEvent e) {
		}

		/**
		 *Activates this frame and makes sure the
		 * editor gets focused along with the frame
		 **/
		public void activate() {
			this.moveToFront();
			try {
				this.setSelected(true);
			}
			catch (java.beans.PropertyVetoException pve0) {
			}

			_EDITOR.activate(); //requestFocus();
			//_EDITOR.requestFocus();

			// Added this... no need to activate
			// this frame if its already active.
			if (((DocumentManager) _APP.getDesktopManager()).active()
				!= this) {
				_APP.getDesktopManager().activateFrame(this);
			}

		}
	}

	/**
	*Keeps a list of the active frames.
	*/
	protected final class DocumentManager
		extends DefaultDesktopManager
		implements DesktopManager, FileActionListener {
		private Main _PARENT = null;
		private Vector _DOC_LIST = null;
		private DocumentFrame _CUR_DOC = null;

		/**
		 * DocumentManager constructor comment.
		 */
		public DocumentManager(Main parent) {
			super();
			_PARENT = parent;
			this.init();
		}

		public void editorNew() {
			//Code.debug("editorNew");
			this.createDocumentFrame();
		}

		public void editorOpen(File file) {
			//Code.debug("editorOpen(File)");
			if (file != null) {
				String mime = Mime.findContentType(file);
				try {
					this
						.createDocumentFrame(null, file.getName(), mime)
						.getEditor()
						.read(
						file);
					_PARENT.addToFileHistory(file);
				}
				catch (IOException ioe0) {
					JOptionPane.showMessageDialog(
						_PARENT,
						"Exception\n" + ioe0.getMessage(),
						"Exception",
						JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		public void editorOpen() {
			//Code.debug("editorOpen");

			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle(
				_PARENT.getRegistry().getString(
					"MAIN",
					"dialog.open.title",
					"Open File..."));
			chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			FileFilter filter = null;
			filter =
				new DynamicFileFilter(
					"java c cc cpp h txt text",
					"Plain Text Format (*.txt, *.text)");
			chooser.addChoosableFileFilter(filter);
			filter = new DynamicFileFilter("rtf", "Rich Text Format (*.rtf)");
			chooser.addChoosableFileFilter(filter);
			filter =
				new DynamicFileFilter(
					"htm html shtml",
					"Hypertext Format (*.html, *.htm)");
			chooser.addChoosableFileFilter(filter);
			filter = chooser.getAcceptAllFileFilter();
			chooser.addChoosableFileFilter(filter);

			// Set the current directory to the default document directory.
			chooser.setCurrentDirectory(
				new File(
					Runtime.ensureDirectory(
						_PARENT.getRegistry().getString(
							"USER",
							"default.documents.directory",
							System.getProperty("user.home")
								+ File.separator
								+ "documents"))));

			//chooser.setSelectedFile(new File("*." + _PARENT.getRegistry().getString("default.filefilter.ext", "txt")));

			chooser.showOpenDialog(_PARENT); // showDialog(this, null);
			File fileToOpen = chooser.getSelectedFile();
			if (fileToOpen != null) {
				String mime = Mime.findContentType(fileToOpen);
				try {
					this
						.createDocumentFrame(null, fileToOpen.getName(), mime)
						.getEditor()
						.read(
						fileToOpen);
					_PARENT.addToFileHistory(fileToOpen);
				}
				catch (IOException ioe0) {
					JOptionPane.showMessageDialog(
						_PARENT,
						"Exception\n" + ioe0.getMessage(),
						"Exception",
						JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		public void editorOpenAs() {
			//Code.debug("editorOpenAs");

			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle(
				_PARENT.getRegistry().getString(
					"MAIN",
					"dialog.openas.title",
					"Open File As..."));
			chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			FileFilter filter = null;
			filter =
				new DynamicFileFilter("*", "Any File As Plain Text (*.*)");
			chooser.addChoosableFileFilter(filter);
			filter =
				new DynamicFileFilter("*", "Any File As Rich Text (*.rtf)");
			chooser.addChoosableFileFilter(filter);
			filter =
				new DynamicFileFilter("*", "Any File As Hypertext (*.html)");
			chooser.addChoosableFileFilter(filter);
			filter = chooser.getAcceptAllFileFilter();
			chooser.addChoosableFileFilter(filter);

			//_PARENT.getFileChooser().setFileFilter(_PARENT.getFileFilters()[3]);

			// Set the current directory to the default document directory.
			chooser.setCurrentDirectory(
				new File(
					Runtime.ensureDirectory(
						_PARENT.getRegistry().getString(
							"USER",
							"default.documents.directory",
							System.getProperty("user.home")
								+ File.separator
								+ "documents"))));

			//_PARENT.getFileChooser().setSelectedFile(new File(""));

			chooser.showOpenDialog(_PARENT); //showDialog(this, null);
			File fileToOpen = chooser.getSelectedFile();
			if (fileToOpen != null) {
				String desc = chooser.getFileFilter().getDescription();
				String mime = "text/plain";

				if (desc.startsWith("Any File As Plain Text (*.*)")) {
					mime = "text/plain";
				}
				else if (desc.startsWith("Any File As Rich Text (*.rtf)")) {
					mime = "text/rtf";
				}
				else if (desc.startsWith("Any File As Hypertext (*.html)")) {
					mime = "text/html";
				}
				else {
					mime = "text/plain";
				}

				try {
					this
						.createDocumentFrame(null, fileToOpen.getName(), mime)
						.getEditor()
						.read(
						fileToOpen);
					// Add the opened file to the histroy menu.
					_PARENT.addToFileHistory(fileToOpen);
				}
				catch (IOException ioe0) {
					JOptionPane.showMessageDialog(
						_PARENT,
						"Exception\n" + ioe0.getMessage(),
						"Exception",
						JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		public void editorRevert(Editor editor) {
			//Code.debug("editorRevert");
			if (editor.getFile() == null) {
				JOptionPane.showMessageDialog(
					_PARENT,
					_PARENT.getRegistry().getString(
						"MAIN",
						"dialog.revert.warning.0",
						"The document has not yet\nbeen saved you must\nsave the file before\nyou can revert to\nthe saved version."),
					"Bad State",
					JOptionPane.ERROR_MESSAGE);
			}
			else {
				switch (JOptionPane
					.showConfirmDialog(
						getParent(),
						_PARENT.getRegistry().getString(
							"MAIN",
							"dialog.revert.warning.1",
							"You are about to revert to a\nsaved version of this document.\nAll changes will be lost.\nAre you sure you want\nto do this?"),
						"Revert to Saved?",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE)) {
					case JOptionPane.YES_OPTION :
						if (!editor.isEmpty()) {
							// first clear the contents of the file.
							editor.getTextComponent().setText("");
						}
						try {
							editor.read(editor.getFile());
						}
						catch (IOException ioe0) {
							JOptionPane.showMessageDialog(
								_PARENT,
								"Exception\n" + ioe0.getMessage(),
								"Exception",
								JOptionPane.ERROR_MESSAGE);
						}
						break;
					case JOptionPane.NO_OPTION :
					case JOptionPane.CANCEL_OPTION :
					case JOptionPane.CLOSED_OPTION :
						break;
				}
			}
		}

		public void editorSave(Editor editor) {
			//Code.debug("editorSave");
			if (editor.getFile() == null) {

				JFileChooser chooser =
					new JFileChooser(
						_PARENT.getRegistry().getString(
							"MAIN",
							"dialog.saveas.title",
							"Save File As..."));
				chooser.setDialogType(JFileChooser.SAVE_DIALOG);
				chooser.setFileSelectionMode(
					JFileChooser.FILES_AND_DIRECTORIES);

				// Set the current directory to the default document directory.
				chooser.setCurrentDirectory(
					new File(
						Runtime.ensureDirectory(
							_PARENT.getRegistry().getString(
								"USER",
								"default.documents.directory",
								System.getProperty("user.home")
									+ File.separator
									+ "documents"))));

				//_PARENT.getFileChooser().setSelectedFile(null);
				// FileFilter filter = null;
				String ct = editor.getContentType();
				String defaultExt = "*.*";
				if (ct.equals("text/rtf")) {
					chooser.setFileFilter(
						new DynamicFileFilter(
							"rtf",
							"Rich Text Format (*.rtf)"));
					defaultExt = "*.rtf";
				}
				else if (ct.equals("text/html")) {
					chooser.setFileFilter(
						new DynamicFileFilter(
							"htm,html,shtml",
							"Hypertext Format (*.html, *.htm)"));
					defaultExt = "*.html";
				}
				else if (ct.equals("text/plain")) {
					chooser.setFileFilter(
						new DynamicFileFilter(
							"java c cc cpp h txt text",
							"Plain Text Format (*.txt, *.text)"));
					defaultExt = "*.txt";
				}
				else {
					chooser.setFileFilter(chooser.getAcceptAllFileFilter());
					defaultExt = "*.*";
				}
				//chooser.addChoosableFileFilter(filter);

				// Set the default file name.
				chooser.setSelectedFile(new File(defaultExt));

				chooser.showSaveDialog(_PARENT);
				File fileToOpen = chooser.getSelectedFile();
				//if(!fileToOpen.getName().equals(defaultExt)){
				String mime = Mime.findContentType(fileToOpen);

				if (!fileToOpen.getName().startsWith("*")) {
					if (!ct.equals(mime)) {
						switch (JOptionPane
							.showConfirmDialog(
								_PARENT,
								_PARENT.getRegistry().getString(
									"MAIN",
									"dialog.save.warning.0",
									"The extension of the file you specified\ndoes not match the content type of\nthe document. If you use this extension,\nyou may have trouble reopening the file\nat a later time.\nAre you sure you want\nto do this?"),
								"Extension Mismatch?",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.WARNING_MESSAGE)) {
							case JOptionPane.YES_OPTION :
								try {
									editor.write(fileToOpen);
									editor.setFile(fileToOpen);
								}
								catch (IOException ioe0) {
									JOptionPane.showMessageDialog(
										_PARENT,
										"Exception\n" + ioe0.getMessage(),
										"Exception",
										JOptionPane.ERROR_MESSAGE);
								}
								break;
							case JOptionPane.NO_OPTION :
							case JOptionPane.CANCEL_OPTION :
							case JOptionPane.CLOSED_OPTION :
								break;
						}
					}
					else {
						try {
							editor.write(fileToOpen);
							editor.setFile(fileToOpen);
						}
						catch (IOException ioe0) {
							JOptionPane.showMessageDialog(
								_PARENT,
								"Exception\n" + ioe0.getMessage(),
								"Exception",
								JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				else {
					// Can't start a file with a *
				}
			}
			else {
				if (editor.isNew() || editor.isChanged()) {
					try {
						editor.write(editor.getFile());
					}
					catch (IOException ioe0) {
						JOptionPane.showMessageDialog(
							_PARENT,
							"Exception\n" + ioe0.getMessage(),
							"Exception",
							JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}

		public void editorSaveAs(Editor editor) {
			//Code.debug("editorSaveAs");

			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle(
				_PARENT.getRegistry().getString(
					"MAIN",
					"dialog.saveas.title",
					"Save File As..."));
			chooser.setDialogType(JFileChooser.SAVE_DIALOG);
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			// Set the current directory to the default document directory.
			chooser.setCurrentDirectory(
				new File(
					Runtime.ensureDirectory(
						_PARENT.getRegistry().getString(
							"USER",
							"default.documents.directory",
							System.getProperty("user.home")
								+ File.separator
								+ "documents"))));

			//_PARENT.getFileChooser().setSelectedFile(null);

			String ct = editor.getContentType();
			String defaultExt = "*.*";
			if (ct.equals("text/rtf")) {
				chooser.setFileFilter(
					new DynamicFileFilter("rtf", "Rich Text Format (*.rtf)"));
				defaultExt = "*.rtf";
			}
			else if (ct.equals("text/html")) {
				chooser.setFileFilter(
					new DynamicFileFilter(
						"htm,html,shtml",
						"Hypertext Format (*.html, *.htm)"));
				defaultExt = "*.html";
			}
			else if (ct.equals("text/plain")) {
				chooser.setFileFilter(
					new DynamicFileFilter(
						"java c cc cpp h txt text",
						"Plain Text Format (*.txt, *.text)"));
				defaultExt = "*.txt";
			}
			else {
				chooser.setFileFilter(chooser.getAcceptAllFileFilter());
				defaultExt = "*.*";
			}

			// Set the default file name.
			chooser.setSelectedFile(new File(defaultExt));

			chooser.showSaveDialog(_PARENT);
			File fileToOpen = chooser.getSelectedFile();

			String mime = Mime.findContentType(fileToOpen);
			if (!fileToOpen.getName().startsWith("*")) {
				if (!ct.equals(mime)) {
					switch (JOptionPane
						.showConfirmDialog(
							_PARENT,
							_PARENT.getRegistry().getString(
								"MAIN",
								"dialog.save.warning.0",
								"The extension of the file you specified\ndoes not match the content type of\nthe document. If you use this extension,\nyou may have trouble reopening the file\nat a later time.\nAre you sure you want\nto do this?"),
							"Extension Mismatch?",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.WARNING_MESSAGE)) {
						case JOptionPane.YES_OPTION :
							try {
								editor.write(fileToOpen);
								editor.setFile(fileToOpen);
							}
							catch (IOException ioe0) {
								JOptionPane.showMessageDialog(
									_PARENT,
									"Exception\n" + ioe0.getMessage(),
									"Exception",
									JOptionPane.ERROR_MESSAGE);
							}
							break;
						case JOptionPane.NO_OPTION :
						case JOptionPane.CANCEL_OPTION :
						case JOptionPane.CLOSED_OPTION :
							break;
					}
				}
				else {
					try {
						editor.write(fileToOpen);
						editor.setFile(fileToOpen);
					}
					catch (IOException ioe0) {
						JOptionPane.showMessageDialog(
							_PARENT,
							"Exception\n" + ioe0.getMessage(),
							"Exception",
							JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}

		public void editorSaveCopy(Editor editor) {
			//Code.debug("editorSaveCopy");

			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle(
				_PARENT.getRegistry().getString(
					"MAIN",
					"dialog.savecopy.title",
					"Save Copy As..."));
			chooser.setDialogType(JFileChooser.SAVE_DIALOG);
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			// Set the current directory to the default document directory.
			chooser.setCurrentDirectory(
				new File(
					Runtime.ensureDirectory(
						_PARENT.getRegistry().getString(
							"USER",
							"default.documents.directory",
							System.getProperty("user.home")
								+ File.separator
								+ "documents"))));

			//_PARENT.getFileChooser().setSelectedFile(null);

			String ct = editor.getContentType();
			String defaultExt = "*.*";
			if (ct.equals("text/rtf")) {
				chooser.setFileFilter(
					new DynamicFileFilter("rtf", "Rich Text Format (*.rtf)"));
				defaultExt = "*.rtf";
			}
			else if (ct.equals("text/html")) {
				chooser.setFileFilter(
					new DynamicFileFilter(
						"htm,html,shtml",
						"Hypertext Format (*.html, *.htm)"));
				defaultExt = "*.html";
			}
			else if (ct.equals("text/plain")) {
				chooser.setFileFilter(
					new DynamicFileFilter(
						"java c cc cpp h txt text",
						"Plain Text Format (*.txt, *.text)"));
				defaultExt = "*.txt";
			}
			else {
				chooser.setFileFilter(chooser.getAcceptAllFileFilter());
				defaultExt = "*.*";
			}

			// Set the default file name.
			chooser.setSelectedFile(new File(defaultExt));

			chooser.showSaveDialog(_PARENT);
			File fileToOpen = chooser.getSelectedFile();

			String mime = Mime.findContentType(fileToOpen);
			if (!fileToOpen.getName().startsWith("*")) {
				if (!ct.equals(mime)) {
					switch (JOptionPane
						.showConfirmDialog(
							_PARENT,
							_PARENT.getRegistry().getString(
								"MAIN",
								"dialog.save.warning.0",
								"The extension of the file you specified\ndoes not match the content type of\nthe document. If you use this extension,\nyou may have trouble reopening the file\nat a later time.\nAre you sure you want\nto do this?"),
							"Extension Mismatch?",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.WARNING_MESSAGE)) {
						case JOptionPane.YES_OPTION :
							try {
								editor.write(fileToOpen);
								//editor.setFile(fileToOpen);
							}
							catch (IOException ioe0) {
								JOptionPane.showMessageDialog(
									_PARENT,
									"Exception\n" + ioe0.getMessage(),
									"Exception",
									JOptionPane.ERROR_MESSAGE);
							}
							break;
						case JOptionPane.NO_OPTION :
						case JOptionPane.CANCEL_OPTION :
						case JOptionPane.CLOSED_OPTION :
							break;
					}
				}
				else {
					try {
						editor.write(fileToOpen);
						//editor.setFile(fileToOpen);
					}
					catch (IOException ioe0) {
						JOptionPane.showMessageDialog(
							_PARENT,
							"Exception\n" + ioe0.getMessage(),
							"Exception",
							JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
		/**
		 * Creates a new instance of DocumentFrame
		 * I changed this to create a generic frame,
		 * This way, we can remove all the frame
		 * creation code from every method that
		 * might need a new frame. Sets the frame's
		 * title to the name of the opened file.
		 * @param newName java.lang.String
		 * @param contentType java.lang.String
		 * @return com.jmonkey.office.lexi.Main.DocumentFrame
		 */
		protected final DocumentFrame createDocumentFrame(
			File file,
			String newName,
			String contentType) {
			//Code.debug("Creating New Document: " + newName);
			DocumentFrame doc = new DocumentFrame(_PARENT, contentType);
			doc.setIconifiable(true);
			doc.setResizable(true);
			doc.setMaximizable(true);
			doc.setTitle(newName);
			doc.setVisible(true);
			_PARENT._DESKTOP.add(doc, doc.getName());
			this.cascade(doc);
			_CUR_DOC = doc;
			_CUR_DOC.activate();
			_PARENT.updateOpenWindowsMenu();
			if (file != null) {
				if (file.exists() && file.isFile()) {
					this.editorOpen(file);
				}
			}
			return doc;
		}

		/**
		 * Creates a new document with the speified file.
		 */
		protected final DocumentFrame createDocumentFrame(File file) {
			return this.createDocumentFrame(
				file,
				_PARENT.getRegistry().getString(
					"MAIN",
					"new.document.title",
					"New Document")
					+ _PARENT.getDocumentNumber(),
				_PARENT.getRegistry().getString(
					"MAIN",
					"default.content.type",
					Editor.VALID_CONTENT_TYPES[2]));
		}

		/**
		 * Same as above, only keeps the title as New Document
		 */
		protected final DocumentFrame createDocumentFrame(String contentType) {
			// Removed the code here
			// so we don't duplicate
			// the code from the above
			// method. -- Brill 03/18/1999
			return this.createDocumentFrame(
				null,
				_PARENT.getRegistry().getString(
					"MAIN",
					"new.document.title",
					"New Document")
					+ _PARENT.getDocumentNumber(),
				contentType);
		}

		/**
		 * Same as above, only keeps the title as New Document
		 */
		protected final DocumentFrame createDocumentFrame() {
			// Removed the code here
			// so we don't duplicate
			// the code from the above
			// method. -- Brill 03/18/1999
			return this
				.createDocumentFrame(
					null,
					"New Document " + (_PARENT.getDocumentNumber() + 1)
			/*_PARENT.getRegistry().getString("MAIN", "new.document.title", "New Document ") + _PARENT.getDocumentNumber()*/
			,
_PARENT.getRegistry().getString(
				"MAIN",
				"default.content.type",
				Editor.VALID_CONTENT_TYPES[2]));
		}

		/**
		 * Returns a list of open documents in the system.
		 * @return java.lang.String[]
		 */
		public final String[] openDocumentList() {
			//Code.debug("Getting Open Document List...");
			Vector v = new Vector();
			Component[] comps = _PARENT._DESKTOP.getComponents();
			for (int i = 0; i < comps.length; i++) {
				try {
					if (comps[i] instanceof JInternalFrame) {
						v.addElement(((JInternalFrame) comps[i]).getTitle());
					}
				}
				catch (java.lang.ClassCastException cEX) {
				}

			}
			String[] names = new String[v.size()];
			v.copyInto(names);
			return names;

		}

		/**
		 * Returns the named DocumentFrame. The frame may or may not be active.
		 * @return com.jmonkey.office.Main.DocumentFrame
		 */
		public final DocumentFrame getOpenDocument(String name) {
			Component[] comps = _PARENT._DESKTOP.getComponents();
			for (int i = 0; i < comps.length; i++) {

				if (comps[i] instanceof JInternalFrame) {
					if (((JInternalFrame) comps[i]).getTitle().equals(name)) {
						return (DocumentFrame) ((JInternalFrame) comps[i]);
					}
				}
			}
			throw new IllegalStateException(
				"The document "
					+ name
					+ " does not exist, or no longer exists.");
		}

		/**
		 * activateFrame sets our current active frame.
		 */
		public void activateFrame(javax.swing.JInternalFrame f) {
			_CUR_DOC = (DocumentFrame) f;
			if (_PARENT != null) {
				//_PARENT.setTitle("[" + f.getTitle() + (_CUR_DOC.getEditor().isChanged() ? "] *" : "]"));
				this.documentChanged(_CUR_DOC, false);
			}
			super.activateFrame(f);
			_CUR_DOC.activate();
		}

		/**
		 * Returns the current active frame.
		 * @return com.jmonkey.office.Main.DocumentFrame
		 */
		protected final DocumentFrame active() {
			return _CUR_DOC;
		}

		/**
		 * Returns which application is the parent for this
		 * @return com.jmonkey.office.Main
		 */
		protected final Main getApp() {
			return _PARENT;
		}

		/**
		 * Starts off this whole mess
		 */
		private void init() {
			_DOC_LIST = new Vector();
		}

		protected final void documentChanged(
			DocumentFrame frame,
			boolean textSelected) {
			_PARENT.a_documentFrameChanged(frame, textSelected);
			//System.out.println("FrameChanged: " + frame.getTitle());
		}

		/**
		 * This method needs documentation.
		 * @param dframe com.jm.wp.DocumentFrame
		 */
		protected final void cascade(DocumentFrame dframe) {
			Dimension dsize = _PARENT._DESKTOP.getSize();
			int targetWidth = 3 * dsize.width / 4;
			int targetHeight = 3 * dsize.height / 4;
			int nextX = 0;
			int nextY = 0;
			if (_CUR_DOC != null) {
				if (_CUR_DOC.isMaximum()) {
					try {
						dframe.setMaximum(true);
					}
					catch (java.beans.PropertyVetoException pve0) {
					}
					return;
				}
				java.awt.Point p = _CUR_DOC.getLocation();
				nextX = p.x;
				nextY = p.y;

				// If the active frame is near the edge,
				// then we should cascade the new frame.
				nextX += 24;
				nextY += 24;
			}
			// Make sure we're not 'out of bounds'.
			if ((nextX + targetWidth > dsize.width)
				|| (nextY + targetHeight > dsize.height)) {
				nextX = 0;
				nextY = 0;
			}
			_PARENT._DESKTOP.getDesktopManager().setBoundsForFrame(
				dframe,
				nextX,
				nextY,
				targetWidth,
				targetHeight);
		}

		/**
		 * Show/Hide format toolbar action.
		 * @version 1.0 Revision 0
		 * @author Brill Pappin 21-APR-1999
		 */
		protected final class CascadeAction extends AbstractAction {
			public CascadeAction() {
				super("Cascade Windows");
			}

			public void actionPerformed(ActionEvent e) {
				cascadeAll();
			}
		}

		protected final Action getCascadeAction() {
			return new CascadeAction();
		}

		/**
		 * Cascade Windows
		 */
		protected final void cascadeAll() {
			Component[] comps = _PARENT._DESKTOP.getComponents();
			Dimension dsize = _PARENT._DESKTOP.getSize();
			int targetWidth = 3 * dsize.width / 4;
			int targetHeight = 3 * dsize.height / 4;
			int nextX = 0;
			int nextY = 0;
			for (int i = 0; i < comps.length; i++) {

				if (comps[i] instanceof JInternalFrame
					&& comps[i].isVisible()
					&& !((JInternalFrame) comps[i]).isIcon()) {
					if ((nextX + targetWidth > dsize.width)
						|| (nextY + targetHeight > dsize.height)) {
						nextX = 0;
						nextY = 0;
					}
					_PARENT._DESKTOP.getDesktopManager().setBoundsForFrame(
						(JComponent) comps[i],
						nextX,
						nextY,
						targetWidth,
						targetHeight);
					((JInternalFrame) comps[i]).toFront();
					nextX += 24;
					nextY += 24;
				}
			}
		}

		/**
		 * Action to close the active docunment.
		 * @version 1.0 Revision 0
		 * @author Brill Pappin 21-APR-1999
		 */
		protected final class CloseAction extends AbstractAction {
			public CloseAction() {
				super("Close");
			}

			public void actionPerformed(ActionEvent e) {
				closeActiveDocument();
			}
		}

		protected final Action getCloseAction() {
			return new CloseAction();
		}

		/**
		 * Close the current document.
		 */
		protected final boolean closeActiveDocument() {
			if (this.active().getEditor().isChanged()) {
				switch (JOptionPane
					.showConfirmDialog(
						_PARENT,
						"Document Changed!\n\""
							+ this.active().getTitle()
							+ "\"\nDo you want to save the changes?",
						"Save Changes?",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE)) {
					case JOptionPane.YES_OPTION :

						if (this.active().getEditor().isNew()) {
							this.editorSaveAs(this.active().getEditor());
						}
						else {
							this.editorSave(this.active().getEditor());
						}
						//this.active().setClosed(true);
						return !this.active().getEditor().isChanged();
					case JOptionPane.NO_OPTION :
						//this.active().setClosed(true);
						return true;
					case JOptionPane.CANCEL_OPTION :
					case JOptionPane.CLOSED_OPTION :
					default :
						return false;
				}
			}
			else {
				//this.editorSave(this.active().getEditor());
				return !this.active().getEditor().isChanged();
			}
		}

		/**
		 * Action to close all the docunments.
		 * @version 1.0 Revision 0
		 * @author Brill Pappin 21-APR-1999
		 */
		protected final class CloseAllAction extends AbstractAction {
			public CloseAllAction() {
				super("Close All");
			}

			public void actionPerformed(ActionEvent e) {
				closeAllDocuments();
			}
		}

		protected final Action getCloseAllAction() {
			return new CloseAllAction();
		}

		/**
		 * Close all document frames
		 * taken from the old JWord
		 * code, and need porting.
		 */
		protected final void closeAllDocuments() {
			Component[] comps = _PARENT._DESKTOP.getComponents();
			for (int i = 0; i < comps.length; i++) {
				if (comps[i] instanceof DocumentFrame /* JInternalFrame */
					&& comps[i].isVisible()
					&& ((JInternalFrame) comps[i]).isClosable()) {

					DocumentFrame actOnDoc = ((DocumentFrame) comps[i]);
					actOnDoc.activate();
					try {
						actOnDoc.setClosed(true);
					}
					catch (Throwable t) {
					}
					//this.closeActiveDocument();
					/*
					  if(actOnDoc.getEditor().isChanged()) {
					  switch (JOptionPane.showConfirmDialog(_PARENT, "Document Changed!\n\"" + actOnDoc.getTitle() + "\"\nDo you want to save the changes?", "Save Changes?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE)) {
					  case JOptionPane.YES_OPTION :
					
					  if(actOnDoc.getEditor().isNew()) {
					  this.editorSaveAs(actOnDoc.getEditor());
					  } else {
					  this.editorSave(actOnDoc.getEditor());
					  }
					  //return !actOnDoc.getEditor().isChanged();
					  case JOptionPane.NO_OPTION :
					  //return true;
					  case JOptionPane.CANCEL_OPTION :
					  case JOptionPane.CLOSED_OPTION :
					  default:
					  //return false;
					  }
					  } else {
					  this.editorSave(actOnDoc.getEditor());
					  //return !actOnDoc.getEditor().isChanged();
					  }
					*/

				}
			}
			// MISTAKE... DONT SET TO NULL, UNLESS WE KNOW ALL FRAMES ARE DEAD, OR WE SELECT ANOTHER FRAME.
			_CUR_DOC = null;
		}

		/**
		 * Show/Hide format toolbar action.
		 * @version 1.0 Revision 0
		 * @author Brill Pappin 21-APR-1999
		 */
		protected final class MinimizeAction extends AbstractAction {
			public MinimizeAction() {
				super("Minimize Windows");
			}

			public void actionPerformed(ActionEvent e) {
				minimizeAll();
			}
		}

		protected final Action getMinimizeAction() {
			return new MinimizeAction();
		}
		/**
		 * Minimize all.
		 */
		protected final void minimizeAll() {
			Component[] comps = _PARENT._DESKTOP.getComponents();
			for (int i = 0; i < comps.length; i++) {
				if (comps[i] instanceof JInternalFrame
					&& comps[i].isVisible()
					&& !((JInternalFrame) comps[i]).isIcon()
					&& ((JInternalFrame) comps[i]).isIconifiable()) {
					try {
						((JInternalFrame) comps[i]).setIcon(true);
					}
					catch (java.beans.PropertyVetoException pve0) {
					}
				}
			}
		}

		/**
		 * Show/Hide format toolbar action.
		 * @version 1.0 Revision 0
		 * @author Brill Pappin 21-APR-1999
		 */
		protected final class TileAction extends AbstractAction {
			public TileAction() {
				super("Tile Windows");
			}

			public void actionPerformed(ActionEvent e) {
				tileAll();
			}
		}

		protected final Action getTileAction() {
			return new TileAction();
		}

		/**
		 * Tile Windows
		 */
		protected final void tileAll() {
			if (_PARENT._DESKTOP.getDesktopManager() == null) {
				// No desktop manager - do nothing
				return;
			}
			Component[] comps = _PARENT._DESKTOP.getComponents();
			Component comp;
			int count = 0;

			// Count and handle only the internal frames
			for (int i = 0; i < comps.length; i++) {
				comp = comps[i];
				if (comp instanceof JInternalFrame
					&& comp.isVisible()
					&& !((JInternalFrame) comp).isIcon()) {
					count++;
				}
			}
			if (count != 0) {
				double root = Math.sqrt((double) count);
				int rows = (int) root;
				int columns = count / rows;
				int spares = count - (columns * rows);
				Dimension paneSize = _PARENT._DESKTOP.getSize();
				int columnWidth = paneSize.width / columns;

				// We leave some space at the bottom that doesn't get covered
				int availableHeight = paneSize.height - 48;
				int mainHeight = availableHeight / rows;
				int smallerHeight = availableHeight / (rows + 1);
				int rowHeight = mainHeight;
				int x = 0;
				int y = 0;
				int thisRow = rows;
				int normalColumns = columns - spares;
				for (int i = comps.length - 1; i >= 0; i--) {
					comp = comps[i];
					if (comp instanceof JInternalFrame
						&& comp.isVisible()
						&& !((JInternalFrame) comp).isIcon()) {
						_PARENT
							._DESKTOP
							.getDesktopManager()
							.setBoundsForFrame(
							(JComponent) comp,
							x,
							y,
							columnWidth,
							rowHeight);
						y += rowHeight;
						if (--thisRow == 0) {
							// Filled the row
							y = 0;
							x += columnWidth;

							// Switch to smaller rows if necessary
							if (--normalColumns <= 0) {
								thisRow = rows + 1;
								rowHeight = smallerHeight;
							}
							else {
								thisRow = rows;
							}
						}
					}
				}
			}
		}
	}

	protected final class MainDesktop
		extends JDesktopPane
		implements Scrollable, AdjustmentListener {

		private volatile Thread doublebufferthread;
		Image i = getToolkit().getImage("images/gui.gif");

		public MainDesktop() {
			//setBackground(Color.black);
			super();
			//updateUI();
		}

		public Dimension getPreferredScrollableViewportSize() {
			return getPreferredSize();
		}

		public int getScrollableUnitIncrement(
			Rectangle visibleRect,
			int orientation,
			int direction) {
			switch (orientation) {
				case SwingConstants.VERTICAL :
					return visibleRect.height / 10;
				case SwingConstants.HORIZONTAL :
					return visibleRect.width / 10;
				default :
					throw new IllegalArgumentException(
						"Invalid orientation: " + orientation);
			}
		}

		public int getScrollableBlockIncrement(
			Rectangle visibleRect,
			int orientation,
			int direction) {
			switch (orientation) {
				case SwingConstants.VERTICAL :
					return visibleRect.height;
				case SwingConstants.HORIZONTAL :
					return visibleRect.width;
				default :
					throw new IllegalArgumentException(
						"Invalid orientation: " + orientation);
			}
		}

		public boolean getScrollableTracksViewportWidth() {
			return false;
		}
		public boolean getScrollableTracksViewportHeight() {
			return false;
		}

		//End Scroll Methods

		//Adjustment Changes
		public void adjustmentValueChanged(AdjustmentEvent e) {
		}

	}

	/**
	* Look & Feel Event Listener. Changes the
	* plaf of the application when
	* requested.
	* @version 1.0 Revision 0
	* @author Brill Pappin 21-APR-1999
	*/
	private final class LAL implements ActionListener {
		private Main _APP = null;
		private LAL(Main app) {
			_APP = app;
		}
		public void actionPerformed(ActionEvent e) {
			// Set the look & feel for the app.
			try {
				UIManager.setLookAndFeel(e.getActionCommand());
				getRegistry().setProperty(
					"MAIN",
					"main.look&feel",
					e.getActionCommand());
				// Tell the system to update the UI.
				SwingUtilities.updateComponentTreeUI(_APP);
			}
			catch (Exception lafe) {

			}

		}
	}

	/**
	* A Dynamic FileFilter. The filter can take its
	* extensions as a space delimited list of extensions.
	* <LI> new DynamicFileFilter("txt text java cpp", "Text File (*.txt)");
	* @version 1.0 Revision 1
	* @author Brill Pappin
	*/
	protected final class DynamicFileFilter extends FileFilter {
		private String extension = "*";
		private String description = "All Files (*.*)";
		public DynamicFileFilter(String ext, String desc) {
			this.extension = ext;
			this.description = desc;
		}
		public DynamicFileFilter(String ext) {
			this.extension = ext;
			this.description = Mime.findContentType(ext) + " (*." + ext + ")";
		}
		public DynamicFileFilter() {
		}

		public boolean accept(File f) {
			if (f.isFile()) {
				if (extension.equals("*")) {
					return true;
				}
				else {
					////Code.debug("Acctept Extension: " + f.getName().substring((f.getName().lastIndexOf(".") + 1), f.getName().length()));
					return (
						extension.indexOf(
							f.getName().substring(
								(f.getName().lastIndexOf(".") + 1),
								f.getName().length()))
							> -1);
				}
			}
			else {
				return true;
			}
		}
		public String getDescription() {
			return this.description;
		}
	}

	/**
	* Execute an action to start the help program
	*/
	protected final class HELPAction extends AbstractAction {
		String help = null;

		public HELPAction(String helpFile) {
			super("Help...");
			help = helpFile;
		}

		public HELPAction() {
			super("Help...");
			help = "lexi";
		}

		public void actionPerformed(ActionEvent e) {
			OfficeHelp helper = new OfficeHelp(help);
			helper.setSize(500, 500);
			helper.setVisible(true);
		}
	}

	/**
	* Checks to see if there is an update to Main available
	*/
	protected final class UpdateAction extends AbstractAction {
		public UpdateAction() {
			super("Check for Update..");
		}

		public void actionPerformed(ActionEvent e) {
			// int whatEver = 1;
			//UpdateTester updater = new UpdateTester(getMain(), whatEver, "test.jar");
			System.out.println("Not implemented...");
		}

	}
	/**
	* Show/Hide format toolbar action.
	* @version 1.0 Revision 0
	* @author Brill Pappin 21-APR-1999
	*/
	protected final class SFTAction extends AbstractAction {
		private Main _LISTENER = null;
		public SFTAction(Main app) {
			super("Show Format Toolbar");
			_LISTENER = app;
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof JCheckBoxMenuItem) {
				_LISTENER.getFormatToolBar().setVisible(
					!_LISTENER.getFormatToolBar().isVisible());
				((JCheckBoxMenuItem) e.getSource()).setState(
					_LISTENER.getFormatToolBar().isVisible());
			}
		}
	}

	/**
	* Show/Hide file toolbar action.
	* @version 1.0 Revision 0
	* @author Brill Pappin 21-APR-1999
	*/
	protected final class SLTAction extends AbstractAction {
		private Main _LISTENER = null;
		public SLTAction(Main app) {
			super("Show File Toolbar");
			_LISTENER = app;
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof JCheckBoxMenuItem) {
				_LISTENER.getFileToolBar().setVisible(
					!_LISTENER.getFileToolBar().isVisible());
				((JCheckBoxMenuItem) e.getSource()).setState(
					_LISTENER.getFileToolBar().isVisible());
			}
		}
	}

	/**
	* Coloured list item Renderer.
	* @version 1.0 Revision 0
	* @author Brill Pappin 21-APR-1999
	*/
	protected class ColourActionCellRenderer
		extends JLabel
		implements ListCellRenderer {
		public ColourActionCellRenderer() {
			setOpaque(true);
		}
		public Component getListCellRendererComponent(
			JList list,
			Object value,
			int index,
			boolean isSelected,
			boolean cellHasFocus) {
			if (value != null) {
				this.setMinimumSize(new Dimension(0, 16));
				Color colour =
					Color.decode(
						getRegistry().getString(
							"COLOURS",
							(String) value,
							null));
				this.setText((String) value);
				this.setIcon(new ColourIcon(colour));
				if (isSelected) {
					this.setBackground(list.getSelectionBackground());
					this.setForeground(list.getSelectionForeground());
				}
				else {
					this.setBackground(list.getBackground());
					this.setForeground(list.getForeground());
				};

				return this;

			}
			else {
				return new JLabel("VALUE IS NULL");
			}
		}

		/**
		 * Icon Renderer
		 * @version 1.0 Revision 0
		 * @author Brill Pappin 21-APR-1999
		 */
		protected final class ColourIcon implements Icon, Serializable {
			private transient Color _COLOUR = null;
			private transient Image _IMAGE = null;

			protected ColourIcon(Color colour) {
				super();
				_COLOUR = colour;
			}

			public void paintIcon(Component c, Graphics g, int x, int y) {
				// Color background = c.getBackground();

				if (_IMAGE == null) {
					_IMAGE =
						c.createImage(
							this.getIconWidth(),
							this.getIconHeight());
					Graphics imageG = _IMAGE.getGraphics();
					this.paintImage(c, imageG, _COLOUR);
				}
				g.drawImage(_IMAGE, x, y, null);

			}

			private void paintImage(Component c, Graphics g, Color colour) {
				g.setColor(colour);
				g.fillRect(0, 0, this.getIconWidth(), this.getIconHeight());
				g.setColor(Color.black);
				g.drawRect(
					0,
					0,
					this.getIconWidth() - 1,
					this.getIconHeight() - 1);
			}

			public int getIconWidth() {
				return 16;
			}

			public int getIconHeight() {
				return 16;
			}
		}
	}

	/**
	* Font list item Renderer.
	* @version 1.0 Revision 0
	* @author Brill Pappin 21-APR-1999
	*/
	protected class FontActionCellRenderer
		extends JLabel
		implements ListCellRenderer {
		public FontActionCellRenderer() {
			setOpaque(true);
		}
		public Component getListCellRendererComponent(
			JList list,
			Object value,
			int index,
			boolean isSelected,
			boolean cellHasFocus) {
			if (value != null) {
				this.setMinimumSize(new Dimension(0, 16));
				Font thisFont = new Font((String) value, Font.PLAIN, 12);
				////Code.message("Font PostScript Name: " + thisFont.getPSName());
				this.setFont(thisFont);
				this.setText((String) value);

				//setIcon((ImageIcon)a.getValue(Action.SMALL_ICON));
				if (isSelected) {
					this.setBackground(list.getSelectionBackground());
					this.setForeground(list.getSelectionForeground());
				}
				else {
					this.setBackground(list.getBackground());
					this.setForeground(list.getForeground());
				};

				return this;

			}
			else {
				return new JLabel("VALUE IS NULL");
			}
		}
	}

	/**
	* Font size list item Renderer.
	* @version 1.0 Revision 0
	* @author Brill Pappin 21-APR-1999
	*/
	protected class FSActionCellRenderer
		extends JLabel
		implements ListCellRenderer {
		public FSActionCellRenderer() {
			setOpaque(true);
		}
		public Component getListCellRendererComponent(
			JList list,
			Object value,
			int index,
			boolean isSelected,
			boolean cellHasFocus) {
			if (value != null) {
				this.setText((String) value);
				this.setMinimumSize(new Dimension(0, 16));
				//setIcon((ImageIcon)a.getValue(Action.SMALL_ICON));
				if (isSelected) {
					this.setBackground(list.getSelectionBackground());
					this.setForeground(list.getSelectionForeground());
				}
				else {
					this.setBackground(list.getBackground());
					this.setForeground(list.getForeground());
				};

				return this;

			}
			else {
				return new JLabel("VALUE IS NULL");
			}
		}
	}

	//PropertySheetDialog
	protected final class UserPropertyAction extends AbstractAction {
		public UserPropertyAction() {
			super("User Options...");
		}

		public void actionPerformed(ActionEvent e) {
			//PropertySheetDialog.display(getMain(), (Properties)getUserRegistry());
			Options opts = new Options(getMain(), "Main Options", true);
			opts.setVisible(true);
			getMain().repaint();
		}
	}

	protected final class PopupPropertyAction extends AbstractAction {
		public PopupPropertyAction() {
			super("Popup Options....");
		}

		public void actionPerformed(ActionEvent e) {
			PropertySheetDialog.display(
				getMain(),
				(Properties) getRegistry().referenceGroup("POPUP"));
			getMain().repaint();
		}
	}

	protected final class MainPropertyAction extends AbstractAction {
		public MainPropertyAction() {
			super("Main Options...");
		}

		public void actionPerformed(ActionEvent e) {
			PropertySheetDialog.display(
				getMain(),
				(Properties) getRegistry().referenceGroup("MAIN"));
			getMain().repaint();
		}
	}

	protected final class ColourPropertyAction extends AbstractAction {
		public ColourPropertyAction() {
			super("Default Colours...");
		}

		public void actionPerformed(ActionEvent e) {
			//PropertySheetDialog.display(getMain(), (Properties)getColourRegistry(), true);
			//	ColourPropertySheet psd =
			//		new ColourPropertySheet(
			//			getMain(),
			//			getRegistry().referenceGroup("COLOURS"),
			//			true);
			getMain().repaint();
		}
	}

	protected final class FontPropertyAction extends AbstractAction {
		public FontPropertyAction() {
			super("Default Fonts...");
		}

		public void actionPerformed(ActionEvent e) {
			//FontPropertySheet.display(getMain(), getFontRegistry());
			//	FontPropertySheet psd =
			//		new FontPropertySheet(
			//			getMain(),
			//			getRegistry().referenceGroup("FONTS"),
			//			false);
			getMain().repaint();
		}
	}

	protected final class QuitAction extends AbstractAction {
		public QuitAction() {
			super("Quit");
		}

		public void actionPerformed(ActionEvent e) {
			doExit();
		}
	}

	/**
	* Start a print job.
	* @version 0.1 Revision 0
	* @author Brill Pappin 21-APR-1999
	*/
	protected final class PrintAction extends AbstractAction {
		public PrintAction() {
			super("Print...");
			this.setEnabled(
				getRegistry().getBoolean(
					"MAIN",
					"print.document.enabled",
					false));
		}

		public void actionPerformed(ActionEvent e) {
			//Code.event(e);
			JOptionPane.showMessageDialog(
				getMain(),
				"Print Not Implemented!",
				"Not Implemented!",
				JOptionPane.WARNING_MESSAGE);
			// Use java.awt.print.PrinterJob instead.

			// What are the properties it wants?
			//PrintJob job = Toolkit.getDefaultToolkit().getPrintJob(getMain(), getMain().getTitle(), null /*Properties props*/);
			////Code.message("Got Print Job: " + job);
		}
	}

	protected final class FontPropertySheet extends JDialog {
		private Properties _P = null;
		private Frame _PARENT = null;
		private boolean _ALLOW_ADD = false;
		private PairTableModel _MODEL = null;

		private FontPropertySheet(
			Frame parent,
			Properties p,
			boolean allowAdd) {
			super(parent);
			this._PARENT = parent;
			this._P = p;
			this._ALLOW_ADD = allowAdd;
			this.init();
			this.pack();
			this.setLocationRelativeTo(parent);
			this.setVisible(true);
		}

		private void doExit() {
			this.dispose();
		}

		private void init() {
			JPanel content = new JPanel();
			content.setLayout(new BorderLayout());

			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new BorderLayout());
			JPanel spacerPanel = new JPanel();
			spacerPanel.setLayout(new GridLayout());
			if (_ALLOW_ADD) {
				JButton addButton = new JButton("Add Key");
				addButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String inputValue =
							JOptionPane.showInputDialog(
								"What is the key you want to add?");
						if (inputValue != null) {
							if (inputValue.trim().length() > 0) {
								_P.setProperty(inputValue, "");
								// redraw the table
								if (_MODEL != null) {
									_MODEL.fireTableDataChanged();
								}
							}
						}
					}
				});
				spacerPanel.add(addButton);
			}

			JButton closeButton = new JButton("Close");
			closeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					doExit();
				}
			});
			spacerPanel.add(closeButton);
			buttonPanel.add(spacerPanel, BorderLayout.EAST);

			content.add(buttonPanel, BorderLayout.SOUTH);

			_MODEL = new PairTableModel();
			JTable tbl = new JTable(_MODEL);
			content.add(new JScrollPane(tbl), BorderLayout.CENTER);

			tbl.getColumnModel().getColumn(1).setPreferredWidth(5);

			this.setContentPane(content);
			// Added this to dispose of
			// the main app window when
			// it gets closed.
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					doExit();
				}
			});
		}

		protected final Properties getProperties() {
			return _P;
		}

		private final class PairTableModel extends AbstractTableModel {

			public PairTableModel() {
				super();
			}

			public int getRowCount() {
				return getProperties().size();
			}

			public int getColumnCount() {
				return 2;
			}

			public String getColumnName(int columnIndex) {
				switch (columnIndex) {
					case 0 :
						return "Font Face";
					case 1 :
						return "Show/Hide";
					default :
						return null;
				}
			}

			public Class getColumnClass(int columnIndex) {
				switch (columnIndex) {
					case 0 :
						return java.lang.String.class;
					case 1 :
						return java.lang.Boolean.class;
					default :
						return java.lang.String.class;
				}
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				switch (columnIndex) {
					case 0 :
						return false;
					case 1 :
						return true;
					default :
						return false;
				}
			}

			public Object getValueAt(int rowIndex, int columnIndex) {
				switch (columnIndex) {
					case 0 :
						return getProperties()
							.keySet()
							.toArray()[rowIndex]
							.toString();
					case 1 :
						return new Boolean(
							getProperties().getProperty(
								getProperties()
									.keySet()
									.toArray()[rowIndex]
									.toString()));
					default :
						return "";
				}
			}

			public void setValueAt(
				Object aValue,
				int rowIndex,
				int columnIndex) {
				switch (columnIndex) {
					case 0 :
						//getProperties().keySet().toArray()[rowIndex] = aValue.toString();
						break;
					case 1 :
						getProperties().setProperty(
							getProperties()
								.keySet()
								.toArray()[rowIndex]
								.toString(),
							aValue.toString());
						break;
				}
			}
		}
	}

	protected final class ColourPropertySheet extends JDialog {
		private Properties _P = null;
		private Frame _PARENT = null;
		private boolean _ALLOW_ADD = false;
		private PairTableModel _MODEL = null;

		private ColourPropertySheet(
			Frame parent,
			Properties p,
			boolean allowAdd) {
			super(parent);
			this._PARENT = parent;
			this._P = p;
			this._ALLOW_ADD = allowAdd;
			this.init();
			this.pack();
			this.setLocationRelativeTo(parent);
			this.setVisible(true);
		}

		private void doExit() {
			this.dispose();
		}

		private void init() {
			JPanel content = new JPanel();
			content.setLayout(new BorderLayout());

			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new BorderLayout());
			JPanel spacerPanel = new JPanel();
			spacerPanel.setLayout(new GridLayout());
			if (_ALLOW_ADD) {
				JButton addButton = new JButton("Add Colour");
				addButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String inputValue =
							JOptionPane.showInputDialog(
								"What is the name of the\ncolour you want to add?");
						if (inputValue != null) {
							if (inputValue.trim().length() > 0) {
								try {
									_P.setProperty(
										inputValue,
										Format.colorToHex(
											JColorChooser.showDialog(
												getMain(),
												"Choose a colour...",
												null)));
								}
								catch (Throwable t) {
									// the colour chooser was most likely
									// canceled, so ignore the exception.
								}
								// redraw the table
								if (_MODEL != null) {
									_MODEL.fireTableDataChanged();
								}
							}
						}
					}
				});
				spacerPanel.add(addButton);
			}

			JButton closeButton = new JButton("Close");
			closeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					doExit();
				}
			});
			spacerPanel.add(closeButton);
			buttonPanel.add(spacerPanel, BorderLayout.EAST);

			content.add(buttonPanel, BorderLayout.SOUTH);

			_MODEL = new PairTableModel();
			JTable tbl = new JTable(_MODEL);
			content.add(new JScrollPane(tbl), BorderLayout.CENTER);

			tbl.getColumnModel().getColumn(1).setPreferredWidth(5);
			tbl.getColumnModel().getColumn(1).setCellRenderer(
				new ColourCellRenderer());

			this.setContentPane(content);
			// Added this to dispose of
			// the main app window when
			// it gets closed.
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					doExit();
				}
			});
		}

		protected final Properties getProperties() {
			return _P;
		}

		//private final class ColourCellEditor extends DefaultCellEditor{
		//}

		private final class ColourCellRenderer
			extends DefaultTableCellRenderer {
			private final Color defaultBackground = this.getBackground();
			private final Color defaultForeground = this.getForeground();
			public Component getTableCellRendererComponent(
				JTable table,
				Object value,
				boolean isSelected,
				boolean hasFocus,
				int row,
				int column) {
				//System.out.println(this.toString());
				this.setValue(value);
				if (!isSelected && column == 1) {
					try {
						Color c = Format.hexToColor((String) value);
						this.setBackground(c);
						this.setForeground(Runtime.getContrastingTextColor(c));
					}
					catch (Throwable t) {
						// Ignore this, its just a bad colour.
						Color c = Color.black;
						this.setBackground(c);
						this.setForeground(Runtime.getContrastingTextColor(c));
						this.setValue("#000000");
					}
				}
				else {
					this.setBackground(defaultBackground);
					this.setForeground(defaultForeground);
				}
				return this;
			}
		}

		private final class PairTableModel extends AbstractTableModel {

			public PairTableModel() {
				super();
			}

			public int getRowCount() {
				return getProperties().size();
			}

			public int getColumnCount() {
				return 2;
			}

			public String getColumnName(int columnIndex) {
				switch (columnIndex) {
					case 0 :
						return "Colour Name";
					case 1 :
						return "RGB Hex";
					default :
						return null;
				}
			}

			public Class getColumnClass(int columnIndex) {
				switch (columnIndex) {
					case 0 :
						return java.lang.String.class;
					case 1 :
						return java.lang.String.class;
					default :
						return java.lang.String.class;
				}
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				switch (columnIndex) {
					case 0 :
						return false;
					case 1 :
						return true;
					default :
						return false;
				}
			}

			public Object getValueAt(int rowIndex, int columnIndex) {
				switch (columnIndex) {
					case 0 :
						return getProperties()
							.keySet()
							.toArray()[rowIndex]
							.toString();
					case 1 :
						return getProperties().getProperty(
							getProperties()
								.keySet()
								.toArray()[rowIndex]
								.toString());
					default :
						return "";
				}
			}

			public void setValueAt(
				Object aValue,
				int rowIndex,
				int columnIndex) {
				switch (columnIndex) {
					case 0 :
						//getProperties().keySet().toArray()[rowIndex] = aValue.toString();
						break;
					case 1 :
						getProperties().setProperty(
							getProperties()
								.keySet()
								.toArray()[rowIndex]
								.toString(),
							aValue.toString());
						break;
				}
			}
		}
	}

	protected final class Options extends JDialog {
		protected Options(Frame owner, String title, boolean modal) {
			super(owner, title, modal);
			this.init();
			this.setLocationRelativeTo(owner);
		}

		private void init() {
			JPanel content = new JPanel();
			content.setLayout(new BorderLayout());
			JTabbedPane jtp = new JTabbedPane();
			content.add(jtp, BorderLayout.CENTER);

			jtp.addTab(
				"Paths",
				null,
				new PathsSheet(getMain()),
				"Changed the paths that Main uses.");

			this.setContentPane(content);
			this.setSize(200, 200);
			// Added this to dispose of
			// the main app window when
			// it gets closed.
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					doExit();
				}
			});
		}

		private void doExit() {
			this.dispose();
		}
	}

	protected final class PathsSheet extends JPanel {
		private Frame _PARENT = null;
		private JTextField user_home_text = new JTextField();
		private JTextField user_temp_text = new JTextField();
		private JTextField user_document_text = new JTextField();

		protected PathsSheet(Frame parent) {
			super();
			_PARENT = parent;
			this.init();
		}

		private void init() {
			this.setLayout(new GridLayout(3, 1));

			this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

			JButton user_home_button = new JButton("...");
			JButton user_temp_button = new JButton("...");
			JButton user_document_button = new JButton("...");

			JPanel user_home_spacer = new JPanel();
			JPanel user_temp_spacer = new JPanel();
			JPanel user_document_spacer = new JPanel();

			user_home_text.setText(
				getMain().getRegistry().getString("USER", "user.home", null));
			user_temp_text.setText(
				getMain().getRegistry().getString(
					"USER",
					"temp.directory",
					null));
			user_document_text.setText(
				getMain().getRegistry().getString(
					"USER",
					"default.documents.directory",
					null));

			DocumentListener listener = new DocumentListener() {
				public void insertUpdate(DocumentEvent e) {
					this.updateRegistry();
				}
				public void removeUpdate(DocumentEvent e) {
					this.updateRegistry();
				}
				public void changedUpdate(DocumentEvent e) {
					this.updateRegistry();
				}
				public void updateRegistry() {
					getMain().getRegistry().setProperty(
						"USER",
						"user.home",
						user_home_text.getText().trim());
					getMain().getRegistry().setProperty(
						"USER",
						"temp.directory",
						user_temp_text.getText().trim());
					getMain().getRegistry().setProperty(
						"USER",
						"default.documents.directory",
						user_document_text.getText().trim());
				}
			};

			user_home_text.getDocument().addDocumentListener(listener);
			user_temp_text.getDocument().addDocumentListener(listener);
			user_document_text.getDocument().addDocumentListener(listener);

			user_home_button.addActionListener(new HA());
			user_temp_button.addActionListener(new TA());
			;
			user_document_button.addActionListener(new DA());

			user_home_spacer.add(user_home_button, BorderLayout.EAST);
			user_temp_spacer.add(user_temp_button, BorderLayout.EAST);
			user_document_spacer.add(user_document_button, BorderLayout.EAST);

			user_home_spacer.add(user_home_text, BorderLayout.CENTER);
			user_temp_spacer.add(user_temp_text, BorderLayout.CENTER);
			user_document_spacer.add(user_document_text, BorderLayout.CENTER);

			this.add(user_home_spacer);
			this.add(user_temp_spacer);
			this.add(user_document_spacer);
		}

		private final class HA extends AbstractAction {
			private HA() {
				super("...");
			}
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Choose Home Diretory");
				chooser.setDialogType(JFileChooser.OPEN_DIALOG);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				//chooser.setCurrentDirectory(new File(FSTool.ensureDirectory(_PARENT.getUserRegistry().getString("default.documents.directory", System.getProperty("user.home") + File.separator + "documents"))));
				chooser.showOpenDialog(null); // showDialog(this, null);
				File dirChoice = chooser.getSelectedFile();
				if (dirChoice != null) {
					user_home_text.setText(dirChoice.getAbsolutePath());
					getMain().getRegistry().setProperty(
						"USER",
						"user.home",
						dirChoice.getAbsolutePath());
				}
			}
		}
		private final class TA extends AbstractAction {
			private TA() {
				super("...");
			}
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Choose Temp Diretory");
				chooser.setDialogType(JFileChooser.OPEN_DIALOG);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				//chooser.setCurrentDirectory(new File(FSTool.ensureDirectory(_PARENT.getUserRegistry().getString("default.documents.directory", System.getProperty("user.home") + File.separator + "documents"))));
				chooser.showOpenDialog(null); // showDialog(this, null);
				File dirChoice = chooser.getSelectedFile();
				if (dirChoice != null) {
					user_temp_text.setText(dirChoice.getAbsolutePath());
					getMain().getRegistry().setProperty(
						"USER",
						"temp.directory",
						dirChoice.getAbsolutePath());
				}
			}
		}
		private final class DA extends AbstractAction {
			private DA() {
				super("...");
			}
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Choose Documents Diretory");
				chooser.setDialogType(JFileChooser.OPEN_DIALOG);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				//chooser.setCurrentDirectory(new File(FSTool.ensureDirectory(_PARENT.getUserRegistry().getString("default.documents.directory", System.getProperty("user.home") + File.separator + "documents"))));
				chooser.showOpenDialog(null); // showDialog(this, null);
				File dirChoice = chooser.getSelectedFile();
				if (dirChoice != null) {
					user_document_text.setText(dirChoice.getAbsolutePath());
					getMain().getRegistry().setProperty(
						"USER",
						"default.documents.directory",
						dirChoice.getAbsolutePath());
				}
			}
		}
	} // === End TypeSheet class ====
	//private Registry _KEY_DESC_REG = null;

	/**
	* Constructor...
	*/
	public Main(String[] args) {
		super("Main Editor");

		this.init();
		this.setVisible(true);
		if (args != null) {
			if (args.length > 0) {
				try {
					// Open any files passed on the command line.
					for (int i = 0; i < args.length; i++) {
						this.getDesktopManager().createDocumentFrame(
							new File(args[i]));
					}
				}
				catch (ArrayIndexOutOfBoundsException ex) {
					if (this
						.getRegistry()
						.getBoolean("USER", "open.blank.default", true)) {
						this.getDesktopManager().createDocumentFrame();
					}
				}
			}
			else {
				if (this
					.getRegistry()
					.getBoolean("USER", "open.blank.default", true)) {
					this.getDesktopManager().createDocumentFrame();
				}
			}
		}
		else {
			if (this
				.getRegistry()
				.getBoolean("USER", "open.blank.default", true)) {
				this.getDesktopManager().createDocumentFrame();
			}
		}
		//} catch(Throwable t) {
		//	System.out.println("Exit with Fatal Exception: " + t.toString());
		//	t.printStackTrace(System.out);
		//}

	}
	protected final void a_documentFrameChanged(
		DocumentFrame frame,
		boolean textSelected) {
		this.setTitle(
			"Main - ["
				+ frame.getTitle()
				+ (frame.getEditor().isChanged() ? "] *" : "]"));
	}
	/**
	* Handles the Menu Actions
	* @depricated Use an action to handle the event.
	*/
	public void actionPerformed(ActionEvent event) {
		//Code.failed(this, "DELETE THIS EVENT CALL (USE ACTIONS INSTEAD) - " + event.toString());
	}
	protected final void addToFileHistory(File file) {
		// Add the opened file to the histroy menu. -- brill 03/04/1999
		if (_FILE_HISTORY.getItemCount()
			>= this.getRegistry().getInteger("USER", "max.file.history", 5)) {
			_FILE_HISTORY.getItem(0).setText(file.getName());
			_FILE_HISTORY.getItem(0).setActionCommand(file.getAbsolutePath());
			//Code.debug("File Hist. replace: " + file.getName() + "=" + file.getAbsolutePath());
		}
		else {
			JMenuItem item = new JMenuItem(file.getName());
			item.setActionCommand(file.getAbsolutePath());
			item.addActionListener(_FILE_HISTROY_ACTION);
			_FILE_HISTORY.insert(item, 0);
			//Code.debug("File Hist. create: " + file.getName() + "=" + file.getAbsolutePath());
		}
	}
	/**
	* Makes a JMenuBar so that we save lines
	* @return javax.swing.JMenuBar
	*/
	private JMenuBar createMenuBar() {

		_MENU_BAR = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('f');

		JMenu editMenu = new JMenu("Edit");
		editMenu.setMnemonic('e');

		JMenu viewMenu = new JMenu("View");
		viewMenu.setMnemonic('i');

		JMenu formatMenu = new JMenu("Format");
		formatMenu.setMnemonic('m');

		JMenu windowMenu = new JMenu("Window");
		windowMenu.setMnemonic('w');

		JMenu helpMenu = new JMenu("Help");
		windowMenu.setMnemonic('h');

		// FILE MENU =========================
		// Special Menu item for File history.
		_FILE_HISTORY = new JMenu("File History");
		fileMenu.add(
			EditorActionManager.instance().getNewAction(
				this,
				((DocumentManager) this.getDesktopManager())));
		fileMenu.add(
			EditorActionManager.instance().getOpenAction(
				this,
				((DocumentManager) this.getDesktopManager())));
		fileMenu.add(
			EditorActionManager.instance().getOpenAsAction(
				this,
				((DocumentManager) this.getDesktopManager())));
		fileMenu.add(
			EditorActionManager.instance().getSaveAction(
				this,
				((DocumentManager) this.getDesktopManager())));
		fileMenu.add(
			EditorActionManager.instance().getSaveAsAction(
				this,
				((DocumentManager) this.getDesktopManager())));
		fileMenu.add(
			EditorActionManager.instance().getSaveCopyAction(
				this,
				((DocumentManager) this.getDesktopManager())));
		fileMenu.add(
			EditorActionManager.instance().getRevertAction(
				this,
				((DocumentManager) this.getDesktopManager())));
		fileMenu.addSeparator();
		fileMenu.add(
			((DocumentManager) this.getDesktopManager()).getCloseAction());
		fileMenu.add(
			((DocumentManager) this.getDesktopManager()).getCloseAllAction());
		fileMenu.addSeparator();
		fileMenu.add(this.getPrintAction());
		fileMenu.addSeparator();
		fileMenu.add(_FILE_HISTORY);
		fileMenu.addSeparator();
		fileMenu.add(new QuitAction());

		// EDIT MENU ===========================
		editMenu.add(EditorActionManager.instance().getUndoAction());
		editMenu.add(EditorActionManager.instance().getRedoAction());
		editMenu.addSeparator();
		editMenu.add(EditorActionManager.instance().getCutAction());
		editMenu.add(EditorActionManager.instance().getCopyAction());
		editMenu.add(EditorActionManager.instance().getPasteAction());
		editMenu.addSeparator();
		editMenu.add(EditorActionManager.instance().getSelectAllAction());
		editMenu.add(EditorActionManager.instance().getSelectNoneAction());
		editMenu.addSeparator();
		editMenu.add(EditorActionManager.instance().getSearchAction(this));
		editMenu.add(EditorActionManager.instance().getReplaceAction(this));

		// VIEW MENU ===========================
		// Action listener Look & Feel only.
		ActionListener lafal = new LAL(this);
		JMenu LANDF = new JMenu("Look & Feel");
		ButtonGroup lafgroup = new ButtonGroup();
		// Testing availible LAFs.
		for (int i = 0;
			i < UIManager.getInstalledLookAndFeels().length;
			i++) {
			JCheckBoxMenuItem lafitem =
				new JCheckBoxMenuItem(
					UIManager.getInstalledLookAndFeels()[i].getName());
			lafitem.setActionCommand(
				UIManager.getInstalledLookAndFeels()[i].getClassName());
			lafitem.addActionListener(lafal);
			lafgroup.add(lafitem);
			LANDF.add(lafitem);
			if (getRegistry()
				.getString("MAIN", "main.look&feel", null)
				.equals(
					UIManager.getInstalledLookAndFeels()[i].getClassName())) {
				lafitem.setSelected(true);
			}
			//Code.debug(UIManager.getInstalledLookAndFeels()[i].getName() + "=" + UIManager.getInstalledLookAndFeels()[i].getClassName());
		}

		LANDF.addSeparator();
		// ===============

		//ButtonGroup toolbargroup = new ButtonGroup();
		JCheckBoxMenuItem showhideFileToolBar =
			new JCheckBoxMenuItem("Show File Toolbar");
		showhideFileToolBar.setActionCommand("mnu-showfiletoolbar");
		showhideFileToolBar.addActionListener(new SLTAction(this));
		showhideFileToolBar.setState(this.getFileToolBar().isVisible());
		//toolbargroup.add(showhideFileToolBar);

		JCheckBoxMenuItem showhideFormatToolBar =
			new JCheckBoxMenuItem("Show Format Toolbar");
		showhideFormatToolBar.setActionCommand("mnu-showformattoolbar");
		showhideFormatToolBar.addActionListener(new SFTAction(this));
		showhideFormatToolBar.setState(this.getFormatToolBar().isVisible());
		//toolbargroup.add(showhideFormatToolBar);

		viewMenu.add(LANDF);
		viewMenu.addSeparator();
		viewMenu.add(showhideFileToolBar);
		viewMenu.add(showhideFormatToolBar);
		viewMenu.addSeparator();
		viewMenu.add(new MainPropertyAction());
		viewMenu.add(new UserPropertyAction());
		viewMenu.add(new PopupPropertyAction());
		viewMenu.addSeparator();
		viewMenu.add(new ColourPropertyAction());
		viewMenu.add(new FontPropertyAction());

		//viewMenu.addSeparator();
		//viewMenu.add(credits);

		// FORMAT MENU =========================
		JMenuItem colours = new JMenuItem("Colours...");
		colours.setActionCommand("mnu-colours");
		colours.addActionListener(this);

		JMenuItem fonts = new JMenuItem("Fonts...");
		fonts.setActionCommand("mnu-fonts");
		fonts.addActionListener(this);

		formatMenu.add(EditorActionManager.instance().getAlignLeftAction());
		formatMenu.add(EditorActionManager.instance().getAlignCenterAction());
		formatMenu.add(EditorActionManager.instance().getAlignRightAction());
		formatMenu.add(EditorActionManager.instance().getAlignJustifyAction());
		formatMenu.addSeparator();
		formatMenu.add(EditorActionManager.instance().getBoldAction());
		formatMenu.add(EditorActionManager.instance().getItalicAction());
		formatMenu.add(EditorActionManager.instance().getUnderlineAction());
		formatMenu.add(
			EditorActionManager.instance().getStrikeThroughAction());
		formatMenu.addSeparator();
		formatMenu.add(
			EditorActionManager.instance().getColourChooserAction(this));
		formatMenu.add(
			EditorActionManager.instance().getFontChooserAction(this));

		// WINDOW MENU =========================
		JMenuItem cascade = new JMenuItem("Cascade Windows");
		cascade.setActionCommand("mnu-cascade");
		cascade.addActionListener(this);

		JMenuItem tile = new JMenuItem("Tile Windows");
		tile.setActionCommand("mnu-tile");
		tile.addActionListener(this);

		JMenuItem minimize = new JMenuItem("Minimize Windows");
		minimize.setActionCommand("mnu-minimize");
		minimize.addActionListener(this);

		_OPEN_WINDOWS = new JMenu("Open Windows");

		// HELP MENU ===========================
		/*
		  JMenuItem help = new JMenuItem("Help...");
		  help.setActionCommand("mnu-help");
		  help.addActionListener(this);
		  help.setEnabled(false);
		*/

		JMenuItem about = new JMenuItem("About...");
		about.setActionCommand("mnu-about");
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(
					getMain(),
					"Main was written by:\nMatthew Schmidt & Brill Pappin\n\nCopyright 1999",
					"About Main...",
					JOptionPane.INFORMATION_MESSAGE);
			}
		});

		JCheckBoxMenuItem interact = new JCheckBoxMenuItem("Show Helper...");
		interact.setActionCommand("mnu-interact");
		//interact.addActionListener(this);
		//interact.setState(this.getHelper().isVisible());

		helpMenu.add(this.getHELPAction());
		helpMenu.add(this.getUpdateAction());
		helpMenu.addSeparator();
		helpMenu.add(about);

		windowMenu.add(
			((DocumentManager) this.getDesktopManager()).getTileAction());
		windowMenu.add(
			((DocumentManager) this.getDesktopManager()).getCascadeAction());
		windowMenu.add(
			((DocumentManager) this.getDesktopManager()).getMinimizeAction());
		windowMenu.addSeparator();
		windowMenu.add(_OPEN_WINDOWS);

		// Add all our menues..===================================.
		_MENU_BAR.add(fileMenu);
		_MENU_BAR.add(editMenu);
		_MENU_BAR.add(viewMenu);
		_MENU_BAR.add(formatMenu);
		_MENU_BAR.add(windowMenu);
		_MENU_BAR.add(helpMenu);

		return _MENU_BAR;
	}
	/**
	* Called to exit the application.
	*/
	public void doExit() {

		// Store the state of the toolbars and interactive helper.
		this.getRegistry().setProperty(
			"USER",
			"show.file.toolbar",
			"" + this.getFileToolBar().isVisible());
		this.getRegistry().setProperty(
			"USER",
			"show.format.toolbar",
			"" + this.getFormatToolBar().isVisible());
		//this.getRegistry().putProperty("show.interact.helper", "" + this.getHelper().isVisible());

		// Store the apps last location and size.
		this.getRegistry().setProperty(
			"MAIN",
			"main.window.w",
			"" + this.getSize().width);
		this.getRegistry().setProperty(
			"MAIN",
			"main.window.h",
			"" + this.getSize().height);
		this.getRegistry().setProperty(
			"MAIN",
			"main.window.x",
			"" + this.getLocation().x);
		this.getRegistry().setProperty(
			"MAIN",
			"main.window.y",
			"" + this.getLocation().y);

		// Save the file history.
		this.getRegistry().deleteGroup("FILE_HISTORY");
		for (int p = 0; p < _FILE_HISTORY.getItemCount(); p++) {
			this.getRegistry().setProperty(
				"FILE_HISTORY",
				_FILE_HISTORY.getItem(p).getText(),
				_FILE_HISTORY.getItem(p).getActionCommand());
		}

		// store registry.
		try {
			this.getRegistry().commit();
		}
		catch (IOException ioe0) {
			System.out.println("Unable to save registry...");
			System.out.println(ioe0.toString());
		}
		this.dispose();
		System.exit(0);
	}
	/*
	* Get the applications DesktopManager
	* @return javax.swing.DesktopManager
	*/
	protected final DocumentManager getDesktopManager() {
		// if we have initalized properly,
		// we should get our implementation
		// of DocumentManager.
		return (DocumentManager) _DESKTOP.getDesktopManager();
	}
	/**
	* returns a number for giving new documents 
	* a unique name. If the number reaches a value
	* greater than the maximum for an integer,
	* it's reset to 0.
	* @return int
	*/
	public int getDocumentNumber() {
		if (_DOCUMENT_COUNT >= Integer.MAX_VALUE) {
			_DOCUMENT_COUNT = 0;
		}
		return _DOCUMENT_COUNT++;
	}
	protected final JToolBar getFileToolBar() {
		if (_FILE_TOOL_BAR == null) {
			_FILE_TOOL_BAR = new ActionToolBar();

			// This causes a fatal exception when not in windows L&F
			//_FILE_TOOL_BAR.putClientProperty("JToolBar.isRollover", Boolean.TRUE);

			((ActionToolBar) _FILE_TOOL_BAR).add(
				false,
				EditorActionManager.instance().getNewAction(
					this,
					((DocumentManager) this.getDesktopManager())));
			((ActionToolBar) _FILE_TOOL_BAR).add(
				false,
				EditorActionManager.instance().getOpenAction(
					this,
					((DocumentManager) this.getDesktopManager())));
			((ActionToolBar) _FILE_TOOL_BAR).add(
				false,
				EditorActionManager.instance().getSaveAction(
					this,
					((DocumentManager) this.getDesktopManager())));
			((ActionToolBar) _FILE_TOOL_BAR).addSeparator();
			((ActionToolBar) _FILE_TOOL_BAR).add(
				false,
				EditorActionManager.instance().getUndoAction());
			((ActionToolBar) _FILE_TOOL_BAR).add(
				false,
				EditorActionManager.instance().getRedoAction());
			((ActionToolBar) _FILE_TOOL_BAR).addSeparator();
			((ActionToolBar) _FILE_TOOL_BAR).add(
				false,
				EditorActionManager.instance().getCutAction());
			((ActionToolBar) _FILE_TOOL_BAR).add(
				false,
				EditorActionManager.instance().getCopyAction());
			((ActionToolBar) _FILE_TOOL_BAR).add(
				false,
				EditorActionManager.instance().getPasteAction());

			// Orientation Not working properly.
			String position =
				this.getRegistry().getString(
					"USER",
					"position.file.toolbar",
					BorderLayout.NORTH);
			if (position.equalsIgnoreCase(BorderLayout.WEST)
				|| position.equalsIgnoreCase(BorderLayout.EAST)) {
				((ActionToolBar) _FILE_TOOL_BAR).setOrientation(
					JToolBar.VERTICAL);
			}
			else if (
				position.equalsIgnoreCase(BorderLayout.NORTH)
					|| position.equalsIgnoreCase(BorderLayout.SOUTH)) {
				((ActionToolBar) _FILE_TOOL_BAR).setOrientation(
					JToolBar.HORIZONTAL);
			}

			((ActionToolBar) _FILE_TOOL_BAR).setVisible(
				this.getRegistry().getBoolean(
					"USER",
					"show.file.toolbar",
					true));
		}

		return _FILE_TOOL_BAR;
	}
	protected final JToolBar getFormatToolBar() {
		if (_FORMAT_TOOL_BAR == null) {
			_FORMAT_TOOL_BAR = new ActionToolBar();
			_FORMAT_TOOL_BAR.setLayout(new FlowLayout(FlowLayout.LEFT));

			// This causes a fatal exception when not in windows L&F
			//_FORMAT_TOOL_BAR.putClientProperty("JToolBar.isRollover", "" + true);

			//Code.debug("Create font colour dropdown.");
			ActionComboBox colours = new ActionComboBox();
			colours.setMinimumSize(new Dimension(0, 16));
			colours.setEditable(false);
			colours.setRenderer(new ColourActionCellRenderer());

			/**
			   Object[] keys = this.getColourRegistry().getKeys();
			   for(int c = 0; c < keys.length; c++) {
			   try {
			   colours.addItem(EditorActionManager.instance().getColourAction( ((String)keys[c]), Color.decode( this.getColourRegistry().getString(((String)keys[c]), null) ) ));
			   } catch(NumberFormatException nfe0) {
			   // ignore this.
			   }
			   }
			*/
			Enumeration colourEnum = this.getRegistry().getKeys("COLOURS");
			while (colourEnum.hasMoreElements()) {
				String colourKey = (String) colourEnum.nextElement();
				try {
					colours.addItem(
						EditorActionManager.instance().getColourAction(
							colourKey,
							Color.decode(
								this.getRegistry().getString(
									"COLOURS",
									colourKey,
									null))));
				}
				catch (NumberFormatException nfe0) {
					// ignore this.
				}
			}

			//getFontRegistry()
			//Code.debug("Create font faces dropdown.");
			ActionComboBox fonts = new ActionComboBox();
			fonts.setMinimumSize(new Dimension(0, 16));
			fonts.setEditable(false);
			fonts.setRenderer(new FontActionCellRenderer());

			/*
			  Action[] fontActions = EditorActionManager.instance().createDefaultFontFaceActions();
			  for(int c = 0; c < fontActions.length;c++) {
			  fonts.addItem(fontActions[c]);
			  }
			*/

			/*
			  Object[] fontKeys = this.getFontRegistry().getKeys();
			  for(int f = 0; f < fontKeys.length; f++) {
			  if(this.getFontRegistry().getBoolean((String)fontKeys[f], false)) {
			  fonts.addItem(EditorActionManager.instance().getFontFaceAction((String)fontKeys[f]));
			  }
			  }
			*/
			Enumeration fontEnum = this.getRegistry().getKeys("FONTS");
			while (fontEnum.hasMoreElements()) {
				String fontKey = (String) fontEnum.nextElement();
				if (this.getRegistry().getBoolean("FONTS", fontKey, false)) {
					fonts.addItem(
						EditorActionManager.instance().getFontFaceAction(
							fontKey));
				}
			}

			//Code.debug("Create font sizes dropdown.");

			ActionComboBox fsizes = new ActionComboBox();
			fsizes.setMinimumSize(new Dimension(0, 16));
			fsizes.setEditable(false);
			fsizes.setRenderer(new FSActionCellRenderer());
			Action[] fontSizes =
				EditorActionManager.instance().createFontSizeActionRange(
					this.getRegistry().getInteger(
						"MAIN",
						"font.sizes.minimum",
						6),
					this.getRegistry().getInteger(
						"MAIN",
						"font.sizes.maximum",
						150),
					this.getRegistry().getInteger(
						"MAIN",
						"font.sizes.granularity",
						2));
			for (int c = 0; c < fontSizes.length; c++) {
				fsizes.addItem(fontSizes[c]);
			}

			((ActionToolBar) _FORMAT_TOOL_BAR).add(colours);
			((ActionToolBar) _FORMAT_TOOL_BAR).add(fonts);
			((ActionToolBar) _FORMAT_TOOL_BAR).add(fsizes);
			((ActionToolBar) _FORMAT_TOOL_BAR).addSeparator();
			((ActionToolBar) _FORMAT_TOOL_BAR).add(
				false,
				EditorActionManager.instance().getAlignLeftAction());
			((ActionToolBar) _FORMAT_TOOL_BAR).add(
				false,
				EditorActionManager.instance().getAlignCenterAction());
			((ActionToolBar) _FORMAT_TOOL_BAR).add(
				false,
				EditorActionManager.instance().getAlignRightAction());
			((ActionToolBar) _FORMAT_TOOL_BAR).add(
				false,
				EditorActionManager.instance().getAlignJustifyAction());
			((ActionToolBar) _FORMAT_TOOL_BAR).addSeparator();
			((ActionToolBar) _FORMAT_TOOL_BAR).add(
				false,
				EditorActionManager.instance().getBoldAction());
			((ActionToolBar) _FORMAT_TOOL_BAR).add(
				false,
				EditorActionManager.instance().getItalicAction());
			((ActionToolBar) _FORMAT_TOOL_BAR).add(
				false,
				EditorActionManager.instance().getUnderlineAction());
			((ActionToolBar) _FORMAT_TOOL_BAR).add(
				false,
				EditorActionManager.instance().getStrikeThroughAction());

			// Orientation Not working properly.
			String position =
				this.getRegistry().getString(
					"USER",
					"position.format.toolbar",
					BorderLayout.NORTH);
			if (position.equalsIgnoreCase(BorderLayout.WEST)
				|| position.equalsIgnoreCase(BorderLayout.EAST)) {
				((ActionToolBar) _FILE_TOOL_BAR).setOrientation(
					JToolBar.VERTICAL);
			}
			else if (
				position.equalsIgnoreCase(BorderLayout.NORTH)
					|| position.equalsIgnoreCase(BorderLayout.SOUTH)) {
				((ActionToolBar) _FILE_TOOL_BAR).setOrientation(
					JToolBar.HORIZONTAL);
			}
			_FORMAT_TOOL_BAR.setVisible(
				this.getRegistry().getBoolean(
					"USER",
					"show.format.toolbar",
					true));
		}

		return _FORMAT_TOOL_BAR;
	}
	protected final Action getHELPAction() {
		return new HELPAction();
	}
	protected final Main getMain() {
		return this;
	}
	protected final Action getPrintAction() {
		return new PrintAction();
	}
	/**
	* Gets the current Registry for Main
	*/
	protected final Registry getRegistry() {
		if (_REGISTRY == null) {
			try {
				_REGISTRY = Registry.loadForClass(Main.class);

				// Default Users group...
				if (!(_REGISTRY.sizeOf("USER") > 0)) {
					_REGISTRY.setProperty(
						"USER",
						"user.name",
						System.getProperty("user.name"));
					_REGISTRY.setProperty(
						"USER",
						"user.timezone",
						System.getProperty("user.timezone"));
					_REGISTRY.setProperty(
						"USER",
						"user.home",
						System.getProperty("user.home"));
					_REGISTRY.setProperty(
						"USER",
						"user.region",
						System.getProperty("user.region"));
					_REGISTRY.setProperty(
						"USER",
						"temp.directory",
						System.getProperty("java.io.tmpdir"));
				}

				// Default Options group
				if (!(_REGISTRY.sizeOf("OPTION") > 0)) {
					_REGISTRY.setProperty("OPTION", "Cut", "true");
					_REGISTRY.setProperty("OPTION", "Copy", "true");
					_REGISTRY.setProperty("OPTION", "Paste", "true");
					_REGISTRY.setProperty("OPTION", "-", "true");
					_REGISTRY.setProperty("OPTION", "Undo", "true");
					_REGISTRY.setProperty("OPTION", "Redo", "true");
					_REGISTRY.setProperty("OPTION", "-", "true");
					_REGISTRY.setProperty("OPTION", "SelectAll", "true");
					_REGISTRY.setProperty("OPTION", "SelectNone", "true");
					_REGISTRY.setProperty("OPTION", "-", "true");
					//_REGISTRY.putProperty("Define", "true");
				}

				// Default Fonts group
				if (!(_REGISTRY.sizeOf("FONTS") > 0)) {
					// Get all the fonts and set them to not load.
					String[] families =
						GraphicsEnvironment
							.getLocalGraphicsEnvironment()
							.getAvailableFontFamilyNames();
					for (int f = 0; f < families.length; f++) {
						if (families[f].indexOf(".") < 0) {
							_REGISTRY.setProperty(
								"FONTS",
								families[f],
								"false");
						}
					}
					_REGISTRY.setProperty("FONTS", "Default", "true");
					_REGISTRY.setProperty("FONTS", "Dialog", "true");
					_REGISTRY.setProperty("FONTS", "DialogInput", "true");
					_REGISTRY.setProperty("FONTS", "Monospaced", "true");
					_REGISTRY.setProperty("FONTS", "SansSerif", "true");
					_REGISTRY.setProperty("FONTS", "Serif", "true");
				}

				// Default Colours
				if (!(_REGISTRY.sizeOf("COLOURS") > 0)) {
					_REGISTRY.setProperty(
						"COLOURS",
						"White",
						Format.colorToHex(Color.white));
					_REGISTRY.setProperty(
						"COLOURS",
						"Black",
						Format.colorToHex(Color.black));
					_REGISTRY.setProperty(
						"COLOURS",
						"Red",
						Format.colorToHex(Color.red));
					_REGISTRY.setProperty(
						"COLOURS",
						"Green",
						Format.colorToHex(Color.green));
					_REGISTRY.setProperty(
						"COLOURS",
						"Blue",
						Format.colorToHex(Color.blue));
					_REGISTRY.setProperty(
						"COLOURS",
						"Orange",
						Format.colorToHex(Color.orange));
					_REGISTRY.setProperty(
						"COLOURS",
						"Dark Gray",
						Format.colorToHex(Color.darkGray));
					_REGISTRY.setProperty(
						"COLOURS",
						"Gray",
						Format.colorToHex(Color.gray));
					_REGISTRY.setProperty(
						"COLOURS",
						"Light Gray",
						Format.colorToHex(Color.lightGray));
					_REGISTRY.setProperty(
						"COLOURS",
						"Cyan",
						Format.colorToHex(Color.cyan));
					_REGISTRY.setProperty(
						"COLOURS",
						"Magenta",
						Format.colorToHex(Color.magenta));
					_REGISTRY.setProperty(
						"COLOURS",
						"Pink",
						Format.colorToHex(Color.pink));
					_REGISTRY.setProperty(
						"COLOURS",
						"Yellow",
						Format.colorToHex(Color.yellow));
				}
			}
			catch (java.io.IOException ioe0) {
				//Code.failed(ioe0);
			}
		}
		return _REGISTRY;
	}

	//"USER", 
	protected JLabel getStatusLabel() {
		if (_STATUS_LABEL == null) {
			_STATUS_LABEL = new JLabel("Editing");
			_STATUS_LABEL.setBorder(BorderFactory.createEtchedBorder());
		}
		return _STATUS_LABEL;
	}
	protected final Action getUpdateAction() {
		return new UpdateAction();
	}
	private void init() {
		// Set the main application Icon.
		this.setIconImage(Loader.load("jmonkey16.gif"));

		// Install out custom look & feel
		// This should be in another Runtime
		// class that will gets its data from
		// a property file. we can then add
		// many L&F's as we need.
		//try {
		//MainSplash splasher = new MainSplash(new ImageIcon("com/jmonkey/office/common/images/jmsplash.gif"));

		//splasher.showStatus("Installing Look and Feel..");

		// Install custom PLAF. We should have a
		// global LAF manager instead, that is
		// not specific to Main.
		//UIManager.installLookAndFeel("Active", com.jmonkey.office.common.swing.plaf.active.ActiveLookAndFeel.class.getName());

		// Set the look & feel for the app.
		try {
			UIManager.setLookAndFeel(
				this.getRegistry().getString(
					"MAIN",
					"main.look&feel",
					UIManager.getSystemLookAndFeelClassName()));
		}
		catch (Exception e) {
			System.out.println("Unknown Look & Feel. Using Defaults.");
		}

		//Thread.sleep(1500);
		//splasher.showStatus("Setting up Desktop..");
		_DESKTOP = new MainDesktop();
		_DESKTOP.setBorder(BorderFactory.createLoweredBevelBorder());

		// Makes it just show the outline when we drag.  Speeds up
		// program significantly -- Matt
		// I've added support so this can be changed later.
		// This says "default to true" if the property is
		// not already in the registry. (i.e. its the first
		// run of the program) -- Brill
		if (this.getRegistry().getBoolean("MAIN", "mdi.outline.drag", true)) {
			_DESKTOP.putClientProperty("JDesktopPane.dragMode", "outline");
		}
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());

		// We'll add everything to our special content pane first.
		this.setContentPane(contentPane);

		// Don't need to store DocumentManager
		// The desktop pane should always know
		// where it is and how to get it.
		// this helps us to write our code
		// so it doesn't depend on our own
		// copy being valid
		_DESKTOP.setDesktopManager(new DocumentManager(this));

		// we need to add the toolbars
		// before we add the desktop pane.
		// -- Brill 03/18/1999
		//Thread.sleep(1500);
		//splasher.showStatus("Setting up toolbars...");
		JPanel fileToolPanel = new JPanel();
		fileToolPanel.setLayout(new BorderLayout());
		fileToolPanel.add(
			this.getFileToolBar(),
			this.getRegistry().getString(
				"USER",
				"position.file.toolbar",
				BorderLayout.WEST));
		// the edit toolbar should also go in here.
		JPanel formatToolPanel = new JPanel();
		formatToolPanel.setLayout(new BorderLayout());
		formatToolPanel.add(
			this.getFormatToolBar(),
			this.getRegistry().getString(
				"USER",
				"position.format.toolbar",
				BorderLayout.NORTH));
		JPanel desktopContainer = new JPanel();
		desktopContainer.setLayout(new BorderLayout());
		desktopContainer.add(_DESKTOP, BorderLayout.CENTER);
		fileToolPanel.add(formatToolPanel, BorderLayout.CENTER);
		formatToolPanel.add(desktopContainer, BorderLayout.CENTER);
		contentPane.add(this.getStatusLabel(), BorderLayout.SOUTH);
		contentPane.add(fileToolPanel, BorderLayout.CENTER);
		//contentPane.add(_DESKTOP, BorderLayout.CENTER);

		// Create and add the menu bar.
		this.setJMenuBar(createMenuBar());
		//Thread.sleep(1500);
		//splasher.showStatus("Reading in Registry...");

		Enumeration fhEnum = this.getRegistry().getKeys("FILE_HISTORY");
		while (fhEnum.hasMoreElements()) {
			String fhKey = (String) fhEnum.nextElement();
			JMenuItem item = new JMenuItem(fhKey);
			item.setActionCommand(
				this.getRegistry().getString("FILE_HISTORY", fhKey, fhKey));
			item.addActionListener(_FILE_HISTROY_ACTION);
			_FILE_HISTORY.add(item);
		}

		//Thread.sleep(1500);
		//splasher.close();

		// Added this to dispose of
		// the main app window when
		// it gets closed.
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				doExit();
			}
		});

		// Restore the apps last location and size.
		// Changed this to use the screen size as defaults.
		this.setSize(
			this.getRegistry().getInteger(
				"MAIN",
				"main.window.w",
				((Toolkit.getDefaultToolkit().getScreenSize().width / 5) * 4)),
			this.getRegistry().getInteger(
				"MAIN",
				"main.window.h",
				((Toolkit.getDefaultToolkit().getScreenSize().height / 5)
					* 4)));
		this.setLocation(
			this.getRegistry().getInteger("MAIN", "main.window.x", 0),
			this.getRegistry().getInteger("MAIN", "main.window.y", 0));
	}

	/**
	* Main method...
	*/
	public static void main(String[] args) {
		Splash s = new Splash(400, 200);
		s.setImage(Loader.load("logo.gif"));
		s.getVersionDate().setText("June 15 2000");
		s.getVersion().setText("0.1.1 Alpha");
		s.getAuthor().setText("Founded by Brill Pappin & Matthew Schmidt.");
		s.getCopyright().setText(
			"This software is licensed under the GNU GPL v.2");
		s.getTital().setText("Lexi");
		s.getDescription().setText("A 100% pure Java 2 word processor");
		s.showSplash();
		// Main app = new Main(args);
		s.hideSplash();
	}
	private void updateOpenWindowsMenu() {
		String[] openDocs =
			((DocumentManager) this.getDesktopManager()).openDocumentList();
		_OPEN_WINDOWS.removeAll();

		for (int o = 0; o < openDocs.length; o++) {
			JMenuItem item = new JMenuItem(openDocs[o]);
			item.setActionCommand(openDocs[o]);
			item.addActionListener(_OPEN_WINDOW_ACTION);
			_OPEN_WINDOWS.insert(item, 0);
		}
	}
}
