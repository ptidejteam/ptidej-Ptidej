//package src.OBSERVER;
public class SupervisorView {
  public static final String NEWLINE = "\n";
  public static final String SET_MONTH = "Set Month";
  public static final String EXIT = "Exit";

  public static void main(String[] args) throws Exception {

    //Create the Subject
    ReportManager objSubject = new ReportManager();

    //Create Observers
    new MonthlyReport(objSubject);
    new YTDChart(objSubject);
  }
}// end of class

