
package com.oreilly.aspectjcookbook;


public class Employee
{
   private String name;
   private String position;
   
   public Employee(String name, String position)
   {
      this.name = name;
      this.position = position;
   }
   
   public void changePosition(String position)
   {
      this.position = position;
   }
   
   public String getName()
   {
      return name;
   }
   
   public String getPosition()
   {
      return position;
   }
   
   public String toString()
   {
      return "Employee: " + this.name + ", position: " + this.position;
   }
}
