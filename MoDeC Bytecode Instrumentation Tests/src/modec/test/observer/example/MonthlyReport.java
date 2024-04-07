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
// CONCRETE OBSERVER
import java.awt.*;
import javax.swing.*;
import java.util.*;


public class MonthlyReport extends JFrame implements Observer {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public static final String newline = "\n";

  private JLabel lblTransactions;
  private JTextArea taTransactions;
  private ReportManager objReportManager;


  public MonthlyReport(ReportManager inp_objReportManager)
  throws Exception {
    super("Observer Pattern - Example");
    this.objReportManager = inp_objReportManager;

    // Create controls
    this.taTransactions = new JTextArea(5, 40);
    this.taTransactions.setFont(new Font("Serif", Font.PLAIN, 14));
    this.taTransactions.setLineWrap(true);
    this.taTransactions.setWrapStyleWord(true);

    //Create Labels
    this.lblTransactions =
      new JLabel("Current Month Transactions");

    //For layout purposes, put the buttons in a separate panel
    JPanel buttonPanel = new JPanel();

    buttonPanel.add(this.lblTransactions);
    buttonPanel.add(this.taTransactions);

    Container contentPane = getContentPane();
    contentPane.add(buttonPanel, BorderLayout.CENTER);
    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
      SwingUtilities.updateComponentTreeUI(
        MonthlyReport.this);
    } catch (Exception ex) {
      System.out.println(ex);
    }

    setSize(400, 300);
    setVisible(true);
    this.objReportManager.register(this);

  }

  public void refreshData(Observable subject) {
    if (subject == this.objReportManager) {
      //get subject's state
      String department = this.objReportManager.getDepartment();

      this.lblTransactions.setText(
        "Current Month Transactions - " +
        department);
      Vector trnList =
        getCurrentMonthTransactions(department);
      String content = "";
      for (int i = 0; i < trnList.size(); i++) {
        content = content +
                  trnList.elementAt(i).toString() + "\n";
      }
      this.taTransactions.setText(content);
    }
  }
  private Vector getCurrentMonthTransactions(String department
                                             ) {
    Vector v = new Vector();
    FileUtil futil = new FileUtil();
    Vector allRows = futil.fileToVector("Transactions.dat");

    //current month
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    int month = cal.get(Calendar.MONTH) + 1;

    String searchStr = department + "," + month + ",";
    int j = 1;
    for (int i = 0; i < allRows.size(); i++) {
      String str = (String) allRows.elementAt(i);
      if (str.indexOf(searchStr) > -1) {

        StringTokenizer st =
          new StringTokenizer(str, ",");
        st.nextToken();//bypass the department
        str = "   " + j + ". " + st.nextToken() + "/" +
              st.nextToken() + "~~~" +
              st.nextToken() + "Items" + "~~~" +
              st.nextToken() + " Dollars";
        j++;
        v.addElement(str);
      }
    }
    return v;
  }
}// end of class

