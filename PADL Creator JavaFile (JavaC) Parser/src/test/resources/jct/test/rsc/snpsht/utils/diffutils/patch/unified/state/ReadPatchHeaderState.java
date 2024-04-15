package jct.test.rsc.snpsht.utils.diffutils.patch.unified.state;
import java.io.IOException;
import java.util.regex.Matcher;
import jct.test.rsc.snpsht.utils.diffutils.DiffHeader;
import jct.test.rsc.snpsht.utils.diffutils.patch.state.AbstractPatchFSMState;
import jct.test.rsc.snpsht.utils.diffutils.patch.state.CopyUnchangedLinesState;
import jct.test.rsc.snpsht.utils.diffutils.patch.state.IPatchErrorState;
import jct.test.rsc.snpsht.utils.diffutils.patch.state.PatchErrorState;
import jct.test.rsc.snpsht.utils.diffutils.patch.unified.UniPatch;
public class ReadPatchHeaderState
extends jct.test.rsc.snpsht.utils.diffutils.patch.state.AbstractPatchFSMState
implements jct.test.rsc.snpsht.utils.diffutils.patch.unified.state.IUniPatchFSMState
{
public void <init>(jct.test.rsc.snpsht.utils.diffutils.patch.unified.UniPatch fsm)
{
this.<init>(fsm);

}

public void process() throws java.io.IOException
{
int oldRevisionStartLine;
int oldRevisionNumberOfLines;
int newRevisionStartLine;
int newRevisionNumberOfLines;
jct.test.rsc.snpsht.utils.diffutils.DiffHeader header;
java.lang.String headerString = this.getContext().getDiffContext().getLastLine();
java.util.regex.Matcher matcher = jct.test.rsc.snpsht.utils.diffutils.patch.state.AbstractPatchFSMState.HEADER_PATTERN.matcher(headerString);
java.lang.System.out.println(headerString);
if(! matcher.find()) 
{
java.lang.System.out.println("ERROR");
this.changeState(new jct.test.rsc.snpsht.utils.diffutils.patch.state.PatchErrorState(this.fsm, jct.test.rsc.snpsht.utils.diffutils.patch.state.IPatchErrorState.MISSING_DIFF_HEADER, "Can't find diff header. Read: " + headerString));

}
 else 
{
matcher = jct.test.rsc.snpsht.utils.diffutils.patch.state.AbstractPatchFSMState.NUMBER_PATTERN.matcher(headerString);
matcher.find();
oldRevisionStartLine = java.lang.Integer.valueOf(matcher.group());
java.lang.System.out.println(oldRevisionStartLine);
matcher.find();
oldRevisionNumberOfLines = java.lang.Integer.valueOf(matcher.group());
java.lang.System.out.println(oldRevisionNumberOfLines);
matcher.find();
newRevisionStartLine = java.lang.Integer.valueOf(matcher.group());
java.lang.System.out.println(newRevisionStartLine);
matcher.find();
newRevisionNumberOfLines = java.lang.Integer.valueOf(matcher.group());
java.lang.System.out.println(newRevisionNumberOfLines);
header = new jct.test.rsc.snpsht.utils.diffutils.DiffHeader(oldRevisionStartLine, oldRevisionNumberOfLines, newRevisionStartLine, newRevisionNumberOfLines);
this.changeState(new jct.test.rsc.snpsht.utils.diffutils.patch.state.CopyUnchangedLinesState(this.fsm, header));

}

}


}
