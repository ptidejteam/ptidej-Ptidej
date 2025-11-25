package com.jmonkey.office.lexi.support;


// Java AWT Imports
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.jmonkey.office.lexi.support.images.Loader;

public class EditorActionManager extends ActionManager {
	// Modifier Constants
	public static final int COLOUR_BLACK = Color.black.getRGB();
	public static final int COLOUR_BLUE = Color.blue.getRGB();
	public static final int COLOUR_CYAN = Color.cyan.getRGB();
	public static final int COLOUR_DARKGRAY = Color.darkGray.getRGB();
	public static final int COLOUR_GRAY = Color.gray.getRGB();
	public static final int COLOUR_GREEN = Color.green.getRGB();
	public static final int COLOUR_LIGHTGRAY = Color.lightGray.getRGB();
	public static final int COLOUR_MAGENTA = Color.magenta.getRGB();
	public static final int COLOUR_ORANGE = Color.orange.getRGB();
	public static final int COLOUR_PINK = Color.pink.getRGB();
	public static final int COLOUR_RED = Color.red.getRGB();
	public static final int COLOUR_WHITE = Color.white.getRGB();
	public static final int COLOUR_YELLOW = Color.yellow.getRGB();

	// Action Types.
	public static final String BEEP_ACTION_PREFIX = "Beep";
	public static final String ALIGN_LEFT_ACTION_PREFIX = "Align Left";
	public static final String ALIGN_RIGHT_ACTION_PREFIX = "Align Right";
	public static final String ALIGN_CENTER_ACTION_PREFIX = "Align Center";
	public static final String ALIGN_JUSTIFIED_ACTION_PREFIX = "Align Justified";
	public static final String BOLD_ACTION_PREFIX = "Bold";
	public static final String ITALIC_ACTION_PREFIX = "Italic";
	public static final String UNDERLINE_ACTION_PREFIX = "Underline";
	public static final String STRIKETHROUGH_ACTION_PREFIX = "Strikethrough";
	public static final String CUT_ACTION_PREFIX = "Cut";
	public static final String COPY_ACTION_PREFIX = "Copy";
	public static final String PASTE_ACTION_PREFIX = "Paste";
	public static final String SELECTALL_ACTION_PREFIX = "Select All";
	public static final String SELECTNONE_ACTION_PREFIX = "Select None";
	public static final String UNDO_ACTION_PREFIX = "Undo";
	public static final String REDO_ACTION_PREFIX = "Redo";
	public static final String COLOUR_CHOOSER_ACTION_PREFIX = "Colour Chooser...";
	public static final String FONT_CHOOSER_ACTION_PREFIX = "Font Chooser...";
	public static final String SEARCH_ACTION_PREFIX = "Find...";
	public static final String REPLACE_ACTION_PREFIX = "Find & Replace...";
	public static final String FILE_NEW_ACTION_PREFIX = "New";
	public static final String FILE_OPEN_ACTION_PREFIX = "Open...";
	public static final String FILE_OPENAS_ACTION_PREFIX = "Open As...";
	public static final String FILE_REVERT_ACTION_PREFIX = "Revert To Saved";
	public static final String FILE_SAVE_ACTION_PREFIX = "Save";
	public static final String FILE_SAVEAS_ACTION_PREFIX = "Save As...";
	public static final String FILE_SAVECOPY_ACTION_PREFIX = "Save Copy...";

	//public static final String FONT_FAMILY_ACTION_PREFIX = "format-font-family@";
	//public static final String FONT_SIZE_ACTION_PREFIX = "format-font-size@";
	//public static final String FONT_COLOUR_ACTION_PREFIX = "format-font-colour@";

	//StyleConstants.ALIGN_RIGHT;

	// ========= PRIVATE MEMBERS ===================
	private static EditorActionManager _INSTANCE = null;
	private final CaretListener _ATTRIBUTE_TRACKER = new AttributeTracker();
	private static Editor _EDITOR = null;
	private static Map _ACTIONS = Collections.synchronizedMap(new HashMap());
	//private static ThreadPool _THREADPOOL = null;

	/**
	* Tracks caret movement and keeps the input attributes set 
	* to reflect the current set of attribute definitions at the 
	* caret position. 
	*/
	protected final class AttributeTracker implements CaretListener, Serializable {
		protected AttributeTracker() {
			super();
		}

		//# Still referencing the local versions

		public void caretUpdate(CaretEvent e) {
			if(EditorActionManager.getActiveEditor() != null) {
				int dot = e.getDot();
				int mark = e.getMark();
				if (dot == mark) {
					// record current character attributes.
					// We should check for a JEditorPane here.
					JTextComponent c = (JTextComponent) e.getSource();
					StyledDocument doc = (StyledDocument) c.getDocument();
					Element run = doc.getCharacterElement(Math.max(dot-1, 0));
					EditorActionManager.getActiveEditor().setCurrentParagraph(doc.getParagraphElement(dot));
					if (run != getActiveEditor().getCurrentRun()) {
						//_CURRENT_RUN = run;
						EditorActionManager.getActiveEditor().setCurrentRun(run);
						EditorActionManager.instance().createInputAttributes(EditorActionManager.getActiveEditor().getCurrentRun(), EditorActionManager.getActiveEditor().getInputAttributes());
					}
				}
			}
		}

	} // End of AttributeTracker ====================================================

	protected final class FontChooserAction extends AbstractAction {
		//BufferedImage
		private JFrame _PARENT;

		public FontChooserAction(String nm, JFrame component) {
			super(nm);
			this._PARENT = component;
		}

		public void actionPerformed(ActionEvent e) {
			Font font = FontChooser.display(_PARENT);
			if(font != null) {
				//JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
				Editor editor = EditorActionManager.getActiveEditor();
				String family = font.getFamily();
				int size = font.getSize();
				boolean is_bold = font.isBold();
				boolean is_italic = font.isItalic();

				//Code.debug("Font Chooser: " + font.toString());
				if (family != null) {
					MutableAttributeSet attr = editor.getSimpleAttributeSet();
					StyleConstants.setFontFamily(attr, family);
					StyleConstants.setFontSize(attr, size);
					StyleConstants.setItalic(attr, is_italic);
					StyleConstants.setBold(attr, is_bold);

					/*
					boolean underline = (StyleConstants.isItalic(attr)) ? false : true;
					StyleConstants.setItalic(attr, underline);
					boolean strike = (StyleConstants.isItalic(attr)) ? false : true;
					StyleConstants.setItalic(attr, strike);
					*/

					EditorActionManager.instance().setCharacterAttributes(editor.getTextComponent(), attr, false);
				} else {
					Toolkit.getDefaultToolkit().beep();
				}
			}
		}
	}

