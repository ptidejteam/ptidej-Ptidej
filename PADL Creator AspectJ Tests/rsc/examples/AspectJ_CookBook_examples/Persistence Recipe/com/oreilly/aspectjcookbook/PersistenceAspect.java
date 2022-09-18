package com.oreilly.aspectjcookbook;

public abstract aspect PersistenceAspect
{
   public interface ObjectStore
   {
      public void persist();
      public void restore();
   }
   
   protected abstract pointcut restoreStorage(ObjectStore store);
   
   after(ObjectStore store) : restoreStorage(store)
   {
      store.restore();
   }
   
   protected abstract pointcut persistStorage(ObjectStore store);
   
   after(ObjectStore store) : persistStorage(store)
   {  
      store.persist();
   }
}
