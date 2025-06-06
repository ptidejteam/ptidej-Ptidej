/* Decompiler 389ms, total 601ms, lines 393 */
package com.ibm.toad.cfparse.utils;

import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.ConstantPool.Utf8Entry;

public final class CPUtils {
   public static final String[] d_typeNames = new String[]{"null0", "CONSTANT_Utf8", "null1", "CONSTANT_Integer", "CONSTANT_Float", "CONSTANT_Long", "CONSTANT_Double", "CONSTANT_Class", "CONSTANT_String", "CONSTANT_Fieldref", "CONSTANT_Methodref", "CONSTANT_InterfaceMethodref", "CONSTANT_NameAndType"};

   public static String java2method(String var0) {
      var0 = var0.trim();
      int var1 = var0.indexOf(" ");
      if (var1 == -1) {
         return null;
      } else {
         String var2 = var0.substring(0, var1).trim();
         int var3 = var0.indexOf("(", var1 + 1);
         if (var3 == -1) {
            return null;
         } else {
            String var4 = var0.substring(var1 + 1, var3).trim();
            if (var4.indexOf(" ") != -1) {
               return null;
            } else {
               String var5 = null;
               if (var4.indexOf(".") != -1) {
                  var5 = var4.substring(0, var4.lastIndexOf("."));
                  var4 = var4.substring(var4.lastIndexOf(".") + 1, var4.length());
               }

               StringBuffer var6 = new StringBuffer();
               if (var5 != null) {
                  var6.append(dots2slashes(var5) + " ");
               }

               var6.append(var4 + " (");
               int var7 = var0.indexOf(")", var3 + 1);
               if (var7 == -1) {
                  return null;
               } else {
                  String var8 = var0.substring(var3 + 1, var7).trim();

                  for(int var9 = var8.indexOf(","); var9 != -1; var9 = var8.indexOf(",")) {
                     String var10 = var8.substring(0, var9).trim();
                     java2descriptor(var10, var6);
                     var8 = var8.substring(var9 + 1, var8.length()).trim();
                  }

                  java2descriptor(var8, var6);
                  var6.append(")");
                  java2descriptor(var2, var6);
                  return var6.toString();
               }
            }
         }
      }
   }

   public static int countArgs(ConstantPool var0, int var1) {
      int var2 = var0.getType(var1);
      if (var2 != 10 && var2 != 11 && var2 != 9) {
         return 0;
      } else {
         Utf8Entry var3 = (Utf8Entry)var0.get(var0.get(var0.get(var1).getIndices()[1]).getIndices()[1]);
         return descriptor2words(var3.getAsString());
      }
   }

   public static int countRet(ConstantPool var0, int var1) {
      int var2 = var0.getType(var1);
      if (var2 != 10) {
         return 0;
      } else {
         String var3 = ((Utf8Entry)var0.get(var0.get(var0.get(var1).getIndices()[1]).getIndices()[1])).getAsString();
         switch(var3.charAt(var3.indexOf(41) + 1)) {
         case 'D':
         case 'J':
            return 2;
         case 'V':
            return 0;
         default:
            return 1;
         }
      }
   }

   CPUtils() {
   }

   public static String internal2java(String var0) {
      if (var0.charAt(0) == '(') {
         return method2java(var0);
      } else {
         StringBuffer var1 = new StringBuffer();
         descriptor2java(var0, 0, var1);
         String varString =  var1.toString();
         return varString;
      }
   }

   public static String[] getMethodParams(String var0) {
      byte var1 = 0;
      if (var0.charAt(var1) != '(') {
       
         return null;
      } else {
         int var2 = 0;
         StringBuffer var3 = new StringBuffer();

         int var8;
         for(var8 = var1 + 1; var8 < var0.length(); ++var2) {
            if (var0.charAt(var8) == ')') {
               ++var8;
               break;
            }

            if (var8 > 1) {
               var3.append(" ");
            }

            var8 = descriptor2java(var0, var8, var3);
         }

         String var4 = var3.toString();
         String[] var5 = new String[var2];
         var8 = 0;
         int var6 = 0;

         for(int var7 = var4.indexOf(" "); var7 != -1; var7 = var4.indexOf(" ", var6)) {
            var5[var8++] = var4.substring(var6, var7);
            var6 = var7 + 1;
         }

         var5[var8++] = var4.substring(var6, var4.length());
         return var5;
      }
   }

   public static String nameForType(int var0) {
      return var0 >= 0 && var0 < d_typeNames.length ? d_typeNames[var0] : null;
   }

