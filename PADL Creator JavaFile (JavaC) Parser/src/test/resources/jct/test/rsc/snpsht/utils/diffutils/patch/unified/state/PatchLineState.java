package jct.test.rsc.snpsht.utils.diffutils.patch.unified.state;
import java.io.IOException;
import jct.test.rsc.snpsht.utils.diffutils.DiffHeader;
import jct.test.rsc.snpsht.utils.diffutils.patch.state.AbstractPatchFSMState;
import jct.test.rsc.snpsht.utils.diffutils.patch.state.CopyEndOfFileState;
import jct.test.rsc.snpsht.utils.diffutils.patch.state.IPatchErrorState;
import jct.test.rsc.snpsht.utils.diffutils.patch.state.PatchErrorState;
import jct.test.rsc.snpsht.utils.diffutils.patch.unified.UniPatch;
public class PatchLineState
extends jct.test.rsc.snpsht.utils.diffutils.patch.state.AbstractPatchFSMState
implements jct.test.rsc.snpsht.utils.diffutils.patch.unified.state.IUniPatchFSMState
{
private jct.test.rsc.snpsht.utils.diffutils.DiffHeader header;

public void <init>(jct.test.rsc.snpsht.utils.diffutils.patch.unified.UniPatch fsm, jct.test.rsc.snpsht.utils.diffutils.DiffHeader header)
{
this.<init>(fsm);
this.header = header;

}

private boolean checkHeaderContract()
{
int indOldCont;
int indOldHead;
int indNewCont;
int indNewHead;
java.lang.System.out.println(this.header.getOldRevisionRange().getStartLine() + this.header.getOldRevisionRange().getNumberOfLines());
java.lang.System.out.println(this.getContext().getOldRevisionContext().getLineNumber());
java.lang.System.out.println(this.header.getNewRevisionRange().getStartLine() + this.header.getNewRevisionRange().getNumberOfLines());
java.lang.System.out.println(this.getContext().getNewRevisionContext().getLineNumber());
indOldCont = this.header.getOldRevisionRange().getStartLine() + this.header.getOldRevisionRange().getNumberOfLines();
indOldHead = this.getContext().getOldRevisionContext().getLineNumber();
indNewCont = this.header.getNewRevisionRange().getStartLine() + this.header.getNewRevisionRange().getNumberOfLines();
indNewHead = this.getContext().getNewRevisionContext().getLineNumber();
return (indOldHead + 1 == indOldCont && indNewHead + 1 == indNewCont);

}

public void process() throws java.io.IOException
{
java.lang.String patchLine = this.getContext().getDiffContext().readLine();
java.lang.String oldLine;
java.lang.System.out.println("==========> " + patchLine);
if(patchLine == null) 
{
if(this.checkHeaderContract()) 
{
this.fsm.changeState(new jct.test.rsc.snpsht.utils.diffutils.patch.state.CopyEndOfFileState(this.fsm));

}
 else 
{
this.fsm.changeState(new jct.test.rsc.snpsht.utils.diffutils.patch.state.PatchErrorState(this.fsm, jct.test.rsc.snpsht.utils.diffutils.patch.state.IPatchErrorState.MISMATCH_HEADER_END_LINE, "Index in old/new revision mismatch with start line index in header "));

}

}
 else 
{
if(jct.test.rsc.snpsht.utils.diffutils.patch.state.AbstractPatchFSMState.HEADER_PATTERN.matcher(patchLine).find()) 
{
if(this.checkHeaderContract()) 
{
this.fsm.changeState(new jct.test.rsc.snpsht.utils.diffutils.patch.unified.state.ReadPatchHeaderState(this.fsm));

}
 else 
{
this.fsm.changeState(new jct.test.rsc.snpsht.utils.diffutils.patch.state.PatchErrorState(this.fsm, jct.test.rsc.snpsht.utils.diffutils.patch.state.IPatchErrorState.MISMATCH_HEADER_END_LINE, "Index in old/new revision mismatch with start line index in header "));

}

}
 else 
{
if(jct.test.rsc.snpsht.utils.diffutils.patch.state.AbstractPatchFSMState.UNCHANGED_LINE_PATTERN.matcher(patchLine).find()) 
{
patchLine = patchLine.substring(1);
oldLine = this.getContext().getOldRevisionContext().readLine();
if(oldLine.compareTo(patchLine) != 0) 
{
this.fsm.changeState(new jct.test.rsc.snpsht.utils.diffutils.patch.state.PatchErrorState(this.fsm, jct.test.rsc.snpsht.utils.diffutils.patch.state.IPatchErrorState.MISMATCH_UNCHANGED_LINE, "Missmatch unchanged line.
Find in old revision: " + oldLine + "
Should patch: " + patchLine));
return;

}
 else 
{
this.getContext().getNewRevisionContext().writeLine(patchLine);

}

}
 else if(jct.test.rsc.snpsht.utils.diffutils.patch.state.AbstractPatchFSMState.ADDED_LINE_PATTERN.matcher(patchLine).find()) 
{
patchLine = patchLine.substring(1);
this.getContext().getNewRevisionContext().writeLine(patchLine);

}
 else if(jct.test.rsc.snpsht.utils.diffutils.patch.state.AbstractPatchFSMState.REMOVED_LINE_PATTERN.matcher(patchLine).find()) 
{
patchLine = patchLine.substring(1);
oldLine = this.getContext().getOldRevisionContext().readLine();
if(oldLine.compareTo(patchLine) != 0) 
{
this.fsm.changeState(new jct.test.rsc.snpsht.utils.diffutils.patch.state.PatchErrorState(this.fsm, jct.test.rsc.snpsht.utils.diffutils.patch.state.IPatchErrorState.MISMATCH_REMOVED_LINE, "Missmatch removed line.
Find in old revision: " + oldLine + "
Should patch: " + patchLine));
return;

}

}
 else 
{
this.fsm.changeState(new jct.test.rsc.snpsht.utils.diffutils.patch.state.PatchErrorState(this.fsm, jct.test.rsc.snpsht.utils.diffutils.patch.state.IPatchErrorState.UNKNOWN_PATCH_LINE_TYPE, "Unknow patch line type for " + patchLine));
return;

}
this.fsm.changeState(this);

}

}

}


}
