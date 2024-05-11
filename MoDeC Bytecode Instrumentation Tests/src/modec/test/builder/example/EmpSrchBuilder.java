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
package modec.test.builder.example;

//package src.BUILDER;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class EmpSrchBuilder extends UIBuilder {
  JLabel lblUserName = new JLabel("Name :");

  private JTextField txtUserName = new JTextField(15);
  private JTextField txtCity = new JTextField(15);
  private JTextField txtRenewal = new JTextField(10);

  public void addUIControls() {
    this.searchUI = new JPanel();
    JLabel lblUserName = new JLabel("Name :");
    JLabel lblCity = new JLabel("City:");
    JLabel lblRenewal = new JLabel("Membership Renewal :");

    GridBagLayout gridbag = new GridBagLayout();
    this.searchUI.setLayout(gridbag);
    GridBagConstraints gbc = new GridBagConstraints();
    this.searchUI.add(lblUserName);
    this.searchUI.add(this.txtUserName);
    this.searchUI.add(lblCity);
    this.searchUI.add(this.txtCity);
    this.searchUI.add(lblRenewal);
    this.searchUI.add(this.txtRenewal);

    gbc.anchor = GridBagConstraints.WEST;

    gbc.insets.top = 5;
    gbc.insets.bottom = 5;
    gbc.insets.left = 5;
    gbc.insets.right = 5;

    gbc.gridx = 0;
    gbc.gridy = 0;
    gridbag.setConstraints(lblUserName, gbc);
    gbc.gridx = 0;
    gbc.gridy = 1;
    gridbag.setConstraints(lblCity, gbc);
    gbc.gridx = 0;
    gbc.gridy = 2;
    gridbag.setConstraints(lblRenewal, gbc);

    gbc.anchor = GridBagConstraints.WEST;
    gbc.gridx = 1;
    gbc.gridy = 0;
    gridbag.setConstraints(this.txtUserName, gbc);
    gbc.gridx = 1;
    gbc.gridy = 1;
    gridbag.setConstraints(this.txtCity, gbc);
    gbc.gridx = 1;
    gbc.gridy = 2;
    gridbag.setConstraints(this.txtRenewal, gbc);
  }

  public void initialize() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new java.util.Date());

    this.txtUserName.setText("Enter UserName Here");
    this.txtRenewal.setText((cal.get(Calendar.MONTH) + 1) + "/" +
                       cal.get(Calendar.DATE) + "/" +
                       cal.get(Calendar.YEAR));
  }

  public String getSQL() {
    return ("Select * from Employer where Username='" +
            this.txtUserName.getText() + "'" + " and City='" +
            this.txtCity.getText() + "' and DateRenewal='" +
            this.txtRenewal.getText() + "'");

  }

}
