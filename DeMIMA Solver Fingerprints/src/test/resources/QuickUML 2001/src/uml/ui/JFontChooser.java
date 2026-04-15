/**
 *
    QuickUML; A simple UML tool that demonstrates one use of the 
    Java Diagram Package 

    Copyright (C) 2001  Eric Crahen <crahen@cse.buffalo.edu>

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

 */

package uml.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @class JFontChooser
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * This component implements a font chooser. The layout is based on Jext's FontChooser.
 * TODO: Expose listmodels for family, style & size 
 */
public class JFontChooser extends JComponent {

  protected static final String[] STYLES = { "plain", "bold", "italic", "boldItalic" };
  protected static final String[] SIZES = { "9", "10", "12", "14", "16", "18", "24" };
  protected static final String[] FONTS = getAvailableFontFamilyNames();


  private JList familyList, sizesList, stylesList;
  private JTextField familyField, sizesField, stylesField;
  private SampleLabel previewLabel;

  /**
   * Create a new FontChooser on the given component with the given Font
   *
   * @param Font
   */
  public JFontChooser(Font font) {
    this(font, null);
  }


  /**
   * Create a new FontChooser on the given component with the given Font
   *
   * @param Font
   */
  public JFontChooser(Font font, String sample) {

    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints gc = new GridBagConstraints();

    setLayout(layout);

    gc.anchor = GridBagConstraints.NORTHWEST;
    gc.gridwidth = gc.gridheight = 1;   
    gc.insets = new Insets(4,4,4,4);
    gc.weightx = 1.0;
    gc.weighty = 0.0;

    // Add the labels
    JLabel lbl = new JLabel("Family", JLabel.CENTER);
    gc.fill = GridBagConstraints.HORIZONTAL;
    layout.setConstraints(lbl, gc); 
    add(lbl);

    lbl = new JLabel("Size", JLabel.CENTER);
    layout.setConstraints(lbl, gc); 
    add(lbl);
    
    lbl = new JLabel("Style", JLabel.CENTER);
    gc.gridwidth = GridBagConstraints.REMAINDER;
    layout.setConstraints(lbl, gc); 
    add(lbl);
  
    // Add text fields
    familyField = new JTextField(10);
    familyField.setEnabled(false);

    gc.gridwidth = 1;
    gc.weighty = 1.0;
    layout.setConstraints(familyField, gc); 
    add(familyField);

    sizesField = new JTextField(10);
    sizesField.setEnabled(false);    

    layout.setConstraints(sizesField, gc); 
    add(sizesField);

    stylesField = new JTextField(10);
    stylesField.setEnabled(false);

    gc.gridwidth = GridBagConstraints.REMAINDER;
    layout.setConstraints(stylesField, gc); 
    add(stylesField);

    // Add lists
    familyList = new JList(FONTS);
    JScrollPane scrolPane = new JScrollPane(familyList);

    gc.ipadx = gc.ipady = 10;
    gc.gridwidth = 1;
    gc.gridheight = 4;
    gc.fill = GridBagConstraints.BOTH;
    layout.setConstraints(scrolPane, gc); 
    add(scrolPane);

    sizesList = new JList(SIZES);
    scrolPane = new JScrollPane(sizesList);
    layout.setConstraints(scrolPane, gc); 
    add(scrolPane);

    stylesList = new JList(STYLES);
    scrolPane = new JScrollPane(stylesList);
    gc.fill = GridBagConstraints.BOTH;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    layout.setConstraints(scrolPane, gc); 
    add(scrolPane);

    // Add preview panel
    previewLabel = new SampleLabel(sample);
    this.addPropertyChangeListener(previewLabel);

    gc.weighty = 0.0;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.gridheight = GridBagConstraints.REMAINDER;
    gc.fill = GridBagConstraints.BOTH;
    layout.setConstraints(previewLabel, gc); 
    add(previewLabel);
  
    
    // Add listeners to the list
    SelectionHandler handler = new SelectionHandler();
    familyList.addListSelectionListener(handler);
    sizesList.addListSelectionListener(handler);
    stylesList.addListSelectionListener(handler);
   
 
    if(font != null)
      setFont(font);

  }

  /**
   * Select the current font
   */
  public void setFont(Font font) {

    // Go by the default font on the label if a null font is selected
    if(font == null)
      font = previewLabel.getFont();

    super.setFont(font);   

    //  firePropertyChange
    if(font != null) {

      familyList.setSelectedValue(font.getName(), true);
      familyField.setText(String.valueOf(font.getName()));

      stylesList.setSelectedIndex(font.getStyle());
      stylesField.setText((String)stylesList.getSelectedValue());
   
      sizesField.setText(String.valueOf(font.getSize()));
      sizesList.setSelectedValue(String.valueOf(font.getSize()), true);

    }   

  }


