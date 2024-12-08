/* Decompiler 76ms, total 270ms, lines 138 */
package com.ibm.toad.cfparse.attributes;

import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.utils.Access;
import com.ibm.toad.utils.D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;

public final class InnerClassesAttrInfo extends AttrInfo {
   private int d_numInnerClasses;
   private int[] d_innerClasses;

   public boolean isAnonymous(int var1) {
      if (var1 >= 0 && var1 < this.d_numInnerClasses) {
         int var2 = this.d_innerClasses[var1 * 4 + 2];
         return var2 == 0;
      } else {
         return false;
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer(this.sindent() + "Attribute: " + super.d_cp.getAsString(super.d_idxName) + ": \n");

      for(int var2 = 0; var2 < this.d_innerClasses.length; var1.append("\n")) {
         int var3 = this.d_innerClasses[var2++];
         int var4 = this.d_innerClasses[var2++];
         int var5 = this.d_innerClasses[var2++];
         int var6 = this.d_innerClasses[var2++];
         String var7 = "";
         if (var5 == 0) {
            var7 = "(Anonymous) ";
         }

         var1.append(this.sindent() + var7 + Access.getAsString(var6) + super.d_cp.getAsJava(var3));
         if (var4 != 0) {
            var1.append(". Member of Class " + super.d_cp.getAsJava(var4));
         }

         if (var5 != 0) {
            var1.append(". Class name " + super.d_cp.getAsJava(var5));
         }
      }

      return var1.toString();
   }

   InnerClassesAttrInfo(ConstantPool var1, int var2, int var3) {
      super(var1, var2, var3);
   }

	// Yann: changed from protected to public
   public void read(DataInputStream var1) throws IOException {
      super.d_len = var1.readInt();
      this.d_numInnerClasses = var1.readShort();
      // D.assert(super.d_len == 2 + this.d_numInnerClasses * 8, "d_len != 2 + (d_numInnerClasses * 8)\n" + super.d_len + " != 2 + (" + this.d_numInnerClasses + "* 8)\n");
      this.d_innerClasses = new int[this.d_numInnerClasses * 4];

      for(int var2 = 0; var2 < this.d_innerClasses.length; ++var2) {
         this.d_innerClasses[var2] = var1.readShort();
      }

   }

   protected void sort(int[] var1) {
      super.sort(var1);

      for(int var2 = 0; var2 < this.d_innerClasses.length; ++var2) {
         if (var2 % 4 != 3) {
            this.d_innerClasses[var2] = var1[this.d_innerClasses[var2]];
         }
      }

   }

   public int getNumInnerClasses() {
      return this.d_numInnerClasses;
   }

   protected void write(DataOutputStream var1) throws IOException {
      var1.writeShort(super.d_idxName);
      var1.writeInt(super.d_len);
      var1.writeShort(this.d_numInnerClasses);

      for(int var2 = 0; var2 < this.d_innerClasses.length; ++var2) {
         var1.writeShort(this.d_innerClasses[var2]);
      }

   }

   protected BitSet uses() {
      BitSet var1 = super.uses();

      for(int var2 = 0; var2 < this.d_innerClasses.length; ++var2) {
         if (var2 % 4 != 3) {
            var1.set(this.d_innerClasses[var2]);
         }
      }

      return var1;
   }

   public String getOuterClassName(int var1) {
      if (var1 >= 0 && var1 < this.d_numInnerClasses) {
         int var2 = this.d_innerClasses[var1 * 4 + 1];
         return var2 == 0 ? "" : super.d_cp.getAsJava(var2);
      } else {
         return "";
      }
   }

   public String getFullClassName(int var1) {
      if (var1 >= 0 && var1 < this.d_numInnerClasses) {
         int var2 = this.d_innerClasses[var1 * 4];
         int var3 = this.d_innerClasses[var1 * 4 + 2];
         int var4 = this.d_innerClasses[var1 * 4 + 3];
         String var5 = "";
         if (var3 == 0) {
            var5 = "(Anonymous) ";
         }

         return var5 + Access.getAsString(var4) + super.d_cp.getAsJava(var2);
      } else {
         return "";
      }
   }

   public String getClassName(int var1) {
      if (var1 >= 0 && var1 < this.d_numInnerClasses) {
         int var2 = this.d_innerClasses[var1 * 4 + 2];
         return var2 == 0 ? "" : super.d_cp.getAsJava(var2);
      } else {
         return "";
      }
   }
}
