/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package padl.creator.classfile.relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import padl.creator.classfile.util.ExtendedMethodInfo;
import util.io.ProxyConsole;
import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.attributes.AttrInfoList;
import com.ibm.toad.cfparse.attributes.CodeAttrInfo;
import com.ibm.toad.cfparse.instruction.ImmutableCodeSegment;
import com.ibm.toad.cfparse.instruction.JVMConstants;
import com.ibm.toad.cfparse.utils.ByteArray;

public class DeepMethodInvocationAnalyzer {
	private static DeepMethodInvocationAnalyzer UniqueInstance;
	public static DeepMethodInvocationAnalyzer getUniqueInstance() {
		if (DeepMethodInvocationAnalyzer.UniqueInstance == null) {
			DeepMethodInvocationAnalyzer.UniqueInstance =
				new DeepMethodInvocationAnalyzer();
		}
		return DeepMethodInvocationAnalyzer.UniqueInstance;
	}
	private static final boolean DEBUG = false;
	private static final int PUSH = 1;
	private static final int POP = -1;
	private static final int PUSH_LOCAL_VAR = 2;
	private static final int PROCESSED = -100000;
	private static final int[][] OPCODE_ARITIES = {
			{ JVMConstants.INEG, JVMConstants.LNEG, JVMConstants.DNEG,
					JVMConstants.I2B, JVMConstants.I2S, JVMConstants.I2L,
					JVMConstants.I2F, JVMConstants.I2D, JVMConstants.L2I,
					JVMConstants.L2F, JVMConstants.L2D, JVMConstants.F2I,
					JVMConstants.F2L, JVMConstants.F2D, JVMConstants.D2I,
					JVMConstants.D2L, JVMConstants.D2F, JVMConstants.ISTORE,
					JVMConstants.ISTORE_0, JVMConstants.ISTORE_1,
					JVMConstants.ISTORE_2, JVMConstants.ISTORE_3,
					JVMConstants.ASTORE, JVMConstants.ASTORE_0,
					JVMConstants.ASTORE_1, JVMConstants.ASTORE_2,
					JVMConstants.ASTORE_3, JVMConstants.FSTORE,
					JVMConstants.FSTORE_0, JVMConstants.FSTORE_1,
					JVMConstants.FSTORE_2, JVMConstants.FSTORE_3,
					JVMConstants.LSTORE, JVMConstants.LSTORE_0,
					JVMConstants.LSTORE_1, JVMConstants.LSTORE_2,
					JVMConstants.LSTORE_3, JVMConstants.DSTORE,
					JVMConstants.DSTORE_0, JVMConstants.DSTORE_1,
					JVMConstants.DSTORE_2, JVMConstants.DSTORE_3,
					JVMConstants.ARRAYLENGTH, JVMConstants.IFNONNULL,
					JVMConstants.IFNULL, JVMConstants.IFEQ, JVMConstants.IFNE,
					JVMConstants.IFLT, JVMConstants.IFGE, JVMConstants.IFGT,
					JVMConstants.IFLE, JVMConstants.IINC, JVMConstants.IRETURN,
					JVMConstants.LRETURN, JVMConstants.FRETURN,
					JVMConstants.DRETURN, JVMConstants.ARETURN,
					JVMConstants.MONITORENTER, JVMConstants.MONITOREXIT,
					JVMConstants.GOTO, JVMConstants.GOTO_W,
					JVMConstants.LOOKUPSWITCH, JVMConstants.TABLESWITCH,
					JVMConstants.NEWARRAY, JVMConstants.PUTFIELD,
					JVMConstants.PUTSTATIC },
			{ JVMConstants.IADD, JVMConstants.LADD, JVMConstants.FADD,
					JVMConstants.DADD, JVMConstants.ISUB, JVMConstants.LSUB,
					JVMConstants.FSUB, JVMConstants.DSUB, JVMConstants.IMUL,
					JVMConstants.LMUL, JVMConstants.FMUL, JVMConstants.DMUL,
					JVMConstants.IDIV, JVMConstants.LDIV, JVMConstants.FDIV,
					JVMConstants.DDIV, JVMConstants.IREM, JVMConstants.LREM,
					JVMConstants.FREM, JVMConstants.DREM, JVMConstants.ISHL,
					JVMConstants.LSHL, JVMConstants.ISHR, JVMConstants.LSHR,
					JVMConstants.IUSHR, JVMConstants.LUSHR, JVMConstants.IAND,
					JVMConstants.LAND, JVMConstants.IOR, JVMConstants.LOR,
					JVMConstants.IXOR, JVMConstants.LXOR, JVMConstants.IALOAD,
					JVMConstants.LALOAD, JVMConstants.FALOAD,
					JVMConstants.DALOAD, JVMConstants.AALOAD,
					JVMConstants.BALOAD, JVMConstants.CALOAD,
					JVMConstants.SALOAD, JVMConstants.LCMP, JVMConstants.FCMPL,
					JVMConstants.DCMPL, JVMConstants.FCMPG, JVMConstants.DCMPG,
					JVMConstants.IF_ACMPNE, JVMConstants.IF_ACMPEQ,
					JVMConstants.IF_ICMPEQ, JVMConstants.IF_ICMPNE,
					JVMConstants.IF_ICMPLT, JVMConstants.IF_ICMPGE,
					JVMConstants.IF_ICMPGT, JVMConstants.IF_ICMPLE },
			{ JVMConstants.BASTORE, JVMConstants.CASTORE, JVMConstants.AASTORE,
					JVMConstants.SASTORE, JVMConstants.IASTORE,
					JVMConstants.LASTORE, JVMConstants.FASTORE,
					JVMConstants.DASTORE } };
	private static final int[] STORE_OPCODES =
		{ JVMConstants.ISTORE, JVMConstants.ISTORE_0, JVMConstants.ISTORE_1,
				JVMConstants.ISTORE_2, JVMConstants.ISTORE_3,
				JVMConstants.ASTORE, JVMConstants.ASTORE_0,
				JVMConstants.ASTORE_1, JVMConstants.ASTORE_2,
				JVMConstants.ASTORE_3, JVMConstants.FSTORE,
				JVMConstants.FSTORE_0, JVMConstants.FSTORE_1,
				JVMConstants.FSTORE_2, JVMConstants.FSTORE_3,
				JVMConstants.LSTORE, JVMConstants.LSTORE_0,
				JVMConstants.LSTORE_1, JVMConstants.LSTORE_2,
				JVMConstants.LSTORE_3, JVMConstants.DSTORE,
				JVMConstants.DSTORE_0, JVMConstants.DSTORE_1,
				JVMConstants.DSTORE_2, JVMConstants.DSTORE_3,
				JVMConstants.BASTORE, JVMConstants.CASTORE,
				JVMConstants.AASTORE, JVMConstants.SASTORE,
				JVMConstants.IASTORE, JVMConstants.LASTORE,
				JVMConstants.FASTORE, JVMConstants.DASTORE,
				JVMConstants.PUTFIELD, JVMConstants.PUTSTATIC };