	protected final class FontFamilyAction extends AbstractAction {
		private String family;

		public FontFamilyAction(String nm, String family) {
			super(nm);
			this.family = family;
		}

		public void actionPerformed(ActionEvent e) {
			//JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
			Editor editor = EditorActionManager.getActiveEditor();
			if (editor != null) {
				String family = this.family;
				if ((e != null) && (e.getSource() == editor)) {
					String s = e.getActionCommand();
					if (s != null) {
						family = s;
						//Code.debug("family: " + s);
					}
				}
				if (family != null) {
					MutableAttributeSet attr = editor.getSimpleAttributeSet();
					StyleConstants.setFontFamily(attr, family);
					EditorActionManager.instance().setCharacterAttributes(editor.getTextComponent(), attr, false);
				} else {
					Toolkit.getDefaultToolkit().beep();
				}
			}
		}
	} // End FontFamilyAction class ===================================================

	protected final class FontSizeAction extends AbstractAction {
		private int size;

		public FontSizeAction(String nm, int size) {
			super(nm);
			this.size = size;
		}

		public void actionPerformed(ActionEvent e) {
			//JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
			Editor editor = EditorActionManager.getActiveEditor();
			if (editor != null) {
				int size = this.size;
				if ((e != null) && (e.getSource() == editor)) {
					String s = e.getActionCommand();
					try {
						size = Integer.parseInt(s, 10);
					} catch (NumberFormatException nfe) {
					}
				}
				if (size != 0) {
					MutableAttributeSet attr = editor.getSimpleAttributeSet();
					StyleConstants.setFontSize(attr, size);
					EditorActionManager.instance().setCharacterAttributes(editor.getTextComponent(), attr, false);
				} else {
					Toolkit.getDefaultToolkit().beep();
				}
			}
		}
	} // End FontSizeAction ==============================================================

	protected final class ColourChooserAction extends AbstractAction {
		//BufferedImage
		private JFrame _PARENT;

		public ColourChooserAction(String nm, JFrame component) {
			super(nm);
			this._PARENT = component;
		}

		public void actionPerformed(ActionEvent e) {
			//JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
			Editor editor = EditorActionManager.getActiveEditor();
			if (editor != null) {
				Color fg = JColorChooser.showDialog(_PARENT, "Choose a colour...", null);
				if ((e != null) && (e.getSource() == editor) && (fg != null)) {
					String s = e.getActionCommand();
					try {
						fg = Color.decode(s);
					} catch (NumberFormatException nfe) {
					}
				}
				if (fg != null) {
					MutableAttributeSet attr = editor.getSimpleAttributeSet();
					StyleConstants.setForeground(attr, fg);
					EditorActionManager.instance().setCharacterAttributes(editor.getTextComponent(), attr, false);
				} else {
					Toolkit.getDefaultToolkit().beep();
				}
			}
		}
	}

	protected final class ForegroundAction extends AbstractAction {
		protected Color fg = null;;
		protected String name = null;

		public ForegroundAction(String nm, Color fg) {
			//super(nm, new ImageIcon(EditorActionManager.instance().create16x16ColourRec(c, fg)));
			super(nm);
			this.name = nm;
			this.fg = fg;
		}

		public void actionPerformed(ActionEvent e) {
			Editor editor = EditorActionManager.getActiveEditor();
			if (editor != null) {
				Color fg = this.fg;
				if (e != null && e.getSource() == editor) {
					String s = e.getActionCommand();
					try {
						fg = Color.decode(s);
					} catch (NumberFormatException nfe) {
					}
				}

				if (fg != null) {
					MutableAttributeSet attr = editor.getSimpleAttributeSet();
					StyleConstants.setForeground(attr, fg);
					EditorActionManager.instance().setCharacterAttributes(editor.getTextComponent(), attr, false);
				} else {
					Toolkit.getDefaultToolkit().beep();
				}
			}
		}
	}

	protected final class AlignmentAction extends AbstractAction {
		private int a;

		public AlignmentAction(String nm, int a) {
			super(nm);
			this.a = a;
			switch(a) {
				case StyleConstants.ALIGN_RIGHT:
					this.putValue(Action.SMALL_ICON, new ImageIcon(Loader.load("align_right16.gif")));
					break;
				case StyleConstants.ALIGN_LEFT:
					this.putValue(Action.SMALL_ICON, new ImageIcon(Loader.load("align_left16.gif")));
					break;
				case StyleConstants.ALIGN_CENTER:
					this.putValue(Action.SMALL_ICON, new ImageIcon(Loader.load("align_center16.gif")));
					break;
				case StyleConstants.ALIGN_JUSTIFIED:
					this.putValue(Action.SMALL_ICON, new ImageIcon(Loader.load("align_justify16.gif")));
					break;
			}
		}

		public void actionPerformed(ActionEvent e) {
			//JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
			Editor editor = EditorActionManager.getActiveEditor();
			if (editor != null) {
				int a = this.a;
				if ((e != null) && (e.getSource() == editor)) {
					String s = e.getActionCommand();
					try {
						a = Integer.parseInt(s, 10);
					} catch (NumberFormatException nfe) {
					}
				}
				MutableAttributeSet attr = editor.getSimpleAttributeSet();
				StyleConstants.setAlignment(attr, a);
				EditorActionManager.instance().setParagraphAttributes(editor.getTextComponent(), attr, false);
			}
		}
	}

	protected final class BoldAction extends AbstractAction {
		public BoldAction() {
			super(BOLD_ACTION_PREFIX);
			this.putValue(Action.SMALL_ICON, new ImageIcon(Loader.load("bold_action16.gif")));
		}

