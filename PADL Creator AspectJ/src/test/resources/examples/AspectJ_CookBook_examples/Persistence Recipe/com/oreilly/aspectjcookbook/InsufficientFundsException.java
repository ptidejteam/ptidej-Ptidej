package com.oreilly.aspectjcookbook;

public class InsufficientFundsException extends Exception
{
   public InsufficientFundsException(String message)
   {
      super(message);
   }
}
