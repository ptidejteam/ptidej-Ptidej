package com.oreilly.aspectjcookbook;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class MainApplication
{
   private List accounts;
   private List employees;
   
   public MainApplication()
   {
      this.accounts = new ArrayList();
      this.employees = new EmployeeCollection();
   }
   
   public void addAccount(Account account)
   {
      this.accounts.add(account);
   }
   
   public void addEmployee(Employee employee)
   {
      this.employees.add(employee);
   }
   
   public void listAccounts()
   {

   	System.out.println("List of accounts, count: " + this.accounts.size());
      Iterator iterator = this.accounts.iterator();
      while (iterator.hasNext())
      {
         System.out.println(iterator.next());
      }
      System.out.println("End of list of accounts");
   }
   
   public void listEmployees()
   {

   	System.out.println("List of employees, count: " + this.employees.size());
      Iterator iterator = this.employees.iterator();
      while (iterator.hasNext())
      {
         System.out.println(iterator.next());
      }
      System.out.println("End of list of employees");
   }
   
   public int countAccounts()
   {
   	return this.accounts.size();
   }
   
   public static void main(String[] args)
   {
      MainApplication application = new MainApplication();
     
      // List current accounts
      application.listAccounts();
      
      // Setup a new accounts
      BankAccount account = new BankAccount(application.countAccounts());

      account.credit(50.0f + application.countAccounts());

      application.addAccount(account);
      
      // List current accounts
      application.listAccounts();
      
      // List current employees
      application.listEmployees();
      
      // Setup a new accounts
      Employee employee = new Employee("Russ", "Intern");

      application.addEmployee(employee);
      
      // List current accounts
      application.listEmployees(); 
   }
}