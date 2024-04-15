/*
 * (c) Copyright 2008 and following years, Julien Tanteri, University of
 * Montreal.
 * 
 * Use and copying of this software and preparation of derivative works based
 * upon this software are permitted. Any copy of this software or of any
 * derivative work must include the above copyright notice of the author, this
 * paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS ALL
 * WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, AND NOT
 * WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN, ANY LIABILITY FOR DAMAGES
 * RESULTING FROM THE SOFTWARE OR ITS USE IS EXPRESSLY DISCLAIMED, WHETHER
 * ARISING IN CONTRACT, TORT (INCLUDING NEGLIGENCE) OR STRICT LIABILITY, EVEN IF
 * THE AUTHOR IS ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package jct.test.rsc.snpsht.utils.diffutils.patch.context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ContextFileReader implements IContextFileReader {
	private BufferedReader reader;
	private String lastLine;
	// True if end of file reached
	private boolean eofReached;
	private int currentLineNumber;

	public ContextFileReader(InputStream source) {
		this.eofReached = false;
		this.currentLineNumber = 0;
		this.reader = new BufferedReader(new InputStreamReader(source));
	}

	@Override
	public String readLine() throws IOException {
		this.lastLine = this.reader.readLine();
		if (this.lastLine == null)
			this.eofReached = true;

		this.currentLineNumber++;

		return this.lastLine;
	}

	@Override
	public String getLastLine() {
		return this.lastLine;
	}

	@Override
	public int getLineNumber() {
		return this.currentLineNumber;
	}

	@Override
	public boolean isEOFReached() {
		return this.eofReached;
	}

	@Override
	public void close() throws IOException {
		this.reader.close();
	}

}
