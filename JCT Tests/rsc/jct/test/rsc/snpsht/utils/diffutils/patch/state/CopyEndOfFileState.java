package jct.test.rsc.snpsht.utils.diffutils.patch.state;
import java.io.IOException;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileReader;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileWriter;
import jct.test.rsc.snpsht.utils.diffutils.patch.unified.UniPatch;
import jct.test.rsc.snpsht.utils.diffutils.state.IFSMEndState;
public class CopyEndOfFileState
extends jct.test.rsc.snpsht.utils.diffutils.patch.state.AbstractPatchFSMState
implements jct.test.rsc.snpsht.utils.diffutils.state.IFSMEndState
{
public void <init>(jct.test.rsc.snpsht.utils.diffutils.patch.unified.UniPatch fsm)
{
this.<init>(fsm);

}

public void process() throws java.io.IOException
{
java.lang.String line;
jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileReader oldRevision = this.getContext().getOldRevisionContext();
jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileWriter newRevision = this.getContext().getNewRevisionContext();
while((line = oldRevision.readLine()) != null) 
{
newRevision.writeLine(line);
java.lang.System.out.println(line + " - " + oldRevision.getLineNumber() + " - " + newRevision.getLineNumber());

}
java.lang.System.out.println(oldRevision.isEOFReached());

}


}
