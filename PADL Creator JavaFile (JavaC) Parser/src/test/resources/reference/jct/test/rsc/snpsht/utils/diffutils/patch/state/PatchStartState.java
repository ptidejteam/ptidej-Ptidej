package jct.test.rsc.snpsht.utils.diffutils.patch.state;
import java.io.IOException;
import jct.test.rsc.snpsht.utils.diffutils.patch.unified.UniPatch;
import jct.test.rsc.snpsht.utils.diffutils.patch.unified.state.ReadPatchHeaderState;
public class PatchStartState
extends jct.test.rsc.snpsht.utils.diffutils.patch.state.AbstractPatchFSMState
{
public void <init>(jct.test.rsc.snpsht.utils.diffutils.patch.unified.UniPatch fsm)
{
this.<init>(fsm);

}

public void process() throws java.io.IOException
{
this.getContext().getDiffContext().readLine();
this.changeState(new jct.test.rsc.snpsht.utils.diffutils.patch.unified.state.ReadPatchHeaderState(this.fsm));

}


}
