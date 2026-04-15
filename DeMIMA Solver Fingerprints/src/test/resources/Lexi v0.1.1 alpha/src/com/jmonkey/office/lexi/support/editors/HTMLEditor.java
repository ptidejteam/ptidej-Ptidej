package com.jmonkey.office.lexi.support.editors;


// Java API imports
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.Utilities;

import com.jmonkey.office.lexi.support.Editor;
import com.jmonkey.office.lexi.support.EditorActionManager;

/**
*
*/
public final class HTMLEditor extends Editor implements MouseListener, KeyListener {
	// Public members
	/**
	* The Content type of the editor.
	*/
	public static final String CONTENT_TYPE = VALID_CONTENT_TYPES[1];
	
	/**
	* File Extensions this editor will handle.
	*/
	public static final String[] FILE_EXTENSIONS = {"html", "htm"};

	// Private Members
	private JTextPane _EDITOR = null;
	private boolean _CHANGED = false;
	private E _EVENT_LISTENER = null;
	private File _FILE = null;
	
	// Stuff for ActionManager ==================
	private Element _CURRENT_RUN = null;
	private Element _CURRENT_PARAGRAPH = null;

	// Inner Classes ==========================================================

	/**
	* Document Event manager
	*/
	private final class E extends Object implements DocumentListener, UndoableEditListener, HyperlinkListener, FocusListener, VetoableChangeListener, ChangeListener {
		private HTMLEditor _PARENT = null;

		/**
		* Default DFL constructor.
		* @param parent com.jmonkey.common.StyledEditor
		*/
		protected E(HTMLEditor parent) {
			_PARENT = parent;
		}

		/**
		* FocusListener reciver.
		* @param e java.awt.event.FocusEvent
		*/
		public void focusGained(FocusEvent e) {
			// Code.event("focusGained:" + e.toString());
			_PARENT.activate();
		}

		/**
		* FocusListener reciver.
		* @param e java.awt.event.FocusEvent
		*/
		public void focusLost(FocusEvent e) {
			// This causes a problem, because
			// it gets called when menus or 
			// dialogs are opened.
			//if(!e.isTemporary()){
			//	_PARENT.deactivate();
			//}
		}

		/**
		* DocumentListener reciver.
		* @param e javax.swing.event.DocumentEvent
		*/
		public void insertUpdate(DocumentEvent e) {
			// Code.event("insertUpdate:" + e.toString());
			if(!_PARENT.isChanged()){
				_PARENT.setChanged(true);
			}
		}

		/**
		* DocumentListener reciver.
		* @param e javax.swing.event.DocumentEvent
		*/
		public void removeUpdate(DocumentEvent e) {
			// Code.event("removeUpdate:" + e.toString());
			if(!_PARENT.isChanged()){
				_PARENT.setChanged(true);
			}
		}

		/**
		* DocumentListener reciver.
		* @param e javax.swing.event.DocumentEvent
		*/
		public void changedUpdate(DocumentEvent e) {
			// Code.event("changedUpdate:" + e.toString());
			if(!_PARENT.isChanged()){
				_PARENT.setChanged(true);
			}
		}

		/**
		* UndoableEditListener reciver.
		* @param e javax.swing.event.UndoableEditEvent
		*/
		public void undoableEditHappened(UndoableEditEvent e) {
			_PARENT.getUndoManager().addEdit(e.getEdit());
		}

		/**
		* HyperlinkListener reciver.
		* @param e javax.swing.event.HyperlinkEvent
		*/
		public void hyperlinkUpdate(HyperlinkEvent e) {
			//Code.event("hyperlinkUpdate:" + e.toString());
		}

		/**
		* VetoableChangeListener reciver.
		* @param evt java.beans.PropertyChangeEvent
		* @exception java.beans.PropertyVetoException
		*/
		public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
			//Code.event("vetoableChange:" + evt.toString());
		}

