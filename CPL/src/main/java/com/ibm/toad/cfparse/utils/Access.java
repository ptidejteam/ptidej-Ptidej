/* Decompiler 196ms, total 408ms, lines 276 */
package com.ibm.toad.cfparse.utils;

import java.util.StringTokenizer;

public final class Access {
	public static final short ACC_ABSTRACT = 1024;
	public static final short ACC_ANNOTATION = 8192;
	public static final short ACC_BRIDGE = 64;
	public static final short ACC_ENUM = 16384;
	public static final short ACC_FINAL = 16;
	public static final short ACC_INTERFACE = 512;
	public static final short ACC_NATIVE = 256;
	public static final short ACC_PRIVATE = 2;
	public static final short ACC_PROTECTED = 4;
	public static final short ACC_PUBLIC = 1;
	public static final short ACC_STATIC = 8;
	public static final short ACC_STRICT = 2048;
	public static final short ACC_SUPER = 32;
	public static final short ACC_SYNCHRONIZED = 32;
	public static final short ACC_SYNTHETIC = 4096;
	public static final short ACC_TRANSIENT = 128;
	public static final short ACC_VARARGS = 128;
	public static final short ACC_VOLATILE = 64;

	public static String getAsString(int var0) {
		String var1 = "";

		if (Access.isAbstract(var0)) {
			var1 = var1 + "abstract ";
		}

		if (Access.isAnnotation(var0)) {
			var1 = var1 + "/*annotation*/ ";
		}

		if (Access.isClass(var0)) {
			var1 = var1 + "class ";
		}

		if (Access.isEnum(var0)) {
			var1 = var1 + "enum ";
		}

		if (Access.isFinal(var0)) {
			var1 = var1 + "final ";
		}

		if (Access.isInterface(var0)) {
			var1 = var1 + "interface ";
		}

		if (Access.isNative(var0)) {
			var1 = var1 + "native ";
		}

		if (Access.isPrivate(var0)) {
			var1 = var1 + "private ";
		}

		if (Access.isProtected(var0)) {
			var1 = var1 + "protected ";
		}

		if (Access.isPublic(var0)) {
			var1 = var1 + "public ";
		}

		if (Access.isStatic(var0)) {
			var1 = var1 + "static ";
		}

		if (Access.isStrict(var0)) {
			var1 = var1 + "strict ";
		}

		if (Access.isSuper(var0)) {
			var1 = var1 + "/*super*/ ";
		}

		if (Access.isSynchronized(var0)) {
			var1 = var1 + "synchronized ";
		}

		if (Access.isSynthetic(var0)) {
			var1 = var1 + "synthetic ";
		}

		if (Access.isTransient(var0)) {
			var1 = var1 + "transient ";
		}

		if (Access.isVolatile(var0)) {
			var1 = var1 + "volatile ";
		}

		return var1;
	}

	public static String getClassAsString(int var0) {
		String var1 = "";

		if (Access.isPublic(var0)) {
			var1 = var1 + "public ";
		}

		if (Access.isFinal(var0)) {
			var1 = var1 + "final ";
		}

		if (Access.isSuper(var0)) {
			var1 = var1 + "/*super*/ ";
		}

		if (Access.isInterface(var0)) {
			var1 = var1 + "interface ";
		}

		if (Access.isAbstract(var0)) {
			var1 = var1 + "abstract ";
		}

		if (Access.isSynthetic(var0)) {
			var1 = var1 + "synthetic ";
		}

		if (Access.isAnnotation(var0)) {
			var1 = var1 + "/*annotation*/ ";
		}

		if (Access.isEnum(var0)) {
			var1 = var1 + "enum ";
		}

		if (Access.isClass(var0)) {
			var1 = var1 + "class ";
		}

		return var1;
	}

	public static String getFieldAsString(int var0) {
		String var1 = "";

		if (Access.isPublic(var0)) {
			var1 = var1 + "public ";
		}

		if (Access.isPrivate(var0)) {
			var1 = var1 + "private ";
		}

		if (Access.isProtected(var0)) {
			var1 = var1 + "protected ";
		}

		if (Access.isStatic(var0)) {
			var1 = var1 + "static ";
		}

		if (Access.isFinal(var0)) {
			var1 = var1 + "final ";
		}

		if (Access.isVolatile(var0)) {
			var1 = var1 + "volatile ";
		}

		if (Access.isTransient(var0)) {
			var1 = var1 + "transient ";
		}

		if (Access.isTransient(var0)) {
			var1 = var1 + "synthetic ";
		}

		return var1.trim();
	}

	public static int getFromString(String var0) {
		final StringTokenizer var2 = new StringTokenizer(var0, " ");
		int var1 = 0;

		while (var2.hasMoreTokens()) {
			final String var3 = var2.nextToken().trim();

			if (var3.equals("abstract")) {
				var1 |= Access.ACC_ABSTRACT;
			}
			else if (var3.equals("/*annotation*/")) {
				var1 |= Access.ACC_ANNOTATION;
			}
			else if (var3.equals("/*bridge*/")) {
				var1 |= Access.ACC_BRIDGE;
			}
			else if (var3.equals("enum")) {
				var1 |= Access.ACC_ENUM;
			}
			else if (var3.equals("final")) {
				var1 |= Access.ACC_FINAL;
			}
			else if (var3.equals("interface")) {
				var1 |= Access.ACC_INTERFACE;
			}
			else if (var3.equals("native")) {
				var1 |= Access.ACC_NATIVE;
			}
			else if (var3.equals("private")) {
				var1 |= Access.ACC_PRIVATE;
			}
			else if (var3.equals("protected")) {
				var1 |= Access.ACC_PROTECTED;
			}
			else if (var3.equals("public")) {
				var1 |= Access.ACC_PUBLIC;
			}
			else if (var3.equals("static")) {
				var1 |= Access.ACC_STATIC;
			}
			else if (var3.equals("strict")) {
				var1 |= Access.ACC_STRICT;
			}
			else if (var3.equals("/*super*/")) {
				var1 |= Access.ACC_SUPER;
			}
			else if (var3.equals("synchronized")) {
				var1 |= Access.ACC_SYNCHRONIZED;
			}
			else if (var3.equals("synthetic")) {
				var1 |= Access.ACC_SYNTHETIC;
			}
			else if (var3.equals("transient")) {
				var1 |= Access.ACC_TRANSIENT;
			}
			else if (var3.equals("/*varargs*/")) {
				var1 |= Access.ACC_VARARGS;
			}
			else if (var3.equals("volatile")) {
				var1 |= Access.ACC_VOLATILE;
			}
			else if (var3.equals("class")) {
				var1 |= 0;
			}
		}

		return var1;
	}

