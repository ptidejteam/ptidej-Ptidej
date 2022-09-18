package com.oreilly.aspectjcookbook;

import java.io.*;

public privileged aspect AccountPersistenceAspect extends PersistenceAspect
{
   declare parents : MainApplication implements ObjectStore, Runnable;

   protected pointcut restoreStorage(ObjectStore store) : 
      execution(MainApplication.new(..)) && 
      target(store);
   
   // Selects all join points where it is necessary to persist the store
   protected pointcut persistStorage(ObjectStore store) : 
      execution(public void MainApplication.run()) && 
      this(store);
   
   declare parents : Account extends Serializable;

   private File MainApplication.accountsFile = new File("accounts.ser");
   
   after(MainApplication mainApplication) : 
      restoreStorage(ObjectStore) && 
      target(mainApplication)
   {
      // Register a shutdown hook
      Thread shutdownThread = new Thread(mainApplication);
      Runtime.getRuntime().addShutdownHook(shutdownThread);
   }
   
   public void MainApplication.run()
   {
      // Do nothing, merely provides the trigger that the shutdown hook has been
      // executed so as to persist the store on shutdown.
   }
   
   public void MainApplication.persist()
   {
      try
      {
         ObjectOutput out = new ObjectOutputStream(
               new FileOutputStream(this.accountsFile));
         
         Object[] objectsToStore = this.accounts.toArray();
         out.writeObject(objectsToStore);
         out.flush();
         out.close();
      }
      catch (Exception e)
      {
         System.err.println("Couldn't store accounts to " + this.accountsFile);
      }
   }
   
   public void MainApplication.restore()
   {
      // Check that the serialized accounts file exists
      if (this.accountsFile.exists() && this.accountsFile.canRead())
      {
         try
         {
            ObjectInput input = new ObjectInputStream(
                  new FileInputStream(this.accountsFile));
            
            Object[] objectsToRestore = (Object[]) input.readObject();
            for(int x = 0; x < objectsToRestore.length; x++)
            {
               this.accounts.add(objectsToRestore[x]);
            }
            
            input.close();
         }
         catch (Exception e)
         {
            System.err.println("Couldn't restore accounts due to a corrupt " + this.accountsFile + " file");
            e.printStackTrace();
         }
      }
   }
}
