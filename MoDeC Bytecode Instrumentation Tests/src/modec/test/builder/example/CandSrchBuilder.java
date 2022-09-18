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

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class CandSrchBuilder extends UIBuilder {

  private JTextField txtUserName = new JTextField(15);
  private JTextField txtSkill = new JTextField(10);
  private JComboBox cmbExperience = new JComboBox();

  public void addUIControls() {
    this.searchUI = new JPanel();
    JLabel lblUserName = new JLabel("Name :");
    JLabel lblExperienceRange =
      new JLabel("Experience(min Yrs.):");
    JLabel lblSkill = new JLabel("Skill :");
    this.cmbExperience.addItem("<5");
    this.cmbExperience.addItem(">5");

    GridBagLayout gridbag = new GridBagLayout();
    this.searchUI.setLayout(gridbag);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.WEST;
    this.searchUI.add(lblUserName);
    this.searchUI.add(this.txtUserName);
    this.searchUI.add(lblExperienceRange);
    this.searchUI.add(this.cmbExperience);
    this.searchUI.add(lblSkill);
    this.searchUI.add(this.txtSkill);

    gbc.insets.top = 5;
    gbc.insets.bottom = 5;
    gbc.insets.left = 5;
    gbc.insets.right = 5;

    gbc.gridx = 0;
    gbc.gridy = 0;
    gridbag.setConstraints(lblUserName, gbc);
    gbc.gridx = 0;
    gbc.gridy = 1;
    gridbag.setConstraints(lblExperienceRange, gbc);
    gbc.gridx = 0;
    gbc.gridy = 2;
    gridbag.setConstraints(lblSkill, gbc);

    gbc.anchor = GridBagConstraints.WEST;
    gbc.gridx = 1;
    gbc.gridy = 0;
    gridbag.setConstraints(this.txtUserName, gbc);
    gbc.gridx = 1;
    gbc.gridy = 1;
    gridbag.setConstraints(this.cmbExperience, gbc);
    gbc.gridx = 1;
    gbc.gridy = 2;
    gridbag.setConstraints(this.txtSkill, gbc);

  }

  public void initialize() {
    this.txtUserName.setText("Enter UserName Here");
    this.txtSkill.setText("Internet Tech");
  }

  public String getSQL() {
    String experience =
      (String) this.cmbExperience.getSelectedItem();
    return ("Select * from Candidate where Username='" +
            this.txtUserName.getText() + "' and Experience " +
            experience + " and Skill='" +
            this.txtSkill.getText() + "'");

  }

}
