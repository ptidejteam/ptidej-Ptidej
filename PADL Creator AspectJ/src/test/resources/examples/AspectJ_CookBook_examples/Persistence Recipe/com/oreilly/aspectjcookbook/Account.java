package com.oreilly.aspectjcookbook;

public interface Account
{
   public float getBalance();

   public void credit(float value);

   public void debit(float value) throws InsufficientFundsException;
}
