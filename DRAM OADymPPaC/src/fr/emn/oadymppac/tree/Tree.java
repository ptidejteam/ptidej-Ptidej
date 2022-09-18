/*
 * $Id: Tree.java,v 1.2 2006/08/11 23:11:03 guehene Exp $
 *
 * Copyright (c) 2001-2002 Jean-Daniel Fekete, Mohammad Ghoniem and
 * Ecole des Mines de Nantes.  All rights reserved.
 *
 * This software is proprietary information of Jean-Daniel Fekete and
 * Ecole des Mines de Nantes.  You shall use it only in accordance
 * with the terms of the license agreement you accepted when
 * downloading this software.  The license is available in the file
 * licence.txt and at the following URL:
 * http://www.emn.fr/fekete/oadymppac/licence.html
 */

package fr.emn.oadymppac.tree;

import java.util.NoSuchElementException;

public class Tree {
	protected int size;
	protected int[] child;
	protected int[] next;
	protected int[] last;
	protected int[] parent;

	public static final int ROOT = 0;
	public static final int NIL = 0;

	public Tree() {
		this(10);
	}
	public Tree(int capacity) {
		if (capacity < 1) {
			capacity = 1;
		}
		this.size = 1;
		this.child = new int[capacity];
		this.next = new int[capacity];
		this.last = new int[capacity];
		this.parent = new int[capacity];
	}
	public Tree(final Tree other) {
		this.size = other.size;
		this.child = (int[]) other.child.clone();
		this.next = (int[]) other.next.clone();
		this.last = (int[]) other.last.clone();
		this.parent = (int[]) other.parent.clone();
	}

	public int add(final int par) {
		if (par < 0) {
			throw new IndexOutOfBoundsException(par + "< 0 ");
		}
		else if (par >= this.size()) {
			throw new IndexOutOfBoundsException(par + " >= " + this.size);
		}
		final int node = this.size;
		this.resize(this.size + 1);
		this.parent[node] = par;
		this.next[node] = Tree.NIL;
		if (this.last[par] == Tree.NIL) {
			this.child[par] = node;
		}
		else {
			this.next[this.last[par]] = node;
		}
		this.last[par] = node;
		return node;
	}

	public int capacity() {
		return this.child.length;
	}

	public NodeEnumeration children(final int node) {
		final int c = this.child[node];
		return new NodeEnumeration() {
			int child = c;

			public boolean hasMoreElements() {
				return Tree.this.next[this.child] != 0;
			}

			public int nextElement() {
				synchronized (Tree.this) {
					if (Tree.this.next[this.child] != 0) {
						this.child = Tree.this.next[this.child];
						return this.child;
					}
				}
				throw new NoSuchElementException("Tree children Enumeration");
			}
		};
	}

	public NodeEnumeration elements() {
		return new NodeEnumeration() {
			int count = 0;

			public boolean hasMoreElements() {
				return this.count < Tree.this.size;
			}

			public int nextElement() {
				synchronized (Tree.this) {
					if (this.count < Tree.this.size) {
						return this.count++;
					}
				}
				throw new NoSuchElementException("Tree Enumeration");
			}
		};
	}

	public final int firstChild(final int node) {
		return this.child[node];
	}

	public boolean isEmpty() {
		return this.size == 1;
	}

	public final boolean isLeaf(final int node) {
		return this.child[node] == Tree.NIL;
	}

	public final int lastChild(final int node) {
		return this.last[node];
	}

	public final int next(final int node) {
		return this.next[node];
	}

	public final int parent(final int node) {
		return this.parent[node];
	}

	public void reserve(final int sz) {
		if (this.child != null && sz <= this.child.length) {
			return;
		}
		final int[] newchild = new int[sz];
		final int[] newnext = new int[sz];
		final int[] newlast = new int[sz];
		final int[] newparent = new int[sz];
		if (this.child != null) {
			System.arraycopy(this.child, 0, newchild, 0, this.size);
			System.arraycopy(this.next, 0, newnext, 0, this.size);
			System.arraycopy(this.last, 0, newlast, 0, this.size);
			System.arraycopy(this.parent, 0, newparent, 0, this.size);
		}
		this.child = newchild;
		this.next = newnext;
		this.last = newlast;
		this.parent = newparent;
	}

	public void resize(final int sz) {
		if (this.capacity() < sz) {
			this.reserve(this.size * 2 < sz ? sz : this.size * 2);
		}
		this.size = sz;
	}

	public int size() {
		return this.size;
	}

	public void visit(final TreeVisitor visitor) {
		this.visit(visitor, Tree.ROOT);
	}

	public void visit(final TreeVisitor visitor, final int node) {
		visitor.preorder(node);
		int i = this.firstChild(node);
		if (i != Tree.NIL) {
			this.visit(visitor, i);
			i = this.next(i);
			visitor.inorder(node);
			while (i != Tree.NIL) {
				this.visit(visitor, i);
				i = this.next(i);
			}
		}
		else {
			visitor.inorder(node);
		}
		visitor.postorder(node);
	}
}
