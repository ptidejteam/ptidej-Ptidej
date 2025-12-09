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
package util.lang;

/**
 * @deprecated
 * This class doesn't include the latest modifiers
 * <p> Use {@link com.ibm.toad.cfparse.utils.Access} instead.
 */
@Deprecated(forRemoval = true)
public class Modifier {
	public static final int ABSTRACT = 0x00000400;
	public static final int DEFAULT = 0x00000000;
	public static final int FINAL = 0x00000010;
	public static final int INTERFACE = 0x00000200;
	public static final int NATIVE = 0x00000100;
	public static final int PRIVATE = 0x00000002;
	public static final int PROTECTED = 0x00000004;
	public static final int PUBLIC = 0x00000001;
	public static final int STATIC = 0x00000008;
	public static final int STRICT = 0x00000800;
	public static final int SYNCHRONIZED = 0x00000020;
	public static final int TRANSIENT = 0x00000080;
	public static final int VOLATILE = 0x00000040;

	public static boolean isAbstract(final int modifier) {
		return (modifier & Modifier.ABSTRACT) != 0;
	}
	public static boolean isDefault(final int modifier) {
		return modifier == Modifier.DEFAULT;
	}
	public static boolean isFinal(final int modifier) {
		return (modifier & Modifier.FINAL) != 0;
	}
	public static boolean isInterface(final int modifier) {
		return (modifier & Modifier.INTERFACE) != 0;
	}
	public static boolean isNative(final int modifier) {
		return (modifier & Modifier.NATIVE) != 0;
	}
	public static boolean isPrivate(final int modifier) {
		return (modifier & Modifier.PRIVATE) != 0;
	}
	public static boolean isProtected(final int modifier) {
		return (modifier & Modifier.PROTECTED) != 0;
	}
	public static boolean isPublic(final int modifier) {
		return (modifier & Modifier.PUBLIC) != 0;
	}
	public static boolean isStatic(final int modifier) {
		return (modifier & Modifier.STATIC) != 0;
	}
	public static boolean isStrict(final int modifier) {
		return (modifier & Modifier.STRICT) != 0;
	}
	public static boolean isSynchronized(final int modifier) {
		return (modifier & Modifier.SYNCHRONIZED) != 0;
	}
	public static boolean isTransient(final int modifier) {
		return (modifier & Modifier.TRANSIENT) != 0;
	}
	public static boolean isVolatile(final int modifier) {
		return (modifier & Modifier.VOLATILE) != 0;
	}
	public static String toString(final int modifier) {
		final StringBuffer sb = new StringBuffer();
		if ((modifier & Modifier.PUBLIC) != 0) {
			sb.append("public ");
		}
		if ((modifier & Modifier.PRIVATE) != 0) {
			sb.append("private ");
		}
		if ((modifier & Modifier.PROTECTED) != 0) {
			sb.append("protected ");
		}
		if ((modifier & Modifier.ABSTRACT) != 0) {
			sb.append("abstract ");
		}
		if ((modifier & Modifier.STATIC) != 0) {
			sb.append("static ");
		}
		if ((modifier & Modifier.FINAL) != 0) {
			sb.append("final ");
		}
		if ((modifier & Modifier.TRANSIENT) != 0) {
			sb.append("transient ");
		}
		if ((modifier & Modifier.VOLATILE) != 0) {
			sb.append("volatile ");
		}
		if ((modifier & Modifier.NATIVE) != 0) {
			sb.append("native ");
		}
		if ((modifier & Modifier.SYNCHRONIZED) != 0) {
			sb.append("synchronized ");
		}
		if ((modifier & Modifier.INTERFACE) != 0) {
			sb.append("interface ");
		}
		if ((modifier & Modifier.STRICT) != 0) {
			sb.append("strict ");
		}

		int len;
		if ((len = sb.length()) > 0) {
			return sb.toString().substring(0, len - 1);
		}
		return "";
	}
	public static int fromString(final String aVisibility) {
		if (aVisibility.equals("public")) {
			return Modifier.PUBLIC;
		}
		else if (aVisibility.equals("private")) {
			return Modifier.PRIVATE;
		}
		else if (aVisibility.equals("protected")) {
			return Modifier.PROTECTED;
		}
		return 0;
	}
}
