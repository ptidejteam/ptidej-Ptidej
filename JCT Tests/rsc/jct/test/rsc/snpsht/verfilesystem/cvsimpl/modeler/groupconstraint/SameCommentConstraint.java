package jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsComment;
public class SameCommentConstraint
implements jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.IGroupingConstraint
{
public void <init>()
{
this.<init>();

}

public java.util.Set applyContrainst(java.util.Set groups)
{
java.util.Set outGroups = new java.util.HashSet();
for(jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.IGroup group : groups) 
{
outGroups.addAll(this.applyConstraint(group));

}
return outGroups;

}

private java.util.Set applyConstraint(jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.IGroup group)
{
jct.test.rsc.snpsht.verfilesystem.flag.VerFsComment currComment;
jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.IGroup currGroup;
java.util.Map groupsMap = new java.util.HashMap();
for(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev fileRev : group.getGroup()) 
{
currComment = (jct.test.rsc.snpsht.verfilesystem.flag.VerFsComment)fileRev.getComment();
if(currComment == null) throw new java.lang.IllegalArgumentException("File revision '" + fileRev.getId() + "' is not commented.
" + "All files revions should contains a comment.");
currGroup = groupsMap.get(currComment);
if(currGroup == null) 
{
currGroup = new jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.CommitGroup();
groupsMap.put(currComment, currGroup);

}
currGroup.add(fileRev);

}
return new java.util.HashSet(groupsMap.values());

}

public java.lang.String[][] getAttributes()
{
return new java.lang.String[][];

}

public java.lang.String getDescription()
{
return "Same comment grouping constraint";

}

public java.lang.String getName()
{
return "sameComment";

}


}
