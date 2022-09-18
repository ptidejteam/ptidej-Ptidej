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
package modec.tool.instrumentation;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import org.apache.bcel.Constants;
import org.apache.bcel.Repository;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.GotoInstruction;
import org.apache.bcel.generic.INVOKESPECIAL;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.InstructionTargeter;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.PUSH;
import org.apache.bcel.generic.ReturnInstruction;
import org.apache.bcel.generic.Type;
import org.apache.bcel.verifier.VerificationResult;
import org.apache.bcel.verifier.Verifier;
import org.apache.bcel.verifier.VerifierFactory;

/**
 * @author Janice Ka-Yee Ng
 * @author Yann
 * @since  2007/03/01
 */
public class MoDeCInstrumentationTool {
	// Determine the unique # of a Loop in the trace
	private static int countLoops = 0;
	private static final boolean LOG_LOOPS = false;
	private static final String LOG_TO_FILE_CLASS_NAME =
		"modec.tool.instrumentation.LogToFile";
	private static final boolean VERIFY_BYTECODE = false;
	private static final boolean DEBUG = false;

	/**
	 * Insert 'Constructor entry' at the 1st instruction.
	 * Insert 'Constructor exit' before each RETURN instruction.
	 * 
	 * @param logFilename filename in which to write the log data
	 * @param cgen class initialized with the existing information for
	 * 		  the class being modified
	 * @param method the method to be modified
	 */
	private static void addLogToConstructors(
		final String logFilename,
		final ClassGen cgen,
		final Method method) {

		final ConstantPoolGen cp = cgen.getConstantPool();
		final MethodGen mgen = new MethodGen(method, cgen.getClassName(), cp);
		final InstructionList il = mgen.getInstructionList();
		final InstructionHandle[] ihs = il.getInstructionHandles();

		int i = ihs.length - 1;
		while (i >= 0) {
			if (ihs[i].getInstruction() instanceof ReturnInstruction) {
				final InstructionList ilConstructorExit =
					logConstructorExit(
						logFilename,
						MoDeCInstrumentationTool.buildMethodSignature(
							cgen,
							mgen),
						cgen);
				final InstructionHandle newTarget =
					il.insert(ihs[i], ilConstructorExit);

				final InstructionList ilCalleeID =
					logCalleeID(logFilename, mgen, cgen, false, false);
				il.insert(ihs[i], ilCalleeID);

				final InstructionTargeter[] targeters = ihs[i].getTargeters();
				if (targeters != null) {
					for (int j = 0; j < targeters.length; j++) {
						targeters[j].updateTarget(ihs[i], newTarget);
					}
				}
			}
			i--;
		}

		boolean callThisConstructor = false;
		boolean callSuperConstructor = false;
		i = 0;
		while (i < ihs.length && !callThisConstructor) {
			if (ihs[i].getInstruction() instanceof INVOKESPECIAL
					&& ihs[i]
						.getInstruction()
						.toString(cgen.getConstantPool().getConstantPool())
						.indexOf(cgen.getClassName().replace('.', '/')) > -1) {
				callThisConstructor = true;
			}
			if (!callThisConstructor) {
				i++;
			}
		}
		if (!callThisConstructor) {
			i = 0;
			while (i < ihs.length && !callSuperConstructor) {
				if (ihs[i].getInstruction() instanceof INVOKESPECIAL
						&& ihs[i]
							.getInstruction()
							.toString(cgen.getConstantPool().getConstantPool())
							.indexOf(cgen.getSuperclassName().replace('.', '/')) > -1) {
					callSuperConstructor = true;
				}
				if (!callSuperConstructor) {
					i++;
				}
			}
		}

		if (callThisConstructor || callSuperConstructor) {
			final InstructionList ilCalleeID =
				logCalleeID(logFilename, mgen, cgen, false, false);
			il.append(ihs[i], ilCalleeID);
			final InstructionList ilConstructorEntry =
				logConstructorEntry(
					logFilename,
					MoDeCInstrumentationTool.buildMethodSignature(cgen, mgen),
					cgen);
			il.append(ihs[i], ilConstructorEntry);
		}

		cgen.removeMethod(method);
		mgen.setInstructionList(il);
		mgen.stripAttributes(true);
		mgen.setMaxStack();
		mgen.setMaxLocals();
		cgen.addMethod(mgen.getMethod());
		il.dispose();
	}

