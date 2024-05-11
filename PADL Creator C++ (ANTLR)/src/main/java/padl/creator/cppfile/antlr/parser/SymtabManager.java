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
package padl.creator.cppfile.antlr.parser;

import java.util.Hashtable;

/**
 * Manages the symbol table and scopes within a given compilation unit.
 */
@SuppressWarnings("rawtypes")
public class SymtabManager {
	/**
	 * Global symbol table indexed by the name of the scope (class/function).
	 */
	static Hashtable scopeTable = new Hashtable();

	/**
	 * Stack of scopes. Currently max. nesting allowed is 100.
	 */
	static Scope[] scopeStack = new Scope[100];

	/**
	 * Current depth of scope nesting.
	 */
	static int depth = 0;

	/**
	 * Dummy at the bottom of the stack so that no need to check for null.
	 */
	static {
		SymtabManager.scopeStack[SymtabManager.depth] = new Scope(null);
	}

	public static void CloseScope() {
		SymtabManager.depth--;
	}

	public static Scope GetCurScope() {
		return SymtabManager.scopeStack[SymtabManager.depth];
	}

	public static Scope GetScope(final String name) {
		int i = SymtabManager.depth;
		Scope sc = null;

		while (i >= 0) {
			if ((sc = SymtabManager.scopeStack[i--].GetScope(name)) != null) {
				return sc;
			}
		}

		return null;
	}

	/**
	 * Returns the Scope of B in A::B::C.
	 */
	public static Scope GetScopeOfFullyScopedName(final String name) {
		Scope sc;
		int i = 0, j = 0;

		if (name.indexOf("::") == -1) {
			return SymtabManager.GetScope(name);
		}

		if (name.indexOf("::") == 0) {
			sc = SymtabManager.scopeStack[1];
			j = 2;
		}
		else {
			sc = SymtabManager.GetCurScope();
		}

		final String tmp = name.substring(j, name.lastIndexOf("::"));

		while ((j = tmp.indexOf("::", i)) != -1) {
			sc = sc.GetScope(tmp.substring(i, j));
			i = j + 2;

			if (sc == null) {
				return null;
			}
		}

		if (sc == SymtabManager.GetCurScope()) {
			return SymtabManager.GetScope(tmp.substring(i, tmp.length()));
		}

		return sc.GetScope(tmp.substring(i, tmp.length()));
	}

	/**
	 * For now, we just say if it is a class name, it is OK to call it a 
	 * constructor.
	 */
	public static boolean IsCtor(final String name) {
		if (name == null) {
			return false;
		}

		if (name.indexOf("::") == -1) {
			return SymtabManager.GetScope(name) != null;
		}

		final Scope sc = SymtabManager.GetScopeOfFullyScopedName(name);

		if (sc != null && sc.parent != null) {
			return sc.parent.GetScope(name.substring(
				name.lastIndexOf("::") + 2,
				name.length())) == sc;
		}

		return false;
	}

	public static boolean IsFullyScopedTypeName(final String name) {
		if (name == null) {
			return false;
		}

		if (name.indexOf("::") == -1) {
			return SymtabManager.IsTypeName(name);
		}

		final Scope sc = SymtabManager.GetScopeOfFullyScopedName(name);

		if (sc != null) {
			return sc.IsTypeName(name.substring(
				name.lastIndexOf("::") + 2,
				name.length()));
		}

		return false;
	}

	public static boolean IsGlobalScope() {
		return SymtabManager.depth == 1 || SymtabManager.depth == 2;
	}

	public static boolean IsTypeName(final String name) {
		int i = SymtabManager.depth;

		while (i >= 0) {
			if (SymtabManager.scopeStack[i--].IsTypeName(name)) {
				return true;
			}
		}
		return false;
	}

	public static void OpenScope(final Scope sc) {
		SymtabManager.scopeStack[++SymtabManager.depth] = sc;
	}

	/**
	 * Opens a new scope (with optional name and type flag).
	 */
	@SuppressWarnings("unchecked")
	public static Scope OpenScope(final String scopeName, final boolean isType) {
		Scope newScope;

		if (scopeName != null) {
			if (isType) {
				newScope =
					new ClassScope(
						scopeName,
						SymtabManager.scopeStack[SymtabManager.depth]);
				SymtabManager.scopeStack[SymtabManager.depth].PutTypeName(
					scopeName,
					newScope);
			}
			else {
				newScope =
					new Scope(
						scopeName,
						isType,
						SymtabManager.scopeStack[SymtabManager.depth]);
			}

			SymtabManager.scopeTable.put(scopeName, newScope);
		}
		else {
			newScope = new Scope(SymtabManager.scopeStack[SymtabManager.depth]);
		}

		SymtabManager.scopeStack[++SymtabManager.depth] = newScope;
		return newScope;
	}

	public static void PutTypeName(final String name) {
		SymtabManager.scopeStack[SymtabManager.depth].PutTypeName(name);
	}
}
