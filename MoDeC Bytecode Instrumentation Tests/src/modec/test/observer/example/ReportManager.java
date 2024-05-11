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
package modec.test.observer.example;

//package src.OBSERVER;
//CONCRETE SUBJECT
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class ReportManager extends JFrame 
  implements Observable {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public static final String newline = "\n";
  public static final String SET_OK = "OK";
  public static final String EXIT = "Exit";

  private JComboBox cmbDepartmentList;
  private JButton btnOK, btnExit;
  private Vector observersList;
  private String department;

  public ReportManager() throws Exception {
    super("Observer Pattern - Example");

    this.observersList = new Vector();

    // Create controls
    this.cmbDepartmentList = new JComboBox();
    this.btnOK = new JButton(ReportManager.SET_OK);
    this.btnOK.setMnemonic(KeyEvent.VK_S);
    this.btnExit = new JButton(ReportManager.EXIT);
    this.btnExit.setMnemonic(KeyEvent.VK_X);

    //Create Labels
    JLabel lblDepartmentList =
      new JLabel("Select a Department:");

    ButtonHandler vf = new ButtonHandler(this);

    this.btnOK.addActionListener(vf);
    this.btnExit.addActionListener(vf);

    JPanel buttonPanel = new JPanel();

    //----------------------------------------------
    GridBagLayout gridbag = new GridBagLayout();
    buttonPanel.setLayout(gridbag);
    GridBagConstraints gbc = new GridBagConstraints();
    buttonPanel.add(lblDepartmentList);
    buttonPanel.add(this.cmbDepartmentList);
    buttonPanel.add(this.btnOK);
    buttonPanel.add(this.btnExit);

    gbc.insets.top = 5;
    gbc.insets.bottom = 5;
    gbc.insets.left = 5;
    gbc.insets.right = 5;

    gbc.gridx = 0;
    gbc.gridy = 0;
    gridbag.setConstraints(lblDepartmentList, gbc);
    gbc.anchor = GridBagConstraints.WEST;
    gbc.gridx = 1;
    gbc.gridy = 0;
    gridbag.setConstraints(this.cmbDepartmentList, gbc);

    gbc.anchor = GridBagConstraints.EAST;
    gbc.insets.left = 2;
    gbc.insets.right = 2;
    gbc.insets.top = 40;
    gbc.gridx = 0;
    gbc.gridy = 6;
    gridbag.setConstraints(this.btnOK, gbc);
    gbc.anchor = GridBagConstraints.WEST;
    gbc.gridx = 1;
    gbc.gridy = 6;
    gridbag.setConstraints(this.btnExit, gbc);

    Container contentPane = getContentPane();
    contentPane.add(buttonPanel, BorderLayout.CENTER);
    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
      SwingUtilities.updateComponentTreeUI(
        ReportManager.this);
    } catch (Exception ex) {
      System.out.println(ex);
    }

    initialize();
    setSize(250, 200);
    setVisible(true);
  }
  private void initialize() throws Exception {
    // fill some test data here into the listbox.
    this.cmbDepartmentList.addItem("HardWare");
    this.cmbDepartmentList.addItem("Electronics");
    this.cmbDepartmentList.addItem("Furniture");
  }

  public void register(Observer obs) {
    // Add to the list of Observers
    this.observersList.addElement(obs);
  }
  public void unRegister(Observer obs) {
    // remove from the list of Observers

  }
  public void notifyObservers() {
    // Send notify to all Observers
    for (int i = 0; i < this.observersList.size(); i++) {
      Observer observer =
        (Observer) this.observersList.elementAt(i);
      observer.refreshData(this);
    }
  }
  public String getDepartment() {
    return this.department;
  }
  public void setDepartment(String dept) {
    this.department = dept;
  }

  class ButtonHandler implements ActionListener {
    ReportManager subject;

    public void actionPerformed(ActionEvent e) {

      if (e.getActionCommand().equals(ReportManager.EXIT)) {
        System.exit(1);
      }
      if (e.getActionCommand().equals(ReportManager.SET_OK)) {
        String dept = (String)
                      ReportManager.this.cmbDepartmentList.getSelectedItem();
        //change in state
        this.subject.setDepartment(dept);
        this.subject.notifyObservers();
      }
    }

    public ButtonHandler() {
    }
    public ButtonHandler(ReportManager manager) {
      this.subject = manager;
    }

  }

}// end of class