		/**
		* ChangeListener reciver.
		* @param e javax.swing.event.ChangeEvent
		*/
		public void stateChanged(ChangeEvent e) {
			//Code.event("stateChanged:" + e.toString());
		}
	}
	/**
	* Default Document Constructor.
	*/
	public HTMLEditor() {
		super();
		this.init();
	}
	public void append(File file) throws IOException {
		EditorActionManager.threads(new FRThread(file) {
								  			public void run() {
								  				// ===============================
								  				BufferedInputStream bis = null;
							  				try {
								  					bis = new BufferedInputStream(new FileInputStream(this.file));
								  					((StyledEditorKit)_EDITOR.getEditorKit()).read(bis, _EDITOR.getStyledDocument(), _EDITOR.getStyledDocument().getLength());
								  					setChanged(true);
							  				} catch(BadLocationException ble0) {
								  					//Code.failed(ble0);
								  					//throw new IOException(ble0.getMessage());
							  				} catch(FileNotFoundException fnfe0) {
								  					//Code.failed(fnfe0);
								  					//throw new IOException(fnfe0.getMessage());
							  				} catch(IOException ioe0) {
								  					//Code.failed(ioe0);
								  					//throw ioe0;
							  				} finally {
							  					if(bis != null) {
							  						try {
								  							bis.close();
							  						} catch(Throwable t) {
								  							// ignore it.
								  							// not the best solution
								  							// but it will do for now.
								  						}
								  					}
								  				}
								  				// ===============================
								  			}
								  		});
	}
	/**
	* @param start int
	* @param length int
	* @param wordsOnly boolean expand or 
	* contract length to match the nearest 
	* whole word.
	*/
	public void documentSetSelection(int start, int length, boolean wordsOnly) {
			try {
				_EDITOR.getCaret().setDot((wordsOnly ? Utilities.getWordStart(_EDITOR, start) : start));
				_EDITOR.getCaret().moveDot((wordsOnly ? Utilities.getWordEnd(_EDITOR, length) : length));
			} catch(BadLocationException ble0) {
				// what should we do here?
			}
	}
	/**
	 * Returns the content type as a MIME string.
	 * @return java.lang.String
	 */
	public final String getContentType() {
		return _EDITOR.getContentType();
	}
	public Element getCurrentParagraph(){
		return _CURRENT_PARAGRAPH;
	}
	public Element getCurrentRun(){
		return _CURRENT_RUN;
	}
	/**
	 * Returns the content type as a MIME string.
	 * @return java.lang.String
	 */
	private E getEventListener() {
		if(_EVENT_LISTENER == null) {
			_EVENT_LISTENER = new E(this);
		}
		return _EVENT_LISTENER;
	}
	/**
	 * Returns the content type as a MIME string.
	 * @return java.lang.String
	 */
	public final String[] getFileExtensions() {
		return FILE_EXTENSIONS;
	}
	public MutableAttributeSet getInputAttributes(){
		return _EDITOR.getInputAttributes();// getCharacterAttributes();
	}
	public JEditorPane getTextComponent(){
		return _EDITOR;
	}
	public void hasBeenActivated(Editor editor){
		if(editor == this){
			//Code.debug("hasBeenActivated");
			EditorActionManager.enableFormatActions(true);
			EditorActionManager.enableGenericActions(true);
			EditorActionManager.enableDocumentActions(true);
			// Test the state of the contained file.
			if(this.hasFile()){
				if(this.isNew()){
					EditorActionManager.enableAction(EditorActionManager.FILE_REVERT_ACTION_PREFIX, false);
					EditorActionManager.enableAction(EditorActionManager.FILE_SAVE_ACTION_PREFIX, true);
				}else{
					if(this.isChanged()){
						EditorActionManager.enableAction(EditorActionManager.FILE_REVERT_ACTION_PREFIX, true);
						EditorActionManager.enableAction(EditorActionManager.FILE_SAVE_ACTION_PREFIX, true);
					}else{
						EditorActionManager.enableAction(EditorActionManager.FILE_REVERT_ACTION_PREFIX, true);
						EditorActionManager.enableAction(EditorActionManager.FILE_SAVE_ACTION_PREFIX, false);
					}
				}
			}else{
				EditorActionManager.enableAction(EditorActionManager.FILE_SAVE_ACTION_PREFIX, true);
				EditorActionManager.enableAction(EditorActionManager.FILE_REVERT_ACTION_PREFIX, false);
			}
			// Enable/disable redo
			EditorActionManager.enableAction(EditorActionManager.REDO_ACTION_PREFIX, getUndoManager().canRedo());
			// Enable/disable undo
			EditorActionManager.enableAction(EditorActionManager.UNDO_ACTION_PREFIX, getUndoManager().canUndo());
		}
	}
	public void hasBeenDeactivated(Editor editor){
		if(editor == this){
			//Code.debug("hasBeenDeactivated");
			//ActionManager.enableFormatActions(false);
		}
	}
	public void init() {
		//JPanel contentPane = new JPanel();
		this.setLayout(new BorderLayout());
		this.getRegistry();

		// Editor Setup
		JScrollPane sp = new JScrollPane();
		_EDITOR = new JTextPane();
		_EDITOR.setContentType(CONTENT_TYPE); // set to plain text.
		_EDITOR.setCaretColor(Color.black);
		_EDITOR.getCaret().setBlinkRate(300);
		
		// Event Listeners
		_EDITOR.addFocusListener(this.getEventListener());
		_EDITOR.getDocument().addDocumentListener(this.getEventListener());
		_EDITOR.getDocument().addUndoableEditListener(this.getUndoManager());
		_EDITOR.addMouseListener(this);
		_EDITOR.addKeyListener(this);
		
		// this should be settable...
		_EDITOR.setBorder(BorderFactory.createLoweredBevelBorder());

		//finalize init
		sp.setViewportView(_EDITOR);
		this.add(sp, BorderLayout.CENTER);
	}
	public void insert(File file, int position) throws IOException {
		EditorActionManager.threads(new FRThread(file) {
								  			public void run() {
								  				// ===============================
								  				BufferedInputStream bis = null;
							  				try {
								  					bis = new BufferedInputStream(new FileInputStream(this.file));
								  					((StyledEditorKit)_EDITOR.getEditorKit()).read(bis, _EDITOR.getStyledDocument(), this.position);
								  					setChanged(true);
							  				} catch(BadLocationException ble0) {
								  					//Code.failed(ble0);
								  					//throw new IOException(ble0.getMessage());
							  				} catch(FileNotFoundException fnfe0) {
								  					//Code.failed(fnfe0);
								  					//throw new IOException(fnfe0.getMessage());
							  				} catch(IOException ioe0) {
								  					//Code.failed(ioe0);
								  					//throw ioe0;
							  				} finally {
							  					if(bis != null) {
							  						try {
								  							bis.close();
							  						} catch(Throwable t) {
								  							// ignore it.
								  							// not the best solution
								  							// but it will do for now.
								  						}
								  					}
								  				}
								  				// ===============================
								  			}
								  		});
	}
	/**
	* Has the document changed since we loaded/created it?
	* @return boolean
	*/
	public final boolean isChanged() {
		return _CHANGED;
	}
	/**
	 * Does the document contain any data?
	 * @return boolean
	 */
	public final boolean isEmpty() {
		// I think this will need some work...
		// The document object most likely will add
		// some hidden char at the begining of the
		// document, depending on the content type.
		// however, we don't want to include those
		// as not empty.
		return !(_EDITOR.getText().length() > 0);
	}
	/**
	 * Does the document contain formatting, or
	 * can we write it as plain text without 
	 * loosing anything.
	 * @return boolean
	 */
	public final boolean isFormatted() {
		// Again, the easy way out...
		// How can this be improved?
		return false;
	}
	/**
	 * Does the document represent a new file?
	 * @return boolean
	 */
	public final boolean isNew() {
		if(_FILE != null) {
			return  !(_FILE.exists() && _FILE.isFile());
		} else {
			return true;
		}
	}
	public void keyPressed(KeyEvent kp) {
	if (kp.getKeyCode() == KeyEvent.VK_TAB) {
	    System.out.println("Caret Position: " + _EDITOR.getCaretPosition());
	    _EDITOR.setCaretPosition(_EDITOR.getCaretPosition() + 5);
	    }
	}
	public void keyReleased(KeyEvent kr) {}
	public void keyTyped(KeyEvent kt) {}
	public void mouseClicked(MouseEvent e) {
	if (SwingUtilities.isRightMouseButton(e) == true) {
		JPopupMenu popUP = this.getPopup();			
		popUP.show(this, e.getX(), e.getY());
	}	
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void read(File file) throws IOException {
		EditorActionManager.threads(new FRThread(file) {
								  			public void run() {
								  				// ===============================
								  				BufferedInputStream bis = null;
							  				try {
								  					bis = new BufferedInputStream(new FileInputStream(this.file));
								  					((StyledEditorKit)_EDITOR.getEditorKit()).read(bis, _EDITOR.getStyledDocument(), 0);
								  					setChanged(false);
							  				} catch(BadLocationException ble0) {
								  					//Code.failed(ble0);
								  					//throw new IOException(ble0.getMessage());
							  				} catch(FileNotFoundException fnfe0) {
								  					//Code.failed(fnfe0);
								  					//throw new IOException(fnfe0.getMessage());
							  				} catch(IOException ioe0) {
								  					//Code.failed(ioe0);
								  					//throw ioe0;
							  				} finally {
							  					if(bis != null) {
							  						try {
								  							bis.close();
							  						} catch(Throwable t) {
								  							// ignore it.
								  							// not the best solution
								  							// but it will do for now.
								  						}
								  					}
								  				}
								  				// ===============================
								  			}
								  		});
	}
	/*
	* Gives focus to the editor
	*/
	public void requestFocus(){
		_EDITOR.requestFocus();
	}
	/**
	* Sets the blink rate of the Caret.
	* @param colour java.awt.Color
	*/
	public final void setCaretBlinkRate(int rate){
		_EDITOR.getCaret().setBlinkRate(rate);
	}
	/**
	* Sets the colour of the Caret.
	* @param colour java.awt.Color
	*/
	public final void setCaretColor(Color colour){
		_EDITOR.setCaretColor(colour);
	}
	/**
	* Set the document changed flag.
	* @param changed boolean
	*/
	public final void setChanged(boolean changed) {
		_CHANGED = changed;
		this.hasBeenActivated(this);
	}
	public void setCurrentParagraph(Element paragraph){
		_CURRENT_PARAGRAPH = paragraph;
	}
	public void setCurrentRun(Element run){
		_CURRENT_RUN = run;
	}
	/**
	* Sets the selection colour.
	* @param colour java.awt.Color
	*/
	public final void setSelectionColor(Color colour){
		_EDITOR.setSelectionColor(colour);
	}
	public void write(File file) throws IOException {
		EditorActionManager.threads(new FWThread(file) {
								  			public void run() {
								  				BufferedOutputStream bos = null;
							  				try {
								  					bos = new BufferedOutputStream(new FileOutputStream(this.file));
								  					((StyledEditorKit)_EDITOR.getEditorKit()).write(bos, _EDITOR.getStyledDocument(), 0, _EDITOR.getStyledDocument().getLength());
								  					setChanged(false);
							  				} catch(BadLocationException ble0) {
								  					//Code.failed(ble0);
								  					//throw new IOException(ble0.getMessage());
							  				} catch(IOException ioe0) {
								  					//Code.failed(ioe0);
								  					//throw ioe0;
							  				} finally {
							  					if(bos != null) {
							  						try {
								  							bos.flush();
								  							bos.close();
							  						} catch(Throwable t) {
								  							// ignore it.
								  							// not the best solution
								  							// but it will do for now.
								  						}
								  					}
								  				}
								  			}
								  		});
	}
}
