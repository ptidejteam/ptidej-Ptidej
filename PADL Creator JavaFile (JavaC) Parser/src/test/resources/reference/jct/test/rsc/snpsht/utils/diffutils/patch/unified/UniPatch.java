package jct.test.rsc.snpsht.utils.diffutils.patch.unified;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import jct.test.rsc.snpsht.utils.diffutils.IFSMDiffUtils;
import jct.test.rsc.snpsht.utils.diffutils.patch.IPatch;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.ContextFileReader;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.ContextFileWriter;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.DiffUtilsContext;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileReader;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileWriter;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.IDiffUtilsContext;
import jct.test.rsc.snpsht.utils.diffutils.patch.state.IPatchFSMState;
import jct.test.rsc.snpsht.utils.diffutils.patch.state.PatchStartState;
import jct.test.rsc.snpsht.utils.diffutils.state.IFSMEndState;
public class UniPatch
implements jct.test.rsc.snpsht.utils.diffutils.IFSMDiffUtils, jct.test.rsc.snpsht.utils.diffutils.patch.IPatch
{
public void <init>()
{
this.<init>();

}

private jct.test.rsc.snpsht.utils.diffutils.patch.context.IDiffUtilsContext context;

private jct.test.rsc.snpsht.utils.diffutils.patch.state.IPatchFSMState currentState;

public boolean patch(java.io.InputStream oldRevision, java.io.OutputStream newRevision, java.io.InputStream patch) throws java.io.IOException
{
this.context = new jct.test.rsc.snpsht.utils.diffutils.patch.context.DiffUtilsContext(new jct.test.rsc.snpsht.utils.diffutils.patch.context.ContextFileReader(oldRevision), new jct.test.rsc.snpsht.utils.diffutils.patch.context.ContextFileWriter(newRevision), new jct.test.rsc.snpsht.utils.diffutils.patch.context.ContextFileReader(patch));
this.currentState = new jct.test.rsc.snpsht.utils.diffutils.patch.state.PatchStartState(this);
this.currentState.process();
this.context.close();
return (this.currentState instanceof jct.test.rsc.snpsht.utils.diffutils.state.IFSMEndState);

}

public jct.test.rsc.snpsht.utils.diffutils.patch.context.IDiffUtilsContext getContext()
{
return this.context;

}

public void changeState(jct.test.rsc.snpsht.utils.diffutils.patch.state.IPatchFSMState state) throws java.io.IOException
{
this.currentState = state;
this.currentState.process();

}

public jct.test.rsc.snpsht.utils.diffutils.patch.state.IPatchFSMState getCurrentState()
{
return this.currentState;

}


}
