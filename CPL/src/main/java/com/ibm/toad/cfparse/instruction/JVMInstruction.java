/* Decompiler 798ms, total 953ms, lines 325 */
package com.ibm.toad.cfparse.instruction;

public class JVMInstruction {
   protected String d_name;
   protected int d_opCode;
   protected int d_len;
   protected String d_operandTypes;
   public static JVMInstruction[] d_instrTable = new JVMInstruction[256];
   public static final int MAX_OPCODE = 255;

   public JVMInstruction() {
   }

   JVMInstruction(String var1, int var2, int var3, String var4) {
      this.d_name = var1;
      this.d_opCode = var2;
      this.d_len = var3;
      this.d_operandTypes = var4;
   }

   JVMInstruction(int var1) {
      JVMInstruction var2 = d_instrTable[var1 & 255];
      this.d_name = var2.d_name;
      this.d_opCode = var2.d_opCode;
      this.d_len = var2.d_len;
      this.d_operandTypes = var2.d_operandTypes;
   }

   static int String2arrayTypeCode(String var0) {
      if (var0.equals("T_BOOLEAN")) {
         return 4;
      } else if (var0.equals("T_CHAR")) {
         return 5;
      } else if (var0.equals("T_FLOAT")) {
         return 6;
      } else if (var0.equals("T_DOUBLE")) {
         return 7;
      } else if (var0.equals("T_BYTE")) {
         return 8;
      } else if (var0.equals("T_SHORT")) {
         return 9;
      } else if (var0.equals("T_INT")) {
         return 10;
      } else {
         return var0.equals("T_LONG") ? 11 : -1;
      }
   }

   static String arrayTypeCode2String(int var0) {
      switch(var0) {
      case 4:
         return "T_BOOLEAN";
      case 5:
         return "T_CHAR";
      case 6:
         return "T_FLOAT";
      case 7:
         return "T_DOUBLE";
      case 8:
         return "T_BYTE";
      case 9:
         return "T_SHORT";
      case 10:
         return "T_INT";
      case 11:
         return "T_LONG";
      default:
         return "<unknown type code: " + var0 + ">";
      }
   }

   public int opCode() {
      return this.d_opCode;
   }

   public int len() {
      return this.d_len;
   }

