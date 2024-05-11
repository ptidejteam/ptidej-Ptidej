/* (c) Copyright 2008 and following years, Julien Tanteri,
 * University of Montreal.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the author, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package jct.test.rsc.snpsht.utils.diffutils;

/**
 * Data structure witch represent a diff header
 * @author Julien Tantéri
 */
public class DiffHeader {
	private Range oldRevisionRange, newRevisionRange;

	public DiffHeader(Range sourceRange, Range patchRange) {
		this.newRevisionRange = patchRange;
		this.oldRevisionRange = sourceRange;
	}

	public DiffHeader(int oldRevisionStartLine, int oldRevisionNumberOfLines, int newRevisionStartLine, int newRevisionNumberOfLines) {
		this.newRevisionRange = new Range(newRevisionStartLine, newRevisionNumberOfLines);
		this.oldRevisionRange = new Range(oldRevisionStartLine, oldRevisionNumberOfLines);
	}

	public void setOlRevisionRange(Range sourceRange) {
		this.oldRevisionRange = sourceRange;
	}

	public Range getOldRevisionRange() {
		return this.oldRevisionRange;
	}

	public void setNewRevisionRange(Range patchRange) {
		this.newRevisionRange = patchRange;
	}

	public Range getNewRevisionRange() {
		return this.newRevisionRange;
	}
	
	public class Range {
		private int startLine, numberOfLines;

		public Range(int startLine, int numberOfLines) {
			this.numberOfLines = numberOfLines;
			this.startLine = startLine;
		}

		public void setStartLine(int startLine) {
			this.startLine = startLine;
		}

		public int getStartLine() {
			return this.startLine;
		}

		public void setNumberOfLines(int numberOfLines) {
			this.numberOfLines = numberOfLines;
		}

		public int getNumberOfLines() {
			return this.numberOfLines;
		}
	}
}
