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
package caffeine.remote;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Vector;

import util.io.ProxyConsole;

import javassist.ClassPool;
import javassist.CodeConverter;
import javassist.CtClass;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import caffeine.Constants;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.MethodInfo;
import com.ibm.toad.cfparse.MethodInfoList;
import com.ibm.toad.cfparse.attributes.AttrInfoList;
import com.ibm.toad.cfparse.attributes.CodeAttrInfo;
import com.ibm.toad.cfparse.instruction.BaseInstruction;
import com.ibm.toad.cfparse.instruction.JVMConstants;
import com.ibm.toad.cfparse.instruction.MutableCodeSegment;
import com.ibm.toad.cfparse.instruction.StringInstructionFactory;

/**
 * @version 	0.8
 * @author		Yann-Gaël Guéhéneuc
 */
public final class Loader extends ClassLoader {
	private static final ClassPool pool = ClassPool.getDefault();
	private static final String NewSuperclassName =
		"caffeine.remote.CaffeineObject";
	private static final String CaffeineWrapperFullName =
		"caffeine.remote.CaffeineWrapper";

	// This is a duplicate of static field Caffeine.SystemClasses.
	// See method Loader.loadClass(String, boolean) for exaplanations.
	// Except for the class:
	// 		caffeine.remote.CaffeineMethodReturnedValueWrapper
	// which I do not want to modify, but which method exits I want
	// to trap!
	public static final String[] SystemClasses =
		new String[] {
			Loader.CaffeineWrapperFullName,
			"caffeine.remote.CaffeineObject",
			"caffeine.remote.Loader",
			"caffeine.remote.VacuumCleaner",
			"caffeine.remote.WrapperMain",
			"com.ibm.toad.*",
			"javassist.*",
			"JIP.*",
			"com.sun.*",
			"java.applet.*",
			"java.awt.*",
			"java.beans.*",
			"java.io.*",
			"java.nio.*",
			"java.lang.*",
			"java.lang.ref.*",
			"java.lang.reflect.*",
			"java.math.*",
			"java.net.*",
			"java.rmi.*",
			"java.security.*",
			"java.sql.*",
			"java.text.*",
			"java.util.*",
			"javax.*",
			"org.omg.*",
			"sun.*" };

	private final String[] classNameFilter;
	private final boolean collectionManagement;
	private final boolean finalizationManagement;
	private final boolean programEndManagement;
	private final boolean returnedValueManagement;
	private CodeConverter converterVector;
	private CodeConverter converterHashtable;

