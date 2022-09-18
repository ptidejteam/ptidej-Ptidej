/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package modec.test.command.example;

//package COMMAND.GUI.before;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class FTPGUI extends JFrame {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public static final String newline = "\n";
  public static final String UPLOAD = "Upload";
  public static final String DOWNLOAD = "Download";
  public static final String DELETE = "Delete";
  public static final String EXIT = "Exit";

  private JList localList;
  private JList remoteList;
  private DefaultListModel defLocalList, defRemoteList;
  private JButton btnUpload;
  private JButton btnDownload;
  private JButton btnDelete;
  private JButton btnExit;

  public FTPGUI() throws Exception {
    super("Command Pattern - Example");

    // Create controls
    this.defLocalList = new DefaultListModel();
    this.defRemoteList = new DefaultListModel();
    this.localList = new JList(this.defLocalList);
    this.remoteList = new JList(this.defRemoteList);

    this.localList.setSelectionMode(
      ListSelectionModel.SINGLE_SELECTION);
    this.localList.setSelectedIndex(-1);
    JScrollPane spLocalList = new JScrollPane(this.localList);

    this.remoteList.setSelectionMode(
      ListSelectionModel.SINGLE_SELECTION);
    this.remoteList.setSelectedIndex(-1);
    JScrollPane spRemoteList = new JScrollPane(this.remoteList);

    //Create Labels
    JLabel lblLocalList = new JLabel("Local List:");
    JLabel lblRemoteList = new JLabel("Remote List:");
    JLabel lblSpacer = new JLabel("         ");

    //Create buttons
    this.btnUpload = new JButton(FTPGUI.UPLOAD);
    this.btnUpload.setMnemonic(KeyEvent.VK_U);
    this.btnDownload = new JButton(FTPGUI.DOWNLOAD);
    this.btnDownload.setMnemonic(KeyEvent.VK_N);
    this.btnDelete = new JButton(FTPGUI.DELETE);
    this.btnDelete.setMnemonic(KeyEvent.VK_D);
    this.btnExit = new JButton(FTPGUI.EXIT);
    this.btnExit.setMnemonic(KeyEvent.VK_X);

    ButtonHandler vf = new ButtonHandler();

    this.btnUpload.addActionListener(vf);
    this.btnDownload.addActionListener(vf);
    this.btnDelete.addActionListener(vf);
    this.btnExit.addActionListener(vf);

    JPanel lstPanel = new JPanel();

    GridBagLayout gridbag2 = new GridBagLayout();
    lstPanel.setLayout(gridbag2);
    GridBagConstraints gbc2 = new GridBagConstraints();
    lstPanel.add(lblLocalList);
    lstPanel.add(lblRemoteList);
    lstPanel.add(spLocalList);
    lstPanel.add(spRemoteList);
    lstPanel.add(lblSpacer);

    gbc2.gridx = 0;
    gbc2.gridy = 0;
    gridbag2.setConstraints(lblLocalList, gbc2);
    gbc2.gridx = 1;
    gbc2.gridy = 0;
    gridbag2.setConstraints(lblSpacer, gbc2);

    gbc2.gridx = 5;
    gbc2.gridy = 0;
    gridbag2.setConstraints(lblRemoteList, gbc2);
    gbc2.gridx = 0;
    gbc2.gridy = 1;
    gridbag2.setConstraints(spLocalList, gbc2);
    gbc2.gridx = 5;
    gbc2.gridy = 1;
    gridbag2.setConstraints(spRemoteList, gbc2);

    //-----------------------------------
    //For layout purposes, put the buttons in a separate panel
    JPanel buttonPanel = new JPanel();

    //----------------------------------------------
    GridBagLayout gridbag = new GridBagLayout();
    buttonPanel.setLayout(gridbag);
    GridBagConstraints gbc = new GridBagConstraints();
    buttonPanel.add(lstPanel);
    buttonPanel.add(this.btnUpload);
    buttonPanel.add(this.btnDownload);
    buttonPanel.add(this.btnDelete);
    buttonPanel.add(this.btnExit);

    gbc.insets.top = 5;
    gbc.insets.bottom = 5;
    gbc.insets.left = 5;
    gbc.insets.right = 5;

    gbc.anchor = GridBagConstraints.WEST;
    gbc.gridx = 1;
    gbc.gridy = 0;
    gridbag.setConstraints(this.btnUpload, gbc);
    gbc.gridx = 2;
    gbc.gridy = 0;
    gridbag.setConstraints(this.btnDownload, gbc);
    gbc.gridx = 3;
    gbc.gridy = 0;
    gridbag.setConstraints(this.btnDelete, gbc);
    gbc.gridx = 4;
    gbc.gridy = 0;
    gridbag.setConstraints(this.btnExit, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    gridbag.setConstraints(lstPanel, gbc);

    gbc.anchor = GridBagConstraints.EAST;
    gbc.insets.left = 2;
    gbc.insets.right = 2;
    gbc.insets.top = 40;

    //****************************************************
    //Add the buttons and the log to the frame
    Container contentPane = getContentPane();
    contentPane.add(lstPanel, BorderLayout.CENTER);
    contentPane.add(buttonPanel, BorderLayout.SOUTH);

    initialize();
    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
      SwingUtilities.updateComponentTreeUI(FTPGUI.this);
    } catch (Exception ex) {
      System.out.println(ex);
    }

  }
  private void initialize() {
    // fill some test data here into the listbox.
    this.defLocalList.addElement("first.html");
    this.defLocalList.addElement("second.html");
    this.defLocalList.addElement("third.html");
    this.defLocalList.addElement("fourth.html");
    this.defLocalList.addElement("fifth.html");
    this.defLocalList.addElement("Design Patterns 1.html");

    this.defRemoteList.addElement("sixth.html");
    this.defRemoteList.addElement("seventh.html");
    this.defRemoteList.addElement("eighth.html");
    this.defRemoteList.addElement("ninth.html");
    this.defRemoteList.addElement("Design Patterns 2.html");

  }

  public static void main(String[] args) throws Exception {

    JFrame frame = new FTPGUI();
    frame.addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            System.exit(0);
          }
        }
                           );

    //frame.pack();
    frame.setSize(450, 300);
    frame.setVisible(true);
  }

  class ButtonHandler implements ActionListener {
    public void actionPerformed(ActionEvent e) {

      // if statements - for different types of client requests
      if (e.getActionCommand().equals(FTPGUI.EXIT)) {
        System.exit(1);
      }
      if (e.getActionCommand().equals(FTPGUI.UPLOAD)) {
        int index = FTPGUI.this.localList.getSelectedIndex();
        String selectedItem =
          FTPGUI.this.localList.getSelectedValue().toString();
        ((DefaultListModel) FTPGUI.this.localList.getModel()).remove(
          index);

        ((DefaultListModel) FTPGUI.this.remoteList.getModel()).
        addElement(selectedItem);
      }
      if (e.getActionCommand().equals(FTPGUI.DOWNLOAD)) {
        int index = FTPGUI.this.remoteList.getSelectedIndex();
        String selectedItem =
          FTPGUI.this.remoteList.getSelectedValue().toString();
        ((DefaultListModel) FTPGUI.this.remoteList.getModel()).remove(
          index);

        ((DefaultListModel) FTPGUI.this.localList.getModel()).
        addElement(selectedItem);
      }
      if (e.getActionCommand().equals(FTPGUI.DELETE)) {
        int index = FTPGUI.this.localList.getSelectedIndex();
        if (index >= 0) {
          ((DefaultListModel) FTPGUI.this.localList.getModel()).
          remove(index);
        }

        index = FTPGUI.this.remoteList.getSelectedIndex();
        if (index >= 0) {
          ((DefaultListModel) FTPGUI.this.remoteList.getModel()).
          remove(index);
        }
      }

    }
  }
}// end of class