		public void actionPerformed(ActionEvent e) {
			//JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
			Editor editor = EditorActionManager.getActiveEditor();
			if (editor != null) {
				//StyledEditorKit kit = getStyledEditorKit(editor);
				MutableAttributeSet attr = editor.getInputAttributes(); // kit.getInputAttributes();
				boolean bold = (StyleConstants.isBold(attr)) ? false : true;
				StyleConstants.setBold(attr, bold);
				EditorActionManager.instance().setCharacterAttributes(editor.getTextComponent(), attr, false);
			}
		}
	}

	protected final class ItalicAction extends AbstractAction {
		public ItalicAction() {
			super(ITALIC_ACTION_PREFIX);
			this.putValue(Action.SMALL_ICON, new ImageIcon(Loader.load("italic_action16.gif")));
		}

		public void actionPerformed(ActionEvent e) {
			//JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
			Editor editor = EditorActionManager.getActiveEditor();
			if (editor != null) {
				//StyledEditorKit kit = getStyledEditorKit(editor);
				MutableAttributeSet attr = editor.getInputAttributes(); // kit.getInputAttributes();
				boolean italic = (StyleConstants.isItalic(attr)) ? false : true;
				StyleConstants.setItalic(attr, italic);
				EditorActionManager.instance().setCharacterAttributes(editor.getTextComponent(), attr, false);
			}
		}
	}

	protected final class UnderlineAction extends AbstractAction {
		public UnderlineAction() {
			super(UNDERLINE_ACTION_PREFIX);
			this.putValue(Action.SMALL_ICON, new ImageIcon(Loader.load("underline_action16.gif")));
		}

		public void actionPerformed(ActionEvent e) {
			//JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
			Editor editor = EditorActionManager.getActiveEditor();
			if (editor != null) {
				//StyledEditorKit kit = getStyledEditorKit(editor);
				MutableAttributeSet attr = editor.getInputAttributes(); // kit.getInputAttributes();
				boolean underline = (StyleConstants.isUnderline(attr)) ? false : true;
				StyleConstants.setUnderline(attr, underline);
				EditorActionManager.instance().setCharacterAttributes(editor.getTextComponent(), attr, false);
			}
		}
	}

	protected final class StrikeThroughAction extends AbstractAction {
		public StrikeThroughAction() {
			super(STRIKETHROUGH_ACTION_PREFIX);
			this.putValue(Action.SMALL_ICON, new ImageIcon(Loader.load("strikethrough_action16.gif")));
		}

		public void actionPerformed(ActionEvent e) {
			//JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
			Editor editor = EditorActionManager.getActiveEditor();
			if (editor != null) {
				//StyledEditorKit kit = getStyledEditorKit(editor);
				MutableAttributeSet attr = EditorActionManager.getActiveEditor().getInputAttributes(); // kit.getInputAttributes();
				boolean bold = (StyleConstants.isStrikeThrough(attr)) ? false : true;
				StyleConstants.setStrikeThrough(attr, bold);
				EditorActionManager.instance().setCharacterAttributes(editor.getTextComponent(), attr, false);
			}
		}
	}

	protected final class CutAction extends AbstractAction {
		public CutAction() {
			super(CUT_ACTION_PREFIX);
			this.putValue(Action.SMALL_ICON, new ImageIcon(Loader.load("cut_action16.gif")));
		}

		public void actionPerformed(ActionEvent e) {
			//JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
			Editor target = EditorActionManager.getActiveEditor();
			if (target != null) {
				target.getTextComponent().cut();
			}
		}
	}

	protected final class CopyAction extends AbstractAction {
		public CopyAction() {
			super(COPY_ACTION_PREFIX);
			this.putValue(Action.SMALL_ICON, new ImageIcon(Loader.load("copy_action16.gif")));
		}

		public void actionPerformed(ActionEvent e) {
			//JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
			Editor target = EditorActionManager.getActiveEditor();
			if (target != null) {
				target.getTextComponent().copy();
			}
		}
	}

	protected final class PasteAction extends AbstractAction {
		public PasteAction() {
			super(PASTE_ACTION_PREFIX);
			this.putValue(Action.SMALL_ICON, new ImageIcon(Loader.load("paste_action16.gif")));
		}
		public void actionPerformed(ActionEvent e) {
			//JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
			Editor target = EditorActionManager.getActiveEditor();
			if (target != null) {
				target.getTextComponent().paste();
			}
		}
	}

	protected final class BeepAction extends AbstractAction {
		public BeepAction() {
			super(BEEP_ACTION_PREFIX);
		}
		public void actionPerformed(ActionEvent e) {
			Toolkit.getDefaultToolkit().beep();
		}
	}

	protected final class SelectAllAction extends AbstractAction {
		protected SelectAllAction() {
			super(SELECTALL_ACTION_PREFIX);
		}

		public void actionPerformed(ActionEvent e) {
			//JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
			Editor target = EditorActionManager.getActiveEditor();
			if (target != null) {
				Document doc = target.getTextComponent().getDocument();
				target.getTextComponent().setCaretPosition(0);
				target.getTextComponent().moveCaretPosition(doc.getLength());
			}
		}
	}

	protected final class SelectNoneAction extends AbstractAction {
		protected SelectNoneAction() {
			super(SELECTNONE_ACTION_PREFIX);
		}

		public void actionPerformed(ActionEvent e) {
			//JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
			Editor target = EditorActionManager.getActiveEditor();
			if (target != null) {
				if (target.getTextComponent().getSelectionStart() != target.getTextComponent().getSelectionEnd()) {
					int dot = target.getTextComponent().getSelectionStart();
					target.getTextComponent().setSelectionStart(dot);
					target.getTextComponent().setSelectionEnd(dot);
					target.getTextComponent().setCaretPosition(dot);
				}

			}
		}
	}

	protected final class UndoAction extends AbstractAction {
		protected UndoAction() {
			super(UNDO_ACTION_PREFIX);
			this.putValue(Action.SMALL_ICON, new ImageIcon(Loader.load("undo_action16.gif")));
		}

		public void actionPerformed(ActionEvent e) {
			//JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
			Editor target = EditorActionManager.getActiveEditor();
			if (target != null) {
				if(target.getUndoManager().canUndo()){
					target.getUndoManager().undo();
				}
			}
		}
	}

	protected final class RedoAction extends AbstractAction {
		protected RedoAction() {
			super(REDO_ACTION_PREFIX);
			this.putValue(Action.SMALL_ICON, new ImageIcon(Loader.load("redo_action16.gif")));
		}

