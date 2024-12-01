/* Decompiler 227ms, total 463ms, lines 327 */
package com.ibm.toad.cfparse.instruction;

import java.util.HashMap;

import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.attributes.CodeAttrInfo.ExceptionInfo;
import com.ibm.toad.cfparse.utils.ByteArray;
import com.ibm.toad.utils.D.AssertionException;

public class ImmutableCodeSegment {
	private int d_numInstructions;
	private int[] d_instructions;
	private HashMap d_offsets;
	private byte[] d_code;

	public int getNumInstructions() {
		return this.d_numInstructions;
	}

	private int specArgSkip(byte[] var1, int var2, int var3) {
		int var4 = var3;
		int var5 = (4 - var3 % 4) % 4;
		int var8;
		int var9;
		switch (var2) {
		case 170:
			var3 += var5 + 4;
			int var6 = ByteArray.getIntAtOffset(var1, var3);
			var3 += 4;
			int var7 = ByteArray.getIntAtOffset(var1, var3);
			var3 += 4;

			for (var8 = var6; var8 <= var7; ++var8) {
				var3 += 4;
			}

			return var3 - var4;
		case 171:
			var3 += var5 + 4;
			var8 = ByteArray.getIntAtOffset(var1, var3);
			var3 += 4;

			for (var9 = 0; var9 < var8; ++var9) {
				var3 += 8;
			}

			return var3 - var4;
		case 196:
			var9 = ByteArray.getByteAtOffset(var1, var3);
			var3 += 3;
			if (var9 == 132) {
				var3 += 2;
			}
		}

		return var3 - var4;
	}

	public String toString() {
		StringBuffer var1 = new StringBuffer();

		for (int var2 = 0; var2 < this.d_numInstructions; ++var2) {
			int var3 = this.d_instructions[var2];
			int var4 = var2 == this.d_numInstructions - 1 ? this.d_code.length
					: this.d_instructions[var2 + 1];
			var1.append(var2 + "(" + this.getOffset(var2) + ") "
					+ this.toBytes(this.d_code, var3, var4) + "\t\t\t"
					+ this.InstrPrint(this.d_code, var3, var4) + "\n");
		}

		return var1.toString();
	}

	public ImmutableCodeSegment(byte[] var1) {
		this.d_code = var1;
		// D.assert(var1 != null, "Cannot create null ImmutableCodeSegment");
		this.readCode(this.d_code);
	}

	private void readCode(byte[] var1) {
		this.d_offsets = new HashMap();
		int[] var2 = new int[var1.length];
		int var3 = 0;

		int var4;
		for (var4 = 0; var3 < var1.length; ++var4) {
			var2[var4] = var3;
			this.d_offsets.put(Integer.valueOf(var3), Integer.valueOf(var4));
			int var5 = ByteArray.getByteAtOffset(var1, var3);
			++var3;
			JVMInstruction var6 = JVMInstruction.d_instrTable[var5];
			int var7 = var6.len();
			if (var7 == 0) {
				switch (var5) {
				case 170: // tableswitch = 170 (0xaa)
				case 171: // lookupswitch = 171 (0xab)
					var3 += this.specArgSkip(var1, var5, var3);
					break;
				// Yann 24/11/28: "New" JVM instruction since Java 7 
				case 186: // invokedynamic = 186 (0xba)
					var3 += this.specArgSkip(var1, var5, var3);
					break;
				case 196: // wide = 196 (0xc4)
					var3 += this.specArgSkip(var1, var5, var3);
					break;
				default:
					throw new AssertionException("Unknown special op code");
				}
			}
			else {
				String var8 = var6.operandTypes();
				int var9 = var8.length();

				for (int var10 = 0; var10 < var9; ++var10) {
					var3 += JVMInstruction.argSkip(var8.charAt(var10));
				}
			}
		}

		this.d_numInstructions = var4;
		this.d_instructions = new int[this.d_numInstructions];
		System.arraycopy(var2, 0, this.d_instructions, 0,
				this.d_numInstructions);
	}

	void sprintOperand(StringBuffer var1, int var2, char var3) {
		// boolean var4 = false;
		int var5;
		switch (var3) {
		case '0':
		case 'i':
			var5 = ByteArray.getByteAtOffset(this.d_code, var2);
			if (var5 > 127) {
				var5 -= 256;
			}

			var1.append("#" + var5);
			break;
		case 'A':
			var5 = ByteArray.getIntAtOffset(this.d_code, var2);
			var1.append(var2 + var5 - 1);
			break;
		case 'C':
			var5 = ByteArray.getShortAtOffset(this.d_code, var2);
			var1.append(var5);
			break;
		case 'I':
			var5 = ByteArray.getShortAtOffset(this.d_code, var2);
			if (var5 > 32767) {
				var5 -= 65536;
			}

			var1.append("#" + var5);
			break;
		case 'V':
			var5 = ByteArray.getShortAtOffset(this.d_code, var2);
			var1.append("v" + var5);
			break;
		case 'a':
			var5 = ByteArray.getSignedShortAtOffset(this.d_code, var2);
			var1.append(var2 + var5 - 1);
			break;
		case 'c':
			var5 = ByteArray.getByteAtOffset(this.d_code, var2);
			var1.append(var5);
			break;
		case 'l':
			var5 = ByteArray.getIntAtOffset(this.d_code, var2);
			var1.append("#" + var5);
			break;
		case 't':
			var5 = ByteArray.getByteAtOffset(this.d_code, var2);
			var1.append(JVMInstruction.arrayTypeCode2String(var5));
			break;
		case 'v':
			var5 = ByteArray.getByteAtOffset(this.d_code, var2);
			var1.append("v" + var5);
			break;
		default:
			var1.append("<unknown operand type: " + var3 + ">");
		}

	}

