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
package util.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author	Yann-Gaël Guéhéneuc
 * @since	2006/07/29
 */
public class DataStream implements DataOutput, DataInput {
	public boolean readBoolean() throws IOException {
		return false;
	}
	public byte readByte() throws IOException {
		return 0;
	}
	public char readChar() throws IOException {
		return 0;
	}
	public double readDouble() throws IOException {
		return 0;
	}
	public float readFloat() throws IOException {
		return 0;
	}
	public void readFully(final byte[] b) throws IOException {
	}
	public void readFully(final byte[] b, final int off, final int len)
			throws IOException {
	}
	public int readInt() throws IOException {
		return 0;
	}
	public String readLine() throws IOException {
		return null;
	}
	public long readLong() throws IOException {
		return 0;
	}
	public short readShort() throws IOException {
		return 0;
	}
	public int readUnsignedByte() throws IOException {
		return 0;
	}
	public int readUnsignedShort() throws IOException {
		return 0;
	}
	public String readUTF() throws IOException {
		return null;
	}
	public int skipBytes(final int n) throws IOException {
		return 0;
	}
	public void write(final byte[] b) throws IOException {
	}
	public void write(final byte[] b, final int off, final int len)
			throws IOException {
	}
	public void write(final int b) throws IOException {
	}
	public void writeBoolean(final boolean v) throws IOException {
	}
	public void writeByte(final int v) throws IOException {
	}
	public void writeBytes(final String s) throws IOException {
	}
	public void writeChar(final int v) throws IOException {
	}
	public void writeChars(final String s) throws IOException {
	}
	public void writeDouble(final double v) throws IOException {
	}
	public void writeFloat(final float v) throws IOException {
	}
	public void writeInt(final int v) throws IOException {
	}
	public void writeLong(final long v) throws IOException {
	}
	public void writeShort(final int v) throws IOException {
	}
	public void writeUTF(final String str) throws IOException {
	}
}