   static {
      d_instrTable[0] = new JVMInstruction("nop", 0, 1, "");
      d_instrTable[1] = new JVMInstruction("aconst_null", 1, 1, "");
      d_instrTable[2] = new JVMInstruction("iconst_m1", 2, 1, "");
      d_instrTable[3] = new JVMInstruction("iconst_0", 3, 1, "");
      d_instrTable[4] = new JVMInstruction("iconst_1", 4, 1, "");
      d_instrTable[5] = new JVMInstruction("iconst_2", 5, 1, "");
      d_instrTable[6] = new JVMInstruction("iconst_3", 6, 1, "");
      d_instrTable[7] = new JVMInstruction("iconst_4", 7, 1, "");
      d_instrTable[8] = new JVMInstruction("iconst_5", 8, 1, "");
      d_instrTable[9] = new JVMInstruction("lconst_0", 9, 1, "");
      d_instrTable[10] = new JVMInstruction("lconst_1", 10, 1, "");
      d_instrTable[11] = new JVMInstruction("fconst_0", 11, 1, "");
      d_instrTable[12] = new JVMInstruction("fconst_1", 12, 1, "");
      d_instrTable[13] = new JVMInstruction("fconst_2", 13, 1, "");
      d_instrTable[14] = new JVMInstruction("dconst_0", 14, 1, "");
      d_instrTable[15] = new JVMInstruction("dconst_1", 15, 1, "");
      d_instrTable[16] = new JVMInstruction("bipush", 16, 2, "i");
      d_instrTable[17] = new JVMInstruction("sipush", 17, 3, "I");
      d_instrTable[18] = new JVMInstruction("ldc", 18, 2, "c");
      d_instrTable[19] = new JVMInstruction("ldc_w", 19, 3, "C");
      d_instrTable[20] = new JVMInstruction("ldc2_w", 20, 3, "C");
      d_instrTable[21] = new JVMInstruction("iload", 21, 2, "v");
      d_instrTable[22] = new JVMInstruction("lload", 22, 2, "v");
      d_instrTable[23] = new JVMInstruction("fload", 23, 2, "v");
      d_instrTable[24] = new JVMInstruction("dload", 24, 2, "v");
      d_instrTable[25] = new JVMInstruction("aload", 25, 2, "v");
      d_instrTable[26] = new JVMInstruction("iload_0", 26, 1, "");
      d_instrTable[27] = new JVMInstruction("iload_1", 27, 1, "");
      d_instrTable[28] = new JVMInstruction("iload_2", 28, 1, "");
      d_instrTable[29] = new JVMInstruction("iload_3", 29, 1, "");
      d_instrTable[30] = new JVMInstruction("lload_0", 30, 1, "");
      d_instrTable[31] = new JVMInstruction("lload_1", 31, 1, "");
      d_instrTable[32] = new JVMInstruction("lload_2", 32, 1, "");
      d_instrTable[33] = new JVMInstruction("lload_3", 33, 1, "");
      d_instrTable[34] = new JVMInstruction("fload_0", 34, 1, "");
      d_instrTable[35] = new JVMInstruction("fload_1", 35, 1, "");
      d_instrTable[36] = new JVMInstruction("fload_2", 36, 1, "");
      d_instrTable[37] = new JVMInstruction("fload_3", 37, 1, "");
      d_instrTable[38] = new JVMInstruction("dload_0", 38, 1, "");
      d_instrTable[39] = new JVMInstruction("dload_1", 39, 1, "");
      d_instrTable[40] = new JVMInstruction("dload_2", 40, 1, "");
      d_instrTable[41] = new JVMInstruction("dload_3", 41, 1, "");
      d_instrTable[42] = new JVMInstruction("aload_0", 42, 1, "");
      d_instrTable[43] = new JVMInstruction("aload_1", 43, 1, "");
      d_instrTable[44] = new JVMInstruction("aload_2", 44, 1, "");
      d_instrTable[45] = new JVMInstruction("aload_3", 45, 1, "");
      d_instrTable[46] = new JVMInstruction("iaload", 46, 1, "");
      d_instrTable[47] = new JVMInstruction("laload", 47, 1, "");
      d_instrTable[48] = new JVMInstruction("faload", 48, 1, "");
      d_instrTable[49] = new JVMInstruction("daload", 49, 1, "");
      d_instrTable[50] = new JVMInstruction("aaload", 50, 1, "");
      d_instrTable[51] = new JVMInstruction("baload", 51, 1, "");
      d_instrTable[52] = new JVMInstruction("caload", 52, 1, "");
      d_instrTable[53] = new JVMInstruction("saload", 53, 1, "");
      d_instrTable[54] = new JVMInstruction("istore", 54, 2, "v");
      d_instrTable[55] = new JVMInstruction("lstore", 55, 2, "v");
      d_instrTable[56] = new JVMInstruction("fstore", 56, 2, "v");
      d_instrTable[57] = new JVMInstruction("dstore", 57, 2, "v");
      d_instrTable[58] = new JVMInstruction("astore", 58, 2, "v");
      d_instrTable[59] = new JVMInstruction("istore_0", 59, 1, "");
      d_instrTable[60] = new JVMInstruction("istore_1", 60, 1, "");
      d_instrTable[61] = new JVMInstruction("istore_2", 61, 1, "");
      d_instrTable[62] = new JVMInstruction("istore_3", 62, 1, "");
      d_instrTable[63] = new JVMInstruction("lstore_0", 63, 1, "");
      d_instrTable[64] = new JVMInstruction("lstore_1", 64, 1, "");
      d_instrTable[65] = new JVMInstruction("lstore_2", 65, 1, "");
      d_instrTable[66] = new JVMInstruction("lstore_3", 66, 1, "");
      d_instrTable[67] = new JVMInstruction("fstore_0", 67, 1, "");
      d_instrTable[68] = new JVMInstruction("fstore_1", 68, 1, "");
      d_instrTable[69] = new JVMInstruction("fstore_2", 69, 1, "");
      d_instrTable[70] = new JVMInstruction("fstore_3", 70, 1, "");
      d_instrTable[71] = new JVMInstruction("dstore_0", 71, 1, "");
      d_instrTable[72] = new JVMInstruction("dstore_1", 72, 1, "");
      d_instrTable[73] = new JVMInstruction("dstore_2", 73, 1, "");
      d_instrTable[74] = new JVMInstruction("dstore_3", 74, 1, "");
      d_instrTable[75] = new JVMInstruction("astore_0", 75, 1, "");
      d_instrTable[76] = new JVMInstruction("astore_1", 76, 1, "");
      d_instrTable[77] = new JVMInstruction("astore_2", 77, 1, "");
      d_instrTable[78] = new JVMInstruction("astore_3", 78, 1, "");
      d_instrTable[79] = new JVMInstruction("iastore", 79, 1, "");
      d_instrTable[80] = new JVMInstruction("lastore", 80, 1, "");
      d_instrTable[81] = new JVMInstruction("fastore", 81, 1, "");
      d_instrTable[82] = new JVMInstruction("dastore", 82, 1, "");
      d_instrTable[83] = new JVMInstruction("aastore", 83, 1, "");
      d_instrTable[84] = new JVMInstruction("bastore", 84, 1, "");
      d_instrTable[85] = new JVMInstruction("castore", 85, 1, "");
      d_instrTable[86] = new JVMInstruction("sastore", 86, 1, "");
      d_instrTable[87] = new JVMInstruction("pop", 87, 1, "");
      d_instrTable[88] = new JVMInstruction("pop2", 88, 1, "");
      d_instrTable[89] = new JVMInstruction("dup", 89, 1, "");
      d_instrTable[90] = new JVMInstruction("dup_x1", 90, 1, "");
      d_instrTable[91] = new JVMInstruction("dup_x2", 91, 1, "");
      d_instrTable[92] = new JVMInstruction("dup2", 92, 1, "");
      d_instrTable[93] = new JVMInstruction("dup2_x1", 93, 1, "");
      d_instrTable[94] = new JVMInstruction("dup2_x2", 94, 1, "");
      d_instrTable[95] = new JVMInstruction("swap", 95, 1, "");
      d_instrTable[96] = new JVMInstruction("iadd", 96, 1, "");
      d_instrTable[97] = new JVMInstruction("ladd", 97, 1, "");
      d_instrTable[98] = new JVMInstruction("fadd", 98, 1, "");
      d_instrTable[99] = new JVMInstruction("dadd", 99, 1, "");
      d_instrTable[100] = new JVMInstruction("isub", 100, 1, "");
      d_instrTable[101] = new JVMInstruction("lsub", 101, 1, "");
      d_instrTable[102] = new JVMInstruction("fsub", 102, 1, "");
      d_instrTable[103] = new JVMInstruction("dsub", 103, 1, "");
      d_instrTable[104] = new JVMInstruction("imul", 104, 1, "");
      d_instrTable[105] = new JVMInstruction("lmul", 105, 1, "");
      d_instrTable[106] = new JVMInstruction("fmul", 106, 1, "");
      d_instrTable[107] = new JVMInstruction("dmul", 107, 1, "");
      d_instrTable[108] = new JVMInstruction("idiv", 108, 1, "");
      d_instrTable[109] = new JVMInstruction("ldiv", 109, 1, "");
      d_instrTable[110] = new JVMInstruction("fdiv", 110, 1, "");
      d_instrTable[111] = new JVMInstruction("ddiv", 111, 1, "");
      d_instrTable[112] = new JVMInstruction("irem", 112, 1, "");
      d_instrTable[113] = new JVMInstruction("lrem", 113, 1, "");
      d_instrTable[114] = new JVMInstruction("frem", 114, 1, "");
      d_instrTable[115] = new JVMInstruction("drem", 115, 1, "");
      d_instrTable[116] = new JVMInstruction("ineg", 116, 1, "");
      d_instrTable[117] = new JVMInstruction("lneg", 117, 1, "");
      d_instrTable[118] = new JVMInstruction("fneg", 118, 1, "");
      d_instrTable[119] = new JVMInstruction("dneg", 119, 1, "");
      d_instrTable[120] = new JVMInstruction("ishl", 120, 1, "");
      d_instrTable[121] = new JVMInstruction("lshl", 121, 1, "");
      d_instrTable[122] = new JVMInstruction("ishr", 122, 1, "");
      d_instrTable[123] = new JVMInstruction("lshr", 123, 1, "");
      d_instrTable[124] = new JVMInstruction("iushr", 124, 1, "");
      d_instrTable[125] = new JVMInstruction("lushr", 125, 1, "");
      d_instrTable[126] = new JVMInstruction("iand", 126, 1, "");
      d_instrTable[127] = new JVMInstruction("land", 127, 1, "");
      d_instrTable[128] = new JVMInstruction("ior", 128, 1, "");
      d_instrTable[129] = new JVMInstruction("lor", 129, 1, "");
      d_instrTable[130] = new JVMInstruction("ixor", 130, 1, "");
      d_instrTable[131] = new JVMInstruction("lxor", 131, 1, "");
      d_instrTable[132] = new JVMInstruction("iinc", 132, 3, "vi");
      d_instrTable[133] = new JVMInstruction("i2l", 133, 1, "");
      d_instrTable[134] = new JVMInstruction("i2f", 134, 1, "");
      d_instrTable[135] = new JVMInstruction("i2d", 135, 1, "");
      d_instrTable[136] = new JVMInstruction("l2i", 136, 1, "");
      d_instrTable[137] = new JVMInstruction("l2f", 137, 1, "");
      d_instrTable[138] = new JVMInstruction("l2d", 138, 1, "");
      d_instrTable[139] = new JVMInstruction("f2i", 139, 1, "");
      d_instrTable[140] = new JVMInstruction("f2l", 140, 1, "");
      d_instrTable[141] = new JVMInstruction("f2d", 141, 1, "");
      d_instrTable[142] = new JVMInstruction("d2i", 142, 1, "");
      d_instrTable[143] = new JVMInstruction("d2l", 143, 1, "");
      d_instrTable[144] = new JVMInstruction("d2f", 144, 1, "");
      d_instrTable[145] = new JVMInstruction("i2b", 145, 1, "");
      d_instrTable[146] = new JVMInstruction("i2c", 146, 1, "");
      d_instrTable[147] = new JVMInstruction("i2s", 147, 1, "");
      d_instrTable[148] = new JVMInstruction("lcmp", 148, 1, "");
      d_instrTable[149] = new JVMInstruction("fcmpl", 149, 1, "");
      d_instrTable[150] = new JVMInstruction("fcmpg", 150, 1, "");
      d_instrTable[151] = new JVMInstruction("dcmpl", 151, 1, "");
      d_instrTable[152] = new JVMInstruction("dcmpg", 152, 1, "");
      d_instrTable[153] = new JVMInstruction("ifeq", 153, 3, "a");
      d_instrTable[154] = new JVMInstruction("ifne", 154, 3, "a");
      d_instrTable[155] = new JVMInstruction("iflt", 155, 3, "a");
      d_instrTable[156] = new JVMInstruction("ifge", 156, 3, "a");
      d_instrTable[157] = new JVMInstruction("ifgt", 157, 3, "a");
      d_instrTable[158] = new JVMInstruction("ifle", 158, 3, "a");
      d_instrTable[159] = new JVMInstruction("if_icmpeq", 159, 3, "a");
      d_instrTable[160] = new JVMInstruction("if_icmpne", 160, 3, "a");
      d_instrTable[161] = new JVMInstruction("if_icmplt", 161, 3, "a");
      d_instrTable[162] = new JVMInstruction("if_icmpge", 162, 3, "a");
      d_instrTable[163] = new JVMInstruction("if_icmpgt", 163, 3, "a");
      d_instrTable[164] = new JVMInstruction("if_icmple", 164, 3, "a");
      d_instrTable[165] = new JVMInstruction("if_acmpeq", 165, 3, "a");
      d_instrTable[166] = new JVMInstruction("if_acmpne", 166, 3, "a");
      d_instrTable[167] = new JVMInstruction("goto", 167, 3, "a");
      d_instrTable[168] = new JVMInstruction("jsr", 168, 3, "a");
      d_instrTable[169] = new JVMInstruction("ret", 169, 2, "v");
      d_instrTable[170] = new JVMInstruction("tableswitch", 170, 0, "special");
      d_instrTable[171] = new JVMInstruction("lookupswitch", 171, 0, "special");
      d_instrTable[172] = new JVMInstruction("ireturn", 172, 1, "");
      d_instrTable[173] = new JVMInstruction("lreturn", 173, 1, "");
      d_instrTable[174] = new JVMInstruction("freturn", 174, 1, "");
      d_instrTable[175] = new JVMInstruction("dreturn", 175, 1, "");
      d_instrTable[176] = new JVMInstruction("areturn", 176, 1, "");
      d_instrTable[177] = new JVMInstruction("return", 177, 1, "");
      d_instrTable[178] = new JVMInstruction("getstatic", 178, 3, "C");
      d_instrTable[179] = new JVMInstruction("putstatic", 179, 3, "C");
      d_instrTable[180] = new JVMInstruction("getfield", 180, 3, "C");
      d_instrTable[181] = new JVMInstruction("putfield", 181, 3, "C");
      d_instrTable[182] = new JVMInstruction("invokevirtual", 182, 3, "C");
      d_instrTable[183] = new JVMInstruction("invokespecial", 183, 3, "C");
      d_instrTable[184] = new JVMInstruction("invokestatic", 184, 3, "C");
      d_instrTable[185] = new JVMInstruction("invokeinterface", 185, 5, "Ci0");
      // Yann 24/11/28: "New" JVM instruction since Java 7 
      d_instrTable[186] = new JVMInstruction("invokedynamic", 186, 5, "C00");
      d_instrTable[187] = new JVMInstruction("new", 187, 3, "C");
      d_instrTable[188] = new JVMInstruction("newarray", 188, 2, "t");
      d_instrTable[189] = new JVMInstruction("anewarray", 189, 3, "C");
      d_instrTable[190] = new JVMInstruction("arraylength", 190, 1, "");
      d_instrTable[191] = new JVMInstruction("athrow", 191, 1, "");
      d_instrTable[192] = new JVMInstruction("checkcast", 192, 3, "C");
      d_instrTable[193] = new JVMInstruction("instanceof", 193, 3, "C");
      d_instrTable[194] = new JVMInstruction("monitorenter", 194, 1, "");
      d_instrTable[195] = new JVMInstruction("monitorexit", 195, 1, "");
      d_instrTable[196] = new JVMInstruction("wide", 196, 0, "special");
      d_instrTable[197] = new JVMInstruction("multianewarray", 197, 4, "Ci");
      d_instrTable[198] = new JVMInstruction("ifnull", 198, 3, "a");
      d_instrTable[199] = new JVMInstruction("ifnonnull", 199, 3, "a");
      d_instrTable[200] = new JVMInstruction("goto_w", 200, 5, "A");
      d_instrTable[201] = new JVMInstruction("jsr_w", 201, 5, "A");
      d_instrTable[254] = new JVMInstruction("line_number", 254, 3, "I");
   }

   public String operandTypes() {
      return this.d_operandTypes;
   }

   static int argSkip(char var0) {
      switch(var0) {
      case '0':
      case 'c':
      case 'i':
      case 't':
      case 'v':
         return 1;
      case 'A':
      case 'l':
         return 4;
      case 'C':
      case 'I':
      case 'V':
      case 'a':
         return 2;
      default:
         return -1;
      }
   }

   public static int isOpcode(String var0) {
      for(int var1 = 0; var1 < d_instrTable.length; ++var1) {
         if (d_instrTable[var1] != null && var0.equals(d_instrTable[var1].d_name)) {
            return var1;
         }
      }

      return -1;
   }

   public String name() {
      return this.d_name;
   }
}
