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

import java.util.regex.Pattern;

import jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileReader;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileWriter;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.IDiffUtilsContext;
import jct.test.rsc.snpsht.utils.diffutils.patch.unified.UniPatch;

public abstract class AbstractPatchFSMState implements IPatchFSMState {
	public static final String HEADER_REGEXP = "^@@ *\\- *\\d+ *, *\\d+ *\\+ *\\d+ *, *\\d+ *@@";
	public static final Pattern HEADER_PATTERN = Pattern.compile(HEADER_REGEXP);
	
	public static final String NUMBER_REGEXP = "\\d+";
	public static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGEXP);
	
	public static final String UNCHANGED_LINE_REGEXP = "^[ ][^\n]*";
	public static final Pattern UNCHANGED_LINE_PATTERN = Pattern.compile(UNCHANGED_LINE_REGEXP);
	
	public static final String ADDED_LINE_REGEXP = "^\\+[^\n]*";
	public static final Pattern ADDED_LINE_PATTERN = Pattern.compile(ADDED_LINE_REGEXP);
	
	public static final String REMOVED_LINE_REGEXP = "^\\-[^\n]*";
	public static final Pattern REMOVED_LINE_PATTERN = Pattern.compile(REMOVED_LINE_REGEXP);
	
	protected UniPatch fsm;

	public AbstractPatchFSMState(UniPatch fsm) {
		this.fsm = fsm;
	}

	@Override
	public UniPatch getFSM() {
		return this.fsm;
	}

	protected IDiffUtilsContext<IContextFileReader, IContextFileWriter> getContext() {
		return this.fsm.getContext();
	}
}
