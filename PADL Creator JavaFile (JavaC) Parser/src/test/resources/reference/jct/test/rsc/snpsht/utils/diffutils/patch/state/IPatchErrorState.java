package jct.test.rsc.snpsht.utils.diffutils.patch.state;
import jct.test.rsc.snpsht.utils.diffutils.state.IFSMErrorState;
public interface IPatchErrorState
extends jct.test.rsc.snpsht.utils.diffutils.patch.state.IPatchFSMState, jct.test.rsc.snpsht.utils.diffutils.state.IFSMErrorState
{
final public static int MISSING_DIFF_HEADER = 0;

final public static int MISMATCH_UNCHANGED_LINE = 1;

final public static int MISMATCH_REMOVED_LINE = 2;

final public static int UNKNOWN_PATCH_LINE_TYPE = 3;

final public static int MISMATCH_HEADER_START_LINE = 4;

final public static int MISMATCH_HEADER_END_LINE = 4;


}
