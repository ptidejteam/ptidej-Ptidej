package jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.ICvsFsCommitConstraint;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.IModeler;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.CommitGroup;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.IGroup;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.IGroupingConstraint;
public class CommitGrouper
implements jct.test.rsc.snpsht.verfilesystem.cvsimpl.IModeler
{
private java.util.List constraints;

public void <init>()
{
this.<init>();
this.constraints = new java.util.ArrayList();

}

public void clearConstraints()
{
this.constraints.clear();

}

public java.util.List getConstraints()
{
return this.constraints;

}

public void addConstraint(jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.IGroupingConstraint constraint)
{
this.constraints.add(constraint);

}

public void removeConstraint(jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.IGroupingConstraint constraint)
{
this.constraints.remove(constraint);

}

public jct.test.rsc.snpsht.verfilesystem.VerFsManager modl(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
try
{
this.modl((jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager)manager);

}
catch(java.lang.ClassCastException e) 
{
throw new java.lang.IllegalArgumentException("Unexpected manager type.
Received manager should be a 'VerFsManager', but is a '" + manager.getClass().getName() + "'");

}
return manager;

}

public jct.test.rsc.snpsht.verfilesystem.VerFsManager modl(jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager manager)
{
java.util.Set groups = new java.util.HashSet();
jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.CommitGroup mainGroup = new jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.CommitGroup(new java.util.HashSet(manager.getAllSimplesRevisions()));
groups.add(mainGroup);
for(jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.IGroupingConstraint constraint : this.constraints) 
{
groups = constraint.applyContrainst(groups);

}
this.makeCommits(manager, groups);
manager.setConstraints(this.constraints.toArray(new jct.test.rsc.snpsht.verfilesystem.cvsimpl.ICvsFsCommitConstraint[0]));
return manager;

}

private void makeCommits(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager, java.util.Set groups)
{
for(jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.IGroup group : groups) 
{
manager.setAsCommit(group.getGroup().toArray(new jct.test.rsc.snpsht.verfilesystem.VerFsFileRev[0]));

}

}


}
