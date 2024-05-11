package jct.test.rsc.snpsht.utils.diffutils.patch.state;
import java.io.IOException;
import jct.test.rsc.snpsht.utils.diffutils.patch.unified.UniPatch;
public class PatchErrorState
extends jct.test.rsc.snpsht.utils.diffutils.patch.state.AbstractPatchFSMState
implements jct.test.rsc.snpsht.utils.diffutils.patch.state.IPatchErrorState
{
private int type;

private java.lang.String message;

public void <init>(jct.test.rsc.snpsht.utils.diffutils.patch.unified.UniPatch fsm, int type, java.lang.String message)
{
this.<init>(fsm);
this.message = message;
this.type = type;

}

public int getErrorType()
{
return this.type;

}

public java.lang.String getMessage()
{
return this.message;

}

public void process() throws java.io.IOException
{

}


}
