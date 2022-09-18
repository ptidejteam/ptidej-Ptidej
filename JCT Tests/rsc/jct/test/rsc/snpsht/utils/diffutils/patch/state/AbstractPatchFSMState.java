package jct.test.rsc.snpsht.utils.diffutils.patch.state;
import java.util.regex.Pattern;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileReader;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileWriter;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.IDiffUtilsContext;
import jct.test.rsc.snpsht.utils.diffutils.patch.unified.UniPatch;
abstract public class AbstractPatchFSMState
implements jct.test.rsc.snpsht.utils.diffutils.patch.state.IPatchFSMState
{
final public static java.lang.String HEADER_REGEXP = "^@@ *\- *\d+ *, *\d+ *\+ *\d+ *, *\d+ *@@";

final public static java.util.regex.Pattern HEADER_PATTERN = java.util.regex.Pattern.compile(jct.test.rsc.snpsht.utils.diffutils.patch.state.AbstractPatchFSMState.HEADER_REGEXP);

final public static java.lang.String NUMBER_REGEXP = "\d+";

final public static java.util.regex.Pattern NUMBER_PATTERN = java.util.regex.Pattern.compile(jct.test.rsc.snpsht.utils.diffutils.patch.state.AbstractPatchFSMState.NUMBER_REGEXP);

final public static java.lang.String UNCHANGED_LINE_REGEXP = "^[ ][^
]*";

final public static java.util.regex.Pattern UNCHANGED_LINE_PATTERN = java.util.regex.Pattern.compile(jct.test.rsc.snpsht.utils.diffutils.patch.state.AbstractPatchFSMState.UNCHANGED_LINE_REGEXP);

final public static java.lang.String ADDED_LINE_REGEXP = "^\+[^
]*";

final public static java.util.regex.Pattern ADDED_LINE_PATTERN = java.util.regex.Pattern.compile(jct.test.rsc.snpsht.utils.diffutils.patch.state.AbstractPatchFSMState.ADDED_LINE_REGEXP);

final public static java.lang.String REMOVED_LINE_REGEXP = "^\-[^
]*";

final public static java.util.regex.Pattern REMOVED_LINE_PATTERN = java.util.regex.Pattern.compile(jct.test.rsc.snpsht.utils.diffutils.patch.state.AbstractPatchFSMState.REMOVED_LINE_REGEXP);

protected jct.test.rsc.snpsht.utils.diffutils.patch.unified.UniPatch fsm;

public void <init>(jct.test.rsc.snpsht.utils.diffutils.patch.unified.UniPatch fsm)
{
this.<init>();
this.fsm = fsm;

}

public jct.test.rsc.snpsht.utils.diffutils.patch.unified.UniPatch getFSM()
{
return this.fsm;

}

protected jct.test.rsc.snpsht.utils.diffutils.patch.context.IDiffUtilsContext getContext()
{
return this.getContext();

}


}
