/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * JasperReports - Free Java report-generating library.
 * Copyright (C) 2001-2006 JasperSoft Corporation http://www.jaspersoft.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 * 
 * JasperSoft Corporation
 * 303 Second Street, Suite 450 North
 * San Francisco, CA 94107
 * http://www.jaspersoft.com
 */
package net.sf.jasperreports.engine.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.sf.jasperreports.engine.JRRuntimeException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: FileBufferedOutputStream.java,v 1.1 2008/09/29 16:20:17 guehene Exp $
 */
public class FileBufferedOutputStream extends OutputStream 
{
	
	private static final Log log = LogFactory.getLog(FileBufferedOutputStream.class);
	
	/**
	 * Specifies the maximum in-memory buffer length that triggers the creation of a temporary file on disk to store further content sent to this output stream.  
	 */
	public static final String PROPERTY_MEMORY_THRESHOLD = JRProperties.PROPERTY_PREFIX + "file.buffer.os.memory.threshold";
	//public static final int DEFAULT_MEMORY_THRESHOLD = 1 << 18;
	public static final int INFINIT_MEMORY_THRESHOLD = -1;
	public static final int DEFAULT_INITIAL_MEMORY_BUFFER_SIZE = 1 << 16;
	public static final int DEFAULT_INPUT_BUFFER_LENGTH = 1 << 14;
	
	private final int memoryThreshold;
	private final int initialMemoryBufferSize;
	private final int inputBufferLength;
	
	private final ByteArrayOutputStream memoryOutput;
	private int size;
	private File file;
	private BufferedOutputStream fileOutput;
	private boolean closed;
	private boolean disposed;
	
	public FileBufferedOutputStream() {
		this(JRProperties.getIntegerProperty(PROPERTY_MEMORY_THRESHOLD, INFINIT_MEMORY_THRESHOLD), DEFAULT_INITIAL_MEMORY_BUFFER_SIZE, DEFAULT_INPUT_BUFFER_LENGTH);
	}
	
	public FileBufferedOutputStream(int memoryThreshold) {
		this(memoryThreshold, DEFAULT_INITIAL_MEMORY_BUFFER_SIZE, DEFAULT_INPUT_BUFFER_LENGTH);
	}
	
	public FileBufferedOutputStream(int memoryThreshold, int initialMemoryBufferSize) {
		this(memoryThreshold, initialMemoryBufferSize, DEFAULT_INPUT_BUFFER_LENGTH);
	}
	
	public FileBufferedOutputStream(int memoryThreshold, int initialMemoryBufferSize, int inputBufferLength) {
		this.memoryThreshold = memoryThreshold;
		this.initialMemoryBufferSize = initialMemoryBufferSize;
		this.inputBufferLength = inputBufferLength;
		
		this.size = 0;
		if (this.memoryThreshold == 0)
		{
			this.memoryOutput = null;
		}
		else
		{
			int initialSize = this.initialMemoryBufferSize;
			if (initialSize > this.memoryThreshold)
			{
				initialSize = this.memoryThreshold;
			}
			this.memoryOutput = new ByteArrayOutputStream(initialSize);
		}
	}

	public void write(int b) throws IOException {
		checkClosed();
		
		if (availableMemorySpace() > 0) {
			this.memoryOutput.write(b);
		} else {
			ensureFileOutput().write(b);
		}
		
		++this.size;
	}

	protected int availableMemorySpace() {
		int availableMemorySpace;
		if (this.memoryOutput != null
				&& (this.memoryThreshold < 0 || this.memoryOutput.size() < this.memoryThreshold)) {
			availableMemorySpace = this.memoryThreshold - this.memoryOutput.size();
		} else {
			availableMemorySpace = 0;
		}
		return availableMemorySpace;
	}
	
	protected BufferedOutputStream ensureFileOutput() throws IOException, FileNotFoundException {
		if (this.fileOutput == null) {
			this.file = File.createTempFile("file.buff.os.", ".tmp");
			FileOutputStream fileOutputStream = new FileOutputStream(this.file);
			this.fileOutput = new BufferedOutputStream(fileOutputStream);
		}
		return this.fileOutput;
	}

	public void write(byte[] b, int off, int len) throws IOException {
		checkClosed();
		
		int memoryLen = availableMemorySpace();
		if (len < memoryLen) {
			memoryLen = len;
		}
		
		if (memoryLen > 0) {
			this.memoryOutput.write(b, off, memoryLen);
		}
		
		if (memoryLen < len) {
			ensureFileOutput().write(b, off + memoryLen, len - memoryLen);
		}
		
		this.size += len;
	}

