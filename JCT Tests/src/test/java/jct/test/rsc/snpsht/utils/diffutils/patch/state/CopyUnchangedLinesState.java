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
package jct.test.rsc.snpsht.utils.diffutils.patch.state;

import java.io.IOException;

import jct.test.rsc.snpsht.utils.diffutils.DiffHeader;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileReader;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileWriter;
import jct.test.rsc.snpsht.utils.diffutils.patch.unified.UniPatch;
import jct.test.rsc.snpsht.utils.diffutils.patch.unified.state.PatchLineState;

public class CopyUnchangedLinesState extends AbstractPatchFSMState {
	private DiffHeader header;

	public CopyUnchangedLinesState(UniPatch fsm, DiffHeader header) {
		super(fsm);

		this.header = header;
	}

	@Override
	public void process() throws IOException {
		String line;
		IContextFileReader oldRevision = getContext().getOldRevisionContext();
		IContextFileWriter newRevision = getContext().getNewRevisionContext();

		// While not EOF and header start range not reached
		while (oldRevision.getLineNumber() < this.header
			.getOldRevisionRange()
			.getStartLine() - 1
				&& (line = oldRevision.readLine()) != null) {

			// Copy unchanged lines
			newRevision.writeLine(line);

			System.out.println(line + " - " + oldRevision.getLineNumber()
					+ " - " + newRevision.getLineNumber());
		}

		System.out.println(oldRevision.isEOFReached());
		System.out.println(this.header.getOldRevisionRange().getStartLine());

		// If start lines of header was respected
		if (oldRevision.getLineNumber() + 1 == this.header
			.getOldRevisionRange()
			.getStartLine()
				&& newRevision.getLineNumber() + 1 == this.header
					.getNewRevisionRange()
					.getStartLine()) {
			this.fsm.changeState(new PatchLineState(this.fsm, this.header));
		} else {
			System.out.println("Error");
			this.fsm
				.changeState(new PatchErrorState(
					this.fsm,
					IPatchErrorState.MISMATCH_HEADER_START_LINE,
					"Index in old/new ("
							+ oldRevision.getLineNumber()
							+ "/"
							+ newRevision.getLineNumber()
							+ ") revision mismatch with start line index in header ("
							+ this.header.getOldRevisionRange().getStartLine()
							+ "/"
							+ this.header.getNewRevisionRange().getStartLine()
							+ ")"));
		}
	}
}