	/**
	 * Instrument loop entry and loop exit for a method.
	 * 
	 * @param logFilename filename in which to write the log data
	 * @param cgen class initialized with the existing information for
	 * 		  the class being modified
	 * @param method the method to be modified
	 */
	private static void addLogToLoops(
		final String logFilename,
		final ClassGen cgen,
		final Method method) {

		if (!method.isAbstract()) { // set up the construction tools
			final ConstantPoolGen cp = cgen.getConstantPool();
			final MethodGen mg = new MethodGen(method, cgen.getClassName(), cp);
			InstructionList il = mg.getInstructionList();
			final InstructionHandle[] ihs = il.getInstructionHandles();
			final Map gotoTarget_afterGoto = new HashMap();
			InstructionHandle newGotoTarget = null;
			int oldTargetIndex = -1;

			for (int i = ihs.length - 1; i >= 0; i--) {
				il = mg.getInstructionList();
				if (ihs[i].getInstruction() instanceof GotoInstruction) {
					GotoInstruction gotoInst =
						(GotoInstruction) ihs[i].getInstruction();
					InstructionHandle oldGotoTarget = gotoInst.getTarget();
					// if the target's index is inferior to gotoInst's index,
					// then it indicates the presence of a loop
					if (gotoInst.getIndex() < 0) {
						if (!gotoTarget_afterGoto.containsKey(gotoInst
							.getTarget())) {
							countLoops++;
							// to indicate that this target is already 
							// associated to a LOOP ENTRY log
							gotoTarget_afterGoto.put(
								gotoInst.getTarget(),
								ihs[i + 1]);
							InstructionList instLoopEntry =
								logLoopEntry(logFilename, countLoops + "", cgen);
							oldGotoTarget = gotoInst.getTarget();
							oldTargetIndex = oldGotoTarget.getPosition();
							newGotoTarget =
								il.insert(oldGotoTarget, instLoopEntry);
							gotoInst.updateTarget(oldGotoTarget, newGotoTarget);
							// update every instruction whose target is oldGotoTarget
							InstructionTargeter[] theTargeters =
								oldGotoTarget.getTargeters();
							if (theTargeters != null) {
								int b = 0;
								while (b < theTargeters.length) {
									if (theTargeters[b]
										.containsTarget(oldGotoTarget))
										theTargeters[b].updateTarget(
											oldGotoTarget,
											newGotoTarget);
									b++;
								}
							}

							gotoTarget_afterGoto.remove(oldGotoTarget);
							gotoTarget_afterGoto.put(newGotoTarget, ihs[i + 1]);
							// we have to update every other GOTOs & IFs target 
							// (only !!!) that references to the old target
							for (int k = i - 1; k >= 0; k--) {
								if (ihs[k].getInstruction() instanceof ReturnInstruction
										&& ihs[k]
											.getPrev()
											.getPrev()
											.getInstruction()
											.toString(
												cgen
													.getConstantPool()
													.getConstantPool())
											.indexOf("ALL LOOPs exit") //.indexOf("ALL LOOPs exit goto")
										== -1) {
									InstructionList newIListReturnInLoop =
										logAllLoopsExit(logFilename, cgen);
									il.insert(ihs[k], newIListReturnInLoop);
								}
							}
						} // insert log LOOP EXIT after gotoInst 
						InstructionList logLoopExitAfterGoto =
							logLoopExit(logFilename, countLoops + "", cgen);
						InstructionHandle loopExitAfterGoto =
							il.append(gotoInst, logLoopExitAfterGoto);
						// update instructions whose target was the instruction
						// AFTER the current GOTO instruction
						InstructionHandle afterGotoInst =
							(InstructionHandle) gotoTarget_afterGoto
								.get(newGotoTarget);
						InstructionTargeter[] theTargetersAfter =
							afterGotoInst.getTargeters();
						if (theTargetersAfter != null)
							for (int p = 0; p < theTargetersAfter.length; p++)
								theTargetersAfter[p].updateTarget(
									afterGotoInst,
									loopExitAfterGoto);
						// insert log LOOP EXIT each time 
						// an iteration of a loop ends
						InstructionList logLoopExitBeforeGoto =
							logLoopExit(logFilename, countLoops + "", cgen);
						InstructionHandle loopExitBeforeGoto;
						loopExitBeforeGoto =
							il.insert(gotoInst, logLoopExitBeforeGoto);
						//update instructions whose target was the instruction
						// BEFORE the current GOTO instruction
						InstructionHandle beforeGotoInst = ihs[i].getPrev();
						InstructionTargeter[] theTargetersBefore =
							beforeGotoInst.getTargeters();
						if (theTargetersBefore != null)
							for (int p = 0; p < theTargetersBefore.length; p++)
								theTargetersBefore[p].updateTarget(
									beforeGotoInst,
									loopExitBeforeGoto);
						int index = i - 1;
						while (oldTargetIndex > -1
								&& ihs[index].getPosition() > oldTargetIndex) {
							InstructionHandle ih_current = ihs[index];
							if (ih_current.getInstruction() instanceof GotoInstruction
									&& ((GotoInstruction) ih_current
										.getInstruction())
										.getTarget()
										.getPosition() > ihs[i].getPosition()) {
								if (ih_current
									.getPrev()
									.getPrev()
									.getInstruction()
									.toString(
										cgen
											.getConstantPool()
											.getConstantPool())
									.indexOf("LOOP exit") == -1) {
									InstructionList newIListExitBeforePositifGoto =
										logLoopExit(logFilename, countLoops
												+ "", cgen);
									il.insert(
										ih_current,
										newIListExitBeforePositifGoto);
								}
							}
							index--;
						}
					}
				} // finalize the constructed method			
				mg.setInstructionList(il);
				mg.stripAttributes(false);
				mg.setMaxStack();
				mg.setMaxLocals();
			}

			cgen.removeMethod(method);
			cgen.addMethod(mg.getMethod());
			il.dispose();
		}
	}

