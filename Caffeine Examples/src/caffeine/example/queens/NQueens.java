/*******************************************************************************
 * Copyright (c) 2002-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package caffeine.example.queens;

public class NQueens {
	public static void main(final String args[]) {
		final Board b1 = new Board(8);
		b1.placeNextQueen(0);
	}
}

/*
 * We introduce wrappers for arrays to respect object-orientation.
 */
final class ArrayIterator {
	private final int n;
	private int i;

	ArrayIterator(final int n) {
		this.n = n;
		this.i = 0;
	}
	boolean hasMoreElements() {
		return this.i < this.n;
	}
	int nextIndex() {
		return this.i++;
	}
}

final class BooleanArray {
	private final boolean array[];

	BooleanArray(final int n) {
		this.array = new boolean[n];
	}
	void write(final int i, final boolean value) {
		this.array[i] = value;
	}
	boolean read(final int i) {
		return this.array[i];
	}
	ArrayIterator elements() {
		return new ArrayIterator(this.array.length);
	}
}

final class IntegerArray {
	private final int array[];

	IntegerArray(final int n) {
		this.array = new int[n];
	}
	void write(final int i, final int value) {
		this.array[i] = value;
	}
	int read(final int i) {
		return this.array[i];
	}
	ArrayIterator elements() {
		return new ArrayIterator(this.array.length);
	}
}

final class Board {
	private final int n;
	private final BooleanArray rowfree;
	private final IntegerArray col;
	private final BooleanArray upfree;
	private final BooleanArray downfree;

	Board(final int n) {
		this.n = n;
		this.rowfree = new BooleanArray(this.n);
		this.col = new IntegerArray(this.n);
		ArrayIterator iter = this.rowfree.elements();
		while (iter.hasMoreElements()) {
			final int i = iter.nextIndex();
			this.rowfree.write(i, true);
			this.col.write(i, -1);
		}
		this.upfree = new BooleanArray(2 * this.n - 1);
		this.downfree = new BooleanArray(2 * this.n - 1);
		iter = this.upfree.elements();
		while (iter.hasMoreElements()) {
			final int i = iter.nextIndex();
			this.upfree.write(i, true);
			this.downfree.write(i, true);
		}
	}
	boolean placeNextQueen(final int column) {
		if (column == this.n) {
			this.print();
			return true;
		}
		else {
			int row = 0;
			while (row < this.n) {
				if (!this.attack(row, column)) {
					this.setQueen(row, column);
					if (this.placeNextQueen(column + 1)) {
						return true;
					}
					else {
						// Backtrack.
						this.removeQueen(row, column);
					}
				}
				row++;
			}
			return false;
		}
	}
	boolean attack(final int r, final int c) {
		return (
			!this.rowfree.read(r)
				|| !this.upfree.read(r + c)
				|| !this.downfree.read(r - c + this.n - 1));
	}
	void setQueen(final int r, final int c) {
		this.col.write(c, r);
		this.rowfree.write(r, false);
		this.upfree.write(r + c, false);
		this.downfree.write(r - c + this.n - 1, false);
	}
	void removeQueen(final int r, final int c) {
		this.col.write(c, -1);
		this.rowfree.write(r, true);
		this.upfree.write(r + c, true);
		this.downfree.write(r - c + this.n - 1, true);
	}
	void print() {
		final ArrayIterator iter = this.col.elements();
		final StringBuffer buffer = new StringBuffer();
		while (iter.hasMoreElements()) {
			final int i = iter.nextIndex();
			buffer.append(this.col.read(i));
			buffer.append(" ");
		}
		System.out.println(buffer.toString());
	}
}