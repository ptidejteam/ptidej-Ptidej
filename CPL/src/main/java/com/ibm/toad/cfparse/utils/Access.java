/* Decompiler 196ms, total 408ms, lines 276 */
package com.ibm.toad.cfparse.utils;

import java.util.StringTokenizer;

public final class Access {
	public static final short ACC_PUBLIC = 1;
	public static final short ACC_PRIVATE = 2;
	public static final short ACC_PROTECTED = 4;
	public static final short ACC_STATIC = 8;
	public static final short ACC_FINAL = 16;
	public static final short ACC_SUPER = 32;
	public static final short ACC_SYNCHRONIZED = 32;
	public static final short ACC_VOLATILE = 64;
	public static final short ACC_TRANSIENT = 128;
	public static final short ACC_NATIVE = 256;
	public static final short ACC_INTERFACE = 512;
	public static final short ACC_ABSTRACT = 1024;
	public static final short ACC_STRICT = 2048;
	public static final short ACC_SYNTHETIC = 4096;

	public static boolean isAbstract(int var0) {
		return (var0 & 1024) != 0;
	}

	public static boolean isPublic(int var0) {
		return (var0 & 1) != 0;
	}

	public static boolean isStatic(int var0) {
		return (var0 & 8) != 0;
	}

	public static boolean isSynchronized(int var0) {
		return (var0 & 32) != 0;
	}

	public static String getClassAsString(int var0) {
		String var1 = "";
		if (isPublic(var0)) {
			var1 = var1 + "public ";
		}

		if (isFinal(var0)) {
			var1 = var1 + "final ";
		}

		if (isSuper(var0)) {
			var1 = var1 + "/*super*/ ";
		}

		if (isInterface(var0)) {
			var1 = var1 + "interface ";
		}

		if (isAbstract(var0)) {
			var1 = var1 + "abstract ";
		}

		if (isClass(var0)) {
			var1 = var1 + "class ";
		}

		return var1;
	}

	public static String getMethodAsString(int var0) {
		String var1 = "";
		if (isPublic(var0)) {
			var1 = var1 + "public ";
		}

		if (isFinal(var0)) {
			var1 = var1 + "final ";
		}

		if (isPrivate(var0)) {
			var1 = var1 + "private ";
		}

		if (isProtected(var0)) {
			var1 = var1 + "protected ";
		}

		if (isStatic(var0)) {
			var1 = var1 + "static ";
		}

		if (isSynchronized(var0)) {
			var1 = var1 + "synchronized ";
		}

		if (isNative(var0)) {
			var1 = var1 + "native ";
		}

		if (isAbstract(var0)) {
			var1 = var1 + "abstract ";
		}

		return var1;
	}

	public static String getFieldAsString(int var0) {
		String var1 = "";
		if (isPublic(var0)) {
			var1 = var1 + "public ";
		}

		if (isPrivate(var0)) {
			var1 = var1 + "private ";
		}

		if (isProtected(var0)) {
			var1 = var1 + "protected ";
		}

		if (isFinal(var0)) {
			var1 = var1 + "final ";
		}

		if (isStatic(var0)) {
			var1 = var1 + "static ";
		}

		if (isVolatile(var0)) {
			var1 = var1 + "volatile ";
		}

		if (isTransient(var0)) {
			var1 = var1 + "transient ";
		}

		return var1;
	}

	public static int getFromString(String var0) {
		int var1 = 0;
		StringTokenizer var2 = new StringTokenizer(var0, " ");

		while (var2.hasMoreTokens()) {
			String var3 = var2.nextToken().trim();
			if (var3.equals("public")) {
				var1 |= 1;
			}
			else if (var3.equals("final")) {
				var1 |= 16;
			}
			else if (var3.equals("/*super*/")) {
				var1 |= 32;
			}
			else if (var3.equals("abstract")) {
				var1 |= 1024;
			}
			else if (var3.equals("interface")) {
				var1 |= 512;
			}
			else if (var3.equals("private")) {
				var1 |= 2;
			}
			else if (var3.equals("protected")) {
				var1 |= 4;
			}
			else if (var3.equals("final")) {
				var1 |= 16;
			}
			else if (var3.equals("static")) {
				var1 |= 8;
			}
			else if (var3.equals("volatile")) {
				var1 |= 64;
			}
			else if (var3.equals("transient")) {
				var1 |= 128;
			}
			else if (var3.equals("synchronized")) {
				var1 |= 32;
			}
			else if (var3.equals("native")) {
				var1 |= 256;
			}
			else if (var3.equals("class")) {
				var1 |= 0;
			}
		}

		return var1;
	}

	public static boolean isClass(int var0) {
		return !isInterface(var0);
	}

	public static boolean isStrict(int var0) {
		return (var0 & 2048) != 0;
	}

	public static boolean isInterface(int var0) {
		return (var0 & 512) != 0;
	}

	public static boolean isPrivate(int var0) {
		return (var0 & 2) != 0;
	}

	public static boolean isTransient(int var0) {
		return (var0 & 128) != 0;
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
		return var0.equals("public") || var0.equals("final")
				|| var0.equals("/*super*/") || var0.equals("abstract")
				|| var0.equals("interface") || var0.equals("private")
				|| var0.equals("protected") || var0.equals("static")
				|| var0.equals("native") || var0.equals("volatile")
				|| var0.equals("transient") || var0.equals("synchronized")
				|| var0.equals("class") || var0.equals("strict");
	}

	public static boolean isVolatile(int var0) {
		return (var0 & 64) != 0;
	}

	public static boolean isSuper(int var0) {
		return (var0 & 32) != 0;
	}

	public static boolean isProtected(int var0) {
		return (var0 & 4) != 0;
	}

	public static boolean isFinal(int var0) {
		return (var0 & 16) != 0;
	}

	public static String getAsString(int var0) {
		String var1 = "";
		if (isPublic(var0)) {
			var1 = var1 + "public ";
		}

		if (isFinal(var0)) {
			var1 = var1 + "final ";
		}

		if (isSuper(var0)) {
			var1 = var1 + "/*super*/ ";
		}

		if (isAbstract(var0)) {
			var1 = var1 + "abstract ";
		}

		if (isInterface(var0)) {
			var1 = var1 + "interface ";
		}

		if (isPrivate(var0)) {
			var1 = var1 + "private ";
		}

		if (isProtected(var0)) {
			var1 = var1 + "protected ";
		}

		if (isSynchronized(var0)) {
			var1 = var1 + "synchronized ";
		}

		if (isStatic(var0)) {
			var1 = var1 + "static ";
		}

		if (isNative(var0)) {
			var1 = var1 + "native ";
		}

		if (isVolatile(var0)) {
			var1 = var1 + "volatile ";
		}

		if (isTransient(var0)) {
			var1 = var1 + "transient ";
		}

		if (isClass(var0)) {
			var1 = var1 + "class ";
		}

		return var1;
	}

	public static boolean isNative(int var0) {
		return (var0 & ACC_NATIVE) != 0;
	}

	public static boolean isSynthetic(int d_accessFlags) {
		return (d_accessFlags & ACC_SYNTHETIC) != 0;
	}
}