	/**
	 * Insert 'Method entry' at the 1st instruction.
	 * Insert 'Method exit' before each RETURN instruction.
	 * 
	 * @param logFilename filename in which to write the log data
	 * @param cgen class initialized with the existing information for
	 * 		  the class being modified
	 * @param method the method to be modified
	 */
	private static void addLogToMethods(
		final String logFilename,
		final ClassGen cgen,
		final Method method) {

		final ConstantPoolGen cp = cgen.getConstantPool();
		final MethodGen mg = new MethodGen(method, cgen.getClassName(), cp);
		final InstructionList il = mg.getInstructionList();
		final InstructionHandle[] ihs = il.getInstructionHandles();

		il.insert(
			ihs[0],
			logMethodEntry(
				logFilename,
				MoDeCInstrumentationTool.buildMethodSignature(cgen, mg),
				cgen));

		// main and static methods have no instance CALLER ID
		if (!mg.getName().equals("main") && !mg.isStatic()) {
			il.insert(ihs[0], logCalleeID(logFilename, mg, cgen, false, false));
		}
		else if (!mg.getName().equals("main") && mg.isStatic()) {
			il.insert(ihs[0], logCalleeID(logFilename, mg, cgen, false, true));
		}
		else {
			il.insert(ihs[0], logCalleeID(logFilename, mg, cgen, true, true));
		}

		int i = ihs.length - 1;
		while (i >= 0) {
			if (ihs[i].getInstruction() instanceof ReturnInstruction) {
				final InstructionHandle newTarget =
					il.insert(
						ihs[i],
						logMethodExit(logFilename, MoDeCInstrumentationTool
							.buildMethodSignature(cgen, mg), cgen));

				// main and static methods have no instance CALLER ID
				if (!mg.getName().equals("main") && !mg.isStatic()) {
					final InstructionList ilCallerID =
						logCalleeID(logFilename, mg, cgen, false, false);
					il.insert(ihs[i], ilCallerID);
				}
				else if (!mg.getName().equals("main") && mg.isStatic()) {
					final InstructionList ilCallerID =
						logCalleeID(logFilename, mg, cgen, false, true);
					il.insert(ihs[i], ilCallerID);
				}
				else {
					final InstructionList ilCallerID =
						logCalleeID(logFilename, mg, cgen, true, true);
					il.insert(ihs[i], ilCallerID);
				}

				final InstructionTargeter[] targeters = ihs[i].getTargeters();
				if (targeters != null) {
					for (int j = 0; j < targeters.length; j++) {
						targeters[j].updateTarget(ihs[i], newTarget);
					}
				}
			}

			i--;
		}

		cgen.removeMethod(method);
		mg.setInstructionList(il);
		mg.stripAttributes(true);
		mg.setMaxStack();
		mg.setMaxLocals();
		cgen.addMethod(mg.getMethod());
		il.dispose();
	}

