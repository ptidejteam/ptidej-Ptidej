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