	protected Loader(final String[] args) {
		this.collectionManagement = args[1].equals("true");
		this.finalizationManagement = args[2].equals("true");
		this.programEndManagement = args[3].equals("true");
		this.returnedValueManagement = args[4].equals("true");
		this.classNameFilter = new String[args.length - 5];
		System.arraycopy(args, 5, this.classNameFilter, 0, args.length - 5);

		System.out.println(
			"Collection management:     " + this.collectionManagement);
		System.out.println(
			"Finalization management:   " + this.finalizationManagement);
		System.out.println(
			"Program-end management:    " + this.programEndManagement);
		System.out.println(
			"Returned value management: " + this.returnedValueManagement);

		// Yann 2002/05/29: Improvements!
		// I do not need to change the wrapper anymore,
		// because I can add a method exit request
		// specifically to monitor it!

		// Yann 2002/05/18: Comments!
		// All the following comments are out-of-date
		// but I keep them to remember the whole story...

		// I do a special treatment when I encounter the first
		// monitored package .
		// I add to it the class CaffeineWrapper
		// that will be used to trap all returned values and
		// program exits.
		//	try {
		//		this.newWrapperFullName = args[1] + ".CaffeineWrapper";
		//		pool.get(Loader.CaffeineWrapperFullName).replaceClassName(
		//			Loader.CaffeineWrapperFullName,
		//			this.newWrapperFullName);
		//	}
		//	catch (final NotFoundException nfe) {
		//		nfe.printStackTrace();
		//		throw new NoClassDefFoundError(Loader.CaffeineWrapperFullName);
		//	}
		// Yann 2002/05/17
		// I must add this special class because the solution
		// that consists in adding the correspondant method to
		// the first class being monitored does not work because
		// of a JDI error!
		// When I add the correspondant methods, event though the
		// program verifies and runs, JDI does not see the trap
		// method arguments!?
		// But there is no way to understand why...
		// this.newCaffeineReturnedValueWrapperFullName = this.mainClassName;
		// caffeineReturnedValueWrapper.setSuperclass(currentClass.getSuperclass());
		// currentClass.setSuperclass(caffeineReturnedValueWrapper);
		//	final CtMethod[] methodWrappers =
		//		caffeineReturnedValueWrapper.getDeclaredMethods();
		//	for (int i = 0; i < methodWrappers.length; i++) {
		//		currentClass.addMethod(CtNewMethod.copy(methodWrappers[i], currentClass, null));
		//		// currentClass.addMethod(CtNewMethod.delegator(methodWrappers[i], currentClass));
		//	}
		// I do all this method-adding thing to allow any class from
		// the package being monitored (or the main class alone) to
		// access them and to make sure these methods got trap by
		// the event manager: This should save lots of time in
		// comparison to the technique that consists in monitoring
		// any method exit for any class because the interesting
		// methods are in a special class.
	}
	protected Class findClass(final String name)
		throws ClassNotFoundException {

		try {
			final CtClass currentClass = pool.get(name);
			boolean monitoredClass = false;

			if (!currentClass.isInterface()) {
				// Yann 2002/07/09: Monitoring.
				// I check if the currently loaded class must be monitored.
				final String currentClassPackageName =
					currentClass.getPackageName();
				for (int i = 0;
					i < this.classNameFilter.length && !monitoredClass;
					i++) {
					if (currentClassPackageName
						.equals(this.classNameFilter[i])) {

						monitoredClass = true;
					}
				}

				if (monitoredClass) {
					// Yann 2002/07/09: Composition relationship management!
					if (this.collectionManagement) {
						if (this.converterVector == null) {
							this.converterVector = new CodeConverter();
							this.converterVector.replaceNew(
								pool.get("java.util.Vector"),
								pool.get("caffeine.remote.Vector"),
								"newInstance");

							this.converterHashtable = new CodeConverter();
							this.converterHashtable.replaceNew(
								pool.get("java.util.Hashtable"),
								pool.get("caffeine.remote.Hashtable"),
								"newInstance");
						}
						currentClass.instrument(this.converterVector);
						currentClass.instrument(this.converterHashtable);
					}

					// Yann 2002/07/09: Management of finalizers.
					if (this.finalizationManagement
						&& (currentClass.getModifiers() & Modifier.ABSTRACT)
							!= Modifier.ABSTRACT) {

						try {
							currentClass.getDeclaredMethod("finalize");
						}
						catch (final NotFoundException e) {
							currentClass.addMethod(
								CtNewMethod.delegator(
									pool.getMethod(
										"java.lang.Object",
										"finalize"),
									currentClass));
						}
					}

					// Yann 2002/07/09: Unique ID.
					if (currentClass
						.getSuperclass()
						.getName()
						.equals("java.lang.Object")) {

						currentClass.setSuperclass(
							pool.get(Loader.NewSuperclassName));
					}
				}
			}

			// Whatever happened before (did the byte-codes got
			// modified or not), I write the current classfile
			// and get the corresponding bytes to define the class.
			byte[] bytes = pool.write(name);

			// I could add to each method a call to the garbage-collector
			// and to the finalization thread. However, I must also
			// add a thread that does the same in parallel to make sure
			// that the JVM finalizes all instances at the very end of the
			// program (just before the JVM exits).
			// The thread (see WrapperMain class) is necessary and
			// sufficient (?).
			// I also instrument the code to trap any call to the
			//		System.exit(int)
			// method and to trap returned values, with the
			//		caffeineReturnedValueWrapper(...)
			// methods. I treat differently the main class: I also
			// want to trap any of its returns (program end).
			// Yann 2003/07/30: Improvements!
			// The instrumentMethods() and instrumentSystemExits() should
			// be refactored to minimize time complexity!
			if (!currentClass.isInterface() && monitoredClass) {
				if (this.finalizationManagement
					|| this.returnedValueManagement) {

					bytes = this.instrumentMethods(bytes);
				}
				if (this.programEndManagement) {
					bytes = this.instrumentSystemExits(bytes);
				}
			}

			return super.defineClass(name, bytes, 0, bytes.length);
		}
		catch (final Exception e) {
			if (Constants.EXCEPTION_DEBUG) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			throw new ClassNotFoundException(name);
		}
	}
	public Class loadClass(final String name, final boolean resolve)
		throws ClassNotFoundException {

		if (Constants.DEBUG) {
			System.err.print("CaffeineClassLoader.loadClass(\"");
			System.err.print(name);
			System.err.print("\", ");
			System.err.print(resolve);
			System.err.println(')');
		}

		Class c = this.findLoadedClass(name);
		if (c == null) {
			// I cannot use the SystemClasses static field
			// from the Caffeine because the Caffeine class
			// stays in the "local" JVM while the Loader class
			// stays in the "remote" JVM. Thus, the access from
			// the Loader class to the SystemClasses of the
			// Caffeine class happens through the JDI interface.
			// When the Loader class instance loads the required
			// classes, the required JDI classes are not loaded
			// yet, resulting in an exception:
			//
			// java.lang.NoClassDefFoundError: com/sun/jdi/connect/Connector
			// at caffeine.Loader.loadClass(Loader.java:nnn)
			// at java.lang.ClassLoader.loadClass(ClassLoader.java:nnn)
			// at caffeine.WrapperMain.main(WrapperMain.java:nn)
			// Exception in thread "main" 
			boolean nameInSystemClasses = false;
			for (int i = 0;
				i < Loader.SystemClasses.length && !nameInSystemClasses;
				i++) {
				final int length = Loader.SystemClasses[i].length() - 1;
				if (Loader.SystemClasses[i].charAt(length) == '*') {
					if (name
						.regionMatches(
							0,
							Loader.SystemClasses[i],
							0,
							length - 1)) {
						nameInSystemClasses = true;
					}
				}
				else {
					if (name.equals(Loader.SystemClasses[i])) {
						nameInSystemClasses = true;
					}
				}
			}
			if (Constants.DEBUG) {
				System.err.print(name);
				System.err.print(" is SystemClasses ? ");
				System.err.println(nameInSystemClasses);
			}
			if (nameInSystemClasses) {
				try {
					c = this.getParent().loadClass(name);
				}
				catch (ClassNotFoundException e) {
					c = this.findClass(name);
				}
			}
			else {
				c = this.findClass(name);
			}
		}
		if (resolve) {
			this.resolveClass(c);
		}
		return c;
	}

