package com.oreilly.aspectjcookbook;

import java.io.*;

public aspect EmployeePersistenceAspect extends PersistenceAspect
{
   declare parents : EmployeeCollection implements ObjectStore;

   protected pointcut restoreStorage(ObjectStore store) : 
      execution(EmployeeCollection.new(..)) && 
      target(store);
   
   protected pointcut persistStorage(ObjectStore store) : 
      call(* java.util.List.add(..)) && 
      target(EmployeeCollection) &&
      target(store);

   declare parents : Employee extends Serializable;
   
   private File EmployeeCollection.employeesFile = new File("employees.ser");
   
   public void EmployeeCollection.persist()
   {
      try
      {
         ObjectOutput out = new ObjectOutputStream(new FileOutputStream(this.employeesFile));
         Object[] objectsToStore = this.toArray();
         out.writeObject(objectsToStore);
         out.flush();
         out.close();
      }
      catch (Exception e)
      {
         System.err.println("Couldn't store employees to " + this.employeesFile);
      }
   }
   
   public void EmployeeCollection.restore()
   {
      // Check that the serialized accounts file exists
      if (this.employeesFile.exists() && this.employeesFile.canRead())
      {
         try
         {
            ObjectInput input = new ObjectInputStream(new FileInputStream(this.employeesFile));
            
            Object[] objectsToRestore = (Object[]) input.readObject();
            for(int x = 0; x < objectsToRestore.length; x++)
            {
               this.add(objectsToRestore[x]);
            }
            
            input.close();
         }
         catch (Exception e)
         {
            System.err.println("Couldn't restore employees due to a corrupt " + this.employeesFile + " file");
            e.printStackTrace();
         }
      }
   }
}