		public void actionPerformed(ActionEvent e) {
			//JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
			Editor target = EditorActionManager.getActiveEditor();
			if (target != null) {
				if(target.getUndoManager().canRedo()){
					target.getUndoManager().redo();
				}
			}
		}
	}

	protected final class SearchAction extends AbstractAction {
		//BufferedImage
		private JFrame _PARENT;

		public SearchAction(String nm, JFrame component) {
			super(nm);
			this._PARENT = component;
		}

		public void actionPerformed(ActionEvent e) {
			//Code.message("Search Activated...");
		}
	}

	protected final class ReplaceAction extends AbstractAction {
		//BufferedImage
		private JFrame _PARENT;

		public ReplaceAction(String nm, JFrame component) {
			super(nm);
			this._PARENT = component;
		}

		public void actionPerformed(ActionEvent e) {
			//Code.message("Replace Activated...");
		}
	}

	protected final class NewAction extends AbstractAction {
		private FileActionListener _LISTENER = null;
		public NewAction(String name, JFrame component, FileActionListener agent) {
			super(name);
			_LISTENER = agent;
			this.putValue(Action.SMALL_ICON, new ImageIcon(Loader.load("new_document16.gif")));
		}

		public void actionPerformed(ActionEvent e) {
			_LISTENER.editorNew();
		}
	}

	protected final class OpenAction extends AbstractAction {
		private FileActionListener _LISTENER = null;
		public OpenAction(String name, JFrame component, FileActionListener agent) {
			super(name);
			_LISTENER = agent;
			this.putValue(Action.SMALL_ICON, new ImageIcon(Loader.load("open_document16.gif")));
		}

		public void actionPerformed(ActionEvent e) {
			_LISTENER.editorOpen();
		}
	}

	protected final class OpenAsAction extends AbstractAction {
		private FileActionListener _LISTENER = null;
		public OpenAsAction(String name, JFrame component, FileActionListener agent) {
			super(name);
			_LISTENER = agent;
		}

		public void actionPerformed(ActionEvent e) {
			// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
			_LISTENER.editorOpenAs();
		}
	}

	protected final class RevertAction extends AbstractAction {
		private FileActionListener _LISTENER = null;
		public RevertAction(String name, JFrame component, FileActionListener agent) {
			super(name);
			_LISTENER = agent;
		}

		public void actionPerformed(ActionEvent e) {
			if(EditorActionManager.getActiveEditor() != null) {
				_LISTENER.editorRevert(EditorActionManager.getActiveEditor());
			}
		}
	}

	protected final class SaveAction extends AbstractAction {
		private FileActionListener _LISTENER = null;
		public SaveAction(String name, JFrame component, FileActionListener agent) {
			super(name);
			_LISTENER = agent;
			this.putValue(Action.SMALL_ICON, new ImageIcon(Loader.load("save_document16.gif")));
		}

		public void actionPerformed(ActionEvent e) {
			if(EditorActionManager.getActiveEditor() != null) {
				_LISTENER.editorSave(EditorActionManager.getActiveEditor());
			}
		}
	}

	protected final class SaveAsAction extends AbstractAction {
		private FileActionListener _LISTENER = null;
		public SaveAsAction(String name, JFrame component, FileActionListener agent) {
			super(name);
			_LISTENER = agent;
		}

		public void actionPerformed(ActionEvent e) {
			if(EditorActionManager.getActiveEditor() != null) {
				_LISTENER.editorSaveAs(EditorActionManager.getActiveEditor());
			}
		}
	}

	protected final class SaveCopyAction extends AbstractAction {
		private FileActionListener _LISTENER = null;
		public SaveCopyAction(String name, JFrame component, FileActionListener agent) {
			super(name);
			_LISTENER = agent;
		}

		public void actionPerformed(ActionEvent e) {
			if(EditorActionManager.getActiveEditor() != null) {
				_LISTENER.editorSaveCopy(EditorActionManager.getActiveEditor());
			}
		}
	}
	
