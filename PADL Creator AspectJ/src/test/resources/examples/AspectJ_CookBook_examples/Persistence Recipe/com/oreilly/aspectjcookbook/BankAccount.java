package com.oreilly.aspectjcookbook;

public class BankAccount implements Account
{
   private long accountNumber;
   private float balance = 0.0f;
   
   public BankAccount(long accountNumber)
   {
      this.accountNumber = accountNumber;
   }

   public float getBalance()
   {
      return balance;
   }

   public void credit(float value)
   {
      this.balance += value;
   }

   public void debit(float value) throws InsufficientFundsException
   {
      if (balance < value)
      {
         throw new InsufficientFundsException(
               "Your available funds are £" + this.balance);
      }
      else
      {
         this.balance -= value;
      }
   }
   
   public String toString()
   {
      return "Account: " + this.accountNumber + ", balance: " + this.balance;
   }
}