   public static void java2descriptor(String var0, StringBuffer var1) {
      if (!var0.equals("")) {
         String var2 = null;
         String var3 = null;
         int var4 = var0.indexOf(" ");
         if (var4 != -1) {
            var3 = var0.substring(var4 + 1, var0.length()).trim();
            var2 = var0.substring(0, var4).trim();
         } else {
            var2 = var0.trim();
         }

         String var6 = null;
         int var5;
         if ((var5 = var2.indexOf("[")) != -1) {
            var6 = var2.substring(var5 + 1, var2.length());
            var2 = var2.substring(0, var5);
         }

         int var7 = 0;
         int var8;
         if (var6 != null) {
            for(var8 = 0; var8 < var6.length(); ++var8) {
               if (var6.charAt(var8) == ']') {
                  ++var7;
               }
            }
         }

         if (var3 != null) {
            for(var8 = 0; var8 < var3.length(); ++var8) {
               if (var3.charAt(var8) == ']') {
                  ++var7;
               }
            }
         }

         while(var7 > 0) {
            var1.append("[");
            --var7;
         }

         if (var2.equals("byte")) {
            var1.append("B");
         } else if (var2.equals("char")) {
            var1.append("C");
         } else if (var2.equals("double")) {
            var1.append("D");
         } else if (var2.equals("float")) {
            var1.append("F");
         } else if (var2.equals("int")) {
            var1.append("I");
         } else if (var2.equals("long")) {
            var1.append("J");
         } else if (var2.equals("short")) {
            var1.append("S");
         } else if (var2.equals("boolean")) {
            var1.append("Z");
         } else if (var2.equals("void")) {
            var1.append("V");
         } else {
            var1.append("L" + dots2slashes(var2) + ";");
         }

      }
   }

   public static String method2java(String var0) {
      byte var1 = 0;
      if (var0.charAt(var1) != '(') {
         System.out.println("Error: couldn't translate <" + var0 + "> to Java representation");
         return var0;
      } else {
         StringBuffer var2 = new StringBuffer("(");

         int var4;
         for(var4 = var1 + 1; var4 < var0.length(); var4 = descriptor2java(var0, var4, var2)) {
            if (var0.charAt(var4) == ')') {
               var2.append(")");
               ++var4;
               break;
            }

            if (var4 > 1) {
               var2.append(", ");
            }
         }

         StringBuffer var3 = new StringBuffer();
         descriptor2java(var0, var4, var3);
         return var3.toString() + " " + var2.toString();
      }
   }

   public static String slashes2dots(String var0) {
      return var0.replace('/', '.');
   }

   public static String dots2slashes(String var0) {
      return var0 == null ? null : var0.replace('.', '/');
   }

   public static int countWords(ConstantPool var0, int var1) {
      int var2 = var0.getType(var1);
      if (var2 != 10 && var2 != 11 && var2 != 9) {
         return 0;
      } else {
         Utf8Entry var3 = (Utf8Entry)var0.get(var0.get(var0.get(var1).getIndices()[1]).getIndices()[1]);
         return countRet(var0, var1) - descriptor2words(var3.getAsString());
      }
   }

   public static int descriptor2words(String var0) {
      int var2 = 0;
      String var1;
      if (var0.charAt(0) == '(') {
         var1 = var0.substring(1, var0.indexOf(41));
      } else {
         var1 = var0;
      }

      int var3 = 0;

      while(true) {
         while(var3 < var1.length()) {
            switch(var1.charAt(var3)) {
            case 'D':
            case 'J':
               var2 += 2;
               ++var3;
               continue;
            case 'L':
               break;
            case '[':
               while(var1.charAt(var3) == '[') {
                  ++var3;
               }

               if (var1.charAt(var3) != 'L') {
                  ++var2;
                  ++var3;
                  continue;
               }
               break;
            default:
               ++var3;
               ++var2;
               continue;
            }

            ++var2;

            while(var1.charAt(var3) != ';') {
               ++var3;
            }

            ++var3;
         }

         return var2;
      }
   }

   public static String java2internal(String var0) {
      if (var0.indexOf("(") != -1) {
         return java2method(var0);
      } else {
         StringBuffer var1 = new StringBuffer();
         java2descriptor(var0, var1);
         return var1.toString();
      }
   }

   public static int descriptor2java(String var0, int var1, StringBuffer var2) {
      String var3;
      for(var3 = ""; var0.charAt(var1) == '['; ++var1) {
         var3 = var3 + "[]";
      }
    
      label37:
      switch(var0.charAt(var1)) {
      case 'B':
         var2.append("byte");
         break;
      case 'C':
         var2.append("char");
         break;
      case 'D':
         var2.append("double");
         break;
      case 'F':
         var2.append("float");
         break;
      case 'I':
         var2.append("int");
         break;
      case 'J':
         var2.append("long");
         break;
      case 'L':
         ++var1;

         while(true) {
            if (var1 >= var0.length()) {
               break label37;
            }

            if (var0.charAt(var1) == '/') {
               var2.append('.');
            } else {
               if (var0.charAt(var1) == ';') {
                  break label37;
               }

               var2.append(var0.charAt(var1));
            }

            ++var1;
         }
      case 'S':
         var2.append("short");
         break;
      case 'V':
         var2.append("void");
         break;
      case 'Z':
         var2.append("boolean");
         break;
      default:
        
         return var0.length();
      }

      var2.append(var3);
      ++var1;
      return var1;
   }

   public static int typeForName(String var0) {
      if (var0 == null) {
         return -1;
      } else {
         for(int var1 = 0; var1 < d_typeNames.length; ++var1) {
            if (var0.equals(d_typeNames[var1])) {
               return var1;
            }
         }

         return -1;
      }
   }
}