	private static String buildMethodSignature(
		final ClassGen aClassGenerator,
		final MethodGen aMethodGenerator) {

		final String declaringClass = aClassGenerator.getClassName();
		final StringBuffer textToBeLogged = new StringBuffer();
		if (aMethodGenerator.getModifiers() > 0) {
			textToBeLogged.append(Modifier.toString(aMethodGenerator
				.getModifiers()));
			textToBeLogged.append(' ');
		}
		textToBeLogged.append(aMethodGenerator.getReturnType());
		textToBeLogged.append(' ');
		textToBeLogged.append(declaringClass);
		textToBeLogged.append('.');
		textToBeLogged.append(aMethodGenerator.getName());
		textToBeLogged.append('(');
		int length = aMethodGenerator.getArgumentTypes().length;

		final int shiftForLocalVariables;
		if (aMethodGenerator.isStatic()) {
			shiftForLocalVariables = 0;
		}
		else {
			shiftForLocalVariables = 1;
		}

		for (int i = 0; i < length; i++) {
			final String argumentType =
				aMethodGenerator.getArgumentType(i).toString();
			textToBeLogged.append(argumentType);
			textToBeLogged.append(' ');
			if (aMethodGenerator.getName().startsWith("access$")
					|| declaringClass.indexOf('$') > 0) {

				textToBeLogged.append("this");
			}
			else if (aMethodGenerator.getLocalVariables().length < length) {
				textToBeLogged.append(aMethodGenerator.getArgumentName(i));
			}
			else {
				textToBeLogged.append(aMethodGenerator.getLocalVariables()[i
						+ shiftForLocalVariables].getName());
			}
			if (i < length - 1)
				textToBeLogged.append(", ");
		}
		textToBeLogged.append(')');
		length = aMethodGenerator.getExceptions().length;
		if (length > 0) {
			textToBeLogged.append(" throws ");
			for (int i = 0; i < length; i++) {
				final String thrownType = aMethodGenerator.getExceptions()[i];
				textToBeLogged.append(thrownType);
				if (i < length - 1)
					textToBeLogged.append(", ");
			}
		}

		// System.out.println(textToBeLogged);

		return textToBeLogged.toString();
	}
	/**
	 * 
	 * @param logFilename
	 * @param originalClasspath
	 * @param bytecodeToInstrument
	 * @param targetDirectory
	 */
	public static void instrumentBytecode(
		final String logFilename,
		final String originalClasspath,
		final String[] bytecodeToInstrument,
		final String targetDirectory) {

		try {
			FolderGenerator.copyFolder(
				originalClasspath,
				originalClasspath,
				targetDirectory);
			org.apache.bcel.Repository.clearCache();
			System.out.println("Classes instrumentation starts...");

			for (int j = 1; j < bytecodeToInstrument.length; j++) {
				final String file = originalClasspath + bytecodeToInstrument[j];
				if (new File(file).exists()) {
					final JavaClass clazz = new ClassParser(file).parse();
					final ClassGen cgen = new ClassGen(clazz);
					System.out.println("\t" + j + ") " + cgen.getClassName());

					if (!cgen.isInterface()) {
						Method[] methods = cgen.getMethods();

						if (MoDeCInstrumentationTool.DEBUG) {
							System.out
								.println("BEFORE ----------------------------------------");
							printCode(methods);
						}

						if (MoDeCInstrumentationTool.LOG_LOOPS) {
							for (int i = 0; i < methods.length; i++) {
								addLogToLoops(logFilename, cgen, methods[i]);
							}

							methods = cgen.getMethods();
						}

						for (int i = 0; i < methods.length; i++)
							if (!(methods[i].getName().equals("<init>"))
									&& !(methods[i].getName()
										.equals("<clinit>"))
									&& !(methods[i].isAbstract())
									&& !(methods[i].isNative())) {

								MoDeCInstrumentationTool.addLogToMethods(
									logFilename,
									cgen,
									methods[i]);
							}
							else if (methods[i].getName().equals("<init>")) {
								MoDeCInstrumentationTool.addLogToConstructors(
									logFilename,
									cgen,
									methods[i]);

								if (MoDeCInstrumentationTool.DEBUG) {
									methods = cgen.getMethods();
									System.out
										.println("AFTER ----------------------------------------");
									printCode(methods);
									System.out
										.println("THE END ----------------------------------------");
								}
							}
					}

					// Dump the new bytecode to a class file
					final FileOutputStream fos =
						new FileOutputStream(targetDirectory
								+ clazz.getClassName().replace('.', '/')
								+ ".class");
					cgen.getJavaClass().dump(fos);
					fos.close();

					final JavaClass instrumentedClazz =
						new ClassParser(targetDirectory
								+ clazz.getClassName().replace('.', '/')
								+ ".class").parse();

					Repository.addClass(instrumentedClazz);
					Repository.addClass(clazz);

					//				System.out.println(
					//					"Loooking for class : "
					//						+ Repository
					//							.lookupClass(clazz.getClassName())
					//							.getClassName());

					if (MoDeCInstrumentationTool.VERIFY_BYTECODE) {
						verifyInstrumentedBytecode(bytecodeToInstrument);
					}
				}
				else {
					System.err.println("\t" + j + ") " + file
							+ " could not be found.");
				}
			}

			org.apache.bcel.Repository.clearCache();
			System.out
				.println("Classes instrumentation successfully completed.");
		}
		catch (final Exception e) {
			e.printStackTrace(System.err);
		}
	}

