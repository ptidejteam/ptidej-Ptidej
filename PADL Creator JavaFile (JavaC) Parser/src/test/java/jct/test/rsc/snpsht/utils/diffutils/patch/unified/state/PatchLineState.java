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
package jct.test.rsc.snpsht.utils.diffutils.patch.unified.state;

import java.io.IOException;

import jct.test.rsc.snpsht.utils.diffutils.DiffHeader;
import jct.test.rsc.snpsht.utils.diffutils.patch.state.AbstractPatchFSMState;
import jct.test.rsc.snpsht.utils.diffutils.patch.state.CopyEndOfFileState;
import jct.test.rsc.snpsht.utils.diffutils.patch.state.IPatchErrorState;
import jct.test.rsc.snpsht.utils.diffutils.patch.state.PatchErrorState;
import jct.test.rsc.snpsht.utils.diffutils.patch.unified.UniPatch;

public class PatchLineState extends AbstractPatchFSMState implements IUniPatchFSMState {
	private DiffHeader header;

	public PatchLineState(UniPatch fsm, DiffHeader header) {
		super(fsm);

		this.header = header;
	}

	private boolean checkHeaderContract() {
		int indOldCont, indOldHead, indNewCont, indNewHead;

		System.out.println(this.header.getOldRevisionRange().getStartLine()
				+ this.header.getOldRevisionRange().getNumberOfLines());
		System.out
			.println(getContext().getOldRevisionContext().getLineNumber());
		System.out.println(this.header.getNewRevisionRange().getStartLine()
				+ this.header.getNewRevisionRange().getNumberOfLines());
		System.out
			.println(getContext().getNewRevisionContext().getLineNumber());

		indOldCont =
			this.header.getOldRevisionRange().getStartLine()
					+ this.header.getOldRevisionRange().getNumberOfLines();
		indOldHead = getContext().getOldRevisionContext().getLineNumber();

		indNewCont =
			this.header.getNewRevisionRange().getStartLine()
					+ this.header.getNewRevisionRange().getNumberOfLines();
		indNewHead = getContext().getNewRevisionContext().getLineNumber();

		// If header indexes was respected
		return (indOldHead + 1 == indOldCont && indNewHead + 1 == indNewCont);
	}

	@Override
	public void process() throws IOException {
		String patchLine = getContext().getDiffContext().readLine();
		String oldLine;

		System.out.println("==========> " + patchLine);

		// if EOF
		if (patchLine == null) {

			// If header indexes was respected
			if (checkHeaderContract()) {
				// Copy end of old file
				this.fsm.changeState(new CopyEndOfFileState(this.fsm));
			} else {
				this.fsm
					.changeState(new PatchErrorState(
						this.fsm,
						IPatchErrorState.MISMATCH_HEADER_END_LINE,
						"Index in old/new revision mismatch with start line index in header "));
			}
		} else {
			// Check line type
			if (HEADER_PATTERN.matcher(patchLine).find()) {
				// If header indexes was respected
				if (checkHeaderContract()) {
					this.fsm.changeState(new ReadPatchHeaderState(this.fsm));
				} else {
					this.fsm
						.changeState(new PatchErrorState(
							this.fsm,
							IPatchErrorState.MISMATCH_HEADER_END_LINE,
							"Index in old/new revision mismatch with start line index in header "));
				}
			} else {
				if (UNCHANGED_LINE_PATTERN.matcher(patchLine).find()) {
					// Trim first character
					patchLine = patchLine.substring(1);
					// Check if really unchanged
					oldLine = getContext().getOldRevisionContext().readLine();
					if (oldLine.compareTo(patchLine) != 0) {
						// Error
						this.fsm
							.changeState(new PatchErrorState(
								this.fsm,
								IPatchErrorState.MISMATCH_UNCHANGED_LINE,
								"Missmatch unchanged line.\nFind in old revision: "
										+ oldLine + "\nShould patch: "
										+ patchLine));
						return;
					} else {
						getContext().getNewRevisionContext().writeLine(
							patchLine);
					}
				} else if (ADDED_LINE_PATTERN.matcher(patchLine).find()) {
					// Trim first character
					patchLine = patchLine.substring(1);
					getContext().getNewRevisionContext().writeLine(patchLine);
				} else if (REMOVED_LINE_PATTERN.matcher(patchLine).find()) {
					// Trim first character
					patchLine = patchLine.substring(1);
					// Check if really unchanged
					oldLine = getContext().getOldRevisionContext().readLine();
					if (oldLine.compareTo(patchLine) != 0) {
						// Error
						this.fsm
							.changeState(new PatchErrorState(
								this.fsm,
								IPatchErrorState.MISMATCH_REMOVED_LINE,
								"Missmatch removed line.\nFind in old revision: "
										+ oldLine + "\nShould patch: "
										+ patchLine));
						return;
					}
				} else {
					// Error
					this.fsm.changeState(new PatchErrorState(
						this.fsm,
						IPatchErrorState.UNKNOWN_PATCH_LINE_TYPE,
						"Unknow patch line type for " + patchLine));
					return;
				}

				this.fsm.changeState(this);
			}
		}
	}
}
