package jct.test.rsc.snpsht.verfilesystem.cvsimpl;
import jct.test.rsc.snpsht.utils.cvsutils.CVSRoot;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.SameAuthorConstraint;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.SameCommentConstraint;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.SlidingWindowConstraint;
public class CvsFsManager
extends jct.test.rsc.snpsht.verfilesystem.VerFsManager
{
private jct.test.rsc.snpsht.verfilesystem.cvsimpl.ICvsFsCommitConstraint[] constraints;

private jct.test.rsc.snpsht.utils.cvsutils.CVSRoot cvsRoot;

final public static java.lang.String BRANCHER_USER_NAME = "Brancher";

public void <init>()
{
this.<init>(null);

}

public void <init>(jct.test.rsc.snpsht.utils.cvsutils.CVSRoot cvsRoot)
{
this.<init>();
this.setCvsRoot(cvsRoot);
this.constraints = new jct.test.rsc.snpsht.verfilesystem.cvsimpl.ICvsFsCommitConstraint[0];

}

public void setConstraints(jct.test.rsc.snpsht.verfilesystem.cvsimpl.ICvsFsCommitConstraint[] constraints)
{
this.constraints = constraints;

}

public void addConstraint(java.lang.String type)
{
this.addConstraint(type, new java.lang.String[][]);

}

public void addConstraint(java.lang.String type, java.lang.String[][] attributes)
{
jct.test.rsc.snpsht.verfilesystem.cvsimpl.ICvsFsCommitConstraint constr;
java.lang.String size = null;
if(type.compareTo("sameComment") == 0) 
{
constr = new jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.SameCommentConstraint();

}
 else if(type.compareTo("sameAuthor") == 0) 
{
constr = new jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.SameAuthorConstraint();

}
 else if(type.compareTo("slidingWindows") == 0) 
{
for(java.lang.String[] attr : attributes) 
{
if(attr[0].compareTo("size") == 0) 
{
size = attr[1];
break;

}

}
if(size == null) 
{
constr = new jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.SlidingWindowConstraint();

}
 else 
{
constr = new jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.SlidingWindowConstraint(java.lang.Double.valueOf(size));

}

}
 else 
{
throw new java.lang.IllegalArgumentException("Can't instanciate ICvsFsCommitConstraint. Unknow type " + type);

}
this.addConstraint(constr);

}

public void addConstraint(jct.test.rsc.snpsht.verfilesystem.cvsimpl.ICvsFsCommitConstraint constraint)
{
jct.test.rsc.snpsht.verfilesystem.cvsimpl.ICvsFsCommitConstraint[] newConstr = new jct.test.rsc.snpsht.verfilesystem.cvsimpl.ICvsFsCommitConstraint[this.constraints.length + 1];
java.lang.System.arraycopy(this.constraints, 0, newConstr, 0, this.constraints.length);
newConstr[this.constraints.length] = constraint;
this.constraints = newConstr;

}

public jct.test.rsc.snpsht.verfilesystem.cvsimpl.ICvsFsCommitConstraint[] getConstraints()
{
return this.constraints;

}

public void setCvsRoot(jct.test.rsc.snpsht.utils.cvsutils.CVSRoot cvsRoot)
{
this.cvsRoot = cvsRoot;

}

public jct.test.rsc.snpsht.utils.cvsutils.CVSRoot getCvsRoot()
{
return this.cvsRoot;

}


}