	/**
	 * Create instruction to log all loops exit.
	 * 
	 * @param filename  filename in which to write the log data
	 * @param methgen the method to be modified
	 * @param cgen class initialized with the existing information for
	 * 		  the class being modified
	 * @param isMain indicate whether the called method is the method main
	 * @param isStatic indicate whether the called method is a static method
	 * @return InstructionList instructions for obtaining the before-last 
	 *						   element of the stacktrace
	 */
	private static InstructionList logAllLoopsExit(
		final String filename,
		final ClassGen cgen) {
		final ConstantPoolGen cp = cgen.getConstantPool();

		final InstructionFactory ifact = new InstructionFactory(cgen);
		final InstructionList newIlist = new InstructionList();

		newIlist.append(new PUSH(cp, filename));
		newIlist.append(ifact.createInvoke(
			MoDeCInstrumentationTool.LOG_TO_FILE_CLASS_NAME,
			"allLoopsExit",
			Type.VOID,
			new Type[] { new ObjectType("java.lang.String") },
			Constants.INVOKESTATIC));
		return newIlist;
	}

	/**
	 * Create instruction to log the ID of a callee.
	 * 
	 * @param filename  filename in which to write the log data
	 * @param methgen the method to be modified
	 * @param cgen class initialized with the existing information for
	 * 		  the class being modified
	 * @param isMain indicate whether the called method is the method main
	 * @param isStatic indicate whether the called method is a static method
	 * @return InstructionList instructions for obtaining the before-last 
	 * 						   element of the stacktrace
	 */
	private static InstructionList logCalleeID(
		final String filename,
		final MethodGen methgen,
		final ClassGen cgen,
		final boolean isMain,
		final boolean isStatic) {

		final ConstantPoolGen cp = cgen.getConstantPool();
		final InstructionFactory ifact = new InstructionFactory(cgen);
		final InstructionList newIlist = new InstructionList();

		newIlist.append(new PUSH(cp, filename));

		if (!isMain && !isStatic) {
			newIlist.append(InstructionFactory.createLoad(
			//new ObjectType(methgen.getClassName()),
				new ObjectType("java.lang.Object"),
				0));
		}
		else {
			newIlist.append(new PUSH(cp, cgen.getClassName()));
		}

		if (isMain) {
			newIlist.append(new PUSH(cp, true));
		}
		else {
			newIlist.append(new PUSH(cp, false));
		}

		if (isStatic) {
			newIlist.append(new PUSH(cp, true));
		}
		else {
			newIlist.append(new PUSH(cp, false));
		}

		newIlist.append(ifact.createInvoke(
			MoDeCInstrumentationTool.LOG_TO_FILE_CLASS_NAME,
			"calleeID",
			Type.VOID,
			new Type[] { new ObjectType("java.lang.String"), Type.OBJECT,
					Type.BOOLEAN, Type.BOOLEAN },
			Constants.INVOKESTATIC));
		return newIlist;
	}

