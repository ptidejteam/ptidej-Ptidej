package jct.test.rsc.snpsht.utils.diffutils.patch.state;
import java.io.IOException;
import jct.test.rsc.snpsht.utils.diffutils.DiffHeader;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileReader;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileWriter;
import jct.test.rsc.snpsht.utils.diffutils.patch.unified.UniPatch;
import jct.test.rsc.snpsht.utils.diffutils.patch.unified.state.PatchLineState;
public class CopyUnchangedLinesState
extends jct.test.rsc.snpsht.utils.diffutils.patch.state.AbstractPatchFSMState
{
private jct.test.rsc.snpsht.utils.diffutils.DiffHeader header;

public void <init>(jct.test.rsc.snpsht.utils.diffutils.patch.unified.UniPatch fsm, jct.test.rsc.snpsht.utils.diffutils.DiffHeader header)
{
this.<init>(fsm);
this.header = header;

}

public void process() throws java.io.IOException
{
java.lang.String line;
jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileReader oldRevision = this.getContext().getOldRevisionContext();
jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileWriter newRevision = this.getContext().getNewRevisionContext();
while(oldRevision.getLineNumber() < this.header.getOldRevisionRange().getStartLine() - 1 && (line = oldRevision.readLine()) != null) 
{
newRevision.writeLine(line);
java.lang.System.out.println(line + " - " + oldRevision.getLineNumber() + " - " + newRevision.getLineNumber());

}
java.lang.System.out.println(oldRevision.isEOFReached());
java.lang.System.out.println(this.header.getOldRevisionRange().getStartLine());
if(oldRevision.getLineNumber() + 1 == this.header.getOldRevisionRange().getStartLine() && newRevision.getLineNumber() + 1 == this.header.getNewRevisionRange().getStartLine()) 
{
this.changeState(new jct.test.rsc.snpsht.utils.diffutils.patch.unified.state.PatchLineState(this.fsm, this.header));

}
 else 
{
java.lang.System.out.println("Error");
this.changeState(new jct.test.rsc.snpsht.utils.diffutils.patch.state.PatchErrorState(this.fsm, jct.test.rsc.snpsht.utils.diffutils.patch.state.IPatchErrorState.MISMATCH_HEADER_START_LINE, "Index in old/new (" + oldRevision.getLineNumber() + "/" + newRevision.getLineNumber() + ") revision mismatch with start line index in header (" + this.header.getOldRevisionRange().getStartLine() + "/" + this.header.getNewRevisionRange().getStartLine() + ")"));

}

}


}
