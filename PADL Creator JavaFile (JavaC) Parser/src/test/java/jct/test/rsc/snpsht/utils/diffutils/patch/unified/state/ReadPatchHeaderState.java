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
import java.util.regex.Matcher;

import jct.test.rsc.snpsht.utils.diffutils.DiffHeader;
import jct.test.rsc.snpsht.utils.diffutils.patch.state.AbstractPatchFSMState;
import jct.test.rsc.snpsht.utils.diffutils.patch.state.CopyUnchangedLinesState;
import jct.test.rsc.snpsht.utils.diffutils.patch.state.IPatchErrorState;
import jct.test.rsc.snpsht.utils.diffutils.patch.state.PatchErrorState;
import jct.test.rsc.snpsht.utils.diffutils.patch.unified.UniPatch;

public class ReadPatchHeaderState extends AbstractPatchFSMState implements IUniPatchFSMState {

	public ReadPatchHeaderState(UniPatch fsm) {
		super(fsm);
	}

	@Override
	public void process() throws IOException {
		int oldRevisionStartLine, oldRevisionNumberOfLines, newRevisionStartLine, newRevisionNumberOfLines;
		DiffHeader header;

		String headerString = getContext().getDiffContext().getLastLine();
		Matcher matcher = HEADER_PATTERN.matcher(headerString);

		System.out.println(headerString);

		// Check header form
		if (!matcher.find()) {
			System.out.println("ERROR");
			this.fsm.changeState(new PatchErrorState(
				this.fsm,
				IPatchErrorState.MISSING_DIFF_HEADER,
				"Can't find diff header. Read: " + headerString));
		} else {
			matcher = NUMBER_PATTERN.matcher(headerString);

			matcher.find();
			oldRevisionStartLine = Integer.valueOf(matcher.group());
			System.out.println(oldRevisionStartLine);

			matcher.find();
			oldRevisionNumberOfLines = Integer.valueOf(matcher.group());
			System.out.println(oldRevisionNumberOfLines);

			matcher.find();
			newRevisionStartLine = Integer.valueOf(matcher.group());
			System.out.println(newRevisionStartLine);

			matcher.find();
			newRevisionNumberOfLines = Integer.valueOf(matcher.group());
			System.out.println(newRevisionNumberOfLines);

			header =
				new DiffHeader(
					oldRevisionStartLine,
					oldRevisionNumberOfLines,
					newRevisionStartLine,
					newRevisionNumberOfLines);
			
			this.fsm.changeState(new CopyUnchangedLinesState(this.fsm, header));
		}
	}

}
