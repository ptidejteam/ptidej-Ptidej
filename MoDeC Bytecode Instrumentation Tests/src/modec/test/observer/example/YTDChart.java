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

public class YTDChart extends JFrame implements Observer {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public static final String newline = "\n";

//  private JPanel pSearchCriteria;
//  private JLabel lblSalesFigure;
  private ReportManager objReportManager;
  private String department = "";
  private boolean fromRefresh;

  public YTDChart(ReportManager inp_objReportManager)
  throws Exception {
    super("Observer Pattern - Example");
    this.objReportManager = inp_objReportManager;

    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
      SwingUtilities.updateComponentTreeUI(YTDChart.this);
    } catch (Exception ex) {
      System.out.println(ex);
    }

    setSize(500, 500);
    setVisible(true);
    this.objReportManager.register(this);
  }

  public void refreshData(Observable subject) {
    if (subject == this.objReportManager) {
      //get subject's state
      this.department = this.objReportManager.getDepartment().trim();

      clear();
      this.fromRefresh = true;
      repaint();
    }
  }
  public void clear() {
    Graphics g = getGraphics();
    Dimension d = getSize();
    Color c = getBackground();
    g.setColor(c);
    g.fillRect(0, 0, d.width, d.height);
    repaint();
  }

  public void paint(Graphics g) {
//    Insets insets = insets();

    plotMonths(g);

    if (this.fromRefresh == true) {
      int x = 100, y = 100;
      int w = 50;
      int h = 20;

      int[] totals = getYTDTotals(this.department);
      //current month
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date());
      int month = cal.get(Calendar.MONTH) + 1;

      for (int i = 0; i < month; i++) {
        g.setColor(Color.blue);
        if (totals[i] > 0) {
          w = (int)(totals[i] / 50);
          g.fillRect(x, y, w, h);
          g.drawString ("$" + totals[i], x + w + 5,
                        y + 15);
        }
        y = y + 30;
      }
      this.fromRefresh = false;
    }
  }

  private void plotMonths(Graphics g) {
    if ((this.department != null) &&
        (this.department.trim().length() > 0)) {
      g.drawString(this.department + " YTD Report",150, 50);
    } else {
      g.drawString(this.department + "YTD Report",150, 50);
    }
    String[] months = {"Jan","Feb","Mar","Apr",
                       "May","Jun","Jul","Aug",
                       "Sep","Oct","Nov","Dec"};

    int x = 50, y = 115;
    for (int j = 0; j < months.length; j++) {
      g.setColor(Color.blue);
      g.drawString(months[j], x, y);
      y = y + 30;
    }

  }
  private int[] getYTDTotals(String department) {
    int[] totals = {1000, 0, 2000, 0, 0, 0, 0, 0, 0, 0, 0,
                    0};
    for (int i = 0; i < 12; i++) {
      totals[i] = getMonthlyTotal(i + 1, department);
    }
    return totals;
  }
  private int getMonthlyTotal(int month, String department) {
    FileUtil futil = new FileUtil();
    Vector allRows = futil.fileToVector("Transactions.dat");
    int total = 0;

    String searchStr = department + "," + month + ",";

    for (int i = 0; i < allRows.size(); i++) {
      String str = (String) allRows.elementAt(i);
      if (str.indexOf(searchStr) > -1) {
        StringTokenizer st =
          new StringTokenizer(str, ",");
        st.nextToken();//bypass the department
        st.nextToken();//bypass the month
        st.nextToken();//bypass the date
        st.nextToken();//bypass items
        String amount = st.nextToken();

        total = total + Integer.valueOf(amount).intValue();
      }
    }
    return total;
  }

}// end of class YTDChart

