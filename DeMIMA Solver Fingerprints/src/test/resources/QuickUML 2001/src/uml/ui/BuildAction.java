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

import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import uml.builder.BuilderException;
import uml.builder.CPlusPlusBuilder;
import uml.builder.CodeBuilder;
import uml.builder.Context;
import uml.builder.JavaBuilder;

/**
 * @class BuildAction
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 * 
 */
public class BuildAction extends AbstractAction {

  protected DiagramContainer container;

  public BuildAction(DiagramContainer container) {

    super("Build ...");

    this.container = container;

  }

  public void actionPerformed(ActionEvent e) {

    BuildDialog dlg = new BuildDialog(container.getFrame());
    dlg.show();
  
  }

  /**
   * @class BuildDialog
   */
  protected class BuildDialog extends JDialog implements ActionListener {
    
    protected JTextArea textArea = new JTextArea();
    protected JTextField currentPath = new JTextField();
    protected JCheckBox useArrays;
    protected BrowseAction browseAction = new BrowseAction();
    protected String currentLanguage;

    public BuildDialog(Frame frame) {
      
      super(frame, "Build source code", true);
      
 
      JPanel content = new JPanel();
      content.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

      GridBagLayout gridbag = new GridBagLayout();
      GridBagConstraints gc = new GridBagConstraints();
      
      content.setLayout(gridbag);
     
      JLabel lbl = new JLabel("Output:");

      gc.gridheight = 1;
      gc.gridwidth = 1;
      gc.weightx = 1.0;
      gc.weighty = 1.0;
      gc.fill = GridBagConstraints.NONE;
      gc.anchor = GridBagConstraints.WEST;

      gridbag.setConstraints(lbl, gc);
      content.add(lbl);

      currentPath = new JTextField();
      currentPath.setText(browseAction.getChooser().getLastDirectory());
      gc.gridwidth = 2;
      gc.anchor = GridBagConstraints.CENTER;
      gc.fill = GridBagConstraints.HORIZONTAL;

      gridbag.setConstraints(currentPath, gc);
      content.add(currentPath);

      JButton btn = new JButton(browseAction);
      btn.setFont(btn.getFont().deriveFont(Font.PLAIN));
      
      gc.anchor = GridBagConstraints.EAST;
      gc.gridwidth = GridBagConstraints.REMAINDER;
      gc.fill = GridBagConstraints.NONE;
      gc.weightx = 1.0;

      gridbag.setConstraints(btn, gc);
      content.add(btn);

      lbl = new JLabel("Language:");
      
      gc.gridheight = 1;
      gc.gridwidth = 1;
      gc.weightx = 1.0;
      gc.weighty = 1.0;
      gc.fill = GridBagConstraints.NONE;
      gc.anchor = GridBagConstraints.WEST;

      gridbag.setConstraints(lbl, gc);
      content.add(lbl);


      JComboBox box = new JComboBox(new Object[] {"Java", "C++"}) {
        protected void fireActionEvent() { currentLanguage = getSelectedItem().toString(); }
      };

      box.setFont(box.getFont().deriveFont(Font.PLAIN));
      currentLanguage = box.getSelectedItem().toString();

      gc.gridwidth = GridBagConstraints.REMAINDER;
      gridbag.setConstraints(box, gc);

      content.add(box);

      lbl = new JLabel("Use arrays:");
      
      gc.gridheight = 1;
      gc.gridwidth = 1;
      gc.weightx = 1.0;
      gc.weighty = 1.0;
      gc.fill = GridBagConstraints.NONE;
      gc.anchor = GridBagConstraints.WEST;

      gridbag.setConstraints(lbl, gc);
      content.add(lbl);

      useArrays = new JCheckBox();
      //box.setFont(box.getFont().deriveFont(Font.PLAIN));

      gc.gridwidth = GridBagConstraints.REMAINDER;
      gridbag.setConstraints(useArrays, gc);

      content.add(useArrays);

      lbl = new JLabel("Messages:");
      gc.gridwidth = GridBagConstraints.REMAINDER;
      gc.anchor = GridBagConstraints.WEST;

      gridbag.setConstraints(lbl, gc);
      content.add(lbl);

      
      // Message panel
      JScrollPane pane = new JScrollPane(textArea);
      textArea.setEditable(false);
      browseAction.getChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

      gc.fill = GridBagConstraints.BOTH;
      gc.gridheight = GridBagConstraints.RELATIVE;
      gc.weighty = 20.0;
 
      gridbag.setConstraints(pane, gc);
      content.add(pane);

      // Add the build button
      JPanel buttonPanel = new JPanel(new GridLayout(1,2,4,4));   

      btn = new JButton("Build");
      btn.addActionListener(this);
      buttonPanel.add(btn);

      btn = new JButton("Cancel");
      btn.addActionListener(this);
      buttonPanel.add(btn);
      
      gc.gridheight = GridBagConstraints.REMAINDER;
      gc.weighty = 1;
      gridbag.setConstraints(buttonPanel, gc);
 
      buttonPanel.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
      content.add(buttonPanel);

      setContentPane(content);

      // Center on the frame
      int w = frame.getWidth();
      int h = frame.getHeight();
      int x = (int)(frame.getX() + w*0.25);
      int y = (int)(frame.getY() + h*0.25);

      setBounds(x, y, (int)(w*0.5), (int)(h*0.5));

    }

    protected CodeBuilder getBuilder() {
  
      String path = currentPath.getText();

      if(currentLanguage.equals("C++"))
        return new CPlusPlusBuilder(path);

      else if(currentLanguage.equals("Java"))
        return new JavaBuilder(path);

      else 
        throw new RuntimeException("No builder available");

    }


    public void actionPerformed(ActionEvent e) {
      
      if(e.getActionCommand().equals("Build")) {

        // Build 
        Context ctx = null;
        try {

          ctx = new Context(container.getView().getModel());
          ctx.enableArrays(useArrays.isSelected());
          getBuilder().build(ctx);

        } catch(BuilderException x) { /* Catch builder exceptions & stop */ 
          
          String msg = x.getMessage();
          String title = "I/O Error";

          if(msg.startsWith("I/O Error: ")) {
            JOptionPane.showMessageDialog(this, msg, title, JOptionPane.ERROR_MESSAGE);
            return;
          }

        }

        // Display the warnings & errors in the text area
        if(ctx != null) {

          // Print warnings
          for(Iterator i = ctx.getWarnings(); i.hasNext();) {

            String warning = (String)i.next();

            append("warning: ");
            append(warning);
            append("\n");

          }

          append("\n");

          // Print errors
          for(Iterator i = ctx.getErrors(); i.hasNext();) {

            String error = (String)i.next();

            append("error: ");
            append(error);
            append("\n");

          }

        }

        // Print done msg & disable
        append("\nDONE!\n");
        //((JButton)e.getSource()).setEnabled(false);

      } else 
        dispose();

    }  
    
    private final void append(String msg) {
      
      try {
        Document doc = textArea.getDocument();
        doc.insertString(doc.getLength(), msg, null);
      } catch(BadLocationException e) { e.printStackTrace(); }

    }


    protected class BrowseAction extends FileAction {
      
      public BrowseAction() { 
        super("...");
      }
      
      public void actionPerformed(ActionEvent e) {
        browseAction.getChooser().showDialog(BuildDialog.this, "Select a directory");
        currentPath.setText(browseAction.getChooser().getLastDirectory());
      }
      
    }


  } /* BuildDialog */

}
