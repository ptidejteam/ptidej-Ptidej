//package src.OBSERVER;
// CONCRETE OBSERVER
import java.awt.*;
import javax.swing.*;
import com.sun.java.swing.plaf.windows.*;
import java.util.*;

public class YTDChart extends JFrame implements Observer {
  public static final String newline = "\n";

//  private JPanel pSearchCriteria;
//  private JLabel lblSalesFigure;
  private ReportManager objReportManager;
  private String department = "";
  private boolean fromRefresh;

  public YTDChart(ReportManager inp_objReportManager)
  throws Exception {
    super("Observer Pattern - Example");
    objReportManager = inp_objReportManager;

    try {
      UIManager.setLookAndFeel(new WindowsLookAndFeel());
      SwingUtilities.updateComponentTreeUI(YTDChart.this);
    } catch (Exception ex) {
      System.out.println(ex);
    }

    setSize(500, 500);
    setVisible(true);
    objReportManager.register(this);
  }

  public void refreshData(Observable subject) {
    if (subject == objReportManager) {
      //get subject's state
      department = objReportManager.getDepartment().trim();

      clear();
      fromRefresh = true;
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

    if (fromRefresh == true) {
      int x = 100, y = 100;
      int w = 50;
      int h = 20;

      int[] totals = getYTDTotals(department);
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
      fromRefresh = false;
    }
  }

  private void plotMonths(Graphics g) {
    if ((department != null) &&
        (department.trim().length() > 0)) {
      g.drawString(department + " YTD Report",150, 50);
    } else {
      g.drawString(department + "YTD Report",150, 50);
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
    Vector allRows = futil.fileToVector("Data/Transactions.dat");
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