	private EditorActionManager() {
		super();
		/*
		if(_INSTANCE != null) {
			_INSTANCE = EditorActionManager.instance();
	}
		*/
	}
	/**
	* Add an editor to the action manager.
	* @param editor com.jmonkey.office.common.Editor
	*/
	public static void activate(Editor editor) {
		// First deactivate the current
		// editor if there is one.
		if(EditorActionManager.instance()._EDITOR != null) {
			EditorActionManager.deactivate(EditorActionManager.instance()._EDITOR);
		}

		EditorActionManager.instance()._EDITOR = editor;
		// After the editor is saved,
		// so that any calles to
		// ActionManager,getActiveEditor()
		// will actually return something.
		EditorActionManager.getActiveEditor().hasBeenActivated(EditorActionManager.getActiveEditor());
		// allow the component time to do setup before allowing Caret events.
		EditorActionManager.getActiveEditor().getTextComponent().addCaretListener(EditorActionManager.instance()._ATTRIBUTE_TRACKER);
	}
	private Image create16x16ColourRec(Component c, Color colour) {
		/*
		byte[] imageData = {
			(byte)71, 		(byte)73, 		(byte)70, 		(byte)56, 		(byte)57, 
			(byte)97, 		(byte)16, 		(byte)0, 			(byte)16, 		(byte)0, 
			(byte)128, 		(byte)255, 		(byte)0, 			(byte)255, 		(byte)255, 
			(byte)255, 		(byte)0, 			(byte)0, 			(byte)0, 			(byte)44, 
			(byte)0, 			(byte)0, 			(byte)0, 			(byte)0, 			(byte)16, 
			(byte)0, 			(byte)16, 		(byte)0, 			(byte)0, 			(byte)2, 
			(byte)14, 		(byte)132, 		(byte)143, 		(byte)169, 		(byte)203, 
			(byte)237, 		(byte)15, 		(byte)163, 		(byte)156, 		(byte)180, 
			(byte)218, 		(byte)139, 		(byte)179, 		(byte)62, 		(byte)5, 
			(byte)0, 			(byte)59
	};
		Image img = java.awt.Toolkit.getDefaultToolkit().createImage(imageData);
		*/
		//Code.debug("Component=" + c);
		//Code.debug("Color=" + colour);
		Image img = c.createImage(16, 16);
		//Code.debug("Image=" + img);
		Graphics g = img.getGraphics();
		//Code.debug("Graphics=" + g);
		g.setColor(colour);
		g.fillRect(0, 0, 16, 16);
		//Code.debug("Coloured Image=" + img);
		return img;
	}
	/**
	* Create the default actions of the type: FONT_COLOUR_ACTION
	* <P>
	* @param colour java.awt.Color the colour to assign the action.
	* @return javax.swing.Action[]
	*/
	public final Action[] createDefaultColourActions() {
		Action[] a = new Action[14];
		a[0] = this.getColourAction("White", Color.white);
		a[1] = this.getColourAction("Black", Color.black);
		a[2] = this.getColourAction("Red", Color.red);
		a[3] = this.getColourAction("Green", Color.green);
		a[4] = this.getColourAction("Blue", Color.blue);
		a[5] = this.getColourAction("Orange", Color.orange);
		a[6] = this.getColourAction("Dark Gray", Color.darkGray);
		a[7] = this.getColourAction("Gray", Color.gray);
		a[8] = this.getColourAction("Light Gray", Color.lightGray);
		a[9] = this.getColourAction("Cyan", Color.cyan);
		a[10] = this.getColourAction("Magenta", Color.magenta);
		a[11] = this.getColourAction("Pink", Color.pink);
		a[12] = this.getColourAction("Yellow", Color.yellow);
		return a;
	}
	/**
	* Create the default font family actions.
	* @return javax.swing.Action[]
	*/
	public final Action[] createDefaultFontFaceActions() {
		// This is returning all the
		// system fonts at the moment
		// but we should change it to
		// only include the fonts that
		// all  VM have. -- Brill 04/07/1999
		String[] families = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		Map fontFamilyRange = Collections.synchronizedMap(new HashMap());
		Action a = null;
		for(int i = 0; i < families.length; i++) {
			if(families[i].indexOf(".") == -1) {
				// we have to test to a "." so
				// we don't get duplicates, there
				// appears to be a bug in the VM.
				a = this.getFontFaceAction(families[i]);
				fontFamilyRange.put(families[i], a);
			}
		}

		// This is a bad way to
		// do it, but I'm to tired
		// to fix it now.
		Action[] output = new Action[fontFamilyRange.size()];
		for(int i = 0; i < output.length; i++) {
			if(families[i].indexOf(".") == -1) {
				output[i] = (Action)fontFamilyRange.get(families[i]);
			}
		}
		return output;
	}
	/**
	* Creates a range of FontSize actions from the 
	* begin number to the end number, with the 
	* specified granularity.
	* <P>
	* i.e. A granularity of two, will increase the 
	* range by 2. So, the number of actions produced 
	* will be ((end - begin) / 2) or begin, begin + 2, 
	* begin + 4, begin + 6 [up to] end.
	* @param int begin start of the range.
	* @param int end end of the range.
	* @param int granularity range granularity (divide by).
	*/
	public final Action[] createFontSizeActionRange(int begin, int end, int granularity) {
		if(begin > end) {
			throw new IllegalArgumentException("Beginning of Font Size range must be less than the end of the range.");
		}
		if(((end - begin) <= granularity)) {
			throw new IllegalArgumentException("The granularity is equal to or grater than the number of elements between the begin and end. No Size elements can be generated.");
		}
		if(((end - begin) % granularity) > 0) {
			throw new IllegalArgumentException("The number of elements between the begin and end must be divisible by the granularity.");
		}


		Action[] a = new Action[((end - begin) / granularity)];

		for(int i = begin; i < a.length; i += granularity) {
			a[i] = this.getFontSizeAction(i);
		}
		return a;
	}
	/**
	 * Copies the key/values in <code>element</code>s AttributeSet into
	 * <code>set</code>. This does not copy component, icon, or element
	 * names attributes. Subclasses may wish to refine what is and what
	 * isn't copied here. But be sure to first remove all the attributes that
	 * are in <code>set</code>.<p>
	 * This is called anytime the caret moves over a different location.
	 *
	 */
	protected void createInputAttributes(Element element, MutableAttributeSet set) {
		set.removeAttributes(set);
		set.addAttributes(element.getAttributes());
		set.removeAttribute(StyleConstants.ComponentAttribute);
		set.removeAttribute(StyleConstants.IconAttribute);
		set.removeAttribute(AbstractDocument.ElementNameAttribute);
		set.removeAttribute(StyleConstants.ComposedTextAttribute);
	}
	/**
	* Remove an editor to the action manager.
	* @param editor com.jmonkey.office.common.Editor
	*/
	public static void deactivate(Editor editor) {
		if(EditorActionManager.instance()._EDITOR != null) {
			// disable Caret events.
			EditorActionManager.getActiveEditor().getTextComponent().removeCaretListener(EditorActionManager.instance()._ATTRIBUTE_TRACKER);
			// Before the editor is removed,
			// so that any calles to
			// EditorActionManager,getActiveEditor()
			// will actually return something.
			EditorActionManager.getActiveEditor().hasBeenDeactivated(EditorActionManager.getActiveEditor());
			EditorActionManager.instance()._EDITOR = null;
		}
	}
	/**
	* Sets the enabled attribute of all actions 
	* matching or containing the specified pattern.
	* To disable a specific action, use a specific 
	* name. if the name is found in the list, it
	* is the only one disabled. Otherwise, all 
	* actions that contain the pattern will be
	* enabled/disabled.
	*
	* @param pattern java.lang.String
	* @param enabled boolean
	*/
	public static final void enableAction(String pattern, boolean enabled) {
		// Code.debug("enableAction: " + pattern + ", " + enabled);
		if(_ACTIONS.containsKey(pattern)) {
			((Action)_ACTIONS.get(pattern)).setEnabled(enabled);
		}
	}
	/**
	* Sets the enabled attribute of all format actions .
	*
	* @param enabled boolean
	*/
	public static final void enableColourActions(boolean enabled) {
		// Code.debug("enableColourActions: " + enabled);
		Iterator it = _ACTIONS.entrySet().iterator();
		while(it.hasNext()) {
			Object o = it.next();
			if((o instanceof ForegroundAction) || (o instanceof ColourChooserAction)) {
				((Action)o).setEnabled(enabled);
			}
		}
	}
	/**
	* Sets the enabled attribute of all Document actions .
	*
	* @param enabled boolean
	*/
	public static final void enableDocumentActions(boolean enabled) {
		//Code.debug("enableDocumentActions: " + enabled);
		EditorActionManager.enableAction(CUT_ACTION_PREFIX, enabled);
		EditorActionManager.enableAction(COPY_ACTION_PREFIX, enabled);
		EditorActionManager.enableAction(PASTE_ACTION_PREFIX, enabled);
		EditorActionManager.enableAction(SELECTALL_ACTION_PREFIX, enabled);
		EditorActionManager.enableAction(SELECTNONE_ACTION_PREFIX, enabled);
		EditorActionManager.enableAction(UNDO_ACTION_PREFIX, enabled);
		EditorActionManager.enableAction(REDO_ACTION_PREFIX, enabled);
		EditorActionManager.enableAction(SEARCH_ACTION_PREFIX, enabled);
		EditorActionManager.enableAction(REPLACE_ACTION_PREFIX, enabled);
	}
	/**
	* Sets the enabled attribute of all format actions .
	*
	* @param enabled boolean
	*/
	public static final void enableFontActions(boolean enabled) {
		// Code.debug("enableFontActions: " + enabled);
		Iterator it = _ACTIONS.entrySet().iterator();
		while(it.hasNext()) {
			Object o = it.next();
			if((o instanceof FontSizeAction) || (o instanceof FontFamilyAction) || (o instanceof FontChooserAction)) {
				((Action)o).setEnabled(enabled);
			}
		}
	}
	/**
	* Sets the enabled attribute of all format actions .
	*
	* @param enabled boolean
	*/
	public static final void enableFormatActions(boolean enabled) {
		//Code.debug("enableFormatActions: " + enabled);
		EditorActionManager.enableAction(ALIGN_LEFT_ACTION_PREFIX, enabled);
		EditorActionManager.enableAction(ALIGN_RIGHT_ACTION_PREFIX, enabled);
		EditorActionManager.enableAction(ALIGN_CENTER_ACTION_PREFIX, enabled);
		EditorActionManager.enableAction(ALIGN_JUSTIFIED_ACTION_PREFIX, enabled);
		EditorActionManager.enableAction(BOLD_ACTION_PREFIX, enabled);
		EditorActionManager.enableAction(ITALIC_ACTION_PREFIX, enabled);
		EditorActionManager.enableAction(UNDERLINE_ACTION_PREFIX, enabled);
		EditorActionManager.enableAction(STRIKETHROUGH_ACTION_PREFIX, enabled);
		EditorActionManager.enableAction(COLOUR_CHOOSER_ACTION_PREFIX, enabled);
		EditorActionManager.enableAction(FONT_CHOOSER_ACTION_PREFIX, enabled);
		EditorActionManager.enableColourActions(enabled);
		EditorActionManager.enableFontActions(enabled);
	}
	/**
	* Sets the enabled attribute of all Generic actions .
	*
	* @param enabled boolean
	*/
	public static final void enableGenericActions(boolean enabled) {
		//Code.debug("enableGenericActions: " + enabled);
		EditorActionManager.enableAction(BEEP_ACTION_PREFIX, enabled);
	}
	public static final Editor getActiveEditor() {
		return EditorActionManager.instance()._EDITOR;
	}
	public final Action getAlignCenterAction() {
		if(!_ACTIONS.containsKey(ALIGN_CENTER_ACTION_PREFIX)) {
			_ACTIONS.put(ALIGN_CENTER_ACTION_PREFIX, new AlignmentAction(ALIGN_CENTER_ACTION_PREFIX, StyleConstants.ALIGN_CENTER));
		}
		return (Action)_ACTIONS.get(ALIGN_CENTER_ACTION_PREFIX);
	}
	public final Action getAlignJustifyAction() {
		if(!_ACTIONS.containsKey(ALIGN_JUSTIFIED_ACTION_PREFIX)) {
			_ACTIONS.put(ALIGN_JUSTIFIED_ACTION_PREFIX, new AlignmentAction(ALIGN_JUSTIFIED_ACTION_PREFIX, StyleConstants.ALIGN_JUSTIFIED));
		}
		return (Action)_ACTIONS.get(ALIGN_JUSTIFIED_ACTION_PREFIX);
	}
	public final Action getAlignLeftAction() {
		if(!_ACTIONS.containsKey(ALIGN_LEFT_ACTION_PREFIX)) {
			_ACTIONS.put(ALIGN_LEFT_ACTION_PREFIX, new AlignmentAction(ALIGN_LEFT_ACTION_PREFIX, StyleConstants.ALIGN_LEFT));
		}
		return (Action)_ACTIONS.get(ALIGN_LEFT_ACTION_PREFIX);
	}
	public final Action getAlignRightAction() {
		if(!_ACTIONS.containsKey(ALIGN_RIGHT_ACTION_PREFIX)) {
			_ACTIONS.put(ALIGN_RIGHT_ACTION_PREFIX, new AlignmentAction(ALIGN_RIGHT_ACTION_PREFIX, StyleConstants.ALIGN_RIGHT));
		}
		return (Action)_ACTIONS.get(ALIGN_RIGHT_ACTION_PREFIX);
	}
	public final Action getBeepAction() {
		if(!_ACTIONS.containsKey(BEEP_ACTION_PREFIX)) {
			_ACTIONS.put(BEEP_ACTION_PREFIX, new BeepAction());
		}
		return (Action)_ACTIONS.get(BEEP_ACTION_PREFIX);
	}
	public final Action getBoldAction() {
		if(!_ACTIONS.containsKey(BOLD_ACTION_PREFIX)) {
			_ACTIONS.put(BOLD_ACTION_PREFIX, new BoldAction());
		}
		return (Action)_ACTIONS.get(BOLD_ACTION_PREFIX);
	}
	public final Action getColourAction(String name, Color colour) {
		//String key = "#" + Integer.toHexString(colour.getRGB()).toUpperCase();
		if(!_ACTIONS.containsKey(name)) {
			_ACTIONS.put(name, new ForegroundAction(name, colour));
		}
		return (Action)_ACTIONS.get(name);
	}
	public final Color getColourAtCaret(){
		Editor editor = EditorActionManager.getActiveEditor();
		if(editor != null){
			return StyleConstants.getForeground(editor.getInputAttributes());
		}else{
			return null;
		}
	}
	public final Action getColourChooserAction(JFrame component) {
		if(!_ACTIONS.containsKey(COLOUR_CHOOSER_ACTION_PREFIX)) {
			_ACTIONS.put(COLOUR_CHOOSER_ACTION_PREFIX, new ColourChooserAction(COLOUR_CHOOSER_ACTION_PREFIX, component));
		}
		return (Action)_ACTIONS.get(COLOUR_CHOOSER_ACTION_PREFIX);
	}
	public final Action getCopyAction() {
		if(!_ACTIONS.containsKey(COPY_ACTION_PREFIX)) {
			_ACTIONS.put(COPY_ACTION_PREFIX, new CopyAction());
		}
		return (Action)_ACTIONS.get(COPY_ACTION_PREFIX);
	}
	public final Action getCutAction() {
		if(!_ACTIONS.containsKey(CUT_ACTION_PREFIX)) {
			_ACTIONS.put(CUT_ACTION_PREFIX, new CutAction());
		}
		return (Action)_ACTIONS.get(CUT_ACTION_PREFIX);
	}
	// =============== BEGIN ADD ACTION METHODS =========================================
	public final Action getFontChooserAction(JFrame component) {
		if(!_ACTIONS.containsKey(FONT_CHOOSER_ACTION_PREFIX)) {
			_ACTIONS.put(FONT_CHOOSER_ACTION_PREFIX, new FontChooserAction(FONT_CHOOSER_ACTION_PREFIX, component));
		}
		return (Action)_ACTIONS.get(FONT_CHOOSER_ACTION_PREFIX);
	}
	/**
	* @param font java.awt.Font the font to use as a template.
	*/
	public final Action getFontFaceAction(Font font) {
		return this.getFontFaceAction(font.getFontName());
	}
	public final Action getFontFaceAction(String name) {
		if(!_ACTIONS.containsKey(name)) {
			_ACTIONS.put(name, new FontFamilyAction(name, name));
		}
		return (Action)_ACTIONS.get(name);
	}
	public final Action getFontSizeAction(int size) {
		String key = "" + size;
		if(!_ACTIONS.containsKey(key)) {
			_ACTIONS.put(key, new FontSizeAction(key, size));
		}
		return (Action)_ACTIONS.get(key);
	}
	public final Action getItalicAction() {
		if(!_ACTIONS.containsKey(ITALIC_ACTION_PREFIX)) {
			_ACTIONS.put(ITALIC_ACTION_PREFIX, new ItalicAction());
		}
		return (Action)_ACTIONS.get(ITALIC_ACTION_PREFIX);
	}
	// ===== Editor File Actions ============================

