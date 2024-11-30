/* Decompiler 50ms, total 289ms, lines 22 */
package com.ibm.toad.cfparse;

import java.io.DataOutputStream;
import java.io.IOException;

public abstract class ConstantPoolEntry {
   abstract void write(DataOutputStream var1) throws IOException;

   void setIndices(int[] var1) {
   }

   public int[] getIndices() {
      return null;
   }

   public abstract String getAsString();

   public String getAsJava() {
      return this.getAsString();
   }
}