	public static CodeViewer getViewer() {
		return new CodeViewer() {
			// InstructionFactory d_if;

			public void setInstructionType(InstructionFactory var1) {
				// this.d_if = var1;
			}

			public String view(ConstantPool var1, byte[] var2,
					ExceptionInfo[] var3, int var4, String var5) {
				StringBuffer var6 = new StringBuffer();
				ImmutableCodeSegment var7 = new ImmutableCodeSegment(var2);
				int var8 = var7.getNumInstructions();

				int var9;
				for (var9 = 0; var9 < var8; ++var9) {
					int var10 = var7.getOffset(var9);
					int var11 = var9 == var8 - 1 ? var2.length
							: var7.getOffset(var9 + 1);
					var6.append(var9 + "(" + var7.getOffset(var9) + ")" + var5
							+ " " + var7.toBytes(var2, var10, var11) + "\t\t\t"
							+ var7.InstrPrint(var2, var10, var11) + "\n");
				}

				var6.append(var5 + "Exception Table:\n");
				if (var4 == 0) {
					var6.append(var5 + "  <none>\n");
				}
				else {
					for (var9 = 0; var9 < var4; ++var9) {
						var6.append(var5 + var3[var9] + "\n");
					}
				}

				return var6.toString();
			}
		};
	}

	public byte[] getInstruction(int var1) {
		if (var1 >= 0 && var1 < this.d_numInstructions) {
			int var2 = var1 == this.d_numInstructions - 1 ? this.d_code.length
					: this.d_instructions[var1 + 1];
			byte[] var3 = new byte[var2 - this.d_instructions[var1]];
			System.arraycopy(this.d_code, this.d_instructions[var1], var3, 0,
					var2 - this.d_instructions[var1]);
			return var3;
		}
		else {
			return null;
		}
	}

	String InstrPrint(byte[] var1, int var2, int var3) {
		int var5 = var1[var2] & 255;
		JVMInstruction var6 = JVMInstruction.d_instrTable[var5];
		StringBuffer var7 = new StringBuffer(var6.name() + " ");
		int var4;
		int var8;
		int var9;
		if (var6.len() == 0) {
			int var10;
			int var11;
			switch (var5) {
			case 170:
				var4 = var2 + (4 - var2 % 4) % 4;
				this.sprintOperand(var7, var4, 'A');
				var4 += 4;
				var7.append(" ");
				var8 = ByteArray.getIntAtOffset(var1, var4);
				this.sprintOperand(var7, var4, 'l');
				var4 += 4;
				var7.append("-");
				var9 = ByteArray.getIntAtOffset(var1, var4);
				this.sprintOperand(var7, var4, 'l');
				var4 += 4;
				var7.append(" ");

				for (var10 = var8; var10 <= var9; ++var10) {
					this.sprintOperand(var7, var4, 'A');
					var4 += 4;
					var7.append(" ");
				}

				return var7.toString();
			case 171:
				var4 = var2 + (4 - var2 % 4) % 4;
				this.sprintOperand(var7, var4, 'A');
				var4 += 4;
				var7.append(" ");
				var10 = ByteArray.getIntAtOffset(var1, var4);
				this.sprintOperand(var7, var4, 'l');
				var4 += 4;
				var7.append(" ");

				for (var11 = 0; var11 < var10; ++var11) {
					var7.append("(");
					this.sprintOperand(var7, var4, 'l');
					var4 += 4;
					var7.append(", ");
					this.sprintOperand(var7, var4, 'A');
					var4 += 4;
					var7.append(") ");
				}

				return var7.toString();
			case 196:
				var11 = ByteArray.getByteAtOffset(var1, var2 + 1);
				JVMInstruction var12 = JVMInstruction.d_instrTable[var11];
				var7.append(var12.name() + " ");
				this.sprintOperand(var7, var2 + 2, 'V');
				var7.append(" ");
				if (var11 == 132) {
					this.sprintOperand(var7, var2 + 4, 'I');
				}
				break;
			default:
				throw new AssertionException("unknown special op code");
			}
		}
		else {
			var4 = var2 + 1;
			var8 = var6.d_operandTypes.length();

			for (var9 = 0; var9 < var8; ++var9) {
				this.sprintOperand(var7, var4,
						var6.d_operandTypes.charAt(var9));
				var4 += JVMInstruction
						.argSkip(var6.d_operandTypes.charAt(var9));
				var7.append(" ");
			}
		}

		return var7.toString();
	}

	public int getInum(int var1) {
		Integer var2 = (Integer) this.d_offsets.get(Integer.valueOf(var1));
		// D.assert(var2 != null, "Illegal offset");
		return var2;
	}

	String toBytes(byte[] var1, int var2, int var3) {
		StringBuffer var4 = new StringBuffer();

		for (int var5 = var2; var5 < var3; ++var5) {
			String var6 = Integer.toHexString(var1[var5] & 255) + " ";
			if ((var1[var5] & 255) < 16) {
				var4.append("0");
			}

			var4.append(var6);
		}

		return var4.toString();
	}

	public int getOffset(int var1) {
		// D.assert(var1 >= 0 && var1 < this.d_numInstructions, "Illegal instruction number");
		return this.d_instructions[var1];
	}
}