	public static String getMethodAsString(int var0) {
		String var1 = "";

		if (Access.isPublic(var0)) {
			var1 = var1 + "public ";
		}

		if (Access.isFinal(var0)) {
			var1 = var1 + "final ";
		}

		if (Access.isPrivate(var0)) {
			var1 = var1 + "private ";
		}

		if (Access.isProtected(var0)) {
			var1 = var1 + "protected ";
		}

		if (Access.isStatic(var0)) {
			var1 = var1 + "static ";
		}

		if (Access.isFinal(var0)) {
			var1 = var1 + "final ";
		}

		if (Access.isSynchronized(var0)) {
			var1 = var1 + "synchronized ";
		}

		if (Access.isBridge(var0)) {
			var1 = var1 + "/*bridge*/ ";
		}

		if (Access.isVarArgs(var0)) {
			var1 = var1 + "/*varargs*/ ";
		}

		if (Access.isNative(var0)) {
			var1 = var1 + "native ";
		}

		if (Access.isAbstract(var0)) {
			var1 = var1 + "abstract ";
		}

		if (Access.isStrict(var0)) {
			var1 = var1 + "strict ";
		}

		if (Access.isSynthetic(var0)) {
			var1 = var1 + "synthetic ";
		}

		return var1.trim();
	}

	public static boolean isAbstract(int var0) {
		return (var0 & Access.ACC_ABSTRACT) != 0;
	}

	public static boolean isAnnotation(int var0) {
		return (var0 & Access.ACC_ANNOTATION) != 0;
	}

	public static boolean isBridge(int var0) {
		return (var0 & Access.ACC_BRIDGE) != 0;
	}

	public static boolean isClass(int var0) {
		return !Access.isInterface(var0);
	}

	public static boolean isEnum(int var0) {
		return (var0 & Access.ACC_ENUM) != 0;
	}

	public static boolean isFinal(int var0) {
		return (var0 & Access.ACC_FINAL) != 0;
	}

	public static boolean isFlag(String var0) {
		// Yann 24/11/28: Missing "strict" flag
		// The original code was missing the "strict" case!
		// return var0.equals("public") || var0.equals("final")
		//		|| var0.equals("/*super*/") || var0.equals("abstract")
		//		|| var0.equals("interface") || var0.equals("private")
		//		|| var0.equals("protected") || var0.equals("static")
		//		|| var0.equals("native") || var0.equals("volatile")
		//		|| var0.equals("transient") || var0.equals("synchronized")
		//		|| var0.equals("class");
		return var0.equals("abstract") //
				|| var0.equals("/*annotation*/") //
				|| var0.equals("/*bridge*/") //
				|| var0.equals("class") //
				|| var0.equals("enum") //
				|| var0.equals("final") //
				|| var0.equals("interface") //
				|| var0.equals("native") //
				|| var0.equals("private") //
				|| var0.equals("protected") //
				|| var0.equals("public") //
				|| var0.equals("static") //
				|| var0.equals("strict") //
				|| var0.equals("/*super*/") // 
				|| var0.equals("synchronized") //
				|| var0.equals("synthetic") //
				|| var0.equals("transient") //
				|| var0.equals("/*varargs*/") //
				|| var0.equals("volatile");
	}

	public static boolean isInterface(int var0) {
		return (var0 & Access.ACC_INTERFACE) != 0;
	}

	public static boolean isNative(int var0) {
		return (var0 & Access.ACC_NATIVE) != 0;
	}

	public static boolean isPrivate(int var0) {
		return (var0 & Access.ACC_PRIVATE) != 0;
	}

	public static boolean isProtected(int var0) {
		return (var0 & Access.ACC_PROTECTED) != 0;
	}

	public static boolean isPublic(int var0) {
		return (var0 & Access.ACC_PUBLIC) != 0;
	}

	public static boolean isStatic(int var0) {
		return (var0 & Access.ACC_STATIC) != 0;
	}

	public static boolean isStrict(int var0) {
		return (var0 & Access.ACC_STRICT) != 0;
	}

	public static boolean isSuper(int var0) {
		return (var0 & Access.ACC_SUPER) != 0;
	}

	public static boolean isSynchronized(int var0) {
		return (var0 & Access.ACC_SYNCHRONIZED) != 0;
	}

	public static boolean isSynthetic(int d_accessFlags) {
		return (d_accessFlags & ACC_SYNTHETIC) != 0;
	}

	public static boolean isTransient(int var0) {
		return (var0 & Access.ACC_TRANSIENT) != 0;
	}

	public static boolean isVarArgs(int var0) {
		return (var0 & Access.ACC_VARARGS) != 0;
	}

	public static boolean isVolatile(int var0) {
		return (var0 & Access.ACC_VOLATILE) != 0;
	}
}