	private byte[] instrumentMethods(byte[] classFileBytes) {
		final ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();
		final DataOutputStream dataOutputStream =
			new DataOutputStream(byteArrayOutputStream);
		try {
			final ByteArrayInputStream byteArrayInputStream =
				new ByteArrayInputStream(classFileBytes);
			final ClassFile cf = new ClassFile(byteArrayInputStream);
			final MethodInfoList ml = cf.getMethods();
			for (int i = 0; i < ml.length(); i++) {
				final MethodInfo mi = ml.get(i);
				if (!Loader.isSpecialMethod(mi)) {
					final AttrInfoList al = mi.getAttrs();
					final CodeAttrInfo cai = (CodeAttrInfo) al.get("Code");
					if (cai != null) {
						final MutableCodeSegment mcs =
							new MutableCodeSegment(cf.getCP(), cai, true);

						mcs.setInstructionFactory(
							new StringInstructionFactory());
						final Vector instructions = mcs.getInstructions();
						BaseInstruction baseInstruction;
						String[] insert;

						// I insert call to the garbage-collector.
						if (this.finalizationManagement) {
							final int length = instructions.size() - 1;
							insert = Loader.getGCCallEntries(cf, mi);
							for (int j = 0; j < insert.length; j++) {
								baseInstruction =
									(BaseInstruction) mcs.create(insert[j]);
								instructions.insertElementAt(
									baseInstruction,
									length + j);
							}
						}

						// I add a wrapper around the returned value (if any) to catch
						// it up to return with the method exit information.
						if (this.returnedValueManagement) {
							final int length = instructions.size() - 1;
							insert =
								Loader.getReturnWrapperEntries(
									cf,
									mi,
									(BaseInstruction) instructions.elementAt(
										length));
							for (int j = 0; j < insert.length; j++) {
								baseInstruction =
									(BaseInstruction) mcs.create(insert[j]);
								instructions.insertElementAt(
									baseInstruction,
									length + j);
							}
						}

						mcs.updateCodeAttrInfo(cai);
					}
				}
			}
			cf.write(dataOutputStream);
		}
		catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (IOException ioe) {
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return byteArrayOutputStream.toByteArray();
	}
	private byte[] instrumentSystemExits(final byte[] classFileBytes) {
		final ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();
		final DataOutputStream dataOutputStream =
			new DataOutputStream(byteArrayOutputStream);
		try {
			final ByteArrayInputStream byteArrayInputStream =
				new ByteArrayInputStream(classFileBytes);
			final ClassFile cf = new ClassFile(byteArrayInputStream);
			final MethodInfoList ml = cf.getMethods();
			for (int i = 0; i < ml.length(); i++) {
				final MethodInfo mi = ml.get(i);
				if (!Loader.isSpecialMethod(mi)) {
					final AttrInfoList al = mi.getAttrs();
					final CodeAttrInfo cai = (CodeAttrInfo) al.get("Code");
					if (cai != null) {
						final MutableCodeSegment mcs =
							new MutableCodeSegment(cf.getCP(), cai, true);

						mcs.setInstructionFactory(
							new StringInstructionFactory());
						final Vector instructions = mcs.getInstructions();

						for (int j = 0; j < instructions.size(); j++) {
							final BaseInstruction baseInstruction =
								(BaseInstruction) instructions.elementAt(j);
							final int opCode = baseInstruction.getOpCode();
							if (opCode == JVMConstants.INVOKESTATIC) {
								final String instruction =
									baseInstruction.toString().trim();
								if (instruction
									.equals("invokestatic void java.lang.System.exit(int)")) {
									instructions.removeElementAt(j);
									instructions.insertElementAt(
										(BaseInstruction) mcs.create(
											"invokestatic void "
												+ Loader
													.CaffeineWrapperFullName
												+ ".caffeineUniqueExit(int)"),
										j);
								}
							}
						}

						mcs.updateCodeAttrInfo(cai);
					}
				}
			}
			cf.write(dataOutputStream);
		}
		catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (IOException ioe) {
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return byteArrayOutputStream.toByteArray();
	}
	private static boolean isSpecialMethod(final MethodInfo mi) { // Is the method a constructor or a finalizer?
		if (mi.getName().charAt(0) == '<'
			|| mi.getName().equals("finalize")
			|| mi.getName().equals("caffeineReturnedValueWrapper")
			|| mi.getName().equals("caffeineUniqueExit")) {

			return true;
		}

		final AttrInfoList al = mi.getAttrs();
		// Is the method a pure abstract or native method?
		if (al == null) {
			return true;
		}
		return false;
	}
	private static String[] getGCCallEntries(
		final ClassFile cf,
		final MethodInfo mi) {

		final String[] codeEntry =
			new String[] {
				"invokestatic void java.lang.System.gc()",
				"invokestatic void java.lang.System.runFinalization()" };
		return codeEntry;
	}
	private static String[] getReturnWrapperEntries(
		final ClassFile cf,
		final MethodInfo mi,
		final BaseInstruction baseInstruction) {

		final AttrInfoList al = mi.getAttrs();
		final CodeAttrInfo cai = (CodeAttrInfo) al.get("Code");
		final int opCode = baseInstruction.getOpCode();
		final String[] codeExit;
		switch (opCode) {
			case JVMConstants.DRETURN :
				cai.setMaxStack(cai.getMaxStack() + 2);
				codeExit =
					new String[] {
						"dup2",
						"invokestatic double "
							+ Loader.CaffeineWrapperFullName
							+ ".caffeineReturnedValueWrapper(double)" };
				break;
			case JVMConstants.FRETURN :
				cai.setMaxStack(cai.getMaxStack() + 1);
				codeExit =
					new String[] {
						"dup",
						"invokestatic float "
							+ Loader.CaffeineWrapperFullName
							+ ".caffeineReturnedValueWrapper(float)" };
				break;
			case JVMConstants.IRETURN :
				if (mi.getReturnType().equals("boolean")) {
					codeExit =
						new String[] {
							"invokestatic int "
								+ Loader.CaffeineWrapperFullName
								+ ".caffeineReturnedValueWrapper(int)" };
				}
				else {
					cai.setMaxStack(cai.getMaxStack() + 1);
					codeExit =
						new String[] {
							"dup",
							"invokestatic int "
								+ Loader.CaffeineWrapperFullName
								+ ".caffeineReturnedValueWrapper(int)" };
				}
				break;
			case JVMConstants.LRETURN :
				cai.setMaxStack(cai.getMaxStack() + 2);
				codeExit =
					new String[] {
						"dup2",
						"invokestatic long "
							+ Loader.CaffeineWrapperFullName
							+ ".caffeineReturnedValueWrapper(long)" };
				break;
			case JVMConstants.ARETURN :
				cai.setMaxStack(cai.getMaxStack() + 1);
				codeExit =
					new String[] {
						"dup",
						"invokestatic java.lang.Object "
							+ Loader.CaffeineWrapperFullName
							+ ".caffeineReturnedValueWrapper(java.lang.Object)",
						"checkcast " + mi.getReturnType()};
				break;
			case JVMConstants.RETURN :
			default :
				codeExit = new String[0];
				break;
		}
		return codeExit;
	}
}
