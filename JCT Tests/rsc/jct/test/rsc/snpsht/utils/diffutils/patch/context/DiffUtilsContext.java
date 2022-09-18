package jct.test.rsc.snpsht.utils.diffutils.patch.context;
import java.io.IOException;
public class DiffUtilsContext
implements jct.test.rsc.snpsht.utils.diffutils.patch.context.IDiffUtilsContext
{
private jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileIO diffContext;

private jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileIO newRevisionContext;

private jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileReader oldRevisionContext;

public void <init>(jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileReader oldRevisionContext, jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileIO newRevisionContext, jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileIO diffContext)
{
this.<init>();
this.newRevisionContext = newRevisionContext;
this.diffContext = diffContext;
this.oldRevisionContext = oldRevisionContext;

}

public jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileIO getNewRevisionContext()
{
return this.newRevisionContext;

}

public jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileReader getOldRevisionContext()
{
return this.oldRevisionContext;

}

public jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileIO getDiffContext()
{
return this.diffContext;

}

public void close() throws java.io.IOException
{
this.diffContext.close();
this.newRevisionContext.close();
this.oldRevisionContext.close();

}


}