	public void checkClosed() {
		if (this.closed) {
			throw new JRRuntimeException("Output stream already closed.");
		}
	}

	public void close() throws IOException {
		if (!this.closed && this.fileOutput != null) {
			this.fileOutput.flush();
			this.fileOutput.close();
		}
		
		this.closed = true;
	}

	public void flush() throws IOException {
		if (this.fileOutput != null) {
			this.fileOutput.flush();
		}
	}

	public int size() {
		return this.size;
	}
	
	public void writeData(OutputStream out) throws IOException {
		if (!this.closed) {
			close();
		}

		if (this.memoryOutput != null) {
			this.memoryOutput.writeTo(out);
		}
		
		if (this.file != null) {
			FileInputStream fileInput = new FileInputStream(this.file);
			boolean inputClosed = false;
			try {
				byte[] buffer = new byte[this.inputBufferLength];
				int read;
				while((read = fileInput.read(buffer)) > 0) {
					out.write(buffer, 0, read);
				}
				fileInput.close();
				inputClosed = true;
			} finally {
				if (!inputClosed) {
					try {
						fileInput.close();
					} catch (IOException e) {
						log.warn("Could not close file input stream", e);
					}
				}
			}
		}
	}
	
	public void dispose() {
		if (this.disposed) {
			return;
		}
		
		boolean success = true;
		if (!this.closed && this.fileOutput != null) {
			try {
				this.fileOutput.close();
			} catch (IOException e) {
				log.warn("Error while closing the temporary file output stream", e);
				success = false;
			}
		}
		
		if (this.file != null && !this.file.delete()) {
			log.warn("Error while deleting the temporary file");
			success = false;
		}
		
		this.disposed = success;
	}

	protected void finalize() throws Throwable {
		dispose();
		super.finalize();
	}
	
	public InputStream getDataInputStream() throws IOException
	{
		if (!this.closed)
		{
			close();
		}
		
		return new DataStream();
	}
	
	protected class DataStream extends InputStream
	{
		private int memoryIdx;
		private final byte[] memoryData;
		private final InputStream fileInput;
		
		public DataStream() throws FileNotFoundException
		{
			this.memoryIdx = 0;
			this.memoryData = FileBufferedOutputStream.this.memoryOutput == null ? new byte[0] : FileBufferedOutputStream.this.memoryOutput.toByteArray(); 
			this.fileInput = FileBufferedOutputStream.this.file == null ? null : new BufferedInputStream(new FileInputStream(FileBufferedOutputStream.this.file));
		}
		
		public synchronized int read() throws IOException
		{
			int read;
			if (this.memoryIdx < this.memoryData.length)
			{
				read = this.memoryData[this.memoryIdx] & 0xff;
				++this.memoryIdx;
			}
			else if (this.fileInput != null)
			{
				read = this.fileInput.read();
			}
			else
			{
				read = -1;
			}
			return read;
		}
		
		public synchronized int read(byte b[], int off, int len) throws IOException
		{
			if (len <= 0)
			{
				return 0;
			}
			
			int read;
			if (this.memoryIdx < this.memoryData.length)
			{
				read = len;
				if (read > this.memoryData.length - this.memoryIdx)
				{
					read = this.memoryData.length - this.memoryIdx;
				}
				
				System.arraycopy(this.memoryData, this.memoryIdx, b, off, read);
				this.memoryIdx += read;
			}
			else
			{
				read = 0;
			}
			
			if (read < len && this.fileInput != null)
			{
				int readFile = this.fileInput.read(b, off + read, len - read);
				if (readFile > 0)
				{
					read += readFile;
				}
			}
			
			return read == 0 ? -1 : read;
		}

		public void close() throws IOException
		{
			if (this.fileInput != null)
			{
				this.fileInput.close();
			}
		}

		public synchronized int available() throws IOException
		{
			int available = this.memoryData.length - this.memoryIdx;
			if (this.fileInput != null)
			{
				available += this.fileInput.available();
			}
			return available;
		}

		public synchronized long skip(long n) throws IOException
		{
			if (n <= 0)
			{
				return 0;
			}
			
			long skipped;
			if (this.memoryIdx < this.memoryData.length)
			{
				skipped = n;
				if (skipped > this.memoryData.length - this.memoryIdx)
				{
					skipped = this.memoryData.length - this.memoryIdx;
				}
				
				this.memoryIdx += skipped;
			}
			else
			{
				skipped = 0;
			}
			
			if (skipped < n && this.fileInput != null)
			{
				skipped += this.fileInput.skip(n - skipped);
			}
			
			return skipped;
		}
		
	}
}