	private DeepMethodInvocationAnalyzer() {
	}
	public List analyzeMethod(final ExtendedMethodInfo aMethodInfo) {
		final List listOfCouples = new ArrayList();
		final AttrInfoList al = aMethodInfo.getAttributes();
		final CodeAttrInfo cai = (CodeAttrInfo) al.get("Code");

		if (cai == null) {
			return listOfCouples;
		}

		// The rawcode is an array that contains every instruction of the method.
		// Every instruction size is one byte.
		final byte[] rawCode = cai.getCode();
		final ImmutableCodeSegment c = new ImmutableCodeSegment(rawCode);
		final int numInstructions = c.getNumInstructions();

		// Here we go for splitting in sets of JAVA instructions (I mean high level as int exx = 0;)
		int start = 0;
		int end = 0;

		// Start and end are two markers to delimit the block.
		for (int j = 0; j < numInstructions; j++) {
			// For an instruction, we have the offset where it begins.
			final int offset = c.getOffset(j);
			final int opCode = getOpCode(offset, rawCode);

			// A solution can be to considerate a field as a singleton (String and not String[]).
			// Then the field is added to the list a String type.					
			/*
				String nameField = getFieldIfExists(opCode, offset, rawCode, cf.getCP());			
				if (nameField != null) {
					listOfCouples.add(nameField);
				}
			*/
			// This operation allows to get the mnemonic operation code.
			switch (opCode) {
				case JVMConstants.INVOKESPECIAL :
					{
						final int nextOffset = c.getOffset(j + 1);
						final int nextOpCode =
							this.getOpCode(nextOffset, rawCode);
						if (this.isOpCodeStore(nextOpCode)) {
							end++;
							j++;
							listOfCouples.addAll(this.analyzeCodeSegment(
								aMethodInfo,
								c,
								rawCode,
								start,
								end));
							start = end + 1;
						}
						break;
					}
				case JVMConstants.INVOKEINTERFACE :
				case JVMConstants.INVOKESTATIC :
				case JVMConstants.INVOKEVIRTUAL :
					{
						// Case of a called method.
						final String nameAndType =
							this.getInfosAt2Bytes(
								offset + 1,
								rawCode,
								aMethodInfo.getDeclaringClassConstantPool())[1];
						final String returnType =
							nameAndType.substring(
								nameAndType.indexOf(".") + 1,
								nameAndType.indexOf(" ("));

						if (returnType.equals("void")) {
							// Case of a constructor :
							// the method <init> returns void and can be considered as an exception.
							// for example: new String((Integer.valueOf(10)).intValue())							
							listOfCouples.addAll(this.analyzeCodeSegment(
								aMethodInfo,
								c,
								rawCode,
								start,
								end));
							start = end + 1;
						}
						break;
					}
				case JVMConstants.DUP :
				case JVMConstants.DUP2 :
				case JVMConstants.DUP2_X1 :
				case JVMConstants.DUP2_X2 :
				case JVMConstants.DUP_X1 :
				case JVMConstants.DUP_X2 :
					{
						// Jump two bytes to avoid
						// instructions like STORExxx or PUTxxx
						// Instructions will be processed in the
						// algorithm for parsing expressions delimited by a ";"
						//						j += 1;
						//						end++;
						break;
					}

				//				case JVMConstants.PUTFIELD :
				//				case JVMConstants.PUTSTATIC :
				case JVMConstants.POP :
				case JVMConstants.RETURN :
				case JVMConstants.IRETURN :
				case JVMConstants.LRETURN :
				case JVMConstants.FRETURN :
				case JVMConstants.DRETURN :
				case JVMConstants.ARETURN :
				case JVMConstants.POP2 :
				case JVMConstants.INSTANCEOF :
				//				case JVMConstants.IFEQ:
				//				case JVMConstants.IFNE :
				//				case JVMConstants.IFLT :
				//				case JVMConstants.IFGE :
				//				case JVMConstants.IFGT :
				//				case JVMConstants.IFLE :
				//				case JVMConstants.IF_ACMPNE :
				//				case JVMConstants.IF_ACMPEQ :
				//				case JVMConstants.IF_ICMPLE :				
				//				case JVMConstants.IF_ICMPEQ :
				//				case JVMConstants.IF_ICMPNE :
				//				case JVMConstants.IF_ICMPLT :
				//				case JVMConstants.IF_ICMPGE :
				//				case JVMConstants.IF_ICMPGT :
				//				case JVMConstants.IFNONNULL :
				//				case JVMConstants.IFNULL :	
				//				case JVMConstants.GOTO:
				//				case JVMConstants.GOTO_W :			
				//				case JVMConstants.ISTORE :
				//				case JVMConstants.ISTORE_0 :
				//				case JVMConstants.ISTORE_1 :
				//				case JVMConstants.ISTORE_2 :
				//				case JVMConstants.ISTORE_3 :
				//				case JVMConstants.ASTORE :
				//				case JVMConstants.ASTORE_0 :
				//				case JVMConstants.ASTORE_1 :
				//				case JVMConstants.ASTORE_2 :
				//				case JVMConstants.ASTORE_3 :
				//				case JVMConstants.FSTORE :
				//				case JVMConstants.FSTORE_0 :
				//				case JVMConstants.FSTORE_1 :
				//				case JVMConstants.FSTORE_2 :
				//				case JVMConstants.FSTORE_3 :
				//				case JVMConstants.LSTORE :
				//				case JVMConstants.LSTORE_0 :
				//				case JVMConstants.LSTORE_1 :
				//				case JVMConstants.LSTORE_2 :
				//				case JVMConstants.LSTORE_3 :
				//				case JVMConstants.DSTORE :
				//				case JVMConstants.DSTORE_0 :
				//				case JVMConstants.DSTORE_1 :
				//				case JVMConstants.DSTORE_2 :
				//				case JVMConstants.DSTORE_3 :
				//				case JVMConstants.BASTORE :
				//				case JVMConstants.CASTORE :
				//				case JVMConstants.AASTORE :
				//				case JVMConstants.SASTORE :
				//				case JVMConstants.IASTORE :
				//				case JVMConstants.LASTORE :
				//				case JVMConstants.FASTORE :
				//				case JVMConstants.DASTORE :	
				//				case JVMConstants.IINC :
				//				case JVMConstants.SWAP:

					{
						listOfCouples.addAll(this.analyzeCodeSegment(
							aMethodInfo,
							c,
							rawCode,
							start,
							end));
						start = end + 1;
						break;
					}
				default :
					break;

			}
			end++;
		}
		return listOfCouples;
	}