	public final Action getNewAction(JFrame component, FileActionListener agent) {
		if(!_ACTIONS.containsKey(FILE_NEW_ACTION_PREFIX)) {
			_ACTIONS.put(FILE_NEW_ACTION_PREFIX, new NewAction(FILE_NEW_ACTION_PREFIX, component, agent));
		}
		return (Action)_ACTIONS.get(FILE_NEW_ACTION_PREFIX);
	}
	public final Action getOpenAction(JFrame component, FileActionListener agent) {
		if(!_ACTIONS.containsKey(FILE_OPEN_ACTION_PREFIX)) {
			_ACTIONS.put(FILE_OPEN_ACTION_PREFIX, new OpenAction(FILE_OPEN_ACTION_PREFIX, component, agent));
		}
		return (Action)_ACTIONS.get(FILE_OPEN_ACTION_PREFIX);
	}
	public final Action getOpenAsAction(JFrame component, FileActionListener agent) {
		if(!_ACTIONS.containsKey(FILE_OPENAS_ACTION_PREFIX)) {
			_ACTIONS.put(FILE_OPENAS_ACTION_PREFIX, new OpenAsAction(FILE_OPENAS_ACTION_PREFIX, component, agent));
		}
		return (Action)_ACTIONS.get(FILE_OPENAS_ACTION_PREFIX);
	}
	public final Action getPasteAction() {
		if(!_ACTIONS.containsKey(PASTE_ACTION_PREFIX)) {
			_ACTIONS.put(PASTE_ACTION_PREFIX, new PasteAction());
		}
		return (Action)_ACTIONS.get(PASTE_ACTION_PREFIX);
	}
	public final Action getRedoAction() {
		if(!_ACTIONS.containsKey(REDO_ACTION_PREFIX)) {
			_ACTIONS.put(REDO_ACTION_PREFIX, new RedoAction());
		}
		return (Action)_ACTIONS.get(REDO_ACTION_PREFIX);
	}
	public final Action getReplaceAction(JFrame component) {
		//Code.debug("getReplaceAction");
		if(!_ACTIONS.containsKey(REPLACE_ACTION_PREFIX)) {
			_ACTIONS.put(REPLACE_ACTION_PREFIX, new FontChooserAction(REPLACE_ACTION_PREFIX, component));
		}
		return (Action)_ACTIONS.get(REPLACE_ACTION_PREFIX);
	}
	public final Action getRevertAction(JFrame component, FileActionListener agent) {
		if(!_ACTIONS.containsKey(FILE_REVERT_ACTION_PREFIX)) {
			_ACTIONS.put(FILE_REVERT_ACTION_PREFIX, new RevertAction(FILE_REVERT_ACTION_PREFIX, component, agent));
		}
		return (Action)_ACTIONS.get(FILE_REVERT_ACTION_PREFIX);
	}
	public final Action getSaveAction(JFrame component, FileActionListener agent) {
		if(!_ACTIONS.containsKey(FILE_SAVE_ACTION_PREFIX)) {
			_ACTIONS.put(FILE_SAVE_ACTION_PREFIX, new SaveAction(FILE_SAVE_ACTION_PREFIX, component, agent));
		}
		return (Action)_ACTIONS.get(FILE_SAVE_ACTION_PREFIX);
	}
	public final Action getSaveAsAction(JFrame component, FileActionListener agent) {
		if(!_ACTIONS.containsKey(FILE_SAVEAS_ACTION_PREFIX)) {
			_ACTIONS.put(FILE_SAVEAS_ACTION_PREFIX, new SaveAsAction(FILE_SAVEAS_ACTION_PREFIX, component, agent));
		}
		return (Action)_ACTIONS.get(FILE_SAVEAS_ACTION_PREFIX);
	}
	public final Action getSaveCopyAction(JFrame component, FileActionListener agent) {
		if(!_ACTIONS.containsKey(FILE_SAVECOPY_ACTION_PREFIX)) {
			_ACTIONS.put(FILE_SAVECOPY_ACTION_PREFIX, new SaveCopyAction(FILE_SAVECOPY_ACTION_PREFIX, component, agent));
		}
		return (Action)_ACTIONS.get(FILE_SAVECOPY_ACTION_PREFIX);
	}
	// ===== Edit File Actions ============================
	public final Action getSearchAction(JFrame component) {
		//Code.debug("getSearchAction");
		if(!_ACTIONS.containsKey(SEARCH_ACTION_PREFIX)) {
			_ACTIONS.put(SEARCH_ACTION_PREFIX, new FontChooserAction(SEARCH_ACTION_PREFIX, component));
		}
		return (Action)_ACTIONS.get(SEARCH_ACTION_PREFIX);
	}
	public final Action getSelectAllAction() {
		if(!_ACTIONS.containsKey(SELECTALL_ACTION_PREFIX)) {
			_ACTIONS.put(SELECTALL_ACTION_PREFIX, new SelectAllAction());
		}
		return (Action)_ACTIONS.get(SELECTALL_ACTION_PREFIX);
	}
	public final Action getSelectNoneAction() {
		if(!_ACTIONS.containsKey(SELECTNONE_ACTION_PREFIX)) {
			_ACTIONS.put(SELECTNONE_ACTION_PREFIX, new SelectNoneAction());
		}
		return (Action)_ACTIONS.get(SELECTNONE_ACTION_PREFIX);
	}
	public final Action getStrikeThroughAction() {
		if(!_ACTIONS.containsKey(STRIKETHROUGH_ACTION_PREFIX)) {
			_ACTIONS.put(STRIKETHROUGH_ACTION_PREFIX, new StrikeThroughAction());
		}
		return (Action)_ACTIONS.get(STRIKETHROUGH_ACTION_PREFIX);
	}
	public final Action getUnderlineAction() {
		if(!_ACTIONS.containsKey(UNDERLINE_ACTION_PREFIX)) {
			_ACTIONS.put(UNDERLINE_ACTION_PREFIX, new UnderlineAction());
		}
		return (Action)_ACTIONS.get(UNDERLINE_ACTION_PREFIX);
	}
	public final Action getUndoAction() {
		if(!_ACTIONS.containsKey(UNDO_ACTION_PREFIX)) {
			_ACTIONS.put(UNDO_ACTION_PREFIX, new UndoAction());
		}
		return (Action)_ACTIONS.get(UNDO_ACTION_PREFIX);
	}
	public static final EditorActionManager instance() {
		if(_INSTANCE == null) {
			_INSTANCE = new EditorActionManager();
		}
		return _INSTANCE;
	}
	/**
	* Returns true if there is an active 
	* editor in the action manager. false
	* otherwise.
	* @return boolean
	*/
	public static final boolean isActiveEditor() {
		return (EditorActionManager.instance()._EDITOR != null);
	}
	/**
	* Applies the given attributes to character 
	* content.  If there is a selection, the attributes
	* are applied to the selection range.  If there
	* is no selection, the attributes are applied to
	* the input attribute set which defines the attributes
	* for any new text that gets inserted.
	*
	* @param editor the editor
	* @param attr the attributes
	* @param replace   if true, then replace the existing attributes first
	*/
	protected final void setCharacterAttributes(JEditorPane editor, AttributeSet attr, boolean replace) {
		int p0 = editor.getSelectionStart();
		int p1 = editor.getSelectionEnd();
		if (p0 != p1) {
			// StyledDocument doc = getStyledDocument(editor);
			if(EditorActionManager.getActiveEditor().getTextComponent().getDocument() instanceof StyledDocument) {
				((StyledDocument)EditorActionManager.getActiveEditor().getTextComponent().getDocument()).setCharacterAttributes(p0, p1 - p0, attr, replace);
			}
		} else {
			//StyledEditorKit k = getStyledEditorKit(editor);
			MutableAttributeSet inputAttributes = EditorActionManager.getActiveEditor().getInputAttributes();
			if (replace) {
				inputAttributes.removeAttributes(inputAttributes);
			}
			inputAttributes.addAttributes(attr);
		}
	}
	/**
		 * Applies the given attributes to paragraphs.  If
		 * there is a selection, the attributes are applied
		 * to the paragraphs that intersect the selection.
		 * if there is no selection, the attributes are applied
		 * to the paragraph at the current caret position.
		 *
		        * @param editor the editor
		 * @param attr the attributes
		 * @param replace   if true, replace the existing attributes first
		 */
	protected final void setParagraphAttributes(JEditorPane editor, AttributeSet attr, boolean replace) {
		int p0 = editor.getSelectionStart();
		int p1 = editor.getSelectionEnd();
		//StyledDocument doc = getStyledDocument(editor);
		//doc.setParagraphAttributes(p0, p1 - p0, attr, replace);
		if(EditorActionManager.getActiveEditor().getTextComponent().getDocument() instanceof StyledDocument) {
			((StyledDocument)EditorActionManager.getActiveEditor().getTextComponent().getDocument()).setParagraphAttributes(p0, p1 - p0, attr, replace);
		}
	}
	/**
	* Returns a running thread wrapping the runnable object.
	* @param r java.lang.Runnable
	* @return java.lang.Thread
	*/
	public static final Runnable threads(Runnable r) {
		Thread t = new Thread(r);
		t.start();
		return t;

		/*
		// The ThreadPool is broken, so we're taking a
		// simple aproach instead... this should be finxed
		// for speed, but will do for the moment.
		if(EditorActionManager.instance()._THREADPOOL == null) {
			EditorActionManager.instance()._THREADPOOL = new ThreadPool();
			EditorActionManager.instance()._THREADPOOL.loadBuffer();
	}
		return EditorActionManager.instance()._THREADPOOL;
		*/
	}
}