	/**
	 * Create instruction to log a constructor entry.
	 * 
	 * @param filename  filename in which to write the log data
	 * @param methgen the method to be modified
	 * @param cgen class initialized with the existing information for
	 * 		  the class being modified
	 * @param isMain indicate whether the called method is the method main
	 * @param isStatic indicate whether the called method is a static method
	 * @return InstructionList instructions for obtaining the before-last 
	 *						   element of the stacktrace
	 */
	private static InstructionList logConstructorEntry(
		final String filename,
		final String toBeLogged,
		final ClassGen cgen) {

		final ConstantPoolGen cp = cgen.getConstantPool();
		final InstructionFactory ifact = new InstructionFactory(cgen);
		final InstructionList newIlist = new InstructionList();

		newIlist.append(new PUSH(cp, filename));
		newIlist.append(new PUSH(cp, toBeLogged));
		newIlist.append(ifact.createInvoke(
			MoDeCInstrumentationTool.LOG_TO_FILE_CLASS_NAME,
			"constructorEntry",
			Type.VOID,
			new Type[] { new ObjectType("java.lang.String"),
					new ObjectType("java.lang.String") },
			Constants.INVOKESTATIC));
		return newIlist;
	}

	/**
	 * Create instruction to log a constructor exit.
	 * 
	 * @param filename  filename in which to write the log data
	 * @param methgen the method to be modified
	 * @param cgen class initialized with the existing information for
	 * 		  the class being modified
	 * @param isMain indicate whether the called method is the method main
	 * @param isStatic indicate whether the called method is a static method
	 * @return InstructionList instructions for obtaining the before-last 
	 *						   element of the stacktrace
	 */
	private static InstructionList logConstructorExit(
		final String filename,
		final String toBeLogged,
		final ClassGen cgen) {

		final ConstantPoolGen cp = cgen.getConstantPool();
		final InstructionFactory ifact = new InstructionFactory(cgen);
		final InstructionList newIlist = new InstructionList();

		newIlist.append(new PUSH(cp, filename));
		newIlist.append(new PUSH(cp, toBeLogged));
		newIlist.append(ifact.createInvoke(
			MoDeCInstrumentationTool.LOG_TO_FILE_CLASS_NAME,
			"constructorExit",
			Type.VOID,
			new Type[] { new ObjectType("java.lang.String"),
					new ObjectType("java.lang.String") },
			Constants.INVOKESTATIC));
		return newIlist;
	}