	private boolean isOpCodeStore(final int opcode) {
		for (int i = 0; i < STORE_OPCODES.length; i++) {
			if (STORE_OPCODES[i] == opcode) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Analyzes a part of the code segment corresponding to the delimited blocks.
	 * These blocks are JAVA instructions like ine JAVA programs(ex: int exx = 0).
	 * The way to analyse the code segment is to use a mechanism like a stack machine.
	 * In one hand, push are considered like +1 and pop -1.
	 * When a push (aload_0, local variables, or call of a static method ),
	 * the offset and a marker are stored at the same place (en lieu et place),
	 * in two vectors.
	 * Then an index allows to do the link between the offset
	 * and the type of instruction (push or pop, +1 or -1).
	 *
	 * @param cf	a handle to the class file
	 * @param cai	its code attribute
	 * @param c		the code segment of the method
	 * @param start beginning of the block
	 * @param end	end of the block
	 */
	private List analyzeCodeSegment(
		final ExtendedMethodInfo aMethodInfo,
		final ImmutableCodeSegment codeSegment,
		final byte[] rawCode,
		final int start,
		final int end) {

		final List couples = new ArrayList();
		final List fieldsAndMi = new ArrayList();
		final List indices = new ArrayList();

		try {
			this.constructLists(
				fieldsAndMi,
				indices,
				start,
				end,
				rawCode,
				codeSegment);

			this.createCouples(
				aMethodInfo,
				fieldsAndMi,
				indices,
				rawCode,
				couples);
		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return couples;
	}

	/**
	 * Create couples with arguments.
	 * An argument, if it is not yet processed,
	 * is recorded as an invocation.
	 * But the associated method is null
	 * At the end, the argument is removed.
	 * 
	 * @param indice
	 * @param nbArgs
	 * @param fieldsAndMI
	 * @param indices
	 * @param rawCode
	 * @param cp
	 * @param cf
	 * @return couples of arguments and null methods
	 */
	private List popArgs(
		final ExtendedMethodInfo aMethodInfo,
		final int indice,
		final int nbArgs,
		final List fieldsAndMI,
		final List indices,
		final byte[] rawCode) {

		final int firstArgumentPosition = indice - nbArgs + 1;
		final List couplesOfArgs = new ArrayList();

		for (int i = 0; i < nbArgs; i++) {
			int offset =
				((Integer) indices.get(firstArgumentPosition)).intValue();
			if (offset != PROCESSED) {
				int opCodeAtOffset = getOpCode(offset, rawCode);

				if (opCodeAtOffset == JVMConstants.ALOAD_0) {
					couplesOfArgs.add(this.makeCoupleAfterALoad0(
						aMethodInfo,
						offset,
						rawCode));
				}
				if (opCodeAtOffset == JVMConstants.GETSTATIC) {
					couplesOfArgs.add(this.makeCoupleForStaticAttributes(
						aMethodInfo,
						offset,
						0,
						rawCode));
				}
				if (opCodeAtOffset == JVMConstants.INVOKESTATIC) {
					couplesOfArgs.add(this.makeCoupleForStaticAttributes(
						aMethodInfo,
						offset,
						1,
						rawCode));
				}
				if (opCodeAtOffset == JVMConstants.LDC
						|| opCodeAtOffset == JVMConstants.LDC2_W
						|| opCodeAtOffset == JVMConstants.LDC_W) {

					couplesOfArgs.add(this.makeCoupleForConstant(
						offset,
						rawCode,
						aMethodInfo.getDeclaringClassConstantPool()));
				}
			}

			// The argument was processed and has to be removed.
			indices.remove(firstArgumentPosition);
			fieldsAndMI.remove(firstArgumentPosition);
		}

		return couplesOfArgs;
	}

	private String[] makeCoupleForConstant(
		final int offset,
		final byte[] rawCode,
		final ConstantPool constantPool) {

		// TODO: Replace the string array with
		// instances of a specific class,
		// maybe, "Invocation"
		final String[] couple = new String[2];
		couple[0] = offset + ":" + getOpCode(offset, rawCode);
		return couple;
	}
	/**
	 * I have to test if aload_0 is followed by a getfield instruction.
	 * It means that a field is loaded. Otherwise, it is like an instruction this.foo().
	 */
	private String[] makeCoupleAfterALoad0(
		final ExtendedMethodInfo aMethodInfo,
		final int offsetALoad0,
		final byte[] rawCode) {

		final String[] couple = new String[2];
		int offsetGetfield = offsetALoad0 + 1;
		int opCodeAfterAload0 = this.getOpCode(offsetGetfield, rawCode);
		if (opCodeAfterAload0 == JVMConstants.GETFIELD) {
			// Farouk 2004/04/02: Field!
			// The case where a public attribute from another class accesses a
			// class field has not been considered.
			// A second opcode GETFIELD follows the first.  
			int step = 3;
			StringBuffer bufferTypeAndField = new StringBuffer();
			while ((opCodeAfterAload0 = this.getOpCode(offsetGetfield, rawCode)) == JVMConstants.GETFIELD) {
				//classAndField = this.getInfosAt(offsetGetfield + 1, rawCode, cp);				
				String[] classAndField =
					this.getInfosAt2Bytes(
						offsetGetfield + 1,
						rawCode,
						aMethodInfo.getDeclaringClassConstantPool());
				offsetGetfield += step;
				bufferTypeAndField.append(classAndField[0] + " "
						+ classAndField[1]);
				bufferTypeAndField.append("#");
				// # is a separator				
			}
			bufferTypeAndField.deleteCharAt(bufferTypeAndField.length() - 1);
			couple[0] = bufferTypeAndField.toString();
		}
		else {
			if (opCodeAfterAload0 == JVMConstants.DUP
					|| opCodeAfterAload0 == JVMConstants.DUP2
					|| opCodeAfterAload0 == JVMConstants.DUP2_X1
					|| opCodeAfterAload0 == JVMConstants.DUP2_X1
					|| opCodeAfterAload0 == JVMConstants.DUP_X1
					|| opCodeAfterAload0 == JVMConstants.DUP_X2) {
				String[] classAndField =
					getInfosAt2Bytes(
						offsetALoad0 + 3,
						rawCode,
						aMethodInfo.getDeclaringClassConstantPool());
				couple[0] = classAndField[0] + " " + classAndField[1];
			}
			else {
				couple[0] =
					String.valueOf(aMethodInfo.getDeclaringClassName())
							+ " this";
			}

		}
		return couple;
	}
	private String[] makeCoupleForStaticAttributes(
		final ExtendedMethodInfo aMethodInfo,
		final int offset,
		final int fieldOrMethod,
		final byte[] rawCode) {

		final String[] couple = new String[2];
		final String[] infos =
			getInfosAt2Bytes(
				offset + 1,
				rawCode,
				aMethodInfo.getDeclaringClassConstantPool());
		couple[fieldOrMethod] = infos[0] + " " + infos[1];

		return couple;
	}
	/**
	 * Creates a couple between the field calling and the method called.
	 * 
	 * @param fieldPosition
	 * @param invokePosition
	 * @param fieldsAndMi
	 * @param indices
	 * @param rawCode
	 * @param cp
	 * @param cf
	 * @return a couple of a variable and a method called
	 */
	private String[] makeCouple(
		final ExtendedMethodInfo aMethodInfo,
		final int fieldPosition,
		final int invokePosition,
		final List fieldsAndMi,
		final List indices,
		final byte[] rawCode) {

		String[] couple = new String[2];

		try {
			int fieldOffset = ((Integer) indices.get(fieldPosition)).intValue();

			if (fieldOffset != PROCESSED) {
				int opCode = getOpCode(fieldOffset, rawCode);
				if (opCode == JVMConstants.ALOAD_0) {
					couple =
						this.makeCoupleAfterALoad0(
							aMethodInfo,
							fieldOffset,
							rawCode);
				}
				else if (opCode == JVMConstants.GETSTATIC) {
					couple =
						makeCoupleForStaticAttributes(
							aMethodInfo,
							fieldOffset,
							0,
							rawCode);
				}
				else if (opCode == JVMConstants.INVOKESTATIC) {
					couple =
						this.makeCoupleForStaticAttributes(
							aMethodInfo,
							fieldOffset,
							1,
							rawCode);
					indices.set(fieldPosition, Integer.valueOf(PROCESSED));
					return couple;
				}
			}

			// Processing for the method.
			final int invokeOffset =
				((Integer) indices.get(invokePosition)).intValue();
			if (invokeOffset != PROCESSED) {
				if (this.getOpCode(invokeOffset, rawCode) == JVMConstants.INSTANCEOF) {
					couple[1] = "instanceof";
				}
				else {
					final String[] classAndMethod =
						this.getInfosAt2Bytes(
							invokeOffset + 1,
							rawCode,
							aMethodInfo.getDeclaringClassConstantPool());
					if (classAndMethod != null) {
						couple[1] = classAndMethod[0] + " " + classAndMethod[1];
					}
				}
			}

			// The field is marked as it was processed and the method is removed.
			indices.set(fieldPosition, Integer.valueOf(PROCESSED));
			fieldsAndMi.remove(invokePosition);
			indices.remove(invokePosition);
		}
		catch (final RuntimeException e) {
			if (DeepMethodInvocationAnalyzer.DEBUG) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}

		return couple;
	}

	/**
	 * Gets JAVA instructions at a given offset.
	 * The precised offset must be given exactly.
	 * If there are no instruction, a null value is returned.
	 * The first instruction is the class name and the second the field or method called.
	 * 
	 * @param offset
	 * @param rawCode
	 * @param cp
	 * @return JAVA instructions at the offset and considering the constant pool of the class file and and the raw code
	 */
	//	private String[] getInfosAt(
	//		int offset,
	//		byte[] rawCode,
	//		ConstantPool cp) {
	//		int num = rawCode[offset] & 0xFF;
	//		int[] idx = cp.get(num).getIndices();
	//		if (idx != null) {
	//			if (idx.length == 2) {
	//				String[] infos = new String[2];
	//				infos[0] = cp.getAsJava(idx[0]);
	//				infos[1] = cp.getAsJava(idx[1]);
	//				return infos;
	//			}
	//		}
	//		return null;
	//	}

	private String[] getInfosAt2Bytes(
		final int offset,
		final byte[] rawCode,
		final ConstantPool constantPool) {

		final int num = ByteArray.getShortAtOffset(rawCode, offset);
		final int[] idx = constantPool.get(num).getIndices();

		if (idx != null) {
			if (idx.length == 2) {
				final String[] infos = new String[2];
				infos[0] = constantPool.getAsJava(idx[0]);
				infos[1] = constantPool.getAsJava(idx[1]);
				return infos;
			}
		}

		return null;
	}
	/**
	 * Gets the opcode mnemonic at the offset in the raw code
	 *
	 * @param offset
	 * @param rawCode
	 * @return the opcode mnemonic at the offset in the raw code
	 */
	private int getOpCode(final int offset, final byte[] rawCode) {
		return (int) (rawCode[offset] & 0xff);
	}
	/**
	 * Counts the number of arguments of a method by analysing a method signature
	 *
	 * @param methodSignature
	 * @return the number of arguments of a method
	 */
	private int countArgs(final String methodSignature) {
		final int firstParenthesis = methodSignature.indexOf("(");
		if (firstParenthesis == -1) {
			// In case this is not a method.
			return 0;
		}
		final int lastParenthesis = methodSignature.indexOf(")");
		final StringTokenizer st =
			new StringTokenizer(methodSignature.substring(
				firstParenthesis + 1,
				lastParenthesis), ",");
		return st.countTokens();
	}
	private String[] makeCoupleForStaticMethod(
		final int methodOffset,
		final byte[] rawCode,
		final ConstantPool constantPool) {

		final int instructionOffset = methodOffset + 1;
		final String[] classAndStaticMethod =
			getInfosAt2Bytes(instructionOffset, rawCode, constantPool);
		final String[] couple = new String[2];
		couple[0] = null;
		couple[1] = classAndStaticMethod[0] + " " + classAndStaticMethod[1];

		return couple;
	}
	// Simple process for GETFIELD, PUTFIELD, GETSTATIC, PUTSTATIC	
	//	private String getFieldIfExists(int opCode, int offset, byte[] rawCode, ConstantPool cp) {
	//		if (opCode == JVMConstants.GETFIELD
	//			|| opCode == JVMConstants.PUTFIELD
	//			|| opCode == JVMConstants.GETSTATIC
	//			|| opCode == JVMConstants.PUTSTATIC) {
	//
	//			String[] infos = getInfosAt(offset + 2, rawCode, cp);
	//			return infos[0] + " " + infos[1];
	//		}
	//		return null;
	//	}
	private void constructLists(
		final List fieldsAndMi,
		final List indices,
		final int start,
		final int end,
		final byte[] rawCode,
		final ImmutableCodeSegment codeSegment) {

		for (int index = start; index <= end; index++) {
			final int offset = codeSegment.getOffset(index);
			final int opcode = this.getOpCode(offset, rawCode);

			switch (opcode) {
				case JVMConstants.NEW :
					{
						index =
							this.handleNew(
								fieldsAndMi,
								indices,
								index,
								rawCode,
								codeSegment,
								opcode,
								offset);
						break;
					}
				case JVMConstants.INVOKEINTERFACE :
				case JVMConstants.INVOKEVIRTUAL :
				case JVMConstants.INSTANCEOF :
					{
						this.handlePop(
							fieldsAndMi,
							indices,
							index,
							rawCode,
							codeSegment,
							opcode,
							offset);
						break;
					}

				case JVMConstants.DUP :
				case JVMConstants.DUP2 :
				case JVMConstants.DUP2_X1 :
				case JVMConstants.DUP2_X2 :
				case JVMConstants.DUP_X1 :
				case JVMConstants.DUP_X2 :
					{
						index =
							this.handleDup(
								fieldsAndMi,
								indices,
								index,
								rawCode,
								codeSegment,
								opcode,
								offset);
						break;
					}
				/**
				 * The DUP opcode can appear after ALOAD_0 but also all ALOAD and GETSTATIC opcodes.
				 * The process has ever been done with ALOAD_0. I extended the process for the other opcodes.
				 * The value pushed on the stack depends on the opcode. 
				 */

				case JVMConstants.ALOAD_0 :
				case JVMConstants.ALOAD :
				case JVMConstants.ALOAD_1 :
				case JVMConstants.ALOAD_2 :
				case JVMConstants.ALOAD_3 :
				case JVMConstants.GETSTATIC :
				case JVMConstants.GETSTATIC_QUICK :
				case JVMConstants.GETSTATIC2_QUICK :
					{
						index =
							this.handleALoad(
								fieldsAndMi,
								indices,
								index,
								rawCode,
								codeSegment,
								opcode,
								offset);
						break;
					}
				case JVMConstants.INVOKESTATIC :
				case JVMConstants.PUTFIELD :
				case JVMConstants.PUTSTATIC :
				case JVMConstants.INVOKESPECIAL :
					{
						this.handlePushClassField(
							fieldsAndMi,
							indices,
							index,
							rawCode,
							codeSegment,
							opcode,
							offset);
						break;
					}

				case JVMConstants.BIPUSH :
				case JVMConstants.SIPUSH :
				case JVMConstants.LDC :
				case JVMConstants.LDC_W :
				case JVMConstants.LDC2_W :

				case JVMConstants.ACONST_NULL :
				case JVMConstants.ILOAD :
				case JVMConstants.FLOAD :
				case JVMConstants.LLOAD :
				case JVMConstants.DLOAD :
				case JVMConstants.ILOAD_0 :
				case JVMConstants.ILOAD_1 :
				case JVMConstants.ILOAD_2 :
				case JVMConstants.ILOAD_3 :
				case JVMConstants.FLOAD_0 :
				case JVMConstants.FLOAD_1 :
				case JVMConstants.FLOAD_2 :
				case JVMConstants.FLOAD_3 :
				case JVMConstants.LLOAD_0 :
				case JVMConstants.LLOAD_1 :
				case JVMConstants.LLOAD_2 :
				case JVMConstants.LLOAD_3 :
				case JVMConstants.DLOAD_0 :
				case JVMConstants.DLOAD_1 :
				case JVMConstants.DLOAD_2 :
				case JVMConstants.DLOAD_3 :

				case JVMConstants.IALOAD :
				case JVMConstants.LALOAD :
				case JVMConstants.FALOAD :
				case JVMConstants.SALOAD :
				case JVMConstants.CALOAD :
				case JVMConstants.BALOAD :
				case JVMConstants.DALOAD :
				case JVMConstants.AALOAD :
				case JVMConstants.ICONST_0 :
				case JVMConstants.ICONST_1 :
				case JVMConstants.ICONST_2 :
				case JVMConstants.ICONST_3 :
				case JVMConstants.ICONST_4 :
				case JVMConstants.ICONST_5 :
				case JVMConstants.LCONST_0 :
				case JVMConstants.LCONST_1 :
				case JVMConstants.FCONST_0 :
				case JVMConstants.FCONST_1 :
				case JVMConstants.FCONST_2 :
				case JVMConstants.DCONST_0 :
				case JVMConstants.DCONST_1 :
				case JVMConstants.ICONST_M1 :

				case JVMConstants.ISTORE :
				case JVMConstants.FSTORE :
				case JVMConstants.LSTORE :
				case JVMConstants.DSTORE :
				case JVMConstants.ASTORE :
				case JVMConstants.ISTORE_0 :
				case JVMConstants.ISTORE_1 :
				case JVMConstants.ISTORE_2 :
				case JVMConstants.ISTORE_3 :
				case JVMConstants.FSTORE_0 :
				case JVMConstants.FSTORE_1 :
				case JVMConstants.FSTORE_2 :
				case JVMConstants.FSTORE_3 :
				case JVMConstants.LSTORE_0 :
				case JVMConstants.LSTORE_1 :
				case JVMConstants.LSTORE_2 :
				case JVMConstants.LSTORE_3 :
				case JVMConstants.DSTORE_0 :
				case JVMConstants.DSTORE_1 :
				case JVMConstants.DSTORE_2 :
				case JVMConstants.DSTORE_3 :
				case JVMConstants.ASTORE_0 :
				case JVMConstants.ASTORE_1 :
				case JVMConstants.ASTORE_2 :
				case JVMConstants.ASTORE_3 :
				case JVMConstants.BASTORE :
				case JVMConstants.CASTORE :
				case JVMConstants.AASTORE :
				case JVMConstants.SASTORE :
				case JVMConstants.IASTORE :
				case JVMConstants.LASTORE :
				case JVMConstants.FASTORE :
				case JVMConstants.DASTORE :

				case JVMConstants.IADD :
				case JVMConstants.LADD :
				case JVMConstants.FADD :
				case JVMConstants.DADD :
				case JVMConstants.ISUB :
				case JVMConstants.LSUB :
				case JVMConstants.FSUB :
				case JVMConstants.DSUB :
				case JVMConstants.IMUL :
				case JVMConstants.LMUL :
				case JVMConstants.FMUL :
				case JVMConstants.DMUL :
				case JVMConstants.IDIV :
				case JVMConstants.LDIV :
				case JVMConstants.FDIV :
				case JVMConstants.DDIV :
				case JVMConstants.IREM :
				case JVMConstants.LREM :
				case JVMConstants.FREM :
				case JVMConstants.DREM :
				case JVMConstants.INEG :
				case JVMConstants.LNEG :
				case JVMConstants.FNEG :
				case JVMConstants.DNEG :
				case JVMConstants.ISHL :
				case JVMConstants.LSHL :
				case JVMConstants.ISHR :
				case JVMConstants.LSHR :
				case JVMConstants.IUSHR :
				case JVMConstants.LUSHR :
				case JVMConstants.IAND :
				case JVMConstants.LAND :
				case JVMConstants.IOR :
				case JVMConstants.LOR :
				case JVMConstants.IXOR :
				case JVMConstants.LXOR :
				case JVMConstants.IINC :
				case JVMConstants.I2L :
				case JVMConstants.I2F :
				case JVMConstants.I2D :
				case JVMConstants.L2I :
				case JVMConstants.L2F :
				case JVMConstants.L2D :
				case JVMConstants.F2I :
				case JVMConstants.F2L :
				case JVMConstants.F2D :
				case JVMConstants.D2I :
				case JVMConstants.D2L :
				case JVMConstants.D2F :
				case JVMConstants.I2B :
				case JVMConstants.I2C :
				case JVMConstants.I2S :
				case JVMConstants.LCMP :
				case JVMConstants.FCMPL :
				case JVMConstants.FCMPG :
				case JVMConstants.DCMPL :
				case JVMConstants.DCMPG :
				case JVMConstants.IF_ACMPNE :
				case JVMConstants.IF_ACMPEQ :
				case JVMConstants.IFNONNULL :
				case JVMConstants.IFNULL :
				case JVMConstants.ARRAYLENGTH :
				case JVMConstants.IFEQ :
				case JVMConstants.IFNE :
				case JVMConstants.IFLT :
				case JVMConstants.IFGE :
				case JVMConstants.IFGT :
				case JVMConstants.IFLE :
				case JVMConstants.IF_ICMPEQ :
				case JVMConstants.IF_ICMPNE :
				case JVMConstants.IF_ICMPLT :
				case JVMConstants.IF_ICMPGE :
				case JVMConstants.IF_ICMPGT :
				case JVMConstants.IF_ICMPLE :

				case JVMConstants.IRETURN :
				case JVMConstants.LRETURN :
				case JVMConstants.FRETURN :
				case JVMConstants.DRETURN :
				case JVMConstants.ARETURN :
				case JVMConstants.MONITORENTER :
				case JVMConstants.MONITOREXIT :
				case JVMConstants.GOTO :
				case JVMConstants.GOTO_W :
				case JVMConstants.LOOKUPSWITCH :
				case JVMConstants.TABLESWITCH :
				case JVMConstants.NEWARRAY :
					{
						this.handleLocalVariable(
							fieldsAndMi,
							indices,
							index,
							rawCode,
							codeSegment,
							opcode,
							offset);
						break;
					}

				default :
					break;
			}
		}
	}
	private int handleNew(
		final List fieldsAndMi,
		final List indices,
		final int start,
		final byte[] rawCode,
		final ImmutableCodeSegment codeSegment,
		final int opCode,
		final int offset) {

		if (this.getOpCode(codeSegment.getOffset(start + 1), rawCode) == JVMConstants.DUP) {

			return start + 1;
		}
		return start;
	}
	private void handlePop(
		final List fieldsAndMi,
		final List indices,
		final int start,
		final byte[] rawCode,
		final ImmutableCodeSegment codeSegment,
		final int opCode,
		final int offset) {

		fieldsAndMi.add(Integer.valueOf(POP));
		indices.add(Integer.valueOf(offset));
	}
	/*
	 * Handle the cases of assignments, such as:
	 *     String.valueOf(a = b);
	 * or:
	 *     p = Integer.valueOf();
	 */
	private int handleDup(
		final List fieldsAndMi,
		final List indices,
		final int start,
		final byte[] rawCode,
		final ImmutableCodeSegment codeSegment,
		final int opCode,
		final int offset) {

		final int newStart = start + 1;
		final int newOffset = codeSegment.getOffset(newStart);
		final int nextOpcode = this.getOpCode(newOffset, rawCode);

		if (nextOpcode != JVMConstants.GETFIELD) {
			final int chosenValue =
				(nextOpcode == JVMConstants.PUTFIELD
						|| nextOpcode == JVMConstants.PUTSTATIC
						|| nextOpcode == JVMConstants.ALOAD_0 || nextOpcode == JVMConstants.GETSTATIC) ? PUSH
						: PUSH_LOCAL_VAR;
			fieldsAndMi.add(Integer.valueOf(chosenValue));
			indices.add(Integer.valueOf(newOffset));
		}

		return newStart;
	}
	private int handleALoad(
		final List fieldsAndMi,
		final List indices,
		final int start,
		final byte[] rawCode,
		final ImmutableCodeSegment codeSegment,
		final int opCode,
		final int offset) {

		final int nextOpCode =
			this.getOpCode(codeSegment.getOffset(start + 1), rawCode);

		/**
		 * Farouk - 2004/04/14
		 * Fast fix (or rustine)
		 * The opcode DUP duplicates on the top of the stack the aload_0 value.
		 * While the opcode putfield needs to be combined with an ALOAD_0, aload_0 is combined with another opcode (like GETFIELD)
		 * So we need to duplicate a value. But this will be marked as ever processed.
		 */
		if (nextOpCode == JVMConstants.DUP) {
			fieldsAndMi.add(Integer.valueOf(PUSH));
			indices.add(Integer.valueOf(PROCESSED));
		}
		if (opCode == JVMConstants.ALOAD_0 || opCode == JVMConstants.GETSTATIC
				|| opCode == JVMConstants.GETSTATIC_QUICK
				|| opCode == JVMConstants.GETSTATIC2_QUICK) {
			fieldsAndMi.add(Integer.valueOf(PUSH));
		}
		else {
			fieldsAndMi.add(Integer.valueOf(PUSH_LOCAL_VAR));
		}

		indices.add(Integer.valueOf(offset));

		return start;
	}
	private void handlePushClassField(
		final List fieldsAndMi,
		final List indices,
		final int start,
		final byte[] rawCode,
		final ImmutableCodeSegment codeSegment,
		final int opCode,
		final int offset) {

		fieldsAndMi.add(Integer.valueOf(PUSH));
		indices.add(Integer.valueOf(offset));
	}
	private void handleLocalVariable(
		final List fieldsAndMi,
		final List indices,
		final int start,
		final byte[] rawCode,
		final ImmutableCodeSegment codeSegment,
		final int opCode,
		final int offset) {

		fieldsAndMi.add(Integer.valueOf(PUSH_LOCAL_VAR));
		indices.add(Integer.valueOf(offset));
	}
	private void createCouples(
		final ExtendedMethodInfo aMethodInfo,
		final List fieldsAndMi,
		final List indices,
		final byte[] rawCode,
		final List couples) {

		try {
			int index = 0;

			while (fieldsAndMi.size() > 0
					&& ((Integer) (indices.get(indices.size() - 1))).intValue() != PROCESSED) {

				// I am looking for static invocation methods without parameters,
				if (fieldsAndMi.size() == 1) {
					index =
						this.createCouplesForStaticInvocations(
							aMethodInfo,
							fieldsAndMi,
							indices,
							rawCode,
							couples,
							index);
				}

				// I am looking for making couples (push, pop).
				if (fieldsAndMi.size() > 1) {
					index =
						this.createCouplesForInvocations(
							aMethodInfo,
							fieldsAndMi,
							indices,
							rawCode,
							couples,
							index);
				}
			}
		}
		catch (final RuntimeException e) {
			if (DeepMethodInvocationAnalyzer.DEBUG) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
	}
	private int createCouplesForInvocations(
		final ExtendedMethodInfo aMethodInfo,
		final List fieldsAndMi,
		final List indices,
		final byte[] rawCode,
		final List couples,
		final int index) {

		int i = index;
		final int isPushAtI = ((Integer) fieldsAndMi.get(i)).intValue();
		final int isPushAtIPlusOne =
			((Integer) fieldsAndMi.get(i + 1)).intValue();
		final int offsetPositionZero = ((Integer) indices.get(0)).intValue();

		if (offsetPositionZero != PROCESSED) {
			if (this.getOpCode(offsetPositionZero, rawCode) == JVMConstants.INVOKESTATIC) {

				final String[] couple =
					this.makeCoupleForStaticAttributes(
						aMethodInfo,
						offsetPositionZero,
						1,
						rawCode);
				indices.set(0, Integer.valueOf(PROCESSED));
				couples.add(couple);
			}
		}

		// I am looking for called methods.
		if ((isPushAtI == PUSH && isPushAtIPlusOne == POP)
				|| (isPushAtI == PUSH_LOCAL_VAR && isPushAtIPlusOne == POP)) {

			i =
				this.processPushPop(
					aMethodInfo,
					fieldsAndMi,
					indices,
					rawCode,
					couples,
					i);
		}
		else if ((isPushAtI == PUSH && isPushAtIPlusOne == PUSH)
				|| (isPushAtI == PUSH_LOCAL_VAR && isPushAtIPlusOne == PUSH)) {

			i =
				this.processPushPush(
					aMethodInfo,
					fieldsAndMi,
					indices,
					rawCode,
					couples,
					i);
		}
		else if ((isPushAtI == PUSH_LOCAL_VAR && isPushAtIPlusOne == PUSH_LOCAL_VAR)
				|| (isPushAtI == PUSH && isPushAtIPlusOne == PUSH_LOCAL_VAR)) {

			i =
				this.processPushPushLocalVar(
					aMethodInfo,
					fieldsAndMi,
					indices,
					rawCode,
					couples,
					i);
		}
		else {
			i++;
		}

		return i;
	}
	private int processPushPushLocalVar(
		final ExtendedMethodInfo aMethodInfo,
		final List fieldsAndMi,
		final List indices,
		final byte[] rawCode,
		final List couples,
		int index) {

		final int operatorOffset =
			((Integer) indices.get(index + 1)).intValue();
		final int opCodeOperator = this.getOpCode(operatorOffset, rawCode);

		final int nbOperatorArgs = this.countOperatorArguments(opCodeOperator);

		// Unary or binary operators.
		if (nbOperatorArgs == 1 || nbOperatorArgs == 2 || nbOperatorArgs == 3) {

			index =
				this.processOperator(
					aMethodInfo,
					rawCode,
					indices,
					fieldsAndMi,
					couples,
					index,
					nbOperatorArgs,
					operatorOffset,
					opCodeOperator);
		}
		else {
			index++;
		}

		return index;
	}
	private int processPushPush(
		final ExtendedMethodInfo aMethodInfo,
		final List fieldsAndMi,
		final List indices,
		final byte[] rawCode,
		final List couples,
		int index) {

		// Get the offset of the method invocation.
		final int methodOffset = ((Integer) indices.get(index + 1)).intValue();

		/**
		 * Farouk - 2004/04/14, 13:42
		 * The condition if below is a fix for the fix corrected in the method handleAload0.
		 * When a DUP appears, the ALOAD_0 is duplicated. But it is important to not count
		 * two times the same value (like this.x += w where x appears 2 times after its value has been duplicated) 
		 * To duplicate a value and not consider it, it is marked as PROCESSED.
		 * Here, we test if the offset is PROCESSED. If it is, the value is removed.
		 * The index does not change.
		 * All JUNIT tests are OK.
		 */
		if (methodOffset == PROCESSED) {
			indices.remove(index + 1);
			fieldsAndMi.remove(index + 1);
			return index;
		}
		final int opCode = getOpCode(methodOffset, rawCode);

		// A couple has been found (push,push) = (1,1).
		// I am looking for called static methods
		if (opCode == JVMConstants.INVOKESTATIC) {
			index =
				this.processPushPushStatic(
					aMethodInfo,
					rawCode,
					indices,
					fieldsAndMi,
					couples,
					index,
					methodOffset);
		}
		else {
			if (opCode == JVMConstants.INVOKESPECIAL) {
				index =
					this.processPushPushSpecial(
						aMethodInfo,
						rawCode,
						indices,
						fieldsAndMi,
						couples,
						index,
						methodOffset);
			}
			else {
				if (this.isOpCodeStore(opCode)) {
					index =
						this.processStoreOperator(
							aMethodInfo,
							rawCode,
							indices,
							fieldsAndMi,
							couples,
							index,
							methodOffset,
							opCode);
				}
				else {
					index++;
				}
			}
		}

		return index;
	}
	private int processPushPop(
		final ExtendedMethodInfo aMethodInfo,
		final List fieldsAndMi,
		final List indices,
		final byte[] rawCode,
		final List couples,
		int index) {

		// Get the offset of the method invocation.
		final int methodOffset = ((Integer) indices.get(index + 1)).intValue();

		// Get a Java form of the code.
		String[] infosOfMethod;
		if (getOpCode(methodOffset, rawCode) != JVMConstants.INSTANCEOF) {
			infosOfMethod =
				this.getInfosAt2Bytes(
					methodOffset + 1,
					rawCode,
					aMethodInfo.getDeclaringClassConstantPool());
		}
		else {
			infosOfMethod = new String[2];
			infosOfMethod[0] = "";
			infosOfMethod[1] = "instanceof()";
		}

		// infosOfMethod[1] contains the method signature.
		int position = index;
		final int nbArgs = this.countArgs(infosOfMethod[1]);
		if (nbArgs > 0) {
			// Arguments must be popped from the vectors.
			// They are transformed in couples,
			// but the method invocation is null.
			couples.addAll(this.popArgs(
				aMethodInfo,
				index,
				nbArgs,
				fieldsAndMi,
				indices,
				rawCode));
			position = index - nbArgs;
		}

		// We restart at the same place where we found the field,
		// even if there is no argument.
		// The index hasn't changed because, 
		// just before, the variable position was affected by i.
		index = position;

		// The real couple (callee, calling) is made here.
		couples.add(this.makeCouple(
			aMethodInfo,
			index,
			index + 1,
			fieldsAndMi,
			indices,
			rawCode));

		return index;
	}
	private int createCouplesForStaticInvocations(
		final ExtendedMethodInfo aMethodInfo,
		final List fieldsAndMi,
		final List indices,
		final byte[] rawCode,
		final List couples,
		final int index) {

		int i = index;
		final int offset = ((Integer) indices.get(i)).intValue();

		if (offset != PROCESSED) {
			final int opCode = this.getOpCode(offset, rawCode);

			// Condition for instructions like Math.random()					
			if (opCode == JVMConstants.INVOKESTATIC) {
				couples.add(this.makeCoupleForStaticMethod(
					offset,
					rawCode,
					aMethodInfo.getDeclaringClassConstantPool()));
			}
			fieldsAndMi.remove(i);
			indices.remove(i);
		}

		return i;
	}
	private int processOperator(
		final ExtendedMethodInfo aMethodInfo,
		final byte[] rawCode,
		final List indices,
		final List fieldsAndMi,
		final List couples,
		final int index,
		final int nbOperatorArgs,
		final int operatorOffset,
		final int opCode) {

		// infosOfMethod[1] contains the method signature.
		int position = index;
		if (nbOperatorArgs > 0) {
			couples.addAll(this.popOperands(
				aMethodInfo,
				index,
				nbOperatorArgs,
				fieldsAndMi,
				indices,
				rawCode));
			position = index - nbOperatorArgs;
		}
		position = position + 1;

		// Make a couple for the operator.
		final String[] couple = new String[2];
		couple[1] = operatorOffset + ":" + opCode;
		couples.add(couple);

		indices.set(position, Integer.valueOf(PROCESSED));

		return position;
	}
	private List popOperands(
		final ExtendedMethodInfo aMethodInfo,
		final int indice,
		final int nbArgs,
		final List fieldsAndMi,
		final List indices,
		final byte[] rawCode) {

		final int firstArgumentPosition = indice - nbArgs + 1;
		final List couplesOfArgs = new ArrayList();

		// The loop indice i is not used within the loop because
		// the offset is computed from a changing list of indices.
		// (See indices.remove(firstArgumentPosition) at the end of the loop.)
		for (int i = 0; i < nbArgs; i++) {
			int offset =
				((Integer) indices.get(firstArgumentPosition)).intValue();
			if (offset != PROCESSED) {
				int opCodeAtOffset = getOpCode(offset, rawCode);
				String[] couple = new String[2];
				if (opCodeAtOffset == JVMConstants.GETSTATIC) {
					couple =
						this.makeCoupleForStaticAttributes(
							aMethodInfo,
							offset,
							0,
							rawCode);
				}
				else {
					if (opCodeAtOffset == JVMConstants.ALOAD_0) {
						couple =
							this.makeCoupleAfterALoad0(
								aMethodInfo,
								offset,
								rawCode);
					}
					else {
						couple[0] = offset + ":" + opCodeAtOffset;
					}
				}
				couplesOfArgs.add(couple);
			}
			fieldsAndMi.remove(firstArgumentPosition);
			indices.remove(firstArgumentPosition);
		}

		return couplesOfArgs;
	}
	private int countOperatorArguments(final int opCode) {
		for (int i = 0; i < OPCODE_ARITIES.length; i++) {
			for (int j = 0; j < OPCODE_ARITIES[i].length; j++) {
				if (opCode == OPCODE_ARITIES[i][j]) {
					return i + 1;
				}
			}
		}
		return -1;
	}
	// Warning! Duplicate methods processPushPushStatic() and processPushPushSpecial()
	private int processPushPushStatic(
		final ExtendedMethodInfo aMethodInfo,
		final byte[] rawCode,
		final List indices,
		final List fieldsAndMi,
		final List couples,
		final int index,
		final int methodOffset) {

		// Get a Java form of the code.
		final String[] infosOfMethod =
			this.getInfosAt2Bytes(
				methodOffset + 1,
				rawCode,
				aMethodInfo.getDeclaringClassConstantPool());

		// infosOfMethod[1] contains the method signature.
		int position = index;
		final int nbArgs = countArgs(infosOfMethod[1]);

		if (nbArgs > 0) {
			couples.addAll(this.popArgs(
				aMethodInfo,
				index,
				nbArgs,
				fieldsAndMi,
				indices,
				rawCode));
			position = index - nbArgs;
		}
		position = position + 1;

		// Make a couple for the static method.
		couples.add(this.makeCoupleForStaticMethod(
			methodOffset,
			rawCode,
			aMethodInfo.getDeclaringClassConstantPool()));

		indices.set(position, Integer.valueOf(PROCESSED));

		return position;
	}
	// Warning! Duplicate methods processPushPushStatic() and processPushPushSpecial()
	private int processPushPushSpecial(
		final ExtendedMethodInfo aMethodInfo,
		final byte[] rawCode,
		final List indices,
		final List fieldsAndMi,
		final List couples,
		final int index,
		final int methodOffset) {

		// Get a Java form of the code.
		final String[] infosOfMethod =
			this.getInfosAt2Bytes(
				methodOffset + 1,
				rawCode,
				aMethodInfo.getDeclaringClassConstantPool());

		// infosOfMethod[1] contains the method signature.
		int position = index;
		final int nbArgs = countArgs(infosOfMethod[1]);

		if (nbArgs > 0) {
			couples.addAll(this.popArgs(
				aMethodInfo,
				index,
				nbArgs,
				fieldsAndMi,
				indices,
				rawCode));
			position = index - nbArgs;
		}
		position = position + 1;

		// Make a couple for the static method.
		couples.add(this.makeCoupleForStaticMethod(
			methodOffset,
			rawCode,
			aMethodInfo.getDeclaringClassConstantPool()));

		indices.set(position, Integer.valueOf(PROCESSED));

		return position;
	}
	//	private String [] makeCoupleForSpecialMethod(int methodOffset, byte[] rawCode, ConstantPool cp) {
	//		int instructionOffset = methodOffset + 2;
	//		String[] methodInfos = this.getInfosAt(instructionOffset, rawCode, cp);
	//		String[] couple = new String[2];
	//		couple[0] = null;
	//		couple[1] = methodInfos[0] + " " + methodInfos[1];
	//		return couple;
	//	}
	private int processStoreOperator(
		final ExtendedMethodInfo aMethodInfo,
		final byte[] rawCode,
		final List indices,
		final List fieldsAndMi,
		final List couples,
		final int index,
		final int operatorOffset,
		final int opCode) {

		if (opCode == JVMConstants.PUTFIELD || opCode == JVMConstants.PUTSTATIC) {

			couples.addAll(this.popOperands(
				aMethodInfo,
				index,
				this.countOperatorArguments(opCode),
				fieldsAndMi,
				indices,
				rawCode));

			final String[] couple = new String[2];
			final String[] infos =
				this.getInfosAt2Bytes(
					operatorOffset + 1,
					rawCode,
					aMethodInfo.getDeclaringClassConstantPool());
			couple[0] = infos[0] + " " + infos[1];
			couple[1] = "=";
			couples.add(couple);
			indices.set(index, Integer.valueOf(PROCESSED));
			//case of PUTFIELD: before PUTFIELD, there is ALOAD_0.
			if (opCode == JVMConstants.PUTFIELD) {
				indices.remove(index - 1);
				fieldsAndMi.remove(index - 1);
				return index - 1;
			}
			return index;
		}
		else
			return this.processOperator(
				aMethodInfo,
				rawCode,
				indices,
				fieldsAndMi,
				couples,
				index,
				1,
				operatorOffset,
				opCode);

	}
}