  /**
   * Gets a list of all available font family names.
   */
  public static String[] getAvailableFontFamilyNames() {

    Vector v = new Vector();
    String names[] = 
      GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

    for(int i = 0; i < names.length; i++) {

      if(!names[i].equals(".bold") && !names[i].equals(".italic"))
        v.addElement(names[i]);

    }

    return (String[])v.toArray(new String[v.size()]);

  }

  /**
   * Get the last selected font
   */
  public Font getSelectedFont() {
    return super.getFont();
  }

  /**
   * Get the last selected font
   */
  public String getSelectedFamily() {
    return getFont().getFamily();
  }

  /**
   * Get the last selected font
   */
  public float getSelectedSize() {
    return getFont().getSize();
  }

  /**
   * Get the last selected font
   */
  public int getSelectedStyle() {
    return getFont().getStyle();
  }

  /**
   * Notify of the property change
   */
  protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {

    super.firePropertyChange(propertyName, oldValue, newValue);

  }

  /**
   * @class SelectionHandler
   *
   * Derive a new Font as a selection is made and fire the property change
   */
  protected class SelectionHandler implements ListSelectionListener {

    /**
     * Listen for selections
     */
    public void valueChanged(ListSelectionEvent e) {
      
      Object source = e.getSource();
      Font font = JFontChooser.this.getFont();
      Font oldFont = font;

      if(source == familyList) {
    
        font = new Font((String)familyList.getSelectedValue(), font.getStyle(), font.getSize());  
        firePropertyChange("font.family", oldFont, font);

      } else if (source == sizesList) {

        // Calculate the new font size
        int fontSize;
        try {
          fontSize = Integer.parseInt((String)sizesList.getSelectedValue());
        } catch (Exception ex) { fontSize = 12; }
      
        font = font.deriveFont((float)fontSize);
        firePropertyChange("font.size", oldFont, font);

      } else if(source == stylesList) {
         
        font = font.deriveFont(stylesList.getSelectedIndex());
        firePropertyChange("font.style", oldFont, font);

      }
     
      // Update selected font
      if(!oldFont.equals(font))
        JFontChooser.super.setFont(font);

    }

  } /* SelectionHandler */
  

  /**
   * @class SampleLabel
   */
  protected class SampleLabel extends JLabel implements PropertyChangeListener {

    public SampleLabel() {
      this(null);
    }

    public SampleLabel(String text) {
      super(text == null ? "Sample Text" : text);
      setHorizontalAlignment(JLabel.CENTER);
    }

    public void propertyChange(PropertyChangeEvent e) {

      String name = e.getPropertyName();
      if(name.equals("font.style") || name.equals("font.size") || name.equals("font.family"))
        this.setFont((Font)e.getNewValue());

    }

    public Dimension getMinimumSize() {
      return getPreferredSize();
    }

    public Dimension getPreferredSize() {
      Dimension dim = super.getPreferredSize();
      dim.height = 35;
      return dim;
    }

  } /* SampleLabel */


  /**
   * Create a modal dialog for choosing a font
   */
  public static Font showDialog(Component component) {
    return showDialog(component, null);
  } 

  public static Font showDialog(Component component, Font font) {
    return showDialog(component, "Select font", font);
  } 

  /**
   * Create a modal dialog for choosing a font
   */
  public static Font showDialog(Component component, String title, Font font) {
    
    if(font == null)
      font = component.getFont();

    FontDialog dlg = new FontDialog(component, title, font);
    return dlg.getSelectedFont();

  } 

  /**
   * @class FontDialog
   */
  protected static class FontDialog extends JDialog {

    private static JFontChooser chooser = new JFontChooser(null);

    /**
     * Create a new FontDialog
     */
    public FontDialog(Component component, String title, Font font) {

      super(JOptionPane.getFrameForComponent(component), title, true);

      Container content = this.getContentPane();
      
      content.setLayout(new BorderLayout());
      
      // Button panel
      JPanel buttonsPanel = new JPanel();  
      buttonsPanel.add(new JButton(new AcceptAction()));
      buttonsPanel.add(new JButton(new CancelAction()));   
      
      content.add(buttonsPanel, BorderLayout.SOUTH);

      chooser.setFont(font);
      content.add(chooser);

      pack();

      this.setResizable(true);
      this.setVisible(true);

    }

    public Font getSelectedFont() {
      return chooser.getSelectedFont();
    }

    /**
     * @class AcceptAction
     */
    protected class AcceptAction extends AbstractAction {
      
      public AcceptAction() { super("OK"); }
      
      public void actionPerformed(ActionEvent e) {
        FontDialog.this.setVisible(false);
      }
      
    }
    
    /**
     * @class CancelAction
     */
    protected class CancelAction extends AbstractAction {
      
      public CancelAction() { super("Cancel"); }
      
      public void actionPerformed(ActionEvent e) {
        FontDialog.this.setVisible(false);
      }
      
    }

  } /* FontDialog */

}