	/**
	 * Create instruction to log a loop entry.
	 * 
	 * @param filename  filename in which to write the log data
	 * @param methgen the method to be modified
	 * @param cgen class initialized with the existing information for
	 * 		  the class being modified
	 * @param isMain indicate whether the called method is the method main
	 * @param isStatic indicate whether the called method is a static method
	 * @return InstructionList instructions for obtaining the before-last 
	 *						   element of the stacktrace
	 */
	private static InstructionList logLoopEntry(
		final String filename,
		final String toBeLogged,
		final ClassGen cgen) {

		final ConstantPoolGen cp = cgen.getConstantPool();
		final InstructionFactory ifact = new InstructionFactory(cgen);
		final InstructionList newIlist = new InstructionList();

		newIlist.append(new PUSH(cp, filename));
		newIlist.append(new PUSH(cp, toBeLogged));
		newIlist.append(ifact.createInvoke(
			MoDeCInstrumentationTool.LOG_TO_FILE_CLASS_NAME,
			"loopEntry",
			Type.VOID,
			new Type[] { new ObjectType("java.lang.String"),
					new ObjectType("java.lang.String") },
			Constants.INVOKESTATIC));
		return newIlist;
	}

	/**
	 * Create instruction to log a loop exit.
	 * 
	 * @param filename  filename in which to write the log data
	 * @param methgen the method to be modified
	 * @param cgen class initialized with the existing information for
	 * 		  the class being modified
	 * @param isMain indicate whether the called method is the method main
	 * @param isStatic indicate whether the called method is a static method
	 * @return InstructionList instructions for obtaining the before-last 
	 *						   element of the stacktrace
	 */
	private static InstructionList logLoopExit(
		final String filename,
		final String toBeLogged,
		final ClassGen cgen) {

		final ConstantPoolGen cp = cgen.getConstantPool();
		final InstructionFactory ifact = new InstructionFactory(cgen);
		final InstructionList newIlist = new InstructionList();

		newIlist.append(new PUSH(cp, filename));
		newIlist.append(new PUSH(cp, toBeLogged));
		newIlist.append(ifact.createInvoke(
			MoDeCInstrumentationTool.LOG_TO_FILE_CLASS_NAME,
			"loopExit",
			Type.VOID,
			new Type[] { new ObjectType("java.lang.String"),
					new ObjectType("java.lang.String") },
			Constants.INVOKESTATIC));
		return newIlist;
	}

	/**
	 * Create instruction to log a method entry.
	 * 
	 * @param filename  filename in which to write the log data
	 * @param methgen the method to be modified
	 * @param cgen class initialized with the existing information for
	 * 		  the class being modified
	 * @param isMain indicate whether the called method is the method main
	 * @param isStatic indicate whether the called method is a static method
	 * @return InstructionList instructions for obtaining the before-last 
	 *						   element of the stacktrace
	 */
	private static InstructionList logMethodEntry(
		final String filename,
		final String toBeLogged,
		final ClassGen cgen) {

		final ConstantPoolGen cp = cgen.getConstantPool();
		final InstructionFactory ifact = new InstructionFactory(cgen);
		final InstructionList newIlist = new InstructionList();

		newIlist.append(new PUSH(cp, filename));
		newIlist.append(new PUSH(cp, toBeLogged));
		newIlist.append(ifact.createInvoke(
			MoDeCInstrumentationTool.LOG_TO_FILE_CLASS_NAME,
			"methodEntry",
			Type.VOID,
			new Type[] { new ObjectType("java.lang.String"),
					new ObjectType("java.lang.String") },
			Constants.INVOKESTATIC));
		return newIlist;
	}

