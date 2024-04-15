package jct.test.rsc.snpsht.utils.diffutils.patch;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import jct.test.rsc.snpsht.utils.diffutils.IFSMDiffUtils;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileReader;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileWriter;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.IDiffUtilsContext;
import jct.test.rsc.snpsht.utils.diffutils.patch.state.IPatchFSMState;
public interface IPatch
extends jct.test.rsc.snpsht.utils.diffutils.IFSMDiffUtils
{
abstract public boolean patch(java.io.InputStream oldRevision, java.io.OutputStream newRevision, java.io.InputStream patch) throws java.io.IOException
{

}

abstract public jct.test.rsc.snpsht.utils.diffutils.patch.context.IDiffUtilsContext getContext()
{

}


}
