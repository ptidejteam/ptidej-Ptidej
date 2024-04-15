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

import jct.test.rsc.snpsht.utils.diffutils.state.IFSMErrorState;

public interface IPatchErrorState extends IPatchFSMState, IFSMErrorState {
	/**
	 * If header expected but not found
	 */
	public final static int MISSING_DIFF_HEADER = 0;
	/**
	 * If read line in old file different from unchanged line in patch
	 */
	public final static int MISMATCH_UNCHANGED_LINE = 1;
	/**
	 * If read line in old file different from removed line in patch
	 */
	public final static int MISMATCH_REMOVED_LINE = 2;
	/**
	 * If unknown patch line type
	 */
	public final static int UNKNOWN_PATCH_LINE_TYPE = 3;
	/**
	 * If index in old or new revision different from expected start line in
	 * header
	 */
	public final static int MISMATCH_HEADER_START_LINE = 4;
	/**
	 * If index in old or new revision different from expected start line in
	 * header
	 */
	public final static int MISMATCH_HEADER_END_LINE = 4;
}