	/**
	 * Create instruction to log a method exit.
	 * 
	 * @param filename  filename in which to write the log data
	 * @param methgen the method to be modified
	 * @param cgen class initialized with the existing information for
	 * 		  the class being modified
	 * @param isMain indicate whether the called method is the method main
	 * @param isStatic indicate whether the called method is a static method
	 * @return InstructionList instructions for obtaining the before-last 
	 *						   element of the stacktrace
	 */
	private static InstructionList logMethodExit(
		final String filename,
		final String toBeLogged,
		final ClassGen cgen) {

		final ConstantPoolGen cp = cgen.getConstantPool();
		final InstructionFactory ifact = new InstructionFactory(cgen);
		final InstructionList newIlist = new InstructionList();

		newIlist.append(new PUSH(cp, filename));
		newIlist.append(new PUSH(cp, toBeLogged));
		newIlist.append(ifact.createInvoke(
			MoDeCInstrumentationTool.LOG_TO_FILE_CLASS_NAME,
			"methodExit",
			Type.VOID,
			new Type[] { new ObjectType("java.lang.String"),
					new ObjectType("java.lang.String") },
			Constants.INVOKESTATIC));
		return newIlist;
	}

	private static void printCode(final Method[] methods) {
		for (int i = 0; i < methods.length; i++) {
			System.out
				.println("*****************************************************");
			System.out.println(methods[i]);
			Code code = methods[i].getCode();
			if (code != null) {
				// Non-abstract method 
				System.out.println(code);
			}
		}
	}

	/**
	 * Verifies newly instrumented .class files.
	 * These class files
	 * must be somewhere in your CLASSPATH (refer to Sun's
	 * documentation for questions about this) or you must have put the classes
	 * into the BCEL Repository yourself (via 'addClass(JavaClass)').
	 * 
	 * @param args
	 */
	private static void verifyInstrumentedBytecode(final String[] args) {
		System.out
			.println("JustIce by Enver Haase, (C) 2001-2002.\n<http://bcel.sourceforge.net>\n<http://jakarta.apache.org/bcel>\n");

		for (int k = 1; k < args.length; k++) {
			try {
				if (args[k].endsWith(".class")) {
					args[k] = args[k].substring(0, args[k].lastIndexOf("."));
					args[k] = args[k].replace('/', '.');
					//args[k] = "instrumented." + args[k];
					System.out.println("Now verifying: " + args[k] + "\n");
					Verifier v = VerifierFactory.getVerifier(args[k]);
					VerificationResult vr;
					vr = v.doPass1();
					System.out.println("Pass 1:\n" + vr);
					vr = v.doPass2();
					System.out.println("Pass 2:\n" + vr);
					if (vr == VerificationResult.VR_OK) {
						JavaClass jc =
							org.apache.bcel.Repository.lookupClass(args[k]);
						for (int i = 0; i < jc.getMethods().length; i++) {
							vr = v.doPass3a(i);
							System.out
								.println("Pass 3a, method number " + i + " ['"
										+ jc.getMethods()[i] + "']:\n" + vr);
							vr = v.doPass3b(i);
							System.out
								.println("Pass 3b, method number " + i + " ['"
										+ jc.getMethods()[i] + "']:\n" + vr);
						}
					}

					System.out.println("Warnings:");
					String[] warnings = v.getMessages();
					if (warnings.length == 0)
						System.out.println("<none>");
					for (int j = 0; j < warnings.length; j++)
						System.out.println(warnings[j]);
					System.out.println("\n");
					// avoid swapping.
					v.flush();
					System.gc();
					//		org.apache.bcel.Repository.clearCache();
				}
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
